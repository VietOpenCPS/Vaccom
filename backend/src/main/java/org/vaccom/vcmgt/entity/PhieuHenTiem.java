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
@Table(name = DBConstant._PHIEUTIEM_TABLE_NAME)
public class PhieuHenTiem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhieuHenTiem() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "LichTiemChung_ID")
	private long lichTiemChung_ID;

	@Column(name = "NguoiTiemChung_ID")
	private long nguoiTiemChung_ID;

	@Column(name = "MaPhieuHen")
	private String maPhieuHen;

	@Column(name = "NgayHenTiem")
	private String ngayHenTiem;

	@Column(name = "GioHenTiem")
	private String gioHenTiem;

	@Column(name = "TinhTrangXacNhan")
	private int tinhTrangXacNhan;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLichTiemChung_ID() {
		return lichTiemChung_ID;
	}

	public void setLichTiemChung_ID(long lichTiemChung_ID) {
		this.lichTiemChung_ID = lichTiemChung_ID;
	}

	public long getNguoiTiemChung_ID() {
		return nguoiTiemChung_ID;
	}

	public void setNguoiTiemChung_ID(long nguoiTiemChung_ID) {
		this.nguoiTiemChung_ID = nguoiTiemChung_ID;
	}

	public String getMaPhieuHen() {
		return maPhieuHen;
	}

	public void setMaPhieuHen(String maPhieuHen) {
		this.maPhieuHen = maPhieuHen;
	}

	public String getNgayHenTiem() {
		return ngayHenTiem;
	}

	public void setNgayHenTiem(String ngayHenTiem) {
		this.ngayHenTiem = ngayHenTiem;
	}

	public String getGioHenTiem() {
		return gioHenTiem;
	}

	public void setGioHenTiem(String gioHenTiem) {
		this.gioHenTiem = gioHenTiem;
	}

	public int getTinhTrangXacNhan() {
		return tinhTrangXacNhan;
	}

	public void setTinhTrangXacNhan(int tinhTrangXacNhan) {
		this.tinhTrangXacNhan = tinhTrangXacNhan;
	}

}