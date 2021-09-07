package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.PhieuHenTiem;

/**
 * @author vaccom
 *
 */
@Service
public interface PhieuHenTiemAction {

	public PhieuHenTiem addPhieuHenTiem(String reqBody) throws Exception;
	
	public long countPhieuHenTiem(long lichTiemChungId, long caTiemChungId, int tinhTrangXacNhan);

	public boolean deleteById(long id) throws Exception;

	public PhieuHenTiem findById(long id);
	
	public long countByLichTiemChungId(long id);

	public List<PhieuHenTiem> findByLichTiemChungId(long id, int page, int size);

	public List<PhieuHenTiem> findByNguoiTiemChungId(long id);

	public List<PhieuHenTiem> findAll();
	
	public List<PhieuHenTiem> searchPhieuHenTiem(long lichTiemChungId, long caTiemChungId, int tinhTrangXacNhan,
			int page, int size);

	public PhieuHenTiem updatePhieuHenTiem(long id, String reqBody) throws Exception;
	
	public void updateTinhTrangXacNhan(String reqBody);
	
	public boolean checkin(String qrcode, String reqBody);

	public PhieuHenTiem findByMaQR(String maQr);

}
