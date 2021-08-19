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

	@Column(name = "QuanHuyen_Ma")
	private String quanHuyen_Ma;

	@Column(name = "QuanHuyen_Ten")
	private String quanHuyen_Ten;

	@Column(name = "TinhThanh_ID")
	private long tinhThanh_ID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getTinhThanh_ID() {
		return tinhThanh_ID;
	}

	public void setTinhThanh_ID(long tinhThanh_ID) {
		this.tinhThanh_ID = tinhThanh_ID;
	}

}