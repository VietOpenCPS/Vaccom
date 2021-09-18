package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.MuiTiemChungAction;
import org.vaccom.vcmgt.action.NguoiTiemChungAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.MuiTiemChung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.MuiTiemChungService;
import org.vaccom.vcmgt.util.MessageUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;

/**
 * @author vaccom
 *
 */
@Service
public class MuiTiemChungActionImpl implements MuiTiemChungAction {

	@Autowired
	private MuiTiemChungService muiTiemChungService;

	@Autowired
	private NguoiTiemChungAction nguoiTiemChungAction;

	@Override
	public long countByCoSoYTeId(long id) {
		return muiTiemChungService.countByCoSoYTeId(id);
	}

	@Override
	public long countByCongDan_ID(long id) {
		return muiTiemChungService.countByCongDan_ID(id);
	}

	@Override
	public MuiTiemChung addMuiTiemChung(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		String hoVaTen = bodyData.has(EntityConstant.HOVATEN) ? bodyData.get(EntityConstant.HOVATEN).textValue()
				: StringPool.BLANK;
		String ngaySinh = bodyData.has(EntityConstant.NGAYSINH) ? bodyData.get(EntityConstant.NGAYSINH).textValue()
				: StringPool.BLANK;
		String cmtcccd = bodyData.has(EntityConstant.CMTCCCD) ? bodyData.get(EntityConstant.CMTCCCD).textValue()
				: StringPool.BLANK;

		String coSoYTeMa = bodyData.has(EntityConstant.COSOYTE_MA) ? bodyData.get(EntityConstant.COSOYTE_MA).textValue()
				: StringPool.BLANK;
		String coSoYTeTen = bodyData.has(EntityConstant.COSOYTE_TEN)
				? bodyData.get(EntityConstant.COSOYTE_TEN).textValue()
				: StringPool.BLANK;
		int lanTiem = bodyData.has(EntityConstant.LANTIEM) ? bodyData.get(EntityConstant.LANTIEM).intValue() : 1;
		String ngayTiemChung = bodyData.has(EntityConstant.NGAYTIEMCHUNG)
				? bodyData.get(EntityConstant.NGAYTIEMCHUNG).textValue()
				: StringPool.BLANK;
		String gioTiemChung = bodyData.has(EntityConstant.GIOTIEMCHUNG)
				? bodyData.get(EntityConstant.GIOTIEMCHUNG).textValue()
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
		String soLoThuoc = bodyData.has(EntityConstant.SOLOTHUOC) ? bodyData.get(EntityConstant.SOLOTHUOC).textValue()
				: StringPool.BLANK;
		String hanSuDung = bodyData.has(EntityConstant.HANSUDUNG) ? bodyData.get(EntityConstant.HANSUDUNG).textValue()
				: StringPool.BLANK;

		long congDanId = bodyData.has(EntityConstant.CONGDAN_ID)
				? bodyData.get(EntityConstant.CONGDAN_ID).longValue()
				: 0;

		long coSoYTeId = bodyData.has(EntityConstant.COSOYTE_ID) ? bodyData.get(EntityConstant.COSOYTE_ID).longValue()
				: 0;
		// TODO Validate fields

		MuiTiemChung muiTiemChung = new MuiTiemChung();
		muiTiemChung.setCmtcccd(cmtcccd);
		muiTiemChung.setCoSoYTeMa(coSoYTeMa);
		muiTiemChung.setCoSoYTeTen(coSoYTeTen);
		muiTiemChung.setDiaDiemTiemChung(diaDiemTiemChung);
		muiTiemChung.setGioTiemChung(gioTiemChung);
		muiTiemChung.setHanSuDung(hanSuDung);
		muiTiemChung.setHoVaTen(hoVaTen);
		muiTiemChung.setLanTiem(lanTiem);
		muiTiemChung.setLoaiThuocTiem(loaiThuocTiem);
		muiTiemChung.setNgaySinh(ngaySinh);
		muiTiemChung.setCongDanID(congDanId);
		muiTiemChung.setNgayTiemChung(ngayTiemChung);
		muiTiemChung.setNoiSanXuat(noiSanXuat);
		muiTiemChung.setSoLoThuoc(soLoThuoc);
		muiTiemChung.setCoSoYTeId(coSoYTeId);

		if(congDanId > 0) {
			NguoiTiemChung nguoiTiemChungFind = nguoiTiemChungAction.findBycongDanID(congDanId);
			if(nguoiTiemChungFind != null) {
				muiTiemChung.setCongDanID(nguoiTiemChungFind.getCongDanID());
			}
		}

		return muiTiemChungService.updateMuiTiemChung(muiTiemChung);
	}

	@Override
	public boolean deleteById(long id) throws Exception {
		MuiTiemChung muiTiemChung = muiTiemChungService.findById(id);
		if (muiTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("muitiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		muiTiemChungService.deleteById(id);

		return true;
	}

	@Override
	public MuiTiemChung findById(long id) {

		return muiTiemChungService.findById(id);
	}

	@Override
	public MuiTiemChung updateMuiTiemChung(long id, String reqBody) throws Exception {

		MuiTiemChung muiTiemChung = muiTiemChungService.findById(id);

		if (muiTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("muitiemchung.tendiaban.empty"),
					HttpStatus.NOT_FOUND.value());
		}

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		String hoVaTen = bodyData.has(EntityConstant.HOVATEN) ? bodyData.get(EntityConstant.HOVATEN).textValue()
				: StringPool.BLANK;
		String ngaySinh = bodyData.has(EntityConstant.NGAYSINH) ? bodyData.get(EntityConstant.NGAYSINH).textValue()
				: StringPool.BLANK;
		String cmtcccd = bodyData.has(EntityConstant.CMTCCCD) ? bodyData.get(EntityConstant.CMTCCCD).textValue()
				: StringPool.BLANK;

		String coSoYTeMa = bodyData.has(EntityConstant.COSOYTE_MA) ? bodyData.get(EntityConstant.COSOYTE_MA).textValue()
				: StringPool.BLANK;
		String coSoYTeTen = bodyData.has(EntityConstant.COSOYTE_TEN)
				? bodyData.get(EntityConstant.COSOYTE_TEN).textValue()
				: StringPool.BLANK;
		int lanTiem = bodyData.has(EntityConstant.LANTIEM) ? bodyData.get(EntityConstant.LANTIEM).intValue() : 1;
		String ngayTiemChung = bodyData.has(EntityConstant.NGAYTIEMCHUNG)
				? bodyData.get(EntityConstant.NGAYTIEMCHUNG).textValue()
				: StringPool.BLANK;
		String gioTiemChung = bodyData.has(EntityConstant.GIOTIEMCHUNG)
				? bodyData.get(EntityConstant.GIOTIEMCHUNG).textValue()
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
		String soLoThuoc = bodyData.has(EntityConstant.SOLOTHUOC) ? bodyData.get(EntityConstant.SOLOTHUOC).textValue()
				: StringPool.BLANK;
		String hanSuDung = bodyData.has(EntityConstant.HANSUDUNG) ? bodyData.get(EntityConstant.HANSUDUNG).textValue()
				: StringPool.BLANK;

		// TODO Validate fields

		muiTiemChung.setCmtcccd(cmtcccd);
		muiTiemChung.setCoSoYTeMa(coSoYTeMa);
		muiTiemChung.setCoSoYTeTen(coSoYTeTen);
		muiTiemChung.setDiaDiemTiemChung(diaDiemTiemChung);
		muiTiemChung.setGioTiemChung(gioTiemChung);
		muiTiemChung.setHanSuDung(hanSuDung);
		muiTiemChung.setHoVaTen(hoVaTen);
		muiTiemChung.setLanTiem(lanTiem);
		muiTiemChung.setLoaiThuocTiem(loaiThuocTiem);
		muiTiemChung.setNgaySinh(ngaySinh);

		muiTiemChung.setNgayTiemChung(ngayTiemChung);
		muiTiemChung.setNoiSanXuat(noiSanXuat);
		muiTiemChung.setSoLoThuoc(soLoThuoc);

		return muiTiemChungService.updateMuiTiemChung(muiTiemChung);
	}

	@Override
	public List<MuiTiemChung> findByCmtcccd(String cmtcccd) {
		return muiTiemChungService.findByCmtcccd(cmtcccd);
	}

	@Override
	public List<MuiTiemChung> findByCoSoYTeId(long id, int page, int size) {
		return muiTiemChungService.findByCoSoYTeId(id, page, size);
	}

	@Override
	public List<MuiTiemChung> findByCongDan_ID(long id) {
		return muiTiemChungService.findByCongDan_ID(id);
	}
}
