
package real;

import Constant.Checker;
import Constant.Logger2;
import com.khanhdz.ManagerGame;
import com.khanhdz.event.TichLuyOnline;
import com.khanhdz.runtime.runtimeCharProperties;
import java.io.DataInputStream;
import java.util.Set;
import data.Xoso;
import data.UserLogger;
import data.SeedTemplate;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import data.InfoQuestTemplate;
import util.Logger;
import data.Logdata;
import io.ThreadPool;
import java.util.Date;
import data.ItemQuest;
import data.Text;
import server.TeamServer;
import java.util.Random;
import data.Database;
import real.cmd.LoginHandler;
import java.util.Iterator;
import io.Message;
import data.NewClan;
import java.io.IOException;
import java.security.PublicKey;

import data.InfoCharPotion;
import data.DanhHieu;
import data.Cooking;
import data.TuBinhTemplate;
import data.FruitTemplate;
import data.AdvInfo;
import data.CharInfo;
import data.TopLuong;
import data.ItemLuckyDraw;
import data.Goverment;
import data.ModelWearing;
import data.CharThanthu;
import java.util.Calendar;
import data.ItemChangeColor;
import java.util.Hashtable;
import data.InfoItemCreate;
import data.GemItem;
import data.CharInboxMessage;
import data.SeedItem;
import data.Pet;
import data.Animal;
import io.Session;
import data.SellerInfo;
import data.Veso;
import java.util.Arrays;
import java.util.Vector;
import static real.Map.itemTemplates;
import real.plugins.*;

public class Char extends LiveActor {

    public runtimeCharProperties runtimeCharProperties = new runtimeCharProperties();

    // <editor-fold defaultstate="collapsed" desc="Instance method">
    public String systemGetInfo() {
        return String.format("Player: id: %s - name: %s%n", id, charname == null ? "NULL" : charname);
    }
    // </editor-fold>

    public Vector<String> nameLienTram;
    public static final int MAP_DUNGEON = 117;
    public static final int MAP_MATERIAL = 17;
    public static byte STRENGTH;
    public static byte AGITITY;
    public static byte SPIRIT;
    public static byte HEALTH;
    public static byte LUCK;
    public static final byte MAX_ITEM_QUEST = 2;
    public static final byte MAX_FARM = 50;
    public static final byte MAX_WEARING = 12;
    public short MAX_INVENTORY;
    public short idPotionUsed;
    public short idTongKim;
    public byte maxBag;
    public byte idTeamTongKim;
    public String dbSkill;
    public String dbPotion;
    public String dbBasic;
    public String dbCharQuest;
    public String dbInfo;
    public Vector<Integer> friendListID;
    public Vector<String> friendListName;
    public Vector<InfoChatRieng> allMsgChatRieng;
    public byte using;
    public byte plant;
    public byte lvHouse;
    public byte lvStore;
    private int luong;
    public int lockLuong;
    public int vetangluong;
    private long xu;
    public long xutim;
    public long xumat;
    public long xuxai;
    public long luongxai;
    public long luongxainhanRuong;
    public int charDBID, tichluy, tichluy_tuan, countChar, tichluy_bosung, qua_npc;
    public byte speed;
    public byte slotWearing;
    public short agenID;
    public short idClan;
    public short nMoonCake2Exp;
    public short ID_ITEM;
    public int userID;
    public short partyID;
    public int masterIDParty;
    public Vector<Short> idItem;
    public Vector<Short> idPotion;
    public Vector<Short> idGem;
    public Vector<Short> idSP;
    public Vector<Short> idItemQuest;
    public Vector<Veso> vesoCu;
    public Vector<Veso> vesoxu;
    public SellerInfo sellerInfo;
    public Party party;
    public String charname;
    public byte headStyle;
    public byte charClass;
    public byte[] skill;
    public short[] skillClan;
    public byte[] skillLvClan;
    public long[] timeUseSkillClan;
    public short strength;
    public short agitity;
    public short spirit;
    public short health;
    public short basic;
    public short luck;
    public short lastLV;
    public boolean exit;
    public boolean die_pk;
    public boolean gotWages;
    public boolean gifMoonFestival;
    public byte isBot;
    public byte rankClan;
    public LevelDetail lvDetail;
    public short basepoint;
    public short luongOpenInventory;
    public short skillpoint;
    private Session session;
    public short[][] coolDown;
    String stCapcha;
    public long[] timeLastUseSkills;
    public long timeDropCake;
    public long timeDropCake1;
    public long timeInClan;
    public long timeUp5Attribute;
    public long timeNguoiTuyet;
    public long timeNguoiTuyet2;
    public long timeLastMove;
    public long timeNextRegion;
    public long lastTimeNghienBot;
    public Vector<Short> nearChars;
    public Vector<Short> nearMons;
    public byte[] arrayBuff;
    public short[] timeOut;
    public short[] countDown;
    byte[] lvBuff;
    public long[] timeUseBuff;
    public long lasTimeMove;
    public long timeInviteTrade;
    public long timeGiveCardTown;
    public long xpLost;
    public long timeUsePK;
    public long timeJoinClan;
    public long timeHoiSinh;
    public int mp;
    public int maxmp;
    
    int lastX;
    int lastY;
    int pointCongHienClan;
    public byte typeConfig;
    public byte upSpeedNguaVanTieu;
    public int giveMoneyClan;
    public boolean isChangeMap;
    public boolean isMonster;
    public byte idNgtuyet;
    public String subCharname;
    public int[] potions;
    public short[] itemquest;
    public int[] tPotions;
    public int[] rPotions;
    public short[][] posNPCInVilage;
    public int attackDamage;
    public int outdelay;
    public Vector<Item> iItems;
    public Vector<Item> tItems;
    public Vector<Item> rItems;
    public Vector<Item> bItem;
    public Vector<Item> awItems;
    public Vector<Item> sellShopItem;
    public Vector<Item> bagItems;
    public Item[][] wItems;
    public Vector<Animal> animal;
    public Vector<Pet> pet;
    public Vector<SeedItem> seedsItem;
    public Vector<CharInboxMessage> inbox;
    public Vector<GemItem> gemItem;
    public Vector<GemItem> gemItemImbue;
    public int[] listGemitem;
    public int[] listGemitemLock;
    public int[] listGemitemSell;
    public int[] allGemGet;
    public int[] allGemGetLock;
    public int[] allGemUse;
    public int[] allGemUseLock;
    public Vector<ShopTemplate> specialItem;
    public int[] listSpItem;
    public int itemImbue;
    public int mapID;
    public int mapID_the_mua_ban;
    public short region_the_mua_ban;
    public byte x_the_mua_ban;
    public byte y_the_mua_ban;
    public short killer;
    public boolean isKiller;
    public byte pk;
    public byte typeItemBuy;
    public byte classBuy;
    public byte pk_chienTruong;
    public boolean isThodia;
    public boolean finishTrade;
    public boolean lockTrade;
    public boolean isPutItem2Bag;
    public boolean isImbue;
    public Vector<Char> userTrade;
    protected static final byte[] GENDER_OF_CLAZZ;
    public byte gender;
    public byte rideHorse;
    public byte nMsgChat;
    public byte idImgHorse;
    public boolean isNewClient;
    public boolean gotoIsland;
    public short nPKill;
    public short timeXP;
    public long timeKiller;
    public long timeWait;
    public long timeChat;
    public long timeChatRieng;
    public long timeChatBang;
    public long fullGoldTime;
    public long halfGoldTime;
    public long timeOutGame;
    public int minuteExp;
    public byte x2;
    public byte[] upAbility;
    public byte[] abilityKham;
    public byte[] attNguHanh;
    public byte[] attNguHanhAnimal;
    public long timePlay;
    public long startPlay;
    public boolean isAdmin;
    public boolean beAttack;
    public short nNearchar;
    public short idRegionNuiChauBau;
    public short idRegionLoidai;
    public byte autoGetItem;
    public byte autoSkill;
    public byte canGiveCard;
    public byte autoComeHome;
    public byte luot_van_tieu;
    public byte buy_luot_van_tieu;
    public byte cuop_tieu;
    public byte vip;
    private long timeHit;
    private long timeHit4;
    private long timeStartHack;
    public int oldLv;
    short nHit;
    short nhit4;
    public InfoItemCreate infoItemCreate;
    public Item itemAx;
    public Item itemLinhDanhThue;
    public static short maxHit;
    public static short maxHit4;
    public Hashtable<Short, Monster> monsters;
    public long timeWaitComeHome;
    public long timeUseThanHanhPhu;
    public byte rcvGifByHour;
    public byte rcvGifByMonth;
    public byte rcvGifByLv;
    public byte totalDayInMonth;
    public short hutmau;
    public short hoihp;
    public short hoimp;
    public short khangbanglan;
    public short khangsetlan;
    public short khangdoclan;
    public short lamthinh;
    public short tanphe;
    public short khanglualan;
    public boolean isOnlineToDay;
    private long millisOnlineStart;
    private long millisOnline;
    private long timeSave;
    public Monster monsTerThuThap;
    public Vector<Item> itemChange5;
    public ItemChangeColor itChangeColor;
    public static byte[][] dx_horse_tool;
    public static byte[][] dy_horse_tool;
    public static byte[][] Dx_Dy_WP_tool;
    public static byte[][] Dx_Dy_PP_tool;
    public static byte[][] Dx_Dy_AVT_tool;
    public int indexDxDyHorseTool;
    public int idHorseTool;
    public Hashtable<Short, PotionUse> coolDownPotion;
    public Vector<EffectBuff> ALL_BUFF_INMAP;
    public static byte ID_CHUYEN_MAP_CHIEN_TRUONG_MOBA;
    public static byte ID_ALL_KHU_CHIEN_TRUONG_MOBA;
    public static byte ID_NOI_LUC;
    public static byte ID_HON_DON;
    public static byte ID_HOI_HON;
    public static byte ID_HON_NGUYEN;
    public static byte ID_BACH_TIEN;
    public static byte ID_DINH_CHU;
    public static byte ID_CAM_LANG;
    public static byte ID_PT_HOA_CONG;
    public static byte ID_PT_HON_NGUYEN;
    public static byte ID_PT_HON_DON;
    public static byte ID_HOI_SINH;
    public static byte ID_INFO_PLAYER;
    public static byte ID_TIME_NUI_KHO_BAU;
    public static byte ID_NOI_LUC_TANG;
    public static byte ID_DEM_NGUOC;
    public static byte ID_TY_SO;
    public static byte ID_TIME_LOI_DAI;
    public static Hashtable<Integer, IndexWearing> POS_WEARING_CHAR;
    int indexHelp;
    public Vector<String> stHelp;
    byte nConfirm;
    private static byte[][] groupKickAnimal;
    private static byte[][] groupKick;
    public static Hashtable<Integer, GroupKick> GROUP_KICH;
    private byte fullSet15;
    private byte fullSet10;
    private byte fullSet13;
    byte isFullsetAnimal;
    public static int Lvx3Exp;
    public static Vector<String> timex2;
    public static Vector<String> timex3;
    public static Vector<String> timex50;
    public boolean rcvInvite;
    public boolean rcvInviteVip;
    public boolean canJoinTranYeuTran;
    short lvSearch;
    byte typeItemSearch;
    byte colortItemSearch;
    byte phamSearch;
    byte plusSearch;
    String searchChar;
    short[] buffPass;
    public int secondHealth;
    byte idRing;
    public static byte KHAM_MU;
    public static byte KHAM_DOC;
    public static byte KHAM_CHOANG;
    public static byte KHAM_BANG;
    public static byte KHAM_HOA_DA;
    public static byte KHAM_GIAM_TOC;
    int attackPlayer;
    int defendMaPlayer;
    int defendVatPlayer;
    public static final byte KIEM = 0;
    public static final byte DAO = 1;
    public static final byte PS = 2;
    public static final byte DS = 3;
    public static final byte CUNG = 4;
    short idBot;
    public byte posNPC;
    long timeSendHelp;
    long timedie;
    long timeCrazy;
    short isCrazy;
    public static byte SET_LAN;
    public static byte DOC_LAN;
    public static byte BANG_LAN;
    public static byte LUA_LAN;
    byte pcGiamSatThuongByCuMeo;
    static String[] namelan;
    long timeExistCharHire;
    byte classlinh;
    byte lvlinh;
    byte lvlinhthue;
    byte genderlinh;
    static int[] idmapthangbe;
    static int[][] xythangbe;
    static int[] idmapchucnu;
    static int[][] xychucnu;
    public byte typeTieu;
    public static int[] HP_MONSTER_VANTIEU;
    public static int[] DEF_MONSTER_VANTIEU;
    public static int[] UP_HP_MONSTER_VANTIEU;
    public static int[] UP_DEF_MONSTER_VANTIEU;
    static byte INDIVITUAL;
    static byte CLAN;
    static byte NATION;
    static long[][] EXP_NV_HANG_NGAY;
    public byte idNgtuyet2;
    long cooldownNguyetAnh;
    Calendar calCheck;
    private String dateStartOnline;
    static byte indexInfoAdv;
    public static boolean isRemove;
    public static long timeSendRaoVat;
    public MonsterVantieu monster;
    public Char charCopy;
    public Char charHire;
    public CharThanthu charthanthu;
    public long timeRegentHpMp;
    public boolean isDoChangeMap;
    public byte idCuiDot;
    public byte idRuouUong;
    public boolean isLuLan;
    int timeHs;
    public static short timeAddMoreHs;
    static byte TOTAL_FANCUNG;
    short countSecond;
    public long timeSendAlertQuangCao;
    byte nNpc;
    short wScreen;
    short hScreen;
    static int ID_HEAD_TIEU_LONG_NU;
    static int ID_HEAD_DUONG_QUA;
    static int ID_HEAD_HANG_NGA;
    static int ID_BODY_HANG_NGA;
    static int ID_LEG_HANG_NGA;
    static int ID_MAT_NA_BI_NGO;
    static int ID_MAT_NA_QUY;
    static int ID_MAT_NA_SOI;
    static int ID_MAT_NA_ZOOMBI;
    static int ID_MAT_NA_PANDA;
    public short[] rangeAddMonster;
    public short[] rangeRemoveMonster;
    public byte typeFirmware;
    long timeForbitChat;
    long timeSendAllChar;
    long timeSendEffTool5l;
    long timeBuffGiamSatThuong;
    byte countSendchat;
    int kkk;
    static byte[][] buffSkill;
    static byte[][] hourShield;
    public long timeExit;
    public Animal animalRide;
    public Pet petUsing;
    public Item itemHoaKyLan;
    public Item itemTam;
    public Item itemPet;
    public Item itemVukhiThoiTrang;
    byte winPH;
    public short hlt_buy;
    public short timePlayThamdinh;
    byte countnvVulan;
    int countMonsterKill;
    public int diemdoivePH;
    public byte reclass;
    static boolean offAuto;
    public Vector<Item> itemCheckDuplicate;
    int countDup;
    public long timeUseTheMuaBan;
    public static String[] nameTop100DaiViet;
    public String lastLog;
    public byte giftTop;
    public byte selectPhiphong;
    public static long[] timeExpireAutoSkill;
    public static long[] timeExpireAutoGetItem;
    public byte idAutoSkill;
    public byte idAutoGetitem;
    public long timeOnlineSukien;
    byte countTime;
    public String infoWearing;
    public Vector<Item> itemResetSock;
    public Vector<Byte> treeID;
    public int questID;
    public int nextQuest;
    public short idNpc;
    public short currentNpc;
    public boolean receiveQuest;
    public boolean isFinish;
    public boolean rcvQuestClan;
    public boolean finishQC;
    public int[][] monsterKill;
    public int[][] monsterIem;
    public int[] npc_quest;
    public boolean talk_npc;
    public CharQuest char_quest;
    public short[][] killMSClan;
    public short[][] itemClan;
    public short flower;
    public ModelWearing wModel;
    public byte[] newAtb;
    public byte[] addMoreLevelSkill;
    public byte[] lockAtb;
    public byte idSkillLearnClan;
    public byte myCountry;
    public byte inCountry;
    public long cSalary;
    public byte typeBox;
    Vector<InfoGifLucky> gifLuckyBox;
    Vector<Byte> idOpen;
    byte nSpecialBox;
    byte[][] nNglieu;
    public static byte[] lixilucky;
    public static byte totalLixiOpened;
    static int[] XU_LI_XI;
    static String daysomaymm;
    public static int numberLucky;
    public static int numberRandom;
    public static int total_open;
    public String dayOpenXoso;
    static short[] idGif35;
    public static int ID_BIG_AVT_AO_TIEN_NU;
    public static int ID_BIG_AVT_AO_TINH_QUAN;
    public static int ID_BIG_AVT_PP_KHONG_TUOC;
    public static int ID_BIG_AVT_PP_HUYEN_VU;
    public static int ID_BIG_AVT_PP_BONG_DA;
    public static int ID_BIG_AVT_AO_DAI_NAM;
    public static int ID_BIG_AVT_AO_DAI_NU;
    static byte[] idRankGov;
    public byte headModel;
    public static Goverment[] gov;
    static Vector<ItemLuckyDraw> allItemLuckyDraw;
    byte indexItemLuckyDraw;
    public static byte pcWinLuckyDraw;
    public static byte nItemLucky;
    public static byte addMore;
    private static byte[][] groupLucky;
    public static String[] except_quay_so;
    public static long timeGetPhuongHoang;
    public static Hashtable<String, TopLuong> TOP_LUONG;
    public static byte have8k;
    long timeMove;
    int lastMovex;
    int lastMovey;
    static int[][] lcx_chan_nui_chau_bau;
    static int[][] lcy_chan_nui_chau_bau;
    boolean startAutoImbue;
    boolean lkdLock;
    boolean MMLock;
    boolean BHLock;
    byte plus;
    short idGemLkd;
    short idGemMM;
    short idGemBH;
    public byte receiveLG;
    public byte gif35;
    public byte nHopMut;
    public int pArena;
    public byte buyAnimalArmor;
    public boolean isCorrectPass;
    public long luongNap;
    public long luonglost;
    public long salaryClan;
    public long expRace;
    public int x25Exp;
    public int x2Phaohoa;
    public int honor;
    public int idWedding;
    public int idArena;
    public byte idFlag;
    public short totalKill;
    public short maxKill;
    public short idGropRace;
    public short idBanhNuongDoi;
    public short idNglieuDoi;
    public short totalKillInArena;
    public short nBekill;
    public byte forbitChat;
    public byte married;
    public byte rcvGiftWedding;
    public String dayLogin;
    public String dayCamChat;
    public byte receiveGiftEvent;
    public byte subpk;
    public byte rankGov;
    public byte usingSummons;
    public byte posRing;
    public byte countQuestClan;
    public byte nBinhTra;
    public byte gif83;
    public byte ntangtoc;
    public byte receiveQuestVulan;
    public byte receiveGiftOldChar;
    public Vector<String> listKiller;
    public int typeGemImbue;
    public int diemNapVip;
    public int diemxaiVip;
    public int typeGemKham;
    public byte xichtho_thienlyma;
    public byte typeHopMaterial;
    public byte slNgLieuDoi;
    public byte type_luong_doi_he;
    public Vector<CharInfo> myFriend;
    public static Vector<AdvInfo> infoAdv;
    public static Vector<AdvInfo> infoAdv2;
    public int itemTemp;
    public int charCount;
    public int moneyThachdau;
    public int moneyTrade;
    public short idItemTach;
    public short setlan;
    public short banglan;
    public short lualan;
    public short doclan;
    public short tyleRotItem;
    public short tylerotXu;
    public String nameHusband_wife;
    public String pInviteWedding;
    public String passWord;
    public String name_char_loi_dai;
    public Vector<Byte> idGifWedding;
    public short idMainQuest;
    public short pointActiveQuest;
    public short minuteSocola;
    public short minuteOnline;
    public NewCharQuest mainQuest;
    public NewCharQuest subQuest;
    public NewCharQuest clanQuestNew;
    public NewCharQuest eventQuest;
    public String itemget;
    public String monskilled;
    public String finish;
    public String itquest;
    public String equip;
    public String invent;
    public String bag;
    public String tuido;
    public Vector<Item> allItemBuy;
    public Vector<String> listKillme;
    public FruitTemplate[] fruit;
    public Vector<TuBinhTemplate> tubinh;
    private Hashtable<Short, Short> ALL_ID_USE;
    public String nameCompetitor;
    public Vector<ItemSell> allNewItem;
    public Vector<ItemSell> allSearchItem;
    public Vector<ItemSell> allItemBid;
    public ItemSell itemBuy;
    public static Hashtable<String, Integer> TOP_CHAR_USE_LUONG;
    public long lastTimeOut;
    public long timeEatSocola;
    public long timecamchat;
    public byte firstNapMoney;
    public byte new_search;
    public int banphaohoa;
    public static Cooking nauBanhChung;
    public static Cooking nauBanhTet;
    public Vector<Item> itemPetEat;
    public static boolean isBaotriVxmm;
    public Vector<DanhHieu> allDanhHieu;
    public DanhHieu currentDanhHieu;
    public int idEffDanhHieu;
    static short[] ID_POINT_NOI_LUC_CHIEN_TRUONG;
    public static Hashtable<String, PlayerXoso> ALL_PLAYER_XOSO;
    public static long MONEY_XOSO;
    public static long timeXoso;
    public static String charNameXosoWin;
    public static int moneyCharXosowin;
    public static int moneyCharXosoBid;
    public static Hashtable<String, PlayerXoso> ALL_PLAYER_XOSO_low;
    public static long MONEY_XOSO_low;
    public static long timeXoso_low;
    public static String charNameXosoWin_low;
    public static int moneyCharXosowin_low;
    public static int moneyCharXosoBid_low;
    public static byte onOffThamdinh;
    public static byte onOffTime180;
    public static String chartopchoitrungthu;
    public byte canReceiveGiftOffline;
    public byte buychoi;
    public byte buylongden;
    public short idPetTang;
    public byte typeJoinXoso;
    public String hoten;
    public String diachi;
    public String nametangkm;
    public String nameTangPet;
    ItemWaitSell itemwait;
    public int nhomThidau;
    public static int dayEndVip;
    public static int timeEndVip;
    public static int minuteEnd;
    public static final int VIP_LEVEL_1 = 10;
    public static final int VIP_LEVEL_2 = 30;
    public static final int VIP_LEVEL_3 = 50;
    public static byte[] soluong_loa_vip;
    public static byte[] up_px_xu_vip;
    public int countRongLon; // Thêm biến đếm số lần bị đánh
    public long timeRongLon;
    private long timeActive8sRongLon;
    private int damageAccumulated = 0; // Sát thương tích lũy

    public long lastTimeUseHP = 0;
    public long lastTimeUseMP = 0;
    // bo suu tap
    public int isCaiTrang = 0;
    public boolean isNghienAuto = false;
    public boolean isAutoBan = false;


    static {
        Char.STRENGTH = 0;
        Char.AGITITY = 1;
        Char.SPIRIT = 2;
        Char.HEALTH = 3;
        Char.LUCK = 4;
        GENDER_OF_CLAZZ = new byte[]{1, 1, 2, 1, 2};
        Char.maxHit = 150;
        Char.maxHit4 = 200;
        Char.dx_horse_tool = new byte[][]{{0, 0, 7, -7}, {0, 0, 5, -5}, {0, 0, 8, -8}, new byte[4],new byte[4]};
        Char.dy_horse_tool = new byte[][]{{-20, -15, -15, -15}, {-25, -15, -15, -15}, {-20, -15, -15, -15}, {-22, -15, -16, -16},{ -22, -15, -17, -16 }};

        Char.Dx_Dy_WP_tool = new byte[][]{
            {-35, -27, -30, -37, -26, -55, -55, -47, -35, -55,-46,-35},
            {-75, -71, -60, -40, -52, -100, -75, -90, -85, -70,-48,-60}
        };

        Char.Dx_Dy_PP_tool = new byte[][]{{-26, -32,60}, {-58, -58,-58}};

        Char.Dx_Dy_AVT_tool = new byte[][]{
            {-44, -18, -44, -18, -18, -44, -44, -25, -20, -21, -20, -26, -28, -28,-22,-32},
            {-63, -63, -67, -69, -70, -63, -63, -69, -64, -62, -69, -69, -69, -85,-75,-75}
        };


        Char.ID_CHUYEN_MAP_CHIEN_TRUONG_MOBA = 0;
        Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA = 1;
        Char.ID_NOI_LUC = 2;
        Char.ID_HON_DON = 3;
        Char.ID_HOI_HON = 4;
        Char.ID_HON_NGUYEN = 5;
        Char.ID_BACH_TIEN = 6;
        Char.ID_DINH_CHU = 7;
        Char.ID_CAM_LANG = 8;
        Char.ID_PT_HOA_CONG = 9;
        Char.ID_PT_HON_NGUYEN = 10;
        Char.ID_PT_HON_DON = 11;
        Char.ID_HOI_SINH = 12;
        Char.ID_INFO_PLAYER = 13;
        Char.ID_TIME_NUI_KHO_BAU = 14;
        Char.ID_NOI_LUC_TANG = 15;
        Char.ID_DEM_NGUOC = 16;
        Char.ID_TY_SO = 17;
        Char.ID_TIME_LOI_DAI = 18;
        (Char.POS_WEARING_CHAR = new Hashtable<Integer, IndexWearing>()).put(0, new IndexWearing(new byte[1]));
        Char.POS_WEARING_CHAR.put(1, new IndexWearing(new byte[]{1}));
        Char.POS_WEARING_CHAR.put(2, new IndexWearing(new byte[]{2}));
        Char.POS_WEARING_CHAR.put(11, new IndexWearing(new byte[]{3}));
        Char.POS_WEARING_CHAR.put(9, new IndexWearing(new byte[]{4}));
        Char.POS_WEARING_CHAR.put(8, new IndexWearing(new byte[]{6, 5}));
        Char.POS_WEARING_CHAR.put(10, new IndexWearing(new byte[]{7}));
        Char.POS_WEARING_CHAR.put(12, new IndexWearing(new byte[]{8}));
        Char.POS_WEARING_CHAR.put(13, new IndexWearing(new byte[]{9}));
        Char.POS_WEARING_CHAR.put(3, new IndexWearing(new byte[]{10}));
        Char.POS_WEARING_CHAR.put(5, new IndexWearing(new byte[]{10}));
        Char.POS_WEARING_CHAR.put(4, new IndexWearing(new byte[]{10}));
        Char.POS_WEARING_CHAR.put(6, new IndexWearing(new byte[]{10}));
        Char.POS_WEARING_CHAR.put(7, new IndexWearing(new byte[]{10}));
        Char.POS_WEARING_CHAR.put(19, new IndexWearing(new byte[]{11}));
        Char.groupKickAnimal = new byte[][]{{18}, {16}, {14}, {15}, {17}};
        Char.groupKick = new byte[][]{{2, 3, 6, 7, 4, 5}, {0, 9}, {10, 8}, {10, 8}, {10, 8}, {10, 8}, {10, 8}, {10, 8}, {12, 11, 0, 9}, {1, 3, 6, 7, 4, 5}, {12, 11}, {8, 1}, {8, 1}};
        (Char.GROUP_KICH = new Hashtable<Integer, GroupKick>()).put(0, new GroupKick(new byte[]{2, 10}));
        Char.GROUP_KICH.put(1, new GroupKick(new byte[]{0, 4}));
        Char.GROUP_KICH.put(2, new GroupKick(new byte[]{5, 7}));
        Char.GROUP_KICH.put(3, new GroupKick(new byte[]{6, 1}));
        Char.GROUP_KICH.put(4, new GroupKick(new byte[]{2, 10}));
        Char.GROUP_KICH.put(5, new GroupKick(new byte[]{8, 3}));
        Char.GROUP_KICH.put(6, new GroupKick(new byte[]{0, 4}));
        Char.GROUP_KICH.put(7, new GroupKick(new byte[]{8, 3}));
        Char.GROUP_KICH.put(8, new GroupKick(new byte[]{6, 1}));
        Char.GROUP_KICH.put(9, new GroupKick(new byte[]{-1}));
        Char.GROUP_KICH.put(10, new GroupKick(new byte[]{5, 7}));
        Char.GROUP_KICH.put(11, new GroupKick(new byte[]{-1}));
        Char.Lvx3Exp = 0;
        Char.timex2 = new Vector<String>();
        Char.timex3 = new Vector<String>();
        Char.timex50 = new Vector<String>();
        Char.KHAM_MU = 0;
        Char.KHAM_DOC = 1;
        Char.KHAM_CHOANG = 2;
        Char.KHAM_BANG = 3;
        Char.KHAM_HOA_DA = 4;
        Char.KHAM_GIAM_TOC = 5;
        Char.SET_LAN = 0;
        Char.DOC_LAN = 1;
        Char.BANG_LAN = 2;
        Char.LUA_LAN = 3;
        Char.namelan = new String[]{"Bang", "Set", "Doc", "Lua"};
        Char.idmapthangbe = new int[]{2, 6, 8, 9};
        Char.xythangbe = new int[][]{{20, 20}, {36, 33}, {40, 40}, {33, 44}, {98, 67}, {48, 24}, {17, 64}, {77, 20}, {35, 10}, {43, 12}, {20, 26}, {2, 20}};
        Char.idmapchucnu = new int[]{113, 10, 15, 18, 21};
        Char.xychucnu = new int[][]{{87, 91}, {38, 8}, {9, 68}, {36, 4}, {8, 12}, {6, 65}};
        Char.HP_MONSTER_VANTIEU = new int[]{5500000, 15000000, 19000000, 23000000, 27000000, 31000000};
        Char.DEF_MONSTER_VANTIEU = new int[]{9000, 16200, 22200, 45000, 65000, 85000};
        Char.UP_HP_MONSTER_VANTIEU = new int[]{0, 10, 20, 35, 50, 65, 65, 65};
        Char.UP_DEF_MONSTER_VANTIEU = new int[]{0, 4, 8, 12, 14, 20, 20};
        Char.INDIVITUAL = 0;
        Char.CLAN = 1;
        Char.NATION = 2;
        Char.EXP_NV_HANG_NGAY = new long[][]{{500000L, 1200000L, 2000000L}, {1200000L, 2500000L, 4000000L}, {3000000L, 6000000L, 10000000L}, {6000000L, 16000000L, 25000000L}, {24000000L, 50000000L, 80000000L}, {40000000L, 90000000L, 150000000L}, {80000000L, 180000000L, 300000000L}};
        Char.indexInfoAdv = 0;
        Char.isRemove = false;
        Char.timeSendRaoVat = System.currentTimeMillis() + 20000L;
        Char.timeAddMoreHs = 30000;
        Char.TOTAL_FANCUNG = 100;
        Char.ID_HEAD_TIEU_LONG_NU = 8784;
        Char.ID_HEAD_DUONG_QUA = 8783;
        Char.ID_HEAD_HANG_NGA = 8792;
        Char.ID_BODY_HANG_NGA = 8793;
        Char.ID_LEG_HANG_NGA = 8794;
        Char.ID_MAT_NA_BI_NGO = 8800;
        Char.ID_MAT_NA_QUY = 8801;
        Char.ID_MAT_NA_SOI = 8802;
        Char.ID_MAT_NA_ZOOMBI = 8803;
        Char.ID_MAT_NA_PANDA = 8804;
        Char.buffSkill = new byte[][]{{4, 5}, {4, 5}, {4, 5, 6, 7}, {4, 5}, {4, 5}};
        Char.hourShield = new byte[][]{{6, 10}, {11, 15}, {16, 20}, {20, 24}};
        Char.offAuto = true;
        Char.nameTop100DaiViet = new String[]{"chucteo", "bamtoc", "duoitoc", "cbrong", "14LaTao", "tokyo1", "zzshaclozz", "zkimdongz", "uylong", "baophuong", "aloha", "theanh", "xemtaone", "kiemhiep", "xedap96vn", "rainy", "9999p", "buadapcho", "nhubg83", "lamborgini", "ChienBjnh", "giangho02", "daophap", "chjenthan", "longvuong", "boday", "genha", "zhieudolaz", "betyxiu", "start", "chihuy", "zz321pro", "BaiTranh", "zzvip9zz", "taolaso12", "zzz123zzz", "vebumedj", "thanhomenh", "zmakiem", "daika", "saobang", "nhoyeu", "hjhehe", "99xu", "fogvan", "badaosv", "bua223", "xxbadaoxx", "zxpeyeuxz", "0owaterdam", "tokyo", "duongpro", "Honey", "kathy8x", "b0ydepzai", "boyydepzai", "khoc5", "shdida", "gakaka", "thandao", "b0ydepzaii", "lyhamchoi", "XauTrai", "khoc1", "Apply3", "xuanquynh", "khoc123", "Fighter", "huebg87", "vida5", "zoro", "thanbua", "dawat", "taxi2017", "ikuta", "tuananh", "Apply2", "sangbg82", "Apply", "trjutulong", "7DAPPRO", "satang1", "hoavjnh", "batgioi1", "24htansat", "tragkhuyet", "vipvipday1", "thienlong", "Trau", "nicolaizs3", "dosatthue", "bmw8", "boyvip", "heawen12", "ngoclinh", "tokyo3", "nhuom", "Nobita", "duckien", "tokyo2"};
        Char.timeExpireAutoSkill = new long[]{2592000000L, 3600000L};
        Char.timeExpireAutoGetItem = new long[]{2592000000L, 3600000L};
        Char.lixilucky = new byte[]{-1, -1, -1, -1, -1};
        Char.totalLixiOpened = 0;
        Char.XU_LI_XI = new int[]{1000000, 1000000, 1000000, 2000000, 5000000};
        Char.daysomaymm = "";
        Char.numberLucky = 5300;
        Char.numberRandom = 4067;
        Char.total_open = 0;
        Char.idGif35 = new short[]{80, 101, 73, 136, 115, 108, 94, 129};
        Char.ID_BIG_AVT_AO_TIEN_NU = 613;
        Char.ID_BIG_AVT_AO_TINH_QUAN = 615;
        Char.ID_BIG_AVT_PP_KHONG_TUOC = 614;
        Char.ID_BIG_AVT_PP_HUYEN_VU = 625;
        Char.ID_BIG_AVT_PP_BONG_DA = 711;
        Char.ID_BIG_AVT_AO_DAI_NAM = 628;
        Char.ID_BIG_AVT_AO_DAI_NU = 629;
        Char.idRankGov = new byte[]{56, 54, 55, 53};
        Char.gov = new Goverment[]{new Goverment(), new Goverment(), new Goverment()};
        Char.allItemLuckyDraw = new Vector<ItemLuckyDraw>();
        Char.pcWinLuckyDraw = 2;
        Char.nItemLucky = 11;
        Char.addMore = 2;
        Char.groupLucky = new byte[][]{{42, 43, 44, 45, 46, 47, 48, 49}, {51}, {15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41}, {50, 52}};
        Char.except_quay_so = null;
        Char.timeGetPhuongHoang = 0L;
        Char.TOP_LUONG = new Hashtable<String, TopLuong>();
        Char.have8k = 0;
        Char.lcx_chan_nui_chau_bau = new int[][]{{7, 4}, {19, 4}, {30, 4}, {42, 4}, {52, 4}};
        Char.lcy_chan_nui_chau_bau = new int[][]{{6, 3}, {6, 3}, {6, 3}, {6, 3}, {6, 3}};
        Char.infoAdv = new Vector<AdvInfo>();
        Char.infoAdv2 = new Vector<AdvInfo>();
        Char.TOP_CHAR_USE_LUONG = new Hashtable<String, Integer>();
        Char.nauBanhChung = new Cooking();
        Char.nauBanhTet = new Cooking();
        Char.isBaotriVxmm = false;
        Char.ID_POINT_NOI_LUC_CHIEN_TRUONG = new short[]{0, 20, 60, 140, 260, 410, 590, 800, 1050, 1350};
        Char.ALL_PLAYER_XOSO = new Hashtable<String, PlayerXoso>();
        Char.MONEY_XOSO = 0L;
        Char.timeXoso = System.currentTimeMillis();
        Char.charNameXosoWin = "";
        Char.moneyCharXosowin = 0;
        Char.moneyCharXosoBid = 0;
        Char.ALL_PLAYER_XOSO_low = new Hashtable<String, PlayerXoso>();
        Char.MONEY_XOSO_low = 0L;
        Char.timeXoso_low = System.currentTimeMillis();
        Char.charNameXosoWin_low = "";
        Char.moneyCharXosowin_low = 0;
        Char.moneyCharXosoBid_low = 0;
        Char.onOffThamdinh = 1;
        Char.onOffTime180 = 0;
        Char.chartopchoitrungthu = "";
        Char.dayEndVip = 1;
        Char.timeEndVip = 23;
        Char.minuteEnd = 59;
        Char.soluong_loa_vip = new byte[]{0, 1, 3, 5};
        Char.up_px_xu_vip = new byte[]{0, 0, 10, 30};
    }

    public static int getIndexItemWearing(final int type, int posNhan) {
        if (type != 8) {
            posNhan = 0;
        }
        return Char.POS_WEARING_CHAR.get(type).getIndex(posNhan);
    }

    public boolean countHit() {
        return false;
    }

    public int receiveXP() {
        if (!Map.checkRcvXP) {
            return 1;
        }
        if (this.timeXP < 180) {
            return 1;
        }
        if (this.timeXP < 300) {
            return 2;
        }
        return 3;
    }

    public Vector<InfoCharPotion> getAllPotion() {
        final Vector<InfoCharPotion> all = new Vector<InfoCharPotion>();
        for (int i = 1; i < 162; ++i) {
            if (this.potions[i] > 0) {
                all.add(new InfoCharPotion(i, this.potions[i]));
            }
        }
        return all;
    }

    public void doAddItemSellShop(final Item it) {
        if (it.timeLoan > 0L && it.minuteExist > 0) {
            return;
        }
        this.sellShopItem.add(it);
        if (this.sellShopItem.size() > 20) {
            this.sellShopItem.remove(0);
        }
    }

    public void doRegentHpByBattle() {
        final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
        if (c != null) {
            final long pc = c.getNoiLuc();
            this.hp += (int) (this.maxhp * pc / 100L);
            this.mp += (int) (this.maxmp * pc / 100L);
            if (this.hp > this.maxhp) {
                this.hp = this.maxhp;
            }
            if (this.mp > this.maxmp) {
                this.mp = this.maxmp;
            }
            try {
                this.sendMessage(MessageCreator.createNew_HMP_Message(this, (int) (this.maxhp * pc / 100L)));
                this.sendToNearPlayer(MessageCreator.createNew_HMP_Message(this, (int) (this.maxhp * pc / 100L)));
                MessageCreator.createMsgUseHpMP(this, (int) (this.maxmp * pc / 100L), 4);
            } catch (final IOException ex) {
            }
        }
    }

    public void confirmCapcha(final String st) {
        ++this.nConfirm;
        if (this.nConfirm > 3) {
            CharManager.instance.kickPlayer(this, "char 2");
            return;
        }
        if (!st.equals(this.stCapcha)) {
            // if (this.charname.equals("chienthan")) {
            //     return;
            // }
            this.stCapcha = MessageCreator.createMsgCapcha(this);
        } else {
            this.timeStartHack = 0L;
            this.nHit = 0;
            this.nConfirm = 0;
            this.timeHit = 0L;
            this.nhit4 = 0;
            this.timeHit4 = 0L;
        }
    }

    public void resetNguHanh() {
        this.attNguHanh = new byte[5];
        this.attNguHanhAnimal = new byte[2];
        for (int i = 0; i < (this.wItems[this.slotWearing]).length; i++) {
            Item item = this.wItems[this.slotWearing][i];
            if (item != null) {
                item.beKick = 0;

                if (item.isPhiphonghehehe()) {
                    item.beKick = 1;

                }

            }
        }
        Vector<Item> wItems = getAnimalWearing();
        for (int j = 0; j < wItems.size(); j++) {
            ((Item) wItems.get(j)).beKick = 0;
        }
        this.newAtb = new byte[10];
        this.addMoreLevelSkill = new byte[15];
        this.lockAtb = new byte[3];
    }

    public void reCheckAtb5() {
        calculateAttrib();
        try {
            sendMessage(MessageCreator.createMainCharInfoMessage(this));
        } catch (Exception exception) {
        }
    }

    public String checkNhanLuongBang() {
        if (this.idClan == -1) {
            return null;
        }
        if (System.currentTimeMillis() - this.timeInClan < 0L) {
            return getDayTimeByTime(this.timeInClan);
        }
        return null;
    }

    public boolean canPhatLuongBang() {
        final NewClan cl = NewClan.getClan(this.idClan);
        return false;
    }

    public void setSkillClan(final int idSkill, final short time, final int devoteBuy, final byte lvSkill) {
        this.skillClan[idSkill] = time;
        this.pointCongHienClan -= devoteBuy;
        this.timeUseSkillClan[idSkill] = System.currentTimeMillis();
        this.skillLvClan[idSkill] = lvSkill;
        this.sendMessage(MessageCreator.createSkillClan(this));
    }

    public void checkKick5AttAnimal() {
        this.isFullsetAnimal = 0;
        Vector<Item> wItems = getAnimalWearing();
        byte type = 0;
        while (type < wItems.size()) {
            Item mItem = wItems.get(type);
            try {
                if (mItem.heItem > -1) {
                    if (mItem.colorName == 1 && mItem.hangItem == 1
                            && mItem.plus == 15) {
                        this.isFullsetAnimal = (byte) (this.isFullsetAnimal + 1);
                    }
                    for (int i = 0; i < wItems.size(); i++) {
                        Item it = wItems.get(i);
                        for (int j = 0; j < (groupKickAnimal[mItem.getType() - 14]).length; j++) {
                            if (it.magic_physic == mItem.magic_physic && it.getType() == groupKickAnimal[mItem.getType() - 14][j] && (mItem.heItem + 1) % 5 == it.heItem && it.heItem > -1) {
                                if (it.beKick != 1) {
                                    it.beKick = 1;
                                    for (int k = 0; k < 2; k++) {
                                        this.attNguHanhAnimal[k] = (byte) (this.attNguHanhAnimal[k] + it.otherAtt[k]);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            } catch (Exception exception) {
            }
            type = (byte) (type + 1);
        }
    }

    public boolean checkGroupKick() {
        return false;
    }

    public void checkKickAtt5() {
        int type = 0;
        this.fullSet15 = 0;
        this.fullSet10 = 0;
        this.fullSet13 = 0;
        Vector<Item> wItems = getCharWearing();
        wItems = changePosRing(wItems);
        while (type < wItems.size()) {
            Item mItem = null;
            mItem = wItems.get(type);
            int i;
            for (i = 0; i < mItem.newAtb.length; i++) {
                this.newAtb[i] = (byte) (this.newAtb[i] + mItem.newAtb[i]);
            }
            for (i = 0; i < mItem.addMoreLevelSkill.length; i++) {
                this.addMoreLevelSkill[i] = (byte) (this.addMoreLevelSkill[i] + mItem.addMoreLevelSkill[i]);
                if (this.addMoreLevelSkill[i] > 1) {
                    this.addMoreLevelSkill[i] = 1;
                }
            }
            for (i = 0; i < mItem.lockAtb.length; i++) {
                this.lockAtb[i] = (byte) (this.lockAtb[i] + mItem.lockAtb[i]);
            }
            if (mItem.heItem > -1) {
                if (mItem.plus == 15) {
                    this.fullSet15 = (byte) (this.fullSet15 + 1);
                }
                if (mItem.plus >= 13) {
                    this.fullSet13 = (byte) (this.fullSet13 + 1);
                }
                if (mItem.plus >= 10) {
                    this.fullSet10 = (byte) (this.fullSet10 + 1);
                }
                try {
                    int indexMain = getIndexItemWearing((mItem.getTemplate()).type, mItem.pos);
                    byte[] group = ((GroupKick) GROUP_KICH.get(Integer.valueOf(indexMain))).getGroup();
                    for (int j = 0; j < wItems.size(); j++) {
                        Item it = wItems.get(j);
                        int checkIndex = getIndexItemWearing((it.getTemplate()).type, it.pos);
                        if (it.magic_physic == mItem.magic_physic || Map.isWeapone(it.getType()) || Map.isWeapone(mItem.getType())) {
                            for (int k = 0; k < group.length; k++) {
                                if (checkIndex == group[k] && (mItem.heItem + 1) % 5 == it.heItem && it.heItem > -1) {
                                    if (it.beKick != 1) {
                                        it.beKick = 1;
                                        for (int m = 28; m < 33; m++) {
                                            this.attNguHanh[m - 28] = (byte) (this.attNguHanh[m - 28] + it.atb[m] + it.getValueUpByVuKhiThoiTrang(it.atb[m], this.itemVukhiThoiTrang));
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            type++;
        }
    }

    public void setItemChange5() {
        Vector<Item> wItems = getCharWearing();
        this.itemChange5.removeAll(this.itemChange5);
        for (int i = 0; i < wItems.size(); i++) {
            Item it = wItems.get(i);
            if (it.heItem != -1 && it.level >= 40 && it.lock == 1) {
                this.itemChange5.add(it);
            }
        }
    }

    public void desTroy() {
        for (int i = 0; i < 6; ++i) {
            this.countDown[i] = 0;
            this.timeUseBuff[i] = 0L;
            this.arrayBuff[i] = -1;
            this.lvBuff[i] = 0;
        }
        this.percentBuff[0] = 0;
        this.percentBuff[1] = 0;
        this.percentBuff[2] = 0;
        this.divSpeed = 1;
        try {
            this.timeCrazy = 0L;
            this.isCrazy(this.isCrazy);
            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
            this.sendToNearPlayer(MessageCreator.createCharInfo(this));
            MessageCreator.createMsgCharMonster(this, this);
        } catch (final Exception ex) {
        }
        this.resetTimeLastuseSkill();
    }

    public int getPK() {
        return this.killer / 100;
    }

    public void sendMsgDropList(final Vector<Actor> dropList) {
        try {
            final Message m = new Message(-55);
            m.dos.writeByte(dropList.size());
            for (int i = 0; i < dropList.size(); ++i) {
                final Actor a = dropList.get(i);
                m.dos.writeByte(a.cat);
                if (a.cat == 4) {
                    m.dos.writeShort(a.getType());
                } else if (a.cat == 3) {
                    m.dos.writeShort(((Item) a).getTemplate().id);
                } else if (a.cat == 6) {
                    m.dos.writeShort(((GemItem) a).idGemTemplate);
                } else if (a.cat == 7) {
                    m.dos.writeShort(((ShopTemplate) a).id);
                }
                if (a.cat == 7) {
                    m.dos.writeShort(((ShopTemplate) a).realId);
                } else {
                    m.dos.writeShort(a.id);
                }
                m.dos.writeShort(a.x);
                m.dos.writeShort(a.y);
            }
            this.sendMessage(m);
            this.sendToNearPlayer(m);
        } catch (final Exception ex) {
        }
    }

    public int expReceive(int xpReceive) {
        int dxp = 0;
        if (this.petUsing != null) {
            dxp = dxp * this.petUsing.getPcUpExp() / 100;
        }

        // xp canh
        int xpCanh = 0;
        if (this.wModel.CanhModel != null) {
            xpCanh = (int)(xpReceive * (this.wModel.CanhModel.otherAtt[50] / 100.0));
        }

        int xpCaiTrang = 0;
        if (isBatGioi()) {
            xpCaiTrang = (int)(xpReceive * (this.getLevelCaiTrang() / 100.0));
        }
        // xp mat na
        int xpMatNa = 0;
        if (this.wModel.matNa != null) {
            xpMatNa = (int)(xpReceive * (this.wModel.matNa.otherAtt[50] / 100.0));
        }

        int expPhaohoa = 0;
        int expPhaohoa2 = 0;
        if (Map.x3ExpDotPhao > 0) {
            expPhaohoa = xpReceive;
        }
        if (this.x2Phaohoa > 0) {
            expPhaohoa2 = xpReceive / 2;
        }
        int x2Player = this.getX2();
        int expWedding = 0;
        try {
            if (this.nameHusband_wife.equals("")) {
                final Char p = CharManager.instance.getCharByCharName(this.nameHusband_wife);
                if (p != null && p.mapID == this.mapID) {
                    expWedding = xpReceive * 2 / 100;
                }
            }
        } catch (final Exception ex) {
        }
        if (getDayOpen(0L).equals("2015-04-28") || getDayOpen(0L).equals("2015-04-30") || getDayOpen(0L).equals("2015-05-01")) {
            x2Player = 2;
        }
        final String daykm = getDayOpen(0L);
        final String time = getDayTime(0L);
        for (int i = 0; i < Char.timex50.size(); ++i) {
            if (daykm.equals(Char.timex50.get(i)) || time.compareTo((String) Char.timex50.get(i)) < 0) {
                x2Player = 1;
                break;
            }
        }
        for (int i = 0; i < Char.timex2.size(); ++i) {
            if (daykm.equals(Char.timex2.get(i)) || time.compareTo((String) Char.timex2.get(i)) < 0) {
                x2Player = 2;
                break;
            }
        }
        int i = 0;
        while (i < Char.timex3.size()) {
            if (daykm.equals(Char.timex3.get(i)) || time.compareTo((String) Char.timex3.get(i)) < 0) {
                if (this.lvDetail.lv < Char.Lvx3Exp) {
                    x2Player = 4;
                    break;
                }
                break;
            } else {
                ++i;
            }
        }
        int expEf = 0;
        if (this.idCuiDot > -1) {
            final int[] pcef = {5, 10, 20};
            expEf = xpReceive * pcef[this.idCuiDot] / 100;
        }
        int exfhove = 0;
        if (this.idNgtuyet == 3) {
            exfhove = xpReceive * 20 / 100;
        }
        if (Map.doubleALL > 1) {
            x2Player = 0;
        }
        if (x2Player == 1) {
            xpReceive += xpReceive / 2;
        } else if (x2Player == 2) {
            xpReceive *= x2Player;
        } else if (x2Player == 3) {
            int exp50 = xpReceive / 2;
            if (exp50 <= 0) {
                exp50 = 1;
            }
            xpReceive = xpReceive * 2 + exp50;
        } else if (x2Player == 4) {
            xpReceive *= 3;
        }
        // Server EXP multiplier is controlled by 5h_systems.tangexp.
        xpReceive *= TangEXP;
//        if (getDayOpen(0L).equals("2023-12-15") || getDayOpen(0L).equals("2023-12-16")
//                || getDayOpen(0L).equals("2023-12-17") || getDayOpen(0L).equals("2023-12-18")
//                || getDayOpen(0L).equals("2023-12-19")) {
//            xpReceive *= 5;
//        } 
       int  exp = xpReceive + xpCanh + xpCaiTrang + xpMatNa + expWedding + dxp + expEf + exfhove + expPhaohoa + expPhaohoa2;
        if (this.lvDetail.lv >= 75 && this.lvDetail.lv < 80) {
            exp = (int)(exp * 0.65); // Giảm 35% exp
        } else if (this.lvDetail.lv >= 80) {
            exp = (int)(exp * 0.60); // Giảm 40% exp
        }
        return exp;
    }

    public static int TangEXP = 1;

    public int getX2() {
        if (this.map.isDun()) {
            return 0;
        }
        int x2 = this.x2;
        if (Map.minuteX2Country[this.myCountry] > 0L) {
            x2 = Map.x2Country[this.myCountry];
        }
        return (this.x25Exp > 0) ? 3 : x2;
    }

    public void setX2(final int id) {
     
        this.x2 = (byte) id;
        if (id == 1) {
            this.halfGoldTime = System.currentTimeMillis();
            this.fullGoldTime = 0L;
            this.x25Exp = 0;
            this.minuteExp = 0;
        }
        if (id == 2 || id == 4 || id == 5) {
            this.halfGoldTime = 0L;
            this.fullGoldTime = System.currentTimeMillis();
            this.x25Exp = 0;
            this.minuteExp = 0;
            if (id == 4 || id == 5) {
                this.minuteExp = 60;
                if (id == 5) {
                    this.minuteExp = 180;
                }
                this.x2 = 2;
                try {
                    final Message m = new Message(86);
                    m.dos.writeByte(2);
                    m.dos.writeByte(this.x2);
                    m.dos.writeInt(this.getGoldTime());
                    m.dos.writeUTF(this.getInfoX2());
                    this.sendMessage(m);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (id == 3) {
            this.halfGoldTime = 0L;
            this.fullGoldTime = 0L;
            this.x2 = 0;
            this.x25Exp = 3600;
        }
    }

    public int getGoldTime() {
        if (Map.minuteX2Country[this.myCountry] > 0L) {
            final int minute = (int) ((Map.minuteX2Country[this.myCountry] - System.currentTimeMillis()) / 1000L / 60L);
            return (minute > 0) ? (minute + 1) : 0;
        }
        if (this.x2 == 2) {
            if (this.minuteExp > 0) {
                return this.minuteExp;
            }
            return 1440 - (int) (System.currentTimeMillis() - this.fullGoldTime) / 60000;
        } else {
            if (this.x2 == 1) {
                return 1440 - (int) (System.currentTimeMillis() - this.halfGoldTime) / 60000;
            }
            if (this.x2 == 3) {
                return 1440 - (int) (System.currentTimeMillis() - this.halfGoldTime) / 60000;
            }
            return 0;
        }
    }

    public String getInfoX2() {
        if (Map.minuteX2Country[this.myCountry] > 0L) {
            if (Map.x2Country[this.myCountry] == 1) {
                return "50%";
            }
            if (Map.x2Country[this.myCountry] == 2) {
                return "100%";
            }
            if (Map.x2Country[this.myCountry] == 3) {
                return "150%";
            }
        }
        if (this.x2 == 1) {
            return "50%";
        }
        if (this.x2 == 2) {
            return "100%";
        }
        if (this.x2 == 3) {
            return "150%";
        }
        return "";
    }

    private int countGem() {
        int k = 0;
        for (int i = 0; i < this.listGemitem.length; ++i) {
            if (this.listGemitem[i] > 0) {
                ++k;
            }
        }
        return k;
    }

    private int countGemLock() {
        int k = 0;
        for (int i = 0; i < this.listGemitemLock.length; ++i) {
            if (this.listGemitemLock[i] > 0) {
                ++k;
            }
        }
        return k;
    }

    private int countSpecial() {
        boolean isTicket1 = false;
        boolean isTicket2 = false;
        int n = 0;
        for (int i = 0; i < this.specialItem.size(); ++i) {
            if (!isTicket1 && this.specialItem.get(i).id == 0) {
                ++n;
                isTicket1 = true;
                if (isTicket2) {
                    return n;
                }
            }
            if (!isTicket2 && this.specialItem.get(i).id == 1) {
                ++n;
                isTicket2 = true;
                if (isTicket1) {
                    return n;
                }
            }
        }
        return n;
    }

    public boolean hadGemItem(final int id) {
        return this.listGemitem[id] > 0;
    }

    public boolean hadSpecialItem(final int id) {
        for (int j = 0; j < this.specialItem.size(); ++j) {
            if (this.specialItem.get(j).id == id) {
                return true;
            }
        }
        return false;
    }

    public int countSlotInventory() {
        final int n = this.iItems.size() + this.countPotion() + this.tItems.size() + this.rItems.size() + this.countGemLock() + this.countGem() + this.countSpecial();
        return this.MAX_INVENTORY * this.maxBag - n;
    }

    public boolean isFullInventory() {
        final int n = this.iItems.size() + this.countPotion() + this.tItems.size() + this.rItems.size() + this.countGemLock() + this.countGem() + this.countSpecial();
        return n >= this.MAX_INVENTORY * this.maxBag;
    }

    public short countPotion() {
        short a = 0;
        for (int i = 1; i < this.potions.length; ++i) {
            if (this.potions[i] > 0) {
                ++a;
            }
        }
        return a;
    }

    public void loadConfigMap() {
        if (Map.config == 0) {
            this.resetConfig();
        } else if (Map.config == 1) {
            this.nNearchar = 30000;
        }
    }

    public void resetConfig() {
        if (this.typeConfig == 0) {
            this.nNearchar = 8;
        } else if (this.typeConfig == 1) {
            this.nNearchar = 12;
        } else if (this.typeConfig == 2) {
            this.nNearchar = 20;
        }
    }

    public void config(final int type) {
        if (type == 0) {
            this.nNearchar = 20;
        } else if (type == 1) {
            this.nNearchar = 25;
        } else if (type == 2) {
            this.nNearchar = 30;
        } else if (type == 3) {
            this.nNearchar = 15;
        } else if (type == 4) {
            this.rcvInvite = true;
        } else if (type == 5) {
            this.rcvInvite = false;
        } else if (type == 6) {
            this.slotWearing = (byte) ((this.slotWearing + 1) % 2);
            this.calculateAttrib();
            try {
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                final Message m = MessageCreator.createCharWearingMessage(this, this);
                this.sendMessage(m);
                this.sendToNearPlayer(m);
                this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                MessageCreator.createMsgCharMonster(this, this);
                final Vector<Item> wItems = this.getCharWearing();
                for (int i = 0; i < wItems.size(); ++i) {
                    final Item item = wItems.get(i);
                    if (item.getTemplate().type > 2 && item.getTemplate().type < 8) {
                        this.sendMessage(MessageCreator.createCharWeaponeData(1, item.getTemplate().type, item.getTemplate().style, item.getTemplate().color));
                        return;
                    }
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        if (type < 4) {
            this.typeConfig = (byte) type;
            try {
                final Message msgCfg = new Message(104);
                msgCfg.dos.writeByte(this.typeConfig);
                msgCfg.dos.writeByte(this.autoSkill);
                this.sendMessage(msgCfg);
            } catch (final Exception ex) {
            }
        }
    }

    public void putIDItem2IDGen(final Vector<Item> iItems) {
        try {
            for (final Item item : iItems) {
                RealController.intance.idGen.putID(item.id, 3, "return id item 2 IDGEN");
            }
        } catch (final Exception ex) {
        }
    }

    public void putIDSpecialItem2IDGen() {
        try {
            for (final ShopTemplate item : this.specialItem) {
                RealController.intance.idGen.putID(item.realId, 7, "return id item 2 IDGEN");
            }
        } catch (final Exception ex) {
        }
    }

    public void putIDChar2IDGen() {
        try {
            RealController.intance.idGen.putID(this.id, 0, "return id player 2 IDGEN");
        } catch (final Exception ex) {
        }
    }

    public void putIDPotion2IDGen() {
        try {
            for (final Item item : this.iItems) {
                RealController.intance.idGen.putID(item.id, 3, "return id item 2 IDGEN");
            }
        } catch (final Exception ex) {
        }
    }

    public void doAddPotionTrad2Inventory() {
        String info = String.valueOf(this.charname) + " chuyển_";
        for (int i = 0; i < this.tPotions.length; ++i) {
            if (this.tPotions[i] > 0) {
                info = String.valueOf(info) + this.tPotions[i] + "_" + LoginHandler.PORTION_NAME[i] + " n ";
                final int[] potions = this.potions;
                final int n = i;
                potions[n] -= this.tPotions[i];
                if (this.potions[i] < 0) {
                    this.potions[i] = 0;
                }
            }
        }
        info = String.valueOf(info) + " >> " + this.userTrade.get(0).charname + " chuyển_";
        for (int i = 0; i < this.userTrade.get(0).tPotions.length; ++i) {
            if (this.userTrade.get(0).tPotions[i] > 0) {
                info = String.valueOf(info) + this.userTrade.get(0).tPotions[i] + "_" + LoginHandler.PORTION_NAME[i] + " n ";
                final int[] potions2 = this.userTrade.get(0).potions;
                final int n2 = i;
                potions2[n2] -= this.userTrade.get(0).tPotions[i];
                if (this.userTrade.get(0).potions[i] < 0) {
                    this.userTrade.get(0).potions[i] = 0;
                }
            }
        }
        this.tPotions = null;
        this.tPotions = new int[162];
        this.userTrade.get(0).tPotions = null;
        this.userTrade.get(0).tPotions = new int[162];
        info = String.valueOf(info) + " ||| " + this.charname + " nhận_";
        for (int i = 0; i < this.rPotions.length; ++i) {
            if (this.rPotions[i] > 0) {
                info = String.valueOf(info) + this.rPotions[i] + "_" + LoginHandler.PORTION_NAME[i] + " n ";
                final int[] potions3 = this.potions;
                final int n3 = i;
                potions3[n3] += this.rPotions[i];
            }
        }
        info = String.valueOf(info) + " >> " + this.userTrade.get(0).charname + " nhận_";
        for (int i = 0; i < this.userTrade.get(0).rPotions.length; ++i) {
            if (this.userTrade.get(0).rPotions[i] > 0) {
                info = String.valueOf(info) + this.userTrade.get(0).rPotions[i] + "_" + LoginHandler.PORTION_NAME[i] + " n ";
                final int[] potions4 = this.userTrade.get(0).potions;
                final int n4 = i;
                potions4[n4] += this.userTrade.get(0).rPotions[i];
            }
        }
        this.rPotions = null;
        this.rPotions = new int[162];
        this.userTrade.get(0).rPotions = null;
        this.userTrade.get(0).rPotions = new int[162];
        try {
            Database.instance.saveLogTradeItem(this.charname, this.userTrade.elementAt(0).charname, info);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void doAddItemTrad2Inventory() {
        String info = String.valueOf(this.charname) + " chuyển_";
        for (int i = 0; i < this.tItems.size(); ++i) {
            final Item item = this.tItems.elementAt(i);
            this.iItems.remove(item);
            this.ALL_ID_USE.remove(item.id);
            info = String.valueOf(info) + item.getTemplate().name + "_" + item.plus + "_" + item.getAttribute() + "_" + item.getDBInfo() + " | ";
        }
        info = String.valueOf(info) + " >> " + this.userTrade.get(0).charname + " chuyển_";
        for (int i = 0; i < this.userTrade.get(0).tItems.size(); ++i) {
            final Item item = this.userTrade.get(0).tItems.elementAt(i);
            this.userTrade.get(0).iItems.remove(item);
            this.userTrade.get(0).ALL_ID_USE.remove(item.id);
            info = String.valueOf(info) + item.getTemplate().name + "_" + item.plus + item.getAttribute() + "_" + item.getDBInfo() + " | ";
        }
        this.tItems.removeAllElements();
        this.userTrade.get(0).tItems.removeAllElements();
        for (int i = 0; i < this.rItems.size(); ++i) {
            final Item item = this.rItems.elementAt(i);
            item.owner = this.charDBID;
            if (item.dbowner != 0) {
                item.dbowner = this.charDBID;
            }
            item.id = this.getIDItem();
            item.place = 0;
            this.iItems.add(item);
        }
        for (int i = 0; i < this.userTrade.get(0).rItems.size(); ++i) {
            final Item item = this.userTrade.get(0).rItems.elementAt(i);
            item.owner = this.userTrade.get(0).charDBID;
            if (item.dbowner != 0) {
                item.dbowner = this.userTrade.get(0).charDBID;
            }
            item.id = this.userTrade.get(0).getIDItem();
            item.place = 0;
            this.userTrade.get(0).iItems.add(item);
        }
        try {
            Database.instance.saveLogTradeItem(this.charname, this.userTrade.elementAt(0).charname, info);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        this.rItems.removeAllElements();
        this.userTrade.get(0).rItems.removeAllElements();
        this.userTrade.get(0).finishTrade = false;
        this.userTrade.get(0).lockTrade = false;
        this.finishTrade = false;
        this.lockTrade = false;
        this.doAddPotionTrad2Inventory();
    }

    public boolean checkExistItem(final int idTemplate) {
        try {
            for (int i = 0; i < this.iItems.size(); ++i) {
                final Item it = this.iItems.get(i);
                if (it.getTemplate().id == idTemplate) {
                    return true;
                }
            }
        } catch (final Exception ex) {
        }
        return false;
    }

    public Item getLenhBaiTranYeu() {
        if (this.lvDetail.lv < 50) {
            return null;
        }
        int idtemplate = 678;
        if (this.lvDetail.lv >= 60 && this.lvDetail.lv < 70) {
            idtemplate = 679;
        }
        if (this.lvDetail.lv > 70) {
            idtemplate = 680;
        }
        try {
            for (int i = 0; i < this.iItems.size(); ++i) {
                final Item it = this.iItems.get(i);
                if (it.getTemplate().id == idtemplate) {
                    return it;
                }
            }
        } catch (final Exception ex) {
        }
        return null;
    }

    public int isExistInvector(final Vector<Item> v, final short itemID) {
        for (int i = 0; i < v.size(); ++i) {
            final Item c = v.elementAt(i);
            if (c.id == itemID) {
                return i;
            }
        }
        return -1;
    }

    public int isExistInvector(final short itemID, final Vector<GemItem> v) {
        for (int i = 0; i < v.size(); ++i) {
            final GemItem c = v.elementAt(i);
            if (c.realId == itemID) {
                return i;
            }
        }
        return -1;
    }

    public void removeGemItem(final short id) {
        for (int i = 0; i < this.gemItem.size(); ++i) {
            if (this.gemItem.get(i).realId == id) {
                this.gemItem.remove(i);
                return;
            }
        }
    }

    public Vector<ItemSell> getAllItemSearch() {
        if (!this.searchChar.equals("")) {
            return this.getAllItemSearchBySell(this.searchChar);
        }
        return this.getListItemSearch(this.lvSearch, this.typeItemSearch, this.colortItemSearch, this.phamSearch, this.plusSearch);
    }

    public Vector<ItemSell> getListItemSearch(final short level, final short typeItem, final short colorItem, final short pham, final short cong) {
        this.allSearchItem.removeAllElements();
        try {
            this.lvSearch = level;
            this.typeItemSearch = (byte) typeItem;
            this.colortItemSearch = (byte) colorItem;
            this.phamSearch = (byte) pham;
            this.plusSearch = (byte) cong;
            final Vector<ItemSell> itemSell = Market.getListItemSearch(level, typeItem, colorItem, pham, cong, this.inCountry);
            if (itemSell.size() > 0) {
                final Vector<ItemSell> removeItem = new Vector<ItemSell>();
                for (int i = 0; i < itemSell.size(); ++i) {
                    final ItemSell item = itemSell.get(i);
                    if (item.isExpire()) {
                        removeItem.add(item);
                    }
                }
                while (removeItem.size() > 0) {
                    final ItemSell item2 = removeItem.remove(0);
                    if (ItemMarket.HASH_ITEM_SELL.get(item2.id) != null) {
                        Market.isItemExpired(item2);
                    } else {
                        itemSell.remove(item2);
                    }
                }
            }
            this.allSearchItem = itemSell;
        } catch (final Exception ex) {
        }
        return this.allSearchItem;
    }

    public Vector<ItemSell> getAllItemSearchBySell(final String charsell) {
        this.searchChar = charsell.trim().toLowerCase();
        System.out.println("getAllItemSearchBySell Char: " + this.searchChar);
        this.allSearchItem.removeAllElements();
        final Vector<ItemSell> itemSell = Market.getListItemSearchByName(charsell.toLowerCase().trim());
        if (itemSell.size() > 0 && !itemSell.get(0).canbuy(this.inCountry)) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể mua vật phẩm của " + charsell + " khi bạn ở trên lãnh thổ của " + charsell, ""));
            return new Vector<ItemSell>();
        }
        return this.allSearchItem = itemSell;
    }

    public Vector<ItemSell> getAllListItemBid() {
        this.allItemBid.removeAllElements();
        synchronized (ItemMarket.ALL_CHAR_SELL) {
            final CharSellItem c = Market.getCharSell(this.charname);
            this.allItemBid = c.getListItemBid();
            //monitorexit(ItemMarket.ALL_CHAR_SELL);
        }
        return this.allItemBid;
    }

    public Vector<ItemSell> getAllistNewItemSell() {
        this.allNewItem.removeAllElements();
        final Vector<ItemSell> it = new Vector<ItemSell>();
        int size = 50;
        Vector<ItemSell> itemsell = ItemMarket.ALL_ITEM_SELL_THANH_LONG;
        if (this.inCountry == 1) {
            itemsell = ItemMarket.ALL_ITEM_SELL_HAC_HO;
        }
        final Vector<ItemSell> vector;
        synchronized (vector = ((this.inCountry == 1) ? ItemMarket.ALL_ITEM_SELL_HAC_HO : ItemMarket.ALL_ITEM_SELL_THANH_LONG)) {
            if (size > itemsell.size()) {
                size = itemsell.size();
            }
            int i = 0;
            int j = 0;
            final Vector<ItemSell> itExpire = new Vector<ItemSell>();
            while (i < size) {
                final ItemSell item = itemsell.get(j);
                if (!item.isExpire()) {
                    it.add(itemsell.get(i));
                    ++i;
                } else {
                    itExpire.add(item);
                }
                if (++j >= itemsell.size()) {
                    break;
                }
            }
            for (i = 0, i = 0; i < itExpire.size(); ++i) {
                Market.isItemExpired(itExpire.get(i));
            }
            this.allNewItem = it;
            //monitorexit(vector);
        }
        return it;
    }

    public Vector<ItemSell> getAllistItemSell() {
        synchronized (ItemMarket.ALL_CHAR_SELL) {
            final CharSellItem c = ItemMarket.ALL_CHAR_SELL.get(this.charname);
            if (c != null) {
                final Vector<ItemSell> allItems = c.getAllItems();
                //monitorexit(ItemMarket.ALL_CHAR_SELL);
                return allItems;
            }
            final Vector<ItemSell> vector = new Vector<ItemSell>();
            //monitorexit(ItemMarket.ALL_CHAR_SELL);
            return vector;
        }
    }

    public Vector<ItemSell> getAllistItemExpire() {
        synchronized (ItemMarket.ALL_CHAR_SELL) {
            final CharSellItem c = ItemMarket.ALL_CHAR_SELL.get(this.charname);
            if (c != null) {
                final Vector<ItemSell> allItemExpire = c.getAllItemExpire();
                //monitorexit(ItemMarket.ALL_CHAR_SELL);
                return allItemExpire;
            }
            final Vector<ItemSell> vector = new Vector<ItemSell>();
            //monitorexit(ItemMarket.ALL_CHAR_SELL);
            return vector;
        }
    }

    public GemItem getItemFormVector(final short id, final Vector<GemItem> v) {
        for (int i = 0; i < v.size(); ++i) {
            final GemItem item = v.elementAt(i);
            if (item.realId == id) {
                return item;
            }
        }
        return null;
    }

    public int countLKD() {
        int n = 0;
        for (int i = 0; i < this.gemItemImbue.size(); ++i) {
            final GemItem item = this.gemItemImbue.elementAt(i);
            if ((item.idGemTemplate > 7 && item.idGemTemplate < 12) || item.idGemTemplate == 158 || item.idGemTemplate == 157 || item.idGemTemplate == 66 || item.idGemTemplate == 245) {
                ++n;
            }
        }
        return n;
    }

    public int countBaoHiem() {
        int n = 0;
        for (int i = 0; i < this.gemItemImbue.size(); ++i) {
            final GemItem item = this.gemItemImbue.elementAt(i);
            if (item.idGemTemplate < 5) {
                ++n;
            }
        }
        return n;
    }

    public int getTotalGem() {
        int total = 0;
        for (int i = 0; i < this.listGemitem.length; ++i) {
            total += this.listGemitem[i];
        }
        return total;
    }

    public String getInfoGemLock() {
        String info = "";
        for (int i = 0; i < this.listGemitemLock.length; ++i) {
            if (this.listGemitemLock[i] > 0) {
                info = String.valueOf(info) + this.listGemitemLock[i] + " " + Map.gemTemplate[i].name + " ";
            }
        }
        return info;
    }

    public int getAllGem() {
        int total = 0;
        for (int i = 0; i < this.listGemitem.length; ++i) {
            if (this.listGemitem[i] > 0 && this.listGemitem[i] - this.listGemitemSell[i] > 0) {
                ++total;
            }
        }
        return total;
    }

    public int getAllGemLock() {
        int total = 0;
        for (int i = 0; i < this.listGemitemLock.length; ++i) {
            if (this.listGemitemLock[i] > 0) {
                ++total;
            }
        }
        return total;
    }

    public int getAllSP() {
        int total = 0;
        for (int i = 0; i < this.listSpItem.length; ++i) {
            if (this.listSpItem[i] > 0) {
                total += this.listSpItem[i];
            }
        }
        return total;
    }

    public int getAllKindSP() {
        int total = 0;
        for (int i = 0; i < this.listSpItem.length; ++i) {
            if (this.listSpItem[i] > 0) {
                ++total;
            }
        }
        return total;
    }

    public void removeItemOutVector(final short id, final Vector<GemItem> v) {
        for (int i = 0; i < v.size(); ++i) {
            final GemItem item = v.elementAt(i);
            if (item.realId == id) {
                v.removeElementAt(i);
                this.ALL_ID_USE.remove(item.realId);
                return;
            }
        }
    }

    public Item getItemFormVector(final Vector<Item> v, final short id) {
        for (int i = 0; i < v.size(); ++i) {
            final Item item = v.elementAt(i);
            if (item.id == id) {
                return item;
            }
        }
        return null;
    }

    public Item getItemInventotyByIDTemplate(final int idtemplate, final int lock) {
        for (int i = 0; i < this.iItems.size(); ++i) {
            final Item item = this.iItems.elementAt(i);
            if (item.tempateID == idtemplate && (lock == -1 || item.lock == lock)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public short getLevel() {
        return (short) this.lvDetail.lv;
    }

    public void doSendProperties() {
        try {
            final Message m = new Message(32);
            m.dos.writeShort(this.id);
            m.dos.writeByte(2);
            m.dos.writeUTF("hp");
            m.dos.writeInt(this.hp);
            m.dos.writeUTF("mp");
            m.dos.writeInt(this.mp);
            m.dos.writeByte(this.lvDetail.lv);
            m.dos.writeShort(this.lvDetail.percent);
            this.sendMessage(m);
            m.cleanup();
        } catch (final Exception ex) {
        }
    }

    private void getInfoDefendItem() {
    }

    public String getinfoSkillClan(final int id, final int type) {
        final String st = "";
        if (type == 0) {
            if (this.skillClan[id] > 0) {
                return String.valueOf(Skill_Clan.SKILL_DECRIPTION_CLAN[id]) + Skill_Clan.SKILL_CLAN[id][this.skillLvClan[id]];
            }
        } else if (type == 1 && this.skillClan[id] > 0) {
            return String.valueOf(Skill_Clan.SKILL_DECRIPTION_CLAN_MEMBER[id - 10]) + Skill_Clan.SKILL_CLAN_MEMBER[id - 10][this.skillLvClan[id]];
        }
        return st;
    }

    public int getEffSkillClan(final int idSkill) {
        if (this.skillClan[idSkill] <= 0) {
            return 0;
        }
        return Skill_Clan.getEffectSkill((byte) idSkill, this.skillLvClan[idSkill], (byte) 0);
    }

    public int getEffSkillClanMember(final int idSkill) {
        if (this.skillClan[idSkill + 10] <= 0) {
            return 0;
        }
        return Skill_Clan.getEffectSkill((byte) idSkill, this.skillLvClan[idSkill + 10], (byte) 1);
    }

    private void checkWearing() {
    }

    public void calculateAttrib() {
        this.checkWearing();
        this.resetNguHanh();
        this.checkKickAtt5();
        this.checkKick5AttAnimal();
        this.coolDown = new short[CharManager.SKILL_COOLDOWN[this.charClass].length][11];
        for (int i = 0; i < this.coolDown.length; ++i) {
            for (int j = 0; j < this.coolDown[i].length; ++j) {
                this.coolDown[i][j] = (short) (CharManager.SKILL_COOLDOWN[this.charClass][i][j] * 100);
            }
        }
        if (this.xichtho_thienlyma == 2) {
            this.upAbility[0] = 3;
            this.upAbility[1] = 3;
            this.upAbility[2] = 3;
            this.upAbility[3] = 3;
            this.upAbility[4] = 3;
            this.speed = 6;
        } else if (this.xichtho_thienlyma == 1) {
            this.upAbility[0] = 0;
            this.upAbility[1] = 0;
            this.upAbility[2] = 0;
            this.upAbility[3] = 0;
            this.upAbility[4] = 0;
        }
        if (this.rideHorse > 0) {
            this.speed = 6;
        }
        final Vector<Item> wItems = this.getCharWearing();
        final byte[] att = new byte[18];
        for (int k = 0; k < wItems.size(); ++k) {
            final Item item = wItems.get(k);
            for (int l = 0; l < 18; ++l) {
                final byte[] array = att;
                final int n = l;
                array[n] += (byte) (item.atb[l + 10] + item.getValueUpByVuKhiThoiTrang(item.atb[l + 10], this.itemVukhiThoiTrang));
            }
        }
        this.abilityKham = att;
        this.updateSkillBuffPassive();
        this.calculateMaxHPMP();
        this.calculateAttack();
        this.calculateDefend();
        this.calculateAccurate();
        this.calculateDodge();
        this.calculateCritical();
    }

    public void updateSkillBuffPassive() {
        switch (this.charClass) {
            case 0: {
                this.buffPass[0] = CharManager.SKILL_DAM_PERCENT[this.charClass][4][this.skill[4] + this.addMoreLevelSkill[4]];
                break;
            }
            case 1: {
                this.buffPass[0] = CharManager.SKILL_DAM_PERCENT[this.charClass][5][this.skill[5] + this.addMoreLevelSkill[5]];
                break;
            }
            case 2: {
                this.buffPass[0] = CharManager.SKILL_DAM_PERCENT[this.charClass][5][this.skill[5] + this.addMoreLevelSkill[5]];
                break;
            }
            case 3: {
                this.buffPass[0] = CharManager.SKILL_DAM_PERCENT[this.charClass][5][this.skill[5] + this.addMoreLevelSkill[5]];
                break;
            }
            case 4: {
                this.buffPass[0] = CharManager.SKILL_DAM_PERCENT[this.charClass][5][this.skill[5] + this.addMoreLevelSkill[5]];
                break;
            }
        }
    }

    @Override
    public int getBuffDefCB(final int type, final boolean isMons) {
        if (this.percentBuff[0] <= 0) {
            return 0;
        }
        if (type == 0) {
            return this.percentBuff[0];
        }
        return CharManager.SKILL_DAM_PERCENT[1][4][this.skill[4] + this.addMoreLevelSkill[4]] * this.defend_magic / 100;
    }

    public void upPercentAtribute(Char c, int bidEff, int id, int lv, Char cBuff) {
        if (bidEff == 19) {
            return;
        }
        Message m = null;
        int percen = 0;
        try {
            switch (bidEff) {
                case 20:
                    if (cBuff.charClass == 1 && c.id == cBuff.id) {
                        c.percentBuff[0] = 0;
                        c.percentBuffPlayer[0] = 0;
                        percen = CharManager.SKILL_DAM_PERCENT[1][4][this.skill[4] + this.addMoreLevelSkill[4]];
                        c.percentBuff[0] = percen * this.defend_physic / 100;
                        c.percentBuffPlayer[0] = percen * this.defendVatPlayer / 100;
                        if (percen * 100 % 100 >= 5) {
                            c.percentBuff[0] = c.percentBuff[0] + 1;
                            c.percentBuffPlayer[0] = c.percentBuffPlayer[0] + 1;
                        }
                        c.sendMessage(MessageCreator.createMainCharInfoMessage(c));
                        c.sendToNearPlayer(MessageCreator.createNew_HMP_Message(c, 0));
                    }
                    break;
                case 25:
                    if (cBuff.charClass == 2 && c.id == cBuff.id) {
                        c.percentBuff[2] = 0;
                        c.percentBuff[1] = 0;
                        percen = CharManager.SKILL_DAM_PERCENT[2][id][lv + this.addMoreLevelSkill[lv]];
                        c.percentBuff[2] = percen * this.maxhp / 100;
                        c.percentBuff[1] = percen * this.maxmp / 100;
                        c.sendMessage(MessageCreator.createMainCharInfoMessage(c));
                        c.sendToNearPlayer(MessageCreator.createNew_HMP_Message(c, 0));
                    }
                    break;
                case 27:
                    if (System.currentTimeMillis() - cBuff.timeLastUseSkills[6] < cBuff.coolDown[6][cBuff.skill[6]]) {
                        return;
                    }
                    if (cBuff.charClass != 2) {
                        if (cBuff.xu < 1000000L) {
                            return;
                        }
                        Database.instance.saveOrtherLog("tob_other_log", cBuff.charname, "hack hs " + cBuff.xu + "_1000000", "hhs");
                        cBuff.xu -= 1000000L;
                        if (cBuff.xu < 0L) {
                            cBuff.xu = 0L;
                        }
                        cBuff.sendMessage(MessageCreator.createCharInventoryMessage(cBuff, 0));
                    }
                    cBuff.timeLastUseSkills[6] = System.currentTimeMillis();
                    c.percentBuff[2] = 0;
                    percen = CharManager.SKILL_DAM_PERCENT[2][6][cBuff.skill[6] + cBuff.addMoreLevelSkill[6]];
                    c.hp = c.maxhp * percen / 100;
                    c.sendMessage(MessageCreator.createMainCharInfoMessage(c));
                    c.sendToNearPlayer(MessageCreator.createNew_HMP_Message(c, 0));
                    if (c.petUsing != null && !c.petUsing.isPetTool() && c.petUsing.getIdEffPet() > -1
                            && c.addEffBuff(c.petUsing.getIdEffPet(), System.currentTimeMillis() + 320000000L, EffectBuff.BY_ACTOR, 0) != null) {
                        c.sendEffToChar(c);
                    }
                    sendMessage(m);
                    break;
            }
        } catch (Exception exception) {
        }
    }

    private void calculateCritical() {
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        Vector<Item> wItems = getCharWearing();
        int crit1 = 0;
        int crit2 = 0;
        if (this.charClass == 4) {
            crit1 = (this.luck + getEffSkillClan(3)) / 5;
        }
        for (Item item : wItems) {
            if (item.level > this.lvDetail.lv) {
                continue;
            }
            if ((item.getTemplate()).type == 9 || (item.getTemplate()).type == 11 || (item.getTemplate()).type == 12 || ((item.getTemplate()).type > 2 && (item.getTemplate()).type < 8)) {
                crit2 += item.atb[4] + item.getValueUpByVuKhiThoiTrang(item.atb[4], this.itemVukhiThoiTrang);
            }
        }
        this.critical = (short) (crit1 + this.newAtb[7] + crit2 + ((animalRide != null) ? (animalRide.att[11] + animalRide.attUp[11]) : 0));
        long pc = 0L;
        if (this.timeUp5Attribute > 0L) {
            pc += 5L;
            this.critical = (short) (int) (this.critical + this.critical * pc / 100L);
        }
        int atkTubinh = 0;
        for (int i = 0; i < this.tubinh.size(); i++) {
            atkTubinh += ((TuBinhTemplate) this.tubinh.get(i)).getCrit();
        }
        this.critical = (short) (this.critical + atkTubinh);
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null) {
                long noiluc = c.getNoiLuc();
                this.critical = (short) (int) (this.critical * noiluc / 10L);
            }
        }
        if(isSaTang()){
            int level = this.getLevelCaiTrang();

            if(level >= 3 && level < 8){
                this.critical = (short) (int) (this.critical * 1.01); // +1%
            } else if(level >= 8 && level < 14){
                this.critical = (short) (int) (this.critical * 1.02); // +2%
            } else if(level == 14){
                this.critical = (short) (int) (this.critical * 1.04); // +4%
            } else if(level == 15){
                this.critical = (short) (int) (this.critical * 1.05); // +5%
            }

        }
    }

    public int getXLuaLan(final int cat) {
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        int x = 1;
        if (petUsing != null) {
            x += petUsing.getXLuaLan();
        }
        if (this.wModel != null) {
            x += this.wModel.getLuaLan();
        }
        if (animalRide != null) {
            if (animalRide.isLan()) {
                if (cat == 1) {
                    x += animalRide.getLuaLan();
                }
            } else {
                x += animalRide.getLuaLan();
            }
        }
        if (this.itemVukhiThoiTrang != null) {
            x += this.itemVukhiThoiTrang.getLuaLan();
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c2 != null) {
                final long noiluc = c2.getNoiLuc();
                x = (int) (x * noiluc / 10L);
            }
        }
        return x;
    }

    public int getXBangLan(final int cat) {
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        int x = 1;
        if (petUsing != null) {
            x += petUsing.getXBangLan();
        }
        if (this.wModel != null) {
            x += (int) this.wModel.getBangLan();
        }
        if (animalRide != null) {
            if (animalRide.isLan()) {
                if (cat == 1) {
                    x += animalRide.getBangLan();
                }
            } else {
                x += animalRide.getBangLan();
            }
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c2 != null) {
                final long noiluc = c2.getNoiLuc();
                x = (int) (x * noiluc / 10L);
            }
        }
        return x;
    }

    public int getPCXbangSetDocLuaLan() {
        if (this.wModel != null) {
            return this.wModel.getPCXbangSetDocLuaLan();
        }
        return 0;
    }

    public int getXSetLan(final int cat) {
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        int x = 1;
        if (petUsing != null) {
            x += petUsing.getXSetLan();
        }
        if (this.wModel != null) {
            x += this.wModel.getSetLan();
        }
        if (animalRide != null) {
            if (animalRide.isLan()) {
                if (cat == 1) {
                    x += animalRide.getSetLan();
                }
            } else {
                x += animalRide.getSetLan();
            }
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c2 != null) {
                final long noiluc = c2.getNoiLuc();
                x = (int) (x * noiluc / 10L);
            }
        }
        return x;
    }

    public int getXDocLan(final int cat) {
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        int x = 1;
        if (petUsing != null) {
            x += petUsing.getXDocLan();
        }
        if (this.wModel != null) {
            x += this.wModel.getDocLan();
        }
        if (animalRide != null) {
            if (animalRide.isLan()) {
                if (cat == 1) {
                    x += animalRide.getDocLan();
                }
            } else {
                x += animalRide.getDocLan();
            }
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c2 != null) {
                final long noiluc = c2.getNoiLuc();
                x = (int) (x * noiluc / 10L);
            }
        }
        return x;
    }

    private void calculateMaxHPMP() {
        this.secondHealth = 0;
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        for (int i = 0; i < this.wItems[this.slotWearing].length; ++i) {
            final Item item = this.wItems[this.slotWearing][i];
            if (item != null) {
                if (item.getType() == 12 && item.level <= this.lvDetail.lv) {
                    this.secondHealth += item.atb[5];
                }
                if (item.getType() == 19 && item.level <= this.lvDetail.lv) {
                    this.secondHealth += item.newAtb[1] * 1000;
                }
            }
        }
        switch (this.charClass) {
            case 0: {
                this.maxhp = (((petUsing != null) ? petUsing.getHealth() : 0) + this.health + this.getEffSkillClan(3) + this.upAbility[0] + this.abilityKham[3] + this.secondHealth + ((animalRide != null) ? animalRide.att[6] : 0)) * 80;
                this.maxmp = (((petUsing != null) ? petUsing.getspirit() : 0) + this.spirit + this.getEffSkillClan(3) + this.upAbility[1] + this.abilityKham[2] + ((animalRide != null) ? animalRide.att[8] : 0)) * 20;
                break;
            }
            case 1: {
                this.maxhp = (((petUsing != null) ? petUsing.getHealth() : 0) + this.health + this.getEffSkillClan(3) + this.secondHealth + this.abilityKham[3] + this.upAbility[0] + ((animalRide != null) ? animalRide.att[6] : 0)) * 70;
                this.maxmp = (((petUsing != null) ? petUsing.getspirit() : 0) + this.spirit + this.getEffSkillClan(3) + this.upAbility[1] + this.abilityKham[2] + ((animalRide != null) ? animalRide.att[8] : 0)) * 20;
                break;
            }
            case 2: {
                this.maxhp = (((petUsing != null) ? petUsing.getHealth() : 0) + this.health + this.getEffSkillClan(3) + this.secondHealth + this.abilityKham[3] + this.upAbility[0] + ((animalRide != null) ? animalRide.att[6] : 0)) * 60;
                this.maxmp = (((petUsing != null) ? petUsing.getspirit() : 0) + this.spirit + this.getEffSkillClan(3) + this.upAbility[1] + this.abilityKham[2] + ((animalRide != null) ? animalRide.att[8] : 0)) * 50;
                this.maxmp += this.maxmp * this.buffPass[0] / 100;
                break;
            }
            case 3: {
                this.maxhp = (((petUsing != null) ? petUsing.getHealth() : 0) + this.health + this.getEffSkillClan(3) + this.upAbility[0] + this.secondHealth + this.abilityKham[3] + ((animalRide != null) ? animalRide.att[6] : 0)) * 70;
                this.maxmp = (((petUsing != null) ? petUsing.getspirit() : 0) + this.spirit + this.getEffSkillClan(3) + this.upAbility[1] + this.abilityKham[2] + ((animalRide != null) ? animalRide.att[8] : 0)) * 20;
                break;
            }
            case 4: {
                this.maxhp = (((petUsing != null) ? petUsing.getHealth() : 0) + this.health + this.getEffSkillClan(3) + this.secondHealth + this.upAbility[0] + this.abilityKham[3] + ((animalRide != null) ? animalRide.att[6] : 0)) * 60;
                this.maxmp = (((petUsing != null) ? petUsing.getspirit() : 0) + this.spirit + this.getEffSkillClan(3) + this.upAbility[1] + this.abilityKham[2] + ((animalRide != null) ? animalRide.att[8] : 0)) * 20;
                break;
            }
        }
        this.maxhp += ((animalRide != null) ? animalRide.att[3] : 0) + this.newAtb[0] * 1000;
        this.maxmp += ((animalRide != null) ? animalRide.att[4] : 0) + this.newAtb[1] * 1000;
        this.maxhp += this.getEffSkillClan(2);
        if (this.rideHorse == 1) {
            this.maxhp += 700;
            this.maxmp += 700;
            this.speed = 6;
        } else if (this.rideHorse == 2) {
            this.maxhp += 1000;
            this.maxmp += 1000;
        }
        if (this.wModel != null) {
            this.maxhp += this.wModel.getHp();
            this.maxmp += this.wModel.getMp();
        }
        


        long pcUphp = 0L;
        long pcUpmp = 0L;
        if (this.fullSet15 >= 10) {
            pcUphp = 12L;
            pcUpmp = 12L;
        } else if (this.fullSet15 >= 6) {
            pcUphp = 8L;
            pcUpmp = 8L;
        }
        if (petUsing != null) {
            pcUphp += petUsing.getCuongTrang();
            pcUpmp += petUsing.getCuongThan();
        }
        this.maxhp += (int) (this.maxhp * pcUphp / 100L);
        this.maxmp += (int) (this.maxmp * pcUpmp / 100L);
        int atkTubinh = 0;
        int atkTubinh2 = 0;
        for (int j = 0; j < this.tubinh.size(); ++j) {
            atkTubinh += this.tubinh.get(j).getHP();
            atkTubinh2 += this.tubinh.get(j).getMP();
        }
        this.maxhp += atkTubinh;
        this.maxmp += atkTubinh2;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c2 != null) {
                final long noiluc = c2.getNoiLuc();
                this.maxhp = (int) (this.maxhp * noiluc / 10L);
                this.maxmp = (int) (this.maxmp * noiluc / 10L);
            }
        }
        // cải tạo cải trang
        // buff hp
        if (this.wModel != null && (this.wModel.isCaiTaoMiNuong() || this.wModel.isCaiTaoSonTinh())) {
            // Lấy phần trăm cải tạo từ wModel
            int percentCaiTao = this.wModel.itemModel.CaiTao; // Giả sử có method này
            
            // Tính toán bonus dựa trên phần trăm cải tạo
            double hpBonus = this.maxhp * (percentCaiTao / 100.0);
            double mpBonus = this.maxmp * (percentCaiTao / 100.0);
            
            this.maxhp += hpBonus;
            this.maxmp += mpBonus;
           
        }
        if (isBatGioi()) {
            int level = this.getLevelCaiTrang();
        if (level >= 3 && level < 8) {
                this.maxhp += 1000;      // Level 3-7: chỉ +1000
            } else if (level >= 8 && level < 14) {
                this.maxhp += 3000;      // Level 8-13: chỉ +3000
            } else if (level == 14) {
                this.maxhp += 5000;      // Level 14: chỉ +5000
            } else if (level == 15) {
                this.maxhp += 10000;     // Level 15: chỉ +10000
            }
        }
        if (this.hp > this.maxhp) {
            this.hp = this.maxhp;
        }
        if (this.mp > this.maxmp) {
            this.mp = this.maxmp;
        }
    }

    private void calculateDodge() {
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        int dogde1 = this.agitity + this.getEffSkillClan(3) + this.upAbility[3] + this.abilityKham[1] + ((animalRide != null) ? animalRide.att[7] : 0);
        dogde1 += ((petUsing != null) ? petUsing.getagitity() : 0);
        int dogde2 = 0;
        for (int i = 0; i < this.wItems[this.slotWearing].length; ++i) {
            final Item item = this.wItems[this.slotWearing][i];
            if (item != null) {
                if (item.level <= this.lvDetail.lv) {
                    dogde2 += item.atb[2];
                }
            }
        }
        this.dodge = (short) ((dogde1 + dogde2 + this.basic) / 7 * 2);
        int atkTubinh = 0;
        for (int j = 0; j < this.tubinh.size(); ++j) {
            atkTubinh += this.tubinh.get(j).getDog();
        }
        this.dodge += (short) atkTubinh;
        if (this.dodge >= 300) {
            this.dodge = 299;
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c2 != null) {
                final long noiluc = c2.getNoiLuc();
                this.dodge = (short) (this.dodge * noiluc / 10L);
            }
        }
    }

    private void calculateAccurate() {
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        int accurate1 = this.agitity + this.getEffSkillClan(3) + this.upAbility[3] + this.abilityKham[1] + ((animalRide != null) ? animalRide.att[7] : 0);
        accurate1 += ((petUsing != null) ? petUsing.getagitity() : 0);
        int accurate2 = 0;
        for (int i = 0; i < this.wItems[this.slotWearing].length; ++i) {
            final Item item = this.wItems[this.slotWearing][i];
            if (item != null) {
                if (item.level <= this.lvDetail.lv) {
                    accurate2 += item.atb[3] + item.getValueUpByVuKhiThoiTrang(item.atb[3], this.itemVukhiThoiTrang);
                }
            }
        }
        this.accurate = (short) ((accurate1 + accurate2 + this.basic) / 7);
        if (this.accurate >= 100) {
            this.accurate = 99;
        }
        this.accurate = (short) (this.accurate * 700 / 100);
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c2 != null) {
                final long noiluc = c2.getNoiLuc();
                this.accurate = (short) (this.accurate * noiluc / 10L);
            }
        }
    }

    public byte getRing() {
        return this.idRing = (byte) ((this.idRing == 0) ? 1 : 0);
    }

    private void calculateDefend() {
        this.setlan = 0;
        this.doclan = 0;
        this.banglan = 0;
        this.lualan = 0;
        this.baokich = 0;
        this.khangbanglan = 0;
        this.khangsetlan = 0;
        this.khangdoclan = 0;
        this.khanglualan = 0;
        this.hoihp = 0;
        this.hoimp = 0;
        this.tyleRotItem = 0;
        this.tylerotXu = 0;
        this.tanphe = 0;
        this.hutmau = 0;
        this.lamthinh = 0;
        this.tyleRotItem = 0;
        this.tylerotXu = 0;
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        int defend1 = ((petUsing != null) ? petUsing.getagitity() : 0) + this.agitity + this.getEffSkillClan(3) + this.newAtb[3] + this.upAbility[3] + this.abilityKham[1] + ((animalRide != null) ? animalRide.att[7] : 0);
        int defen4 = this.agitity + this.getEffSkillClan(3) + this.newAtb[3] + this.upAbility[2] + this.abilityKham[1] + ((animalRide != null) ? animalRide.att[8] : 0);
        int defendVat = 0;
        int defendMa = 0;
        if (this.charClass == 3) {
            defend1 = defend1 * 4 / 3;
        }
        if (this.charClass == 4) {
            if (isSaTang() ) {
                int lv = getLevelCaiTrang();
                if (lv >= 1 && lv <= 15) {
                    defen4 = this.agitity + lv + this.getEffSkillClan(3) + this.newAtb[3] + this.upAbility[3] + this.abilityKham[1] + ((animalRide != null) ? animalRide.att[7] : 0);
                    defend1 = defend1 * 11 / 10;
                    defen4 = defen4 * 11 / 10;
                 
                }
            } else {
            defen4 = this.agitity + this.getEffSkillClan(3) + this.newAtb[3] + this.upAbility[3] + this.abilityKham[1] + ((animalRide != null) ? animalRide.att[7] : 0);
            defend1 = defend1 * 11 / 10;
            defen4 = defen4 * 11 / 10;
            }
        }
        int upMa = 0;
        int upVat = 0;
        int upPTma = 0;
        int upPtvat = 0;
        int upvP = 0;
        int upmP = 0;
        int upThuTbChar = 0;
        for (int i = 0; i < this.wItems[this.slotWearing].length; ++i) {
            final Item item = this.wItems[this.slotWearing][i];
            if (item != null) {
                final int t = item.getTemplate().type;
                if (item.level <= this.lvDetail.lv) {
                    if (t == 19) {
                        if (item.magic_physic == 0) {
                            defendMa += item.atb[6];
                            defendVat += item.atb[1];
                        } else if (item.magic_physic == 1) {
                            defendVat += item.atb[1];
                            defendMa += item.atb[6];
                        }
                        upPTma += item.otherAtt[2];
                        upPtvat += item.otherAtt[3];
                        upmP += item.otherAtt[2];
                        upvP += item.otherAtt[3];
                        this.baokich = item.otherAtt[4];
                    }
                    if (Map.isWeapone(t) || t == 12) {
                        this.hutmau += item.otherAtt[16];
                        this.tanphe += item.otherAtt[19];
                        this.lamthinh += item.otherAtt[21];
                    } else {
                        this.hoihp += item.otherAtt[17];
                        this.hoimp += item.otherAtt[18];
                        this.tyleRotItem += item.otherAtt[22];
                        this.tylerotXu += item.otherAtt[23];
                        this.khangbanglan += item.otherAtt[24];
                        this.khangsetlan += item.otherAtt[25];
                        this.khangdoclan += item.otherAtt[26];
                        this.khanglualan += item.otherAtt[31];
                        upThuTbChar += item.otherAtt[27];
                    }
                    if (t == 0) {
                        if (item.magic_physic == 2) {
                            defendVat += item.atb[1] * 2 / 10;
                            defendMa += item.atb[1] * 2 / 10;
                        } else if (item.magic_physic == 0) {
                            defendMa += item.atb[6] * 2;
                            defendVat += item.atb[1] * 2;
                        } else if (item.magic_physic == 1) {
                            defendVat += item.atb[1] * 2;
                            defendMa += item.atb[6] * 2;
                        }
                        upMa += item.lockAtb[1];
                        upVat += item.lockAtb[2];
                        upmP += item.lockAtb[1];
                        upvP += item.lockAtb[2];
                    }
                    if (t == 1) {
                        if (item.magic_physic == 2) {
                            defendVat += item.atb[1] / 10;
                            defendMa += item.atb[1] / 10;
                        } else if (item.magic_physic == 0) {
                            defendMa += item.atb[6];
                            defendVat += item.atb[1];
                        } else if (item.magic_physic == 1) {
                            defendVat += item.atb[1];
                            defendMa += item.atb[6];
                        }
                        upMa += item.lockAtb[1];
                        upVat += item.lockAtb[2];
                        upmP += item.lockAtb[1];
                        upvP += item.lockAtb[2];
                    }
                    if (t == 11) {
                        if (item.magic_physic == 2) {
                            defendVat += item.atb[1] / 10;
                            defendMa += item.atb[1] / 10;
                        } else if (item.magic_physic == 0) {
                            defendMa += item.atb[6];
                            defendVat += item.atb[1];
                        } else if (item.magic_physic == 1) {
                            defendVat += item.atb[1];
                            defendMa += item.atb[6];
                        }
                        upMa += item.lockAtb[1];
                        upVat += item.lockAtb[2];
                        upvP += item.lockAtb[2];
                        upmP += item.lockAtb[1];
                    }
                    if (t == 2) {
                        if (item.magic_physic == 2) {
                            defendVat += item.atb[1] / 10;
                            defendMa += item.atb[1] / 10;
                        } else if (item.magic_physic == 0) {
                            defendMa += item.atb[6];
                            defendVat += item.atb[1];
                        } else if (item.magic_physic == 1) {
                            defendVat += item.atb[1];
                            defendMa += item.atb[6];
                        }
                        upMa += item.lockAtb[1];
                        upVat += item.lockAtb[2];
                        upvP += item.lockAtb[2];
                        upmP += item.lockAtb[1];
                    }
                    if (t == 12) {
                        upMa += item.lockAtb[1];
                        upVat += item.lockAtb[2];
                        upvP += item.lockAtb[2];
                        upmP += item.lockAtb[1];
                    }
                    if (t == 10) {
                        if (item.magic_physic == 2) {
                            defendVat += item.atb[1] / 10;
                            defendMa += item.atb[1] / 10;
                        } else if (item.magic_physic == 0) {
                            defendMa += item.atb[6];
                            defendVat += item.atb[1];
                        } else if (item.magic_physic == 1) {
                            defendVat += item.atb[1];
                            defendMa += item.atb[6];
                        }
                        upMa += item.lockAtb[1];
                        upVat += item.lockAtb[2];
                        upvP += item.lockAtb[2];
                        upmP += item.lockAtb[1];
                    }
                    this.setlan += item.otherAtt[5];
                    this.doclan += item.otherAtt[6];
                    this.banglan += item.otherAtt[7];
                    this.lualan += item.otherAtt[28];
                }
            }
        }
      
        if (isLinhThueLan()) {
            this.lualan += 5;
      
        }
   
        if (this.itemHoaKyLan != null) {
            this.khangbanglan += this.itemHoaKyLan.otherAtt[24];
            this.khangsetlan += this.itemHoaKyLan.otherAtt[25];
            this.khangdoclan += this.itemHoaKyLan.otherAtt[26];
            this.khanglualan += this.itemHoaKyLan.otherAtt[31];
        }
        int defThuma = 0;
        int defThuvat = 0;
        int defThumaP = 0;
        int defThuvatP = 0;
        int upvatp = 0;
        int upmap = 0;
        final Vector<Item> awItems = this.getAnimalWearing();
        for (final Item item2 : awItems) {
            final int t2 = item2.getTemplate().type;
            if (t2 == 16 || t2 == 18 || t2 == 14) {
                if (item2.magic_physic == 0) {
                    defThuma += item2.atb[6];
                    defThuvat += item2.atb[1];
                    defThumaP += item2.atb[6];
                    defThuvatP += item2.atb[1];
                } else if (item2.magic_physic == 1) {
                    defThuvat += item2.atb[1];
                    defThuma += item2.atb[6];
                    defThuvatP += item2.atb[1];
                    defThumaP += item2.atb[6];
                }
                upMa += item2.lockAtb[1];
                upVat += item2.lockAtb[2];
                upvatp += item2.lockAtb[1];
                upmap += item2.lockAtb[2];
                upvP += item2.lockAtb[2];
                upmP += item2.lockAtb[1];
            }
        }
        this.defend_physic = (short) (defend1 + defendVat);
        this.defend_magic = (short) (defen4 + defendMa);
        this.defendVatPlayer = (short) (defend1 + defendVat);
        this.defendMaPlayer = (short) (defen4 + defendMa);
        if (this.charClass == 3) {
            this.defend_physic += this.defend_physic * this.buffPass[0] / 100;
            this.defend_magic += this.defend_magic * this.buffPass[0] / 100;
            upvP += this.buffPass[0];
            upmP += this.buffPass[0];
        }
        int defPet = 0;
        if (petUsing != null) {
            this.banglan += (short) petUsing.getBangLan();
            this.setlan += (short) petUsing.getSetLan();
            this.lualan += (short) petUsing.getLuaLan();
            this.doclan += (short) petUsing.getDocLan();
            defPet = petUsing.getPcUpDef();
            this.baokich += (short) petUsing.getbaoKich();
        }
        upvP += defPet;
        upmP += defPet;
        if (this.rankGov == 0) {
            upVat += 5;
            upMa += 5;
            upvP += 5;
            upmP += 5;
        }
        if (this.timeUp5Attribute > 0L) {
            upVat += 5;
            upMa += 5;
        }
        if (this.vip > 1) {
            upVat += 5;
            upMa += 5;
            if (this.vip == 3) {
                this.baokich += 30;
            }
        }
        if (petUsing != null) {
            upMa += petUsing.getCanKhon()[1] + petUsing.getKienCo();
            upVat += petUsing.getKimCang()[1] + petUsing.getKienCo();
            upThuTbChar += petUsing.getHoThe();
        }
        this.defend_physic += upVat * this.defend_physic / 100;
        this.defend_magic += upMa * this.defend_magic / 100;
        if (animalRide != null) {
            this.baokich += (short) animalRide.getBaoKich();
            this.defend_magic += this.defend_magic * (animalRide.att[1] + animalRide.attUp[1]) / 100;
            this.defend_physic += this.defend_physic * (animalRide.att[0] + animalRide.attUp[0]) / 100;
            upvP += animalRide.att[0] + animalRide.attUp[0];
            upmP += animalRide.att[1] + animalRide.attUp[1];
            byte upm = 0;
            byte upv = 0;
            if (this.isFullsetAnimal >= 5) {
                upm += 12;
                upv += 12;
                upvP += 12;
                upmP += 12;
            } else if (this.isFullsetAnimal >= 3) {
                upv += 8;
                upm += 8;
                upvP += 8;
                upmP += 8;
            }
            defThuma += defThuma * (animalRide.att[1] + animalRide.attUp[1] + upMa + upm) / 100;
            defThuvat += defThuvat * (animalRide.att[0] + animalRide.attUp[0] + upVat + upv) / 100;
            defThumaP += defThuma * (animalRide.att[1] + animalRide.attUp[1] + upmap + upm) / 100;
            defThuvatP += defThuvat * (animalRide.att[0] + animalRide.attUp[0] + upvatp + upv) / 100;
        }
        int upfullset = 0;
        if (this.married == 1) {
            if (this.potions[88] > 0) {
                upfullset = 2;
            } else if (this.potions[89] > 0) {
                upfullset = 4;
            } else if (this.potions[90] > 0) {
                upfullset = 7;
            }
        }
        if (this.fullSet15 >= 10) {
            upfullset += 12;
        } else if (this.fullSet15 >= 6) {
            upfullset += 8;
        } else if (this.fullSet13 >= 10) {
            upfullset += 4;
        } else if (this.fullSet10 >= 10) {
            ++upfullset;
        }
        upvP += upfullset;
        upmP += upfullset;
        if (this.wModel != null) {
            this.defend_magic += this.defend_magic * (this.wModel.getDefend() + upfullset + defPet) / 100;
            this.defend_physic += this.defend_physic * (this.wModel.getDefend() + upfullset + defPet) / 100;
            upvP += this.wModel.getDefend() + upfullset + defPet;
            upmP += this.wModel.getDefend() + upfullset + defPet;
            this.baokich += (short) this.wModel.getbaoKich();
            if (this.baokich > 0) {
                this.baokich += (short) this.wModel.getbaoKichMatna();
            }
        } else {
            this.defend_magic += this.defend_magic * (upfullset + defPet) / 100;
            this.defend_physic += this.defend_physic * (upfullset + defPet) / 100;
            upvP += upfullset + defPet;
            upmP += upfullset + defPet;
        }
        this.defend_magic += defThuma + defendMa * upPTma / 100 + defendMa * upThuTbChar / 1000;
        this.defend_physic += defThuvat + defendVat * upPtvat / 100 + defendVat * upThuTbChar / 1000;
        int atkTubinh = 0;
        for (int j = 0; j < this.tubinh.size(); ++j) {
            atkTubinh += this.tubinh.get(j).getDef();
        }
        this.defend_magic += atkTubinh;
        this.defend_physic += atkTubinh;
        this.defendMaPlayer += defThumaP + defendMa * upmP / 100 + atkTubinh + defendMa * upThuTbChar / 1000;
        this.defendVatPlayer += defThuvatP + defendVat * upvP / 100 + atkTubinh + defendVat * upThuTbChar / 1000;
        if (this.charname.equals("chienthan")) {
            this.defend_magic = 100000;
            this.defend_physic = 100000;
            this.defendMaPlayer = 100000;
            this.defendVatPlayer = 100000;
        }
        if (this.charClass == 3) {
            this.defend_physic += (short) (defend1 + defendVat);
            this.defend_magic += (short) (defen4 + defendMa);
            this.defendVatPlayer += (short) (defend1 + defendVat);
            this.defendMaPlayer += (short) (defen4 + defendMa);
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c2 != null) {
                final long noiluc = c2.getNoiLuc();
                this.defend_physic = (int) (this.defend_physic * noiluc / 10L);
                this.defend_magic = (int) (this.defend_magic * noiluc / 10L);
                this.baokich = (short) (this.baokich * noiluc / 10L);
            }
        }
    }

    public void buffAttackSkill(final Monster mt, final int d) {
        final Random r = new Random(System.currentTimeMillis());
        try {
            int damPhan = d * this.attNguHanh[4] / 100;
            if (this.charClass == 0 && this.arrayBuff[4] != -1) {
                final int n = r.nextInt(100);
                if (n < 70) {
                    final int damPhan2 = d * CharManager.SKILL_DAM_PERCENT[this.charClass][5][this.skill[5] + this.addMoreLevelSkill[5]] / 100;
                    if (damPhan2 > damPhan) {
                        damPhan = damPhan2;
                    }
                }
            }
            if (damPhan > 0) {
                final Message m = new Message(89);
                m.dos.writeShort(mt.id);
                m.dos.writeByte(mt.cat);
                m.dos.writeByte(0);
                m.dos.writeShort(damPhan);
                m.dos.writeByte(5);
                m.dos.writeByte(-1);
                m.dos.writeByte(0);
                mt.hp -= damPhan;
                this.sendMessage(m);
                this.sendToNearPlayer(m);
            }
        } catch (final Exception ex) {
        }
    }

    public int khamMu() {
        return this.abilityKham[4];
    }

    public int khamBang() {
        return this.abilityKham[5];
    }

    public int khamDoc() {
        return this.abilityKham[6];
    }

    public int khamChoang() {
        return this.abilityKham[7];
    }

    public int khamHoathach() {
        return this.abilityKham[8];
    }

    public int khamGiamtoc() {
        return this.abilityKham[9];
    }

    @Override
    public int khamKhangGiamtoc() {
        return this.abilityKham[10];
    }

    @Override
    public int khamKhangDoc() {
        return this.abilityKham[11];
    }

    @Override
    public int khamKhangMu() {
        return this.abilityKham[12];
    }

    @Override
    public int khamKhangBang() {
        if (this.animalRide != null && 
            (this.animalRide.isPhuongHoangLua() || this.animalRide.isRongLon()) && 
            this.animalRide.KHANG_DONG_BANG > 0) {
            return 95;
        }
        return this.abilityKham[13];
    }

    @Override
    public int khamKhangChoang() {
        return this.abilityKham[14];
    }

    @Override
    public int khamKhangHoathach() {
        return this.abilityKham[15];
    }

    @Override
    public int khamX2() {
        return this.abilityKham[16];
    }

    @Override
    public int khamDropItem() {
        return this.abilityKham[17];
    }

    public void buffSkillKham(final LiveActor actor) {
        try {
            if (actor.isActiveTienKhi() || actor.isActive8sRongLon()) {
                return;
            }
            int[] khang = new int[6];
            byte nEff = 0;
            khang = new int[]{actor.khamKhangMu(), actor.khamKhangBang(), actor.khamKhangDoc(), actor.khamKhangChoang(), actor.khamKhangHoathach(), actor.khamKhangGiamtoc()};
            final int pckhang = 1000;
            if (this.khamMu() > 0 && Map.r.nextInt(pckhang) <= this.khamMu() && Map.r.nextInt(pckhang) > khang[0] && actor.khamEff[0] == 0L) {
                actor.khamEff[0] = System.currentTimeMillis() / 1000L;
                ++nEff;
            }
            if (this.khamBang() > 0 && Map.r.nextInt(pckhang) <= this.khamBang() && Map.r.nextInt(pckhang) > khang[1] && actor.khamEff[3] == 0L) {
                actor.khamEff[3] = System.currentTimeMillis() / 1000L;
                ++nEff;
            }
            if (this.khamDoc() > 0 && Map.r.nextInt(pckhang) <= this.khamDoc() && Map.r.nextInt(pckhang) > khang[2] && actor.khamEff[1] == 0L) {
                actor.khamEff[1] = System.currentTimeMillis() / 1000L;
                actor.timeOutKhamDoc = System.currentTimeMillis();
                ++nEff;
            }
            if (this.khamChoang() > 0 && Map.r.nextInt(pckhang) <= this.khamChoang() && Map.r.nextInt(pckhang) > khang[3] && actor.khamEff[2] == 0L) {
                actor.khamEff[2] = System.currentTimeMillis() / 1000L;
                ++nEff;
            }
            if (this.khamHoathach() > 0 && Map.r.nextInt(pckhang) <= this.khamHoathach() && Map.r.nextInt(pckhang) > khang[4] && actor.khamEff[4] == 0L) {
                actor.khamEff[4] = System.currentTimeMillis() / 1000L;
                ++nEff;
            }
            if (this.khamGiamtoc() > 0 && Map.r.nextInt(pckhang) <= this.khamGiamtoc() && Map.r.nextInt(pckhang) > khang[5] && actor.khamEff[5] == 0L) {
                actor.khamEff[5] = System.currentTimeMillis() / 1000L;
                ++nEff;
            }
            final Message m = this.sendEffKham(actor);
            if (m != null) {
                this.sendMessage(m);
                this.sendToNearPlayer(m);
            }
        } catch (final Exception ex) {
        }
    }

    public void setBangLan(final Vector<LiveActor> actor) {
    }

    public void setDocLan(final Vector<LiveActor> actor) {
    }

    public Message sendEffKham(final LiveActor actor) {
        final Message msg = new Message(-42);
        boolean haveEff = false;
        try {
            msg.dos.writeShort(actor.id);
            for (int i = 0; i < actor.khamEff.length; ++i) {
                if (actor.khamEff[i] > 0L) {
                    msg.dos.writeByte(i);
                    msg.dos.writeByte((byte) (3L - (System.currentTimeMillis() / 1000L - actor.khamEff[i])));
                    if (!haveEff) {
                        haveEff = true;
                    }
                }
            }
        } catch (final Exception ex) {
        }
        if (haveEff) {
            return msg;
        }
        return null;
    }

    public void buffAttackSkill(final int d, final LiveActor actor) {
        try {
            if (actor.isActiveTienKhi() || actor.isActive8sRongLon()) {
                return;
            }
            if (this.charClass == 4) {
                try {
                    if (actor.cat == 0 && ((Char) actor).charClass == 4) {
                        return;
                    }
                } catch (final Exception ex) {
                }
                if (this.arrayBuff[2] != -1) {
                    int random = 0;
                    if ((random = Map.r.nextInt(100)) <= 100) {
                        int khangdoc = actor.khamKhangDoc();
                        if (actor.cat == 0) {
                            final int hour = getCurrentHour();
                            if (hour > 6 && khangdoc > 50) {
                                khangdoc = 50;
                            }
                            if (actor.getLevel() - this.getLevel() > 5 && this.getLevel() < 60) {
                                khangdoc = 0;
                            }
                        }
                        if (actor.tDelay != 0 || random < khangdoc) {
                            return;
                        }
                        final int[] timeCash = {5, 5, 5, 5, 5, 4, 4, 3, 2, 1, 1};
                        int damage = CharManager.SKILL_DAM_PERCENT[4][4][this.skill[4] + this.addMoreLevelSkill[4]];
                        final int deltaLV = this.lvDetail.lv - actor.getLevel();
                        if (actor.cat == 0 && deltaLV < 0) {
                            damage -= Map.abs(deltaLV) * 50;
                            if (damage < 50) {
                                damage = 50;
                            }
                        }
                        if (this.skill[5] + this.addMoreLevelSkill[5] > 0) {
                            final int pc = CharManager.SKILL_DAM_PERCENT[4][5][this.skill[5] + this.addMoreLevelSkill[5]];
                            damage += damage * pc / 100;
                        }
                        actor.timeOutPoinson = System.currentTimeMillis();
                        actor.poinson = (short) damage;
                        actor.tDelay = (short) timeCash[this.skill[4]];
                        actor.totalTime = 36;
                        final Message m = new Message(89);
                        m.dos.writeShort(actor.id);
                        m.dos.writeByte(actor.cat);
                        m.dos.writeByte(timeCash[this.skill[4]]);
                        m.dos.writeShort(damage);
                        m.dos.writeByte(4);
                        m.dos.writeByte(this.charClass);
                        m.dos.writeByte(36);
                        this.sendMessage(m);
                        this.sendToNearPlayer(m);
                        m.cleanup();
                    }
                }
            } else if (this.charClass == 3 && this.arrayBuff[0] != -1) {
                try {
                    if (actor.cat == 0 && ((Char) actor).charClass == 3) {
                        return;
                    }
                } catch (final Exception ex2) {
                }
                if (actor.beStune) {
                    return;
                }
                if ((actor.cat == 0 || (actor.cat == 1 && actor.isMonsterVantieu())) && actor.getLevel() - this.getLevel() > 5 && this.getLevel() < 60) {
                    return;
                }
                final int percent = CharManager.SKILL_DAM_PERCENT[3][4][this.skill[4] + this.addMoreLevelSkill[4]];
                if (Map.r.nextInt(100) <= percent) {
                    this.timeBeStune = 3000L + System.currentTimeMillis();
                    actor.beStune = true;
                    final Message i = new Message(89);
                    i.dos.writeShort(actor.id);
                    i.dos.writeByte(actor.cat);
                    i.dos.writeByte(-1);
                    i.dos.writeShort(-1);
                    i.dos.writeByte(4);
                    i.dos.writeByte(this.charClass);
                    i.dos.writeByte(3);
                    this.sendMessage(i);
                    this.sendToNearPlayer(i);
                }
            }
            if (actor.cat == 0) {
                final Char temp = (Char) actor;
                int phandam = d * temp.attNguHanh[4] / 100;
                if (temp.arrayBuff[4] != -1 && temp.charClass == 0 && Map.r.nextInt(100) < 70) {
                    final int phandam2 = d * CharManager.SKILL_DAM_PERCENT[temp.charClass][5][temp.skill[5] + this.addMoreLevelSkill[5]] / 100;
                    if (phandam2 > phandam) {
                        phandam = phandam2;
                    }
                    if (this.charClass == 2) {
                        phandam -= (int) ((Char) actor).checkMagicShield(phandam);
                    }
                }
                if (phandam > 0) {
                    final Message j = new Message(89);
                    j.dos.writeShort(this.id);
                    j.dos.writeByte(this.cat);
                    j.dos.writeByte(0);
                    j.dos.writeShort(phandam);
                    j.dos.writeByte(5);
                    j.dos.writeByte(-1);
                    this.hp -= phandam;
                    if (this.hp <= 0) {
                        this.actorDie();
                        this.hp = 0;
                        if (Map.giveCardFail(this)) {
                            this.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " giao thẻ thất bại"), this.myCountry);
                            this.map.sendAllPlayer(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
                        }
                    }
                    this.sendMessage(j);
                    this.sendToNearPlayer(j);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    public boolean checkMatNaVaLan() {
        return ((this.wModel.isMatNaTho() || this.wModel.isMatNaCao() )&& this.wModel.isNguyetLinhTruong()) ;
 
    }


    public boolean checkVuKhiThanBinh() {
        return this.itemVukhiThoiTrang != null && this.itemVukhiThoiTrang.isVuKhiThanBinhNewUpdate09092024();
    }

    public boolean CheckNguyenLieuTL() {
        // Kiểm tra xem có đang mặc vũ khí thần binh không
        if (this.itemVukhiThoiTrang == null || !this.itemVukhiThoiTrang.isVuKhiThanTL()) {
            return false;
        }
    
        int[] validMaterialIDs = {421,408,447,434,460,422,409,448,435,461,423,410,449,436,462,424,411,450,437,463,425,412,451,438,464,893,894,895,896,897};
        
        // Lấy level và type của trang bị đang mặc
        int equipLevel = this.itemVukhiThoiTrang.level;
        int equipType = this.itemVukhiThoiTrang.getTemplate().type;
    
        // Kiểm tra trong túi đồ
        for (Item item : this.iItems) {
            // Kiểm tra ID có trong danh sách nguyên liệu hợp lệ
            boolean isValidMaterial = false;
            for (int materialID : validMaterialIDs) {
                if (item.tempateID == materialID) {
                    isValidMaterial = true;
                    break;
                }
            }
            if (isValidMaterial) {
                // Kiểm tra level (phải < 11) và cùng type
                if (Math.abs(item.level - equipLevel) <= 10 && 
                    item.getTemplate().type == equipType) {
                    
                    return true; // Tìm thấy nguyên liệu hợp lệ
                }
            }
        }
    
        return false; // Không tìm thấy nguyên liệu hợp lệ
    }

    public String getNguyenLieuTL() {
        if (this.itemVukhiThoiTrang == null || !this.itemVukhiThoiTrang.isVuKhiThanTL()) {
            return "vũ khí thần binh";
        }
        
        // Get equipment type and level
        int equipType = this.itemVukhiThoiTrang.getTemplate().type;
        int equipLevel = this.itemVukhiThoiTrang.level;
        
        // Get valid materials for this type
        StringBuilder materials = new StringBuilder("\n");
        boolean first = true;
        
        int[] validMaterialIDs = {421,408,447,434,460,422,409,448,435,461,423,410,449,436,462,424,411,450,437,463,425,412,451,438,464,893,894,895,896,897};
        
        for (int materialID : validMaterialIDs) {
            ItemTemplates template = (ItemTemplates)Map.itemTemplates.get(0).get(materialID);
            if (template != null && template.type == equipType) {
                if (Math.abs(template.level - equipLevel) <= 10) {  // Add level check
                    if (!first) {
                        materials.append(" hoặc ");
                    }
                    materials.append(template.name);
                    if (equipLevel > 0) {
                        materials.append("(lv ").append(template.level).append(")");
                    }
                    first = false;
                }
            }
        }
        
        return materials.toString();
    }




    public int checkHapthuSatThuong(final int dam, final LiveActor actor) {
        if (actor.isBossSonTinhThuyTinh()) {
            return dam;
        }
        int finalDam = dam;
        if (this.isActive8sRongLon()) {
            // Tích lũy sát thương nhận vào
            this.damageAccumulated += finalDam;
       
            }
    

        if (this.wModel.isMatNaTho() || this.wModel.isMatNaCao()) {
            int hpRecover = (finalDam * 10) / 100;
            
          
            
            this.hp += hpRecover;
            if (this.hp > this.maxhp) {
                this.hp = this.maxhp;
            }
      
            
            
        }

        int a = 0;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }

        if (isLinhThueLan()) {
            // Hấp thụ 3% sát thương
            a += finalDam * 3 / 100;
        }
        if (animalRide != null && animalRide.att[9] + animalRide.attUp[9] > 0 && Map.r.nextInt(200) <= animalRide.att[9] + animalRide.attUp[9]) {
            a = finalDam * (animalRide.att[9] + animalRide.attUp[9]) / 100;
        }
        final int backdam = this.getBackDam(dam);
        if (backdam > 0 && ((!actor.isLienHoaTru() && actor.isMonsterMoba()) || !this.map.isMapChienTruongMoba())) {
            final Message mbd = MessageCreator.createMsgBuffEffect(5, actor.cat, actor, backdam, 0, -1);
            this.sendMessage(mbd);
            this.sendToNearPlayer(mbd);
            actor.hp -= backdam;
            if (actor.hp <= 0) {
                if (actor.cat == 1) {
                    Map.onMosterDie(this, actor, (byte) 0, (short) backdam, (byte) 0, (byte) 0);
                } else {
                    actor.actorDie();
                }
            }
        }
        a += finalDam * this.getPcGiamSatThuong() / 100;
        if (a < 0) {
            a = 1;
        }
        finalDam -= a;
        if (finalDam < 0) {
            finalDam = 1;
        }
        return finalDam;
    }

    @Override
    public boolean isMagic() {
        return this.charClass == 2 || this.charClass == 4;
    }

    public int checkPassAttack(final LiveActor attacker, final int dam) {
        this.checkActiveSkillPet();
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 7) {
                return dam;
            }
        }
        if (attacker.isMagic()) {
            if (Map.r.nextInt(100) < this.attNguHanhAnimal[0]) {
                return 0;
            }
        } else if (Map.r.nextInt(100) < this.attNguHanhAnimal[1]) {
            return 0;
        }
        return dam;
    }

    @Override
    public int checkGiamSatThuongTong(final int dam) {
        int finalDam = dam;
        long pcgiam = 0L;
        if (this.wModel != null && System.currentTimeMillis() - this.timeBuffGiamSatThuong < 0L) {
            pcgiam = this.wModel.getPcGiamSatThuong();
        }
        final EffectBuff ef = this.hashEffBuff.get(EffectBuff.EFF_PHA_TRIEN);
        if (ef != null) {
            pcgiam += ef.dam;
        }
        finalDam -= (int) (finalDam * pcgiam / 100L);
        return finalDam;
    }

    /**
     * todo check giảm sát thương khi bị ăn đòn
     *
     * @param dam
     * @return
     */
    public int checkGiamSatThuong(final int dam) {
        int finalDam = dam;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        if (animalRide != null && animalRide.att[10] + animalRide.attUp[10] > 0 && Map.r.nextInt(2000) <= animalRide.att[10] + animalRide.attUp[10]) {
            finalDam = 1;
        }
        long pcgiam = 0L;
        this.ischeckActivebuffgiamsatthuong();
        if (this.wModel != null && System.currentTimeMillis() - this.timeBuffGiamSatThuong < 0L) {
            pcgiam = this.wModel.getPcGiamSatThuong();
        }
        final EffectBuff ef = this.hashEffBuff.get(EffectBuff.EFF_PHA_TRIEN);
        if (ef != null) {
            pcgiam += ef.dam;
        }
        finalDam -= (int) (finalDam * pcgiam / 100L);
        return finalDam;
    }

    public long checkMagicShield(final int d) {
        long mpLost = 0L;
        try {
            if (this.arrayBuff[3] != -1 && this.charClass == 2) {
                final long pc = CharManager.SKILL_DAM_PERCENT[2][7][this.skill[7] + this.addMoreLevelSkill[7]];
                mpLost = d * pc / 100L;
                if (this.mp < mpLost) {
                    mpLost = this.mp;
                }
                this.mp -= (int) mpLost;
                if (this.mp < 0) {
                    this.mp = 0;
                }
                final Message m = new Message(89);
                m.dos.writeShort(this.id);
                m.dos.writeByte(this.cat);
                m.dos.writeByte(0);
                m.dos.writeShort((int) mpLost);
                m.dos.writeByte(7);
                m.dos.writeByte(2);
                m.dos.writeByte(0);
                this.sendMessage(m);
                this.sendToNearPlayer(m);
            }
        } catch (final Exception ex) {
        }
        if (mpLost < 0L) {
            mpLost = 0L;
        }
        return mpLost;
    }

    public boolean havecrit() {
        return Map.r.nextInt(300) < this.critical + this.getEffSkillClan(1) * this.critical / 100;
    }

    @Override
    public boolean haveBaoKich() {
        return Map.r.nextInt(1000) < this.baokich;
    }

    public int getBuffEffAttack() {
        final Random r = new Random(System.currentTimeMillis());
        switch (this.charClass) {
            case 0: {
                if (this.attNguHanh[3] > 0 && r.nextInt(100) < this.attNguHanh[3]) {
                    return 1;
                }
                if (this.skill[4] + this.addMoreLevelSkill[4] > 0 && r.nextInt(100) < CharManager.SKILL_DAM_PERCENT[0][4][this.skill[4] + this.addMoreLevelSkill[4]]) {
                    return 0;
                }
                break;
            }
            case 1:
            case 2:
            case 3:
            case 4: {
                if (this.attNguHanh[3] > 0 && r.nextInt(100) < this.attNguHanh[3]) {
                    return 1;
                }
                break;
            }
        }
        return -1;
    }

    public int calDelTaLevel(final Char actor1, final Char actor2) {
        return Map.abs(actor1.lvDetail.lv - actor2.lvDetail.lv);
    }

    public int playerAttackPlayer(final Char p1, final Char p2, final int buffAttack) {
        final int finalDam = 0;
        if (p1.charClass != 0 && p1.charClass != 1 && p1.charClass != 2 && p1.charClass != 3) {
            final byte charClass = p1.charClass;
        }
        return finalDam;
    }

    public int[] calInfoAttackPlayer(final Char actor, final int defend) {
        final int[] result = {defend, this.attack};
        if (this.isMonster) {
            final int[] array = result;
            final int n = 1;
            array[n] *= 2;
        }
        return result;
    }

    @Override
    public boolean attackMiss(final LiveActor actor) {
        return super.attackMiss(actor);
    }

    public boolean isCharMagic() {
        return this.charClass == 2 || this.charClass == 4;
    }

    public int getDamtThanThu(final LiveActor ac) {
        if (this.charthanthu != null) {
            return this.charthanthu.attackDam(ac);
        }
        return 0;
    }

    public int getDamtThanThuAuto(final LiveActor ac) {
        if (this.charthanthu != null) {
            return this.charthanthu.attackDamAuto(ac);
        }
        return 0;
    }

    public void charHireAttackDam(final LiveActor actor, final int type, final int level, final int bubbAttack) {
        try {
            if (this.charHire != null && this.charHire.hp > 0) {
                if (actor.isRuongMaquai() || actor.isNgocRong() || actor.isBossSonTinh() || actor.isBossThuyTinh() || actor.isBossSonTinhThuyTinh()) {
                    return;
                }
                if (this.charHire.isCoolDown(type) && actor.cat == 1) {
                    return;
                }
                this.charHire.attackDam(actor, type, level, bubbAttack);
            }
        } catch (final Exception ex) {
        }
    }

    public void charHireAttackMultiMOnster(final Vector<Monster> ms, final int idskill) {
        try {
            if (this.charHire != null && this.charHire.hp > 0) {
                if (this.charHire.isCoolDown(idskill)) {
                    return;
                }
                this.charHire.charHireAttackMultiMOnster(ms, idskill);
            }
        } catch (final Exception ex) {
        }
    }

    public boolean isNotDownExp() {
        return this.vip == 3;
    }

    @Override
    public int getAttack() {
        return super.getAttack() + this.getValueLua(2) * this.attack / 100;
    }

    @Override
    public boolean isKhangDoSat() {
        Pet petUsing = this.petUsing;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
        }
        return petUsing != null && petUsing.isPetBachThu();
    }

    @Override
    public void doHoaNguoiTuyetKhiBiDanh(final LiveActor p) {
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        if (animalRide != null) {
            final int pc = animalRide.getPcHoaNguoiTuyetKhiBiDanh();
            if (Map.r.nextInt(100) < pc) {
                p.setNguoiTuyet(2);
                MessageCreator.createMsgCharMonster((Char) p, this);
            }
        }
    }

    @Override
    public void do8sRongLon(final LiveActor p) {
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        
        if (animalRide != null) {
            final int pc = animalRide.getPc8sRongLon();
            // Kiểm tra thời gian cooldown
            if (System.currentTimeMillis() - this.timeRongLon < 8000) {
                return;
            }
            
            if (Map.r.nextInt(100) < pc) {
                // Tăng biến đếm số lần bị đánh
                this.countRongLon++;
                try {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Sát thương cam chịu tích lũy lên " + this.countRongLon));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Kiểm tra nếu đã đủ 8 lần
                if (this.countRongLon >= 8) {
                    // Reset counter và set thời gian cooldown
                    this.countRongLon = 0;
                    this.timeRongLon = System.currentTimeMillis();
                    // Kích hoạt trạng thái 8s Rồng Lớn
                    this.timeActive8sRongLon = System.currentTimeMillis() + 8000; // Set thời gian kết thúc sau 8s
                    try {
                        this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Kích hoạt trạng thái bất tử trong 8 giây!")); 
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        }

        @Override
        public boolean isActive8sRongLon() {
            return System.currentTimeMillis() < this.timeActive8sRongLon;
        }

    public boolean isPetCanAttack(final LiveActor actor) {
        return (!actor.isBossSonTinh() || this.wModel.isThuyTinh()) && (!actor.isBossThuyTinh() || this.wModel.isSonTinh());
    }

    public void doUseHoaDuoc() {
        try {
            final Message msg = MessageCreator.createMsgNewEffectSkill5Long(EffectBuff.EFF_BOM_DIA_HOA, 1000, this.id, this.cat, true, false);
            this.sendMessage(msg);
            this.sendToNearPlayer(msg);
            final CharChienTruong cct = MapChienTruongMoba.getCharChienTruong(this.charname);
            for (int i = 0; i < this.nearChars.size(); ++i) {
                final Char p = this.map.getPlayerByID(this.nearChars.get(i));
                final Session s = p.session;
                if (p != null && p.hp > 0 && p.id != this.id && p.isBot == -1 && this.near(p, 90) && p.map == this.map && p.region == this.region) {
                    final int hpb = p.maxhp * (cct.getNoiLuc() * 10) / 100;
                    final Char char1 = p;
                    char1.hp -= hpb;
                    try {
                        this.sendMessage(MessageCreator.createNew_HMP_Message(p, hpb));
                        this.sendToNearPlayer(MessageCreator.createNew_HMP_Message(p, hpb));
                    } catch (final IOException ex) {
                    }
                    if (p.hp <= 0) {
                        p.hp = 0;
                        p.desTroy();
                        p.actorDie();
                        this.doAddPointChienTruong(p.getPointChienTruong());
                        if (cct != null && !p.isCharCopy()) {
                            cct.checkLienTram(p.charname);
                        }
                        p.doSetTimeAutoHoiSinhMapMoba();
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void sendInfoNuiChauBau() {
        int t = (int) this.getTimeNuiChauBau();
        if (t > 0 && t < 60) {
            t = 1;
        }
        if (t > 0) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Tìm kho báu: ", t, -1, Char.ID_TIME_NUI_KHO_BAU, Map.COUNT_DOWN, 618));
        }
    }

    public void sendInfoLoidai() {
        this.sendMessage(MessageCreator.createMsgTimeCountdown("Time: ", 180, -1, Char.ID_TIME_LOI_DAI, Map.COUNT_DOWN, -1));
    }

    public void sendInfoChienTruong(final int id, final int time) {
        final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
        if (id == Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Còn lại " + MapChienTruongMoba.getAllKhu() + " khu", 5, -1, Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, Map.NOT_COUNT_DOWN, -1));
        }
        if (id == Char.ID_NOI_LUC) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Nội lực: " + c.getNoiLuc() + "/10", 5, -1, Char.ID_NOI_LUC, Map.NOT_COUNT_DOWN, -1));
        }
        if (id == Char.ID_HON_DON) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("+50% st: ", time, -1, Char.ID_HON_DON, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_DINH_CHU) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("20% gây câm lặng: ", time, -1, Char.ID_DINH_CHU, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_HOI_HON) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Hồi 5% hp: ", time, -1, Char.ID_HOI_HON, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_HON_NGUYEN) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Kháng st trụ: ", time, -1, Char.ID_HON_NGUYEN, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_BACH_TIEN) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("+20% giáp: ", time, -1, Char.ID_BACH_TIEN, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_CAM_LANG) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Câm lặng: ", time, -1, Char.ID_CAM_LANG, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_PT_HOA_CONG) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Hoá công: ", time, -1, Char.ID_PT_HOA_CONG, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_PT_HON_DON) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Hỗn độn: ", time, -1, Char.ID_PT_HON_DON, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_PT_HON_NGUYEN) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Hỗn nguyên: ", time, -1, Char.ID_PT_HON_NGUYEN, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_HOI_SINH) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Hồi sinh sau: ", time, -1, Char.ID_HOI_SINH, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_INFO_PLAYER) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown(": ", time, -1, Char.ID_INFO_PLAYER, Map.NOT_COUNT_DOWN, -1));
        }
        if (id == Char.ID_NOI_LUC_TANG) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Nội lực x", time, -1, Char.ID_NOI_LUC_TANG, Map.COUNT_DOWN, -1));
        }
        if (id == Char.ID_DEM_NGUOC) {
            this.sendMessage(MessageCreator.createMsgTimeCountdown("Bắt đầu sau: ", time, -1, Char.ID_DEM_NGUOC, Map.COUNT_DOWN, -1));
        }
    }

    public int attackDamMonsterMoba(final LiveActor actor, final int idSkill, final int level, final int bubbAttack) {
        final int def = actor.getDefendPhysic() + actor.percentBuff[0];
        final int att = this.getAttack();
        actor.isLienHoaTru();
        return 0;
    }

    public int attackDam(final LiveActor actor, final int idSkill, final int level, final int bubbAttack) {
        actor.isCheckActiveBuffGiamSatThuong();
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        try {
            if (this.itemAx != null) {
                return this.itemAx.atb[0] * 2;
            }
            if (actor.isMonsterMoba() && (actor.isLienHoaTru() || actor.isBossTruRong()) && !actor.canAttack(this)) {
                return 0;
            }
            actor.doHoaNguoiTuyetKhiBiDanh(this);
            actor.do8sRongLon(this);
            if (this.isKiller && actor.isKhangDoSat() && this.map.isMapTrain()) {
                return 0;
            }
            if (actor.isBossGetItemByHit()) {
                if (actor.isBossSonTinh() && !this.wModel.isThuyTinh()) {
                    return 0;
                }
                if (idSkill > 0) {
                    actor.checkReceivePotion(this);
                }
            }
            if (actor.isBossThuyTinh()) {
                if (!this.wModel.isSonTinh()) {
                    return 0;
                }
                if (idSkill > 0) {
                    actor.checkReceivePotion(this);
                }
            }
            if (actor.isNgocRong()) {
                if (actor.getInCountry() != this.inCountry) {
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Không thể nhặt ngọc của làng khác."));
                    return 0;
                }
                return 1;
            } else {
                if (!actor.isRuongMaquai()) {
                    int def = actor.getDefendPhysic() + actor.percentBuff[0];
                    int att = this.getAttack();
                    // Thêm logic HoaHinh ở đây
                    if (actor.cat == 0 && this.getAnimalRide() != null && 
                    this.getAnimalRide().isHoaHinh() && Map.r.nextInt(100) < 100) { // tile hoahinh
                    
                    actor.applyHoaHinhDefenseReduction();
                    
                    if (actor instanceof Char) {
                        Char target = (Char)actor;
                        try {
                            target.sendMessage(
                                MessageCreator.createServerAlertAutoOffMessage(
                                    "Bị giảm " + actor.currentDefReduc + "% giáp trong 10 giây!"
                                )
                            );
                            
                            target.sendMessage(MessageCreator.createMainCharInfoMessage(target));
                            target.sendToNearPlayer(MessageCreator.createCharInfo(target));     
                            
                        } catch (IOException e) {
                            e.printStackTrace(); 
                        }
                    }
                }
                    if (this.idRuouUong > -1) {
                        final int[] pcf = {5, 10, 20};
                        att += pcf[this.idRuouUong] * att / 100;
                    }
                    boolean isBossBaoCat = false;
                    final CharChienTruong c2 = MapChienTruongMoba.getCharChienTruong(this.charname);
                    final CharChienTruong ca = MapChienTruongMoba.getCharChienTruong(actor.getName());
                    if (this.map != null && this.map.isMapChienTruongMoba()) {
                        int pcup = c2.isHonNguyen();
                        pcup += c2.getBuffHeHoa();
                        if (actor.isBoss()) {
                            pcup += c2.isHoaCong();
                        }
                        att += att * pcup / 100;
                    }
                    if (actor.cat == 0) {
                        if (((Char) actor).isBatTu()) {
                            return 0;
                        }
                        if (this.wModel.wpModel != null) {
                            int pcngtuyet = this.wModel.getPCNgtuyet();
                            if (this.isAdmin && Map.adminTest) {
                                pcngtuyet = 100;
                                
                            }
                            if (Map.r.nextInt(100) < pcngtuyet) {
                                ((Char) actor).setNguoiTuyet(2);
                                MessageCreator.createMsgCharMonster(this, this);
                            }
                        }
                        if (this.isCharMagic()) {
                            def = actor.getDefendMagic() + actor.getBuffDefCB(1, true);
                        }
                        if (((Char) actor).isMonster) {
                            if (actor.getDefendPhysic() + actor.percentBuff[0] > actor.getDefendMagic() + actor.getBuffDefCB(1, true)) {
                                def = actor.getDefendPhysic() + actor.percentBuff[0];
                            } else {
                                def = actor.getDefendMagic() + actor.getBuffDefCB(1, true);
                            }
                            def *= 3;
                            att *= 2;
                        }
                        final int deltalv = this.lvDetail.lv - actor.getLevel();
                        if (this.map != null && this.map.isMapChienTruongMoba() && ca != null) {
                            int pcup2 = ca.getBuffHeKim();
                            pcup2 += ca.isHonDon();
                            def += def * pcup2 / 100;
                        }
                        if (!this.map.isMapChienTruongMoba() || (this.map.isMapChienTruongMoba() && !actor.isCharMonster())) {
                            if (deltalv < 0) {
                                def += def * Map.abs(deltalv / 6);
                                if (Map.abs(deltalv) > 5 && Map.abs(deltalv) <= 10) {
                                    att /= 2;
                                } else if (Map.abs(deltalv) > 10 && Map.abs(deltalv) <= 15) {
                                    att /= 3;
                                } else if (Map.abs(deltalv) > 15 && Map.abs(deltalv) <= 20) {
                                    att /= 10;
                                } else if (Map.abs(deltalv) > 20) {
                                    att /= 50;
                                }
                            } else if (deltalv > 0) {
                                if (Map.abs(deltalv) > 5 && Map.abs(deltalv) <= 10) {
                                    def /= 2;
                                } else if (Map.abs(deltalv) > 10 && Map.abs(deltalv) <= 15) {
                                    def /= 3;
                                } else if (Map.abs(deltalv) > 15 && Map.abs(deltalv) <= 20) {
                                    def /= 10;
                                } else if (Map.abs(deltalv) > 20) {
                                    def /= 50;
                                }
                            }
                        }
                        if (actor.getAttack() <= 0 || def <= 0) {
                            def = 0;
                        }
                        if (this.map != null && this.map.isMapChienTruongMoba() && Map.r.nextInt(100) <= c2.getBuffHeMoc()) {
                            final CharChienTruong cc = MapChienTruongMoba.getCharChienTruong(actor.getName());
                            if (cc != null && System.currentTimeMillis() - cc.timeCamLang >= 0L) {
                                cc.timeCamLang = System.currentTimeMillis() + 3000L;
                                ((Char) actor).sendInfoChienTruong(Char.ID_CAM_LANG, 3);
                            }
                        }
                    } else if (actor.cat == 1) {
                        final Monster mt = (Monster) actor;
                        isBossBaoCat = mt.isBossBaoCat();
                        if (!actor.isMonsterVantieu() && !((Monster) actor).isBoss && mt.idTemplate != 36 && mt.idTemplate != 37 && mt.idTemplate != 43 && mt.idTemplate != 46 && !this.map.isMapChienTruongMoba()) {
                            final int tempLv = actor.getLevel() - this.lvDetail.lv;
                            if (tempLv > 0) {
                                def += tempLv * 100;
                                if (Map.abs(tempLv) > 5 && Map.abs(tempLv) <= 10) {
                                    att /= 2;
                                } else if (Map.abs(tempLv) > 10 && Map.abs(tempLv) <= 15) {
                                    att /= 3;
                                } else if (Map.abs(tempLv) > 15) {
                                    att /= 10;
                                }
                            } else if (tempLv < 0) {
                                att += Map.abs(20 * tempLv);
                            }
                        }
                    }
                    if (att < 0) {
                        att = 5 + Map.r.nextInt(10);
                    }
                    if (def < 0) {
                        def = 5 + Map.r.nextInt(10);
                    }
                    if (!this.map.isMapChienTruongMoba() || (this.map.isMapChienTruongMoba() && !actor.isCharMonster())) {
                        if ((this.he + 2) % 5 == actor.he) {
                            def -= def * 5 / 100;
                            att += att * 10 / 100;
                        } else if ((this.he + 3) % 5 == actor.he) {
                            if (actor.cat == 0) {
                                def += def * 7 / 100;
                            } else if (actor.cat == 1) {
                                def += def * 10 / 100;
                            }
                            att -= att * ((actor.cat == 1) ? 2 : 5) / 100;
                        }
                    }
                    if (this.idNgtuyet == 1 && actor.cat == 1 && !((Monster) actor).isBoss) {
                        att *= 2;
                    }
                    def += def * actor.getValueLua(1) / 100;
                    att += this.getValueLua(2) * att / 100;
                    if (this.checkSuyYeu()) {
                        final EffectBuff ef = this.hashEffBuff.get(EffectBuff.SUY_YEU);
                        att -= att * ef.dam / 100;
                    }
                    int pcgiamdef = 0;
                    if (actor.checkSuyYeu()) {
                        final EffectBuff ef2 = actor.hashEffBuff.get(EffectBuff.SUY_YEU);
                        pcgiamdef = ef2.dam;
                    }
                    def -= def * pcgiamdef / 100;
                    if (c2 != null && actor.isTruMoba()) {
                        def = att * (10 - c2.getNoiLuc()) / 10;
                    }
                    int dam = att - def;
                    if (dam < 0) {
                        dam = 5 + Map.r.nextInt(10);
                    }
                    dam = dam * 110 / 100 + Map.r.nextInt() % 10;
                    if (bubbAttack > -1 && !actor.isTruMoba() && (this.charClass == 0 || bubbAttack == 1)) {
                        dam = att;
                    }
                    if (dam < 5) {
                        dam = 5 + Map.r.nextInt(10);
                    }
                    if (actor.cat == 1 && this.isFullParty() && !actor.isMonsterVantieu() && !actor.isTruMoba()) {
                        dam += dam * 20 / 100;
                    }
                    int finalDame = dam;
                    int skillDamPercent = CharManager.SKILL_DAM_PERCENT[this.charClass][idSkill][level];
                    if (actor.isTruMoba() && c2 != null && this.map.isMapChienTruongMoba() && actor.isTruMoba()) {
                        skillDamPercent = skillDamPercent * c2.getNoiLuc() / 10;
                    }
                    finalDame = finalDame * skillDamPercent / 100;
                    if (idSkill >= 3 && this.wModel != null) {
                        if (this.wModel.haveDameAnhSang() && Map.r.nextInt(100) < 20 && !actor.haveDamAnhsang()) {
                            finalDame += Map.r.nextInt(5000) + 5001;
                        }
                        if (this.wModel.haveDameBongtoi() && Map.r.nextInt(100) < 20 && !actor.haveDamBongtoi()) {
                            finalDame += Map.r.nextInt(5000) + 5001;
                        }
                        if (this.wModel.haveHutMau()) {
                            this.hp += 100;
                            MessageCreator.createMsgUseHpMP(this, 100, 1);
                        }
                        if (this.wModel.haveHoangSo() && actor.cat == 0 && Map.r.nextInt(100) < 10) {
                            boolean issend = false;
                            if (actor.addEffBuff(EffectBuff.EFF_HOANG_SO, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0) != null) {
                                actor.sendEffToChar((Char) actor);
                                issend = true;
                            }
                            if (issend) {
                                actor.sendEffToNearChar();
                            }
                        }
                    }
                    if (isBossBaoCat) {
                        if (finalDame <= 100) {
                            finalDame = this.lvDetail.lv * 50 + Map.r.nextInt() % 50;
                        } else if (finalDame > actor.maxhp * 20 / 100) {
                            finalDame = actor.maxhp * 20 / 100 + Map.r.nextInt(1000);
                        }
                    }
                    if ((actor.isMonsterVanTienTran() || actor.isBossTruRong()) && !actor.isTruMoba()) {
                        final long maxHP = actor.maxhp;
                        if (!actor.isBossBaoCat() && !actor.isBossTruRong()) {
                            int pcmin = 10 - actor.getWave();
                            int pcmmax = 15 - actor.getWave();
                            if (idSkill < 2) {
                                pcmin = idSkill + 1;
                                pcmmax = idSkill + 1;
                            }
                            if (finalDame < maxHP * pcmin / 1000L) {
                                finalDame = (int) (maxHP * pcmin / 1000L + Map.r.nextInt(100));
                            } else if (finalDame > maxHP * pcmmax / 1000L) {
                                finalDame = (int) (maxHP * pcmmax / 1000L + Map.r.nextInt(100));
                            }
                            if (idSkill >= 2) {
                                final long addmore = this.skill[idSkill];
                                finalDame += (int) (this.attack * addmore / 1000L);
                            }
                        } else if (!actor.isBossTruRong()) {
                            int pcmin = 6 - actor.getWave();
                            int pcmmax = 8 - actor.getWave();
                            if (idSkill < 2) {
                                pcmin = idSkill + 1;
                                pcmmax = idSkill + 1;
                            }
                            if (finalDame < maxHP * pcmin / 1000L) {
                                finalDame = (int) (maxHP * pcmin / 1000L + Map.r.nextInt(100));
                            } else if (finalDame > maxHP * pcmmax / 1000L) {
                                finalDame = (int) (maxHP * pcmmax / 1000L + Map.r.nextInt(100));
                            }
                            if (idSkill >= 2) {
                                final long addmore = this.skill[idSkill];
                                finalDame += (int) (this.attack * addmore / 1000L);
                                if (actor.isLienHoaTru()) {
                                    finalDame -= finalDame / 3;
                                }
                            }
                        } else {
                            int pcmin = 3 - actor.getWave() + idSkill;
                            int pcmmax = 6 - actor.getWave() + idSkill;
                            if (idSkill < 2) {
                                pcmin = 1;
                                pcmmax = 1;
                            }
                            if (finalDame < maxHP * pcmin / 10000L) {
                                finalDame = this.lvDetail.lv * 50 + Map.r.nextInt() % 50;
                            } else if (finalDame > maxHP * pcmmax / 1000L) {
                                finalDame = (int) (maxHP * pcmmax / 1000L + Map.r.nextInt(100));
                            }
                            if (idSkill >= 2) {
                                final long addmore = this.skill[idSkill];
                                finalDame += (int) (this.attack * addmore / 10000L);
                                finalDame -= finalDame / 3;
                            }
                        }
                    }
                    if (finalDame <= 0) {
                        finalDame = 1;
                    }


                    if (isLinhThueLan()) {
                        // Tăng cố định 2% sát thương và làm tròn lên
                        double bonusDamagePercent = 2.0;
                        int originalDamage = finalDame;
                        
                        // Sử dụng Math.ceil để làm tròn lên
                        finalDame = (int)Math.ceil(finalDame + (finalDame * bonusDamagePercent / 100));
                       
                    }


                    if (petUsing != null && petUsing.isPetKhi() && this.idNgtuyet == 5 && this.isCrazy == -1) {
                        finalDame *= 3;
                    }
                    if (petUsing != null && petUsing.isPetKhi() && this.idNgtuyet == 5 && this.isCrazy == -1) {
                        finalDame *= 3;
                    }
                    if (petUsing != null && ((this.map.isMapChienTruongMoba() && c2.getNoiLuc() >= 5) || !this.map.isMapChienTruongMoba())) {
                        finalDame += petUsing.getSatThuong();
                    }
                    if (Map.r.nextInt(1000) < actor.haveNeTranh()) {
                        finalDame = 0;
                    }
                    if (actor.isNgu()) {
                        actor.huyEff(this, EffectBuff.RU_NGU);
                    }
                    this.checkNewEffectPet(actor);
                    if (actor.checkBong()) {
                        final EffectBuff ef3 = actor.hashEffBuff.get(EffectBuff.BONG);
                        finalDame += finalDame * ef3.dam / 100;
                    }
                    if (this.itemVukhiThoiTrang != null && Map.r.nextInt(1000) < this.itemVukhiThoiTrang.otherAtt[56] && !actor.isBossTruRong()) {
                        final long mhp = actor.maxhp;
                        final long st = this.itemVukhiThoiTrang.otherAtt[54] * mhp / 1000L;
                        finalDame += (int) st;
                    }
                    if (actor.cat == 0) {
                        final Char p = (Char) actor;
                        if (p.getPetUsing() != null) {
                            final int[] battu = p.getPetUsing().checkBatTu(p);
                            if (battu[0] > -1) {
                                EffectBuff ef4 = null;
                                if ((ef4 = p.addEffBuff(EffectBuff.BAT_TU, System.currentTimeMillis() + battu[1], EffectBuff.BY_ACTOR, 0)) != null) {
                                    final Message msg = MessageCreator.createMsgNewEffectSkill5Long(battu[0], battu[1], p.id, p.cat, true, false);
                                    p.sendMessage(msg);
                                    p.sendToNearPlayer(msg);
                                }
                                finalDame = 0;
                            }
                        }
                    }
                    if (this.isCuMeo()) {
                        finalDame -= finalDame * this.pcGiamSatThuongByCuMeo / 100;
                    }
                    if (actor.isBossSonTinhThuyTinh()) {
                        final int d = actor.getMaxDamPerHit();
                        if (d > 0 && finalDame > d) {
                            finalDame = d;
                        }
                    }
                    if ((!this.isAdmin || !Map.adminTest) && (actor.isLienHoaTru() || (actor.isBoss() && actor.isMonsterMoba()))) {
                        TeamServer.isServerLocal();
                    }

                    if (this.damageAccumulated > 0) {
                        finalDame += this.damageAccumulated;
                        this.damageAccumulated = 0;
                    }
                    return finalDame;
                }
                if (this.itemVukhiThoiTrang == null || !this.itemVukhiThoiTrang.isGayGiangSinh()) {
                    return 0;
                }
                if (actor.getInCountry() != this.inCountry) {
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Không thể đánh rương của làng khác."));
                    return 0;
                }
                return 1;
            }
        } catch (final Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Pet getPetUsing() {
        Pet petUsing = this.petUsing;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
        }
        return petUsing;
    }

    

    @Override
    public boolean isBatTu() {
        final EffectBuff ef = this.hashEffBuff.get(EffectBuff.BAT_TU);
        return ef != null && !ef.timeOut();
    }

    public boolean isHoangLoan() {
        return this.hashEffBuff.get(EffectBuff.HOANG_LOAN) != null;
    }

    public boolean isYemBua() {
        return this.hashEffBuff.get(EffectBuff.YEM_BUA) != null;
    }

    public boolean isVetThuongSau() {
        return this.hashEffBuff.get(EffectBuff.VET_THUONG_SAU) != null;
    }

    @Override
    public boolean haveDamAnhsang() {
        if (this.wModel != null) {
            return this.wModel.haveDameAnhSang();
        }
        return super.haveDamAnhsang();
    }

    @Override
    public boolean haveDamBongtoi() {
        if (this.wModel != null) {
            return this.wModel.haveDameBongtoi();
        }
        return super.haveDamBongtoi();
    }

    public int subDam(final Char c, int finalDame) {
        if (this.isCharMagic()) {
            finalDame -= finalDame * c.attNguHanh[1] / 100;
        } else {
            finalDame -= finalDame * c.attNguHanh[0] / 100;
        }
        if (finalDame < 0) {
            finalDame = 1;
        }
        return finalDame;
    }

    public int subDam(final Monster c, int finalDame) {
        int a = 0;
        if (c.magic_physic == 0) {
            a = finalDame * this.attNguHanh[1] / 100;
        } else {
            a = finalDame * this.attNguHanh[0] / 100;
        }
        if (a < 0) {
            a = 1;
        }
        finalDame -= a;
        if (finalDame < 0) {
            finalDame = 1;
        }
        return finalDame;
    }

    public void checkExpirePet() {
        try {
            boolean expire = false;
            for (int i = 0; i < this.pet.size(); ++i) {
                if (this.pet.get(i).getTimeExpire() <= 0) {
                    expire = true;
                    this.pet.remove(i);
                }
            }
            if (expire) {
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            }
            if (this.petUsing != null && this.petUsing.getTimeExpire() <= 0) {
                this.calculateAttrib();
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                Database.instance.saveOrtherLog("tob_log_other_item", this.charname, this.petUsing.getDBInfo(), "pd");
                Database.instance.deletePet(this.petUsing.idDB);
                this.sendMessage(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.petUsing.getName()) + " đã hết thời gian sử dụng"));
                this.petUsing = null;
                this.sendMessage(MessageCreator.createCharWearingMessage(this, this));
                this.sendToNearPlayer(MessageCreator.createCharWearingMessage(this, this));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void checkExpireMDAmor() {
        for (int i = 0; i < this.wItems[this.slotWearing].length; ++i) {
            final Item item = this.wItems[this.slotWearing][i];
            if (item != null && item.getMinuteLoan(false) <= 0 && item.minuteExist > 0) {
                try {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng " + item.getTemplate().name + " đã hết"));
                    this.wItems[this.slotWearing][i] = null;
                    Database.instance.deleteItem(item.dbid);
                    this.calculateAttrib();
                    this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                    this.sendMessage(MessageCreator.createCharWearingMessage(this, this));
                    Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getDBInfo()) + "|" + item.getAttribute(), "timeout");
                } catch (final Exception ex) {
                }
            }
        }
    }

    public void checkExpireItemInentry() {
        try {
            final Vector<Item> itemExpire = new Vector<Item>();
            for (int i = 0; i < this.iItems.size(); ++i) {
                final Item item = this.iItems.get(i);
                if (item.getMinuteLoan(false) <= 0 && item.minuteExist > 0) {
                    itemExpire.add(item);
                }
            }
            if (itemExpire.size() > 0) {
                for (int i = 0; i < itemExpire.size(); ++i) {
                    try {
                        final Item item = itemExpire.get(i);
                        this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng " + item.getTemplate().name + " đã hết"));
                        this.iItems.remove(item);
                        Database.instance.deleteItem(item.dbid);
                        Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getDBInfo()) + "|" + item.getAttribute(), "timeout");
                        this.removeIDItem(item.id);
                    } catch (final Exception ex) {
                    }
                }
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
            }
        } catch (final Exception ex2) {
        }
    }

    public boolean checkDurableWeapone() {
        for (int i = 0; i < this.wItems[this.slotWearing].length; ++i) {
            final Item item = this.wItems[this.slotWearing][i];
            if (item != null) {
                final int t = item.getTemplate().type;
                final int id = item.getTemplate().id;
    
                // Bỏ qua kiểm tra nếu ID nằm trong khoảng 773-787
                if (id >= 773 && id <= 787) {
                    continue;
                }
             

    
                if (t == 6 || t == 7 || t == 4 || t == 5 || t == 3 || t == 13) {
                    if (item.getMinuteLoan(false) <= 0 && item.minuteExist > 0) {
                        try {
                            if (!TeamServer.isServerIndo()) {
                                this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian thuê vũ khí " + item.getTemplate().name + " đã hết"));
                            } else {
                                this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Masa sewa senjata  " + item.getTemplate().name + " sudah berakhir"));
                            }
                            this.wItems[this.slotWearing][i] = null;
                            Database.instance.deleteItem(item.dbid);
                            this.calculateAttrib();
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                            this.sendMessage(MessageCreator.createCharWearingMessage(this, this));
                            Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getDBInfo()) + "|" + item.getAttribute(), "timeout");
                            if (t == 13) {
                                this.itemAx = null;
                            }
                        } catch (final Exception ex) {
                        }
                        return false;
                    }
                    if (item.mDurable == 0 || item.durable == 0) {
                        if (this.luongNap >= 20000 || this.diemNapVip >= 20000) {
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }
    

  

    public int getPriceRepair(final int type) {
        final Vector<Item> wItems = this.getCharWearing();
        int pArmo = 0;
        int pWeapone = 0;
        int total = 0;
        for (int i = 0; i < wItems.size(); ++i) {
            final Item cwi = wItems.elementAt(i);
            if (cwi.getTemplate().type == 3 || cwi.getTemplate().type == 4 || cwi.getTemplate().type == 5 || cwi.getTemplate().type == 6 || cwi.getTemplate().type == 7) {
                pWeapone += cwi.getTemplate().price / 10;
            } else if (cwi.getTemplate().type == 0 || cwi.getTemplate().type == 1 || cwi.getTemplate().type == 2 || cwi.getTemplate().type == 11 || cwi.getTemplate().type == 10) {
                pArmo += cwi.getTemplate().price / 10;
            }
        }
        if (type == 0) {
            total = pArmo;
        } else if (type == 1) {
            total = pWeapone;
        } else {
            total = pArmo + pWeapone;
        }
        return total;
    }

    public void repairItem(final int id) {
        final Vector<Item> wItems = this.getCharWearing();
        int price = 5000;
        try {
            switch (id) {
                case 0: {
                    price = this.getPriceRepair(1);
                    if (this.xu < price) {
                        this.sendMessage(MessageCreator.createServerAlertMessage("Hết tiền", ""));
                        return;
                    }
                    for (final Item item : wItems) {
                        final int t = item.getTemplate().type;
                        if (t == 6 || t == 7 || t == 4 || t == 5 || t == 3) {
                            item.durable = item.getTemplate().durable;
                            item.mDurable = (short) (item.durable * 10);
                        }
                    }
                    Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(this.xu) + " sửa VK với giá " + price + " xu", "RW");
                    break;
                }
                case 1: {
                    price = this.getPriceRepair(0);
                    if (this.xu < price) {
                        this.sendMessage(MessageCreator.createServerAlertMessage(Text.HET_TIEN, ""));
                        return;
                    }
                    for (final Item item : wItems) {
                        final int t = item.getTemplate().type;
                        if (t == 0 || t == 11 || t == 2 || t == 10 || t == 1) {
                            item.durable = item.getTemplate().durable;
                            item.mDurable = (short) (item.durable * 10);
                        }
                    }
                    Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(this.xu) + " sửa GIAP với giá " + price + " xu", "RG");
                    break;
                }
                case 2: {
                    price = this.getPriceRepair(2);
                    if (this.xu < price) {
                        this.sendMessage(MessageCreator.createServerAlertMessage(Text.HET_TIEN, ""));
                        return;
                    }
                    for (final Item item : wItems) {
                        item.durable = item.getTemplate().durable;
                        item.mDurable = (short) (item.durable * 10);
                    }
                    Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(this.xu) + " sửa ALL với giá " + price + " xu", "RA");
                    break;
                }
            }
            this.calculateAttrib();
            this.xu -= price;
            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
            this.sendMessage(MessageCreator.createCharWearingMessage(this, this));
        } catch (final Exception e) {
            System.out.println("LOI TRONG HAM REPAIR ITEM");
        }
    }

    public int getDurableWeapone() {
        final Vector<Item> wItems = this.getCharWearing();
        for (final Item item : wItems) {
            final int t = item.getTemplate().type;
            if (t == 6 || t == 7 || t == 4 || t == 5 || t == 3) {
                return item.durable;
            }
        }
        return 0;
    }

    public int getDurableArmo() {
        final Vector<Item> wItems = this.getCharWearing();
        for (final Item item : wItems) {
            final int t = item.getTemplate().type;
            if (t == 0 || t == 11 || t == 2 || t == 1) {
                return item.durable;
            }
        }
        return 0;
    }

    public void downDurableWeapone() {
        final Vector<Item> wItems = this.getCharWearing();
        for (final Item item : wItems) {
            final int t = item.getTemplate().type;
            if ((t == 6 || t == 7 || t == 4 || t == 5 || t == 3) && item.mDurable > 0) {
                final Item item2 = item;
                --item2.mDurable;
                if (item.mDurable % 10 != 0) {
                    continue;
                }
                final Item item3 = item;
                --item3.durable;
            }
        }
    }

    public void downDuarable() {
        final Vector<Item> wItems = this.getCharWearing();
        for (final Item item : wItems) {
            final int t = item.getTemplate().type;
            if ((t == 0 || t == 11 || t == 2 || t == 10 || t == 1) && item.mDurable > 0) {
                final Item item2 = item;
                --item2.mDurable;
                if (item.mDurable % 10 != 0) {
                    continue;
                }
                final Item item3 = item;
                --item3.durable;
            }
        }
    }

    private void calculateAttack() {
        Vector<Item> wItems = getCharWearing();
        Pet petUsing = this.petUsing;
        Animal animalRide = this.animalRide;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
            if (c != null && c.getNoiLuc() < 7) {
                animalRide = null;
            }
        }
        int attack1 = (petUsing != null) ? petUsing.getStrength() : 0;
        // cải trang
        if (isSaTang() && (this.charClass == 0 || this.charClass == 1 || this.charClass == 3)) {
            int lv = getLevelCaiTrang();
            if (lv >= 1 && lv <= 15) {
                attack1 = attack1 + (lv * 1);
             
            }
        }
        switch (this.charClass) {
            case 0:
                attack1 += (short) ((this.strength + getEffSkillClan(3) + this.newAtb[2] + this.upAbility[0] + this.abilityKham[0] + ((animalRide != null) ? animalRide.att[5] : 0)) * 4 / 3);
                break;
            case 1:
                attack1 += (short) ((this.strength + getEffSkillClan(3) + this.newAtb[2] + this.upAbility[0] + this.abilityKham[0] + ((animalRide != null) ? animalRide.att[5] : 0)) * 11 / 10);
                if (this.skill[5] + this.addMoreLevelSkill[5] > 0) {
                    int pc = attack1 * this.buffPass[0] / 100;
                    if (pc <= 0) {
                        pc = 0;
                    }
                    attack1 += pc;
                }
                attack1 += attack1 / 2;
                break;
            case 2:
                if (isSaTang() && (this.charClass == 2)) {
                    int lv = getLevelCaiTrang();
                    if (lv >= 1 && lv <= 15) {
                    attack1 += (short) ((this.spirit + lv + getEffSkillClan(3) + this.newAtb[4] + this.upAbility[3] + this.abilityKham[2]) * 5 / 3 + ((animalRide != null) ? animalRide.att[8] : 0) + (this.strength + this.abilityKham[0]) / 15);
                    }
                } else {
                    attack1 += (short) ((this.spirit + getEffSkillClan(3) + this.newAtb[4] + this.upAbility[3] + this.abilityKham[2]) * 5 / 3 + ((animalRide != null) ? animalRide.att[8] : 0) + (this.strength + this.abilityKham[0]) / 15);
                }
                break;
            case 3:
                attack1 += (short) (this.strength + getEffSkillClan(3) + this.newAtb[2] + this.upAbility[0] + this.abilityKham[0] + ((animalRide != null) ? animalRide.att[5] : 0));
                if (this.strength >= this.agitity) {
                    attack1 = attack1 * 2 / 3;
                }
                break;
            case 4:
                if (isSaTang() && (this.charClass == 4 )) {
                    int lv = getLevelCaiTrang();
                    if (lv >= 1 && lv <= 15) {
                        attack1 += (short) ((this.agitity + lv+ getEffSkillClan(3) + this.newAtb[3] + this.upAbility[1] + this.abilityKham[1]) * 5 / 3);
                    }
                } else {
                    attack1 += (short) ((this.agitity + getEffSkillClan(3) + this.newAtb[3] + this.upAbility[1] + ((animalRide != null) ? animalRide.att[7] : 0) + this.abilityKham[1]) * 5 / 3);
                }
                break;
        }
        int attack2 = 0;
        int upAttack = 0;
        int upatkP = 0;
        for (Item item : wItems) {
            int t = (item.getTemplate()).type;
            if (item.level > this.lvDetail.lv) {
                continue;
            }
            if (t == 6 || t == 7
                    || t == 4 || t == 5
                    || t == 3) {
                attack2 += item.atb[0] + item.getValueUpByVuKhiThoiTrang(item.atb[0], this.itemVukhiThoiTrang);
                upAttack += item.lockAtb[0];
                upatkP += item.lockAtb[0];
            }
            if (t == 9) {
                attack2 += item.atb[0];
                upAttack += item.lockAtb[0];
                upatkP += item.lockAtb[0];
            }
            if (t == 8) {
                attack2 += item.atb[0];
                upAttack += item.lockAtb[0];
                upatkP += item.lockAtb[0];
            }
        }
        Vector<Item> awItems = getAnimalWearing();
        for (Item item : awItems) {
            int t = (item.getTemplate()).type;
            if (t == 17 || t == 15) {
                attack2 += item.atb[0];
                upAttack += item.lockAtb[0];
                upatkP += item.lockAtb[0];
            }
        }

        this.attackPlayer = attack1 + attack2;
        this.attack = attack1 + attack2;
        upatkP += this.attNguHanh[2];
        this.attack += this.attack * this.attNguHanh[2] / 100;
        upatkP += this.lockAtb[0];
        this.attack += this.attack * this.lockAtb[0] / 100;
        this.attack += (animalRide != null) ? (this.attack * animalRide.att[2] / 100) : 0;
        upatkP += (animalRide != null) ? animalRide.att[2] : 0;
        if (animalRide != null) {
            upAttack += animalRide.attUp[2];
            upatkP += animalRide.attUp[2];
            if (this.isFullsetAnimal >= 5) {
                upAttack += 12;
                upatkP += 12;
            } else if (this.isFullsetAnimal >= 3) {
                upAttack += 8;
                upatkP += 8;
            }
        }
        if (this.rankGov == 0 && this.rideHorse > 0) {
            upAttack += 5;
            upatkP += 5;
        }
        if (this.idNgtuyet == 3) {
            upAttack += 20;
            upatkP += 20;
        }
        if (this.timeUp5Attribute > 0L) {
            upAttack += 5;
            upatkP += 5;
        }
        if (petUsing != null) {
            upAttack += petUsing.getDoatMenh();
        }
        if (this.vip > 0) {
            upAttack += 5;
        }
        this.attack += this.attack * upAttack / 100;
        if (this.fullSet15 >= 10) {
            this.attack += this.attack * 12 / 100;
            upatkP += 12;
        } else if (this.fullSet15 >= 6) {
            this.attack += this.attack * 8 / 100;
            upatkP += 8;
        } else if (this.fullSet13 >= 10) {
            this.attack += this.attack * 4 / 100;
            upatkP += 4;
        } else if (this.fullSet10 >= 10) {
            this.attack += this.attack * 1 / 100;
            upatkP++;
        }
        if (this.wModel != null) {
            this.attack += this.attack * (this.wModel.getAttack() + ((petUsing != null) ? petUsing.getPcUpDam() : 0)) / 100;
            upatkP += this.wModel.getAttack() + ((petUsing != null) ? petUsing.getPcUpDam() : 0);
        } else {
            this.attack += this.attack * ((petUsing != null) ? petUsing.getPcUpDam() : 0) / 100;
            upatkP += (petUsing != null) ? petUsing.getPcUpDam() : 0;
        }
        int atkTubinh = 0;
        for (int i = 0; i < this.tubinh.size(); i++) {
            atkTubinh += ((TuBinhTemplate) this.tubinh.get(i)).getAttack();
        }
        this.attack += atkTubinh;
        this.attackPlayer += this.attackPlayer * upatkP / 100 + atkTubinh;
        if (this.charClass == 3) {
            this.attack += attack1 + attack2;
            this.attackPlayer += attack1 + attack2;
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null) {
                long noiluc = c.getNoiLuc();
                if (noiluc < 10L) {
                    this.attack = (int) (this.attack * noiluc / 10L + (this.attack / 20));
                    this.attackPlayer = (int) (this.attackPlayer * noiluc / 10L + (this.attackPlayer / 20));
                }
            }
        }
        // cải tạo cải trang
        if (this.wModel != null && (this.wModel.isCaiTaoMiNuong() || this.wModel.isCaiTaoSonTinh())) {
            int percentCaiTao = this.wModel.itemModel.CaiTao;
            double dameBonus = this.attack * (percentCaiTao / 100.0);
            this.attack += dameBonus;
            this.attackPlayer += dameBonus;
            System.out.println("Cải tạo cải trang: " + percentCaiTao + "%" + "dameBonus: " + dameBonus);
        }
    }

    public Char(final Session conn) {
        super((byte) 0);
        this.nameLienTram = new Vector<String>();
        this.MAX_INVENTORY = 42;
        this.idPotionUsed = -1;
        this.idTongKim = -1;
        this.maxBag = 1;
        this.idTeamTongKim = 0;
        this.dbSkill = null;
        this.dbPotion = null;
        this.dbBasic = null;
        this.dbCharQuest = null;
        this.dbInfo = null;
        this.friendListID = new Vector<Integer>();
        this.friendListName = new Vector<String>();
        this.allMsgChatRieng = new Vector<InfoChatRieng>();
        this.using = 1;
        this.plant = -1;
        this.lvHouse = 1;
        this.lvStore = 1;
        this.luong = 0;
        this.lockLuong = 0;
        this.vetangluong = 0;
        this.xu = 0L;
        this.xutim = 0L;
        this.xumat = 0L;
        this.luongxainhanRuong = 0L;
        this.speed = 5;
        this.slotWearing = 0;
        this.agenID = -1;
        this.idClan = -1;
        this.nMoonCake2Exp = 15503;
        this.ID_ITEM = -32000;
        this.partyID = -1;
        this.masterIDParty = -1;
        this.idItem = new Vector<Short>();
        this.idPotion = new Vector<Short>();
        this.idGem = new Vector<Short>();
        this.idSP = new Vector<Short>();
        this.idItemQuest = new Vector<Short>();
        this.vesoCu = new Vector<Veso>();
        this.vesoxu = new Vector<Veso>();
        this.sellerInfo = null;
        this.party = new Party();
        this.skill = new byte[15];
        this.skillClan = new short[20];
        this.skillLvClan = new byte[20];
        this.timeUseSkillClan = new long[20];
        this.isBot = -1;
        this.rankClan = 3;
        this.lvDetail = new LevelDetail();
        this.luongOpenInventory = 700;
        this.stCapcha = "";
        this.timeLastUseSkills = new long[30];
        this.nearChars = new Vector<Short>();
        this.nearMons = new Vector<Short>();
        this.arrayBuff = new byte[]{-1, -1, -1, -1, -1, -1, -1};
        this.timeOut = new short[]{30, 30, 30, 30, 30, 30, 30};
        this.countDown = new short[7];
        this.lvBuff = new byte[7];
        this.timeUseBuff = new long[7];
        this.lasTimeMove = System.currentTimeMillis() + 3000L;
        this.timeGiveCardTown = 0L;
        this.xpLost = 0L;
        this.timeUsePK = 0L;
        this.timeJoinClan = 0L;
        this.timeHoiSinh = 0L;
        this.pointCongHienClan = 0;
        this.typeConfig = 0;
        this.upSpeedNguaVanTieu = 1;
        this.giveMoneyClan = 1;
        this.isChangeMap = false;
        this.isMonster = false;
        this.idNgtuyet = -1;
        this.subCharname = "";
        this.potions = new int[162];
        this.itemquest = new short[ItemQuest.NAME_ITEM_QUEST.length];
        this.tPotions = new int[162];
        this.rPotions = new int[162];
        this.posNPCInVilage = null;
        this.attackDamage = 10;
        this.outdelay = 0;
        this.sellShopItem = new Vector<Item>();
        this.bagItems = new Vector<Item>();
        this.wItems = new Item[][]{new Item[12], new Item[12]};
        this.inbox = new Vector<CharInboxMessage>();
        this.gemItemImbue = new Vector<GemItem>();
        this.listGemitem = new int[Map.gemTemplate.length];
        this.listGemitemLock = new int[Map.gemTemplate.length];
        this.listGemitemSell = new int[Map.gemTemplate.length];
        this.allGemGet = new int[Map.gemTemplate.length];
        this.allGemGetLock = new int[Map.gemTemplate.length];
        this.allGemUse = new int[Map.gemTemplate.length];
        this.allGemUseLock = new int[Map.gemTemplate.length];
        this.specialItem = new Vector<ShopTemplate>();
        this.listSpItem = new int[2];
        this.itemImbue = 40000;
        this.mapID_the_mua_ban = -1;
        this.region_the_mua_ban = -1;
        this.isKiller = false;
        this.typeItemBuy = 2;
        this.classBuy = 0;
        this.pk_chienTruong = 0;
        this.isThodia = false;
        this.userTrade = new Vector<Char>();
        this.rideHorse = 0;
        this.nMsgChat = 0;
        this.idImgHorse = 0;
        this.isNewClient = false;
        this.gotoIsland = false;
        this.nPKill = 0;
        this.timeXP = 0;
        this.timeKiller = 0L;
        this.minuteExp = 0;
        this.x2 = 0;
        this.upAbility = new byte[5];
        this.abilityKham = new byte[18];
        this.attNguHanh = new byte[5];
        this.attNguHanhAnimal = new byte[2];
        this.isAdmin = false;
        this.nNearchar = 20;
        this.idRegionNuiChauBau = -1;
        this.idRegionLoidai = -1;
        this.autoGetItem = 0;
        this.autoSkill = 1;
        this.canGiveCard = -1;
        this.autoComeHome = 0;
        this.luot_van_tieu = 3;
        this.buy_luot_van_tieu = 3;
        this.cuop_tieu = 3;
        this.vip = 0;
        this.oldLv = -5;
        this.nHit = 0;
        this.infoItemCreate = null;
        this.itemAx = null;
        this.itemLinhDanhThue = null;
        this.timeWaitComeHome = 0L;
        this.rcvGifByHour = 0;
        this.rcvGifByMonth = 0;
        this.rcvGifByLv = 0;
        this.totalDayInMonth = 0;
        this.isOnlineToDay = false;
        this.millisOnlineStart = 0L;
        this.millisOnline = 0L;
        this.timeSave = System.currentTimeMillis() + 7200000L;
        this.timeNextRegion = -1;
        this.lastTimeNghienBot = -1;
        this.monsTerThuThap = null;
        this.itemChange5 = new Vector<Item>();
        this.itChangeColor = null;
        this.indexDxDyHorseTool = -1;
        this.idHorseTool = -1;
        this.coolDownPotion = new Hashtable<Short, PotionUse>();
        this.ALL_BUFF_INMAP = new Vector<EffectBuff>();
        this.indexHelp = 0;
        this.stHelp = new Vector<String>();
        this.fullSet15 = 0;
        this.fullSet10 = 0;
        this.fullSet13 = 0;
        this.isFullsetAnimal = 0;
        this.rcvInvite = true;
        this.rcvInviteVip = true;
        this.canJoinTranYeuTran = false;
        this.lvSearch = -1;
        this.searchChar = "";
        this.buffPass = new short[5];
        this.secondHealth = 0;
        this.idRing = 0;
        this.idBot = 0;
        this.posNPC = 0;
        this.timeSendHelp = 0L;
        this.timedie = 0L;
        this.timeCrazy = 0L;
        this.isCrazy = -1;
        this.pcGiamSatThuongByCuMeo = 0;
        this.timeExistCharHire = 0L;
        this.classlinh = 0;
        this.lvlinh = 0;
        this.lvlinhthue = 45;
        this.genderlinh = 0;
        this.typeTieu = -1;
        this.idNgtuyet2 = -1;
        this.cooldownNguyetAnh = 0L;
        this.dateStartOnline = "";
        this.charthanthu = null;
        this.timeRegentHpMp = System.currentTimeMillis();
        this.isDoChangeMap = false;
        this.idCuiDot = -1;
        this.idRuouUong = -1;
        this.isLuLan = false;
        this.countSecond = 0;
        this.timeSendAlertQuangCao = 0L;
        this.nNpc = 0;
        this.wScreen = 320;
        this.hScreen = 320;
        this.rangeAddMonster = new short[]{320};
        this.rangeRemoveMonster = new short[]{320};
        this.typeFirmware = 0;
        this.timeForbitChat = System.currentTimeMillis();
        this.timeSendAllChar = System.currentTimeMillis() + 180000L;
        this.timeBuffGiamSatThuong = 0L;
        this.countSendchat = 0;
        this.kkk = 0;
        this.timeExit = 0L;
        this.itemHoaKyLan = null;
        this.itemTam = null;
        this.itemPet = null;
        this.itemVukhiThoiTrang = null;
        this.winPH = 0;
        this.hlt_buy = 0;
        this.timePlayThamdinh = 0;
        this.countnvVulan = 0;
        this.countMonsterKill = 0;
        this.diemdoivePH = 0;
        this.reclass = 0;
        this.itemCheckDuplicate = new Vector<Item>();
        this.countDup = 0;
        this.timeUseTheMuaBan = 0L;
        this.lastLog = "2010-01-01 00:00:00";
        this.giftTop = 0;
        this.selectPhiphong = 0;
        this.idAutoSkill = 0;
        this.idAutoGetitem = 0;
        this.timeOnlineSukien = 0L;
        this.countTime = 0;
        this.infoWearing = "";
        this.itemResetSock = new Vector<Item>();
        this.treeID = new Vector<Byte>();
        this.questID = -1;
        this.nextQuest = 1;
        this.idNpc = 5;
        this.currentNpc = 0;
        this.receiveQuest = false;
        this.isFinish = false;
        this.rcvQuestClan = false;
        this.finishQC = false;
        this.talk_npc = false;
        this.char_quest = null;
        this.flower = 0;
        this.wModel = new ModelWearing();
        this.newAtb = new byte[10];
        this.addMoreLevelSkill = new byte[15];
        this.lockAtb = new byte[3];
        this.myCountry = -1;
        this.inCountry = 1;
        this.typeBox = 0;
        this.gifLuckyBox = new Vector<InfoGifLucky>();
        this.idOpen = new Vector<Byte>();
        this.nSpecialBox = 2;
        this.nNglieu = new byte[][]{new byte[4], new byte[4], new byte[4]};
        this.dayOpenXoso = "";
        this.headModel = -1;
        this.indexItemLuckyDraw = -1;
        this.timeMove = 0L;
        this.lastMovex = 0;
        this.lastMovey = 0;
        this.startAutoImbue = false;
        this.lkdLock = false;
        this.MMLock = false;
        this.BHLock = false;
        this.plus = 0;
        this.idGemLkd = -1;
        this.idGemMM = -1;
        this.idGemBH = -1;
        this.receiveLG = 64;
        this.gif35 = 0;
        this.nHopMut = -1;
        this.pArena = 1;
        this.buyAnimalArmor = -1;
        this.isCorrectPass = false;
        this.luongNap = 0L;
        this.luonglost = 0L;
        this.salaryClan = 0L;
        this.x25Exp = 0;
        this.honor = 0;
        this.idWedding = -1;
        this.idArena = -1;
        this.idFlag = -1;
        this.maxKill = 0;
        this.idGropRace = -1;
        this.idBanhNuongDoi = -1;
        this.idNglieuDoi = -1;
        this.totalKillInArena = 0;
        this.forbitChat = 0;
        this.married = 0;
        this.dayLogin = "";
        this.dayCamChat = "";
        this.receiveGiftEvent = 0;
        this.subpk = 0;
        this.rankGov = -1;
        this.usingSummons = 0;
        this.posRing = -1;
        this.countQuestClan = 0;
        this.nBinhTra = 20;
        this.gif83 = 14;
        this.ntangtoc = 0;
        this.receiveQuestVulan = 0;
        this.receiveGiftOldChar = 1;
        this.listKiller = new Vector<String>();
        this.typeGemImbue = 0;
        this.diemNapVip = 0;
        this.diemxaiVip = 0;
        this.typeGemKham = 0;
        this.xichtho_thienlyma = 1;
        this.typeHopMaterial = 0;
        this.slNgLieuDoi = 0;
        this.type_luong_doi_he = 0;
        this.myFriend = new Vector<CharInfo>();
        this.itemTemp = -40000;
        this.charCount = 0;
        this.moneyThachdau = 0;
        this.moneyTrade = 0;
        this.idItemTach = 32100;
        this.setlan = 0;
        this.banglan = 0;
        this.lualan = 0;
        this.doclan = 0;
        this.nameHusband_wife = "";
        this.pInviteWedding = "";
        this.passWord = "";
        this.name_char_loi_dai = "";
        this.idGifWedding = new Vector<Byte>();
        this.idMainQuest = 0;
        this.pointActiveQuest = 100;
        this.minuteSocola = 0;
        this.minuteOnline = 0;
        this.mainQuest = new NewCharQuest(false);
        this.subQuest = new NewCharQuest(true);
        this.clanQuestNew = new NewCharQuest(true);
        this.eventQuest = new NewCharQuest(true);
        this.itemget = "";
        this.monskilled = "";
        this.finish = "";
        this.itquest = "";
        this.equip = "";
        this.invent = "";
        this.bag = "";
        this.tuido = "";
        this.allItemBuy = new Vector<Item>();
        this.listKillme = new Vector<String>();
        this.fruit = new FruitTemplate[FruitTemplate.MAX_FRUIT];
        this.tubinh = new Vector<TuBinhTemplate>();
        this.ALL_ID_USE = new Hashtable<Short, Short>();
        this.nameCompetitor = "";
        this.allNewItem = new Vector<ItemSell>();
        this.allSearchItem = new Vector<ItemSell>();
        this.allItemBid = new Vector<ItemSell>();
        this.itemBuy = null;
        this.lastTimeOut = 0L;
        this.firstNapMoney = 0;
        this.new_search = 0;
        this.banphaohoa = 0;
        this.itemPetEat = new Vector<Item>();
        this.allDanhHieu = new Vector<DanhHieu>();
        this.currentDanhHieu = null;
        this.idEffDanhHieu = -1;
        this.canReceiveGiftOffline = -1;
        this.buychoi = 0;
        this.buylongden = 0;
        this.idPetTang = 0;
        this.typeJoinXoso = 0;
        this.hoten = null;
        this.diachi = null;
        this.nametangkm = null;
        this.nameTangPet = null;
        this.itemwait = null;
        this.nhomThidau = -1;
        this.timeHs = Char.timeAddMoreHs;
        this.timePlay = System.currentTimeMillis();
        this.startPlay = this.timePlay;
        this.gender = Char.GENDER_OF_CLAZZ[this.charClass];
        this.session = conn;
        this.initWItem();
        this.iItems = new Vector<Item>();
        this.tItems = new Vector<Item>();
        this.rItems = new Vector<Item>();
        this.awItems = new Vector<Item>();
        this.bItem = new Vector<Item>();
        this.seedsItem = new Vector<SeedItem>();
        this.gemItem = new Vector<GemItem>();
        this.animal = new Vector<Animal>();
        this.pet = new Vector<Pet>();
        this.setTimeCake();
        this.setTimeCake1();
        for (int i = 0; i < this.timeUseSkillClan.length; ++i) {
            this.timeUseSkillClan[i] = System.currentTimeMillis();
        }
        this.coolDownPotion.put((short) 1, new PotionUse(1, 350));
        this.coolDownPotion.put((short) 2, new PotionUse(2, 350));
        this.coolDownPotion.put((short) 3, new PotionUse(3, 350));
        this.coolDownPotion.put((short) 21, new PotionUse(21, 350));
        this.coolDownPotion.put((short) 22, new PotionUse(22, 350));
        this.coolDownPotion.put((short) 93, new PotionUse(93, 350));
        this.coolDownPotion.put((short) 94, new PotionUse(94, 350));
        this.coolDownPotion.put((short) 4, new PotionUse(4, 350));
        this.coolDownPotion.put((short) 5, new PotionUse(5, 350));
        this.coolDownPotion.put((short) 6, new PotionUse(6, 350));
        this.coolDownPotion.put((short) 23, new PotionUse(23, 350));
        this.coolDownPotion.put((short) 24, new PotionUse(24, 350));
        this.coolDownPotion.put((short) 95, new PotionUse(95, 350));
        this.coolDownPotion.put((short) 96, new PotionUse(96, 350));
        this.coolDownPotion.put((short) 126, new PotionUse(126, 350));
        this.coolDownPotion.put((short) 127, new PotionUse(127, 350));
        this.listGemitem = new int[Map.gemTemplate.length];
        this.listGemitemLock = new int[Map.gemTemplate.length];
        this.listGemitemSell = new int[Map.gemTemplate.length];
        this.allGemGet = new int[Map.gemTemplate.length];
        this.allGemGetLock = new int[Map.gemTemplate.length];
        this.allGemUse = new int[Map.gemTemplate.length];
        this.allGemUseLock = new int[Map.gemTemplate.length];
    }

    public void initWItem() {
    }

    public void setTimeCake() {
        int[] tick = {10000, 9000, 8000, 10000, 10000, 10000, 10000, 11000, 12000};
        this.timeDropCake = System.currentTimeMillis() + tick[Map.r.nextInt(tick.length)];
        if (isSuKienNoel()) {
            tick = new int[]{30000, 60000, 75000, 80000, 81000, 85000};
            this.timeDropCake = System.currentTimeMillis() + tick[Map.r.nextInt(tick.length)];
        }
    }

    public void setTimeCake1() {
        int[] tick = {60000, 120000, 150000, 160000, 162000, 170000};
        this.timeDropCake1 = System.currentTimeMillis() + tick[Map.r.nextInt(tick.length)];
        if (isSuKienNoel()) {
            tick = new int[]{7200000, 7320000, 7350000, 7360000, 7362000, 7370000};
            this.timeDropCake1 = System.currentTimeMillis() + tick[Map.r.nextInt(tick.length)];
        }
    }

    public void setTimeCake2() {
        final int[] tick = {29, 29, 30, 30, 31, 31};
    }

    public Message sendBuffToUser(final Char ch) {
        final Message m = new Message(51);
        try {
            m.dos.writeByte(2);
            m.dos.writeShort(ch.id);
            m.dos.write(ch.arrayBuff, 0, ch.arrayBuff.length);
            for (int i = 0; i < ch.arrayBuff.length; ++i) {
                m.dos.writeShort(ch.timeOut[i] - ch.countDown[i]);
            }
            m.dos.write(ch.lvBuff, 0, ch.lvBuff.length);
        } catch (final Exception ex) {
        }
        return m;
    }

    public Message sendRemoveBuffToUser(final Char ch) {
        final Message m = new Message(51);
        try {
            m.dos.writeByte(2);
            m.dos.writeShort(ch.id);
            m.dos.write(ch.arrayBuff, 0, ch.arrayBuff.length);
            for (int i = 0; i < ch.arrayBuff.length; ++i) {
                m.dos.writeShort(1);
            }
            m.dos.write(ch.lvBuff, 0, ch.lvBuff.length);
        } catch (final Exception ex) {
        }
        return m;
    }

    public int[] setTimeOutBuff() {
        try {
            final byte[][] temp = {{4, 5}, {1, 1, 6}, {5, 5, 6, 3}, {0, 1, 6}, {2, 3, 6}};
            for (int i = 0; i < temp[this.charClass].length; ++i) {
                if (this.skill[i + 4] + this.addMoreLevelSkill[i + 4] != -1) {
                    this.timeOut[temp[this.charClass][i]] = CharManager.TIME_LIFE_BUFF_SKILL[temp[this.charClass][i]][this.skill[i + 4] + this.addMoreLevelSkill[i + 4]];
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean calculatorHPMP() {
        boolean rs = false;
        if (this.hp > this.maxhp + this.percentBuff[2]) {
            this.hp = this.maxhp + this.percentBuff[2];
            rs = true;
        }
        if (this.mp > this.maxmp + this.percentBuff[1]) {
            this.mp = this.maxmp + this.percentBuff[1];
            rs = true;
        }
        return rs;
    }

    public void resetGoldTimeCountry() {
        try {
            if (this.x2 != 0) {
                final Message m = new Message(86);
                m.dos.writeByte(2);
                m.dos.writeByte(this.x2);
                m.dos.writeInt(this.getGoldTime());
                m.dos.writeUTF(this.getInfoX2());
                this.sendMessage(m);
            } else {
                final Message m = new Message(86);
                m.dos.writeByte(0);
                this.sendMessage(m);
            }
        } catch (final Exception ex) {
        }
    }

    private void updateGoldTime() {
        final int time = 86400000;
        try {
            if (this.x2 == 1) {
                if (System.currentTimeMillis() - this.halfGoldTime > time) {
                    this.x2 = 0;
                    this.halfGoldTime = 0L;
                    this.fullGoldTime = 0L;
                    final Message m = new Message(86);
                    m.dos.writeByte(0);
                    this.sendMessage(m);
                    m.cleanup();
                }
            } else if (this.x2 == 2) {
                if (this.minuteExp > 0) {
                    if (System.currentTimeMillis() - this.fullGoldTime > 60000L) {
                        --this.minuteExp;
                        this.fullGoldTime = System.currentTimeMillis();
                        if (this.minuteExp <= 0) {
                            this.x2 = 0;
                            this.fullGoldTime = 0L;
                            this.minuteExp = 0;
                            final Message m = new Message(86);
                            m.dos.writeByte(0);
                            this.sendMessage(m);
                        }
                    }
                } else if (System.currentTimeMillis() - this.fullGoldTime > time) {
                    this.x2 = 0;
                    this.halfGoldTime = 0L;
                    this.fullGoldTime = 0L;
                    final Message m = new Message(86);
                    m.dos.writeByte(0);
                    this.sendMessage(m);
                    m.cleanup();
                }
            }
            if ((this.x25Exp > 0 || this.x2Phaohoa > 0) && System.currentTimeMillis() - this.startPlay > 1000L) {
                if (this.x25Exp > 0) {
                    --this.x25Exp;
                    this.startPlay = System.currentTimeMillis();
                    if (this.x25Exp % 900 == 0) {
                        this.sendMessage(MessageCreator.createMsgChat(this.id, "Thời gian nhận được 150% kinh nghiệm của bạn còn lại " + this.x25Exp / 60 + " phút"));
                    }
                    if (this.x25Exp == 0) {
                        try {
                            this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng vé giờ vàng tăng 150% kinh nghiệm đẵ hết"));
                        } catch (final Exception ex) {
                        }
                    }
                }
                if (this.x2Phaohoa > 0) {
                    --this.x2Phaohoa;
                    if (this.x2Phaohoa % 900 == 0) {
                        this.sendMessage(MessageCreator.createMsgChat(this.id, "Thời gian nhận được 50% kinh nghiệm của bạn còn lại " + this.x2Phaohoa / 60 + " phút"));
                    }
                }
            }
        } catch (final Exception ex2) {
        }
    }

    public void doSetTimeAutoHoiSinh() {
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            this.doSetTimeAutoHoiSinhMapMoba();
        } else if (this.timeHoiSinh == 0L && this.lvDetail.lv < 50 && (this.map.isMapTrain() || this.map.isMapBoss())) {
            this.timeHoiSinh = System.currentTimeMillis() + this.timeHs;
            if (this.isKiller) {
                this.timeHs += Char.timeAddMoreHs * 2;
            } else {
                this.timeHs += Char.timeAddMoreHs;
            }
        }
    }

    public void doSetTimeAutoHoiSinhMapMoba() {
        if (this.timeHoiSinh == 0L && this.map != null && (this.map.isMapChienTruongMoba() || this.map.isMapLoiDai())) {
            if (this.map.isMapLoiDai()) {
                final RegionLoiDai region = this.map.getRegionLoiDai(this.idRegionLoidai);
                if (!region.isFinish) {
                    region.isFinish(this, false);
                    this.timeHoiSinh = System.currentTimeMillis() + 5000L;
                }
            } else {
                final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
                if (c != null) {
                    this.timeHoiSinh = System.currentTimeMillis() + c.getNoiLuc() * 5 * 1000;
                    this.sendInfoChienTruong(Char.ID_HOI_SINH, c.getNoiLuc() * 5);
                    c.lientram = 0;
                    c.resetTimeBuff();
                }
            }
        }
    }

    @Override
    public int getPointChienTruong() {
        return 3;
    }

    @Override
    public void actorDie() 
    {
        System.out.println("actor die: " + charname);
        Thread.dumpStack();  // <-- In ra nơi gọi đến phương thức này        
        try 
        {
            if (this.map != null) 
            {
                this.map.checkTrade(this);
            }
            this.timeOutPoinson = 0L;
            this.poinson = 0;
            this.hpDocLan = 0;
            this.isThodia = false;
            this.finishTrade = false;
            this.userTrade.removeAllElements();
            this.tItems.removeAllElements();
            this.rItems.removeAllElements();
            final Message m = new Message(90);
            m.dos.writeShort(this.id);
            m.dos.writeByte(this.cat);
            this.sendMessage(m);
            this.sendToNearPlayer(m);
            m.cleanup();
            if (this.map != null && (this.map.isMapChienTruongMoba() || this.map.isMapLoiDai())) 
            {
                this.doSetTimeAutoHoiSinhMapMoba();
            } else 
            {
                this.doSetTimeAutoHoiSinh();
            }
            this.timedie = System.currentTimeMillis();
            if (this.timeWaitComeHome == 0L) {
                this.timeWaitComeHome = this.timedie;
            }
            this.partyIsDie(this, this.mapID);
            if (this.isMonster || this.isNguoiTuyet()) 
            {
                this.map.charMonsterDissapear(this);
                this.map.removeCharOutEventMonster(this);
            }

            this.hashEffBuff.clear();
            this.huyAllEff(this);
            this.AllEffBuff.removeAllElements();
        } 
        catch (final Exception ex) 
        {
            System.out.println("Loi khi nhan vat die: " + charname + ", eff còn lại: " + AllEffBuff.size());
            ex.printStackTrace();
        }
    }

    //doiten
    public boolean isNhom() {
    return this.partyID != -1 ;
}

    public void setInfoHelp(final short idBot) {
        if (this.indexHelp >= Text.help.length || this.indexHelp == 0) {
            this.indexHelp = 0;
            this.timeSendHelp = System.currentTimeMillis();
        }
        this.idBot = idBot;
    }

    public synchronized void isCrazyChangeMap(final short id) {
        if (this.timeCrazy > 0L) {
            if (this.isCrazy != -1) {
                this.calculateAttrib();
            }
            if (id == 0) {
                this.defend_magic *= 2;
                this.defend_physic *= 2;
                this.attack *= 2;
                this.accurate *= 2;
                this.critical *= 2;
                this.dodge *= 2;
                this.isCrazy = id;
                this.defendMaPlayer *= 2;
                this.defendVatPlayer *= 2;
                this.attackPlayer *= 2;
            } else if (id == 1) {
                this.defend_magic = this.defend_magic * 2 + this.defend_magic / 2;
                this.defend_physic = this.defend_physic * 2 + this.defend_physic / 2;
                this.attack = this.attack * 2 + this.attack / 2;
                this.accurate = (short) (this.accurate * 2 + this.accurate / 2);
                this.critical = (short) (this.critical * 2 + this.critical / 2);
                this.dodge = (short) (this.dodge * 2 + this.dodge / 2);
                this.isCrazy = id;
                this.defendMaPlayer = this.defendMaPlayer * 2 + this.defendMaPlayer / 2;
                this.defendVatPlayer = this.defendVatPlayer * 2 + this.defendVatPlayer / 2;
                this.attackPlayer = this.attackPlayer * 2 + this.attackPlayer / 2;
            }
        } else {
            this.calculateAttrib();
            this.isCrazy = -1;
        }
    }

    public synchronized void isCrazy(final short id) {
        if (this.timeCrazy > 0L) {
            if (this.isCrazy == -1 || this.isCrazy != id) {
                if (this.isCrazy != -1) {
                    this.calculateAttrib();
                }
                if (id == 0) {
                    this.defend_magic *= 2;
                    this.defend_physic *= 2;
                    this.attack *= 2;
                    this.accurate *= 2;
                    this.critical *= 2;
                    this.dodge *= 2;
                    this.isCrazy = id;
                    this.defendMaPlayer *= 2;
                    this.defendVatPlayer *= 2;
                    this.attackPlayer *= 2;
                } else if (id == 1) {
                    this.defend_magic = this.defend_magic * 2 + this.defend_magic / 2;
                    this.defend_physic = this.defend_physic * 2 + this.defend_physic / 2;
                    this.attack = this.attack * 2 + this.attack / 2;
                    this.accurate = (short) (this.accurate * 2 + this.accurate / 2);
                    this.critical = (short) (this.critical * 2 + this.critical / 2);
                    this.dodge = (short) (this.dodge * 2 + this.dodge / 2);
                    this.isCrazy = id;
                    this.defendMaPlayer = this.defendMaPlayer * 2 + this.defendMaPlayer / 2;
                    this.defendVatPlayer = this.defendVatPlayer * 2 + this.defendVatPlayer / 2;
                    this.attackPlayer = this.attackPlayer * 2 + this.attackPlayer / 2;
                }
            }
        } else {
            this.calculateAttrib();
            this.isCrazy = -1;
        }
    }

    public void selectionSort(final Vector<Integer> a, final Vector<Integer> d) {
        for (int j = 0; j < a.size() - 1; ++j) {
            int iMin = j;
            for (int i = j + 1; i < a.size(); ++i) {
                if (a.get(i) < a.get(iMin)) {
                    iMin = i;
                }
            }
            if (iMin != j) {
                final int b = a.get(iMin);
                final int c = d.get(iMin);
                a.setElementAt(a.elementAt(j), iMin);
                a.setElementAt(b, j);
                d.setElementAt(d.elementAt(j), iMin);
                d.setElementAt(c, j);
            }
        }
    }

    public int getUpPCBangSetDocLuaLan(final int type) {
        if (this.wModel != null) {
            if (type == 0) {
                return ((this.wModel.wpModel != null) ? this.wModel.wpModel.otherAtt[8] : 0) + ((this.wModel.matNa != null) ? this.wModel.matNa.otherAtt[8] : 0) + ((this.wModel.CanhModel != null) ? this.wModel.CanhModel.otherAtt[8] : 0);
            }
            if (type == 1) {
                return ((this.wModel.wpModel != null) ? this.wModel.wpModel.otherAtt[9] : 0) + ((this.wModel.matNa != null) ? this.wModel.matNa.otherAtt[9] : 0) + ((this.wModel.CanhModel != null) ? this.wModel.CanhModel.otherAtt[9] : 0);
            }
            if (type == 2) {
                return ((this.wModel.wpModel != null) ? this.wModel.wpModel.otherAtt[10] : 0) + ((this.wModel.matNa != null) ? this.wModel.matNa.otherAtt[10] : 0) + ((this.wModel.CanhModel != null) ? this.wModel.CanhModel.otherAtt[10] : 0);
            }
            if (type == 3) {
                return ((this.wModel.wpModel != null) ? this.wModel.wpModel.otherAtt[29] : 0) + ((this.wModel.matNa != null) ? this.wModel.matNa.otherAtt[29] : 0) + ((this.wModel.CanhModel != null) ? this.wModel.CanhModel.otherAtt[29] : 0);
            }
        }
        if (this.itemVukhiThoiTrang != null && this.itemVukhiThoiTrang.isChoiLuaLanBienHinh()) {
            return this.itemVukhiThoiTrang.otherAtt[29];
        }
        return 0;
    }

    public int getUpDamBangSetDocLuaLan(final int type) {
        if (this.wModel != null && this.wModel.wpModel != null) {
            if (type == 0) {
                return this.wModel.wpModel.otherAtt[12];
            }
            if (type == 1) {
                return this.wModel.wpModel.otherAtt[13];
            }
            if (type == 2) {
                return this.wModel.wpModel.otherAtt[11];
            }
            if (type == 3) {
                return this.wModel.wpModel.otherAtt[30];
            }
        }
        if (this.itemVukhiThoiTrang != null && this.itemVukhiThoiTrang.isChoiLuaLanBienHinh()) {
            return this.itemVukhiThoiTrang.otherAtt[30];
        }
        return 0;
    }

    public static int getIndexRandomXoso(final int... num) {
        try {
            final Random r = new Random(System.currentTimeMillis());
            int max = 0;
            for (int i = 0; i < num.length; ++i) {
                max += num[i];
            }
            final int intRandom = r.nextInt(max) + 1;
            int percent = 0;
            for (int j = 0; j < num.length; ++j) {
                percent += num[j];
                if (percent >= intRandom) {
                    return j;
                }
            }
        } catch (final Exception ex) {
        }
        return -1;
    }

    public static int getIndexRandom(final Vector<Short> num) {
        try {
            int max = 0;
            for (int i = 0; i < num.size(); ++i) {
                max += num.get(i);
            }
            final int intRandom = Map.r.nextInt(max) + 1;
            int percent = 0;
            for (int j = 0; j < num.size(); ++j) {
                percent += num.get(j);
                if (percent >= intRandom) {
                    return j;
                }
            }
        } catch (final Exception ex) {
        }
        return -1;
    }

    public static int getIndexRandom(final int... num) {
        try {
            int max = 0;
            for (int i = 0; i < num.length; ++i) {
                max += num[i];
            }
            final int intRandom = Map.r.nextInt(max) + 1;
            int percent = 0;
            for (int j = 0; j < num.length; ++j) {
                percent += num[j];
                if (percent >= intRandom) {
                    return j;
                }
            }
        } catch (final Exception ex) {
        }
        return -1;
    }

    @Override
    public boolean isKhangLan(final int type, int giamKhang) {
        if (type == 1) {
            return this.khangSetLan(giamKhang);
        }
        if (type == 0) {
            return this.khangBangLan(giamKhang);
        }
        if (type == 2) {
            return this.khangDocLan(giamKhang);
        }
        return this.khangLuaLan(giamKhang);
    }

    @Override
    public boolean isActiveTienKhi() {
        return this.hashEffBuff.get(EffectBuff.EFF_TIEN_KHI) != null;
    }

  
    @Override
    public void checkActiveSkillPet() {
        Pet petUsing = this.petUsing;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
        }
        if (petUsing != null) {
            int[] a = petUsing.getTienKhi();
            if ((Map.r.nextInt(100) < a[0] || (TeamServer.isServerLocal() && a[0] > 0)) && this.addEffBuff(EffectBuff.EFF_TIEN_KHI, System.currentTimeMillis() + a[1], EffectBuff.BY_ACTOR, 0) != null) {
                final Message msg = MessageCreator.createMsgNewEffectSkill5Long(EffectBuff.EFF_TIEN_KHI, a[1], this.id, this.cat, false, false);
                this.sendMessage(msg);
                this.sendToNearPlayer(msg);
            }
            a = petUsing.getPhatTrien();
            if ((Map.r.nextInt(100) < a[1] || (TeamServer.isServerLocal() && a[1] > 0)) && this.addEffBuff(EffectBuff.EFF_PHA_TRIEN, System.currentTimeMillis() + a[2], EffectBuff.BY_ACTOR, a[0]) != null) {
                final Message msg = MessageCreator.createMsgNewEffectSkill5Long(EffectBuff.EFF_PHA_TRIEN, a[2], this.id, this.cat, false, false);
                this.sendMessage(msg);
                this.sendToNearPlayer(msg);
            }
        }
    }

    public void checkNewEffectPet(final LiveActor beAttack) {
        boolean issend = false;
        beAttack.checkActiveSkillPet();
        if (beAttack.isActiveTienKhi() || beAttack.isActive8sRongLon()) {
            return;
        }
        if (beAttack.isBossSonTinhThuyTinh()) {
            return;
        }
        Pet petUsing = this.petUsing;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
        }
        if (petUsing != null) {
            int pc = 0;
            if (petUsing.isPetCu()) {
                pc = petUsing.getPcRuNgu();
                if (Map.r.nextInt(1000) < pc && beAttack.addEffBuff(EffectBuff.RU_NGU, System.currentTimeMillis() + 10000L, EffectBuff.BY_ACTOR, 0) != null) {
                    if (beAttack.cat == 0) {
                        beAttack.sendEffToChar((Char) beAttack);
                    }
                    issend = true;
                }
                if (beAttack.cat == 0) {
                    final Char p = (Char) beAttack;
                    if (!p.isBienHinh()) {
                        final int[] bienhinh = petUsing.checkBienHinh();
                        if (bienhinh[0] > -1) {
                            beAttack.setNguoiTuyet(6);
                            this.timeNguoiTuyet = System.currentTimeMillis() + bienhinh[0];
                            this.pcGiamSatThuongByCuMeo = (byte) bienhinh[1];
                        }
                    }
                }
            } else if (petUsing.isPetRong()) {
                pc = petUsing.getPcChoang();
                if (Map.r.nextInt(1000) < pc && beAttack.addEffBuff(EffectBuff.CHOANG, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0) != null && beAttack.cat == 0) {
                    beAttack.sendEffToChar((Char) beAttack);
                    issend = true;
                }
                if (beAttack.cat == 0) {
                    final Char p = (Char) beAttack;
                    final int[] bienhinh = petUsing.checkHoangSo();
                    if (bienhinh[0] > -1) {
                        EffectBuff ef = null;
                        if ((ef = beAttack.addEffBuff(EffectBuff.HOANG_LOAN, System.currentTimeMillis() + bienhinh[1], EffectBuff.BY_ACTOR, 0)) != null) {
                            final Message msg = MessageCreator.createMsgNewEffectSkill5Long(bienhinh[0], bienhinh[1], p.id, p.cat, false, false);
                            p.sendMessage(msg);
                            p.sendToNearPlayer(msg);
                        }
                    }
                }
            } else if (petUsing.isPetYeuTinh()) {
                pc = petUsing.getPcBong();
                if (Map.r.nextInt(1000) < pc) {
                    EffectBuff ef2 = null;
                    if ((ef2 = beAttack.addEffBuff(EffectBuff.BONG, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0)) != null) {
                        if (beAttack.cat == 0) {
                            beAttack.sendEffToChar((Char) beAttack);
                            issend = true;
                        }
                        ef2.dam = petUsing.getPcUpSatThuongBong();
                    }
                }
                if (beAttack.cat == 0) {
                    final Char p = (Char) beAttack;
                    final int[] bienhinh = petUsing.checkYemBua();
                    if (bienhinh[0] > -1) {
                        EffectBuff ef = null;
                        if ((ef = beAttack.addEffBuff(EffectBuff.YEM_BUA, System.currentTimeMillis() + bienhinh[1], EffectBuff.BY_ACTOR, 0)) != null) {
                            final Message msg = MessageCreator.createMsgNewEffectSkill5Long(bienhinh[0], bienhinh[1], p.id, p.cat, false, false);
                            p.sendMessage(msg);
                            p.sendToNearPlayer(msg);
                            final int mplost = this.mp * bienhinh[2] / 100;
                            this.mp -= mplost;
                            if (this.mp < 0) {
                                this.mp = 0;
                            }
                            MessageCreator.createMsgUseHpMP(this, -mplost, 4);
                        }
                    }
                }
            } else if (petUsing.isPetDoi()) {
                pc = petUsing.getpcTrungDoc();
                if (Map.r.nextInt(1000) < pc) {
                    EffectBuff ef2 = null;
                    if ((ef2 = beAttack.addEffBuff(EffectBuff.TRUNG_DOC, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0)) != null) {
                        if (beAttack.cat == 0) {
                            beAttack.sendEffToChar((Char) beAttack);
                            issend = true;
                        }
                        ef2.dam = petUsing.getDoc();
                    }
                }
                if (beAttack.cat == 0) {
                    final Char p = (Char) beAttack;
                    final int[] bienhinh = petUsing.checkVetThuongSau();
                    if (bienhinh[0] > -1) {
                        EffectBuff ef = null;
                        if ((ef = beAttack.addEffBuff(EffectBuff.VET_THUONG_SAU, System.currentTimeMillis() + bienhinh[1], EffectBuff.BY_ACTOR, 0)) != null) {
                            final Message msg = MessageCreator.createMsgNewEffectSkill5Long(bienhinh[0], bienhinh[1], p.id, p.cat, false, false);
                            p.sendMessage(msg);
                            p.sendToNearPlayer(msg);
                        }
                    }
                }
            } else if (petUsing.isPetDaiBang()) {
                pc = petUsing.getpcSuyYeu();
                if (Map.r.nextInt(1000) < pc) {
                    EffectBuff ef2 = null;
                    if ((ef2 = beAttack.addEffBuff(EffectBuff.SUY_YEU, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0)) != null) {
                        if (beAttack.cat == 0) {
                            beAttack.sendEffToChar((Char) beAttack);
                            issend = true;
                        }
                        ef2.dam = petUsing.getPcTacDongSuyYeu();
                    }
                }
            }
        }
        if (issend) {
            beAttack.sendEffToNearChar();
        }
    }

    // todo tính toán giảm kháng
    private int calGiamKhangLan(int type) {

        if (this.animalRide != null) {
            return this.animalRide.getGiamKhangLan(type);
        }

        return 0;
    }

    public void checkNewEffectItem(final int cat, long damage, final LiveActor attacker) {
        final int[] sl = {8, 15, 8, 8};
        final int pc = Map.r.nextInt(1000) + 1;
        int id = 0;
        final int setlan = this.setlan * this.getXSetLan(cat) + this.getUpPCBangSetDocLuaLan(0) + this.setlan * this.getPCXbangSetDocLuaLan() / 100;
        final int banglan = this.banglan * this.getXBangLan(cat) + this.getUpPCBangSetDocLuaLan(2) + this.banglan * this.getPCXbangSetDocLuaLan() / 100;
        final int doclan = this.doclan * this.getXDocLan(cat) + this.getUpPCBangSetDocLuaLan(1) + this.doclan * this.getPCXbangSetDocLuaLan() / 100;
        final int lualan = this.lualan * this.getXLuaLan(cat) + this.getUpPCBangSetDocLuaLan(3) + this.lualan * this.getPCXbangSetDocLuaLan() / 100;
        final int[] getindex = {banglan, setlan, doclan, lualan};
        id = getIndexRandom(getindex);
        if (id == -1) {
            return;
        }
        if (pc > getindex[id]) {
            return;
        }
        
    
//    /* ---------------- KIỂM TRA CHÊNH LỆCH LEVEL (±5) ---------------- */
//    if (attacker != null) {
//        int attackerLevel = -1;
//
//        if (attacker.cat == 0) { // Attacker là người chơi
//            attackerLevel = ((Char) attacker).lvDetail.lv;
//            if (attackerLevel != -1) {
//                int diff = Math.abs(this.lvDetail.lv - attackerLevel);
//                if (diff > 10) {
//                    return; // Chênh quá 5 cấp ⇒ không xử lý
//                }
//            }
//        if (attacker.cat == 1) { // Attacker là quái / boss
//            attackerLevel = ((Monster) attacker).level;
//            if (attackerLevel != -1) {
//                int diff = Math.abs(this.lvDetail.lv - attackerLevel);
//                if (diff > 5) {
//                    return; // Chênh quá 5 cấp ⇒ không xử lý
//                }
//            }
//        }
//    }
    
        // tính toán giảm kháng từ pet cưỡi
        int giamKhangLan = this.animalRide == null ? 0 : this.animalRide.getGiamKhangLan(id);

        final String info = String.valueOf(Char.namelan[id]) + ":";
        String infokhang = "";
        final Vector<LiveActor> allnewar = this.map.getAllNearActor(this, cat, sl[id], attacker);
        final Vector<Integer> dam = new Vector<Integer>();
        final Vector<Integer> hpLeft = new Vector<Integer>();
        final Vector<LiveActor> allactordie = new Vector<LiveActor>();
        int totalxp = 0;
        for (int i = 0; i < allnewar.size(); ++i) 
        {
            if (i == 10 ) break; // Chỉ tính 10 mục tiêu
            final LiveActor actor = allnewar.get(i);
            if (!actor.isRuongMaquai() && !actor.isNgocRong() && !actor.isBossSonTinh() && !actor.isBossThuyTinh() ) 
            {
                if (!actor.isBossSonTinhThuyTinh()) 
                {
                    if (!actor.isActiveTienKhi() || !actor.isActive8sRongLon()) 
                    {

                        if (actor.isKhangLan(id, giamKhangLan) || actor.isBoss())  
                        {
                            if (actor.cat == 0 && ((Char) actor).timeGiveCardTown > 0L) 
                            {
                                infokhang = String.valueOf(actor.getName()) + " KHANG DC " + Char.namelan[id] + ",bl=" + this.khangbanglan + ",dl=" + this.khangsetlan + ",dl=" + this.khangdoclan + ",ll=" + this.khanglualan;
                                Database.instance.saveOrtherLog("", actor.getName(), infokhang, "khanglan");
                            }
                            dam.add(0);
                            hpLeft.add(actor.hp);
                        } 
                        else if (id == 2) 
                        {
                            dam.add(0);
                            int d = actor.hp / 2;
                            if (actor.isBoss() || actor.isCongThanh()) 
                            {
                                dam.add(0);
                                hpLeft.add(actor.hp);
                            }
                            if (d <= 0) 
                            {
                                d = 1;
                            }
                            d += d * this.getUpDamBangSetDocLuaLan(2) / 100;
                            dam.add(d);
                            if (cat == 1) 
                            {
                                totalxp += actor.getXpReceive(d);
                                final Monster ms = (Monster) actor;
                                if (ms.target == null) {
                                    ms.target = this;
                                }
                            }
                            if (!this.isBatTu()) 
                            {
                                final LiveActor liveActor = actor;
                                liveActor.hp -= d;
                            }
                            hpLeft.add(actor.hp);
                            if (actor.hp <= 0) 
                            {
                                allactordie.add(actor);
                            } 
                            else 
                            {
                                try 
                                {
                                    actor.timeOutPoinson = System.currentTimeMillis();
                                    if (damage > 32768L) 
                                    {
                                        damage = 32760L;
                                    }
                                    damage += damage * this.getUpDamBangSetDocLuaLan(1) / 100L;
                                    if (damage <= 0L) 
                                    {
                                        damage = 1L;
                                    }
                                    if (damage > 32768L) 
                                    {
                                        damage = 32760L;
                                    }
                                    if (actor.cat == 0 && actor.getAnimalRide() != null && actor.getAnimalRide().isNguaXuong() && Map.r.nextInt(100) < 95) 
                                    {
                                        actor.hpDocLan = (short) (damage / 2L);
                                    } 
                                    else 
                                    {
                                        actor.poinson = (short) damage;
                                    }
                                    actor.tDelay = 1;
                                    actor.totalTime = 5;
                                    final Message m = new Message(89);
                                    m.dos.writeShort(actor.id);
                                    m.dos.writeByte(actor.cat);
                                    m.dos.writeByte(1);
                                    m.dos.writeShort((short) damage);
                                    m.dos.writeByte(4);
                                    m.dos.writeByte(4);
                                    m.dos.writeByte(10);
                                    this.sendMessage(m);
                                    this.sendToNearPlayer(m);
                                } catch (final Exception ex) {
                                }
                                if (cat == 1) {
                                    final Monster ms = (Monster) actor;
                                    if (ms.target == null) {
                                        ms.target = this;
                                    }
                                }
                            }
                        } 
                        else if (id == 0) 
                        {
                            int d = actor.hp / 2;
                            if (actor.isBoss() || actor.isCongThanh()) 
                            {
                                d = actor.hp / 100;
                            }
                            if (d <= 0) {
                                d = 1;
                            }
                            d += d * this.getUpDamBangSetDocLuaLan(2) / 100;
                            dam.add(d);
                            if (cat == 1) 
                            {
                                totalxp += actor.getXpReceive(d);
                                final Monster ms = (Monster) actor;
                                if (ms.target == null) 
                                {
                                    ms.target = this;
                                }
                            }
                            if (!this.isBatTu()) 
                            {
                                final LiveActor liveActor2 = actor;
                                liveActor2.hp -= d;
                            }
                            hpLeft.add(actor.hp);
                            if (actor.hp <= 0) 
                            {
                                allactordie.add(actor);
                            } 
                            else {
                                int khangdongbang = 0;
                                if (cat == 0) {
                                    final Char c = (Char) actor;
                                    if (c.animalRide != null && (c.animalRide.isPhuongHoangLua() || c.animalRide.isRongLon()) && c.animalRide.KHANG_DONG_BANG > 0) {
                                        khangdongbang = 95 - (this.animalRide == null ? 0 : this.animalRide.getGiamKhangDongBang());
                                    }
                                }
                                if (Map.r.nextInt(100) >= khangdongbang) {
                                    actor.khamEff[3] = System.currentTimeMillis() / 1000L;
                                    this.sendMessage(this.sendEffKham(actor));
                                    this.sendToNearPlayer(this.sendEffKham(actor));
                                }
                            }
                        } else if (id == 1) {
                            int d = actor.hp / 2;
                            if (actor.isBoss() || actor.isCongThanh()) {
                                d = actor.hp / 100;
                            }
                            d += d * this.getUpDamBangSetDocLuaLan(0) / 100;
                            if (d <= 0) {
                                d = 1;
                            }
                            dam.add(d);
                            if (cat == 1) {
                                totalxp += actor.getXpReceive(d);
                                final Monster ms = (Monster) actor;
                                if (ms.target == null) {
                                    ms.target = this;
                                }
                            }
                            if (!this.isBatTu()) {
                                final LiveActor liveActor3 = actor;
                                liveActor3.hp -= d;
                            }
                            hpLeft.add(actor.hp);
                            if (actor.hp <= 0) {
                                allactordie.add(actor);
                            } else if (actor.cat == 0) {
                                if (actor.getAnimalRide() != null && actor.getAnimalRide().isHeo() && Map.r.nextInt(100) < 95) {
                                    if (this.addEffBuff(EffectBuff.CHOANG, System.currentTimeMillis() + 3000L, EffectBuff.BY_ACTOR, 0) != null) {
                                        this.sendEffToChar(this);
                                        this.sendEffToNearChar();
                                    }
                                } else if (actor.addEffBuff(EffectBuff.CHOANG, System.currentTimeMillis() + 3000L, EffectBuff.BY_ACTOR, 0) != null && actor.cat == 0) {
                                    actor.sendEffToChar((Char) actor);
                                    actor.sendEffToNearChar();
                                }
                            }
                        } else if (id == 3) {
                            int d = actor.hp / 2;
                            if (actor.isBoss() || actor.isCongThanh()) {
                                d = actor.hp / 100;
                            }
                            if (d <= 0) {
                                d = 1;
                            }
                            d += d * this.getUpDamBangSetDocLuaLan(3) / 100;
                            dam.add(d);
                            if (cat == 1) {
                                totalxp += actor.getXpReceive(d);
                                final Monster ms = (Monster) actor;
                                if (ms.target == null) {
                                    ms.target = this;
                                }
                            }
                            if (!this.isBatTu()) {
                                final LiveActor liveActor4 = actor;
                                liveActor4.hp -= d;
                            }
                            hpLeft.add(actor.hp);
                            if (actor.hp <= 0) {
                                allactordie.add(actor);
                            } else {
                                try {
                                    boolean issend = false;
                                    if (cat == 0) {
                                        final Char c = (Char) actor;
                                        int khangluachay = 0;
                                        if (c.animalRide != null && 
                                        (c.animalRide.isPhuongHoangBang() || c.animalRide.isRongLon()) && 
                                        c.animalRide.KHANG_BI_CHAY > 0) {
                                            khangluachay = 95;
                                        }
                                        if (Map.r.nextInt(100) > khangluachay) {
                                            if (c.addEffBuff(EffectBuff.TAN_PHE, System.currentTimeMillis() + 3000L, EffectBuff.BY_ACTOR, 0) != null) {
                                                c.sendEffToChar(c);
                                                c.divSpeed = 2;
                                                issend = true;
                                                c.sendMessage(MessageCreator.createMainCharInfoMessage(c));
                                            }
                                            if (c.addEffBuff(EffectBuff.LAM_THINH, System.currentTimeMillis() + 3000L, EffectBuff.BY_ACTOR, 0) != null) {
                                                c.sendEffToChar(c);
                                                issend = true;
                                            }
                                            if (c.addEffBuff(EffectBuff.FIRE_BURN, System.currentTimeMillis() + 5000L, EffectBuff.BY_ACTOR, 0) != null) {
                                                c.sendEffToChar(c);
                                                issend = true;
                                            }
                                        }
                                        if (issend) {
                                            c.sendEffToNearChar();
                                        }
                                    }
                                } catch (final Exception ex2) {
                                }
                            }
                        }
                    }
                }
            }
        }
        if (totalxp > 0)   // Exp Up lan
        {
            try 
            {
                final int newxp = Map.calculatorXpParty(this, totalxp);
                if (newxp != totalxp) 
                {
                    int nUser = this.party.userParty.size();
                    if (nUser > 1) 
                    {
                        nUser = 5;
                    }
                    
                    int xpReceive = newxp * 80 / (100 * nUser);
                    final int maxLv = this.lvDetail.lv;
                    
                    for (int j = 0; j < this.party.userParty.size(); ++j) 
                    {
                        final Char pp = this.party.userParty.get(j);
                        if (pp.id != id && this.near(pp, 320) && pp.mapID == this.mapID && pp.inCountry == this.inCountry && pp.region == this.region) 
                        {
                            final int dlv = Map.abs(maxLv - pp.lvDetail.lv);
                            int temp = 1;
                            if (dlv <= 5) 
                            {
                                temp = xpReceive;
                            } else if (dlv <= 10) {
                                temp = xpReceive / 5;
                            } else if (dlv <= 20) {
                                temp = xpReceive / 10;
                            } else if (dlv <= 30) {
                                temp = xpReceive / 15;
                            } else {
                                temp = xpReceive / 20;
                            }
                            if (temp == 0) 
                            {
                                temp = 1;
                            }
                            
                            if (pp.hp > 0) 
                            {
                                temp /= this.party.userParty.size(); // Chia theo số lượng người trong party
                                temp *= Map.doubleALL;
                                temp = pp.expReceive(temp);
                                Map.addXPForChar(pp, temp + pp.getEffSkillClan(0) * temp / 100, false, "char checkNewEffectItem1");
                            }
                        }
                    }
                    xpReceive = newxp * 20 / 100 * Map.doubleALL;
                    xpReceive = this.expReceive(xpReceive);
                    Map.addXPForChar(this, xpReceive + this.getEffSkillClan(0) * xpReceive / 100, false, "char checkNewEffectItem2");
                } 
                else 
                {
                    totalxp *= Map.doubleALL;
                    totalxp = this.expReceive(totalxp);
                    Map.addXPForChar(this, totalxp + this.getEffSkillClan(0) * totalxp / 100, false, "char checkNewEffectItem3");
                }
            } catch (final Exception ex3) {
            }
        }
        
        final Message k = MessageCreator.createMsgNewEffect(this, allnewar, cat, id, dam, hpLeft, 2000);
        this.sendMessage(k);
        this.sendToNearPlayer(k);
        for (int l = 0; l < allactordie.size(); ++l) {
            if (cat == 1) {
                allactordie.get(l).charKillBoss(this);
                Map.onMosterDie(this, allactordie.get(l), (byte) 0, 1, (byte) 0, (byte) 0);
            } else {
                allactordie.get(l).actorDie();
            }
        }
        for (int l = 0; l < allnewar.size(); ++l) {
            if (cat == 0) {
                try {
                    Char p = (Char) allnewar.get(l);
                    p.sendMessage(MessageCreator.createNew_HMP_Message(p, 0));
                    p.sendToNearPlayer(MessageCreator.createNew_HMP_Message(p, 0));
                } catch (final Exception ex4) {
                }
            }
        }
    }

    public void doHutMau() {
    }

    public int getIdSkillAttack() {
        return 0;
    }

    public void doCharHireAttackPlayer(final Char be_attacked) {
    }

    public void doCharHireAttackMonster(final Monster be_attacked) {
    }

    public void doCharHireAttackMultiMonster(final Vector<Monster> allmonster) {
    }

    public void doCreateCharThanThu(final int idThanthu) {
        try {
            final byte[][] idquanao = {{2, 28, 54}, {1, 27, 53}};
            this.charthanthu = new CharThanthu((Session) null);
            this.charthanthu.owner = this;
            this.charthanthu.headStyle = 9;
            this.charthanthu.gender = this.gender;
            this.charthanthu.charClass = this.charClass;
            this.charthanthu.lvDetail.setExpNew(LevelDetail.getXpFromLevel(40));
            this.charthanthu.mapID = this.map.mapId;
            this.charthanthu.id = RealController.intance.idGen.getID(0, "Tao bot");
            this.charthanthu.x = this.x;
            this.charthanthu.y = this.y;
            this.charthanthu.map = this.map;
            this.charthanthu.lastLV = 40;
            this.charthanthu.charClass = this.charClass;
            this.charthanthu.charname = "@" + this.charname + "@";
            this.charthanthu.maxhp = 1000;
            this.charthanthu.maxmp = 1000;
            this.charthanthu.hp = 1000;
            this.charthanthu.mp = 1000;
            this.charthanthu.setXtoYto(this.x, this.y);
            this.charthanthu.userID = this.userID + 1000000000;
            this.charthanthu.myCountry = this.myCountry;
            this.charthanthu.inCountry = this.inCountry;
            this.charthanthu.idThanThu = (byte) (idThanthu - 1);
            CharManager.instance.put(this.charthanthu);
            this.sendMessage(this.writeActorPos(new Message(4), this.charthanthu));
            if (!this.isAdmin) {
                this.sendToNearPlayer(this.writeActorPos(new Message(4), this.charthanthu));
            }
        } catch (final Exception ex) {
        }
    }

    public void doCreateCharHire(final int cclass, final int capdo, final int gender, final int lvLinhthue) {
        if (this.charHire != null) {
            this.charhireDissapear();
        }
        this.charHire = new CharCopy((Session) null);
        final byte[][] idquanao = {{2, 28, 54}, {1, 27, 53}};
        final int gd = gender;
        this.charHire.setInfoChar(String.valueOf(this.charname) + (this.lvlinh == 64 ? " lan su vu" : " ho ve"), -1, gd + 1, cclass, this.map, 1088, 1008, -100000 + -1 * this.userID, idquanao[gd][0], idquanao[gd][1], -1);

        final int[] idWeapone = {599, 601, 603, 605, 607};
        this.charHire.wItems[0][getIndexItemWearing(3, 0)] = this.genItem(idWeapone[cclass], 0);
        this.charHire.headStyle = (byte) ((gd == 0) ? 10 : 9);
        this.charHire.id = RealController.intance.idGen.getID(0, "Tao bot");
        this.charHire.lvDetail.setExpNew(LevelDetail.getXpFromLevel(capdo));
        ((CharCopy) this.charHire).lvLinhthue = (byte) lvLinhthue;
        this.charHire.lastLV = (byte) capdo;
        this.charHire.charClass = (byte) cclass;
        this.charHire.x = this.x;
        this.charHire.y = this.y;
        this.charHire.mapID = this.mapID;
        this.charHire.region = this.region;
        this.charHire.setXtoYto(this.x, this.y);
        this.charHire.myCountry = this.myCountry;
        this.charHire.inCountry = this.inCountry;
        ((CharCopy) this.charHire).owner = this;
        ((CharCopy) this.charHire).setMaxHp();
        CharManager.instance.put(this.charHire);
        this.charHire.setFollow();
        this.sendMessage(this.writeActorPos(new Message(4), this.charHire));
        this.sendToNearPlayer(this.writeActorPos(new Message(4), this.charHire));
        this.classlinh = (byte) cclass;
        this.genderlinh = (byte) gender;
        this.lvlinh = (byte) capdo;
        this.lvlinhthue = (byte) lvLinhthue;
       
        if (this.lvlinh == 64) {
            this.charHire.setCharHire(true);
            
        }
       
       
    }


 
    
    public void checkCreateThangBe(final int mapID) {
    }

    public boolean isLinhThueLan() {
        return this.lvlinh == 64 && this.timeExistCharHire > 0L &&  System.currentTimeMillis() < this.timeExistCharHire && this.charHire != null;
    }

    public void doInitCharCopy(final int mapid, final int x, final int y) {
        if (this.charCopy != null) {
            this.charCopyDissapear();
        }
        if (Map.r.nextInt(100) < 100) {
            this.charCopy = new CharCopy((Session) null);
            final byte[][] idquanao = {{2, 28, 54}, {1, 27, 53}};
            final int gd = Map.r.nextInt(2);
            this.charCopy.setInfoChar(String.valueOf(this.charname) + " copy", -64, gd + 1, 0, RealController.mapList.get(mapid), 1088, 1008, -10000 + -1 * this.userID, idquanao[gd][0], idquanao[gd][1], -1);
            this.charCopy.headStyle = (byte) ((gd == 0) ? 10 : 9);
            this.charCopy.id = RealController.intance.idGen.getID(0, "Tao bot");
            this.charCopy.charClass = this.charClass;
            this.charCopy.x = x * 16;
            this.charCopy.y = y * 16;
            this.charCopy.setXtoYto(x * 16, y * 16);
            this.charCopy.region = 0;
            this.charCopy.myCountry = this.myCountry;
            this.charCopy.inCountry = this.inCountry;
            ((CharCopy) this.charCopy).owner = this;
            CharManager.instance.put(this.charCopy);
        }
    }

    public void doInitCharCopy() {
        if (this.charCopy != null) {
            this.charCopyDissapear();
        }
        this.charCopy = new CharCopy((Session) null);
        final byte[][] idquanao = {{2, 28, 54}, {1, 27, 53}};
        final int gd = Map.r.nextInt(2);
        final int[] idMap = isSuKienMiniChucNu() ? Char.idmapchucnu : Char.idmapthangbe;
        int indexMap = Map.r.nextInt(idMap.length);
        if (TeamServer.isServerLocal()) {
            if (isSuKienMiniChucNu()) {
                indexMap = 1;
            } else {
                indexMap = 2;
            }
        }
        System.out.println("MAP THANG BE: " + idMap[indexMap]);
        this.charCopy.setInfoChar(String.valueOf(this.charname) + " copy", -64, gd + 1, 0, RealController.mapList.get(idMap[indexMap]), 1088, 1008, -10000 + -1 * this.userID, idquanao[gd][0], idquanao[gd][1], -1);
        this.charCopy.headStyle = (byte) ((gd == 0) ? 10 : 9);
        this.charCopy.id = RealController.intance.idGen.getID(0, "Tao bot");
        if (isSuKienMiniChucNu()) {
            this.charCopy.setInfoModelCharBot(0, 574);
        }
        this.charCopy.charClass = this.charClass;
        if (isSuKienMiniChucNu()) {
            this.charCopy.x = Char.xychucnu[indexMap][0] * 16;
            this.charCopy.y = Char.xychucnu[indexMap][1] * 16;
            this.charCopy.setXtoYto(Char.xychucnu[indexMap][0] * 16, Char.xychucnu[indexMap][1] * 16);
        } else {
            this.charCopy.x = Char.xythangbe[indexMap][0] * 16;
            this.charCopy.y = Char.xythangbe[indexMap][1] * 16;
            this.charCopy.setXtoYto(Char.xythangbe[indexMap][0] * 16, Char.xythangbe[indexMap][1] * 16);
        }
        this.charCopy.region = this.region;
        this.charCopy.myCountry = this.myCountry;
        this.charCopy.inCountry = this.inCountry;
        ((CharCopy) this.charCopy).owner = this;
        CharManager.instance.put(this.charCopy);
    }

    public static Char doCreateCharNhanBan(final Char p) {
        final Char charCopy = new CharCopy((Session) null);
        final byte[][] idquanao = {{2, 28, 54}, {1, 27, 53}};
        final int gd = Map.r.nextInt(2);
        final int[] idMap = Char.idmapthangbe;
        int indexMap = Map.r.nextInt(idMap.length);
        if (TeamServer.isServerLocal()) {
            indexMap = 2;
        }
        charCopy.charDBID = -MapChienTruongMoba.all_char_chien_truong.size();
        charCopy.setInfoChar(String.valueOf(p.charname) + " " + MapChienTruongMoba.all_char_chien_truong.size(), -1, gd + 1, 0, RealController.mapList.get(idMap[indexMap]), 1088, 1008, -10000 + -1 * MapChienTruongMoba.all_char_chien_truong.size(), idquanao[gd][0], idquanao[gd][1], -1);
        charCopy.headStyle = (byte) ((gd == 0) ? 10 : 9);
        charCopy.id = RealController.intance.idGen.getID(0, "Tao bot");
        charCopy.charClass = p.charClass;
        ((CharCopy) charCopy).isCharChienTruong = true;
        charCopy.lastLV = 60;
        charCopy.x = Char.xythangbe[indexMap][0] * 16;
        charCopy.y = Char.xythangbe[indexMap][1] * 16;
        charCopy.setXtoYto(Char.xythangbe[indexMap][0] * 16, Char.xythangbe[indexMap][1] * 16);
        charCopy.region = p.region;
        charCopy.myCountry = p.myCountry;
        charCopy.inCountry = p.inCountry;
        ((CharCopy) charCopy).isCharChienTruong = true;
        CharManager.instance.put(charCopy);
        return charCopy;
    }

    public void setFollow() {
    }

    public boolean isCharHire() {
        return false;
    }

    public void setCharHire(final boolean hire) {
    }

    public static synchronized void doInitMonter(final Char p, final int typetieu) {
        try {
            if (p.monster != null) {
                if (!p.monster.isFinish()) {
                    p.sendMessage(MessageCreator.createServerAlertMessage("Bạn chưa hoàn thành chuyến tiêu đã nhận", ""));
                    return;
                }
                try {
                    final Message m = new Message(90);
                    m.dos.writeShort(p.monster.id);
                    m.dos.writeByte(p.monster.cat);
                    Map.sendAllCharServerInMap(new int[]{0, 70, 301, 1701, 1, 2, 3, 4, 5, 6, 7, 8, 9, 118}, m);
                    MonsterVantieu.removeMonster(p.monster);
                    RealController.intance.idGen.putID(p.monster.id, 1, "");
                    p.monster = null;
                    p.typeTieu = -1;
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            if (p.luot_van_tieu < 1) {
                p.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã hết lượt nhận tiêu", ""));
                return;
            }
            p.upSpeedNguaVanTieu = 1;
            final String[] name = {"Trắng", "Xanh", "Đỏ", "Hoàn mỹ", "Vàng", "Tím"};
            final int[][] idMonster = {{95, 96, 97, 98, 99, 100}, {101, 102, 103, 104, 105, 106}, {107, 108, 109, 110, 111, 112}};
            final int ran = Map.r.nextInt(1000);
            int indexMons = 5;
            if (ran < 600) {
                indexMons = 0;
            } else if (ran < 892) {
                indexMons = 1;
            } else if (ran < 995) {
                indexMons = 2;
            } else if (ran < 998) {
                indexMons = 3;
            } else if (ran < 999) {
                indexMons = 4;
            }
            p.typeTieu = (byte) (typetieu * 6 + indexMons);
            p.monster = new MonsterVantieu(p.map, Map.monsterTemplates.get(idMonster[typetieu][indexMons]), p.x, p.y, p.myCountry);
            p.monster.id = RealController.intance.idGen.getID(1, "");
            p.monster.charDbid = p.charDBID;
            p.monster.level = p.lvDetail.lv;
            p.monster.typeTieu = p.typeTieu;
            p.monster.maxhp = Char.HP_MONSTER_VANTIEU[(p.lvDetail.lv - 40) / 10] + Char.HP_MONSTER_VANTIEU[(p.lvDetail.lv - 40) / 10] * Char.UP_HP_MONSTER_VANTIEU[(p.lvDetail.lv - 40) / 10] / 100;
            p.monster.hp = p.monster.maxhp;
            p.monster.defend_magic = Char.DEF_MONSTER_VANTIEU[(p.lvDetail.lv - 40) / 10] + Char.DEF_MONSTER_VANTIEU[(p.lvDetail.lv - 40) / 10] * Char.UP_DEF_MONSTER_VANTIEU[(p.lvDetail.lv - 40) / 10] / 100;
            p.monster.defend_physic = p.monster.defend_magic;
            p.monster.setTimeFinish(System.currentTimeMillis() + 1200000L);
            p.monster.namemaster = p.charname;
            MonsterVantieu.addMonster(p.monster);
            p.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã nhận được tiêu " + name[indexMons], ""));
            Database.instance.saveOrtherLog("", p.charname, "Nhan tieu " + name[indexMons], "nhantieu");
            try {
                if (indexMons > 3) {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(p.charname) + " lãnh thổ " + Map.nameCountry[p.myCountry] + " đã nhận được tiêu " + name[indexMons]));
                }
            } catch (final IOException ex) {
            }
            if (!p.isAdmin && TeamServer.server != 100) {
                --p.luot_van_tieu;
            }
            final long[] receive_TIEU = Map.RECEIVE_TIEU;
            final int n = indexMons;
            ++receive_TIEU[n];
            Database.instance.saveMonsterVantieu(p.monster);
        } catch (final Exception e) {
            p.typeTieu = -1;
            p.monster.actorDie();
            p.monster = null;
            e.printStackTrace();
        }
    }

    public boolean canChangeTieu() {
        return (this.map.mapIDLoadMap == 0 || this.map.mapIDLoadMap == 5 || this.myCountry != 1) && (this.map.mapIDLoadMap == 70 || this.map.mapIDLoadMap == 5 || this.myCountry != 0) && (this.map.mapIDLoadMap == 5 || this.map.mapIDLoadMap == 0 || this.map.mapIDLoadMap == 70) && (this.map.mapIDLoadMap != 5 || (this.x / 16 >= 37 && this.y / 16 >= 37 && this.x / 16 <= 51 && this.y / 16 <= 49)) && this.monster.map.mapId == this.map.mapId && this.region == this.monster.region;
    }

    public void doChangeTieu(final int xu_luong) {
        synchronized (Map.CHANGE_TIEU) {
            if (this.monster == null) {
                //monitorexit(Map.CHANGE_TIEU);
                return;
            }
            this.upSpeedNguaVanTieu = 1;
            final int oldColor = this.typeTieu % 6;
            final int[][] idMonster = {{95, 96, 97, 98, 99, 100}, {101, 102, 103, 104, 105, 106}, {107, 108, 109, 110, 111, 112}};
            final int ran = Map.r.nextInt(1000);
            int index = 5;
            if (xu_luong == 0) {
                if (ran < 500) {
                    index = 0;
                } else if (ran < 850) {
                    index = 1;
                } else if (ran < 984) {
                    index = 2;
                } else if (ran < 994) {
                    index = 3;
                } else if (ran < 998) {
                    index = 4;
                }
            } else if (ran < 200) {
                index = 0;
            } else if (ran < 500) {
                index = 1;
            } else if (ran < 940) {
                index = 2;
            } else if (ran < 970) {
                index = 3;
            } else if (ran < 990) {
                index = 4;
            }
            try {
                final Message m = new Message(90);
                m.dos.writeShort(this.monster.id);
                m.dos.writeByte(this.monster.cat);
                Map.sendAllCharServerInMap(new int[]{0, 70, 301, 1701, 1, 2, 3, 4, 5, 6, 7, 8, 9, 118}, m);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            this.monster.idTemplate = idMonster[this.typeTieu / 6][index];
            this.typeTieu = (byte) (this.typeTieu / 6 * 6 + index);
            this.monster.setType(Map.monsterTemplates.get(this.monster.idTemplate).type);
            this.monster.typeTieu = this.typeTieu;
            try {
                this.monster.map.sendAllCharMap(this.writeActorPos(new Message(4), this.monster), this.myCountry, 0);
            } catch (final Exception ex) {
            }
            try {
                final Message m = new Message(7);
                m.dos.writeShort(this.monster.id);
                m.dos.writeByte(this.monster.getType());
                m.dos.writeShort(this.monster.x);
                m.dos.writeShort(this.monster.y);
                m.dos.writeInt(this.monster.hp);
                m.dos.writeByte(this.monster.level);
                m.dos.writeByte(this.monster.he);
                m.dos.writeInt(this.monster.maxhp);
                m.dos.writeInt(this.monster.getTimeReborn());
                this.sendMessage(m);
                this.monster.map.sendAllPlayer(m, this.monster.inCountry, this.region);
            } catch (final Exception ex2) {
            }
            final String[] name = {"Trắng", "Xanh", "Đỏ", "Hoàn mỹ", "Vàng", "Tím"};
            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng bạn đã đổi được tiêu " + name[index], ""));
            try {
                if (index > 3) {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " lãnh thổ " + Map.nameCountry[this.myCountry] + " đã đổi được tiêu " + name[index]));
                }
            } catch (final IOException ex3) {
            }
            final long[] change_TIEU = Map.CHANGE_TIEU;
            final int n = index;
            ++change_TIEU[n];
            final long[] card_TIEU = Map.CARD_TIEU;
            ++card_TIEU[xu_luong];
            try {
                Database.instance.saveOrtherLog("", this.charname, "doi tu tieu " + name[oldColor] + " sang tieu" + name[index], "doitieu");
                Database.instance.saveMonsterVantieu(this.monster);
            } catch (final Exception ex4) {
            }
            //monitorexit(Map.CHANGE_TIEU);
        }
    }

    public void doResponseTieu(final Char pReceive, final boolean isThief, final int type, int lvOpen) {
        try {
            synchronized (Map.FINISH_TIEU) {
                if (lvOpen > 78) {
                    lvOpen = 78;
                }
                final String[] name = {"Trắng", "Xanh", "Đỏ", "Hoàn mỹ", "Vàng", "Tím"};
                final int div = isThief ? 2 : 1;
                final int typeTieu = type / 6;
                final int colortieu = type % 6;
                int indexExp = (lvOpen - 20) / 10;
                if (indexExp < 0) {
                    indexExp = 0;
                }
                final int[] idexp = {0, 0, 1, 2, 2, 2};
                final int[] luongadd = {0, 0, 0, 0, 100 + Map.r.nextInt(11), 100 + Map.r.nextInt(11), 0};
                final int random = Map.r.nextInt(100);
                if (random >= 80) {
                    if (random < 89) {
                        luongadd[4] = 110 + Map.r.nextInt(11);
                        luongadd[5] = 110 + Map.r.nextInt(11);
                    } else if (random < 93) {
                        luongadd[4] = 120 + Map.r.nextInt(31);
                        luongadd[5] = 120 + Map.r.nextInt(31);
                    } else if (random < 97) {
                        luongadd[4] = 150 + Map.r.nextInt(21);
                        luongadd[5] = 150 + Map.r.nextInt(21);
                    } else {
                        luongadd[4] = 170 + Map.r.nextInt(31);
                        luongadd[5] = 170 + Map.r.nextInt(31);
                    }
                }
                final int[] pcExpAdd = {50, 100, 150, 250, 250, 500, 0};
                if (lvOpen > pReceive.lvDetail.lv) {
                    lvOpen = pReceive.lvDetail.lv;
                }
                if (typeTieu == Char.INDIVITUAL) {
                    long exp = Char.EXP_NV_HANG_NGAY[indexExp][idexp[colortieu]] * 2; // TangExpVanTieu
                    exp = exp * pcExpAdd[colortieu] / 100L;
                    exp /= div;
                    if (TeamServer.isServerHoaLu() && isSuKienDuaTopHoaLu()) {
                        exp = 0L;
                    }
                    if (TeamServer.ExpVantieu) {
                    Map.addXpCharEvent(pReceive, exp, false, "char doResponseTieu");
                    }
                    final Item it = this.doAddItemVanTieu(pReceive, colortieu, lvOpen);
                    if (it != null) {
                        it.magic_physic = (byte) Map.r.nextInt(2);
                        if (it.magic_physic == 0) {
                            it.atb[6] = it.atb[1];
                            final short[] atb = it.atb;
                            final int n = 1;
                            atb[n] /= 10;
                        } else if (it.magic_physic == 1) {
                            it.atb[6] = (short) (it.atb[1] / 10);
                        }
                    }
                    
                    String info = "nhận được " + exp + "exp";
                    if (it != null) {
                        this.sendMessage(MessageCreator.createCharInventoryMessage(pReceive, 1));
                        info = String.valueOf(info) + "," + it.getName();
                    }
                    if (luongadd[colortieu] > 0) {
                        final boolean notlock = Map.r.nextInt(1000) < 5;
                        if (!notlock) {
                            this.addLuongLock(luongadd[colortieu] / div);
                        } else {
                            this.addLuong(luongadd[colortieu] / div);
                        }
                        info = String.valueOf(info) + " và " + luongadd[colortieu] / div + " lượng" + (notlock ? "" : " khoá");
                        this.sendMessage(MessageCreator.createCharInventoryMessage(pReceive, 0));
                        if (notlock) {
                            try {
                                Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + this.charname + " đã nhận được " + luongadd[colortieu] / div + " lượng"));
                            } catch (final IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    final int[] pcAdd = {0, isThief ? 5 : 10, isThief ? 25 : 50, isThief ? 35 : 70, isThief ? 35 : 70, isThief ? 50 : 100};
                    final int pc = Map.r.nextInt(100);
                    final int[] sl = {15, 15, 15, 6, 3, 1};
                    final int[] slmin = {3, 3, 3, 2, 1, 1};
                    if (pc < pcAdd[colortieu]) {
                        final int index = Map.r.nextInt(6);
                        int numberran = sl[index] - slmin[index];
                        if (numberran <= 0) {
                            numberran = 1;
                        }
                        final int soluong = Map.r.nextInt(numberran) + slmin[index];
                        this.doAddGemItem(GemTemplate.ID_NGOC_HUYEN_MINH[index], soluong, true);
                        info = String.valueOf(info) + " và " + soluong + " " + Map.gemTemplate[GemTemplate.ID_NGOC_HUYEN_MINH[index]].name;
                        pReceive.sendMessage(MessageCreator.createCharGemItem(this));
                    }
                    this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng bạn " + info, ""));
                    Database.instance.saveOrtherLog("", pReceive.charname, String.valueOf(info) + ((it != null) ? ("_" + it.getInfoSave()) : "") + " " + isThief + " mau tieu: " + name[colortieu], isThief ? "motieu" : "tratieu");
                    if (!isThief) {
                        final long[] finish_TIEU = Map.FINISH_TIEU;
                        final int n2 = colortieu;
                        ++finish_TIEU[n2];
                    }
                } else if (typeTieu != Char.CLAN) {
                }
                isSuKienHe2017();
                if (Map.r.nextInt(1000) < 5) {
                    Map.doCreateBookSkillPet(this, 0);
                }
                //monitorexit(Map.FINISH_TIEU);
            }
        } catch (final Exception e2) {
            e2.printStackTrace();
        }
    }

    public Item doAddItemVanTieu(final Char pRcv, final int colorTieu, final int lvOpen) {
        Item it = this.map.dropItemAnimalByLvChar(lvOpen, 4);
        if (it == null) {
            return null;
        }
        it.id = pRcv.getIDItem();
        if (colorTieu == 0 && Map.r.nextInt(100) < 50) {
            pRcv.iItems.add(it);
            return it;
        }
        if (colorTieu == 1) {
            if (Map.r.nextInt(100) < 50) {
                pRcv.iItems.add(it);
                return it;
            }
            it = ItemChangeColor.createItemColorAnimal(it, Item.COLOR_BLUE, Map.r.nextInt(3) + 1);
            pRcv.iItems.add(it);
            return it;
        } else if (colorTieu == 2) {
            if (Map.r.nextInt(100) < 50) {
                it = ItemChangeColor.createItemColorAnimal(it, Item.COLOR_BLUE, Map.r.nextInt(3) + 1);
                pRcv.iItems.add(it);
                return it;
            }
            it = ItemChangeColor.createItemColorAnimal(it, Item.COLOR_RED, Map.r.nextInt(3) + 1);
            pRcv.iItems.add(it);
            return it;
        } else if (colorTieu == 3 || colorTieu == 4) {
            if (Map.r.nextInt(100) < 50) {
                it = ItemChangeColor.createItemColorAnimal(it, Item.COLOR_RED, Map.r.nextInt(3) + 1);
                pRcv.iItems.add(it);
                return it;
            }
            it = ItemChangeColor.createItemColorAnimal(it, Item.COLOR_GREEN, (Map.r.nextInt(100) < 10) ? 1 : 2);
            pRcv.iItems.add(it);
            return it;
        } else {
            if (colorTieu == 5) {
                it = ItemChangeColor.createItemColorAnimal(it, Item.COLOR_GREEN, (Map.r.nextInt(100) < 10) ? 1 : 2);
                pRcv.iItems.add(it);
                return it;
            }
            return null;
        }
    }

    public boolean isBienHinh() {
        return this.getIDNguoiuyet() > -1 || this.isMonster;
    }

    @Override
    public void setNguoiTuyet(final int id) {
        if (this.idNgtuyet > -1) {
            return;
        }
        if ((id == 1 || id == 2) && (this.isActiveTienKhi() || this.isActive8sRongLon() || (this.animalRide != null && Map.r.nextInt(100) < this.animalRide.KHANG_HOA_TUYET))) {
            return;
        }
        if (id == 1) {
            this.timeNguoiTuyet = System.currentTimeMillis() + 60000L;
            this.idNgtuyet = (byte) id;
        } else if (id == 0 || id == 2) {
            this.timeNguoiTuyet = System.currentTimeMillis() + 8000L;
            this.idNgtuyet = (byte) id;
        } else if (id == 3) {
            this.timeNguoiTuyet = System.currentTimeMillis() + 900000L;
            this.idNgtuyet = (byte) id;
            this.calculateAttrib();
            try {
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
            } catch (final IOException ex) {
            }
        } else if (id == 4) {
            this.timeNguoiTuyet2 = System.currentTimeMillis() + 30000L;
            this.idNgtuyet2 = (byte) id;
        } else if (id == 5) {
            this.timeNguoiTuyet = System.currentTimeMillis() + 120000L;
            this.idNgtuyet = (byte) id;
            this.calculateAttrib();
            try {
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
            } catch (final IOException ex2) {
            }
        } else if (id == 6) {
            this.timeNguoiTuyet = System.currentTimeMillis() + 5000L;
            this.idNgtuyet = (byte) id;
            this.calculateAttrib();
            try {
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
            } catch (final IOException ex3) {
            }
        }
        MessageCreator.createMsgCharMonster(this, this);
    }

    public boolean isHieuUngCoLongDuongQua() {

        if (this.isAdmin) {
            return true;
        } else {
            return false;
        }
        /*
        if (this.party == null) {
            return false;
        }
        for (int i = 0; i < this.party.userParty.size(); ++i) {
            final Char p = this.party.userParty.get(i);
            if (p.id != this.id && p.nameHusband_wife.equals(this.charname) && p.map.mapId == this.map.mapId && p.region == this.region) {
                if (this.isDuongQua() && p.isTieuLongNu()) {
                    return true;
                }
                if (this.isTieuLongNu() && p.isDuongQua()) {
                    return true;
                }
            }
        }
        return false;
         */
    }

    public void sendEffectBuff(final LiveActor a, final int ideff, final int milisecond) {
        this.sendMessage(MessageCreator.createMsgNewEffectSkill5Long(ideff, milisecond, a.id, a.cat, true, false));
        this.sendToNearPlayer(MessageCreator.createMsgNewEffectSkill5Long(ideff, milisecond, a.id, a.cat, false, false));
    }

    public int getPCDamNguyetAnh(final int idskill) {
        if (idskill > -1 && System.currentTimeMillis() - this.cooldownNguyetAnh >= 0L && (this.wModel.isTienNu() || this.wModel.isTinhQuan()) && Map.r.nextInt(100) < 10) {
            this.sendMessage(MessageCreator.createMsgNewEffectSkill5Long(EffectBuff.EFF_START_NGUYET_ANH, 1000, this.id, this.cat, false, false));
            this.sendToNearPlayer(MessageCreator.createMsgNewEffectSkill5Long(EffectBuff.EFF_START_NGUYET_ANH, 1000, this.id, this.cat, false, false));
            this.cooldownNguyetAnh = System.currentTimeMillis() + 1000L;
            return 10 + Map.r.nextInt(6);
        }
        return 0;
    }

    private int getPcKhangHoa() {
        final Item[] wItems = this.getArrayCharWearing();
        final int index = getIndexItemWearing(19, 0);
        if (wItems[index] != null && wItems[index].isPhiPhongHuyenVu()) {
            return 300;
        }
        return 0;
    }

    public boolean isDuongQua() {
        return this.wModel.isDuongQua();
    }

    public boolean isTieuLongNu() {
        return this.wModel.isTieuLongNu();
    }

    public boolean isNguoiTuyet() {
        real.plugins.addNPC.addNPC(this);
        return this.idNgtuyet > -1;
    }

    public boolean isCuMeo() {
        return this.idNgtuyet == 6;
    }

    public boolean cannotAttackWhenBienhinh() {
        return this.getIDNguoiuyet() == 2 || this.isCuMeo();
    }

    public int getIDNguoiuyet() {
        return (this.idNgtuyet2 > -1) ? this.idNgtuyet2 : this.idNgtuyet;
    }

    @Override
    public boolean checkTanPhe() {
        return this.hashEffBuff.get(EffectBuff.TAN_PHE) != null;
    }

    @Override
    public boolean checkLamthinh() {
        return this.hashEffBuff.get(EffectBuff.LAM_THINH) != null;
    }

    public boolean checkCamLang() {
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong cc = MapChienTruongMoba.getCharChienTruong(this.charname);
            return cc.isCamLang();
        }
        return false;
    }

    @Override
    public int haveRegentHP() {
        if (this.hp <= 0) {
            return 0;
        }
        int hoihp = this.hoihp;
        Pet petUsing = this.petUsing;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
        }
        if (petUsing != null) {
            hoihp += petUsing.getHpRegen();
            final int[] a = petUsing.getChuaTri();
            final long b = this.maxhp * a[0] / 100;
            hoihp += (int) (b + petUsing.getPhiHuyet());
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            final int pchoi = c.getBuffHeThuy();
            if (pchoi > 0) {
                hoihp += this.maxhp * 5 / 100;
            } else {
                hoihp += 500;
            }
        }
        return hoihp;
    }

    @Override
    public int haveRegentMP() {
        if (this.hp <= 0) {
            return 0;
        }
        int hoimp = this.hoimp;
        Pet petUsing = this.petUsing;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
        }
        if (petUsing != null) {
            hoimp += petUsing.getMpRegen();
            hoimp += petUsing.getThuanAnh();
        }
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null) {
                hoimp += 50;
            }
        }
        return hoimp;
    }

    @Override
    public int getPcGiamSatThuong() {
        if (this.hashEffBuff.get(EffectBuff.EFF_SHIELD_1) != null) {
            return 15;
        }
        if (this.hashEffBuff.get(EffectBuff.EFF_SHIELD_2) != null) {
            return 30;
        }
        return super.getPcGiamSatThuong();
    }

    @Override
    public int getBackDam(final int dam) {
        int backDam = dam;
        
        // Nếu đang cưỡi thú và là Rồng Lớn
        if (this.animalRide != null) {
            int giamSatThuongPhan = 50;
            if (giamSatThuongPhan > 0) {
                backDam = backDam * (100 - giamSatThuongPhan) / 100;
            }
        }

        if (this.hashEffBuff.get(EffectBuff.EFF_SHIELD_1) != null) {
            return 20 * backDam / 100;
        }
        if (this.hashEffBuff.get(EffectBuff.EFF_SHIELD_2) != null) {
            return 30 * backDam / 100;
        }
        return super.getBackDam(backDam);
    }

    @Override
    public boolean haveBackDam() {
        return this.hashEffBuff.get(EffectBuff.EFF_SHIELD_1) != null || this.hashEffBuff.get(EffectBuff.EFF_SHIELD_2) != null;
    }

    @Override
    public int getPcSuyyeu() {
        final EffectBuff ef = this.hashEffBuff.get(EffectBuff.SUY_YEU);
        if (ef != null) {
            return ef.dam;
        }
        return super.getPcSuyyeu();
    }

    @Override
    public boolean haveSuyYeu() {
        return this.hashEffBuff.get(EffectBuff.SUY_YEU) != null;
    }

    @Override
    public int haveTanPhe() {
        return (Map.r.nextInt(1000) < this.tanphe) ? 1 : 0;
    }

    @Override
    public int haveLamThinh() {
        return (Map.r.nextInt(1000) < this.lamthinh) ? 1 : 0;
    }

    @Override
    public int haveKhangBangLan() {
        return (Map.r.nextInt(1000) < this.khangbanglan) ? 1 : 0;
    }

    @Override
    public int haveKhangSetLan() {
        return (Map.r.nextInt(1000) < this.khangsetlan) ? 1 : 0;
    }

    @Override
    public int haveKhangDocLan() {
        return (Map.r.nextInt(1000) < this.khangdoclan) ? 1 : 0;
    }

    @Override
    public int haveHutHp() {
        int hphut = this.hutmau;
        Pet petUsing = this.petUsing;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
        }
        if (petUsing != null && Map.r.nextInt(1000) < petUsing.getpcHutHpByHit()) {
            hphut += petUsing.getHpHutByHit();
        }
        return hphut;
    }

    @Override
    public int haveNeTranh() {
        Pet petUsing = this.petUsing;
        if (this.map != null && this.map.isMapChienTruongMoba()) {
            final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
            if (c != null && c.getNoiLuc() < 5) {
                petUsing = null;
            }
        }
        if (petUsing != null) {
            return petUsing.getNeTranh();
        }
        return super.haveNeTranh();
    }

    @Override
    public boolean khangBangLan(int giamKhang) 
    {
        return Map.r.nextInt(1000) < this.khangbanglan - giamKhang;
    }

    @Override
    public boolean khangSetLan(int giamKhang) {
        return Map.r.nextInt(1000) < this.khangsetlan - giamKhang;
    }

    @Override
    public boolean khangDocLan(int giamKhang) {
        return Map.r.nextInt(1000) < this.khangdoclan - giamKhang;
    }

    @Override
    public boolean khangLuaLan(int giamKhang) {
        return Map.r.nextInt(1000) < this.khanglualan - giamKhang;
    }

    @Override
    public void resetSpeed() {
        this.divSpeed = 1;
        try {
            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
        } catch (final IOException ex) {
        }
    }

    @Override
    public boolean khangBiChay() {
        return this.animalRide != null && this.animalRide.isPhuongHoangBang() && this.animalRide.KHANG_BI_CHAY > 0 && Map.r.nextInt(100) < 95;
    }

    @Override
    public synchronized EffectBuff addEffBuff(final int id, final long time, final int type, final int dam) 
    {
        if (this.hashEffBuff.get((short) id) != null) 
        {
            if (!this.hashEffBuff.get((short) id).timeOut()) 
            {
                return null;
            }
            this.AllEffBuff.remove(this.hashEffBuff.get((short) id));
            this.hashEffBuff.remove((short) id);
        }
        final EffectBuff ef = new EffectBuff(id, time);
        this.AllEffBuff.add(ef);
        ef.type = (byte) type;
        ef.damFly = dam;
        this.hashEffBuff.put((short) id, ef);
        System.out.println("(" + this.charname + ")Add Eff Buff: " +  id + ": " + time);
        return ef;
    }

    @Override
    public void updateEffectBuff() {
        final Vector<EffectBuff> rm = new Vector<EffectBuff>();
        try {
            for (int i = 0; i < this.AllEffBuff.size(); ++i) {
                final EffectBuff ef = this.AllEffBuff.get(i);
                if (ef.idEff == EffectBuff.UONG_RUOU && System.currentTimeMillis() - this.timeRegentHpMp >= 5000L) {
                    Map.addXpCharEvent(this, 12500L, false, "char updateEffectBuff1");
                } else if (ef.idEff == EffectBuff.UONG_RUOU1 && System.currentTimeMillis() - this.timeRegentHpMp >= 5000L) {
                    Map.addXpCharEvent(this, 83330L, false, "char updateEffectBuff2");
                } else if (ef.idEff == EffectBuff.UONG_RUOU2 && System.currentTimeMillis() - this.timeRegentHpMp >= 5000L) {
                    Map.addXpCharEvent(this, 583330L, false, "char updateEffectBuff3");
                } else if (ef.idEff == EffectBuff.EFF_RI_MAU && System.currentTimeMillis() - ef.timeCount >= 1000L) {
                    ef.timeCount = System.currentTimeMillis();
                    this.hp -= 2000;
                    if (this.hp <= 0) {
                        this.hp = 0;
                        this.actorDie();
                        try {
                            if (Map.giveCardFail(this)) {
                                this.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " giao thẻ thất bại"), this.myCountry);
                                this.map.sendAllPlayer(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
                            }
                        } catch (final IOException ex) {
                        }
                    } else {
                        try {
                            MessageCreator.createMsgUseHpMP(this, -2000, 1);
                        } catch (final Exception ex2) {
                        }
                    }
                } else if (ef.idEff == EffectBuff.TRUNG_DOC && System.currentTimeMillis() - ef.timeCount >= 1000L) {
                    ef.timeCount = System.currentTimeMillis();
                    this.hp -= ef.dam;
                    if (this.hp <= 0) {
                        this.hp = 0;
                        this.actorDie();
                        try {
                            if (Map.giveCardFail(this)) {
                                this.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " giao thẻ thất bại"), this.myCountry);
                                this.map.sendAllPlayer(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
                            }
                        } catch (final IOException ex3) {
                        }
                    } else {
                        try {
                            MessageCreator.createMsgUseHpMP(this, -ef.dam, 1);
                        } catch (final Exception ex4) {
                        }
                    }
                }
                if (ef.timeOut()) {
                    if (this.cat == 0) {
                        if (ef.isTanPhe()) {
                            this.resetSpeed();
                        }
                        if (ef.isEffBuffNoiLuc()) {
                            this.calculateAttrib();
                            this.sendInfoChienTruong(Char.ID_NOI_LUC, 0);
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                            this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                        }
                    }
                    rm.add(ef);
                    final Message m = MessageCreator.createMsgRemoveDynamicNewEffect(ef.idEff, 1L, 1, this.id, this.x / 16, this.y / 16, this.cat, 1);
                    this.sendMessage(m);
                    this.sendToNearPlayer(m);
                    if (ef.idEff == EffectBuff.UONG_RUOU || ef.idEff == EffectBuff.UONG_RUOU1 || ef.idEff == EffectBuff.UONG_RUOU2) {
                        this.idRuouUong = -1;
                    }
                }
            }
            for (int i = 0; i < rm.size(); ++i) {
                final EffectBuff ef = rm.get(i);
                this.AllEffBuff.remove(ef);
                this.hashEffBuff.remove(ef.idEff);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public EffectBuff getEffectUongRuou() {
        for (int i = 0; i < this.AllEffBuff.size(); ++i) {
            final EffectBuff ef = this.AllEffBuff.get(i);
            if (!ef.timeOut()) {
                return ef;
            }
        }
        return null;
    }

    public EffectBuff getEffectDongLua() {
        for (int i = 0; i < this.ALL_BUFF_INMAP.size(); ++i) {
            final EffectBuff ef = this.ALL_BUFF_INMAP.get(i);
            if (!ef.timeOut()) {
                return ef;
            }
        }
        return null;
    }

    public boolean canHSChiemThanh() {
        return System.currentTimeMillis() - this.timeWaitComeHome > 1500L;
    }

    @Override
    public int getValueLua(final int type) {
        int updef = 0;
        final Vector<EffectBuff> rm = new Vector<EffectBuff>();
        for (int i = 0; i < this.ALL_BUFF_INMAP.size(); ++i) {
            final EffectBuff ef = this.ALL_BUFF_INMAP.get(i);
            if (type == 1 && ef.idEff == EffectBuff.LUA_VUA && ef.mapID == this.mapID && ef.idRegion == this.region && ef.idCountry == this.myCountry && Map.inRangeActor(ef, this, 120)) {
                updef = 5;
            } else if (type == 0 && ef.idEff == EffectBuff.LUA_NHO && ef.mapID == this.mapID && ef.idRegion == this.region && ef.idCountry == this.myCountry && Map.inRangeActor(ef, this, 120)) {
                updef = 2;
            } else if (type == 2 && ef.idEff == EffectBuff.LUA_TO && ef.mapID == this.mapID && ef.idRegion == this.region && ef.idCountry == this.myCountry && Map.inRangeActor(ef, this, 120)) {
                updef = 10;
            }
            if (ef.timeOut()) {
                rm.add(ef);
                if (ef.idEff == EffectBuff.LUA_NHO || ef.idEff == EffectBuff.LUA_VUA || ef.idEff == EffectBuff.LUA_TO) {
                    this.idCuiDot = -1;
                }
            }
            if (updef > 0) {
                break;
            }
        }
        for (int i = 0; i < rm.size(); ++i) {
            this.ALL_BUFF_INMAP.remove(rm.get(i));
        }
        return updef;
    }

    public void updateLuLan() {
        if (!this.isHoangLoan()) {
            return;
        }
        final int xx0 = this.x;
        final int yy0 = this.y;
        int xL = xx0 - 72;
        final int xR = xx0 + 72;
        final int yU = yy0 + 72;
        int yD = yy0 - 72;
        final int xNew = Map.ranDom(xL, xR);
        final int yNew = Map.ranDom(yD, yU);
        if (xL < 16) {
            xL = 16;
        }
        if (yD < 16) {
            yD = 16;
        }
        if (!this.map.canMove(xNew, yNew)) {
            final Message m = new Message(4);
            this.writeActorPos(m, this);
            this.sendMessage(m);
            return;
        }
        final byte step = 2;
        final int totalPositionX = (xNew - this.x) / step;
        final int totalPositionY = (yNew - this.y) / step;
        boolean blockX = false;
        boolean blockY = false;
        int dau = (totalPositionX >= 0) ? 1 : -1;
        for (int i = 0; i < Map.abs(totalPositionX); ++i) {
            final int tem = this.x + dau * i * step;
            if (!this.map.canMove(tem, this.y)) {
                blockX = true;
                break;
            }
        }
        if (!blockX) {
            dau = ((totalPositionY >= 0) ? 1 : -1);
            for (int i = 0; i < Map.abs(totalPositionY); ++i) {
                final int tem = this.x + dau * i * step;
                if (!this.map.canMove(this.y, tem)) {
                    blockY = true;
                    break;
                }
            }
        }
        if (blockX || blockY) {
            final Message j = new Message(4);
            this.writeActorPos(j, this);
            this.sendMessage(j);
            return;
        }
        this.x = xNew;
        this.y = yNew;
        final Message j = new Message(4);
        this.writeActorPos(j, this);
        this.sendMessage(j);
        this.sendInfoMove2Near();
        try {
            if (Map.giveCardFail(this)) {
                this.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " giao thẻ thất bại"), this.myCountry);
                this.map.sendAllPlayer(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
            }
        } catch (final Exception ex) {
        }
    }

    @Override
    public void update() {
        if (this.isBot != -1) {
            this.hp = 1;
            return;
        }
        if (this.currentDanhHieu == null || (this.currentDanhHieu != null && this.currentDanhHieu.isExpire())) {
            this.doOffDanhHieu();
        }
        if (this.killer > 32000) {
            this.killer = 32000;
        }
        if (this.monsTerThuThap != null && this.monsTerThuThap.isFinishThuThap()) {
            this.monsTerThuThap.doCancelThuthap();
            this.monsTerThuThap.actorDie();
            this.doAddGifThungGoNuiChauBau(this.lvDetail.lv);
            this.monsTerThuThap = null;
        }
        if (this.map != null && this.map.isMapTrain() && !this.map.canMove(this.x, this.y)) {
            final Hashtable<Short, Monster> mons = this.map.getAllMons(this.inCountry, this.region);
            if (mons.size() >= 0) {
                final short id = (short) Map.r.nextInt(mons.size() - mons.size() / 4);
                final Monster ms = mons.get(id);
                this.x = ms.default_x;
                this.y = ms.default_y;
                final Message m = new Message(4);
                this.writeActorPos(m, this);
                this.sendMessage(m);
            }
        }
        CharInfo ccb = null;
        if (this.map != null && (ccb = MapChauBau.all_char_nui_kho_bau.get(this.charname)) != null && ccb.getTimeNuiChauBau() <= 0L && (this.map.isMapChanNui() || this.map.isMapNuiChauBau())) {
            this.map.doReturnVillage(this);
        }
        ++this.countSecond;
        if (this.map != null && (this.map.isMapTrain() || this.map.isMapBoss() || this.map.isMapChienTruongMoba()) && this.hp <= 0 && this.timeHoiSinh > 0L && this.countSecond % 5 == 0) {
            final int second = (int) ((this.timeHoiSinh - System.currentTimeMillis()) / 1000L);
            if (second > 0) {
                if (!this.map.isMapChienTruongMoba()) {
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Hồi sinh sau " + second));
                }
            } else {
                this.timeHoiSinh = 0L;
                this.hp = this.maxhp;
                this.mp = this.maxmp;
                this.desTroy();
                try {
                    this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                    this.sendToNearPlayer(MessageCreator.createNew_HMP_Message(this, 0));
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                    this.sendPetEff();
                    if (this.map.isMapChienTruongMoba()) {
                        final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
                        this.x = MapChienTruongMoba.POS_REVIVAL[c.team][0] * 16;
                        this.y = MapChienTruongMoba.POS_REVIVAL[c.team][1] * 16;
                        this.map.move2Map(this, this.x / 16, this.y / 16, 40, this.inCountry);
                        this.doSendProperties();
                        this.sendInfoChienTruong(Char.ID_ALL_KHU_CHIEN_TRUONG_MOBA, 0);
                        this.sendInfoChienTruong(Char.ID_NOI_LUC, 0);
                        this.map.getRegionMoba(this.region).sendInfoPlayer(this);
                        return;
                    }
                    final Message i = new Message(4);
                    this.writeActorPos(i, this);
                    this.sendMessage(i);
                } catch (final Exception ex) {
                }
            }
        }
        if (this.countSecond >= 300) {
            try {
                this.checkExpireMDAmor();
                this.checkExpirePet();
                this.checkExpireItemInentry();
            } catch (final Exception ex2) {
            }
            ++this.minuteOnline;
            if (this.minuteOnline > 120) {
                this.minuteOnline = 0;
                if (Char.TOTAL_FANCUNG > 0) {
                    // TODO thêm danh hiệu fan cứng ở đây
                    final boolean isNew = this.addDanhHieu(8788, 1440L);
                    if (isNew) {
                        --Char.TOTAL_FANCUNG;
                        this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được danh hiệu Fan cứng"));
                    }
                }
            }
            this.countSecond = 0;
        }
        boolean DH_Daigia = true;
//        if (this.diemNapVip >= 200000 && DH_Daigia) {
//            final boolean isNew = this.addDanhHieu_VIP(8770, 43200L);
//            if (isNew) {
//                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được danh hiệu Đại gia."));
//            }
//        }
        if (this.charCopy != null) {
            if (this.hp <= 0 || (!this.charCopy.map.equals(this.map) && this.charCopy.isFollow() && !this.isDoChangeMap)) {
                if (isSuKienMiniChucNu()) {
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhiệm vụ tìm Chức nữ thất bại"));
                } else {
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhiệm vụ tìm trẻ lạc thất bại"));
                }
                this.charCopyDissapear();
            } else {
                this.charCopy.update();
            }
        }
        this.updateLuLan();
        if (this.charthanthu != null) {
            if (this.myCountry == this.inCountry && !this.isAdmin && System.currentTimeMillis() - this.charthanthu.timeAttack >= 0L) {
                this.charthanthu.timeAttack = System.currentTimeMillis() + (Map.r.nextInt(3) + 2) * 1000;
                final Vector<Char> players = this.getAllPlayerMap();
                try {
                    final Vector<LiveActor> target = new Vector<LiveActor>();
                    for (int j = players.size() - 1; j >= 0; --j) {
                        final Char p = players.get(j);
                        if (Map.inRangeActor(p, this, 120) && p.id != this.id && !p.isAdmin && p.isBot == -1 && p.hp > 0 && (p.isKiller || (p.myCountry != this.myCountry && p.inCountry == this.myCountry)) && !Map.isMapThanh(this.map)) {
                            target.add(p);
                            final Char char1 = p;
                            char1.hp -= this.getDamtThanThuAuto(p);
                            p.sendMessage(MessageCreator.createNew_HMP_Message(p, 0));
                            p.sendToNearPlayer(MessageCreator.createNew_HMP_Message(p, 0));
                            if (p.hp <= 0) {
                                p.actorDie();
                            }
                        }
                    }
                    if (target.size() > 0) {
              
                        this.charthanthu.doAttackAuto(target);
                    }
                } catch (final Exception ex3) {
                }
            }
            this.charthanthu.update();
        }
        if (this.charHire != null) {
            if (this.charHire.myCountry != this.myCountry) {
                this.charHire.myCountry = this.myCountry;
            }
            this.charHire.update();
        }
        this.updateEffectBuff();
        if (this.monster == null) {
            this.monster = MonsterVantieu.HAS_MONSTER_VANTIEU.get(this.charDBID);
        }
        if (this.timeUp5Attribute > 0L && System.currentTimeMillis() - this.timeUp5Attribute > 0L) {
            this.calculateAttrib();
            this.timeUp5Attribute = 0L;
            try {
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
            } catch (final Exception ex4) {
            }
        }
        if (this.timeExistCharHire > 0L && System.currentTimeMillis() - this.timeExistCharHire > 0L && this.charHire != null) {
            this.charhireDissapear();
            this.timeExistCharHire = 0L;
            this.lvlinh = 0;
            this.lvlinhthue = 0;
            this.classlinh = 0;
            this.sendMessage(MessageCreator.createMsgChat(this.id, "Hộ vệ đã hết hạn"));
            Database.instance.saveOrtherLog("", this.charname, "linh thue het han:c= " + this.classlinh + ",g=" + this.genderlinh + ",lvl=" + this.lvlinhthue + ",lvcl=" + this.lvlinh, "lhethan");
        }
        if (this.isNguoiTuyet() && System.currentTimeMillis() - this.timeNguoiTuyet >= 0L) {
            if (this.idNgtuyet == 3) {
                try {
                    this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                } catch (final IOException ex5) {
                }
            }
            this.idNgtuyet = -1;
            this.map.charNguoiTuyetDissapear(this);
        }
        // hoa hinh
        if (this.currentDefReduc > 0) {
            long currentTime = System.currentTimeMillis();
            // Kiểm tra nếu đã quá 10s kể từ lần giảm đầu tiên
            if (this.timeFirstDefReduc > 0 && currentTime - this.timeFirstDefReduc >= 10000) {
                // Reset toàn bộ
                this.currentDefReduc = 0;
                this.timeFirstDefReduc = 0L;
                this.timeResetDefReduc = 0L;
                
                // Thông báo cho người chơi
                try {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Đã hết hiệu ứng giảm giáp!"));
                    // Cập nhật thông tin cho người chơi và người chơi xung quanh
                    Message msg = MessageCreator.createCharInfo(this);
                    this.sendMessage(msg);
                    this.sendToNearPlayer(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (System.currentTimeMillis() - this.timeRegentHpMp >= 5000L && this.hp > 0) {
            if (this.petUsing != null && this.petUsing.isPetKhi() && Map.r.nextInt(100) < 50) {
                this.setNguoiTuyet(5);
            }
            if (this.haveRegentHP() > 0 && this.hp < this.maxhp && this.hp > 0 && !this.isVetThuongSau() && !this.checkChoang()) {
                final int a = this.haveRegentHP();
                this.hp += a;
                if (this.calculatorHPMP()) {
                    try {
                        this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                    } catch (final Exception ex6) {
                    }
                }
                try {
                    this.sendMessage(MessageCreator.createNew_HMP_Message(this, a));
                    this.sendToNearPlayer(MessageCreator.createNew_HMP_Message(this, a));
                } catch (final IOException ex7) {
                }
            }
            if (this.haveRegentMP() > 0 && this.mp < this.maxmp && this.hp > 0 && !this.isYemBua()) {
                final int a = this.haveRegentMP();
                this.mp += a;
                this.calculatorHPMP();
                try {
                    this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                } catch (final Exception ex8) {
                }
                MessageCreator.createMsgUseHpMP(this, a, 4);
            }
            this.timeRegentHpMp = System.currentTimeMillis();
            final Vector<EffectBuff> rm = new Vector<EffectBuff>();
            for (int k = 0; k < this.ALL_BUFF_INMAP.size(); ++k) {
                final EffectBuff ef = this.ALL_BUFF_INMAP.get(k);
                if (ef.timeOut()) {
                    rm.add(ef);
                    if (ef.idEff == EffectBuff.LUA_NHO || ef.idEff == EffectBuff.LUA_VUA || ef.idEff == EffectBuff.LUA_TO) {
                        this.idCuiDot = -1;
                    }
                }
            }
            for (int k = 0; k < rm.size(); ++k) {
                this.ALL_BUFF_INMAP.remove(rm.get(k));
            }
        }
        if (this.monster != null) {
            if (this.monster.map.mapId == this.map.mapId) {
                this.monster.target = this;
            }
            try {
                if (this.monster.isFinish()) {
                    final Message l = new Message(90);
                    l.dos.writeShort(this.monster.id);
                    l.dos.writeByte(this.monster.cat);
                    if (this.monster.map != null) {
                        this.monster.map.sendAllPlayer(l, this.monster.inCountry);
                    }
                    this.monster = null;
                    this.typeTieu = -1;
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        if (this.timeWaitComeHome > 0L && System.currentTimeMillis() - this.timeWaitComeHome > 30000L && this.mapID == Map.idMapTown) {
            try {
                if (this.hp <= 0) {
                    final int[][] mapstart = {{70, 1701}, {0, 301}, {80, 1901}};
                    this.hp = 1;
                    this.desTroy();
                    try {
                        this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                    } catch (final IOException ex9) {
                    }
                    final int idVilage = mapstart[this.inCountry][Map.r.nextInt(mapstart[this.inCountry].length)];
                    this.map.move2Map(this, 16 + Database.r.nextInt() % 5, 24 + Database.r.nextInt(20), idVilage, this.inCountry);
                    this.resetTimeLastuseSkill();
                    if (this.petUsing != null && !this.petUsing.isPetTool() && this.petUsing.getIdEffPet() > -1 && this.addEffBuff(this.petUsing.getIdEffPet(), System.currentTimeMillis() + 320000000L, EffectBuff.BY_ACTOR, 0) != null) {
                        this.sendEffToChar(this);
                    }
                    this.timeWaitComeHome = 0L;
                } else {
                    this.timeWaitComeHome = 0L;
                }
            } catch (final Exception ex10) {
            }
        }
        if (this.minuteSocola > 0 && System.currentTimeMillis() - this.timeEatSocola >= 60000L) {
            --this.minuteSocola;
            this.timeEatSocola = System.currentTimeMillis();
            try {
                if (this.minuteSocola <= 0) {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng hạt mầm của bạn đã hết"));
                    Database.instance.saveOrtherLog("", this.charname, "loc may man het gio", "ohoatuyet");
                }
            } catch (final IOException ex11) {
            }
        }
        if (this.lasTimeMove > 0L && System.currentTimeMillis() - this.lasTimeMove > 0L) {
            this.lasTimeMove = System.currentTimeMillis() + 3000L;
            this.sendInfoMove2Near();
        }
        if (this.animalRide != null) {
            final byte timeout = this.animalRide.timeOutFood();
            if (timeout > -1) {
                try {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(LoginHandler.PORTION_NAME[timeout + 112]) + " đã hết tác dụng."));
                    Database.instance.saveOrtherLog("", this.charname, LoginHandler.PORTION_NAME[timeout + 112], "aexpire");
                    this.calculateAttrib();
                    this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                    this.sendMessage(MessageCreator.createCharWearingMessage(this, this));
                } catch (final Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        try {
            if (this.millisOnlineStart == 0L) {
                this.millisOnlineStart = System.currentTimeMillis();
            }
            final Calendar calGame = Calendar.getInstance();
            final Calendar calNow = Calendar.getInstance();
            calGame.setTime(new Date(this.millisOnlineStart));
            if (!this.dateStartOnline.trim().equals("0") && !this.dateStartOnline.trim().equals("") && this.calCheck == null) {
                try {
                    final int y = Integer.parseInt(this.dateStartOnline.substring(0, 2)) + 2000;
                    final int m2 = Integer.parseInt(this.dateStartOnline.substring(2, 4));
                    final int d = Integer.parseInt(this.dateStartOnline.substring(4, 6));
                    (this.calCheck = Calendar.getInstance()).set(y, m2, d);
                } catch (final Exception ex12) {
                }
            }
            if (calGame.get(5) != calNow.get(5)) {
                this.millisOnlineStart = System.currentTimeMillis();
                this.rcvGifByHour = 1;
                this.isOnlineToDay = false;
                ++this.totalDayInMonth;
            }
            if (calGame.get(2) != calNow.get(2)) {
                this.totalDayInMonth = 1;
                this.rcvGifByMonth = 1;
            }
            this.millisOnline = System.currentTimeMillis() - this.millisOnlineStart;
            this.bonusTime();
            this.bonusDay();
            this.bonusMonth();
            this.bonusLevel();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        this.beAttack = false;
        try {
            if ((Map.checkRcvXP || (isAcountTest(this) && Char.onOffTime180 == 1)) && Map.openLog && System.currentTimeMillis() - this.startPlay >= 60000L) {
                ++this.timeXP;
                this.startPlay = System.currentTimeMillis();
                if (this.timeXP == 179) {
                    this.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã chơi được " + this.timeXP + " phút. Bạn sẽ không thể tiếp tục chơi sau 1 phút nữa.", ""));
                } else if (this.timeXP == 180) {
                    this.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã chơi được " + this.timeXP + " phút. Bạn không thể tiếp tục chơi tiếp.", ""));
                    CharManager.instance.kickPlayer(this, "char 4");
                }
            }
            if (this.timeCrazy > 0L && System.currentTimeMillis() - this.timeCrazy > ((this.isCrazy == 0) ? 60000 : 30000)) {
                this.timeCrazy = 0L;
                this.isCrazy(this.isCrazy);
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                MessageCreator.createMsgCharMonster(this, this);
            }
            if (this.timeExit > 0L && System.currentTimeMillis() - this.timeExit > 5000L) {
                CharManager.instance.kickPlayer(this, "char 5");
                return;
            }
            if (this.mapID != 204) {
                if (!MessageCreator.isVersion162() && this.timedie > 0L && System.currentTimeMillis() - this.timedie >= 1000L) {
                    if (this.myCountry > -1 && !Map.idMapMONSTER.get(this.myCountry).contains(this.map.mapId)) {
                        final int[] cost = {1000, 2000, 3000, 4000, 5000, 10000, 15000, 20000, 30000, 60000, 70000, 500000, 1000000, 1300000, 1500000, 2000000, 2500000, 3000000, 4500000};
                        int cp = cost[this.lvDetail.lv / 5];
                        if (this.mapID == 112 || this.mapID == 2541 || this.mapID == 2542 || this.mapID == 2543 || this.mapID == 2544) {
                            cp = 500000;
                        // } else if (this.mapID == 206) {
                        //     cp = 3000000;
                        } else {
                            if (this.myCountry != this.inCountry && this.charthanthu != null) {
                                cp = cost[this.lastLV / 5];
                            }
                            if (this.charthanthu != null && this.isKiller) {
                                cp *= 2;
                            }
                        }
                        if (this.mapID == 204 || this.mapID == 205) {
                            cp = 0;
                        }
                        if (cp > 0) {
                            if (this.potions[119] > 0) {
                                final String[] menu = {"Dùng tiên đan", "Hs bằng xu (" + cp + " xu)"};
                                this.sendMessage(MessageCreator.createMsgMenu(menu, -802, 0));
                            } else {
                                this.sendMessage(MessageCreator.createMsgPopUp(this.id, 1, "Bạn muốn hồi sinh tại chỗ không. Chi phí " + cp + " xu"));
                            }
                        } else {
                            this.sendMessage(MessageCreator.createMsgPopUp(this.id, 1, "Bạn muốn hồi sinh tại chỗ không?"));
                        }
                    }
                    this.timedie = 0L;
                }
                if (this.kickOutTown()) {
                    final int[] mapstart2 = {9, 481, 482, 483, 484};
                    final int homeX = 31 + Database.r.nextInt() % 5;
                    final int homeY = 79 + Database.r.nextInt(20);
                    this.map.move2Map(this, homeX, homeY, mapstart2[Map.r.nextInt(MessageCreator.nclone)], this.inCountry);
                }
            } else if (this.timedie > 0L && System.currentTimeMillis() - this.timedie >= 10000L) {
                this.hp = this.maxhp;
                this.mp = this.maxmp;
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                this.sendToNearPlayer(MessageCreator.createNew_HMP_Message(this, 0));
                this.timedie = 0L;
            }
            if (this.timeSendHelp > 0L && ((System.currentTimeMillis() - this.timeSendHelp > 3000L && this.indexHelp < Text.help.length) || this.indexHelp == 0)) {
                final Message l = new Message(27);
                l.dos.writeShort(this.idBot);
                l.dos.writeUTF(Text.help[this.indexHelp++]);
                this.timeSendHelp = System.currentTimeMillis();
                final Char bot = CharManager.instance.getByCharID(this.idBot);
                if (bot != null && this.near(bot, 220)) {
                    this.sendMessage(l);
                } else {
                    this.indexHelp = Text.help.length;
                    this.timeSendHelp = 0L;
                }
            }
            if (this.timeStartHack > 0L && System.currentTimeMillis() - this.timeStartHack > 30000L) {
                CharManager.instance.kickPlayer(this, "char 6");
                return;
            }
            if (Map.config == 0) {
                this.resetConfig();
            } else {
                this.nNearchar = 30000;
            }
            if (this.nNearchar != 30000) {
                if ((this.myCountry > -1 && this.mapID == Map.idMapDautruong) || this.mapID == 204 || this.mapID == 205 || (this.mapID == Map.idMapTown && Map.getTown[this.myCountry])) {
                    this.nNearchar = 30000;
                } else if (Map.config == 0) {
                    this.resetConfig();
                }
            }
            if (this.autoGetItem == 1) {
                try {
                    while (this.idItem.size() > 0) {
                        this.map.doGetItem(this, this.idItem.remove(0));
                    }
                    while (this.idPotion.size() > 0) {
                        this.map.doGetPotion(this, this.idPotion.remove(0));
                    }
                    while (this.idGem.size() > 0) {
                        this.map.doGetGemItem(this, 6, this.idGem.remove(0));
                    }
                    while (this.idSP.size() > 0) {
                        this.map.doGetGemItem(this, 7, this.idSP.remove(0));
                    }
                    while (this.idItemQuest.size() > 0) {
                        this.map.doGetItemQuest(this, this.idItemQuest.remove(0));
                    }
                } catch (final Exception ex13) {
                }
            }
            if (this.beStune && System.currentTimeMillis() > this.timeBeStune) {
                this.beStune = false;
            }
            this.updateGoldTime();
            if (System.currentTimeMillis() - this.timeWait >= TeamServer.timeBoardRun && this.map != null) {
                if (this.gotoIsland && (this.mapID == 106 || this.mapID == 2421 || this.mapID == 2422 || this.mapID == 2423 || this.mapID == 2424)) {
                    int id2 = this.mapID % 2420;
                    if (this.mapID == 106) {
                        id2 = 0;
                    }
                    if (id2 != 0) {
                        id2 += 2440;
                    } else {
                        id2 = 107;
                    }
                    this.map.move2Map(this, 19 + Map.random(3) + 1, 10 + Map.random(3) + 1, id2, this.inCountry);
                }
                this.gotoIsland = false;
            }
            if (this.tDelay > 0 && System.currentTimeMillis() - this.timeOutPoinson >= this.tDelay * 1000) 
            {
                if (this.hpDocLan > 0) 
                {
                    this.hp += this.hpDocLan;
                    if (this.calculatorHPMP()) {
                        try {
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                        } catch (final Exception ex14) {
                        }
                    }
                    try {
                        this.sendMessage(MessageCreator.createNew_HMP_Message(this, this.hpDocLan));
                        this.sendToNearPlayer(MessageCreator.createNew_HMP_Message(this, this.hpDocLan));
                    } catch (final IOException ex15) {
                    }
                }
                this.hp -= this.poinson;
                this.totalTime -= (byte) this.tDelay;
                this.timeOutPoinson = System.currentTimeMillis();
                if (this.totalTime <= 0) 
                {
                    this.totalTime = 36;
                    this.tDelay = 0;
                    this.poinson = 0;
                    this.hpDocLan = 0;
                }
                if (this.hp <= 0) 
                {
                    this.hp = 0;
                    this.totalTime = 36;
                    this.tDelay = 0;
                    System.out.println("update khi nhan vat die: " + charname + ", hp còn lại: " + this.hp);
                    this.actorDie();
                    this.desTroy();
                    if (this.isMonster) 
                    {
                        final Vector<Char> players = this.map.getAllPlayer(Map.openLog ? 0 : this.myCountry, this.region);
                        for (int k = 0; k < players.size(); ++k) {
                            final Char ccc = players.get(k);
                            if (ccc != null && ccc.hp > 0 && !ccc.isMonster) {
                                final int[] potions = ccc.potions;
                                final int n = 102;
                                ++potions[n];
                                ccc.sendMessage(MessageCreator.createCharInventoryMessage(ccc, 0));
                                ccc.sendMessage(MessageCreator.createMsgChat(ccc.id, "Nhận được rương bạc"));
                            }
                        }
                        this.map.charMonsterDissapear(this);
                        this.hp = this.maxhp;
                        this.mp = this.maxmp;
                        this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                        this.sendToNearPlayer(MessageCreator.createNew_HMP_Message(this, 0));
                    }
                    if (Map.giveCardFail(this)) {
                        this.map.doSend2AllChar(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " giao thẻ thất bại"), this.myCountry);
                        this.map.sendAllPlayer(MessageCreator.createMsgStartGetTown(this.inCountry), this.inCountry);
                    }
                    this.map.onActorDie(this);
                }
            }
            if (this.isKiller && System.currentTimeMillis() - this.timeKiller >= 86400000L && this.nPKill > 0) {
                this.nPKill = 0;
                this.timeKiller = 0L;
            }
            for (int i2 = 0; i2 < this.arrayBuff.length; ++i2) {
                if (this.arrayBuff[i2] != -1) {
                    final long t = System.currentTimeMillis() - this.timeUseBuff[i2];
                    if (t / 1000L > this.countDown[i2]) {
                        final short[] countDown = this.countDown;
                        final int n2 = i2;
                        ++countDown[n2];
                        if (this.countDown[i2] >= this.timeOut[i2]) {
                            if (this.arrayBuff[i2] == 25 || this.arrayBuff[i2] == 20) {
                                Message m3 = null;
                                try {
                                    if (this.arrayBuff[i2] == 25) {
                                        this.percentBuff[1] = 0;
                                        this.percentBuff[2] = 0;
                                        this.calculatorHPMP();
                                    } else if (this.arrayBuff[i2] == 20) {
                                        this.percentBuff[0] = 0;
                                    }
                                    m3 = MessageCreator.createMainCharInfoMessage(this);
                                    this.sendMessage(m3);
                                } catch (final Exception ex16) {
                                } finally {
                                    try {
                                        m3.cleanup();
                                    } catch (final Exception ex17) {
                                    }
                                }
                                try {
                                    m3.cleanup();
                                } catch (final Exception ex18) {
                                }
                            }
                            this.arrayBuff[i2] = -1;
                            this.countDown[i2] = 0;
                        }
                    }
                }
            }
            this.updateNearChar();
            this.subUpdate();
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("LOI TRONG UPDATE CHAR " + this.mapID);
        }
        this.updateEffKham();
        if (!AdminHandler.isStopServer && System.currentTimeMillis() - this.timeSave > 0L && !AdminHandler.isStopServer) {
            final Char pp = this;
            ThreadPool.instant.workList.add(new Runnable() {
                @Override
                public void run() {
                    Database.instance.saveCharAuto(pp);
                }
            });
            Database.instance.saveOrtherLog("", this.charname, String.valueOf(this.lvDetail.lv) + "_" + this.lvDetail.getExp() + "_" + this.xpLost + "_" + this.xu + "_" + this.luong + " xutim: " + this.xutim + " xumat: " + this.xumat, "autosave");
            Database.instance.saveOrtherLog("", this.charname, String.valueOf(this.xu) + "_" + this.xumat + "_" + this.xutim, "logxu");
            String[] info = this.getListGem();
            info = this.getInfoSaveTubinh();
           
            this.allGemGet = new int[Map.gemTemplate.length];
            this.allGemUse = new int[Map.gemTemplate.length];
            this.allGemGetLock = new int[Map.gemTemplate.length];
            this.allGemUseLock = new int[Map.gemTemplate.length];
            final short[] time = {120, 150, 180, 210};
            this.timeSave = System.currentTimeMillis() + time[Map.r.nextInt(time.length)] * 60 * 1000;
        }
    }

    public void doAddGifThungGoNuiChauBau(final int lv) {
        final CharInfo cinfo = MapChauBau.all_char_nui_kho_bau.get(this.charname);
        String info = "Nhận được ";
        int sl = 1;
        int idGem = 0;
        final int idLkd = GemTemplate.LKD_30;
        final int[] in = {0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
        int index = in[Map.r.nextInt(in.length)];
        final int pcBonus = Map.r.nextInt(100);
        if (lv < 60) {
            idGem = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(2) + 1][Map.r.nextInt(5)];
            if (pcBonus < 10) {
                if (index == 0) {
                    sl = Map.r.nextInt(2) + 1;
                    info = String.valueOf(info) + sl + " lkd 30% " + (cinfo.isLockGift ? "khoá" : "không khoá.");
                    this.doAddGemItem(idLkd, sl, cinfo.isLockGift);
                } else if (index == 1) {
                    final int time = Map.r.nextInt(2) + 1;
                    final Item it = Map.doCreateVuKhiThoiTrang(this, time * 24 * 60, Map.ID_VU_KHI_THOI_TRANG[this.charClass]);
                    info = String.valueOf(info) + it.getName() + " thời hạn " + time + " ngày.";
                } else if (index == 2) {
                    final int[][] iditem = {{725, 723}, {726, 724}};
                    final Item it = Map.doBuyModelClothe(this, this.isCharNam() ? iditem[0][Map.r.nextInt(2)] : iditem[1][Map.r.nextInt(2)], 1, 1);
                    info = String.valueOf(info) + " " + it.getName() + " thời hạn " + 1 + " ngày.";
                }
            }
        } else if (lv < 80) {
            idGem = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(2) + 2][Map.r.nextInt(5)];
            if (pcBonus < 10) {
                if (index == 0) {
                    sl = Map.r.nextInt(2) + 1;
                    info = String.valueOf(info) + sl + " lkd 35% " + (cinfo.isLockGift ? "khoá" : "không khoá.");
                    this.doAddGemItem(GemTemplate.LKD_35, sl, cinfo.isLockGift);
                } else if (index == 1) {
                    index = Map.r.nextInt(3);
                    sl = Map.r.nextInt(2) + 1;
                    if (index == 0) {
                        this.createNguaXuong(sl, false);
                        info = String.valueOf(info) + "Bạch cốt mã thời hạn " + sl + " ngày";
                    } else if (index == 1) {
                        this.createHeo(11L, false);
                        info = String.valueOf(info) + "Đương khang thời hạn " + sl + " ngày";
                    } else if (index == 2) {
                        this.createLan(1440L, false, 95);
                        info = String.valueOf(info) + "Lân sư tử thời hạn " + sl + " ngày";
                    }
                } else if (index == 2) {
                    final int[][] iditem = {{725, 723}, {726, 724}};
                    final Item it = Map.doBuyModelClothe(this, this.isCharNam() ? iditem[0][Map.r.nextInt(2)] : iditem[1][Map.r.nextInt(2)], 1, 1);
                    info = String.valueOf(info) + " " + it.getName() + " thời hạn " + 1 + " ngày.";
                }
            }
        } else {
            idGem = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(2) + 3][Map.r.nextInt(5)];
            if (pcBonus < 10) {
                if (index == 0) {
                    sl = Map.r.nextInt(2) + 1;
                    info = String.valueOf(info) + sl + " lkd 40% " + (cinfo.isLockGift ? "khoá" : "không khoá.");
                    this.doAddGemItem(GemTemplate.LKD_40, sl, cinfo.isLockGift);
                } else if (index == 1) {
                    final int[] idchoi = {617, 619, 729, 618};
                    final int time2 = Map.r.nextInt(2) + 1;
                    final boolean isBienHinh = Map.r.nextInt(2) == 1;
                    final Item it2 = createChoi(idchoi[Map.r.nextInt(idchoi.length)], this, time2 * 24 * 60, 0, isBienHinh);
                    info = String.valueOf(info) + " " + it2.getName() + " thời hạn " + time2 + " ngày. " + (isBienHinh ? "có hoá tuyết" : "không có hoá tuyết");
                } else if (index == 2) {
                    final int[][] iditem = {{725, 723}, {726, 724}};
                    final Item it = Map.doBuyModelClothe(this, this.isCharNam() ? iditem[0][Map.r.nextInt(2)] : iditem[1][Map.r.nextInt(2)], 1, 1);
                    info = String.valueOf(info) + " " + it.getName() + " thời hạn " + 1 + " ngày.";
                }
            }
        }
        sl = Map.r.nextInt(3) + 1;
        this.doAddGemItem(idGem, sl, cinfo.isLockGift);
        info = String.valueOf(info) + " " + sl + " " + Map.gemTemplate[idGem].name;
        Database.instance.saveOrtherLog("", this.charname, info, "quakhobau");
        this.sendMessage(MessageCreator.createMsgChat(this.id, info));
        this.sendMessage(MessageCreator.createCharGemItem(this));
    }

    public boolean kickOutTown() {
        if (this.isAdmin) {
            return false;
        }
        if (this.map.mapId == Map.idMapTown) {
            if ((Map.getTown[this.inCountry] && this.myCountry != this.inCountry) || (Map.getTown[this.inCountry] && this.lvDetail.lv < 50)) {
                return true;
            }
            if (Map.getTown[this.inCountry] && !MapTown.congthanh.get(this.inCountry).isDead && this.y < 1200) {
                return true;
            }
        }
        return false;
    }

    public void receiveGiftDay() {
    }

    public static boolean canSendRaoVat() {
        if (!Map.openLog) {
            return false;
        }
        if (Char.infoAdv2.size() <= 0) {
            return false;
        }
        final long time = System.currentTimeMillis() - Char.timeSendRaoVat;
        if (time >= 0L) {
            if (time >= 20000L) {
                Char.timeSendRaoVat = System.currentTimeMillis() + 900000L;
            }
            return false;
        }
        return true;
    }

    public Vector<Char> getAllPlayerMap() {
        Vector<Char> p = new Vector<Char>();
        p = this.map.getAllPlayer((this.mapID == 225 || this.mapID == 226) ? this.idWedding : ((this.mapID == 204 || this.mapID == 205) ? this.idArena : this.inCountry), (this.mapID == 206) ? this.idClan : this.region);
        return p;
    }

    public void doAddGiftEventOutGame() {
    }

    public void setMocNap() {
        if (this.tichluy > 0 && this.luongNap == 0) {
            this.luongNap = this.tichluy / 2;
        }
    }

    public static Item createtemCoat_chonkhang(int idTemplate, Char p, int mavat, boolean ismax, int time, int khang) 
    {
        Item newItem = new Item();
        ItemTemplates item = (ItemTemplates) ((Hashtable) Map.itemTemplates.get(5)).get(Integer.valueOf(idTemplate));
        for (int i = 0; i < 10; i++) 
        {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short) (item.atb[i] + item.atb[i] / 4);
            }
        }
        newItem.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = (newItem.getTemplate()).level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        if (time > 0) {
            newItem.minuteExist = time * 24 * 60;
            newItem.timeLoan = System.currentTimeMillis();
        }

        /*
        5 - Kháng hoả
        1 - Kháng thủy
        2 - Kháng mộc
        3 - Kháng kim
        4 - Kháng thổ
         */
        newItem.createAttributeItemCoat(ismax, (byte) mavat, khang);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        System.out.println(String.valueOf(idTemplate) + "_" + ((mavat == 0) ? "maphap" : "vat ly ") + newItem.getInfoSave());
        return newItem;
    }

    public static Item createtemCoat(int idTemplate, Char p, int mavat, boolean ismax, int time) {
        Item newItem = new Item();
        ItemTemplates item = (ItemTemplates) ((Hashtable) Map.itemTemplates.get(5)).get(Integer.valueOf(idTemplate));
        for (int i = 0; i < 10; i++) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short) (item.atb[i] + item.atb[i] / 4);
            }
        }
        newItem.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = (newItem.getTemplate()).level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.minuteExist = time;
        if (time > 0) {
            newItem.timeLoan = System.currentTimeMillis();
        }
        newItem.createAttributeItemCoat(ismax, (byte) mavat, -1);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        System.out.println(String.valueOf(idTemplate) + "_" + ((mavat == 0) ? "maphap" : "vat ly ") + newItem.getInfoSave());
        return newItem;
    }

    // TODO BẬT TẮT QUA TICH LUY
    public static boolean TYPE_QUA_TICH_LUY = true;

    public void doAddQuaTichLuy(int QuaTuan) {
//        if (!this.charname.equals("chienthan")) {
//            return;
//        }

        if (TYPE_QUA_TICH_LUY == false) {
            return;
        }

        String result = "0|NO DATA";
        if (QuaTuan == 1) {
            result = Database.instance.getCheckQua_Tuan(this);
        } else if (QuaTuan == 2) {
            result = Database.instance.getCheckQua(this);
        } else if (QuaTuan == 3) {
            result = Database.instance.getCheckQua_Bonus(this);
        }
        // System.out.println(result);
        String[] tach_string = result.trim().split("\\|");
        int id = Integer.parseInt(tach_string[0]);
        if (id == 1) {
            if (this.getLevel() < Integer.parseInt(tach_string[5])) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải đặt Level " + tach_string[5] + " thì mới nhận được GiftCode", ""));
                return;
            }
            String code = tach_string[6];
            //System.out.println(this.charname + " nhap CODE: " + code + " KQ= " + result + " ID= " + id);
            int xu = Integer.parseInt(tach_string[1]);
            int luong = Integer.parseInt(tach_string[2]);
            int luongkhoa = Integer.parseInt(tach_string[3]);
            String listItem = tach_string[4];
            //System.out.println("XU: " + xu + " - luong: " + luong + " - luongkhoa: " + luongkhoa + " - listItem: " + listItem + " - ");
            if (xu > 0) {
                this.addXu(xu, "nhan tu giftcode" + code);
            }
            if (luong > 0) {
                this.addLuong(luong);
            }
            if (luongkhoa > 0) {
                this.addLuongLock(luongkhoa);
            }
            if (!listItem.equals("NULL")) {
                String[] tachItem = listItem.trim().split(",");
                for (int i = 0; i < tachItem.length; i++) {
                    String[] tachTime = tachItem[i].trim().split(":");
                    if (tachTime[0].equals("DB")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            GiftCode.AddItem(this, Integer.parseInt(tachTime[1]), 0, 0, Integer.parseInt(tachTime[3]));
                        }
                    } else if (tachTime[0].equals("EGG")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createTrungPhuongHoang(1L, Integer.parseInt(tachTime[1]));
                        }
                    } else if (tachTime[0].equals("PET")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createPet(Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]));
                        }
                    } else if (tachTime[0].equals("TK")) {
                        this.createThanKhi(this, Integer.parseInt(tachTime[1]), 0, Integer.parseInt(tachTime[3]), null, false, true, 1, false);    
                    } else if (tachTime[0].equals("PETV")) {
                           
                        this.createPetVinhVien(Integer.parseInt(tachTime[1]));    
                    } else if (tachTime[0].equals("HLT")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            Map.doCreateHuyetLinhThao(this, Integer.parseInt(tachTime[1]));
                        }
                    } else if (tachTime[0].equals("HOVELAN")) {
                        this.createHoVeLenhLan(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[3]), 1);
                    } else if (tachTime[0].equals("TRANGPHUC")) {
                        // Format: TRANGPHUC:itemID:quantity:days
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            Char.doDropModelClothe(this, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]));
                        } 
                        
                    } else if (tachTime[0].equals("DANHHIEU2")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.addDanhHieu(Short.parseShort(tachTime[1]), Long.parseLong(tachTime[3]));
                        }
                        try {
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                            this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                        } catch (IOException ex) {
                        } 
                } else if (tachTime[0].equals("DH")) {
                    // Danh sách ID danh hiệu có sẵn
                    int[] availableTitles = {
                        8767, 8768, 8769, 8770, 8771, 8772, 8773, 8774, 8775, 8776, 8777,
                        8778, 8779, 8785, 8786, 8787, 8788, 8900, 8901, 8902, 8903, 8904,
                        8905, 8906, 8907, 8908, 8909, 8910, 8911, 8924, 8925, 8935, 8956, 8957
                    };
                    
                    // Nếu ID = 0, chọn ngẫu nhiên một danh hiệu
                    int titleId = Integer.parseInt(tachTime[1]);
                    if (titleId == 0) {
                        titleId = availableTitles[Map.r.nextInt(availableTitles.length)];
                    }
                    
                    for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                        this.addDanhHieu(titleId, Long.parseLong(tachTime[3]));
                    }
                    try {
                        this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                        this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                    } catch (IOException ex) {
    }


                    } else if (tachTime[0].equals("PPR")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createtemCoat(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), false, Integer.parseInt(tachTime[3]));
                        }
                    } else if (tachTime[0].equals("PPRK")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createtemCoat_chonkhang(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), false, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[5]));
                        }
                    } else if (tachTime[0].equals("PPV")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createtemCoat(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), true, Integer.parseInt(tachTime[3]));
                        }
                    } else if (tachTime[0].equals("PPVK")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createtemCoat_chonkhang(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), true, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[5]));
                        }

                    } else if (tachTime[0].equals("DH")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.addDanhHieu(Short.parseShort(tachTime[1]), Long.parseLong(tachTime[3]));
                        }
                        try {
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                            this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                        } catch (IOException ex) {
                        }
                    } else if (tachTime[0].equals("DANHHIEU2")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.addDanhHieu(Short.parseShort(tachTime[1]), Long.parseLong(tachTime[3]));
                        }
                        try {
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                            this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                        } catch (IOException ex) {
                        }    
                    } else if (tachTime[0].equals("PT")) {
                        int idGem = Integer.parseInt(tachTime[1]);
                        int soluong = Integer.parseInt((tachTime[2]));
                        this.potions[idGem] = this.potions[idGem] + soluong;
                        this.calculateAttrib();
                        try {
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                        } catch (IOException ex) {
                        }
                    } else if (tachTime[0].equals("GEM")) {
                        boolean lockitemgem = true;
                        try {
                            lockitemgem = (Integer.parseInt(tachTime[3]) < 0 ? true : false);
                        } catch (Exception e) {
                            lockitemgem = true;
                            e.printStackTrace();
                        }
                        this.doAddGemItem(Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[2]), lockitemgem);
                        this.sendMessage(MessageCreator.createCharGemItem(this));
                    } else if (tachTime[0].equals("PP")) {
                        Char.createPhiPhongMaxBaoKich(this, 0, Integer.parseInt(tachTime[3]), (Item) null, false, false, 0, true);
                        Char.createPhiPhongMaxBaoKich(this, 1, Integer.parseInt(tachTime[3]), (Item) null, false, false, 0, true);
                    } else if (tachTime[0].equals("PPLUCK")) {
                        this.createtemCoat(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), true, 0);
                    } else if (tachTime[0].equals("PP8X")) {
                        Char.createPhiPhongMaxBaoKich(this, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 5, true);
                        Char.createPhiPhongMaxBaoKich(this, 1, Integer.parseInt(tachTime[3]), (Item) null, true, true, 5, true);
                    } else if (tachTime[0].equals("VKTT")) {
                        Char.createVuKhiThoiTrang(this, 0, Integer.parseInt(tachTime[3]), (Item) null, false, true, 1, false);
                    } else if (tachTime[0].equals("VKMAX")) {
                        Char.createVuKhiThoiTrang(this, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 1, false);
                    } else if (tachTime[0].equals("VKTB")) {
                        Char.createVuKhiThoiTrangTHANBINH_NEW(this, 0, Integer.parseInt(tachTime[3]), (Item) null, false, true, 1, false);
                    } else if (tachTime[0].equals("VKTBMAX")) {
                        Char.createVuKhiThoiTrangTHANBINH_NEW(this, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 1, false);
                    } else if (tachTime[0].equals("BT")) {
                        this.doCreatePetBachThu(Integer.parseInt(tachTime[3]), false);
                    } else if (tachTime[0].equals("AOTT")) {
                        if (Integer.parseInt(tachTime[1]) <= 755) {
                            Map.doBuyModelClothe(this, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]), 1);
                        } else {
                            ManagerGame.doBuyModelClothe(this, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]), 1);
                        }
                    } else if (tachTime[0].equals("AOSHOP")) {
                        doDropModelClothe(this, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]));
                    } else if (tachTime[0].equals("CHOI")) {
                            this.createChoi(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[4]), true);
                    } else if (tachTime[0].equals("MATNA")) {
                        int[] valuemn = {5, 5, 10, 30, 50};
                        int randomMN = Map.r.nextInt(valuemn.length);
                        if (Integer.parseInt(tachTime[1]) > -1) {
                            randomMN = Integer.parseInt(tachTime[1]);
                        }
                        Item newitem = Map.doCreateMatNa3(this, Integer.parseInt(tachTime[3]), ID_MAT_NA[randomMN], randomMN, valuemn[randomMN]);
                        this.sendMessage(MessageCreator.createServerAlertMessage("Nhận " + newitem.getName(), ""));
                    } else if (tachTime[0].equals("MATNA2")) {
                        // Tạo mặt nạ với option và value random
                        int[] optionValues = {5, 5, 10, 30, 50}; // Các giá trị value có thể
                        int randomIndex = Map.r.nextInt(optionValues.length);
                        int matnaID = Integer.parseInt(tachTime[1]); // ID mặt nạ
                        int option = randomIndex; // Option mặt nạ (0-4)
                        int value = optionValues[randomIndex];  // Giá trị option tương ứng
                        Item newitem = Map.doCreateMatNa2(this, Integer.parseInt(tachTime[3]), matnaID, option, value);
                        this.sendMessage(MessageCreator.createServerAlertMessage("Nhận " + newitem.getName() + (Integer.parseInt(tachTime[3]) > 0 ? " " + Integer.parseInt(tachTime[3])/1440 + " ngày" : " vĩnh viễn"), ""));    
// ... existing code ...    
                    } else if (tachTime[0].equals("DK")) {
                        this.createHeo(0, false);
                    } else if (tachTime[0].equals("TL")) {
                        this.createTuanLoc(Integer.parseInt(tachTime[1]));
                    } else if (tachTime[0].equals("DBT")) {
                        this.createPoro(Integer.parseInt(tachTime[1]), false);

                        ///  todo REGEX quà tích lũy 
                    } else if (tachTime[0].equals("SUTU")) {
                        this.createSuTu(Integer.parseInt(tachTime[1]), true);
                    
                    }  else if (tachTime[0].equals("LANT")) {
                        this.createLan(Integer.parseInt(tachTime[1]), false, 95);

                    } else if (tachTime[0].equals("DKT")) {
                        this.createHeo(Integer.parseInt(tachTime[1]), true);

                    } else if (tachTime[0].equals("PHT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG, true);
                    } else if (tachTime[0].equals("PHBT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_BANG, true);
                    } else if (tachTime[0].equals("PHDST")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_7MAU, true);
                    } else if (tachTime[0].equals("PHMT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_MOC, true);
                    } else if (tachTime[0].equals("PHKT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_KIM, true);
                    } else if (tachTime[0].equals("PHTT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_THO, true);

                    } else if (tachTime[0].equals("MNT")) {
                        Item newitem = Map.doCreateMatNa3(this, Integer.parseInt(tachTime[1]), 837, 10, 5);
                    } else if (tachTime[0].equals("MNC")) {
                        Item newitem2 = Map.doCreateMatNa3(this, Integer.parseInt(tachTime[1]), 838, 11, 5);

                    }
                }
            }
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng bạn đã nhận được phần quà từ " + code + ".", ""));
            Database.instance.saveOrtherLog("", this.charname, "MOC: " + code, "mocNap");
            return;
        }
    }

    public static void doDropModelClothe(Char player, int IDItem, int nday) {
        ItemTemplates template = null;
        Item newItem = null;
        template = Map.itemTemplates.get(0).get(IDItem);
        newItem = new Item(template, false, -1, 0, IDItem);
        if (newItem != null) {
            newItem.id = player.getIDItem();
            newItem.owner = player.charDBID;
            newItem.level = (newItem.getTemplate()).level;
            player.iItems.add(newItem);
            newItem.clazz = 0;
            newItem.createAttGiap();
            newItem.identify = player.charDBID;
            if (nday > 0) {
                newItem.minuteExist = nday * 24 * 60;
                newItem.timeLoan = System.currentTimeMillis();
            }
                Logger2.DebugLogic("NAME ITEM        :::: " + newItem.getTemplate().name);
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
            player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
            // Database.instance.saveLogBuyItemShop(player.charname, (newItem.getTemplate()).name, 0, item.getPrice(isSale), String.valueOf(player.getxu()) + "_" + player.getLuong());
            Database.instance.saveItem(newItem);
        }
    }

    // TODO Nhận quà từ ngude_giftcode
    public static boolean NhanGiftCode = true;

    public void doAddGiftEventOffline() {
        if (!NhanGiftCode) {
            return;
        }
        String result = Database.instance.getGiftCode_DB(this);
        // System.out.println(result);
        String[] tach_string = result.trim().split("\\|");
        int id = Integer.parseInt(tach_string[0]);
        if (id == 1) {
            if (this.getLevel() < Integer.parseInt(tach_string[5])) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải đặt Level " + tach_string[5] + " thì mới nhận được GiftCode", ""));
                return;
            }
            String code = tach_string[6];
            //System.out.println(this.charname + " nhap CODE: " + code + " KQ= " + result + " ID= " + id);
            int xu = Integer.parseInt(tach_string[1]);
            int luong = Integer.parseInt(tach_string[2]);
            int luongkhoa = Integer.parseInt(tach_string[3]);
            String listItem = tach_string[4];
            //System.out.println("XU: " + xu + " - luong: " + luong + " - luongkhoa: " + luongkhoa + " - listItem: " + listItem + " - ");
            if (xu != 0) {
                this.addXu(xu, "nhan tu giftcode" + code);
            }
            if (luong != 0) {
                this.addLuong(luong);
            }
            if (luongkhoa != 0) {
                this.addLuongLock(luongkhoa);
            }
            if (!listItem.equals("NULL")) {
                String[] tachItem = listItem.trim().split(",");
                for (int i = 0; i < tachItem.length; i++) {
                    String[] tachTime = tachItem[i].trim().split(":");
                    if (tachTime[0].equals("DB")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            GiftCode.AddItem(this, Integer.parseInt(tachTime[1]), 0, 0, Integer.parseInt(tachTime[3]));
                        }
                    } else if (tachTime[0].equals("EGG")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createTrungPhuongHoang(1L, Integer.parseInt(tachTime[1]));
                        }

                    } else if (tachTime[0].equals("DH")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.addDanhHieu(Short.parseShort(tachTime[1]), Long.parseLong(tachTime[3]));
                        }
                        try {
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                            this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                        } catch (IOException ex) {
                        }
                    } else if (tachTime[0].equals("PET")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createPet(Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]));
                        }
                    } else if (tachTime[0].equals("TK")) {
                        this.createThanKhi(this, Integer.parseInt(tachTime[1]), 0, Integer.parseInt(tachTime[3]), null, false, true, 1, false);    
                    } else if (tachTime[0].equals("PETV")) {
                      
                            this.createPetVinhVien(Integer.parseInt(tachTime[1]));
                      

                    } else if (tachTime[0].equals("PPR")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createtemCoat(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), false, Integer.parseInt(tachTime[3]));
                        }
                    } else if (tachTime[0].equals("PPRK")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createtemCoat_chonkhang(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), false, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[5]));
                        }
                    } else if (tachTime[0].equals("PPV")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createtemCoat(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), true, Integer.parseInt(tachTime[3]));
                        }
                    } else if (tachTime[0].equals("PPVK")) {
                        for (int j = 0; j < Integer.parseInt(tachTime[2]); j++) {
                            this.createtemCoat_chonkhang(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), true, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[5]));
                        }

                    } else if (tachTime[0].equals("PPLUCK")) {
                        this.createtemCoat(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), true, 0);
                    } else if (tachTime[0].equals("PT")) {
                        int idGem = Integer.parseInt(tachTime[1]);
                        int soluong = Integer.parseInt((tachTime[2]));
                        this.potions[idGem] = this.potions[idGem] + soluong;
                        this.calculateAttrib();
                        try {
                            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                        } catch (IOException ex) {
                        }
                    } else if (tachTime[0].equals("GEM")) {
                        boolean lockitemgem = true;
                        try {
                            lockitemgem = (Integer.parseInt(tachTime[3]) < 0 ? true : false);
                        } catch (Exception e) {
                            lockitemgem = true;
                            e.printStackTrace();
                        }
                        this.doAddGemItem(Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[2]), lockitemgem);
                        this.sendMessage(MessageCreator.createCharGemItem(this));
                    } else if (tachTime[0].equals("DN")) {
                        this.diemNapVip += Integer.parseInt(tachTime[2]);
                    } else if (tachTime[0].equals("MN")) {
                        this.luongNap += Integer.parseInt(tachTime[2]);
                    } else if (tachTime[0].equals("PP")) {
                        Char.createPhiPhongMaxBaoKich(this, 0, Integer.parseInt(tachTime[3]), (Item) null, false, false, 0, true);
                        Char.createPhiPhongMaxBaoKich(this, 1, Integer.parseInt(tachTime[3]), (Item) null, false, false, 0, true);
                    } else if (tachTime[0].equals("PPLUCK")) {
                        this.createtemCoat(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[4]), true, 0);
                    } else if (tachTime[0].equals("PP8X")) {
                        this.createPhiPhongMaxBaoKich(this, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 5, true);
                        this.createPhiPhongMaxBaoKich(this, 1, Integer.parseInt(tachTime[3]), (Item) null, true, true, 5, true);
                    } else if (tachTime[0].equals("VKTT")) {
                        this.createVuKhiThoiTrang(this, 0, Integer.parseInt(tachTime[3]), (Item) null, false, true, 1, false);
                    } else if (tachTime[0].equals("VKMAX")) {
                        this.createVuKhiThoiTrang(this, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 1, false);
                    } else if (tachTime[0].equals("VKTB")) {
                        this.createVuKhiThoiTrangTHANBINH_NEW(this, 0, Integer.parseInt(tachTime[3]), (Item) null, false, true, 1, false);
                    } else if (tachTime[0].equals("VKTBMAX")) {
                        this.createVuKhiThoiTrangTHANBINH_NEW(this, 0, Integer.parseInt(tachTime[3]), (Item) null, true, true, 1, false);
                        
                    } else if (tachTime[0].equals("BT")) {
                        this.doCreatePetBachThu(Integer.parseInt(tachTime[3]), false);
                    } else if (tachTime[0].equals("AOTT")) {
                        Map.doBuyModelClothe(this, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]), 1);
                    } else if (tachTime[0].equals("AOSHOP")) {
                        doDropModelClothe(this, Integer.parseInt(tachTime[1]), Integer.parseInt(tachTime[3]));
                    } else if (tachTime[0].equals("CHOI")) {
                        this.createChoi(Integer.parseInt(tachTime[1]), this, Integer.parseInt(tachTime[3]), Integer.parseInt(tachTime[4]), true);
                    } else if (tachTime[0].equals("MATNA")) {
                        int[] valuemn = {5, 5, 10, 30, 50};
                        int randomMN = Map.r.nextInt(valuemn.length);
                        if (Integer.parseInt(tachTime[1]) > -1) {
                            randomMN = Integer.parseInt(tachTime[1]);
                        }
                        Item newitem = Map.doCreateMatNa(this, Integer.parseInt(tachTime[3]), ID_MAT_NA[randomMN], randomMN, valuemn[randomMN]);
                        this.sendMessage(MessageCreator.createServerAlertMessage("Nhận " + newitem.getName() + " 7 ngày", ""));
                    } else if (tachTime[0].equals("MATNA2")) {
                        // Tạo mặt nạ với option và value random
                        int[] optionValues = {5, 5, 10, 30, 50}; // Các giá trị value có thể
                        int randomIndex = Map.r.nextInt(optionValues.length);
                        int matnaID = Integer.parseInt(tachTime[1]); // ID mặt nạ
                        int option = randomIndex; // Option mặt nạ (0-4)
                        int value = optionValues[randomIndex];  // Giá trị option tương ứng
                        Item newitem = Map.doCreateMatNa2(this, Integer.parseInt(tachTime[3]), matnaID, option, value);
                        this.sendMessage(MessageCreator.createServerAlertMessage("Nhận " + newitem.getName() + (Integer.parseInt(tachTime[3]) > 0 ? " " + Integer.parseInt(tachTime[3])/1440 + " ngày" : " vĩnh viễn"), ""));    
                    } else if (tachTime[0].equals("DK")) {
                        this.createHeo(0, false);
                    } else if (tachTime[0].equals("TL")) {
                        this.createTuanLoc(Integer.parseInt(tachTime[1]));
                    } else if (tachTime[0].equals("DBT")) {
                        this.createPoro(Integer.parseInt(tachTime[1]), false);

                        ///  todo REGEX quà tích lũy 
                    } else if (tachTime[0].equals("LANT")) {
                        this.createLan(Integer.parseInt(tachTime[1]), false, 95);

                    } else if (tachTime[0].equals("DKT")) {
                        this.createHeo(Integer.parseInt(tachTime[1]), true);

                    } else if (tachTime[0].equals("PHT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG, true);
                    } else if (tachTime[0].equals("PHBT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_BANG, true);
                    } else if (tachTime[0].equals("PHDST")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_7MAU, true);
                    } else if (tachTime[0].equals("PHMT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_MOC, true);
                    } else if (tachTime[0].equals("PHKT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_KIM, true);
                    } else if (tachTime[0].equals("PHTT")) {
                        this.createPhuongHoang(Integer.parseInt(tachTime[1]), Animal.PHUONG_HOANG_THO, true);
                    } else if (tachTime[0].equals("MNT")) {
                        Item newitem = Map.doCreateMatNa3   (this, Integer.parseInt(tachTime[1]), 837, 10, 5);
                    } else if (tachTime[0].equals("MNC")) {
                        Item newitem2 = Map.doCreateMatNa3(this, Integer.parseInt(tachTime[1]), 838, 11, 5);

                    }
                }
            }
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng bạn đã nhận được phần quà từ hệ thống.", ""));
            Database.instance.saveOrtherLog("", this.charname, "CODE: " + code, "putGiftCode");
            return;
        }
    }

    private void updateNearChar() {
        final Message mActor = new Message(4);
        boolean havedata = false;
        for (int j = this.nearChars.size() - 1; j >= 0; --j) {
            try {
                final Char c = this.map.getPlayerByID(this.nearChars.elementAt(j));
                if (c != null) {
                    final boolean givcard = givingCard(c.inCountry, c.id);
                    if (c.exit) {
                        this.nearChars.remove(j);
                        this.charOutRange(c);
                    } else if ((c.inCountry != this.inCountry && c.isBot == -1 && !this.isPublicMap()) || (c.mapID != this.mapID && c.isBot == -1) || (c.mapID == this.mapID && c.region != this.region)) {
                        this.nearChars.remove(j);
                        this.charOutRange(c);
                    } else {
                        final boolean near = this.near(c, this.rangeAddMonster[0]);
                        if ((near && c.isBot != -1) || ((this.checkInParty(c) || givcard) && !c.isAdmin)) {
                            boolean send = true;
                            if (c.isBot == -35 && !canSendRaoVat()) {
                                send = false;
                            }
                            if (send) {
                                this.writeActorPos(mActor, c);
                                havedata = true;
                            }
                        }
                    }
                } else {
                    this.nearChars.remove(j);
                    this.map.removePlayer(this.inCountry, c);
                }
            } catch (final Exception e) {
                break;
            }
        }
        if (this.map.mapIDLoadMap == 0 || this.map.mapIDLoadMap == 70 || this.map.mapIDLoadMap == 80) {
            havedata = true;
            for (int i = 0; i < Map.npcVilage.size(); ++i) {
                if (!TeamServer.isServerIndo()) {
                    final Char aa = Map.npcVilage.get(i);
                    int xx = 0;
                    int yy = 0;
                    if (aa.posNPCInVilage != null) {
                        xx = aa.posNPCInVilage[this.inCountry][0];
                        yy = aa.posNPCInVilage[this.inCountry][1];
                    }
                    if (this.near(this.x, this.y, xx, yy, 120)) {
                        this.writeActorPos(mActor, Map.npcVilage.get(i));
                    }
                }
            }
        }
        for (int i = 0; i < this.map.npcPrivateMap.size(); ++i) {
            havedata = true;
            final Char aa = this.map.npcPrivateMap.get(i);
            final int xx = aa.x;
            final int yy = aa.y;
            if (this.near(this.x, this.y, xx, yy, 120)) {
                this.writeActorPos(mActor, aa);
            }
        }
        if (havedata) {
            this.sendMessage(mActor);
        }
        havedata = false;
        if (!this.map.isDun()) {
            final Message mActor2 = new Message(4);
            final Vector<Char> players = this.getAllPlayerMap();
            for (int k = players.size() - 1; k >= 0; --k) {
                try {
                    final Char p1 = players.get(k);
                    if (p1 != null) {
                        final boolean givcard2 = givingCard(p1.inCountry, p1.id);
                        if (this != p1) {
                            if ((p1.mapID != this.mapID && p1.isBot == -1) || (p1.mapID == this.mapID && p1.region != this.region) || (p1.isBot == -1 && p1.inCountry != this.inCountry && !this.isPublicMap())) {
                                this.charOutRange(p1);
                                try {
                                    this.map.removePlayer(this.inCountry, p1);
                                } catch (final Exception ex) {
                                }
                            } else if (!p1.exit) {
                                if (!this.nearChars.contains(p1.id)) {
                                    if (this.nearChars.size() - this.nNpc < this.nNearchar || p1.pk != 0 || p1.isKiller || this.checkInParty(p1) || p1.isBot != -1 || this.mapID == Map.idMapDautruong || this.map.isMapChienTruongMoba() || (this.mapID == Map.idMapTown && (Map.nwar[this.myCountry] || Map.getTown[this.myCountry]))) {
                                        final boolean near2 = this.near(p1, this.rangeAddMonster[0]);
                                        if ((near2 && p1.inCountry == this.inCountry) || (this.isPublicMap() && near2) || p1.isBot != -1) {
                                            final Message mb = this.sendBuffToUser(p1);
                                            this.sendMessage(mb);
                                            mb.cleanup();
                                            final Message mkef = this.sendEffKham(p1);
                                            if (mkef != null) {
                                                this.sendMessage(mkef);
                                            }
                                            p1.sendEffToChar(this);
                                            boolean send2 = true;
                                            if (p1.isBot == -35 && !canSendRaoVat()) {
                                                send2 = false;
                                            }
                                            if (!p1.isAdmin && send2) {
                                                this.writeActorPos(mActor2, p1);
                                                havedata = true;
                                            }
                                            if (p1.isBot != -1) {
                                                ++this.nNpc;
                                            }
                                            this.nearChars.add(p1.id);
                                        }
                                    }
                                } else if (!this.near(p1, this.rangeRemoveMonster[0]) && !this.checkInParty(p1) && p1.isBot == -1 && !givcard2) {
                                    this.nearChars.remove(p1);
                                    this.charOutRange(p1);
                                }
                            }
                        }
                    }
                } catch (final Exception e2) {
                    System.out.println("LOI TIM NEARCHAR TRONG CHAR ");
                    break;
                }
            }
            if (havedata) {
                this.sendMessage(mActor2);
            }
        }
    }

    public void updateFarm() {
        boolean growUp = false;
        for (int i = 0; i < this.seedsItem.size(); ++i) {
            final SeedItem s = this.seedsItem.get(i);
            if (s.idTemplate > -1) {
                final int level = s.lvTree;
                s.setLevelByTime();
                if (level < s.lvTree) {
                    growUp = true;
                }
                if (s.isDie()) {
                    s.idTemplate = -1;
                    s.lvTree = 0;
                    s.timeLive = 0L;
                    growUp = true;
                }
            }
        }
        if (growUp && this.mapID == Map.mapIDFarm) {
            this.map.doSendFarm(this);
        }
    }

    public void sendInfoMove2Player(final Char p) {
        if (!this.isAdmin) {
            final Message m = new Message(4);
            this.writeActorPos(m, this);
            p.sendMessage(m);
        }
    }

    public void sendInfoMove2Near() {
        if (!this.isAdmin) {
            final Message m = new Message(4);
            this.writeActorPos(m, this);
            for (int size = this.nearChars.size(), i = 0; i < size; ++i) {
                try {
                    final Char p = this.map.getPlayerByID(this.nearChars.elementAt(i));
                    if (p != null && p.id != this.id && p.isBot == -1 && this.near(p, this.rangeAddMonster[0]) && p.map == this.map && p.region == this.region) {
                        p.sendMessage(m);
                    }
                } catch (final Exception ex) {
                }
            }
        }
    }

    public void setSizeScreen(short w, final short h) {
        if (w < 0) {
            w = 320;
        }
        if (w < 320) {
            w = 220;
        }
        this.rangeAddMonster[0] = (short) (w / 2 + 50);
        this.rangeRemoveMonster[0] = (short) (w / 2 + 100);
    }

    public boolean inDataRange(final Actor p1, final Actor p2) {
        if (this.wScreen <= 240) {
            return Math.abs(p1.x - p2.x) < this.wScreen + (this.wScreen >> 1) && Math.abs(p1.y - p2.y) < this.hScreen + (this.hScreen >> 1);
        }
        return Math.abs(p1.x - p2.x) < this.wScreen && Math.abs(p1.y - p2.y) < this.hScreen;
    }

    public static boolean givingCard(final int country, final int id) {
        if (!Map.getTown[country]) {
            return false;
        }
        final Vector<NpcReceiveCard> npcReceiveCard = Map.npcReceiveCard.get(country);
        for (int pos = 0; pos < npcReceiveCard.size(); ++pos) {
            final NpcReceiveCard npc = npcReceiveCard.get(pos);
            if (npc.charGive != null && npc.charGive.id == id) {
                return true;
            }
        }
        return false;
    }

    public boolean isBatGioi() {
        return this.isCaiTrang == 1 ;
    }

    public boolean isSaTang() {
        return this.isCaiTrang == 2 ;
    }

    public int getLevelCaiTrang() {
        int level = 0;
        int targetId = 0;
        if (this.isCaiTrang == 1) {
            targetId = 903;
        } else if (this.isCaiTrang == 2) {
            targetId = 904;
        }
        // Lọc items từ rương người chơi
        final int size = this.iItems.size();
        for (int i = 0; i < size; i++) {
            Item item = this.iItems.elementAt(i);
            if (item == null) continue;
            
            // Kiểm tra tempateID thay vì id
            if (item.tempateID == targetId) {
                level += item.plus;
            }
        }
        return level;
    }

    public boolean checkItemTheMuaBan() {
        // Lọc items từ rương người chơi
        final int size = this.iItems.size();
        for (int i = 0; i < size; i++) {
            Item item = this.iItems.elementAt(i);
            if (item == null) continue;
            
            // Kiểm tra id 675 (THE_MUA_BAN)
            if (item.tempateID == Item.THE_MUA_BAN) {
                // Kiểm tra thời gian hết hạn
                if (item.isHetHan()) {
                    return false; // Item đã hết hạn
                }
                return true; // Item còn hạn
            }
        }
        return false; // Không tìm thấy item
    }

    public int getIdHeadDynamic() {
       
           if(this.isBatGioi() && this.isBatCaiTrang())
           {
            if(this.getLevelCaiTrang() >= 13)
            {
                return 8700 + 249;
            } else {
                return 8700 + 238;
            }
           }
           if(this.isSaTang() && this.isBatCaiTrang()){
            if(this.getLevelCaiTrang() >= 13){
                return 8700 + 252;
            } else {
                return 8700 + 241;
            }
           }
        // todo thêm đầu mặt nạ
            if (this.wModel.isCaiTaoSonTinh()) {
                return 8700 + 281;
            }
            if (this.wModel.isCaiTaoMiNuong()) {
                return 8700 + 278;
            }
            if (this.wModel.isGiapRong()) {
                return 8700 + 229;
            }
            if (this.wModel.isMeoXanh()) {
                return 8700 + 216;
            }
            if (this.wModel.isMeoQuyToc()) {
                return 8700 + 213;
            }

        if (!this.isTatMatNa()) {

            if (this.wModel.isMatNaTho()) {
                return 8700 + 223;
            }
            if (this.wModel.isMatNaCao()) {
                return 8700 + 222;
            }

            if (this.wModel.isMatNaBiNgo()) {
                return Char.ID_MAT_NA_BI_NGO;
            }
            if (this.wModel.isMatNaQuy()) {
                return Char.ID_MAT_NA_QUY;
            }
            if (this.wModel.isMatNaSoi()) {
                return Char.ID_MAT_NA_SOI;
            }
            if (this.wModel.isMatNaZoombi()) {
                return Char.ID_MAT_NA_ZOOMBI;
            }
            if (this.wModel.isMatNaPanda()) {
                return Char.ID_MAT_NA_PANDA;
            }
        }
        

        if (this.wModel.isTieuLongNu()) {
            return Char.ID_HEAD_TIEU_LONG_NU;
        }
        if (this.wModel.isDuongQua()) {
            return Char.ID_HEAD_DUONG_QUA;
        }
        if (this.wModel.isTienNu()) {
            return EffectBuff.EFF_TOC_TIEN_NU;
        }
        if (this.wModel.isTinhQuan()) {
            return EffectBuff.EFF_TOC_TINH_QUAN;
        }
        if (this.wModel.isAoDaiNam()) {
            return EffectBuff.EFF_NON_AO_DAI_NAM;
        }
        if (this.wModel.isAoDaiNu()) {
            return EffectBuff.EFF_NON_AO_DAI_NU;
        }
        
         // TODO THÊM ÁO THỜI TRANG (3) HEAD
        if (this.wModel.isNgheThuong()) {
            return 8700 + 175;
        }
        if (this.wModel.isNgocNu()) {
            return 8700 + 178;
        }       
        if (this.wModel.isXichCuoc()) {
            return 8700 + 181;
        }       
        if (this.wModel.isKimDong()) {
            return 8700 + 185;
        }       
        if (this.wModel.isLoiCong()) {
            return 8700 + 172;
        }
        if (this.wModel.isLacLongQuan()) {
            return 8700 + 119;
        }
        if (this.wModel.isAuCo()) {
            return 8700 + 116;
        }  
        if (this.wModel.isAonoelNam2024()) {
            return 8700 + 262;
        } 
        if (this.wModel.isAonoelNu2024()) {
            return 8700 + 259;
        } 
        
        return -1;
    }

    public int getIdBodyDynamic() {
        if(this.isBatGioi() && this.isBatCaiTrang()){
            if(this.getLevelCaiTrang() >= 13){
            return 8700 + 250;
           } else {
            return 8700 + 239;
           }
        }
        if(this.isSaTang() && this.isBatCaiTrang()){
            if(this.getLevelCaiTrang() >= 13){
            return 8700 + 253;
           } else {
            return 8700 + 242;
           }
        }
        if (this.wModel.isCaiTaoSonTinh()) {
            return 8700 + 282;
        }
        if (this.wModel.isCaiTaoMiNuong()) {
            return 8700 + 279;
        }
        if (this.wModel.isGiapRong()) {
            return 8700 + 230;
        }
        if (this.wModel.isMeoXanh()) {
            return 8700 + 217;
        }
        if (this.wModel.isMeoQuyToc()) {
            return 8700 + 214;
        }

        if (this.wModel.isTienNu()) {
            return EffectBuff.EFF_AO_TIEN_NU;
        }
        if (this.wModel.isTinhQuan()) {
            return EffectBuff.EFF_AO_TINH_QUAN;
        }
        if (this.wModel.isAoDaiNam()) {
            return EffectBuff.EFF_AO_DAI_NAM;
        }
        if (this.wModel.isAoDaiNu()) {
            return EffectBuff.EFF_AO_DAI_NU;
        }
        
         // TODO THÊM ÁO THỜI TRANG (4) MAIN
        if (this.wModel.isNgheThuong()) {
            return 8700 + 176;
        }
        if (this.wModel.isNgocNu()) {
            return 8700 + 179;
        }       
        if (this.wModel.isXichCuoc()) {
            return 8700 + 182;
        }       
        if (this.wModel.isKimDong()) {
            return 8700 + 184;
        }       
        if (this.wModel.isLoiCong()) {
            return 8700 + 173;
        }
        if (this.wModel.isLacLongQuan()) {
            return 8700 + 120;
        }
        if (this.wModel.isAuCo()) {
            return 8700 + 117;
        }
         if (this.wModel.isAonoelNam2024()) {
            return 8700 + 263;
        }  
         if (this.wModel.isAonoelNu2024()) {
            return 8700 + 260;
        } 
        
        return -1;
    }

    public int getIdLegDynamic() {
        if(this.isBatGioi() && this.isBatCaiTrang()){
            if(this.getLevelCaiTrang() >= 13){
            return 8700 + 248;
           } else {
            return 8700 + 240;
           }
           }
        if(this.isSaTang() && this.isBatCaiTrang()){
            if(this.getLevelCaiTrang() >= 13){
            return 8700 + 251;
           } else {
            return 8700 + 243;
           }
           }
        if (this.wModel.isCaiTaoSonTinh()) {
            return 8700 + 280;
        }
        if (this.wModel.isCaiTaoMiNuong()) {
            return 8700 + 277;
        }
        if (this.wModel.isGiapRong()) {
            return 8700 + 231;
        }
        if (this.wModel.isMeoXanh()) {
            return 8700 + 218;
        }
        if (this.wModel.isMeoQuyToc()) {
            return 8700 + 215;
        }

        if (this.wModel.isTienNu()) {
            return EffectBuff.EFF_QUAN_TIEN_NU;
        }
        if (this.wModel.isTinhQuan()) {
            return EffectBuff.EFF_QUAN_TINH_QUAN;
        }
        if (this.wModel.isAoDaiNam()) {
            return EffectBuff.EFF_QUAN_AO_DAI_NAM;
        }
        if (this.wModel.isAoDaiNu()) {
            return EffectBuff.EFF_QUAN_AO_DAI_NU;
        }
        
         // TODO THÊM ÁO THỜI TRANG (5) LEG
        if (this.wModel.isNgheThuong()) {
            return 8700 + 174;
        }
        if (this.wModel.isNgocNu()) {
            return 8700 + 177;
        }       
        if (this.wModel.isXichCuoc()) {
            return 8700 + 180;
        }       
        if (this.wModel.isKimDong()) {
            return 8700 + 183;
        }       
        if (this.wModel.isLoiCong()) {
            return 8700 + 171;
        }   
        if (this.wModel.isLacLongQuan()) {
            return 8700 + 121;
        }
        if (this.wModel.isAuCo()) {
            return 8700 + 118;
        }
        if (this.wModel.isAonoelNam2024()) {
            return 8700 + 261;
        } 
        if (this.wModel.isAonoelNu2024()) {
            return 8700 + 258;
        } 
        return -1;
    }

    public int getIdEffTool5l() {
        if (this.wModel != null) {
            return this.wModel.getIdEff();
        }
        return -1;
    }

    public boolean ischeckActivebuffgiamsatthuong() {
        if (System.currentTimeMillis() - this.timeSendEffTool5l >= 0L) {
            this.timeBuffGiamSatThuong = System.currentTimeMillis() + 10000L;
            return true;
        }
        return false;
    }

    
    @Override
    public void subUpdate() {
        if (this.isBot != -1) {
            return;
        }
        handleModelEffects();
        if (this.hp > 0 && this.getIdEffTool5l() > -1) {
            if (this.wModel.isBachMi() || this.wModel.isThuyVuHongSam()) {
                if (System.currentTimeMillis() - this.timeSendEffTool5l >= 0L && System.currentTimeMillis() - this.timeBuffGiamSatThuong <= 0L) {
                    final Message mef = MessageCreator.createMsgNewEffectSkill5Long(this.getIdEffTool5l(), 10000, this.id, this.cat, true, false);
                    this.sendMessage(mef);
                    this.sendToNearPlayer(mef);
                    this.timeSendEffTool5l = System.currentTimeMillis() + 30000L;
                }
            } else if (System.currentTimeMillis() - this.timeSendEffTool5l >= 0L) {
               
                final Message mef = MessageCreator.createMsgNewEffectSkill5Long(this.getIdEffTool5l() , 5000, this.id, this.cat, true, false);
                this.sendMessage(mef);
                this.sendToNearPlayer(mef);
                this.timeSendEffTool5l = System.currentTimeMillis() + 10000L;
            }
        }
        if (this.forbitChat > 0 && (System.currentTimeMillis() - this.timeForbitChat) / 60000L >= 1L) {
            --this.forbitChat;
            if (this.forbitChat == 0) {
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Thời gian cấm chat của bạn đã kết thúc."));
            }
            this.timeForbitChat = System.currentTimeMillis();
        }
        ++this.countSendchat;
        if (this.countSendchat >= 4) {
            if (this.allMsgChatRieng.size() > 0) {
                try {
                    final InfoChatRieng info = this.allMsgChatRieng.remove(0);
                    final Message m = new Message(-5);
                    m.dos.writeByte(1);
                    m.dos.writeUTF(info.charChat);
                    m.dos.writeUTF(info.infoChat);
                    this.sendMessage(m);
                } catch (final Exception ex) {
                }
            }
            this.countSendchat = 0;
        }
        this.charname.equals("chienthan");
        this.syncVipByLevel(false);
        if (!this.dayLogin.equals(getDayOpen(0L))) {
            this.countnvVulan = 0;
            this.maxKill = 0;
            this.usingSummons = 0;
            this.dayLogin = getDayOpen(0L);
            this.countQuestClan = 0;
            this.pointActiveQuest += 100;
            this.receiveQuestVulan = 0;
            this.receiveGiftEvent = 0;
            this.nBinhTra = 100;
            this.nHopMut = 100;
            this.countTime = 0;
            this.countMonsterKill = 0;
            this.flower = 0;
            this.timeXP = 0;
            this.giveMoneyClan = 1;
            if (this.pointActiveQuest > 300) {
                this.pointActiveQuest = 300;
            }
            if (this.subQuest.id_quest > -1) {
                this.subQuest.reset();
                this.subQuest.id_quest = -1;
                MessageCreator.createMsgCharQuest(this, 0);
            }

            this.luot_van_tieu = 3;
            this.buy_luot_van_tieu = (byte) (3 + this.vip);
            //this.buy_luot_van_tieu = 0;
            this.cuop_tieu = 3;
            if (this.lvDetail.lv >= 34) {
                if (this.ntangtoc < 6) {
                    final int[] potions = this.potions;
                    final int n = 7;
                    potions[n] += 6 - this.ntangtoc;
                    Database.instance.saveOrtherLog("", this.charname, "tang " + (6 - this.ntangtoc) + " binh tang  toc", "btt");
                    this.ntangtoc = 6;
                } else if (this.ntangtoc == 6 && this.potions[7] < 6) {
                    final int[] potions2 = this.potions;
                    final int n2 = 7;
                    potions2[n2] += 6;
                    Database.instance.saveOrtherLog("", this.charname, "tang 6 binh tang  toc", "btt");
                }
            }
            if (this.lvDetail.lv >= 40) {
                this.lockLuong += 1000;
            }
            if (this.vip > 0 && this.vip <= 3) {
                final int[] potions3 = this.potions;
                final int n3 = 100;
                potions3[n3] += Char.soluong_loa_vip[this.vip];
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + this.vip + " loa."));
                Database.instance.saveOrtherLog("", this.charname, "nhan loa vip", "loavip");
            }
            if (isDemTrungThu() && this.lvDetail.lv >= 20) {
                this.potions[125] = 1;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được hộp quà trung thu."));
                Database.instance.saveOrtherLog("", this.charname, "nhan qua tang trung thu", "hopquatrungthu");
            }
            if ((isDemHaloween() && this.lvDetail.lv >= 40 && this.receiveGiftOldChar == 1) || this.dayLogin.equals("2017-10-30")) {
                this.potions[141] = 1;
                Database.instance.saveOrtherLog("", this.charname, "nhan qua tang haloween", "hopquahlw");
            }
//            if (this.lvDetail.lv >= 40 && isDemHaloween2023()) {
//                int[] valuemn = {5, 5, 10, 30, 50};
//                int randomMN = Map.r.nextInt(valuemn.length);
//                Item newitem = Map.doCreateMatNa3(this, 10080, ID_MAT_NA[randomMN], randomMN, valuemn[randomMN]);
//                this.sendMessage(MessageCreator.createServerAlertMessage("Nhận " + newitem.getName() + " 7 ngày", ""));
//                Database.instance.saveOrtherLog("", this.charname, "nhan qua tang haloween", "hopquahlw");
//            }
            if (isDemNoel()) {
                final int lv = this.lvDetail.lv;
            }
            if (!Map.openLog && this.dayLogin.compareTo("2014-08-06") < 0) {
                this.doAddGiftSn();
            }
            if (UtilKPAH.getDayOfMonth() == 1) {
                Database.instance.check();
                Database.instance.getDiemNapXaiLuong(this);
            }
        }
        if (this.potions[78] > 0 && this.idClan == -1) {
            this.potions[78] = 0;
            this.potions[28] = 0;
            if (this.rankGov == 0) {
                this.potions[85] = 0;
                this.calculateAttrib();
                try {
                    this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                } catch (final IOException ex3) {
                }
            }
            this.sendMessage(MessageCreator.createCharWearingMessage(this, this));
            this.sendToNearPlayer(MessageCreator.createCharInfo(this));
            MessageCreator.createMsgCharMonster(this, this);
        }
        boolean sendupdate = false;
        if (this.fruit != null) {
            for (int i = 0; i < this.fruit.length; ++i) {
                if (this.fruit[i] != null) {
                    final boolean a = this.fruit[i].update(this);
                    if (!sendupdate) {
                        sendupdate = a;
                    }
                }
            }
        }
        if (sendupdate) {
            MessageCreator.createMsgCharFruit(this, 0, 0);
        }
        final Message m = new Message(4);
        boolean haveData = false;
        if (!this.map.isDun() && this.isBot == -1) {
            for (int j = 0; j < this.nearMons.size(); ++j) {
                final Monster mt = this.map.getMonster(this.nearMons.get(j), (this.mapID == 225 || this.mapID == 226) ? this.idWedding : this.inCountry, this.region);
                if (mt != null) {
                    if (mt.isDead || !this.near(mt, this.rangeRemoveMonster[0])) {
                        if (Map.isNewVersion && !mt.isBoss && mt.getMonsterTemplate().id != Map.idGhost) {
                            this.nearMons.remove(j);
                        }
                    } else if (!mt.isMonsterVantieu()) {
                        if ((!Map.isNewVersion || mt.isBoss || mt.getMonsterTemplate().id == Map.idGhost || mt.isMaterialMons()) && (!mt.isBossVanTienTran() || mt.isBossTruRong())) {
                            this.writeActorPos(m, mt);
                            haveData = true;
                        }
                        if (mt.isBoss() && System.currentTimeMillis() - mt.timeAddBuff >= 0L && mt.addEffBuff(EffectBuff.BUFF_DAM_BOSS, System.currentTimeMillis() + 15000L, EffectBuff.BY_ACTOR, 0) != null) {
                            mt.sendEffToChar(this);
                            mt.timeAddBuff = System.currentTimeMillis() + 20000L;
                        }
                        if ((this.near(mt, this.map.isvanTienTran() ? 240 : 120) || mt.isBoss || mt.idTemplate == 83) && this.hp > 0 && mt.target == null && mt.isActive() && !this.isAdmin && mt.isEnemy(this)) {
                            if (mt.region == this.region) {
                                mt.target = this;
                            } else {
                                this.nearMons.remove(j);
                            }
                        }
                    }
                } else {
                    this.nearMons.remove(j);
                }
            }
        }
        if (haveData) {
            this.sendMessage(m);
            if (!Map.isNewVersion) {
                this.nearMons.removeAllElements();
            }
        }
        this.moved = false;
        this.checkOutDelay();
        if (this.isMonster && this.map != null && this.map.haveCharMonter[Map.openLog ? 0 : this.myCountry] && System.currentTimeMillis() - this.map.timeLiveMonster[Map.openLog ? 0 : this.myCountry] >= 300000L) {
            this.map.charMonsterDissapear(this);
        }
        if (this.isMonster && !Map.idMapMONSTER.get(Map.openLog ? 0 : this.myCountry).contains(this.map.mapId)) {
            this.map.charMonsterDissapear(this);
        }
    }
    
    
   
    private void handleModelEffects() 
    {
        if (this.hp <= 0 || this.getIdEffTool5l() <= -1 || System.currentTimeMillis() - this.timeSendEffTool5l < 0L) 
        {
            return;
        }
    
        int effectId = -1;
    
        // Xác định effectId dựa trên model
        if (this.wModel.isGiapRong()) 
        {
            effectId = 232;
        } else if (this.wModel.isMeoQuyToc()) 
        {
            effectId = 233;
        } else if (this.wModel.isNgheThuong()) 
        {
            effectId = 189;
        } else if (this.wModel.isNgocNu()) 
        {
            effectId = 190;
        } else if (this.wModel.isXichCuoc()) 
        {
            
            effectId = 187;
        } else if (this.wModel.isKimDong()) 
        {
          
            effectId = 188;
        } else if (this.wModel.isLoiCong()) 
        {
            effectId = 186;
        }
        // Thêm các model khác ở đây...
    
        
        // Nếu có effect thì apply
        if (effectId != -1) {
        int idEff = effectId + 8700;
        long duration = 5000L; // 5 giây

        if (this.addEffBuff(idEff, duration, EffectBuff.BY_ACTOR, 0) != null) {
        try {
            Message mef = MessageCreator.createMsgNewEffectSkill5Long(idEff, (int) duration, this.id, this.cat, true, false);
            this.sendMessage(mef);
            this.sendToNearPlayer(mef);
            this.timeSendEffTool5l = System.currentTimeMillis() + 10000L;
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
    }
  }      
    private void charOutRange(final Char p) {
        final Message msg = new Message(8);
        try {
            msg.dos.writeShort(p.id);
            if (p.map == this.map) {
                this.sendMessage(msg);
            }
            if (p.isBot != -1) {
                --this.nNpc;
            }
        } catch (final IOException ex) {
        }
    }

    public boolean checkInParty(final Char c) {
        return this.party.userParty.contains(c) && c.region == this.region;
    }

    public void checkOutDelay() {
        if (this.outdelay > 0) {
            --this.outdelay;
            if (this.outdelay == 0) {
                CharManager.instance.kickPlayer(this, "char 1");
                this.exit = true;
            }
        }
    }

    public void doResetItemChienTruong() {
        this.potions[128] = 0;
        this.potions[131] = 0;
        this.potions[132] = 0;
        this.potions[129] = 0;
        this.potions[130] = 0;
        this.potions[126] = 0;
        this.potions[127] = 0;
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
    }

    @Override
    public int getInCountry() {
        if (this.map == null || !this.map.isMapChienTruongMoba()) {
            return this.inCountry;
        }
        final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
        if (c != null) {
            return c.team;
        }
        return 0;
    }

    public int getMyCountry() {
        if (this.map == null || !this.map.isMapChienTruongMoba()) {
            return this.myCountry;
        }
        final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
        if (c != null) {
            return c.team;
        }
        return 0;
    }

    @Override
    public int getTypePk() {
        if (this.map != null && (this.map.isMapChienTruongMoba() || this.map.isMapLoiDai())) {
            return this.pk_chienTruong;
        }
        return this.pk;
    }

    public Message writeActorPos(final Message m, final Actor a) {
        try {
            m.dos.writeByte(a.cat);
            m.dos.writeByte(a.getType());
            m.dos.writeShort(a.id);
            short x = (short) a.x;
            short y = (short) a.y;
            if (a.cat == 0) {
                final Char aa = (Char) a;
                if (aa.posNPCInVilage != null) {
                    x = aa.posNPCInVilage[this.inCountry][0];
                    y = aa.posNPCInVilage[this.inCountry][1];
                }
            }
            m.dos.writeShort(x);
            m.dos.writeShort(y);
            m.dos.writeByte((a.cat == 0 && (this.isKiller || this.killer <= 0)) ? a.getTypePk() : -1);
            m.dos.writeInt(a.getCharID());
            if (a.cat == 1) {
                m.dos.writeByte(((Monster) a).getMonsterTemplate().isNewMonster);
            }
            if (!TeamServer.isServerIndo()) {
                m.dos.writeByte(a.getIdCharThanThu());
                if (a.isMyHoVe(this)) {
                    m.dos.writeBoolean(false);
                } else {
                    m.dos.writeBoolean(a.canFocus(this));
                }
            }
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
        return m;
    }

    public void sendPetEff() {
        if (this.petUsing != null && !this.petUsing.isPetTool() && this.petUsing.getIdEffPet() > -1 && this.addEffBuff(this.petUsing.getIdEffPet(), System.currentTimeMillis() + 320000000L, EffectBuff.BY_ACTOR, 0) != null) {
            this.sendEffToChar(this);
        }
    }

    public void sendMessage(final Message m) {
        if (this.isBot != -1) {
            return;
        }
        if (this.session != null) {
            this.session.sendMessage(m);
        }
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(final Session s) {
        this.session = s;
    }

    @Override
    public void sendEffToNearChar() {
        for (int j = this.nearChars.size() - 1; j >= 0; --j) {
            try {
                final Char p = this.map.getPlayerByID(this.nearChars.get(j));
                if (p == null || p.id == this.id || p.isBot != -1 || !this.near(p, this.rangeAddMonster[0]) || p.map != this.map || p.region == this.region) {
                }
                this.sendEffToChar(p);
            } catch (final Exception ex) {
            }
        }
    }

    public void sendToNearPlayer(final Message m) {
        for (int j = this.nearChars.size() - 1; j >= 0; --j) {
            try {
                final Char p = this.map.getPlayerByID(this.nearChars.get(j));
                final Session s = p.session;
                if (p == null || p.id == this.id || p.isBot != -1 || !this.near(p, this.rangeAddMonster[0]) || p.map != this.map || p.region == this.region) {
                }
                s.sendMessage(m);
            } catch (final Exception ex) {
            }
        }
    }

    public boolean partyIsDie(final Char p, final int mapID) {
        if (p.party.userParty.size() < 0) {
            return false;
        }
        for (int i = 0; i < p.party.userParty.size(); ++i) {
            final Char c = p.party.userParty.get(i);
            if (c.hp > 0 && c.mapID == mapID) {
                return false;
            }
        }
        return true;
    }

    public void createParty() {
        try {
            final int size = RealController.intance.idGen.getSize(5);
            if (this.partyID != -1 || size == 0) {
                final Message m = MessageCreator.createServerAlertMessage("Không thể tạo thêm party", "");
                this.sendMessage(m);
                m.cleanup();
                return;
            }
            short idP = RealController.intance.idGen.getID(5, "tao party");
            if (idP == -1) {
                idP = RealController.intance.idGen.getID(5, "tao party");
            }
            this.partyID = idP;
            this.masterIDParty = this.id;
            this.party.idMaster = this.id;
            final Message m = new Message(48);
            m.dos.writeShort(idP);
            this.sendMessage(m);
            m.cleanup();
        } catch (final Exception ex) {
        }
    }

    public void deleteParty() {
        final int size = this.party.userParty.size();
        try {
            if (size < 0 || this.partyID == -1 || (size > 0 && this.id != this.party.idMaster)) {
                final Message mm = MessageCreator.createServerAlertMessage(Text.TRUONG_NHOM_GIAI_TAN, "");
                this.sendMessage(mm);
                mm.cleanup();
                return;
            }
            if (size > 0) {
                final Message mm = new Message(50);
                mm.dos.writeByte(1);
                for (int i = 0; i < size; ++i) {
                    final Char ch = this.party.userParty.elementAt(i);
                    if (ch.id != this.id) {
                        ch.party = null;
                        ch.sendMessage(mm);
                        ch.party = new Party();
                        ch.partyID = -1;
                        ch.masterIDParty = -1;
                    }
                }
                RealController.intance.idGen.putID(this.partyID, 5, "xoa party");
                this.sendMessage(mm);
                this.party.userParty.removeAllElements();
                this.party = null;
                this.party = new Party();
                this.partyID = -1;
                this.masterIDParty = -1;
                mm.cleanup();
            }
        } catch (final Exception ex) {
        }
    }

    public void sendInviteParty2User(final Message m) {
        try {
            if (!this.party.userParty.isEmpty() && this.id != this.party.idMaster) {
                final Message mm = MessageCreator.createServerAlertMessage("Chỉ trưởng nhóm mới được mời.", "");
                this.sendMessage(mm);
                mm.cleanup();
                return;
            }
            final int nMember = this.party.userParty.size();
            if (nMember >= Party.MAX_MEMBER) {
                final Message mm = MessageCreator.createServerAlertMessage(Text.KO_THE_MOI_THEM_PARTY, "");
                this.sendMessage(mm);
                mm.cleanup();
                return;
            }
            final int idFriend = m.dis.readShort();
            final Char fr = this.map.getPlayerByID((short) idFriend);
            if (fr != null) {
                if (fr.map.isMapLoiDai()) {
                    return;
                }
                if (!fr.rcvInvite || !fr.rcvInviteVip) {
                    this.sendMessage(MessageCreator.createServerAlertMessage(Text.KO_NHAN_LOI_MOI, ""));
                    return;
                }
                if (fr.id == idFriend && fr.party.userParty.size() <= 0) {
                    final Message mm = new Message(49);
                    mm.dos.writeByte(0);
                    mm.dos.writeShort(this.partyID);
                    mm.dos.writeShort(this.id);
                    mm.dos.writeUTF(this.charname);
                    mm.dos.writeByte(this.lvDetail.lv);
                    mm.dos.writeShort(this.masterIDParty);
                    mm.dos.writeByte(this.charClass);
                    if (nMember > 0) {
                        for (int j = 0; j < nMember; ++j) {
                            final Char ch = this.party.userParty.elementAt(j);
                            if (ch.id != this.id) {
                                mm.dos.writeShort(ch.id);
                                mm.dos.writeUTF(ch.charname);
                                mm.dos.writeByte(ch.lvDetail.lv);
                                mm.dos.writeByte(ch.charClass);
                            }
                        }
                        fr.sendMessage(mm);
                    }
                    fr.sendMessage(mm);
                    mm.cleanup();
                    return;
                }
            }
            final Message mm = MessageCreator.createServerAlertMessage(Text.DUNG_QUA_XA, "");
            this.sendMessage(mm);
            mm.cleanup();
        } catch (final Exception ex) {
        }
    }

    public void okJoinParty(final Message m) {
        try {
            if (this.party.userParty.size() >= Party.MAX_MEMBER) {
                return;
            }
            final short idInvite = m.dis.readShort();
            final Char fr = CharManager.instance.getByCharID(idInvite);
            if (fr != null && this.partyID == -1) {
                final int size = fr.party.userParty.size();
                this.partyID = fr.partyID;
                Message mm = new Message(49);
                mm.dos.writeByte(1);
                mm.dos.writeShort(this.id);
                mm.dos.writeUTF(this.charname);
                mm.dos.writeByte(this.lvDetail.lv);
                mm.dos.writeByte(this.charClass);
                fr.sendMessage(mm);
                mm.cleanup();
                if (size < 1) {
                    fr.party.addUser(fr);
                    mm.cleanup();
                } else {
                    mm = new Message(49);
                    mm.dos.writeByte(3);
                    mm.dos.writeShort(this.id);
                    mm.dos.writeUTF(this.charname);
                    mm.dos.writeByte(this.lvDetail.lv);
                    mm.dos.writeByte(this.charClass);
                    for (int j = 0; j < size; ++j) {
                        final Char ch = fr.party.userParty.elementAt(j);
                        if (ch.id != fr.id) {
                            ch.sendMessage(mm);
                        }
                    }
                    mm.cleanup();
                }
                fr.party.addUser(this);
                this.party = fr.party;
                this.masterIDParty = fr.masterIDParty;
            }
        } catch (final Exception e) {
            System.out.println("LOI KHI NHAN LOI OK VAO NHOM CUA USER");
            e.printStackTrace();
        }
    }

    public void cancelJoinParty(final Message m) {
        try {
            final int idInvite = m.dis.readShort();
            final Char fr = this.map.getPlayerByID((short) idInvite);
            if (fr != null && fr.id == idInvite) {
                final Message mm = new Message(49);
                mm.dos.writeByte(2);
                mm.dos.writeUTF(this.charname);
                fr.sendMessage(mm);
                mm.cleanup();
            }
        } catch (final Exception ex) {
        }
    }

    public void outParty() {
        try {
            final Message msg = new Message(50);
            msg.dos.writeByte(2);
            msg.dos.writeShort(this.id);
            for (int i = 0; i < this.party.userParty.size(); ++i) {
                final Char ch = this.party.userParty.elementAt(i);
                if (ch.id != this.id) {
                    ch.party.remoUser(this.id);
                    if (ch.id == this.party.idMaster && ch.party.userParty.size() == 1) {
                        ch.removeParty();
                    }
                }
                ch.sendMessage(msg);
            }
            this.party = null;
            this.partyID = -1;
            this.masterIDParty = -1;
            this.party = new Party();
            this.sendMessage(msg);
            msg.cleanup();
        } catch (final Exception ex) {
        }
    }

    public void kickOutParty(final Message m) {
        try {
            if (this.id != this.party.idMaster) {
                final Message msg = MessageCreator.createServerAlertMessage(Text.DUOI_PARTY, "");
                this.sendMessage(msg);
                msg.cleanup();
                return;
            }
            final short idchar = m.dis.readShort();
            final Char delChar = this.party.remoUser(idchar);
            if (delChar != null) {
                final Message msg = new Message(50);
                msg.dos.writeByte(0);
                msg.dos.writeShort(idchar);
                for (int i = 0; i < this.party.userParty.size(); ++i) {
                    final Char ch = this.party.userParty.elementAt(i);
                    if (ch.id != this.id) {
                        ch.party.remoUser(idchar);
                    }
                    ch.sendMessage(msg);
                }
                delChar.party = null;
                delChar.partyID = -1;
                delChar.masterIDParty = -1;
                delChar.party = new Party();
                delChar.sendMessage(msg);
                this.sendMessage(msg);
                if (this.party.userParty.size() == 1) {
                    this.removeParty();
                }
                msg.cleanup();
            }
        } catch (final Exception ex) {
        }
    }

    private void removeParty() {
        RealController.intance.idGen.putID(this.partyID, 5, "kick va giai tan party");
        this.party.partyRemove();
        this.party = null;
        this.partyID = -1;
        this.masterIDParty = -1;
        this.party = new Party();
    }

    public void resetTimeLastuseSkill() {
        for (int i = 0; i < this.timeLastUseSkills.length; ++i) {
            this.timeLastUseSkills[i] = 0L;
        }
    }

    public void setBuff2Char(final Message message) {
        

        try {
            if (this.hp <= 0) {
                return;
            }
            final int charID = message.dis.readShort();
            final int skilltype = message.dis.readByte();
            final int buffEff = message.dis.readByte();
            if (buffEff == 27 && this.charClass != 2) {
                return;
            }
            if (!CharManager.isBuffActive(this.charClass, buffEff)) {
                this.sendMessage(MessageCreator.createServerAlertMessage(Text.CAN_NOT_USE, ""));
                return;
            }
            final int bid = CharManager.getSkillBuffByID(this.charClass, buffEff);
            if (bid != -1) {
                Char c = null;
                if (charID == this.id) {
                    c = this;
                } else {
                    c = this.getCharFromNearChar(charID);
                }
                if (c == null) {
                    final Message m = MessageCreator.createServerAlertMessage(Text.NGUOI_CHOI_DUNG_QUA_XA, "");
                    this.sendMessage(m);
                    m.cleanup();
                    return;
                }
                final int buff = bid;
                if (buff == -1) {
                    final Message i = MessageCreator.createServerAlertMessage("Nhân vật bạn chọn không có kỹ năng này.", "");
                    this.sendMessage(i);
                    i.cleanup();
                    return;
                }
                if (this.skill[buff + 4] + this.addMoreLevelSkill[buff + 4] == 0) {
                    return;
                }
                final int mplost = CharManager.SKILL_MP[this.charClass][buff][this.skill[buff + 4] + this.addMoreLevelSkill[buff + 4]];
                if (this.mp + this.percentBuff[1] < mplost) {
                    return;
                }
                this.mp -= mplost;
                int div = 1;
                if (buffEff != 27) {
                    div = 2;
                }
                if (System.currentTimeMillis() - this.timeLastUseSkills[buff + 4] < CharManager.SKILL_COOLDOWN[this.charClass][buff + 4][this.skill[buff + 4] + this.addMoreLevelSkill[buff + 4]] * 100 / div) {
                    if (buffEff == 27) {
                        final long time = CharManager.SKILL_COOLDOWN[this.charClass][buff + 4][this.skill[buff + 4] + this.addMoreLevelSkill[buff + 4]] / 10 - (System.currentTimeMillis() - this.timeLastUseSkills[buff + 4]) / 1000L;
                        this.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể sử dụng sau " + time + "s", ""));
                    }
                    return;
                }
                int[] tempBuff;
                int j;
                for (tempBuff = new int[]{19, 20, 22, 23, 24, 25, 27}, j = 0; j < tempBuff.length && tempBuff[j] != buffEff; ++j) {
                }
                if (buffEff == 27) {
                    if (c.inCountry != this.inCountry) {
                        return;
                    }
                    if (c.mapID != this.mapID) {
                        return;
                    }
                    if (c.region != this.region) {
                        return;
                    }
                    if (c.hp > 0) {
                        final Message k = MessageCreator.createServerAlertMessage("Không thể hồi sinh người còn HP.", "");
                        this.sendMessage(k);
                        k.cleanup();
                        return;
                    }
                    if (!c.canHSChiemThanh() && Map.getTown[this.inCountry]) {
                        long time2 = 1L - (System.currentTimeMillis() - c.timeWaitComeHome) / 1000L;
                        if (time2 < 1L) {
                            time2 = 1L;
                        }
                        try {
                            Thread.sleep(System.currentTimeMillis() - c.timeWaitComeHome);
                        } catch (final Exception ex) {
                        }
                    }
                }
                c.upPercentAtribute(c, buffEff, j, this.skill[buff + 4] + this.addMoreLevelSkill[buff + 4], this);
                final Message k = new Message(51);
                k.dos.writeByte(1);
                k.dos.writeShort(charID);
                k.dos.writeByte(buffEff);
                c.timeUseBuff[j] = System.currentTimeMillis();
                c.countDown[j] = 0;
                c.lvBuff[j] = (byte) (this.skill[buff + 4] + this.addMoreLevelSkill[buff + 4]);
                c.timeOut[j] = this.timeOut[j];
                k.dos.writeShort(this.timeOut[j]);
                c.arrayBuff[j] = (byte) buffEff;
                k.dos.writeByte(this.skill[buff + 4] + this.addMoreLevelSkill[buff + 4]);
                this.timeLastUseSkills[buff + 4] = System.currentTimeMillis();
                this.sendMessage(k);
                if (c.id != this.id) {
                    c.sendMessage(k);
                    c.sendToNearPlayer(k);
                } else {
                    this.sendToNearPlayer(k);
                }
                k.cleanup();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public Char getCharFromNearChar(final int charID) {
        for (int i = 0; i < this.nearChars.size(); ++i) {
            final Char c = this.map.getPlayerByID(this.nearChars.elementAt(i));
            if (c != null && c.id == charID) {
                return c;
            }
        }
        return null;
    }

    public void removeBuff(final Message message) {
        try {
            final int charID = message.dis.readShort();
            final int buffEff = message.dis.readByte();
            final Message m = new Message(51);
            m.dos.writeByte(0);
            m.dos.writeShort(charID);
            m.dos.writeByte(buffEff);
            for (int i = 0; i < this.nearChars.size(); ++i) {
                final Char ch = this.map.getPlayerByID(this.nearChars.elementAt(i));
                if (ch != null) {
                    ch.sendMessage(m);
                }
            }
            m.cleanup();
        } catch (final Exception ex) {
        }
    }

    private int getBuffType(final int buffID) {
        try {
            return CharManager.EFF_BUFF_SKILL[this.charClass][buffID];
        } catch (final Exception ex) {
            return -1;
        }
    }

    public void receiveClanQuest() {
        try {
            if (this.rcvQuestClan) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Không thể nhận thêm khi đang làm nhiệm vụ", ""));
                return;
            }
            final int index = Map.r.nextInt(10);
            final Message m = new Message(-70);
            m.dos.writeByte(0);
            m.dos.writeByte(QuestClan.info_receive[index].length);
            for (int i = 0; i < QuestClan.info_receive[index].length; ++i) {
                m.dos.writeUTF(QuestClan.info_receive[index][i]);
            }
            this.sendMessage(m);
        } catch (final Exception ex) {
        }
    }

    public void acceptClanQuest(final int type) {
        try {
            if (type != 1) {
            }
        } catch (final Exception ex) {
        }
    }

    public void responseClanQuest() {
    }

    public void finishClanQuest() {
    }

    public void receiveQuest(final Message m) {
    }

    public void sendPotionQuest(final Potion pt) {
        try {
            final Message m = new Message(19);
            RealController.intance.idGen.putID(pt.id, 4, "tra lai cho real trong char");
            final int[] potions = this.potions;
            final short type = pt.getType();
            potions[type] += pt.quantity;
            m.dos.writeShort(this.id);
            m.dos.writeShort(pt.id);
            m.dos.writeByte(pt.getType());
            m.dos.writeShort(pt.quantity);
            this.sendMessage(m);
            m.cleanup();
        } catch (final Exception ex) {
        }
    }

    public void createCharQuest() {
        if (this.char_quest == null) {
            this.char_quest = new CharQuest();
        }
        this.char_quest.id_char = this.charDBID;
        this.char_quest.id_quest = this.nextQuest;
        if (this.monsterKill != null) {
            for (int i = 0; i < this.monsterKill.length; ++i) {
                this.char_quest.nMonster[i] = (short) this.monsterKill[i][1];
            }
        }
        if (this.monsterIem != null) {
            for (int i = 0; i < this.monsterIem.length; ++i) {
                this.char_quest.nItem[i] = (short) this.monsterIem[i][1];
            }
        }
        this.char_quest.receive = (byte) (this.receiveQuest ? 1 : 0);
        this.char_quest.finish = (byte) (this.isFinish ? 1 : 0);
    }

    public void sendInfoQuest() {
        MessageCreator.createMsgCharQuest(this, 0);
        MessageCreator.createMsgCharQuest(this, 1);
        MessageCreator.createMsgCharQuest(this, 2);
    }

    private void sendInfoQuest(final int idQuest) {
        final Message msg = new Message(52);
        final int type = QuestTemplate.QUEST_TYPE[idQuest - 1];
        try {
            msg.dos.writeByte(0);
            msg.dos.writeByte(type);
            msg.dos.writeUTF(QuestTemplate.title_quest[idQuest - 1]);
            msg.dos.writeByte(QuestTemplate.content_quest[idQuest - 1].length);
            for (int i = 0; i < QuestTemplate.content_quest[idQuest - 1].length; ++i) {
                msg.dos.writeUTF(QuestTemplate.content_quest[idQuest - 1][i]);
            }
            msg.dos.writeUTF(QuestTemplate.decription_quest[idQuest - 1]);
            msg.dos.writeUTF(QuestTemplate.info_Gif[idQuest - 1]);
            switch (type) {
                case 0: {
                    final int[][] monster = QuestTemplate.getMonsterKill(idQuest - 1);
                    this.monsterKill = new int[monster.length][3];
                    msg.dos.writeByte(monster.length);
                    for (int j = 0; j < monster.length; ++j) {
                        for (int k = 0; k < monster[j].length; ++k) {
                            msg.dos.writeShort(monster[j][k]);
                            this.monsterKill[j][k] = monster[j][k];
                        }
                    }
                    break;
                }
                case 1: {
                    this.npc_quest = null;
                    this.npc_quest = new int[QuestTemplate.npc_quest[idQuest - 1].length];
                    msg.dos.writeByte(this.npc_quest.length);
                    for (int j = 0; j < this.npc_quest.length; ++j) {
                        msg.dos.writeByte(QuestTemplate.npc_quest[idQuest - 1][j]);
                        this.npc_quest[j] = QuestTemplate.npc_quest[idQuest - 1][j];
                    }
                    final String[] info = QuestTemplate.info_npc_quest[idQuest - 1];
                    msg.dos.writeByte(info.length);
                    for (int l = 0; l < info.length; ++l) {
                        msg.dos.writeUTF(info[l]);
                    }
                    msg.dos.writeUTF(QuestTemplate.nameItemQuest[this.nextQuest - 1]);
                    break;
                }
                case 2: {
                    final short[][] monster_item = QuestTemplate.monster_item[idQuest - 1];
                    this.monsterIem = new int[monster_item.length][3];
                    msg.dos.writeByte(this.monsterIem.length);
                    for (int m = 0; m < monster_item.length; ++m) {
                        for (int j2 = 0; j2 < monster_item[m].length; ++j2) {
                            msg.dos.writeShort(monster_item[m][j2]);
                            this.monsterIem[m][j2] = monster_item[m][j2];
                        }
                    }
                    msg.dos.writeUTF(QuestTemplate.nameItemQuest[this.nextQuest - 1]);
                    break;
                }
            }
            this.sendMessage(msg);
        } catch (final Exception e) {
            System.out.println("LOI KHI GUI THONG TIN QUEST CHO CLIENT " + e.toString());
        } finally {
            try {
                msg.cleanup();
            } catch (final Exception ex) {
            }
        }
        try {
            msg.cleanup();
        } catch (final Exception ex2) {
        }
    }

    public static byte getIdOpenShield(final int iHour) {
        if (iHour < 10) {
            return 0;
        }
        if (iHour >= 10 && iHour < 15) {
            return 1;
        }
        if (iHour >= 15 && iHour < 20) {
            return 2;
        }
        return 3;
    }

    public static boolean haveShieldNation(final int inCountry) {
        final Calendar cl = Calendar.getInstance();
        final int iHour = cl.get(11);
        if (iHour >= 0 && iHour < 6) {
            return true;
        }
        if (Map.idClanTown[inCountry] == -1) {
            return true;
        }
        for (byte i = 0; i < 4; ++i) {
            if (Map.SHIELD[inCountry][i] > 0 && iHour >= Char.hourShield[i][0] && iHour < Char.hourShield[i][1]) {
                return true;
            }
        }
        return false;
    }

    public boolean haveShield() {
        final Calendar cl = Calendar.getInstance();
        final int iHour = cl.get(11);
        if (this.map != null && this.map.mapIDLoadMap == 118) {
            return false;
        }
        if (iHour >= 0 && iHour < 6) {
            return true;
        }
        if (Map.pkAuto) {
            return false;
        }
        if (Map.idClanTown[this.inCountry] == -1) {
            return !this.isKiller;
        }
        for (byte i = 0; i < 4; ++i) {
            if (Map.SHIELD[this.inCountry][i] > 0 && iHour >= Char.hourShield[i][0] && iHour < Char.hourShield[i][1]) {
                return !this.isKiller;
            }
        }
        return false;
    }

    public void controlQuest() {
    }

    public void gotItemQuest() {
    }

    public void removeAllItem() {
        this.iItems.removeAll(this.iItems);
        this.tItems.removeAll(this.tItems);
        this.rItems.removeAll(this.rItems);
        this.bItem.removeAll(this.bItem);
        this.awItems.removeAll(this.awItems);
        this.friendListID.removeAll(this.friendListID);
        this.friendListName.removeAll(this.friendListName);
    }

    public void cleanup() {
        this.nearChars.removeAll(this.nearChars);
        this.iItems.removeAll(this.iItems);
        this.tItems.removeAll(this.tItems);
        this.rItems.removeAll(this.rItems);
        this.bItem.removeAll(this.bItem);
        this.awItems.removeAll(this.awItems);
        this.friendListID.removeAll(this.friendListID);
        this.friendListName.removeAll(this.friendListName);
        this.animal.removeAll(this.animal);
        this.pet.removeAll(this.pet);
    }

    public void resetClanQuest() {
    }

    public void sendInfoQuestClan() {
    }

    public void checkFinishQuestClan(final byte item) {
    }

    public boolean checkFinishQuestClanMons(final int idmonster) {
        return false;
    }

    public boolean checkFinsishQuest(final int idMonster, final int idNpc, final int idItem) {
        if (this.isFinish) {
            return false;
        }
        final int type = QuestTemplate.QUEST_TYPE[this.questID - 1];
        final Message m = new Message(52);
        final int nNPC = 0;
        int f = 0;
        try {
            switch (type) {
                case 0: {
                    f = 0;
                    if (this.monsterKill != null) {
                        for (int i = 0; i < this.monsterKill.length; ++i) {
                            if (this.monsterKill[i][1] == 0) {
                                ++f;
                            } else if (idMonster == this.monsterKill[i][0]) {
                                if (this.monsterKill[i][1] > 0) {
                                    final int[] array = this.monsterKill[i];
                                    final int n = 1;
                                    --array[n];
                                }
                                if (this.monsterKill[i][1] == 0) {
                                    ++f;
                                }
                            }
                        }
                    }
                    if (f == this.monsterKill.length) {
                        this.sendInfoFinishQuest();
                        break;
                    }
                    break;
                }
                case 1: {
                    this.sendInfoFinishQuest();
                    break;
                }
                case 2: {
                    f = 0;
                    if (this.monsterIem != null) {
                        for (int i = 0; i < this.monsterIem.length; ++i) {
                            if (this.monsterIem[i][1] == 0) {
                                ++f;
                            } else if (idItem == this.monsterIem[i][2]) {
                                if (this.monsterIem[i][1] > 0) {
                                    final int[] array2 = this.monsterIem[i];
                                    final int n2 = 1;
                                    --array2[n2];
                                }
                                if (this.monsterIem[i][1] == 0) {
                                    ++f;
                                }
                            }
                        }
                    }
                    if (f == this.monsterIem.length) {
                        this.sendInfoFinishQuest();
                        break;
                    }
                    break;
                }
                case 3: {
                    this.sendInfoFinishQuest();
                    break;
                }
            }
        } catch (final Exception ex) {
        } finally {
            try {
                m.cleanup();
            } catch (final Exception ex2) {
            }
        }
        try {
            m.cleanup();
        } catch (final Exception ex3) {
        }
        return false;
    }

    private void sendInfoFinishQuest() {
        MessageCreator.createMsgCharQuest(this, 0);
        MessageCreator.createMsgCharQuest(this, 1);
        MessageCreator.createMsgCharQuest(this, 2);
    }

    public void talkWithNpcResponse(final int idNpc, final int type, final int idQuest) {
        if (type == 0) {
            if (idQuest != this.questID || !this.isFinish || !this.receiveQuest) {
                return;
            }
            if (!this.talk_npc && idNpc == QuestTemplate.npc_quest_response[this.questID - 1][this.currentNpc]) {
                this.talk_npc = true;
            }
        } else {
            if ((idQuest != this.questID || !this.isFinish || !this.receiveQuest) && QuestTemplate.getTypeQuest(idQuest) != 3) {
                return;
            }
            if (this.talk_npc && idNpc == QuestTemplate.npc_quest_response[this.questID - 1][this.currentNpc]) {
                ++this.currentNpc;
                if (this.currentNpc >= QuestTemplate.npc_quest_response[this.questID - 1].length) {
                    final Message m = new Message(54);
                    try {
                        this.delItemQuestOutInventory();
                        this.onSendGiftQuest();
                        this.resetQuest();
                        if (this.nextQuest < QuestTemplate.npc_quest_receive.length) {
                            this.idNpc = QuestTemplate.npc_quest_receive[this.nextQuest];
                            m.dos.writeShort(++this.nextQuest);
                            m.dos.writeByte(QuestTemplate.npc_quest_receive[this.nextQuest - 1]);
                            this.createCharQuest();
                            Database.instance.saveCharQuest(this.charDBID, this.char_quest);
                        } else {
                            m.dos.writeShort(-1);
                            ++this.nextQuest;
                            this.createCharQuest();
                            Database.instance.saveCharQuest(this.charDBID, this.char_quest);
                        }
                        this.sendMessage(m);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            m.cleanup();
                        } catch (final Exception ex) {
                        }
                    }
                    try {
                        m.cleanup();
                    } catch (final Exception ex2) {
                    }
                }
                this.talk_npc = false;
            }
        }
    }

    public boolean canEatCandy(final int type) {
        for (int i = 0; i < this.AllEffBuff.size(); ++i) {
            if (this.AllEffBuff.get(i).idEff == type) {
                return false;
            }
        }
        return true;
    }

    private void delItemQuestOutInventory() {
        for (int i = 10; i < 14; ++i) {
            this.potions[i] = 0;
        }
    }

    private void onSendGiftQuest() {
        Message m = new Message(59);
        try {
            if (this.questID == 2) {
                Item item = null;
                ItemTemplates itemTemplate = null;
                m.dos.writeUTF(QuestTemplate.info_Gif[this.questID - 1]);
                this.sendMessage(m);
                m.cleanup();
                m = new Message(18);
                switch (this.charClass) {
                    case 0: {
                        itemTemplate = (ItemTemplates) Map.itemTemplates.get(0).get(80);
                        item = new Item(itemTemplate, false, 0, 0, 80);
                        break;
                    }
                    case 1: {
                        itemTemplate = (ItemTemplates) Map.itemTemplates.get(1).get(87);
                        item = new Item(itemTemplate, false, 1, 1, 87);
                        break;
                    }
                    case 2: {
                        itemTemplate = (ItemTemplates) Map.itemTemplates.get(2).get(94);
                        item = new Item(itemTemplate, false, 2, 2, 94);
                        break;
                    }
                    case 3: {
                        itemTemplate = (ItemTemplates) Map.itemTemplates.get(3).get(101);
                        item = new Item(itemTemplate, false, 3, 3, 101);
                        break;
                    }
                    case 4: {
                        itemTemplate = (ItemTemplates) Map.itemTemplates.get(4).get(108);
                        item = new Item(itemTemplate, false, 4, 4, 108);
                        break;
                    }
                }
                item.id = this.getIDItem();
                item.owner = this.charDBID;
                this.iItems.add(item);
                m.dos.writeShort(this.id);
                m.dos.writeByte(item.clazz);
                m.dos.writeShort(item.id);
                m.dos.writeShort(item.id);
                m.dos.writeByte(item.getTemplate().id);
                m.dos.writeByte(item.plus);
                m.dos.writeByte(item.level);
                m.dos.writeShort(item.durable);
                m.dos.writeShort(item.mDurable);
                this.sendMessage(m);
                m.cleanup();
            }
            if (this.questID == 8) {
                final String[] st = {"Chém", "Chém", "Đánh", "Đập", "Bắn"};
                m.dos.writeUTF(String.valueOf(QuestTemplate.info_Gif[this.questID - 1]) + st[this.charClass]);
                if (this.skill[0] == 0) {
                    this.skill[0] = 1;
                }
                this.sendMessage(m);
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
            }
            Map.addXPForChar(this, QuestTemplate.gold_xp_gift[this.questID - 1][1], false, "char onSendGiftQuest");
            if (QuestTemplate.gold_xp_gift[this.questID - 1][0] > 0) {
                this.sendPotionQuest(this.map.createPotion((short) 0, QuestTemplate.gold_xp_gift[this.questID - 1][0], this.map));
            }
            final short[] giftPotion = QuestTemplate.potionGift[this.questID - 1];
            for (int i = 0; i < giftPotion.length; ++i) {
                final int[] potions = this.potions;
                final int n = i;
                potions[n] += giftPotion[i];
            }
        } catch (final Exception e) {
            e.printStackTrace();
            System.err.println("LOI GUI THONG TIN QUA CHO CLIENT");
        }
    }

    public void talkWithNpc(final int idNpc, final int type, final int questID) {
        if (questID != this.questID || !this.receiveQuest || this.npc_quest == null) {
            return;
        }
        if (QuestTemplate.QUEST_TYPE[questID - 1] != 1) {
            return;
        }
        if (type == 0) {
            if (!this.talk_npc && idNpc == this.npc_quest[this.currentNpc]) {
                this.talk_npc = true;
            }
        } else if (this.npc_quest != null && this.talk_npc) {
            ++this.currentNpc;
            if (this.currentNpc >= this.npc_quest.length) {
                this.currentNpc = 0;
                this.checkFinsishQuest(-1, -1, -1);
                this.npc_quest = null;
                this.delItemQuestOutInventory();
            }
            this.talk_npc = false;
        }
    }

    public int getIdItemQuest(final int idmonster) {
        if (this.monsterIem != null) {
            for (int i = 0; i < this.monsterIem.length; ++i) {
                if (idmonster == this.monsterIem[i][0] && this.monsterIem[i][1] > 0) {
                    return this.monsterIem[i][2];
                }
            }
        }
        return -1;
    }

    public void resetQuest() {
        this.receiveQuest = false;
        this.isFinish = false;
        this.monsterIem = null;
        this.monsterKill = null;
        this.npc_quest = null;
        this.questID = -1;
        this.talk_npc = false;
        this.currentNpc = 0;
        if (this.char_quest != null) {
            for (int i = 0; i < 3; ++i) {
                this.char_quest.nMonster[i] = 0;
                this.char_quest.nItem[i] = 0;
            }
        }
    }

    public void userExitGame() {
        TichLuyOnline.Instance.exitGame(this.charDBID);
        if (!this.isAdmin) {
            Logdata.addChar(this, 0);
        }
        this.timeExit = System.currentTimeMillis();
        try {
            if (this.userTrade.size() == 1) {
                try {
                    if (!this.finishTrade) {
                        final Message m = new Message(66);
                        m.dos.writeByte(3);
                        this.userTrade.get(0).sendMessage(m);
                        m.cleanup();
                    }
                    this.userTrade.get(0).tItems.removeAllElements();
                    this.userTrade.get(0).rItems.removeAllElements();
                    this.userTrade.get(0).finishTrade = false;
                    this.userTrade.get(0).userTrade.removeAllElements();
                    this.userTrade.get(0).tPotions = null;
                    this.userTrade.get(0).rPotions = null;
                    this.userTrade.get(0).tPotions = new int[162];
                    this.userTrade.get(0).rPotions = new int[162];
                } catch (final Exception e) {
                    System.out.println("LOI THUC HIEN HOAN THANH TRAO DOI");
                }
                this.userTrade.removeAllElements();
            }
            this.gifLuckyBox.removeAllElements();
        } catch (final Exception e) {
            System.out.println("LOI KHI DANG TRONG QUA TRINH EXIT + CO TRAO DOI");
        }
        this.pk = 0;
        this.isThodia = false;
        this.finishTrade = false;
        final int size = this.party.userParty.size();
        if (size > 0) {
            if (this.id == this.party.idMaster) {
                this.deleteParty();
            } else {
                this.outParty();
            }
        }
        try {
            this.tItems.removeAllElements();
            this.rItems.removeAllElements();
        } catch (final Exception ex) {
        }
        this.removeUserSell();
        if (this.mapID == Map.idMapTown && this.timeGiveCardTown > 0L) {
            if (Map.giveCardFail(this)) {
                final Vector<Char> players = this.map.getAllPlayer((this.mapID == 225 || this.mapID == 226) ? this.idWedding : this.inCountry, this.region);
                for (int i = 0; i < players.size(); ++i) {
                    try {
                        players.get(i).sendMessage(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " giao thẻ thất bại"));
                        players.get(i).sendMessage(MessageCreator.createMsgStartGetTown(this.myCountry));
                    } catch (final Exception ex2) {
                    }
                }
            }
            if (this.map.isMapLienDau()) {
                final MapLienDau j = (MapLienDau) this.map;
                if (j.giveCardLienDauFail(this)) {
                    try {
                        j.sendAllPlayer(MessageCreator.createServerAlertAutoOffMessage("Lãnh thổ " + NpcReceiveCard.nameCountry[this.myCountry] + " giao thẻ thất bại"), this.myCountry);
                        j.sendAllPlayer(j.createMsgStartGetTown(this.myCountry), this.myCountry);
                    } catch (final IOException ex3) {
                    }
                }
            }
        }
        this.exit = true;
        this.charCopyDissapear();
        this.charhireDissapear();
        this.charThanThuDissapear();
    }

    public void charCopyDissapear() {
        try {
            if (this.charCopy != null) {
                this.receiveQuestVulan = 0;
                CharManager.instance.remove(this.charCopy);
                RealController.intance.idGen.putID(this.charCopy.id, 0, "return id player 2 IDGEN");
                final Message msg = new Message(8);
                msg.dos.writeShort(this.charCopy.id);
                final byte idcountry = this.charCopy.myCountry;
                ((CharCopy) this.charCopy).owner = null;
                this.charCopy = null;
                this.map.sendAllPlayer(msg, idcountry, this.region);
            }
        } catch (final Exception ex) {
        }
    }

    public void charhireDissapear() {
        try {
            if (this.charHire != null) {
                CharManager.instance.remove(this.charHire);
                RealController.intance.idGen.putID(this.charHire.id, 0, "return id player 2 IDGEN");
                final Message msg = new Message(8);
                msg.dos.writeShort(this.charHire.id);
                final byte idcountry = this.charHire.myCountry;
                ((CharCopy) this.charHire).owner = null;
                this.charHire = null;
                this.map.sendAllPlayer(msg, idcountry, this.region);
            }
        } catch (final Exception ex) {
        }
    }

    public void charThanThuDissapear() {
        try {
            if (this.charthanthu != null) {
                CharManager.instance.remove(this.charthanthu);
                RealController.intance.idGen.putID(this.charthanthu.id, 0, "return id player 2 IDGEN");
                final Message msg = new Message(8);
                msg.dos.writeShort(this.charthanthu.id);
                final byte idcountry = this.charthanthu.myCountry;
                this.charthanthu.owner = null;
                this.charthanthu = null;
                this.map.sendAllPlayer(msg, idcountry, this.region);
            }
        } catch (final Exception ex) {
        }
    }

    public boolean removeUserSell() {
        for (int i = 0; i < Map.npcSell[this.myCountry].length; ++i) {
            Map.npcSell[this.myCountry][i].removeAllUser(this.id);
        }
        return false;
    }

    public void setItem2Shop() {
        try {
            if (this.myCountry == -1) {
                return;
            }
            for (int i = 0; i < Map.npcSell[this.myCountry].length; ++i) {
                final Vector<Item> itnew = new Vector<Item>();
                for (int m = 0; m < 3; ++m) {
                    final Vector<Item> item = Map.npcSell[this.myCountry][i].getListItemUser(this.id, m);
                    if (item.size() > 0) {
                        for (int k = 0; k < item.size(); ++k) {
                            final Item its = item.get(k);
                            for (int j = 0; j < this.iItems.size(); ++j) {
                                final Item it = this.iItems.get(j);
                                if (it.dbid == its.dbid) {
                                    itnew.add(it);
                                    it.prizeSell = its.prizeSell;
                                }
                            }
                        }
                        if (itnew.size() > 0) {
                            Map.npcSell[this.myCountry][i].shop[m].setListItem(this, itnew);
                        }
                    }
                }
            }
        } catch (final Exception ex) {
        }
    }

    public void resetAllData() {
        this.resetQuest();
        this.monsterIem = null;
        this.monsterKill = null;
        this.receiveQuest = false;
        this.isFinish = false;
        this.char_quest = null;
    }

    public String getInfoAnimal() {
        if (this.animalRide != null) {
            return this.animalRide.getInfoSendUser();
        }
        return "";
    }

    public String[] getListGem() {
        String soluong = String.valueOf(this.listGemitem[0]) + "_" + this.allGemGet[0] + "_" + this.allGemUse[0];
        String soluongLock = String.valueOf(this.listGemitemLock[0]) + "_" + this.allGemGetLock[0] + "_" + this.allGemUseLock[0];
        for (int i = 1; i < this.listGemitem.length; ++i) {
            soluong = String.valueOf(soluong) + "," + this.listGemitem[i] + "_" + this.allGemGet[i] + "_" + this.allGemUse[i];
            soluongLock = String.valueOf(soluongLock) + "," + this.listGemitemLock[i] + "_" + this.allGemGetLock[i] + "_" + this.allGemUseLock[i];
        }
        return new String[]{soluong, soluongLock};
    }

    public String getDBInfo() {
        String pInfo = new StringBuilder().append(this.headStyle).toString();
        pInfo = String.valueOf(pInfo) + "," + this.gender;
        pInfo = String.valueOf(pInfo) + "," + this.charname;
        pInfo = String.valueOf(pInfo) + "," + this.charClass;
        pInfo = String.valueOf(pInfo) + "," + this.lvDetail.getExp();
        pInfo = String.valueOf(pInfo) + "," + this.lastLV;
        pInfo = String.valueOf(pInfo) + "," + this.hp;
        pInfo = String.valueOf(pInfo) + "," + this.mp;
        pInfo = String.valueOf(pInfo) + "," + this.mapID;
        pInfo = String.valueOf(pInfo) + "," + this.x;
        pInfo = String.valueOf(pInfo) + "," + this.y;
        pInfo = String.valueOf(pInfo) + "," + this.killer;
        pInfo = String.valueOf(pInfo) + "," + this.timeKiller;
        pInfo = String.valueOf(pInfo) + "," + this.nPKill;
        pInfo = String.valueOf(pInfo) + "," + this.fullGoldTime;
        pInfo = String.valueOf(pInfo) + "," + this.halfGoldTime;
        pInfo = String.valueOf(pInfo) + "," + (this.isAdmin ? 1 : 0);
        pInfo = String.valueOf(pInfo) + "," + this.typeConfig;
        pInfo = String.valueOf(pInfo) + "," + this.autoGetItem;
        pInfo = String.valueOf(pInfo) + "," + this.autoSkill;
        pInfo = String.valueOf(pInfo) + "," + this.oldLv;
        pInfo = String.valueOf(pInfo) + "," + this.autoComeHome;
        pInfo = String.valueOf(pInfo) + "," + this.rideHorse;
        pInfo = String.valueOf(pInfo) + "," + this.wModel.idModel;
        pInfo = String.valueOf(pInfo) + "," + this.pointCongHienClan;
        pInfo = String.valueOf(pInfo) + "," + (this.isOnlineToDay ? 1 : 0);
        pInfo = String.valueOf(pInfo) + "," + this.rcvGifByLv;
        pInfo = String.valueOf(pInfo) + "," + this.rcvGifByHour;
        pInfo = String.valueOf(pInfo) + "," + this.rcvGifByMonth;
        pInfo = String.valueOf(pInfo) + "," + this.totalDayInMonth;
        pInfo = String.valueOf(pInfo) + "," + this.idAutoSkill;
        pInfo = String.valueOf(pInfo) + "," + this.idAutoGetitem;
        pInfo = String.valueOf(pInfo) + "," + this.dateStartOnline;
        pInfo = String.valueOf(pInfo) + "," + this.lvHouse;
        pInfo = String.valueOf(pInfo) + "," + this.lvStore;
        pInfo = String.valueOf(pInfo) + "," + this.maxBag;
        pInfo = String.valueOf(pInfo) + "," + this.slotWearing;
        pInfo = String.valueOf(pInfo) + "," + this.nMoonCake2Exp;
        pInfo = String.valueOf(pInfo) + "," + this.receiveLG;
        pInfo = String.valueOf(pInfo) + "," + this.myCountry;
        pInfo = String.valueOf(pInfo) + "," + this.gif35;
        pInfo = String.valueOf(pInfo) + "," + this.luongNap;
        pInfo = String.valueOf(pInfo) + "," + this.luonglost;
        pInfo = String.valueOf(pInfo) + "," + this.dayLogin;
        pInfo = String.valueOf(pInfo) + "," + this.forbitChat;
        pInfo = String.valueOf(pInfo) + "," + this.usingSummons;
        pInfo = String.valueOf(pInfo) + "," + this.maxKill;
        pInfo = String.valueOf(pInfo) + "," + this.honor;
        pInfo = String.valueOf(pInfo) + "," + this.xichtho_thienlyma;
        pInfo = String.valueOf(pInfo) + "," + this.married;
        pInfo = String.valueOf(pInfo) + "," + this.idWedding;
        pInfo = String.valueOf(pInfo) + "," + this.nameHusband_wife;
        pInfo = String.valueOf(pInfo) + "," + this.rcvGiftWedding;
        pInfo = String.valueOf(pInfo) + "," + this.passWord;
        pInfo = String.valueOf(pInfo) + "," + this.countQuestClan;
        pInfo = String.valueOf(pInfo) + "," + this.pointActiveQuest;
        pInfo = String.valueOf(pInfo) + "," + this.idArena;
        pInfo = String.valueOf(pInfo) + "," + this.pArena;
        pInfo = String.valueOf(pInfo) + "," + this.idMainQuest;
        pInfo = String.valueOf(pInfo) + "," + this.nBinhTra;
        pInfo = String.valueOf(pInfo) + "," + this.nHopMut;
        pInfo = String.valueOf(pInfo) + "," + this.gif83;
        pInfo = String.valueOf(pInfo) + "," + this.ntangtoc;
        pInfo = String.valueOf(pInfo) + "," + this.subpk;
        pInfo = String.valueOf(pInfo) + "," + this.receiveQuestVulan;
        pInfo = String.valueOf(pInfo) + "," + this.timeUp5Attribute;
        pInfo = String.valueOf(pInfo) + "," + this.typeTieu;
        pInfo = String.valueOf(pInfo) + "," + this.timeOutGame;
        pInfo = String.valueOf(pInfo) + "," + this.region;
        pInfo = String.valueOf(pInfo) + "," + this.lockLuong;
        pInfo = String.valueOf(pInfo) + "," + this.minuteExp;
        pInfo = String.valueOf(pInfo) + "," + this.luot_van_tieu;
        pInfo = String.valueOf(pInfo) + "," + this.buy_luot_van_tieu;
        pInfo = String.valueOf(pInfo) + "," + this.cuop_tieu;
        pInfo = String.valueOf(pInfo) + "," + this.timeJoinClan;
        pInfo = String.valueOf(pInfo) + "," + this.giveMoneyClan;
        pInfo = String.valueOf(pInfo) + "," + this.minuteSocola;
        pInfo = String.valueOf(pInfo) + "," + this.receiveGiftEvent;
        pInfo = String.valueOf(pInfo) + "," + this.idTongKim;
        pInfo = String.valueOf(pInfo) + "," + this.idTeamTongKim;
        pInfo = String.valueOf(pInfo) + "," + this.diemdoivePH;
        pInfo = String.valueOf(pInfo) + "," + this.winPH;
        pInfo = String.valueOf(pInfo) + "," + this.timeExistCharHire;
        pInfo = String.valueOf(pInfo) + "," + this.classlinh;
        pInfo = String.valueOf(pInfo) + "," + this.lvlinhthue;
        pInfo = String.valueOf(pInfo) + "," + this.genderlinh;
        pInfo = String.valueOf(pInfo) + "," + this.lvlinh;
        pInfo = String.valueOf(pInfo) + "," + this.countnvVulan;
        pInfo = String.valueOf(pInfo) + "," + this.firstNapMoney;
        pInfo = String.valueOf(pInfo) + "," + this.timeUseTheMuaBan;
        pInfo = String.valueOf(pInfo) + "," + this.banphaohoa;
        pInfo = String.valueOf(pInfo) + "," + this.x2Phaohoa;
        pInfo = String.valueOf(pInfo) + "," + this.dayCamChat;
        pInfo = String.valueOf(pInfo) + "," + this.countTime;
        pInfo = String.valueOf(pInfo) + "," + this.receiveGiftOldChar;
        pInfo = String.valueOf(pInfo) + "," + this.buychoi;
        pInfo = String.valueOf(pInfo) + "," + this.buylongden;
        pInfo = String.valueOf(pInfo) + "," + this.xuxai;
        pInfo = String.valueOf(pInfo) + "," + this.luongxai;
        pInfo = String.valueOf(pInfo) + "," + this.vetangluong;
        pInfo = String.valueOf(pInfo) + "," + this.countMonsterKill;
        pInfo = String.valueOf(pInfo) + "," + this.hlt_buy;
        pInfo = String.valueOf(pInfo) + "," + this.luongxainhanRuong;
        pInfo = String.valueOf(pInfo) + "," + this.idEffDanhHieu;
        pInfo = String.valueOf(pInfo) + "," + this.minuteOnline;
        pInfo = String.valueOf(pInfo) + "," + this.timeInClan;
        pInfo = String.valueOf(pInfo) + "," + this.nhomThidau;
        pInfo = String.valueOf(pInfo) + "," + this.vip;
        pInfo = String.valueOf(pInfo) + "," + this.diemNapVip;
        pInfo = String.valueOf(pInfo) + "," + this.diemxaiVip;
        pInfo = String.valueOf(pInfo) + "," + (this.rcvInviteVip ? 1 : 0);
        pInfo = String.valueOf(pInfo) + "," + (this.isCaiTrang);

        return pInfo;
    }

    public static void setTimeAuto(final int day) {
    }

    public Item genItem(final int idTemplate, final int charClass) {
        final ItemTemplates itTemp = (ItemTemplates) Map.itemTemplates.get(charClass).get(new Integer(idTemplate));
        final Item item = new Item(itTemp, false, charClass, charClass, idTemplate);
        return item;
    }

    public void setInfoModelCharBot(final int type, final int idTemplate) {
        final Item it = new Item();
        it.setTemplate(idTemplate, 0, 0, idTemplate);
        this.wModel.setItemModel(this, it, it.atb[4]);
    }

    public void setInfoChar(final String charname, final int type, final int gender, final int charclass, final Map mapid, final int x, final int y, final int userid, final int ao, final int quan, final int non) {
        this.headStyle = 9;
        this.charname = charname;
        this.isBot = (byte) type;
        this.gender = (byte) gender;
        this.charClass = (byte) charclass;
        this.wItems[0][getIndexItemWearing(0, 0)] = this.genItem(ao, 0);
        this.wItems[0][getIndexItemWearing(1, 0)] = this.genItem(quan, 0);
        if (non > -1) {
            this.wItems[0][getIndexItemWearing(2, 0)] = this.genItem(non, 0);
        }
      
        this.wItems[0][getIndexItemWearing(0, 0)].id = 0;
        this.map = mapid;
        this.mapID = this.map.mapId;
        this.x = x;
        this.y = y;
        this.userID = userid;
        this.hp = 1;
        this.myCountry = 1;
        Char.timeSendRaoVat = System.currentTimeMillis() + 900000L;
    }

   


    private void initOnlineReceiveGif(final Date lastlog) {
        final Calendar calNow = Calendar.getInstance();
        final Calendar calLog = Calendar.getInstance();
        calLog.setTime(lastlog);
        if (calLog.get(1) != calNow.get(1) || calLog.get(2) != calNow.get(2) || calLog.get(5) != calNow.get(5)) {
            this.rcvGifByHour = 1;
            ++this.totalDayInMonth;
            this.isOnlineToDay = false;
        }
        if (calLog.get(1) != calNow.get(1) || calLog.get(2) != calNow.get(2)) {
            this.rcvGifByMonth = 1;
            this.totalDayInMonth = 1;
            this.isOnlineToDay = false;
        }
    }

    public void setCountry() {
        try {
            final NewClan clan = NewClan.getClan(this.idClan);
            if (clan == null) {
                this.idClan = -1;
            } else if ((this.idClan >= 0 && this.myCountry == -1) || clan.country != this.myCountry) {
                this.myCountry = clan.country;
            }
        } catch (final Exception ex) {
        }
    }

    public void countItemBug() {
        for (int i = 0; i < this.itemCheckDuplicate.size(); ++i) {
        }
    }

    public void setItemTuiDo(String info) {
        if (info == null) {
            return;
        }
        try {
            if (info.startsWith(">")) {
                info = info.substring(1, info.length());
            }
        } catch (final Exception ex) {
        }
        
        if (!info.equals("")) {
            final String[] allitem = split(info, ">");
            int size = allitem.length;
            if (size > 130) {
                size = 130;
            }
            
            for (int i = 0; i < size; ++i) {
                try {
                    final String[] dtitem = split(allitem[i], "|");
                    final Item item = new Item();
                    item.dbInfo = dtitem[0];
                    item.initInfoFromDB();
                    item.owner = this.charDBID;
                    item.setTemplate(item.tempateID, item.clazz, item.clazz, item.tempateID);
    
                    // Only keep essential time-based checks
                    if (item.getMinuteLoan(false) <= 0 && item.minuteExist > 0) {
                        try {
                            this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng " + item.getTemplate().name + " đã hết"));
                            Database.instance.deleteItem(item.dbid);
                            Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "timeout");
                        } catch (final Exception ex2) {
                        }
                        continue;
                    }
    
                    // Process item attributes
                    final String[] data = split(dtitem[1], ",");
                    String countnguhanh = "";
                    for (int j = 0; j < data.length; ++j) {
                        try {
                            if (j < 33) {
                                item.atb[j] = Short.parseShort(data[j].trim());
                                if (j >= 28 && item.atb[j] > 0 && j != item.idNguHanh2) {
                                    countnguhanh = String.valueOf(countnguhanh) + j + ",";
                                }
                            } else if (j < 43) {
                                item.newAtb[j - 33] = Byte.parseByte(data[j].trim());
                            } else if (j < 58) {
                                item.addMoreLevelSkill[j - 43] = Byte.parseByte(data[j].trim());
                            } else if (j < 61) {
                                item.lockAtb[j - 58] = Byte.parseByte(data[j].trim());
                            } else {
                                item.otherAtt[j - 61] = Short.parseShort(data[j].trim());
                            }
                        } catch (final Exception ex3) {
                        }
                    }
    
                    // Handle animal items
                    if (Map.isWearingAnimal(item.getTemplate().type)) {
                        if (!item.isHaveAttLock() && item.colorName == 1) {
                            switch (item.hangItem) {
                                case 1:
                                    item.lockItemAnimal(new byte[]{0, 0, 0, 0, 0, 1});
                                    break;
                                case 2:
                                    item.lockItemAnimal(new byte[]{0, 0, 0, 0, 1, 0});
                                    break;
                                case 3:
                                    item.lockItemAnimal(new byte[]{0, 0, 0, 1, 0, 0});
                                    break;
                                case 4:
                                    item.lockItemAnimal(new byte[]{0, 0, 1, 0, 0, 0});
                                    break;
                                case 5:
                                    byte[] material = new byte[6];
                                    material[1] = 1;
                                    item.lockItemAnimal(material);
                                    break;
                            }
                        }
                        item.reFixItemAnimal(this.charname);
                    }
    
                    // Set server-specific char seal
                    if (TeamServer.isServerLienDau() && this.charname.indexOf("_") > -1 && !item.charSeal.equals(this.charname)) {
                        item.charSeal = this.charname;
                    }
    
                    // Final item setup
                    if (ItemLuckyDraw.isChoi(item.getTemplate().id)) {
                        item.atb[9] = 2;
                    }
                    item.id = this.getIDItem();
                    this.bagItems.add(item);
    
                } catch (final Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void setitemInvenBag(final int place) {
        String info = (place == 0) ? this.invent : this.bag;
        if (info == null) {
            return;
        }
        try {
            if (info.startsWith(">")) {
                info = info.substring(1, info.length());
            }
        } catch (final Exception ex) {
        }
        if (info.equals("")) {
            return;
        }
        final String[] allitem = split(info, ">");
        int size = allitem.length;
        if (size > 130) {
            size = 130;
        }
        for (int i = 0; i < size; ++i) {
            try {
                final String[] dtitem = split(allitem[i], "|");
                final Item item = new Item();
                item.dbInfo = dtitem[0];
                item.initInfoFromDB();
                item.owner = this.charDBID;
                item.setTemplate(item.tempateID, item.clazz, item.clazz, item.tempateID);
                if (item.getMinuteLoan(false) <= 0 && item.minuteExist > 0) {
                    try {
                        this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng " + item.getTemplate().name + " đã hết"));
                        Database.instance.deleteItem(item.dbid);
                        Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "timeout");
                    } catch (final Exception ex2) {
                    }
                } else if (Map.isPhiPhong(item.getTemplate().type) && item.minuteExist == 0 && item.dateCreate.equals("2016-09-15")) {
                    Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "ffbug");
                } else if (item.isPhiPhongWordCupHetHan() && item.dateCreate.compareTo("2018-08-22") <= 0) {
                    Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "ffbug");
                } else if (item.isGayGiangSinh() && item.dateCreate.compareTo("2020-07-24 11:00:00") < 0) {
                    Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "thugay");
                } else {
                    if (item.isChoiSell() && this.lastLog.compareTo("2019-11-14 10:35:00") < 0) {
                        item.doResetTimeChoiDuaTop();
                    }
                    if (item.isChoi() && this.lastLog.compareTo("2020-05-08 14:35:00") < 0) {
                        Database.instance.saveOrtherLog("", this.charname, String.valueOf(item.getName()) + "_" + item.getInfoSave(), "logchoi");
                    }
                    if (item.isChoiSell() && this.lastLog.compareTo("2019-11-29 10:50:00") < 0 && !this.charname.equals(Char.chartopchoitrungthu) && item.dogetbackChoiDuaTop()) {
                        Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "tichthuchoi");
                    } else if (!item.isHoVeLenh() || this.lastLog.compareTo("2020-01-17 16:30:00") >= 0) {
                        if (ItemLuckyDraw.isChoi(item.getTemplate().id) && this.lastLog.compareTo("2019-02-26 11:30:00") < 0 && item.dateCreate.compareTo("2019-02-26 11:00:00") >= 0) {
                            item.lock = 0;
                        }
                        if (!Map.openLog && TeamServer.server == 5 && this.lastLog.compareTo("2018-03-12 11:25:00") < 0 && Map.isItemThanThu(item.getTemplate().type)) {
                            Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "hklbug");
                        } else {
                            final String[] data = split(dtitem[1], ",");
                            String countnguhanh = "";
                            for (int j = 0; j < data.length; ++j) {
                                try {
                                    if (j < 33) {
                                        item.atb[j] = Short.parseShort(data[j].trim());
                                        if (j >= 28 && item.atb[j] > 0 && j != item.idNguHanh2) {
                                            countnguhanh = String.valueOf(countnguhanh) + j + ",";
                                        }
                                    } else if (j < 43) {
                                        item.newAtb[j - 33] = Byte.parseByte(data[j].trim());
                                    } else if (j < 58) {
                                        item.addMoreLevelSkill[j - 43] = Byte.parseByte(data[j].trim());
                                    } else if (j < 61) {
                                        item.lockAtb[j - 58] = Byte.parseByte(data[j].trim());
                                    } else {
                                        item.otherAtt[j - 61] = Short.parseShort(data[j].trim());
                                    }
                                } catch (final Exception ex3) {
                                }
                            }
                            if (Map.isPhiPhong(item.getTemplate().type) && !Map.openLog && item.magic_physic == 0 && item.atb[1] > item.atb[6]) {
                                final short d = item.atb[1];
                                item.atb[1] = item.atb[6];
                                item.atb[6] = d;
                            }
                            if (ItemLuckyDraw.isChoi(item.getTemplate().id) && this.lastLog.compareTo("2020-02-20 13:45:00") < 0 && item.dateCreate.compareTo("2020-02-20 11:40:00") >= 0) {
                                item.removeOptionHoaNguoiTuyetChoi();
                            }
                            if (ItemLuckyDraw.isChoi(item.getTemplate().id) && this.lastLog.compareTo("2019-02-27 15:45:00") < 0 && item.dateCreate.compareTo("2019-02-26 11:00:00") >= 0) {
                                item.setOptionHoaNguoiTuyetChoi();
                            }
                            if (ItemLuckyDraw.isChoi(item.getTemplate().id) && this.lastLog.compareTo("2019-11-11 11:45:00") < 0 && item.dateCreate.compareTo("2019-10-03 11:00:00") >= 0) {
                                item.setOptionHoaNguoiTuyetChoi();
                            }
                            if (item.istrungPet() && this.lastLog.compareTo("2019-09-19 15:52:00") < 0) {
                                Database.instance.saveOrtherLog("", this.charname, item.getInfoSave(), "");
                            }
                            if (!countnguhanh.equals("")) {
                                try {
                                    final String[] dtnh = split(countnguhanh.substring(0, countnguhanh.length() - 1), ",");
                                    if (dtnh.length > 2) {
                                        final byte idNguhanh = Byte.parseByte(dtnh[Map.r.nextInt(dtnh.length)]);
                                        Database.instance.saveOrtherLog("", this.charname, String.valueOf(item.getName()) + " bi huy dong " + InfoItemAttribute.getNameAtt(idNguhanh) + " chi so " + item.atb[idNguhanh], "huynguhanh");
                                        item.atb[idNguhanh] = 0;
                                    }
                                } catch (final Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                if (item.resetItem != 5000 || Map.isWearingAnimal(item.getTemplate().type)) {
                                    if (Map.isWearingAnimal(item.getTemplate().type)) {
                                        if (!item.isHaveAttLock() && item.colorName == 1) {
                                            switch (item.hangItem) {
                                                case 1: {
                                                    item.lockItemAnimal(new byte[]{0, 0, 0, 0, 0, 1});
                                                    break;
                                                }
                                                case 2: {
                                                    item.lockItemAnimal(new byte[]{0, 0, 0, 0, 1, 0});
                                                    break;
                                                }
                                                case 3: {
                                                    item.lockItemAnimal(new byte[]{0, 0, 0, 1, 0, 0});
                                                    break;
                                                }
                                                case 4: {
                                                    item.lockItemAnimal(new byte[]{0, 0, 1, 0, 0, 0});
                                                    break;
                                                }
                                                case 5: {
                                                    final Item item2 = item;
                                                    final byte[] material = new byte[6];
                                                    material[1] = 1;
                                                    item2.lockItemAnimal(material);
                                                    break;
                                                }
                                            }
                                        }
                                        item.reFixItemAnimal(this.charname);
                                    }
                                    item.resetItem = 5000;
                                }
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                            if (this.charname.indexOf("@") > -1 && ((item.dateCreate.compareTo("2016-08-29") < 0 && TeamServer.server == 3) || (item.dateCreate.compareTo("2016-09-01") < 0 && TeamServer.server == 2)) && item.charSeal.toLowerCase().equals(this.charname.substring(0, this.charname.length() - 3))) {
                                item.charSeal = this.charname;
                            }
                            if (this.charname.indexOf("@dn") > -1 && !Map.openLog && this.lastLog.compareTo("2017-04-26 10:15:00") < 0 && TeamServer.server == 1 && item.charSeal.toLowerCase().indexOf(this.charname) > -1) {
                                item.charSeal = this.charname;
                            }
                            if (TeamServer.isServerLienDau() && this.charname.indexOf("_") > -1 && !item.charSeal.equals(this.charname)) {
                                item.charSeal = this.charname;
                            }
                            if (ItemLuckyDraw.isChoi(item.getTemplate().id)) {
                                item.atb[9] = 2;
                            }
                            item.id = this.getIDItem();
                            if (place == 0) {
                                if (item.place == 4) {
                                    if (this.wModel != null) {
                                        item.place = 0;
                                        this.iItems.add(item);
                                    } else {
                                        (this.wModel = new ModelWearing()).setItemModel(this, item, item.getTemplate().atb[4]);
                                    }
                                } else {
                                    item.place = 0;
                                    this.iItems.add(item);
                                }
                            } else {
                                this.bItem.add(item);
                            }
                        }
                    }
                }
            } catch (final Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public void setItemWearing() {
        if (this.equip == null || this.equip.equals("")) {
            final int aoTemplate = (this.gender == 1) ? 2 : 1;
            final int quanTemplate = (this.gender == 1) ? 28 : 27;
            final int[] defaultVK = {79, 86, 93, 100, 107, 79, 86, 93, 100, 107};
            final int vkTemplate = defaultVK[this.charClass];
            final Item ao = new Item((ItemTemplates) Map.itemTemplates.get(this.charClass).get(new Integer(aoTemplate)), false, this.charClass, this.charClass, aoTemplate);
            final Item quan = new Item((ItemTemplates) Map.itemTemplates.get(this.charClass).get(new Integer(quanTemplate)), false, this.charClass, this.charClass, quanTemplate);
            final Item vk = new Item((ItemTemplates) Map.itemTemplates.get(this.charClass).get(vkTemplate), false, this.charClass, this.charClass, vkTemplate);
            ao.place = 1;
            quan.place = 1;
            vk.place = 1;
            ao.id = this.getIDItem();
            quan.id = this.getIDItem();
            vk.id = this.getIDItem();
            int index = getIndexItemWearing(ao.getTemplate().type, 0);
            this.wItems[0][index] = ao;
            index = getIndexItemWearing(quan.getTemplate().type, 0);
            this.wItems[0][index] = quan;
            index = getIndexItemWearing(vk.getTemplate().type, 0);
            this.wItems[0][index] = vk;
            return;
        }
        int posNhan = -1;
        if (!this.equip.equals("")) {
            final String[] allitem = split(this.equip, ">");
            for (int i = 0; i < allitem.length; ++i) {
                try {
                    final String[] dtitem = split(allitem[i], "|");
                    final Item item = new Item();
                    item.dbInfo = dtitem[0];
                    item.initInfoFromDB();
                    item.setTemplate(item.tempateID, item.clazz, item.clazz, item.tempateID);
                    if (item.getMinuteLoan(false) <= 0 && item.minuteExist > 0) {
                        try {
                            this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng " + item.getTemplate().name + " đã hết"));
                            Database.instance.deleteItem(item.dbid);
                            Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "timeout");
                        } catch (final Exception ex) {
                        }
                    } else if (item.isPhiPhongWordCupHetHan() && item.dateCreate.compareTo("2018-08-22") <= 0) {
                        Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "ffbug");
                    } else if (item.isGayGiangSinh() && item.dateCreate.compareTo("2020-07-24 11:00:00") < 0) {
                        Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "thugay");
                    } else {
                        if (item.isChoiSell() && this.lastLog.compareTo("2019-11-14 10:35:00") < 0) {
                            item.doResetTimeChoiDuaTop();
                        }
                        if (item.isChoi() && this.lastLog.compareTo("2020-05-08 14:35:00") < 0) {
                            Database.instance.saveOrtherLog("", this.charname, String.valueOf(item.getName()) + "_" + item.getInfoSave(), "logchoi");
                        }
                        if (item.isChoiSell() && this.lastLog.compareTo("2019-11-29 10:50:00") < 0 && !this.charname.equals(Char.chartopchoitrungthu) && item.dogetbackChoiDuaTop()) {
                            Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "tichthuchoi");
                        } else if (!item.isHoVeLenh() || getDayTime(0L).compareTo("2019-11-30 00:00:00") < 0) {
                            if (Map.isPhiPhong(item.getTemplate().type) && item.minuteExist == 0 && item.dateCreate.equals("2016-09-15")) {
                                Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "ffbug");
                            } else if (!Map.openLog && TeamServer.server == 5 && this.lastLog.compareTo("2018-03-12 11:25:00") < 0 && Map.isItemThanThu(item.getTemplate().type)) {
                                Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "hklbug");
                            } else {
                                String countnguhanh = "";
                                final String[] data = split(dtitem[1], ",");
                                for (int j = 0; j < data.length; ++j) {
                                    try {
                                        if (j < 33) {
                                            item.atb[j] = Short.parseShort(data[j].trim());
                                            if (j >= 28 && item.atb[j] > 0 && j != item.idNguHanh2) {
                                                countnguhanh = String.valueOf(countnguhanh) + j + ",";
                                            }
                                        } else if (j < 43) {
                                            item.newAtb[j - 33] = Byte.parseByte(data[j].trim());
                                        } else if (j < 58) {
                                            item.addMoreLevelSkill[j - 43] = Byte.parseByte(data[j].trim());
                                        } else if (j < 61) {
                                            item.lockAtb[j - 58] = Byte.parseByte(data[j].trim());
                                        } else {
                                            item.otherAtt[j - 61] = Short.parseShort(data[j].trim());
                                        }
                                    } catch (final Exception ex2) {
                                    }
                                }
                                if (ItemLuckyDraw.isChoi(item.getTemplate().id) && this.lastLog.compareTo("2020-02-20 13:45:00") < 0 && item.dateCreate.compareTo("2020-02-20 11:40:00") >= 0) {
                                    item.removeOptionHoaNguoiTuyetChoi();
                                }
                                if (ItemLuckyDraw.isChoi(item.getTemplate().id) && this.lastLog.compareTo("2019-02-27 15:45:00") < 0 && item.dateCreate.compareTo("2019-02-26 11:00:00") >= 0) {
                                    item.setOptionHoaNguoiTuyetChoi();
                                }
                                if (ItemLuckyDraw.isChoi(item.getTemplate().id) && this.lastLog.compareTo("2019-11-11 11:45:00") < 0 && item.dateCreate.compareTo("2019-10-03 11:00:00") >= 0) {
                                    item.setOptionHoaNguoiTuyetChoi();
                                }
                                if (!countnguhanh.equals("")) {
                                    try {
                                        final String[] dtnh = split(countnguhanh.substring(0, countnguhanh.length() - 1), ",");
                                        if (dtnh.length > 2) {
                                            final byte idNguhanh = Byte.parseByte(dtnh[Map.r.nextInt(dtnh.length)]);
                                            Database.instance.saveOrtherLog("", this.charname, String.valueOf(item.getName()) + " bi huy dong " + InfoItemAttribute.getNameAtt(idNguhanh) + " chi so " + item.atb[idNguhanh], "huynguhanh");
                                            item.atb[idNguhanh] = 0;
                                        }
                                    } catch (final Exception ex3) {
                                    }
                                }
                                if (ItemLuckyDraw.isChoi(item.getTemplate().id)) {
                                    item.atb[9] = 2;
                                }
                                if (Map.isPhiPhong(item.getTemplate().type) && !Map.openLog && item.magic_physic == 0 && item.atb[1] > item.atb[6]) {
                                    final short d = item.atb[1];
                                    item.atb[1] = item.atb[6];
                                    item.atb[6] = d;
                                }
                                if (item.getTemplate().type == 13) {
                                    if (this.itemAx == null) {
                                        if (ItemLuckyDraw.isChoi(item.getTemplate().id)) {
                                            item.place = 0;
                                            this.iItems.add(item);
                                        } else if (item.place == 1) {
                                            this.itemAx = item;
                                        }
                                    } else if (ItemLuckyDraw.isChoi(item.getTemplate().id)) {
                                        item.place = 0;
                                        this.iItems.add(item);
                                    } else if (item.place == 1) {
                                        item.place = 0;
                                    }
                                }
                                try {
                                    if (item.resetItem != 5000 || Map.isWearingAnimal(item.getTemplate().type)) {
                                        if (Map.isWearingAnimal(item.getTemplate().type)) {
                                            if (!item.isHaveAttLock() && item.colorName == 1) {
                                                switch (item.hangItem) {
                                                    case 1: {
                                                        item.lockItemAnimal(new byte[]{0, 0, 0, 0, 0, 1});
                                                        break;
                                                    }
                                                    case 2: {
                                                        item.lockItemAnimal(new byte[]{0, 0, 0, 0, 1, 0});
                                                        break;
                                                    }
                                                    case 3: {
                                                        item.lockItemAnimal(new byte[]{0, 0, 0, 1, 0, 0});
                                                        break;
                                                    }
                                                    case 4: {
                                                        item.lockItemAnimal(new byte[]{0, 0, 1, 0, 0, 0});
                                                        break;
                                                    }
                                                    case 5: {
                                                        final Item item2 = item;
                                                        final byte[] material = new byte[6];
                                                        material[1] = 1;
                                                        item2.lockItemAnimal(material);
                                                        break;
                                                    }
                                                }
                                            }
                                            item.reFixItemAnimal(this.charname);
                                        }
                                        item.resetItem = 5000;
                                    }
                                } catch (final Exception ex4) {
                                }
                                if (this.charname.indexOf("@") > -1 && ((item.dateCreate.compareTo("2016-08-29") < 0 && TeamServer.server == 3) || (item.dateCreate.compareTo("2016-09-01") < 0 && TeamServer.server == 2)) && item.charSeal.toLowerCase().equals(this.charname.substring(0, this.charname.length() - 3))) {
                                    item.charSeal = this.charname;
                                }
                                if (this.charname.indexOf("@dn") > -1 && !Map.openLog && this.lastLog.compareTo("2017-04-26 10:15:00") < 0 && TeamServer.server == 1 && item.charSeal.toLowerCase().indexOf(this.charname) > -1) {
                                    item.charSeal = this.charname;
                                }
                                if (TeamServer.isServerLienDau() && this.charname.indexOf("_") > -1 && !item.charSeal.equals(this.charname)) {
                                    item.charSeal = this.charname;
                                }
                                final int ao2 = 0;
                                final int quan2 = 0;
                                final int gang = 0;
                                final int non = 0;
                                final int vukhi = 0;
                                final int nhan = 0;
                                final int daychuyen = 0;
                                final int giay = 0;
                                final int ngoc = 0;
                                final int cuoc = 0;
                                final int aochoang = 0;
                                final int ao3 = 0;
                                final int quan3 = 0;
                                final int gang2 = 0;
                                final int non2 = 0;
                                final int vukhi2 = 0;
                                final int nhan2 = 0;
                                final int daychuyen2 = 0;
                                final int giay2 = 0;
                                final int ngoc2 = 0;
                                final int cuoc2 = 0;
                                final int aochoang2 = 0;
                                final int ring = 0;
                                final int ring2 = 0;
                                item.id = this.getIDItem();
                                final int t = item.getTemplate().type;
                                if (!Map.isWearingAnimal(t)) {
                                    boolean vk2 = false;
                                    if (t == 6 || t == 7 || t == 4 || t == 5 || t == 3) {
                                        vk2 = true;
                                    }
                                    if (item.clazz != this.charClass && item.getTemplate().type != 13 && item.getTemplate().type != 19) {
                                        item.place = 0;
                                        this.iItems.add(item);
                                        System.out.println(String.valueOf(item.clazz) + " " + this.charClass + " ADD VAO INVEN 0");
                                    } else if (item.getTemplate().gender != 0 && this.gender != 0 && item.getTemplate().type != 13 && item.getTemplate().type != 19 && item.getTemplate().gender != this.gender) {
                                        item.place = 0;
                                        this.iItems.add(item);
                                    } else if (item.level > this.lastLV) {
                                        item.place = 0;
                                        this.iItems.add(item);
                                    } else {
                                        if (t == 8) {
                                            if (posNhan == -1) {
                                                posNhan = item.pos;
                                            } else if (posNhan == item.pos) {
                                                if (posNhan == 0) {
                                                    posNhan = 1;
                                                } else {
                                                    posNhan = 0;
                                                }
                                            } else {
                                                posNhan = item.pos;
                                            }
                                        }
                                        if (!ItemLuckyDraw.isChoi(item.getTemplate().id)) {
                                            final int index2 = getIndexItemWearing(t, posNhan);
                                            if (index2 > -1 && this.wItems[item.slotWearing][index2] == null) {
                                                this.wItems[item.slotWearing][index2] = item;
                                                item.pos = (byte) posNhan;
                                            } else {
                                                item.slotWearing = 0;
                                                item.place = 0;
                                                this.iItems.add(item);
                                            }
                                        }
                                    }
                                } else {
                                    if (this.charname.indexOf("@dn") > -1 && !Map.openLog && this.lastLog.compareTo("2017-04-26 10:15:00") < 0 && TeamServer.server == 1) {
                                        final Item item3 = item;
                                        item3.idAnimal += 12607;
                                    }
                                    if (this.checkExistAnimal(item.idAnimal)) {
                                        this.awItems.add(item);
                                    } else {
                                        item.place = 0;
                                        item.slotWearing = 0;
                                        item.place = 0;
                                        this.iItems.add(item);
                                    }
                                }
                            }
                        }
                    }
                } catch (final Exception ex5) {
                }
            }
        }
    }

    public void initPinfo() {
        final byte[] he = {4, 2, 0, 3, 1};
        final short[] basic = {400, 400, 500, 350, 350};
        if (this.dbInfo != null) {
            final String[] data = split(this.dbInfo, ",");
            try {
                this.headStyle = Byte.parseByte(data[0]);
                final byte gender = Byte.parseByte(data[1]);
                if (gender > 0) {
                    if (this.headStyle % 2 != 0 && gender == 1) {
                        this.gender = 2;
                    } else {
                        this.gender = gender;
                        this.isNewClient = true;
                    }
                } else if (this.headStyle % 2 == 0) {
                    this.gender = 1;
                } else {
                    this.gender = 2;
                }
                this.charClass = Byte.parseByte(data[3]);
                this.he = he[this.charClass];
                this.basic = basic[this.charClass];
                this.oldLv = 0;
                try {
                    this.oldLv = Integer.parseInt(data[20]);
                } catch (final Exception ex) {
                }
                final long xp = Long.parseLong(data[4]);
                if (this.oldLv != -5) {
                    this.lvDetail.setExp(xp, this.oldLv, this.charname, "char initPinfo");
                    this.oldLv = -5;
                } else {
                    this.lvDetail.setExpNew(xp);
                }
                this.lastLV = Short.parseShort(data[5]);
                if (this.lastLV < this.lvDetail.lv) {
                    this.lastLV = (short) this.lvDetail.lv;
                }
                this.hp = Integer.parseInt(data[6]);
                if (this.hp <= 0) {
                    this.hp = 1;
                }
                this.mp = Integer.parseInt(data[7]);
                this.mapID = Short.parseShort(data[8]);
                this.mapID = Checker.resetMapId(this.mapID);
                this.x = Integer.parseInt(data[9]);
                this.y = Integer.parseInt(data[10]);
                this.killer = Short.parseShort(data[11]);
                if (this.killer >= 32000 || this.killer < 0) {
                    this.killer = 32000;
                }
                this.timeKiller = Long.parseLong(data[12]);
                this.nPKill = Short.parseShort(data[13]);
                this.fullGoldTime = Long.parseLong(data[14]);
                this.halfGoldTime = Long.parseLong(data[15]);
                this.isAdmin = (Integer.parseInt(data[16]) == 1);
            } catch (final Exception ex2) {
            }
            try {
                this.config(this.typeConfig = Byte.parseByte(data[17]));
            } catch (final Exception ex3) {
            }
            try {
                try {
                    this.autoGetItem = Byte.parseByte(data[18]);
                    this.autoSkill = Byte.parseByte(data[19]);
                    this.autoSkill = 1;
                    this.autoGetItem = 1;
                    this.rideHorse = Byte.parseByte(data[22]);
                    final byte mWearing = Byte.parseByte(data[23]);
                    if (mWearing > -1 && mWearing != 21 && mWearing != 22) {
                    }
                } catch (final Exception ex4) {
                }
                try {
                    this.pointCongHienClan = Integer.parseInt(data[24]);
                    this.isOnlineToDay = (Byte.parseByte(data[25]) == 1);
                    this.rcvGifByLv = Byte.parseByte(data[26]);
                    this.rcvGifByHour = Byte.parseByte(data[27]);
                    this.rcvGifByMonth = Byte.parseByte(data[28]);
                    this.totalDayInMonth = Byte.parseByte(data[29]);
                    this.dateStartOnline = data[32];
                    this.lvHouse = Byte.parseByte(data[33]);
                    this.lvStore = Byte.parseByte(data[34]);
                    this.maxBag = Byte.parseByte(data[35]);
                    this.slotWearing = Byte.parseByte(data[36]);
                    this.nMoonCake2Exp = Short.parseShort(data[37]);
                    this.receiveLG = Byte.parseByte(data[38]);
                    this.myCountry = Byte.parseByte(data[39]);
                } catch (final Exception ex5) {
                }
                try {
                    this.setCountry();
                    if (this.myCountry >= 0) {
                        this.inCountry = this.myCountry;
                        this.session.sendMessage(MessageCreator.createMessageLocation(this.myCountry));
                    }
                } catch (final Exception ex6) {
                }
                try {
                    this.gif35 = Byte.parseByte(data[40]);
                } catch (final Exception ex7) {
                }
                try {
                    this.luongNap = Long.parseLong(data[41]);
                    this.luonglost = Long.parseLong(data[42]);
                    this.dayLogin = data[43].trim();
                    this.forbitChat = Byte.parseByte(data[44]);
                    this.usingSummons = Byte.parseByte(data[45]);
                    this.maxKill = Short.parseShort(data[46]);
                    this.honor = Integer.parseInt(data[47]);
                    this.xichtho_thienlyma = Byte.parseByte(data[48]);
                    this.married = Byte.parseByte(data[49]);
                    this.idWedding = Integer.parseInt(data[50]);
                    this.nameHusband_wife = data[51];
                    this.rcvGiftWedding = Byte.parseByte(data[52]);
                    this.passWord = data[53];
                    this.countQuestClan = Byte.parseByte(data[54]);
                    this.pointActiveQuest = Short.parseShort(data[55]);
                    this.idArena = Integer.parseInt(data[56]);
                    this.pArena = Integer.parseInt(data[57]);
                    this.idMainQuest = Short.parseShort(data[58]);
                    this.nBinhTra = Byte.parseByte(data[59]);
                    this.nHopMut = Byte.parseByte(data[60]);
                    this.gif83 = Byte.parseByte(data[61]);
                    this.ntangtoc = Byte.parseByte(data[62]);
                    this.subpk = Byte.parseByte(data[63]);
                    this.receiveQuestVulan = Byte.parseByte(data[64]);
                    this.timeUp5Attribute = Long.parseLong(data[65]);
                    this.typeTieu = Byte.parseByte(data[66]);
                    this.timeOutGame = Long.parseLong(data[67]);
                    this.region = Short.parseShort(data[68]);
                    this.lockLuong = Integer.parseInt(data[69]);
                    this.minuteExp = Integer.parseInt(data[70]);
                    if (this.minuteExp > 0) {
                        this.fullGoldTime = System.currentTimeMillis();
                        this.x2 = 2;
                    }
                    this.luot_van_tieu = Byte.parseByte(data[71]);
//                    if (this.luot_van_tieu >= 3) {
//                        this.luot_van_tieu = 0;
//                    }
                    this.buy_luot_van_tieu = Byte.parseByte(data[72]);
//                    if (this.buy_luot_van_tieu >= 1) {
//                        this.buy_luot_van_tieu = 0;
//                    }
                    this.cuop_tieu = Byte.parseByte(data[73]);
                    this.timeJoinClan = Long.parseLong(data[74]);
                    this.giveMoneyClan = Byte.parseByte(data[75]);
                    this.minuteSocola = Short.parseShort(data[76]);
                    this.receiveGiftEvent = Byte.parseByte(data[77]);
                    this.idTongKim = Short.parseShort(data[78]);
                    this.idTeamTongKim = Byte.parseByte(data[79]);
                    this.diemdoivePH = Integer.parseInt(data[80]);
                    this.winPH = Byte.parseByte(data[81]);
                    this.timeExistCharHire = Long.parseLong(data[82]);
                    this.classlinh = Byte.parseByte(data[83]);
                    this.lvlinhthue = Byte.parseByte(data[84]);
                    this.genderlinh = Byte.parseByte(data[85]);
                    this.lvlinh = Byte.parseByte(data[86]);
                    this.countnvVulan = Byte.parseByte(data[87]);
                    this.firstNapMoney = Byte.parseByte(data[88]);
                    this.timeUseTheMuaBan = Long.parseLong(data[89]);
                    this.banphaohoa = Integer.parseInt(data[90]);
                    this.idTongKim = -1;
                    this.x2Phaohoa = Integer.parseInt(data[91]);
                    this.dayCamChat = data[92].trim();
                    this.countTime = Byte.parseByte(data[93]);
                    this.receiveGiftOldChar = Byte.parseByte(data[94]);
                    this.buychoi = Byte.parseByte(data[95]);
                    this.buylongden = Byte.parseByte(data[96]);
                    this.xuxai = Long.parseLong(data[97]);
                    this.luongxai = Long.parseLong(data[98]);
                    this.vetangluong = Integer.parseInt(data[99]);
                    this.countMonsterKill = Integer.parseInt(data[100]);
                    this.hlt_buy = Short.parseShort(data[101]);
                    this.luongxainhanRuong = Integer.parseInt(data[102]);
                    this.idEffDanhHieu = Integer.parseInt(data[103]);
                    this.minuteOnline = Short.parseShort(data[104]);
                    this.timeInClan = Long.parseLong(data[105]);
                    this.nhomThidau = Byte.parseByte(data[106]);
                    this.vip = Byte.parseByte(data[107]);
                    this.diemNapVip = Integer.parseInt(data[108]);
                    this.diemxaiVip = Integer.parseInt(data[109]);
                    this.rcvInviteVip = (Integer.parseInt(data[110]) == 1);
                    this.isCaiTrang = (Integer.parseInt(data[111]));
                } catch (final Exception ex8) {
                }
                try {
                    this.idAutoSkill = Byte.parseByte(data[30]);
                    this.idAutoGetitem = Byte.parseByte(data[31]);
                } catch (final Exception ex9) {
                }
                try {
                    this.autoComeHome = Byte.parseByte(data[21]);
                } catch (final Exception ex10) {
                }
                if (this.autoComeHome > 1 || this.autoComeHome < 0) {
                    this.autoComeHome = 0;
                }
                if (TeamServer.server == 100) {
                    this.passWord = "";
                }
                if (this.minuteSocola > 0) {
                    try {
                        this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng hạt mầm của bạn còn lại " + this.minuteSocola + " phút"));
                    } catch (final IOException ex11) {
                    }
                }
            } catch (final Exception ex12) {
            }
        }
    }

    public void checkCreateCharHire() {
        try {
            if (this.timeExistCharHire > 0L) {
                if (System.currentTimeMillis() - this.timeExistCharHire < 0L) {
                    this.doCreateCharHire(this.classlinh, this.lvlinh, this.genderlinh, this.lvlinhthue);
                } else {
                    this.timeExistCharHire = 0L;
                    this.lvlinh = 0;
                    this.lvlinhthue = 0;
                    this.classlinh = 0;
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Hộ vệ của bạn đã hết hạn"));
                    Database.instance.saveOrtherLog("", this.charname, "linh thue het han:c= " + this.classlinh + ",g=" + this.genderlinh + ",lvl=" + this.lvlinhthue + ",lvcl=" + this.lvlinh, "lhethan");
                }
            }
        } catch (final Exception ex) {
        }
    }

    public boolean canUseTheMuaBan() {
        return this.isAdmin || (this.mapID != 17 && this.mapID != 25 && this.mapID != 111 && this.mapID != 118 && this.mapID != 201 && this.mapID != 202 && this.mapID != 203 && this.mapID != 204 && this.mapID != 205 && this.mapID != 207 && this.mapID != 225 && this.mapID != 226 && this.mapID != 0 && this.mapID != 301 && this.mapID != 302 && this.mapID != 303 && this.mapID != 304 && this.mapID != 206 && this.mapID != 208 && this.mapID != 209 && this.mapID != 210 && this.mapID != 70 && this.mapID != 1701 && this.mapID != 1702 && this.mapID != 1703 && this.mapID != 1704 && System.currentTimeMillis() - this.timeUseTheMuaBan >= 0L);
    }

    public void checkItemDuplicate() {
    }

    public void initInfoFromDB(final Vector<Item> charItem, final Vector<Date> lastlog) {
        try {
            this.timeOnlineSukien = System.currentTimeMillis();
            this.timeSendAlertQuangCao = System.currentTimeMillis() + 15000L;
            this.initWItem();
            String info = "";
            this.initPinfo();
            this.lastLog = split(this.lastLog.trim(), ".")[0].trim();
            if (this.charname.equals("chienthan")) {
                System.out.println("LASTLOG=" + split(this.lastLog.trim(), ".")[0].trim());
            }
            try {
                this.initOnlineReceiveGif(lastlog.get(0));
            } catch (final Exception ex) {
            }
            if (this.lastLog.compareTo("2010-01-01 00:00:00") == 0) {
                this.receiveGiftOldChar = 0;
            }
            if (this.idArena > -1 && ArenaMap.getArenaData(this) == null) {
                this.idArena = -1;
            }
            try {
                if (this.receiveLG != 64) {
                    this.char_quest = Database.instance.loadCharQuest(this.charDBID);
                }
                this.receiveLG = 64;
                if (this.char_quest != null) {
                    this.idMainQuest = (short) this.char_quest.id_quest;
                    if (this.idMainQuest >= Map.allMainQuest.size()) {
                        this.idMainQuest = (short) Map.allMainQuest.size();
                    }
                    final String itemget = new StringBuilder(String.valueOf(this.char_quest.nItem[0])).toString();
                    final String monskilled = new StringBuilder(String.valueOf(this.char_quest.nMonster[0])).toString();
                    final int finish = this.char_quest.finish;
                    final int state = this.char_quest.receive;
                    final String stateQ = String.valueOf(this.char_quest.id_quest) + "," + ((finish == 1) ? finish : ((state == 1) ? 2 : 0)) + ",0";
                    this.initQuest(itemget, monskilled, stateQ);
                    if (this.mainQuest.id_quest >= Map.allMainQuest.size()) {
                        this.mainQuest.id_quest = (short) (Map.allMainQuest.size() - 1);
                        this.mainQuest.state_quest = NewCharQuest.DONE;
                    }
                    Database.instance.addCharQuest(this.charDBID, this.getInfoQuestSave());
                    final InfoQuestTemplate infoQuest = this.mainQuest.getTemplate();
                    final int iditem = infoQuest.getItemGet()[0];
                    if (iditem < 32000) {
                        this.itemquest[iditem] = this.char_quest.nItem[0];
                    }
                    Database.instance.isCheckCharQuest(this.charDBID);
                } else {
                    if (!Map.openLog && this.lastLV <= 2 && this.idMainQuest > 8 && TeamServer.server == 4) {
                        this.idMainQuest = 0;
                        this.itemget = "";
                    }
                    this.initQuest(this.itemget, this.monskilled, this.finish);
                    if (this.itemget.equals("") && this.itemget.equals("") && this.itemget.equals("")) {
                        Database.instance.addCharQuest(this.charDBID, this.getInfoQuestSave());
                    }
                    final String[] data = split(this.itquest, ",");
                    for (int i = 0; i < data.length; ++i) {
                        this.itemquest[i] = Short.parseShort(data[i]);
                    }
                }
            } catch (final Exception ex2) {
            }
            this.itemget = "";
            this.monskilled = "";
            this.finish = "";
            this.itquest = "";
            try {
                if (this.dayLogin.compareTo("2013-05-10") < 0) {
                    try {
                        if (this.animalRide != null) {
                            this.rideHorse = Animal.rideHorse[this.animalRide.idImg];
                        }
                    } catch (final Exception ex3) {
                    }
                    try {
                        final String infoNl = "";
                        for (int i = 0; i < this.listGemitemLock.length; ++i) {
                            if (this.listGemitemLock[i] > 0) {
                                info = String.valueOf(info) + this.listGemitem[i] + " _ " + this.listGemitemLock[i] + " > ";
                                final int[] listGemitem = this.listGemitem;
                                final int n = i;
                                listGemitem[n] -= this.listGemitemLock[i];
                                if (this.listGemitem[i] < 0) {
                                    this.listGemitem[i] = 0;
                                }
                            }
                        }
                        if (!infoNl.equals("")) {
                            Database.instance.saveOrtherLog("", this.charname, infoNl, "tachnl");
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                if (this.rideHorse <= 0) {
                    if (this.animalRide != null) {
                        this.animalRide.place = 0;
                        this.animalRide = null;
                    }
                } else {
                    if (this.animalRide != null && Animal.rideHorse[this.animalRide.idImg] != this.rideHorse) {
                        this.animalRide.place = 0;
                        this.animalRide = null;
                        this.rideHorse = 0;
                    }
                    if (this.animalRide != null) {
                        this.idImgHorse = Animal.idImgPaint[this.animalRide.idImg];
                    } else if (this.rideHorse == 1) {
                        if (this.xichtho_thienlyma == 1) {
                            this.idImgHorse = 0;
                        } else if (this.xichtho_thienlyma == 2) {
                            this.idImgHorse = 1;
                        }
                    }
                }
            } catch (final Exception ex4) {
                ex4.printStackTrace();
            }
            if (this.xu < 0L) {
                this.xu = 0L;
            }
            if (TeamServer.isServerLienDau()) {
                this.isAdmin = false;
            }
            if (this.isAdmin && Map.isAcountAdmin(this.getSession().username)) {
                this.isAdmin = false;
            }
            if (this.charname.toLowerCase().equals("chienthan")) {
                this.isAdmin = true;
                this.luot_van_tieu = 3;
            }
            // todo sửa map player ở đây
            if (this.lvDetail.lv > 1 && (this.mapID == -1 || this.mapID == 105 || (this.mapID >= 30 && this.mapID <= 39))) {
                this.mapID = 0;
            }

            this.mapID = Checker.resetMapId(this.mapID);

            if (this.mapID == -1 || this.mapID == 105) {
                this.mapID = 105;
                this.x = 176;
                this.y = 272;
            } else {
                final int[][] mapstart = {{70, 1701}, {0, 301}, {80, 1901}};
                if (this.myCountry > -1) {
                    if (this.mapID == 228 || this.mapID == 227 || this.mapID == 42 || this.mapID == 41 || this.mapID == 40 || this.mapID == 17 || this.mapID == 25 || this.mapID == 111 || this.mapID == 118 || this.mapID == 201 || this.mapID == 202 || this.mapID == 203 || this.mapID == 204 || this.mapID == 205 || this.mapID == 207 || this.mapID == 225 || this.mapID == 226 || RealController.mapList.get(this.mapID) == null || this.mapID == 0 || this.mapID == 301 || this.mapID == 302 || this.mapID == 303 || this.mapID == 304 || this.mapID == 206 || this.mapID == 208 || this.mapID == 209 || this.mapID == 210 || this.mapID == 70 || this.mapID == 1701 || this.mapID == 1702 || this.mapID == 1703 || this.mapID == 1704 || this.mapID == 203 || this.mapID == 4361 || this.mapID == 4362 || this.mapID == 4363 || this.mapID == 4364 || this.mapID == 4365 || this.mapID == 4366) {
                        this.mapID = mapstart[this.myCountry][Map.r.nextInt(mapstart[this.myCountry].length)];
                        final int[][] pos = {{10, 23, 14, 38, 30, 35, 21, 49}, {22, 41, 27, 32, 8, 30, 18, 11}, {10, 23, 14, 38, 30, 35, 21, 49}};
                        final int index = Map.r.nextInt(4);
                        this.x = pos[this.myCountry][index * 2] * 16 + Map.r.nextInt() % 16;
                        this.y = pos[this.myCountry][index * 2 + 1] * 16 + Map.r.nextInt() % 16;
                        this.region = 0;
                    }
                } else {
                    final int[] mapstartDun = {117, 2641};
                    final int ran = Map.r.nextInt(mapstartDun.length);
                    this.mapID = mapstartDun[ran];
                    this.x = 624 + Database.r.nextInt() % 5;
                    this.y = 512 + Database.r.nextInt() % 5;
                }
            }
            int count = 0;
            if (this.dbSkill != null) {
                final String[] data2 = split(this.dbSkill, ",");
                for (int j = 0; j < data2.length; ++j) {
                    try {
                        if (j < 15) {
                            this.skill[j] = Byte.parseByte(data2[j]);
                            count += this.skill[j];
                        } else if (j >= 15 && j < 35) {
                            this.skillClan[j - 15] = Short.parseShort(data2[j]);
                        } else {
                            this.skillLvClan[j - 35] = Byte.parseByte(data2[j]);
                        }
                    } catch (final Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (this.lvDetail.lv >= 3 && this.skill[0] == 0) {
                    this.skill[0] = 1;
                }
            }
            if (this.killer > 0) {
                this.isKiller = true;
            }
            final NewClan c = Map.getClaninfoByID(this.idClan);
            if (c != null) {
                if (c != null) {
                    for (int j = 0; j < c.timeUseSkill.length; ++j) {
                        this.skillClan[j] = c.skillClan[j];
                        this.timeUseSkillClan[j] = c.timeUseSkill[j];
                        this.skillLvClan[j] = c.skillLvClan[j];
                    }
                }
                if (!c.master.toLowerCase().equals(this.charname.toLowerCase())) {
                    if (this.rankClan <= 0) {
                        this.rankClan = 3;
                    }
                } else {
                    this.rankClan = 0;
                    if (Map.idClanTown[this.myCountry] == this.idClan) {
                        Char.gov[this.myCountry].king = this.charname;
                        Database.instance.saveEvent(Map.event.getInfo());
                    }
                }
            } else {
                this.rankClan = 3;
                this.idClan = -1;
            }
            if (this.dbPotion != null) {
                final String[] data3 = split(this.dbPotion, ",");
                for (int k = 0; k < data3.length; ++k) {
                    try {
                        if (k == 0) {
                            if (this.xu < 0L) {
                                this.xu = 0L;
                            }
                        } else {
                            this.potions[k] = Integer.parseInt(data3[k]);
                            if (this.potions[k] < 0) {
                                this.potions[k] = 0;
                            }
                        }
                    } catch (final Exception ex5) {
                    }
                }
                this.potions[36] = 0;
                this.potions[85] = 0;
                this.potions[87] = 0;
                this.potions[62] = 0;
            }
            if (this.married == 0 && this.idWedding == -1) {
                this.potions[92] = 0;
                this.potions[88] = 0;
                this.potions[89] = 0;
                this.potions[90] = 0;
                this.potions[99] = 0;
                this.potions[98] = 0;
            }
            if (this.dayLogin.compareTo("2020-07-02") < 0 || this.dayLogin.equals("")) {
                this.potions[70] = 0;
            }
            if (getDayOpen(0L).equals("2023-07-03") && this.dayLogin.compareTo("2023-07-03") < 0) {
                if (!Map.openLog) {
                    this.doAddGiftSn();
                }
            } else if (getDayOpen(0L).equals("2023-07-04") && this.dayLogin.compareTo("2023-07-04") < 0) {
                if (!Map.openLog) {
                    this.doAddGiftSn();
                }
            } else if (getDayOpen(0L).equals("2023-07-05") && this.dayLogin.compareTo("2023-07-05") < 0 && !Map.openLog) {
                this.doAddGiftSn();
            }
            if (this.subpk == 0) {
                this.killer -= 200;
                if (this.killer < 0) {
                    this.killer = 0;
                    this.isKiller = false;
                }
                this.subpk = 1;
            }
            if (this.pointActiveQuest > 300) {
                this.pointActiveQuest = 300;
            }
            if (this.nMoonCake2Exp != 15503) {
                this.luongNap = 0L;
                this.luonglost = 0L;
                this.flower = 0;
                this.nMoonCake2Exp = 15503;
                this.potions[75] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.potions[122] = 0;
                this.potions[117] = 0;
                if (this.lvDetail.lv >= 34) {
                    this.potions[7] = 6;
                    this.ntangtoc = 6;
                }
            }
            if (this.gif83 != 14) {
                this.luongNap = 0L;
                this.luonglost = 0L;
                this.gif83 = 14;
                this.potions[80] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[75] = 0;
                this.potions[35] = 0;
                this.potions[82] = 0;
                this.potions[97] = 0;
                this.potions[116] = 0;
            }
            if (this.lastLog.compareTo("2015-03-07 16:25:00") < 0) {
                this.flower = 0;
            }
            if (this.lastLog.compareTo("2014-12-24 16:30:00") < 0) {
                this.potions[117] = 0;
            }
            if (this.lastLog.compareTo("2014-12-24 16:30:00") < 0) {
                this.potions[117] = 0;
            }
            if (this.lastLog.compareTo("2015-08-26 10:30:00") < 0) {
                this.potions[118] = 0;
                this.luongNap = 0L;
            }
            if (this.lastLog.compareTo("2015-10-30 17:10:00") < 0) {
                this.potions[72] = 0;
                this.potions[71] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[120] = 0;
                this.potions[83] = 0;
                this.potions[118] = 0;
                this.potions[121] = 0;
                this.potions[125] = 0;
                this.potions[122] = 0;
                this.potions[117] = 0;
                this.potions[105] = 0;
                this.potions[119] = 0;
                this.potions[124] = 0;
                this.potions[104] = 0;
                this.luongNap = 0L;
            }
            if (this.lastLog.compareTo("2016-08-09 10:10:00") < 0) {
                this.potions[72] = 0;
                this.potions[71] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
            }
            if (this.lastLog.compareTo("2016-08-10 11:05:00") < 0) {
                final int[] potions = this.potions;
                final int n2 = 120;
                potions[n2] += this.potions[125];
                this.potions[125] = 0;
            }
            if (this.lastLog.compareTo("2016-04-15 10:10:00") < 0) {
                this.potions[72] = 0;
                this.potions[71] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[120] = 0;
                this.potions[83] = 0;
                this.potions[104] = 0;
                this.potions[125] = 0;
                this.potions[105] = 0;
                this.potions[119] = 0;
                this.luongNap = 0L;
                this.nBinhTra = 20;
            }
            if (this.lastLog.compareTo("2016-02-04 16:22:00") < 0) {
                this.potions[74] = 0;
                this.potions[120] = 0;
                this.potions[83] = 0;
                this.potions[121] = 0;
                this.potions[75] = 0;
                this.potions[72] = 0;
                this.potions[71] = 0;
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[119] = 0;
                this.potions[125] = 0;
                this.potions[104] = 0;
                this.potions[122] = 0;
                this.potions[117] = 0;
                this.potions[124] = 0;
                this.potions[81] = 0;
                this.luongNap = 0L;
            }
            if (this.lastLog.compareTo("2015-12-19 10:15:00") < 0) {
                this.potions[71] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[120] = 0;
                this.potions[83] = 0;
                this.potions[121] = 0;
                this.potions[125] = 0;
                this.potions[105] = 0;
                this.potions[119] = 0;
                this.nBinhTra = 10;
                this.luongNap = 0L;
            }
            if (this.lastLog.compareTo("2015-09-26 10:15:00") < 0) {
                this.potions[72] = 0;
                this.potions[71] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[120] = 0;
                this.potions[83] = 0;
                this.potions[125] = 0;
                this.potions[122] = 0;
                this.potions[117] = 0;
                this.potions[105] = 0;
                this.potions[119] = 0;
                this.potions[124] = 0;
                this.potions[104] = 0;
                this.potions[121] = 0;
                this.flower = 0;
                this.luongNap = 0L;
            }
            if (this.lastLog.compareTo("2015-04-23 16:05:00") < 0) {
                this.potions[121] = 0;
                this.potions[122] = 0;
                this.potions[119] = 0;
                this.potions[125] = 0;
                this.potions[117] = 0;
                this.potions[120] = 0;
                this.luongNap = 0L;
            }
            if (this.lastLog.compareTo("2016-09-09 16:05:00") < 0) {
                this.potions[72] = 0;
                this.potions[71] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[120] = 0;
                this.potions[83] = 0;
                this.potions[104] = 0;
                this.potions[117] = 0;
                this.potions[105] = 0;
                this.potions[119] = 0;
                this.potions[124] = 0;
                this.potions[122] = 0;
                this.potions[142] = 0;
                this.luongNap = 0L;
                this.nBinhTra = 20;
            }
            if (this.lastLog.compareTo("2017-09-05 14:00:00") < 0 && !Map.openLog && TeamServer.server == 3) {
                try {
                    for (int l = 0; l < Char.nameTop100DaiViet.length; ++l) {
                        if (this.charname.toLowerCase().equals(Char.nameTop100DaiViet[l].toLowerCase())) {
                            this.doAddSpecialItem(0, 3);
                            final int slsc = 2;
                            final int slcc = 2;
                            for (int m = 0; m < GemTemplate.ID_MATERIAL_LOW[5].length; ++m) {
                                this.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[5][m], slsc, false);
                                this.doAddGemItem(GemTemplate.ID_MATERIAL_HIGHT[5][m], slcc, false);
                            }
                            Database.instance.saveOrtherLog("", this.charname, "nhan qua top 100 3 ve gio vang, nlcc,sc moi loai 2 vien", "top100");
                            break;
                        }
                    }
                } catch (final Exception ex6) {
                }
            }
            this.charname.equals("chienthan");
            if (this.lastLog.compareTo("2016-10-27 16:15:00") < 0) {
                this.potions[71] = 0;
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[120] = 0;
                this.potions[77] = 0;
                this.potions[83] = 0;
                this.potions[105] = 0;
                this.potions[104] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[122] = 0;
                this.potions[124] = 0;
                this.potions[74] = 0;
                this.potions[125] = 0;
                this.potions[121] = 0;
                this.luongNap = 0L;
            }
            if (this.lastLog.compareTo("2016-12-22 15:15:00") < 0) {
                this.potions[71] = 0;
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[83] = 0;
                this.potions[105] = 0;
                this.potions[104] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[125] = 0;
                this.nBinhTra = 10;
                final int[] id = {72, 73, 74};
                this.nHopMut = (byte) id[Map.r.nextInt(id.length)];
                this.luongNap = 0L;
            }
            if (this.lastLog.compareTo("2017-01-12 15:15:00") < 0) {
                this.potions[71] = 0;
            }
            if (this.lastLog.compareTo("2017-01-24 15:15:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[104] = 0;
                this.potions[83] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[125] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.banphaohoa = 0;
            }
            if (this.lastLog.compareTo("2017-04-29 10:15:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[104] = 0;
                this.potions[83] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[125] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.receiveQuestVulan = 0;
                this.countnvVulan = 0;
            }
            if (this.lastLog.compareTo("2017-10-03 15:10:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[104] = 0;
                this.potions[83] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[122] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.potions[124] = 0;
                this.banphaohoa = 0;
            }
            if (this.lastLog.compareTo("2017-12-20 15:05:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[83] = 0;
                this.potions[105] = 0;
                this.potions[104] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[122] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.potions[124] = 0;
                this.banphaohoa = 0;
                this.nBinhTra = 10;
                this.luongNap = 0L;
                final int[] id = {73, 77};
                this.nHopMut = (byte) id[Map.r.nextInt(id.length)];
            }
            if (isSuKienNoel() && this.nHopMut == -1) {
                final int[] id = {73, 77};
                this.nHopMut = (byte) id[Map.r.nextInt(id.length)];
            }
            if (this.lastLog.compareTo("2018-09-21 16:30:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[122] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.potions[124] = 0;
                this.potions[125] = 0;
                this.banphaohoa = 0;
                this.nBinhTra = 0;
                this.luongNap = 0L;
                this.nHopMut = 20;
                this.receiveGiftEvent = 0;
            }
            if (this.lastLog.compareTo("2018-02-09 16:00:00") < 0) {
                if (this.banphaohoa >= 10 || this.potions[105] > 10 || this.potions[142] > 100 || this.potions[119] > 100) {
                    Database.instance.saveOrtherLog("", this.charname, "tich thu " + this.banphaohoa + ",phao: " + this.potions[105] + ", thuoc: " + this.potions[119] + ", giay: " + this.potions[142], "tichthu");
                    this.potions[72] = 0;
                    this.potions[73] = 0;
                    this.potions[74] = 0;
                    this.potions[77] = 0;
                    this.potions[105] = 0;
                    this.potions[142] = 0;
                    this.potions[119] = 0;
                    this.potions[117] = 0;
                    this.potions[122] = 0;
                    this.potions[120] = 0;
                    this.banphaohoa = 0;
                    this.nBinhTra = 10;
                    this.luongNap = 0L;
                }
                this.potions[104] = 0;
                this.potions[83] = 0;
                this.potions[121] = 0;
                this.potions[124] = 0;
                if (this.potions[117] > 100 || this.potions[122] > 100 || this.potions[120] > 100) {
                    this.potions[119] = 0;
                    this.potions[117] = 0;
                    this.potions[122] = 0;
                }
                if (this.potions[121] > 10) {
                    this.potions[121] = 0;
                }
            }
            if (this.lastLog.compareTo("2018-02-12 10:14:00") < 0) {
                this.countTime = 0;
            }
            if (this.lastLog.compareTo("2018-05-30 14:26:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[104] = 0;
                this.potions[83] = 0;
                this.receiveQuestVulan = 0;
                this.countnvVulan = 0;
            }
            if (this.lastLog.compareTo("2018-07-02 15:15:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.banphaohoa = 0;
            }
            if (this.lastLog.compareTo("2019-10-30 14:10:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[142] = 0;
                this.winPH = 0;
            }
            this.syncVipByLevel(false);
            if (!this.dayLogin.equals(getDayOpen(0L))) {
                this.countnvVulan = 0;
                this.usingSummons = 0;
                this.maxKill = 0;
                this.countQuestClan = 0;
                this.countMonsterKill = 0;
                this.nHopMut = 100;
                this.nBinhTra = 100;
                this.timeXP = 0;
                this.pointActiveQuest += 100;
                this.receiveGiftEvent = 0;
                this.flower = 0;
                this.giveMoneyClan = 1;
                if (this.subQuest.id_quest > -1) {
                    this.subQuest.reset();
                    this.subQuest.id_quest = -1;
                    MessageCreator.createMsgCharQuest(this, 0);
                }
                if (this.lvDetail.lv >= 34) {
                    if (this.ntangtoc == 6) {
                        if (this.potions[7] < 6) {
                            final int[] potions2 = this.potions;
                            final int n3 = 7;
                            potions2[n3] += 6;
                            Database.instance.saveOrtherLog("", this.charname, "tang 6 binh tang  toc", "btt");
                        }
                    } else if (this.ntangtoc < 6) {
                        final int[] potions3 = this.potions;
                        final int n4 = 7;
                        potions3[n4] += 6 - this.ntangtoc;
                        Database.instance.saveOrtherLog("", this.charname, "tang " + (6 - this.ntangtoc) + " binh tang  toc", "btt");
                        this.ntangtoc = 6;
                    }
                }
                this.luot_van_tieu = 3;
//                if (this.luot_van_tieu >= 3) {
//                    this.luot_van_tieu = 0;
//                }
                this.buy_luot_van_tieu = (byte) (3 + this.vip);
//                this.buy_luot_van_tieu = 0;
                this.cuop_tieu = 3;
                this.countTime = 0;
                if (this.lvDetail.lv >= 10) {
                    try {
                        this.receiveGiftDay();
                    } catch (final Exception ex7) {
                    }
                }
                if (this.vip > 0 && this.vip <= 3) {
                    final int[] potions4 = this.potions;
                    final int n5 = 100;
                    potions4[n5] += Char.soluong_loa_vip[this.vip];
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + this.vip + " loa."));
                    Database.instance.saveOrtherLog("", this.charname, "nhan loa vip", "loavip");
                }
                if (isDemNoel()) {
                    final int lv = this.lvDetail.lv;
                }
                if (isDemHaloween() && this.lvDetail.lv >= 40 && this.receiveGiftOldChar == 1) {
                    this.potions[141] = 1;
                    Database.instance.saveOrtherLog("", this.charname, "nhan qua tang haloween", "hopquahlw");
                }
                if (isDemTrungThu() && this.lvDetail.lv >= 20) {
                    this.potions[125] = 1;
                    Database.instance.saveOrtherLog("", this.charname, "nhan qua tang trung thu", "hopquatrungthu");
                }
            }
          
           
            if (this.lastLog.compareTo("2019-03-08 10:46:00") < 0) {
                this.potions[72] = 0;
                if (this.isCharNu() && getDayOpen(0L).equals("2019-03-08")) {
                    Item newItem = null;
                    final int[] idchoi = {617, 618, 619};
                    String infotang = "";
                    for (int m = 0; m < idchoi.length; ++m) {
                        final int idtemp = idchoi[m];
                        newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idtemp), false, 0, 0, idtemp);
                        newItem.id = this.getIDItem();
                        newItem.owner = this.charDBID;
                        newItem.level = newItem.getTemplate().level;
                        newItem.identify = this.charDBID;
                        newItem.clazz = this.charClass;
                        newItem.createAttChoi();
                        newItem.dateCreate = getDayOpen(0L);
                        newItem.timeLoan = System.currentTimeMillis();
                        newItem.minuteExist = 4320;
                        this.iItems.add(newItem);
                        this.sendMessage(MessageCreator.createServerAlertMessage("Nhận được " + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút", ""));
                        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                        infotang = String.valueOf(infotang) + ">" + newItem.getName() + "_" + newItem.getDBInfo() + "|" + newItem.getAttribute() + "_" + newItem.level + " thoi han " + newItem.minuteExist;
                    }
                    this.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã nhận được Chổi nhân ngày 8/3. Chúc bạn 8/3 thật vui vẻ và hạnh phúc.", ""));
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                    Database.instance.saveOrtherLog("", this.charname, infotang, "qua83");
                }
            }
            if (this.lastLog.compareTo("2018-12-21 16:35:00") < 0) {
                this.potions[72] = 0;
                this.potions[73] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[117] = 0;
                this.potions[122] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.potions[124] = 0;
                this.potions[125] = 0;
                this.banphaohoa = 0;
                this.nBinhTra = 10;
                this.luongNap = 0L;
            }
        
            if (this.lastLog.compareTo("2020-01-17 16:20:00") < 0) {
                this.potions[72] = 0;
                this.potions[74] = 0;
                this.potions[77] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.potions[125] = 0;
                this.potions[122] = 0;
                this.potions[117] = 0;
                this.potions[105] = 0;
                this.potions[142] = 0;
                this.potions[119] = 0;
                this.potions[124] = 0;
                this.luongNap = 0L;
                this.banphaohoa = 0;
                this.nBinhTra = 100;
            }
            if ((this.lastLog.compareTo("2020-03-26 10:40:00") < 0 && Map.openLog) || (this.lastLog.compareTo("2020-03-26 11:19:00") < 0 && !Map.openLog)) {
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[142] = 0;
                this.potions[117] = 0;
                this.potions[121] = 0;
                this.luongxai = 0L;
            }
            if (this.lastLog.compareTo("2020-02-25 17:40:00") < 0) {
                this.potions[72] = 0;
                this.potions[74] = 0;
            }
            if (this.lastLog.compareTo("2020-04-29 15:43:00") < 0) {
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[142] = 0;
            }
            if (this.lastLog.compareTo(Map.openLog ? "2020-08-06 15:10:00" : "2020-08-06 16:18:00") < 0) {
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[118] = 0;
                this.luongxai = 0L;
                this.luongxainhanRuong = 0L;
                this.receiveQuestVulan = 0;
                this.countnvVulan = 0;
            }
        
            if (isSuKienNoel() && this.nHopMut == -1) {
                final int[] id = {73, 77};
                this.nHopMut = (byte) id[Map.r.nextInt(id.length)];
            }
            this.canReceiveGiftOffline = Database.instance.checkAddGiftEventGame(this.charname);
            if (!Map.openLog && this.lastLog.compareTo("2016-12-29 10:15:00") < 0) {
                Database.instance.traGemNgude(this);
            }
            if (this.lastLog.compareTo("2017-10-20 10:10:00") < 0 && getDayOpen(0L).equals("2017-10-20") && this.isCharNu()) {
                final int[] potions6 = this.potions;
                final int n7 = 122;
                ++potions6[n7];
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được hộp quà nhân ngày 20-10"));
                Database.instance.saveOrtherLog("", this.charname, "nhan qua trung thu khi choi qua dem", "nhanhopqua2010");
            }
            if (this.lastLog.compareTo("2020-04-09 11:00:00") < 0) {
                this.potions[119] = 0;
                this.potions[120] = 0;
                this.luongxai = 250L;
            }
            if (this.lastLog.compareTo("2020-09-22 10:15:00") < 0) {
                this.potions[77] = 0;
                this.potions[105] = 0;
                this.potions[117] = 0;
                this.potions[142] = 0;
                this.potions[120] = 0;
                this.potions[121] = 0;
                this.potions[122] = 0;
                this.potions[124] = 0;
                this.potions[125] = 0;
                this.potions[133] = 0;
                this.potions[134] = 0;
                this.potions[135] = 0;
                this.potions[136] = 0;
                this.luongNap = 0L;
                this.banphaohoa = 0;
                this.nBinhTra = 100;
                this.nHopMut = 100;
            }
            if (this.lastLog.compareTo("2020-09-23 16:28:00") < 0 && this.getLuong() <= 0 && this.potions[135] > 1) {
                Database.instance.saveOrtherLog("", this.charname, "tich thu " + this.potions[135] + " hat mam", "thuhatmam");
                this.potions[135] = 0;
            }
            if (this.lastLog.compareTo("2020-07-01 14:05:00") < 0) {
                this.potions[73] = 0;
                for (int j = 0; j < GemTemplate.ID_NGOC_RONG.length; ++j) {
                    try {
                        this.delGem(GemTemplate.ID_NGOC_RONG[j], this.listGemitem[GemTemplate.ID_NGOC_RONG[j]], false);
                        this.delGem(GemTemplate.ID_NGOC_RONG[j], this.listGemitemLock[GemTemplate.ID_NGOC_RONG[j]], true);
                    } catch (final Exception ex8) {
                    }
                }
            }
            if (this.lastLog.compareTo("2020-10-22 10:00:00") < 0) {
                this.potions[115] = 0;
            }
            if (this.lastLog.compareTo("2022-10-25 00:00:00") >= 0) {
                this.potions[137] = 0;
                this.potions[138] = 0;
                this.potions[139] = 0;
                this.potions[140] = 0;
                this.potions[141] = 0;
                this.potions[143] = 0;
                this.potions[144] = 0;
                this.potions[118] = 0;
                this.luongxainhanRuong = 0L;
            }
            if (this.lastLog.compareTo("2020-12-17 10:45:00") < 0) {
                for (int j = 0; j < GemTemplate.ID_NGOC_RONG.length; ++j) {
                    try {
                        this.delGem(GemTemplate.ID_NGOC_RONG[j], this.listGemitem[GemTemplate.ID_NGOC_RONG[j]], false);
                        this.delGem(GemTemplate.ID_NGOC_RONG[j], this.listGemitemLock[GemTemplate.ID_NGOC_RONG[j]], true);
                    } catch (final Exception ex9) {
                    }
                }
                this.potions[146] = 0;
                this.potions[145] = 0;
                this.potions[147] = 0;
            }
            if (this.lastLog.compareTo("2021-02-02 15:30:00") < 0) {
                this.potions[122] = 0;
                this.potions[155] = 0;
                this.potions[149] = 0;
                this.potions[150] = 0;
                this.potions[148] = 0;
                this.potions[154] = 0;
                this.potions[153] = 0;
                this.potions[151] = 0;
                this.potions[152] = 0;
                this.luongNap = 0L;
                this.luongxai = 0L;
                this.luongxainhanRuong = 0L;
                this.diemNapVip = 0;
                this.diemxaiVip = 0;
            }
            if (this.lastLog.compareTo("2020-10-28 10:50:00") < 0) {
                final int luongdiem = Database.instance.getDiemLuongXaiNhanDiem(this.charname);
                if (this.luongxainhanRuong < luongdiem) {
                    this.luongxainhanRuong = luongdiem;
                }
            }
            if (!TeamServer.isServerHoaLu() && this.lastLog.compareTo("2020-04-14 16:42:00") < 0) {
                this.potions[119] = 0;
                this.potions[120] = 0;
            }
          
            Database.instance.getDiemNapXaiLuong(this);
            Database.instance.getCharDanhHieu(this);
            this.dayLogin = getDayOpen(0L);
            if (this.dbBasic != null) {
                final String[] data3 = split(this.dbBasic, ",");
                this.strength = (short) Map.abs(Short.parseShort(data3[0]));
                this.agitity = (short) Map.abs(Short.parseShort(data3[1]));
                this.spirit = (short) Map.abs(Short.parseShort(data3[2]));
                this.health = (short) Map.abs(Short.parseShort(data3[3]));
                this.luck = (short) Map.abs(Short.parseShort(data3[4]));
                this.basepoint = (short) Map.abs(Short.parseShort(data3[5]));
                this.skillpoint = (short) Map.abs(Short.parseShort(data3[6]));
            }
            if (!this.charname.toLowerCase().equals("chienthan")) {
                final int allBasicPoint = this.lvDetail.getBasicPoint(this.lastLV) + (this.lastLV - 1) * 5;
                final int tempSt = this.strength - Database.basicPoint[this.charClass][0];
                final int tempAgitity = this.agitity - Database.basicPoint[this.charClass][1];
                final int tempSpirit = this.spirit - Database.basicPoint[this.charClass][2];
                final int tempHealth = this.health - Database.basicPoint[this.charClass][3];
                final int tempLuck = this.luck - Database.basicPoint[this.charClass][4];
                final int tong = tempAgitity + tempHealth + tempLuck + tempSpirit + tempSt;
                if (tong > allBasicPoint || this.basepoint > allBasicPoint) {
                    this.basepoint = (short) (allBasicPoint - (this.lastLV - 1) * 5);
                    this.skillpoint = (short) (this.lastLV - 1);
                    this.hp = 100;
                    this.mp = 100;
                    this.strength = (short) (Database.basicPoint[this.charClass][0] + (this.lastLV - 1));
                    this.agitity = (short) (Database.basicPoint[this.charClass][1] + (this.lastLV - 1));
                    this.spirit = (short) (Database.basicPoint[this.charClass][2] + (this.lastLV - 1));
                    this.health = (short) (Database.basicPoint[this.charClass][3] + (this.lastLV - 1));
                    this.luck = (short) (Database.basicPoint[this.charClass][4] + (this.lastLV - 1));
                    final int firstSkill = this.skill[0];
                    for (int i2 = 0; i2 < this.skill.length; ++i2) {
                        if (this.skill[i2] >= 0) {
                            this.skill[i2] = 0;
                        }
                    }
                    if (firstSkill > 0 || this.lvDetail.lv >= 3) {
                        this.skill[0] = 1;
                    }
                }
                if (this.skillpoint < 0) {
                    this.skillpoint = 0;
                }
                if (this.basepoint < 0) {
                    this.basepoint = 0;
                }
            }
            if (this.fullGoldTime != 0L) {
                this.x2 = 2;
            } else if (this.halfGoldTime != 0L) {
                this.x2 = 1;
            }
            if (this.idClan == -1) {
                this.skillClan = new short[20];
            }
            if (count + this.skillpoint > this.lastLV && !this.isAdmin) {
                for (int j = 0; j < this.skill.length; ++j) {
                    if (this.skill[j] > 0) {
                        this.skill[j] = 0;
                    }
                }
                if (this.lastLV >= 3 && this.skill[0] == 0) {
                    this.skill[0] = 1;
                }
                this.skillpoint = (short) (this.lastLV - 1);
                if (this.skillpoint < 0) {
                    this.skillpoint = 0;
                }
            }
            final int openGif = 0;
            final String logError = "";
            this.setitemInvenBag(0);
            this.setitemInvenBag(1);
            this.setItemWearing();
            this.setItemTuiDo(this.tuido);
            this.invent = "";
            this.equip = "";
            this.bag = "";
            this.tuido = "";
            int iItemCount = 0;
            final int wItemCount = 0;
            final int ao = 0;
            final int quan = 0;
            final int gang = 0;
            final int non = 0;
            final int vukhi = 0;
            final int nhan = 0;
            final int daychuyen = 0;
            final int giay = 0;
            final int ngoc = 0;
            final int cuoc = 0;
            final int aochoang = 0;
            final int ao2 = 0;
            final int quan2 = 0;
            final int gang2 = 0;
            final int non2 = 0;
            final int vukhi2 = 0;
            final int nhan2 = 0;
            final int daychuyen2 = 0;
            final int giay2 = 0;
            final int ngoc2 = 0;
            final int cuoc2 = 0;
            final int aochoang2 = 0;
            int nAnimalItemDel = openGif * 10;
            final int ring = 0;
            final int ring2 = 0;
            int posNhan = -1;
            if (charItem != null) {
                for (int i3 = 0; i3 < charItem.size(); ++i3) {
                    final Item item = charItem.get(i3);
                    try {
                        item.initInfoFromDB();
                        if (item.getMinuteLoan(false) <= 0 && item.minuteExist > 0) {
                            try {
                                this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Thời gian sử dụng " + item.getTemplate().name + " đã hết"));
                                Database.instance.deleteItem(item.dbid);
                                Database.instance.saveOrtherLog("tob_log_other_item", this.charname, String.valueOf(item.getTemplate().name) + "|" + item.getDBInfo() + "|" + item.getAttribute(), "timeout");
                            } catch (final Exception ex10) {
                            }
                        } else if (item.owner == this.charDBID) {
                            if (item.identify == 0) {
                                item.identify = this.charDBID;
                            }
                            if (item.dbAttribute != null) {
                                final String[] data4 = split(item.dbAttribute, ",");
                                for (byte j2 = 0; j2 < data4.length; ++j2) {
                                    try {
                                        if (j2 < 33) {
                                            item.atb[j2] = Short.parseShort(data4[j2].trim());
                                        } else if (j2 < 43) {
                                            item.newAtb[j2 - 33] = Byte.parseByte(data4[j2].trim());
                                        } else if (j2 < 58) {
                                            item.addMoreLevelSkill[j2 - 43] = Byte.parseByte(data4[j2].trim());
                                        } else if (j2 < 61) {
                                            item.lockAtb[j2 - 58] = Byte.parseByte(data4[j2].trim());
                                        } else {
                                            item.otherAtt[j2 - 61] = Short.parseShort(data4[j2].trim());
                                        }
                                    } catch (final Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                try {
                                    if (item.resetItem != 5000 || (Map.isWearingAnimal(item.getTemplate().type) && !item.isHaveAttLock())) {
                                        if (Map.isWearingAnimal(item.getTemplate().type) && item.colorName == 1) {
                                            switch (item.hangItem) {
                                                case 1: {
                                                    item.lockItemAnimal(new byte[]{0, 0, 0, 0, 0, 1});
                                                    break;
                                                }
                                                case 2: {
                                                    item.lockItemAnimal(new byte[]{0, 0, 0, 0, 1, 0});
                                                    break;
                                                }
                                                case 3: {
                                                    item.lockItemAnimal(new byte[]{0, 0, 0, 1, 0, 0});
                                                    break;
                                                }
                                                case 4: {
                                                    item.lockItemAnimal(new byte[]{0, 0, 1, 0, 0, 0});
                                                    break;
                                                }
                                                case 5: {
                                                    final Item item2 = item;
                                                    final byte[] material = new byte[6];
                                                    material[1] = 1;
                                                    item2.lockItemAnimal(material);
                                                    break;
                                                }
                                            }
                                        }
                                        item.resetItem = 5000;
                                    }
                                } catch (final Exception ex11) {
                                }
                                item.setAnotherSkill();
                            }
                            if (item.mDurable == -1) {
                                item.mDurable = (short) (item.durable * 10);
                            }
                            if (item.getTemplate().type == 12 && item.atb[5] == 0) {
                                item.atb[5] = item.getTemplate().atb[5];
                                for (int m2 = 0; m2 < item.plus; ++m2) {
                                    final short[] atb = item.atb;
                                    final int n8 = 5;
                                    ++atb[n8];
                                }
                            }
                            if (item.getTemplate().type == 13) {
                                if (this.itemAx == null) {
                                    if (item.place == 1) {
                                        this.itemAx = item;
                                    }
                                } else if (item.place == 1) {
                                    item.place = 0;
                                }
                            }
                            if (nAnimalItemDel > 0 && Map.isWearingAnimal(item.getTemplate().type) && item.dateCreate.equals("2013-04-16") && item.colorName != 1) {
                                Database.instance.deleteItem(item.dbid);
                                --nAnimalItemDel;
                            } else {
                                switch (item.place) {
                                    case 0: {
                                        ++iItemCount;
                                        item.id = this.getIDItem();
                                        this.iItems.add(item);
                                        break;
                                    }
                                    case 1: {
                                        if (wItemCount >= 20) {
                                            break;
                                        }
                                        item.id = this.getIDItem();
                                        final int t = item.getTemplate().type;
                                        if (Map.isWearingAnimal(t)) {
                                            this.awItems.add(item);
                                            break;
                                        }
                                        boolean vk = false;
                                        if (t == 6 || t == 7 || t == 4 || t == 5 || t == 3) {
                                            vk = true;
                                        }
                                        if (item.clazz != this.charClass && item.getTemplate().type != 13 && item.getTemplate().type != 19) {
                                            item.place = 0;
                                            this.iItems.add(item);
                                            break;
                                        }
                                        if (item.getTemplate().gender != 0 && this.gender != 0 && item.getTemplate().type != 13 && item.getTemplate().type != 19 && item.getTemplate().gender != this.gender) {
                                            break;
                                        }
                                        if (item.level > this.lastLV) {
                                            break;
                                        }
                                        if (t == 8) {
                                            if (posNhan == -1) {
                                                posNhan = item.pos;
                                            } else if (posNhan == item.pos) {
                                                if (item.pos == 0) {
                                                    posNhan = 1;
                                                } else {
                                                    posNhan = 0;
                                                }
                                            }
                                        }
                                        final int index2 = getIndexItemWearing(t, posNhan);
                                        if (index2 > -1 && this.wItems[item.slotWearing][index2] == null) {
                                            this.wItems[item.slotWearing][index2] = item;
                                            break;
                                        }
                                        item.slotWearing = 0;
                                        item.place = 0;
                                        this.iItems.add(item);
                                        break;
                                    }
                                    case 2: {
                                        item.id = this.getIDItem();
                                        break;
                                    }
                                    case 3: {
                                        item.id = this.getIDItem();
                                        this.bItem.add(item);
                                        break;
                                    }
                                    case 4: {
                                        if (this.wModel != null) {
                                            item.place = 0;
                                            this.iItems.add(item);
                                            break;
                                        }
                                        (this.wModel = new ModelWearing()).setItemModel(this, item, item.getTemplate().atb[4]);
                                        break;
                                    }
                                    case 5: {
                                        item.id = this.getIDItem();
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (final Exception ex12) {
                    }
                }
            }
            this.reclass = 1;
            try {
                this.dbSkill = null;
                this.dbPotion = null;
                this.dbBasic = null;
                this.dbCharQuest = null;
                this.dbInfo = null;
                if (Map.onOffSound) {
                    this.sendMessage(MessageCreator.createMsgSound(0));
                }
            } catch (final Exception ex13) {
            }
            final short[] time = {120, 150, 180, 210};
            this.timeSave = System.currentTimeMillis() + time[Map.r.nextInt(time.length)] * 60 * 1000;
            String noCp = "Chơi quá 180 phút mỗi ngày sẽ hại sức khoẻ.";
            try {
                if (Map.checkRcvXP && Map.openLog) {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage(noCp));
                } else {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage(noCp));
                }
                // this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("5HQuoc.ME - Ngũ Hành Quốc chào mừng bạn đến vùng đất mới."));
            } catch (final Exception ex14) {
            }
            Label_11022:
            {
                if (!getDayOpen(0L).equals("2015-02-26") && !getDayOpen(0L).equals("2015-02-27")) {
                    if (!getDayOpen(0L).equals("2015-02-28")) {
                        break Label_11022;
                    }
                }
                try {
                    if (Map.openLog) {
                        this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Từ 26/02-28/02 Khuyến mãi 50% các loại thẻ. 100% carot và thẻ mệnh giá từ 500k trở lên."));
                    } else {
                        this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Từ 26/02-28/02 Khuyến mãi 100% các loại thẻ, sms, vcoin."));
                    }
                } catch (final Exception ex15) {
                }
            }
            if (Map.pkAuto) {
                try {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Đang trong thời gian pk tự do."));
                } catch (final IOException ex16) {
                }
            }
            if (this.haveShield()) {
                try {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Bạn đang được bảo hộ"));
                } catch (final IOException ex17) {
                }
            }
            if (this.idClan > -1 && this.myCountry > -1) {
                this.rankGov = Char.gov[this.myCountry].getRankGov(this.charname);
                if (this.rankGov > -1) {
                    if (this.rankGov < 2) {
                        this.potions[87] = 1;
                    }
                    if (this.rankGov == 0) {
                        this.potions[85] = 1;
                    }
                    this.potions[78] = 1;
                    this.potions[28] = 1;
                    if (this.potions[27] == 0) {
                        this.potions[27] = 1;
                    }
                }
            }
            if (this.lvDetail.lv >= 34) {
                try {
                    final FruitTemplate[] fr = Database.instance.loadCharFruit(this);
                    if (fr == null) {
                        Database.instance.addCharFruit(this.charDBID);
                    } else {
                        this.fruit = fr;
                    }
                    this.createFruit();
                    MessageCreator.createMsgCharFruit(this, 0, 0);
                } catch (final Exception e4) {
                    e4.printStackTrace();
                }
            }
            if (this.lvDetail.lv >= 40) {
                Vector<TuBinhTemplate> tb = Database.instance.loadCharTubinh(this);
                if (this.lastLog.compareTo("2017-10-09 09:28:00") < 0 && Map.openLog && TeamServer.server == 3) {
                    final Vector<TuBinhTemplate> tb2 = Database.instance.loadCharTubinh0610(this);
                    if (tb.size() == 0) {
                        tb = tb2;
                    } else {
                        for (int i4 = 0; i4 < tb2.size(); ++i4) {
                            final TuBinhTemplate tubinh = tb2.get(i4);
                            for (int j3 = 0; j3 < tb.size(); ++j3) {
                                final TuBinhTemplate tbchinh = tb.get(j3);
                                if (tbchinh.id == tubinh.id) {
                                    for (int k2 = 0; k2 < tubinh.manh.length; ++k2) {
                                        tbchinh.addManh(k2, this, tubinh.manh[k2]);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                if (tb.size() == 0) {
                    Database.instance.addCharTubinh(this.charDBID);
                } else {
                    this.tubinh = tb;
                }
                this.createTubinh();
                MessageCreator.createMsgCharTuBinh(this);
            }
            this.getInfoWebWearing();
            if (this.firstNapMoney == 0 && isSuKienHalowwen2015() && Map.openLog) {
                final String kq = Database.instance.getFirstNap(this);
                if (!kq.equals("")) {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage(kq));
                    this.firstNapMoney = 1;
                    Database.instance.saveOrtherLog("", this.charname, kq, "firstx2");
                }
            }
        } catch (final Exception e5) {
            e5.printStackTrace();
        }
//        if (this.potions[70] > 0) {
//            System.out.println("SO LUONG PH " + this.potions[70]);
//            if (this.potions[70] > 2) {
//                Database.instance.banChar(this.charname, 1);
//                CharManager.instance.kickPlayer(this, "char 3");
//            }
//            Logger.logString(String.valueOf(this.charname) + this.potions[70], String.valueOf(this.charname) + ".txt");
//        }
        this.monster = MonsterVantieu.getMonsterVantieu(this.charDBID);
        if (this.monster == null && this.typeTieu > -1) {
            this.typeTieu = -1;
            try {
                this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Chuyến tiêu của bạn đã kết thúc."));
            } catch (final IOException e6) {
                e6.printStackTrace();
            }
        } else if (this.monster != null) {
            try {
                if (this.monster.isFinish()) {
                    this.monster.actorDie();
                    this.typeTieu = -1;
                } else {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Ngựa vận tiêu đang ở " + Map.getNameMap(this.monster.map.mapId) + " toạ độ " + this.monster.x / 16 + " " + this.monster.y / 16));
                }
            } catch (final IOException e6) {
                e6.printStackTrace();
            }
        }
        if (this.isCharNu() && getDayOpen(0L).equals("2017-03-08")) {
            try {
                final Message msg = MessageCreator.createMsgWeather(5, 99, 60000);
                this.sendMessage(msg);
            } catch (final Exception ex18) {
            }
            if (this.lastLog.compareTo("2017-03-08 10:57:00") <= 0) {
                this.createPet(4, 10080);
            }
        }
        try {
            Database.instance.addCharEventTrungthu(this);
        } catch (final Exception ex19) {
        }
    }

    private void doAddGiftSn() {
        String info = "";
        final int pc = Map.r.nextInt(100);
        if (pc < 20) {
            final int[] potions = this.potions;
            final int n = 69;
            ++potions[n];
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            info = "Nhận được vé quay số";
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (pc < 40) {
            final int sl = 50000 + (Map.r.nextInt(5) + 1) * 10000;
            this.addXu(sl, "char 1");
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            info = "Nhận được " + sl + " xu";
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (pc < 60) {
            final int idtangtoc = (Map.r.nextInt(100) < 30) ? 8 : 13;
            final int[] potions2 = this.potions;
            final int n2 = idtangtoc;
            ++potions2[n2];
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            info = "Nhận được " + LoginHandler.PORTION_NAME[idtangtoc];
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (pc < 80) {
            final int[] potions3 = this.potions;
            final int n3 = 100;
            ++potions3[n3];
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            info = "Nhận được loa";
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else {
            final int idgem = GemTemplate.ID_MATERIAL_LOW[Map.r.nextInt(4)][Map.r.nextInt(5)];
            this.doAddGemItem(idgem, 2, false);
            this.sendMessage(MessageCreator.createCharGemItem(this));
            info = "Nhận được 2 " + Map.gemTemplate[idgem].name;
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        }
        Database.instance.saveOrtherLog("", this.charname, info, "ogsn");
    }

    private void doSetGiftOffline() {
        for (int i = 0; i < GemTemplate.ID_MATERIAL_HIGHT[5].length; ++i) {
            this.doAddGemItem(GemTemplate.ID_MATERIAL_HIGHT[5][i], 3, true);
            this.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[5][i], 3, true);
        }
        this.doAddGemItem(154, 3, true);
        Database.instance.updateRcvGiftTop(this.charname);
        Database.instance.saveOrtherLog("", this.charname, "Nhan qua offline hn", "offline");
        try {
            this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Bạn đã nhận được quà offline"));
        } catch (final IOException ex) {
        }
    }

    public void addPet(final int id) {
        final Pet pet = new Pet(Map.petTemplate[id].idTemplate, Map.petTemplate[id].idImg, this.getIDItem(), 0, this.charDBID, Map.petTemplate[id].name);
        pet.totalMinute = Map.petTemplate[id].timeExpire;
        pet.type = 3;
        this.pet.add(pet);
        pet.timeBuy = System.currentTimeMillis();
        Database.instance.savePet(pet);
    }

    public void addGif83() {
        final Calendar cl = Calendar.getInstance();
        final int day = cl.get(5);
    }

    public void resetTN() {
        final int allBasicPoint = this.lvDetail.getBasicPoint(this.lastLV) + (this.lastLV - 1) * 5;
        this.basepoint = (short) (allBasicPoint - (this.lastLV - 1) * 5);
        this.strength = (short) (Database.basicPoint[this.charClass][0] + (this.lastLV - 1));
        this.agitity = (short) (Database.basicPoint[this.charClass][1] + (this.lastLV - 1));
        this.spirit = (short) (Database.basicPoint[this.charClass][2] + (this.lastLV - 1));
        this.health = (short) (Database.basicPoint[this.charClass][3] + (this.lastLV - 1));
        this.luck = (short) (Database.basicPoint[this.charClass][4] + (this.lastLV - 1));
        if (this.basepoint < 0) {
            this.basepoint = 0;
        }
        this.calculateAttrib();
        try {
            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
        } catch (final Exception ex) {
        }
    }

    public void setAutoSkill(final int type) {
    }

    public void setAutoSkillSmall() {
    }

    public void setAutoGetITem(final int type) {
    }

    public void resetKN() {
        for (int i = 0; i < this.skill.length; ++i) {
            if (this.skill[i] > 0) {
                this.skill[i] = 0;
            }
        }
        if (this.lastLV >= 3) {
            this.skill[0] = 1;
        }
        this.skillpoint = (short) (this.lastLV - 1);
        if (this.skillpoint < 0) {
            this.skillpoint = 0;
        }
        try {
            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
        } catch (final Exception ex) {
        }
    }

    public void resetKNDownlv() {
        for (int i = 0; i < this.skill.length; ++i) {
            if (this.skill[i] > 0) {
                this.skill[i] = 0;
            }
        }
        if (this.lastLV > 3) {
            this.skill[0] = 1;
        }
        this.skillpoint = (short) (this.lvDetail.lv - 1);
        if (this.skillpoint < 0) {
            this.skillpoint = 0;
        }
    }

    public static String[] split(String original, final String separator) {
        Vector<String> nodes = new Vector();

        for (int index = original.indexOf(separator); index >= 0; index = original.indexOf(separator)) {
            nodes.addElement(original.substring(0, index));
            original = original.substring(index + separator.length());
        }

        nodes.addElement(original);
        String[] result = new String[nodes.size()];
        if (nodes.size() > 0) {
            for (int loop = 0; loop < nodes.size(); ++loop) {
                result[loop] = (String) nodes.elementAt(loop);
            }
        }

        return result;
    }

    public boolean isFullParty() {
        if (this.party.userParty.size() < 5) {
            return false;
        }
        final int[] clazz = new int[5];
        for (int i = 0; i < this.party.userParty.size(); ++i) {
            final Char p = this.party.userParty.get(i);
            final int[] array = clazz;
            final byte charClass = p.charClass;
            ++array[charClass];
            if (clazz[p.charClass] > 1) {
                return false;
            }
        }
        return true;
    }

    public int getMaxLvUserParty() {
        int max = 0;
        for (int i = 0; i < this.party.userParty.size(); ++i) {
            final Char p = this.party.userParty.get(i);
            if (p.lvDetail.lv > max) {
                max = p.lvDetail.lv;
            }
        }
        return max;
    }

    public void sendItem(final int type, final int idGif) {
        if (this.lvDetail.lv > 30) {
            return;
        }
        if (this.lastLV > 30) {
            return;
        }
        final Calendar calNow = Calendar.getInstance();
        if (this.calCheck != null && calNow.getTime().getTime() - this.calCheck.getTime().getTime() > 604800000L) {
            return;
        }
        switch (type) {
            case 0: {
                if (idGif == 0) {
                    final int[] potions = this.potions;
                    final int n = 22;
                    potions[n] += 50;
                    final int[] potions2 = this.potions;
                    final int n2 = 24;
                    potions2[n2] += 50;
                    final int[] potions3 = this.potions;
                    final int n3 = 84;
                    potions3[n3] += 3;
                    final int[] potions4 = this.potions;
                    final int n4 = 79;
                    ++potions4[n4];
                } else if (idGif == 1) {
                    final int[] potions5 = this.potions;
                    final int n5 = 22;
                    potions5[n5] += 50;
                    final int[] potions6 = this.potions;
                    final int n6 = 24;
                    potions6[n6] += 50;
                    final int[] potions7 = this.potions;
                    final int n7 = 84;
                    potions7[n7] += 3;
                    final int[] potions8 = this.potions;
                    final int n8 = 79;
                    potions8[n8] += 3;
                    Map.addGoldTimeTicket(this, 1);
                } else if (idGif == 2) {
                    final int[] potions9 = this.potions;
                    final int n9 = 22;
                    potions9[n9] += 100;
                    final int[] potions10 = this.potions;
                    final int n10 = 24;
                    potions10[n10] += 100;
                    final int[] potions11 = this.potions;
                    final int n11 = 84;
                    potions11[n11] += 3;
                    final int[] potions12 = this.potions;
                    final int n12 = 79;
                    potions12[n12] += 5;
                } else if (idGif == 3) {
                    final int[] potions13 = this.potions;
                    final int n13 = 22;
                    potions13[n13] += 100;
                    final int[] potions14 = this.potions;
                    final int n14 = 24;
                    potions14[n14] += 100;
                    final int[] potions15 = this.potions;
                    final int n15 = 84;
                    potions15[n15] += 3;
                    final int[] potions16 = this.potions;
                    final int n16 = 79;
                    potions16[n16] += 7;
                }
                try {
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                    this.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã nhận được quà.", ""));
                } catch (final Exception ex) {
                }
                Database.instance.saveOrtherLog("", this.charname, String.valueOf(type) + " nhan goi qua " + idGif, "tanthu");
                break;
            }
            case 1: {
                if (idGif == 0) {
                    final int[] potions17 = this.potions;
                    final int n17 = 79;
                    potions17[n17] += 3;
                } else if (idGif == 1) {
                    final int[] potions18 = this.potions;
                    final int n18 = 22;
                    potions18[n18] += 50;
                    final int[] potions19 = this.potions;
                    final int n19 = 24;
                    potions19[n19] += 50;
                } else if (idGif == 2) {
                    final int[] potions20 = this.potions;
                    final int n20 = 79;
                    potions20[n20] += 3;
                } else if (idGif == 3) {
                    final int[] idModel = {50, 55, 51, 54};
                    int rd = Map.r.nextInt(2);
                    if (this.headStyle % 2 == 0) {
                        rd += 2;
                    }
                    final ShopTemplate item = Map.getShopTemplate(idModel[rd]);
                    Map.doBuyModelClothe(this, item, 3);
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                } else if (idGif == 4) {
                    final int[] potions21 = this.potions;
                    final int n21 = 79;
                    potions21[n21] += 5;
                } else if (idGif == 5) {
                    final int[] potions22 = this.potions;
                    final int n22 = 79;
                    potions22[n22] += 7;
                }
                try {
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                    this.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã nhận được quà.", ""));
                } catch (final Exception ex2) {
                }
                Database.instance.saveOrtherLog("", this.charname, String.valueOf(type) + " nhan goi qua " + idGif, "tanthu");
            }
            case 2: {
            }
            case 3: {
            }
        }
    }

    private void bonusTime() {
        isSuKienTet2017();
        try {
            switch (this.rcvGifByHour) {
                case 1: {
                    if (this.millisOnline >= 300000L) {
                        this.millisOnlineStart = System.currentTimeMillis();
                        ++this.rcvGifByHour;
                        this.sendItem(0, 0);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.millisOnline >= 900000L) {
                        this.millisOnlineStart = System.currentTimeMillis();
                        ++this.rcvGifByHour;
                        this.sendItem(0, 1);
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.millisOnline >= 1800000L) {
                        this.millisOnlineStart = System.currentTimeMillis();
                        ++this.rcvGifByHour;
                        this.sendItem(0, 2);
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.millisOnline >= 2700000L) {
                        this.millisOnlineStart = System.currentTimeMillis();
                        ++this.rcvGifByHour;
                        this.sendItem(0, 3);
                        break;
                    }
                    break;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void bonusDay() {
        try {
            if (!this.isOnlineToDay) {
                if (this.dateStartOnline.trim().equals("") || this.dateStartOnline.trim().equals("0")) {
                    final Calendar calNow = Calendar.getInstance();
                    final NumberFormat f = new DecimalFormat("00");
                    final int y = calNow.get(1) - 2000;
                    final int m = calNow.get(2);
                    final int d = calNow.get(5);
                    this.dateStartOnline = String.valueOf(f.format(y)) + f.format(m) + f.format(d);
                }
                this.isOnlineToDay = true;
                this.sendItem(2, 0);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void bonusMonth() {
        try {
            switch (this.rcvGifByMonth) {
                case 1: {
                    if (this.totalDayInMonth >= 3) {
                        ++this.rcvGifByMonth;
                        this.sendItem(3, 0);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.totalDayInMonth >= 7) {
                        ++this.rcvGifByMonth;
                        this.sendItem(3, 0);
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.totalDayInMonth >= 14) {
                        ++this.rcvGifByMonth;
                        this.sendItem(3, 0);
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.totalDayInMonth >= 26) {
                        ++this.rcvGifByMonth;
                        this.sendItem(3, 0);
                        break;
                    }
                    break;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void bonusLevel() {
        try {
            switch (this.rcvGifByLv) {
                case 1: {
                    if (this.lvDetail.lv >= 5) {
                        ++this.rcvGifByLv;
                        this.sendItem(1, 0);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (this.lvDetail.lv >= 10) {
                        ++this.rcvGifByLv;
                        this.sendItem(1, 1);
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.lvDetail.lv >= 15) {
                        ++this.rcvGifByLv;
                        this.sendItem(1, 2);
                        break;
                    }
                    break;
                }
                case 4: {
                    if (this.lvDetail.lv >= 20) {
                        ++this.rcvGifByLv;
                        this.sendItem(1, 3);
                        break;
                    }
                    break;
                }
                case 5: {
                    if (this.lvDetail.lv >= 25) {
                        ++this.rcvGifByLv;
                        this.sendItem(1, 4);
                        break;
                    }
                    break;
                }
                case 6: {
                    if (this.lvDetail.lv >= 30) {
                        ++this.rcvGifByLv;
                        this.sendItem(1, 5);
                        break;
                    }
                    break;
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setPlant(final int idFarm, final int idTree) {
        if (idFarm < this.seedsItem.size()) {
            final SeedTemplate st = Map.seedsTemplate[idTree];
            for (int i = 0; i < this.seedsItem.size(); ++i) {
                final SeedItem s = this.seedsItem.get(i);
                if (s.idFarm == idFarm) {
                    s.idTemplate = (byte) idTree;
                    s.timeLive = System.currentTimeMillis();
                    s.rankTree = (byte) Map.r.nextInt(4);
                    s.lvTree = 0;
                    return true;
                }
            }
        }
        return false;
    }

    public String buyFarm(final int idFarm) {
        if (idFarm < this.seedsItem.size()) {
            if (idFarm > 3 && this.seedsItem.get(idFarm - 1).buy == 0) {
                return "Bạn phải mua các ô đất theo thứ tự";
            }
            int i = 0;
            while (i < this.seedsItem.size()) {
                final SeedItem s = this.seedsItem.get(i);
                if (s.idFarm == idFarm) {
                    if (s.buy == 0) {
                        s.buy = 1;
                        this.map.doSendFarm(this);
                        return "Chúc mừng. bạn đã mua được ô đất " + (idFarm + 1);
                    }
                    return "ô đất đã được mua";
                } else {
                    ++i;
                }
            }
        }
        return "Có lỗi khi mua ô đất";
    }

    public boolean checkAvaliableFarm(final int idFarm) {
        if (idFarm < this.seedsItem.size()) {
            for (int i = 0; i < this.seedsItem.size(); ++i) {
                final SeedItem s = this.seedsItem.get(i);
                if (s.idFarm == idFarm) {
                    return s.checkAvaliable();
                }
            }
        }
        return false;
    }

    public String doHarvest(final int idFarm) {
        if (idFarm < this.seedsItem.size()) {
            for (int i = 0; i < this.seedsItem.size(); ++i) {
                final SeedItem s = this.seedsItem.get(i);
                if (s.idFarm == idFarm) {
                    return s.doHavest(this);
                }
            }
        }
        return "Có lỗi khi thu hoạch";
    }

    public boolean doDelTreePlot(final int idPlot) {
        if (idPlot < this.seedsItem.size()) {
            for (int i = 0; i < this.seedsItem.size(); ++i) {
                final SeedItem s = this.seedsItem.get(i);
                if (s.idFarm == idPlot) {
                    final int idtree = s.idTemplate;
                    final int lv = s.lvTree;
                    s.delTree();
                    Database.instance.saveOrtherLog("tob_log_farm", this.charname, String.valueOf(s.idFarm + 1) + " cay " + idtree + " lv = " + lv, "dt");
                    return true;
                }
            }
        }
        return false;
    }

    public String doUpLvPlot(final int idPlot) {
        if (idPlot < this.seedsItem.size()) {
            for (int i = 0; i < this.seedsItem.size(); ++i) {
                final SeedItem s = this.seedsItem.get(i);
                if (s.idFarm == idPlot) {
                    return s.upLevelPlot(this);
                }
            }
        }
        return "Có lỗi khi nâng cấp ô đất.";
    }

    public byte getLvFarm() {
        return 1;
    }

    public byte getLvStore() {
        return 1;
    }

    public byte getLvMainHouse() {
        return 1;
    }

    public SeedItem getFram(final int idFarm) {
        for (int i = 0; i < this.seedsItem.size(); ++i) {
            if (this.seedsItem.get(i).idFarm == idFarm) {
                return this.seedsItem.get(i);
            }
        }
        return null;
    }

    public void getTreeID(final int idFarm) {
        final SeedItem farm = this.getFram(idFarm);
        if (farm != null) {
            for (int i = 0; i < Map.seedsTemplate.length; ++i) {
                final SeedTemplate s = Map.seedsTemplate[i];
                if (s.sell == 1 && this.lvDetail.lv >= s.lvChar && farm.lvPlot >= s.lvFarm && farm.buy == 1 && !this.treeID.contains(s.id)) {
                    this.treeID.add(s.id);
                }
            }
        }
    }

    public String getInfoWebWearing() {
        this.infoWearing = new StringBuilder().append(this.myCountry).toString();
        String result = "";
        try {
            final String[] data = {"-1,-1", "-1,-1", "2," + this.headStyle, "-1,-1", "", ""};
            final byte[] typeClient = {1, 0, 3};
            int currentWP = -1;
            int currentSkillType = -1;
            int weaIndex = -1;
            int typeWeapone = -1;
            final int classChar = -1;
            Vector<Item> charwearing = new Vector<Item>();
            if (this.getCharWearing().size() == 0) {
                charwearing = MessageCreator.createItem(this.gender, this.charClass);
            } else {
                charwearing = this.getCharWearing();
            }
            for (int i = 0; i < charwearing.size(); ++i) {
                final Item item = charwearing.get(i);
                if (!Map.isModelClothes(item.atb[9]) && (item.getType() == 0 || item.getType() == 1 || item.getType() == 2)) {
                    data[typeClient[item.getType()]] = String.valueOf(typeClient[item.getType()]) + "," + item.getTemplate().style;
                }
                this.infoWearing = String.valueOf(this.infoWearing) + "," + item.getTemplate().id + "," + item.clazz + "," + item.level + "," + item.plus;
                if (Map.isWeapone(item.getType())) {
                    currentSkillType = 0;
                    currentWP = item.getType() - 3;
                    weaIndex = item.getTemplate().color;
                    typeWeapone = item.getTemplate().style;
                    data[4] = String.valueOf(currentWP) + "," + currentSkillType + "," + weaIndex + "," + typeWeapone + "," + this.charClass;
                }
            }
            int idImgPet = -1;
            int typePet = -1;
            int nFrame = 0;
            if (this.petUsing != null) {
                idImgPet = this.petUsing.idImg;
                typePet = this.petUsing.type;
                nFrame = this.petUsing.getPetTemplate().nFrame;
                data[5] = String.valueOf(idImgPet) + "," + typePet + "," + nFrame;
            }
            result = data[0];
            for (int j = 1; j < 6; ++j) {
                result = String.valueOf(result) + "," + data[j];
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getAccountName() {
        String st = "";
        try {
            st = this.session.username;
        } catch (final Exception ex) {
        }
        return st;
    }

    public String[] getAllItemInfoSave() {
        final String[] st = {"", "", "", ""};
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < this.wItems[i].length; ++j) {
                if (this.wItems[i][j] != null) {
                    final String[] array = st;
                    final int n = 0;
                    array[n] = String.valueOf(array[n]) + this.wItems[i][j].getDBInfo() + "|" + this.wItems[i][j].getAttribute() + ">";
                }
            }
            final String[] array2 = st;
            final int n2 = 0;
            array2[n2] = String.valueOf(array2[n2]) + "@";
        }
        for (int k = 0; k < this.iItems.size(); ++k) {
            final String[] array3 = st;
            final int n3 = 1;
            array3[n3] = String.valueOf(array3[n3]) + this.iItems.get(k).getDBInfo() + "|" + this.iItems.get(k).getAttribute() + ">";
        }
        for (int k = 0; k < this.bItem.size(); ++k) {
            final String[] array4 = st;
            final int n4 = 2;
            array4[n4] = String.valueOf(array4[n4]) + this.bItem.get(k).getDBInfo() + "|" + this.bItem.get(k).getAttribute() + ">";
        }
        for (int k = 0; k < this.awItems.size(); ++k) {
            final String[] array5 = st;
            final int n5 = 3;
            array5[n5] = String.valueOf(array5[n5]) + this.awItems.get(k).getDBInfo() + "|" + this.awItems.get(k).getAttribute() + ">";
        }
        return st;
    }

    public void addItem2Wearing(final Item it) {
    }

    public Item[] getArrayCharWearing() {
        return this.wItems[this.slotWearing];
    }

    public void removeItemOutWearing(final int type, final int posnhan) {
        this.wItems[this.slotWearing][getIndexItemWearing(type, posnhan)] = null;
    }

    public Vector<Item> getCharWearing() {
        final Vector<Item> item = new Vector<Item>();
        for (int i = 0; i < this.wItems[this.slotWearing].length; ++i) {
            if (this.wItems[this.slotWearing][i] != null) {
                item.add(this.wItems[this.slotWearing][i]);
            }
        }
        return item;
    }

    public Vector<Item> changePosRing(final Vector<Item> witem) {
        final byte[] pos = new byte[2];
        byte index = 0;
        for (byte i = 0; i < witem.size(); ++i) {
            final Item it = witem.get(i);
            if (it.getType() == 8) {
                final byte[] array = pos;
                final byte b = index;
                index = (byte) (b + 1);
                array[b] = i;
                if (index >= 2) {
                    break;
                }
            }
        }
        if (index == 2) {
            final Item it2 = witem.get(pos[0]);
            final Item it3 = witem.get(pos[1]);
            if (it2.pos == 0 && it3.pos == 0) {
                it2.pos = 1;
            }
            if (it2.pos == 1 && it3.pos == 1) {
                it3.pos = 0;
            }
            if (it2.pos < it3.pos) {
                witem.setElementAt(it3, pos[0]);
                witem.setElementAt(it2, pos[1]);
            }
        } else if (index == 1) {
            witem.get(pos[0]).pos = 1;
        }
        return witem;
    }

    public Vector<Item> getItemResetSock() {
        this.itemResetSock.removeAllElements();
        final Vector<Item> witem = this.getCharWearing();
        this.itemResetSock = new Vector<Item>();
        for (byte i = 0; i < witem.size(); ++i) {
            final Item it = witem.get(i);
            if (it.nhadSock > 0) {
                this.itemResetSock.add(it);
            }
        }
        return this.itemResetSock;
    }

    public Vector<Item> getCharWearing(final int slotWearing) {
        final Vector<Item> item = new Vector<Item>();
        for (int i = 0; i < this.wItems[slotWearing].length; ++i) {
            if (this.wItems[slotWearing][i] != null) {
                item.add(this.wItems[slotWearing][i]);
            }
        }
        return item;
    }

    public boolean checkExistAnimal(final int dbid) {
        for (int i = 0; i < this.animal.size(); ++i) {
            if (this.animal.get(i).dbid == dbid) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Animal getAnimalRide() {
        return this.animalRide;
    }

    public Vector<Item> getAnimalWearing() {
        final Vector<Item> a = new Vector<Item>();
        byte houyen = 0;
        byte bandap = 0;
        byte non = 0;
        byte giap = 0;
        byte yen = 0;
        boolean bInventory = false;
        final Vector<Item> itBackInventory = new Vector<Item>();
        if (this.animalRide != null) {
            for (int i = 0; i < this.awItems.size(); ++i) {
                final Item it = this.awItems.get(i);
                if (it.idAnimal == this.animalRide.dbid) {
                    if (it.getType() == 17) {
                        if (bandap == 0) {
                            a.add(it);
                            bandap = 1;
                        } else {
                            it.place = 0;
                            bInventory = true;
                        }
                    }
                    if (it.getType() == 16) {
                        if (non == 0) {
                            non = 1;
                            a.add(it);
                        } else {
                            it.place = 0;
                            bInventory = true;
                            itBackInventory.add(it);
                        }
                    }
                    if (it.getType() == 15) {
                        if (houyen == 0) {
                            houyen = 1;
                            a.add(it);
                        } else {
                            it.place = 0;
                            bInventory = true;
                            itBackInventory.add(it);
                        }
                    }
                    if (it.getType() == 18) {
                        if (yen == 0) {
                            yen = 1;
                            a.add(it);
                        } else {
                            it.place = 0;
                            bInventory = true;
                            itBackInventory.add(it);
                        }
                    }
                    if (it.getType() == 14) {
                        if (giap == 0) {
                            giap = 1;
                            a.add(it);
                        } else {
                            it.place = 0;
                            bInventory = true;
                            itBackInventory.add(it);
                        }
                    }
                } else {
                    boolean exist = false;
                    for (byte j = 0; j < this.animal.size(); ++j) {
                        final Animal anim = this.animal.get(j);
                        if (anim.dbid == it.idAnimal) {
                            exist = true;
                            bInventory = true;
                            break;
                        }
                    }
                    if (!exist) {
                        it.place = 0;
                        itBackInventory.add(it);
                    }
                }
            }
        }
        if (bInventory) {
            for (int i = 0; i < itBackInventory.size(); ++i) {
                final Item it = itBackInventory.get(i);
                this.awItems.remove(it);
                this.iItems.add(it);
                it.idAnimal = -1;
            }
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
        }
        return a;
    }

    public boolean isPublicMap() {
        return this.map.isPublicMap();
    }

    public void createGifLixi() {
        if (isFinishAllSuKienTet2017()) {
            return;
        }
        this.idOpen.removeAllElements();
        this.nSpecialBox = 2;
        final byte nBox = 9;
        byte id = 0;
        for (byte i = 0; i < nBox - 2; ++i) {
            id = (byte) Map.r.nextInt(3);
            final InfoGifLucky info = new InfoGifLucky(id, this.getType());
            info.createGifGold(false, (byte) ((nBox - 5) / 2));
            this.gifLuckyBox.add(info);
        }
        for (byte i = 0; i < 2; ++i) {
            final InfoGifLucky info = new InfoGifLucky((byte) 2, this.getType());
            info.createGifGold(true, (byte) ((nBox - 5) / 2));
            this.gifLuckyBox.add(info);
        }
    }

    public boolean createGifLuckyBox(final byte nBox, final byte type) {
        this.idOpen.removeAllElements();
        if (this.gifLuckyBox.size() > 2) {
            return false;
        }
        this.nSpecialBox = 2;
        byte id = 0;
        for (byte i = 0; i < nBox - 2; ++i) {
            if (type == 0) {
                id = (byte) Map.r.nextInt(3);
                final InfoGifLucky info = new InfoGifLucky(id, type);
                info.createGifSilver(false, (byte) ((nBox - 5) / 2));
                this.gifLuckyBox.add(info);
            } else if (type == 1) {
                id = (byte) Map.r.nextInt(3);
                final InfoGifLucky info = new InfoGifLucky(id, type);
                info.createGifGold(false, (byte) ((nBox - 5) / 2));
                this.gifLuckyBox.add(info);
            }
        }
        if (type == 0) {
            for (byte i = 0; i < 2; ++i) {
                if (Map.r.nextInt(100) < 90) {
                    final InfoGifLucky info = new InfoGifLucky((byte) 2, type);
                    info.createGifSilver(true, (byte) ((nBox - 5) / 2));
                    this.gifLuckyBox.add(info);
                } else {
                    final InfoGifLucky info = new InfoGifLucky((byte) 3, type);
                    info.createGifSilver(true, (byte) ((nBox - 5) / 2));
                    this.gifLuckyBox.add(info);
                }
            }
        } else if (type == 1) {
            for (byte i = 0; i < 2; ++i) {
                if (Map.r.nextInt(100) < 50) {
                    final InfoGifLucky info = new InfoGifLucky((byte) 3, type);
                    info.createGifGold(true, (byte) ((nBox - 5) / 2));
                    this.gifLuckyBox.add(info);
                } else {
                    final InfoGifLucky info = new InfoGifLucky((byte) 2, type);
                    info.createGifGold(true, (byte) ((nBox - 5) / 2));
                    this.gifLuckyBox.add(info);
                }
            }
        }
        return true;
    }

    public void doCreatePetMax(final int idTemplate, final int level, final int idtemplatetrung, final int time) {
        ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(0).get(new Integer(idTemplate));
        Item newItem = null;
        newItem = new Item(template, false, 0, 0, template.id);
        newItem.id = this.getIDItem();
        newItem.owner = this.charDBID;
        newItem.level = (short) level;
        newItem.identify = this.charDBID;
        newItem.clazz = 0;
        newItem.doAddOptionPet(template.atb[0]);
        newItem.dateCreate = getDayTime(0L);
        newItem.otherAtt[4] = 30;
        template = (ItemTemplates) Map.itemTemplates.get(0).get(new Integer(idtemplatetrung));
        this.createPet(template.atb[1], time, newItem);
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
        this.sendMessage(MessageCreator.createServerAlertMessage("Tạo pet thành công", ""));
    }

    public void doCreatePetBachThu(final int time, final boolean isShow) {
        final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(0).get(727);
        Item newItem = null;
        newItem = new Item(template, false, 0, 0, template.id);
        newItem.id = this.getIDItem();
        newItem.owner = this.charDBID;
        newItem.level = newItem.getTemplate().level;
        if (time > 0) {
            newItem.minuteExist = time;
            newItem.timeLoan = System.currentTimeMillis();
        }
        newItem.identify = this.charDBID;
        newItem.clazz = 0;
        newItem.doAddOptionPet(template.atb[0]);
        newItem.dateCreate = getDayTime(0L);
        this.createPet(29, newItem.minuteExist, newItem);
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
        if (isShow) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Nhận được Bạch Thử", ""));
        }
    }

    public void doCreatePet(final Item itemtrung) {
        final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(0).get(new Integer(itemtrung.getTemplate().idItemUpLevel));
        Item newItem = null;
        newItem = new Item(template, false, 0, 0, template.id);
        newItem.id = this.getIDItem();
        newItem.owner = this.charDBID;
        newItem.level = newItem.getTemplate().level;
        if (itemtrung.minuteExist > 0) {
            newItem.minuteExist = itemtrung.minuteExist;
            newItem.timeLoan = itemtrung.timeLoan;
        }
        newItem.identify = this.charDBID;
        newItem.clazz = 0;
        newItem.doAddOptionPet(template.atb[0]);
        newItem.dateCreate = getDayTime(0L);
        this.iItems.remove(itemtrung);
        final Pet p = this.createPet(itemtrung.getTemplate().atb[1], itemtrung.minuteExist, newItem);
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
        this.sendMessage(MessageCreator.createServerAlertMessage("Tạo pet thành công", ""));
        if (itemtrung.minuteExist > 0 && Map.r.nextInt(100) < 1) {
            try {
                p.doUpdrage2MaxByAdmin(this, 4);
                p.itemPet.level = 2;
                Database.instance.savePet(p);
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
                Database.instance.saveOrtherLog("", this.charname, "tao pet " + itemtrung.getName() + " co thoi han va dc nang cap len cap cuoi", "taopcc");
            } catch (final Exception ex) {
            }
        }
    }

    public void doNangCapHoaKyLan(final Item itemHoaKyLan, final int idHoaThach) {
        if (itemHoaKyLan.getTemplate().idItemUpLevel > -1) {
            int pcUp = 1;
            if (idHoaThach == GemTemplate.HOA_THACH_C2) {
                pcUp = 10;
            }
            String infoLog = String.valueOf(idHoaThach) + "|" + itemHoaKyLan.getName() + "_" + itemHoaKyLan.getInfoSave();
            if (Map.r.nextInt(100) < pcUp) {
                final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(0).get(new Integer(itemHoaKyLan.getTemplate().idItemUpLevel));
                Item newItem = null;
                newItem = new Item(template, false, 0, 0, template.id);
                newItem.id = this.getIDItem();
                newItem.owner = this.charDBID;
                newItem.level = newItem.getTemplate().level;
                this.iItems.add(newItem);
                newItem.identify = this.charDBID;
                newItem.clazz = 0;
                newItem.doAddOptionThanThu(template.atb[0]);
                newItem.dateCreate = getDayTime(0L);
                this.iItems.remove(itemHoaKyLan);
                infoLog = String.valueOf(infoLog) + " nang cap thanh cong";
                this.sendMessage(MessageCreator.createServerAlertMessage("Nâng cấp thành công", ""));
            } else {
                this.sendMessage(MessageCreator.createServerAlertMessage("Nâng cấp thất bại", ""));
                infoLog = String.valueOf(infoLog) + " nang cap that bai";
            }
            if (this.listGemitemLock[idHoaThach] > 0) {
                this.delGem(idHoaThach, 1, true);
            } else {
                this.delGem(idHoaThach, 1, false);
            }
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
            this.sendMessage(MessageCreator.createCharGemItem(this));
            Database.instance.saveOrtherLog("", this.charname, infoLog, "nangcaphkl");
        }
    }

    public String doOpenLuckyBox() {
        if (this.gifLuckyBox.size() <= 2) {
            return "";
        }
        String result = "";
        if (Map.r.nextInt(100) < 2 && this.nSpecialBox > 0) {
            final byte index = (byte) (Map.r.nextInt(this.nSpecialBox) + (this.gifLuckyBox.size() - this.nSpecialBox));
            final InfoGifLucky info = this.gifLuckyBox.get(index);
            this.gifLuckyBox.remove(info);
            result = info.removeGif(this);
            --this.nSpecialBox;
        } else {
            final byte index = (byte) Map.r.nextInt(this.gifLuckyBox.size() - this.nSpecialBox);
            final InfoGifLucky info = this.gifLuckyBox.get(index);
            this.gifLuckyBox.remove(info);
            result = info.removeGif(this);
        }
        return result;
    }

    public void doChuc83(final String friendName) {
        if (!isSuKien83()) {
            return;
        }
        final int[] per = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 10, 10, 2000, 200, 500, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 200, 200, 200, 200, 200, 200, 10, 1000, 1000, 1000, 1000, 1000, 10, 10};
        final int[] idgift = {84, 8, 113, 112, 114, 94, 96, 29, GemTemplate.LKD_40, GemTemplate.LKD_35, GemTemplate.DA_TIEN_GIAI, GemTemplate.BOT_XANH_LA, GemTemplate.BOT_XANH, GemTemplate.BOT_TRANG, GemTemplate.DUC_TUONG_KHAM_1, GemTemplate.DUC_TUONG_KHAM_2, GemTemplate.DUC_TUONG_KHAM_3, GemTemplate.DUC_TUONG_KHAM_4, GemTemplate.DUC_TUONG_KHAM_5, GemTemplate.DUC_TUONG_KHAM_6, GemTemplate.DA_MAY_MAN_C4, GemTemplate.DA_MAY_MAN_C5, GemTemplate.VAI_C1, GemTemplate.SAT_C1, GemTemplate.NGOC_C1, GemTemplate.GO_THUONG_C1, GemTemplate.DA_MEM_C1, 587, 588, 589, 590, 591, 592, 83, 715, 716, 717, 718, 719, 29, 722};
        final int[] typegift = {4, 4, 4, 4, 4, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, -1, -1, -1, -1, -1, -1, 4, 3, 3, 3, 3, 3, -4, -5};
        final int[] soluongmax = {5, 3, 5, 5, 5, 100, 100, 10, 1, 1, 3, 50, 100, 100, 20, 20, 20, 20, 20, 20, 100, 100, 100, 100, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        final int[] soluongmin = {3, 1, 1, 1, 1, 10, 10, 5, 1, 1, 1, 10, 50, 50, 10, 10, 10, 10, 10, 10, 50, 50, 50, 50, 50, 50, 50, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        String info = "Chúc mừng bạn nhận được ";
        final int index = getIndexRandom(per);
        Label_2933:
        {
            if (typegift[index] == -5) {
                Map.doCreateBookSkillPet(this, (Map.r.nextInt(100) >= 30) ? 1 : 0);
                info = "sach skill pet";
            } else if (typegift[index] == -4) {
                final int[] time = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 5};
                this.doCreatePetBachThu(time[Map.r.nextInt(time.length)] * 24 * 60, false);
                info = "pet bach thu";
            } else if (typegift[index] == -1) {
                final int idtemp = idgift[index];
                Item newItem = null;
                if (!ItemLuckyDraw.isChoi(idtemp)) {
                    newItem = ItemLuckyDraw.createtemCoat(idtemp, this, Map.r.nextInt(2), false, (Map.r.nextInt(7) + 1) * 24 * 60);
                    newItem.timeLoan = System.currentTimeMillis();
                    newItem.minuteExist = (Map.r.nextInt(7) + 1) * 24 * 60;
                    this.sendMessage(MessageCreator.createServerAlertMessage("Nhận được " + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút", ""));
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                    info = String.valueOf(newItem.getName()) + "_" + newItem.getDBInfo() + "|" + newItem.getAttribute() + "_" + newItem.level + " thoi han " + newItem.minuteExist;
                    try {
                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được " + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút khi gửi lời chúc 8/3 đến bạn " + friendName));
                    } catch (final IOException ex) {
                    }
                } else {
                    newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idtemp), false, 0, 0, idtemp);
                    newItem.id = this.getIDItem();
                    newItem.owner = this.charDBID;
                    newItem.level = newItem.getTemplate().level;
                    newItem.identify = this.charDBID;
                    newItem.clazz = this.charClass;
                    newItem.createAttChoi();
                    newItem.dateCreate = getDayOpen(0L);
                    newItem.timeLoan = System.currentTimeMillis();
                    newItem.minuteExist = (Map.r.nextInt(7) + 1) * 24 * 60;
                    this.iItems.add(newItem);
                    this.sendMessage(MessageCreator.createServerAlertMessage("Nhận được " + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút", ""));
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                    info = String.valueOf(newItem.getName()) + "_" + newItem.getDBInfo() + "|" + newItem.getAttribute() + "_" + newItem.level + " thoi han " + newItem.minuteExist;
                    try {
                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được " + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút khi gửi lời chúc 8/3 đến bạn " + friendName));
                    } catch (final IOException ex2) {
                    }
                }
            } else if (typegift[index] == -2) {
                this.addXu(soluongmax[index], "char 2");
                info = String.valueOf(info) + soluongmax[index] + " xu";
                this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            } else if (typegift[index] == -3) {
                Map.addXpCharEvent(this, soluongmax[index], false, "char doChuc83");
                info = String.valueOf(info) + soluongmax[index] + " exp";
                this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            } else if (typegift[index] == 4) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final int[] potions = this.potions;
                final int n = idgift[index];
                potions[n] += soluong;
                info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
                this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            } else if (typegift[index] == 6) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final boolean lock = Map.r.nextInt(2) == 1;
                this.doAddGemItem(idgift[index], soluong, lock);
                info = String.valueOf(info) + soluong + " " + Map.gemTemplate[idgift[index]].name + (lock ? " khoá" : "");
                this.sendMessage(MessageCreator.createCharGemItem(this));
                this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                if (idgift[index] != GemTemplate.LKD_35) {
                    if (idgift[index] != GemTemplate.LKD_40) {
                        break Label_2933;
                    }
                }
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được " + Map.gemTemplate[idgift[index]].name + " khi gửi lời chúc 8/3 đến bạn " + friendName));
                } catch (final IOException ex3) {
                }
            } else if (typegift[index] == 3) {
                if (this.isFullInventory()) {
                    final int xu = 5000 + Map.r.nextInt(100000);
                    this.addXu(xu, "char 3");
                    info = String.valueOf(info) + xu + " xu";
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                } else {
                    final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                    Item newItem = null;
                    newItem = new Item(template, false, template.clazz, template.clazz, template.id);
                    newItem.id = this.getIDItem();
                    newItem.owner = this.charDBID;
                    newItem.level = newItem.getTemplate().level;
                    newItem.identify = this.charDBID;
                    int time2 = Map.r.nextInt(48) + 1;
                    final int pc = Map.r.nextInt(100);
                    if (pc < 1) {
                        time2 = 360;
                    } else if (pc < 10) {
                        time2 = 240;
                    } else if (pc < 50) {
                        time2 = 72 + Map.r.nextInt(49);
                    }
                    newItem.timeLoan = System.currentTimeMillis();
                    newItem.minuteExist = time2 * 60;
                    if (newItem.isVukhiThoiTrang()) {
                        newItem.createAttributeVuKhiThoiTrang();
                    }
                    newItem.dateCreate = getDayTime(0L);
                    this.iItems.add(newItem);
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                    info = String.valueOf(info) + newItem.getName();
                    try {
                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được " + newItem.getName() + " khi tặng hoa"));
                    } catch (final IOException ex4) {
                    }
                }
                this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            }
        }
        Database.instance.saveOrtherLog("", this.charname, info, "chuc83");
        if (isSuKien83()) {
            Database.instance.addCharEvent83(this);
        }
    }

    public void doOpenLixiNgude() {
        final int[] per = {50, 50, 50, 50, 50, 50, 50, 50, 50};
        final int[] idgift = {108, 115, 122, 129, 136, 69, GemTemplate.DA_NGU_HOP_6, GemTemplate.DA_THUOC_TINH_05, -1};
        final int[] typegift = {6, 6, 6, 6, 6, 4, 6, 6, -1};
        final int[] soluongmax = {1, 1, 1, 1, 1, 5, 1, 1, 1};
        final int[] soluongmin = {1, 1, 1, 1, 1, 5, 1, 1, 1};
        String info = "Chúc mừng bạn nhận được ";
        final int index = getIndexRandom(per);
        if (typegift[index] == -1) {
            final int[] minute = {4320};
            final Item newItem = ItemLuckyDraw.createItemCoat(this, Map.r.nextInt(2), false, minute[Map.r.nextInt(minute.length)]);
            this.iItems.add(newItem);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
            info = String.valueOf(info) + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
            this.sendMessage(MessageCreator.createMsgChat(this.id, info));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        } else if (typegift[index] == 6) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            this.doAddGemItem(idgift[index], soluong, true);
            info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgift[index]].name;
            this.sendMessage(MessageCreator.createCharGemItem(this));
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (typegift[index] == 4) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            final int[] potions = this.potions;
            final int n = idgift[index];
            potions[n] += soluong;
            info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            if (idgift[index] == 9) {
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được nhân sâm khi mở bao lì xì"));
                } catch (final IOException ex) {
                }
            }
        } else if (typegift[index] == 3) {
            if (this.isFullInventory()) {
                final int xu = 5000 + Map.r.nextInt(100000);
                this.addXu(xu, "char 4");
                info = String.valueOf(info) + xu + " xu";
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            } else {
                final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                Item newItem = null;
                newItem = new Item(template, false, 0, 0, template.id);
                newItem.id = this.getIDItem();
                newItem.owner = this.charDBID;
                newItem.level = newItem.getTemplate().level;
                this.iItems.add(newItem);
                newItem.identify = this.charDBID;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                info = String.valueOf(info) + newItem.getName();
            }
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        }
        Database.instance.saveOrtherLog("", this.charname, info, "olixi");
    }

    public void doEatBanhSn() {
        final int[] per = {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 1};
        final int[] idgift = {73, 80, 87, 94, 101, 108, 115, 122, 129, 136, 35, 61, -1};
        final int[] typegift = {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 4, -1};
        final int[] soluongmax = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 9, 1};
        final int[] soluongmin = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 9, 1};
        String info = "Chúc mừng bạn nhận được ";
        final int index = getIndexRandom(per);
        if (typegift[index] == -1) {
            final int[] minute = {-1};
            final Item newItem = ItemLuckyDraw.createItemCoat(this, Map.r.nextInt(2), false, minute[Map.r.nextInt(minute.length)]);
            this.iItems.add(newItem);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
            info = String.valueOf(info) + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
            this.sendMessage(MessageCreator.createMsgChat(this.id, info));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        } else if (typegift[index] == 6) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            this.doAddGemItem(idgift[index], soluong, true);
            info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgift[index]].name;
            this.sendMessage(MessageCreator.createCharGemItem(this));
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (typegift[index] == 4) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            final int[] potions = this.potions;
            final int n = idgift[index];
            potions[n] += soluong;
            info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            if (idgift[index] == 9) {
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được nhân sâm khi mở bao lì xì"));
                } catch (final IOException ex) {
                }
            }
        } else if (typegift[index] == 3) {
            if (this.isFullInventory()) {
                final int xu = 5000 + Map.r.nextInt(100000);
                this.addXu(xu, "char 5");
                info = String.valueOf(info) + xu + " xu";
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            } else {
                final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                Item newItem = null;
                newItem = new Item(template, false, 0, 0, template.id);
                newItem.id = this.getIDItem();
                newItem.owner = this.charDBID;
                newItem.level = newItem.getTemplate().level;
                this.iItems.add(newItem);
                newItem.identify = this.charDBID;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                info = String.valueOf(info) + newItem.getName();
            }
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        }
        Database.instance.saveOrtherLog("", this.charname, info, "obsn");
    }

    public static void resetLixi() {
        Char.lixilucky[0] = -1;
        Char.daysomaymm = "";
        Char.totalLixiOpened = 0;
        Map.MAX_LI_XI = 100;
    }

    public static synchronized void doOpenLixi2021(final Char p) {
        if (Char.lixilucky[0] == -1) {
            final Vector<Byte> number = new Vector<Byte>();
            for (int i = 0; i < 100; ++i) {
                number.add((byte) (i + 1));
            }
            System.out.print("SO MAY MAN: ");
            for (int i = 0; i < Char.lixilucky.length; ++i) {
                Char.lixilucky[i] = number.remove(Map.r.nextInt(number.size()));
                System.out.print(String.valueOf(Char.lixilucky[i]) + ",");
                Char.daysomaymm = String.valueOf(Char.daysomaymm) + Char.lixilucky[i] + ",";
            }
            System.out.println("");
        }
        ++Char.totalLixiOpened;
        final int[] per = {500, -1, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500};
        final int[] idgift = {119, 9, 93, 95, GemTemplate.NGOC_HUYEN_MINH_1, GemTemplate.NGOC_HUYEN_MINH_2, GemTemplate.NGOC_HUYEN_MINH_3, 676, 677, 10, 11, 12, GemTemplate.BAN_DO_KHO_BAU, GemTemplate.DA_NGU_HOP_4, GemTemplate.DA_NGU_HOP_5, GemTemplate.DA_NGU_HOP_6};
        final int[] typegift = {4, 4, 4, 4, 6, 6, 6, 3, 3, 4, 4, 4, 6, 6, 6, 6};
        final int[] soluongmax = {1, 1, 10, 10, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        final int[] soluongmin = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        String info = "Chúc mừng bạn nhận được ";
        if (TeamServer.isServerLocal()) {
            info = String.valueOf(Char.daysomaymm) + " số hộp dc mở: " + Char.totalLixiOpened + ". " + info;
        }
        boolean isXu = false;
        for (int j = 0; j < Char.lixilucky.length; ++j) {
            if (Char.lixilucky[j] == Char.totalLixiOpened) {
                p.addXu(Char.XU_LI_XI[j], "char 6");
                info = String.valueOf(info) + Char.XU_LI_XI[j] + " xu. ";
                isXu = true;
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                break;
            }
        }
        if (!isXu) {
            final int xu = UtilKPAH.getRandomMinMax(10000, 20000);
            p.addXu(xu, "char 6");
            info = String.valueOf(info) + xu + " xu. ";
            isXu = true;
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
        }
        final int index = getIndexRandom(per);
        if (typegift[index] == -1) {
            p.addXu(soluongmax[index], "char 6");
            info = String.valueOf(info) + soluongmax[index] + " xu. ";
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
        } else if (typegift[index] == 6) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            p.doAddGemItem(idgift[index], soluong, false);
            info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgift[index]].name;
            p.sendMessage(MessageCreator.createCharGemItem(p));
        } else if (typegift[index] == 4) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            final int[] potions = p.potions;
            final int n = idgift[index];
            potions[n] += soluong;
            info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            if (idgift[index] == 9) {
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(p.charname) + " đã nhận được nhân sâm khi mở bao lì xì"));
                } catch (final IOException ex) {
                }
            }
        } else if (typegift[index] == 3) {
            if (p.isFullInventory()) {
                final int xu2 = 5000 + Map.r.nextInt(100000);
                p.addXu(xu2, "char 7");
                info = String.valueOf(info) + xu2 + " xu";
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            } else {
                final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                Item newItem = null;
                newItem = new Item(template, false, 0, 0, template.id);
                newItem.id = p.getIDItem();
                newItem.owner = p.charDBID;
                newItem.level = newItem.getTemplate().level;
                p.iItems.add(newItem);
                newItem.identify = p.charDBID;
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
                info = String.valueOf(info) + newItem.getName();
            }
        }
        p.sendMessage(MessageCreator.createMsgChat(p.id, info));
        Database.instance.saveOrtherLog("", p.charname, info, "olixi");
    }

    public void doOpenLixi() {
        Map.addXpCharEvent(this, 100000L, false, "char doOpenLixi");
        final int[] per = {500, -1, 500, 500, 500, 500, 500, 500, 500};
        final int[] idgift = {82, 9, 93, 95, GemTemplate.NGOC_HUYEN_MINH_1, GemTemplate.NGOC_HUYEN_MINH_2, GemTemplate.NGOC_HUYEN_MINH_3, 676, 677};
        final int[] typegift = {4, 4, 4, 4, 6, 6, 6, 3, 3};
        final int[] soluongmax = {1, 1, 10, 10, 3, 3, 3, 1, 1};
        final int[] soluongmin = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        String info = "Chúc mừng bạn nhận được ";
        final int index = getIndexRandom(per);
        if (typegift[index] == -1) {
            this.addXu(soluongmax[index], "char 6");
            info = String.valueOf(info) + soluongmax[index] + " xu";
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        } else if (typegift[index] == 6) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            this.doAddGemItem(idgift[index], soluong, false);
            info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgift[index]].name;
            this.sendMessage(MessageCreator.createCharGemItem(this));
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (typegift[index] == 4) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            final int[] potions = this.potions;
            final int n = idgift[index];
            potions[n] += soluong;
            info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            if (idgift[index] == 9) {
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được nhân sâm khi mở bao lì xì"));
                } catch (final IOException ex) {
                }
            }
        } else if (typegift[index] == 3) {
            if (this.isFullInventory()) {
                final int xu = 5000 + Map.r.nextInt(100000);
                this.addXu(xu, "char 7");
                info = String.valueOf(info) + xu + " xu";
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            } else {
                final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                Item newItem = null;
                newItem = new Item(template, false, 0, 0, template.id);
                newItem.id = this.getIDItem();
                newItem.owner = this.charDBID;
                newItem.level = newItem.getTemplate().level;
                this.iItems.add(newItem);
                newItem.identify = this.charDBID;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                info = String.valueOf(info) + newItem.getName();
            }
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        }
        Database.instance.saveOrtherLog("", this.charname, info, "olixi");
    }

    public void doOpenHopQuaNoelHong() {
        if (isFinishAllSuKienNoel()) {
            return;
        }
        String info = "";
        if (Map.r.nextInt(100) < 10) {
            Map.addXpCharEvent(this, 300000L, false, "char doOpenHopQuaNoelHong");
            info = "Bạn nhận được 300.000 exp";
        } else {
            int[] idPo;
            int id;
            for (idPo = new int[]{73, 77}, id = idPo[Map.r.nextInt(idPo.length)]; id == this.nHopMut; id = idPo[Map.r.nextInt(idPo.length)]) {
            }
            if (System.currentTimeMillis() - this.timeDropCake1 >= 0L && this.nBinhTra > 0) {
                if (Map.r.nextInt(100) < 50) {
                    id = this.nHopMut;
                    --this.nBinhTra;
                }
                this.setTimeCake1();
            }
            final int[] potions = this.potions;
            final int n = id;
            ++potions[n];
            boolean nhansam = false;
            if (this.potions[74] > 0 && this.potions[77] > 0) {
                final int[] potions2 = this.potions;
                final int n2 = 9;
                ++potions2[n2];
                final int[] potions3 = this.potions;
                final int n3 = 73;
                --potions3[n3];
                final int[] potions4 = this.potions;
                final int n4 = 77;
                --potions4[n4];
                nhansam = true;
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được nhân sâm khi sưu tầm đủ chữ noel"));
                } catch (final IOException ex) {
                }
            }
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            info = "Bạn nhận được 1 " + LoginHandler.PORTION_NAME[id] + (nhansam ? " và 1 nhân sâm" : "");
        }
        if (Map.r.nextInt(100) < 30) {
            final short[] id2 = {82, 93, 95, 84, 100};
            final int[] sl = {1, 500, 500, 1, 1};
            final int index = Map.r.nextInt(id2.length);
            final int[] potions5 = this.potions;
            final short n5 = id2[index];
            potions5[n5] += sl[index];
            info = String.valueOf(info) + " và " + sl[index] + " " + LoginHandler.PORTION_NAME[id2[index]];
        }
        this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        Database.instance.saveOrtherLog("", this.charname, info, "hopquahong");
    }
    public static int[] ID_MAT_NA = new int[]{745, 746, 747, 748, 749};

    public void doOpenTuiQuaMayManNoel() {
        if (this.isFullInventory()) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy", ""));
            return;
        }
        Map.addXpCharEvent(this, 50000L, false, "char doOpenHopQuaVang");
        final int[] per = {500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 100, 250, 250, 0, 250, 100, 200, 200, 200, 100};
        final int[] idgift = {GemTemplate.DA_TIEN_GIAI, GemTemplate.XUONG_C3, GemTemplate.XUONG_C4, GemTemplate.XUONG_C5, 35, 114, 113, 112, 93, 95, -1, -2, 35, -3, 117, -4, 676, 677, 84, 99};
        final int[] typegift = {6, 6, 6, 6, 4, 4, 4, 4, 4, 4, -1, -2, 4, -3, 4, -4, 3, 3, 4, -5};
        final int[] soluongmax = {1, 1, 1, 1, 1, 1, 1, 1, 50, 50, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        final int[] soluongmin = {1, 1, 1, 1, 1, 1, 1, 1, 50, 50, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        String info = "Chúc mừng bạn nhận được ";
        final Hashtable<Integer, Integer> indexReceive = new Hashtable<Integer, Integer>();
        for (int i = 0; i < 3; ++i) {
            int index = getIndexRandom(per);
            for (int count = 0; count++ < 100 && indexReceive.get(index) != null; index = getIndexRandom(per)) {
            }
            indexReceive.put(index, index);
            if (typegift[index] == -3) {
                final int time = Map.r.nextInt(2) + 1;
                this.createHeo(time, false);
                info = String.valueOf(info) + " đường khang, ";
            } else if (typegift[index] == -5) {
                int[] valuemn = {5, 5, 10, 30, 50};
                int randomMN = Map.r.nextInt(valuemn.length);
                Item newitem = Map.doCreateMatNa(this, 10080, ID_MAT_NA[randomMN], randomMN, valuemn[randomMN]);
                this.sendMessage(MessageCreator.createServerAlertMessage("Nhận " + newitem.getName() + " 7 ngày", ""));
            } else if (typegift[index] == -4) {
                final int[] minute = {1440};
                final Item newItem = ItemLuckyDraw.createtemCoat(750, this, Map.r.nextInt(2), true, minute[Map.r.nextInt(minute.length)]);
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                info = String.valueOf(info) + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
                this.sendMessage(MessageCreator.createMsgChat(this.id, info));
            } else if (typegift[index] == -2) {
                final int time = Map.r.nextInt(2) + 1;
                final Item it = createPhiPhongMaxBaoKich(this, Map.r.nextInt(2), time * 24 * 60, null, false, false, 0, true);
                info = String.valueOf(info) + it.getName() + ", ";
            } else if (typegift[index] == -1) {
                final int[] idthe = {678, 679, 680};
                int iditem = idthe[Map.r.nextInt(idthe.length)];
                if (this.lvDetail.lv < 60) {
                    iditem = 678;
                } else if (this.lvDetail.lv < 70) {
                    iditem = 679;
                } else {
                    iditem = 680;
                }
                Map.doAddItemIsNotWearing(this, iditem, 0);
                final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(0).get(new Integer(iditem));
                info = String.valueOf(info) + template.name + ", ";
            } else if (typegift[index] == 6) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                this.doAddGemItem(idgift[index], soluong, true);
                info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgift[index]].name + ", ";
                this.sendMessage(MessageCreator.createCharGemItem(this));
            } else if (typegift[index] == 4) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final int[] potions = this.potions;
                final int n = idgift[index];
                potions[n] += soluong;
                info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]] + ", ";
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                if (idgift[index] == 9) {
                    try {
                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được nhân sâm khi mở bao lì xì"));
                    } catch (final IOException ex) {
                    }
                }
            } else if (typegift[index] == 3) {
                if (this.isFullInventory()) {
                    final int xu = 5000 + Map.r.nextInt(100000);
                    this.addXu(xu, "char 8");
                    info = String.valueOf(info) + xu + " xu" + ", ";
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                } else {
                    final ItemTemplates template2 = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                    Item newItem = null;
                    newItem = new Item(template2, false, 0, 0, template2.id);
                    newItem.id = this.getIDItem();
                    newItem.owner = this.charDBID;
                    newItem.level = newItem.getTemplate().level;
                    this.iItems.add(newItem);
                    newItem.identify = this.charDBID;
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                    info = String.valueOf(info) + newItem.getName();
                }
            }
        }
        this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        Database.instance.saveOrtherLog("", this.charname, info, "hopquamayman");
    }

    public void doDoiQuaDacBietNoel() {
        if (this.isFullInventory()) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Hành trang đầy", ""));
            return;
        }
        for (int i = 0; i < GemTemplate.ID_NGOC_RONG.length; ++i) {
            this.delGem(GemTemplate.ID_NGOC_RONG[i], 1, true);
        }
        final int[] soluongmax = {3, 10, 100, 100, 5, 1, 1, 1, 1, 5, 20, 20, 20, 10, 100, 1, 10, 1, 1, 1};
        final int[] soluongmin = {1, 5, 50, 50, 3, 1, 1, 1, 1, 3, 10, 10, 10, 5, 50, 1, 5, 1, 1, 1};
        final int[] per = {500, 500, 500, 500, 500, 500, 500, 10, 10, 500, 250, 250, 250, 150, 350, 50, 150, 150, 150, 150};
        final int[] idgift = {69, 100, 93, 95, 58, 35, GemTemplate.NGOC_HUYEN_MINH_4, GemTemplate.LKD_30, GemTemplate.LKD_35, GemTemplate.XUONG_C1, GemTemplate.BOT_TRANG, GemTemplate.BOT_XANH, GemTemplate.BOT_DO, GemTemplate.BOT_XANH_LA, GemTemplate.DA_MAY_MAN_C3, GemTemplate.DA_NGU_HOP_6 - 4, -5, -6, -7};
        final int[] typegift = {4, 4, 4, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, -4, -5, -6, -7};
        final int sl = Map.r.nextInt(3) + 3;
        String info = "Chúc mừng bạn nhận được ";
        for (int j = 0; j < sl; ++j) {
            final int index = getIndexRandom(per);
            if (typegift[index] == -7) {
                this.doCreatePetBachThu(14400, false);
                info = String.valueOf(info) + " pet bạch thử, ";
            } else if (typegift[index] == -6) {
                final Item it = Map.doBuyModelClothe(this, this.isCharNam() ? 725 : 726, 10, 1);
                info = String.valueOf(info) + " " + it.getName();
            } else if (typegift[index] == -5) {
                final int time = Map.r.nextInt(2) + 1;
                this.createHeo(time, false);
                info = String.valueOf(info) + " đuong khang, ";
            } else if (typegift[index] == -4) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final int indexTB = Map.r.nextInt(GemTemplate.ID_TU_BINH.length);
                final int idtubinh = GemTemplate.ID_TU_BINH[indexTB][Map.r.nextInt(GemTemplate.ID_TU_BINH[indexTB].length)];
                this.doAddGemItem(idtubinh, soluong, false);
                info = String.valueOf(info) + soluong + " " + Map.gemTemplate[idtubinh].name + ", ";
                this.sendMessage(MessageCreator.createCharGemItem(this));
            } else if (typegift[index] == 6) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                this.doAddGemItem(idgift[index], soluong, false);
                info = String.valueOf(info) + soluong + " " + Map.gemTemplate[idgift[index]].name + ", ";
                this.sendMessage(MessageCreator.createCharGemItem(this));
            } else if (typegift[index] == 4) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final int[] potions = this.potions;
                final int n = idgift[index];
                potions[n] += soluong;
                info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]] + ", ";
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            } else if (typegift[index] == 3) {
                if (this.isFullInventory()) {
                    final int xu = 5000 + Map.r.nextInt(100000);
                    this.addXu(xu, "char 9");
                    info = String.valueOf(info) + xu + " xu" + ", ";
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                } else {
                    final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                    Item newItem = null;
                    newItem = new Item(template, false, 0, 0, template.id);
                    newItem.id = this.getIDItem();
                    newItem.owner = this.charDBID;
                    newItem.level = newItem.getTemplate().level;
                    this.iItems.add(newItem);
                    newItem.identify = this.charDBID;
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                    info = String.valueOf(info) + newItem.getName();
                }
            }
        }
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
        this.sendMessage(MessageCreator.createCharGemItem(this));
        this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        Database.instance.saveOrtherLog("", this.charname, info, "quadacbiet");
    }

    public void doDoiQuaDacBietNoel2020() {
        if (this.countSlotInventory() < 5) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Hành trang phải còn tối thiếu 3 ô trống mới có thể đổi quà", ""));
            return;
        }
        for (int i = 0; i < GemTemplate.ID_NGOC_RONG.length; ++i) {
            this.delGem(GemTemplate.ID_NGOC_RONG[i], 1, true);
        }
        final int[] soluongmax = {3, 10, 100, 100, 5, 1, 1, 1, 1, 5, 20, 20, 20, 10, 100, 1, 10, 1, 1, 1, 20, 3, 1, 1};
        final int[] soluongmin = {1, 5, 50, 50, 3, 1, 1, 1, 1, 3, 10, 10, 10, 5, 50, 1, 5, 1, 1, 1, 10, 1, 1, 1};
        final int[] per = {500, 500, 500, 500, 500, 500, 500, 10, 10, 500, 250, 250, 250, 150, 350, 50, 0, 150, 150, 0, 500, 50, 500, 150};
        final int[] idgift = {69, 100, 93, 95, 58, 35, GemTemplate.NGOC_HUYEN_MINH_4, GemTemplate.LKD_30, GemTemplate.LKD_35, GemTemplate.XUONG_C1, GemTemplate.BOT_TRANG, GemTemplate.BOT_XANH, GemTemplate.BOT_DO, GemTemplate.BOT_XANH_LA, GemTemplate.DA_MAY_MAN_C3, GemTemplate.DA_NGU_HOP_6, -4, -5, -6, -7, 19, -8, 77, -9};
        final int[] typegift = {4, 4, 4, 4, 4, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, -4, -5, -6, -7, 4, -8, 4, -9};
        final int sl = Map.r.nextInt(3) + 3;
        String info = "Chúc mừng bạn nhận được ";
        final Hashtable<Integer, Integer> indexReceive = new Hashtable<Integer, Integer>();
        for (int j = 0; j < sl; ++j) {
            int index = getIndexRandom(per);
            for (int count = 0; count++ < 100 && indexReceive.get(index) != null; index = getIndexRandom(per)) {
            }
            indexReceive.put(index, index);
            if (typegift[index] == -8) {
                final int lk = Map.r.nextInt(3) + 1;
                this.addLuongLock(lk);
                info = String.valueOf(info) + lk + " lượng khoá, ";
            } else if (typegift[index] == -9) {
                final int[] minute = {4320};
                final Item newItem = ItemLuckyDraw.createtemCoat(750, this, Map.r.nextInt(2), true, minute[Map.r.nextInt(minute.length)]);
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                info = String.valueOf(info) + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
                this.sendMessage(MessageCreator.createMsgChat(this.id, info));
            } else if (typegift[index] == -7) {
                this.doCreatePetBachThu(14400, false);
                info = String.valueOf(info) + " pet bạch thử, ";
            } else if (typegift[index] == -6) {
                final Item it = Map.doBuyModelClothe(this, this.isCharNam() ? 725 : 726, 10, 1);
                info = String.valueOf(info) + " " + it.getName();
            } else if (typegift[index] == -5) {
                final int time = 3;
                this.createHeo(time, false);
                info = String.valueOf(info) + " đương khang, ";
            } else if (typegift[index] == -4) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final int indexTB = Map.r.nextInt(GemTemplate.ID_TU_BINH.length);
                final int idtubinh = GemTemplate.ID_TU_BINH[indexTB][Map.r.nextInt(GemTemplate.ID_TU_BINH[indexTB].length)];
                this.doAddGemItem(idtubinh, soluong, false);
                info = String.valueOf(info) + soluong + " " + Map.gemTemplate[idtubinh].name + ", ";
                this.sendMessage(MessageCreator.createCharGemItem(this));
            } else if (typegift[index] == 6) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                this.doAddGemItem(idgift[index], soluong, false);
                info = String.valueOf(info) + soluong + " " + Map.gemTemplate[idgift[index]].name + ", ";
                this.sendMessage(MessageCreator.createCharGemItem(this));
            } else if (typegift[index] == 4) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final int[] potions = this.potions;
                final int n = idgift[index];
                potions[n] += soluong;
                info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]] + ", ";
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            } else if (typegift[index] == 3) {
                if (this.isFullInventory()) {
                    final int xu = 5000 + Map.r.nextInt(100000);
                    this.addXu(xu, "char 9");
                    info = String.valueOf(info) + xu + " xu" + ", ";
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                } else {
                    final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                    Item newItem = null;
                    newItem = new Item(template, false, 0, 0, template.id);
                    newItem.id = this.getIDItem();
                    newItem.owner = this.charDBID;
                    newItem.level = newItem.getTemplate().level;
                    this.iItems.add(newItem);
                    newItem.identify = this.charDBID;
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                    info = String.valueOf(info) + newItem.getName();
                }
            }
        }
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
        this.sendMessage(MessageCreator.createCharGemItem(this));
        this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        Database.instance.saveOrtherLog("", this.charname, info, "quadacbiet");
        Database.instance.saveLogThongKeChiTieu("DOI 7 VIEN NGOC RONG", 0, 1, 1L, "ngoc");
    }

    public static void initINfoCheckTrungItemVV() {
        if (TeamServer.isServerKinhMon()) {
            Char.numberRandom = Map.r.nextInt(62) + 10;
        } else if (TeamServer.isServerHoangMy()) {
            Char.numberRandom = Map.r.nextInt(11) + 10;
        } else if (TeamServer.isServerHoaLu()) {
            Char.numberRandom = Map.r.nextInt(51) + 100;
        } else if (TeamServer.isServerTrangTien()) {
            Char.numberRandom = Map.r.nextInt(23) + 10;
        } else if (TeamServer.isServerDaiLa()) {
            Char.numberRandom = Map.r.nextInt(43) + 10;
        } else if (TeamServer.isServerLocal()) {
            Char.numberRandom = Map.r.nextInt(5) + 10;
        }
    }

    public static boolean checkReceiveTrungLinhThuRuongBatbao(final Char p) {
        ++Char.total_open;
        System.out.println("check trung trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open);
        if (TeamServer.isServerKinhMon()) {
            if (Char.total_open == Char.numberRandom) {
                Database.instance.saveOrtherLog("", p.charname, "check trung trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                if (Char.total_open <= 150) {
                    Char.numberRandom = 151 + Map.r.nextInt(300);
                } else if (Char.total_open <= 450) {
                    Char.numberRandom = 451 + Map.r.nextInt(182);
                }
                Database.instance.saveOrtherLog("", p.charname, "random lai so trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                try {
                    Map.sendAllCharServer(-2, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.charname + " đã trúng mặt nạ vĩnh viễn"));
                } catch (final IOException ex) {
                }
                return true;
            }
        } else if (TeamServer.isServerHoangMy()) {
            if (Char.total_open == Char.numberRandom) {
                Database.instance.saveOrtherLog("", p.charname, "check trung trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                if (Char.total_open <= 150) {
                    Char.numberRandom = 151 + Map.r.nextInt(300);
                } else if (Char.total_open <= 450) {
                    Char.numberRandom = 451 + Map.r.nextInt(182);
                }
                Database.instance.saveOrtherLog("", p.charname, "random lai so trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                try {
                    Map.sendAllCharServer(-2, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.charname + " đã trúng mặt nạ vĩnh viễn"));
                } catch (final IOException ex2) {
                }
                return true;
            }
        } else if (TeamServer.isServerHoaLu()) {
            if (Char.total_open == Char.numberRandom) {
                Database.instance.saveOrtherLog("", p.charname, "check trung trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                if (Char.total_open <= 150) {
                    Char.numberRandom = 151 + Map.r.nextInt(300);
                } else if (Char.total_open <= 450) {
                    Char.numberRandom = 451 + Map.r.nextInt(182);
                }
                Database.instance.saveOrtherLog("", p.charname, "random lai so trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                try {
                    Map.sendAllCharServer(-2, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.charname + " đã trúng mặt nạ vĩnh viễn"));
                } catch (final IOException ex3) {
                }
                return true;
            }
        } else if (TeamServer.isServerTrangTien()) {
            if (Char.total_open == Char.numberRandom) {
                Database.instance.saveOrtherLog("", p.charname, "check trung trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                if (Char.total_open <= 150) {
                    Char.numberRandom = 151 + Map.r.nextInt(300);
                } else if (Char.total_open <= 450) {
                    Char.numberRandom = 451 + Map.r.nextInt(182);
                }
                Database.instance.saveOrtherLog("", p.charname, "random lai so trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                try {
                    Map.sendAllCharServer(-2, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.charname + " đã trúng mặt nạ vĩnh viễn"));
                } catch (final IOException ex4) {
                }
                return true;
            }
        } else if (TeamServer.isServerDaiLa()) {
            if (Char.total_open == Char.numberRandom) {
                Database.instance.saveOrtherLog("", p.charname, "check trung trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                if (Char.total_open <= 150) {
                    Char.numberRandom = 151 + Map.r.nextInt(300);
                } else if (Char.total_open <= 450) {
                    Char.numberRandom = 451 + Map.r.nextInt(182);
                }
                Database.instance.saveOrtherLog("", p.charname, "random lai so trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                try {
                    Map.sendAllCharServer(-2, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.charname + " đã trúng mặt nạ vĩnh viễn"));
                } catch (final IOException ex5) {
                }
                return true;
            }
        } else if (TeamServer.isServerLocal()) {
            if (Char.total_open == Char.numberRandom) {
                Database.instance.saveOrtherLog("", p.charname, "check trung trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                System.err.println("TRUNG MAT NA VV NE " + Char.numberRandom + " total open " + Char.total_open);
                if (Char.total_open <= 15) {
                    Char.numberRandom = 16 + Map.r.nextInt(20);
                } else if (Char.total_open <= 35) {
                    Char.numberRandom = 36 + Map.r.nextInt(30);
                }
                Database.instance.saveOrtherLog("", p.charname, "random lai so trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
                try {
                    Map.sendAllCharServer(-2, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + p.charname + " đã trúng mặt nạ vĩnh viễn"));
                } catch (final IOException ex6) {
                }
                return true;
            }
        } else if (Char.total_open == Char.numberLucky || Char.total_open == Char.numberRandom || Char.total_open == 500 || Char.total_open == 1500 || Char.total_open == 3500) {
            Database.instance.saveOrtherLog("", p.charname, "check trung trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
            if (Char.total_open == 500) {
                Char.numberRandom = 501 + Map.r.nextInt(1000);
            } else if (Char.total_open == 1500) {
                Char.numberRandom = 1501 + Map.r.nextInt(2000);
            } else if (Char.total_open == 3500) {
                Char.numberRandom = 3501 + Map.r.nextInt(1500);
            }
            Database.instance.saveOrtherLog("", p.charname, "random lai so trung: nblucky: " + Char.numberLucky + ", nbrandom= " + Char.numberRandom + ", totalopen= " + Char.total_open, "iftrung");
            return false;
        }
        return false;
    }

    public void doOpenRuongBatBao() {
        if (!TeamServer.isServerHoaLu() && !TeamServer.isServerLocal()) {
            return;
        }
        try {
            final int[] trungthu = {86, 70, 74, 72};
            final int[] idgift = {Animal.PHUONG_HOANG, Animal.PHUONG_HOANG_BANG, Animal.HEO, Animal.NGUA_XUONG, 119, -10001, -15001, -20001, -30001, -2};
            final int[] typegift = {-3, -3, -3, -3, 4, -10001, -15001, -20001, -30001, -2};
            final int[] pc = {500, 500, 500, 500, 500, 100, 100, 100, 100, 500};
            final int index = getIndexRandom(pc);
            final boolean istrungtrung = checkReceiveTrungLinhThuRuongBatbao(this);
            System.out.println("index trung " + index);
            if (istrungtrung) {
                final int idtrung = trungthu[Map.r.nextInt(trungthu.length)];
                final int[] potions = this.potions;
                final int n = idtrung;
                ++potions[n];
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + LoginHandler.PORTION_NAME[idtrung]));
                try {
                    Map.sendAllCharServer(MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + this.charname + " mở rương bát bảo trúng 1 " + LoginHandler.PORTION_NAME[idtrung]), -1);
                } catch (final IOException ex) {
                }
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                Database.instance.saveOrtherLog("", this.charname, "trung trung " + LoginHandler.PORTION_NAME[idtrung], "ruongbb");
            } else {
                String info = "nhan dc: ";
                if (typegift[index] == -10001 || typegift[index] == -15001 || typegift[index] == -20001 || typegift[index] == -30001) {
                    final int xu = Map.abs(idgift[index]) - 1;
                    this.addXu(xu, "ruong bb char");
                    info = String.valueOf(info) + xu + " xu";
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + xu + " xu"));
                } else if (typegift[index] == -2) {
                    final Item it = Map.doCreateHoVeLenh(this, 4320);
                    if (it != null) {
                        info = String.valueOf(info) + it.getName();
                        this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + it.getName()));
                    }
                } else if (typegift[index] == -3) {
                    final int[] d = {3, 5, 7};
                    final int day = d[Map.r.nextInt(d.length)];
                    if (idgift[index] == Animal.NGUA_XUONG) {
                        this.createNguaXuong(day, true);
                        info = String.valueOf(info) + " ngua xuong " + day + " ngay";
                    } else if (idgift[index] == Animal.HEO) {
                        this.createHeo(day, true);
                        info = String.valueOf(info) + " duong khang " + day + " ngay";
                    } else {
                        this.createPhuongHoang(day, idgift[index], true);
                    }
                } else if (typegift[index] == 4) {
                    final int[] potions2 = this.potions;
                    final int n2 = idgift[index];
                    ++potions2[n2];
                    info = String.valueOf(info) + " tien dan";
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được Tiên đan"));
                }
                Database.instance.saveOrtherLog("", this.charname, info, "ruongbb");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void doOpenQuaTet() {
        for (int i = 0; i < GemTemplate.ID_MATERIAL_LOW[5].length; ++i) {
            this.doAddGemItem(GemTemplate.ID_MATERIAL_LOW[5][i], 1, true);
        }
        this.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được nguyên liệu sơ cấp cấp 6 khoá mỗi loại 1 viên", ""));
        this.sendMessage(MessageCreator.createCharGemItem(this));
        Database.instance.saveOrtherLog("", this.charname, "mo hop qua tet, nhan dc nglsc 6 moi loai 1 vien", "quatet");
    }

    public void doOpenHopBanhTrungThuDacBiet() {
        Map.addXpCharEvent(this, 500000L, false, "char doOpenHopBanhTrungThuDacBiet");
        final int[] per = {500, 500, 0, 500, 500, 500, 500, 0, 500, 500, 500, 500, 500, 500, 500, 500, 500};
        final int[] idgift = {GemTemplate.XUONG_C5, GemTemplate.XUONG_C6, GemTemplate.LKD_40, 117, 82, 123, 100, -1, -2, 676, 677, -3, 93, 95, 13, 7, 136};
        final int[] typegift = {6, 6, 6, 4, 4, 4, 4, -1, -2, 3, 3, -3, 4, 4, 4, 4, 4};
        final int[] soluongmax = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        final int[] soluongmin = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        if (this.isFullInventory()) {
            return;
        }
        String info = "Chúc mừng bạn nhận được: ";
        final int index = getIndexRandom(per);
        if (typegift[index] == -1) {
            final int[] minute = {-1};
            final Item newItem = ItemLuckyDraw.createItemCoat(this, Map.r.nextInt(2), false, minute[Map.r.nextInt(minute.length)]);
            this.iItems.add(newItem);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
            info = String.valueOf(info) + newItem.getName() + " vĩnh viễn";
            this.sendMessage(MessageCreator.createMsgChat(this.id, info));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        } else if (typegift[index] == -2) {
            final int[] minute = {1440, 4320, 7200, 10080};
            final Item newItem = ItemLuckyDraw.createItemCoat(this, Map.r.nextInt(2), false, minute[Map.r.nextInt(minute.length)]);
            this.iItems.add(newItem);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
            info = String.valueOf(info) + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
            this.sendMessage(MessageCreator.createMsgChat(this.id, info));
        } else if (typegift[index] == 6) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            this.doAddGemItem(idgift[index], soluong, true);
            info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgift[index]].name;
            this.sendMessage(MessageCreator.createCharGemItem(this));
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (typegift[index] == 4) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            final int[] potions = this.potions;
            final int n = idgift[index];
            potions[n] += soluong;
            info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            if (idgift[index] == 9) {
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được nhân sâm khi mở bao lì xì"));
                } catch (final IOException ex) {
                }
            }
        } else if (typegift[index] == 3) {
            if (this.isFullInventory()) {
                final int xu = 5000 + Map.r.nextInt(100000);
                this.addXu(xu, "char 10");
                info = String.valueOf(info) + xu + " xu";
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            } else {
                final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                Item newItem = null;
                newItem = new Item(template, false, 0, 0, template.id);
                newItem.id = this.getIDItem();
                newItem.owner = this.charDBID;
                newItem.level = newItem.getTemplate().level;
                this.iItems.add(newItem);
                newItem.identify = this.charDBID;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                info = String.valueOf(info) + newItem.getName();
            }
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        }
        Database.instance.saveOrtherLog("", this.charname, info, "ohqtt");
    }

    public void doOpenHopQuaTrungThu() {
        final int[] per = {500, 500, 500, 500, 500, 500, 500, 1, 500};
        final int[] idgift = {GemTemplate.DA_TIEN_GIAI, GemTemplate.XUONG_C6, GemTemplate.LKD_40, 81, 82, 123, 100, -1, -2};
        final int[] typegift = {6, 6, 6, 4, 4, 4, 4, -1, -2};
        final int[] soluongmax = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        final int[] soluongmin = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        if (this.isFullInventory()) {
            return;
        }
        String info = "Chúc mừng bạn nhận được: ";
        final int index = getIndexRandom(per);
        if (typegift[index] == -1) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            final int idgem = Map.r.nextInt(31) + 195;
            this.doAddGemItem(idgem, soluong, Map.r.nextInt(100) < 50);
            info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgem].name;
            this.sendMessage(MessageCreator.createCharGemItem(this));
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (typegift[index] == -2) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            final int idgem = Map.r.nextInt(31) + 195;
            this.doAddGemItem(idgem, soluong, Map.r.nextInt(100) < 50);
            info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgem].name;
            this.sendMessage(MessageCreator.createCharGemItem(this));
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        } else if (typegift[index] == 6) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            this.doAddGemItem(idgift[index], soluong, true);
            info = String.valueOf(info) + soluongmax[index] + " " + Map.gemTemplate[idgift[index]].name;
            this.sendMessage(MessageCreator.createCharGemItem(this));
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (typegift[index] == 4) {
            int soluong = soluongmin[index];
            if (soluongmin[index] < soluongmax[index]) {
                soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
            }
            final int[] potions = this.potions;
            final int n = idgift[index];
            potions[n] += soluong;
            info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            if (idgift[index] == 9) {
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được nhân sâm khi mở bao lì xì"));
                } catch (final IOException ex) {
                }
            }
        } else if (typegift[index] == 3) {
            if (this.isFullInventory()) {
                final int xu = 5000 + Map.r.nextInt(100000);
                this.addXu(xu, "char 10");
                info = String.valueOf(info) + xu + " xu";
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            } else {
                final ItemTemplates template = (ItemTemplates) Map.itemTemplates.get(5).get(idgift[index]);
                Item newItem = null;
                newItem = new Item(template, false, 0, 0, template.id);
                newItem.id = this.getIDItem();
                newItem.owner = this.charDBID;
                newItem.level = newItem.getTemplate().level;
                this.iItems.add(newItem);
                newItem.identify = this.charDBID;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
                info = String.valueOf(info) + newItem.getName();
            }
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        }
        Database.instance.saveOrtherLog("", this.charname, info, "ohqtt");
    }

    public void doOpenQuaHe() {
        String info = "";
        final int pc = Map.r.nextInt(100);
        if (pc < 30) {
            final short[] arr = (Map.r.nextInt(100) < 50) ? GemTemplate.ID_MATERIAL_LOW[3] : GemTemplate.ID_MATERIAL_LOW[4];
            final int idgem = arr[Map.r.nextInt(arr.length)];
            this.doAddGemItem(idgem, 1, true);
            this.sendMessage(MessageCreator.createCharGemItem(this));
            info = "Nhận được " + Map.gemTemplate[idgem].name;
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        } else if (pc < 80 && !this.isFullInventory()) {
            final int[] id = {584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597};
            int lv = this.lvDetail.lv;
            if (lv < 35) {
                lv = 35;
            }
            final int idtemp = id[(lv - 35) / 5];
            final Item newItem = ItemLuckyDraw.createMaxItemCoatLucky(idtemp, this, Map.r.nextInt(2), false);
            newItem.dateCreate = getDayOpen(0L);
            newItem.timeLoan = System.currentTimeMillis();
            newItem.minuteExist = 1440;
            this.iItems.add(newItem);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
            this.sendMessage(MessageCreator.createServerAlertMessage("Nhận được " + newItem.getName(), ""));
            info = String.valueOf(newItem.getDBInfo()) + "|" + newItem.getAttribute() + "_" + newItem.level + " thoi han " + 1440;
        } else {
            final short[] arr = (Map.r.nextInt(100) < 50) ? GemTemplate.ID_MATERIAL_HIGHT[3] : GemTemplate.ID_MATERIAL_HIGHT[4];
            final int idgem = arr[Map.r.nextInt(arr.length)];
            this.doAddGemItem(idgem, 1, true);
            this.sendMessage(MessageCreator.createCharGemItem(this));
            info = "Nhận được " + Map.gemTemplate[idgem].name;
            this.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
        }
        Database.instance.saveOrtherLog("", this.charname, info, "ogsn");
    }

    public void doOpenAllLuckyBox() {
        if (this.gifLuckyBox.size() <= 2) {
            this.gifLuckyBox.removeAllElements();
            this.idOpen.removeAllElements();
            this.nSpecialBox = 2;
            return;
        }
        String infoGif = "";
        while (this.gifLuckyBox.size() > 2) {
            if (Map.r.nextInt(100) < 2 && this.nSpecialBox > 0) {
                final byte index = (byte) (Map.r.nextInt(this.nSpecialBox) + (this.gifLuckyBox.size() - this.nSpecialBox));
                final InfoGifLucky info = this.gifLuckyBox.get(index);
                infoGif = String.valueOf(infoGif) + info.removeGif(this) + " ";
                this.gifLuckyBox.remove(info);
                --this.nSpecialBox;
            } else {
                final byte index = (byte) Map.r.nextInt(this.gifLuckyBox.size() - this.nSpecialBox);
                final InfoGifLucky info = this.gifLuckyBox.get(index);
                infoGif = String.valueOf(infoGif) + info.removeGif(this) + " ";
                this.gifLuckyBox.remove(info);
            }
        }
        this.sendMessage(MessageCreator.createLuckyBox((byte) 0, null, 2));
        if (!infoGif.trim().equals("")) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng bạn đã nhận được " + infoGif, ""));
        } else {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc bạn may mắn lần sau", ""));
        }
        this.gifLuckyBox.removeAllElements();
        this.idOpen.removeAllElements();
        this.nSpecialBox = 2;
    }

    public Vector<String> getInfoGifLucky() {
        final Vector<String> info = new Vector<String>();
        for (int i = 0; i < this.gifLuckyBox.size(); ++i) {
            final String st = this.gifLuckyBox.get(i).getInfoGif();
            if (!st.equals("")) {
                if (info.size() == 0) {
                    info.add(st);
                } else {
                    info.insertElementAt(st, Map.r.nextInt(info.size()));
                }
            }
        }
        return info;
    }

    public void doBuyNumber(final byte type, final byte number) {
        if (this.xu < 1000000L) {
            return;
        }
        this.xu -= 1000000L;
        final Vector<Veso> temp = this.vesoxu;
        if (this.dayOpenXoso.equals("")) {
            final int time = Calendar.getInstance().get(11);
            if (time >= 17 && time <= 23) {
                this.dayOpenXoso = getDayOpen(32400000L);
            } else {
                this.dayOpenXoso = getDayOpen(0L);
            }
        }
        for (byte size = (byte) temp.size(), i = 0; i < size; ++i) {
            final Veso vs = temp.get(i);
            if (vs.number == number) {
                vs.addNumber();
                Database.instance.saveVeso(this);
                return;
            }
        }
        final Veso vs2 = new Veso();
        vs2.number = number;
        vs2.addNumber();
        temp.add(vs2);
        Database.instance.saveOrtherLog("", this.charname, "Mua vé số " + number, "bxoso");
        Database.instance.saveVeso(this);
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
    }

    public void sendInfoVeso(final byte type) {
        String info = "";
        final Vector<Veso> temp = this.vesoxu;
        final byte size = (byte) temp.size();
        if (size == 0) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chưa có vé.", ""));
            return;
        }
        info = "Ngày xổ: " + this.dayOpenXoso + "  ";
        for (byte i = 0; i < size; ++i) {
            final Veso vs = temp.get(i);
            info = String.valueOf(info) + "vé " + vs.number + " sl " + vs.soluong + ", ";
        }
        this.sendMessage(MessageCreator.createServerAlertMessage(info.substring(0, info.length() - 2), ""));
    }

    public static int getCurrentHour() {
        final Calendar cl = Calendar.getInstance();
        final int iHour = cl.get(11);
        final int iMinute = cl.get(12);
        return iHour;
    }

    public static String getDayOpen(final long time) {
        return UserLogger.dateFormat.format(new Date(System.currentTimeMillis() + time));
    }

    public static String getYear(final long time) {
        return UserLogger.dateYearFormat.format(new Date(System.currentTimeMillis() + time));
    }

    public static String getMonth(final long time) {
        return UserLogger.dateMonthFormat.format(new Date(System.currentTimeMillis() + time));
    }

    public static String getDay(final long time) {
        return UserLogger.dateDayFormat.format(new Date(System.currentTimeMillis() + time));
    }

    public static String getDayTime(final long time) {
        final long t = System.currentTimeMillis() + time;
        return Database.timeFormat.format(new Date(t));
    }

    public static String getDayTimeByTime(final long time) {
        return Database.timeFormat.format(new Date(time));
    }

    public void doOpenNumber() {
        final int time = Calendar.getInstance().get(11);
        if (!this.dayOpenXoso.equals("")) {
            if (this.dayOpenXoso.equals(getDayOpen(0L))) {
                if (time >= 17 && time <= 23) {
                    if (Map.winNumber == -1) {
                        Xoso.selectWinNumber();
                    }
                    final String allNumber = this.allNumberBuy();
                    while (this.vesoxu.size() > 0) {
                        final Veso vs = this.vesoxu.remove(0);
                        if (vs.number == Map.winNumber) {
                            long win = 70000000 * vs.soluong;
                            win -= win * 10L / 100L;
                            this.xu += win;
                            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng bạn đã mua " + vs.soluong + " số " + vs.number + " và trúng được " + win + " xu trong chương trình may mắn đầu năm. Thuế 10%", ""));
                            Database.instance.saveOrtherLog("", this.charname, String.valueOf(win) + "_" + this.xu + "_" + allNumber, "txs");
                            this.vesoxu.removeAllElements();
                            Database.instance.saveVeso(this);
                            this.dayOpenXoso = "";
                            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                            try {
                                Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Chúc mừng " + this.charname + " đã trúng được " + win + " xu trong chương trình may mắn đầu năm"));
                            } catch (final Exception ex) {
                            }
                            return;
                        }
                    }
                    try {
                        this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Vé không trúng thưởng. Số trúng là số " + Map.winNumber));
                    } catch (final IOException ex2) {
                    }
                    this.dayOpenXoso = "";
                    Database.instance.saveOrtherLog("", this.charname, String.valueOf(this.xu) + "_" + allNumber, "fxs");
                    Database.instance.saveVeso(this);
                }
            } else {
                Database.instance.saveOrtherLog("", this.charname, this.allNumberBuy(), "hethanXS");
                try {
                    this.sendMessage(MessageCreator.createServerAlertAutoOffMessage("Vé bạn mua hôm qua đã hết hạn."));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                this.vesoxu.removeAllElements();
                this.dayOpenXoso = "";
                Database.instance.saveVeso(this);
            }
        }
    }

    public void doSwitch2Old() {
        if (this.vesoxu.size() > 0) {
            for (byte i = 0; i < this.vesoxu.size(); ++i) {
                this.vesoCu.add(this.vesoxu.get(i));
            }
        }
        this.vesoxu.removeAllElements();
        Database.instance.saveVeso(this);
    }

    public static boolean timeBuy() {
        final Calendar cl = Calendar.getInstance();
        final int iHour = cl.get(11);
        return iHour >= 18 || iHour < 16;
    }

    public String allNumberBuy() {
        String info = "";
        final Vector<Veso> temp = this.vesoxu;
        if (temp.size() == 0) {
            return "";
        }
        for (byte size = (byte) temp.size(), i = 0; i < size; ++i) {
            final Veso vs = temp.get(i);
            info = String.valueOf(info) + vs.number + "," + vs.soluong + ",";
        }
        return String.valueOf(info.substring(0, info.length() - 1)) + "|" + this.dayOpenXoso;
    }

    public String oldNumber() {
        String info = "";
        final Vector<Veso> temp = this.vesoCu;
        if (this.vesoCu.size() == 0) {
            return "";
        }
        for (byte size = (byte) temp.size(), i = 0; i < size; ++i) {
            final Veso vs = temp.get(i);
            info = String.valueOf(info) + vs.number + "," + vs.soluong + ",";
        }
        return info.equals("") ? info : info.substring(0, info.length() - 1);
    }

    public void doReceiveGif35(final int magic) {
        final byte[] soluong = {(byte) ((magic == 0) ? 0 : 4), (byte) (4 + ((magic == 0) ? 4 : 0)), 4, (byte) (2 + ((magic == 0) ? 2 : 0)), (byte) ((magic == 0) ? 0 : 2), 2, 0, 0};
        switch (this.charClass) {
            case 0:
            case 1:
            case 3: {
                final byte[] array = soluong;
                final int n = 6;
                array[n] += 4;
                final byte[] array2 = soluong;
                final int n2 = 4;
                array2[n2] += 2;
                break;
            }
            case 2:
            case 4: {
                final byte[] array3 = soluong;
                final int n3 = 0;
                array3[n3] += 4;
                final byte[] array4 = soluong;
                final int n4 = 7;
                array4[n4] += 2;
                break;
            }
        }
        String info = "";
        for (byte i = 0; i < Char.idGif35.length; ++i) {
            if (soluong[i] > 0) {
                info = String.valueOf(info) + soluong[i] + " " + Map.gemTemplate[Char.idGif35[i]].name + " ";
                this.doAddGemItem(Char.idGif35[i], soluong[i], true);
            }
        }
        this.gif35 = 1;
        this.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + info.trim(), ""));
        Database.instance.saveOrtherLog("", this.charname, info, "gif35");
        this.sendMessage(MessageCreator.createCharGemItem(this));
    }

    public void doReceiveGif35Offline(final int magic) {
        final byte[] soluong = {(byte) ((magic == 0) ? 0 : 4), (byte) (4 + ((magic == 0) ? 4 : 0)), 4, (byte) (2 + ((magic == 0) ? 2 : 0)), (byte) ((magic == 0) ? 0 : 2), 2, 0, 0};
        switch (this.charClass) {
            case 0:
            case 1:
            case 3: {
                final byte[] array = soluong;
                final int n = 6;
                array[n] += 4;
                final byte[] array2 = soluong;
                final int n2 = 4;
                array2[n2] += 2;
                break;
            }
            case 2:
            case 4: {
                final byte[] array3 = soluong;
                final int n3 = 0;
                array3[n3] += 4;
                final byte[] array4 = soluong;
                final int n4 = 7;
                array4[n4] += 2;
                break;
            }
        }
        String info = "";
        for (byte i = 0; i < Char.idGif35.length; ++i) {
            if (soluong[i] > 0) {
                info = String.valueOf(info) + soluong[i] + " " + Map.gemTemplate[Char.idGif35[i]].name + " ";
                this.doAddGemItem(Char.idGif35[i], soluong[i], true);
            }
        }
        this.doAddGemItem(240, 10, true);
        this.doAddGemItem(154, 5, true);
        info = String.valueOf(info) + " và 10 ngọc huyền minh cấp 6";
        info = String.valueOf(info) + " và 5 đá ngũ hợp tinh khiết cấp 6";
        this.gifMoonFestival = false;
        this.sendMessage(MessageCreator.createServerAlertMessage("Bạn nhận được " + info.trim(), ""));
        Database.instance.saveOrtherLog("", this.charname, info, "gifoffline");
        this.sendMessage(MessageCreator.createCharGemItem(this));
    }

    public void addLockGem(final int id, final int soluong) {
        if (this.listGemitem[id] == 0 && this.listGemitemLock[id] == 0) {
            final GemItem g = new GemItem(id);
            g.ownerId = this.charDBID;
            g.realId = this.getIDItem();
            this.gemItem.add(g);
        }
        final int[] listGemitemLock = this.listGemitemLock;
        listGemitemLock[id] += soluong;
        final int[] allGemGetLock = this.allGemGetLock;
        allGemGetLock[id] += soluong;
    }

    public boolean checkEnoughtGem(final int idGem, final int sl, final int lock) {
        if (lock == 0) {
            return this.listGemitem[idGem] >= sl;
        }
        return this.listGemitemLock[idGem] >= sl;
    }

    public void doAddGemItem(final int id, final int soluong, final boolean lock) {
        if (this.listGemitem[id] == 0 && this.listGemitemLock[id] == 0) {
            final GemItem g = new GemItem(id);
            g.ownerId = this.charDBID;
            g.realId = this.getIDItem();
            this.gemItem.add(g);
        }
        if (lock) {
            final int[] listGemitemLock = this.listGemitemLock;
            listGemitemLock[id] += soluong;
            final int[] allGemGetLock = this.allGemGetLock;
            allGemGetLock[id] += soluong;
        } else {
            final int[] allGemGet = this.allGemGet;
            allGemGet[id] += soluong;
            final int[] listGemitem = this.listGemitem;
            listGemitem[id] += soluong;
        }
    }

    public void delGem(final int id, final int sl, final boolean lock) {
        if (lock) {
            final int[] listGemitemLock = this.listGemitemLock;
            listGemitemLock[id] -= sl;
            final int[] allGemUseLock = this.allGemUseLock;
            allGemUseLock[id] += sl;
            if (this.listGemitemLock[id] < 0) {
                this.listGemitemLock[id] = 0;
            }
        } else {
            final int[] listGemitem = this.listGemitem;
            listGemitem[id] -= sl;
            final int[] allGemUse = this.allGemUse;
            allGemUse[id] += sl;
            if (this.listGemitem[id] < 0) {
                this.listGemitem[id] = 0;
            }
        }
        if (this.listGemitem[id] <= 0 && this.listGemitemLock[id] <= 0) {
            this.listGemitem[id] = 0;
            this.listGemitemLock[id] = 0;
            for (int i = 0; i < this.gemItem.size(); ++i) {
                final GemItem g = this.gemItem.get(i);
                if (g.idGemTemplate == id) {
                    this.gemItem.remove(i);
                    this.ALL_ID_USE.remove(g.realId);
                    break;
                }
            }
        }
    }

    public void doAddGemItem(final int id) {
        if (this.listGemitem[id] == 0 && this.listGemitemLock[id] == 0) {
            final GemItem g = new GemItem(id);
            g.ownerId = this.charDBID;
            g.realId = this.getIDItem();
            this.gemItem.add(g);
        }
        final int[] listGemitem = this.listGemitem;
        ++listGemitem[id];
        final int[] allGemGet = this.allGemGet;
        ++allGemGet[id];
    }

    public void doAddSpecialItem(final int id, final int soluong) {
        final ShopTemplate item = Map.getShopTemplate(id);
        if (this.listSpItem[item.id] == 0) {
            final ShopTemplate spItem = new ShopTemplate();
            spItem.coppy(spItem, item);
            spItem.ownerId = this.charDBID;
            spItem.realId = this.getIDItem();
            this.specialItem.add(spItem);
        }
        final int[] listSpItem = this.listSpItem;
        final short id2 = item.id;
        listSpItem[id2] += soluong;
        this.sendMessage(MessageCreator.createCharSpecialItem(this));
    }

    public void doAddSpecialItem(final int id) {
        final ShopTemplate item = Map.getShopTemplate(id);
        if (this.listSpItem[item.id] == 0) {
            final ShopTemplate spItem = new ShopTemplate();
            spItem.coppy(spItem, item);
            spItem.ownerId = this.charDBID;
            spItem.realId = this.getIDItem();
            this.specialItem.add(spItem);
        }
        final int[] listSpItem = this.listSpItem;
        final short id2 = item.id;
        ++listSpItem[id2];
        this.sendMessage(MessageCreator.createCharSpecialItem(this));
    }

    public void doReceiveGifNap() {
    }

    public byte getIdModel() {
        if (this.itemVukhiThoiTrang != null && this.itemVukhiThoiTrang.isChoiLuaLanBienHinh()) {
            return 0;
        }
        if (this.wModel.wpModel != null && this.wModel.wpModel.isChoiLuaLanBienHinh()) {
            return 0;
        }
        return this.wModel.idModel;
    }

    public boolean hasWearingThanBinhWeapon() {
        // Check all wearing items
        for (Item item : this.wItems[0]) {
            if (item != null) {
                // Check if item is equipped
                if (item.place == Item.PLACE_WEARING) {
                    // Check if item is a weapon (type 3-7)
                    int type = item.getTemplate().type;
                    if (type >= 3 && type <= 7) {
                        // Check if item ID is in range 773-787
                        int id = item.getTemplate().id;
                        if (id >= 773 && id <= 787) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int[] getInfoVukhiThoiTrang() {
        final int[] a = {-1, -1, -1, 1};
//        String check ="??";
        if (this.itemVukhiThoiTrang != null) {
            a[0] = this.itemVukhiThoiTrang.getIdEff();
            a[1] = this.itemVukhiThoiTrang.getIndexDxy();
            a[2] = this.itemVukhiThoiTrang.getIdBigImgAvatar();

            if (a[2] == 823 || a[2] == 824 || a[2] == 825 || a[2] == 852 || a[2] == 853 ) {
                a[3] = 5;
            } else if (a[2] == 636 || a[2] == 684 || a[2] == 688) {
                a[3] = 4; 
            } else if (a[2] == 640 || a[2] == 641 || a[2] == 648 || a[2] == 652 || 
                      a[2] == 656 || a[2] == 660 || a[2] == 664 || a[2] == 668 || 
                      a[2] == 675 || a[2] == 679 || a[2] == 683 || a[2] == 692) {
                a[3] = 5; 
            } else if (a[2] == 638 || a[2] == 642 || a[2] == 650 || a[2] == 646 || 
                      a[2] == 654 || a[2] == 658 || a[2] == 662 || a[2] == 666 || 
                      a[2] == 670 || a[2] == 674 || a[2] == 678 || a[2] == 682 || 
                      a[2] == 686 || a[2] == 690 || a[2] == 694) {
                a[3] = 1; 
            } else {
                a[3] = 3; 
            }


//            check="1;";
        } else if(this.wModel.wpModel != null && this.wModel.wpModel.isNguyetLinhTruong()) { 
//            check="2;";
            a[0] = this.itemVukhiThoiTrang.getIdEff();
            a[1] = this.itemVukhiThoiTrang.getIndexDxy();
            a[2] = this.itemVukhiThoiTrang.getIdBigImgAvatar();
            a[3] = 3;
        
            
        } else if (this.wModel.wpModel != null && this.wModel.wpModel.isChoiLuaLanBienHinh()) {
//                        check="3;";

            a[0] = this.wModel.wpModel.getIdEff();
            a[1] = this.wModel.wpModel.getIndexDxy();
            a[2] = this.wModel.wpModel.getIdBigImgAvatar();
            a[3] = 3;
        } else if (this.hasWearingThanBinhWeapon()) {
            a[0] = this.getIdEff();
            a[1] = this.getIndexDxy();
            a[2] = this.getIdBigImgAvatar();
            if (a[2] == 823 || a[2] == 824 || a[2] == 825) {
                a[3] = 5;
            } else if (a[2] == 636 || a[2] == 684 || a[2] == 688) {
                a[3] = 4; 
            } else if (a[2] == 640 || a[2] == 641 || a[2] == 648 || a[2] == 652 || 
                      a[2] == 656 || a[2] == 660 || a[2] == 664 || a[2] == 668 || 
                      a[2] == 675 || a[2] == 679 || a[2] == 683 || a[2] == 692) {
                a[3] = 5; 
            } else if (a[2] == 638 || a[2] == 642 || a[2] == 650 || a[2] == 646 || 
                      a[2] == 654 || a[2] == 658 || a[2] == 662 || a[2] == 666 || 
                      a[2] == 670 || a[2] == 674 || a[2] == 678 || a[2] == 682 || 
                      a[2] == 686 || a[2] == 690 || a[2] == 694) {
                a[3] = 1; 
            } else {
                a[3] = 3; 
            }
        }
        
                             

        
        return a;
    }
    
    public int getIdEff() {
        if (this.hasWearingThanBinhWeapon()) {
            // Get the currently wearing weapon item
            Vector<Item> wearingItems = this.getCharWearing();
            for (Item item : wearingItems) {
                if (item != null && item.place == Item.PLACE_WEARING) {
                    // Check if item is a weapon (type 3-7)
                    int type = item.getTemplate().type;
                    if (type >= 3 && type <= 7) {
                        // Check if item ID is in range 773-787 (Than Binh weapons)
                        int id = item.getTemplate().id;
                        if (id >= 773 && id <= 787) {
                            return item.getTemplate().idEff + 8700;
                        }
                    }
                }
            }
        }
        return -1; // Return -1 if no Than Binh weapon is found
    }

    public int getIndexDxy() {
        return 0;
    }
    
    public int getIdBigImgAvatar() {
        if (this.hasWearingThanBinhWeapon()) {
            // Get the currently wearing weapon item
            Vector<Item> wearingItems = this.getCharWearing();
            for (Item item : wearingItems) {
                if (item != null && item.place == Item.PLACE_WEARING) {
                    // Check if item is a weapon (type 3-7)
                    int type = item.getTemplate().type;
                    if (type >= 3 && type <= 7) {
                        // Check if item ID is in range 773-787 (Than Binh weapons)
                        int id = item.getTemplate().id;
                        if (id >= 773 && id <= 787) {
                            return item.getTemplate().idBigImgAvatar; // Avatar ID for Than Binh weapons
                        }
                    }
                }
            }
        }
        return -1;
    }

    public int[] getIdBigAoThoiTrang() {
        final int[] a = {-1, -1, -1};
        
        // Kiểm tra Bát Cái Trang
      
            if (this.isBatGioi() && this.isBatCaiTrang()) {
                if (this.getLevelCaiTrang() >= 13) {
                    a[0] = 781;
                    a[1] = 12;
                    a[2] = 2;
                } else {
                    a[0] = 780;
                    a[1] = 12;
                    a[2] = 2;
                }
            } else if (this.isSaTang() && this.isBatCaiTrang()) {
                if (this.getLevelCaiTrang() >= 13) {
                    a[0] = 783;
                    a[1] = 12;
                    a[2] = 3;
                } else {
                    a[0] = 782;
                    a[1] = 12;
                    a[2] = 3;
                }
            }
        
        // Kiểm tra các loại trang phục khác
        else if (this.wModel.isCaiTaoSonTinh()) {
            a[0] = 831;
            a[1] = 10;
            a[2] = 2;
        }
        else if (this.wModel.isCaiTaoMiNuong()) {
            a[0] = 833;
            a[1] = 10;
            a[2] = 2;
        }
        else if (this.wModel.isTienNu()) {
            a[0] = Char.ID_BIG_AVT_AO_TIEN_NU;
            a[1] = 0;
            a[2] = 3;
        }
        else if (this.wModel.isTinhQuan()) {
            a[0] = Char.ID_BIG_AVT_AO_TINH_QUAN;
            a[1] = 1;
            a[2] = 1;
        }
        else if (this.wModel.isAoDaiNam()) {
            a[0] = Char.ID_BIG_AVT_AO_DAI_NAM;
            a[1] = 2;
            a[2] = 3;
        }
        else if (this.wModel.isAoDaiNu()) {
            a[0] = Char.ID_BIG_AVT_AO_DAI_NU;
            a[1] = 2;
            a[2] = 3;
        }
        else if (this.wModel.isMeoQuyToc()) {
            a[0] = 761;
            a[1] = 11;
            a[2] = 2;
        }
        else if (this.wModel.isGiapRong()) {
          
            a[0] = 770;
            a[1] = 13;
            a[2] = 2;
        }

        else if (this.wModel.isLacLongQuan()) {
            a[0] = 634;
            a[1] = 1;
            a[2] = 3;
        }
        else if (this.wModel.isAuCo()) {
            a[0] = 632;
            a[1] = 1;
            a[2] = 3;
        }
        else if (this.wModel.isAonoelNam2024()) {
            a[0] = 845;
            a[1] = 3;
            a[2] = 2;
        }
        else if (this.wModel.isAonoelNu2024()) {
            a[0] = 847;
            a[1] = 3;
            a[2] = 2;
        }
        
        return a;
    }

    private boolean isTatPhiPhong = false;

    public boolean isTatPhiPhong() {
        return isTatPhiPhong;
    }

    public void setTatPhiPhong() {
        isTatPhiPhong = true;
    }

    public void setBatPhiPhong() {
        isTatPhiPhong = false;
    }

    private boolean isTatCanh = false;

    public boolean isTatCanh() {
        return isTatCanh;
    }

    public void setTatCanh() {
        isTatCanh = true;
    }

    public void setBatCanh() {
        isTatCanh = false;
    }


    private boolean isTatMatNa = false;

    public boolean isTatMatNa() {
        return isTatMatNa;
    }

    public void setTatMatNa() {
        isTatMatNa = true;
    }

    public void setBatMatNa() {
        isTatMatNa = false;
    }

    private boolean isBatCaiTrang = true;

    public boolean isBatCaiTrang() {
        return isBatCaiTrang;
    }

    public void setTatCaiTrang() {
        isBatCaiTrang = false;
    }

    public void setBatCaiTrang() {
        isBatCaiTrang = true;
    }

    public int[] getIdBigPhiPhong() {
        final int[] a = {-1, -1, -1, -1};
        final Item[] wItems = this.getArrayCharWearing();
        final int index = getIndexItemWearing(19, 0);
        
        // Kiểm tra cánh trước
        if (this.wModel.CanhModel != null && this.wModel.CanhModel.isCanh() && !this.isTatCanh()) {
            a[0] = this.wModel.CanhModel.getIdEff();
            a[2] = 1;
            a[3] = 3;
            return a;
        }
        
        // Xử lý phi phong
        if (!this.isTatPhiPhong()) {
            // Xử lý VIP phi phong
            if (this.vip == 1) {
                a[0] = EffectBuff.EFF_PHI_PHONG_VIP3;
                a[2] = 1;
                a[3] = 3;
            } else if (this.vip == 2) {
                a[0] = EffectBuff.EFF_PHI_PHONG_VIP2;
                a[2] = 1;
                a[3] = 3;
            } else if (this.vip == 3) {
                a[0] = EffectBuff.EFF_PHI_PHONG_VIP1;
                a[2] = 1;
                a[3] = 3;
            }
        } else {
            // Xử lý các loại phi phong khác khi đã tắt phi phong VIP
            if (wItems[index] != null) {
                if (wItems[index].isPhiPhongKhongTuoc()) {
                    a[0] = EffectBuff.EFF_PHI_PHONG_KHONG_TUOC;
                    a[1] = Char.ID_BIG_AVT_PP_KHONG_TUOC;
                    a[2] = 0;
                    a[3] = 3;
                } else if (wItems[index].isPhiPhongHuyenVu()) {
                    a[0] = EffectBuff.EFF_PHI_PHONG_HUYEN_VU;
                    a[1] = Char.ID_BIG_AVT_PP_HUYEN_VU;
                    a[2] = 1;
                    a[3] = 3;
                } else if (wItems[index].isPhiPhongBongDa()) {
                    a[0] = EffectBuff.EFF_PHI_PHONG_BONG_DA;
                    a[1] = Char.ID_BIG_AVT_PP_HUYEN_VU;
                    a[2] = 1;
                    a[3] = 3;
                } else if (wItems[index].isPhiPhongTim()) {
                    a[0] = 8937; // EFFECT
                    a[1] = 714; // AVATAR
                    a[2] = 1;
                    a[3] = 3;
                }
            }
        }
        return a;
    }

    public short[] getIDModel() {
        final short[] id = {-1, -1, -1, -1, -1};
        if (this.wModel.itemModel != null) {
            for (int i = 0; i < 4; ++i) {
                if (this.wModel.itemModel.getTemplate().atb[i + 5] > 0) {
                    id[i] = this.wModel.itemModel.getTemplate().atb[i + 5];
                }
            }
        }
        if (this.wModel.headModel != null) {
            id[0] = this.wModel.headModel.getTemplate().atb[5];
        }
        if (this.wModel.hatModel != null) {
            id[1] = this.wModel.hatModel.getTemplate().atb[6];
        }
        if (this.rankGov > -1) {
            id[1] = Char.idRankGov[this.rankGov];
        }
        if (this.wModel.wpModel != null && !this.wModel.wpModel.isChoiLuaLanBienHinh()) {
            id[4] = this.wModel.wpModel.getTemplate().atb[8];
        }
        if (this.wModel.itemModel != null && this.wModel.doNotPaintHat()) {
            id[1] = -2;
        }
        if (this.headModel > -1) {
            id[0] = this.headModel;
        }
        if (this.wModel.matNa != null && this.wModel.hatModel == null) {
            id[1] = -2;
        }

//        Logger2.DebugLogic("id: " + Arrays.toString(id));
        return id;
    }

    public void addKing(final Char p, final int country) {
        Char.gov[country].addMember(0, p.charname);
    }

    public void addDaiThan(final Char p, final int country) {
        if (!Char.gov[country].addMember(1, p.charname)) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Không thể phong chức cho " + p.charname, ""));
        } else {
            this.sendMessage(MessageCreator.createServerAlertMessage(String.valueOf(p.charname) + " đã được phong làm đại thần của lãnh thổ " + Map.nameCountry[this.myCountry], ""));
            try {
                Map.sendAllCharServer(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(p.charname) + " đã được phong làm đại thần của lãnh thổ " + Map.nameCountry[this.myCountry]), this.myCountry);
            } catch (final IOException ex) {
            }
            p.rankGov = 1;
            this.potions[28] = 1;
            p.potions[87] = 1;
            this.potions[78] = 1;
            if (this.potions[27] == 0) {
                this.potions[27] = 1;
            }
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
            p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
            p.sendToNearPlayer(MessageCreator.createCharInfo(p));
            Database.instance.saveEvent(Map.event.getInfo());
            MessageCreator.createMsgCharMonster(p, p);
        }
    }

    public void addTuongQuan(final Char p, final int country) {
        if (!Char.gov[country].addMember(2, p.charname)) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Không thể phong chức cho " + p.charname, ""));
        } else {
            this.sendMessage(MessageCreator.createServerAlertMessage(String.valueOf(p.charname) + " đã được phong làm tướng quân của lãnh thổ " + Map.nameCountry[this.myCountry], ""));
            try {
                Map.sendAllCharServer(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(p.charname) + " đã được phong làm đại thần của lãnh thổ " + Map.nameCountry[this.myCountry]), this.myCountry);
            } catch (final IOException ex) {
            }
            p.rankGov = 2;
            this.potions[28] = 1;
            this.potions[78] = 1;
            if (this.potions[27] == 0) {
                this.potions[27] = 1;
            }
            p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
            p.sendToNearPlayer(MessageCreator.createCharInfo(p));
            Database.instance.saveEvent(Map.event.getInfo());
            MessageCreator.createMsgCharMonster(p, p);
        }
    }

    public void addBoDau(final Char p, final int country) {
        if (!Char.gov[country].addMember(3, p.charname)) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Không thể phong chức cho " + p.charname, ""));
        } else {
            this.sendMessage(MessageCreator.createServerAlertMessage(String.valueOf(p.charname) + " đã được phong làm bộ đầu của lãnh thổ " + Map.nameCountry[this.myCountry], ""));
            try {
                Map.sendAllCharServer(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(p.charname) + " đã được phong làm bộ đầu của lãnh thổ " + Map.nameCountry[this.myCountry]), this.myCountry);
            } catch (final IOException ex) {
            }
            p.rankGov = 3;
            this.potions[28] = 1;
            this.potions[78] = 1;
            if (this.potions[27] == 0) {
                this.potions[27] = 1;
            }
            p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
            p.sendToNearPlayer(MessageCreator.createCharInfo(p));
            Database.instance.saveEvent(Map.event.getInfo());
            MessageCreator.createMsgCharMonster(p, p);
        }
    }

    public void removeDaiThan(final String charname, final int country) {
        final Char p = CharManager.instance.getCharByCharName(charname);
        if (Char.gov[country].removeMember(1, charname)) {
            this.sendMessage(MessageCreator.createServerAlertMessage(String.valueOf(charname) + " đã bị phế chức đại thần của lãnh thổ " + Map.nameCountry[this.myCountry], ""));
            try {
                Map.sendAllCharServer(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(charname) + " đã bị phế chức đại thần của lãnh thổ " + Map.nameCountry[this.myCountry]), this.myCountry);
            } catch (final IOException ex) {
            }
            if (p != null) {
                p.rankGov = -1;
                p.potions[87] = 0;
                this.potions[28] = 0;
                this.potions[78] = 0;
                if (this.potions[27] == 1) {
                    this.potions[27] = 0;
                }
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
                p.sendToNearPlayer(MessageCreator.createCharInfo(p));
                MessageCreator.createMsgCharMonster(p, p);
            }
            Database.instance.saveEvent(Map.event.getInfo());
        }
    }

    public void removeTuongQuan(final String charname, final int country) {
        final Char p = CharManager.instance.getCharByCharName(charname);
        if (Char.gov[country].removeMember(2, p.charname)) {
            this.sendMessage(MessageCreator.createServerAlertMessage(String.valueOf(charname) + " đã bị phế chức tướng quân của lãnh thổ " + Map.nameCountry[this.myCountry], ""));
            try {
                Map.sendAllCharServer(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(charname) + " đã bị phế chức tướng quân của lãnh thổ " + Map.nameCountry[this.myCountry]), this.myCountry);
            } catch (final IOException ex) {
            }
            if (p != null) {
                p.rankGov = -1;
                p.potions[87] = 0;
                this.potions[28] = 0;
                this.potions[78] = 0;
                if (this.potions[27] == 1) {
                    this.potions[27] = 0;
                }
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
                p.sendToNearPlayer(MessageCreator.createCharInfo(p));
                MessageCreator.createMsgCharMonster(p, p);
            }
            Database.instance.saveEvent(Map.event.getInfo());
        }
    }

    public void removeBoDau(final String charname, final int country) {
        final Char p = CharManager.instance.getCharByCharName(charname);
        if (Char.gov[country].removeMember(3, p.charname)) {
            this.sendMessage(MessageCreator.createServerAlertMessage(String.valueOf(charname) + " đã bị phế chức bộ đầu của lãnh thổ " + Map.nameCountry[this.myCountry], ""));
            try {
                Map.sendAllCharServer(MessageCreator.createServerAlertAutoOffMessage(String.valueOf(charname) + " đã bị phế chức bộ đầu của lãnh thổ " + Map.nameCountry[this.myCountry]), this.myCountry);
            } catch (final IOException ex) {
            }
            if (p != null) {
                p.rankGov = -1;
                p.potions[87] = 0;
                this.potions[28] = 0;
                this.potions[78] = 0;
                if (this.potions[27] == 1) {
                    this.potions[27] = 0;
                }
                p.sendMessage(MessageCreator.createCharInventoryMessage(p, 0));
                p.sendMessage(MessageCreator.createCharWearingMessage(p, p));
                p.sendToNearPlayer(MessageCreator.createCharInfo(p));
                MessageCreator.createMsgCharMonster(p, p);
            }
            Database.instance.saveEvent(Map.event.getInfo());
        }
    }

    public void removeKing(final Char p, final int country) {
        Char.gov[country].addMember(0, p.charname);
    }

    public void startPrivateMap() {
    }

    public boolean fullRing() {
        final Vector<Item> wItems = this.getCharWearing();
        byte n = 0;
        for (int i = 0; i < wItems.size(); ++i) {
            if (wItems.get(i).getType() == 8) {
                ++n;
                if (n > 1) {
                    break;
                }
            }
        }
        return n > 1;
    }

    public void createIdGiftWedding(final int type) {
        this.idGifWedding.removeAll(this.idGifWedding);
        if (type > 0) {
            for (byte i = 0; i < 6; ++i) {
                this.idGifWedding.add(i);
            }
        }
        this.rcvGiftWedding = 2;
    }

    public static void loadAllItemLucky() {
        if (TeamServer.isServerIndo()) {
            return;
        }
        for (int i = 584; i <= 592; ++i) {
            final ItemLuckyDraw item = new ItemLuckyDraw();
            item.cat = 3;
            item.id = i;
            final ItemTemplates it = (ItemTemplates) Map.itemTemplates.get(5).get(i);
            item.name = String.valueOf(it.name) + " lv " + it.level;
            Char.allItemLuckyDraw.add(item);
        }
        ItemLuckyDraw item2 = new ItemLuckyDraw();
        item2.cat = 3;
        item2.id = 616;
        ItemTemplates it2 = (ItemTemplates) Map.itemTemplates.get(5).get(616);
        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
        Char.allItemLuckyDraw.add(item2);
        item2 = new ItemLuckyDraw();
        item2.cat = 3;
        item2.id = 617;
        it2 = (ItemTemplates) Map.itemTemplates.get(5).get(617);
        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
        Char.allItemLuckyDraw.add(item2);
        item2 = new ItemLuckyDraw();
        item2.cat = 3;
        item2.id = 618;
        it2 = (ItemTemplates) Map.itemTemplates.get(5).get(618);
        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
        Char.allItemLuckyDraw.add(item2);
        item2 = new ItemLuckyDraw();
        item2.cat = 3;
        item2.id = 619;
        it2 = (ItemTemplates) Map.itemTemplates.get(5).get(619);
        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
        Char.allItemLuckyDraw.add(item2);
// TODO cánh quay s
//        item2 = new ItemLuckyDraw();
//        item2.cat = 3;
//        item2.id = 898;
//        it2 = (ItemTemplates) Map.itemTemplates.get(5).get(898);
//        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
//        Char.allItemLuckyDraw.add(item2);
//
//        item2 = new ItemLuckyDraw();
//        item2.cat = 3;
//        item2.id = 899;
//        it2 = (ItemTemplates) Map.itemTemplates.get(5).get(899);
//        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
//        Char.allItemLuckyDraw.add(item2);
//
//        item2 = new ItemLuckyDraw();
//        item2.cat = 3;
//        item2.id = 900;
//        it2 = (ItemTemplates) Map.itemTemplates.get(5).get(900);
//        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
//        Char.allItemLuckyDraw.add(item2);
//
//        item2 = new ItemLuckyDraw();
//        item2.cat = 3;
//        item2.id = 901;
//        it2 = (ItemTemplates) Map.itemTemplates.get(5).get(901);
//        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
//        Char.allItemLuckyDraw.add(item2);
//
//        item2 = new ItemLuckyDraw();
//        item2.cat = 3;
//        item2.id = 902;
//        it2 = (ItemTemplates) Map.itemTemplates.get(5).get(902);
//        item2.name = String.valueOf(it2.name) + " lv " + it2.level;
//        Char.allItemLuckyDraw.add(item2);
        
        item2 = new ItemLuckyDraw();
        item2.cat = 6;
        item2.id = 11;
        item2.name = Map.gemTemplate[11].name;
        Char.allItemLuckyDraw.add(item2);
        item2 = new ItemLuckyDraw();
        item2.cat = 12;
        item2.id = 5;
        item2.name = Map.petTemplate[5].name;
        item2.idIconPet = Map.petTemplate[5].idImgPaint;
        Char.allItemLuckyDraw.add(item2);
        item2 = new ItemLuckyDraw();
        item2.cat = 12;
        item2.id = 16;
        item2.name = Map.petTemplate[16].name;
        item2.idIconPet = Map.petTemplate[16].idImgPaint;
        Char.allItemLuckyDraw.add(item2);
        item2 = new ItemLuckyDraw();
        item2.cat = 12;
        item2.id = 17;
        item2.name = Map.petTemplate[17].name;
        item2.idIconPet = Map.petTemplate[17].idImgPaint;
        Char.allItemLuckyDraw.add(item2);
        final short[] idgem = {67, 74, 81, 88, 95, 68, 75, 82, 89, 96, 102, 109, 116, 123, 130, 103, 110, 117, 124, 131, 137, 60, 61, 62, 63, 64, 65};
        for (int j = 0; j < idgem.length; ++j) {
            item2 = new ItemLuckyDraw();
            item2.cat = 6;
            item2.id = idgem[j];
            item2.isSelect = 0;
            item2.name = Map.gemTemplate[idgem[j]].name;
            Char.allItemLuckyDraw.add(item2);
        }
        final byte[] ps = {3, 21, 22, 94, 6, 23, 24, 96, 100, 0, 84};
        final int[] value = {3, 3, 3, 3, 3, 3, 3, 3, 1, 1000, 1};
        for (int k = 0; k < ps.length; ++k) {
            item2 = new ItemLuckyDraw();
            item2.cat = 4;
            item2.id = ps[k];
            item2.value = value[k];
            item2.name = String.valueOf(value[k]) + " " + LoginHandler.PORTION_NAME[ps[k]];
            item2.isSelect = 0;
            Char.allItemLuckyDraw.add(item2);
        }
    }

    public void doSendListItemLuckyDraw() {
        final Message m = new Message(87);
        try {
            m.dos.writeByte(0);
            m.dos.writeByte(1);
            m.dos.writeByte(Char.allItemLuckyDraw.size());
            for (int i = 0; i < Char.allItemLuckyDraw.size(); ++i) {
                final ItemLuckyDraw item = Char.allItemLuckyDraw.get(i);
                m.dos.writeByte(item.cat);
                m.dos.writeShort((item.cat == 12) ? item.idIconPet : item.id);
                m.dos.writeUTF(item.name);
                final Vector<String> decript = item.getInfo(this);
                m.dos.writeByte(decript.size());
                for (int j = 0; j < decript.size(); ++j) {
                    m.dos.writeUTF(decript.get(j));
                }
                m.dos.writeByte(item.isSelect);
            }
            this.sendMessage(m);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void doStartLuckDraw(final byte index) {
        if (this.indexItemLuckyDraw > -1) {
            return;
        }


        final boolean isPPWorldCup = Char.allItemLuckyDraw.get(index).isPPWorldCup();
        if (isPPWorldCup) {
            if (this.potions[76] <= 0) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Vui lòng mua vé quay số đặc biệt để có thể tham gia quay số vật phẩm này", ""));
                return;
            }
        } else if (Char.allItemLuckyDraw.get(index).isPhuongHoang()) {
            if (this.potions[118] <= 0) {
                this.sendMessage(MessageCreator.createServerAlertMessage("bạn phải có vé phượng hoàng mới có thể quay phượng hoàng", ""));
                return;
            }
        } else if (this.potions[69] <= 0) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Vui lòng mua vé để có thể tham gia quay số", ""));
            return;
        }
        this.indexItemLuckyDraw = index;
        if (Char.allItemLuckyDraw.get(index).isPhuongHoang()) {
            final int[] potions = this.potions;
            final int n = 118;
            --potions[n];
        } else if (isPPWorldCup) {
            final int[] potions2 = this.potions;
            final int n2 = 76;
            --potions2[n2];
        } else {
            final int[] potions3 = this.potions;
            final int n3 = 69;
            --potions3[n3];
        }
        this.doFinishLuckyDraw();
        Database.instance.saveOrtherLog("", this.charname, "quay so " + index, "luckydraw");
    }
    

    public static boolean isSuKienNoel() 
    {
        final String daycheck = getDayTime(0L);
        return !TeamServer.isServerLocal() && !TeamServer.isServerLienDau() && daycheck.compareTo("2023-06-01 00:00:00") < 0;
    }

    public static boolean isSuKienTetduonglich2024() 
    {
        return TeamServer.isSuKienTetduonglich2024;
    }

    public static boolean isSuKienNoel2023() 
    {
        return TeamServer.isSuKienNoel2023;
    }

    public static boolean isSuKienMiniNuiChauBau() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2020-10-18 23:59:59") < 0;
    }

    public static boolean isSuKienMini() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2020-07-11 10:00:00") < 0;
    }

    public static boolean isSuKienMiniChucNu() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2024-08-30 23:59:00") < 0;
    }

    public static boolean isFinishSuKienMiniChucNu() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2024-08-31 10:00:00") >= 0;
    }

    public static boolean isFinishAllSuKienMini() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2020-07-11 10:00:00") > 0;
    }

    public static boolean isOpenDauGiaChoiBlackFriday() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2022-07-13 10:00:00") >= 0 && daycheck.compareTo("2023-07-21 23:59:59") < 0;
    }

    public static boolean isOpenSellBlackFriday() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2023-07-13 10:00:00") >= 0 && daycheck.compareTo("2023-07-15 00:00:00") < 0;
    }

    public static boolean isSuKienBlackFriday() {
        final String daycheck = getDayOpen(0L);
        return daycheck.equals("2020-11-25");
    }

    public static boolean isSuKien83() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2020-03-08 23:59:59") < 0;
    }

    public static boolean isDemHaloween2023() {
        return getDayOpen(0L).equals("2023-11-01");
    }

    public static boolean isDemHaloween() {
        if (TeamServer.isServerLocal()) {
            return getDayOpen(0L).equals("2022-11-01");
        }
        return getDayOpen(0L).equals("2022-10-31");
    }

    public static boolean isDemTrungThu() {
        return getDayOpen(0L).equals("2023-09-17");
    }

    public static boolean is123Tet() {
        final String time = getDayOpen(0L);
        return time.equals("2021-02-12") || time.equals("2021-02-13") || time.equals("2021-02-14") || TeamServer.isServerLocal();
    }

    public static boolean isDemNoel() {
        return getDayOpen(0L).equals("2022-12-24") || getDayOpen(0L).equals("2023-01-01");
    }

    public static boolean isFinishAllSuKienHaloween2016() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2020-11-01 00:00:00") >= 0;
    }

    public static boolean isSuKienHaloween2016() {
        final String daycheck = getDayTime(0L);
        return (TeamServer.isServerLocal() && daycheck.compareTo("2020-11-01 00:00:00") < 0)
                || daycheck.compareTo("2020-11-01 00:00:00") < 0;
    }

    public static boolean isSuKien2011l2016() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2022-9-20 10:00:00") >= 0 && daycheck.compareTo("2024-11-20 23:59:59") <= 0;
    }

    public static boolean isBanLongDenHoaSen() {
        if (!Map.openLog) {
            return false;
        }
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2018-11-24 23:59:59") <= 0;
    }

    public static boolean isSuKienTrungThul2016() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2023-05-15 00:00:00") < 0;
    }

    public static boolean isFinishAllSuKien() {
        if (TeamServer.isServerIndo()) {
            return true;
        }
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2023-05-09 10:00:00") > 0;
    }

    public static boolean isFinishAllSuKienTrungThul2016() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2023-10-15 00:00:00") > 0;
    }

    public static boolean isFinishAllSuKienTet2017() 
    {
        return !TeamServer.isSuKienTet2017;
    }

    public static boolean isSuKienTet2017() 
    {
        return TeamServer.isSuKienTet2017;
    }

    public static boolean isSuKienTet2016() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2016-03-01 10:00:00") < 0;
    }

    public static boolean isSuKienDuaTopHoaLu() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2020-04-09 10:00:00") < 0;
    }

    public static boolean isSuKienGioTo2016() 
    {
        return TeamServer.isSuKienGioTo2016;
    }

    public static boolean isFinishAllSuKienTet2016() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2016-03-06 10:00:00") >= 0;
    }

    public static boolean isFinishAllSuKienNoel() {
        final String daycheck = getDayTime(0L);
        return TeamServer.isServerLienDau() || TeamServer.isServerLocal() || daycheck.compareTo("2023-06-01 00:00:00") >= 0;
    }

    public static boolean isFinishAllSuKienGioTo2016() 
    {
        return !TeamServer.isSuKienGioTo2016;
    }

    public static boolean isSuKienSnNgude() {
        if (Map.openLog) {
            return false;
        }
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2016-08-17 10:00:00") < 0;
    }

    public static boolean isFinishAllSuKienSnNgude() {
        if (Map.openLog) {
            return true;
        }
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2016-08-20 10:00:00") >= 0;
    }

    public static boolean isSuKienVuLan2015() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2015-09-14 10:00:00") < 0;
    }

    public static boolean isSuKienTrungThu2015() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2020-09-30 10:00:00") < 0;
    }

    public static boolean isFinishSuKien() {
        return !isSuKienNoel();
    }

    public static boolean isSinhNhatNgude() {
        if (!Map.openLog) {
            final String daycheck = getDayOpen(0L);
            if (daycheck.equals("2015-08-01") || daycheck.equals("2015-08-02") || daycheck.equals("2015-08-03") || daycheck.equals("2015-08-04") || daycheck.equals("2015-08-05")) {
                return true;
            }
        }
        return false;
    }

    public static boolean canReceiveGiftSNNgude() {
        if (!Map.openLog) {
            final String daycheck = getDayOpen(0L);
            if (daycheck.equals("2015-08-01") || daycheck.equals("2015-08-02") || daycheck.equals("2015-08-03")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSuKienWordcup2017() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2024-07-25 10:00:00") < 0;
    }

    public static boolean isSuKienHe2017() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2024-08-01 10:00:00") < 0;
    }
      
    public static boolean isFinishAllSuKienHe2017() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2024-08-01 10:00:00") > 0;
    }

    public static boolean isSuKienHalowwen2015() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2023-11-20 11:59:00") < 0;
    }

    public static boolean isFinishAllSuKienHaloween2015() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2023-11-29 00:00:00") >= 0;
    }

    public static boolean isFinishAllSuKienHe2015() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2015-07-08 00:00:00") >= 0;
    }

    public static boolean isFinishAllSuKienTrungthu2015() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2020-09-30 10:00:00") >= 0;
    }

    public static boolean isFinishAllSuKienVulan2015() {
        final String daycheck = getDayTime(0L);
        return daycheck.compareTo("2015-09-14 10:00:00") >= 0;
    }
    

    public void doFinishLuckyDraw() {
        try {
            if (this.indexItemLuckyDraw == -1) {
                return;
            }
            
            int pc = Map.r.nextInt(100); // Set tỉ lệ min
            int soluongtrung = 3;
            final byte[] index = new byte[soluongtrung];
            byte n = 0;
            String info = "";
            int pcwin = Char.pcWinLuckyDraw;
            
            // Lưu index gốc và kiểm tra phượng hoàng
            final int originalIndex = this.indexItemLuckyDraw;
            boolean isPhuongHoang = Char.allItemLuckyDraw.get(originalIndex).isPhuongHoang();
            int lv = 35;
    
            // Xử lý đặc biệt cho phượng hoàng
            if (isPhuongHoang) {
                pc = Map.r.nextInt(100_000_000); // set tỉ lệ max
                pcwin = 1;
                lv = 1;
                ++Map.countQuaySo;
                System.out.println("");
               
            }
    
            // Kiểm tra người chơi đặc biệt
            try {
                if (Char.except_quay_so != null) {
                    for (int i = 0; i < Char.except_quay_so.length; ++i) {
                        if (this.charname.toLowerCase().equals(Char.except_quay_so[i].toLowerCase().trim())) {
                            this.winPH = 1;
                            break;
                        }
                    }
                }
            } catch (Exception ex) {}
    
            // Xử lý điều kiện trúng phượng hoàng
            if (isPhuongHoang) {
                if (isFinishAllSuKienHaloween2016() || this.winPH == 1) {
                    pcwin = -1;
                } else {
                    synchronized (Char.allItemLuckyDraw) {
                        if (Map.countQuaySo >= Map.MAX_QUAY) {
                            Map.MAX_QUAY *= 2;
                            Map.countQuaySo = 0;
                            if (Map.max_phuong_hoang > 0) {
                                pcwin = 10000;
                                System.out.println(this.charname + " QUAY SO TRUNG PHUONG HOANG NE");
                            } else {
                                pcwin = -1;
                            }
                            Char.timeGetPhuongHoang = System.currentTimeMillis() + (Map.r.nextInt(5) + 1) * 60 * 60000;
                        } else {
                            pcwin = -1;
                        }
                    }
                }
            }
    
            // Xử lý admin
            if (this.isAdmin) {
                pcwin = 100_000_000;
                isPhuongHoang = true;
                Map.max_phuong_hoang = 1;
                Map.nPhuongHoang = 1;
            }
    
            // Xử lý trúng thưởng
            Label_0613: {
                if (pc < pcwin && this.lvDetail.lv >= lv) {
                    if (isPhuongHoang) {
                        synchronized (Char.allItemLuckyDraw) {
                            if (Map.max_phuong_hoang > 0 && Map.nPhuongHoang > 0) {
                                --soluongtrung;
                                index[0] = (byte)originalIndex;
                                ++n;
                                this.winPH = 1;
                                --Map.max_phuong_hoang;
                                --Map.nPhuongHoang;
                                if (Map.nPhuongHoang < 0) {
                                    Map.nPhuongHoang = 0;
                                }
                                Char.allItemLuckyDraw.get(originalIndex).addGif(this, 30);
                                Database.instance.saveEvent(Map.event.getInfo());
                                break Label_0613;
                            }
                        }
                    }
                    
                    // Xử lý trúng item thường
                    --soluongtrung;
                    index[0] = (byte)originalIndex;
                    ++n;
                    final int pc2 = Map.r.nextInt(10000);
                    int time = 0;
                    if (pc2 > 1) {
                        if (pc2 <= 400) {
                            time = 7200;
                        } else if (pc2 <= 700) {
                            time = 4320;
                        } else {
                            time = 1440;
                        }
                    }
                    Char.allItemLuckyDraw.get(originalIndex).addGif(this, time);
                }
            }
    
            // Xử lý phần thưởng phụ
            final Hashtable<Byte, Byte> idselect = new Hashtable<>();
            for (int j = 0; j < soluongtrung; ++j) {
                byte[] glucky = Char.groupLucky[0];
                int ran = Map.r.nextInt(100);
                if (ran < 3) {
                    glucky = Char.groupLucky[3];
                } else if (ran < 8) {
                    glucky = Char.groupLucky[2];
                } else if (ran < 50) {
                    glucky = Char.groupLucky[1];
                }
    
                int idx;
                ItemLuckyDraw checkItem;
                for (idx = glucky[Map.r.nextInt(glucky.length)] + Char.addMore; 
                    idselect.get((byte)idx) != null || 
                    ((checkItem = Char.allItemLuckyDraw.get(idx)).cat == 12 || 
                    (checkItem.cat == 3 && Map.r.nextInt(1000) < 990)); // 99% sẽ bỏ qua cat 3
                    idx = glucky[Map.r.nextInt(glucky.length)] + Char.addMore) {
                    
                    glucky = Char.groupLucky[0];
                    if (this.lvDetail.lv >= 35) {
                        ran = Map.r.nextInt(100);
                        if (ran < 3) {
                            glucky = Char.groupLucky[3];
                        } else if (ran < 8) {
                            glucky = Char.groupLucky[2];
                        } else if (ran < 50) {
                            glucky = Char.groupLucky[1];
                        }
                    }
                }
    
                final ItemLuckyDraw item = Char.allItemLuckyDraw.get(idx);
                if (item.cat == 4) {
                    if (item.id == 0) {
                        this.xu += item.value;
                        info = String.valueOf(info) + item.value + " xu,";
                    } else {
                        final int[] potions = this.potions;
                        final int id = item.id;
                        potions[id] += item.value;
                        try {
                            info = String.valueOf(info) + item.value + " " + LoginHandler.PORTION_NAME[item.id] + ",";
                        } catch (Exception ex2) {}
                    }
                } else if (item.cat == 6) {
                    this.doAddGemItem(item.id);
                    try {
                        info = String.valueOf(info) + " 1 " + Map.gemTemplate[item.id].name + ",";
                    } catch (Exception ex3) {}
                }
                
                idselect.put((byte)idx, (byte)idx);
                index[n++] = (byte)idx;
            }
    
            // Gửi thông báo kết quả
            final Message m = new Message(87);
            try {
                m.dos.writeByte(1);
                m.dos.writeByte(3);
                for (int k = 0; k < 3; ++k) {
                    m.dos.writeByte(index[k]);
                }
                this.sendMessage(m);
            } catch (Exception ex4) {}
    
            // Cập nhật inventory và gem
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharGemItem(this));
    
            // Lưu log
            try {
                Database.instance.saveLogThongKeChiTieu("quayso " + Char.allItemLuckyDraw.get(originalIndex).id, 
                                                      0, 1, 1L, "quayso");
            } catch (Exception ex5) {}
    
            // Reset và lưu thông tin
            this.indexItemLuckyDraw = -1;
            Database.instance.saveOrtherLog("", this.charname, info, "quayso");
    
            // Kiểm tra sự kiện
            isSuKienTrungThul2016();
            if (isSuKienHaloween2016()) {
                Map.r.nextInt(100);
            }
            isSuKienHe2017();
    
            // Xử lý book skill pet
            try {
                if (Map.r.nextInt(1000) < 5) {
                    Map.doCreateBookSkillPet(this, 0);
                }
            } catch (Exception ex7) {}
    
        } catch (Exception e) {
            Logger.logError(e, "errorquayso.txt");
        }
    }

    public void createMainQuest() {
        if (this.mainQuest.state_quest == NewCharQuest.DONE || this.idMainQuest > this.mainQuest.id_quest) {
            final InfoQuestTemplate info = Map.allMainQuest.get(this.idMainQuest);
            if (info != null) {
                this.mainQuest = new NewCharQuest(this.idMainQuest, (byte) 0);
                MessageCreator.createMsgCharQuest(this, 0);
            }
        }
    }

    public void initQuest(final String itemGet, final String monsKilled, final String finish) {
        if (itemGet.equals("") && this.idMainQuest == 0) {
            this.mainQuest = new NewCharQuest(this.idMainQuest, (byte) 0);
        } else {
            final String[] data2 = split(itemGet, "|");
            final String[] data3 = split(monsKilled, "|");
            final String[] data4 = split(finish, "|");
            for (short i = 0; i < data4.length; ++i) {
                final NewCharQuest q = new NewCharQuest(true);
                final String[] isfinish = split(data4[i], ",");
                q.main_sub = Byte.parseByte(isfinish[2]);
                q.initQuest(Short.parseShort(isfinish[0]), split(data2[i], ","), split(data3[i], ","), isfinish[1]);
                if (q.main_sub == 0) {
                    this.mainQuest = q;
                    if (this.mainQuest.id_quest > this.idMainQuest) {
                        this.idMainQuest = this.mainQuest.id_quest;
                    }
                    if (this.mainQuest.id_quest < this.idMainQuest) {
                        this.mainQuest.state_quest = NewCharQuest.DONE;
                    }
                    if (this.mainQuest.state_quest == NewCharQuest.DONE) {
                        this.createMainQuest();
                    }
                } else if (q.main_sub == 1) {
                    final InfoQuestTemplate info = q.getTemplate();
                    if (info != null) {
                        this.subQuest = q;
                    }
                } else if (q.main_sub == 2) {
                    this.clanQuestNew = q;
                }
            }
        }
    }

    public String[] getInfoQuestSave() {
        final String[] info = {"", "", "", ""};
        final NewCharQuest cq = this.mainQuest;
        info[0] = new StringBuilder(String.valueOf(cq.getInfoItemGet())).toString();
        info[1] = new StringBuilder(String.valueOf(cq.getInfoMonsKilled())).toString();
        info[2] = String.valueOf(cq.id_quest) + "," + cq.state_quest + "," + cq.main_sub;
        if (this.subQuest.id_quest > -1) {
            final String[] array = info;
            final int n = 0;
            array[n] = String.valueOf(array[n]) + "|" + this.subQuest.getInfoItemGet();
            final String[] array2 = info;
            final int n2 = 1;
            array2[n2] = String.valueOf(array2[n2]) + "|" + this.subQuest.getInfoMonsKilled();
            final String[] array3 = info;
            final int n3 = 2;
            array3[n3] = String.valueOf(array3[n3]) + "|" + this.subQuest.id_quest + "," + this.subQuest.state_quest + "," + this.subQuest.main_sub;
        }
        if (this.clanQuestNew.id_quest > -1) {
            final String[] array4 = info;
            final int n4 = 0;
            array4[n4] = String.valueOf(array4[n4]) + "|" + this.clanQuestNew.getInfoItemGet();
            final String[] array5 = info;
            final int n5 = 1;
            array5[n5] = String.valueOf(array5[n5]) + "|" + this.clanQuestNew.getInfoMonsKilled();
            final String[] array6 = info;
            final int n6 = 2;
            array6[n6] = String.valueOf(array6[n6]) + "|" + this.clanQuestNew.id_quest + "," + this.clanQuestNew.state_quest + "," + this.clanQuestNew.main_sub;
        }
        info[3] = new StringBuilder(String.valueOf(this.itemquest[0])).toString();
        for (int i = 1; i < this.itemquest.length; ++i) {
            final String[] array7 = info;
            final int n7 = 3;
            array7[n7] = String.valueOf(array7[n7]) + "," + this.itemquest[i];
        }
        return info;
    }

    public Vector<NewCharQuest> getAllQuestCanReceive() {
        final Vector<NewCharQuest> allquest = new Vector<NewCharQuest>();
        if (this.mainQuest.state_quest == NewCharQuest.UN_RECEIVE) {
            allquest.add(this.mainQuest);
        }
        if (this.subQuest.id_quest == -1) {
            for (final InfoQuestTemplate info : Map.allSubQuest.values()) {
                if (info.type == 4) {
                    final int lvget = this.lvDetail.lv - info.lv;
                    if (lvget < 0 || lvget >= 10) {
                        continue;
                    }
                    allquest.add(new NewCharQuest(info.id, (byte) 1));
                } else {
                    allquest.add(new NewCharQuest(info.id, (byte) 1));
                }
            }
        }
        if (this.clanQuestNew.id_quest == -1 && this.idClan > -1 && this.countQuestClan < 10) {
            for (final InfoQuestTemplate info : Map.allClanQuest.values()) {
                allquest.add(new NewCharQuest(info.id, (byte) 2));
            }
        }
        return allquest;
    }

    public Vector<NewCharQuest> getAllQuestFinish() {
        final Vector<NewCharQuest> allquest = new Vector<NewCharQuest>();
        if (this.mainQuest.state_quest == NewCharQuest.FINISH) {
            allquest.add(this.mainQuest);
        }
        if (this.subQuest.state_quest == NewCharQuest.FINISH && this.subQuest.id_quest != -1) {
            allquest.add(this.subQuest);
        }
        if (this.clanQuestNew.state_quest == NewCharQuest.FINISH && this.clanQuestNew.id_quest != -1) {
            allquest.add(this.clanQuestNew);
        }
        return allquest;
    }

    public Vector<NewCharQuest> getAllQuestWorking() {
        final Vector<NewCharQuest> allquest = new Vector<NewCharQuest>();
        if (this.mainQuest.state_quest == NewCharQuest.WORKING) {
            allquest.add(this.mainQuest);
        }
        if (this.subQuest.state_quest == NewCharQuest.WORKING && this.subQuest.id_quest != -1) {
            allquest.add(this.subQuest);
        }
        if (this.clanQuestNew.state_quest == NewCharQuest.WORKING && this.clanQuestNew.id_quest != -1) {
            allquest.add(this.clanQuestNew);
        }
        return allquest;
    }

    public Vector<Short> checkDropItemQuest(final int idMonster) {
        final Vector<Short> idItemquest = new Vector<Short>();
        final Vector<NewCharQuest> allQuestWorking = this.getAllQuestWorking();
        if (allQuestWorking.size() > 0) {
            for (int i = 0; i < allQuestWorking.size(); ++i) {
                final NewCharQuest q = allQuestWorking.get(i);
                final InfoQuestTemplate info = q.getTemplate();
                if (info.type == 2) {
                    final short[] monsKill = info.getMonsterKill();
                    final short[] totalItemGet = info.getTotalItemGet();
                    for (int j = 0; j < q.nItem.length; ++j) {
                        if (monsKill[j] == idMonster && q.nItem[j] < totalItemGet[j] && !idItemquest.contains(info.itemget.get(j))) {
                            idItemquest.add(info.itemget.get(j));
                        }
                    }
                }
            }
        }
        return idItemquest;
    }

    public void checkFinishQuest(final int idMonster_item, final boolean iskillMons, final int lv) {
        final Vector<NewCharQuest> allQuestWorking = this.getAllQuestWorking();
        if (allQuestWorking.size() > 0) {
            for (int i = 0; i < allQuestWorking.size(); ++i) {
                final NewCharQuest q = allQuestWorking.get(i);
                final InfoQuestTemplate info = q.getTemplate();
                if (q.state_quest == NewCharQuest.WORKING) {
                    boolean isFinish = true;
                    if (info.type == 0 && iskillMons) {
                        final short[] monsKill = info.getMonsterKill();
                        final short[] totalMonsKill = info.getTotalMonsterKill();
                        for (int j = 0; j < q.monsterKilled.length; ++j) {
                            if (monsKill[j] == idMonster_item && q.monsterKilled[j] < totalMonsKill[j]) {
                                final short[] monsterKilled = q.monsterKilled;
                                final int n = j;
                                ++monsterKilled[n];
                                break;
                            }
                        }
                        for (int j = 0; j < totalMonsKill.length; ++j) {
                            if (q.monsterKilled[j] < totalMonsKill[j]) {
                                isFinish = false;
                            }
                        }
                        if (isFinish) {
                            q.state_quest = NewCharQuest.FINISH;
                            MessageCreator.createMsgCharQuest(this, 1);
                            Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                            this.sendMessage(MessageCreator.createServerAlertMessage("Nhiệm vụ " + info.name + " đã hoàn thành. Hãy đi trả nhiệm vụ", ""));
                        }
                    } else if (info.type == 4 && iskillMons) {
                        if (Map.abs(this.lvDetail.lv - lv) <= info.deltaLv) {
                            final short[] totalMonsKill2 = info.getTotalMonsterKill();
                            for (int k = 0; k < q.monsterKilled.length; ++k) {
                                if (q.monsterKilled[k] < totalMonsKill2[k]) {
                                    final short[] monsterKilled2 = q.monsterKilled;
                                    final int n2 = k;
                                    ++monsterKilled2[n2];
                                    break;
                                }
                            }
                            for (int k = 0; k < totalMonsKill2.length; ++k) {
                                if (q.monsterKilled[k] < totalMonsKill2[k]) {
                                    isFinish = false;
                                }
                            }
                            if (isFinish) {
                                q.state_quest = NewCharQuest.FINISH;
                                MessageCreator.createMsgCharQuest(this, 1);
                                Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                                this.sendMessage(MessageCreator.createServerAlertMessage("Nhiệm vụ " + info.name + " đã hoàn thành. Hãy đi trả nhiệm vụ", ""));
                            }
                        }
                    } else if (info.type == 2 && !iskillMons) {
                        final short[] itemQuest = info.getItemGet();
                        final short[] totalItemGet = info.getTotalItemGet();
                        for (int j = 0; j < q.nItem.length; ++j) {
                            if (itemQuest[j] == idMonster_item && q.nItem[j] < totalItemGet[j]) {
                                final short[] nItem = q.nItem;
                                final int n3 = j;
                                ++nItem[n3];
                                break;
                            }
                        }
                        for (int j = 0; j < totalItemGet.length; ++j) {
                            if (q.nItem[j] < totalItemGet[j]) {
                                isFinish = false;
                            }
                        }
                        if (isFinish) {
                            q.state_quest = NewCharQuest.FINISH;
                            MessageCreator.createMsgCharQuest(this, 1);
                            MessageCreator.createMsgCharQuest(this, 2);
                            Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                            this.sendMessage(MessageCreator.createServerAlertMessage("Nhiệm vụ " + info.name + " đã hoàn thành. Hãy đi trả nhiệm vụ", ""));
                        }
                    }
                }
            }
        }
    }

    public void OnQuest(final byte type, final short questID, final byte main_sub) 
    {              
        if (main_sub == 0) 
        {
            final InfoQuestTemplate info = this.mainQuest.getTemplate();
            if (type == 0) 
            {
                try {
                    if (info.pAvtive > this.pointActiveQuest) 
                    {
                        this.sendMessage(MessageCreator.createServerAlertMessage("Bạn cần " + info.pAvtive + " điểm hoạt động mới có thể nhận nhiệm vụ này. Bạn còn " + this.pointActiveQuest + " điểm.", ""));
                        return;
                    }
                    this.pointActiveQuest -= info.pAvtive; // trừ điểm hoạt động
                    this.mainQuest.state_quest = NewCharQuest.WORKING;
                    if (info.type == 1) 
                    {
                        this.mainQuest.state_quest = NewCharQuest.FINISH;
                        this.itemquest[info.itemget.get(0)] = 1;
                        MessageCreator.createMsgCharQuest(this, 1);
                        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 3));
                    } 
                    else 
                    {
                        MessageCreator.createMsgCharQuest(this, 2);
                    }
                    MessageCreator.createMsgCharQuest(this, 0);
                    this.sendMessage(MessageCreator.createServerAlertMessage("Đã nhận nhiệm vụ " + info.name, ""));
                    Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                } catch (final Exception e) 
                {
                    e.printStackTrace();
                }
            } else if (type == 1) {
                try {
                    if (this.mainQuest.state_quest != NewCharQuest.DONE && (this.mainQuest.state_quest == NewCharQuest.FINISH || info.type == 1 || info.type == 3)) {
                        this.mainQuest.state_quest = NewCharQuest.DONE;
                        this.mainQuest.addGift(this);
                        boolean sendInfo = true;
                        if (info.type == 3) {
                            sendInfo = false;
                        }
                        if (info.type == 2 || info.type == 1) {
                            final short[] itemQuest = info.getItemGet();
                            final short[] totalItemGet = info.getTotalItemGet();
                            for (int i = 0; i < itemQuest.length; ++i) {
                                this.itemquest[itemQuest[i]] = 0;
                                this.mainQuest.nItem[i] = 0;
                                if (this.mainQuest.nItem[i] < 0) {
                                    this.mainQuest.nItem[i] = 0;
                                }
                                if (this.itemquest[itemQuest[i]] < 0) {
                                    this.itemquest[itemQuest[i]] = 0;
                                }
                            }
                            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 3));
                        }
                        ++this.idMainQuest;
                        this.createMainQuest();
                        MessageCreator.createMsgCharQuest(this, 1);
                        MessageCreator.createMsgCharQuest(this, 0);
                        if (sendInfo) {
                            this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhiệm vụ hoàn thành"));
                        }
                        Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            } else if (type == 2) {
                if (this.mainQuest.state_quest == NewCharQuest.DONE) {
                    return;
                }
                this.mainQuest.reset();
                MessageCreator.createMsgCharQuest(this, 0);
                MessageCreator.createMsgCharQuest(this, 2);
                this.sendMessage(MessageCreator.createServerAlertMessage("Đã hủy nhiệm vụ " + info.name, ""));
                Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
            }
        } else if (main_sub == 1) {
            InfoQuestTemplate info = null;
            if (type == 0) {
                if (this.subQuest.id_quest > -1 && this.subQuest.id_quest != questID) {
                    return;
                }
                this.subQuest = new NewCharQuest(questID, (byte) 1);
                info = this.subQuest.getTemplate();
                if (info.pAvtive > this.pointActiveQuest) {
                    this.sendMessage(MessageCreator.createServerAlertMessage("Bạn cần " + info.pAvtive + " điểm hoạt động mới có thể nhận nhiệm vụ này. Bạn còn " + this.pointActiveQuest + " điểm.", ""));
                    this.subQuest.reset();
                    this.subQuest.id_quest = -1;
                    return;
                }
                this.pointActiveQuest -= info.pAvtive;
                try {
                    this.subQuest.state_quest = NewCharQuest.WORKING;
                    if (info.type == 1) {
                        this.subQuest.state_quest = NewCharQuest.FINISH;
                        MessageCreator.createMsgCharQuest(this, 1);
                    } else {
                        MessageCreator.createMsgCharQuest(this, 2);
                    }
                    MessageCreator.createMsgCharQuest(this, 0);
                    this.sendMessage(MessageCreator.createServerAlertMessage("Đã nhận nhiệm vụ " + info.name, ""));
                    Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                    if (info.pAvtive > 0) {
                        this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            } else if (type == 1) {
                if (this.subQuest.id_quest > -1 && this.subQuest.id_quest != questID) {
                    return;
                }
                info = this.subQuest.getTemplate();
                if (this.subQuest.state_quest != NewCharQuest.DONE && (info.type == 3 || info.type == 1 || this.subQuest.state_quest == NewCharQuest.FINISH)) {
                    this.subQuest.state_quest = NewCharQuest.DONE;
                    this.subQuest.addGift(this);
                    boolean sendInfo = true;
                    if (info.type == 3) {
                        sendInfo = false;
                    }
                    if (info.type == 2 || info.type == 1) {
                        final short[] itemQuest = info.getItemGet();
                        final short[] totalItemGet = info.getTotalItemGet();
                        for (int i = 0; i < itemQuest.length; ++i) {
                            this.itemquest[itemQuest[i]] = 0;
                            this.subQuest.nItem[i] = 0;
                            if (this.subQuest.nItem[i] < 0) {
                                this.subQuest.nItem[i] = 0;
                            }
                            if (this.itemquest[itemQuest[i]] < 0) {
                                this.itemquest[itemQuest[i]] = 0;
                            }
                        }
                        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 3));
                    }
                    this.subQuest.reset();
                    this.subQuest.id_quest = -1;
                    if (info.type != 3) {
                        MessageCreator.createMsgCharQuest(this, 1);
                    }
                    MessageCreator.createMsgCharQuest(this, 0);
                    if (sendInfo) {
                        this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhiệm vụ hoàn thành"));
                    }
                    Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                }
            } else if (type == 2) {
                if (this.subQuest.id_quest > -1 && this.subQuest.id_quest != questID) {
                    return;
                }
                info = this.subQuest.getTemplate();
                if (this.subQuest.state_quest == NewCharQuest.DONE) {
                    return;
                }
                this.subQuest.reset();
                this.subQuest.id_quest = -1;
                MessageCreator.createMsgCharQuest(this, 0);
                MessageCreator.createMsgCharQuest(this, 2);
                this.sendMessage(MessageCreator.createServerAlertMessage("Đã hủy nhiệm vụ " + info.name, ""));
                Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
            }
        } else if (main_sub == 2) {
            InfoQuestTemplate info = null;
            if (type == 0) 
            {
                if (this.clanQuestNew.id_quest > -1 && this.clanQuestNew.id_quest != questID) 
                {
                    return;
                }
                this.clanQuestNew = new NewCharQuest(questID, (byte) 2);
                info = this.clanQuestNew.getTemplate();
                if (info.pAvtive > this.pointActiveQuest) 
                {
                    this.sendMessage(MessageCreator.createServerAlertMessage("Bạn cần " + info.pAvtive + " điểm hoạt động mới có thể nhận nhiệm vụ này. Bạn còn " + this.pointActiveQuest + " điểm.", ""));
                    this.clanQuestNew.reset();
                    this.clanQuestNew.id_quest = -1;
                    return;
                }
                
                System.out.print(this.charname + " nhận nv bang:  pAvtive: " + info.pAvtive + " điểm hoạt động mới có thể nhận nhiệm vụ này. Bạn còn " + this.pointActiveQuest + " điểm.");
                this.pointActiveQuest -= info.pAvtive;
                try {
                    this.clanQuestNew.state_quest = NewCharQuest.WORKING;
                    if (info.type == 1) {
                        this.clanQuestNew.state_quest = NewCharQuest.FINISH;
                        MessageCreator.createMsgCharQuest(this, 1);
                    } else {
                        MessageCreator.createMsgCharQuest(this, 2);
                    }
                    MessageCreator.createMsgCharQuest(this, 0);
                    this.sendMessage(MessageCreator.createServerAlertMessage("Đã nhận nhiệm vụ " + info.name, ""));
                    Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            } else if (type == 1) {
                if (this.clanQuestNew.id_quest > -1 && this.clanQuestNew.id_quest != questID) {
                    return;
                }
                info = this.clanQuestNew.getTemplate();
                if (this.clanQuestNew.state_quest != NewCharQuest.DONE && (info.type == 3 || info.type == 1 || this.clanQuestNew.state_quest == NewCharQuest.FINISH)) {
                    this.clanQuestNew.state_quest = NewCharQuest.DONE;
                    this.clanQuestNew.addGiftClan(this);
                    boolean sendInfo = true;
                    if (info.type == 3) {
                        sendInfo = false;
                    }
                    if (info.type == 2 || info.type == 1) {
                        final short[] itemQuest = info.getItemGet();
                        final short[] totalItemGet = info.getTotalItemGet();
                        for (int i = 0; i < itemQuest.length; ++i) {
                            this.itemquest[itemQuest[i]] = 0;
                            this.clanQuestNew.nItem[i] = 0;
                            if (this.clanQuestNew.nItem[i] < 0) {
                                this.clanQuestNew.nItem[i] = 0;
                            }
                            if (this.itemquest[itemQuest[i]] < 0) {
                                this.itemquest[itemQuest[i]] = 0;
                            }
                        }
                        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 3));
                    }
                    this.clanQuestNew.reset();
                    this.clanQuestNew.id_quest = -1;
                    if (info.type != 3) {
                        MessageCreator.createMsgCharQuest(this, 1);
                    }
                    MessageCreator.createMsgCharQuest(this, 0);
                    ++this.countQuestClan;
                    if (sendInfo) {
                        this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhiệm vụ hoàn thành"));
                    }
                    Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
                }
            } else if (type == 2) {
                if (this.clanQuestNew.id_quest > -1 && this.clanQuestNew.id_quest != questID) {
                    return;
                }
                info = this.clanQuestNew.getTemplate();
                if (this.clanQuestNew.state_quest == NewCharQuest.DONE) {
                    return;
                }
                this.clanQuestNew.reset();
                this.clanQuestNew.id_quest = -1;
                MessageCreator.createMsgCharQuest(this, 0);
                MessageCreator.createMsgCharQuest(this, 2);
                this.sendMessage(MessageCreator.createServerAlertMessage("Đã hủy nhiệm vụ " + info.name, ""));
                Database.instance.saveCharQuest(this.charDBID, this.getInfoQuestSave());
            }
        }
    }

    public static Item createPhiPhongLongThuMax(final Char p, final int ma_vat, final int idTemplate, final boolean is8x, final boolean lock, final int time) {
        final int[] id = {584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597};
        int lv = p.lvDetail.lv;
        if (lv < 35) {
            lv = 35;
        }
        if (lv > 80) {
            lv = 80;
        }
        int idtemp = id[(lv - 35) / 5];
        if (is8x) {
            idtemp = 593;
        }
        final Item newItem = new Item();
        final ItemTemplates item = (ItemTemplates) Map.itemTemplates.get(5).get(idtemp);
        for (int i = 0; i < 10; ++i) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short) (item.atb[i] + item.atb[i] / 4);
            }
        }
        newItem.setTemplate(idTemplate, p.charClass, p.charClass, idTemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = item.level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.dateCreate = getDayTime(0L);
        if (lock) {
            newItem.lock = 1;
        }
        if (time > 0) {
            newItem.timeLoan = System.currentTimeMillis();
            newItem.minuteExist = time;
        }
        newItem.createAttributeItemCoat(true, (byte) ma_vat, -1);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        return newItem;
    }

    public static Item createPhiPhongMaxBaoKich(final Char player, final int ma_vat, final int time, final Item itemReceive, final boolean isMax, final boolean is8x, final int plus, final boolean isLock) {
        final int[] id = {584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597, 840};
        int lv = player.lvDetail.lv;
        if (lv < 35) {
            lv = 35;
        }
        if (lv > 80) {
            lv = 80;
        }
        if (is8x) {
            lv = 80;
        }
        int idtemp = id[(lv - 35) / 5];
        if (is8x) {
            idtemp = 593;
        }
        final Item newItem = ItemLuckyDraw.createMaxItemCoatLucky(idtemp, player, ma_vat, isMax);
        newItem.dateCreate = getDayTime(0L);
        newItem.timeLoan = System.currentTimeMillis();
        if (isMax) {
            newItem.otherAtt[4] = 30;
        }
        if (time > 0) {
            newItem.timeLoan = System.currentTimeMillis();
            newItem.minuteExist = time;
        }
        if (plus > 0) {
            player.doImbueItem(newItem, plus);
        }
        if (isLock) {
            newItem.lock = 1;
        }
        player.iItems.add(newItem);
        player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
        return newItem;
    }

    
    public static void createNguyetLinhTruong(final Char p, final int ma_vat, final int time, final Item itemReceive, final boolean isMax, final boolean is8x, final int plus, final boolean isLock) {
        if (p != null) {
            int iditem = 796;
            int clazz = p.charClass;
            int magic_vl = ma_vat;
            int minute = time;
            ItemTemplates template = (ItemTemplates) ((Hashtable) itemTemplates.get(clazz)).get(new Integer(iditem));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = p.getIDItem();
            newItem.owner = p.charDBID;
            newItem.level = (newItem.getTemplate()).level;
            newItem.lock = (byte) (isLock ? 1 : 0);
            p.iItems.add(newItem);
            newItem.identify = p.charDBID;
            newItem.clazz = (byte) clazz;
            if (template.type < 3 || template.type == 10 || template.type == 11) {
                newItem.magic_physic = p.typeItemBuy;
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (OfflineMap.isAnimalItem(template.type)) {
                newItem.magic_physic = (byte) magic_vl;
                if (newItem.magic_physic < 0) {
                    newItem.magic_physic = 2;
                }
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (minute > 0) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = minute;
            }
            if (isItemThanThu(template.type)) {
                newItem.doAddOptionThanThu(template.atb[0]);
            }
            if (newItem.isVukhiThoiTrang()) {
                newItem.createAttributeNguyetLinhTruong();
            }
            if (newItem.isVukhiThoiTrang() && isMax) {
                newItem.createAttributeNguyetLinhTruongMax();
            }
            newItem.createAttChoi();
            newItem.dateCreate = Char.getDayTime(0L);
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        }
    }

    public static void createThanKhi(final Char p, final int idThanKhi, final int ma_vat, final int time, final Item itemReceive, final boolean isMax, final boolean is8x, final int plus, final boolean isLock) {
        if (p != null) {
            int iditem = idThanKhi;
            int clazz = p.charClass;
            int magic_vl = ma_vat;
            int minute = time;
            ItemTemplates template = (ItemTemplates) ((Hashtable) itemTemplates.get(clazz)).get(new Integer(iditem));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = p.getIDItem();
            newItem.owner = p.charDBID;
            newItem.level = (newItem.getTemplate()).level;
            newItem.lock = 0;
            p.iItems.add(newItem);
            newItem.identify = p.charDBID;
            newItem.clazz = (byte) clazz;
            if (template.type < 3 || template.type == 10 || template.type == 11) {
                newItem.magic_physic = p.typeItemBuy;
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (OfflineMap.isAnimalItem(template.type)) {
                newItem.magic_physic = (byte) magic_vl;
                if (newItem.magic_physic < 0) {
                    newItem.magic_physic = 2;
                }
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (minute > 0) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = minute;
            }
            if (isItemThanThu(template.type)) {
                newItem.doAddOptionThanThu(template.atb[0]);
            }
            if (newItem.isThanKhi()) {
                newItem.createAttributeThanKhiVip();
            } else if (newItem.isVukhiThoiTrang()) {
                newItem.createAttributeVuKhiThoiTrang();
                if (isMax) {
                    newItem.createAttributeVuKhiThoiTrangMax();
                }
            }
            newItem.createAttChoi();
            newItem.dateCreate = Char.getDayTime(0L);
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        }
    }


    public static void createVuKhiThoiTrangID(final Char p, final int iditem, final int ma_vat, final int time, final Item itemReceive, final boolean isMax, final boolean is8x, final int plus, final boolean isLock) {
        if (p != null) {
            int clazz = p.charClass;
            int magic_vl = ma_vat;
            int minute = time;
            ItemTemplates template = (ItemTemplates) ((Hashtable) itemTemplates.get(clazz)).get(new Integer(iditem));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = p.getIDItem();
            newItem.owner = p.charDBID;
            newItem.level = (newItem.getTemplate()).level;
            newItem.lock = 1;
            p.iItems.add(newItem);
            newItem.identify = p.charDBID;
            newItem.clazz = (byte) clazz;
            if (template.type < 3 || template.type == 10 || template.type == 11) {
                newItem.magic_physic = p.typeItemBuy;
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (OfflineMap.isAnimalItem(template.type)) {
                newItem.magic_physic = (byte) magic_vl;
                if (newItem.magic_physic < 0) {
                    newItem.magic_physic = 2;
                }
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (minute > 0) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = minute;
            }
            if (isItemThanThu(template.type)) {
                newItem.doAddOptionThanThu(template.atb[0]);
            }
            if (newItem.isVukhiThoiTrang() && !isMax) {
                newItem.createAttributeVuKhiThoiTrang();
            }
            if (newItem.isVukhiThoiTrang() && isMax) {
                newItem.createAttributeVuKhiThoiTrangMax();
            }
            newItem.createAttChoi();
            newItem.dateCreate = Char.getDayTime(0L);
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        }
    }

    
    
    public static void createVuKhiThoiTrang(final Char p, final int ma_vat, final int time, final Item itemReceive, final boolean isMax, final boolean is8x, final int plus, final boolean isLock) {
        if (p != null) {
            int iditem = (715 + p.charClass);
            int clazz = p.charClass;
            int magic_vl = ma_vat;
            int minute = time;
            ItemTemplates template = (ItemTemplates) ((Hashtable) itemTemplates.get(clazz)).get(new Integer(iditem));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = p.getIDItem();
            newItem.owner = p.charDBID;
            newItem.level = (newItem.getTemplate()).level;
            newItem.lock = 1;
            p.iItems.add(newItem);
            newItem.identify = p.charDBID;
            newItem.clazz = (byte) clazz;
            if (template.type < 3 || template.type == 10 || template.type == 11) {
                newItem.magic_physic = p.typeItemBuy;
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (OfflineMap.isAnimalItem(template.type)) {
                newItem.magic_physic = (byte) magic_vl;
                if (newItem.magic_physic < 0) {
                    newItem.magic_physic = 2;
                }
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (minute > 0) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = minute;
            }
            if (isItemThanThu(template.type)) {
                newItem.doAddOptionThanThu(template.atb[0]);
            }
            if (newItem.isVukhiThoiTrang() && !isMax) {
                newItem.createAttributeVuKhiThoiTrang();
            }
            if (newItem.isVukhiThoiTrang() && isMax) {
                newItem.createAttributeVuKhiThoiTrangMax();
            }
            newItem.createAttChoi();
            newItem.dateCreate = Char.getDayTime(0L);
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        }
    }
    
    public static void createVuKhiThoiTrangTHANBINH_LV(final Char p, final int ma_vat, final int time, final Item itemReceive, final boolean isMax, final int level, final int tinhLuyen, final boolean isLock) {
        if (p != null) {
            int iditem;
            // Xác định ID item dựa vào level
            if (level == 60) {
                iditem = 758 + p.charClass;
            } else if (level == 70) {
                iditem = 763 + p.charClass;
            } else if (level == 80) {
                iditem = 768 + p.charClass;
            } else {
                return; // Return nếu level không hợp lệ
            }
            
            int clazz = p.charClass;
            int magic_vl = ma_vat;
            int minute = time;
            ItemTemplates template = (ItemTemplates) ((Hashtable) itemTemplates.get(clazz)).get(new Integer(iditem));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = p.getIDItem();
            newItem.owner = p.charDBID;
            newItem.level = (newItem.getTemplate()).level;
            newItem.lock = 0;
            p.iItems.add(newItem);
            newItem.identify = p.charDBID;
            newItem.clazz = (byte) clazz;
    
            // Set tinh luyện nếu có
            if (tinhLuyen > 0 && tinhLuyen <= 99) {
                newItem.TinhLuyen = tinhLuyen;
            }
    
            if (template.type < 3 || template.type == 10 || template.type == 11) {
                newItem.magic_physic = p.typeItemBuy;
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (OfflineMap.isAnimalItem(template.type)) {
                newItem.magic_physic = (byte) magic_vl;
                if (newItem.magic_physic < 0) {
                    newItem.magic_physic = 2;
                }
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (minute > 0) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = minute;
            }
            if (isItemThanThu(template.type)) {
                newItem.doAddOptionThanThu(template.atb[0]);
            }
            if (newItem.isVukhiThoiTrang() && !isMax) {
                newItem.createAttributeVuKhiThoiTrang();
            }
            if (newItem.isVukhiThoiTrang() && isMax) {
                newItem.createAttributeVuKhiThoiTrangMax();
            }
            newItem.createAttChoi();
            newItem.dateCreate = Char.getDayTime(0L);
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        }
    }

    public static void createVuKhiThoiTrangTHANBINH_NEW(final Char p, final int ma_vat, final int time, final Item itemReceive, final boolean isMax, final boolean is8x, final int plus, final boolean isLock) {
        if (p != null) {
            int iditem = 758 + p.charClass;
            int clazz = p.charClass;
            int magic_vl = ma_vat;
            int minute = time;
            ItemTemplates template = (ItemTemplates) ((Hashtable) itemTemplates.get(clazz)).get(new Integer(iditem));
            Item newItem = null;
            newItem = new Item(template, false, clazz, clazz, template.id);
            newItem.id = p.getIDItem();
            newItem.owner = p.charDBID;
            newItem.level = (newItem.getTemplate()).level;
            newItem.lock = 0;
            p.iItems.add(newItem);
            newItem.identify = p.charDBID;
            newItem.clazz = (byte) clazz;
            if (template.type < 3 || template.type == 10 || template.type == 11) {
                newItem.magic_physic = p.typeItemBuy;
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (OfflineMap.isAnimalItem(template.type)) {
                newItem.magic_physic = (byte) magic_vl;
                if (newItem.magic_physic < 0) {
                    newItem.magic_physic = 2;
                }
                if (magic_vl == 0) {
                    newItem.atb[6] = newItem.atb[1];
                    newItem.atb[1] = (short) (newItem.atb[1] / 10);
                } else if (magic_vl == 1) {
                    newItem.atb[6] = (short) (newItem.atb[1] / 10);
                }
            }
            if (minute > 0) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = minute;
            }
            if (isItemThanThu(template.type)) {
                newItem.doAddOptionThanThu(template.atb[0]);
            }
            if (newItem.isVukhiThoiTrang() && !isMax) {
                newItem.createAttributeVuKhiThoiTrang();
            }
            if (newItem.isVukhiThoiTrang() && isMax) {
                newItem.createAttributeVuKhiThoiTrangMax();
            }
            newItem.createAttChoi();
            newItem.dateCreate = Char.getDayTime(0L);
            p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        }
    }

    public static boolean isItemThanThu(int type) {
        return (type == 25);
    }

    public static void genItemPhiPhong() {
        final int[] id = {584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597};
        final int time = 0;
        for (int i = 0; i < id.length; ++i) {
            final int idTemplate = id[i];
            final Item newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idTemplate), false, 0, 0, idTemplate);
            newItem.level = newItem.getTemplate().level;
            newItem.createAttributeItemCoat(false, (byte) (-1), -1);
            newItem.dateCreate = getDayOpen(0L);
            System.out.println(String.valueOf(newItem.getTemplate().name) + " LV_" + newItem.level + "_" + newItem.getDBInfo() + "|" + newItem.getAttribute());
        }
    }

    public void genItemPhiPhong(final int idTemplate, final boolean isMax, final int mv, final int time) {
        final int[] id = {584, 585, 586, 587, 588, 589, 590, 591, 592, 593, 594, 595, 596, 597};
        int lv = this.lvDetail.lv;
        if (lv < 35) {
            lv = 35;
        }
        final int idtemp = id[(lv - 35) / 5];
        final Item newItem = new Item();
        final ItemTemplates item = (ItemTemplates) Map.itemTemplates.get(5).get(idtemp);
        for (int i = 0; i < 10; ++i) {
            if (item.atb[i] > 0) {
                newItem.atb[i] = (short) (item.atb[i] + item.atb[i] / 4);
            }
        }
        newItem.setTemplate(idTemplate, this.charClass, this.charClass, idTemplate);
        newItem.id = this.getIDItem();
        newItem.owner = this.charDBID;
        newItem.level = (byte) ((lv - 35) / 5 * 5 + 35);
        newItem.identify = this.charDBID;
        newItem.clazz = this.charClass;
        newItem.timeLoan = System.currentTimeMillis();
        newItem.minuteExist = time;
        newItem.lock = 1;
        newItem.createAttributeItemCoat(true, (byte) mv, -1);
        this.iItems.add(newItem);
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
    }

    public void initListKillMe(final String list) {
        try {
            final String[] data = split(list, ",");
            for (int i = 0; i < data.length; ++i) {
                if (!data[i].trim().equals("")) {
                    this.listKillme.add(data[i].trim());
                }
            }
        } catch (final Exception ex) {
        }
    }

    public void addListKillMe(final String charname2) {
        for (int i = 0; i < this.listKillme.size(); ++i) {
            if (this.listKillme.get(i).equals(charname2)) {
                return;
            }
        }
        this.listKillme.insertElementAt(charname2, 0);
        if (this.listKillme.size() > 20) {
            this.listKillme.remove(this.listKillme.size() - 1);
        }
    }

    public Vector<String> getTopKillMe() {
        Vector<String> result = new Vector<String>();
        final Vector<String> online = new Vector<String>();
        final Vector<String> off = new Vector<String>();
        for (int i = 0; i < this.listKillme.size(); ++i) {
            final String name = this.listKillme.get(i);
            if (CharManager.instance.getCharByCharName(name) != null) {
                online.add(String.valueOf(name) + " - on");
            } else {
                off.add(String.valueOf(name) + " - off");
            }
        }
        result = online;
        for (int i = 0; i < off.size(); ++i) {
            result.add(off.get(i));
        }
        return result;
    }

    public String getSaveListKillMe() {
        String info = "";
        int size = this.listKillme.size();
        if (size > 20) {
            size = 20;
        }
        for (int i = 0; i < size; ++i) {
            info = String.valueOf(info) + this.listKillme.get(i) + ",";
        }
        if (!info.equals("")) {
            info = info.substring(0, info.length() - 1);
        }
        return info;
    }

    public boolean createPoro(final long day, final boolean isAlert) {
        try {
            final Animal anima = new Animal();
            anima.name = "Đại Bạch Thử";
            anima.idImg = Animal.PORO;
            anima.createAttMax();
            anima.level = 4;
            anima.place = 0;
            anima.ownerId = this.charDBID;
            if (day > 0L) {
                anima.texpire = System.currentTimeMillis() + day * 24L * 60L * 60000L;
            }
            anima.id = this.getIDItem();
            this.animal.add(anima);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            Database.instance.saveAnimal(anima);
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "mua poro", "poro");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (isAlert) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được linh thú Đại bạch thử", ""));
        }
        return true;
    }

    public boolean createNguaXuong(final long day, final boolean isAlert) {
        try {
            final Animal anima = new Animal();
            anima.name = "Bạch cốt mã";
            anima.idImg = Animal.NGUA_XUONG;
            anima.createAttMax();
            anima.level = 4;
            anima.place = 0;
            anima.ownerId = this.charDBID;
            if (day > 0L) {
                anima.texpire = System.currentTimeMillis() + day * 24L * 60L * 60000L;
            }
            anima.id = this.getIDItem();
            this.animal.add(anima);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            Database.instance.saveAnimal(anima);
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "mua ngua", "ngua");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (isAlert) {
            this.sendMessage(MessageCreator.createMsgChat(this.id, "Chúc mừng. Bạn đã nhận được linh thú Bạch cốt mã"));
        }
        return true;
    }

    public boolean createHeo(final long day, final boolean isAlert) {
        try {
            final Animal anima = new Animal();
            anima.name = "Đương khang";
            anima.idImg = Animal.HEO;
            anima.createAttMax();
            anima.level = 4;
            anima.place = 0;
            anima.ownerId = this.charDBID;
            if (day > 0L) {
                anima.texpire = System.currentTimeMillis() + day * 24L * 60L * 60000L;
            }
            anima.id = this.getIDItem();
            this.animal.add(anima);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            Database.instance.saveAnimal(anima);
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "mua heo", "heo");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (isAlert) {
            this.sendMessage(MessageCreator.createMsgChat(this.id, "Chúc mừng. Bạn đã nhận được linh thú Đương khang"));
        }
        return true;
    }

    public boolean createHeoBang(final long day, final boolean isAlert) {
        try {
            final Animal anima = new Animal();
            anima.name = "Đương khang Băng Giá";
            anima.idImg = Animal.HEO_BANG;
            anima.createAttMax();
            anima.level = 4;
            anima.place = 0;
            anima.ownerId = this.charDBID;
            if (day > 0L) {
                anima.texpire = System.currentTimeMillis() + day * 24L * 60L * 60000L;
            }
            anima.id = this.getIDItem();
            this.animal.add(anima);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            Database.instance.saveAnimal(anima);
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "mua heo bang", "heo bang");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (isAlert) {
            this.sendMessage(MessageCreator.createMsgChat(this.id, "Chúc mừng. Bạn đã nhận được linh thú Đương khang Băng Giá"));
        }
        return true;
    }

    public boolean createSuTu(final long day, final boolean isAlert) {
        try {
            final Animal anima = new Animal();
            anima.name = "Sư tử";
            anima.idImg = Animal.SU_TU;
            anima.createAttMax();
            anima.level = 4;
            anima.place = 0;
            anima.ownerId = this.charDBID;
            if (day > 0L) {
                anima.texpire = System.currentTimeMillis() + day * 24L * 60L * 60000L;
            }
            anima.id = this.getIDItem();
            this.animal.add(anima);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            Database.instance.saveAnimal(anima);
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "mua su tu", "su tu");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (isAlert) {
            this.sendMessage(MessageCreator.createMsgChat(this.id, "Chúc mừng. Bạn đã nhận được linh thú Sư tử"));
        }
        return true;
    }

    public boolean createRongLon(final long day, final boolean isAlert) {
        try {
            final Animal anima = new Animal();
            anima.name = "Rồng Băng";
            anima.idImg = Animal.RONG_LON;
            anima.createAttMax();
            anima.level = 4;
            anima.place = 0;
            anima.ownerId = this.charDBID;
            if (day > 0L) {
                anima.texpire = System.currentTimeMillis() + day * 24L * 60L * 60000L;
            }
            anima.id = this.getIDItem();
            this.animal.add(anima);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            Database.instance.saveAnimal(anima);
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "mua heo", "heo");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (isAlert) {
            this.sendMessage(MessageCreator.createMsgChat(this.id, "Chúc mừng. Bạn đã nhận được linh thú Rồng Băng"));
        }
        return true;
    }

    public boolean createLan(final long minute, final boolean isAlert, final int khanghoatuyet) {
        try {
            final Animal anima = new Animal();
            anima.name = "Lân Sư Tử";
            anima.idImg = Animal.LAN;
            anima.createAttMax();
            anima.level = 4;
            anima.place = 0;
            anima.ownerId = this.charDBID;
            if (minute > 0L) {
                anima.texpire = System.currentTimeMillis() + minute * 60000L;
            }
            anima.KHANG_HOA_TUYET = (byte) khanghoatuyet;
            anima.id = this.getIDItem();
            this.animal.add(anima);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            Database.instance.saveAnimal(anima);
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "mua lan", "lan");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (isAlert) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được linh thú Lân sư tử", ""));
        }
        return true;
    }

    public void createTuanLoc(final long day) {
        try {
            final Animal anima = new Animal();
            anima.name = "Tuần lộc";
            anima.idImg = Animal.TUAN_LOC;
            anima.createAttMax();
            anima.level = 4;
            anima.place = 0;
            anima.ownerId = this.charDBID;
            if (day > 0L) {
                anima.texpire = System.currentTimeMillis() + day * 24L * 60L * 60000L;
            }
            anima.id = this.getIDItem();
            this.animal.add(anima);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
            Database.instance.saveAnimal(anima);
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "mua tuan loc", "tuan loc");
        } catch (final Exception ex) {
        }
        this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được linh thú tuần lộc", ""));
    }

    public void createPhuongHoang(final long day, final int idPH, final boolean isAlert) {
        try {
            try {
                final Animal anima = new Animal();
                anima.name = "Phượng hoàng";
                anima.idImg = Animal.PHUONG_HOANG;

                if (idPH == Animal.PHUONG_HOANG_BANG) {
                    anima.name = "Phượng hoàng băng";
                    anima.idImg = Animal.PHUONG_HOANG_BANG;
                }
                if (idPH == Animal.PHUONG_HOANG_7MAU) {
                    anima.name = "Phượng hoàng đa sắc";
                    anima.idImg = Animal.PHUONG_HOANG_7MAU;
                }
                if (idPH == Animal.PHUONG_HOANG_MOC) {
                    anima.name = "Phượng hoàng Mộc";
                    anima.idImg = Animal.PHUONG_HOANG_MOC;
                }
                if (idPH == Animal.PHUONG_HOANG_KIM) {
                    anima.name = "Phượng hoàng Kim";
                    anima.idImg = Animal.PHUONG_HOANG_KIM;
                }
                if (idPH == Animal.PHUONG_HOANG_THO) {
                    anima.name = "Phượng hoàng Thổ";
                    anima.idImg = Animal.PHUONG_HOANG_THO;
                }

                anima.createAttMax();
                anima.level = 4;
                anima.place = 0;
                anima.ownerId = this.charDBID;
                if (day > 0L) {
                    anima.texpire = System.currentTimeMillis() + day * 24L * 60L * 60000L;
                }
                anima.id = this.getIDItem();
                this.animal.add(anima);
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
                Database.instance.saveAnimal(anima);
                Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "trung " + anima.name, "phuonghoang");
                if (isAlert) {
                    this.sendMessage(MessageCreator.createMsgChat(this.id, "Chúc mừng. Bạn đã nhận được linh thú " + anima.name));
                }

            } catch (final Exception e) {
                e.printStackTrace();
            }
        } catch (final Exception ex) {
        }
    }

    public void createTrungPhuongHoang(final long day, final int idPH) {
        try {
            if (idPH == 16) {
                final int[] potions = this.potions;
                final int n = 86;
                ++potions[n];
            } else if (idPH == 17) {
                final int[] potions2 = this.potions;
                final int n2 = 70;
                ++potions2[n2];
            } else if (idPH == 18) {
                final int[] potions2 = this.potions;
                final int n2 = 71;
                ++potions2[n2];
            } else if (idPH == 19) {
                final int[] potions2 = this.potions;
                final int n2 = 111;
                ++potions2[n2];
            }
            Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, "Trung trung phuong hoang " + idPH, "phuonghoang");
        } catch (final Exception ex) {
        }
        this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được trứng phượng hoàng", ""));
    }

    public static void createChoi(final int idtemplate, final Char p) {
        p.buychoi = 1;
        final Item newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idtemplate), false, 0, 0, idtemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = newItem.getTemplate().level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.createAttChoiVinhVien();
        newItem.dateCreate = getDayTime(0L);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        final String info = String.valueOf(newItem.getDBInfo()) + "|" + newItem.getAttribute() + "_" + newItem.level;
        p.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được " + newItem.getName(), ""));
        Database.instance.saveOrtherLog("", p.getName(), info, "nhanchoi");
    }


    public static Item createChoi(final int idtemplate, final Char p, final int minute, final int lock, final boolean isHaveBienhinh) {
        final Item newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idtemplate), false, 0, 0, idtemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = newItem.getTemplate().level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.createAttChoiVinhVien();
        newItem.lock = (byte) lock;
        newItem.dateCreate = getDayTime(0L);
        if (minute > 0) {
            newItem.timeLoan = System.currentTimeMillis();
            newItem.minuteExist = minute;
        }
        if (isHaveBienhinh) {
            newItem.setOptionHoaNguoiTuyetChoi();
        }
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        final String info = String.valueOf(newItem.getDBInfo()) + "|" + newItem.getAttribute() + "_" + newItem.level;
        p.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được " + newItem.getName(), ""));
        Database.instance.saveOrtherLog("", p.getName(), info, "nhanchoi");
        if (isDemNoel()) {
            p.receiveGiftEvent = 1;
        }
        return newItem;
    }

    

    public static Item createItemSoLuong(final int idtemplate, int soLuong, final Char p) {
        final Item newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idtemplate), false, 0, 0, idtemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = newItem.getTemplate().level;
        newItem.identify = p.charDBID;
        newItem.clazz = -1;
        newItem.lock = 1;
        newItem.dateCreate = getDayTime(0L);
        if (newItem.isManhSuuTap()) {
            boolean found = false;
            for (Item item : p.iItems) {
                if (item.isManhSuuTap() && item.getTemplate().id == newItem.getTemplate().id) {
                    // Cộng dồn otherAtt[65]
                    item.otherAtt[65] += soLuong;
                    if (item.otherAtt[65] > 30000) {
                        item.otherAtt[65] = 30000;
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                newItem.otherAtt[65] = (short) soLuong;
                p.iItems.add(newItem);
            }
        } else {
            p.iItems.add(newItem);
        }
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        final String info = String.valueOf(newItem.getDBInfo()) + "|" + newItem.getAttribute() + "_" + newItem.level;
        p.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được " + soLuong + " " + newItem.getName(), ""));
        Database.instance.saveOrtherLog("", p.getName(), info, "item_soluong");
      
        return newItem;
    }

    public static Item createItemCaiTrang(final int idtemplate, final Char p, int lv) {
        final Item newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idtemplate), false, 0, 0, idtemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.lock = 1;
        newItem.plus = (byte) lv;
   
        if (idtemplate == 903) {
            newItem.setAttributeByBatGioi();
           } else if (idtemplate == 904) {
            newItem.setAttributeBySaTang();
        }
      
        newItem.dateCreate = getDayTime(0L);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        final String info = String.valueOf(newItem.getDBInfo()) + "|" + newItem.getAttribute() + "_" + newItem.level;
        p.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được " + newItem.getName(), ""));
        Database.instance.saveOrtherLog("", p.getName(), info, "item_caitrang");
      
        return newItem;
    }

    public static Item createItemSuuTap(final int idtemplate, final Char p) {
        final Item newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idtemplate), false, 0, 0, idtemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = 0;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.lock = 1;
        newItem.dateCreate = getDayTime(0L);
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        final String info = String.valueOf(newItem.getDBInfo()) + "|" + newItem.getAttribute() + "_" + newItem.level;
        p.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được   " + newItem.getName(), ""));
        Database.instance.saveOrtherLog("", p.getName(), info, "suutap");
      
        return newItem;
    }

    public static Item createHoVeLenhLan(final int idtemplate, final Char p, final int minute, final int lock) {
        final Item newItem = new Item((ItemTemplates) Map.itemTemplates.get(5).get(idtemplate), false, 0, 0, idtemplate);
        newItem.id = p.getIDItem();
        newItem.owner = p.charDBID;
        newItem.level = newItem.getTemplate().level;
        newItem.identify = p.charDBID;
        newItem.clazz = p.charClass;
        newItem.createAttChoiVinhVien();
        newItem.lock = (byte) lock;
        newItem.dateCreate = getDayTime(0L);
        if (minute > 0) {
            newItem.timeLoan = System.currentTimeMillis();
            newItem.minuteExist = minute;
        }
        p.iItems.add(newItem);
        p.sendMessage(MessageCreator.createCharInventoryMessage(p, 1));
        final String info = String.valueOf(newItem.getDBInfo()) + "|" + newItem.getAttribute() + "_" + newItem.level;
        p.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã nhận được " + newItem.getName(), ""));
        Database.instance.saveOrtherLog("", p.getName(), info, "nhanchoi");
        if (isDemNoel()) {
            p.receiveGiftEvent = 1;
        }
        return newItem;
    }

    public void createPet(final int idpet, final int time) {
        final Pet pet = new Pet(Map.petTemplate[idpet].idTemplate, Map.petTemplate[idpet].idImg, this.getIDItem(), 0, this.charDBID, Map.petTemplate[idpet].name);
        pet.totalMinute = time;
        pet.type = Map.petTemplate[idpet].type_move;
        pet.timeBuy = System.currentTimeMillis();
        this.pet.add(pet);
        Database.instance.savePet(pet);
        Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, String.valueOf(pet.getName()) + "_" + this.luong, "pet");
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
        this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã mua được " + pet.getName(), ""));
    }

    public void createPetVinhVien(final int idpet) {
        final Pet pet = new Pet(Map.petTemplate[idpet].idTemplate, Map.petTemplate[idpet].idImg, this.getIDItem(), 0, this.charDBID, Map.petTemplate[idpet].name);
        pet.type = Map.petTemplate[idpet].type_move;
        this.pet.add(pet);
        Database.instance.savePet(pet);
        Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, String.valueOf(pet.getName()) + "_" + this.luong, "pet");
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
        this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã mua được " + pet.getName(), ""));
    }

    public Pet createPet(final int idpet, final int time, final Item itemPet) {
        final Pet pet = new Pet(Map.petTemplate[idpet].idTemplate, Map.petTemplate[idpet].idImg, this.getIDItem(), 0, this.charDBID, Map.petTemplate[idpet].name, itemPet);
        pet.totalMinute = time;
        pet.type = Map.petTemplate[idpet].type_move;
        pet.timeBuy = System.currentTimeMillis();
        if (time > 0) {
            pet.totalMinute = time;
            pet.timeBuy = itemPet.timeLoan;
            pet.ispetthoihan = true;
        }
        this.pet.add(pet);
        pet.itemPet = itemPet;
        Database.instance.savePet(pet);
        Database.instance.saveOrtherLog("tob_log_other_animal", this.charname, String.valueOf(pet.getName()) + "_" + this.luong, "pet");
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 2));
        this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng. Bạn đã mua được " + pet.getName(), ""));
        return pet;
    }

    public String[] getInfoSaveTubinh() {
        final String[] info = {"", ""};
        for (int i = 0; i < this.tubinh.size(); ++i) {
            final String[] array = info;
            final int n = 0;
            array[n] = String.valueOf(array[n]) + this.tubinh.get(i).id + ",";
            final String[] array2 = info;
            final int n2 = 1;
            array2[n2] = String.valueOf(array2[n2]) + this.tubinh.get(i).getInfoSave() + "|";
        }
        if (!info[0].equals("")) {
            info[0] = info[0].substring(0, info[0].length() - 1);
        }
        if (!info[1].equals("")) {
            info[1] = info[1].substring(0, info[1].length() - 1);
        }
        return info;
    }

    public String[] getInfoSaveFruit() {
        final String[] info = {"", "", "", "", "", ""};
        if (this.fruit != null) {
            for (int i = 0; i < FruitTemplate.MAX_FRUIT; ++i) {
                if (this.fruit[i] != null) {
                    final String[] array = info;
                    final int n = 0;
                    array[n] = String.valueOf(array[n]) + this.fruit[i].id + ",";
                    final String[] array2 = info;
                    final int n2 = 1;
                    array2[n2] = String.valueOf(array2[n2]) + this.fruit[i].status + ",";
                    final String[] array3 = info;
                    final int n3 = 2;
                    array3[n3] = String.valueOf(array3[n3]) + this.fruit[i].tcount + ",";
                    final String[] array4 = info;
                    final int n4 = 3;
                    array4[n4] = String.valueOf(array4[n4]) + this.fruit[i].gift + ",";
                    final String[] array5 = info;
                    final int n5 = 4;
                    array5[n5] = String.valueOf(array5[n5]) + this.fruit[i].tcountTangtoc + ",";
                    final String[] array6 = info;
                    final int n6 = 5;
                    array6[n6] = String.valueOf(array6[n6]) + this.fruit[i].tcountKho + ",";
                }
            }
        }
        return info;
    }

    public void createTubinh() {
        if (this.tubinh.size() == 0) {
            for (int i = 0; i < TuBinhTemplate.ALL_TU_BINH.size(); ++i) {
                final TuBinhTemplate t = new TuBinhTemplate();
                t.id = (byte) i;
                this.tubinh.add(t);
            }
        } else if (this.tubinh.size() == 4) {
            for (int i = 4; i < TuBinhTemplate.ALL_TU_BINH.size(); ++i) {
                final TuBinhTemplate t = new TuBinhTemplate();
                t.id = (byte) i;
                this.tubinh.add(t);
            }
        } else if (this.tubinh.size() == 8) {
            for (int i = 8; i < TuBinhTemplate.ALL_TU_BINH.size(); ++i) {
                final TuBinhTemplate t = new TuBinhTemplate();
                t.id = (byte) i;
                this.tubinh.add(t);
            }
        } else if (this.tubinh.size() == 12) {
            for (int i = 12; i < TuBinhTemplate.ALL_TU_BINH.size(); ++i) {
                final TuBinhTemplate t = new TuBinhTemplate();
                t.id = (byte) i;
                this.tubinh.add(t);
            }
        }
    }

    public void doAddManhTubinh(final int idManhTemplate, final boolean lock) {
        if (lock && this.listGemitemLock[idManhTemplate] <= 0) {
            return;
        }
        if (!lock && this.listGemitem[idManhTemplate] - this.listGemitemSell[idManhTemplate] <= 0) {
            return;
        }
        int idTubinh = 0;
        if (idManhTemplate >= 159 && idManhTemplate <= 174) {
            idTubinh = (idManhTemplate - 159) / 4;
        } else if (idManhTemplate >= 179 && idManhTemplate <= 226) {
            idTubinh = (idManhTemplate - 179) / 4 + 4;
        }
        final TuBinhTemplate temp = this.tubinh.get(idTubinh);
        int indexManh = 0;
        if (idManhTemplate >= 159 && idManhTemplate <= 174) {
            indexManh = (idManhTemplate - 159) % 4;
        } else if (idManhTemplate >= 179 && idManhTemplate <= 226) {
            indexManh = (idManhTemplate - 179) % 4;
        }
        if (temp.addManh(indexManh, this)) {
            if (lock) {
                final int[] listGemitemLock = this.listGemitemLock;
                --listGemitemLock[idManhTemplate];
                final int[] allGemUseLock = this.allGemUseLock;
                ++allGemUseLock[idManhTemplate];
            } else {
                final int[] listGemitem = this.listGemitem;
                --listGemitem[idManhTemplate];
                final int[] allGemUse = this.allGemUse;
                ++allGemUse[idManhTemplate];
            }
            if (this.listGemitem[idManhTemplate] <= 0 && this.listGemitemLock[idManhTemplate] <= 0) {
                for (int i = 0; i < this.gemItem.size(); ++i) {
                    final GemItem g = this.gemItem.get(i);
                    if (g.idGemTemplate == idManhTemplate) {
                        this.gemItem.remove(g);
                        this.ALL_ID_USE.remove(g.realId);
                        break;
                    }
                }
            }
            MessageCreator.createMsgCharTuBinh(this);
            this.sendMessage(MessageCreator.createCharGemItem(this));
        } else {
            this.sendMessage(MessageCreator.createServerAlertMessage("Không thể cộng thêm", ""));
        }
    }

    public void createFruit() {
        if (this.fruit[4] == null) {
            this.fruit[4] = new FruitTemplate();
            this.fruit[4].id = 4;
            this.fruit[4].tcount = System.currentTimeMillis();
            this.fruit[4].type = this.fruit[4].getTemplate().type;
        }
        if (this.fruit[1] == null) {
            this.fruit[1] = new FruitTemplate();
            this.fruit[1].id = 1;
            this.fruit[1].tcount = System.currentTimeMillis();
            this.fruit[1].type = this.fruit[1].getTemplate().type;
        }
        final byte[] id = {0, 2, 3, 5, 6};
        for (int i = 0; i < this.fruit.length; ++i) {
            if (this.fruit[i] == null) {
                this.fruit[i] = new FruitTemplate();
                this.fruit[i].id = id[Map.r.nextInt(id.length)];
                this.fruit[i].type = this.fruit[i].getTemplate().type;
                this.fruit[i].tcount = System.currentTimeMillis();
                this.fruit[i].tcountTangtoc = System.currentTimeMillis() + 600000L;
                this.fruit[i].initGift(this.lvDetail.lv);
            }
        }
    }

    public synchronized short getIDItem() {
        while (this.ALL_ID_USE.get(this.ID_ITEM) != null) {
            ++this.ID_ITEM;
        }
        this.ALL_ID_USE.put(this.ID_ITEM, this.ID_ITEM);
        return this.ID_ITEM;
    }

    public void removeIDItem(final short id) {
        this.ALL_ID_USE.remove(id);
    }

    public boolean isMasterClan() {
        return this.rankClan == 0;
    }

    public boolean isCharNam() {
        return this.gender == 1;
    }

    public boolean isCharNu() {
        return this.gender == 2;
    }

    public void doSetAutoImbue(final short idItem, final short[] idGem, final int plus, final byte[] lock) {
        this.startAutoImbue = false;
        final Item item = this.getItemFormVector(this.iItems, idItem);
        if (item == null) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Không tìm thấy vật phẩm", ""));
            return;
        }
        try {
            if (Map.isItemSelled(this, item.id, item)) {
                return;
            }
            if (!Map.isItemCanImbue(item.getTemplate().type)) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Không thể cường hoá trang bị này", ""));
                return;
            }
            if (item.isManhSuuTap() || item.isTBSuuTap() || item.isCanh() || item.isCaiTrangSuuTap()) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Không thể cường hoá ", ""));
                return;
            }
        } catch (final Exception ex) {
        }
        if (!GemTemplate.isLKD(idGem[0])) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chưa chọn luyện kim dược", ""));
            return;
        }
        this.plus = (byte) plus;
        this.idGemLkd = idGem[0];
        this.lkdLock = (lock[0] == 1);
        if (GemTemplate.isBH(idGem[1])) {
            this.idGemBH = idGem[1];
            this.BHLock = (lock[1] == 1);
        }
        if (GemTemplate.isDaMayMan(idGem[2])) {
            this.idGemMM = idGem[2];
            this.MMLock = (lock[2] == 1);
        }
        this.itemImbue = idItem;
        this.startAutoImbue = true;
    }

    boolean enoughtLkd() {
        if (this.idGemLkd <= 0) {
            return false;
        }
        if (this.lkdLock) {
            return this.listGemitemLock[this.idGemLkd] > 0;
        }
        return this.listGemitem[this.idGemLkd] > 0;
    }

    int getPcBHAuto() {
        if (this.idGemBH <= -1) {
            return 0;
        }
        if (this.BHLock) {
            if (this.listGemitemLock[this.idGemBH] <= 0) {
                this.idGemBH = -1;
                return 0;
            }
            return Map.gemTemplate[this.idGemBH].value;
        } else {
            if (this.listGemitem[this.idGemBH] <= 0) {
                this.idGemBH = -1;
                return 0;
            }
            return Map.gemTemplate[this.idGemBH].value;
        }
    }

    int getSLMM() {
        int maxMM = 0;
        if (this.idGemMM > -1) {
            maxMM = 7 - ((this.idGemBH > -1) ? 1 : 0);
            if (this.MMLock) {
                if (this.listGemitemLock[this.idGemMM] < maxMM) {
                    maxMM = this.listGemitemLock[this.idGemMM];
                }
            } else if (this.listGemitem[this.idGemMM] < maxMM) {
                maxMM = this.listGemitem[this.idGemMM];
            }
        }
        return maxMM;
    }

    int calculatePercenUp(final Char player, final int percent) {
        int pc = 0;
        if (Map.isSpesialLkd(this.idGemLkd)) {
            pc += Map.gemTemplate[this.idGemLkd].value;
        } else {
            pc += percent * Map.gemTemplate[this.idGemLkd].value / 100;
        }
        if (this.idGemMM > -1) {
            for (int slMM = this.getSLMM(), i = 0; i < slMM; ++i) {
                if (this.MMLock) {
                    if (this.listGemitemLock[this.idGemMM] > 0) {
                        pc += percent * Map.gemTemplate[this.idGemMM].value / 100;
                    }
                } else if (this.listGemitem[this.idGemMM] > 0) {
                    pc += percent * Map.gemTemplate[this.idGemMM].value / 100;
                }
            }
        }
        return pc;
    }

    public void doImbueItem(final Item item, final int endplus) {
        if (item != null && item.plus < 15) {
            for (int j = item.plus; j < endplus; ++j) {
                ++item.plus;
                final int[] plus = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};
                for (int i = 0; i < 10; ++i) {
                    final short[] atb = item.atb;
                    final int n = i;
                    atb[n] += (short) ((i > 0) ? 1 : plus[item.plus]);
                }
                if (item.plus == endplus) {
                    break;
                }
            }
        }
    }

    public void doAutoImbue() {
        if (!this.startAutoImbue) {
            return;
        }
        final int[] percent = {100, 90, 85, 80, 70, 30, 15, 10, 7, 4, 3, 2, 1, 1, 0};
        final Item item = this.getItemFormVector(this.iItems, (short) this.itemImbue);
        if (item != null) {
            if (!Map.isItemCanImbue(item.getTemplate().type)) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Không thể cường hoá trang bị này", ""));
                return;
            }
            if (item.isManhSuuTap() || item.isTBSuuTap() || item.isCanh() || item.isCaiTrangSuuTap()) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Không thể cường hoá ", ""));
                return;
            }
            boolean isLock = false;
            final int slDaMayMan = this.getSLMM();
            String infoGemItem = String.valueOf(this.idGemLkd) + "_" + this.lkdLock;
            if (this.idGemBH > -1) {
                infoGemItem = String.valueOf(infoGemItem) + " BH " + this.idGemBH + "_" + this.BHLock;
            }
            if (this.idGemMM > -1) {
                infoGemItem = String.valueOf(infoGemItem) + " DMM " + this.idGemMM + "_" + slDaMayMan + "_" + this.MMLock;
            }
            if (this.lkdLock || this.BHLock || this.MMLock) {
                isLock = true;
            }
            if (item.plus < this.plus) {
                final int pcInsu = Map.random(100);
                final int bh = this.getPcBHAuto();
                final int pc = Map.random(100);
                final int up = this.calculatePercenUp(this, percent[item.plus]);
                if (pc <= percent[item.plus] + up) {
                    final Item item2 = item;
                    ++item2.plus;
                    final int[] plus = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};
                    for (int i = 0; i < 10; ++i) {
                        final short[] atb = item.atb;
                        final int n = i;
                        atb[n] += (short) ((i > 0) ? 1 : plus[item.plus]);
                    }
                    Message m = new Message(-69);
                    try {
                        m.dos.writeByte(1);
                        m.dos.writeUTF("Thành công +" + item.plus);
                        m.dos.writeByte(0);
                        this.sendMessage(m);
                        final String sub = String.valueOf(item.getTemplate().name) + "_" + item.plus + "|" + item.getAttribute() + "|" + item.getDBInfo();
                        Database.instance.saveOrtherLog("tob_log_other_kham", this.charname, String.valueOf(infoGemItem) + " imbue" + sub + " > ", "ibok");
                    } catch (final Exception ex) {
                    }
                    if (item.plus == this.plus) {
                        this.startAutoImbue = false;
                        m = new Message(-69);
                        try {
                            m.dos.writeByte(1);
                            m.dos.writeUTF("Hoàn thành +" + item.plus);
                            m.dos.writeByte(1);
                            this.sendMessage(m);
                        } catch (final Exception ex2) {
                        }
                    }
                } else {
                    final Message j = new Message(-69);
                    try {
                        if (pcInsu > bh) {
                            final Item item3 = item;
                            --item3.plus;
                            if (item.plus > 0) {
                                final int[] plus2 = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 10};
                                for (int k = 0; k < 10; ++k) {
                                    final short[] atb2 = item.atb;
                                    final int n2 = k;
                                    atb2[n2] -= (short) ((k > 0) ? 1 : plus2[item.plus + 1]);
                                    if (item.atb[k] < 0) {
                                        item.atb[k] = 0;
                                    }
                                }
                            }
                        }
                        j.dos.writeByte(1);
                        j.dos.writeUTF("Thất bại +" + item.plus);
                        j.dos.writeByte(0);
                        this.sendMessage(j);
                        final String sub2 = String.valueOf(item.getTemplate().name) + "_" + item.plus + "|" + item.getAttribute() + "|" + item.getDBInfo();
                        Database.instance.saveOrtherLog("tob_log_other_kham", this.charname, String.valueOf(infoGemItem) + " imbue" + sub2 + " > ", (pcInsu <= bh) ? "fib" : "fibd");
                        j.cleanup();
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                if (isLock) {
                    item.lock = 1;
                }
                this.delGem(this.idGemLkd, 1, this.lkdLock);
                if (this.idGemBH > -1) {
                    this.delGem(this.idGemBH, 1, this.BHLock);
                }
                if (this.idGemMM > -1) {
                    this.delGem(this.idGemMM, slDaMayMan, this.MMLock);
                }
                this.sendMessage(MessageCreator.createCharGemItem(this));
                try {
                    this.sendMessage(MessageCreator.createMsgItemInfo(item));
                } catch (final Exception e2) {
                    e2.printStackTrace();
                }
                if (!this.startAutoImbue) {
                    this.idGemLkd = -1;
                }
                this.xu -= 5000L;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                if (!this.enoughtLkd() || this.xu < 5000L) {
                    this.idGemLkd = -1;
                    final Message j = new Message(-69);
                    try {
                        j.dos.writeByte(1);
                        j.dos.writeUTF("Hoàn thành +" + item.plus);
                        j.dos.writeByte(1);
                        this.sendMessage(j);
                    } catch (final Exception ex3) {
                    }
                    this.startAutoImbue = false;
                }
            } else {
                this.startAutoImbue = false;
                this.idGemLkd = -1;
            }
        }
    }

    public void setLuong(final int l) {
        this.luong = l;
        if (this.luong < 0) {
            this.luong = 0;
        }
    }

    public int getLuong() {
        if (this.luong < 0) {
            Database.instance.banChar(this, "amLuong");
        }
        return (this.luong > 0) ? this.luong : 0;
    }

    public int getLuongLock() {
        if (this.lockLuong < 0) {
            Database.instance.banChar(this, "amluongLock");
        }
        return (this.lockLuong > 0) ? this.lockLuong : 0;
    }

    public void addLuong(final int luong) {
        this.luong += luong;
    }

    public void addLuongLock(final int luong) {
        this.lockLuong += luong;
    }

    public void addXu(final long xu, final String vitri) {
        this.xu += xu;
        this.xutim += xu;
        if (xu != 0L) {
            Database.instance.saveOrtherLog("", this.charname, String.valueOf(this.xu) + " nhan " + xu + " tu vi tri " + vitri, "nhanxu");
        }
    }

    public void subXu(final long xu, final boolean isCountTop, final String vitri) {
        this.xu -= xu;
        this.xumat += xu;
        if (this.xu < 0L) {
            this.xu = 0L;
        }
        if (xu != 0L) {
            Database.instance.saveOrtherLog("", this.charname, String.valueOf(this.xu) + " tru " + xu + " tu vi tri " + vitri, "truxu");
        }
        if (isSuKienNoel()) {
        }
    }

    public long getxu() {
        if (this.xu < 0L) {
            Database.instance.banChar(this, "amXu");
        }
        return (this.xu > 0L) ? this.xu : 0L;
    }

    public void setxu(final long money) {
        this.xu = money;
        if (this.xu < 0L) {
            this.xu = 0L;
        }
    }

    public void subLuong(final long l) {
        this.luong -= (int) l;
        this.luonglost += l;
        if (this.luong < 0) {
            this.luong = 0;
        }
        this.diemxaiVip += (int) l;
        // Database.instance.addCharXaiNapLuong(this, 1);
        isSuKienNoel();
        if (getDayTime(0L).compareTo("2023-04-16 00:00:00") >= 0 && getDayTime(0L).compareTo("2023-06-01 00:00:00") <= 0) {
            Database.instance.updateLuongNap((int) l, this);
        }
        if (isSuKienNoel2023()) {
            this.luongxai += l;
            this.luongxainhanRuong += l;
            Database.instance.addCharEvent_ND(this, 2, 0, (int) l);
        }
        if (isSuKienTrungThul2016()) {
            this.luongxai += l;
            this.luongxainhanRuong += l;
            Database.instance.addCharEvent_ND(this, 2, 0, (int) l);
        }
        if (((TeamServer.isServerHoaLu() || TeamServer.isServerLocal()) && isSuKienGioTo2016()) || isSuKienMini()) {
            this.luongxai += l;
            this.luongxainhanRuong += l;
            Database.instance.addCharEvent(this, 2, 0, (int) l);

            final long a = this.luongxai / 100L;
            if (a > 0L) {
                final int x = 300000;
                this.addXu(a * x, "char 11");
                Database.instance.saveOrtherLog("", this.charname, "nhan " + a * x + " xu khi xai luong : lx= " + this.luongxai + " con: " + (this.luongxai - a * 100L), "xl");
                this.luongxai -= a * 100L;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + a * x + " xu khi xài lượng"));
            }
        }
        if (isSuKienMiniChucNu()) {
            this.luongxai += l;
            this.luongxainhanRuong += l;
            Database.instance.addCharEvent_MiniCN(this, 2, 0, (int) l);
//            System.out.println("Đã + 1 lượng vào điểm top event" + this.luongxai);
            final long a = this.luongxai / 5000L;
            if (a > 0L) {
                final int[] potions = this.potions;
                final int n = 118;
                potions[n] += (int) a;
                Database.instance.saveOrtherLog("", this.charname, "nhan " + a + " ve qua so ph khi xai luong : lx= " + this.luongxai + " con: " + (this.luongxai - a * 5000L), "xl");
                this.luongxai -= a * 5000L;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + a + " vé quay Phượng Hoàng khi xài lượng"));
            }
        }
        if (isSuKienHaloween2016()) {
            long totalLuongDaTieu = 0; // Biến mới để theo dõi tổng lượng đã tiêu
            this.luongxai += l;
            // Tính điểm từ việc tiêu 100 lượng
            totalLuongDaTieu += l; // Cộng thêm lượng tiêu dùng vào tổng

            // Tính điểm từ việc tiêu 100 lượng
            if (totalLuongDaTieu >= 100) {
                long points = totalLuongDaTieu / 100; // Tính số điểm
                this.luongxainhanRuong += points; // Cộng điểm vào số điểm hiện có
                totalLuongDaTieu -= points * 100; // Giữ lại lượng tiêu dùng dư cho lần sau
            }
            
            Database.instance.addCharEventHLW(this, 2, 0, (int) l, (int)this.luongxainhanRuong);
        }
        
    }

    public void subLuongTang(final long l) {
        this.luong -= (int) l;
        this.luonglost += l;
        if (this.luong < 0) {
            this.luong = 0;
        }
        isSuKienNoel();
        if (((TeamServer.isServerHoaLu() || TeamServer.isServerLocal()) && isSuKienGioTo2016()) || isSuKienMini()) {
            this.luongxai += l;
            this.luongxainhanRuong += l;
            Database.instance.addCharEvent(this, 2, 0, (int) l);
            final long a = this.luongxai / 100L;
            if (a > 0L) {
                final int x = 300000;
                this.addXu(a * x, "char 11");
                Database.instance.saveOrtherLog("", this.charname, "nhan " + a * x + " xu khi xai luong : lx= " + this.luongxai + " con: " + (this.luongxai - a * 100L), "xl");
                this.luongxai -= a * 100L;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + a * x + " xu khi xài lượng"));
            }
        }
        if (isSuKienMiniChucNu()) {
            this.luongxai += l;
            this.luongxainhanRuong += l;
            Database.instance.addCharEvent_MiniCN(this, 2, 0, (int) l);
            final long a = this.luongxai / 5000L;
            if (a > 0L) {
                final int[] potions = this.potions;
                final int n = 118;
                potions[n] += (int) a;
                Database.instance.saveOrtherLog("", this.charname, "nhan " + a + " ve qua so ph khi xai luong : lx= " + this.luongxai + " con: " + (this.luongxai - a * 5000L), "xl");
                this.luongxai -= a * 5000L;
                this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                this.sendMessage(MessageCreator.createMsgChat(this.id, "Nhận được " + a + " vé quay Phượng Hoàng khi xài lượng"));
            }
        }
    }

    public void subLuongLock(final long l) {
        this.lockLuong -= (int) l;
        if (this.lockLuong < 0) {
            this.lockLuong = 0;
        }
    }

    public void doFinishAutoInbue() {
        if (this.startAutoImbue) {
            this.startAutoImbue = false;
            final Message m = new Message(-69);
            try {
                m.dos.writeByte(1);
                m.dos.writeUTF("Hoàn thành");
                m.dos.writeByte(1);
                this.sendMessage(m);
            } catch (final Exception ex) {
            }
        }
    }

    public static synchronized void addLuongNapToTop(final Char p) {
        try {
            if (p.luongNap >= 10000L) {
                if (Map.nameTopNapLuong == null || Map.nameTopNapLuong.equals("")) {
                    Map.nameTopNapLuong = p.charname;
                    Map.maxLuongNap = p.luongNap;
                    Database.instance.saveOrtherLog("", p.charname, "nhan dc top 10k " + p.luongNap, "topluong");
                }
                Logger.logStringWithDate(String.valueOf(p.charname) + "_" + p.luongNap + "_" + p.luong, "logtopluong.txt");
            }
        } catch (final Exception ex) {
        }
    }

    public static void initTopNapLuong(final String info) {
        try {
            if (info == null || info.equals("")) {
                return;
            }
            final String[] data = split(info, ",");
            Map.nameTopNapLuong = data[0];
            Map.maxLuongNap = Long.parseLong(data[1]);
        } catch (final Exception ex) {
        }
    }

    public static String getInfoTopLuongSave() {
        return String.valueOf(Map.nameTopNapLuong) + "," + Map.maxLuongNap;
    }

    public static synchronized String[] getTopListCharNapLuong() {
        final String[] list = new String[Char.TOP_LUONG.size()];
        final String[] name = new String[Char.TOP_LUONG.size()];
        final long[] ll = new long[Char.TOP_LUONG.size()];
        synchronized (Char.TOP_LUONG) {
            int i = 0;
            for (final TopLuong top : Char.TOP_LUONG.values()) {
                name[i] = top.name;
                ll[i] = top.luongNap;
                ++i;
            }
            i = 0;
            int j;
            int n;
            int iMin;
            long a;
            String b;
            for (j = 0, n = name.length, j = 0; j < n - 1; ++j) {
                iMin = j;
                for (i = j + 1; i < n; ++i) {
                    if (ll[i] > ll[j]) {
                        iMin = i;
                    }
                }
                if (iMin != j) {
                    a = ll[j];
                    b = name[j];
                    ll[j] = ll[iMin];
                    ll[iMin] = a;
                    name[j] = name[iMin];
                    name[iMin] = b;
                }
            }
            for (j = 0; j < name.length; ++j) {
                list[j] = String.valueOf(name[j]) + " " + ll[j] + " lượng";
            }
            //monitorexit(Char.TOP_LUONG);
        }
        return list;
    }

    public static void initTongKim(final String info) {
        try {
            final String[] data = split(info, ",");
            MapTongKim.ID_TONG_KIM = (short) (data.length / 2);
        } catch (final Exception ex) {
        }
    }

    public static synchronized String[] getTopListCharUseLuong() {
        final String[] list = new String[Char.TOP_CHAR_USE_LUONG.size()];
        final String[] name = new String[Char.TOP_CHAR_USE_LUONG.size()];
        final int[] ll = new int[Char.TOP_CHAR_USE_LUONG.size()];
        final Set<String> set = Char.TOP_CHAR_USE_LUONG.keySet();
        final Iterator<String> itr = set.iterator();
        int i = 0;
        while (itr.hasNext()) {
            final String str = itr.next();
            final int value = Char.TOP_CHAR_USE_LUONG.get(str);
            name[i] = str;
            ll[i] = value;
            ++i;
        }
        i = 0;
        int j;
        int n;
        int iMin;
        int a;
        String b;
        for (j = 0, n = name.length, j = 0; j < n - 1; ++j) {
            iMin = j;
            for (i = j + 1; i < n; ++i) {
                if (ll[i] > ll[j]) {
                    iMin = i;
                }
            }
            if (iMin != j) {
                a = ll[j];
                b = name[j];
                ll[j] = ll[iMin];
                ll[iMin] = a;
                name[j] = name[iMin];
                name[iMin] = b;
            }
        }
        for (j = 0; j < name.length; ++j) {
            list[j] = String.valueOf(name[j]) + " " + ll[j] + " lượng";
        }
        return list;
    }

    public static String getTopCharUseLuong() {
        return new StringBuilder(String.valueOf(MapTongKim.ID_TONG_KIM)).toString();
    }

    public static synchronized void addCharUsingLuong(final Char p, final int luongLost) {
    }

    public void doPlayerMove(final Message message) {
        try {
            final DataInputStream dis = message.dis;
            final long now = System.currentTimeMillis();
            if (this.beStune || this.freeze() || this.isStune() || this.isHoangLoan()) {
                final Message m = new Message(4);
                this.writeActorPos(m, this);
                this.sendMessage(m);
                return;
            }
            if (this.map.givingCard(this)) {
                final Message m = new Message(4);
                this.writeActorPos(m, this);
                this.sendMessage(m);
                return;
            }
            final int x = dis.readUnsignedShort();
            final int y = dis.readUnsignedShort();
            if ((this.x != x || this.y != y) && this.hp <= 0) {
                this.map.doUseHP(this, (short) 4, -1000000);
                this.actorDie();
                return;
            }
            if (!this.map.canMove(x, y)) {
                final Message i = new Message(4);
                this.writeActorPos(i, this);
                this.sendMessage(i);
                return;
            }
            if (this.map.mapId == Map.idMapTown && Map.getTown[this.inCountry] && !MapTown.congthanh.get(this.inCountry).isDead && !this.isAdmin && y < 1206) {
                this.y = 1280;
                final Message i = new Message(4);
                this.writeActorPos(i, this);
                this.sendMessage(i);
                return;
            }
            if (this.x == x && this.y == y) {
                return;
            }
            this.isThodia = false;
            if (this.hp > 0) {
                final long t = now - this.timeMove;
                if (t < 200L) {
                    Thread.sleep(200L - t);
                }
                int distance = (this.mapID == 17) ? 96 : 50;
                if (this.checkTanPhe()) {
                    distance = ((this.mapID == 17) ? 48 : 25);
                }
                this.timeMove = System.currentTimeMillis();
                this.timeLastMove = System.currentTimeMillis();
                this.lastMovex = x;
                this.lastMovey = y;
                this.lastMovex = 0;
                this.lastMovey = 0;
                if (this.getLevel() < 3) {
                    distance = 1000;
                }
                if (Map.abs(this.x - x) <= distance && Map.abs(this.y - y) <= distance) {
                    if (this.mapID == Map.idMapChanNui && !this.isDoChangeMap && isOnLocaltion(x, y)) {
                        final CharInfo cinfo = MapChauBau.all_char_nui_kho_bau.get(this.charname);
                        MapChauBau map = (MapChauBau) RealController.mapList.get(42);
                        final RegionNuiChauBau rg = map.createNewRegion();
                        this.idRegionNuiChauBau = rg.idRegion;
                        map.loadTru(rg, rg.isChauBau = (Map.r.nextInt(500) < (cinfo.isLockGift ? 2 : 5)), this.lvDetail.lv);
                        rg.tend = cinfo.timeNuiChaubau;
                        if (rg.isChauBau) {
                            rg.tend = System.currentTimeMillis() + 180000L;
                            cinfo.timeNuiChaubau = rg.tend;
                            this.sendInfoNuiChauBau();
                            Database.instance.saveOrtherLog("", this.charname, "vao duoc nui chau bau", "chaubau");
                            Database.instance.saveLogThongKeChiTieu("VAO DUOC NUI CHAU BAU", 0, 1, 0L, "nui chau bau");
                        }
                        this.isDoChangeMap = true;
                        this.map.move2Map(this, 36 + Map.r.nextInt() % 5, 75 + Map.r.nextInt() % 5, 42, 0);
                        return;
                    }
                    if (this.mapID == Map.idMapChoLoiDai && isOnLocaltionSanhChoLoiDai(x, y)) {
                        this.map.doReturnVillage(this);
                        return;
                    }
                    final int criX = this.x / 16;
                    final int criY = this.y / 16;
                    final int lastX = x / 16;
                    final int lastY = y / 16;
                    final Positions p1 = new Positions();
                    p1.x1 = criX;
                    p1.y1 = criY;
                    final Positions p2 = new Positions();
                    p2.x1 = lastX;
                    p2.y1 = lastY;
                    if (!this.checkToPoint(p1, p2)) {
                        final Message j = new Message(4);
                        this.writeActorPos(j, this);
                        this.sendMessage(j);
                        return;
                    }
                    this.x = x;
                    this.y = y;
                    final Message j = new Message(4);
                    this.writeActorPos(j, this);
                    try {
                        final Vector<Char> players = this.map.getAllPlayer(this.inCountry, this.region);
                        for (int k = 0; k < players.size(); ++k) {
                            if (players.get(k).id != this.id) {
                                players.get(k).sendMessage(j);
                            }
                        }
                        j.cleanup();
                    } catch (final Exception ex) {
                    }
                    if (this.charthanthu != null && (Map.abs(this.x - this.charthanthu.x) > 48 || Map.abs(this.y - this.charthanthu.y) > 48)) {
                        this.charthanthu.setXtoYto(x, y);
                    }
                    if (this.charCopy != null) {
                        this.charCopy.setXtoYto(x, y);
                    }
                    if (this.charHire != null && !Map.inRangeActor(this, this.charHire, 60)) {
                        this.charHire.setXtoYto(x, y);
                    }
                } else {
                    final Message l = new Message(4);
                    this.writeActorPos(l, this);
                    this.sendMessage(l);
                }
            }
            this.moved = true;
        } catch (final Exception ex2) {
        }
    }

    public static boolean isOnLocaltion(final int x, final int y) {
        for (int i = 0; i < Char.lcx_chan_nui_chau_bau.length; ++i) {
            final int xs = Char.lcx_chan_nui_chau_bau[i][0] * 16;
            final int xe = xs + Char.lcx_chan_nui_chau_bau[i][1] * 16;
            final int ys = Char.lcy_chan_nui_chau_bau[i][0] * 16;
            final int ye = ys + Char.lcy_chan_nui_chau_bau[i][1] * 16;
            if (x >= xs && x <= xe && y >= ys && y <= ye) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnLocaltionSanhChoLoiDai(final int x, final int y) {
        final int xs = 256;
        final int xe = xs + 128;
        final int ys = 592;
        final int ye = ys + 64;
        return x >= xs && x <= xe && y >= ys && y <= ye;
    }

    public boolean checkToPoint(final Positions p1, final Positions p2) {
        if (p1.x1 == p2.x1 && this.checkLineX(p1.y1, p2.y1, p1.x1)) {
            return true;
        }
        if (p1.y1 == p2.y1 && this.checkLineY(p1.x1, p2.x1, p1.y1)) {
            return true;
        }
        int t = -1;
        return (t = this.checkRectX(p1, p2)) != -1 || (t = this.checkRectY(p1, p2)) != -1 || (t = this.checkMoreLineX(p1, p2, 1)) != -1 || (t = this.checkMoreLineX(p1, p2, -1)) != -1 || (t = this.checkMoreLineY(p1, p2, 1)) != -1 || (t = this.checkMoreLineY(p1, p2, -1)) != -1;
    }

    public boolean checkLineX(final int y1, final int y2, final int x) {
        final int min = Math.min(y1, y2);
        for (int max = Math.max(y1, y2), y3 = min; y3 <= max; ++y3) {
            if ((this.map.type[y3 * this.map.w + x] & 0x2) == 0x2) {
                return false;
            }
        }
        return true;
    }

    public boolean checkLineY(final int x1, final int x2, final int y) {
        final int min = Math.min(x1, x2);
        for (int max = Math.max(x1, x2), x3 = min; x3 <= max; ++x3) {
            if ((this.map.type[y * this.map.w + x3] & 0x2) == 0x2) {
                return false;
            }
        }
        return true;
    }

    public int checkRectX(final Positions p1, final Positions p2) {
        Positions pMinY = p1;
        Positions pMaxY = p2;
        if (p1.y1 > p2.y1) {
            pMinY = p2;
            pMaxY = p1;
        }
        for (int y = pMinY.y1 + 1; y < pMaxY.y1; ++y) {
            if (this.checkLineX(pMinY.y1, y, pMinY.x1) && this.checkLineY(pMinY.x1, pMaxY.x1, y) && this.checkLineX(y, pMaxY.y1, pMaxY.x1)) {
                return y;
            }
        }
        return -1;
    }

    public int checkRectY(final Positions p1, final Positions p2) {
        Positions pMinX = p1;
        Positions pMaxX = p2;
        if (p1.x1 > p2.x1) {
            pMinX = p2;
            pMaxX = p1;
        }
        for (int x = pMinX.x1 + 1; x < pMaxX.x1; ++x) {
            if (this.checkLineY(pMinX.x1, x, pMinX.y1) && this.checkLineX(pMinX.y1, pMaxX.y1, x) && this.checkLineY(x, pMaxX.x1, pMaxX.y1)) {
                return x;
            }
        }
        return -1;
    }

    public int checkMoreLineX(final Positions p1, final Positions p2, final int type1) {
        Positions pMinY = p1;
        Positions pMaxY = p2;
        if (p1.y1 > p2.y1) {
            pMinY = p2;
            pMaxY = p1;
        }
        int y = pMaxY.y1;
        int row = pMinY.x1;
        if (type1 == -1) {
            y = pMinY.y1;
            row = pMaxY.x1;
        }
        if (this.checkLineX(pMinY.y1, pMaxY.y1, row)) {
            while ((this.map.type[y * this.map.w + pMinY.x1] & 0x2) != 0x2 && (this.map.type[y * this.map.w + pMaxY.x1] & 0x2) != 0x2) {
                if (this.checkLineY(pMinY.x1, pMaxY.x1, y)) {
                    return y;
                }
                y += type1;
            }
        }
        return -1;
    }

    private int checkMoreLineY(final Positions p1, final Positions p2, final int type1) {
        Positions pMinX = p1;
        Positions pMaxX = p2;
        if (p1.x1 > p2.x1) {
            pMinX = p2;
            pMaxX = p1;
        }
        int x = pMaxX.x1;
        int col = pMinX.y1;
        if (type1 == -1) {
            x = pMinX.x1;
            col = pMaxX.y1;
        }
        if (this.checkLineY(pMinX.x1, pMaxX.x1, col)) {
            while ((this.map.type[pMinX.y1 * this.map.w + x] & 0x2) != 0x2 && (this.map.type[pMaxX.y1 * this.map.w + x] & 0x2) != 0x2) {
                if (this.checkLineX(pMinX.y1, pMaxX.y1, x)) {
                    return x;
                }
                x += type1;
            }
        }
        return -1;
    }

    public void doMove(final int x, final int y) {
        try {
            final long now = System.currentTimeMillis();
            if (this.beStune || this.freeze() || this.isStune()) {
                final Message m = new Message(4);
                this.writeActorPos(m, this);
                this.sendMessage(m);
                return;
            }
            if (this.map.givingCard(this)) {
                final Message m = new Message(4);
                this.writeActorPos(m, this);
                this.sendMessage(m);
                return;
            }
            if ((this.x != x || this.y != y) && this.hp <= 0) {
                this.map.doUseHP(this, (short) 4, -1000000);
                this.actorDie();
                return;
            }
            if (this.map.mapId == Map.idMapTown && Map.getTown[this.inCountry] && !MapTown.congthanh.get(this.inCountry).isDead && !this.isAdmin && y < 1206) {
                this.y = 1280;
                final Message m = new Message(4);
                this.writeActorPos(m, this);
                this.sendMessage(m);
                return;
            }
            if (this.x == x && this.y == y) {
                return;
            }
            this.isThodia = false;
            if (this.hp > 0) {
                int distance = (this.mapID == 17) ? 96 : 50;
                if (this.checkTanPhe()) {
                    distance = ((this.mapID == 17) ? 48 : 25);
                }
                this.timeMove = System.currentTimeMillis();
                this.lastMovex = 0;
                this.lastMovey = 0;
                if (Map.abs(this.x - x) <= distance && Map.abs(this.y - y) <= distance) {
                    this.x = x;
                    this.y = y;
                    this.sendInfoMove2Near();
                    if (this.charCopy != null) {
                        this.charCopy.setXtoYto(x, y);
                    }
                    if (this.charHire != null && !Map.inRangeActor(this, this.charHire, 60)) {
                        this.charHire.setXtoYto(x, y);
                    }
                } else {
                    final Message i = new Message(4);
                    this.writeActorPos(i, this);
                    this.sendMessage(i);
                }
            }
            this.moved = true;
        } catch (final Exception ex) {
        }
    }

    public void setXtoYto(final int x, final int y) {
    }

    @Override
    public boolean getPaintHat() {
        final short[] idModel = this.getIDModel();
        return (idModel[0] == 26 && idModel[1] == 50) || idModel[0] != 26;
    }

    public void doChangeMapCharHire() {
        try {
            if (this.charHire != null) {
                try {
                    final Message msg = new Message(8);
                    msg.dos.writeShort(this.charHire.id);
                    this.charHire.map.sendAllPlayer(msg, this.charHire.inCountry, this.region);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                this.charHire.mapID = this.mapID;
                this.charHire.map = this.map;
                this.charHire.setXtoYto(this.x + 32, this.y + 32);
                this.charHire.region = this.region;
                this.charHire.inCountry = this.inCountry;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void doChangeMapMonster(final boolean onMapMonster, final Map oldMap) {
        if (this.monster != null && onMapMonster && this.map.mapId == this.map.mapIDLoadMap && this.map.isMapTrain()) {
            this.monster.x = this.x;
            this.monster.y = this.y;
            this.monster.removeOutMap();
            try {
                final Message m = new Message(90);
                m.dos.writeShort(this.monster.id);
                m.dos.writeByte(this.monster.cat);
                Map.sendAllCharServerInMap(new int[]{0, 70, 301, 1701, 1, 2, 3, 4, 5, 6, 7, 8, 9, 118}, m);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            this.monster.map = this.map;
            this.monster.region = this.region;
            this.monster.inCountry = this.inCountry;
        }
        try {
            if (this.charthanthu != null) {
                try {
                    final Message msg = new Message(8);
                    msg.dos.writeShort(this.charthanthu.id);
                    this.charthanthu.map.sendAllPlayer(msg, this.charthanthu.inCountry, this.region);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                this.charthanthu.map = this.map;
                this.charthanthu.mapID = this.mapID;
                this.charthanthu.setXtoYto(this.x + 48, this.y + 48);
                this.charthanthu.region = this.region;
                this.charthanthu.inCountry = this.inCountry;
            }
        } catch (final Exception ex) {
        }
        try {
            if (this.charHire != null) {
                try {
                    final Message msg = new Message(8);
                    msg.dos.writeShort(this.charHire.id);
                    this.charHire.map.sendAllPlayer(msg, this.charHire.inCountry, this.region);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                this.charHire.map = this.map;
                this.charHire.mapID = this.mapID;
                this.charHire.setXtoYto(this.x + 32, this.y + 32);
                this.charHire.region = this.region;
                this.charHire.inCountry = this.inCountry;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (this.charCopy != null && this.charCopy.map.mapId == oldMap.mapId && this.charCopy.region == this.region) {
            if (this.charCopy.isFollow()) {
                try {
                    final Message msg = new Message(8);
                    msg.dos.writeShort(this.charCopy.id);
                    this.charCopy.map.sendAllPlayer(msg, this.charCopy.myCountry);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                this.charCopy.map = this.map;
                this.charCopy.setXtoYto(this.x + 17, this.y + 16);
                this.charCopy.region = this.region;
                this.charCopy.inCountry = this.inCountry;
            } else {
                this.sendMessage(this.writeActorPos(new Message(4), this.charCopy));
            }
        }
    }

    public boolean isFollow() {
        return false;
    }

    public void setTimeEatSocola() {
        this.minuteSocola = 180;
        this.timeEatSocola = System.currentTimeMillis();
        this.sendMessage(MessageCreator.createServerAlertMessage("Thời gian sử dụng hạt mầm của bạn còn " + this.minuteSocola + " phút", ""));
    }

    public boolean canReceiveGiftEvent() {
        return this.receiveGiftEvent == 0 && (getDayOpen(0L).compareTo("2020-08-11") <= 0 || getDayOpen(0L).equals("2020-08-12") || getDayOpen(0L).equals("2020-08-13") || getDayOpen(0L).equals("2020-08-14") || getDayOpen(0L).equals("2020-08-15"));
    }

    public void receiveGiftEvent(final int ma_vat) {
        try {
            if (this.isFullInventory()) {
                this.sendMessage(MessageCreator.createServerAlertMessage("Rương đầy", ""));
                return;
            }
            final int time = (Map.r.nextInt(5) + 1) * 60;
            final Item newItem = ItemLuckyDraw.createMaxItemCoatLucky(616, this, ma_vat);
            newItem.dateCreate = getDayTime(0L);
            if (time > 0) {
                newItem.timeLoan = System.currentTimeMillis();
                newItem.minuteExist = time;
            }
            this.iItems.add(newItem);
            this.sendMessage(MessageCreator.createCharInventoryMessage(this, 1));
            this.sendMessage(MessageCreator.createServerAlertMessage("Chúc mừng bạn đã nhận được phi phong lông thú thời hạn " + time + " phút", ""));
            Database.instance.saveOrtherLog("", this.charname, String.valueOf(newItem.getTemplate().name) + "_" + newItem.getDBInfo() + "|" + newItem.getAttribute() + " " + newItem.level, "quanoel");
            this.receiveGiftEvent = 1;
        } catch (final Exception ex) {
        }
    }

    @Override
    public boolean isCharMonster() {
        return this.isMonster;
    }

    public String getNameChiemThanh() {
        return "daihiep";
    }

    @Override
    public String getName() {
        return this.charname;
    }

    public boolean isCharCopy() {
        return false;
    }

    public boolean isCharChienTruong() {
        return false;
    }

    @Override
    public boolean isMyHoVe(final Char p) {
        return false;
    }

    public boolean isThangBe() {
        return false;
    }

    public String getinfoCharCopy() {
        return "";
    }

    public boolean isCoolDown(final int idSkill) {
        return false;
    }

    public void doChoKeo() {
        if (this.potions[83] <= 0) {
            int indexmap;
            int idmap;
            for (indexmap = Map.r.nextInt(Char.idmapthangbe.length), idmap = Char.idmapthangbe[indexmap]; idmap == this.mapID; idmap = Char.idmapthangbe[indexmap]) {
                indexmap = Map.r.nextInt(Char.idmapthangbe.length);
            }
            this.map.move2Map(this, Char.xythangbe[indexmap][0], Char.xythangbe[indexmap][1], idmap, this.inCountry);
            this.charCopyDissapear();
            return;
        }
        final int[] potions = this.potions;
        final int n = 83;
        --potions[n];
        final int[] per = {3000, 3000, 3000, 3000, 3000, 500, 3000, 3000, 3000, 3000, 3000, 500, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000};
        final int[] idgift = {-2, -2, -2, -2, -2, -2, -1, -1, -1, -1, -1, -1, 114, 113, 112, 93, 95, 123, 100, GemTemplate.BOT_XANH, GemTemplate.BOT_DO, GemTemplate.BOT_XANH_LA};
        final int[] typegift = {-1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, 4, 4, 4, 4, 4, 4, 4, 6, 6, 6};
        final int[] soluongmax = {10000, 20000, 30000, 40000, 50000, 5000000, 500000, 70000, 80000, 90000, 100000, 10000000, 3, 3, 3, 200, 200, 1, 1, 10, 10, 10};
        final int[] soluongmin = {10000, 20000, 30000, 40000, 50000, 5000000, 500000, 70000, 80000, 90000, 100000, 10000000, 1, 1, 1, 100, 100, 1, 1, 10, 10, 10};
        String info = "Chúc mừng bạn nhận được ";
        final int index = getIndexRandom(per);
        if (typegift[index] == -2) {
            info = String.valueOf(info) + soluongmax[index] + " exp";
            Map.addXpCharEvent(this, soluongmax[index], false, "char doChoKeo");
            this.sendMessage(MessageCreator.createMsgChat(this.id, info));
        }
        Label_1295:
        {
            if (typegift[index] == -1) {
                this.addXu(soluongmax[index], "char 12");
                info = String.valueOf(info) + soluongmax[index] + " xu";
                this.sendMessage(MessageCreator.createMsgChat(this.id, info));
                this.sendMessage(MessageCreator.createMsgChat(this.id, info));
            } else if (typegift[index] == 4) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final int[] potions2 = this.potions;
                final int n2 = idgift[index];
                potions2[n2] += soluong;
                info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
                this.sendMessage(MessageCreator.createMsgChat(this.id, info));
            } else if (typegift[index] == 6) {
                int soluong = soluongmin[index];
                if (soluongmin[index] < soluongmax[index]) {
                    soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                }
                final boolean lock = Map.r.nextInt(2) == 1;
                this.doAddGemItem(idgift[index], soluong, lock);
                info = String.valueOf(info) + soluong + " " + Map.gemTemplate[idgift[index]].name + (lock ? " khoá" : "");
                this.sendMessage(MessageCreator.createCharGemItem(this));
                this.sendMessage(MessageCreator.createMsgChat(this.id, info));
                if (idgift[index] != GemTemplate.LKD_35) {
                    if (idgift[index] != GemTemplate.LKD_40) {
                        break Label_1295;
                    }
                }
                try {
                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(this.charname) + " đã nhận được " + Map.gemTemplate[idgift[index]].name + " đốt đèn"));
                } catch (final IOException ex) {
                }
            }
        }
        Database.instance.saveOrtherLog("", this.charname, info, "chokeo");
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        this.charCopyDissapear();
    }

    public void addLienTram(final String name) {
        if (this.nameLienTram.size() >= 15) {
            this.nameLienTram.remove(0);
        }
        this.nameLienTram.add(name);
    }

    public boolean checkLienTram(final String info) {
        for (int i = 0; i < this.nameLienTram.size(); ++i) {
            if (this.nameLienTram.get(i).equals(info)) {
                return true;
            }
        }
        this.addLienTram(info);
        return false;
    }

    public static boolean isAcountTest(final Char p) {
        try {
            return p.getSession().username.toLowerCase().equals("huog25") || p.getSession().username.equals("tmbtest123") || p.getSession().username.equals("tmbtest123@gmail.com") || !p.getSession().is18Tuoi() || ((!p.getSession().is18Tuoi() || p.getSession().userID >= 127597851) && Char.onOffThamdinh == 1);
        } catch (final Exception ex) {
            return false;
        }
    }

    public static void startNauBanh() {
        Char.nauBanhChung = Database.instance.loadAllCharCooking(1);
        Char.nauBanhTet = Database.instance.loadAllCharCooking(2);
        if (Char.nauBanhChung == null) {
            Char.nauBanhChung = new Cooking();
            Char.nauBanhChung.type = 1;
        }
        if (Char.nauBanhTet == null) {
            Char.nauBanhTet = new Cooking();
            Char.nauBanhTet.type = 2;
        }
        if (isSuKienTet2017()) {
            new Thread() {
                @Override
                public void run() {
                    while (!AdminHandler.isStopServer) {
                        try {
                            Char.nauBanhChung.update();
                        } catch (final Exception ex) {
                        }
                        try {
                            Char.nauBanhTet.update();
                        } catch (final Exception ex2) {
                        }
                        try {
                            Thread.sleep(500L);
                        } catch (final Exception ex3) {
                        }
                    }
                }
            }.start();
        }
    }

    public static void startThreadXoso() {
        new Thread() {
            @Override
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        Thread.sleep(900000L);
                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage("Trò chơi dành cho người chơi trên 12 tuổi. Chơi quá 180 phút mỗi ngày sẽ hại sức khỏe"));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        if ((System.currentTimeMillis() - Char.timeXoso) / 1000L >= 120L) {
                            if (Char.ALL_PLAYER_XOSO.size() > 1) {
                                final int[] value = new int[Char.ALL_PLAYER_XOSO.size()];
                                final String[] namexoso = new String[Char.ALL_PLAYER_XOSO.size()];
                                final Vector<PlayerXoso> allplayer = new Vector<PlayerXoso>();
                                int i = 0;
                                for (final PlayerXoso pxs : Char.ALL_PLAYER_XOSO.values()) {
                                    value[i] = pxs.money;
                                    namexoso[i] = pxs.name;
                                    pxs.index = i;
                                    allplayer.add(pxs);
                                    ++i;
                                }
                                int tempindex;
                                int index;
                                Char pwin;
                                int[] value2;
                                PlayerXoso pxs2;
                                for (index = (tempindex = Char.getIndexRandomXoso(value)), pwin = CharManager.instance.getCharByCharName(namexoso[index]); pwin == null && allplayer.size() > 0; pwin = CharManager.instance.getCharByCharName(namexoso[index])) {
                                    allplayer.remove(tempindex);
                                    if (allplayer.size() == 0) {
                                        break;
                                    }
                                    value2 = new int[allplayer.size()];
                                    for (i = 0; i < allplayer.size(); ++i) {
                                        pxs2 = allplayer.get(i);
                                        value2[i] = pxs2.money;
                                    }
                                    tempindex = Char.getIndexRandomXoso(value2);
                                    if (tempindex == -1) {
                                        break;
                                    }
                                    index = allplayer.get(tempindex).index;
                                }
                                int tax = Char.ALL_PLAYER_XOSO.size() - 1;
                                if (tax > 10) {
                                    tax = 10;
                                }
                                final int moneyWin = (int) (Char.MONEY_XOSO - Char.MONEY_XOSO * tax / 100L);
                                final long moneytest = value[index];
                                final long tl = moneytest * 100L / Char.MONEY_XOSO;
                                String info = "Không tìm thấy người chiến thắng";
                                if (pwin != null) {
                                    info = "Chúc mừng " + namexoso[index].toUpperCase() + " đã chiến thắng " + Map.getDotNumber(moneyWin) + " vàng trong trò chơi Vòng xoay may mắn";
                                    if (tl <= 2L) {
                                        info = "Chúc mừng " + namexoso[index].toUpperCase() + " đặt " + Map.getDotNumber(moneytest) + " vàng và chiến thắng " + Map.getDotNumber(moneyWin) + " vàng trong trò chơi Vòng xoay may mắn";
                                    }
                                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(info));
                                    Database.instance.resetXoso(namexoso[index], value[index], moneyWin, (int) (Char.MONEY_XOSO * tax / 100L));
                                    Char.charNameXosoWin = namexoso[index];
                                    Char.moneyCharXosowin = moneyWin;
                                    Char.moneyCharXosoBid = value[index];
                                    pwin.addXu(moneyWin, "vxmm vip");
                                    pwin.sendMessage(MessageCreator.createCharInventoryMessage(pwin, 0));
                                    Database.instance.saveCharAuto(pwin);
                                } else {
                                    Char.charNameXosoWin = "";
                                    Char.moneyCharXosowin = 0;
                                    Char.moneyCharXosoBid = 0;
                                    Database.instance.resetXoso("ko char win", value[index], moneyWin, (int) (Char.MONEY_XOSO * tax / 100L));
                                }
                                Char.MONEY_XOSO = 0L;
                                Char.timeXoso = System.currentTimeMillis();
                                Char.ALL_PLAYER_XOSO.clear();
                            } else {
                                Char.timeXoso = System.currentTimeMillis();
                            }
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100L);
                    } catch (final Exception ex) {
                    }
                }
            }
        }.start();
    }

    public static void startThreadXosoLow() {
        new Thread() {
            @Override
            public void run() {
                while (!AdminHandler.isStopServer) {
                    try {
                        if ((System.currentTimeMillis() - Char.timeXoso_low) / 1000L >= 120L) {
                            if (Char.ALL_PLAYER_XOSO_low.size() > 1) {
                                final int[] value = new int[Char.ALL_PLAYER_XOSO_low.size()];
                                final String[] namexoso = new String[Char.ALL_PLAYER_XOSO_low.size()];
                                final Vector<PlayerXoso> allplayer = new Vector<PlayerXoso>();
                                int i = 0;
                                for (final PlayerXoso pxs : Char.ALL_PLAYER_XOSO_low.values()) {
                                    value[i] = pxs.money;
                                    namexoso[i] = pxs.name;
                                    pxs.index = i;
                                    allplayer.add(pxs);
                                    ++i;
                                }
                                int tempindex;
                                int index;
                                Char pwin;
                                int[] value2;
                                PlayerXoso pxs2;
                                for (index = (tempindex = Char.getIndexRandomXoso(value)), pwin = CharManager.instance.getCharByCharName(namexoso[index]); pwin == null && allplayer.size() > 0; pwin = CharManager.instance.getCharByCharName(namexoso[index])) {
                                    allplayer.remove(tempindex);
                                    if (allplayer.size() == 0) {
                                        break;
                                    }
                                    value2 = new int[allplayer.size()];
                                    for (i = 0; i < allplayer.size(); ++i) {
                                        pxs2 = allplayer.get(i);
                                        value2[i] = pxs2.money;
                                    }
                                    tempindex = Char.getIndexRandomXoso(value2);
                                    if (tempindex == -1) {
                                        break;
                                    }
                                    index = allplayer.get(tempindex).index;
                                }
                                int tax = Char.ALL_PLAYER_XOSO_low.size() - 1;
                                if (tax > 10) {
                                    tax = 10;
                                }
                                final int moneyWin = (int) (Char.MONEY_XOSO_low - Char.MONEY_XOSO_low * tax / 100L);
                                final long moneytest = value[index];
                                final long tl = moneytest * 100L / Char.MONEY_XOSO_low;
                                String info = "Không tìm thấy người chiến thắng";
                                if (pwin != null) {
                                    info = "Chúc mừng " + namexoso[index].toUpperCase() + " đã chiến thắng " + Map.getDotNumber(moneyWin) + " vàng trong trò chơi Vòng xoay may mắn";
                                    if (tl <= 2L) {
                                        info = "Chúc mừng " + namexoso[index].toUpperCase() + " đặt " + Map.getDotNumber(moneytest) + " vàng và chiến thắng " + Map.getDotNumber(moneyWin) + " vàng trong trò chơi Vòng xoay may mắn";
                                    }
                                    Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(info));
                                    Database.instance.resetXosoLow(namexoso[index], value[index], moneyWin, (int) (Char.MONEY_XOSO_low * tax / 100L));
                                    Char.charNameXosoWin_low = namexoso[index];
                                    Char.moneyCharXosowin_low = moneyWin;
                                    Char.moneyCharXosoBid_low = value[index];
                                    pwin.addXu(moneyWin, "vxmm thuong");
                                    pwin.sendMessage(MessageCreator.createCharInventoryMessage(pwin, 0));
                                    Database.instance.saveCharAuto(pwin);
                                } else {
                                    Database.instance.resetXosoLow("ko char win", value[index], moneyWin, (int) (Char.MONEY_XOSO_low * tax / 100L));
                                    Char.charNameXosoWin_low = "";
                                    Char.moneyCharXosowin_low = 0;
                                    Char.moneyCharXosoBid_low = 0;
                                }
                                Char.MONEY_XOSO_low = 0L;
                                Char.timeXoso_low = System.currentTimeMillis();
                                Char.ALL_PLAYER_XOSO_low.clear();
                            } else {
                                Char.timeXoso_low = System.currentTimeMillis();
                            }
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100L);
                    } catch (final Exception ex) {
                    }
                }
            }
        }.start();
    }

    public void infoXosoLow() {
        try {
            if (Char.ALL_PLAYER_XOSO_low.size() < 2) {
                Char.timeXoso_low = System.currentTimeMillis();
            }
            final PlayerXoso pxs = Char.ALL_PLAYER_XOSO_low.get(this.charname.toLowerCase());
            long moneytest = 0L;
            if (pxs != null) {
                moneytest = pxs.money;
            }
            final long moneyMax = Char.MONEY_XOSO_low;
            String tl = "";
            String tl2 = "";
            long pc = 0L;
            if (moneyMax > 0L) {
                tl = String.valueOf(tl) + moneytest * 100L / moneyMax;
                pc = moneytest * 100L / moneyMax;
                long mod = moneytest * 100L % moneyMax;
                if (mod != 0L) {
                    tl = String.valueOf(tl) + ".";
                    int i = 0;
                    while (i < 4) {
                        ++i;
                        tl = String.valueOf(tl) + mod * 10L / moneyMax;
                        tl2 = String.valueOf(tl2) + mod * 10L / moneyMax;
                        if (mod * 10L % moneyMax == 0L) {
                            break;
                        }
                        mod = mod * 10L % moneyMax;
                    }
                } else {
                    tl = String.valueOf(tl) + ".0";
                    tl2 = "0";
                }
            } else {
                tl = "0";
                tl2 = "0";
            }
            final long timexs = Char.timeXoso_low;
            long time = 120L - (System.currentTimeMillis() - timexs) / 1000L;
            if (time < 0L) {
                time = 0L;
            }
            String hh = "0" + time / 60L + ":";
            String sc = "";
            if (time / 60L == 0L) {
                sc = new StringBuilder(String.valueOf(time)).toString();
            } else {
                sc = new StringBuilder(String.valueOf(time % 60L)).toString();
            }
            if (sc.length() == 1) {
                sc = "0" + sc;
            }
            hh = String.valueOf(hh) + sc;
            final String str = "Vòng quay thường\nThời gian\n" + hh + "\n" + Map.getDotNumber(Char.MONEY_XOSO_low) + " xu\n" + "Tỷ lệ thắng: " + tl + "%\n" + "Số người tham gia: " + Char.ALL_PLAYER_XOSO_low.size() + "\n" + "Người vừa chiến thắng: " + Char.charNameXosoWin_low + "\n" + "Số xu ăn được: " + Map.getDotNumber(Char.moneyCharXosowin_low) + " xu\n" + "Số xu tham gia: " + Map.getDotNumber(Char.moneyCharXosoBid_low) + " xu";
            this.sendMessage(MessageCreator.createMsgPopUp(this.id, 100, str));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void infoXoso(final int type) {
        if (type == 1) {
            this.infoXosoLow();
            return;
        }
        try {
            if (Char.ALL_PLAYER_XOSO.size() < 2) {
                Char.timeXoso = System.currentTimeMillis();
            }
            final PlayerXoso pxs = Char.ALL_PLAYER_XOSO.get(this.charname.toLowerCase());
            long moneytest = 0L;
            if (pxs != null) {
                moneytest = pxs.money;
            }
            final long moneyMax = Char.MONEY_XOSO;
            String tl = "";
            String tl2 = "";
            long pc = 0L;
            if (moneyMax > 0L) {
                tl = String.valueOf(tl) + moneytest * 100L / moneyMax;
                pc = moneytest * 100L / moneyMax;
                long mod = moneytest * 100L % moneyMax;
                if (mod != 0L) {
                    tl = String.valueOf(tl) + ".";
                    int i = 0;
                    while (i < 4) {
                        ++i;
                        tl = String.valueOf(tl) + mod * 10L / moneyMax;
                        tl2 = String.valueOf(tl2) + mod * 10L / moneyMax;
                        if (mod * 10L % moneyMax == 0L) {
                            break;
                        }
                        mod = mod * 10L % moneyMax;
                    }
                } else {
                    tl = String.valueOf(tl) + ".0";
                    tl2 = "0";
                }
            } else {
                tl = "0";
                tl2 = "0";
            }
            final long timexs = Char.timeXoso;
            long time = 120L - (System.currentTimeMillis() - timexs) / 1000L;
            if (time < 0L) {
                time = 0L;
            }
            String hh = "0" + time / 60L + ":";
            String sc = "";
            if (time / 60L == 0L) {
                sc = new StringBuilder(String.valueOf(time)).toString();
            } else {
                sc = new StringBuilder(String.valueOf(time % 60L)).toString();
            }
            if (sc.length() == 1) {
                sc = "0" + sc;
            }
            hh = String.valueOf(hh) + sc;
            final String str = "Vòng quay vip\nThời gian\n" + hh + "\n" + Map.getDotNumber(Char.MONEY_XOSO) + " xu\n" + "Tỷ lệ thắng: " + tl + "%\n" + "Số người tham gia: " + Char.ALL_PLAYER_XOSO.size() + "\n" + "Người vừa chiến thắng: " + Char.charNameXosoWin + "\n" + "Số xu ăn được: " + Map.getDotNumber(Char.moneyCharXosowin) + " xu\n" + "Số xu tham gia: " + Map.getDotNumber(Char.moneyCharXosoBid) + " xu";
            this.sendMessage(MessageCreator.createMsgPopUp(this.id, 100, str));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public Vector<Item> getAllItemPetEat() {
        this.itemPetEat = new Vector<Item>();
        for (int i = 0; i < this.iItems.size(); ++i) {
            final Item item = this.iItems.get(i);
            if (!item.isItemThoiTrang() && !item.isVukhiThoiTrang() && !item.isSelling) {
                if (item.isHuyetLinhThao() && this.petUsing.itemPet != null && this.petUsing.itemPet != null && this.petUsing.itemPet.level > 0) {
                    this.itemPetEat.add(item);
                } else if (item.getTemplate().type < 13 && !item.isItemShop) {
                    this.itemPetEat.add(item);
                }
            }
        }
        if (this.potions[83] > 0) {
            final Item it = new Item((ItemTemplates) Map.itemTemplates.get(5).get(714), false, 0, 0, 714);
            this.itemPetEat.add(it);
        }
        return this.itemPetEat;
    }

    public void doXoso(final int money, final int type, final int hight_low) {
        if (Char.isBaotriVxmm) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chức năng đang bảo trì", ""));
            return;
        }
        if (money < 0) {
            Database.instance.saveOrtherLog("", this.charname, "hack vxmm " + money, "hvxmm");
            return;
        }
        if (hight_low == 1) {
            this.doXosoLow(money, type, hight_low);
            return;
        }
        if (this.xu < money) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Không đủ xu", ""));
            return;
        }
        if (Char.ALL_PLAYER_XOSO_low.get(this.charname.toLowerCase()) != null) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể tham gia 1 vòng trong 1 lượt quay", ""));
            return;
        }
        if (this.xu < 5000000L) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Bạn phải có từ 5.000.000 xu trở lên mới có thể tham gia vòng xuay vip", ""));
            return;
        }
        PlayerXoso pxs = Char.ALL_PLAYER_XOSO.get(this.charname.toLowerCase());
        if (pxs != null) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể tham gia 1 lần trong 1 lượt quay", ""));
            return;
        }
        if ((System.currentTimeMillis() - Char.timeXoso) / 1000L >= 110L || Char.MONEY_XOSO >= 500000000L) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Đã khoá đặt cược", ""));
            return;
        }
        if (money > 50000000) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Số xu tối đa có thể đặt là 50.000.000 xu", ""));
            return;
        }
        if (money < 1000000) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Số xu tối thiểu phải là 1.000.000 xu", ""));
            return;
        }
        if (this.xu - money < 2500000L) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Số xu tối đa có thể đặt là " + (this.xu - 2500000L) + " xu", ""));
            return;
        }
        Char.MONEY_XOSO += money;
        pxs = new PlayerXoso();
        pxs.name = this.charname.toLowerCase();
        final PlayerXoso playerXoso = pxs;
        playerXoso.money += money;
        Char.ALL_PLAYER_XOSO.put(this.charname.toLowerCase(), pxs);
        this.subXu(money, false, "dat tien vxmm vip doxoso");
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        final Char p = this;
        ThreadPool.instant.workList.add(new Runnable() {
            @Override
            public void run() {
                Database.instance.doAddUpdatePlayerXoso(Char.this.charname, money);
                Database.instance.saveCharAuto(p);
            }
        });
        this.infoXoso(hight_low);
        Database.instance.saveOrtherLog("", this.charname, "dat cuoc vong xoay may man vip " + money, "bidxoso");
    }

    public void doXosoLow(final int money, final int type, final int hight_low) {
        if (money < 0) {
            return;
        }
        if (Char.ALL_PLAYER_XOSO.get(this.charname.toLowerCase()) != null) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể tham gia 1 vòng trong 1 lượt quay", ""));
            return;
        }
        if (this.xu < money) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Không đủ xu", ""));
            return;
        }
        PlayerXoso pxs = Char.ALL_PLAYER_XOSO_low.get(this.charname.toLowerCase());
        if (pxs != null) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Chỉ có thể tham gia 1 lần trong 1 lượt quay", ""));
            return;
        }
        if ((System.currentTimeMillis() - Char.timeXoso_low) / 1000L >= 110L || Char.MONEY_XOSO_low >= 500000000L) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Đã khoá đặt cược", ""));
            return;
        }
        if (money < 10000) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Số xu tổi thiểu phải là 10.000 xu", ""));
            return;
        }
        if (money > 100000) {
            this.sendMessage(MessageCreator.createServerAlertMessage("Số xu tổi đa có thể đặt là 100.000 xu", ""));
            return;
        }
        Char.MONEY_XOSO_low += money;
        pxs = new PlayerXoso();
        pxs.name = this.charname.toLowerCase();
        final PlayerXoso playerXoso = pxs;
        playerXoso.money += money;
        Char.ALL_PLAYER_XOSO_low.put(this.charname.toLowerCase(), pxs);
        this.subXu(money, false, "dat tien vxmm thg dosoxolow");
        this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
        final Char p = this;
        ThreadPool.instant.workList.add(new Runnable() {
            @Override
            public void run() {
                Database.instance.doAddUpdatePlayerXosoLow(Char.this.charname, money);
                Database.instance.saveCharAuto(p);
            }
        });
        this.infoXoso(hight_low);
        Database.instance.saveOrtherLog("", this.charname, "dat cuoc vong xoay may man thuong " + money, "bidxoso");
    }

    public void countKillMonster(final boolean count) {
        try {
            if (count) {
                ++this.countMonsterKill;
                if (this.countMonsterKill == 10 || this.countMonsterKill == 100 || this.countMonsterKill == 1000 || this.countMonsterKill == 10000 || this.countMonsterKill == 100000 || this.countMonsterKill == 1000000 || this.countMonsterKill == 10000000) {
                    final int[] potions = this.potions;
                    final int n = 77;
                    ++potions[n];
                    this.sendMessage(MessageCreator.createCharInventoryMessage(this, 0));
                    Database.instance.saveOrtherLog("", this.getName(), "nhan dc trung vang khi train quai", "trungvang");
                }
            }
        } catch (final Exception ex) {
        }
    }

    public void doOffDanhHieu() {
        if (this.idEffDanhHieu > -1) {
            this.idEffDanhHieu = -1;
            this.currentDanhHieu = null;
            try {
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
            } catch (final IOException ex) {
            }
            this.sendToNearPlayer(MessageCreator.createCharInfo(this));
        }
    }

    public void doSelectDanhHieu(final int index) {
        this.idEffDanhHieu = this.allDanhHieu.get(index).ideff;
        this.currentDanhHieu = this.allDanhHieu.get(index);

//        System.out.println("idEffDanhHieu: " + idEffDanhHieu +", currentDanhHieu: " + currentDanhHieu.getName());
        try {
            this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
        this.sendToNearPlayer(MessageCreator.createCharInfo(this));
    }

    public String[] getAllNamDanhHieu() {
        String[] a = null;
        final Vector<DanhHieu> dh = new Vector<DanhHieu>();
        for (int i = 0; i < this.allDanhHieu.size(); ++i) {
            if (!this.allDanhHieu.get(i).isExpire()) {
                dh.add(this.allDanhHieu.get(i));
            }
        }
        this.allDanhHieu = dh;
        a = new String[dh.size() + 1];
        if (dh.size() > 0) {
            for (int i = 0; i < this.allDanhHieu.size(); ++i) {
                a[i] = dh.get(i).getName();
            }
        }
        a[a.length - 1] = "Tắt danh hiệu";
        return a;
    }

    public String getAllDanhHieuSave() {
        String info = "";
        for (int i = 0; i < this.allDanhHieu.size(); ++i) {
            if (!this.allDanhHieu.get(i).isExpire()) {
                info = String.valueOf(info) + this.allDanhHieu.get(i).ideff + "," + this.allDanhHieu.get(i).timeExpire + ",";
            }
        }
        return (info.length() > 0) ? info.substring(0, info.length() - 1) : info;
    }

    public boolean addDanhHieu(final int id, final long minute) {
        for (int i = 0; i < this.allDanhHieu.size(); ++i) {
            if (this.allDanhHieu.get(i).ideff == id) {
                if (minute > 0L) {
                    this.allDanhHieu.get(i).timeExpire = System.currentTimeMillis() + minute * 60000L;
                } else {
                    this.allDanhHieu.get(i).timeExpire = 0L;
                }
                Database.instance.addCharDanhHieu(this);
                return false;
            }
        }
        final DanhHieu dh = new DanhHieu();
        dh.ideff = id;
        if (minute > 0L) {
            dh.timeExpire = System.currentTimeMillis() + minute * 60000L;
        } else {
            dh.timeExpire = 0L;
        }
        this.allDanhHieu.add(dh);
        if (this.idEffDanhHieu == -1) {
            this.idEffDanhHieu = id;
            this.currentDanhHieu = dh;
        }
        Database.instance.addCharDanhHieu(this);
        return true;
    }

    public boolean addDanhHieu_VIP(final int id, final long minute) {
        for (int i = 0; i < this.allDanhHieu.size(); ++i) {
            if (this.allDanhHieu.get(i).ideff == id) {
                return false;
            }
        }
        final DanhHieu dh = new DanhHieu();
        dh.ideff = id;
        if (minute > 0L) {
            dh.timeExpire = System.currentTimeMillis() + minute * 60000L;
        } else {
            dh.timeExpire = 0L;
        }
        this.allDanhHieu.add(dh);
        if (this.idEffDanhHieu == -1) {
            this.idEffDanhHieu = id;
            this.currentDanhHieu = dh;
        }
        Database.instance.addCharDanhHieu(this);
        return true;
    }

    public void setDanhHieu(final String info) {
        try {
            if (info == null || info.equals("")) {
                return;
            }
            final String[] data = split(info, ",");
            for (int i = 0; i < data.length; i += 2) {
                final int ideff = Integer.parseInt(data[i]);
                final long time = Long.parseLong(data[i + 1]);
                if (time == 0L || (time > 0L && time - System.currentTimeMillis() > 10000L)) {
                    final DanhHieu dh = new DanhHieu();
                    dh.ideff = ideff;
                    dh.timeExpire = time;
                    this.allDanhHieu.add(dh);
                    if (this.idEffDanhHieu == ideff) {
                        this.currentDanhHieu = dh;
                    }

                }
            }
        } catch (final Exception ex) {
        }
    }

    public int[][] getInfoDanhHieu() {
        final int[][] a = {{this.idEffDanhHieu}, {2}};
        return a;
    }

    public void doAddGiftThungGo() {
        try {
            if (this.isFullInventory()) {
                return;
            }
            final int[] per = {5000, 5000, 5000, 200, 200, 200, 500, 5000, 5000, 2000, 300, 10, 4000, 1000};
            final int[] idgift = {94, 96, 69, GemTemplate.NGOC_HUYEN_MINH_3, GemTemplate.NGOC_HUYEN_MINH_4, GemTemplate.NGOC_HUYEN_MINH_5, GemTemplate.DA_TACH_NL_DAC_BIET, -1, -1, -1, -1, -1, -2, -3};
            final int[] typegift = {4, 4, 4, 6, 6, 6, 6, -1, -1, -1, -1, -1, -2, -3};
            final int[] soluongmax = {100, 100, 1, 5, 5, 5, 1, 10000, 15000, 20000, 50000, 200000, 1, 1};
            final int[] soluongmin = {50, 50, 1, 3, 3, 3, 1, 10000, 15000, 20000, 50000, 200000, 1, 1};
            final Char player = this;
            String info = "Chúc mừng bạn nhận được ";
            final int index = getIndexRandom(per);
            Label_1161:
            {
                if (typegift[index] == -1) {
                    player.addXu(soluongmax[index], "map 57");
                    info = String.valueOf(info) + soluongmax[index] + " xu";
                    player.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                } else if (typegift[index] == -2) {
                    final int[] minute = {180, 300, 420, 1440};
                    final Item newItem = ItemLuckyDraw.createItemCoat(player, Map.r.nextInt(2), Map.r.nextInt(100) < 50, minute[Map.r.nextInt(minute.length)]);
                    player.iItems.add(newItem);
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 1));
                    info = String.valueOf(info) + newItem.getName() + " thời hạn " + newItem.minuteExist + " phút";
                    player.sendMessage(MessageCreator.createMsgChat(player.id, info));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                } else if (typegift[index] == -3) {
                    final int[] idchoi = {617, 619, 618};
                    final Item it = createChoi(idchoi[Map.r.nextInt(idchoi.length)], this, 1440, 0, true);
                    info = String.valueOf(info) + " " + it.getName();
                } else if (typegift[index] == 4) {
                    int soluong = soluongmin[index];
                    if (soluongmin[index] < soluongmax[index]) {
                        soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                    }
                    final int[] potions = player.potions;
                    final int n = idgift[index];
                    potions[n] += soluong;
                    info = String.valueOf(info) + soluong + " " + LoginHandler.PORTION_NAME[idgift[index]];
                    player.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                    player.sendMessage(MessageCreator.createCharInventoryMessage(player, 0));
                } else if (typegift[index] == 6) {
                    int soluong = soluongmin[index];
                    if (soluongmin[index] < soluongmax[index]) {
                        soluong += Map.r.nextInt(soluongmax[index] - soluongmin[index] + 1);
                    }
                    final boolean lock = Map.r.nextInt(2) == 1;
                    player.doAddGemItem(idgift[index], soluong, lock);
                    info = String.valueOf(info) + soluong + " " + Map.gemTemplate[idgift[index]].name + (lock ? " khoá" : "");
                    player.sendMessage(MessageCreator.createCharGemItem(player));
                    player.sendMessage(MessageCreator.createServerAlertMessage(info, ""));
                    if (idgift[index] != GemTemplate.LKD_35) {
                        if (idgift[index] != GemTemplate.LKD_40) {
                            break Label_1161;
                        }
                    }
                    try {
                        Map.sendAllCharServer(-1, MessageCreator.createServerAlertAutoOffMessage(String.valueOf(player.charname) + " đã nhận được " + Map.gemTemplate[idgift[index]].name + " khi làm nhiệm vụ tìm trẻ lạc"));
                    } catch (final IOException ex) {
                    }
                }
            }
            Database.instance.saveOrtherLog("", player.charname, info, "thunggo");
        } catch (final Exception ex2) {
        }
    }

    public String getInfoServer() {
        String a = "";
        a = "@";
        a = "Thông tin chiến trường:@";
        a = String.valueOf(a) + "Sát thương trụ: ";
        a = String.valueOf(a) + "Giáp trụ: 20@";
        a = String.valueOf(a) + "Giáp quái: 10@";
        return a;
    }

    public void doAddPointChienTruong(final int point) {
        final CharChienTruong c = MapChienTruongMoba.getCharChienTruong(this.charname);
        if (c != null) {
            if (c.noi_luc >= 10) {
                return;
            }
            final int oldNoiLuc = c.noi_luc;
            final CharChienTruong charChienTruong = c;
            charChienTruong.pointKillMons += point;
            if (c.pointKillMons >= Char.ID_POINT_NOI_LUC_CHIEN_TRUONG[c.noi_luc]) {
                final CharChienTruong charChienTruong2 = c;
                ++charChienTruong2.noi_luc;
                if (c.noi_luc == 5 || c.noi_luc == 7 || c.noi_luc == 10) {
                    this.calculateAttrib();
                }
            }
            if (oldNoiLuc < c.noi_luc) {
                try {
                    final Message m = new Message(33);
                    m.dos.writeShort(this.id);
                    m.dos.writeByte(c.noi_luc);
                    m.dos.writeInt(this.maxhp);
                    m.dos.writeInt(this.maxmp);
                    this.hp = this.maxhp;
                    this.mp = this.maxmp;
                    this.calculateAttrib();
                    this.sendMessage(m);
                    this.sendToNearPlayer(m);
                    this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                    this.sendInfoChienTruong(Char.ID_NOI_LUC, 0);
                } catch (final Exception ex) {
                }
            }
        }
    }

    public boolean isMasterClanAndHaveTown() {
        if (this.idClan == -1) {
            return false;
        }
        
        NewClan clanInfo = Map.getClaninfoByID(this.idClan);
        if (clanInfo == null) {
            return false;
        }
    
        // Check if character is clan master and town > 0
        return clanInfo.master.toLowerCase().equals(this.charname.toLowerCase()) && clanInfo.town > 0;
    }

    public long getTimeNuiChauBau() {
        final CharInfo c = MapChauBau.all_char_nui_kho_bau.get(this.charname);
        if (c != null) {
            return c.getTimeNuiChauBau();
        }
        return 0L;
    }

    public boolean isVip() {
        return this.vip > 0;
    }

    public static byte getVipByLevel(int level) {
        if (level >= VIP_LEVEL_3) {
            return 3;
        }
        if (level >= VIP_LEVEL_2) {
            return 2;
        }
        if (level >= VIP_LEVEL_1) {
            return 1;
        }
        return 0;
    }

    public boolean syncVipByLevel(boolean notify) {
        if (this.charname == null || this.charname.length() == 0 || Map.ALL_CHAR_VIP == null) {
            return false;
        }
        byte levelVip = getVipByLevel(this.getLevel());
        byte oldVip = this.vip;
        Integer savedVip = Map.ALL_CHAR_VIP.get(this.charname);
        this.vip = levelVip;

        if (levelVip > 0) {
            if (savedVip == null) {
                Database.instance.doAddCharVip(this.charname, levelVip);
            } else if (savedVip.intValue() != levelVip) {
                Database.instance.doUpdateCharVip(this.charname, levelVip);
            }
            Map.ALL_CHAR_VIP.put(this.charname, Integer.valueOf(levelVip));
            this.rcvInviteVip = false;
        } else {
            if (savedVip != null) {
                Database.instance.doresetCharVip(this.charname);
                Map.ALL_CHAR_VIP.remove(this.charname);
            }
            this.rcvInviteVip = true;
        }

        if (oldVip != levelVip) {
            try {
                this.calculateAttrib();
                this.sendMessage(MessageCreator.createMainCharInfoMessage(this));
                this.sendToNearPlayer(MessageCreator.createCharInfo(this));
                if (notify && levelVip > oldVip) {
                    this.sendMessage(MessageCreator.createServerAlertMessage("Bạn đã mở " + Map.VIP[levelVip] + " khi đạt cấp " + this.getLevel(), ""));
                }
                Database.instance.saveOrtherLog("", this.charname, "sync vip theo cap " + oldVip + "->" + levelVip + " lv " + this.getLevel(), "viplevel");
            } catch (final Exception ignored) {
            }
        }

        return oldVip != levelVip;
    }

    @Override
    public boolean canFocus(final Char me) {
        return this.idBot != -70 && (!this.map.isMapLoiDai() || (me.name_char_loi_dai != null && this.name_char_loi_dai != null)) && super.canFocus(me);
    }

    public void doAttackSkillNew(final Vector<Monster> ms) {
      
        try {
                for(int i = 0; i < ms.size(); ++i) {
                    Monster mt = (Monster)ms.get(i);
                    final Message msg = MessageCreator.createMsgNewEffectSkill5Long(8999, 450, mt.id, mt.cat, true, false);
                    this.sendMessage(msg);
                    this.sendToNearPlayer(msg);
                }
          
        } catch (Exception var10) {
        }

    }

    
    
}
