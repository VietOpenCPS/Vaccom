package org.vaccom.vcmgt.entity;

import org.vaccom.vcmgt.constant.DBConstant;
import org.vaccom.vcmgt.dto.UyBanNhanDanDto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = DBConstant._UYBANNHANDAN_TABLE_NAME)
public class UyBanNhanDan implements Serializable {
    private static final long serialVersionUID = 1L;
    public UyBanNhanDan() {

    }

    public UyBanNhanDan(UyBanNhanDanDto uyBanNhanDanDto) {
        this.setTenCoQuan(uyBanNhanDanDto.tenCoQuan);
        this.setMaDinhDanh(uyBanNhanDanDto.maDinhDanh);
        this.setTinhThanhMa(uyBanNhanDanDto.tinhThanhMa);
        this.setTinhThanhTen(uyBanNhanDanDto.tinhThanhTen);
        this.setQuanHuyenMa(uyBanNhanDanDto.quanHuyenMa);
        this.setQuanHuyenTen(uyBanNhanDanDto.quanHuyenTen);
        this.setPhuongXaMa(uyBanNhanDanDto.phuongXaMa);
        this.setPhuongXaTen(uyBanNhanDanDto.phuongXaTen);
        this.setDiaChiHoatDong(uyBanNhanDanDto.diaChiHoatDong);
        this.setNguoiDaiDien(uyBanNhanDanDto.nguoiDaiDien);
        this.setSoDienThoai(uyBanNhanDanDto.soDienThoai);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "TenCoQuan")
    private String tenCoQuan;

    @Column(name = "MaDinhDanh")
    private String maDinhDanh;

    @Column(name = "UyBanNhanDanChaID")
    private long uyBanNhanDanChaID;

    @Column(name = "TinhThanhMa")
    private String tinhThanhMa;

    @Column(name = "TinhThanhTen")
    private String tinhThanhTen;

    @Column(name = "QuanHuyenMa")
    private String quanHuyenMa;

    @Column(name = "QuanHuyenTen")
    private String quanHuyenTen;

    @Column(name = "PhuongXaMa")
    private String phuongXaMa;

    @Column(name = "PhuongXaTen")
    private String phuongXaTen;

    @Column(name = "DiaChiHoatDong")
    private String diaChiHoatDong;

    @Column(name = "NguoiDaiDien")
    private String nguoiDaiDien;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenCoQuan() {
        return tenCoQuan;
    }

    public void setTenCoQuan(String tenCoQuan) {
        this.tenCoQuan = tenCoQuan;
    }

    public String getMaDinhDanh() {
        return maDinhDanh;
    }

    public void setMaDinhDanh(String maDinhDanh) {
        this.maDinhDanh = maDinhDanh;
    }

    public long getUyBanNhanDanChaID() {
        return uyBanNhanDanChaID;
    }

    public void setUyBanNhanDanChaID(long uyBanNhanDanChaID) {
        this.uyBanNhanDanChaID = uyBanNhanDanChaID;
    }

    public String getTinhThanhMa() {
        return tinhThanhMa;
    }

    public void setTinhThanhMa(String tinhThanhMa) {
        this.tinhThanhMa = tinhThanhMa;
    }

    public String getTinhThanhTen() {
        return tinhThanhTen;
    }

    public void setTinhThanhTen(String tinhThanhTen) {
        this.tinhThanhTen = tinhThanhTen;
    }

    public String getQuanHuyenMa() {
        return quanHuyenMa;
    }

    public void setQuanHuyenMa(String quanHuyenMa) {
        this.quanHuyenMa = quanHuyenMa;
    }

    public String getQuanHuyenTen() {
        return quanHuyenTen;
    }

    public void setQuanHuyenTen(String quanHuyenTen) {
        this.quanHuyenTen = quanHuyenTen;
    }

    public String getPhuongXaMa() {
        return phuongXaMa;
    }

    public void setPhuongXaMa(String phuongXaMa) {
        this.phuongXaMa = phuongXaMa;
    }

    public String getPhuongXaTen() {
        return phuongXaTen;
    }

    public void setPhuongXaTen(String phuongXaTen) {
        this.phuongXaTen = phuongXaTen;
    }

    public String getDiaChiHoatDong() {
        return diaChiHoatDong;
    }

    public void setDiaChiHoatDong(String diaChiHoatDong) {
        this.diaChiHoatDong = diaChiHoatDong;
    }

    public String getNguoiDaiDien() {
        return nguoiDaiDien;
    }

    public void setNguoiDaiDien(String nguoiDaiDien) {
        this.nguoiDaiDien = nguoiDaiDien;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
