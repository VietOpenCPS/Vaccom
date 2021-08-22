package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.NguoiTiemChung;

@Service
public interface NguoiTiemChungService {
	
	public int countByDiaBanCoSoId(long id);
	
	public NguoiTiemChung findById(long id);
	
	public NguoiTiemChung findByCmtcccd(String cmtcccd);
	
	public void deleteNguoiTiemChung(long id) throws Exception;
	
	public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung);
	
	public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size);
}
