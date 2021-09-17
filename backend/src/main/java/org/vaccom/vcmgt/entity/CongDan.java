package org.vaccom.vcmgt.entity;

import org.vaccom.vcmgt.constant.DBConstant;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = DBConstant._CONGDAN_TABLE_NAME)
public class CongDan implements Serializable {
    private static final long serialVersionUID = 1L;

    public CongDan() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "HoVaTen")
    private String hoVaTen;

    @Column(name = "NgaySinh")
    private String ngaySinh;

    @Column(name = "GioiTinh")
    private long gioiTinh;

    @Column(name = "DiaChiThuongTru")
    private String diaChiThuongTru;

    @Column(name = "TinhThanh_Ma")
    private String tinhThanhMa;

    @Column(name = "TinhThanh_Ten")
    private String tinhThanhTen;

    @Column(name = "QuanHuyen_Ma")
    private String quanHuyenMa;

    @Column(name = "QuanHuyen_Ten")
    private String quanHuyenTen;

    @Column(name = "PhuongXa_Ma")
    private String phuongXaMa;

    @Column(name = "PhuongXa_Ten")
    private String phuongXaTen;

    @Column(name = "CMTCCCD")
    private String cmtcccd;

    @Column(name = "NoiCap")
    private String noiCap;

    @Column(name = "NgayCap")
    private String ngayCap;

    @Column(name = "SoMuiTiem")
    private int soMuiTiem;

    public int getSoMuiTiem() {
        return soMuiTiem;
    }

    public void setSoMuiTiem(int soMuiTiem) {
        this.soMuiTiem = soMuiTiem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public long getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(long gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChiThuongTru() {
        return diaChiThuongTru;
    }

    public void setDiaChiThuongTru(String diaChiThuongTru) {
        this.diaChiThuongTru = diaChiThuongTru;
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

    public String getCmtcccd() {
        return cmtcccd;
    }

    public void setCmtcccd(String cmtcccd) {
        this.cmtcccd = cmtcccd;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }
}
