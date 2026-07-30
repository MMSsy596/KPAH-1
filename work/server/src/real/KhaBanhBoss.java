package real;

import data.GemItem;
import io.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class KhaBanhBoss extends Boss {

    private static final int HP_MULTIPLIER = 16;
    private static final int HP_FLOOR = 10500000;
    private static final int DAMAGE_MIN_PERCENT = 14;
    private static final int DAMAGE_MAX_PERCENT = 16;
    private static final int DAU_SI_DAMAGE_PERCENT = 2;
    private static final int DAU_SI_FOCUS_RANGE = 220;
    private static final int BOSS_XU_REWARD = 50000;
    private static final int BOSS_LUONG_REWARD = 200;
    private static final int BOSS_LUONG_KHOA_REWARD = 100;
    private static final int MATERIAL_DROP_MIN = 3;
    private static final int MATERIAL_DROP_MAX = 7;
    private static final String NAME = "Kh\u00e1 B\u1ea3nh";
    private static final String[] CHAT_LINES = new String[]{
        "\u1ed0i b\u1ea1n \u01a1i!",
        "B\u1ea1n y\u1ebfu l\u00e0 do ch\u01b0a ch\u01a1i \u0111\u1ed3 \u0111\u1ea5y b\u1ea1n \u1ea1.",
        "Th\u1eb1ng n\u00e0o m\u1edbi b\u1ea3o t\u00f3c b\u1ea3nh nh\u01b0 l\u00f4ng loz?",
        "\u1ea2o th\u1eadt \u0111\u1ea5y!",
        "\u0110i \u0111\u01b0\u1eddng ph\u1ea3i ng\u1ea9ng cao \u0111\u1ea7u ch\u1ee9 b\u1ea1n.",
        "Anh \u0111\u00e2y l\u00e0 d\u00e2n ch\u01a1i th\u1ee9 thi\u1ec7t, \u0111\u1eebng nh\u00ecn m\u00e0 khinh.",
        "Nh\u00ecn qu\u1ea3 \u0111\u1ea7u n\u00e0y l\u00e0 bi\u1ebft \u0111\u1eb3ng c\u1ea5p r\u1ed3i."
    };
    private static final byte VISUAL_GENDER = 1;
    private static final byte VISUAL_CLASS = 0;
    private static final byte VISUAL_HEAD_STYLE = 11;
    private static final int VISUAL_AO = 277;
    private static final int VISUAL_QUAN = 301;
    private static final int VISUAL_NON = -1;

    private final ArrayList<Short> minionIds;
    private final KhaBanhVisualActor visual;
    private final int moveRadius;
    private long nextTalkAt;
    private boolean activated;

    public KhaBanhBoss(Map mapLiveIn, MonsterTemplate template, int x, int y, int country) {
        super(mapLiveIn, template, x, y, country);
        this.minionIds = new ArrayList<Short>();
        this.moveRadius = 160;
        this.nextTalkAt = System.currentTimeMillis() + 5000L;
        this.activated = false;
        this.isBoss = true;
        this.randomMap = false;
        this.attackDelay = 2400L;
        this.moveDelay = 700L;
        this.percentDam = 120;
        this.level = 80;
        this.maxhp = Math.max(this.maxhp * HP_MULTIPLIER, HP_FLOOR);
        this.hp = this.maxhp;
        this.attack = Math.max(this.attack * 3, 14000);
        this.defend_physic = Math.max(this.defend_physic * 4, 7600);
        this.defend_magic = Math.max(this.defend_physic, 7600);
        this.accurate = 2800;
        this.dodge = 320;
        this.dmove = 96;
        this.visual = new KhaBanhVisualActor(this, NAME, VISUAL_GENDER, VISUAL_CLASS, VISUAL_HEAD_STYLE, VISUAL_AO, VISUAL_QUAN, VISUAL_NON);
    }

    public void setMinionIds(ArrayList<KhaBanhMinion> minions) {
        this.minionIds.clear();
        for (int i = 0; i < minions.size(); i++) {
            this.minionIds.add(Short.valueOf(minions.get(i).id));
        }
        this.activated = KhaBanhEvent.getInstance().areAllMinionsDead(this.inCountry);
    }

    public void onMinionsCleared() {
        this.activated = true;
        this.target = null;
        this.sendBossChat("Anh em tao n\u1eb1m h\u1ebft r\u1ed3i, gi\u1edd th\u00ec v\u00e0o vi\u1ec7c th\u00f4i!");
    }

    public boolean canReceiveDamage() {
        return this.activated && KhaBanhEvent.getInstance().areAllMinionsDead(this.inCountry);
    }

    public boolean canBeDamagedBy(LiveActor attacker) {
        return this.canReceiveDamage() && KhaBanhEvent.getInstance().canDamageMonster(attacker, this.inCountry);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int attackDam(LiveActor actor) {
        if (actor instanceof Char && ((Char) actor).charClass == Char.DS) {
            return (int) Math.max(1L, ((long) actor.maxhp * DAU_SI_DAMAGE_PERCENT) / 100L);
        }
        int minDamage = (int) Math.max(900L, ((long) actor.maxhp * DAMAGE_MIN_PERCENT) / 100L);
        int maxDamage = (int) Math.max(minDamage, ((long) actor.maxhp * DAMAGE_MAX_PERCENT) / 100L);
        return minDamage + Map.r.nextInt(Math.max(1, maxDamage - minDamage + 1));
    }

    @Override
    public void update() {
        this.maybeTalk();
        if (this.isDead) {
            this.visual.remove();
            return;
        }
        if (!this.canReceiveDamage()) {
            this.activated = false;
            this.target = null;
            this.x = this.default_x;
            this.y = this.default_y;
            this.updateEffKham();
            this.visual.sync(false);
            return;
        }
        this.activated = true;
        this.refreshDauSiFocus();
        super.update();
        this.keepInsideArena();
        this.maybeTalk();
        this.visual.sync(false);
    }

    @Override
    public void attack() {
        if (!this.activated || this.target == null) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - this.lastTimeAttack <= this.attackDelay) {
            return;
        }
        this.lastTimeAttack = now;
        try {
            Vector<Char> victims = new Vector<Char>();
            Vector<Integer> damages = new Vector<Integer>();
            if (this.target != null && this.target.hp > 0 && this.near(this.target, 160)) {
                victims.add(this.target);
                damages.add(Integer.valueOf(this.getDamageTo(this.target)));
            }
            Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);
            for (int i = 0; i < players.size(); i++) {
                Char player = players.get(i);
                if (player == null || player.hp <= 0 || victims.contains(player) || !this.near(player, 160)) {
                    continue;
                }
                victims.add(player);
                damages.add(Integer.valueOf(this.getDamageTo(player)));
                if (victims.size() >= 8) {
                    break;
                }
            }
            if (victims.isEmpty()) {
                return;
            }
            Message m = new Message(83);
            m.dos.writeShort(this.id);
            m.dos.writeByte(Map.r.nextInt(3));
            m.dos.writeByte(victims.size());
            for (int i = 0; i < victims.size(); i++) {
                Char player = victims.get(i);
                int damage = damages.get(i).intValue();
                int hp = player.hp - damage;
                if (hp < 0) {
                    hp = 0;
                }
                player.hp = hp;
                m.dos.writeShort(player.id);
                m.dos.writeShort(damage);
                m.dos.writeInt(hp);
            }
            m.dos.writeByte(-1);
            this.map.sendAllPlayer(m, this.inCountry);
            for (int i = 0; i < victims.size(); i++) {
                Char player = victims.get(i);
                if (player.hp <= 0) {
                    player.actorDie();
                }
            }
        } catch (IOException ignored) {
        }
    }

    @Override
    public Vector<Actor> onDropItem(Map m, Char p) {
        Vector<Actor> drops = new Vector<Actor>();
        long now = System.currentTimeMillis();
        this.rewardKiller(p);
        this.addGemDrops(drops, m, GemTemplate.ID_MATERIAL_LOW[5], now, p);
        this.addGemDrops(drops, m, GemTemplate.ID_MATERIAL_HIGHT[5], now, p);
        this.announceKiller(p);
        this.actorDie();
        KhaBanhEvent.getInstance().onBossDefeated(this, p);
        return drops;
    }

    @Override
    public void actorDie() {
        this.visual.remove();
        super.actorDie();
        if (this.map != null) {
            this.map.removeMonster(this.id, this.inCountry, this.region);
        }
    }

    public void syncVisual(boolean force) {
        this.visual.sync(force);
    }

    public void disposeVisual() {
        this.visual.remove();
    }

    private void addGemDrops(Vector<Actor> drops, Map m, short[] ids, long now, Char killer) {
        for (int i = 0; i < ids.length; i++) {
            int count = MATERIAL_DROP_MIN + Map.r.nextInt(MATERIAL_DROP_MAX - MATERIAL_DROP_MIN + 1);
            for (int j = 0; j < count; j++) {
                GemItem gem = new GemItem(ids[i]);
                gem.id = m.getIDITEM();
                gem.x = this.x + Map.r.nextInt(81) - 40;
                gem.y = this.y + Map.r.nextInt(41) - 20;
                gem.belongUser = killer != null ? killer.charDBID : 0;
                gem.isBoss = killer != null;
                gem.idPartyBoss = killer != null ? killer.charDBID : 0;
                gem.belongParty = killer != null ? killer.charDBID : 0;
                gem.islock = false;
                gem.time_drop = now;
                m.addGemItem(gem, this.inCountry);
                drops.add(gem);
            }
        }
    }

    private void rewardKiller(Char killer) {
        if (killer == null) {
            return;
        }
        killer.addXu(BOSS_XU_REWARD, "kha_banh_boss");
        killer.addLuong(BOSS_LUONG_REWARD);
        killer.addLuongLock(BOSS_LUONG_KHOA_REWARD);
        killer.sendMessage(MessageCreator.createCharInventoryMessage(killer, 0));
        killer.sendMessage(MessageCreator.createMsgChat(
                killer.id,
                "Bạn nhận được 50000 xu, 200 lượng và 100 lượng khóa khi kết liễu Khá Bảnh."
        ));
    }

    private void announceKiller(Char killer) {
        if (killer == null) {
            return;
        }
        String text = killer.charname + " đã kết liễu Khá Bảnh và độc chiếm toàn bộ chiến lợi phẩm.";
        for (int i = 0; i < 3; i++) {
            try {
                Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(text));
            } catch (IOException ignored) {
            }
        }
    }

    private int getDamageTo(Char player) {
        int damage = this.attackDam(player);
        if (this.attackMiss(player)) {
            return 0;
        }
        return damage;
    }

    private void keepInsideArena() {
        if (Math.abs(this.x - this.default_x) > this.moveRadius || Math.abs(this.y - this.default_y) > this.moveRadius) {
            this.x = this.default_x;
            this.y = this.default_y;
            this.target = null;
        }
    }

    private void maybeTalk() {
        if (this.map == null || this.isDead || System.currentTimeMillis() < this.nextTalkAt) {
            return;
        }
        this.nextTalkAt = System.currentTimeMillis() + 7000L + Map.r.nextInt(7000);
        this.sendBossChat(CHAT_LINES[Map.r.nextInt(CHAT_LINES.length)]);
    }

    private void refreshDauSiFocus() {
        if (this.isPreferredDauSiTarget(this.target)) {
            return;
        }
        Char preferred = this.findNearestDauSiTarget();
        if (preferred != null) {
            this.target = preferred;
        }
    }

    private Char findNearestDauSiTarget() {
        if (this.map == null) {
            return null;
        }
        Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);
        Char nearest = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < players.size(); i++) {
            Char player = players.get(i);
            if (!this.isPreferredDauSiTarget(player)) {
                continue;
            }
            int distance = Math.abs(this.x - player.x) + Math.abs(this.y - player.y);
            if (distance < bestDistance) {
                bestDistance = distance;
                nearest = player;
            }
        }
        return nearest;
    }

    private boolean isPreferredDauSiTarget(Char player) {
        return player != null
                && player.hp > 0
                && !player.isAdmin
                && player.charClass == Char.DS
                && player.map != null
                && player.map.equals(this.map)
                && player.region == this.region
                && player.inCountry == this.inCountry
                && this.near(player, DAU_SI_FOCUS_RANGE)
                && KhaBanhEvent.getInstance().canDamageMonster(player, this.inCountry);
    }

    private void sendBossChat(String line) {
        this.visual.say(line);
    }
}
