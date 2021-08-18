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

	@Column(name = "PhuongXa_Ma")
	private String phuongXa_Ma;

	@Column(name = "PhuongXa_Ten")
	private String phuongXa_Ten;

	@Column(name = "QuanHuyen_ID")
	private long quanHuyen_ID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getQuanHuyen_ID() {
		return quanHuyen_ID;
	}

	public void setQuanHuyen_ID(long quanHuyen_ID) {
		this.quanHuyen_ID = quanHuyen_ID;
	}

}