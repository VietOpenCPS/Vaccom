package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.NguoiTiemChungAction;

import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.dto.CongDanDto;
import org.vaccom.vcmgt.dto.MuiTiemChungDto;
import org.vaccom.vcmgt.dto.NguoiTiemChungDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.*;

import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.security.impl.RandomString;
import org.vaccom.vcmgt.security.impl.StrongTextDataEncryptor;
import org.vaccom.vcmgt.service.*;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.RoleUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

@Service
public class NguoiTiemChungActionImpl implements NguoiTiemChungAction {

	@Value("${spring.security.encoding-strength}")
	private Integer encodingStrength;

	@Autowired
	private NguoiDungService nguoiDungService;

	@Autowired
	private NguoiTiemChungService nguoiTiemChungService;
	
	@Autowired
	private CoSoYTeService coSoYTeService;

	@Autowired
	private MuiTiemChungService muiTiemChungService;

	@Autowired
	private CongDanService congDanService;

	@Override
	public long countAll() {

		return nguoiTiemChungService.countAll();
	}

	@Override
	public void addNguoiTiemChung(NguoiTiemChungDto nguoiTiemChungDto) throws Exception {
		NguoiTiemChung nguoiTiemChung = new NguoiTiemChung();

		long countUser = !nguoiTiemChungDto.cmtcccd.isEmpty()
				? nguoiTiemChungService.countByCmtcccd(nguoiTiemChungDto.cmtcccd) : 0;

		if(countUser == 0 && nguoiTiemChungDto.cmtcccd.isEmpty()) {
			countUser = nguoiTiemChungService.countBySoDienThoai(nguoiTiemChungDto.sodienthoai);
		}
		boolean userHasButNotMuiTiem = false;

		if (countUser > 0) {
			return;
		}

		nguoiTiemChung.setKetQuaKiemTra("{\"nguoikiemtra\": \"auto\"}");
		nguoiTiemChung.setDiaBanCoSoId(nguoiTiemChungDto.diabancosoid);
		nguoiTiemChung.setHoVaTen(nguoiTiemChungDto.hovaten);
		nguoiTiemChung.setNgaySinh(nguoiTiemChungDto.ngaysinh);
		nguoiTiemChung.setGioiTinh(nguoiTiemChungDto.gioitinh);
		nguoiTiemChung.setNhomDoiTuong(nguoiTiemChungDto.nhomdoituong);
		nguoiTiemChung.setDonViCongTac(nguoiTiemChungDto.donvicongtac);
		nguoiTiemChung.setSoDienThoai(nguoiTiemChungDto.sodienthoai);
		nguoiTiemChung.setCmtcccd(nguoiTiemChungDto.cmtcccd);
		nguoiTiemChung.setSoTheBHYT(nguoiTiemChungDto.sothebhyt);
		nguoiTiemChung.setDiaChiNoiO(nguoiTiemChungDto.diachinoio);
		nguoiTiemChung.setPhuongXaTen(nguoiTiemChungDto.phuongxaten);
		nguoiTiemChung.setQuanHuyenTen(nguoiTiemChungDto.quanhuyenten);
		nguoiTiemChung.setTinhThanhTen(nguoiTiemChungDto.tinhthanhten);
		nguoiTiemChung.setTinhTrangDangKi(nguoiTiemChungDto.tinhtrangdangki);
		nguoiTiemChung.setMaQR(VaccomUtil.generateQRCode("ntc", 6));

		List<MuiTiemChungDto> listTiemChungDto = nguoiTiemChungDto.listMuiTieuChungDto;
		NguoiTiemChung nguoiTiemChungCreated = nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);

		if(nguoiTiemChungCreated == null) {
			_log.error("Loi khong tao duoc nguoi dung voi cmt: " + nguoiTiemChungDto.cmtcccd);
			return ;
		}

		long idCongDanNguoiTiem = nguoiTiemChungCreated.getCongDanID();
		//Add cong dan
		CongDan oldCongDan = congDanService.findBySdtOrCmt(nguoiTiemChungCreated.getSoDienThoai(), nguoiTiemChungCreated.getCmtcccd());
		CongDan congDannew = null;

		if(oldCongDan == null) {
			try {
				congDannew = createCongDanByNguoiTiemChung(nguoiTiemChungCreated);

				if(congDannew == null) {
					_log.warn("Cong dan new is null for cmt: " + nguoiTiemChungCreated.getCmtcccd() + ", sdt: " + nguoiTiemChungCreated.getSoDienThoai());
				} else {
					nguoiTiemChungCreated.setCongDanID(congDannew.getId());
					nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChungCreated);
				}
			} catch (Exception e) {
				_log.error("Error when create cong dan for cmt: " + nguoiTiemChungCreated.getCmtcccd() + ", sdt: " + nguoiTiemChungCreated.getSoDienThoai());
				_log.error(e.getMessage());
			}
		} else {
			nguoiTiemChungCreated.setCongDanID(oldCongDan.getId());
			nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChungCreated);
		}
		String tenNguoiTiem = nguoiTiemChungCreated.getHoVaTen();
		String cmt = nguoiTiemChungCreated.getCmtcccd();

		long congDanId = 0;
		int oldSoMuiTiem = 0;

		if(congDannew != null) {
			congDanId = congDannew.getId();
		} else {
			congDanId = oldCongDan.getId();
			oldSoMuiTiem = oldCongDan.getSoMuiTiem();
		}

		if(listTiemChungDto == null || listTiemChungDto.size() == 0) {
			MuiTiemChung muiTiemChung = new MuiTiemChung();
			muiTiemChung.setCongDanID(idCongDanNguoiTiem);
			muiTiemChung.setLanTiem(1);
			muiTiemChung.setHoVaTen(tenNguoiTiem);
			muiTiemChung.setCmtcccd(cmt);
			muiTiemChung.setCongDanID(congDanId);

			if(congDannew != null) {
				congDannew.setSoMuiTiem(1);
			} else {
				oldCongDan.setSoMuiTiem(oldSoMuiTiem + 1);
			}

			muiTiemChungService.updateMuiTiemChung(muiTiemChung);

			if(congDannew != null) {
				congDanService.save(congDannew);
			} else {
				congDanService.save(oldCongDan);
			}

			return;
		}

		int lanTiem = 1;
		for(MuiTiemChungDto muiTiemChungDto: listTiemChungDto) {
			MuiTiemChung muiTiemChung = new MuiTiemChung();
			muiTiemChung.setCongDanID(idCongDanNguoiTiem);
			muiTiemChung.setHoVaTen(tenNguoiTiem);
			muiTiemChung.setLanTiem(lanTiem);
			muiTiemChung.setCmtcccd(cmt);
			muiTiemChung.setNgayTiemChung(muiTiemChungDto.ngaytiem);
			muiTiemChung.setSoLoThuoc(muiTiemChungDto.soLo);
			muiTiemChung.setLoaiThuocTiem(muiTiemChungDto.tenThuoc);
			muiTiemChung.setCongDanID(congDanId);

			if(congDannew != null) {
				congDannew.setSoMuiTiem(lanTiem);
			} else {
				oldSoMuiTiem++;
			}
			lanTiem++;
			muiTiemChungService.updateMuiTiemChung(muiTiemChung);
		}

		if(congDannew != null) {
			congDanService.save(congDannew);
		} else {
			oldCongDan.setSoMuiTiem(oldSoMuiTiem);
			congDanService.save(oldCongDan);
		}
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

		NguoiTiemChung nguoiTiemChung = new NguoiTiemChung();

		long countByCmtcccd = nguoiTiemChungService.countByCmtcccd(cmtcccd, VaccomUtil.MOIDANGKY);

		if (countByCmtcccd > 0) {
			/*
			 * List<NguoiTiemChung> lstNguoiTiemChung =
			 * nguoiTiemChungService.findByCmtcccd(cmtcccd); for (NguoiTiemChung
			 * nguoiTiemChungTmp : lstNguoiTiemChung) {
			 * if(nguoiTiemChungTmp.getTinhTrangDangKi() == VaccomUtil.MOIDANGKY) {
			 * nguoiTiemChungTmp.setKiemTraTrung(VaccomUtil.KIEMTRACOTRUNG);
			 * nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChungTmp); }
			 * 
			 * }
			 */
			nguoiTiemChung.setKiemTraTrung(VaccomUtil.KIEMTRACOTRUNG);
		} else {
			nguoiTiemChung.setKiemTraTrung(VaccomUtil.KIEMTRAKHONGTRUNG);
		}

		nguoiTiemChung.setKetQuaKiemTra("{\"nguoikiemtra\": \"auto\"}");

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
		// nguoiTiemChung.setMaSoBHXH(maSoBHXH);
		nguoiTiemChung.setNgayDangKi(ngayDangKi);
		nguoiTiemChung.setNgaySinh(ngaySinh);
		// nguoiTiemChung.setNgheNghiep(ngheNghiep);
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
		nguoiTiemChung.setMaQR(VaccomUtil.generateQRCode("ntc", 6));
		CoSoYTe coSoYTe = coSoYTeService.findByMaCoSo(coSoYTeMa);
		nguoiTiemChung.setCoSoYTeId(coSoYTe != null ? coSoYTe.getId() : 0);

		NguoiTiemChung nguoiTiemChungNew = nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);
		CongDan oldCongDan = congDanService.findBySdtOrCmt(nguoiTiemChungNew.getSoDienThoai(), nguoiTiemChungNew.getCmtcccd());

		if(oldCongDan == null) {
			CongDan congDannew = null;
			if(nguoiTiemChungNew != null) {
				congDannew = createCongDanByNguoiTiemChung(nguoiTiemChungNew);
			}

			if(congDannew != null) {
				nguoiTiemChungNew.setCongDanID(congDannew.getId());
			}
		} else {
			nguoiTiemChungNew.setCongDanID(oldCongDan.getId());
		}


		return nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChungNew);
	}


	private CongDan createCongDanByNguoiTiemChung(NguoiTiemChung nguoiTiemChungCreated) {
		CongDanDto congDanDto = new CongDanDto();
		congDanDto.cmtcccd = nguoiTiemChungCreated.getCmtcccd();
		congDanDto.hoVaTen = nguoiTiemChungCreated.getHoVaTen();
		congDanDto.diaChiThuongTru = nguoiTiemChungCreated.getDiaChiNoiO();
		congDanDto.soDienThoai = nguoiTiemChungCreated.getSoDienThoai();
		congDanDto.ngaySinh = nguoiTiemChungCreated.getNgaySinh();
		congDanDto.phuongXaMa = nguoiTiemChungCreated.getPhuongXaMa();
		congDanDto.phuongXaTen = nguoiTiemChungCreated.getPhuongXaTen();
		congDanDto.quanHuyenMa = nguoiTiemChungCreated.getQuanHuyenMa();
		congDanDto.quanHuyenTen = nguoiTiemChungCreated.getQuanHuyenTen();
		congDanDto.tinhThanhMa = nguoiTiemChungCreated.getTinhThanhMa();
		congDanDto.tinhThanhTen = nguoiTiemChungCreated.getTinhThanhTen();
		congDanDto.gioiTinh = nguoiTiemChungCreated.getGioiTinh();

		CongDan congDannew = congDanService.addCongDan(congDanDto);
		return  congDannew;
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
		
		if(nguoiTiemChung.getTinhTrangDangKi() != 0) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.inuse"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}
		// TODO Validate fields
		long countByCmtcccd = nguoiTiemChungService.countByCmtcccd(cmtcccd, nguoiTiemChung.getTinhTrangDangKi());

		if (countByCmtcccd > 0 && !cmtcccd.equals(nguoiTiemChung.getCmtcccd())) {
			nguoiTiemChung.setKiemTraTrung(VaccomUtil.KIEMTRACOTRUNG);
			
		} else {
			nguoiTiemChung.setKiemTraTrung(VaccomUtil.KIEMTRAKHONGTRUNG);
		}
		
		//TODO validate
		CoSoYTe coSoYTe = coSoYTeService.findByMaCoSo(coSoYTeMa);

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
		// nguoiTiemChung.setMaSoBHXH(maSoBHXH);
		nguoiTiemChung.setNgayDangKi(ngayDangKi);
		nguoiTiemChung.setNgaySinh(ngaySinh);
		// nguoiTiemChung.setNgheNghiep(ngheNghiep);
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
		//nguoiTiemChung.setTinhTrangDangKi(tinhTrangDangKi);
		
		nguoiTiemChung.setCoSoYTeId(coSoYTe != null ? coSoYTe.getId() : 0);

		return nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);
	}

	@Override
	public boolean deleteNguoiTiemChung(long id) throws Exception {
		NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(id);

		if (nguoiTiemChung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.not_found"),
					HttpStatus.NOT_FOUND.value());
		}

		/*
		if (nguoiTiemChung.getTinhTrangDangKi() != 0) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoitiemchung.inuse"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}
		*/

		nguoiTiemChungService.deleteNguoiTiemChung(id);

		return true;
	}

	@Override
	public void deleteNguoiTiemChung(String reqBody) {

		try {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode bodyData = mapper.readTree(reqBody);

			String ids = bodyData.has(EntityConstant.IDS) ? bodyData.get(EntityConstant.IDS).textValue()
					: StringPool.BLANK;

			List<String> lstId = StringUtil.split(ids);

			if (lstId != null) {
				for (String strId : lstId) {
					long id = GetterUtil.getLong(strId, 0);
					if (id > 0) {
						NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(id);
						if (nguoiTiemChung != null && nguoiTiemChung.getTinhTrangDangKi() == VaccomUtil.MOIDANGKY) {
							try {
								nguoiTiemChungService.deleteNguoiTiemChung(id);
							} catch (Exception e) {
								_log.warn(e.getMessage());
							}
						}
					}

				}
			}
		} catch (Exception e) {
			_log.error(e);
		}

	}

	@Override
	public NguoiTiemChung findById(long id) {
		return nguoiTiemChungService.findById(id);
	}

	@Override
	public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size) {

		return nguoiTiemChungService.searchNguoiTiemChung(page, size);
	}

	@Override
	public long countNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema, Integer tinhtrangdangky, Integer kiemtratrung, String tinhthanhma, String tinhthanhten, String quanhuyenma
			,  String quanhuyenten, String phuongxama, String phuongxaten) {
		return nguoiTiemChungService.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten, diabancosoid,
				cosoytema, tinhtrangdangky, kiemtratrung,  tinhthanhma,  tinhthanhten,  quanhuyenma
				,   quanhuyenten,  phuongxama,  phuongxaten);
	}

	@Override
	public List<NguoiTiemChung> searchNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki,
			String hovaten, Long diabancosoid, String cosoytema, Integer tinhtrangdangky, Integer kiemtratrung,
			Integer page, Integer size, String tinhthanhma, String tinhthanhten, String quanhuyenma
			,  String quanhuyenten, String phuongxama, String phuongxaten) {
		return nguoiTiemChungService.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten, diabancosoid,
				cosoytema, tinhtrangdangky, kiemtratrung, page, size,  tinhthanhma,  tinhthanhten,  quanhuyenma
				,   quanhuyenten,  phuongxama,  phuongxaten);
	}

	@Override
	public int duyetDangKyMoi(String reqBody) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode bodyData = mapper.readTree(reqBody);

			boolean isAutoAccept = bodyData.has(EntityConstant.IS_AUTO_ACCEPT) ? bodyData.get(EntityConstant.IS_AUTO_ACCEPT).booleanValue() : false;

			if(isAutoAccept) {
				int countAccept = bodyData.has(EntityConstant.COUNT_ACCEPT) ? bodyData.get(EntityConstant.COUNT_ACCEPT).intValue()  : -1;
				if(countAccept < 0) {
					return 0;
				}

				NguoiTiemChungDto dto = new NguoiTiemChungDto();
				dto.tinhtrangdangki = VaccomUtil.MOIDANGKY;

				List<NguoiTiemChung> listNguoiTiemChungDangCho = nguoiTiemChungService.searchListChuyenDangKyChinhThuc(dto);

				if(listNguoiTiemChungDangCho == null || listNguoiTiemChungDangCho.size() == 0) {
					return 0;
				}

				long id;
				int count = 0;
				for(NguoiTiemChung nguoiTiemChung: listNguoiTiemChungDangCho) {
					id = nguoiTiemChung.getId();
					List<MuiTiemChung> lstMuiTiemChung = muiTiemChungService.findByCongDan_ID(nguoiTiemChung.getCongDanID());
					if(lstMuiTiemChung != null ) {
						if(lstMuiTiemChung.size() > countAccept) {
							continue;
						}
					}
					nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.DANGKYCHINHTHUC);
					nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);
					count ++;
				}

				return count;
			}

			String ids = bodyData.has(EntityConstant.IDS) ? bodyData.get(EntityConstant.IDS).textValue()

					: StringPool.BLANK;

			List<String> lstId = StringUtil.split(ids);

			if (lstId != null) {
				for (String strId : lstId) {
					long id = GetterUtil.getLong(strId, 0);

					if (id > 0) {
						NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(id);

						if (nguoiTiemChung != null && nguoiTiemChung.getTinhTrangDangKi() == VaccomUtil.MOIDANGKY) {
							try {
								List<MuiTiemChung> lstMuiTiemChung = muiTiemChungService.findByCongDan_ID(nguoiTiemChung.getCongDanID());
								if(lstMuiTiemChung != null ) {
									if(lstMuiTiemChung.size() == 1) {
										if(lstMuiTiemChung.get(0).getLanTiem() > 1) {
											continue;
										}
									}

									if(lstMuiTiemChung.size() >=2) {
										continue;
									}
								}

								nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.DANGKYCHINHTHUC);

								long countByCmtcccd = nguoiTiemChungService.countByCmtcccd(nguoiTiemChung.getCmtcccd(), VaccomUtil.DANGKYCHINHTHUC);

								if(countByCmtcccd > 0) {
									nguoiTiemChung.setKiemTraTrung(VaccomUtil.KIEMTRACOTRUNG);
								}else {
									nguoiTiemChung.setKiemTraTrung(VaccomUtil.KIEMTRAKHONGTRUNG);
								}

								nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);
								/*
								if (Validator.isNotNull(nguoiTiemChung.getCmtcccd())) {
									NguoiDung nguoiDung = nguoiDungService
											.findByTenDanNhap(nguoiTiemChung.getCmtcccd());

									if (nguoiDung == null) {
										addNguoiDung(nguoiTiemChung.getCmtcccd(), nguoiTiemChung.getCmtcccd(),
												RoleUtil.REGULAR, nguoiTiemChung.getHoVaTen(),
												StringPool.BLANK, nguoiTiemChung.getSoDienThoai(),
												nguoiTiemChung.getEmail(), nguoiTiemChung.getDiaBanCoSoId(), 0, false,
												nguoiTiemChung.getId());
									} else {
										if (nguoiDung.isKhoaTaiKhoan()) {
											nguoiDung.setKhoaTaiKhoan(false);
											nguoiDungService.updateNguoiDung(nguoiDung);
										}
									}
								}
								*/

							} catch (Exception e) {
								_log.warn(e.getMessage());
							}
						}
					}

				}
			}
			return 0;
		} catch (Exception e) {
			_log.error(e);
		}
		return 0;
	}

	@Override
	public void huyDangKyChinhThuc(String reqBody) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode bodyData = mapper.readTree(reqBody);

			String ids = bodyData.has(EntityConstant.IDS) ? bodyData.get(EntityConstant.IDS).textValue()
					: StringPool.BLANK;

			List<String> lstId = StringUtil.split(ids);

			if (lstId != null) {
				for (String strId : lstId) {
					long id = GetterUtil.getLong(strId, 0);

					if (id > 0) {
						NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(id);
						
						if (nguoiTiemChung != null
								&& nguoiTiemChung.getTinhTrangDangKi() == VaccomUtil.DANGKYCHINHTHUC) {
							try {
								
								nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.MOIDANGKY);
								nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);

//								if (Validator.isNotNull(nguoiTiemChung.getCmtcccd())) {
//									NguoiDung nguoiDung = nguoiDungService
//											.findByTenDanNhap(nguoiTiemChung.getCmtcccd());
//
//									if (nguoiDung != null && !nguoiDung.isKhoaTaiKhoan()) {
//										nguoiDung.setKhoaTaiKhoan(true);
//										nguoiDungService.updateNguoiDung(nguoiDung);
//									}
//								}

							} catch (Exception e) {
								_log.warn(e.getMessage());
							}
						}
					}

				}
			}
		} catch (Exception e) {
			_log.error(e);
		}
	}
	
	@Override
	public void khoiPhucDangKy(String reqBody) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode bodyData = mapper.readTree(reqBody);

			String ids = bodyData.has(EntityConstant.IDS) ? bodyData.get(EntityConstant.IDS).textValue()
					: StringPool.BLANK;

			List<String> lstId = StringUtil.split(ids);

			if (lstId != null) {
				for (String strId : lstId) {
					long id = GetterUtil.getLong(strId, 0);

					if (id > 0) {
						NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(id);

						if (nguoiTiemChung != null
								&& nguoiTiemChung.getTinhTrangDangKi() == VaccomUtil.XOADANGKY) {
							try {
								long countByCmtcccd = nguoiTiemChungService.countByCmtcccd(nguoiTiemChung.getCmtcccd(), VaccomUtil.MOIDANGKY);
								nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.MOIDANGKY);
								if(countByCmtcccd > 0) {
									nguoiTiemChung.setKiemTraTrung(VaccomUtil.KIEMTRACOTRUNG);
								}
								nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);

							} catch (Exception e) {
								_log.warn(e.getMessage());
							}
						}
					}

				}
			}
		} catch (Exception e) {
			_log.error(e);
		}
	}

	@Override
	public NguoiTiemChung updateTrangThaiDangKy(String reqBody) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode bodyData = mapper.readTree(reqBody);

			int tinhTrangDangKi = bodyData.has(EntityConstant.TINHTRANGDANGKI)
					? bodyData.get(EntityConstant.TINHTRANGDANGKI).intValue()
					: 0;
			String ids = bodyData.has(EntityConstant.IDS) ? bodyData.get(EntityConstant.IDS).textValue()
					: StringPool.BLANK;

			List<String> lstId = StringUtil.split(ids);

			if (lstId != null) {
				for (String strId : lstId) {
					long id = GetterUtil.getLong(strId, 0);

					if (id > 0) {
						NguoiTiemChung nguoiTiemChung = nguoiTiemChungService.findById(id);

						if (nguoiTiemChung != null) {
							try {
								nguoiTiemChung.setTinhTrangDangKi(tinhTrangDangKi);
								nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);
								/*
								if (Validator.isNotNull(nguoiTiemChung.getCmtcccd())) {
									NguoiDung nguoiDung = nguoiDungService
											.findByTenDanNhap(nguoiTiemChung.getCmtcccd());
									if (tinhTrangDangKi == VaccomUtil.DANGKYCHINHTHUC) {
										if (nguoiDung == null) {
											addNguoiDung(nguoiTiemChung.getCmtcccd(), nguoiTiemChung.getCmtcccd(),
													RoleUtil.REGULAR, nguoiTiemChung.getHoVaTen(),
													StringPool.BLANK,
													nguoiTiemChung.getSoDienThoai(), nguoiTiemChung.getEmail(),
													nguoiTiemChung.getDiaBanCoSoId(), 0, false, nguoiTiemChung.getId());
										} else {
											if (nguoiDung.isKhoaTaiKhoan()) {
												nguoiDung.setKhoaTaiKhoan(false);
												nguoiDungService.updateNguoiDung(nguoiDung);
											}
										}
									} else {
										if (nguoiDung != null) {
											nguoiDung.setKhoaTaiKhoan(true);
											nguoiDungService.updateNguoiDung(nguoiDung);
										}
									}
								}
								*/
								return nguoiTiemChung;
							} catch (Exception e) {
								_log.warn(e.getMessage());
							}
						}
					}

				}
			}
		} catch (Exception e) {
			_log.error(e);
		}
		return null;

	}

	@Override
	public NguoiTiemChung findByMaQR(String MaQR) {
		return nguoiTiemChungService.findByMaQR(MaQR);
	}

	@Override
	public ResultSearchDto<NguoiTiemChung> search(NguoiTiemChungDto nguoiTiemChungDto, int page, int size) {
		if(nguoiTiemChungDto.isSearchOr) {
			return nguoiTiemChungService.searchOr(nguoiTiemChungDto, page, size);
		}
		return nguoiTiemChungService.search(nguoiTiemChungDto, page, size);
	}

	@Override
	public ResultSearchDto<CongDan> searchDaTiem(NguoiTiemChungDto nguoiTiemChungDto, int page, int size) {
		return nguoiTiemChungService.searchDaTiem(nguoiTiemChungDto, page, size);
	}

	@Override
	public NguoiTiemChung findByCMTCCCD(String cmtcccd) {
		return nguoiTiemChungService.findByCMTCCCD(cmtcccd);
	}

	@Override
	public NguoiTiemChung update(NguoiTiemChung nguoiTiemChung) {
		return nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);
	}

	@Override
	public NguoiTiemChung findBycongDanID(long id) {
		return nguoiTiemChungService.findBycongDanID(id);
	}

	@Override
	public NguoiTiemChung addNguoiTiemChung(String hoVaTen, String ngaySinh, int gioiTinh, String cmtcccd,
			int nhomDoiTuong, String donViCongTac, String soDienThoai, String email, String soTheBHYT,
			String diaChiNoiO, String tinhThanhMa, String tinhThanhTen, String quanHuyenMa, String quanHuyenTen,
			String phuongXaMa, String phuongXaTen, long diaBanCoSoId, String coSoYTeMa, String coSoYTeTen,
			String danTocMa, String quocTichMa, String tienSuDiUng, String cacBenhLyDangMac, String cacThuocDangDung,
			String ghiChu, String ngayDangKi, int tinhTrangDangKi) {
		NguoiTiemChung nguoiTiemChung = new NguoiTiemChung();

		long countByCmtcccd = nguoiTiemChungService.countByCmtcccd(cmtcccd);

		if (countByCmtcccd > 0) {

			List<NguoiTiemChung> lstNguoiTiemChung = nguoiTiemChungService.findByCmtcccd(cmtcccd);
			for (NguoiTiemChung nguoiTiemChungTmp : lstNguoiTiemChung) {
				nguoiTiemChungTmp.setKiemTraTrung(2);
				nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChungTmp);
			}
			nguoiTiemChung.setKiemTraTrung(2);
		} else {
			nguoiTiemChung.setKiemTraTrung(1);
		}
		
		//TODO validate
		CoSoYTe coSoYTe = coSoYTeService.findByMaCoSo(coSoYTeMa);

		nguoiTiemChung.setKetQuaKiemTra("{\"nguoikiemtra\": \"auto\"}");

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
		nguoiTiemChung.setNgayDangKi(ngayDangKi);
		nguoiTiemChung.setNgaySinh(ngaySinh);
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
		nguoiTiemChung.setMaQR(VaccomUtil.generateQRCode("ntc", 6));
		nguoiTiemChung.setCoSoYTeId(coSoYTe != null ? coSoYTe.getId() : 0);

		NguoiTiemChung nguoiTiemChungNew = nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChung);

		CongDan oldCongDan = congDanService.findBySdtOrCmt(nguoiTiemChungNew.getSoDienThoai(), nguoiTiemChungNew.getCmtcccd());

		if(oldCongDan == null) {
			CongDan congDannew = null;
			if(nguoiTiemChungNew != null) {
				congDannew = createCongDanByNguoiTiemChung(nguoiTiemChungNew);
			}

			if(congDannew != null) {
				nguoiTiemChungNew.setCongDanID(congDannew.getId());
			}
		} else {
			nguoiTiemChungNew.setCongDanID(oldCongDan.getId());
		}

		nguoiTiemChungService.updateNguoiTiemChung(nguoiTiemChungNew);

		return null;
	}

	private boolean addNguoiDung(String tenDangNhap, String matKhau, int quanTriHeThong, String hoVaTen, String chucDanh,
			String soDienThoai, String email, long diaBanCoSoId, long coSoYTeId, boolean khoaTaiKhoan,
			long nguoiTiemChungId) throws Exception {

		NguoiDung nguoiDung = new NguoiDung();

		nguoiDung.setChucDanh(chucDanh);
		nguoiDung.setCoSoYTeId(coSoYTeId);
		nguoiDung.setDiaBanCoSoId(diaBanCoSoId);
		nguoiDung.setEmail(email);
		nguoiDung.setHoVaTen(hoVaTen);
		nguoiDung.setKhoaTaiKhoan(khoaTaiKhoan);
		nguoiDung.setMatKhau(new BCryptPasswordEncoder(encodingStrength).encode(matKhau));
		nguoiDung.setQuanTriHeThong(quanTriHeThong);
		nguoiDung.setSoDienThoai(soDienThoai);
		nguoiDung.setTenDangNhap(tenDangNhap);
		nguoiDung.setNguoiTiemChungId(nguoiTiemChungId);
		
		KhoaDangKy khoaDangKy = createKhoaDangKy(RoleUtil.getTenVaiTro(nguoiDung));

		nguoiDungService.addNguoiDung(nguoiDung, khoaDangKy);

		return true;
	}

	private String gennerateClientId(String digest) {
		StrongTextDataEncryptor encryptor = new StrongTextDataEncryptor();

		RandomString random = new RandomString(32);

		String clientId = random.nextString();

		return encryptor.encrypt(digest, clientId);
	}

	private String gennerateSecretKey(String digest) {
		StrongTextDataEncryptor encryptor = new StrongTextDataEncryptor();

		RandomString random = new RandomString(32);

		String secretKey = random.nextString();

		return encryptor.encrypt(digest, secretKey);
	}

	private KhoaDangKy createKhoaDangKy(String tenVaiTro) {

		RandomString random = new RandomString(64);

		String digest = random.nextString();

		String khoaCongKhai = gennerateClientId(digest);

		String khoaBiMat = gennerateSecretKey(digest);

		KhoaDangKy khoaDangKy = new KhoaDangKy();
		khoaDangKy.setKhoaBiMat(khoaBiMat);
		khoaDangKy.setKhoaCongKhai(khoaCongKhai);
		khoaDangKy.setPhamVi(tenVaiTro);
		khoaDangKy.setTrangThai(1);

		return khoaDangKy;
	}

	private final Log _log = LogFactory.getLog(NguoiTiemChungActionImpl.class);
}
