package org.vaccom.vcmgt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.vaccom.vcmgt.constant.DBConstant;

@Entity
@Table(name = DBConstant._NGUOITIEMCHUNG_TABLE_NAME)
public class NguoiTiemChung implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NguoiTiemChung() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@NotBlank
	@Column(name = "HoVaTen")
	private String hoVaTen;

	@Column(name = "NgaySinh")
	private String ngaySinh;

	@Column(name = "GioiTinh")
	private int gioiTinh;

	@NotBlank
	@Column(name = "CMTCCCD")
	private String cmtcccd;

	@Column(name = "NgheNghiep")
	private String ngheNghiep;

	@Column(name = "NhomDoiTuong")
	private int nhomDoiTuong;

	@Column(name = "DonViCongTac")
	private String donViCongTac;

	@Column(name = "SoDienThoai")
	private String soDienThoai;

	@Column(name = "Email")
	private String email;

	@Column(name = "MaSoBHXH")
	private String maSoBHXH;

	@Column(name = "SoTheBHYT")
	private String soTheBHYT;

	@Column(name = "DiaChiNoiO")
	private int diaChiNoiO;

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

	@Column(name = "DiaBanCoSoID")
	private long diaBanCoSoID;

	@Column(name = "CoSoYTeMa")
	private String coSoYTeMa;

	@Column(name = "CoSoYTeTen")
	private String coSoYTeTen;

	@Column(name = "DanTocMa")
	private String danTocMa;

	@Column(name = "QuocTichMa")
	private String quocTichMa;

	@Column(name = "TienSuDiUng")
	private String tienSuDiUng;

	@Column(name = "CacBenhLyDangMac")
	private String cacBenhLyDangMac;

	@Column(name = "CacThuocDangDung")
	private String cacThuocDangDung;

	@Column(name = "GhiChu")
	private String ghiChu;

	@Column(name = "NgayDangKi")
	private String ngayDangKi;

	@Column(name = "tinhTrangDangKi")
	private int tinhTrangDangKi;

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

	public int getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(int gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getCmtcccd() {
		return cmtcccd;
	}

	public void setCmtcccd(String cmtcccd) {
		this.cmtcccd = cmtcccd;
	}

	public String getNgheNghiep() {
		return ngheNghiep;
	}

	public void setNgheNghiep(String ngheNghiep) {
		this.ngheNghiep = ngheNghiep;
	}

	public int getNhomDoiTuong() {
		return nhomDoiTuong;
	}

	public void setNhomDoiTuong(int nhomDoiTuong) {
		this.nhomDoiTuong = nhomDoiTuong;
	}

	public String getDonViCongTac() {
		return donViCongTac;
	}

	public void setDonViCongTac(String donViCongTac) {
		this.donViCongTac = donViCongTac;
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

	public String getMaSoBHXH() {
		return maSoBHXH;
	}

	public void setMaSoBHXH(String maSoBHXH) {
		this.maSoBHXH = maSoBHXH;
	}

	public String getSoTheBHYT() {
		return soTheBHYT;
	}

	public void setSoTheBHYT(String soTheBHYT) {
		this.soTheBHYT = soTheBHYT;
	}

	public int getDiaChiNoiO() {
		return diaChiNoiO;
	}

	public void setDiaChiNoiO(int diaChiNoiO) {
		this.diaChiNoiO = diaChiNoiO;
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

	public long getDiaBanCoSoID() {
		return diaBanCoSoID;
	}

	public void setDiaBanCoSoID(long diaBanCoSoID) {
		this.diaBanCoSoID = diaBanCoSoID;
	}

	public String getCoSoYTeMa() {
		return coSoYTeMa;
	}

	public void setCoSoYTeMa(String coSoYTeMa) {
		this.coSoYTeMa = coSoYTeMa;
	}

	public String getCoSoYTeTen() {
		return coSoYTeTen;
	}

	public void setCoSoYTeTen(String coSoYTeTen) {
		this.coSoYTeTen = coSoYTeTen;
	}

	public String getDanTocMa() {
		return danTocMa;
	}

	public void setDanTocMa(String danTocMa) {
		this.danTocMa = danTocMa;
	}

	public String getQuocTichMa() {
		return quocTichMa;
	}

	public void setQuocTichMa(String quocTichMa) {
		this.quocTichMa = quocTichMa;
	}

	public String getTienSuDiUng() {
		return tienSuDiUng;
	}

	public void setTienSuDiUng(String tienSuDiUng) {
		this.tienSuDiUng = tienSuDiUng;
	}

	public String getCacBenhLyDangMac() {
		return cacBenhLyDangMac;
	}

	public void setCacBenhLyDangMac(String cacBenhLyDangMac) {
		this.cacBenhLyDangMac = cacBenhLyDangMac;
	}

	public String getCacThuocDangDung() {
		return cacThuocDangDung;
	}

	public void setCacThuocDangDung(String cacThuocDangDung) {
		this.cacThuocDangDung = cacThuocDangDung;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getNgayDangKi() {
		return ngayDangKi;
	}

	public void setNgayDangKi(String ngayDangKi) {
		this.ngayDangKi = ngayDangKi;
	}

	public int getTinhTrangDangKi() {
		return tinhTrangDangKi;
	}

	public void setTinhTrangDangKi(int tinhTrangDangKi) {
		this.tinhTrangDangKi = tinhTrangDangKi;
	}

}