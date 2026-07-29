package real;

import io.Message;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class KhaBanhMinion extends Boss {

    private static final int HP_MULTIPLIER = 14;
    private static final int HP_FLOOR = 8500000;
    private static final int DAMAGE_MIN_PERCENT = 5;
    private static final int DAMAGE_MAX_PERCENT = 10;
    private static final int DAU_SI_DAMAGE_PERCENT = 2;
    private static final int DAU_SI_FOCUS_RANGE = 160;
    private static final int STUN_CHANCE_PERCENT = 35;
    private static final int STUN_DURATION = 2000;
    private static final int POISON_DURATION = 4000;
    private static final int MINION_XU_REWARD = 10000;
    private static final int MINION_LUONG_REWARD = 100;
    private static final int MINION_LUONG_KHOA_REWARD = 50;
    private static final int[] DROP_ITEM_IDS = new int[]{616, 742, 750, 617, 618, 619, 729, 746, 747, 748, 749, 754, 755};
    private static final byte VISUAL_GENDER = 1;
    private static final byte VISUAL_CLASS = 0;
    private static final int VISUAL_AO = 269;
    private static final int VISUAL_QUAN = 293;
    private static final int VISUAL_NON = -1;

    private final int slot;
    private final KhaBanhVisualActor visual;
    private short ownerBossId;

    public KhaBanhMinion(Map mapLiveIn, MonsterTemplate template, int x, int y, int country, int slot) {
        super(mapLiveIn, template, x, y, country);
        this.slot = slot;
        this.ownerBossId = -1;
        this.isBoss = false;
        this.randomMap = false;
        this.attackDelay = 1800L;
        this.moveDelay = 550L;
        this.percentDam = 105;
        this.level = 75;
        this.maxhp = Math.max(this.maxhp * HP_MULTIPLIER, HP_FLOOR);
        this.hp = this.maxhp;
        this.attack = Math.max(this.attack * 2, 9000);
        this.defend_physic = Math.max(this.defend_physic * 4, 6200);
        this.defend_magic = Math.max(this.defend_physic, 6200);
        this.accurate = 2400;
        this.dodge = 240;
        this.dmove = 80;
        this.visual = new KhaBanhVisualActor(this, this.getName(), VISUAL_GENDER, VISUAL_CLASS, this.getVisualHeadStyle(slot), VISUAL_AO, VISUAL_QUAN, VISUAL_NON);
    }

    public void setOwnerBossId(short ownerBossId) {
        this.ownerBossId = ownerBossId;
    }

    public boolean canBeDamagedBy(LiveActor attacker) {
        return KhaBanhEvent.getInstance().canDamageMonster(attacker, this.inCountry);
    }

    @Override
    public String getName() {
        return "\u0110\u1ec7 anh B\u1ea3nh " + this.slot;
    }

    @Override
    public void update() {
        if (this.isDead) {
            this.visual.remove();
            return;
        }
        this.refreshDauSiFocus();
        super.update();
        this.visual.sync(false);
    }

    @Override
    public int attackDam(LiveActor actor) {
        if (actor instanceof Char && ((Char) actor).charClass == Char.DS) {
            return (int) Math.max(1L, ((long) actor.maxhp * DAU_SI_DAMAGE_PERCENT) / 100L);
        }
        int minDamage = (int) Math.max(400L, ((long) actor.maxhp * DAMAGE_MIN_PERCENT) / 100L);
        int maxDamage = (int) Math.max(minDamage, ((long) actor.maxhp * DAMAGE_MAX_PERCENT) / 100L);
        return minDamage + Map.r.nextInt(Math.max(1, maxDamage - minDamage + 1));
    }

    @Override
    public void attack() {
        if (this.target == null) {
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
            if (this.target != null && this.target.hp > 0 && this.near(this.target, 128)) {
                victims.add(this.target);
                damages.add(Integer.valueOf(this.getDamageTo(this.target)));
            }
            Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);
            for (int i = 0; i < players.size(); i++) {
                Char player = players.get(i);
                if (player == null || player.hp <= 0 || victims.contains(player) || !this.near(player, 128)) {
                    continue;
                }
                victims.add(player);
                damages.add(Integer.valueOf(this.getDamageTo(player)));
                if (victims.size() >= 5) {
                    break;
                }
            }
            if (victims.isEmpty()) {
                return;
            }
            Message m = new Message(83);
            m.dos.writeShort(this.id);
            m.dos.writeByte(Map.r.nextInt(2));
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
                int damage = damages.get(i).intValue();
                if (player.hp <= 0) {
                    player.actorDie();
                    continue;
                }
                if (damage > 0) {
                    this.applyPressureEffects(player, now);
                }
            }
        } catch (IOException ignored) {
        }
    }

    @Override
    public Vector<Actor> onDropItem(Map m, Char p) {
        Vector<Actor> drops = new Vector<Actor>();
        this.rewardKiller(p);
        Item item = this.createRandomTimedItem();
        if (item != null) {
            item.id = m.getIDITEM();
            item.x = this.x + Map.r.nextInt(61) - 30;
            item.y = this.y + Map.r.nextInt(33) - 16;
            item.belongUser = 0;
            item.belongParty = KhaBanhEvent.getInstance().getMinionLootMarker(this.inCountry);
            item.time_drop = System.currentTimeMillis();
            m.addItem(item, this.inCountry);
            drops.add(item);
        }
        this.actorDie();
        KhaBanhEvent.getInstance().onMinionDefeated(this, p);
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

    private int getDamageTo(Char player) {
        int damage = this.attackDam(player);
        if (this.attackMiss(player)) {
            return 0;
        }
        return damage;
    }

    private void applyPressureEffects(Char player, long now) {
        boolean hasNewEffect = false;
        if (Map.r.nextInt(100) < STUN_CHANCE_PERCENT && player.addEffBuff(EffectBuff.CHOANG, now + STUN_DURATION, EffectBuff.BY_ACTOR, 0) != null) {
            hasNewEffect = true;
        }
        EffectBuff poison = player.addEffBuff(EffectBuff.TRUNG_DOC, now + POISON_DURATION, EffectBuff.BY_ACTOR, 0);
        if (poison != null) {
            poison.dam = (int) Math.max(120L, ((long) player.maxhp) / 100L);
            hasNewEffect = true;
        }
        if (hasNewEffect) {
            player.sendEffToChar(player);
            player.sendEffToNearChar();
        }
    }

    private void rewardKiller(Char killer) {
        if (killer == null) {
            return;
        }
        killer.addXu(MINION_XU_REWARD, "kha_banh_minion");
        killer.addLuong(MINION_LUONG_REWARD);
        killer.addLuongLock(MINION_LUONG_KHOA_REWARD);
        killer.sendMessage(MessageCreator.createCharInventoryMessage(killer, 0));
        killer.sendMessage(MessageCreator.createMsgChat(
                killer.id,
                "Bạn nhận được 10000 xu, 100 lượng và 50 lượng khóa từ Đệ anh Bảnh."
        ));
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

    private Item createRandomTimedItem() {
        int itemId = DROP_ITEM_IDS[Map.r.nextInt(DROP_ITEM_IDS.length)];
        ItemTemplates template = (ItemTemplates) ((Hashtable) Map.itemTemplates.get(5)).get(Integer.valueOf(itemId));
        if (template == null) {
            return null;
        }
        Item item = new Item(template, false, itemId == 754 || itemId == 755 ? -1 : 0, 0, itemId);
        item.level = template.level;
        item.lock = 0;
        item.minuteExist = 6 * 24 * 60;
        item.timeLoan = System.currentTimeMillis();
        item.dateCreate = Char.getDayTime(0L);
        switch (itemId) {
            case 616:
            case 742:
            case 750:
                item.createAttributeItemCoat(true, (byte) Map.r.nextInt(2), -1);
                break;
            case 617:
            case 618:
            case 619:
            case 729:
                item.createAttChoiVinhVien();
                break;
            case 746:
            case 747:
            case 748:
            case 749: {
                int[] values = new int[]{5, 5, 10, 30, 50};
                int option = Map.r.nextInt(values.length);
                item.createOptionMatNa(option, values[option]);
                break;
            }
            case 754:
            case 755:
                item.createAttributeItemModel(false);
                break;
            default:
                break;
        }
        return item;
    }

    private byte getVisualHeadStyle(int slot) {
        return (byte) (13 + Math.max(0, slot - 1) * 2);
    }
}
