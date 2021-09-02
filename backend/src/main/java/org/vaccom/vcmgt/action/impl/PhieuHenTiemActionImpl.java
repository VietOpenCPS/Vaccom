package org.vaccom.vcmgt.action.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.PhieuHenTiemAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.CaTiemChung;
import org.vaccom.vcmgt.entity.LichTiemChung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.PhieuHenTiem;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.CaTiemChungService;
import org.vaccom.vcmgt.service.LichTiemChungService;
import org.vaccom.vcmgt.service.NguoiDungService;
import org.vaccom.vcmgt.service.NguoiTiemChungService;
import org.vaccom.vcmgt.service.PhieuHenTiemService;
import org.vaccom.vcmgt.util.DatetimeUtil;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author vaccom
 *
 */
@Service
public class PhieuHenTiemActionImpl implements PhieuHenTiemAction {

	@Autowired
	private PhieuHenTiemService phieuHenTiemService;

	@Autowired
	private LichTiemChungService lichTiemChungService;

	@Autowired
	private NguoiTiemChungService nguoiTiemChungService;

	@Autowired
	private CaTiemChungService caTiemChungService;

	@Override
	public long countByLichTiemChungId(long id) {
		return phieuHenTiemService.countByLichTiemChungId(id);
	}

	@Override
	public long countPhieuHenTiem(long lichTiemChungId, long caTiemChungId, int tinhTrangXacNhan) {
		return phieuHenTiemService.countPhieuHenTiem(lichTiemChungId, caTiemChungId, tinhTrangXacNhan);
	}

	@Override
	public PhieuHenTiem addPhieuHenTiem(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		long lichTiemChungId = bodyData.has(EntityConstant.LICHTIEMCHUNG_ID)
				? bodyData.get(EntityConstant.LICHTIEMCHUNG_ID).longValue()
				: 0;

		LichTiemChung lichTiemChung = lichTiemChungService.findById(lichTiemChungId);

		if (lichTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.lichtiem.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		long nguoiTiemChungId = bodyData.has(EntityConstant.NGUOITIEMCHUNG_ID)
				? bodyData.get(EntityConstant.NGUOITIEMCHUNG_ID).longValue()
				: 0;

		NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(nguoiTiemChungId);

		if (nguoiTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.nguoitiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		String ngayHenTiem = bodyData.has(EntityConstant.NGAYHENTIEM)
				? bodyData.get(EntityConstant.NGAYHENTIEM).textValue()
				: StringPool.BLANK;

		String gioHenTiem = bodyData.has(EntityConstant.GIOHENTIEM)
				? bodyData.get(EntityConstant.GIOHENTIEM).textValue()
				: StringPool.BLANK;

		int lanTiem = bodyData.has(EntityConstant.LANTIEM) ? bodyData.get(EntityConstant.LANTIEM).intValue() : 0;

		long caTiemChungId = bodyData.has(EntityConstant.CATIEMCHUNG_ID)
				? bodyData.get(EntityConstant.CATIEMCHUNG_ID).longValue()
				: 0;

		CaTiemChung caTiemChung = caTiemChungService.findById(caTiemChungId);

		if (caTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.catiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		if (Validator.isNull(ngayHenTiem)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.ngayhentiem.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(gioHenTiem)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.giohentiem.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		PhieuHenTiem phieuHenTiem = new PhieuHenTiem();
		phieuHenTiem.setGioHenTiem(gioHenTiem);
		phieuHenTiem.setLichTiemChungId(lichTiemChungId);
		phieuHenTiem.setCaTiemChungId(caTiemChungId);
		phieuHenTiem.setNgayHenTiem(ngayHenTiem);
		phieuHenTiem.setNguoiTiemChungId(nguoiTiemChungId);
		phieuHenTiem.setMaQR(VaccomUtil.generateQRCode("pht", 6));
		phieuHenTiem.setLanTiem(lanTiem);
		phieuHenTiem.setTinhTrangXacNhan(VaccomUtil.DUKIEN);
		phieuHenTiem.setNgayCheckin(StringPool.BLANK);
		phieuHenTiem.setThongTinCheckin(StringPool.BLANK);
		phieuHenTiem.setGioDuocTiem(StringPool.BLANK);
		phieuHenTiem.setTrieuChungSauTiem(StringPool.BLANK);
		phieuHenTiem.setDieuTriTrieuChung(StringPool.BLANK);

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
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		long lichTiemChungId = bodyData.has(EntityConstant.LICHTIEMCHUNG_ID)
				? bodyData.get(EntityConstant.LICHTIEMCHUNG_ID).longValue()
				: 0;

		LichTiemChung lichTiemChung = lichTiemChungService.findById(lichTiemChungId);

		if (lichTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.lichtiem.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		long nguoiTiemChungId = bodyData.has(EntityConstant.NGUOITIEMCHUNG_ID)
				? bodyData.get(EntityConstant.NGUOITIEMCHUNG_ID).longValue()
				: 0;

		NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(nguoiTiemChungId);

		if (nguoiTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.nguoitiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		String ngayHenTiem = bodyData.has(EntityConstant.NGAYHENTIEM)
				? bodyData.get(EntityConstant.NGAYHENTIEM).textValue()
				: StringPool.BLANK;

		String gioHenTiem = bodyData.has(EntityConstant.GIOHENTIEM)
				? bodyData.get(EntityConstant.GIOHENTIEM).textValue()
				: StringPool.BLANK;

		String gioDuocTiem = bodyData.has(EntityConstant.GIODUOCTIEM)
				? bodyData.get(EntityConstant.GIODUOCTIEM).textValue()
				: StringPool.BLANK;

		String trieuChungSauTiem = bodyData.has(EntityConstant.TRIEUCHUNGSAUTIEM)
				? bodyData.get(EntityConstant.TRIEUCHUNGSAUTIEM).textValue()
				: StringPool.BLANK;

		String dieuTriTrieuChung = bodyData.has(EntityConstant.DIEUTRITRIEUCHUNG)
				? bodyData.get(EntityConstant.DIEUTRITRIEUCHUNG).textValue()
				: StringPool.BLANK;

		int tinhTrangXacNhan = bodyData.has(EntityConstant.TINHTRANGXACNHAN)
				? bodyData.get(EntityConstant.TINHTRANGXACNHAN).intValue()
				: 0;

		int lanTiem = bodyData.has(EntityConstant.LANTIEM) ? bodyData.get(EntityConstant.LANTIEM).intValue() : 0;

		long caTiemChungId = bodyData.has(EntityConstant.CATIEMCHUNG_ID)
				? bodyData.get(EntityConstant.CATIEMCHUNG_ID).longValue()
				: 0;

		CaTiemChung caTiemChung = caTiemChungService.findById(caTiemChungId);

		if (caTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.catiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		if (Validator.isNull(ngayHenTiem)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.ngayhentiem.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(gioHenTiem)) {
			throw new ActionException(MessageUtil.getVNMessageText("phieuhentiem.giohentiem.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		phieuHenTiem.setGioHenTiem(gioHenTiem);
		phieuHenTiem.setLichTiemChungId(lichTiemChungId);
		phieuHenTiem.setCaTiemChungId(caTiemChungId);
		phieuHenTiem.setNgayHenTiem(ngayHenTiem);
		phieuHenTiem.setNguoiTiemChungId(nguoiTiemChungId);
		//phieuHenTiem.setTinhTrangXacNhan(tinhTrangXacNhan);
		phieuHenTiem.setLanTiem(lanTiem);
		// phieuHenTiem.setNgayCheckin(StringPool.BLANK);
		// phieuHenTiem.setThongTinCheckin(StringPool.BLANK);
		phieuHenTiem.setGioDuocTiem(gioDuocTiem);
		phieuHenTiem.setTrieuChungSauTiem(trieuChungSauTiem);
		phieuHenTiem.setDieuTriTrieuChung(dieuTriTrieuChung);

		return phieuHenTiemService.updatePhieuHenTiem(phieuHenTiem);
	}

	@Override
	public List<PhieuHenTiem> findByLichTiemChungId(long id, int page, int size) {

		return phieuHenTiemService.findByLichTiemChungId(id, page, size);
	}

	@Override
	public List<PhieuHenTiem> searchPhieuHenTiem(long lichTiemChungId, long caTiemChungId, int tinhTrangXacNhan,
			int page, int size) {

		return phieuHenTiemService.searchPhieuHenTiem(lichTiemChungId, caTiemChungId, tinhTrangXacNhan, page, size);
	}

	@Override
	public List<PhieuHenTiem> findByNguoiTiemChungId(long id) {
		return phieuHenTiemService.findByNguoiTiemChungId(id);
	}

	@Override
	public void updateTinhTrangXacNhan(String reqBody) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode bodyData = mapper.readTree(reqBody);

			int tinhTrangXacNhan = bodyData.has(EntityConstant.TINHTRANGXACNHAN)
					? bodyData.get(EntityConstant.TINHTRANGXACNHAN).intValue()
					: 0;
			String ids = bodyData.has(EntityConstant.IDS) ? bodyData.get(EntityConstant.IDS).textValue()
					: StringPool.BLANK;

			List<String> lstId = StringUtil.split(ids);

			if (lstId != null) {
				for (String strId : lstId) {
					long id = GetterUtil.getLong(strId, 0);

					if (id > 0) {
						PhieuHenTiem phieuHenTiem = phieuHenTiemService.findById(id);

						if (phieuHenTiem != null) {
							if (tinhTrangXacNhan == VaccomUtil.DACHECKIN) {
								phieuHenTiem.setNgayCheckin(
										DatetimeUtil.dateToString(new Date(), DatetimeUtil._VN_DATE_FORMAT));
							}
							phieuHenTiem.setTinhTrangXacNhan(tinhTrangXacNhan);
							phieuHenTiemService.updatePhieuHenTiem(phieuHenTiem);
						}
					}

				}
			}
		} catch (Exception e) {
			_log.error(e);
		}
	}

	@Override
	public boolean checkin(String qrcode, String reqBody) {
		
		PhieuHenTiem phieuHenTiem = phieuHenTiemService.findByMaQR(qrcode);
		if (phieuHenTiem == null) {
			return false;
		} else {
			phieuHenTiem.setTinhTrangXacNhan(VaccomUtil.DACHECKIN);
			phieuHenTiem.setThongTinCheckin(reqBody);
			phieuHenTiem.setNgayCheckin(DatetimeUtil.dateToString(new Date(), DatetimeUtil._VN_DATE_FORMAT));
			phieuHenTiemService.updatePhieuHenTiem(phieuHenTiem);
			return true;
		}

	}

	@Override
	public PhieuHenTiem findByMaQR(String maQr) {
		return phieuHenTiemService.findByMaQR(maQr);
	}

	private final Log _log = LogFactory.getLog(PhieuHenTiemActionImpl.class);
}
