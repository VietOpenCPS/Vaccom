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
@Table(name = DBConstant._PHIEUTIEM_TABLE_NAME)
public class DiaBanCoSo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DiaBanCoSo() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "TenDiaBan")
	private String tenDiaBan;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenDiaBan() {
		return tenDiaBan;
	}

	public void setTenDiaBan(String tenDiaBan) {
		this.tenDiaBan = tenDiaBan;
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

}