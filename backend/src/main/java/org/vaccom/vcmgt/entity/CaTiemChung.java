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
@Table(name = DBConstant._CATIEMCHUNG_TABLE_NAME)
public class CaTiemChung implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CaTiemChung() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "LichTiemChungID")
	private long lichTiemChungId;

	@Column(name = "STT")
	private int stt;

	@Column(name = "NgayHenTiem")
	private String ngayHenTiem;

	@Column(name = "GioHenTien")
	private String gioHenTien;

	@Column(name = "SoMuiTiem")
	private int soMuiTiem;

	@Column(name = "DiaBanCoSoID")
	private long diaBanCoSoId;

	@Column(name = "SoPhieuHen")
	private int soPhieuHen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLichTiemChungId() {
		return lichTiemChungId;
	}

	public void setLichTiemChungId(long lichTiemChungId) {
		this.lichTiemChungId = lichTiemChungId;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public String getNgayHenTiem() {
		return ngayHenTiem;
	}

	public void setNgayHenTiem(String ngayHenTiem) {
		this.ngayHenTiem = ngayHenTiem;
	}

	public String getGioHenTien() {
		return gioHenTien;
	}

	public void setGioHenTien(String gioHenTien) {
		this.gioHenTien = gioHenTien;
	}

	public int getSoMuiTiem() {
		return soMuiTiem;
	}

	public void setSoMuiTiem(int soMuiTiem) {
		this.soMuiTiem = soMuiTiem;
	}

	public long getDiaBanCoSoId() {
		return diaBanCoSoId;
	}

	public void setDiaBanCoSoId(long diaBanCoSoId) {
		this.diaBanCoSoId = diaBanCoSoId;
	}

	public int getSoPhieuHen() {
		return soPhieuHen;
	}

	public void setSoPhieuHen(int soPhieuHen) {
		this.soPhieuHen = soPhieuHen;
	}

	
}