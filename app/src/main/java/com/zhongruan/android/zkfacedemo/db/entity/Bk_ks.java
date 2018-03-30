package com.zhongruan.android.zkfacedemo.db.entity;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;


/**
 * Created by Administrator on 2017/7/31.
 */
@Entity(nameInDb = "bk_ks")
public class Bk_ks implements Parcelable {
    @Id(autoincrement = true)
    @Property(nameInDb = "ksid")
    private Long ksid;
    @Property(nameInDb = "ks_ksno")
    private String ks_ksno;
    @Property(nameInDb = "ks_xm")
    private String ks_xm;
    @Property(nameInDb = "ks_zjno")
    private String ks_zjno;
    @Property(nameInDb = "ks_xb")
    private String ks_xb;
    @Property(nameInDb = "ks_ccno")
    private String ks_ccno;
    @Property(nameInDb = "ks_ccmc")
    private String ks_ccmc;
    @Property(nameInDb = "ks_kcno")
    private String ks_kcno;
    @Property(nameInDb = "ks_kcmc")
    private String ks_kcmc;
    @Property(nameInDb = "ks_zwh")
    private String ks_zwh;
    @Property(nameInDb = "ks_xp")
    private String ks_xp;
    @Property(nameInDb = "isRZ")
    private String isRZ;

    @Generated(hash = 1530620330)
    public Bk_ks(Long ksid, String ks_ksno, String ks_xm, String ks_zjno,
            String ks_xb, String ks_ccno, String ks_ccmc, String ks_kcno,
            String ks_kcmc, String ks_zwh, String ks_xp, String isRZ) {
        this.ksid = ksid;
        this.ks_ksno = ks_ksno;
        this.ks_xm = ks_xm;
        this.ks_zjno = ks_zjno;
        this.ks_xb = ks_xb;
        this.ks_ccno = ks_ccno;
        this.ks_ccmc = ks_ccmc;
        this.ks_kcno = ks_kcno;
        this.ks_kcmc = ks_kcmc;
        this.ks_zwh = ks_zwh;
        this.ks_xp = ks_xp;
        this.isRZ = isRZ;
    }

    @Generated(hash = 1002290122)
    public Bk_ks() {
    }

    protected Bk_ks(Parcel in) {
        if (in.readByte() == 0) {
            ksid = null;
        } else {
            ksid = in.readLong();
        }
        ks_ksno = in.readString();
        ks_xm = in.readString();
        ks_zjno = in.readString();
        ks_xb = in.readString();
        ks_kcno = in.readString();
        ks_kcmc = in.readString();
        ks_zwh = in.readString();
        ks_xp = in.readString();
        isRZ = in.readString();
    }

    public static final Creator<Bk_ks> CREATOR = new Creator<Bk_ks>() {
        @Override
        public Bk_ks createFromParcel(Parcel in) {
            return new Bk_ks(in);
        }

        @Override
        public Bk_ks[] newArray(int size) {
            return new Bk_ks[size];
        }
    };

    public Long getKsid() {
        return this.ksid;
    }

    public void setKsid(Long ksid) {
        this.ksid = ksid;
    }

    public String getKs_ksno() {
        return this.ks_ksno;
    }

    public void setKs_ksno(String ks_ksno) {
        this.ks_ksno = ks_ksno;
    }

    public String getKs_xm() {
        return this.ks_xm;
    }

    public void setKs_xm(String ks_xm) {
        this.ks_xm = ks_xm;
    }

    public String getKs_zjno() {
        return this.ks_zjno;
    }

    public void setKs_zjno(String ks_zjno) {
        this.ks_zjno = ks_zjno;
    }

    public String getKs_xb() {
        return this.ks_xb;
    }

    public void setKs_xb(String ks_xb) {
        this.ks_xb = ks_xb;
    }

    public String getKs_kcno() {
        return this.ks_kcno;
    }

    public void setKs_kcno(String ks_kcno) {
        this.ks_kcno = ks_kcno;
    }

    public String getKs_kcmc() {
        return this.ks_kcmc;
    }

    public void setKs_kcmc(String ks_kcmc) {
        this.ks_kcmc = ks_kcmc;
    }

    public String getKs_zwh() {
        return this.ks_zwh;
    }

    public void setKs_zwh(String ks_zwh) {
        this.ks_zwh = ks_zwh;
    }

    public String getKs_xp() {
        return this.ks_xp;
    }

    public void setKs_xp(String ks_xp) {
        this.ks_xp = ks_xp;
    }

    public String getIsRZ() {
        return this.isRZ;
    }

    public void setIsRZ(String isRZ) {
        this.isRZ = isRZ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.ksid);
        parcel.writeString(this.ks_ksno);
        parcel.writeString(this.ks_xm);
        parcel.writeString(this.ks_zjno);
        parcel.writeString(this.ks_xb);
        parcel.writeString(this.ks_kcno);
        parcel.writeString(this.ks_kcmc);
        parcel.writeString(this.ks_zwh);
        parcel.writeString(this.ks_xp);
        parcel.writeString(this.isRZ);

    }

    public String getKs_ccno() {
        return this.ks_ccno;
    }

    public void setKs_ccno(String ks_ccno) {
        this.ks_ccno = ks_ccno;
    }

    public String getKs_ccmc() {
        return this.ks_ccmc;
    }

    public void setKs_ccmc(String ks_ccmc) {
        this.ks_ccmc = ks_ccmc;
    }
}
