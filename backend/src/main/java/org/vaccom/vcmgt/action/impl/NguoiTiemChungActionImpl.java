package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.NguoiTiemChungAction;

import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.NguoiTiemChung;

import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.NguoiTiemChungService;
import org.vaccom.vcmgt.util.MessageUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

@Service
public class NguoiTiemChungActionImpl implements NguoiTiemChungAction {

	@Autowired
	NguoiTiemChungService nguoiTiemChungService;
	
	@Override
	public long countAll() {

		return nguoiTiemChungService.countAll();
	}

	@Override
	public NguoiTiemChung addNguoiTiemChung(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		String hoVaTen = bodyData.has(EntityConstant.HOVATEN) ? bodyData.get(EntityConstant.HOVATEN).textValue()
				: StringPool.BLANK;
		String ngaySinh = bodyData.has(EntityConstant.NGAYSINH) ? bodyData.get(EntityConstant.NGAYSINH).textValue()
				: StringPool.BLANK;

		int gioiTinh = bodyData.has(EntityConstant.GIOITINH) ? bodyData.get(EntityConstant.GIOITINH).intValue() : 2;

		String cmtcccd = bodyData.has(EntityConstant.CMTCCCD) ? bodyData.get(EntityConstant.CMTCCCD).textValue()
				: StringPool.BLANK;

		String ngheNghiep = bodyData.has(EntityConstant.NGHENGHIEP)
				? bodyData.get(EntityConstant.NGHENGHIEP).textValue()
				: StringPool.BLANK;
		int nhomDoiTuong = bodyData.has(EntityConstant.NHOMDOITUONG)
				? bodyData.get(EntityConstant.NHOMDOITUONG).intValue()
				: 1;
		String donViCongTac = bodyData.has(EntityConstant.DONVICONGTAC)
				? bodyData.get(EntityConstant.DONVICONGTAC).textValue()
				: StringPool.BLANK;
		String soDienThoai = bodyData.has(EntityConstant.SODIENTHOAI)
				? bodyData.get(EntityConstant.SODIENTHOAI).textValue()
				: StringPool.BLANK;
		String email = bodyData.has(EntityConstant.EMAIL) ? bodyData.get(EntityConstant.EMAIL).textValue()
				: StringPool.BLANK;
		String maSoBHXH = bodyData.has(EntityConstant.MASOBHXH) ? bodyData.get(EntityConstant.MASOBHXH).textValue()
				: StringPool.BLANK;
		String soTheBHYT = bodyData.has(EntityConstant.SOTHEBHYT) ? bodyData.get(EntityConstant.SOTHEBHYT).textValue()
				: StringPool.BLANK;
		String diaChiNoiO = bodyData.has(EntityConstant.DIACHINOIO)
				? bodyData.get(EntityConstant.DIACHINOIO).textValue()
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
		long diaBanCoSoId = bodyData.has(EntityConstant.DIABANCOSO_ID)
				? bodyData.get(EntityConstant.DIABANCOSO_ID).longValue()
				: 0;
		String coSoYTeMa = bodyData.has(EntityConstant.COSOYTE_MA) ? bodyData.get(EntityConstant.COSOYTE_MA).textValue()
				: StringPool.BLANK;
		String coSoYTeTen = bodyData.has(EntityConstant.COSOYTE_TEN)
				? bodyData.get(EntityConstant.COSOYTE_TEN).textValue()
				: StringPool.BLANK;
		String danTocMa = bodyData.has(EntityConstant.DANTOC_MA) ? bodyData.get(EntityConstant.DANTOC_MA).textValue()
				: StringPool.BLANK;
		String quocTichMa = bodyData.has(EntityConstant.QUOCTICH_MA)
				? bodyData.get(EntityConstant.QUOCTICH_MA).textValue()
				: StringPool.BLANK;
		String tienSuDiUng = bodyData.has(EntityConstant.TIENSUDIUNG)
				? bodyData.get(EntityConstant.TIENSUDIUNG).textValue()
				: StringPool.BLANK;
		String cacBenhLyDangMac = bodyData.has(EntityConstant.CACBENHLYDANGMAC)
				? bodyData.get(EntityConstant.CACBENHLYDANGMAC).textValue()
				: StringPool.BLANK;
		String cacThuocDangDung = bodyData.has(EntityConstant.CACTHUOCDANGDUNG)
				? bodyData.get(EntityConstant.CACTHUOCDANGDUNG).textValue()
				: StringPool.BLANK;
		String ghiChu = bodyData.has(EntityConstant.GHICHU) ? bodyData.get(EntityConstant.GHICHU).textValue()
				: StringPool.BLANK;
		String ngayDangKi = bodyData.has(EntityConstant.NGAYDANGKI)
				? bodyData.get(EntityConstant.NGAYDANGKI).textValue()
				: StringPool.BLANK;
		int tinhTrangDangKi = bodyData.has(EntityConstant.TINHTRANGDANGKI)
				? bodyData.get(EntityConstant.TINHTRANGDANGKI).intValue()
				: 0;

		// TODO Validate fields

		if (Validator.isNull(hoVaTen)) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.hovaten.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(cmtcccd)) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.cmtcccd.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		if (Validator.isNull(ngaySinh)) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.ngaysinh.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findByCmtcccd(cmtcccd);

		if (nguoiTiemChung != null) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.cmtcccd.exist"),
					HttpStatus.CONFLICT.value());

		}

		nguoiTiemChung = new NguoiTiemChung();
		nguoiTiemChung.setCacBenhLyDangMac(cacBenhLyDangMac);
		nguoiTiemChung.setCacThuocDangDung(cacThuocDangDung);
		nguoiTiemChung.setCmtcccd(cmtcccd);
		nguoiTiemChung.setCoSoYTeMa(coSoYTeMa);
		nguoiTiemChung.setCoSoYTeTen(coSoYTeTen);
		nguoiTiemChung.setDanTocMa(danTocMa);
		nguoiTiemChung.setDiaBanCoSoId(diaBanCoSoId);
		nguoiTiemChung.setDiaChiNoiO(diaChiNoiO);
		nguoiTiemChung.setDonViCongTac(donViCongTac);
		nguoiTiemChung.setEmail(email);
		nguoiTiemChung.setGhiChu(ghiChu);
		nguoiTiemChung.setGioiTinh(gioiTinh);
		nguoiTiemChung.setHoVaTen(hoVaTen);
		nguoiTiemChung.setMaSoBHXH(maSoBHXH);
		nguoiTiemChung.setNgayDangKi(ngayDangKi);
		nguoiTiemChung.setNgaySinh(ngaySinh);
		nguoiTiemChung.setNgheNghiep(ngheNghiep);
		nguoiTiemChung.setNhomDoiTuong(nhomDoiTuong);
		nguoiTiemChung.setPhuongXaMa(phuongXaMa);
		nguoiTiemChung.setPhuongXaTen(phuongXaTen);
		nguoiTiemChung.setQuanHuyenMa(quanHuyenMa);
		nguoiTiemChung.setQuanHuyenTen(quanHuyenTen);
		nguoiTiemChung.setQuocTichMa(quocTichMa);
		nguoiTiemChung.setSoDienThoai(soDienThoai);
		nguoiTiemChung.setSoTheBHYT(soTheBHYT);
		nguoiTiemChung.setTienSuDiUng(tienSuDiUng);
		nguoiTiemChung.setTinhThanhMa(tinhThanhMa);
		nguoiTiemChung.setTinhThanhTen(tinhThanhTen);
		nguoiTiemChung.setTinhTrangDangKi(tinhTrangDangKi);

		return nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);
	}

	@Override
	public NguoiTiemChung updateNguoiTiemChung(long id, String reqBody) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(id);

		if (nguoiTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		String hoVaTen = bodyData.has(EntityConstant.HOVATEN) ? bodyData.get(EntityConstant.HOVATEN).textValue()
				: StringPool.BLANK;
		String ngaySinh = bodyData.has(EntityConstant.NGAYSINH) ? bodyData.get(EntityConstant.NGAYSINH).textValue()
				: StringPool.BLANK;
		int gioiTinh = bodyData.has(EntityConstant.GIOITINH) ? bodyData.get(EntityConstant.GIOITINH).intValue() : 2;
		String cmtcccd = bodyData.has(EntityConstant.CMTCCCD) ? bodyData.get(EntityConstant.CMTCCCD).textValue()
				: StringPool.BLANK;
		String ngheNghiep = bodyData.has(EntityConstant.NGHENGHIEP)
				? bodyData.get(EntityConstant.NGHENGHIEP).textValue()
				: StringPool.BLANK;
		int nhomDoiTuong = bodyData.has(EntityConstant.NHOMDOITUONG)
				? bodyData.get(EntityConstant.NHOMDOITUONG).intValue()
				: 1;
		String donViCongTac = bodyData.has(EntityConstant.DONVICONGTAC)
				? bodyData.get(EntityConstant.DONVICONGTAC).textValue()
				: StringPool.BLANK;
		String soDienThoai = bodyData.has(EntityConstant.SODIENTHOAI)
				? bodyData.get(EntityConstant.SODIENTHOAI).textValue()
				: StringPool.BLANK;
		String email = bodyData.has(EntityConstant.EMAIL) ? bodyData.get(EntityConstant.EMAIL).textValue()
				: StringPool.BLANK;
		String maSoBHXH = bodyData.has(EntityConstant.MASOBHXH) ? bodyData.get(EntityConstant.MASOBHXH).textValue()
				: StringPool.BLANK;
		String soTheBHYT = bodyData.has(EntityConstant.SOTHEBHYT) ? bodyData.get(EntityConstant.SOTHEBHYT).textValue()
				: StringPool.BLANK;
		String diaChiNoiO = bodyData.has(EntityConstant.DIACHINOIO)
				? bodyData.get(EntityConstant.DIACHINOIO).textValue()
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
		long diaBanCoSoId = bodyData.has(EntityConstant.DIABANCOSO_ID)
				? bodyData.get(EntityConstant.DIABANCOSO_ID).longValue()
				: 0;
		String coSoYTeMa = bodyData.has(EntityConstant.COSOYTE_MA) ? bodyData.get(EntityConstant.COSOYTE_MA).textValue()
				: StringPool.BLANK;
		String coSoYTeTen = bodyData.has(EntityConstant.COSOYTE_TEN)
				? bodyData.get(EntityConstant.COSOYTE_TEN).textValue()
				: StringPool.BLANK;
		String danTocMa = bodyData.has(EntityConstant.DANTOC_MA) ? bodyData.get(EntityConstant.DANTOC_MA).textValue()
				: StringPool.BLANK;
		String quocTichMa = bodyData.has(EntityConstant.QUOCTICH_MA)
				? bodyData.get(EntityConstant.QUOCTICH_MA).textValue()
				: StringPool.BLANK;
		String tienSuDiUng = bodyData.has(EntityConstant.TIENSUDIUNG)
				? bodyData.get(EntityConstant.TIENSUDIUNG).textValue()
				: StringPool.BLANK;
		String cacBenhLyDangMac = bodyData.has(EntityConstant.CACBENHLYDANGMAC)
				? bodyData.get(EntityConstant.CACBENHLYDANGMAC).textValue()
				: StringPool.BLANK;
		String cacThuocDangDung = bodyData.has(EntityConstant.CACTHUOCDANGDUNG)
				? bodyData.get(EntityConstant.CACTHUOCDANGDUNG).textValue()
				: StringPool.BLANK;
		String ghiChu = bodyData.has(EntityConstant.GHICHU) ? bodyData.get(EntityConstant.GHICHU).textValue()
				: StringPool.BLANK;
		String ngayDangKi = bodyData.has(EntityConstant.NGAYDANGKI)
				? bodyData.get(EntityConstant.NGAYDANGKI).textValue()
				: StringPool.BLANK;
		int tinhTrangDangKi = bodyData.has(EntityConstant.TINHTRANGDANGKI)
				? bodyData.get(EntityConstant.TINHTRANGDANGKI).intValue()
				: 0;

		// TODO Validate fields

		NguoiTiemChung nguoiTiemChungTmp = nguoiTiemChungService.findByCmtcccd(cmtcccd);

		if (nguoiTiemChungTmp != null && nguoiTiemChungTmp.getId() != nguoiTiemChung.getId()) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.cmtcccd.exist"),
					HttpStatus.CONFLICT.value());

		}

		nguoiTiemChung.setCacBenhLyDangMac(cacBenhLyDangMac);
		nguoiTiemChung.setCacThuocDangDung(cacThuocDangDung);
		nguoiTiemChung.setCmtcccd(cmtcccd);
		nguoiTiemChung.setCoSoYTeMa(coSoYTeMa);
		nguoiTiemChung.setCoSoYTeTen(coSoYTeTen);
		nguoiTiemChung.setDanTocMa(danTocMa);
		nguoiTiemChung.setDiaBanCoSoId(diaBanCoSoId);
		nguoiTiemChung.setDiaChiNoiO(diaChiNoiO);
		nguoiTiemChung.setDonViCongTac(donViCongTac);
		nguoiTiemChung.setEmail(email);
		nguoiTiemChung.setGhiChu(ghiChu);
		nguoiTiemChung.setGioiTinh(gioiTinh);
		nguoiTiemChung.setHoVaTen(hoVaTen);
		nguoiTiemChung.setMaSoBHXH(maSoBHXH);
		nguoiTiemChung.setNgayDangKi(ngayDangKi);
		nguoiTiemChung.setNgaySinh(ngaySinh);
		nguoiTiemChung.setNgheNghiep(ngheNghiep);
		nguoiTiemChung.setNhomDoiTuong(nhomDoiTuong);
		nguoiTiemChung.setPhuongXaMa(phuongXaMa);
		nguoiTiemChung.setPhuongXaTen(phuongXaTen);
		nguoiTiemChung.setQuanHuyenMa(quanHuyenMa);
		nguoiTiemChung.setQuanHuyenTen(quanHuyenTen);
		nguoiTiemChung.setQuocTichMa(quocTichMa);
		nguoiTiemChung.setSoDienThoai(soDienThoai);
		nguoiTiemChung.setSoTheBHYT(soTheBHYT);
		nguoiTiemChung.setTienSuDiUng(tienSuDiUng);
		nguoiTiemChung.setTinhThanhMa(tinhThanhMa);
		nguoiTiemChung.setTinhThanhTen(tinhThanhTen);
		nguoiTiemChung.setTinhTrangDangKi(tinhTrangDangKi);

		return nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);
	}

	@Override
	public boolean deleteNguoiTiemChung(long id) throws Exception {
		NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(id);

		if (nguoiTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		nguoiTiemChungService.deleteNguoiTiemChung(id);
		return true;
	}

	@Override
	public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size) {

		return nguoiTiemChungService.searchNguoiTiemChung(page, size);
	}

}
