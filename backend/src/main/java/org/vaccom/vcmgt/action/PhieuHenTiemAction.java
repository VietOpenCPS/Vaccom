package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.dto.NguoiTiemChungDto;
import org.vaccom.vcmgt.dto.PhieuHenTiemDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.PhieuHenTiem;

/**
 * @author vaccom
 *
 */
@Service
public interface PhieuHenTiemAction {

	public PhieuHenTiem addPhieuHenTiem(String reqBody) throws Exception;

	public PhieuHenTiem addPhieuHenTiem(PhieuHenTiem phieuHenTiem);
	
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

	public List<PhieuHenTiem> findByLichTiemChungID(long lichTiemChungId);

	public List<PhieuHenTiem> findByTinhTrangXacNhan_LichTiemChungID(int tinhTrangXacNhan, long LichTiemChungID);
	public List<PhieuHenTiem> findByKhacTinhTrangXacNhan_LichTiemChungID(int tinhTrangXacNhan, long LichTiemChungID);
	public List<PhieuHenTiem> findByLichTiemChungId_TinhTrangXacNhan(long LichTiemChungID, int tinhTrangXacNhan);

	public PhieuHenTiem update(PhieuHenTiem phieuHenTiem);

	public List<PhieuHenTiem> findExist(long lichTiemChungId, long nguoiTiemChungId);
}

