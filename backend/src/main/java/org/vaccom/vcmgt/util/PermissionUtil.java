package org.vaccom.vcmgt.util;

import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.VaiTro;

public class PermissionUtil {
	
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
