package io;

import real.cmd.LoginHandler;
import server.TeamServer;

import java.net.Socket;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

public class LogInController implements ISessionHandler {
    private static final byte CHECK_LOGIN = 1;
    private Hashtable<Long, Session> htLoginSessionList;
    protected LinkedBlockingQueue<Message> m_msgSendQueue;
    protected LinkedBlockingQueue<Message> m_msgReceiveQueue;
    private static long SESSION_ID;
    private Session m_sLogInSession;
    public static LogInController Instance;

    static {
        LogInController.SESSION_ID = 1L;
        LogInController.Instance = new LogInController();
    }

    public LogInController() {
        this.htLoginSessionList = new Hashtable<>();
        this.m_msgSendQueue = new LinkedBlockingQueue<>();
        this.m_msgReceiveQueue = new LinkedBlockingQueue<>();
        this.m_sLogInSession = null;
    }

    private synchronized long GetSessionID() {
        return LogInController.SESSION_ID++;
    }

    public boolean OnMessageLogInComming(Session s, String sUserName, String sPassword) {
        Message m = new Message(1);
        long sSessionID = this.GetSessionID();
        s.lgData = new LogInData();
        s.lgData.username = sUserName;
        s.lgData.UserID = -9;
        try {
            m.dos.writeLong(sSessionID);
            m.dos.writeUTF(sUserName);
            m.dos.writeUTF(sPassword);
            m.dos.writeByte(9);
            if (this.m_msgSendQueue.size() > 50) 
            {
                System.out.println("LOG IN QUEUE FULLLLLLLLLLLLL !!!!!!!!!!!!!");
                LoginHandler.onLoginFail(s);
                return false;
            }
            this.m_msgSendQueue.add(m);
            this.htLoginSessionList.put(sSessionID, s);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void OnMessageLogInResponse(long sSessionID, int iUserID, boolean bIsVinaphone, byte bRegion) {
        Session s = this.htLoginSessionList.get(sSessionID);
        this.htLoginSessionList.remove(sSessionID);
        if (s != null) {
            s.lgData.UserID = iUserID;
            s.lgData.bVinaFone = bIsVinaphone;
            s.lgData.bRegion = bRegion;
            LoginHandler.onLoginOK(s);
            synchronized (s.LOCK) {
                try {
                    s.LOCK.notify();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // // monitorexit(s.LOCK);
            }
        }
    }

    public void run() {
        this.SendMessage();
        this.ReceiveMessage();
    }

    private void ReceiveMessage() {
        new Thread(() -> {
            while (true) {
                try {
                    while (true) {
                        Message m = LogInController.this.m_msgReceiveQueue.take();
                        long sessionId = m.dis.readLong();
                        int iUID = m.dis.readInt();
                        boolean bIsVinaPhone = m.dis.readBoolean();
                        byte bRegion = m.dis.readByte();
                        LogInController.this.OnMessageLogInResponse(sessionId, iUID, bIsVinaPhone, bRegion);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void SendMessage() {
        System.out.println("RUN LOG IN SEND MESSAGE .....");
        new Thread(() -> {
            while (true) {
                try {
                    while (true) {
                        Label_0094:
                        {
                            if (LogInController.this.m_sLogInSession != null) {
                                if (LogInController.this.m_sLogInSession.isConnected()) {
                                    break Label_0094;
                                }
                            }
                            try {
                                Socket s = new Socket(TeamServer.ipLogin, TeamServer.portLogin);
                                System.out.println("CONNECTED TO SERVER LOGIN");
                                LogInController.this.m_sLogInSession = new Session(s, LogInController.Instance, true);
                                LogInController.this.m_sLogInSession.start();
                            } catch (Exception e) {
                                System.err.println("CONNECT TO SERVER LOG IN FAIL !!!!!!!!");
                                Thread.sleep(1000L);
                                continue;
                            }
                        }
                        Message m = LogInController.this.m_msgSendQueue.take();
                        LogInController.this.m_sLogInSession.sendMessageLogin(m);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDisconnected(Session conn) {
        System.out.println(" LOG IN SERVER DISCONNECTED !!!!!!!!! ALARM ALARM ALARM ALARM ALARM !!!!!!!!!!!!!!!");
    }

    @Override
    public void processMessage(Session conn, Message message) {
        this.m_msgReceiveQueue.add(message);
    }

    @Override
    public void onDisconnectedFromLocalPlace(Session conn) {
    }
}
