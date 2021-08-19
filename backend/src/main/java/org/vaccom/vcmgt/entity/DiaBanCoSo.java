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

}