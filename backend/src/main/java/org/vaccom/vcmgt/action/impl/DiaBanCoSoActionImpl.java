package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.DiaBanCoSoAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.DiaBanCoSo;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.DiaBanCoSoService;
import org.vaccom.vcmgt.service.NguoiDungService;
import org.vaccom.vcmgt.service.NguoiTiemChungService;
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
public class DiaBanCoSoActionImpl implements DiaBanCoSoAction {

	@Autowired
	private DiaBanCoSoService diaBanCoSoService;

	@Autowired
	private NguoiDungService nguoiDungService;

	@Autowired
	private NguoiTiemChungService nguoiTiemChungService;

	@Override
	public DiaBanCoSo addDiaBanCoSo(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		String tenDiaBan = bodyData.has(EntityConstant.TENDIABAN) ? bodyData.get(EntityConstant.TENDIABAN).textValue()
				: StringPool.BLANK;
		String tinhThanhMa = bodyData.has(EntityConstant.TINHTHANH_MA)
				? bodyData.get(EntityConstant.TINHTHANH_MA).textValue()
				: StringPool.BLANK;
		String tinhThanhTen = bodyData.has(EntityConstant.TINHTHANH_TEN)
				? bodyData.get(EntityConstant.TINHTHANH_TEN).textValue()
				: StringPool.BLANK;
		String quanHuyenMa = bodyData.has(EntityConstant.QUANHUYEN_MA)
				? bodyData.get(EntityConstant.QUANHUYEN_MA).textValue()
				: StringPool.BLANK;
		String quanHuyenTen = bodyData.has(EntityConstant.QUANHUYEN_TEN)
				? bodyData.get(EntityConstant.QUANHUYEN_TEN).textValue()
				: StringPool.BLANK;
		String phuongXaMa = bodyData.has(EntityConstant.PHUONGXA_MA)
				? bodyData.get(EntityConstant.PHUONGXA_MA).textValue()
				: StringPool.BLANK;
		String phuongXaTen = bodyData.has(EntityConstant.PHUONGXA_TEN)
				? bodyData.get(EntityConstant.PHUONGXA_TEN).textValue()
				: StringPool.BLANK;

		long coSoYTeId = bodyData.has(EntityConstant.COSOYTE_ID) ? bodyData.get(EntityConstant.COSOYTE_ID).longValue()
				: 0;

		// TODO Validate fields

		if (Validator.isNull(tenDiaBan)) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		List<DiaBanCoSo> lstDiaBanCoSo = diaBanCoSoService.findAll();

		if (lstDiaBanCoSo != null) {
			for (DiaBanCoSo diaBanCoSo : lstDiaBanCoSo) {
				if (diaBanCoSo.getTenDiaBan().toLowerCase().replace(" ", "")
						.equals(tenDiaBan.toLowerCase().replace(" ", ""))) {
					throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.exist"),
							HttpStatus.METHOD_NOT_ALLOWED.value());

				}
			}
		}

		DiaBanCoSo diaBanCoSo = new DiaBanCoSo();
		diaBanCoSo.setPhuongXaMa(phuongXaMa);
		diaBanCoSo.setPhuongXaTen(phuongXaTen);
		diaBanCoSo.setQuanHuyenMa(quanHuyenMa);
		diaBanCoSo.setQuanHuyenTen(quanHuyenTen);
		diaBanCoSo.setTenDiaBan(tenDiaBan);
		diaBanCoSo.setTinhThanhMa(tinhThanhMa);
		diaBanCoSo.setTinhThanhTen(tinhThanhTen);
		diaBanCoSo.setCoSoYTeId(coSoYTeId);
		return diaBanCoSoService.updateDiaBanCoSo(diaBanCoSo);
	}

	@Override
	public boolean deleteById(long id) throws Exception {
		DiaBanCoSo diaBanCoSo = diaBanCoSoService.findById(id);
		if (diaBanCoSo == null) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		long count = nguoiDungService.countByDiaBanCoSoId(diaBanCoSo.getId());

		if (count > 0) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.inuse"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		count = nguoiTiemChungService.countByDiaBanCoSoId(diaBanCoSo.getId());

		if (count > 0) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.inuse"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		diaBanCoSoService.deleteById(id);

		return true;
	}

	@Override
	public DiaBanCoSo findById(long id) {

		return diaBanCoSoService.findById(id);
	}

	@Override
	public List<DiaBanCoSo> findAll() {

		return diaBanCoSoService.findAll();
	}

	@Override
	public DiaBanCoSo updateDiaBanCoSo(long id, String reqBody) throws Exception {

		DiaBanCoSo diaBanCoSo = diaBanCoSoService.findById(id);

		if (diaBanCoSo == null) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.empty"),
					HttpStatus.NOT_FOUND.value());
		}

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		String tenDiaBan = bodyData.has(EntityConstant.TENDIABAN) ? bodyData.get(EntityConstant.TENDIABAN).textValue()
				: StringPool.BLANK;
		String tinhThanhMa = bodyData.has(EntityConstant.TINHTHANH_MA)
				? bodyData.get(EntityConstant.TINHTHANH_MA).textValue()
				: StringPool.BLANK;
		String tinhThanhTen = bodyData.has(EntityConstant.TINHTHANH_TEN)
				? bodyData.get(EntityConstant.TINHTHANH_TEN).textValue()
				: StringPool.BLANK;
		String quanHuyenMa = bodyData.has(EntityConstant.QUANHUYEN_MA)
				? bodyData.get(EntityConstant.QUANHUYEN_MA).textValue()
				: StringPool.BLANK;
		String quanHuyenTen = bodyData.has(EntityConstant.QUANHUYEN_TEN)
				? bodyData.get(EntityConstant.QUANHUYEN_TEN).textValue()
				: StringPool.BLANK;
		String phuongXaMa = bodyData.has(EntityConstant.PHUONGXA_MA)
				? bodyData.get(EntityConstant.PHUONGXA_MA).textValue()
				: StringPool.BLANK;
		String phuongXaTen = bodyData.has(EntityConstant.PHUONGXA_TEN)
				? bodyData.get(EntityConstant.PHUONGXA_TEN).textValue()
				: StringPool.BLANK;

		long coSoYTeId = bodyData.has(EntityConstant.COSOYTE_ID) ? bodyData.get(EntityConstant.COSOYTE_ID).longValue()
				: 0;

		// TODO Validate fields

		if (Validator.isNull(tenDiaBan)) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		List<DiaBanCoSo> lstDiaBanCoSo = diaBanCoSoService.findAll();

		if (lstDiaBanCoSo != null) {
			for (DiaBanCoSo diaBanCoSoTmp : lstDiaBanCoSo) {
				if (diaBanCoSoTmp.getTenDiaBan().toLowerCase().replace(" ", "")
						.equals(tenDiaBan.toLowerCase().replace(" ", "")) && diaBanCoSoTmp.getId() != id) {
					throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.exist"),
							HttpStatus.METHOD_NOT_ALLOWED.value());

				}
			}
		}

		diaBanCoSo.setPhuongXaMa(phuongXaMa);
		diaBanCoSo.setPhuongXaTen(phuongXaTen);
		diaBanCoSo.setQuanHuyenMa(quanHuyenMa);
		diaBanCoSo.setQuanHuyenTen(quanHuyenTen);
		diaBanCoSo.setTenDiaBan(tenDiaBan);
		diaBanCoSo.setTinhThanhMa(tinhThanhMa);
		diaBanCoSo.setTinhThanhTen(tinhThanhTen);
		diaBanCoSo.setCoSoYTeId(coSoYTeId);
		return diaBanCoSoService.updateDiaBanCoSo(diaBanCoSo);
	}

	@Override
	public List<DiaBanCoSo> findByCoSoYTeId(long id) {
		return diaBanCoSoService.findByCoSoYTeId(id);
	}

}
