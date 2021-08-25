package org.vaccom.vcmgt.controler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import org.vaccom.vcmgt.action.CaTiemChungAction;
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
import org.vaccom.vcmgt.entity.PhieuHenTiem;
import org.vaccom.vcmgt.entity.CaTiemChung;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/v1/app")
public class ApplicationControler {

	private final Log _log = LogFactory.getLog(ApplicationControler.class);

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
	private CaTiemChungAction caTiemChungAction;

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
	public ResponseEntity<?> addNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.add.permission_error"));
			}

			nguoiDungAction.addNguoiDung(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoidung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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
						.body(MessageUtil.getVNMessageText("nguoidung.add.permission_error"));
			}

			boolean result = nguoiDungAction.deleteNguoiDung(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("nguoidung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("nguoidung.delete.error");

		} catch (Exception e) {

			_log.error(e);

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
						.body(MessageUtil.getVNMessageText("nguoidung.lock.permission_error"));
			}

			nguoiDungAction.lockNguoiDung(id, isKhoaTaiKhoan);

			String msg = MessageUtil.getVNMessageText("nguoidung.lock.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			_log.error(e);

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
						.body(MessageUtil.getVNMessageText("nguoidung.update.permission_error"));
			}

			nguoiDungAction.updateNguoiDung(reqId, reqBody);

			String msg = MessageUtil.getVNMessageText("nguoidung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e);

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/nguoidung/{id}/quantri/{isquantri}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateNguoiDungQuanTri(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @PathVariable(value = "isquantri") boolean isquantri) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.update.permission_error"));
			}

			nguoiDungAction.updateNguoiDung(id, isquantri);

			String msg = MessageUtil.getVNMessageText("nguoidung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.danhsach.permission_error"));
			}

			long total = nguoiDungAction.countAll();

			List<NguoiDung> lstNguoiDung = nguoiDungAction.findAll(page, size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstNguoiDung));

		} catch (Exception e) {
			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.chitiet.permission_error"));
			}

			NguoiDung nguoiDung = nguoiDungAction.findById(id);

			nguoiDung.setMatKhau("");

			return ResponseEntity.status(HttpStatus.OK).body(nguoiDung);

		} catch (Exception e) {
			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (Validator.isNull(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("doituong.danhsach.permission_error"));
			}

			List<DoiTuong> lstDoiTuong = doiTuongAction.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstDoiTuong);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (Validator.isNull(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("quocgia.danhsach.permission_error"));
			}

			List<QuocGia> lstQuocGia = quocGiaAction.finAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstQuocGia);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (Validator.isNull(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("dantoc.danhsach.permission_error"));
			}

			List<DanToc> lstDanToc = danTocAction.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstDanToc);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (Validator.isNull(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("tinhthanh.danhsach.permission_error"));
			}

			List<TinhThanh> lstTinhThanh = donViHanhChinhAction.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstTinhThanh);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (Validator.isNull(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("quanhuyen.danhsach.permission_error"));
			}

			List<QuanHuyen> lstQuanHuyen = donViHanhChinhAction.findByTinhThanhID(id);

			return ResponseEntity.status(HttpStatus.OK).body(lstQuanHuyen);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (Validator.isNull(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phuongxa.danhsach.permission_error"));
			}

			List<PhuongXa> lsPhuongXa = donViHanhChinhAction.findByQuanHuyenID(id);

			return ResponseEntity.status(HttpStatus.OK).body(lsPhuongXa);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.add.permission_error"));
			}

			nguoiTiemChungAction.addNguoiTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.update.permission_error"));
			}

			nguoiTiemChungAction.updateNguoiTiemChung(id, reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.delete.permission_error"));
			}

			boolean result = nguoiTiemChungAction.deleteNguoiTiemChung(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("nguoitiemchung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("nguoitiemchung.delete.error");

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.update.permission_error"));
			}

			nguoiTiemChungAction.updateTrangThaiDangKy(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.delete.permission_error"));
			}

			nguoiTiemChungAction.deleteNguoiTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.delete.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			ObjectMapper mapper = new ObjectMapper();

			ArrayNode data = mapper.createArrayNode();

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
			}

			long total = 0;

			List<NguoiTiemChung> lstNguoiTiemChung = new ArrayList<NguoiTiemChung>();

			if (VaccomUtil.isQuanTriHeThong(vaiTro)) {
				total = nguoiTiemChungAction.countAll();

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(page, size);

			} else {
				NguoiDung nguoiDung = nguoiDungAction.findById(reqId);

				if (nguoiDung == null) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN)
							.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
				}

				String coSoYTeMa = StringPool.BLANK;

				if (nguoiDung.getCoSoYTeId() > 0) {
					CoSoYTe coSoYTe = coSoYTeAction.findById(nguoiDung.getCoSoYTeId());
					coSoYTeMa = coSoYTe != null ? coSoYTe.getMaCoSo() : StringPool.BLANK;
				}

				total = nguoiTiemChungAction.countNguoiTiemChung(StringPool.BLANK, -1, StringPool.BLANK,
						StringPool.BLANK, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						-1, -1);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(StringPool.BLANK, -1, StringPool.BLANK,
						StringPool.BLANK, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						-1, -1, page, size);

			}

			lstNguoiTiemChung.forEach(nguoiTiemChung -> {
				// JsonNode node = mapper.valueToTree(nguoiTiemChung);

				List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByNguoiTiemChungId(nguoiTiemChung.getId());

				ArrayNode jsonArrayObj = mapper.convertValue(lstMuiTiemChung, ArrayNode.class);

				ObjectNode node = mapper.convertValue(nguoiTiemChung, ObjectNode.class);

				node.put("muiTiemChung", jsonArrayObj);

				List<PhieuHenTiem> lstPhieuHenTiem = phieuHenTiemAction.findByNguoiTiemChungId(nguoiTiemChung.getId());

				jsonArrayObj = mapper.convertValue(lstPhieuHenTiem, ArrayNode.class);

				node.put("phieuHenTiem", jsonArrayObj);

				data.add(node);
			});

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, data));

		} catch (Exception e) {

			_log.error(e);

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

			ObjectMapper mapper = new ObjectMapper();

			ArrayNode data = mapper.createArrayNode();

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
			}

			long total = 0;

			List<NguoiTiemChung> lstNguoiTiemChung = new ArrayList<NguoiTiemChung>();

			if (VaccomUtil.isQuanTriHeThong(vaiTro)) {
				total = nguoiTiemChungAction.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten,
						diabancosoid, cosoytema, tinhtrangdangky, kiemtratrung);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
						hovaten, diabancosoid, cosoytema, tinhtrangdangky, kiemtratrung, page, size);

			} else {
				NguoiDung nguoiDung = nguoiDungAction.findById(reqId);

				if (nguoiDung == null) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN)
							.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
				}

				String coSoYTeMa = StringPool.BLANK;

				if (nguoiDung.getCoSoYTeId() > 0) {
					CoSoYTe coSoYTe = coSoYTeAction.findById(nguoiDung.getCoSoYTeId());
					coSoYTeMa = coSoYTe != null ? coSoYTe.getMaCoSo() : StringPool.BLANK;
				}

				total = nguoiTiemChungAction.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten,
						nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa, tinhtrangdangky,
						kiemtratrung);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
						hovaten, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						tinhtrangdangky, kiemtratrung, page, size);

			}

			lstNguoiTiemChung.forEach(nguoiTiemChung -> {
				// JsonNode node = mapper.valueToTree(nguoiTiemChung);

				List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByNguoiTiemChungId(nguoiTiemChung.getId());

				ArrayNode jsonArrayObj = mapper.convertValue(lstMuiTiemChung, ArrayNode.class);

				ObjectNode node = mapper.convertValue(nguoiTiemChung, ObjectNode.class);

				node.put("muiTiemChung", jsonArrayObj);

				List<PhieuHenTiem> lstPhieuHenTiem = phieuHenTiemAction.findByNguoiTiemChungId(nguoiTiemChung.getId());

				jsonArrayObj = mapper.convertValue(lstPhieuHenTiem, ArrayNode.class);

				node.put("phieuHenTiem", jsonArrayObj);

				data.add(node);
			});

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, data));

		} catch (Exception e) {
			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("diabancoso.add.permission_error"));
			}

			diaBanCoSoAction.addDiaBanCoSo(reqBody);

			String msg = MessageUtil.getVNMessageText("diabancoso.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {
			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("diabancoso.update.permission_error"));
			}

			diaBanCoSoAction.updateDiaBanCoSo(id, reqBody);

			String msg = MessageUtil.getVNMessageText("diabancoso.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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
			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("diabancoso.delete.permission_error"));
			}

			boolean result = diaBanCoSoAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("diabancoso.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("diabancoso.delete.error");

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (Validator.isNull(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("diabancoso.danhsach.permission_error"));
			}

			List<DiaBanCoSo> lstDiaBanCoSo = new ArrayList<DiaBanCoSo>();

			if (id < 0) {
				lstDiaBanCoSo = diaBanCoSoAction.findAll();

			} else {
				lstDiaBanCoSo = diaBanCoSoAction.findByCoSoYTeId(id);
			}

			long total = lstDiaBanCoSo.size();

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstDiaBanCoSo));

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("cosoyte.add.permission_error"));
			}

			coSoYTeAction.addCoSoYTe(reqBody);

			String msg = MessageUtil.getVNMessageText("cosoyte.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("cosoyte.update.permission_error"));
			}

			coSoYTeAction.updateCoSoYTe(id, reqBody);

			String msg = MessageUtil.getVNMessageText("cosoyte.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("cosoyte.delete.permission_error"));
			}

			boolean result = coSoYTeAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("cosoyte.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cosoyte.delete.error");

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (Validator.isNull(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("cosoyte.danhsach.permission_error"));
			}

			List<CoSoYTe> lstCoSoYTe = coSoYTeAction.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstCoSoYTe);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.add.permission_error"));
			}

			phieuHenTiemAction.addPhieuHenTiem(reqBody);

			String msg = MessageUtil.getVNMessageText("phieuhentiem.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.update.permission_error"));
			}

			phieuHenTiemAction.updatePhieuHenTiem(id, reqBody);

			String msg = MessageUtil.getVNMessageText("phieuhentiem.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/phieuhentiem/tinhtrangxacnhan", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateTinhTrangXacNhan(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.update.permission_error"));
			}

			phieuHenTiemAction.updateTinhTrangXacNhan(reqBody);

			String msg = MessageUtil.getVNMessageText("phieuhentiem.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	@RequestMapping(value = "/update/phieuhentiem/checkin/{qrcode}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> checkin(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "qrcode") String qrcode) {

		try {

			boolean result = phieuHenTiemAction.checkin(qrcode);

			if (result) {
				String msg = MessageUtil.getVNMessageText("phieuhentiem.checkin.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			} else {
				String msg = MessageUtil.getVNMessageText("phieuhentiem.checkin.error");

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
			}

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.delete.permission_error"));
			}

			boolean result = phieuHenTiemAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("phieuhentiem.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("phieuhentiem.delete.error");

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.add.permission_error"));
			}

			lichTiemChungAction.addLichTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("lichtiemchung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.update.permission_error"));
			}

			lichTiemChungAction.updateLichTiemChung(id, reqBody);

			String msg = MessageUtil.getVNMessageText("lichtiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.delete.permission_error"));
			}

			boolean result = lichTiemChungAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("lichtiemchung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("lichtiemchung.delete.error");

		} catch (Exception e) {

			_log.error(e);

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

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.danhsach.permission_error"));
			}

			if (VaccomUtil.isQuanTriHeThong(vaiTro)) {
				long total = lichTiemChungAction.countAll();

				List<LichTiemChung> lstLichTiemChung = lichTiemChungAction.findAll(page, size);

				return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstLichTiemChung));
			} else {

				NguoiDung nguoiDung = nguoiDungAction.findById(reqId);

				if (nguoiDung == null) {
					return ResponseEntity.status(HttpStatus.FORBIDDEN)
							.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
				}

				long total = lichTiemChungAction.countCoSoYTeId(nguoiDung.getCoSoYTeId());

				List<LichTiemChung> lstLichTiemChung = lichTiemChungAction.findByCoSoYTeId(nguoiDung.getCoSoYTeId(),
						page, size);

				return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstLichTiemChung));
			}

		} catch (Exception e) {

			_log.error(e);

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
			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.add.permission_error"));
			}

			muiTiemChungAction.addMuiTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("muitiemchung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.update.permission_error"));
			}

			muiTiemChungAction.updateMuiTiemChung(id, reqBody);

			String msg = MessageUtil.getVNMessageText("muitiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.delete.permission_error"));
			}

			boolean result = muiTiemChungAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("muitiemchung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("muitiemchung.delete.error");

		} catch (Exception e) {

			_log.error(e);

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

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.danhsach.permission_error"));
			}

			CoSoYTe coSoYTe = coSoYTeAction.findByMaCoSoYTe(coSoYTeMa);

			long total = 0;

			List<MuiTiemChung> lstMuiTiemChung = new ArrayList<>();

			if (coSoYTe != null) {
				total = muiTiemChungAction.countByCoSoYTeId(coSoYTe.getId());

				lstMuiTiemChung = muiTiemChungAction.findByCoSoYTeId(coSoYTe.getId(), page, size);
			}

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstMuiTiemChung));

		} catch (Exception e) {

			_log.error(e);

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	@RequestMapping(value = "/add/catiemchung", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> updateCaTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {
			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("catiemchung.add.permission_error"));
			}

			caTiemChungAction.addCaTiemChung(reqBody);

			String msg = MessageUtil.getVNMessageText("catiemchung.add.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/update/catiemchung/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateCaTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @RequestBody String reqBody) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("catiemchung.update.permission_error"));
			}

			caTiemChungAction.updateCaTiemChung(id, reqBody);

			String msg = MessageUtil.getVNMessageText("catiemchung.update.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);

		} catch (Exception e) {

			_log.error(e);

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/delete/catiemchung/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteCaTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("catiem.delete.permission_error"));
			}

			boolean result = caTiemChungAction.deleteById(id);

			if (result) {
				String msg = MessageUtil.getVNMessageText("catiemchung.delete.success");

				return ResponseEntity.status(HttpStatus.OK).body(msg);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("catiemchung.delete.error");

		} catch (Exception e) {

			_log.error(e);

			if (e instanceof ActionException) {
				String msg = e.getMessage();
				int status = ((ActionException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}

	@RequestMapping(value = "/get/catiemchung", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSCaTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam(name = "lichtiemchungid", defaultValue = "-1") long lichTiemChungId,
			@RequestParam(name = "diabancosoid", defaultValue = "-1") long diaBanCoSoId) {

		try {

			String vaiTro = GetterUtil.getString(request.getAttribute("_VAI_TRO"), StringPool.BLANK);

			if (!VaccomUtil.hasUpdatePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.danhsach.permission_error"));
			}

			long total = caTiemChungAction.countCaTiemChung(lichTiemChungId, diaBanCoSoId);

			List<CaTiemChung> lstMuiTiemChung = caTiemChungAction.searchCaTiemChung(lichTiemChungId, diaBanCoSoId, page,
					size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstMuiTiemChung));

		} catch (Exception e) {

			_log.error(e);

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