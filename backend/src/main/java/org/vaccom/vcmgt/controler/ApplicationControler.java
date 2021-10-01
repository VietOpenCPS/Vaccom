package org.vaccom.vcmgt.controler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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
import org.vaccom.vcmgt.constant.ZaloConstant;
import org.vaccom.vcmgt.dto.*;
import org.vaccom.vcmgt.entity.*;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.response.DataResponeBody;
import org.vaccom.vcmgt.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author trungnt
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/v1/app")
public class ApplicationControler {

    private final Log _log = LogFactory.getLog(ApplicationControler.class);

    @Value("${server.domain.url}")
    private String domainUrl;


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

    @Autowired
    private HangChoThongBaoAction hangChoThongBaoAction;

    @Autowired
    private UyBanNhanDanAction uyBanNhanDanAction;

    @Autowired
    private ThuocTiemAction thuocTiemAction;

    @Autowired
    private CongDanAction congDanAction;


    @RequestMapping(value = "/add/nguoidung", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> addNguoiDung(HttpServletRequest request, HttpServletResponse response,
                                          @RequestBody String reqBody) {

        try {

            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            if (!PermissionUtil.canAccessNguoiDung(vaiTro, null, reqBody, MethodConstant.CREATE)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(MessageUtil.getVNMessageText("nguoidung.add.permission_error"));
            }


            NguoiDung nguoiDung = nguoiDungAction.addNguoiDung(reqBody);

            String msg = MessageUtil.getVNMessageText("nguoidung.add.success");

//            System.out.println(nguoiDung);
//            if (Validator.isNotNull(nguoiDung)) {
//
//                //zalo notification
//                ObjectMapper mapper = new ObjectMapper();
//                JsonNode bodyData = mapper.readTree(reqBody);
//                String matKhau = bodyData.get(EntityConstant.MATKHAU).textValue();
//                System.out.println(matKhau);
//
//                ObjectNode template_data = mapper.createObjectNode();
//                template_data.put(ZaloConstant.HoVaTen, nguoiDung.getHoVaTen());
//                template_data.put(ZaloConstant.TenDangNhap, nguoiDung.getTenDangNhap());
//                template_data.put(ZaloConstant.MatKhau, matKhau);
//                System.out.println(matKhau);
//                System.out.println(nguoiDung.getHoVaTen());
//                System.out.println(nguoiDung.getTenDangNhap());
//                hangChoThongBaoAction.addHangChoThongBao(template_data.toString(), nguoiDung, false, ZaloConstant.Loai_XacNhan_NguoiTiemChung);
//
//            }

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

            if (vaiTro == null || reqId <= 0) {
                _log.warn("Not found vai tro or request id");
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(MessageUtil.getVNMessageText("vaitro.notfound"));
            }

            vaiTro.setCurrentId(reqId);
            if (!PermissionUtil.canAccessNguoiDung(vaiTro, nguoiDung, reqBody, MethodConstant.UPDATE)) {
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
            NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.addNguoiTiemChung(reqBody);

            if (Validator.isNotNull(nguoiTiemChung)) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode bodyData = mapper.readTree(reqBody);
                int lanTiem = bodyData.has(EntityConstant.LANTIEM)
                        ? bodyData.get(EntityConstant.LANTIEM).intValue()
                        : 0;

                if (lanTiem > 0) {
                    String loaiThuocTiem = bodyData.has(EntityConstant.LOAITHUOCTIEM)
                            ? bodyData.get(EntityConstant.LOAITHUOCTIEM).textValue()
                            : StringPool.BLANK;

                    String ngayTiem = bodyData.has(EntityConstant.NGAYTIEMCHUNG)
                            ? bodyData.get(EntityConstant.NGAYTIEMCHUNG).textValue()
                            : StringPool.BLANK;
                    MuiTiemChung muiTiemChung = new MuiTiemChung();
                    muiTiemChung.setLoaiThuocTiem(loaiThuocTiem);
                    muiTiemChung.setNgayTiemChung(ngayTiem);
                    muiTiemChung.setCmtcccd(nguoiTiemChung.getCmtcccd());
                    muiTiemChung.setCongDanID(nguoiTiemChung.getCongDanID());
                    muiTiemChung.setLanTiem(lanTiem);
                    muiTiemChung.setHoVaTen(nguoiTiemChung.getHoVaTen());
                    muiTiemChung.setCoSoYTeMa(nguoiTiemChung.getCoSoYTeMa());
                    muiTiemChung.setNgaySinh(nguoiTiemChung.getNgaySinh());
                    muiTiemChung.setCoSoYTeTen(nguoiTiemChung.getCoSoYTeTen());
                    muiTiemChung.setDiaDiemTiemChung(nguoiTiemChung.getCoSoYTeTen());

                    muiTiemChungAction.create(muiTiemChung);
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(nguoiTiemChung);

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
            NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(id);
            long congDanId = nguoiTiemChung.getCongDanID();

            boolean result = nguoiTiemChungAction.deleteNguoiTiemChung(id);

            if (result) {
                List<MuiTiemChung> muiTiemChungs = muiTiemChungAction.findByCongDan_ID(congDanId);

                if (Validator.isNotNull(muiTiemChungs)) {
                    for (MuiTiemChung muitiemchung : muiTiemChungs) {
                        muiTiemChungAction.deleteById(muitiemchung.getId());
                    }
                }
                ;
//                CongDan congDan = congDanAction.findByCongDanId(congDanId);
//                _log.info("congDan :" + congDan);
//                if(Validator.isNotNull(congDan)){
//                    congDanAction.deleteById(congDan.getId());
//                }

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

            ObjectMapper mapper = new ObjectMapper();
            JsonNode bodyData = mapper.readTree(reqBody);
            boolean isAutoAccept = bodyData.has(EntityConstant.IS_AUTO_ACCEPT) ? bodyData.get(EntityConstant.IS_AUTO_ACCEPT).booleanValue() : false;

            if (isAutoAccept) {
                if (!RoleUtil.isQuanTriHeThong(vaiTro) && !RoleUtil.isQuanTriCoSo(vaiTro)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(MessageUtil.getVNMessageText("nguoitiemchung.duyetdangky.permission_error"));
                }
            }

            int total = nguoiTiemChungAction.duyetDangKyMoi(reqBody);

            String msg = MessageUtil.getVNMessageText("nguoitiemchung.duyetdangky.success");

//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode bodyData = mapper.readTree(reqBody);
//
//
//            String ids = bodyData.has(EntityConstant.IDS) ? bodyData.get(EntityConstant.IDS).textValue()
//                    : StringPool.BLANK;
//
//            List<String> lstId = StringUtil.split(ids);
//
//            for (String strId : lstId) {
//                long id = GetterUtil.getLong(strId, 0);
//                NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(id);
//                if(nguoiTiemChung == null) {
//                    continue;
//                }
//                if (nguoiTiemChung.getTinhTrangDangKi() == VaccomUtil.DANGKYCHINHTHUC) {
//                    HangChoThongBao hangChoThongBao = hangChoThongBaoAction.findByPhone_LoaiThongBao(ZaloNotificationUtil.convertPhoneNumber(nguoiTiemChung.getSoDienThoai()), ZaloConstant.Loai_XacNhan_NguoiTiemChung);
//                    if (Validator.isNotNull(hangChoThongBao)) {
//                        hangChoThongBao.setReady(true);
//                        hangChoThongBaoAction.update(hangChoThongBao);
//                    }
//                }
//
//            }


            return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, null));

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

                List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByCongDan_ID(nguoiTiemChung.getCongDanID());

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

    @RequestMapping(value = "/get/search-nguoitiemchung", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> searchNguoiTiemChungNew(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestBody NguoiTiemChungDto nguoiTiemChungDto,
                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "30") int size) {
        try {

            ObjectMapper mapper = new ObjectMapper();

            ArrayNode data = mapper.createArrayNode();

            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            if (RoleUtil.isQuanTriHeThong(vaiTro)) {
                nguoiTiemChungDto.cosoyteid = 0;
                nguoiTiemChungDto.diabancosoid = 0;
            } else {
                //todo set cosoyteId
                nguoiTiemChungDto.cosoyteid = 0;
                nguoiTiemChungDto.diabancosoid = vaiTro.getDiaBanCoSoId();
            }

            if (nguoiTiemChungDto.isSearchDaTiem) {
                ResultSearchDto<CongDan> result = nguoiTiemChungAction.searchDaTiem(nguoiTiemChungDto, page, size);
                List<CongDan> lstCongdan = result.datas;

                return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(result.total, lstCongdan));
            }

            ResultSearchDto<NguoiTiemChung> result = nguoiTiemChungAction.search(nguoiTiemChungDto, page, size);

            List<NguoiTiemChung> lstNguoiTiemChung = result.datas;


            lstNguoiTiemChung.forEach(nguoiTiemChung -> {
                // JsonNode node = mapper.valueToTree(nguoiTiemChung);


                List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByCongDan_ID(nguoiTiemChung.getCongDanID());

                ArrayNode jsonArrayObj = mapper.convertValue(lstMuiTiemChung, ArrayNode.class);

                ObjectNode node = mapper.convertValue(nguoiTiemChung, ObjectNode.class);

                node.put("muiTiemChung", jsonArrayObj);


                List<PhieuHenTiem> lstPhieuHenTiem = phieuHenTiemAction.findByNguoiTiemChungId(nguoiTiemChung.getId());
                if (nguoiTiemChungDto.lichTiemChungId > 0) {
                    lstPhieuHenTiem = lstPhieuHenTiem.stream().filter(
                            phieuHenTiem -> nguoiTiemChungDto.lichTiemChungId == phieuHenTiem.getLichTiemChungId()).collect(Collectors.toList());
                }

                if (nguoiTiemChungDto.caTiemChungId > 0) {
                    lstPhieuHenTiem = lstPhieuHenTiem.stream().filter(
                            phieuHenTiem -> nguoiTiemChungDto.caTiemChungId == phieuHenTiem.getCaTiemChungId()).collect(Collectors.toList());
                }

                if (nguoiTiemChungDto.listtinhtrangxacnhan != null && nguoiTiemChungDto.listtinhtrangxacnhan.size() > 0) {
                    List<Integer> listTinhTrang = nguoiTiemChungDto.listtinhtrangxacnhan;
                    lstPhieuHenTiem = lstPhieuHenTiem.stream().filter(p -> listTinhTrang.contains(p.getTinhTrangXacNhan())).collect(Collectors.toList());
                }

                jsonArrayObj = mapper.convertValue(lstPhieuHenTiem, ArrayNode.class);

                node.put("phieuHenTiem", jsonArrayObj);

                data.add(node);
            });


            return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(result.total, data));

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
                                                  @RequestParam("ngaydangki") String ngaydangki,
                                                  @RequestParam("tinhthanhma") String tinhthanhma,
                                                  @RequestParam("tinhthanhten") String tinhthanhten,
                                                  @RequestParam("quanhuyenma") String quanhuyenma,
                                                  @RequestParam("quanhuyenten") String quanhuyenten,
                                                  @RequestParam("phuongxama") String phuongxama,
                                                  @RequestParam("phuongxaten") String phuongxaten,
                                                  @RequestParam("hovaten") String hovaten,
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
                        diabancosoid, cosoytema, tinhtrangdangky, kiemtratrung, tinhthanhma, tinhthanhten, quanhuyenma
                        , quanhuyenten, phuongxama, phuongxaten,
                        false, null, null, 0L, -1L, null);

                lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
                        hovaten, diabancosoid, cosoytema, tinhtrangdangky, kiemtratrung, page, size
                        , tinhthanhma, tinhthanhten, quanhuyenma, quanhuyenten, phuongxama, phuongxaten,
                        false, null, null, 0L, -1L, null);

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
                        kiemtratrung, tinhthanhma, tinhthanhten, quanhuyenma, quanhuyenten, phuongxama, phuongxaten,
                        false, null, null, 0L, -1L, null);

                lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
                        hovaten, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
                        tinhtrangdangky, kiemtratrung, page, size, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten, false,
                        null, null, 0L, -1L, null);

            }

            lstNguoiTiemChung.forEach(nguoiTiemChung -> {
                // JsonNode node = mapper.valueToTree(nguoiTiemChung);

                List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByCongDan_ID(nguoiTiemChung.getCongDanID());

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
                                                     @RequestParam("tinhthanhma") String tinhthanhma,
                                                     @RequestParam("tinhthanhten") String tinhthanhten,
                                                     @RequestParam("quanhuyenma") String quanhuyenma,
                                                     @RequestParam("quanhuyenten") String quanhuyenten,
                                                     @RequestParam("phuongxama") String phuongxama,
                                                     @RequestParam("phuongxaten") String phuongxaten,
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
                        diabancosoid, cosoytema, VaccomUtil.MOIDANGKY, kiemtratrung, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten, false,
                        null, null, 0L, -1L, null);

                lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
                        hovaten, diabancosoid, cosoytema, VaccomUtil.MOIDANGKY, kiemtratrung, page, size, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten, false, null, null,
                        0L, -1L, null);

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
                        VaccomUtil.MOIDANGKY, kiemtratrung, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten, false,
                        null, null, 0L, -1L, null);

                lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
                        hovaten, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
                        VaccomUtil.MOIDANGKY, kiemtratrung, page, size, tinhthanhma, tinhthanhten, quanhuyenma,
                        quanhuyenten, phuongxama, phuongxaten, false, null, null, 0L, -1L, null);
            }

            lstNguoiTiemChung.forEach(nguoiTiemChung -> {
                // JsonNode node = mapper.valueToTree(nguoiTiemChung);

                List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByCongDan_ID(nguoiTiemChung.getCongDanID());

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
                                                           @RequestParam("tinhthanhma") String tinhthanhma,
                                                           @RequestParam("tinhthanhten") String tinhthanhten,
                                                           @RequestParam("quanhuyenma") String quanhuyenma,
                                                           @RequestParam("quanhuyenten") String quanhuyenten,
                                                           @RequestParam("phuongxama") String phuongxama,
                                                           @RequestParam("phuongxaten") String phuongxaten,
                                                           @RequestParam(name = "diachinoio", defaultValue = StringPool.BLANK) String diachinoio,
                                                           @RequestParam(name = "diabancosoid", defaultValue = "-1") Long diabancosoid,
                                                           @RequestParam("cosoytema") String cosoytema,
                                                           @RequestParam(name = "isDatTieuChuan", defaultValue = "0") Boolean isDatTieuChuan,
                                                           @RequestParam(name = "loaiThuocTiem", defaultValue = StringPool.BLANK) String loaiThuocTiem,
                                                           @RequestParam(name = "kiemtratrung", defaultValue = "-1") Integer kiemtratrung,
                                                           @RequestParam(name = "lichTiemChungId", defaultValue = "0") Long lichtiemchungid,
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
                        diabancosoid, cosoytema, VaccomUtil.DANGKYCHINHTHUC, kiemtratrung, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten, isDatTieuChuan,
                        loaiThuocTiem, diachinoio, lichtiemchungid, -1L, null);

                lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
                        hovaten, diabancosoid, cosoytema, VaccomUtil.DANGKYCHINHTHUC, kiemtratrung, page, size,
                        tinhthanhma, tinhthanhten, quanhuyenma, quanhuyenten, phuongxama, phuongxaten,
                        isDatTieuChuan, loaiThuocTiem, diachinoio, lichtiemchungid, -1L, null);

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
                        VaccomUtil.DANGKYCHINHTHUC, kiemtratrung, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten, isDatTieuChuan,
                        loaiThuocTiem, diachinoio, lichtiemchungid, -1L, null);

                lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
                        hovaten, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
                        VaccomUtil.DANGKYCHINHTHUC, kiemtratrung, page, size, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten, isDatTieuChuan, loaiThuocTiem, diachinoio, lichtiemchungid, -1L, null);
            }


            lstNguoiTiemChung.forEach(nguoiTiemChung -> {
                // JsonNode node = mapper.valueToTree(nguoiTiemChung);

                List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByCongDan_ID(nguoiTiemChung.getCongDanID());

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
                                                 @RequestParam("tinhthanhma") String tinhthanhma,
                                                 @RequestParam("tinhthanhten") String tinhthanhten,
                                                 @RequestParam("quanhuyenma") String quanhuyenma,
                                                 @RequestParam("quanhuyenten") String quanhuyenten,
                                                 @RequestParam("phuongxama") String phuongxama,
                                                 @RequestParam("phuongxaten") String phuongxaten,
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
                        diabancosoid, cosoytema, VaccomUtil.XOADANGKY, kiemtratrung, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten, false,
                        null, null, 0L, -1L, null);

                lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
                        hovaten, diabancosoid, cosoytema, VaccomUtil.XOADANGKY, kiemtratrung, page, size, tinhthanhma,
                        tinhthanhten, quanhuyenma, quanhuyenten, phuongxama, phuongxaten,
                        false, null, null, 0L, -1L, null);

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
                        VaccomUtil.XOADANGKY, kiemtratrung, tinhthanhma, tinhthanhten,
                        quanhuyenma, quanhuyenten, phuongxama, phuongxaten,
                        false, null, null, 0L, -1L, null);

                lstNguoiTiemChung = nguoiTiemChungAction.searchNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki,
                        hovaten, nguoiDung.getDiaBanCoSoId() > 0 ? nguoiDung.getDiaBanCoSoId() : -1, coSoYTeMa,
                        VaccomUtil.XOADANGKY, kiemtratrung, page, size, tinhthanhma, tinhthanhten, quanhuyenma,
                        quanhuyenten, phuongxama, phuongxaten, false, null, null, 0L, -1L, null);
            }

            lstNguoiTiemChung.forEach(nguoiTiemChung -> {
                // JsonNode node = mapper.valueToTree(nguoiTiemChung);

                List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByCongDan_ID(nguoiTiemChung.getCongDanID());

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
                                             @RequestParam(name = "tinhthanhma", defaultValue = "") String tinhthanhma,
                                             @RequestParam(name = "tinhthanhten", defaultValue = "") String tinhthanhten,
                                             @RequestParam(name = "quanhuyenma", defaultValue = "") String quanhuyenma,
                                             @RequestParam(name = "quanhuyenten", defaultValue = "") String quanhuyenten,
                                             @RequestParam(name = "phuongxama", defaultValue = "") String phuongxama,
                                             @RequestParam(name = "phuongxaten", defaultValue = "") String phuongxaten,
                                             @RequestParam(name = "cosoyteid") long id

    ) {

        try {

            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            if (vaiTro == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(MessageUtil.getVNMessageText("diabancoso.danhsach.permission_error"));
            }

            List<DiaBanCoSo> lstDiaBanCoSo = new ArrayList<DiaBanCoSo>();

            if (id < 0) {
                lstDiaBanCoSo = diaBanCoSoAction.findAll();
                lstDiaBanCoSo = lstDiaBanCoSo.stream()
                        .filter(Validator.isNotNull(tinhthanhma) ? diaBanCoSo -> tinhthanhma.equals(diaBanCoSo.getTinhThanhMa()) : diaBanCoSo -> true)
                        .filter(Validator.isNotNull(tinhthanhten) ? diaBanCoSo -> tinhthanhten.equals(diaBanCoSo.getTinhThanhTen()) : diaBanCoSo -> true)
                        .filter(Validator.isNotNull(quanhuyenma) ? diaBanCoSo -> quanhuyenma.equals(diaBanCoSo.getQuanHuyenMa()) : diaBanCoSo -> true)
                        .filter(Validator.isNotNull(quanhuyenten) ? diaBanCoSo -> quanhuyenten.equals(diaBanCoSo.getQuanHuyenTen()) : diaBanCoSo -> true)
                        .filter(Validator.isNotNull(phuongxama) ? diaBanCoSo -> phuongxama.equals(diaBanCoSo.getPhuongXaMa()) : diaBanCoSo -> true)
                        .filter(Validator.isNotNull(phuongxaten) ? diaBanCoSo -> phuongxaten.equals(diaBanCoSo.getPhuongXaTen()) : diaBanCoSo -> true)
                        .collect(Collectors.toList());
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
            PhieuHenTiem phieuHenTiem = phieuHenTiemAction.addPhieuHenTiem(reqBody);
            long nguoiTiemChungID = phieuHenTiem.getNguoiTiemChungId();
            NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(nguoiTiemChungID);
            if (Validator.isNotNull(nguoiTiemChung)) {
                nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.DACHUYENSANGPHIEUHEN);
                nguoiTiemChungAction.update(nguoiTiemChung);
            }


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

    @RequestMapping(value = "/add/phieuhentiem/list", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> addPhieuHenTiemList(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestBody PhieuHenTiemDto phieuHenTiemDto) {
        try {
            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            if (phieuHenTiemDto.NguoiTiemChungIDs != null && phieuHenTiemDto.NguoiTiemChungIDs.size() > 0) {
                List<Integer> NguoiTiemChungIdList = phieuHenTiemDto.NguoiTiemChungIDs;
                LichTiemChung lichTiemChung = lichTiemChungAction.findById(phieuHenTiemDto.LichTiemChungID);

                if (Validator.isNull(lichTiemChung)) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(MessageUtil.getVNMessageText("lichtiemchung.chitiet.not_found"));
                }
                for (Integer nguoiTiemChungID : NguoiTiemChungIdList) {
                    NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(nguoiTiemChungID);

                    List<MuiTiemChung> muiTiemChungList = muiTiemChungAction.findByCongDan_ID(nguoiTiemChung.getCongDanID());
                    int lanTiem = 0;
                    if (Validator.isNull(muiTiemChungList)) {
                        lanTiem = 1;
                    } else {
                        lanTiem = muiTiemChungList.size() + 1;
                    }

                    if (Validator.isNull(nguoiTiemChung)) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(MessageUtil.getVNMessageText("nguoiTiemChung.chitiet.not_found"));
                    }
                    PhieuHenTiem phieuHenTiem = new PhieuHenTiem();

                    phieuHenTiem.setGioHenTiem(lichTiemChung.getGioHenTiem());
                    phieuHenTiem.setLichTiemChungId(lichTiemChung.getId());
                    phieuHenTiem.setCaTiemChungId(0);
                    phieuHenTiem.setNgayHenTiem(lichTiemChung.getNgayBatDau());
                    phieuHenTiem.setNguoiTiemChungId(nguoiTiemChung.getId());
                    phieuHenTiem.setMaQR(VaccomUtil.generateQRCode("pht", 4));
                    phieuHenTiem.setLanTiem(lanTiem);
                    phieuHenTiem.setTinhTrangXacNhan(VaccomUtil.DUKIEN);
                    phieuHenTiem.setNgayCheckin(StringPool.BLANK);
                    phieuHenTiem.setThongTinCheckin(StringPool.BLANK);
                    phieuHenTiem.setGioDuocTiem(StringPool.BLANK);
                    phieuHenTiem.setTrieuChungSauTiem(StringPool.BLANK);
                    phieuHenTiem.setDieuTriTrieuChung(StringPool.BLANK);

                    phieuHenTiemAction.addPhieuHenTiem(phieuHenTiem);

                    nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.DACHUYENSANGPHIEUHEN);
                    nguoiTiemChungAction.update(nguoiTiemChung);

                }

                String msg = MessageUtil.getVNMessageText("phieuhentiem.list.add.success");

                return ResponseEntity.status(HttpStatus.OK).body(msg);
            }

            String msg = MessageUtil.getVNMessageText("NguoiTiemChungIDs.not_found");

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

            PhieuHenTiem phieuHenTiem = phieuHenTiemAction.findById(id);
            if (Validator.isNull(phieuHenTiem)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("phieuHenTiem.chitiet.not_found"));
            }
            long nguoiTiemChungID = phieuHenTiem.getNguoiTiemChungId();
            NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(nguoiTiemChungID);
            if (Validator.isNotNull(nguoiTiemChung)) {
                nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.DANGKYCHINHTHUC);
                nguoiTiemChungAction.update(nguoiTiemChung);
            }

            //Xóa phiếu hẹn
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

            List<PhieuHenTiem> listPhieuHenChuaTiem = phieuHenTiemAction.findByKhacTinhTrangXacNhan_LichTiemChungID(VaccomUtil.DATIEMXONG, id);
            for (PhieuHenTiem phieuHenTiem : listPhieuHenChuaTiem) {
                NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(phieuHenTiem.getNguoiTiemChungId());

                if (Validator.isNotNull(nguoiTiemChung)) {
                    nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.DANGKYCHINHTHUC);
                    nguoiTiemChungAction.update(nguoiTiemChung);
                }

            }


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
            ObjectMapper mapper = new ObjectMapper();

            JsonNode bodyData = mapper.readTree(reqBody);

            long congDanId = bodyData.has(EntityConstant.CONGDAN_ID)
                    ? bodyData.get(EntityConstant.CONGDAN_ID).longValue()
                    : 0;

            long maPhieuHen = bodyData.has(EntityConstant.MAPHIEUHEN) ? bodyData.get(EntityConstant.MAPHIEUHEN).longValue()
                    : 0;
            PhieuHenTiem phieuHenTiem = phieuHenTiemAction.findById(maPhieuHen);

            if (Validator.isNull(phieuHenTiem)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageUtil.getVNMessageText("phieuHenTiem.not.found"));
            }

            NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(phieuHenTiem.getNguoiTiemChungId());

            if (Validator.isNull(nguoiTiemChung)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageUtil.getVNMessageText("nguoiTiemChung.not.found"));
            }

            CongDan congDan = congDanAction.findByCongDanId(nguoiTiemChung.getCongDanID());

            if (Validator.isNull(congDan)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageUtil.getVNMessageText("congDan.not.found"));
            }


            MuiTiemChung muiTiemChung = muiTiemChungAction.addMuiTiemChung(reqBody);

            int soMuiTiem = congDan.getSoMuiTiem() + 1;
            congDan.setSoMuiTiem(soMuiTiem);
            congDanAction.update(congDan);
            nguoiTiemChung.setTinhTrangDangKi(VaccomUtil.DATIEM);
            nguoiTiemChung.setSoMuiTiem(soMuiTiem);
            nguoiTiemChung.setNgayTiemCuoi(DatetimeUtil.dateToString(new Date(), DatetimeUtil._VN_DATE_FORMAT));
            nguoiTiemChungAction.update(nguoiTiemChung);

            phieuHenTiem.setTinhTrangXacNhan(VaccomUtil.DATIEMXONG);
            phieuHenTiemAction.update(phieuHenTiem);


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
            NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(id);

            if (Validator.isNull(nguoiTiemChung)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("nguoiTiemChung.not_found"));
            }

            long total = muiTiemChungAction.countByCongDan_ID(nguoiTiemChung.getCongDanID());

            List<MuiTiemChung> lstMuiTiemChung = muiTiemChungAction.findByCongDan_ID(nguoiTiemChung.getCongDanID());

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
            GiayDiDuong giayDiDuong = giayDiDuongAction.create(giayDiDuongDto);

            if (giayDiDuong == null) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageUtil.getVNMessageText("giaydiduong.add.error"));
            }

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

    @RequestMapping(value = "/get/giaydiduong/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> getDsGiayDiDuong(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable(value = "id") int id,
                                              @RequestBody GiayDiDuongDto giayDiDuongDto,
                                              @RequestParam("page") int page, @RequestParam("size") int size) {

        try {
            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            if (!PermissionUtil.canAccessGiayDiDuong(vaiTro, null, null, MethodConstant.GET)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(MessageUtil.getVNMessageText("giaydiduong.danhsach.permission_error"));
            }
            List<GiayDiDuong> lstGiayDiDuong = new ArrayList<>();

            if (id > 0) {
                GiayDiDuong giayDiDuong = giayDiDuongAction.findById(id);
                if (giayDiDuong == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(MessageUtil.getVNMessageText("giayDiDuong.not_found"));
                }

                lstGiayDiDuong.add(giayDiDuong);

                return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(1, lstGiayDiDuong));
            }

            long total = 0;
            giayDiDuongDto.uyBanNhanDanID = (int) vaiTro.getUyBanNhanDanId();
            giayDiDuongDto.size = size;
            giayDiDuongDto.page = page;

            ResultSearchDto<GiayDiDuong> result = giayDiDuongAction.search(giayDiDuongDto);
            total = result.total;
            lstGiayDiDuong = result.datas;

            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();


            for (GiayDiDuong giayDiDuong : lstGiayDiDuong) {
                String json = mapper.writeValueAsString(giayDiDuong);
                JsonNode giaydiduongJson = mapper.readTree(json);
                HangChoThongBao hangChoThongBao = hangChoThongBaoAction.findByLoaiThongBao_mappingKey(giaydiduongJson.get("id").asLong(), ZaloConstant.Loai_Giay_Di_Duong);
                if (Validator.isNotNull(hangChoThongBao)) {
                    ((ObjectNode) giaydiduongJson).put(ZaloConstant.statusGuiTinNhan, hangChoThongBao.getStatus());
                } else {
                    ((ObjectNode) giaydiduongJson).put(ZaloConstant.statusGuiTinNhan, -1);
                }
                arrayNode.add(giaydiduongJson);
            }


            return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, arrayNode));

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

    @RequestMapping(value = "/get/phieuhen-maqr", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getPhieuHenMaQr(@RequestParam("maQr") String maQr) {

        try {
            PhieuHenTiem phieuHenTiem = null;
            LichTiemChung lichTiemChung = null;
            NguoiTiemChung nguoiTiemChung = null;
            UyBanNhanDan uyBanNhanDan = null;

            try {
                phieuHenTiem = phieuHenTiemAction.findByMaQR(maQr);

                lichTiemChung = lichTiemChungAction.findById(phieuHenTiem.getLichTiemChungId());

                nguoiTiemChung = nguoiTiemChungAction.findById(phieuHenTiem.getNguoiTiemChungId());

                uyBanNhanDan = uyBanNhanDanAction.findById(lichTiemChung.getUyBanNhanDanID());
            } catch (Exception ex) {
                _log.error(ex.getMessage());
            }


            if (phieuHenTiem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("phieuHenTiem.not_found"));
            }
            if (Validator.isNull(lichTiemChung)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("lichTiemChung.not_found"));
            }
            if (Validator.isNull(nguoiTiemChung)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("nguoiTiemChung.not_found"));
            }
            if (Validator.isNull(uyBanNhanDan)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("uyBanNhanDan.not_found"));
            }

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(phieuHenTiem);
            JsonNode phieuHenTiemJson = mapper.readTree(json);
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.LinkQrCode, domainUrl + "/#/pages/hen-tiem-chung/" + phieuHenTiem.getMaQR());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.HoVaTen, nguoiTiemChung.getHoVaTen());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.CoSoYTe, lichTiemChung.getTenCoSo());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.LoaiThuocTiem, lichTiemChung.getLoaiThuocTiem());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.SoLo, lichTiemChung.getSoLoThuoc());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.DiaDiem, lichTiemChung.getDiaDiemTiemChung());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.CMTCCCD, nguoiTiemChung.getCmtcccd());
            ((ObjectNode) phieuHenTiemJson).put("ngaySinh", nguoiTiemChung.getNgaySinh());

            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan() + " - " + uyBanNhanDan.getQuanHuyenTen() + " - " + uyBanNhanDan.getTinhThanhTen());

            return ResponseEntity.status(HttpStatus.OK).body(phieuHenTiemJson);

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

    @RequestMapping(value = "/get/chung-nhan-tiem-chung-maqr", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getChungNhanTiemChungMaQr(@RequestParam("maQr") String maQr) {

        try {

            PhieuHenTiem phieuHenTiem = phieuHenTiemAction.findByMaQR(maQr);

            LichTiemChung lichTiemChung = lichTiemChungAction.findById(phieuHenTiem.getLichTiemChungId());

            NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findById(phieuHenTiem.getNguoiTiemChungId());

            CoSoYTe coSoYTe = coSoYTeAction.findById(lichTiemChung.getCoSoYTeId());

            if (Validator.isNull(phieuHenTiem)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("phieuHenTiem.not_found"));
            }
            if (Validator.isNull(lichTiemChung)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("lichTiemChung.not_found"));
            }
            if (Validator.isNull(nguoiTiemChung)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("nguoiTiemChung.not_found"));
            }
            if (Validator.isNull(coSoYTe)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("coSoYTe.not_found"));
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(phieuHenTiem);
            JsonNode phieuHenTiemJson = mapper.readTree(json);
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.LinkQrCode, domainUrl + "/#/pages/chung-nhan-tiem-chung/" + phieuHenTiem.getMaQR());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.HoVaTen, nguoiTiemChung.getHoVaTen());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.CoSoYTe, coSoYTe.getTenCoSo());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.LoaiThuocTiem, lichTiemChung.getLoaiThuocTiem());
            ((ObjectNode) phieuHenTiemJson).put(ZaloConstant.SoLo, lichTiemChung.getSoLoThuoc());


            return ResponseEntity.status(HttpStatus.OK).body(phieuHenTiemJson);

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
            UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(giayDiDuong.getUyBanNhanDanID());


            if (giayDiDuong == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("giayDiDuong.not_found"));
            }
            if (uyBanNhanDan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("uyBanNhanDan.not_found"));
            }

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(giayDiDuong);
            JsonNode giayDiDuongJson = mapper.readTree(json);

            ((ObjectNode) giayDiDuongJson).put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan() + " - " + uyBanNhanDan.getQuanHuyenTen() + " - " + uyBanNhanDan.getTinhThanhTen());

            ((ObjectNode) giayDiDuongJson).put(ZaloConstant.LinkQrCode, domainUrl + "/#/pages/giay-di-duong/" + giayDiDuong.getMaQR());

            return ResponseEntity.status(HttpStatus.OK).body(giayDiDuongJson);

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

    @RequestMapping(value = "/get/dangkymoi-maqr", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getNguoiTiemChungMaQr(@RequestParam("maQr") String maQr) {

        try {
            NguoiTiemChung nguoiTiemChung = nguoiTiemChungAction.findByMaQR(maQr);


            if (nguoiTiemChung == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("nguoiTiemChung.not_found"));
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(nguoiTiemChung);
            JsonNode nguoiTiemChungJson = mapper.readTree(json);

            ((ObjectNode) nguoiTiemChungJson).put(ZaloConstant.LinkQrCode, domainUrl + "/#/pages/dang-ky-moi/" + nguoiTiemChung.getMaQR());

            return ResponseEntity.status(HttpStatus.OK).body(nguoiTiemChungJson);

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

    @RequestMapping(value = "/update/all/giaydiduong", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateGiayDiDuong(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam("statusUpdate") int statusUpdate,
                                               @RequestBody GiayDiDuongDto giayDiDuongDto) {
        try {
            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            giayDiDuongDto.size = -1;
            ResultSearchDto<GiayDiDuong> result = giayDiDuongAction.search(giayDiDuongDto);
            List<GiayDiDuong> giayDiDuongs = result.datas;
            int statusOld;

            for (GiayDiDuong giayDiDuongUpdate : giayDiDuongs) {
                try {

                    if (giayDiDuongUpdate.getUyBanNhanDanID() != (int) vaiTro.getUyBanNhanDanId()) {
                        continue;
                    }

                    statusOld = giayDiDuongUpdate.getStatus();
                    GiayDiDuong giayDiDuongNew = giayDiDuongAction.updateStatus(giayDiDuongUpdate, statusUpdate);

                    if (Validator.isNotNull(giayDiDuongUpdate) && Validator.isNotNull(giayDiDuongNew)) {
                        if (statusOld != VaccomUtil.DADUYET && giayDiDuongNew.getStatus() == VaccomUtil.DADUYET) {
                            UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(giayDiDuongNew.getUyBanNhanDanID());

                            if (Validator.isNotNull(uyBanNhanDan)) {
                                ObjectMapper mapper = new ObjectMapper();
                                ObjectNode template_data = mapper.createObjectNode();


                                template_data.put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan());
                                template_data.put("HovaTen", giayDiDuongNew.getHoVaTen());
                                template_data.put(ZaloConstant.QrCodeID, giayDiDuongNew.getMaQR());

                                hangChoThongBaoAction.addHangChoThongBao(template_data.toString(),
                                        ZaloNotificationUtil.convertPhoneNumber(giayDiDuongNew.getSoDienThoai()),
                                        giayDiDuongNew.getEmail(),
                                        true,
                                        ZaloConstant.Loai_Giay_Di_Duong,
                                        uyBanNhanDan.getId(),
                                        giayDiDuongNew.getId());
                            }
                        }

                    }
                } catch (Exception e) {
                    _log.error("error update with giay di duong id: " + giayDiDuongUpdate.getId());
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(MessageUtil.getVNMessageText("giaydiduong.update.success"));

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

            if (id == 0 && giayDiDuongDto.listIdUpdate != null && giayDiDuongDto.listIdUpdate.size() > 0) {
                List<Integer> listIdUpdate = giayDiDuongDto.listIdUpdate;
                for (Integer idUpdate : listIdUpdate) {
                    try {
                        GiayDiDuong giayDiDuongUpdate = giayDiDuongAction.findById(idUpdate);
                        int statusOld = giayDiDuongUpdate.getStatus();
                        if (giayDiDuongUpdate != null) {
                            if (giayDiDuongUpdate.getUyBanNhanDanID() != (int) vaiTro.getUyBanNhanDanId()) {
                                continue;
                            }
                            GiayDiDuong giayDiDuongNew = giayDiDuongAction.update(giayDiDuongUpdate, giayDiDuongDto);

                            if (Validator.isNotNull(giayDiDuongUpdate) && Validator.isNotNull(giayDiDuongNew)) {
                                if (statusOld != VaccomUtil.DADUYET && giayDiDuongNew.getStatus() == VaccomUtil.DADUYET) {
                                    UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(giayDiDuongNew.getUyBanNhanDanID());

                                    if (Validator.isNotNull(uyBanNhanDan)) {
                                        ObjectMapper mapper = new ObjectMapper();
                                        ObjectNode template_data = mapper.createObjectNode();


                                        template_data.put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan());
                                        template_data.put("HovaTen", giayDiDuongNew.getHoVaTen());
                                        template_data.put(ZaloConstant.QrCodeID, giayDiDuongNew.getMaQR());

                                        hangChoThongBaoAction.addHangChoThongBao(template_data.toString(),
                                                ZaloNotificationUtil.convertPhoneNumber(giayDiDuongNew.getSoDienThoai()),
                                                giayDiDuongNew.getEmail(), true,
                                                ZaloConstant.Loai_Giay_Di_Duong,
                                                uyBanNhanDan.getId(),
                                                giayDiDuongNew.getId());

                                    }
                                }

                            }
                        }
                    } catch (Exception e) {
                        _log.error("Error update with id: " + idUpdate);
                        e.printStackTrace();
                    }
                }

                return ResponseEntity.status(HttpStatus.OK).body(MessageUtil.getVNMessageText("giaydiduong.update.success"));
            }

            GiayDiDuong giayDiDuong = giayDiDuongAction.findById(id);
            int statusOld = giayDiDuong.getStatus();
            if (giayDiDuong == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("giayDiDuong.not_found"));
            }

            if (!PermissionUtil.canAccessGiayDiDuong(vaiTro, giayDiDuong, null, MethodConstant.UPDATE)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(MessageUtil.getVNMessageText("giaydiduong.update.permission_error"));
            }
            GiayDiDuong giayDiDuongNew = giayDiDuongAction.update(giayDiDuong, giayDiDuongDto);

            if (Validator.isNotNull(giayDiDuong) && Validator.isNotNull(giayDiDuongNew)) {
                if (statusOld != VaccomUtil.DADUYET && giayDiDuongNew.getStatus() == VaccomUtil.DADUYET) {
                    UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(giayDiDuongNew.getUyBanNhanDanID());

                    if (Validator.isNotNull(uyBanNhanDan)) {
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode template_data = mapper.createObjectNode();

                        template_data.put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan());
                        template_data.put("HovaTen", giayDiDuongNew.getHoVaTen());
                        template_data.put(ZaloConstant.QrCodeID, giayDiDuongNew.getMaQR());

                        hangChoThongBaoAction.addHangChoThongBao(
                                template_data.toString(),
                                ZaloNotificationUtil.convertPhoneNumber(giayDiDuongNew.getSoDienThoai()),
                                giayDiDuongNew.getEmail(),
                                true,
                                ZaloConstant.Loai_Giay_Di_Duong,
                                uyBanNhanDan.getId(),
                                giayDiDuongNew.getId());

                    }
                } else if (statusOld == VaccomUtil.DADUYET && giayDiDuongNew.getStatus() == VaccomUtil.DADUYET) {

                    UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(giayDiDuongNew.getUyBanNhanDanID());
                    if (Validator.isNotNull(uyBanNhanDan)) {
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode template_data = mapper.createObjectNode();

                        template_data.put(ZaloConstant.NgayCap, giayDiDuongNew.getNgayCap());
                        template_data.put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan());
                        template_data.put("HovaTen", giayDiDuongNew.getHoVaTen());
                        template_data.put(ZaloConstant.QrCodeID, giayDiDuongNew.getMaQR());

                        hangChoThongBaoAction.addHangChoThongBao(
                                template_data.toString(),
                                ZaloNotificationUtil.convertPhoneNumber(giayDiDuongNew.getSoDienThoai()),
                                giayDiDuongNew.getEmail(),
                                true,
                                ZaloConstant.Loai_Sua_Giay_Di_Duong,
                                uyBanNhanDan.getId(),
                                giayDiDuongNew.getId());

                    }
                }

            }


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

            if (id == 0 && giayDiDuongDto.listIdDelete != null && giayDiDuongDto.listIdDelete.size() > 0) {
                List<Integer> listIdDelete = giayDiDuongDto.listIdDelete;
                for (Integer idDelete : listIdDelete) {
                    try {
                        GiayDiDuong giayDiDuongDelete = giayDiDuongAction.findById(idDelete);
                        if (giayDiDuongDelete != null) {
                            if (giayDiDuongDelete.getUyBanNhanDanID() != (int) vaiTro.getUyBanNhanDanId()) {
                                continue;
                            }
                            giayDiDuongAction.delete(giayDiDuongDelete);
                        }
                    } catch (Exception e) {
                        _log.error("Error delete with id: " + idDelete);
                        e.printStackTrace();
                    }
                }

                return ResponseEntity.status(HttpStatus.OK).body(MessageUtil.getVNMessageText("giaydiduong.delete.success"));
            }

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

    @RequestMapping(value = "/get/uybannhandan/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getDsUyBanNhanDan(HttpServletRequest request, HttpServletResponse response,
                                               @PathVariable(value = "id") int id,
                                               @RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            List<UyBanNhanDan> lstUybanNhanDan = new ArrayList<>();

            if (id > 0) {
                UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(id);
                if (uyBanNhanDan == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(MessageUtil.getVNMessageText("uyBanNhanDan.not_found"));
                }
                lstUybanNhanDan.add(uyBanNhanDan);

                return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(1, lstUybanNhanDan));
            }

            long total = 0;
            total = uyBanNhanDanAction.countAll();
            lstUybanNhanDan = uyBanNhanDanAction.findAll(page, size);

            return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, lstUybanNhanDan));

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

    @RequestMapping(value = "/add/uybannhandan", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> addGiayDiDuong(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody UyBanNhanDanDto uyBanNhanDanDto) {
        try {
            uyBanNhanDanAction.create(uyBanNhanDanDto);

            String msg = MessageUtil.getVNMessageText("uybannhandan.add.success");

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

    @RequestMapping(value = "/update/uybannhandan/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateUyBanNhanDan(HttpServletRequest request, HttpServletResponse response,
                                                @PathVariable(value = "id") long id, @RequestBody UyBanNhanDanDto uyBanNhanDanDto) {
        try {

            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            if (id == 0 && uyBanNhanDanDto.listIdUpdate != null && uyBanNhanDanDto.listIdUpdate.size() > 0) {
                List<Integer> listIdUpdate = uyBanNhanDanDto.listIdUpdate;
                for (Integer idUpdate : listIdUpdate) {
                    try {
                        UyBanNhanDan uyBanNhanDanUpdate = uyBanNhanDanAction.findById(idUpdate);
                        if (uyBanNhanDanUpdate != null) {
                            uyBanNhanDanAction.update(uyBanNhanDanUpdate, uyBanNhanDanDto);
                        }
                    } catch (Exception e) {
                        _log.error("Error update with id: " + idUpdate);
                        e.printStackTrace();
                    }
                }

                return ResponseEntity.status(HttpStatus.OK).body(MessageUtil.getVNMessageText("uyBanNhanDan.update.success"));
            }

            UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(id);

            if (uyBanNhanDan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("uyBanNhanDan.not_found"));
            }

            uyBanNhanDanAction.update(uyBanNhanDan, uyBanNhanDanDto);

            String msg = MessageUtil.getVNMessageText("uyBanNhanDan.update.success");

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

    @RequestMapping(value = "/delete/uybannhandan/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteUyBanNhanDan(HttpServletRequest request, HttpServletResponse response,
                                                @PathVariable(value = "id") long id, @RequestBody UyBanNhanDanDto uyBanNhanDanDto) {
        try {

            VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

            if (id == 0 && uyBanNhanDanDto.listIdDelete != null && uyBanNhanDanDto.listIdDelete.size() > 0) {
                List<Integer> listIdDelete = uyBanNhanDanDto.listIdDelete;
                for (Integer idDelete : listIdDelete) {
                    try {
                        UyBanNhanDan uyBanNhanDanDelete = uyBanNhanDanAction.findById(idDelete);
                        if (uyBanNhanDanDelete != null) {
                            uyBanNhanDanAction.delete(uyBanNhanDanDelete);
                        }
                    } catch (Exception e) {
                        _log.error("Error delete with id: " + idDelete);
                        e.printStackTrace();
                    }
                }

                return ResponseEntity.status(HttpStatus.OK).body(MessageUtil.getVNMessageText("uyBanNhanDan.delete.success"));
            }

            UyBanNhanDan uyBanNhanDan = uyBanNhanDanAction.findById(id);

            if (uyBanNhanDan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(MessageUtil.getVNMessageText("uyBanNhanDan.not_found"));
            }

            uyBanNhanDanAction.delete(uyBanNhanDan);

            String msg = MessageUtil.getVNMessageText("uyBanNhanDan.delete.success");

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

    @RequestMapping(value = "/get/thuoctiem", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getDSThuocTiem(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            long total = 0;
            List<ThuocTiem> listThuocTiem = new ArrayList<>();
            listThuocTiem = thuocTiemAction.findAll();
            total = thuocTiemAction.count();

            return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, listThuocTiem));
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

    @RequestMapping(value = "/update/phieuhentiem/guiThongBao/{type}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> thucHienGuiThongBao(HttpServletRequest request, HttpServletResponse response,
                                                 @PathVariable(value = "type") long type, @RequestBody PhieuHenTiemDto phieuHenTiemDto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (type > 0) {
                List<Integer> ids = phieuHenTiemDto.PhieuHenTiems;
                for (Integer id : ids) {
                    PhieuHenTiem phieuHenTiem = phieuHenTiemAction.findById(id);
                    if (Validator.isNotNull(phieuHenTiem) && phieuHenTiem.getLichTiemChungId() == phieuHenTiemDto.LichTiemChungID) {
                        int oldTinhTrangXacNhan = phieuHenTiem.getTinhTrangXacNhan();
                        phieuHenTiem.setTinhTrangXacNhan(VaccomUtil.HENDAXACNHAN);
                        PhieuHenTiem phieuHenTiemNew = phieuHenTiemAction.addPhieuHenTiem(phieuHenTiem);
                        int newTinhTrangXacNhan = phieuHenTiemNew.getTinhTrangXacNhan();
                        if (Validator.isNotNull(phieuHenTiemNew)) {
                            if (oldTinhTrangXacNhan == VaccomUtil.DUKIEN && newTinhTrangXacNhan == VaccomUtil.HENDAXACNHAN) {
                                if (Validator.isNotNull(phieuHenTiem)) {
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
                                    template_data.put(ZaloConstant.NgayTiemChung, phieuHenTiem.getNgayHenTiem() + " " + phieuHenTiem.getGioHenTiem());
                                    template_data.put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan());
                                    template_data.put(ZaloConstant.DonViTiem, TenCoSo);
                                    template_data.put(ZaloConstant.DiaDiem, DiaDiem);
                                    template_data.put(ZaloConstant.LoaiThuocTiem, lichTiemChung.getLoaiThuocTiem());
                                    template_data.put(ZaloConstant.QrCodeID, phieuHenTiem.getMaQR());

                                    template_data.put(ZaloConstant.LanTiem, phieuHenTiem.getLanTiem());

                                    String namSinh = nguoiTiemChung.getNgaySinh();
                                    Date namSinhDate = DatetimeUtil.stringToDate(namSinh, DatetimeUtil._VN_DATE_FORMAT);
                                    template_data.put(ZaloConstant.NamSinh, namSinhDate.getYear() + 1900);


                                    hangChoThongBaoAction.addHangChoThongBao(template_data.toString(),
                                            nguoiTiemChung.getSoDienThoai(),
                                            nguoiTiemChung.getEmail(),
                                            true,
                                            ZaloConstant.Loai_Hen_TiemChung,
                                            uyBanNhanDanId,
                                            phieuHenTiem.getId());

                                }
                            }
                        }
                    }
                }
                String msg = MessageUtil.getVNMessageText("phieuhentiem.update.trangthai.success");

                return ResponseEntity.status(HttpStatus.OK).body(msg);
            }

            List<PhieuHenTiem> phieuHenTiemList = phieuHenTiemAction.findByLichTiemChungId_TinhTrangXacNhan(phieuHenTiemDto.LichTiemChungID, VaccomUtil.DUKIEN);

            for (PhieuHenTiem phieuHenTiem : phieuHenTiemList) {

                phieuHenTiem.setTinhTrangXacNhan(VaccomUtil.HENDAXACNHAN);
                PhieuHenTiem phieuHenTiemNew = phieuHenTiemAction.addPhieuHenTiem(phieuHenTiem);

                if (Validator.isNotNull(phieuHenTiemNew)) {

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
                    template_data.put(ZaloConstant.NgayTiemChung, phieuHenTiem.getNgayHenTiem() + " " + phieuHenTiem.getGioHenTiem());
                    template_data.put(ZaloConstant.DonViCap, uyBanNhanDan.getTenCoQuan());
                    template_data.put(ZaloConstant.DonViTiem, TenCoSo);
                    template_data.put(ZaloConstant.DiaDiem, DiaDiem);
                    template_data.put(ZaloConstant.LoaiThuocTiem, lichTiemChung.getLoaiThuocTiem());
                    template_data.put(ZaloConstant.QrCodeID, phieuHenTiem.getMaQR());

                    template_data.put(ZaloConstant.LanTiem, phieuHenTiem.getLanTiem());

                    String namSinh = nguoiTiemChung.getNgaySinh();

                    Date namSinhDate = DatetimeUtil.stringToDate(namSinh, DatetimeUtil._VN_DATE_FORMAT);

                    template_data.put(ZaloConstant.NamSinh, namSinhDate.getYear() + 1900);


                    hangChoThongBaoAction.addHangChoThongBao(template_data.toString(),
                            nguoiTiemChung.getSoDienThoai(),
                            nguoiTiemChung.getEmail(),
                            true,
                            ZaloConstant.Loai_Hen_TiemChung,
                            uyBanNhanDanId,
                            phieuHenTiem.getId());
                }


            }


            String msg = MessageUtil.getVNMessageText("phieuhentiem.update.trangthai.success");

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

    @RequestMapping(value = "/get/hangchothongbao", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getHangChoThongBao(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam("isSent") String isSent,
                                                @RequestParam("isReady") String isReady,
                                                @RequestParam("createDate") String createDate) {
        try {
            boolean isSentBoolean = Boolean.parseBoolean(isSent);
            boolean isReadyBoolean = Boolean.parseBoolean(isReady);

            long total = 0;
            List<HangChoThongBao> hangChoThongBaos = new ArrayList<>();
            hangChoThongBaos = hangChoThongBaoAction.findByIsSentIsReadyCreateDate(isSentBoolean, isReadyBoolean, createDate);
            total = hangChoThongBaos.size();

            return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(total, hangChoThongBaos));
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

    @RequestMapping(value = "/update/hangchothongbao/resetSendMessage", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> updateGuiLaiTinNhan(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<HangChoThongBao> hangChoThongBaoList = hangChoThongBaoAction.findByStatus(ZaloConstant.GUI_THAT_BAI);
            for (HangChoThongBao hangChoThongBao : hangChoThongBaoList) {
                hangChoThongBao.setStatus(ZaloConstant.CHUA_GUI);
                hangChoThongBao.setReady(true);
                hangChoThongBao.setSent(false);
                hangChoThongBaoAction.update(hangChoThongBao);
            }

            return ResponseEntity.status(HttpStatus.OK).body(new DataResponeBody(hangChoThongBaoList.size(), hangChoThongBaoList));
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