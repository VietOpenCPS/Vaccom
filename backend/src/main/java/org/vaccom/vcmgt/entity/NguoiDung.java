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
@Table(name = DBConstant._NGUOIDUNG_TABLE_NAME)
public class NguoiDung implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NguoiDung() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@NotBlank
	@Column(name = "TenDangNhap")
	private String tenDangNhap;

	@NotBlank
	@Column(name = "HoVaTen")
	private String hoVaTen;

	@Column(name = "ChucDanh")
	private String chucDanh;

	@Column(name = "SoDienThoai")
	private String soDienThoai;

	@Column(name = "Email")
	private String email;

	@Column(name = "MatKhau")
	private String matKhau;

	@Column(name = "DiaBanCoSoID")
	private long diaBanCoSoId;

	@Column(name = "CoSoYTeID")
	private long coSoYTeId;

	@Column(name = "NguoiTiemChungID ")
	private long nguoiTiemChungId;

	@Column(name = "QuanTriHeThong")
	private int quanTriHeThong;

	@Column(name = "KhoaTaiKhoan")
	private boolean khoaTaiKhoan;

	@Column(name = "UyBanNhanDanID ")
	private long uyBanNhanDanId;

	@Column(name = "CongDan_ID")

	private Long congDanID;

	public Long getCongDanID() {
		return congDanID;
	}

	public void setCongDanID(Long congDanID) {
		this.congDanID = congDanID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getChucDanh() {
		return chucDanh;
	}

	public void setChucDanh(String chucDanh) {
		this.chucDanh = chucDanh;
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

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public long getDiaBanCoSoId() {
		return diaBanCoSoId;
	}

	public void setDiaBanCoSoId(long diaBanCoSoId) {
		this.diaBanCoSoId = diaBanCoSoId;
	}

	public long getCoSoYTeId() {
		return coSoYTeId;
	}

	public void setCoSoYTeId(long coSoYTeId) {
		this.coSoYTeId = coSoYTeId;
	}

	public boolean isKhoaTaiKhoan() {
		return khoaTaiKhoan;
	}

	public void setKhoaTaiKhoan(boolean khoaTaiKhoan) {
		this.khoaTaiKhoan = khoaTaiKhoan;
	}

	public long getNguoiTiemChungId() {
		return nguoiTiemChungId;
	}

	public void setNguoiTiemChungId(long nguoiTiemChungId) {
		this.nguoiTiemChungId = nguoiTiemChungId;
	}

	public int getQuanTriHeThong() {
		return quanTriHeThong;
	}

	public void setQuanTriHeThong(int quanTriHeThong) {
		this.quanTriHeThong = quanTriHeThong;
	}

	public long getUyBanNhanDanId() {
		return uyBanNhanDanId;
	}

	public void setUyBanNhanDanId(long uyBanNhanDanId) {
		this.uyBanNhanDanId = uyBanNhanDanId;
	}

}