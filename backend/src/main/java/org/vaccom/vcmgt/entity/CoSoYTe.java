package org.vaccom.vcmgt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.vaccom.vcmgt.constant.DBConstant;

@Entity
@Table(name = DBConstant._COSOYTE_TABLE_NAME)
public class CoSoYTe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CoSoYTe() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "MaCoSo")
	private String maCoSo;

	@Column(name = "TenCoSo")
	private String tenCoSo;

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

	@Column(name = "DiaChiCoSo")
	private String diaChiCoSo;

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

	public String getMaCoSo() {
		return maCoSo;
	}

	public void setMaCoSo(String maCoSo) {
		this.maCoSo = maCoSo;
	}

	public String getTenCoSo() {
		return tenCoSo;
	}

	public void setTenCoSo(String tenCoSo) {
		this.tenCoSo = tenCoSo;
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

	public String getDiaChiCoSo() {
		return diaChiCoSo;
	}

	public void setDiaChiCoSo(String diaChiCoSo) {
		this.diaChiCoSo = diaChiCoSo;
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