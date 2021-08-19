package org.vaccom.vcmgt.controler;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vaccom.vcmgt.action.NguoiDungAction;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.exception.NguoiDungException;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

@RestController
@RequestMapping("/rest/v1/app")
public class ApplicationControler {

	private final Logger _log = LoggerFactory.getLogger(ApplicationControler.class);

	@Autowired
	NguoiDungAction nguoiDungAction;

	@Autowired
	AuthenticationManager authenticationManager;

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

			if (e instanceof NguoiDungException) {
				String msg = e.getMessage();
				int status = ((NguoiDungException) e).getStatus();
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

			if (e instanceof NguoiDungException) {
				String msg = e.getMessage();
				int status = ((NguoiDungException) e).getStatus();
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

			if (e instanceof NguoiDungException) {
				String msg = e.getMessage();
				int status = ((NguoiDungException) e).getStatus();
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

			if (e instanceof NguoiDungException) {
				String msg = e.getMessage();
				int status = ((NguoiDungException) e).getStatus();
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

			if (e instanceof NguoiDungException) {
				String msg = e.getMessage();
				int status = ((NguoiDungException) e).getStatus();
				return ResponseEntity.status(status).body(msg);

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}
	}
}