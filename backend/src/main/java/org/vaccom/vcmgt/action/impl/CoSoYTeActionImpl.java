package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.CoSoYTeAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.CoSoYTe;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.CoSoYTeService;
import org.vaccom.vcmgt.service.DiaBanCoSoService;
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
public class CoSoYTeActionImpl implements CoSoYTeAction {

	@Autowired
	private CoSoYTeService coSoYTeService;

	@Autowired
	private DiaBanCoSoService diaBanCoSoService;

	@Override
	public CoSoYTe addCoSoYTe(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		String maCoSo = bodyData.has(EntityConstant.MACOSO) ? bodyData.get(EntityConstant.MACOSO).textValue()
				: StringPool.BLANK;
		String tenCoSo = bodyData.has(EntityConstant.TENCOSO) ? bodyData.get(EntityConstant.TENCOSO).textValue()
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
		String diaChiCoSo = bodyData.has(EntityConstant.DIACHICOSO)
				? bodyData.get(EntityConstant.DIACHICOSO).textValue()
				: StringPool.BLANK;
		String nguoiDaiDien = bodyData.has(EntityConstant.NGUOIDAIDIEN)
				? bodyData.get(EntityConstant.NGUOIDAIDIEN).textValue()
				: StringPool.BLANK;
		String soDienThoai = bodyData.has(EntityConstant.SODIENTHOAI)
				? bodyData.get(EntityConstant.SODIENTHOAI).textValue()
				: StringPool.BLANK;

		// TODO Validate fields

		if (Validator.isNull(tenCoSo)) {
			throw new ActionException(MessageUtil.getVNMessageText("cosoyte.tencoso.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(maCoSo)) {
			throw new ActionException(MessageUtil.getVNMessageText("cosoyte.macoso.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		CoSoYTe coSoYTe = coSoYTeService.findByMaCoSo(maCoSo);

		if (coSoYTe != null) {
			throw new ActionException(MessageUtil.getVNMessageText("cosoyte.macoso.exist"),
					HttpStatus.CONFLICT.value());
		}

		coSoYTe = new CoSoYTe();
		coSoYTe.setMaCoSo(maCoSo);
		coSoYTe.setPhuongXaMa(phuongXaMa);
		coSoYTe.setPhuongXaTen(phuongXaTen);
		coSoYTe.setQuanHuyenMa(quanHuyenMa);
		coSoYTe.setQuanHuyenTen(quanHuyenTen);
		coSoYTe.setTenCoSo(tenCoSo);
		coSoYTe.setTinhThanhMa(tinhThanhMa);
		coSoYTe.setTinhThanhTen(tinhThanhTen);
		coSoYTe.setNguoiDaiDien(nguoiDaiDien);
		coSoYTe.setSoDienThoai(soDienThoai);
		coSoYTe.setDiaChiCoSo(diaChiCoSo);

		return coSoYTeService.updateCoSoYTe(coSoYTe);
	}

	@Override
	public boolean deleteById(long id) throws Exception {
		CoSoYTe coSoYTe = coSoYTeService.findById(id);
		if (coSoYTe == null) {
			throw new ActionException(MessageUtil.getVNMessageText("cosoyte.not_found"), HttpStatus.NOT_FOUND.value());
		}

		int count = diaBanCoSoService.countByCoSoYTeId(coSoYTe.getId());

		if (count > 0) {
			throw new ActionException(MessageUtil.getVNMessageText("cosoyte.inuse"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		coSoYTeService.deleteById(id);

		return true;
	}

	@Override
	public CoSoYTe findById(long id) {

		return coSoYTeService.findById(id);
	}

	@Override
	public List<CoSoYTe> findAll() {

		return coSoYTeService.findAll();
	}

	@Override
	public CoSoYTe updateCoSoYTe(long id, String reqBody) throws Exception {

		CoSoYTe coSoYTe = coSoYTeService.findById(id);

		if (coSoYTe == null) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.empty"),
					HttpStatus.NOT_FOUND.value());
		}

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		String maCoSo = bodyData.has(EntityConstant.MACOSO) ? bodyData.get(EntityConstant.MACOSO).textValue()
				: StringPool.BLANK;
		String tenCoSo = bodyData.has(EntityConstant.TENCOSO) ? bodyData.get(EntityConstant.TENCOSO).textValue()
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
		String diaChiCoSo = bodyData.has(EntityConstant.DIACHICOSO)
				? bodyData.get(EntityConstant.DIACHICOSO).textValue()
				: StringPool.BLANK;
		String nguoiDaiDien = bodyData.has(EntityConstant.NGUOIDAIDIEN)
				? bodyData.get(EntityConstant.NGUOIDAIDIEN).textValue()
				: StringPool.BLANK;
		String soDienThoai = bodyData.has(EntityConstant.SODIENTHOAI)
				? bodyData.get(EntityConstant.SODIENTHOAI).textValue()
				: StringPool.BLANK;

		// TODO Validate fields

		if (Validator.isNull(tenCoSo)) {
			throw new ActionException(MessageUtil.getVNMessageText("cosoyte.tencoso.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(maCoSo)) {
			throw new ActionException(MessageUtil.getVNMessageText("cosoyte.macoso.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		CoSoYTe coSoYTeTemp = coSoYTeService.findByMaCoSo(maCoSo);

		if (coSoYTeTemp != null && coSoYTeTemp.getId() != id) {
			throw new ActionException(MessageUtil.getVNMessageText("cosoyte.macoso.exist"),
					HttpStatus.CONFLICT.value());
		}
		coSoYTe.setMaCoSo(maCoSo);
		coSoYTe.setPhuongXaMa(phuongXaMa);
		coSoYTe.setPhuongXaTen(phuongXaTen);
		coSoYTe.setQuanHuyenMa(quanHuyenMa);
		coSoYTe.setQuanHuyenTen(quanHuyenTen);
		coSoYTe.setTenCoSo(tenCoSo);
		coSoYTe.setTinhThanhMa(tinhThanhMa);
		coSoYTe.setTinhThanhTen(tinhThanhTen);
		coSoYTe.setNguoiDaiDien(nguoiDaiDien);
		coSoYTe.setSoDienThoai(soDienThoai);
		coSoYTe.setDiaChiCoSo(diaChiCoSo);

		return coSoYTeService.updateCoSoYTe(coSoYTe);
	}

	@Override
	public CoSoYTe findByMaCoSoYTe(String maCoSo) {
		return coSoYTeService.findByMaCoSo(maCoSo);
	}

	@Override
	public List<CoSoYTe> findAll(int page, int size) {

		return coSoYTeService.findAll(page, size);
	}

	@Override
	public CoSoYTe addCoSoYTe(String tinhThanhTen, String tinhThanhMa, String quanHuyenTen, String quanHuyenMa,
			String phuongXaTen, String phuongXaMa, String maCoSo, String tenCoSo, String diaChiCoSo,
			String nguoiDaiDien, String soDienThoai) throws Exception {
		if (Validator.isNotNull(maCoSo)) {
			CoSoYTe coSoYTe = coSoYTeService.findByMaCoSo(maCoSo);
			if (coSoYTe == null) {
				coSoYTe = new CoSoYTe();
				coSoYTe.setMaCoSo(maCoSo);
				coSoYTe.setPhuongXaMa(phuongXaMa);
				coSoYTe.setPhuongXaTen(phuongXaTen);
				coSoYTe.setQuanHuyenMa(quanHuyenMa);
				coSoYTe.setQuanHuyenTen(quanHuyenTen);
				coSoYTe.setTenCoSo(tenCoSo);
				coSoYTe.setTinhThanhMa(tinhThanhMa);
				coSoYTe.setTinhThanhTen(tinhThanhTen);
				coSoYTe.setNguoiDaiDien(nguoiDaiDien);
				coSoYTe.setSoDienThoai(soDienThoai);
				coSoYTe.setDiaChiCoSo(diaChiCoSo);

				return coSoYTeService.updateCoSoYTe(coSoYTe);
			}
		}

		return null;
	}

}
