package org.vaccom.vcmgt.action.impl;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.*;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.constant.ZaloConstant;
import org.vaccom.vcmgt.dto.PhieuHenTiemDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.*;
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

	@Autowired
	private LichTiemChungAction lichTiemChungAction;

	@Autowired
	private NguoiTiemChungAction nguoiTiemChungAction;

	@Autowired
	private CoSoYTeAction coSoYTeAction;

	@Autowired
	private HangChoThongBaoAction hangChoThongBaoAction;

	@Autowired
	private UyBanNhanDanAction uyBanNhanDanAction;

	@Autowired
	private CongDanAction congDanAction;

	@Autowired
	private MuiTiemChungAction muiTiemChungAction;



	@Override
	public long countByLichTiemChungId(long id) {
		return phieuHenTiemService.countByLichTiemChungId(id);
	}

	@Override
	public long countPhieuHenTiem(long lichTiemChungId, long caTiemChungId, int tinhTrangXacNhan) {
		return phieuHenTiemService.countPhieuHenTiem(lichTiemChungId, caTiemChungId, tinhTrangXacNhan);
	}
	@Override
	public PhieuHenTiem addPhieuHenTiem(PhieuHenTiem phieuHenTiem){
		return phieuHenTiemService.updatePhieuHenTiem(phieuHenTiem);
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
		phieuHenTiem.setCaTiemChungId(0);
		phieuHenTiem.setNgayHenTiem(ngayHenTiem);
		phieuHenTiem.setNguoiTiemChungId(nguoiTiemChungId);
		phieuHenTiem.setMaQR(VaccomUtil.generateQRCode("pht", 4));
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
		phieuHenTiem.setCaTiemChungId(0);
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

						int oldTinhTrangXacNhan = phieuHenTiem.getTinhTrangXacNhan();

						if (phieuHenTiem != null) {
							if (tinhTrangXacNhan == VaccomUtil.DACHECKIN) {
								phieuHenTiem.setNgayCheckin(
										DatetimeUtil.dateToString(new Date(), DatetimeUtil._VN_DATE_FORMAT));

							}
							if(tinhTrangXacNhan == VaccomUtil.DATIEMXONG){
								Date now = new Date();
								System.out.println(phieuHenTiem.getNguoiTiemChungId());
								LichTiemChung lichTiemChung = lichTiemChungAction.findById(phieuHenTiem.getLichTiemChungId());
								NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(phieuHenTiem.getNguoiTiemChungId());
								if(Validator.isNotNull(nguoiTiemChung)){
									CongDan congDan = congDanAction.findByCongDanId(nguoiTiemChung.getCongDanID());
									if(Validator.isNotNull(congDan)){
										MuiTiemChung muiTiemChung = new MuiTiemChung();
										muiTiemChung.setHoVaTen(congDan.getHoVaTen());
										muiTiemChung.setLoaiThuocTiem(lichTiemChung.getLoaiThuocTiem());
										muiTiemChung.setLanTiem(phieuHenTiem.getLanTiem());
										muiTiemChung.setCmtcccd(congDan.getCmtcccd());
										muiTiemChung.setCoSoYTeTen(lichTiemChung.getTenCoSo());
										muiTiemChung.setCongDanID(congDan.getId());
										muiTiemChung.setCoSoYTeMa(String.valueOf(lichTiemChung.getCoSoYTeId()));
										muiTiemChung.setNoiSanXuat(lichTiemChung.getNoiSanXuat());
										muiTiemChung.setNgaySinh(congDan.getNgaySinh());
										muiTiemChung.setHanSuDung(lichTiemChung.getHanSuDung());
										muiTiemChung.setDiaDiemTiemChung(lichTiemChung.getDiaDiemTiemChung());
										muiTiemChung.setNgayTiemChung(DatetimeUtil.dateToString(new Date(), DatetimeUtil._VN_DATE_FORMAT));
										String gioTiemChung = now.getHours() + ":" + now.getMinutes();
										muiTiemChung.setGioTiemChung(gioTiemChung);
										muiTiemChungAction.create(muiTiemChung);

										congDan.setSoMuiTiem(phieuHenTiem.getLanTiem());
										congDanAction.update(congDan);

										nguoiTiemChung.setSoMuiTiem(phieuHenTiem.getLanTiem());
										nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.DATIEM);
										nguoiTiemChung.setNgayTiemCuoi(DatetimeUtil.dateToString(new Date(), DatetimeUtil._VN_DATE_FORMAT));
										nguoiTiemChungAction.update(nguoiTiemChung);
									}
								}
							}
							phieuHenTiem.setTinhTrangXacNhan(tinhTrangXacNhan);
							PhieuHenTiem phieuHenTiemNew = phieuHenTiemService.update(phieuHenTiem);

							int newTinhTrangXacNhan = phieuHenTiem.getTinhTrangXacNhan();
							if(Validator.isNotNull(phieuHenTiemNew)){
								if(oldTinhTrangXacNhan!=VaccomUtil.HENDAXACNHAN && newTinhTrangXacNhan==VaccomUtil.HENDAXACNHAN){
									if(Validator.isNotNull(phieuHenTiem)){
										LichTiemChung lichTiemChung = lichTiemChungAction.findById(phieuHenTiem.getLichTiemChungId());
										NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(phieuHenTiem.getNguoiTiemChungId());
										UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(lichTiemChung.getUyBanNhanDanID());

										String TenCoSo = lichTiemChung.getTenCoSo();
										String DiaDiem = lichTiemChung.getDiaDiemTiemChung();

										long uyBanNhanDanId = lichTiemChung.getUyBanNhanDanID();

										//Json
										ObjectNode template_data = mapper.createObjectNode();

										template_data.put(ZaloConstant.HoVaTen, nguoiTiemChung.getHoVaTen());
										template_data.put(ZaloConstant.CoSoYTe, TenCoSo);
										template_data.put(ZaloConstant.NgayTiemChung, phieuHenTiem.getNgayHenTiem() +" "+ phieuHenTiem.getGioHenTiem());
										template_data.put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan());
										template_data.put(ZaloConstant.DonViTiem, TenCoSo);
										template_data.put(ZaloConstant.DiaDiem, DiaDiem);
										template_data.put(ZaloConstant.LoaiThuocTiem, lichTiemChung.getLoaiThuocTiem());
										template_data.put(ZaloConstant.QrCodeID, phieuHenTiem.getMaQR());
										template_data.put(ZaloConstant.SoDonViCap, uyBanNhanDan.getSoDienThoai());
										template_data.put(ZaloConstant.LanTiem, phieuHenTiem.getLanTiem());


										hangChoThongBaoAction.addHangChoThongBao(template_data.toString(),
												nguoiTiemChung.getSoDienThoai(),
												nguoiTiemChung.getEmail(),
												true, ZaloConstant.Loai_Hen_TiemChung,
												uyBanNhanDanId,
												phieuHenTiem.getId());
									}
								}
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

	@Override
	public List<PhieuHenTiem> findByLichTiemChungID(long lichTiemChungId) {
		return phieuHenTiemService.findByLichTiemChungID(lichTiemChungId);
	}

	@Override
	public List<PhieuHenTiem> findByTinhTrangXacNhan_LichTiemChungID(int tinhTrangXacNhan, long LichTiemChungID) {
		return phieuHenTiemService.findByTinhTrangXacNhan_LichTiemChungID(tinhTrangXacNhan, LichTiemChungID);
	}

	@Override
	public List<PhieuHenTiem> findByKhacTinhTrangXacNhan_LichTiemChungID(int tinhTrangXacNhan, long LichTiemChungID) {
		return phieuHenTiemService.findByKhacTinhTrangXacNhan_LichTiemChungID(tinhTrangXacNhan, LichTiemChungID);
	}

	@Override
	public PhieuHenTiem update(PhieuHenTiem phieuHenTiem) {
		return phieuHenTiemService.update(phieuHenTiem);
	}


	private final Log _log = LogFactory.getLog(PhieuHenTiemActionImpl.class);
}
