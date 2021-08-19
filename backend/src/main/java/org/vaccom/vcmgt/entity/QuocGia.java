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

	@Column(name = "DanToc_Ma")
	private String danToc_Ma;

	@Column(name = "DanToc_Ten")
	private String danToc_Ten;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDanToc_Ma() {
		return danToc_Ma;
	}

	public void setDanToc_Ma(String danToc_Ma) {
		this.danToc_Ma = danToc_Ma;
	}

	public String getDanToc_Ten() {
		return danToc_Ten;
	}

	public void setDanToc_Ten(String danToc_Ten) {
		this.danToc_Ten = danToc_Ten;
	}

}