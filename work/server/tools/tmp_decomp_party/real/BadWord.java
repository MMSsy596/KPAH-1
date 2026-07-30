/*
 * Decompiled with CFR 0.152.
 */
package real;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class BadWord {
    public static String[] list = new String[]{"concac", "ditme", "cailin", "matday", "ditmay", "matnet", "damtac", "djtme", "dkm", "khonnan", "ocheo", "occho", "chode", "chochet", "dume", "duma", "dime", "memay", "mamay", "concac", "cailon", "mekiep", "deome", "bopvu", "bopveu", "bopchim", "lonto", "sucsinh", "quanque", "vailin", "vailon", "chetcha", "bulon", "bucac", "pulon", "pucac", "chetme", "chetba", "trungdai", "hondai", "lodit", "vaicalon", "vaicailon", "ditbo", "lonma", "lonme", "hiepdam", "bopcu", "bopvu", "bopcac", "laocho", "choma", "ditcu", "ditba", "dcm", "dmm", "dis", "vcl", "xaolon", "laolon", "soclo", "congsan", "shit", "lodit", "fuck", "concu", "hochiminh", "vonguyengiap", "nguyentandung", "truongtansang", "nguyenphutrong", "nguyensinhhung", "nguyenthidoan", "truonghoabinh", "nguyenhoabinh", "hoangtrunghai", "nguyenthiennhan", "nguyenxuanphuc", "vuvanninh", "phungquangthanh", "trandaiquang", "phambinhminh", "nguyenthaibinh", "hahungcuong", "buiquangvinh", "vuongdinhhue", "vuhuyhoang", "caoducphat", "dinhlathang", "trinhdinhdung", "nguyenminhquang", "nguyenbacson", "phamthihaichuyen", "hoangtuananh", "nguyenquan", "phamvuluan", "nguyenthikimtien", "giangseophu", "nguyenvanbinh", "huynhphongtranh", "vuducdam", "pussy", "lamtinh", "fuck", "shit", "cunt", "pussy", "dick", "asshole", "bullshit", "fucker", "vagina", "queer", "bastard", "nigger", "bitch", "tinhduc", "tjnhduc", "m\u1ea1i d\u00e2m", "mai dam"};
    public static String[] listEng = new String[]{"bug-generator", "concac", "ditme", "cailin", "matday", "ditmay", "matnet", "damtac", "djtme", "dkm", "khonnan", "ocheo", "occho", "chode", "chochet", "dume", "duma", "dime", "memay", "mamay", "concac", "cailon", "mekiep", "deome", "bopvu", "bopveu", "bopchim", "lonto", "sucsinh", "quanque", "vailin", "vailon", "chetcha", "bulon", "bucac", "pulon", "pucac", "chetme", "chetba", "trungdai", "hondai", "lodit", "vaicalon", "vaicailon", "ditbo", "lonma", "lonme", "hiepdam", "bopcu", "bopvu", "bopcac", "laocho", "choma", "ditcu", "ditba", "dcm", "dmm", "dis", "vcl", "xaolon", "laolon", "soclo", "congsan", "shit", "lodit", "fuck", "concu", "hochiminh", "vonguyengiap", "nguyentandung", "truongtansang", "nguyenphutrong", "nguyensinhhung", "nguyenthidoan", "truonghoabinh", "nguyenhoabinh", "hoangtrunghai", "nguyenthiennhan", "nguyenxuanphuc", "vuvanninh", "phungquangthanh", "trandaiquang", "phambinhminh", "nguyenthaibinh", "hahungcuong", "buiquangvinh", "vuongdinhhue", "vuhuyhoang", "caoducphat", "dinhlathang", "trinhdinhdung", "nguyenminhquang", "nguyenbacson", "phamthihaichuyen", "hoangtuananh", "nguyenquan", "phamvuluan", "nguyenthikimtien", "giangseophu", "nguyenvanbinh", "huynhphongtranh", "vuducdam", "pussy", "lamtinh", "fuck", "shit", "cunt", "pussy", "dick", "asshole", "bullshit", "fucker", "vagina", "queer", "bastard", "nigger", "bitch", "tinhduc", "tjnhduc", "gay"};
    public static String[] listindo = new String[]{"kontol", "memek", "anjing", "lonte", "babi", "ngentot", "whore", "memex", "homo", "l0nte", "l0nt3", "p3a", "p.e.a", "bodoh", "b0d0h", "tolol", "t0l0l", "monyet", "m0nyet", "idiot", "idi0t", "i.d.i.o.t", "genk", "pelacur", "jeblai", "jablay", "jancok", "jancuk", "cok", "kete", "negentot", "peju", "kont0l", "k0nt0l", "ngent0t", "fvck", "randi", "kuti"};
    public static String[] list2 = new String[]{"word", "*sc", ".sc", "vvap", "w\u00e1p", "dm", "dkm", "h\u00e1ck", "http", "iwin", "*mw", "fuck", "lon`", "l0n`", "lam tinh", ".mw", "www", "wap", ".com", ".vn", "sex", ",lt", ",yn", ",sh", ".lt", "hack", "*in", "dcm", "w\u00e1p", "tinhduc", ".yn", "hak", "*sh", "xtgem", ".sh", ".in", ".tk", ".net", "c\u1eb7c", "l\u1ed3n", ",in", ",sc", "*yn", "lt", "hochiminh"};
    public static String[] listvn = new String[]{"c\u1eb7c", "\u0111\u1ecbt", "l\u1ed3n", "l\u00ecn", "\u0111\u1ee5", "d\u00e2m", "djt", "\u0111\u00e9o", "v\u00fa", "v\u1ebfu", "\u0111\u00edt", "d\u00e1i", "ch\u00f3", "\u0111\u1ec9", "\u0111\u0129"};
    public static String[] extend;

    static {
        String[] st = new String[]{"\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead", "\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7", "\u00ed\u00ec\u1ec9\u0129\u1ecb", "\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9", "\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3", "\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1", "\u00fd\u1ef3\u1ef7\u1ef9\u1ef5"};
        int i = 0;
        while (i < st.length) {
            System.out.print(st[i].toUpperCase());
            ++i;
        }
        System.out.println("");
        extend = new String[]{".com", ".info", ".name", ".net", ".org", ".pro", ".biz", ".asia", ".cat", ".coop", ".edu", ".gov", ".int", ".jobs", ".mil ", ".mobi", ".museum", ".tel", ".travel", ".ac", ".ad", ".ae", ".af", ".ag", ".ai", ".al", ".am", ".an", ".ao", ".aq", ".ar", ".as", ".at", ".au", ".aw", ".ax", ".az", ".ba", ".bb", ".bd", ".be", ".bf", ".bg", ".bh", ".bi", ".bj", ".bm", ".bn", ".bo", ".br", ".bs", ".bt", ".bw", ".by", ".bz", ".ca", ".cc", ".cd", ".cf", ".cg", ".ch", ".ci", ".ck", ".cl", ".cm", ".cn", ".co", ".cr", ".cu", ".cv", ".cx", ".cy", ".cz", ".de", ".dj", ".dk", ".dm", ".do", ".dz", ".ec", ".ee", ".eg", ".er", ".es", ".et", ".eu", ".fi", ".fj", ".fk", ".fm", ".fo", ".fr", ".ga", ".gd", ".ge", ".gf", ".gg", ".gh", ".gi", ".gl", ".gm", ".gn", ".gp", ".gq", ".gr", ".gs", ".gt", ".gu", ".gw", ".gy", ".hk", ".hm", ".hn", ".hr", ".ht", ".hu", ".id", ".ie", ".il", ".im", ".in", ".io", ".iq", ".ir", ".is", ".it", ".je", ".jm", ".jo", ".jp", ".ke", ".kg", ".kh", ".ki", ".km", ".kn", ".kp", ".kr", ".kw", ".ky", ".kz", ".la", ".lb", ".lc", ".li", ".lk", ".lr", ".ls", ".lt", ".lu", ".lv", ".ly", ".ma", ".mc", ".md", ".me", ".mg", ".mh", ".mk", ".ml", ".mm", ".mn", ".mo", ".mp", ".mq", ".mr", ".ms", ".mt", ".mu", ".mv", ".mw", ".mx", ".my", ".mz", ".na", ".nc", ".ne", ".nf", ".ng", ".ni", ".nl", ".no", ".np", ".nr", ".nu", ".nz", ".om", ".pa", ".pe", ".pf", ".pg", ".ph", ".pk", ".pl", ".pn", ".pr", ".ps", ".pt", ".pw", ".py", ".qa", ".re", ".ro", ".rs", ".ru", ".rw", ".sa", ".sb", ".sc", ".sd", ".se", ".sg", ".sh", ".si", ".sk", ".sl", ".sm", ".sn", ".sr", ".st", ".su", ".sv", ".sy", ".sz", ".tc", ".td", ".tf", ".tg", ".th", ".tj", ".tk", ".tl", ".tm", ".tn", ".to", ".tr", ".tt", ".tv", ".tw", ".tz", ".ua", ".ug", ".uk", ".us", ".uy", ".uz", ".va", ".vc", ".ve", ".vg", ".vi", ".vn", ".vu", ".wf", ".ws", ".ye", ".za", ".zm", ".zw", ".vn", ".com.vn", ".net.vn", ".biz.vn", ".info.vn", ".org.vn", ".gov.vn", ".name.vn", ".edu.vn", ".ac.vn", ".pro.vn", ".health.vn", ".angiang.vn", ".danang.vn", ".kontum.vn", ".quangtri.vn", ".bacgian.vn", ".dienbien.vn", ".laichau.vn", ".soctrang.vn", ".backan.vn", ".dongnai.vn", ".lamdong.vn", ".sonla.vn", ".baclieu.vn", ".dongthap.vn", ".langson.vn", ".tayninh.vn", ".bacninh.vn", ".gialai.vn", ".laocai.vn", ".thaibinh.vn.baria-vungtau.vn", ".hagiang.vn", ".longan.vn", ".thainguyen.vn", ".bentre.vn", ".haiduong.vn", ".namdinh.vn", ".thanhhoa.vn", ".binhdinh.vn", ".haiphong.vn", ".nghean.vn", ".thanhphohochiminh.vn", ".binhduong.vn", ".hanam.vn", ".ninhbinh.vn", ".thuathienhue.vn", ".binhphuoc.vn", ".hanoi.vn", ".ninhthuan.vn", ".tiengiang.vn", ".binhthuan.vn", ".hatinh.vn", ".phutho.vn", ".travinh.vn", ".camau.vn", ".haugiang.vn", ".phuyen.vn", ".tuyenquang.vn", ".cantho.vn", ".hoabinh.vn", ".quangbinh.vn", ".vinhlong.vn", ".caobang.vn", ".hungyen.vn", ".quangnam.vn", ".vinhphuc.vn", ".daklac.vn", ".khanhhoa.vn", ".quangngai.vn", ".yenbai.vn", ".daknong.vn", ".kiengiang.vn", ".quangninh.vn", ".Esy.es", ".16mb.com", ".96.Lt", ".hol.es", ".pe.hu", ".890m.com", ".wap.sh", "wap.", ".wap", ". com", ". info", ". name", ". net", ". org", ". pro", ". biz", ". asia", ". cat", ". coop", ". edu", ". gov", ". int", ". jobs", ". mil ", ". mobi", ". museum", ". tel", ". travel", ". ac", ". ad", ". ae", ". af", ". ag", ". ai", ". al", ". am", ". an", ". ao", ". aq", ". ar", ". as", ". at", ". au", ". aw", ". ax", ". az", ". ba", ". bb", ". bd", ". be", ". bf", ". bg", ". bh", ". bi", ". bj", ". bm", ". bn", ". bo", ". br", ". bs", ". bt", ". bw", ". by", ". bz", ". ca", ". cc", ". cd", ". cf", ". cg", ". ch", ". ci", ". ck", ". cl", ". cm", ". cn", ". co", ". cr", ". cu", ". cv", ". cx", ". cy", ". cz", ". de", ". dj", ". dk", ". dm", ". do", ". dz", ". ec", ". ee", ". eg", ". er", ". es", ". et", ". eu", ". fi", ". fj", ". fk", ". fm", ". fo", ". fr", ". ga", ". gd", ". ge", ". gf", ". gg", ". gh", ". gi", ". gl", ". gm", ". gn", ". gp", ". gq", ". gr", ". gs", ". gt", ". gu", ". gw", ". gy", ". hk", ". hm", ". hn", ". hr", ". ht", ". hu", ". id", ". ie", ". il", ". im", ". in", ". io", ". iq", ". ir", ". is", ". it", ". je", ". jm", ". jo", ". jp", ". ke", ". kg", ". kh", ". ki", ". km", ". kn", ". kp", ". kr", ". kw", ". ky", ". kz", ". la", ". lb", ". lc", ". li", ". lk", ". lr", ". ls", ". lt", ". lu", ". lv", ". ly", ". ma", ". mc", ". md", ". me", ". mg", ". mh", ". mk", ". ml", ". mm", ". mn", ". mo", ". mp", ". mq", ". mr", ". ms", ". mt", ". mu", ". mv", ". mw", ". mx", ". my", ". mz", ". na", ". nc", ". ne", ". nf", ". ng", ". ni", ". nl", ". no", ". np", ". nr", ". nu", ". nz", ". om", ". pa", ". pe", ". pf", ". pg", ". ph", ". pk", ". pl", ". pn", ". pr", ". ps", ". pt", ". pw", ". py", ". qa", ". re", ". ro", ". rs", ". ru", ". rw", ". sa", ". sb", ". sc", ". sd", ". se", ". sg", ". sh", ". si", ". sk", ". sl", ". sm", ". sn", ". sr", ". st", ". su", ". sv", ". sy", ". sz", ". tc", ". td", ". tf", ". tg", ". th", ". tj", ". tk", ". tl", ". tm", ". tn", ". to", ". tr", ". tt", ". tv", ". tw", ". tz", ". ua", ". ug", ". uk", ". us", ". uy", ". uz", ". va", ". vc", ". ve", ". vg", ". vi", ". vn", ". vu", ". wf", ". ws", ". ye", ". za", ". zm", ". zw", ". vn", ". com.vn", ". net.vn", ". biz.vn", ". info.vn", ". org.vn", ". gov.vn", ". name.vn", ". edu.vn", ". ac.vn", ". pro.vn", ". health.vn", ". angiang.vn", ". danang.vn", ". kontum.vn", ". quangtri.vn", ". bacgian.vn", ". dienbien.vn", ". laichau.vn", ". soctrang.vn", ". backan.vn", ". dongnai.vn", ". lamdong.vn", ". sonla.vn", ". baclieu.vn", ". dongthap.vn", ". langson.vn", ". tayninh.vn", ". bacninh.vn", ". gialai.vn", ". laocai.vn", ". thaibinh.vn.baria-vungtau.vn", ". hagiang.vn", ". longan.vn", ". thainguyen.vn", ". bentre.vn", ". haiduong.vn", ". namdinh.vn", ". thanhhoa.vn", ". binhdinh.vn", ". haiphong.vn", ". nghean.vn", ". thanhphohochiminh.vn", ". binhduong.vn", ". hanam.vn", ". ninhbinh.vn", ". thuathienhue.vn", ". binhphuoc.vn", ". hanoi.vn", ". ninhthuan.vn", ". tiengiang.vn", ". binhthuan.vn", ". hatinh.vn", ". phutho.vn", ". travinh.vn", ". camau.vn", ". haugiang.vn", ". phuyen.vn", ". tuyenquang.vn", ". cantho.vn", ". hoabinh.vn", ". quangbinh.vn", ". vinhlong.vn", ". caobang.vn", ". hungyen.vn", ". quangnam.vn", ". vinhphuc.vn", ". daklac.vn", ". khanhhoa.vn", ". quangngai.vn", ". yenbai.vn", ". daknong.vn", ". kiengiang.vn", ". quangninh.vn", ". Esy.es", ". 16mb.com", ". 96.Lt", ". hol.es", ". pe.hu", ". 890m.com", ". wap.sh", "wap.", ". wap", ".heck"};
    }

    public static String replaceString(String info) {
        return info.replaceAll("[^a-zA-Z]", "");
    }

    public static String replaceString1(String info) {
        return info.replaceAll("[^a-zA-Z]", "");
    }

    public static String replaceStringWithSpace(String info) {
        return info.replaceAll("[^a-zA-Z0-9\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u00c1\u00c0\u1ea2\u00c3\u1ea0\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\u00cd\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\u00dd\u1ef2\u1ef6\u1ef8\u1ef4@._+,: -]", "");
    }

    public static String replaceSpaceString(String info) {
        return info.trim().replaceAll("\\s+", " ");
    }

    public static String checkBadWord2(String info) {
        info = BadWord.replaceSpaceString(info.toLowerCase());
        int i = 0;
        while (i < list2.length) {
            if (info.indexOf(list2[i]) > -1) {
                info = info.replaceAll(list2[i], "@");
            }
            ++i;
        }
        return info;
    }

    public static boolean ischeckBadWord2(String info) {
        info = BadWord.replaceSpaceString(info.toLowerCase());
        int i = 0;
        while (i < list2.length) {
            if (info.indexOf(list2[i]) > -1) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean checkBadWord(String info) {
        String infoVN = info;
        info = BadWord.replaceString(BadWord.removeAccent(info).toLowerCase());
        int i = 0;
        while (i < list.length) {
            if (info.indexOf(list[i]) > -1) {
                return true;
            }
            ++i;
        }
        i = 0;
        while (i < listvn.length) {
            if (infoVN.indexOf(listvn[i]) > -1) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(temp).replaceAll("");
        result = result.replace("\u0111", "d");
        result = result.replace("\u0110", "D");
        return result;
    }

    public static String replaceExtendDomain(String info) {
        info = BadWord.replaceSpaceString(info.toLowerCase());
        int i = 0;
        while (i < extend.length) {
            if (info.indexOf(extend[i].toLowerCase()) > -1) {
                info = info.replaceAll(extend[i].toLowerCase(), "");
            }
            ++i;
        }
        return info;
    }
}

