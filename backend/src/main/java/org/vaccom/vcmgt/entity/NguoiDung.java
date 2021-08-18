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

	@NotBlank
	@Column(name = "SoDienThoai")
	private String soDienThoai;

	@Column(name = "Email")
	private String email;

	@Column(name = "MatKhau")
	private String matKhau;

	@Column(name = "DiaBanCoSo_ID")
	private long diaBanCoSo_ID;

	@Column(name = "CoSoYTe_ID")
	private long coSoYTe_ID;

	@Column(name = "QuanTriHeThong")
	private boolean quanTriHeThong;

	@Column(name = "KhoaTaiKhoan")
	private boolean khoaTaiKhoan;

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

	public long getDiaBanCoSo_ID() {
		return diaBanCoSo_ID;
	}

	public void setDiaBanCoSo_ID(long diaBanCoSo_ID) {
		this.diaBanCoSo_ID = diaBanCoSo_ID;
	}

	public long getCoSoYTe_ID() {
		return coSoYTe_ID;
	}

	public void setCoSoYTe_ID(long coSoYTe_ID) {
		this.coSoYTe_ID = coSoYTe_ID;
	}

	public boolean isQuanTriHeThong() {
		return quanTriHeThong;
	}

	public void setQuanTriHeThong(boolean quanTriHeThong) {
		this.quanTriHeThong = quanTriHeThong;
	}

	public boolean isKhoaTaiKhoan() {
		return khoaTaiKhoan;
	}

	public void setKhoaTaiKhoan(boolean khoaTaiKhoan) {
		this.khoaTaiKhoan = khoaTaiKhoan;
	}

}