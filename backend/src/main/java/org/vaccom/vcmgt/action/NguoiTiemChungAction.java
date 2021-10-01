package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.dto.NguoiTiemChungDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.CongDan;
import org.vaccom.vcmgt.entity.NguoiTiemChung;

@Service
public interface NguoiTiemChungAction {
	public void addNguoiTiemChung(NguoiTiemChungDto nguoiTiemChungDto) throws Exception;

	public NguoiTiemChung addNguoiTiemChung(String reqBody) throws Exception;

	public NguoiTiemChung addNguoiTiemChung(String hoVaTen, String ngaySinh, int gioiTinh, String cmtcccd,
			int nhomDoiTuong, String donViCongTac, String soDienThoai, String email, String soTheBHYT,
			String diaChiNoiO, String tinhThanhMa, String tinhThanhTen, String quanHuyenMa, String quanHuyenTen,
			String phuongXaMa, String phuongXaTen, long diaBanCoSoId, String coSoYTeMa, String coSoYTeTen,
			String danTocMa, String quocTichMa, String tienSuDiUng, String cacBenhLyDangMac, String cacThuocDangDung,
			String ghiChu, String ngayDangKi, int tinhTrangDangKi);

	public boolean deleteNguoiTiemChung(long id) throws Exception;

	public void deleteNguoiTiemChung(String reqBody);

	public long countAll();
	
	public NguoiTiemChung findById(long id);

	public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size);

	public NguoiTiemChung updateNguoiTiemChung(long id, String reqBody) throws Exception;

	public List<NguoiTiemChung> searchNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki,
			String hovaten, Long diabancosoid, String cosoytema, Integer tinhtrangdangky, Integer kiemtratrung,
			Integer page, Integer size, String tinhthanhma, String tinhthanhten, String quanhuyenma
			,  String quanhuyenten, String phuongxama, String phuongxaten, Boolean isDatTieuChuan, String loaiThuocTiem, String diachinoio);

	public long countNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema, Integer tinhtrangdangky, Integer kiemtratrung, String tinhthanhma, String tinhthanhten, String quanhuyenma
			,  String quanhuyenten, String phuongxama, String phuongxaten, Boolean isDatTieuChuan, String loaiThuocTiem, String diachinoio);
	
	public int duyetDangKyMoi(String reqBody);
	
	public void huyDangKyChinhThuc(String reqBody);
	
	public void khoiPhucDangKy(String reqBody);

	public NguoiTiemChung updateTrangThaiDangKy(String reqBody);

	public NguoiTiemChung findByMaQR(String MaQR);

	public ResultSearchDto<NguoiTiemChung> search(NguoiTiemChungDto nguoiTiemChungDto, int page, int size);

	public ResultSearchDto<CongDan> searchDaTiem(NguoiTiemChungDto nguoiTiemChungDto, int page, int size);

	public NguoiTiemChung findByCMTCCCD(String cmtcccd);

	public NguoiTiemChung update(NguoiTiemChung nguoiTiemChung);

	public NguoiTiemChung findBycongDanID(long id);

}
