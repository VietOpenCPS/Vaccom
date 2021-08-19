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
@Table(name = DBConstant._PHUONGXA_TABLE_NAME)
public class PhuongXa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhuongXa() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "PhuongXaMa")
	private String phuongXaMa;

	@Column(name = "PhuongXaTen")
	private String phuongXaTen;

	@Column(name = "QuanHuyenID")
	private long quanHuyenID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getQuanHuyenID() {
		return quanHuyenID;
	}

	public void setQuanHuyenID(long quanHuyenID) {
		this.quanHuyenID = quanHuyenID;
	}

}