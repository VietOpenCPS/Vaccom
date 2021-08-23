package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.NguoiTiemChung;

@Service
public interface NguoiTiemChungService {

	public long countByDiaBanCoSoId(long id);

	public long countAll();

	public NguoiTiemChung findById(long id);

	public NguoiTiemChung findByCmtcccd(String cmtcccd);

	public void deleteNguoiTiemChung(long id) throws Exception;

	public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung);

	public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size);
	
	public long countNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema);

	public List<NguoiTiemChung> searchNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema, Integer page, Integer size);
}
