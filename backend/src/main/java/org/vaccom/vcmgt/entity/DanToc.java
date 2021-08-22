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
@Table(name = DBConstant._DANTOC_TABLE_NAME)
public class DanToc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DanToc() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "DanTocMa")
	private String danTocMa;

	@Column(name = "DanTocTen")
	private String danTocTen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDanTocMa() {
		return danTocMa;
	}

	public void setDanTocMa(String danTocMa) {
		this.danTocMa = danTocMa;
	}

	public String getDanTocTen() {
		return danTocTen;
	}

	public void setDanTocTen(String danTocTen) {
		this.danTocTen = danTocTen;
	}

}