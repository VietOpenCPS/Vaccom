package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.CaTiemChungAction;
import org.vaccom.vcmgt.action.LichTiemChungAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.CaTiemChung;
import org.vaccom.vcmgt.entity.LichTiemChung;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.CaTiemChungService;
import org.vaccom.vcmgt.util.MessageUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;

/**
 * @author vaccom
 *
 */
@Service
public class CaTiemChungActionImpl implements CaTiemChungAction {

	@Autowired
	private CaTiemChungService caTiemChungService;

	@Autowired
	private LichTiemChungAction lichTiemChungAction;

	@Override
	public CaTiemChung addCaTiemChung(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode bodyData = mapper.readTree(reqBody);

		long lichTiemChungId = bodyData.has(EntityConstant.LICHTIEMCHUNG_ID)
				? bodyData.get(EntityConstant.LICHTIEMCHUNG_ID).longValue()
				: 0;

		LichTiemChung lichTiemChung = lichTiemChungAction.findById(lichTiemChungId);

		if (lichTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("catiemchung.lichtiem.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		int stt = bodyData.has(EntityConstant.STT) ? bodyData.get(EntityConstant.STT).intValue() : 1;

		String ngayHenTiem = bodyData.has(EntityConstant.NGAYHENTIEM)
				? bodyData.get(EntityConstant.NGAYHENTIEM).textValue()
				: StringPool.BLANK;

		String gioHenTiem = bodyData.has(EntityConstant.GIOHENTIEM)
				? bodyData.get(EntityConstant.GIOHENTIEM).textValue()
				: StringPool.BLANK;
		int soMuiTiem = bodyData.has(EntityConstant.SOMUITIEM) ? bodyData.get(EntityConstant.SOMUITIEM).intValue() : 1;

		long diaBanCoSoId = bodyData.has(EntityConstant.DIABANCOSO_ID)
				? bodyData.get(EntityConstant.DIABANCOSO_ID).longValue()
				: 0;

		int soPhieuHen = bodyData.has(EntityConstant.SOPHIEUHEN) ? bodyData.get(EntityConstant.SOPHIEUHEN).intValue()
				: 1;

		CaTiemChung caTiemChung = new CaTiemChung();
		caTiemChung.setDiaBanCoSoId(diaBanCoSoId);
		caTiemChung.setGioHenTiem(gioHenTiem);
		caTiemChung.setLichTiemChungId(lichTiemChungId);
		caTiemChung.setNgayHenTiem(ngayHenTiem);
		caTiemChung.setSoMuiTiem(soMuiTiem);
		// caTiemChung.setSoPhieuHen(soPhieuHen);
		caTiemChung.setStt(stt);

		return caTiemChungService.updateCaTiemChung(caTiemChung);
	}

	@Override
	public boolean deleteById(long id) throws Exception {
		CaTiemChung caTiemChung = caTiemChungService.findById(id);
		if (caTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("catiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		caTiemChungService.deleteById(id);

		return true;
	}

	@Override
	public CaTiemChung findById(long id) {
		return caTiemChungService.findById(id);
	}

	@Override
	public long countCaTiemChung(Long lichTiemChungId, Long diaBanCoSoId) {

		return caTiemChungService.countCaTiemChung(lichTiemChungId, diaBanCoSoId);
	}

	@Override
	public List<CaTiemChung> searchCaTiemChung(Long lichTiemChungId, Long diaBanCoSoId, int page, int size) {
		return caTiemChungService.searchCaTiemChung(lichTiemChungId, diaBanCoSoId, page, size);
	}

	@Override
	public CaTiemChung updateCaTiemChung(long id, String reqBody) throws Exception {

		CaTiemChung caTiemChung = caTiemChungService.findById(id);
		if (caTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("catiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		long lichTiemChungId = bodyData.has(EntityConstant.LICHTIEMCHUNG_ID)
				? bodyData.get(EntityConstant.LICHTIEMCHUNG_ID).longValue()
				: 0;

		LichTiemChung lichTiemChung = lichTiemChungAction.findById(lichTiemChungId);

		if (lichTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("catiemchung.lichtiem.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		int stt = bodyData.has(EntityConstant.STT) ? bodyData.get(EntityConstant.STT).intValue() : 1;

		String ngayHenTiem = bodyData.has(EntityConstant.NGAYHENTIEM)
				? bodyData.get(EntityConstant.NGAYHENTIEM).textValue()
				: StringPool.BLANK;

		String gioHenTiem = bodyData.has(EntityConstant.GIOHENTIEM)
				? bodyData.get(EntityConstant.GIOHENTIEM).textValue()
				: StringPool.BLANK;
		int soMuiTiem = bodyData.has(EntityConstant.SOMUITIEM) ? bodyData.get(EntityConstant.SOMUITIEM).intValue() : 1;

		long diaBanCoSoId = bodyData.has(EntityConstant.DIABANCOSO_ID)
				? bodyData.get(EntityConstant.DIABANCOSO_ID).longValue()
				: 0;

		int soPhieuHen = bodyData.has(EntityConstant.SOPHIEUHEN) ? bodyData.get(EntityConstant.SOPHIEUHEN).intValue()
				: 1;

		caTiemChung.setDiaBanCoSoId(diaBanCoSoId);
		caTiemChung.setGioHenTiem(gioHenTiem);
		caTiemChung.setLichTiemChungId(lichTiemChungId);
		caTiemChung.setNgayHenTiem(ngayHenTiem);
		caTiemChung.setSoMuiTiem(soMuiTiem);
		// caTiemChung.setSoPhieuHen(soPhieuHen);
		caTiemChung.setStt(stt);

		return caTiemChungService.updateCaTiemChung(caTiemChung);
	}

}
