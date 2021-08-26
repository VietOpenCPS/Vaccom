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

	@Column(name = "LichTiemChungID")
	private long lichTiemChungId;

	@Column(name = "NguoiTiemChungID")
	private long nguoiTiemChungId;

	@Column(name = "NgayHenTiem")
	private String ngayHenTiem;

	@Column(name = "GioHenTiem")
	private String gioHenTiem;

	@Column(name = "MaQR")
	private String maQR;

	@Column(name = "CaTiemChungID")
	private long caTiemChungId;

	@Column(name = "LanTiem")
	private int lanTiem;

	@Column(name = "TinhTrangXacNhan")
	private int tinhTrangXacNhan;

	@Column(name = "NgayCheckin")
	private String ngayCheckin;

	@Column(name = "ThongTinCheckin")
	private String ThongTinCheckin;

	@Column(name = "GioDuocTiem")
	private String GioDuocTiem;

	@Column(name = "TrieuChungSauTiem")
	private String trieuChungSauTiem;

	@Column(name = "DieuTriTrieuChung")
	private String dieuTriTrieuChung;

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

	public long getNguoiTiemChungId() {
		return nguoiTiemChungId;
	}

	public void setNguoiTiemChungId(long nguoiTiemChungId) {
		this.nguoiTiemChungId = nguoiTiemChungId;
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

	public String getMaQR() {
		return maQR;
	}

	public void setMaQR(String maQR) {
		this.maQR = maQR;
	}

	public long getCaTiemChungId() {
		return caTiemChungId;
	}

	public void setCaTiemChungId(long caTiemChungId) {
		this.caTiemChungId = caTiemChungId;
	}

	public int getLanTiem() {
		return lanTiem;
	}

	public void setLanTiem(int lanTiem) {
		this.lanTiem = lanTiem;
	}

	public String getNgayCheckin() {
		return ngayCheckin;
	}

	public void setNgayCheckin(String ngayCheckin) {
		this.ngayCheckin = ngayCheckin;
	}

	public String getThongTinCheckin() {
		return ThongTinCheckin;
	}

	public void setThongTinCheckin(String thongTinCheckin) {
		ThongTinCheckin = thongTinCheckin;
	}

	public String getGioDuocTiem() {
		return GioDuocTiem;
	}

	public void setGioDuocTiem(String gioDuocTiem) {
		GioDuocTiem = gioDuocTiem;
	}

	public String getTrieuChungSauTiem() {
		return trieuChungSauTiem;
	}

	public void setTrieuChungSauTiem(String trieuChungSauTiem) {
		this.trieuChungSauTiem = trieuChungSauTiem;
	}

	public String getDieuTriTrieuChung() {
		return dieuTriTrieuChung;
	}

	public void setDieuTriTrieuChung(String dieuTriTrieuChung) {
		this.dieuTriTrieuChung = dieuTriTrieuChung;
	}

}