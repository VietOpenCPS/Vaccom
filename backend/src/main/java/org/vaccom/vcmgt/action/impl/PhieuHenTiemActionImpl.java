package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.PhieuHenTiemAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.PhieuHenTiem;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.PhieuHenTiemService;
import org.vaccom.vcmgt.util.MessageUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author vaccom
 *
 */
@Service
public class PhieuHenTiemActionImpl implements PhieuHenTiemAction {

	@Autowired
	private PhieuHenTiemService phieuHenTiemService;


	@Override
	public PhieuHenTiem addPhieuHenTiem(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		long lichTiemChungId = bodyData.has(EntityConstant.LICHTIEMCHUNG_ID)
				? bodyData.get(EntityConstant.LICHTIEMCHUNG_ID).longValue()
				: 0;
		long nguoiTiemChungId = bodyData.has(EntityConstant.NGUOITIEMCHUNG_ID)
				? bodyData.get(EntityConstant.NGUOITIEMCHUNG_ID).longValue()
				: 0;
		String maPhieuHen = bodyData.has(EntityConstant.MAPHIEUHEN)
				? bodyData.get(EntityConstant.MAPHIEUHEN).textValue()
				: StringPool.BLANK;
		String ngayHenTiem = bodyData.has(EntityConstant.NGAYHENTIEM)
				? bodyData.get(EntityConstant.NGAYHENTIEM).textValue()
				: StringPool.BLANK;
		String gioHenTiem = bodyData.has(EntityConstant.GIOHENTIEM)
				? bodyData.get(EntityConstant.GIOHENTIEM).textValue()
				: StringPool.BLANK;
		int tinhTrangXacNhan = bodyData.has(EntityConstant.TINHTRANGXACNHAN)
				? bodyData.get(EntityConstant.TINHTRANGXACNHAN).intValue()
				: 0;

		// TODO Validate fields

		if (Validator.isNull(maPhieuHen)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.maphieuhen.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(ngayHenTiem)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.ngayhentiem.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(gioHenTiem)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.giohentiem.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		PhieuHenTiem phieuHenTiem = phieuHenTiemService.findByMaPhieuHen(maPhieuHen);

		if (phieuHenTiem != null) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.maphieuhen.exist"),
					HttpStatus.CONFLICT.value());
		}

		phieuHenTiem = new PhieuHenTiem();
		phieuHenTiem.setGioHenTiem(gioHenTiem);
		phieuHenTiem.setLichTiemChungId(lichTiemChungId);
		phieuHenTiem.setMaPhieuHen(maPhieuHen);
		phieuHenTiem.setNgayHenTiem(ngayHenTiem);
		phieuHenTiem.setNguoiTiemChungId(nguoiTiemChungId);
		phieuHenTiem.setTinhTrangXacNhan(tinhTrangXacNhan);

		return phieuHenTiemService.updatePhieuHenTiem(phieuHenTiem);
	}

	@Override
	public boolean deleteById(long id) throws Exception {
		PhieuHenTiem phieuHenTiem = phieuHenTiemService.findById(id);
		if (phieuHenTiem == null) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		phieuHenTiemService.deleteById(id);

		return true;
	}

	@Override
	public PhieuHenTiem findById(long id) {

		return phieuHenTiemService.findById(id);
	}

	@Override
	public List<PhieuHenTiem> findAll() {

		return phieuHenTiemService.findAll();
	}

	@Override
	public PhieuHenTiem updatePhieuHenTiem(long id, String reqBody) throws Exception {

		PhieuHenTiem phieuHenTiem = phieuHenTiemService.findById(id);

		if (phieuHenTiem == null) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.empty"),
					HttpStatus.NOT_FOUND.value());
		}

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		long lichTiemChungId = bodyData.has(EntityConstant.LICHTIEMCHUNG_ID)
				? bodyData.get(EntityConstant.LICHTIEMCHUNG_ID).longValue()
				: 0;
		long nguoiTiemChungId = bodyData.has(EntityConstant.NGUOITIEMCHUNG_ID)
				? bodyData.get(EntityConstant.NGUOITIEMCHUNG_ID).longValue()
				: 0;
		String maPhieuHen = bodyData.has(EntityConstant.MAPHIEUHEN)
				? bodyData.get(EntityConstant.MAPHIEUHEN).textValue()
				: StringPool.BLANK;
		String ngayHenTiem = bodyData.has(EntityConstant.NGAYHENTIEM)
				? bodyData.get(EntityConstant.NGAYHENTIEM).textValue()
				: StringPool.BLANK;
		String gioHenTiem = bodyData.has(EntityConstant.GIOHENTIEM)
				? bodyData.get(EntityConstant.GIOHENTIEM).textValue()
				: StringPool.BLANK;
		int tinhTrangXacNhan = bodyData.has(EntityConstant.TINHTRANGXACNHAN)
				? bodyData.get(EntityConstant.TINHTRANGXACNHAN).intValue()
				: 0;

		// TODO Validate fields

		if (Validator.isNull(maPhieuHen)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.maphieuhen.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(ngayHenTiem)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.ngayhentiem.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(gioHenTiem)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.giohentiem.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		PhieuHenTiem phieuHenTiemTemp = phieuHenTiemService.findByMaPhieuHen(maPhieuHen);

		if (phieuHenTiemTemp != null && phieuHenTiemTemp.getId() != id) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.maphieuhen.exist"),
					HttpStatus.CONFLICT.value());
		}

		phieuHenTiem.setGioHenTiem(gioHenTiem);
		phieuHenTiem.setLichTiemChungId(lichTiemChungId);
		phieuHenTiem.setMaPhieuHen(maPhieuHen);
		phieuHenTiem.setNgayHenTiem(ngayHenTiem);
		phieuHenTiem.setNguoiTiemChungId(nguoiTiemChungId);
		phieuHenTiem.setTinhTrangXacNhan(tinhTrangXacNhan);

		return phieuHenTiemService.updatePhieuHenTiem(phieuHenTiem);
	}

	@Override
	public List<PhieuHenTiem> findByLichTiemChungId(long id, int page, int size) {

		return phieuHenTiemService.findByLichTiemChungId(id, page, size);
	}

	@Override
	public List<PhieuHenTiem> findByNguoiTiemChungId(long id) {
		return phieuHenTiemService.findByNguoiTiemChungId(id);
	}

}
