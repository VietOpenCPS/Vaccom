package org.vaccom.vcmgt.entity;

import org.vaccom.vcmgt.constant.DBConstant;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = DBConstant._GIAYDIDUONG_TABLE_NAME)
public class GiayDiDuong implements Serializable {
    private static final long serialVersionUID = 1L;

    public GiayDiDuong() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "MaQR")
    private String maQR;

    @Column(name = "HoVaTen")
    private String hoVaTen;

    @Column(name = "CMTCCCD")
    private String cmtcccd;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "Email")
    private String email;

    @Column(name = "NoiCtTenCoQuan")
    private String noiCtTenCoQuan;

    @Column(name = "NoiCtDiaChi")
    private String noiCtDiaChi;

    @Column(name = "NoiCtTinhThanhMa")
    private String noiCtTinhThanhMa;

    @Column(name = "NoiCtTinhThanhTen")
    private String noiCtTinhThanhTen;

    @Column(name = "NoiCtQuanHuyenMa")
    private String noiCtQuanHuyenMa;

    @Column(name = "NoiCtQuanHuyenTen")
    private String noiCtQuanHuyenTen;

    @Column(name = "NoiCtPhuongXaMa")
    private String noiCtPhuongXaMa;

    @Column(name = "NoiCtPhuongXaTen")
    private String noiCtPhuongXaTen;

    @Column(name = "NoiODiaChi")
    private String noiODiaChi;

    @Column(name = "NoiOTinhThanhMa")
    private String noiOTinhThanhMa;

    @Column(name = "NoiOTinhThanhTen")
    private String noiOTinhThanhTen;

    @Column(name = "NoiOQuanHuyenMa")
    private String noiOQuanHuyenMa;

    @Column(name = "NoiOQuanHuyenTen")
    private String noiOQuanHuyenTen;

    @Column(name = "NoiOPhuongXaMa")
    private String noiOPhuongXaMa;

    @Column(name = "NoiOPhuongXaTen")
    private String noiOPhuongXaTen;

    @Column(name = "LichLamViec")
    private String lichLamViec;

    @Column(name = "UyBanNhanDanID")
    private int uyBanNhanDanID;

    @Column(name = "NgayCap")
    private String ngayCap;

    @Column(name = "ThoiHan")
    private String thoiHan;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "CheckSum")
    private String checksum;

    @Column(name = "Status")
    private int status;

    @Column(name = "CongDan_ID")
<<<<<<< HEAD
    private Long congDanID;

    public Long getCongDanID() {
        return congDanID;
    }

    public void setCongDanID(Long congDanID) {
=======
    private long congDanID;

    public long getCongDanID() {
        return congDanID;
    }

    public void setCongDanID(long congDanID) {
>>>>>>> c77610a423fd162f2c6330b12ab2549c1cf645d0
        this.congDanID = congDanID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMaQR() {
        return maQR;
    }

    public void setMaQR(String maQR) {
        this.maQR = maQR;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getCmtcccd() {
        return cmtcccd;
    }

    public void setCmtcccd(String cmtcccd) {
        this.cmtcccd = cmtcccd;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoiCtTenCoQuan() {
        return noiCtTenCoQuan;
    }

    public void setNoiCtTenCoQuan(String noiCtTenCoQuan) {
        this.noiCtTenCoQuan = noiCtTenCoQuan;
    }

    public String getNoiCtDiaChi() {
        return noiCtDiaChi;
    }

    public void setNoiCtDiaChi(String noiCtDiaChi) {
        this.noiCtDiaChi = noiCtDiaChi;
    }

    public String getNoiCtTinhThanhMa() {
        return noiCtTinhThanhMa;
    }

    public void setNoiCtTinhThanhMa(String noiCtTinhThanhMa) {
        this.noiCtTinhThanhMa = noiCtTinhThanhMa;
    }

    public String getNoiCtTinhThanhTen() {
        return noiCtTinhThanhTen;
    }

    public void setNoiCtTinhThanhTen(String noiCtTinhThanhTen) {
        this.noiCtTinhThanhTen = noiCtTinhThanhTen;
    }

    public String getNoiCtQuanHuyenMa() {
        return noiCtQuanHuyenMa;
    }

    public void setNoiCtQuanHuyenMa(String noiCtQuanHuyenMa) {
        this.noiCtQuanHuyenMa = noiCtQuanHuyenMa;
    }

    public String getNoiCtQuanHuyenTen() {
        return noiCtQuanHuyenTen;
    }

    public void setNoiCtQuanHuyenTen(String noiCtQuanHuyenTen) {
        this.noiCtQuanHuyenTen = noiCtQuanHuyenTen;
    }

    public String getNoiCtPhuongXaMa() {
        return noiCtPhuongXaMa;
    }

    public void setNoiCtPhuongXaMa(String noiCtPhuongXaMa) {
        this.noiCtPhuongXaMa = noiCtPhuongXaMa;
    }

    public String getNoiCtPhuongXaTen() {
        return noiCtPhuongXaTen;
    }

    public void setNoiCtPhuongXaTen(String noiCtPhuongXaTen) {
        this.noiCtPhuongXaTen = noiCtPhuongXaTen;
    }

    public String getNoiODiaChi() {
        return noiODiaChi;
    }

    public void setNoiODiaChi(String noiODiaChi) {
        this.noiODiaChi = noiODiaChi;
    }

    public String getNoiOTinhThanhMa() {
        return noiOTinhThanhMa;
    }

    public void setNoiOTinhThanhMa(String noiOTinhThanhMa) {
        this.noiOTinhThanhMa = noiOTinhThanhMa;
    }

    public String getNoiOTinhThanhTen() {
        return noiOTinhThanhTen;
    }

    public void setNoiOTinhThanhTen(String noiOTinhThanhTen) {
        this.noiOTinhThanhTen = noiOTinhThanhTen;
    }

    public String getNoiOQuanHuyenMa() {
        return noiOQuanHuyenMa;
    }

    public void setNoiOQuanHuyenMa(String noiOQuanHuyenMa) {
        this.noiOQuanHuyenMa = noiOQuanHuyenMa;
    }

    public String getNoiOQuanHuyenTen() {
        return noiOQuanHuyenTen;
    }

    public void setNoiOQuanHuyenTen(String noiOQuanHuyenTen) {
        this.noiOQuanHuyenTen = noiOQuanHuyenTen;
    }

    public String getNoiOPhuongXaMa() {
        return noiOPhuongXaMa;
    }

    public void setNoiOPhuongXaMa(String noiOPhuongXaMa) {
        this.noiOPhuongXaMa = noiOPhuongXaMa;
    }

    public String getNoiOPhuongXaTen() {
        return noiOPhuongXaTen;
    }

    public void setNoiOPhuongXaTen(String noiOPhuongXaTen) {
        this.noiOPhuongXaTen = noiOPhuongXaTen;
    }

    public String getLichLamViec() {
        return lichLamViec;
    }

    public void setLichLamViec(String lichLamViec) {
        this.lichLamViec = lichLamViec;
    }

    public int getUyBanNhanDanID() {
        return uyBanNhanDanID;
    }

    public void setUyBanNhanDanID(int uyBanNhanDanID) {
        this.uyBanNhanDanID = uyBanNhanDanID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(String thoiHan) {
        this.thoiHan = thoiHan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
