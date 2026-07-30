/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import common.Assert;
import common.Logger;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.biff.BaseCompoundFile;
import jxl.biff.IntegerHelper;
import jxl.read.biff.BiffException;
import jxl.write.biff.CopyAdditionalPropertySetsException;

final class CompoundFile
extends BaseCompoundFile {
    private static Logger logger = Logger.getLogger(class$jxl$write$biff$CompoundFile == null ? (class$jxl$write$biff$CompoundFile = CompoundFile.class$("jxl.write.biff.CompoundFile")) : class$jxl$write$biff$CompoundFile);
    private OutputStream out;
    private byte[] excelData;
    private int size;
    private int requiredSize;
    private int numBigBlockDepotBlocks;
    private int numSmallBlockDepotChainBlocks;
    private int numSmallBlockDepotBlocks;
    private int numExtensionBlocks;
    private int extensionBlock;
    private int excelDataBlocks;
    private int rootStartBlock;
    private int excelDataStartBlock;
    private int bbdStartBlock;
    private int sbdStartBlockChain;
    private int sbdStartBlock;
    private int additionalPropertyBlocks;
    private int numSmallBlocks;
    private int numPropertySets;
    private int numRootEntryBlocks;
    private ArrayList additionalPropertySets;
    private HashMap standardPropertySets;
    private int bbdPos;
    private byte[] bigBlockDepot;
    static /* synthetic */ Class class$jxl$write$biff$CompoundFile;

    public CompoundFile(byte[] data, int l, OutputStream os, jxl.read.biff.CompoundFile rcf) throws CopyAdditionalPropertySetsException, IOException {
        this.size = l;
        this.excelData = data;
        this.readAdditionalPropertySets(rcf);
        this.numRootEntryBlocks = 1;
        this.numPropertySets = 4 + (this.additionalPropertySets != null ? this.additionalPropertySets.size() : 0);
        if (this.additionalPropertySets != null) {
            this.numSmallBlockDepotChainBlocks = this.getBigBlocksRequired(this.numSmallBlocks * 4);
            this.numSmallBlockDepotBlocks = this.getBigBlocksRequired(this.numSmallBlocks * 64);
            this.numRootEntryBlocks += this.getBigBlocksRequired(this.additionalPropertySets.size() * 128);
        }
        int blocks = this.getBigBlocksRequired(l);
        this.requiredSize = l < 4096 ? 4096 : blocks * 512;
        this.out = os;
        this.excelDataBlocks = this.requiredSize / 512;
        this.numBigBlockDepotBlocks = 1;
        int blockChainLength = 109;
        int startTotalBlocks = this.excelDataBlocks + 8 + 8 + this.additionalPropertyBlocks + this.numSmallBlockDepotBlocks + this.numSmallBlockDepotChainBlocks + this.numRootEntryBlocks;
        int totalBlocks = startTotalBlocks + this.numBigBlockDepotBlocks;
        this.numBigBlockDepotBlocks = (int)Math.ceil((double)totalBlocks / 128.0);
        totalBlocks = startTotalBlocks + this.numBigBlockDepotBlocks;
        this.numBigBlockDepotBlocks = (int)Math.ceil((double)totalBlocks / 128.0);
        totalBlocks = startTotalBlocks + this.numBigBlockDepotBlocks;
        if (this.numBigBlockDepotBlocks > blockChainLength - 1) {
            this.extensionBlock = 0;
            int bbdBlocksLeft = this.numBigBlockDepotBlocks - blockChainLength + 1;
            this.numExtensionBlocks = (int)Math.ceil((double)bbdBlocksLeft / 127.0);
            totalBlocks = startTotalBlocks + this.numExtensionBlocks + this.numBigBlockDepotBlocks;
            this.numBigBlockDepotBlocks = (int)Math.ceil((double)totalBlocks / 128.0);
            totalBlocks = startTotalBlocks + this.numExtensionBlocks + this.numBigBlockDepotBlocks;
        } else {
            this.extensionBlock = -2;
            this.numExtensionBlocks = 0;
        }
        this.excelDataStartBlock = this.numExtensionBlocks;
        this.sbdStartBlock = -2;
        if (this.additionalPropertySets != null && this.numSmallBlockDepotBlocks != 0) {
            this.sbdStartBlock = this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks + 16;
        }
        this.sbdStartBlockChain = -2;
        if (this.sbdStartBlock != -2) {
            this.sbdStartBlockChain = this.sbdStartBlock + this.numSmallBlockDepotBlocks;
        }
        this.bbdStartBlock = this.sbdStartBlockChain != -2 ? this.sbdStartBlockChain + this.numSmallBlockDepotChainBlocks : this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks + 16;
        this.rootStartBlock = this.bbdStartBlock + this.numBigBlockDepotBlocks;
        if (totalBlocks != this.rootStartBlock + this.numRootEntryBlocks) {
            logger.warn("Root start block and total blocks are inconsistent  generated file may be corrupt");
            logger.warn("RootStartBlock " + this.rootStartBlock + " totalBlocks " + totalBlocks);
        }
    }

    private void readAdditionalPropertySets(jxl.read.biff.CompoundFile readCompoundFile) throws CopyAdditionalPropertySetsException, IOException {
        if (readCompoundFile == null) {
            return;
        }
        this.additionalPropertySets = new ArrayList();
        this.standardPropertySets = new HashMap();
        int blocksRequired = 0;
        int numPropertySets = readCompoundFile.getNumberOfPropertySets();
        for (int i = 0; i < numPropertySets; ++i) {
            BaseCompoundFile.PropertyStorage ps = readCompoundFile.getPropertySet(i);
            boolean standard = false;
            if (ps.name.equalsIgnoreCase("Root Entry")) {
                standard = true;
                ReadPropertyStorage rps = new ReadPropertyStorage(ps, null, i);
                this.standardPropertySets.put("Root Entry", rps);
            }
            for (int j = 0; j < STANDARD_PROPERTY_SETS.length && !standard; ++j) {
                if (!ps.name.equalsIgnoreCase(STANDARD_PROPERTY_SETS[j])) continue;
                BaseCompoundFile.PropertyStorage ps2 = readCompoundFile.findPropertyStorage(ps.name);
                Assert.verify(ps2 != null);
                if (ps2 != ps) continue;
                standard = true;
                ReadPropertyStorage rps = new ReadPropertyStorage(ps, null, i);
                this.standardPropertySets.put(STANDARD_PROPERTY_SETS[j], rps);
            }
            if (standard) continue;
            try {
                int blocks;
                byte[] data = null;
                data = ps.size > 0 ? readCompoundFile.getStream(i) : new byte[]{};
                ReadPropertyStorage rps = new ReadPropertyStorage(ps, data, i);
                this.additionalPropertySets.add(rps);
                if (data.length > 4096) {
                    blocks = this.getBigBlocksRequired(data.length);
                    blocksRequired += blocks;
                    continue;
                }
                blocks = this.getSmallBlocksRequired(data.length);
                this.numSmallBlocks += blocks;
                continue;
            }
            catch (BiffException e) {
                logger.error(e);
                throw new CopyAdditionalPropertySetsException();
            }
        }
        this.additionalPropertyBlocks = blocksRequired;
    }

    public void write() throws IOException {
        this.writeHeader();
        this.writeExcelData();
        this.writeDocumentSummaryData();
        this.writeSummaryData();
        this.writeAdditionalPropertySets();
        this.writeSmallBlockDepot();
        this.writeSmallBlockDepotChain();
        this.writeBigBlockDepot();
        this.writePropertySets();
    }

    private void writeAdditionalPropertySets() throws IOException {
        if (this.additionalPropertySets == null) {
            return;
        }
        Iterator i = this.additionalPropertySets.iterator();
        while (i.hasNext()) {
            ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
            byte[] data = rps.data;
            if (data.length <= 4096) continue;
            int numBlocks = this.getBigBlocksRequired(data.length);
            int requiredSize = numBlocks * 512;
            this.out.write(data, 0, data.length);
            byte[] padding = new byte[requiredSize - data.length];
            this.out.write(padding, 0, padding.length);
        }
    }

    private void writeExcelData() throws IOException {
        this.out.write(this.excelData, 0, this.size);
        byte[] padding = new byte[this.requiredSize - this.size];
        this.out.write(padding);
    }

    private void writeDocumentSummaryData() throws IOException {
        byte[] padding = new byte[4096];
        this.out.write(padding);
    }

    private void writeSummaryData() throws IOException {
        byte[] padding = new byte[4096];
        this.out.write(padding);
    }

    private void writeHeader() throws IOException {
        int i;
        byte[] headerBlock = new byte[512];
        byte[] extensionBlockData = new byte[512 * this.numExtensionBlocks];
        System.arraycopy(IDENTIFIER, 0, headerBlock, 0, IDENTIFIER.length);
        headerBlock[24] = 62;
        headerBlock[26] = 3;
        headerBlock[28] = -2;
        headerBlock[29] = -1;
        headerBlock[30] = 9;
        headerBlock[32] = 6;
        headerBlock[57] = 16;
        IntegerHelper.getFourBytes(this.numBigBlockDepotBlocks, headerBlock, 44);
        IntegerHelper.getFourBytes(this.sbdStartBlockChain, headerBlock, 60);
        IntegerHelper.getFourBytes(this.numSmallBlockDepotChainBlocks, headerBlock, 64);
        IntegerHelper.getFourBytes(this.extensionBlock, headerBlock, 68);
        IntegerHelper.getFourBytes(this.numExtensionBlocks, headerBlock, 72);
        IntegerHelper.getFourBytes(this.rootStartBlock, headerBlock, 48);
        int pos = 76;
        int blocksToWrite = Math.min(this.numBigBlockDepotBlocks, 109);
        int blocksWritten = 0;
        for (i = 0; i < blocksToWrite; ++i) {
            IntegerHelper.getFourBytes(this.bbdStartBlock + i, headerBlock, pos);
            pos += 4;
            ++blocksWritten;
        }
        for (i = pos; i < 512; ++i) {
            headerBlock[i] = -1;
        }
        this.out.write(headerBlock);
        pos = 0;
        for (int extBlock = 0; extBlock < this.numExtensionBlocks; ++extBlock) {
            blocksToWrite = Math.min(this.numBigBlockDepotBlocks - blocksWritten, 127);
            for (int j = 0; j < blocksToWrite; ++j) {
                IntegerHelper.getFourBytes(this.bbdStartBlock + blocksWritten + j, extensionBlockData, pos);
                pos += 4;
            }
            int nextBlock = (blocksWritten += blocksToWrite) == this.numBigBlockDepotBlocks ? -2 : extBlock + 1;
            IntegerHelper.getFourBytes(nextBlock, extensionBlockData, pos);
            pos += 4;
        }
        if (this.numExtensionBlocks > 0) {
            for (i = pos; i < extensionBlockData.length; ++i) {
                extensionBlockData[i] = -1;
            }
            this.out.write(extensionBlockData);
        }
    }

    private void checkBbdPos() throws IOException {
        if (this.bbdPos >= 512) {
            this.out.write(this.bigBlockDepot);
            this.bigBlockDepot = new byte[512];
            this.bbdPos = 0;
        }
    }

    private void writeBlockChain(int startBlock, int numBlocks) throws IOException {
        int bbdBlocks;
        int blockNumber = startBlock + 1;
        for (int blocksToWrite = numBlocks - 1; blocksToWrite > 0; blocksToWrite -= bbdBlocks) {
            bbdBlocks = Math.min(blocksToWrite, (512 - this.bbdPos) / 4);
            for (int i = 0; i < bbdBlocks; ++i) {
                IntegerHelper.getFourBytes(blockNumber, this.bigBlockDepot, this.bbdPos);
                this.bbdPos += 4;
                ++blockNumber;
            }
            this.checkBbdPos();
        }
        IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
        this.bbdPos += 4;
        this.checkBbdPos();
    }

    private void writeAdditionalPropertySetBlockChains() throws IOException {
        if (this.additionalPropertySets == null) {
            return;
        }
        int blockNumber = this.excelDataStartBlock + this.excelDataBlocks + 16;
        Iterator i = this.additionalPropertySets.iterator();
        while (i.hasNext()) {
            ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
            if (rps.data.length <= 4096) continue;
            int numBlocks = this.getBigBlocksRequired(rps.data.length);
            this.writeBlockChain(blockNumber, numBlocks);
            blockNumber += numBlocks;
        }
    }

    private void writeSmallBlockDepotChain() throws IOException {
        if (this.sbdStartBlockChain == -2) {
            return;
        }
        byte[] smallBlockDepotChain = new byte[this.numSmallBlockDepotChainBlocks * 512];
        int pos = 0;
        int sbdBlockNumber = 1;
        Iterator i = this.additionalPropertySets.iterator();
        while (i.hasNext()) {
            ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
            if (rps.data.length > 4096 || rps.data.length == 0) continue;
            int numSmallBlocks = this.getSmallBlocksRequired(rps.data.length);
            for (int j = 0; j < numSmallBlocks - 1; ++j) {
                IntegerHelper.getFourBytes(sbdBlockNumber, smallBlockDepotChain, pos);
                pos += 4;
                ++sbdBlockNumber;
            }
            IntegerHelper.getFourBytes(-2, smallBlockDepotChain, pos);
            pos += 4;
            ++sbdBlockNumber;
        }
        this.out.write(smallBlockDepotChain);
    }

    private void writeSmallBlockDepot() throws IOException {
        if (this.additionalPropertySets == null) {
            return;
        }
        byte[] smallBlockDepot = new byte[this.numSmallBlockDepotBlocks * 512];
        int pos = 0;
        Iterator i = this.additionalPropertySets.iterator();
        while (i.hasNext()) {
            ReadPropertyStorage rps = (ReadPropertyStorage)i.next();
            if (rps.data.length > 4096) continue;
            int smallBlocks = this.getSmallBlocksRequired(rps.data.length);
            int length = smallBlocks * 64;
            System.arraycopy(rps.data, 0, smallBlockDepot, pos, rps.data.length);
            pos += length;
        }
        this.out.write(smallBlockDepot);
    }

    private void writeBigBlockDepot() throws IOException {
        int summaryInfoBlock;
        int i;
        this.bigBlockDepot = new byte[512];
        this.bbdPos = 0;
        for (int i2 = 0; i2 < this.numExtensionBlocks; ++i2) {
            IntegerHelper.getFourBytes(-3, this.bigBlockDepot, this.bbdPos);
            this.bbdPos += 4;
            this.checkBbdPos();
        }
        this.writeBlockChain(this.excelDataStartBlock, this.excelDataBlocks);
        for (i = summaryInfoBlock = this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks; i < summaryInfoBlock + 7; ++i) {
            IntegerHelper.getFourBytes(i + 1, this.bigBlockDepot, this.bbdPos);
            this.bbdPos += 4;
            this.checkBbdPos();
        }
        IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
        this.bbdPos += 4;
        this.checkBbdPos();
        for (i = summaryInfoBlock + 8; i < summaryInfoBlock + 15; ++i) {
            IntegerHelper.getFourBytes(i + 1, this.bigBlockDepot, this.bbdPos);
            this.bbdPos += 4;
            this.checkBbdPos();
        }
        IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
        this.bbdPos += 4;
        this.checkBbdPos();
        this.writeAdditionalPropertySetBlockChains();
        if (this.sbdStartBlock != -2) {
            this.writeBlockChain(this.sbdStartBlock, this.numSmallBlockDepotBlocks);
            this.writeBlockChain(this.sbdStartBlockChain, this.numSmallBlockDepotChainBlocks);
        }
        for (i = 0; i < this.numBigBlockDepotBlocks; ++i) {
            IntegerHelper.getFourBytes(-3, this.bigBlockDepot, this.bbdPos);
            this.bbdPos += 4;
            this.checkBbdPos();
        }
        this.writeBlockChain(this.rootStartBlock, this.numRootEntryBlocks);
        if (this.bbdPos != 0) {
            for (i = this.bbdPos; i < 512; ++i) {
                this.bigBlockDepot[i] = -1;
            }
            this.out.write(this.bigBlockDepot);
        }
    }

    private int getBigBlocksRequired(int length) {
        int blocks = length / 512;
        return length % 512 > 0 ? blocks + 1 : blocks;
    }

    private int getSmallBlocksRequired(int length) {
        int blocks = length / 64;
        return length % 64 > 0 ? blocks + 1 : blocks;
    }

    private void writePropertySets() throws IOException {
        ReadPropertyStorage rps;
        byte[] propertySetStorage = new byte[512 * this.numRootEntryBlocks];
        int pos = 0;
        int[] mappings = null;
        if (this.additionalPropertySets != null) {
            mappings = new int[this.numPropertySets];
            for (int i = 0; i < STANDARD_PROPERTY_SETS.length; ++i) {
                ReadPropertyStorage rps2 = (ReadPropertyStorage)this.standardPropertySets.get(STANDARD_PROPERTY_SETS[i]);
                if (rps2 != null) {
                    mappings[rps2.number] = i;
                    continue;
                }
                logger.warn("Standard property set " + STANDARD_PROPERTY_SETS[i] + " not present in source file");
            }
            int newMapping = STANDARD_PROPERTY_SETS.length;
            Iterator i = this.additionalPropertySets.iterator();
            while (i.hasNext()) {
                ReadPropertyStorage rps3 = (ReadPropertyStorage)i.next();
                mappings[rps3.number] = newMapping++;
            }
        }
        int child = 0;
        int previous = 0;
        int next = 0;
        int size = 0;
        if (this.additionalPropertySets != null) {
            size += this.getBigBlocksRequired(this.requiredSize) * 512;
            size += this.getBigBlocksRequired(4096) * 512;
            size += this.getBigBlocksRequired(4096) * 512;
            Iterator i = this.additionalPropertySets.iterator();
            while (i.hasNext()) {
                rps = (ReadPropertyStorage)i.next();
                if (rps.propertyStorage.type == 1) continue;
                if (rps.propertyStorage.size >= 4096) {
                    size += this.getBigBlocksRequired(rps.propertyStorage.size) * 512;
                    continue;
                }
                size += this.getSmallBlocksRequired(rps.propertyStorage.size) * 64;
            }
        }
        BaseCompoundFile.PropertyStorage ps = (BaseCompoundFile)this.new BaseCompoundFile.PropertyStorage("Root Entry");
        ps.setType(5);
        ps.setStartBlock(this.sbdStartBlock);
        ps.setSize(size);
        ps.setPrevious(-1);
        ps.setNext(-1);
        ps.setColour(0);
        child = 1;
        if (this.additionalPropertySets != null) {
            rps = (ReadPropertyStorage)this.standardPropertySets.get("Root Entry");
            child = mappings[rps.propertyStorage.child];
        }
        ps.setChild(child);
        System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
        pos += 128;
        ps = (BaseCompoundFile)this.new BaseCompoundFile.PropertyStorage("Workbook");
        ps.setType(2);
        ps.setStartBlock(this.excelDataStartBlock);
        ps.setSize(this.requiredSize);
        previous = 3;
        next = -1;
        if (this.additionalPropertySets != null) {
            rps = (ReadPropertyStorage)this.standardPropertySets.get("Workbook");
            previous = rps.propertyStorage.previous != -1 ? mappings[rps.propertyStorage.previous] : -1;
            next = rps.propertyStorage.next != -1 ? mappings[rps.propertyStorage.next] : -1;
        }
        ps.setPrevious(previous);
        ps.setNext(next);
        ps.setChild(-1);
        System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
        pos += 128;
        ps = (BaseCompoundFile)this.new BaseCompoundFile.PropertyStorage("\u0005SummaryInformation");
        ps.setType(2);
        ps.setStartBlock(this.excelDataStartBlock + this.excelDataBlocks);
        ps.setSize(4096);
        previous = 1;
        next = 3;
        if (this.additionalPropertySets != null && (rps = (ReadPropertyStorage)this.standardPropertySets.get("\u0005SummaryInformation")) != null) {
            previous = rps.propertyStorage.previous != -1 ? mappings[rps.propertyStorage.previous] : -1;
            next = rps.propertyStorage.next != -1 ? mappings[rps.propertyStorage.next] : -1;
        }
        ps.setPrevious(previous);
        ps.setNext(next);
        ps.setChild(-1);
        System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
        pos += 128;
        ps = (BaseCompoundFile)this.new BaseCompoundFile.PropertyStorage("\u0005DocumentSummaryInformation");
        ps.setType(2);
        ps.setStartBlock(this.excelDataStartBlock + this.excelDataBlocks + 8);
        ps.setSize(4096);
        ps.setPrevious(-1);
        ps.setNext(-1);
        ps.setChild(-1);
        System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
        pos += 128;
        if (this.additionalPropertySets == null) {
            this.out.write(propertySetStorage);
            return;
        }
        int bigBlock = this.excelDataStartBlock + this.excelDataBlocks + 16;
        int smallBlock = 0;
        Iterator i = this.additionalPropertySets.iterator();
        while (i.hasNext()) {
            ReadPropertyStorage rps4 = (ReadPropertyStorage)i.next();
            int block = rps4.data.length > 4096 ? bigBlock : smallBlock;
            ps = (BaseCompoundFile)this.new BaseCompoundFile.PropertyStorage(rps4.propertyStorage.name);
            ps.setType(rps4.propertyStorage.type);
            ps.setStartBlock(block);
            ps.setSize(rps4.propertyStorage.size);
            previous = rps4.propertyStorage.previous != -1 ? mappings[rps4.propertyStorage.previous] : -1;
            next = rps4.propertyStorage.next != -1 ? mappings[rps4.propertyStorage.next] : -1;
            child = rps4.propertyStorage.child != -1 ? mappings[rps4.propertyStorage.child] : -1;
            ps.setPrevious(previous);
            ps.setNext(next);
            ps.setChild(child);
            System.arraycopy(ps.data, 0, propertySetStorage, pos, 128);
            pos += 128;
            if (rps4.data.length > 4096) {
                bigBlock += this.getBigBlocksRequired(rps4.data.length);
                continue;
            }
            smallBlock += this.getSmallBlocksRequired(rps4.data.length);
        }
        this.out.write(propertySetStorage);
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    private static final class ReadPropertyStorage {
        BaseCompoundFile.PropertyStorage propertyStorage;
        byte[] data;
        int number;

        ReadPropertyStorage(BaseCompoundFile.PropertyStorage ps, byte[] d, int n) {
            this.propertyStorage = ps;
            this.data = d;
            this.number = n;
        }
    }
}

