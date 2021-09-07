package org.vaccom.vcmgt.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.parameters.P;
import org.vaccom.vcmgt.constant.EntityConstant;
import org.vaccom.vcmgt.constant.MethodConstant;
import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.entity.GiayDiDuong;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.VaiTro;

import javax.management.relation.Role;

public class PermissionUtil {

	public static boolean canAccessGiayDiDuong(VaiTro vaiTro, GiayDiDuong giayDiDuong,
											   GiayDiDuongDto giayDiDuongDto, String method) {
		boolean ok    = true;
		boolean notOk = false;

		try {
			if(!RoleUtil.isCanBoUBND(vaiTro)) {
				return notOk;
			}

			if(method.equals(MethodConstant.CREATE) || method.equals(MethodConstant.GET)) {
				return ok;
			} else if (method.equals(MethodConstant.UPDATE)
					|| method.equals(MethodConstant.DELETE)) {

				if((int)vaiTro.getUyBanNhanDanId() != giayDiDuong.getUyBanNhanDanID()) {
					return notOk;
				}

				return ok;
			}

			return notOk;
		} catch (Exception e) {
			e.printStackTrace();
			return notOk;
		}
	}

	public static boolean canAccessNguoiDung(VaiTro vaiTro, NguoiDung nguoiDung, String reqBody, String method) {
		boolean ok    = true;
		boolean notOk = false;

		JsonNode bodyData = null;

		try {
			ObjectMapper mapper = new ObjectMapper();

			if(vaiTro == null) {
				return notOk;
			}

			if(method.equals(MethodConstant.GET)) {
				if(RoleUtil.isQuanTriHeThong(vaiTro)) {
					return ok;
				}
			} else if (method.equals(MethodConstant.CREATE)
					|| method.equals(MethodConstant.UPDATE)
					|| method.equals(MethodConstant.DELETE)
					|| method.equals(MethodConstant.LOCK)) {

				if(RoleUtil.isQuanTriHeThong(vaiTro)) {
					return ok;
				}

				int vaiTroUserNew;
				int diaBanUserNew;
				int coSoYteUserNew;

				if(method.equals(MethodConstant.CREATE)) {
					bodyData = mapper.readTree(reqBody);

					diaBanUserNew = bodyData.has(EntityConstant.DIABANCOSO_ID)
							 ? bodyData.get(EntityConstant.DIABANCOSO_ID).intValue() : 0;
					coSoYteUserNew = bodyData.has(EntityConstant.COSOYTE_ID)
							? bodyData.get(EntityConstant.COSOYTE_ID).intValue() : 0;
					vaiTroUserNew = bodyData.has(EntityConstant.VAITRO)
							? bodyData.get(EntityConstant.VAITRO).intValue() : RoleUtil.REGULAR;
				} else {
					//update, delete
					diaBanUserNew  = (int) nguoiDung.getDiaBanCoSoId();
					coSoYteUserNew = (int) nguoiDung.getCoSoYTeId();
					vaiTroUserNew  = nguoiDung.getQuanTriHeThong();
				}

				if(method.equals(MethodConstant.UPDATE)) {
					//update itself
					if(vaiTro.getCurrentId() == nguoiDung.getId()) {
						return ok;
					}
				}

				if(vaiTroUserNew == RoleUtil.SUPER_ADMIN || vaiTroUserNew == RoleUtil.REGION_ADMIN) {
					//user has only permission to create, update, delete account with lower role
					return notOk;
				}

				if(RoleUtil.isQuanTriCoSo(vaiTro)) {
					if(vaiTro.getCoSoYTeId() != 0 && vaiTro.getCoSoYTeId() == coSoYteUserNew) {
						return ok;
					}

					if(vaiTro.getDiaBanCoSoId() != 0 && vaiTro.getDiaBanCoSoId() == diaBanUserNew) {
						return ok;
					}
				}

			} else if (method.equals(MethodConstant.UPDATE_ROLE)) {
				if(RoleUtil.isQuanTriHeThong(vaiTro)) {
					return ok;
				}
			}

			return notOk;
		} catch (Exception e) {
			e.printStackTrace();
			return notOk;
		}
	}

	public static boolean hasAddNguoiDung(VaiTro vaiTro) {
		if(RoleUtil.isQuanTriHeThong(vaiTro)) {
			return true;
		}else if(RoleUtil.isQuanTriCoSo(vaiTro)) {
			//TODO check cosoyteid, diabancosoid
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean hasDeleteNguoiDung(VaiTro vaiTro) {
		if(RoleUtil.isQuanTriHeThong(vaiTro)) {
			return true;
		}else if(RoleUtil.isQuanTriCoSo(vaiTro)) {
			//TODO check cosoyteid, diabancosoid
			return true;
		}else {
			return false;
		}
	}
	
	
	
	public static boolean hasUpdateNguoiTiemChung(VaiTro vaiTro, NguoiTiemChung nguoiTiemChung) {
		return false;
	}
}
