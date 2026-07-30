package real;

import io.Message;
import io.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

final class KhaBanhVisualActor {

    private static final Hashtable<Short, Monster> OWNER_BY_VISUAL_ID = new Hashtable<Short, Monster>();
    private static final Hashtable<Short, KhaBanhVisualActor> VISUALS_BY_ID = new Hashtable<Short, KhaBanhVisualActor>();

    private final Monster owner;
    private final Char disguise;
    private final Set<Short> visibleViewers = new HashSet<Short>();
    private boolean removed;
    private int lastX = Integer.MIN_VALUE;
    private int lastY = Integer.MIN_VALUE;
    private int lastHp = Integer.MIN_VALUE;
    private int lastMaxHp = Integer.MIN_VALUE;

    KhaBanhVisualActor(Monster owner, String name, byte gender, byte charClass, byte headStyle, int ao, int quan, int non) {
        this.owner = owner;
        this.disguise = this.createDisguise(name, gender, charClass, headStyle, ao, quan, non);
        OWNER_BY_VISUAL_ID.put(Short.valueOf(this.disguise.id), owner);
        VISUALS_BY_ID.put(Short.valueOf(this.disguise.id), this);
        this.syncStateFromOwner();
    }

    public static Monster resolveOwnerMonster(short visualId) {
        return OWNER_BY_VISUAL_ID.get(Short.valueOf(visualId));
    }

    public static Char resolveVisualChar(short visualId) {
        KhaBanhVisualActor visual = VISUALS_BY_ID.get(Short.valueOf(visualId));
        if (visual == null || visual.removed) {
            return null;
        }
        visual.syncStateFromOwner();
        return visual.disguise;
    }

    public void sync(boolean force) {
        if (this.removed) {
            return;
        }
        if (this.owner == null || this.owner.map == null || this.owner.isDead) {
            this.remove();
            return;
        }
        this.syncStateFromOwner();
        boolean moved = force || this.disguise.x != this.lastX || this.disguise.y != this.lastY;
        boolean hpChanged = force || this.disguise.hp != this.lastHp || this.disguise.maxhp != this.lastMaxHp;
        ArrayList<Char> viewers = this.collectViewers();
        HashSet<Short> currentViewerIds = new HashSet<Short>();
        for (int i = 0; i < viewers.size(); i++) {
            Char viewer = viewers.get(i);
            if (viewer == null) {
                continue;
            }
            currentViewerIds.add(Short.valueOf(viewer.id));
            boolean firstSeen = this.visibleViewers.add(Short.valueOf(viewer.id));
            if (firstSeen) {
                this.sendSpawn(viewer);
                continue;
            }
            if (moved) {
                this.sendActorPos(viewer);
            }
            if (hpChanged) {
                this.sendHpUpdate(viewer);
            }
        }
        ArrayList<Short> staleViewers = new ArrayList<Short>(this.visibleViewers);
        for (int i = 0; i < staleViewers.size(); i++) {
            Short viewerId = staleViewers.get(i);
            if (viewerId == null || currentViewerIds.contains(viewerId)) {
                continue;
            }
            this.visibleViewers.remove(viewerId);
            this.sendRemove(viewerId.shortValue());
        }
        this.lastX = this.disguise.x;
        this.lastY = this.disguise.y;
        this.lastHp = this.disguise.hp;
        this.lastMaxHp = this.disguise.maxhp;
    }

    public void say(String line) {
        if (this.removed || line == null || line.length() == 0) {
            return;
        }
        this.sync(false);
        ArrayList<Char> viewers = this.collectViewers();
        if (viewers.isEmpty()) {
            return;
        }
        Message msg = MessageCreator.createMsgChat(this.disguise.id, line);
        try {
            for (int i = 0; i < viewers.size(); i++) {
                viewers.get(i).sendMessage(msg);
            }
        } finally {
            msg.cleanup();
        }
    }

    public void remove() {
        if (this.removed) {
            return;
        }
        this.removed = true;
        OWNER_BY_VISUAL_ID.remove(Short.valueOf(this.disguise.id));
        VISUALS_BY_ID.remove(Short.valueOf(this.disguise.id));
        ArrayList<Short> viewers = new ArrayList<Short>(this.visibleViewers);
        this.visibleViewers.clear();
        for (int i = 0; i < viewers.size(); i++) {
            Short viewerId = viewers.get(i);
            if (viewerId != null) {
                this.sendRemove(viewerId.shortValue());
            }
        }
        if (this.disguise.id > 0) {
            RealController.intance.idGen.putID(this.disguise.id, 0, "remove kha banh visual");
        }
    }

    private Char createDisguise(String name, byte gender, byte charClass, byte headStyle, int ao, int quan, int non) {
        Char fake = new Char((Session) null);
        short visualId = RealController.intance.idGen.getID(0, "kha banh visual");
        if (visualId == -1) {
            visualId = RealController.intance.idGen.getID(0, "kha banh visual retry");
        }
        if (visualId == -1) {
            visualId = (short) (-20000 - Map.r.nextInt(1000));
        }
        int userId = -500000 - Math.abs(visualId);
        fake.setInfoChar(name, -1, gender, charClass, this.owner.map, this.owner.x, this.owner.y, userId, ao, quan, non);
        fake.id = visualId;
        fake.userID = userId;
        fake.charDBID = userId;
        fake.headStyle = headStyle;
        fake.idBot = 0;
        fake.map = this.owner.map;
        fake.mapID = this.owner.map != null ? this.owner.map.mapId : -1;
        fake.inCountry = (byte) this.owner.inCountry;
        fake.myCountry = (byte) this.owner.inCountry;
        fake.region = this.owner.region;
        fake.lastLV = (short) Math.max(1, this.owner.level);
        fake.lvDetail.setExpNew(LevelDetail.getXpFromLevel(Math.max(1, this.owner.level)));
        return fake;
    }

    private void syncStateFromOwner() {
        if (this.owner == null) {
            return;
        }
        this.disguise.map = this.owner.map;
        this.disguise.mapID = this.owner.map != null ? this.owner.map.mapId : -1;
        this.disguise.region = this.owner.region;
        this.disguise.inCountry = (byte) this.owner.inCountry;
        this.disguise.myCountry = (byte) this.owner.inCountry;
        this.disguise.x = this.owner.x;
        this.disguise.y = this.owner.y;
        this.disguise.lastLV = (short) Math.max(1, this.owner.level);
        this.disguise.lvDetail.setExpNew(LevelDetail.getXpFromLevel(Math.max(1, this.owner.level)));
        this.disguise.attack = this.owner.attack;
        this.disguise.defend_physic = this.owner.defend_physic;
        this.disguise.defend_magic = this.owner.defend_magic;
        this.disguise.accurate = this.owner.accurate;
        this.disguise.dodge = this.owner.dodge;
        this.disguise.critical = this.owner.critical;
        this.disguise.baokich = this.owner.baokich;
        this.disguise.maxhp = Math.max(1, this.owner.maxhp);
        this.disguise.hp = Math.max(0, Math.min(this.owner.hp, this.disguise.maxhp));
        this.disguise.maxmp = 1;
        this.disguise.mp = 1;
    }

    private ArrayList<Char> collectViewers() {
        ArrayList<Char> viewers = new ArrayList<Char>();
        if (this.owner == null || this.owner.map == null) {
            return viewers;
        }
        for (int country = 0; country < 3; country++) {
            Vector<Char> players;
            try {
                players = this.owner.map.getAllPlayer(country, this.owner.region);
            } catch (Exception ex) {
                continue;
            }
            if (players == null) {
                continue;
            }
            Char[] snapshot = players.toArray(new Char[0]);
            for (int i = 0; i < snapshot.length; i++) {
                Char viewer = snapshot[i];
                if (viewer == null || viewer.exit || viewer.isBot != -1 || viewer.map != this.owner.map) {
                    continue;
                }
                if (viewer.region != this.owner.region) {
                    continue;
                }
                if (!this.owner.map.isPublicMap() && viewer.inCountry != this.owner.inCountry) {
                    continue;
                }
                if (!viewer.near(this.owner, viewer.rangeAddMonster[0])) {
                    continue;
                }
                viewers.add(viewer);
            }
        }
        return viewers;
    }

    private void sendSpawn(Char viewer) {
        this.sendActorPos(viewer);
        AmbientBotManager.sendAmbientSnapshot(viewer, this.disguise);
    }

    private void sendActorPos(Char viewer) {
        if (viewer == null) {
            return;
        }
        Message pos = new Message(4);
        try {
            viewer.writeActorPos(pos, this.disguise);
            viewer.sendMessage(pos);
        } finally {
            pos.cleanup();
        }
    }

    private void sendHpUpdate(Char viewer) {
        if (viewer == null) {
            return;
        }
        Message hp = null;
        try {
            hp = MessageCreator.createNew_HMP_Message(this.disguise, 0);
            viewer.sendMessage(hp);
        } catch (IOException ignored) {
        } finally {
            if (hp != null) {
                hp.cleanup();
            }
        }
    }

    private void sendRemove(short viewerId) {
        Char viewer = CharManager.instance.getByCharID(viewerId);
        if (viewer == null || viewer.exit) {
            return;
        }
        Message out = new Message(8);
        try {
            out.dos.writeShort(this.disguise.id);
            viewer.sendMessage(out);
        } catch (IOException ignored) {
        } finally {
            out.cleanup();
        }
    }
}
