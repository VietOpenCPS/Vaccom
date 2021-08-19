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
@Table(name = DBConstant._QUOCGIA_TABLE_NAME)
public class QuocGia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuocGia() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "QuocGiaMa")
	private String quocGiaMa;

	@Column(name = "QuocGiaTen")
	private String quocGiaTen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuocGiaMa() {
		return quocGiaMa;
	}

	public void setQuocGiaMa(String quocGiaMa) {
		this.quocGiaMa = quocGiaMa;
	}

	public String getQuocGiaTen() {
		return quocGiaTen;
	}

	public void setQuocGiaTen(String quocGiaTen) {
		this.quocGiaTen = quocGiaTen;
	}

}