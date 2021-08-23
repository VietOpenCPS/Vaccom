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
@Table(name = DBConstant._MUITIEMCHUNG_TABLE_NAME)
public class MuiTiemChung implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MuiTiemChung() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "NguoiTiemChungID")
	private long nguoiTiemChungId;

	@Column(name = "CoSoYTeID")
	private long coSoYTeID;

	@Column(name = "HoVaTen")
	private String hoVaTen;

	@Column(name = "NgaySinh")
	private String ngaySinh;

	@Column(name = "CMTCCCD")
	private String cmtcccd;

	@Column(name = "CoSoYTeMa")
	private String coSoYTeMa;

	@Column(name = "CoSoYTeTen")
	private String coSoYTeTen;

	@Column(name = "LanTiem")
	private int lanTiem;

	@Column(name = "NgayTiemChung")
	private String ngayTiemChung;

	@Column(name = "GioTiemChung")
	private String gioTiemChung;

	@Column(name = "DiaDiemTiemChung")
	private String diaDiemTiemChung;

	@Column(name = "LoaiThuocTiem")
	private String loaiThuocTiem;

	@Column(name = "NoiSanXuat")
	private String noiSanXuat;

	@Column(name = "SoLoThuoc")
	private String soLoThuoc;

	@Column(name = "HanSuDung")
	private String hanSuDung;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getCmtcccd() {
		return cmtcccd;
	}

	public void setCmtcccd(String cmtcccd) {
		this.cmtcccd = cmtcccd;
	}

	public String getCoSoYTeMa() {
		return coSoYTeMa;
	}

	public void setCoSoYTeMa(String coSoYTeMa) {
		this.coSoYTeMa = coSoYTeMa;
	}

	public String getCoSoYTeTen() {
		return coSoYTeTen;
	}

	public void setCoSoYTeTen(String coSoYTeTen) {
		this.coSoYTeTen = coSoYTeTen;
	}

	public int getLanTiem() {
		return lanTiem;
	}

	public void setLanTiem(int lanTiem) {
		this.lanTiem = lanTiem;
	}

	public String getNgayTiemChung() {
		return ngayTiemChung;
	}

	public void setNgayTiemChung(String ngayTiemChung) {
		this.ngayTiemChung = ngayTiemChung;
	}

	public String getGioTiemChung() {
		return gioTiemChung;
	}

	public void setGioTiemChung(String gioTiemChung) {
		this.gioTiemChung = gioTiemChung;
	}

	public String getDiaDiemTiemChung() {
		return diaDiemTiemChung;
	}

	public void setDiaDiemTiemChung(String diaDiemTiemChung) {
		this.diaDiemTiemChung = diaDiemTiemChung;
	}

	public String getLoaiThuocTiem() {
		return loaiThuocTiem;
	}

	public void setLoaiThuocTiem(String loaiThuocTiem) {
		this.loaiThuocTiem = loaiThuocTiem;
	}

	public String getNoiSanXuat() {
		return noiSanXuat;
	}

	public void setNoiSanXuat(String noiSanXuat) {
		this.noiSanXuat = noiSanXuat;
	}

	public String getSoLoThuoc() {
		return soLoThuoc;
	}

	public void setSoLoThuoc(String soLoThuoc) {
		this.soLoThuoc = soLoThuoc;
	}

	public String getHanSuDung() {
		return hanSuDung;
	}

	public void setHanSuDung(String hanSuDung) {
		this.hanSuDung = hanSuDung;
	}

	public long getNguoiTiemChungId() {
		return nguoiTiemChungId;
	}

	public void setNguoiTiemChungId(long nguoiTiemChungId) {
		this.nguoiTiemChungId = nguoiTiemChungId;
	}

	public long getCoSoYTeID() {
		return coSoYTeID;
	}

	public void setCoSoYTeID(long coSoYTeID) {
		this.coSoYTeID = coSoYTeID;
	}

}