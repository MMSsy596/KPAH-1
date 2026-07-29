/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author TOM
 */
public class Message {

    public int command;
    private ByteArrayOutputStream os = null;
    public DataOutputStream dos = null;
    private ByteArrayInputStream is = null;
    public DataInputStream dis = null;
    private byte[] bytes = null;

    public Message(int command) {
        this.command = command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    /**
     * cái này tạo message để đọc
     * @param command
     * @param data 
     */
    public Message(int command, byte[] data) {
        this.command = command;
        this.is = new ByteArrayInputStream(data);
        this.dis = new DataInputStream(this.is);
    }

    public byte[] toByteArray() {
        int datalen = 0;
        byte[] data = (byte[]) null;
        if (this.bytes == null) {
            ByteArrayOutputStream bos1 = null;
            DataOutputStream dos1 = null;

            try {
                if (this.dos != null) {
                    this.dos.flush();
                    data = this.os.toByteArray();
                    datalen = data.length;
                    this.dos.close();
                }

                bos1 = new ByteArrayOutputStream(3 + datalen);
                dos1 = new DataOutputStream(bos1);
                if (datalen > 32767) {
                    dos1.writeByte(-128);
                }

                dos1.writeByte(this.command);
                if (datalen > 32767) {
                    dos1.writeInt(datalen);
                } else {
                    dos1.writeShort(datalen);
                }

                if (datalen > 0) {
                    dos1.write(data);
                }

                this.bytes = bos1.toByteArray();
            } catch (IOException var18) {
                var18.printStackTrace();
                System.out.println("LOI KHI TAO BYTE");
            } finally {
                try {
                    dos1.close();
                } catch (Exception var17) {
                }

                try {
                    bos1.close();
                } catch (Exception var16) {
                }

            }

            this.cleanup();
        }

        return this.bytes;
    }

    public void cleanup() {
    }

    public void cleanALlData() {
        try {
            this.dis.close();
        } catch (Exception var3) {
        }

        try {
            this.dos.close();
        } catch (Exception var2) {
        }

    }
}
