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

	@Column(name = "TinhThanh_Ma")
	private String tinhThanh_Ma;

	@Column(name = "TinhThanh_Ten")
	private String tinhThanh_Ten;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTinhThanh_Ma() {
		return tinhThanh_Ma;
	}

	public void setTinhThanh_Ma(String tinhThanh_Ma) {
		this.tinhThanh_Ma = tinhThanh_Ma;
	}

	public String getTinhThanh_Ten() {
		return tinhThanh_Ten;
	}

	public void setTinhThanh_Ten(String tinhThanh_Ten) {
		this.tinhThanh_Ten = tinhThanh_Ten;
	}

}