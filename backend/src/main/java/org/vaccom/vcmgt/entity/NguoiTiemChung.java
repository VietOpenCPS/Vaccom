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

	@Column(name = "TinhThanh_Ma")
	private String tinhThanh_Ma;

	@Column(name = "TinhThanh_Ten")
	private String tinhThanh_Ten;

	@Column(name = "QuanHuyen_Ma")
	private String quanHuyen_Ma;

	@Column(name = "QuanHuyen_Ten")
	private String quanHuyen_Ten;

	@Column(name = "PhuongXa_Ma")
	private String phuongXa_Ma;

	@Column(name = "PhuongXa_Ten")
	private String phuongXa_Ten;

	@Column(name = "DiaBanCoSo_ID")
	private long diaBanCoSo_ID;

	@Column(name = "CoSoYTe_Ma")
	private String coSoYTe_Ma;

	@Column(name = "CoSoYTe_Ten")
	private String coSoYTe_Ten;

	@Column(name = "DanToc_Ma")
	private String danToc_Ma;

	@Column(name = "QuocTich_Ma")
	private String quocTich_Ma;

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

	public String getTinhThanh_Ma() {
		return tinhThanh_Ma;
	}

	public void setTinhThanh_Ma(String tinhThanh_Ma) {
		this.tinhThanh_Ma = tinhThanh_Ma;
	}

	public String getTinhThanh_Ten() {
		return tinhThanh_Ten;
	}

	public void setTinhThanh_Ten(String tinhThanh_Ten) {
		this.tinhThanh_Ten = tinhThanh_Ten;
	}

	public String getQuanHuyen_Ma() {
		return quanHuyen_Ma;
	}

	public void setQuanHuyen_Ma(String quanHuyen_Ma) {
		this.quanHuyen_Ma = quanHuyen_Ma;
	}

	public String getQuanHuyen_Ten() {
		return quanHuyen_Ten;
	}

	public void setQuanHuyen_Ten(String quanHuyen_Ten) {
		this.quanHuyen_Ten = quanHuyen_Ten;
	}

	public String getPhuongXa_Ma() {
		return phuongXa_Ma;
	}

	public void setPhuongXa_Ma(String phuongXa_Ma) {
		this.phuongXa_Ma = phuongXa_Ma;
	}

	public String getPhuongXa_Ten() {
		return phuongXa_Ten;
	}

	public void setPhuongXa_Ten(String phuongXa_Ten) {
		this.phuongXa_Ten = phuongXa_Ten;
	}

	public long getDiaBanCoSo_ID() {
		return diaBanCoSo_ID;
	}

	public void setDiaBanCoSo_ID(long diaBanCoSo_ID) {
		this.diaBanCoSo_ID = diaBanCoSo_ID;
	}

	public String getCoSoYTe_Ma() {
		return coSoYTe_Ma;
	}

	public void setCoSoYTe_Ma(String coSoYTe_Ma) {
		this.coSoYTe_Ma = coSoYTe_Ma;
	}

	public String getCoSoYTe_Ten() {
		return coSoYTe_Ten;
	}

	public void setCoSoYTe_Ten(String coSoYTe_Ten) {
		this.coSoYTe_Ten = coSoYTe_Ten;
	}

	public String getDanToc_Ma() {
		return danToc_Ma;
	}

	public void setDanToc_Ma(String danToc_Ma) {
		this.danToc_Ma = danToc_Ma;
	}

	public String getQuocTich_Ma() {
		return quocTich_Ma;
	}

	public void setQuocTich_Ma(String quocTich_Ma) {
		this.quocTich_Ma = quocTich_Ma;
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