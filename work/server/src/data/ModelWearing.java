package data;

import real.Char;
import real.Item;

public class ModelWearing {

    public byte idModel = -1;
    public Item itemModel;
    public Item hatModel;
    public Item headModel;
    public Item wpModel;
    public Item matNa;
    public Item CanhModel;

    public void setItemModel(Char p, Item item, int idModel) {
        if (item != null) {
            if (item.isMatna()) {
                item.place = 4;
                this.matNa = item;
            } else if (item.getTemplate().type == 0) {
                item.place = 4;
                this.itemModel = item;
            } else if (item.getTemplate().type == 1) {
                item.place = 4;
                this.headModel = item;
            } else if (item.getTemplate().type == 2) {
                item.place = 4;
                this.hatModel = item;
            } else if (item.getTemplate().type == 13 || item.isChoiLuaLanBienHinh()) {
                item.place = 4;
                this.wpModel = item;
            } else if (item.isCanh()) {
                item.place = 4;
                this.CanhModel = item;
            }
        }

        this.idModel = (byte) idModel;
    }

    public boolean isChoi() {
        return this.wpModel != null && this.wpModel.isChoi();
    }

    public boolean isNguyetLinhTruong() {
        return this.wpModel != null && this.wpModel.isNguyetLinhTruong();
    }
    
    public boolean isQuatBaiTieu() {
        return this.wpModel != null && this.wpModel.isQuatBaiTieu();
    }

    public boolean isGayNhuY() {
        return this.wpModel != null && this.wpModel.isGayNhuY();
    }

    public boolean isLuongNhatDao() {
        return this.wpModel != null && this.wpModel.isLuongNhatDao();
    }

    public boolean isVuKhiThanBinhNewUpdate09092024() {
        return this.wpModel != null && this.wpModel.isVuKhiThanBinhNewUpdate09092024();
    }


    public int getIdEff() {
        return this.itemModel != null ? this.itemModel.getIdEff() : -1;
    }

    public void changClothes(Char p, int type) {
        if (type == 29) {
            if (this.CanhModel != null) {
                this.CanhModel.place = 0;
                p.iItems.add(this.CanhModel);
                this.CanhModel = null;
            }
        }
      
        if (type == 0) {
            if (this.itemModel != null) {
                this.itemModel.place = 0;
                p.iItems.add(this.itemModel);
                this.itemModel = null;
            }
        } else if (type == 2) {
            if (this.hatModel != null) {
                this.hatModel.place = 0;
                p.iItems.add(this.hatModel);
                this.hatModel = null;
            }
        } else if (type == 1) {
            if (this.headModel != null) {
                this.headModel.place = 0;
                p.iItems.add(this.headModel);
                this.headModel = null;
            }
        } else if (type != 13 && type != 28) {
            if (type == 30 && this.matNa != null) {
                this.matNa.place = 0;
                p.iItems.add(this.matNa);
                this.matNa = null;
            }
        } else if (this.wpModel != null) {
            this.wpModel.place = 0;
            p.iItems.add(this.wpModel);
            this.wpModel = null;
        }

        this.idModel = 25;
    }

    public void unWearChoi(Char p) {
        if (this.wpModel != null) {
            this.wpModel.place = 0;
            p.iItems.add(this.wpModel);
            this.wpModel = null;
        }

        if (this.wpModel == null && this.itemModel == null && this.hatModel == null && this.headModel == null && this.matNa == null) {
            this.idModel = -1;
        }

    }

    public void unWearMask(Char p) {
        if (this.matNa != null) {
            this.matNa.place = 0;
            p.iItems.add(this.matNa);
            this.matNa = null;
        }

        if (this.wpModel == null && this.itemModel == null && this.hatModel == null && this.headModel == null && this.matNa == null) {
            this.idModel = -1;
        }

    }

    public void unWearing(Char p, int idmodel) {
        if (this.idModel > 0) {
            if (this.itemModel != null) {
                this.itemModel.place = 0;
                p.iItems.add(this.itemModel);
                this.itemModel = null;
            }

            if (this.hatModel != null) {
                this.hatModel.place = 0;
                p.iItems.add(this.hatModel);
                this.hatModel = null;
            }

            if (this.headModel != null) {
                this.headModel.place = 0;
                p.iItems.add(this.headModel);
                this.headModel = null;
            }

            if (this.wpModel != null) {
                this.wpModel.place = 0;
                p.iItems.add(this.wpModel);
                this.wpModel = null;
            }

            this.idModel = (byte) idmodel;
        }

    }

    public boolean haveDameAnhSang() {
        return this.itemModel != null && this.itemModel.tempateID == 673;
    }

    public boolean haveDameBongtoi() {
        return this.itemModel != null && this.itemModel.tempateID == 674;
    }

    public boolean haveHutMau() {
        return this.itemModel != null && this.itemModel.tempateID == 672;
    }

    public boolean haveHoangSo() {
        return this.itemModel != null && this.itemModel.tempateID == 671;
    }

    public boolean isBachMi() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 725;
    }

    public boolean isThuyVuHongSam() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 726;
    }

    public boolean isTieuLongNu() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 740;
    }

    public boolean isDuongQua() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 741;
    }

    public boolean isTienNu() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 743;
    }

    public boolean isTinhQuan() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 744;
    }

    public boolean isAoDaiNam() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 754;
    }

    public boolean isAoDaiNu() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 755;
    }

    public boolean isSonTinh() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 736;
    }

    public boolean isThuyTinh() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 737;
    }

    public boolean isLacLongQuan() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 756;
    }
    public boolean isAuCo() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 757;
    }

    // TODO THÊM ÁO THỜI TRANG (2)
    public boolean isNgheThuong() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 797;
    }

    public boolean isNgocNu() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 798;
    }

    public boolean isXichCuoc() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 799;
    }

    public boolean isKimDong() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 800;
    }

    public boolean isLoiCong() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 801;
    }

    public boolean isMatna() {
        return this.isMatNaBiNgo() || this.isMatNaPanda() || this.isMatNaQuy() || this.isMatNaSoi() || this.isMatNaZoombi()
                || this.isMatNaTho()
                || this.isMatNaCao();
    }

    public boolean isMatNaBiNgo() {
        return this.matNa != null && this.matNa.getTemplate().id == 745;
    }

    public boolean isMatNaQuy() {
        return this.matNa != null && this.matNa.getTemplate().id == 746;
    }

    public boolean isMatNaSoi() {
        return this.matNa != null && this.matNa.getTemplate().id == 747;
    }

    public boolean isMatNaZoombi() {
        return this.matNa != null && this.matNa.getTemplate().id == 748;
    }

    public boolean isMatNaPanda() {
        return this.matNa != null && this.matNa.getTemplate().id == 749;
    }

    public boolean isMatNaTho() {
        return this.matNa != null && this.matNa.getTemplate().id == 837;
    }

    public boolean isMatNaCao() {
        return this.matNa != null && this.matNa.getTemplate().id == 838;
    }

    public boolean isGiapRong() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 839;
    }

    public boolean isMeoXanh() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 833;
    }

    public boolean isMeoQuyToc() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 834;
    }
    
    //(Kiêm tra xem co dang mac hay k)
    public boolean isAonoelNam2024() {
        return this.itemModel != null && this.itemModel.getTemplate().id == 888;
    }
    public boolean isAonoelNu2024() 
    {
        
        return this.itemModel != null && this.itemModel.getTemplate().id == 889;
    }
    
    public boolean isCaiTaoSonTinh() {
        if (this.itemModel == null) {
            return false;
        }
       
        return (this.itemModel.getTemplate().id == 755 || 
                this.itemModel.getTemplate().id == 756 ||
                this.itemModel.getTemplate().id == 723 ||
                this.itemModel.getTemplate().id == 725 ||
                this.itemModel.getTemplate().id == 799 ||
                this.itemModel.getTemplate().id == 800 ||
                this.itemModel.getTemplate().id == 801) &&
               this.itemModel.CaiTao > 0;
    }

    public boolean isCaiTaoMiNuong() {
        if (this.itemModel == null) {
            return false;
        }
       
        return 
               (this.itemModel.getTemplate().id == 737 ||
                this.itemModel.getTemplate().id == 757 ||
                this.itemModel.getTemplate().id == 724 ||
                this.itemModel.getTemplate().id == 726 ||
                this.itemModel.getTemplate().id == 797 ||
                this.itemModel.getTemplate().id == 798) &&
               this.itemModel.CaiTao > 0;
    }

  

 
   
    
    public int getHp() {
        if (this.idModel > 0) {
            int hp = 0;
            if (this.itemModel != null) {
                hp = this.itemModel.getTemplate().atb[2];
            }

            if (this.hatModel != null) {
                hp += this.hatModel.getTemplate().atb[2];
            }

            if (this.headModel != null) {
                hp += this.headModel.getTemplate().atb[2];
            }

            return hp;
        } else {
            return 0;
        }
    }

    public int getMp() {
        if (this.idModel > 0) {
            int mp = 0;
            if (this.itemModel != null) {
                mp = this.itemModel.getTemplate().atb[3];
            }

            if (this.hatModel != null) {
                mp += this.hatModel.getTemplate().atb[3];
            }

            if (this.headModel != null) {
                mp += this.headModel.getTemplate().atb[3];
            }

            return mp;
        } else {
            return 0;
        }
    }

    public int getAttack() {
        int resul = 0;
        if (this.itemModel != null) {
            resul += this.itemModel.getTemplate().atb[0];
        }

        if (this.hatModel != null) {
            resul += this.hatModel.getTemplate().atb[0];
        }

        if (this.matNa != null) {
            resul += this.matNa.lockAtb[0];
        }

        if (this.headModel != null) {
            resul += this.headModel.getTemplate().atb[0];
        }

        if (this.wpModel != null) {
            resul += 10;
        }

        if (this.CanhModel != null) {
            resul += this.CanhModel.lockAtb[0];
        }

        return resul;
    }

    public boolean doNotPaintHat() {
        return this.itemModel.getTemplate().id == 622 || this.itemModel.getTemplate().id == 623 || this.itemModel.getTemplate().id == 672 || this.itemModel.getTemplate().id == 673 || this.itemModel.getTemplate().id == 674 || this.isAoDaiNam() || this.isAoDaiNu();
    }

    public int getPCXbangSetDocLuaLan() {
        int result = 0;
        if (this.itemModel != null) {
            switch (this.itemModel.getTemplate().id) {
                case 833: // Meo Xanh
                case 834: // Meo Quy Toc
                case 839: // Giap Rong
                    result = 100;
                    break;
                case 579:
                case 580:
                case 582:
                case 583:
                case 723:
                case 724:
                case 725:
                case 726:
                case 736:
                case 737:
                case 740:
                case 741:
                case 743:
                case 744:
                case 797:
                case 798:
                case 799:
                case 800:
                case 801:
                    result = 50;
                    break;
                default:
                    break;
            }
        }

        if (this.hatModel != null && this.hatModel.getTemplate().id == 578) {
            result += 50;
        }

        if (this.headModel != null && this.headModel.getTemplate().id == 577) {
            result += 50;
        }

        return result;
    }

    public int getLuaLan() {
        return this.itemModel == null || this.itemModel.getTemplate().id != 620 && this.itemModel.getTemplate().id != 621 && this.itemModel.getTemplate().id != 622 && this.itemModel.getTemplate().id != 623 && this.itemModel.getTemplate().id != 671 && this.itemModel.getTemplate().id != 672 && this.itemModel.getTemplate().id != 673 && this.itemModel.getTemplate().id != 674 && this.itemModel.getTemplate().id != 754 && this.itemModel.getTemplate().id != 755 ? 0 : 1;
    }

    public float getBangLan() {
        return this.itemModel == null || this.itemModel.getTemplate().id != 610 && this.itemModel.getTemplate().id != 609 && this.itemModel.getTemplate().id != 620 && this.itemModel.getTemplate().id != 621 && this.itemModel.getTemplate().id != 622 && this.itemModel.getTemplate().id != 623 && this.itemModel.getTemplate().id != 671 && this.itemModel.getTemplate().id != 672 && this.itemModel.getTemplate().id != 673 && this.itemModel.getTemplate().id != 674 && this.itemModel.getTemplate().id != 754 && this.itemModel.getTemplate().id != 755 ? 0.0F : 1.0F;
    }

    public int getSetLan() {
        return this.itemModel == null || this.itemModel.getTemplate().id != 610 && this.itemModel.getTemplate().id != 609 && this.itemModel.getTemplate().id != 620 && this.itemModel.getTemplate().id != 621 && this.itemModel.getTemplate().id != 622 && this.itemModel.getTemplate().id != 623 && this.itemModel.getTemplate().id != 671 && this.itemModel.getTemplate().id != 672 && this.itemModel.getTemplate().id != 673 && this.itemModel.getTemplate().id != 674 && this.itemModel.getTemplate().id != 754 && this.itemModel.getTemplate().id != 755 ? 0 : 1;
    }

    public int getDocLan() {
        return this.itemModel == null || this.itemModel.getTemplate().id != 610 && this.itemModel.getTemplate().id != 609 && this.itemModel.getTemplate().id != 620 && this.itemModel.getTemplate().id != 621 && this.itemModel.getTemplate().id != 622 && this.itemModel.getTemplate().id != 623 && this.itemModel.getTemplate().id != 671 && this.itemModel.getTemplate().id != 672 && this.itemModel.getTemplate().id != 673 && this.itemModel.getTemplate().id != 674 && this.itemModel.getTemplate().id != 754 && this.itemModel.getTemplate().id != 755 ? 0 : 1;
    }

    /**
     * todo Giảm sát thương cuối
     *
     * @return
     */
    public int getPcGiamSatThuong() {
        return this.itemModel == null || this.itemModel.getTemplate().id != 725 && this.itemModel.getTemplate().id != 726 ? 0 : 50;
    }

    public int getbaoKichMatna() {
        int baokich = 0;
        if (this.matNa != null) {
            baokich = this.matNa.otherAtt[4];
        }

        if (this.CanhModel != null) {
            baokich += this.CanhModel.otherAtt[4];
        }

        return baokich;
    }

    public int getbaoKich() {
        int baokich = 0;
        return this.itemModel == null || this.itemModel.getTemplate().id != 620 && this.itemModel.getTemplate().id != 621 && this.itemModel.getTemplate().id != 622 && this.itemModel.getTemplate().id != 623 && this.itemModel.getTemplate().id != 754 && this.itemModel.getTemplate().id != 755 ? baokich : 20 + baokich;
    }

    public int getDefend() {
        int resul = 0;
        if (this.itemModel != null) {
            resul += this.itemModel.getTemplate().atb[1];
        }

        if (this.matNa != null) {
            resul += this.matNa.lockAtb[1] > this.matNa.lockAtb[2] ? this.matNa.lockAtb[1] : this.matNa.lockAtb[2];
        }

        if (this.hatModel != null) {
            resul += this.hatModel.getTemplate().atb[1];
        }

        if (this.headModel != null) {
            resul += this.headModel.getTemplate().atb[1];
        }

        if (this.CanhModel != null) {
            resul += this.CanhModel.lockAtb[1] > this.CanhModel.lockAtb[2] ? this.CanhModel.lockAtb[1] : this.CanhModel.lockAtb[2];
        }

        return resul;
    }

    public int getPCNgtuyet() {
        return this.wpModel != null ? this.wpModel.otherAtt[14] : 0;
    }

    public String getInfoModelWearing() {
        String[] st = new String[6];
        if (this.itemModel != null) {
            st[0] = this.itemModel.getInfoSave();
        }

        if (this.headModel != null) {
            st[1] = this.headModel.getInfoSave();
        }

        if (this.hatModel != null) {
            st[2] = this.hatModel.getInfoSave();
        }

        if (this.wpModel != null) {
            st[3] = this.wpModel.getInfoSave();
        }

        if (this.matNa != null) {
            st[4] = this.matNa.getInfoSave();
        }

        if (this.CanhModel != null) {
            st[5] = this.CanhModel.getInfoSave();
        }

        String result = "";

        for (int i = 0; i < st.length; ++i) {
            if (st[i] != null) {
                result = result + st[i] + ">";
            }
        }

        if (!result.equals("")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    public int getIdImageClothes() {
        return this.idModel;
    }

    public boolean haveWearing() {
        return this.itemModel != null || this.hatModel != null || this.headModel != null;
    }
    
}
