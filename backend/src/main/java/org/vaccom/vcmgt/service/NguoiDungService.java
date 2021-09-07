package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.entity.NguoiDung;


@Service
public interface NguoiDungService {
	
	public NguoiDung addNguoiDung(NguoiDung nguoiDung, KhoaDangKy khoaDangKy);
	
	public long countAll();
	
	public int countByDiaBanCoSoId(long id);
	
	public void deleteNguoiDung(long id) throws Exception;
	
	public void deleteNguoiDung(long nguoiDungId, long khoaDangKyId, long khoaTruyCapId) throws Exception;

	public NguoiDung findByID(long id);

	public NguoiDung findByTenDanNhap(String tenDangNhap);

	public List<NguoiDung> findAll(int page, int size);
	
	public NguoiDung updateNguoiDung(NguoiDung nguoiDung);
	
	public NguoiDung updateNguoiDung(NguoiDung nguoiDung, KhoaDangKy khoaDangKy);

	public NguoiDung findByNguoiTiemChungId(long nguoiTiemChungId);
}
