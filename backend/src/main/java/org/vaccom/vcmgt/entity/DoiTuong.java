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
@Table(name = DBConstant._DOITUONG_TABLE_NAME)
public class DoiTuong implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DoiTuong() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "DoiTuongMa")
	private String doiTuongMa;

	@Column(name = "DoiTuongMoTa")
	private String doiTuongMoTa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDoiTuongMa() {
		return doiTuongMa;
	}

	public void setDoiTuongMa(String doiTuongMa) {
		this.doiTuongMa = doiTuongMa;
	}

	public String getDoiTuongMoTa() {
		return doiTuongMoTa;
	}

	public void setDoiTuongMoTa(String doiTuongMoTa) {
		this.doiTuongMoTa = doiTuongMoTa;
	}

}