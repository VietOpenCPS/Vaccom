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
@Table(name = DBConstant._KHOADANGKY_TABLE_NAME)
public class KhoaDangKy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KhoaDangKy() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "NguoiDungID")
	private long nguoiDungID;

	@Column(name = "KhoaCongKhai")
	private String khoaCongKhai;

	@Column(name = "KhoaBiMat")
	private String khoaBiMat;

	@Column(name = "PhamVi")
	private String phamVi;

	@Column(name = "TrangThai")
	private int trangThai;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNguoiDungID() {
		return nguoiDungID;
	}

	public void setNguoiDungID(long nguoiDungID) {
		this.nguoiDungID = nguoiDungID;
	}

	public String getKhoaCongKhai() {
		return khoaCongKhai;
	}

	public void setKhoaCongKhai(String khoaCongKhai) {
		this.khoaCongKhai = khoaCongKhai;
	}

	public String getKhoaBiMat() {
		return khoaBiMat;
	}

	public void setKhoaBiMat(String khoaBiMat) {
		this.khoaBiMat = khoaBiMat;
	}

	public String getPhamVi() {
		return phamVi;
	}

	public void setPhamVi(String phamVi) {
		this.phamVi = phamVi;
	}

	public int getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

}