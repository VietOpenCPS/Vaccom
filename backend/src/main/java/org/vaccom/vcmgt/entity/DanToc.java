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

	@Column(name = "QuocGia_Ma")
	private String quocGia_Ma;

	@Column(name = "QuocGia_Ten")
	private String quocGia_Ten;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuocGia_Ma() {
		return quocGia_Ma;
	}

	public void setQuocGia_Ma(String quocGia_Ma) {
		this.quocGia_Ma = quocGia_Ma;
	}

	public String getQuocGia_Ten() {
		return quocGia_Ten;
	}

	public void setQuocGia_Ten(String quocGia_Ten) {
		this.quocGia_Ten = quocGia_Ten;
	}

}