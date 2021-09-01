package org.vaccom.vcmgt.controler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import com.liferay.portal.kernel.util.Validator;
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
import org.vaccom.vcmgt.action.*;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.constant.MethodConstant;
import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.entity.*;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.response.DataResponeBody;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.PermissionUtil;
import org.vaccom.vcmgt.util.RoleUtil;
import org.vaccom.vcmgt.util.VaccomUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author trungnt
 *
 */

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

	@Autowired
	private GiayDiDuongAction giayDiDuongAction;

	@RequestMapping(value = "/add/nguoidung", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> addNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

//			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
//
//			if (!PermissionUtil.hasAddNguoiDung(vaiTro)) {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN)
//						.body(MessageUtil.getVNMessageText("nguoidung.add.permission_error"));
//			}

			if (!PermissionUtil.canAccessNguoiDung(vaiTro, null, reqBody, MethodConstant.CREATE)) {
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
			NguoiDung nguoiDung = nguoiDungAction.findById(id);

			if (nguoiDung == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(MessageUtil.getVNMessageText("nguoidung.not_found"));
			}

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!PermissionUtil.canAccessNguoiDung(vaiTro, nguoiDung, null, MethodConstant.DELETE)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.delete.permission_error"));
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
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			NguoiDung nguoiDung = nguoiDungAction.findById(id);

			if (nguoiDung == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(MessageUtil.getVNMessageText("nguoidung.not_found"));
			}

			if (!PermissionUtil.canAccessNguoiDung(vaiTro, nguoiDung, null, MethodConstant.LOCK)) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

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

			if (RoleUtil.isQuanTriHeThong(vaiTro)) {

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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			NguoiDung nguoiDung = nguoiDungAction.findById(id);

			if (nguoiDung == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(MessageUtil.getVNMessageText("nguoidung.not_found"));
			}

			if(vaiTro == null || reqId <= 0) {
				_log.warn("Not found vai tro or request id");
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("vaitro.notfound"));
			}

			vaiTro.setCurrentId(reqId);
			if (!PermissionUtil.canAccessNguoiDung(vaiTro,nguoiDung, reqBody, MethodConstant.UPDATE)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.update.permission_error"));
			}

//			if (vaiTro == null || reqId <= 0
//					|| ((RoleUtil.isCanBoDiaBan(vaiTro) || RoleUtil.isCanBoYTe(vaiTro) || RoleUtil.isCanBoUBND(vaiTro)
//							|| RoleUtil.isNguoiDung(vaiTro)) && reqId != id)
//					|| (RoleUtil.isQuanTriCoSo(vaiTro) && RoleUtil.isQuanTriHeThong(RoleUtil.getVaiTro(nguoiDung))
//							|| (RoleUtil.isQuanTriCoSo(vaiTro)
//									&& !RoleUtil.hasUpdateObjectPermission(vaiTro, nguoiDung)))) {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN)
//						.body(MessageUtil.getVNMessageText("nguoidung.update.permission_error"));
//			}

			nguoiDungAction.updateNguoiDung(id, reqBody);

			String msg = MessageUtil.getVNMessageText("nguoidung.update.success");

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

	@RequestMapping(value = "/update/nguoidung/{id}/vaitro/{vaitro}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateNguoiDungQuanTri(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id, @PathVariable(value = "vaitro") int vaiTro) {

		try {

			VaiTro reqVaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!PermissionUtil.canAccessNguoiDung(reqVaiTro, null, null, MethodConstant.UPDATE_ROLE)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.update.permission_error"));
			}

			nguoiDungAction.updateNguoiDung(id, vaiTro);

			String msg = MessageUtil.getVNMessageText("nguoidung.update.success");

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

	@RequestMapping(value = "/get/nguoidung", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSNguoiDung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("page") int page, @RequestParam("size") int size) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!PermissionUtil.canAccessNguoiDung(vaiTro, null, null, MethodConstant.GET)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.danhsach.permission_error"));
			}

			long total = 0;

			List<NguoiDung> lstNguoiDung = new ArrayList<NguoiDung>();
			//TODO check
			total = nguoiDungAction.countAll();
			lstNguoiDung = nguoiDungAction.findAll(page, size);

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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			if (vaiTro == null || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.chitiet.permission_error"));
			}

			if (reqId != id && !RoleUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoidung.chitiet.permission_error"));
			}

			NguoiDung nguoiDung = nguoiDungAction.findById(id);

			nguoiDung.setMatKhau(StringPool.BLANK);

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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (vaiTro == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("doituong.danhsach.permission_error"));
			}

			List<DoiTuong> lstDoiTuong = doiTuongAction.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(lstDoiTuong);

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

	@RequestMapping(value = "/get/quocgia", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSQuocGia(HttpServletRequest request, HttpServletResponse response) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (vaiTro == null) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (vaiTro == null) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (vaiTro == null) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (vaiTro == null) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (vaiTro == null) {
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

	/**
	 * @param request
	 * @param response
	 * @param reqBody  Vai tro tu can bo dia ban tro len
	 */
	@RequestMapping(value = "/add/nguoitiemchung", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> addNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.add.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.update.permission_error"));
			}
			
			*/

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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasQuanTriCoSoPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.delete.permission_error"));
			}
			*/

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

	@Deprecated
	@RequestMapping(value = "/update/nguoitiemchung/tinhtrangdangky", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateTinhTrangDangKy(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasQuanTriCoSoPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.update.permission_error"));
			}
			 */
			nguoiTiemChungAction.updateTrangThaiDangKy(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.update.success");

			if(nguoiTiemChung.getTinhTrangDangKi() == 1){
				HangChoThongBao hangChoThongBao = hangChoThongBaoAction.findByPhone_LoaiThongBao(ZaloNotificationUtil.convertPhoneNumber(nguoiTiemChung.getSoDienThoai()), ZaloConstant.Loai_XacNhan_NguoiTiemChung);
				_log.info("hangChoThongBao : " + hangChoThongBao);
				if(Validator.isNotNull(hangChoThongBao)){
					hangChoThongBao.setReady(true);
					hangChoThongBaoAction.update(hangChoThongBao);
				}
			}

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

	@RequestMapping(value = "/update/nguoitiemchung/duyetdangky", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateDuyetDangKy(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.duyetdangky.permission_error"));
			}
			
			*/

			nguoiTiemChungAction.duyetDangKyMoi(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.duyetdangky.success");

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

	@RequestMapping(value = "/update/nguoitiemchung/khoiphucdangky", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateKhoiPhucDangKy(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {
			
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.khoiphucdangky.permission_error"));
			}
			*/
			nguoiTiemChungAction.khoiPhucDangKy(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.khoiphucdangky.success");

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

	@RequestMapping(value = "/update/nguoitiemchung/huydangky", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateHuyDangKy(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.huydangky.permission_error"));
			}
			*/
			nguoiTiemChungAction.huyDangKyChinhThuc(reqBody);

			String msg = MessageUtil.getVNMessageText("nguoitiemchung.huydangky.success");

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
	public ResponseEntity<?> deleteNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String reqBody) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.delete.permission_error"));
			}
			*/
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

	@Deprecated
	@RequestMapping(value = "/get/nguoitiemchung/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSNguoiTiemChung(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("page") int page, @RequestParam("size") int size) {

		try {

			ObjectMapper mapper = new ObjectMapper();

			ArrayNode data = mapper.createArrayNode();

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasQuanTriHeThongPermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
			}
			*/
			long total = nguoiTiemChungAction.countAll();

			List<NguoiTiemChung> lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(page, size);

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

	@Deprecated
	@RequestMapping(value = "/get/nguoitiemchung", method = RequestMethod.GET, produces = "application/json")
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
			}
			*/
			long total = 0;

			List<NguoiTiemChung> lstNguoiTiemChung = new ArrayList<NguoiTiemChung>();

			if (RoleUtil.isQuanTriHeThong(vaiTro)) {
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

	@RequestMapping(value = "/get/danhsachdangkymoi", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchDanhSachDangKyMoi(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "cmtcccd", defaultValue = "") String cmtcccd,
			@RequestParam(name = "nhomdoituong", defaultValue = "-1") Integer nhomdoituong,
			@RequestParam("ngaydangki") String ngaydangki, @RequestParam("hovaten") String hovaten,
			@RequestParam(name = "diabancosoid", defaultValue = "-1") Long diabancosoid,
			@RequestParam("cosoytema") String cosoytema,
			@RequestParam(name = "kiemtratrung", defaultValue = "-1") Integer kiemtratrung,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "30") int size) {

		try {

			ObjectMapper mapper = new ObjectMapper();

			ArrayNode data = mapper.createArrayNode();

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
			}
			*/
			long total = 0;

			List<NguoiTiemChung> lstNguoiTiemChung = new ArrayList<NguoiTiemChung>();

			if (RoleUtil.isQuanTriHeThong(vaiTro)) {
				total = nguoiTiemChungAction.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten,
						diabancosoid, cosoytema, VaccomUtil.MOIDANGKY, kiemtratrung);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
						hovaten, diabancosoid, cosoytema, VaccomUtil.MOIDANGKY, kiemtratrung, page, size);

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
						nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						VaccomUtil.MOIDANGKY, kiemtratrung);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
						hovaten, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						VaccomUtil.MOIDANGKY, kiemtratrung, page, size);
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

	@RequestMapping(value = "/get/danhsachdangkychinhthuc", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchDanhSachDangKyChinhThuc(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "cmtcccd", defaultValue = "") String cmtcccd,
			@RequestParam(name = "nhomdoituong", defaultValue = "-1") Integer nhomdoituong,
			@RequestParam("ngaydangki") String ngaydangki, @RequestParam("hovaten") String hovaten,
			@RequestParam(name = "diabancosoid", defaultValue = "-1") Long diabancosoid,
			@RequestParam("cosoytema") String cosoytema,
			@RequestParam(name = "kiemtratrung", defaultValue = "-1") Integer kiemtratrung,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "30") int size) {

		try {

			ObjectMapper mapper = new ObjectMapper();

			ArrayNode data = mapper.createArrayNode();

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoDiaBanPermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
			}
			*/
			long total = 0;

			List<NguoiTiemChung> lstNguoiTiemChung = new ArrayList<NguoiTiemChung>();

			if (RoleUtil.isQuanTriHeThong(vaiTro)) {
				total = nguoiTiemChungAction.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten,
						diabancosoid, cosoytema, VaccomUtil.DANGKYCHINHTHUC, kiemtratrung);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
						hovaten, diabancosoid, cosoytema, VaccomUtil.DANGKYCHINHTHUC, kiemtratrung, page, size);

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
						nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						VaccomUtil.DANGKYCHINHTHUC, kiemtratrung);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
						hovaten, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						VaccomUtil.DANGKYCHINHTHUC, kiemtratrung, page, size);
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

	@RequestMapping(value = "/get/danhsachdahuy", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> searchDanhSachDaHuy(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "cmtcccd", defaultValue = "") String cmtcccd,
			@RequestParam(name = "nhomdoituong", defaultValue = "-1") Integer nhomdoituong,
			@RequestParam("ngaydangki") String ngaydangki, @RequestParam("hovaten") String hovaten,
			@RequestParam(name = "diabancosoid", defaultValue = "-1") Long diabancosoid,
			@RequestParam("cosoytema") String cosoytema,
			@RequestParam(name = "kiemtratrung", defaultValue = "-1") Integer kiemtratrung,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "30") int size) {

		try {

			ObjectMapper mapper = new ObjectMapper();

			ArrayNode data = mapper.createArrayNode();

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("nguoitiemchung.danhsach.permission_error"));
			}
			 */
			long total = 0;

			List<NguoiTiemChung> lstNguoiTiemChung = new ArrayList<NguoiTiemChung>();

			if (RoleUtil.isQuanTriHeThong(vaiTro)) {
				total = nguoiTiemChungAction.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten,
						diabancosoid, cosoytema, VaccomUtil.XOADANGKY, kiemtratrung);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
						hovaten, diabancosoid, cosoytema, VaccomUtil.XOADANGKY, kiemtratrung, page, size);

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
						nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						VaccomUtil.XOADANGKY, kiemtratrung);

				lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
						hovaten, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
						VaccomUtil.XOADANGKY, kiemtratrung, page, size);
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!RoleUtil.isQuanTriHeThong(vaiTro)) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!RoleUtil.isQuanTriHeThong(vaiTro)) {
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
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!RoleUtil.isQuanTriHeThong(vaiTro)) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (vaiTro == null) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!RoleUtil.isQuanTriHeThong(vaiTro)) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!RoleUtil.isQuanTriHeThong(vaiTro)) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!RoleUtil.isQuanTriHeThong(vaiTro)) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (vaiTro == null) {
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.add.permission_error"));
			}
			*/
			phieuHenTiemAction.addPhieuHenTiem(reqBody);

			String msg = MessageUtil.getVNMessageText("phieuhentiem.add.success");
			LichTiemChung lichTiemChung = lichTiemChungAction.findById(phieuHenTiem.getLichTiemChungId());
			NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(phieuHenTiem.getNguoiTiemChungId());
			CoSoYTe coSoYTe = coSoYTeAction.findById(lichTiemChung.getCoSoYTeId());



			//Value
			String HoVaTen = nguoiTiemChung.getHoVaTen();
			String CoSoYTe = coSoYTe.getTenCoSo();
			String NgayTiemChung = phieuHenTiem.getNgayHenTiem();
			String GioTiemChung = phieuHenTiem.getGioHenTiem();
			String DonViTiem = CoSoYTe;
			String DiaDiem = coSoYTe.getDiaChiCoSo();
			String LoaiThuocTiem = lichTiemChung.getLoaiThuocTiem();
			String LinkLichHenTiem = null;





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
			
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.update.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.update.permission_error"));
			}
			*/
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
			@PathVariable(value = "qrcode") String qrcode, @RequestBody String reqBody) {

		try {
			/*
			 * VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			 * 
			 * if (vaiTro == null) { return ResponseEntity.status(HttpStatus.FORBIDDEN)
			 * .body(MessageUtil.getVNMessageText("phieuhentiem.checkin.permission_error"));
			 * }
			 */

			boolean result = phieuHenTiemAction.checkin(qrcode, reqBody);

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

	@RequestMapping(value = "/get/phieuhentiem/{tinhtrangxacnhan}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPhieuHenTiem(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "tinhtrangxacnhan") int tinhTrangXacNhan,
			@RequestParam(value = "lichtiemchungid") long lichTiemChungId,
			@RequestParam(value = "catiemchungid") long caTiemChungId, @RequestParam("page") int page,
			@RequestParam("size") int size) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.danhsach.permission_error"));
			}
			*/
			long total = phieuHenTiemAction.countPhieuHenTiem(lichTiemChungId, caTiemChungId, tinhTrangXacNhan);

			List<PhieuHenTiem> lstPhieuHenTiem = phieuHenTiemAction.searchPhieuHenTiem(lichTiemChungId, caTiemChungId,
					tinhTrangXacNhan, page, size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstPhieuHenTiem));

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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("phieuhentiem.delete.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.add.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.update.permission_error"));
			}
			*/
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

	@RequestMapping(value = "/update/lichtiemchung/{id}/donglichtiem", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateDongLichTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") long id) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.update.permission_error"));
			}
			*/
			lichTiemChungAction.dongLichTiemChung(id);

			String msg = MessageUtil.getVNMessageText("lichtiemchung.donglichtiem.success");

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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.delete.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.danhsach.permission_error"));
			}
			*/
			if (RoleUtil.isQuanTriHeThong(vaiTro)) {
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

	@RequestMapping(value = "/get/lichtiemchung/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getLichTiemChung(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") long id) {

		try {

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro) || reqId <= 0) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("lichtiemchung.chitiet.permission_error"));
			}
			*/
			LichTiemChung lichTiemChung = lichTiemChungAction.findById(id);

			if (lichTiemChung == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(MessageUtil.getVNMessageText("lichtiemchung.chitiet.not_found"));
			}

			NguoiDung nguoiDung = nguoiDungAction.findById(reqId);

			if (nguoiDung == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(MessageUtil.getVNMessageText("lichtiemchung.chitiet.error"));
			}

			if (RoleUtil.isQuanTriHeThong(vaiTro)) {
				return ResponseEntity.status(HttpStatus.OK).body(lichTiemChung);
			} else {
				if (lichTiemChung.getCoSoYTeId() == nguoiDung.getCoSoYTeId()) {
					return ResponseEntity.status(HttpStatus.OK).body(lichTiemChung);
				} else {
					return ResponseEntity.status(HttpStatus.FORBIDDEN)
							.body(MessageUtil.getVNMessageText("lichtiemchung.chitiet.permission_error"));
				}
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
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.add.permission_error"));
			}
			*/
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
			
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.update.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.delete.permission_error"));
			}
			*/
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

	@RequestMapping(value = "/get/muitiemchung/nguoitiemchung/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSMuiTiemChung(HttpServletRequest request, HttpServletResponse response,

			@PathVariable("id") long id) {

		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasNguoiDungPermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.danhsach.permission_error"));
			}
			*/
			long total = muiTiemChungAction.countByNguoiTiemChungId(id);

			List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByNguoiTiemChungId(id);

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

	@RequestMapping(value = "/get/muitiemchung/{muitiemchungid}/nguoitiemchung/{nguoitiemchungid}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDSMuiTiemChung(HttpServletRequest request, HttpServletResponse response,

			@PathVariable("muitiemchungid") long muitiemchungid,
			@PathVariable("nguoitiemchungid") long nguoitiemchungid) {

		try {

			long reqId = GetterUtil.getLong(request.getAttribute("_ID"), 0);

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasNguoiDungPermission(vaiTro) || reqId != nguoitiemchungid) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.danhsach.permission_error"));
			}
			*/
			MuiTiemChung muiTiemChung = muiTiemChungAction.findById(muitiemchungid);

			return ResponseEntity.status(HttpStatus.OK).body(muiTiemChung);

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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("muitiemchung.danhsach.permission_error"));
			}
			*/
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
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("catiemchung.add.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("catiemchung.update.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("catiem.delete.permission_error"));
			}
			*/
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

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");
			//TODO check permission
			/*
			if (!RoleUtil.hasCanBoYTePermission(vaiTro)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("catiemchung.danhsach.permission_error"));
			}
			*/
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


	@RequestMapping(value = "/add/giaydiduong", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> addGiayDiDuong(HttpServletRequest request, HttpServletResponse response,
											@RequestBody GiayDiDuongDto giayDiDuongDto) {
		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!PermissionUtil.canAccessGiayDiDuong(vaiTro, null, null, MethodConstant.CREATE)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("giaydiduong.add.permission_error"));
			}

			giayDiDuongDto.uyBanNhanDanID = (int) vaiTro.getUyBanNhanDanId();
			giayDiDuongAction.create(giayDiDuongDto);

			String msg = MessageUtil.getVNMessageText("giaydiduong.add.success");

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

	@RequestMapping(value = "/get/giaydiduong/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDsGiayDiDuong(HttpServletRequest request, HttpServletResponse response,
											  @PathVariable(value = "id") int id,
											  @RequestParam("status") int trangthai,
											  @RequestParam("page") int page, @RequestParam("size") int size) {

		try {
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!PermissionUtil.canAccessGiayDiDuong(vaiTro, null, null, MethodConstant.GET)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("giaydiduong.danhsach.permission_error"));
			}
			List<GiayDiDuong> lstGiayDiDuong = new ArrayList<>();

			if(id > 0) {
				GiayDiDuong giayDiDuong = giayDiDuongAction.findById(id);
				if(giayDiDuong == null) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(MessageUtil.getVNMessageText("giayDiDuong.not_found"));
				}
				lstGiayDiDuong.add(giayDiDuong);

				return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(1, lstGiayDiDuong));
			}

			long total = 0;

			total = giayDiDuongAction.countByUyBanNhanDanIdAndStatus((int)vaiTro.getUyBanNhanDanId(), trangthai);
			lstGiayDiDuong = giayDiDuongAction.findByUyBanNhanDanIdAndStatus((int)vaiTro.getUyBanNhanDanId(), trangthai, page, size);

			return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstGiayDiDuong));

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

	@RequestMapping(value = "/get/giaydiduong-maqr", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getGiayDiDuongMaQr(@RequestParam("maQr") String maQr) {

		try {
			GiayDiDuong giayDiDuong = giayDiDuongAction.findByMaQr(maQr);

			if(giayDiDuong == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(MessageUtil.getVNMessageText("giayDiDuong.not_found"));
			}

			return ResponseEntity.status(HttpStatus.OK).body(giayDiDuong);

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


	@RequestMapping(value = "/update/giaydiduong/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateGiayDiDuong(HttpServletRequest request, HttpServletResponse response,
											   @PathVariable(value = "id") long id, @RequestBody GiayDiDuongDto giayDiDuongDto) {
		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			GiayDiDuong giayDiDuong = giayDiDuongAction.findById(id);

			if (giayDiDuong == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(MessageUtil.getVNMessageText("giayDiDuong.not_found"));
			}

			if (!PermissionUtil.canAccessGiayDiDuong(vaiTro, giayDiDuong, null, MethodConstant.UPDATE)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("giaydiduong.update.permission_error"));
			}

			giayDiDuongAction.update(giayDiDuong, giayDiDuongDto);

			String msg = MessageUtil.getVNMessageText("giaydiduong.update.success");

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

	@RequestMapping(value = "/delete/giaydiduong/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteGiayDiDuong(HttpServletRequest request, HttpServletResponse response,
											   @PathVariable(value = "id") long id, @RequestBody GiayDiDuongDto giayDiDuongDto) {
		try {

			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			GiayDiDuong giayDiDuong = giayDiDuongAction.findById(id);

			if (giayDiDuong == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(MessageUtil.getVNMessageText("giayDiDuong.not_found"));
			}

			if (!PermissionUtil.canAccessGiayDiDuong(vaiTro, giayDiDuong, null, MethodConstant.UPDATE)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("giaydiduong.delete.permission_error"));
			}

			giayDiDuongAction.delete(giayDiDuong);

			String msg = MessageUtil.getVNMessageText("giaydiduong.delete.success");

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

}