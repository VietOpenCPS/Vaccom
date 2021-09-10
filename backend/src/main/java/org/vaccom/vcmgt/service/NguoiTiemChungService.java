package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.dto.NguoiTiemChungDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.NguoiTiemChung;

@Service
public interface NguoiTiemChungService {

	public long countByCmtcccd(String cmtcccd);
	
	public long countByCmtcccd(String cmtcccd, int tinhTrangDangKy);

	public long countByDiaBanCoSoId(long id);

	public long countAll();

	public NguoiTiemChung findById(long id);

	public NguoiTiemChung findByMaQR(String MaQR);

	public List<NguoiTiemChung> findByCmtcccd(String cmtcccd);

	public void deleteNguoiTiemChung(long id) throws Exception;

	public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung);
	
	public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung, boolean createAccount);

	public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size);

	public long countNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung, String tinhthanhma, String tinhthanhten, String quanhuyenma
			,  String quanhuyenten, String phuongxama, String phuongxaten);

	public List<NguoiTiemChung> searchNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki,
			String hovaten, Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung, Integer page, Integer size, String tinhthanhma, String tinhthanhten, String quanhuyenma
			,  String quanhuyenten, String phuongxama, String phuongxaten);

	public ResultSearchDto<NguoiTiemChung> search(NguoiTiemChungDto nguoiTiemChungDto, int page, int size);
	public ResultSearchDto<NguoiTiemChung> searchOr(NguoiTiemChungDto nguoiTiemChungDto, int page, int size);

}
