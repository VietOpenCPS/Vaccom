package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.LichTiemChungAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.LichTiemChung;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.LichTiemChungService;
import org.vaccom.vcmgt.service.PhieuHenTiemService;
import org.vaccom.vcmgt.util.MessageUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;

/**
 * @author vaccom
 *
 */
@Service
public class LichTiemChungActionImpl implements LichTiemChungAction {

	@Autowired
	private LichTiemChungService lichTiemChungService;

	@Autowired
	private PhieuHenTiemService phieuHenTiemService;

	@Override
	public LichTiemChung addLichTiemChung(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		long coSoYTeId = bodyData.has(EntityConstant.COSOYTE_ID) ? bodyData.get(EntityConstant.COSOYTE_ID).longValue()
				: 0;
		String tenDot = bodyData.has(EntityConstant.TENDOT) ? bodyData.get(EntityConstant.TENDOT).textValue()
				: StringPool.BLANK;
		String ngayBatDau = bodyData.has(EntityConstant.NGAYBATDAU)
				? bodyData.get(EntityConstant.NGAYBATDAU).textValue()
				: StringPool.BLANK;
		String ngayKetThuc = bodyData.has(EntityConstant.NGAYKETTHUC)
				? bodyData.get(EntityConstant.NGAYKETTHUC).textValue()
				: StringPool.BLANK;
		String diaDiemTiemChung = bodyData.has(EntityConstant.DIADIEMTIEMCHUNG)
				? bodyData.get(EntityConstant.DIADIEMTIEMCHUNG).textValue()
				: StringPool.BLANK;
		String loaiThuocTiem = bodyData.has(EntityConstant.LOAITHUOCTIEM)
				? bodyData.get(EntityConstant.LOAITHUOCTIEM).textValue()
				: StringPool.BLANK;
		String noiSanXuat = bodyData.has(EntityConstant.NOISANXUAT)
				? bodyData.get(EntityConstant.NOISANXUAT).textValue()
				: StringPool.BLANK;
		String soLoThuoc = bodyData.has(EntityConstant.SOLOTHUOC) ? bodyData.get(EntityConstant.NOISANXUAT).textValue()
				: StringPool.BLANK;
		String hanSuDung = bodyData.has(EntityConstant.HANSUDUNG) ? bodyData.get(EntityConstant.HANSUDUNG).textValue()
				: StringPool.BLANK;
		int tongSoMuiTiem = bodyData.has(EntityConstant.TONGSOMUITIEM)
				? bodyData.get(EntityConstant.TONGSOMUITIEM).intValue()
				: 2;
		int tinhTrangLich = bodyData.has(EntityConstant.TINHTRANGLICH)
				? bodyData.get(EntityConstant.TINHTRANGLICH).intValue()
				: 0;

		// TODO Validate fields

	

		LichTiemChung lichTiemChung = new LichTiemChung();
		lichTiemChung.setCoSoYTeId(coSoYTeId);
		lichTiemChung.setDiaDiemTiemChung(diaDiemTiemChung);
		lichTiemChung.setHanSuDung(hanSuDung);
		lichTiemChung.setLoaiThuocTiem(loaiThuocTiem);
		lichTiemChung.setNgayBatDau(ngayBatDau);
		lichTiemChung.setNgayBatDau(ngayBatDau);
		lichTiemChung.setNgayKetThuc(ngayKetThuc);
		lichTiemChung.setNoiSanXuat(noiSanXuat);
		lichTiemChung.setSoLoThuoc(soLoThuoc);
		lichTiemChung.setTenDot(tenDot);
		lichTiemChung.setTinhTrangLich(tinhTrangLich);
		lichTiemChung.setTongSoMuiTiem(tongSoMuiTiem);

		return lichTiemChungService.updateLichTiemChung(lichTiemChung);
	}

	@Override
	public boolean deleteById(long id) throws Exception {
		LichTiemChung lichTiemChung = lichTiemChungService.findById(id);
		if (lichTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("lichtiemchung.not_found"), HttpStatus.NOT_FOUND.value());
		}

		int count = phieuHenTiemService.countByLichTiemChungId(id);

		if (count > 0) {
			throw new ActionException(MessageUtil.getVNMessageText("lichtiemchung.inuse"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		lichTiemChungService.deleteById(id);

		return true;
	}

	@Override
	public LichTiemChung findById(long id) {

		return lichTiemChungService.findById(id);
	}


	@Override
	public LichTiemChung updateLichTiemChung(long id, String reqBody) throws Exception {

		LichTiemChung lichTiemChung = lichTiemChungService.findById(id);

		if (lichTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.empty"),
					HttpStatus.NOT_FOUND.value());
		}

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		long coSoYTeId = bodyData.has(EntityConstant.COSOYTE_ID) ? bodyData.get(EntityConstant.COSOYTE_ID).longValue()
				: 0;
		String tenDot = bodyData.has(EntityConstant.TENDOT) ? bodyData.get(EntityConstant.TENDOT).textValue()
				: StringPool.BLANK;
		String ngayBatDau = bodyData.has(EntityConstant.NGAYBATDAU)
				? bodyData.get(EntityConstant.NGAYBATDAU).textValue()
				: StringPool.BLANK;
		String ngayKetThuc = bodyData.has(EntityConstant.NGAYKETTHUC)
				? bodyData.get(EntityConstant.NGAYKETTHUC).textValue()
				: StringPool.BLANK;
		String diaDiemTiemChung = bodyData.has(EntityConstant.DIADIEMTIEMCHUNG)
				? bodyData.get(EntityConstant.DIADIEMTIEMCHUNG).textValue()
				: StringPool.BLANK;
		String loaiThuocTiem = bodyData.has(EntityConstant.LOAITHUOCTIEM)
				? bodyData.get(EntityConstant.LOAITHUOCTIEM).textValue()
				: StringPool.BLANK;
		String noiSanXuat = bodyData.has(EntityConstant.NOISANXUAT)
				? bodyData.get(EntityConstant.NOISANXUAT).textValue()
				: StringPool.BLANK;
		String soLoThuoc = bodyData.has(EntityConstant.SOLOTHUOC) ? bodyData.get(EntityConstant.NOISANXUAT).textValue()
				: StringPool.BLANK;
		String hanSuDung = bodyData.has(EntityConstant.HANSUDUNG) ? bodyData.get(EntityConstant.HANSUDUNG).textValue()
				: StringPool.BLANK;
		int tongSoMuiTiem = bodyData.has(EntityConstant.TONGSOMUITIEM)
				? bodyData.get(EntityConstant.TONGSOMUITIEM).intValue()
				: 2;
		int tinhTrangLich = bodyData.has(EntityConstant.TINHTRANGLICH)
				? bodyData.get(EntityConstant.TINHTRANGLICH).intValue()
				: 0;

		// TODO Validate fields

		lichTiemChung.setCoSoYTeId(coSoYTeId);
		lichTiemChung.setDiaDiemTiemChung(diaDiemTiemChung);
		lichTiemChung.setHanSuDung(hanSuDung);
		lichTiemChung.setLoaiThuocTiem(loaiThuocTiem);
		lichTiemChung.setNgayBatDau(ngayBatDau);
		lichTiemChung.setNgayBatDau(ngayBatDau);
		lichTiemChung.setNgayKetThuc(ngayKetThuc);
		lichTiemChung.setNoiSanXuat(noiSanXuat);
		lichTiemChung.setSoLoThuoc(soLoThuoc);
		lichTiemChung.setTenDot(tenDot);
		lichTiemChung.setTinhTrangLich(tinhTrangLich);
		lichTiemChung.setTongSoMuiTiem(tongSoMuiTiem);

		return lichTiemChungService.updateLichTiemChung(lichTiemChung);
	}

	@Override
	public List<LichTiemChung> findAll(int page, int size) {

		return lichTiemChungService.findAll(page, size);
	}

}
