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
@Table(name = DBConstant._KHOA_TABLE_NAME)
public class Khoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Khoa() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "NguoiDung_ID")
	private long nguoiDung_ID;

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

	public long getNguoiDung_ID() {
		return nguoiDung_ID;
	}

	public void setNguoiDung_ID(long nguoiDung_ID) {
		this.nguoiDung_ID = nguoiDung_ID;
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