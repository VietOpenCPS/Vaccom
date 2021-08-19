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
@Table(name = DBConstant._KHOATRUYCAP_TABLE_NAME)
public class KhoaTruyCap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KhoaTruyCap() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "KhoaTruyCap")
	private String khoaTruyCap;

	@Column(name = "DoiTuong")
	private String doiTuong;

	@Column(name = "TrangThai")
	private int trangThai;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKhoaTruyCap() {
		return khoaTruyCap;
	}

	public void setKhoaTruyCap(String khoaTruyCap) {
		this.khoaTruyCap = khoaTruyCap;
	}

	public String getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(String doiTuong) {
		this.doiTuong = doiTuong;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

}