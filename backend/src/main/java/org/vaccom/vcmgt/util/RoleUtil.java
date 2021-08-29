package org.vaccom.vcmgt.util;

import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.entity.VaiTro;


public class RoleUtil {

	public final static int REGULAR = 0;

	public final static int SUPER_ADMIN = 1;

	public final static int REGION_ADMIN = 2;

	public static String getTenVaiTro(NguoiDung nguoiDung) {
		VaiTro vaiTro = new VaiTro(nguoiDung);
		return vaiTro.getTenVaiTro();
	}

	public static VaiTro getVaiTro(NguoiDung nguoiDung) {
		return new VaiTro(nguoiDung);
	}

	public static boolean hasUpdateObjectPermission(VaiTro vaiTro, Object object) {
		if (isQuanTriHeThong(vaiTro)) {
			return true;
		}

		if (object instanceof NguoiDung) {
			NguoiDung nguoiDung = (NguoiDung) object;
			if (isQuanTriCoSo(vaiTro) && (vaiTro.getCoSoYTeId() == nguoiDung.getCoSoYTeId()
					|| vaiTro.getDiaBanCoSoId() == nguoiDung.getDiaBanCoSoId()
					|| vaiTro.getUyBanNhanDanId() == nguoiDung.getUyBanNhanDanId())) {
				return true;
			}
		} else if (object instanceof NguoiTiemChung) {
			// TODO
			return false;
		} else {
			// TODO
			return false;
		}

		return false;
	}

	public static boolean isQuanTriHeThong(VaiTro vaiTro) {
		if (vaiTro == null) {
			return false;
		}

		return vaiTro.isQuanTriHeThong();

	}

	public static boolean isQuanTriCoSo(VaiTro vaiTro) {
		if (vaiTro == null) {
			return false;
		}
		return vaiTro.isQuanTriCoSo();
	}

	public static boolean isCanBoUBND(VaiTro vaiTro) {
		if (vaiTro == null) {
			return false;
		}
		return vaiTro.isCanBoUBND();
	}

	public static boolean isCanBoYTe(VaiTro vaiTro) {
		if (vaiTro == null) {
			return false;
		}
		return vaiTro.isCanBoYTe();
	}

	public static boolean isCanBoDiaBan(VaiTro vaiTro) {
		if (vaiTro == null) {
			return false;
		}
		return vaiTro.isCanBoDiaBan();
	}

	public static boolean isNguoiDung(VaiTro vaiTro) {
		if (vaiTro == null) {
			return false;
		}
		return vaiTro.isNguoiDung();
	}

	/*
	 * public static boolean hasNguoiDungPermission(VaiTro vaiTro) { return
	 * (hasCanBoUBNDPermission(vaiTro) || isCanBoYTe(vaiTro) ||
	 * isCanBoDiaBan(vaiTro) || isNguoiDung(vaiTro)); }
	 * 
	 * public static boolean hasCanBoDiaBanPermission(VaiTro vaiTro) { return
	 * (hasCanBoUBNDPermission(vaiTro) || isCanBoDiaBan(vaiTro)); }
	 * 
	 * public static boolean hasCanBoYTePermission(VaiTro vaiTro) { return
	 * (hasCanBoUBNDPermission(vaiTro) || isCanBoYTe(vaiTro)); }
	 * 
	 * public static boolean hasCanBoUBNDPermission(VaiTro vaiTro) { return
	 * (hasQuanTriCoSoPermission(vaiTro) || isCanBoUBND(vaiTro)); }
	 * 
	 * public static boolean hasQuanTriCoSoPermission(VaiTro vaiTro) { return
	 * (hasQuanTriHeThongPermission(vaiTro) || isQuanTriCoSo(vaiTro)); }
	 * 
	 * public static boolean hasQuanTriHeThongPermission(VaiTro vaiTro) { return
	 * isQuanTriHeThong(vaiTro); }
	 * 
	 */
}
