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
@Table(name = DBConstant._TINHTHANH_TABLE_NAME)
public class TinhThanh implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TinhThanh() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "TinhThanhMa")
	private String tinhThanhMa;

	@Column(name = "TinhThanhTen")
	private String tinhThanhTen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}