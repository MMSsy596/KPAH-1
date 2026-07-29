/*
 * Decompiled with CFR 0.152.
 */
package jxl.biff;

import common.Assert;
import common.Logger;
import jxl.biff.IntegerHelper;

public abstract class BaseCompoundFile {
    private static Logger logger = Logger.getLogger(class$jxl$biff$BaseCompoundFile == null ? (class$jxl$biff$BaseCompoundFile = BaseCompoundFile.class$("jxl.biff.BaseCompoundFile")) : class$jxl$biff$BaseCompoundFile);
    protected static final byte[] IDENTIFIER = new byte[]{-48, -49, 17, -32, -95, -79, 26, -31};
    protected static final int NUM_BIG_BLOCK_DEPOT_BLOCKS_POS = 44;
    protected static final int SMALL_BLOCK_DEPOT_BLOCK_POS = 60;
    protected static final int NUM_SMALL_BLOCK_DEPOT_BLOCKS_POS = 64;
    protected static final int ROOT_START_BLOCK_POS = 48;
    protected static final int BIG_BLOCK_SIZE = 512;
    protected static final int SMALL_BLOCK_SIZE = 64;
    protected static final int EXTENSION_BLOCK_POS = 68;
    protected static final int NUM_EXTENSION_BLOCK_POS = 72;
    protected static final int PROPERTY_STORAGE_BLOCK_SIZE = 128;
    protected static final int BIG_BLOCK_DEPOT_BLOCKS_POS = 76;
    protected static final int SMALL_BLOCK_THRESHOLD = 4096;
    private static final int SIZE_OF_NAME_POS = 64;
    private static final int TYPE_POS = 66;
    private static final int COLOUR_POS = 67;
    private static final int PREVIOUS_POS = 68;
    private static final int NEXT_POS = 72;
    private static final int CHILD_POS = 76;
    private static final int START_BLOCK_POS = 116;
    private static final int SIZE_POS = 120;
    public static final String ROOT_ENTRY_NAME = "Root Entry";
    public static final String WORKBOOK_NAME = "Workbook";
    public static final String SUMMARY_INFORMATION_NAME = "\u0005SummaryInformation";
    public static final String DOCUMENT_SUMMARY_INFORMATION_NAME = "\u0005DocumentSummaryInformation";
    public static final String COMP_OBJ_NAME = "\u0001CompObj";
    public static final String[] STANDARD_PROPERTY_SETS = new String[]{"Root Entry", "Workbook", "\u0005SummaryInformation", "\u0005DocumentSummaryInformation"};
    public static final int NONE_PS_TYPE = 0;
    public static final int DIRECTORY_PS_TYPE = 1;
    public static final int FILE_PS_TYPE = 2;
    public static final int ROOT_ENTRY_PS_TYPE = 5;
    static /* synthetic */ Class class$jxl$biff$BaseCompoundFile;

    protected BaseCompoundFile() {
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        }
        catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public class PropertyStorage {
        public String name;
        public int type;
        public int colour;
        public int startBlock;
        public int size;
        public int previous;
        public int next;
        public int child;
        public byte[] data;

        public PropertyStorage(byte[] d) {
            this.data = d;
            int nameSize = IntegerHelper.getInt(this.data[64], this.data[65]);
            if (nameSize > 64) {
                logger.warn("property set name exceeds max length - truncating");
                nameSize = 64;
            }
            this.type = this.data[66];
            this.colour = this.data[67];
            this.startBlock = IntegerHelper.getInt(this.data[116], this.data[117], this.data[118], this.data[119]);
            this.size = IntegerHelper.getInt(this.data[120], this.data[121], this.data[122], this.data[123]);
            this.previous = IntegerHelper.getInt(this.data[68], this.data[69], this.data[70], this.data[71]);
            this.next = IntegerHelper.getInt(this.data[72], this.data[73], this.data[74], this.data[75]);
            this.child = IntegerHelper.getInt(this.data[76], this.data[77], this.data[78], this.data[79]);
            int chars = 0;
            if (nameSize > 2) {
                chars = (nameSize - 1) / 2;
            }
            StringBuffer n = new StringBuffer("");
            for (int i = 0; i < chars; ++i) {
                n.append((char)this.data[i * 2]);
            }
            this.name = n.toString();
        }

        public PropertyStorage(String name) {
            this.data = new byte[128];
            Assert.verify(name.length() < 32);
            IntegerHelper.getTwoBytes((name.length() + 1) * 2, this.data, 64);
            for (int i = 0; i < name.length(); ++i) {
                this.data[i * 2] = (byte)name.charAt(i);
            }
        }

        public void setType(int t) {
            this.type = t;
            this.data[66] = (byte)t;
        }

        public void setStartBlock(int sb) {
            this.startBlock = sb;
            IntegerHelper.getFourBytes(sb, this.data, 116);
        }

        public void setSize(int s) {
            this.size = s;
            IntegerHelper.getFourBytes(s, this.data, 120);
        }

        public void setPrevious(int prev) {
            this.previous = prev;
            IntegerHelper.getFourBytes(prev, this.data, 68);
        }

        public void setNext(int nxt) {
            this.next = nxt;
            IntegerHelper.getFourBytes(this.next, this.data, 72);
        }

        public void setChild(int dir) {
            this.child = dir;
            IntegerHelper.getFourBytes(this.child, this.data, 76);
        }

        public void setColour(int col) {
            this.colour = col == 0 ? 0 : 1;
            this.data[67] = (byte)this.colour;
        }
    }
}

