package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.dto.NguoiTiemChungDto;
import org.vaccom.vcmgt.dto.PhieuHenTiemDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.PhieuHenTiem;

/**
 * @author vaccom
 *
 */
@Service
public interface PhieuHenTiemService {
	
	public long countByLichTiemChungId(long id);
	
	public long countPhieuHenTiem(Long lichTiemChungId, Long caTiemChungId, int tinhTrangXacNhan);
	
	public void deleteById(long id);
	
	public PhieuHenTiem updatePhieuHenTiem(PhieuHenTiem phieuHenTiem);
	
	public PhieuHenTiem findById(long id);
	
	public PhieuHenTiem findByMaQR(String maQR);
	
	public List<PhieuHenTiem> findAll();
	
	public List<PhieuHenTiem> findByLichTiemChungId(long id, int page, int size);
	
	public List<PhieuHenTiem> findByNguoiTiemChungId(long id);
	
	public List<PhieuHenTiem> searchPhieuHenTiem(Long lichTiemChungId, Long caTiemChungId, int tinhTrangXacNhan,
			Integer page, Integer size);

	public List<PhieuHenTiem> findByLichTiemChungID(long lichTiemChungId);

	public List<PhieuHenTiem> findByTinhTrangXacNhan_LichTiemChungID(int tinhTrangXacNhan, long LichTiemChungID);
	public List<PhieuHenTiem> findByKhacTinhTrangXacNhan_LichTiemChungID(int tinhTrangXacNhan, long LichTiemChungID);
public List<PhieuHenTiem> findByLichTiemChungId_TinhTrangXacNhan(long LichTiemChungID, int TinhTrangXacNhan);
	public PhieuHenTiem update(PhieuHenTiem phieuHenTiem);

}
