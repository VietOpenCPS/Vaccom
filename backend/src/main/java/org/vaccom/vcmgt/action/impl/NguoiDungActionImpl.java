package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.NguoiDungAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.entity.KhoaTruyCap;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.security.impl.RandomString;
import org.vaccom.vcmgt.security.impl.StrongTextDataEncryptor;
import org.vaccom.vcmgt.service.KhoaDangKyService;
import org.vaccom.vcmgt.service.KhoaTruyCapService;
import org.vaccom.vcmgt.service.NguoiDungService;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.RoleUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

@Service
public class NguoiDungActionImpl implements NguoiDungAction {

	@Value("${spring.security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${spring.config.default.pass}")
	private String defaultPass;

	@Autowired
	NguoiDungService nguoiDungService;

	@Autowired
	KhoaDangKyService khoaDangKyService;

	@Autowired
	KhoaTruyCapService khoaTruyCapService;

	@Override
	public long countAll() {
		return nguoiDungService.countAll();
	}

	@Override
	public NguoiDung addNguoiDung(String reqBody) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		String tenDangNhap = bodyData.has(EntityConstant.TENDANGNHAP)
				? bodyData.get(EntityConstant.TENDANGNHAP).textValue().toLowerCase()
				: StringPool.BLANK;

		String matKhau = bodyData.has(EntityConstant.MATKHAU) ? bodyData.get(EntityConstant.MATKHAU).textValue()
				: StringPool.BLANK;

		int quanTriHeThong = bodyData.has(EntityConstant.QUANTRIHETHONG) ? bodyData.get(EntityConstant.QUANTRIHETHONG).intValue()
				: RoleUtil.REGULAR;

		String hoVaTen = bodyData.has(EntityConstant.HOVATEN) ? bodyData.get(EntityConstant.HOVATEN).textValue()
				: StringPool.BLANK;
		String chucDanh = bodyData.has(EntityConstant.CHUCDANH) ? bodyData.get(EntityConstant.CHUCDANH).textValue()
				: StringPool.BLANK;
		String soDienThoai = bodyData.has(EntityConstant.SODIENTHOAI)
				? bodyData.get(EntityConstant.SODIENTHOAI).textValue()
				: StringPool.BLANK;
		String email = bodyData.has(EntityConstant.EMAIL) ? bodyData.get(EntityConstant.EMAIL).textValue()
				: StringPool.BLANK;

		// TODO validate
		long diaBanCoSoId = bodyData.has(EntityConstant.DIABANCOSO_ID)
				? bodyData.get(EntityConstant.DIABANCOSO_ID).longValue()
				: 0;
		// TODO validate
		long coSoYTeId = bodyData.has(EntityConstant.COSOYTE_ID) ? bodyData.get(EntityConstant.COSOYTE_ID).longValue()
				: 0;

		boolean khoaTaiKhoan = bodyData.has(EntityConstant.KHOATAIKHOAN)
				? bodyData.get(EntityConstant.KHOATAIKHOAN).booleanValue()
				: false;

		if (Validator.isNull(tenDangNhap)) {
			throw new ActionException(MessageUtil.getVNMessageText("tendangnhap.empty"),
					HttpStatus.NOT_ACCEPTABLE.value());
		}

		if (tenDangNhap.equalsIgnoreCase("admin")) {
			throw new ActionException(MessageUtil.getVNMessageText("tendangnhap.not_allow"),
					HttpStatus.NOT_ACCEPTABLE.value());
		}

		NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap(tenDangNhap);

		if (nguoiDung != null) {
			throw new ActionException(MessageUtil.getVNMessageText("tendangnhap.exist"), HttpStatus.CONFLICT.value());
		}

		if (Validator.isNull(matKhau)) {
			throw new ActionException(MessageUtil.getVNMessageText("matkhau.empty"), HttpStatus.NOT_ACCEPTABLE.value());
		}

		if (Validator.isNull(hoVaTen)) {
			throw new ActionException(MessageUtil.getVNMessageText("hovaten.empty"), HttpStatus.NOT_ACCEPTABLE.value());
		}

		nguoiDung = new NguoiDung();

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

		KhoaDangKy khoaDangKy = createKhoaDangKy(RoleUtil.getTenVaiTro(nguoiDung));

		return nguoiDungService.addNguoiDung(nguoiDung, khoaDangKy);
	}

	@Override
	public NguoiDung updateNguoiDung(long id, String reqBody) throws Exception {

		NguoiDung nguoiDung = nguoiDungService.findByID(id);

		if (nguoiDung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoidung.not_exist"),
					HttpStatus.NOT_FOUND.value());
		}

		ObjectMapper mapper = new ObjectMapper();

		JsonNode bodyData = mapper.readTree(reqBody);

		// boolean quanTriHeThong = bodyData.has(EntityConstant.QUANTRIHETHONG)
		// ? bodyData.get(EntityConstant.QUANTRIHETHONG).booleanValue()
		// : false;

		String hoVaTen = bodyData.has(EntityConstant.HOVATEN) ? bodyData.get(EntityConstant.HOVATEN).textValue()
				: StringPool.BLANK;
		String chucDanh = bodyData.has(EntityConstant.CHUCDANH) ? bodyData.get(EntityConstant.CHUCDANH).textValue()
				: StringPool.BLANK;
		String soDienThoai = bodyData.has(EntityConstant.SODIENTHOAI)
				? bodyData.get(EntityConstant.SODIENTHOAI).textValue()
				: StringPool.BLANK;
		String email = bodyData.has(EntityConstant.EMAIL) ? bodyData.get(EntityConstant.EMAIL).textValue()
				: StringPool.BLANK;

		// TODO validate
		long diaBanCoSoId = bodyData.has(EntityConstant.DIABANCOSO_ID)
				? bodyData.get(EntityConstant.DIABANCOSO_ID).longValue()
				: 0;
		// TODO validate
		long coSoYTeId = bodyData.has(EntityConstant.COSOYTE_ID) ? bodyData.get(EntityConstant.COSOYTE_ID).longValue()
				: 0;

		if (Validator.isNull(hoVaTen)) {
			throw new ActionException(MessageUtil.getVNMessageText("hovaten.empty"), HttpStatus.NOT_ACCEPTABLE.value());
		}

		nguoiDung.setChucDanh(chucDanh);
		nguoiDung.setCoSoYTeId(coSoYTeId);
		nguoiDung.setDiaBanCoSoId(diaBanCoSoId);
		nguoiDung.setEmail(email);
		nguoiDung.setHoVaTen(hoVaTen);
//		 nguoiDung.setQuanTriHeThong(quanTriHeThong);
		nguoiDung.setSoDienThoai(soDienThoai);

		return nguoiDungService.updateNguoiDung(nguoiDung);
	}

	@Override
	public NguoiDung updateNguoiDung(long id, int quanTriHeThong) throws Exception {

		NguoiDung nguoiDung = nguoiDungService.findByID(id);

		if (nguoiDung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoidung.not_exist"),
					HttpStatus.NOT_FOUND.value());
		}
		
		nguoiDung.setQuanTriHeThong(quanTriHeThong);
		
		KhoaDangKy khoaDangKy = khoaDangKyService.findByNguoiDungID(nguoiDung.getId());
		
		if(khoaDangKy == null) {
			khoaDangKy = createKhoaDangKy(RoleUtil.getTenVaiTro(nguoiDung));
		}else {
			khoaDangKy.setPhamVi(RoleUtil.getTenVaiTro(nguoiDung));
		}

		return nguoiDungService.updateNguoiDung(nguoiDung, khoaDangKy);
	}

	@Override
	public void initNguoiDung() throws Exception {

		NguoiDung nguoiDung = nguoiDungService.findByTenDanNhap("admin");

		if (nguoiDung == null) {

			nguoiDung = new NguoiDung();
			nguoiDung.setChucDanh("Admin");
			nguoiDung.setCoSoYTeId(0);
			nguoiDung.setDiaBanCoSoId(0);
			nguoiDung.setEmail("admin@vaccom.vn");
			nguoiDung.setHoVaTen("Super");
			nguoiDung.setKhoaTaiKhoan(false);
			nguoiDung.setMatKhau(defaultPass);
			nguoiDung.setQuanTriHeThong(RoleUtil.SUPER_ADMIN);
			nguoiDung.setSoDienThoai("");
			nguoiDung.setTenDangNhap("admin");

			KhoaDangKy khoaDangKy = createKhoaDangKy(RoleUtil.getTenVaiTro(nguoiDung));

			nguoiDungService.addNguoiDung(nguoiDung, khoaDangKy);
		}

	}

	@Override
	public boolean deleteNguoiDung(long id) throws Exception {
		NguoiDung nguoiDung = nguoiDungService.findByID(id);

		if (nguoiDung == null || nguoiDung.getTenDangNhap().equals("admin")) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoidung.not_exist"),
					HttpStatus.NOT_FOUND.value());
		}

		KhoaDangKy khoaDangKy = khoaDangKyService.findByNguoiDungID(nguoiDung.getId());

		KhoaTruyCap khoaTruyCap = khoaTruyCapService.findByDoiTuong(nguoiDung.getTenDangNhap());

		nguoiDungService.deleteNguoiDung(nguoiDung.getId(), khoaDangKy != null ? khoaDangKy.getId() : 0,
				khoaTruyCap != null ? khoaTruyCap.getId() : 0);

		return true;
	}

	@Override
	public NguoiDung lockNguoiDung(long id, boolean isKhoaTaiKhoan) throws Exception {

		NguoiDung nguoiDung = nguoiDungService.findByID(id);

		if (nguoiDung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoidung.not_exist"),
					HttpStatus.NOT_FOUND.value());
		}

		if (nguoiDung.getTenDangNhap().equals("admin")) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoidung.lock.not_allow"),
					HttpStatus.NOT_ACCEPTABLE.value());
		}

		nguoiDung.setKhoaTaiKhoan(isKhoaTaiKhoan);

		return nguoiDungService.updateNguoiDung(nguoiDung);
	}

	@Override
	public NguoiDung findById(long id) {
		return nguoiDungService.findByID(id);
	}

	@Override
	public List<NguoiDung> findAll(int page, int size) {
		return nguoiDungService.findAll(page, size);
	}

	@Override
	public NguoiDung changeMatKhau(long id, String matKhauMoi) throws Exception {

		NguoiDung nguoiDung = nguoiDungService.findByID(id);

		if (nguoiDung == null) {
			throw new ActionException(MessageUtil.getVNMessageText("nguoidung.not_exist"),
					HttpStatus.NOT_FOUND.value());
		}

		if (Validator.isNull(matKhauMoi)) {
			throw new ActionException(MessageUtil.getVNMessageText("matkhau.empty"),
					HttpStatus.METHOD_NOT_ALLOWED.value());
		}

		nguoiDung.setMatKhau(new BCryptPasswordEncoder(encodingStrength).encode(matKhauMoi));

		return nguoiDungService.updateNguoiDung(nguoiDung);
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

}
