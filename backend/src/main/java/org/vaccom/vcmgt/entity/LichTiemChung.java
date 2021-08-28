package org.vaccom.vcmgt.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.ToString;
import org.vaccom.vcmgt.constant.DBConstant;

@Entity
@Table(name = DBConstant._LICTIEMCHUNG_TABLE_NAME)
@ToString
public class LichTiemChung implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public LichTiemChung() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "CoSoYTeID")
	private long coSoYTeId;

	@Column(name = "MaQR")
	private String maQR;

	@Column(name = "MaDot")
	private String maDot;

	@Column(name = "NgayBatDau")
	private String ngayBatDau;

	@Column(name = "NgayKetThuc")
	private String ngayKetThuc;

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

	@Column(name = "TongSoMuiTiem")
	private int tongSoMuiTiem;

	@Column(name = "TinhTrangLich")
	private int tinhTrangLich;

	@Column(name = "SoCaTiem")
	private int soCaTiem;

	@Column(name = "SoMuiMotCa")
	private int soMuiMotCa;

	@Column(name = "BacSiKham")
	private String bacSiKham;

	@Column(name = "SoDienThoai")
	private String soDienThoai;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCoSoYTeId() {
		return coSoYTeId;
	}

	public void setCoSoYTeId(long coSoYTeId) {
		this.coSoYTeId = coSoYTeId;
	}

	public String getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(String ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public String getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(String ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
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

	public int getTongSoMuiTiem() {
		return tongSoMuiTiem;
	}

	public void setTongSoMuiTiem(int tongSoMuiTiem) {
		this.tongSoMuiTiem = tongSoMuiTiem;
	}

	public int getTinhTrangLich() {
		return tinhTrangLich;
	}

	public void setTinhTrangLich(int tinhTrangLich) {
		this.tinhTrangLich = tinhTrangLich;
	}

	public String getMaQR() {
		return maQR;
	}

	public void setMaQR(String maQR) {
		this.maQR = maQR;
	}

	public String getMaDot() {
		return maDot;
	}

	public void setMaDot(String maDot) {
		this.maDot = maDot;
	}

	public int getSoCaTiem() {
		return soCaTiem;
	}

	public void setSoCaTiem(int soCaTiem) {
		this.soCaTiem = soCaTiem;
	}

	public int getSoMuiMotCa() {
		return soMuiMotCa;
	}

	public void setSoMuiMotCa(int soMuiMotCa) {
		this.soMuiMotCa = soMuiMotCa;
	}

	public String getBacSiKham() {
		return bacSiKham;
	}

	public void setBacSiKham(String bacSiKham) {
		this.bacSiKham = bacSiKham;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public boolean isValidPlan() {
		// TODO: Implement to return a valid plan here
		return true;
	}
}