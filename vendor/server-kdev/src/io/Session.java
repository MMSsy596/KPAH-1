package io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import real.Char;

public class Session extends Thread {
    private static final long serialVersionUID = 1L;
    private volatile boolean connected = false;
    protected Socket socket;
    public int userID = -1;
    public int serviceID = -1;
    public String ip = "";
    public String bigProvider = "0";
    public String privateProvider = "0";
    public String clientProvider = "0";
    public String agentProvider = "0";
    public String username = "";
    public String version = "0";
    public String charname = "";
    public int versionCode = 0;
    public byte firmWare = 0;
    public byte vsTile = 0;
    public byte firmWareReg = 0;
    public boolean isLogin = false;
    public boolean available = false;
    public short w = 320;
    public short h = 320;
    public String IMEI = "";
    public String syntax = "";
    public String centerReg = "";
    public String unameReg = "";
    public String usernameReg = "";
    public long timeNapMoney = 0L;
    public byte idServer = 0;
    ISessionHandler messageHandler;
    private Sender sender;
    public Object LOCK = new Object();
    public long connectTime = System.currentTimeMillis();
    private byte[] key;
    public long lastActiveTime;
    public long lastTimeSendData;
    public int idPrivateParty = -1;
    public byte isOldVersion = 0;
    public boolean exit = false;
    public LogInData lgData;
    private int curR;
    private int curW;
    int loginday = 0;
    public short messCount = 0;
    public long lastCheck = System.currentTimeMillis();
    int llll = 0;
    public boolean isCapNhatThongTin = false;
    public int timeXp = 0;
    public String ngaysinh = "18 11 1984";
    public String noicap;
    public String ngaycap;
    public String hoten;
    public String diachi;
    public String cmnd;
    public static byte[][] groupCmd = new byte[0][];
    public static Vector<Hashtable<Byte, Byte>> hasGroup = new Vector();

    static {
        for(int i = 0; i < groupCmd.length; ++i) {
            Hashtable<Byte, Byte> has = new Hashtable();

            for(int j = 0; j < groupCmd[i].length; ++j) {
                try {
                    has.put(groupCmd[i][j], groupCmd[i][j]);
                } catch (Exception var4) {
                }
            }

            hasGroup.add(has);
        }


    }

    public Session(Socket socket, ISessionHandler handler) {
        try {
            socket.setTcpNoDelay(true);
            this.setSocket(socket);
            this.messageHandler = handler;
            this.connected = true;
            SessionManager.instance.add(this);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }

    public Session(Socket s, LogInController instance, boolean b) {
        try {
            this.setSocket(s);
            this.messageHandler = instance;
            this.connected = true;
            this.loginday = 1;
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }

    private byte readKey(byte b) {
        byte i = (byte)(this.key[this.curR] & 255 ^ b & 255);
        this.curR = (this.curR + 1) % this.key.length;
        return i;
    }

    private byte writeKey(byte b) {
        byte i = (byte)(this.key[this.curW] & 255 ^ b & 255);
        this.curW = (this.curW + 1) % this.key.length;
        return i;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public String getIP() {
        return this.socket.getRemoteSocketAddress().toString();
    }

    public void setMessageHandler(ISessionHandler handler) {
        this.messageHandler = handler;
    }

    public Sender getSender() {
        return this.sender;
    }

    protected void setSocket(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
        this.sender = new Sender();

        try {
            DataInputStream dis = null;

            try {
                if (this.socket != null) {
                    dis = new DataInputStream(this.socket.getInputStream());
                }

                Thread.currentThread().setName("READ ");
            } catch (Exception var9) {
            }

            while(this.connected) {
                Message message = this.readMessage(dis);
                this.lastActiveTime = System.currentTimeMillis();

                try {
                    this.messageHandler.processMessage(this, message);
                    if (message.command != -47 && message.command != -48 && message.command != -51 && message.command != -49) {
                        ++this.messCount;
                        if (this.messCount > 300) // tăng lên nếu muốn. (số tin tối đa trong 5s)
                        {
                            if (System.currentTimeMillis() - this.lastCheck < 5000L) 
                            {
                                System.out.println(this.username + "_" + this.clientProvider + "_" + this.agentProvider + " =========>SEND TOO MUCH " + this.messCount + "============= LAST CMD=" + message.command);
                                
                                this.disconnect(7); /// bỏ nết mkn nhiều
                                System.out.println(this.username + "_disconnect >>>>>>>>>>>");
                                break;
                            }

                            this.lastCheck = System.currentTimeMillis();
                            this.messCount = 0;
                        }
                    }
                } catch (Exception var10) {
                }
            }
        } catch (Exception var11) {
        } finally {
            this.disconnect(7);
        }

    }

    private Message readMessage(DataInputStream dis) throws Exception {
        Message msg = null;
        int cmd = dis.readByte();
        if (this.key != null) {
            cmd = this.readKey((byte)cmd);
        }

        short size = 0;
        if (this.key != null) {
            byte a1 = dis.readByte();
            a1 = this.readKey(a1);
            byte a2 = dis.readByte();
            a2 = this.readKey(a2);
            size = (short)((a1 & 255) << 8 | a2 & 255);
            if (size > 10000) {
                throw new IOException("Data too big");
            }
        } else {
            size = (short)dis.readUnsignedShort();
            if (size > 10000) {
                throw new IOException("Data too big");
            }
        }

        if (size >= 1024) {
            return null;
        } else {
            byte[] data = new byte[size];
            int byteRead = 0;

            while(byteRead != -1 && byteRead < size) {
                int len = dis.read(data, byteRead, size - byteRead);
                if (len > 0) {
                    byteRead += len;
                }

                if (len == -1) {
                    return null;
                }
            }

            if (this.key != null) {
                for(int i = 0; i < data.length; ++i) {
                    data[i] = this.readKey(data[i]);
                }
            }

            msg = new Message(cmd, data);
            return msg;
        }
    }

    public void sendMessageLogin(Message m) {
        this.sender.sendMessage(m, 8);
    }

    public void sendMessage(Message m) {
        try {
            if (m != null) {
                for(int i = 0; i < hasGroup.size(); ++i) {
                    if (((Hashtable)hasGroup.get(i)).get(m.command) != null) {
                        this.sender.sendMessage(m, i);
                        return;
                    }
                }

                this.sender.sendMessage(m, 8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isConnected() {
        return this.connected;
    }

    public void notifySender() {
        try {
            synchronized(this.LOCK) {
                this.LOCK.notify();
            }
        } catch (Exception var3) {
        }

    }

    public void disconnect() {
        if (this.connected) {
            this.connected = false;
        }

    }

    public void disconnect(int cause) {
        this.exit = true;
        this.notifySender();
        if (this.connected) {
            try {
                this.socket.close();
            } catch (Exception var7) {
            }

            this.connected = false;

            try {
                SessionManager.instance.remove(this);
            } catch (Exception var6) {
            }

            try {
                if (this.userID != -1) {
                    this.messageHandler.onDisconnected(this);
                    this.userID = -1;
                }
            } catch (Exception var5) {
            }
        }

        this.connected = false;
        this.userID = -1;

        try {
            synchronized(this.LOCK) {
                this.LOCK.notify();
            }
        } catch (Exception var4) {
        }

    }

    public void destroyAllMessage() {
        this.sender.destroyAll();
    }

    public boolean is12Tuoi() {
        try {
            Calendar cl = Calendar.getInstance();
            int year = cl.get(1);
            String[] data = Char.split(this.ngaysinh, " ");
            Integer.parseInt(data[0]);
            Integer.parseInt(data[1]);
            if (Integer.parseInt(data[2].trim()) > year - 12) {
                return false;
            }
        } catch (Exception var4) {
        }

        return true;
    }

    public boolean is18Tuoi() {
        try {
            Calendar cl = Calendar.getInstance();
            int year = cl.get(1);
            String[] data = Char.split(this.ngaysinh, " ");
            Integer.parseInt(data[0]);
            Integer.parseInt(data[1]);
            if (Integer.parseInt(data[2].trim()) > year - 18) {
                return false;
            }
        } catch (Exception var4) {
        }

        return true;
    }

    public boolean isVersion180() {
        return this.version.compareTo("1.8.0") > -1;
    }

    private class Sender implements Runnable {
        private LinkedBlockingQueue<byte[]> sendDatas = new LinkedBlockingQueue();

        public Sender() {
            Session.this.lastTimeSendData = System.currentTimeMillis();
            (new Thread(this)).start();
        }

        public void destroyAll() {
            this.sendDatas.clear();
        }

        public void sendMessageLogin(Message m) {
            try {
                byte[] data = m.toByteArray();
                if (Session.this.socket != null) {
                    OutputStream os = Session.this.socket.getOutputStream();
                    os.write(data);
                    os.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void sendMessage(Message m, int index) {
            if (Session.this.connected) {
                try {
                    byte[] data = m.toByteArray();
                    if (data != null) {
                        this.sendDatas.add(data);
                    }
                } catch (Exception var6) {
                    System.out.println("LOI KHI GUI DATA");
                }

                Session.this.lastTimeSendData = System.currentTimeMillis();

                try {
                    synchronized(Session.this.LOCK) {
                        Session.this.LOCK.notify();
                    }
                } catch (Exception var5) {
                }
            }

        }

        public byte[] int2Bytes(int number) {
            byte[] res = new byte[4];

            for(int i = 3; i >= 0; --i) {
                res[i] = (byte)number;
                number >>= 8;
            }

            return res;
        }

        private boolean emtyMsg() {
            return this.sendDatas.isEmpty();
        }

        public void run() {
            try {
                OutputStream os = null;

                try {
                    if (Session.this.socket != null) {
                        os = Session.this.socket.getOutputStream();
                    }

                    Thread.currentThread().setName("SEND ");
                } catch (Exception var27) {
                }

                long timetest = System.currentTimeMillis();

                while(Session.this.connected) {
                    int i = 0;
                    long l = System.currentTimeMillis();

                    try {
                        try {
                            byte[] bytes = (byte[])this.sendDatas.poll(1000L, TimeUnit.MILLISECONDS);
                            if (Session.this.key != null && bytes[0] != -40) {
                                byte[] sdata = new byte[bytes.length];

                                for(int j = 0; j < bytes.length; ++j) {
                                    sdata[j] = Session.this.writeKey(bytes[j]);
                                }

                                os.write(sdata);
                            } else {
                                os.write(bytes);
                            }
                        } catch (Exception var28) {
                        }

                        os.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (os == null) {
                            break;
                        }
                    }

                    if (Session.this.exit) {
                        try {
                            synchronized(Session.this.LOCK) {
                                Session.this.LOCK.notify();
                            }
                        } catch (Exception var26) {
                        }
                        break;
                    }

                    if (os == null) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Session.this.LOCK.notify();
                } catch (Exception var23) {
                }

            }

            Session.this.exit = true;

            try {
                this.sendDatas.removeAll(this.sendDatas);
            } catch (Exception var24) {
            }

        }
    }
}
