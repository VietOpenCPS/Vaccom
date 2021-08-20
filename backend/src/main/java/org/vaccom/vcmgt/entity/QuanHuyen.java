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
@Table(name = DBConstant._QUANHUYEN_TABLE_NAME)
public class QuanHuyen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuanHuyen() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "QuanHuyenMa")
	private String quanHuyenMa;

	@Column(name = "QuanHuyenTen")
	private String quanHuyenTen;

	@Column(name = "TinhThanhID")
	private long tinhThanhID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getTinhThanhID() {
		return tinhThanhID;
	}

	public void setTinhThanhID(long tinhThanhID) {
		this.tinhThanhID = tinhThanhID;
	}

}