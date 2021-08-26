package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.PhieuHenTiem;

/**
 * @author vaccom
 *
 */
@Service
public interface PhieuHenTiemService {
	
	public long countByLichTiemChungId(long id);
	
	public void deleteById(long id);
	
	public PhieuHenTiem updatePhieuHenTiem(PhieuHenTiem phieuHenTiem);
	
	public PhieuHenTiem findById(long id);
	
	public PhieuHenTiem findByMaQR(String maQR);
	
	public List<PhieuHenTiem> findAll();
	
	public List<PhieuHenTiem> findByLichTiemChungId(long id, int page, int size);
	
	public List<PhieuHenTiem> findByNguoiTiemChungId(long id);
}
