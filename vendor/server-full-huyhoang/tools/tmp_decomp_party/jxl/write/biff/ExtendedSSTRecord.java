/*
 * Decompiled with CFR 0.152.
 */
package jxl.write.biff;

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

class ExtendedSSTRecord
extends WritableRecordData {
    private static final int infoRecordSize = 8;
    private int numberOfStrings;
    private int[] absoluteStreamPositions;
    private int[] relativeStreamPositions;
    private int currentStringIndex = 0;

    public ExtendedSSTRecord(int newNumberOfStrings) {
        super(Type.EXTSST);
        this.numberOfStrings = newNumberOfStrings;
        int numberOfBuckets = this.getNumberOfBuckets();
        this.absoluteStreamPositions = new int[numberOfBuckets];
        this.relativeStreamPositions = new int[numberOfBuckets];
        this.currentStringIndex = 0;
    }

    public int getNumberOfBuckets() {
        int numberOfStringsPerBucket = this.getNumberOfStringsPerBucket();
        return numberOfStringsPerBucket != 0 ? (this.numberOfStrings + numberOfStringsPerBucket - 1) / numberOfStringsPerBucket : 0;
    }

    public int getNumberOfStringsPerBucket() {
        int bucketLimit = 128;
        return (this.numberOfStrings + 128 - 1) / 128;
    }

    public void addString(int absoluteStreamPosition, int relativeStreamPosition) {
        this.absoluteStreamPositions[this.currentStringIndex] = absoluteStreamPosition + relativeStreamPosition;
        this.relativeStreamPositions[this.currentStringIndex] = relativeStreamPosition;
        ++this.currentStringIndex;
    }

    public byte[] getData() {
        int numberOfBuckets = this.getNumberOfBuckets();
        byte[] data = new byte[2 + 8 * numberOfBuckets];
        IntegerHelper.getTwoBytes(this.getNumberOfStringsPerBucket(), data, 0);
        for (int i = 0; i < numberOfBuckets; ++i) {
            IntegerHelper.getFourBytes(this.absoluteStreamPositions[i], data, 2 + i * 8);
            IntegerHelper.getTwoBytes(this.relativeStreamPositions[i], data, 6 + i * 8);
        }
        return data;
    }
}

