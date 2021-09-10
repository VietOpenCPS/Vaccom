package org.vaccom.vcmgt.action.impl;

import java.util.List;

import com.liferay.portal.kernel.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.LichTiemChungAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.CoSoYTe;
import org.vaccom.vcmgt.entity.LichTiemChung;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.CoSoYTeService;
import org.vaccom.vcmgt.service.LichTiemChungService;
import org.vaccom.vcmgt.service.PhieuHenTiemService;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

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

	@Autowired
	private CoSoYTeService coSoYTeService;

	@Override
	public long countAll() {
		return lichTiemChungService.countAll();
	}

	@Override
	public long countCoSoYTeId(long id) {
		return lichTiemChungService.countCoSoYTeId(id);
	}

	@Override
	public LichTiemChung addLichTiemChung(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		long coSoYTeId = bodyData.has(EntityConstant.COSOYTE_ID) ? bodyData.get(EntityConstant.COSOYTE_ID).longValue()
				: 0;
		String maDot = bodyData.has(EntityConstant.MADOT) ? bodyData.get(EntityConstant.MADOT).textValue()
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

		String gioHenTiem = bodyData.has(EntityConstant.GIOHENTIEM) ? bodyData.get(EntityConstant.GIOHENTIEM).textValue()
				: StringPool.BLANK;

		long uyBanNhanDanID = bodyData.has(EntityConstant.UYBANNHANDAN_ID) ? bodyData.get(EntityConstant.UYBANNHANDAN_ID).longValue()
				: 0;

		String tenCoSo = null;

		if(coSoYTeId > 0){
			CoSoYTe coSoYTe = null;
			try {
				coSoYTe = coSoYTeService.findById(coSoYTeId);
			} catch (Exception ex){
				tenCoSo = bodyData.has(EntityConstant.TENCOSO)
						? bodyData.get(EntityConstant.TENCOSO).textValue()
						: StringPool.BLANK;
			}
			if(Validator.isNotNull(coSoYTe)){
				tenCoSo = coSoYTe.getTenCoSo();
			}


		} else {
			tenCoSo = bodyData.has(EntityConstant.TENCOSO)
					? bodyData.get(EntityConstant.TENCOSO).textValue()
					: StringPool.BLANK;
		}

		int tongSoMuiTiem = bodyData.has(EntityConstant.TONGSOMUITIEM)
				? bodyData.get(EntityConstant.TONGSOMUITIEM).intValue()
				: 2;

		String bacSiKham = bodyData.has(EntityConstant.BACSIKHAM) ? bodyData.get(EntityConstant.BACSIKHAM).textValue()
				: StringPool.BLANK;

		int soMuiMotCa = bodyData.has(EntityConstant.SOMUIMOTCA) ? bodyData.get(EntityConstant.SOMUIMOTCA).intValue()
				: 0;

		int soCaTiem = bodyData.has(EntityConstant.SOCATIEM) ? bodyData.get(EntityConstant.SOCATIEM).intValue() : 0;

		String soDienThoai = bodyData.has(EntityConstant.SODIENTHOAI)
				? bodyData.get(EntityConstant.SODIENTHOAI).textValue()
				: StringPool.BLANK;


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
		lichTiemChung.setMaDot(maDot);
		lichTiemChung.setTinhTrangLich(0);
		lichTiemChung.setTongSoMuiTiem(tongSoMuiTiem);
		lichTiemChung.setBacSiKham(bacSiKham);
		lichTiemChung.setSoDienThoai(soDienThoai);
		lichTiemChung.setSoMuiMotCa(soMuiMotCa);
		lichTiemChung.setSoCaTiem(soCaTiem);
		lichTiemChung.setMaQR(VaccomUtil.generateQRCode("ltc", 6));
		lichTiemChung.setUyBanNhanDanID(uyBanNhanDanID);
		lichTiemChung.setTenCoSo(tenCoSo);
		lichTiemChung.setGioHenTiem(gioHenTiem);

		return lichTiemChungService.updateLichTiemChung(lichTiemChung);
	}

	@Override
	public boolean deleteById(long id) throws Exception {
		LichTiemChung lichTiemChung = lichTiemChungService.findById(id);
		if (lichTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("lichtiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		long count = phieuHenTiemService.countByLichTiemChungId(id);

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
		String maDot = bodyData.has(EntityConstant.MADOT) ? bodyData.get(EntityConstant.MADOT).textValue()
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

		String bacSiKham = bodyData.has(EntityConstant.BACSIKHAM) ? bodyData.get(EntityConstant.BACSIKHAM).textValue()
				: StringPool.BLANK;

		int soMuiMotCa = bodyData.has(EntityConstant.SOMUIMOTCA) ? bodyData.get(EntityConstant.SOMUIMOTCA).intValue()
				: 0;

		int soCaTiem = bodyData.has(EntityConstant.SOCATIEM) ? bodyData.get(EntityConstant.SOCATIEM).intValue() : 0;

		String soDienThoai = bodyData.has(EntityConstant.SODIENTHOAI)
				? bodyData.get(EntityConstant.SODIENTHOAI).textValue()
				: StringPool.BLANK;

		String gioHenTiem = bodyData.has(EntityConstant.GIOHENTIEM) ? bodyData.get(EntityConstant.GIOHENTIEM).textValue()
				: StringPool.BLANK;

		long uyBanNhanDanID = bodyData.has(EntityConstant.UYBANNHANDAN_ID) ? bodyData.get(EntityConstant.UYBANNHANDAN_ID).longValue()
				: 0;

		String tenCoSo = null;

		if(coSoYTeId > 0){
			CoSoYTe coSoYTe = null;
			try {
				coSoYTe = coSoYTeService.findById(coSoYTeId);
			} catch (Exception ex){
				tenCoSo = bodyData.has(EntityConstant.TENCOSO)
						? bodyData.get(EntityConstant.TENCOSO).textValue()
						: StringPool.BLANK;
			}
			if(Validator.isNotNull(coSoYTe)){
				tenCoSo = coSoYTe.getTenCoSo();
			}


		} else {
			tenCoSo = bodyData.has(EntityConstant.TENCOSO)
					? bodyData.get(EntityConstant.TENCOSO).textValue()
					: StringPool.BLANK;
		}

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
		lichTiemChung.setMaDot(maDot);
		//lichTiemChung.setTinhTrangLich(tinhTrangLich);
		lichTiemChung.setTongSoMuiTiem(tongSoMuiTiem);
		lichTiemChung.setBacSiKham(bacSiKham);
		lichTiemChung.setSoDienThoai(soDienThoai);
		lichTiemChung.setSoMuiMotCa(soMuiMotCa);
		lichTiemChung.setSoCaTiem(soCaTiem);
		lichTiemChung.setGioHenTiem(gioHenTiem);
		lichTiemChung.setUyBanNhanDanID(uyBanNhanDanID);
		lichTiemChung.setTenCoSo(tenCoSo);

		return lichTiemChungService.updateLichTiemChung(lichTiemChung);
	}
	
	@Override
	public LichTiemChung dongLichTiemChung(long id) throws Exception {
		LichTiemChung lichTiemChung = lichTiemChungService.findById(id);

		if (lichTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("diabancoso.tendiaban.empty"),
					HttpStatus.NOT_FOUND.value());
		}
		
		lichTiemChung.setTinhTrangLich(VaccomUtil.DADONG);
		
		return lichTiemChungService.updateLichTiemChung(lichTiemChung);

	}

	@Override
	public List<LichTiemChung> findAll(int page, int size) {

		return lichTiemChungService.findAll(page, size);
	}

	@Override
	public List<LichTiemChung> findByCoSoYTeId(long id, int page, int size) {
		return lichTiemChungService.findByCoSoYTeId(id, page, size);
	}
}
