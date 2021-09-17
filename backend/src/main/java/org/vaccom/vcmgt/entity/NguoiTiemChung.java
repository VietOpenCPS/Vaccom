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

	@Column(name = "MaQR")
	private String MaQR;

	@Column(name = "NgaySinh")
	private String ngaySinh;

	@Column(name = "GioiTinh")
	private int gioiTinh;
	@Column(name = "CMTCCCD")
	private String cmtcccd;

	@Column(name = "NhomDoiTuong")
	private int nhomDoiTuong;

	@Column(name = "DonViCongTac")
	private String donViCongTac;

	@Column(name = "SoDienThoai")
	private String soDienThoai;

	@Column(name = "Email")
	private String email;

	@Column(name = "SoTheBHYT")
	private String soTheBHYT;

	@Column(name = "DiaChiNoiO")
	private String diaChiNoiO;

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
	private long diaBanCoSoId;

	@Column(name = "CoSoYTeID")
	private long CoSoYTeId;

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

	@Column(name = "KiemTraTrung")
	private int kiemTraTrung;

	@Column(name = "KetQuaKiemTra")
	private String ketQuaKiemTra;

	@Column(name = "CongDan_ID")
	private long congDanID;

	@Column(name = "SoMuiTiem")
	private int soMuiTiem;

	@Column(name = "NgayTiemCuoi")
	private String ngayTiemCuoi;

	public int getSoMuiTiem() {
		return soMuiTiem;
	}

	public void setSoMuiTiem(int soMuiTiem) {
		this.soMuiTiem = soMuiTiem;
	}

	public String getNgayTiemCuoi() {
		return ngayTiemCuoi;
	}

	public void setNgayTiemCuoi(String ngayTiemCuoi) {
		this.ngayTiemCuoi = ngayTiemCuoi;
	}

	public long getCongDanID() {
		return congDanID;
	}

	public void setCongDanID(long congDanID) {
		this.congDanID = congDanID;
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

	public String getSoTheBHYT() {
		return soTheBHYT;
	}

	public void setSoTheBHYT(String soTheBHYT) {
		this.soTheBHYT = soTheBHYT;
	}

	public String getDiaChiNoiO() {
		return diaChiNoiO;
	}

	public void setDiaChiNoiO(String diaChiNoiO) {
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

	public long getDiaBanCoSoId() {
		return diaBanCoSoId;
	}

	public void setDiaBanCoSoId(long diaBanCoSoId) {
		this.diaBanCoSoId = diaBanCoSoId;
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

	public String getMaQR() {
		return MaQR;
	}

	public void setMaQR(String maQR) {
		MaQR = maQR;
	}

	public int getKiemTraTrung() {
		return kiemTraTrung;
	}

	public void setKiemTraTrung(int kiemTraTrung) {
		this.kiemTraTrung = kiemTraTrung;
	}

	public String getKetQuaKiemTra() {
		return ketQuaKiemTra;
	}

	public void setKetQuaKiemTra(String ketQuaKiemTra) {
		this.ketQuaKiemTra = ketQuaKiemTra;
	}

	public long getCoSoYTeId() {
		return CoSoYTeId;
	}

	public void setCoSoYTeId(long coSoYTeId) {
		CoSoYTeId = coSoYTeId;
	}

}