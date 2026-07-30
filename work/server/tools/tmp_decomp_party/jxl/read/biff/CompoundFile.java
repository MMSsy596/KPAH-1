/*
 * Decompiled with CFR 0.152.
 */
package jxl.read.biff;

import common.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.WorkbookSettings;
import jxl.biff.BaseCompoundFile;
import jxl.biff.IntegerHelper;
import jxl.read.biff.BiffException;

public final class CompoundFile
extends BaseCompoundFile {
    private static Logger logger = Logger.getLogger(class$jxl$read$biff$CompoundFile == null ? (class$jxl$read$biff$CompoundFile = CompoundFile.class$("jxl.read.biff.CompoundFile")) : class$jxl$read$biff$CompoundFile);
    private byte[] data;
    private int numBigBlockDepotBlocks;
    private int sbdStartBlock;
    private int rootStartBlock;
    private int extensionBlock;
    private int numExtensionBlocks;
    private byte[] rootEntry;
    private int[] bigBlockChain;
    private int[] smallBlockChain;
    private int[] bigBlockDepotBlocks;
    private ArrayList propertySets;
    private WorkbookSettings settings;
    private BaseCompoundFile.PropertyStorage rootEntryPropertyStorage;
    static /* synthetic */ Class class$jxl$read$biff$CompoundFile;

    public CompoundFile(byte[] d, WorkbookSettings ws) throws BiffException {
        this.data = d;
        this.settings = ws;
        for (int i = 0; i < IDENTIFIER.length; ++i) {
            if (this.data[i] == IDENTIFIER[i]) continue;
            throw new BiffException(BiffException.unrecognizedOLEFile);
        }
        this.propertySets = new ArrayList();
        this.numBigBlockDepotBlocks = IntegerHelper.getInt(this.data[44], this.data[45], this.data[46], this.data[47]);
        this.sbdStartBlock = IntegerHelper.getInt(this.data[60], this.data[61], this.data[62], this.data[63]);
        this.rootStartBlock = IntegerHelper.getInt(this.data[48], this.data[49], this.data[50], this.data[51]);
        this.extensionBlock = IntegerHelper.getInt(this.data[68], this.data[69], this.data[70], this.data[71]);
        this.numExtensionBlocks = IntegerHelper.getInt(this.data[72], this.data[73], this.data[74], this.data[75]);
        this.bigBlockDepotBlocks = new int[this.numBigBlockDepotBlocks];
        int pos = 76;
        int bbdBlocks = this.numBigBlockDepotBlocks;
        if (this.numExtensionBlocks != 0) {
            bbdBlocks = 109;
        }
        for (int i = 0; i < bbdBlocks; ++i) {
            this.bigBlockDepotBlocks[i] = IntegerHelper.getInt(d[pos], d[pos + 1], d[pos + 2], d[pos + 3]);
            pos += 4;
        }
        for (int j = 0; j < this.numExtensionBlocks; ++j) {
            pos = (this.extensionBlock + 1) * 512;
            int blocksToRead = Math.min(this.numBigBlockDepotBlocks - bbdBlocks, 127);
            for (int i = bbdBlocks; i < bbdBlocks + blocksToRead; ++i) {
                this.bigBlockDepotBlocks[i] = IntegerHelper.getInt(d[pos], d[pos + 1], d[pos + 2], d[pos + 3]);
                pos += 4;
            }
            if ((bbdBlocks += blocksToRead) >= this.numBigBlockDepotBlocks) continue;
            this.extensionBlock = IntegerHelper.getInt(d[pos], d[pos + 1], d[pos + 2], d[pos + 3]);
        }
        this.readBigBlockDepot();
        this.readSmallBlockDepot();
        this.rootEntry = this.readData(this.rootStartBlock);
        this.readPropertySets();
    }

    private void readBigBlockDepot() {
        int pos = 0;
        int index = 0;
        this.bigBlockChain = new int[this.numBigBlockDepotBlocks * 512 / 4];
        for (int i = 0; i < this.numBigBlockDepotBlocks; ++i) {
            pos = (this.bigBlockDepotBlocks[i] + 1) * 512;
            for (int j = 0; j < 128; ++j) {
                this.bigBlockChain[index] = IntegerHelper.getInt(this.data[pos], this.data[pos + 1], this.data[pos + 2], this.data[pos + 3]);
                pos += 4;
                ++index;
            }
        }
    }

    private void readSmallBlockDepot() {
        int pos = 0;
        int index = 0;
        int sbdBlock = this.sbdStartBlock;
        this.smallBlockChain = new int[0];
        if (sbdBlock == -1) {
            logger.warn("invalid small block depot number");
            return;
        }
        while (sbdBlock != -2) {
            int[] oldChain = this.smallBlockChain;
            this.smallBlockChain = new int[this.smallBlockChain.length + 128];
            System.arraycopy(oldChain, 0, this.smallBlockChain, 0, oldChain.length);
            pos = (sbdBlock + 1) * 512;
            for (int j = 0; j < 128; ++j) {
                this.smallBlockChain[index] = IntegerHelper.getInt(this.data[pos], this.data[pos + 1], this.data[pos + 2], this.data[pos + 3]);
                pos += 4;
                ++index;
            }
            sbdBlock = this.bigBlockChain[sbdBlock];
        }
    }

    private void readPropertySets() {
        byte[] d = null;
        for (int offset = 0; offset < this.rootEntry.length; offset += 128) {
            d = new byte[128];
            System.arraycopy(this.rootEntry, offset, d, 0, d.length);
            BaseCompoundFile.PropertyStorage ps = (BaseCompoundFile)this.new BaseCompoundFile.PropertyStorage(d);
            if (ps.name == null || ps.name.length() == 0) {
                if (ps.type == 5) {
                    ps.name = "Root Entry";
                    logger.warn("Property storage name for " + ps.type + " is empty - setting to " + "Root Entry");
                } else if (ps.size != 0) {
                    logger.warn("Property storage type " + ps.type + " is non-empty and has no associated name");
                }
            }
            this.propertySets.add(ps);
            if (!ps.name.equalsIgnoreCase("Root Entry")) continue;
            this.rootEntryPropertyStorage = ps;
        }
        if (this.rootEntryPropertyStorage == null) {
            this.rootEntryPropertyStorage = (BaseCompoundFile.PropertyStorage)this.propertySets.get(0);
        }
    }

    public byte[] getStream(String streamName) throws BiffException {
        BaseCompoundFile.PropertyStorage ps = this.findPropertyStorage(streamName, this.rootEntryPropertyStorage);
        if (ps == null) {
            ps = this.getPropertyStorage(streamName);
        }
        if (ps.size >= 4096 || streamName.equalsIgnoreCase("Root Entry")) {
            return this.getBigBlockStream(ps);
        }
        return this.getSmallBlockStream(ps);
    }

    public byte[] getStream(int psIndex) throws BiffException {
        BaseCompoundFile.PropertyStorage ps = this.getPropertyStorage(psIndex);
        if (ps.size >= 4096 || ps.name.equalsIgnoreCase("Root Entry")) {
            return this.getBigBlockStream(ps);
        }
        return this.getSmallBlockStream(ps);
    }

    public BaseCompoundFile.PropertyStorage findPropertyStorage(String name) {
        return this.findPropertyStorage(name, this.rootEntryPropertyStorage);
    }

    private BaseCompoundFile.PropertyStorage findPropertyStorage(String name, BaseCompoundFile.PropertyStorage base) {
        if (base.child == -1) {
            return null;
        }
        BaseCompoundFile.PropertyStorage child = this.getPropertyStorage(base.child);
        if (child.name.equalsIgnoreCase(name)) {
            return child;
        }
        BaseCompoundFile.PropertyStorage prev = child;
        while (prev.previous != -1) {
            prev = this.getPropertyStorage(prev.previous);
            if (!prev.name.equalsIgnoreCase(name)) continue;
            return prev;
        }
        BaseCompoundFile.PropertyStorage next = child;
        while (next.next != -1) {
            next = this.getPropertyStorage(next.next);
            if (!next.name.equalsIgnoreCase(name)) continue;
            return next;
        }
        return this.findPropertyStorage(name, child);
    }

    private BaseCompoundFile.PropertyStorage getPropertyStorage(String name) throws BiffException {
        Iterator i = this.propertySets.iterator();
        boolean found = false;
        boolean multiple = false;
        BaseCompoundFile.PropertyStorage ps = null;
        while (i.hasNext()) {
            BaseCompoundFile.PropertyStorage ps2 = (BaseCompoundFile.PropertyStorage)i.next();
            if (!ps2.name.equalsIgnoreCase(name)) continue;
            multiple = found;
            found = true;
            ps = ps2;
        }
        if (multiple) {
            logger.warn("found multiple copies of property set " + name);
        }
        if (!found) {
            throw new BiffException(BiffException.streamNotFound);
        }
        return ps;
    }

    private BaseCompoundFile.PropertyStorage getPropertyStorage(int index) {
        return (BaseCompoundFile.PropertyStorage)this.propertySets.get(index);
    }

    private byte[] getBigBlockStream(BaseCompoundFile.PropertyStorage ps) {
        int count;
        int numBlocks = ps.size / 512;
        if (ps.size % 512 != 0) {
            ++numBlocks;
        }
        byte[] streamData = new byte[numBlocks * 512];
        int block = ps.startBlock;
        int pos = 0;
        for (count = 0; block != -2 && count < numBlocks; ++count) {
            pos = (block + 1) * 512;
            System.arraycopy(this.data, pos, streamData, count * 512, 512);
            block = this.bigBlockChain[block];
        }
        if (block != -2 && count == numBlocks) {
            logger.warn("Property storage size inconsistent with block chain.");
        }
        return streamData;
    }

    private byte[] getSmallBlockStream(BaseCompoundFile.PropertyStorage ps) throws BiffException {
        byte[] rootdata = this.readData(this.rootEntryPropertyStorage.startBlock);
        byte[] sbdata = new byte[]{};
        int block = ps.startBlock;
        int pos = 0;
        while (block != -2) {
            byte[] olddata = sbdata;
            sbdata = new byte[olddata.length + 64];
            System.arraycopy(olddata, 0, sbdata, 0, olddata.length);
            pos = block * 64;
            System.arraycopy(rootdata, pos, sbdata, olddata.length, 64);
            if ((block = this.smallBlockChain[block]) != -1) continue;
            logger.warn("Incorrect terminator for small block stream " + ps.name);
            block = -2;
        }
        return sbdata;
    }

    private byte[] readData(int bl) throws BiffException {
        int block = bl;
        int pos = 0;
        byte[] entry = new byte[]{};
        while (block != -2) {
            byte[] oldEntry = entry;
            entry = new byte[oldEntry.length + 512];
            System.arraycopy(oldEntry, 0, entry, 0, oldEntry.length);
            pos = (block + 1) * 512;
            System.arraycopy(this.data, pos, entry, oldEntry.length, 512);
            if (this.bigBlockChain[block] == block) {
                throw new BiffException(BiffException.corruptFileFormat);
            }
            block = this.bigBlockChain[block];
        }
        return entry;
    }

    public int getNumberOfPropertySets() {
        return this.propertySets.size();
    }

    public BaseCompoundFile.PropertyStorage getPropertySet(int index) {
        return this.getPropertyStorage(index);
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }
}

