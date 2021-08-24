package org.vaccom.vcmgt.controler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vaccom.vcmgt.action.CoSoYTeAction;
import org.vaccom.vcmgt.action.DanTocAction;
import org.vaccom.vcmgt.action.DiaBanCoSoAction;
import org.vaccom.vcmgt.action.DoiTuongAction;
import org.vaccom.vcmgt.action.DonViHanhChinhAction;
import org.vaccom.vcmgt.action.LichTiemChungAction;
import org.vaccom.vcmgt.action.MuiTiemChungAction;
import org.vaccom.vcmgt.action.NguoiDungAction;
import org.vaccom.vcmgt.action.NguoiTiemChungAction;
import org.vaccom.vcmgt.action.PhieuHenTiemAction;
import org.vaccom.vcmgt.action.QuocGiaAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.entity.CoSoYTe;
import org.vaccom.vcmgt.entity.DanToc;
import org.vaccom.vcmgt.entity.DiaBanCoSo;
import org.vaccom.vcmgt.entity.DoiTuong;
import org.vaccom.vcmgt.entity.LichTiemChung;
import org.vaccom.vcmgt.entity.MuiTiemChung;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.PhuongXa;
import org.vaccom.vcmgt.entity.QuanHuyen;
import org.vaccom.vcmgt.entity.QuocGia;
import org.vaccom.vcmgt.entity.TinhThanh;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.response.DataResponeBody;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/v1/app")
public class ApplicationControler {

	private final Logger _log = LoggerFactory.getLogger(ApplicationControler.class);

	@Autowired
	private NguoiDungAction nguoiDungAction;

	@Autowired
	private DoiTuongAction doiTuongAction;

	@Autowired
	private NguoiTiemChungAction nguoiTiemChungAction;

	@Autowired
	private DiaBanCoSoAction diaBanCoSoAction;

	@Autowired
	private CoSoYTeAction coSoYTeAction;

	@Autowired
	private LichTiemChungAction lichTiemChungAction;

	@Autowired
	private MuiTiemChungAction muiTiemChungAction;

	@Autowired
	private PhieuHenTiemAction phieuHenTiemAction;

	@Autowired
	private QuocGiaAction quocGiaAction;

	@Autowired
	private DanTocAction danTocAction;

	@Autowired
	private DonViHanhChinhAction donViHanhChinhAction;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/add/nguoidung", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> updateNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.add.error"));
			}

			nguoiDungAction.addNguoiDung(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoidung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/nguoidung/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.add.error"));
			}

			boolean result = nguoiDungAction.deleteNguoiDung(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("nguoidung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("nguoidung.delete.error");

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/lock/nguoidung/{id}/{isKhoaTaiKhoan}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> lockNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @PathVariable(value = "isKhoaTaiKhoan") boolean isKhoaTaiKhoan) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.lock.error"));
			}

			nguoiDungAction.lockNguoiDung(id, isKhoaTaiKhoan);

			String msg = MessageUtil.getVNMessageText("nguoidung.lock.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/changepwd/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> changepwdNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			String tenDangNhap = GetterUtil.getString(request.getAttribute("_TEN_DANG_NHAP"), StringPool.BLANK);

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			ObjectMapper mapper = new ObjectMapper();

			JsonNode bodyData = mapper.readTree(reqBody);

			String matKhauMoi = bodyData.has(EntityConstant.MATKHAUMOI)
					? bodyData.get(EntityConstant.MATKHAUMOI).textValue()
					: StringPool.BLANK;

			String matKhauCu = bodyData.has(EntityConstant.MATKHAUCU)
					? bodyData.get(EntityConstant.MATKHAUCU).textValue()
					: StringPool.BLANK;

			if (VaccomUtil.isQuanTriHeThong(vaiTro)) {

				if (reqId > 0 && reqId != id) {
					nguoiDungAction.changeMatKhau(id, matKhauMoi);

					String msg = MessageUtil.getVNMessageText("nguoidung.changepwd.success");

					return ResponseEntity.status(HttpStatus.OK).body(msg);
				}
			} else {
				if (reqId > 0 && reqId != id) {
					String msg = MessageUtil.getVNMessageText("nguoidung.changepwd.not_allow");
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body(msg);
				}
			}

			try {

				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(tenDangNhap, matKhauCu));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				nguoiDungAction.changeMatKhau(id, matKhauMoi);

				String msg = MessageUtil.getVNMessageText("nguoidung.changepwd.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);

			} catch (Exception e) {
				String msg = MessageUtil.getVNMessageText("nguoidung.changepwd.not_match");

				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/nguoidung/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			if (reqId <= 0 || reqId != id) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.update.error"));
			}

			nguoiDungAction.updateNguoiDung(reqId, reqBody);

			String msg = MessageUtil.getVNMessageText("nguoidung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/nguoidung", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("page") int page, @RequestParam("size") int size) {

		try {

			long total = nguoiDungAction.countAll();

			List<NguoiDung> lstNguoiDung = nguoiDungAction.findAll(page, size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstNguoiDung));

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/nguoidung/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") long id) {

		try {

			NguoiDung nguoiDung = nguoiDungAction.findById(id);

			nguoiDung.setMatKhau("");

			return ResponseEntity.status(HttpStatus.OK).body(nguoiDung);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/doituong", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSDoiTuong(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<DoiTuong> lstDoiTuong = doiTuongAction.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(lstDoiTuong);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/quocgia", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSQuocGia(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<QuocGia> lstQuocGia = quocGiaAction.finAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstQuocGia);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/dantoc", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSDanToc(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<DanToc> lstDanToc = danTocAction.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstDanToc);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/tinhthanh", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSTinhThanh(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<TinhThanh> lstTinhThanh = donViHanhChinhAction.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstTinhThanh);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/quanhuyen/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSQuanHuyen(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			List<QuanHuyen> lstQuanHuyen = donViHanhChinhAction.findByTinhThanhID(id);

			return ResponseEntity.status(HttpStatus.OK).body(lstQuanHuyen);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/phuongxa/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSPhuongXa(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			List<PhuongXa> lsPhuongXa = donViHanhChinhAction.findByQuanHuyenID(id);

			return ResponseEntity.status(HttpStatus.OK).body(lsPhuongXa);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/add/nguoitiemchung", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> updateNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			/*
			 * 
			 * HttpSession session = request.getSession(true);
			 * 
			 * String captcha = GetterUtil.getString(request.getParameter("captcha"));
			 * 
			 * if (Validator.isNull(captcha) ||
			 * !captcha.equals(GetterUtil.getString(session.getAttribute("captcha"),
			 * StringPool.BLANK))) { String msg =
			 * MessageUtil.getVNMessageText("nguoitiemchung.add.captcha_incorrect");
			 * 
			 * return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(msg); }
			 * 
			 */
			
			//TODO check role
			nguoiTiemChungAction.addNguoiTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/nguoitiemchung/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			nguoiTiemChungAction.updateNguoiTiemChung(id, reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/nguoitiemchung/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			boolean result = nguoiTiemChungAction.deleteNguoiTiemChung(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("nguoitiemchung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("nguoitiemchung.delete.error");

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/nguoitiemchung/tinhtrangdangky", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateTinhTrangDangKy(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			nguoiTiemChungAction.updateTrangThaiDangKy(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/nguoitiemchung", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteListNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			nguoiTiemChungAction.deleteNguoiTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.delete.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/nguoitiemchung", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("page") int page, @RequestParam("size") int size) {

		try {

			long total = nguoiTiemChungAction.countAll();

			List<NguoiTiemChung> lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(page, size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstNguoiTiemChung));

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/search/nguoitiemchung", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "cmtcccd", defaultValue = "") String cmtcccd,
			@RequestParam(name = "nhomdoituong", defaultValue = "-1") Integer nhomdoituong,
			@RequestParam("ngaydangki") String ngaydangki, @RequestParam("hovaten") String hovaten,
			@RequestParam(name = "diabancosoid", defaultValue = "-1") Long diabancosoid,
			@RequestParam("cosoytema") String cosoytema,
			@RequestParam(name = "tinhtrangdangky", defaultValue = "-1") Integer tinhtrangdangky,
			@RequestParam(name = "kiemtratrung", defaultValue = "-1") Integer kiemtratrung,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "30") int size) {

		try {

			long total = nguoiTiemChungAction.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten,
					diabancosoid, cosoytema, tinhtrangdangky, kiemtratrung);

			List<NguoiTiemChung> lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong,
					ngaydangki, hovaten, diabancosoid, cosoytema, tinhtrangdangky, kiemtratrung, page, size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstNguoiTiemChung));

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/add/diabancoso", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> updateDiaBanCoSo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			diaBanCoSoAction.addDiaBanCoSo(reqBody);

			String msg = MessageUtil.getVNMessageText("diabancoso.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/diabancoso/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateDiaBanCoSo(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			diaBanCoSoAction.updateDiaBanCoSo(id, reqBody);

			String msg = MessageUtil.getVNMessageText("diabancoso.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/diabancoso/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteDiaBanCoSo(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			boolean result = diaBanCoSoAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("diabancoso.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("diabancoso.delete.error");

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/diabancoso", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSDiaBanCoSo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("cosoyteid") long id) {

		try {

			List<DiaBanCoSo> lstDiaBanCoSo = new ArrayList<DiaBanCoSo>();

			if (id < 0) {
				lstDiaBanCoSo = diaBanCoSoAction.findAll();
			} else {
				lstDiaBanCoSo = diaBanCoSoAction.findByCoSoYTeId(id);
			}

			return ResponseEntity.status(HttpStatus.OK).body(lstDiaBanCoSo);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/add/cosoyte", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> updateCoSoYte(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			coSoYTeAction.addCoSoYTe(reqBody);

			String msg = MessageUtil.getVNMessageText("cosoyte.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/cosoyte/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateCoSoYte(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			coSoYTeAction.updateCoSoYTe(id, reqBody);

			String msg = MessageUtil.getVNMessageText("cosoyte.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/cosoyte/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteCoSoYTe(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			boolean result = coSoYTeAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("cosoyte.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cosoyte.delete.error");

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/cosoyte", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSCoSoYTe(HttpServletRequest request, HttpServletResponse response) {

		try {

			List<CoSoYTe> lstCoSoYTe = coSoYTeAction.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstCoSoYTe);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/add/phieuhentiem", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> updatePhieuHenTiem(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			phieuHenTiemAction.addPhieuHenTiem(reqBody);

			String msg = MessageUtil.getVNMessageText("phieuhentiem.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/phieuhentiem/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updatePhieuHenTiem(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			phieuHenTiemAction.updatePhieuHenTiem(id, reqBody);

			String msg = MessageUtil.getVNMessageText("phieuhentiem.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/phieuhentiem/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deletePhieuHenTiem(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			boolean result = phieuHenTiemAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("phieuhentiem.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("phieuhentiem.delete.error");

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/add/lichtiemchung", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> updateLichTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			lichTiemChungAction.addLichTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("lichtiemchung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/lichtiemchung/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateLichTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			lichTiemChungAction.updateLichTiemChung(id, reqBody);

			String msg = MessageUtil.getVNMessageText("lichtiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/lichtiemchung/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteLichTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			boolean result = lichTiemChungAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("lichtiemchung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("lichtiemchung.delete.error");

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/lichtiemchung", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSLichTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("page") int page, @RequestParam("size") int size) {

		try {

			long total = lichTiemChungAction.countAll();

			List<LichTiemChung> lstLichTiemChung = lichTiemChungAction.findAll(page, size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstLichTiemChung));

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/add/muitiemchung", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> updateMuiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			muiTiemChungAction.addMuiTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("muitiemchung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/muitiemchung/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateMuiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			muiTiemChungAction.updateMuiTiemChung(id, reqBody);

			String msg = MessageUtil.getVNMessageText("muitiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/muitiemchung/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteMuiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			boolean result = muiTiemChungAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("muitiemchung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("muitiemchung.delete.error");

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/muitiemchung/cosoyte/{cosoytema}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSMuiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("page") int page, @RequestParam("size") int size,
			@PathVariable("cosoytema") String coSoYTeMa) {

		try {

			long total = muiTiemChungAction.countCoSoYTeMa(coSoYTeMa);

			List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findCoSoYTeMa(coSoYTeMa, page, size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstMuiTiemChung));

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}
}