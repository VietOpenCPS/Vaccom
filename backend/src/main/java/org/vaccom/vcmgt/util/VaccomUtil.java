package org.vaccom.vcmgt.util;

import org.vaccom.vcmgt.security.impl.RandomString;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

public class VaccomUtil {

	public static String getManagerRoleName(boolean isQuanTriHeThong) {
		if (isQuanTriHeThong) {
			return "admin";
		}

		return "mod";
	}

	public static boolean isQuanTriHeThong(String roleName) {
		if (Validator.isNotNull(roleName) && roleName.equalsIgnoreCase("admin")) {
			return true;
		}

		return false;
	}

	public static boolean isCanBoXuLy(String roleName) {
		if (Validator.isNotNull(roleName) && (roleName.equalsIgnoreCase("admin") || roleName.equalsIgnoreCase("mod"))) {
			return true;
		}
		return false;
	}

	public static boolean isNguoiDung(String roleName) {
		if (Validator.isNotNull(roleName) && (roleName.equalsIgnoreCase("admin") || roleName.equalsIgnoreCase("mod")
				|| roleName.equalsIgnoreCase("default"))) {
			return true;
		}
		return false;
	}

	public static boolean hasUpdatePermission(String roleName) {
		return (isCanBoXuLy(roleName) || isQuanTriHeThong(roleName));
	}

	public static String generateQRCode(String prefix, int unitLength) {
		RandomString random = new RandomString(unitLength);
		String code = random.nextString() + StringPool.DASH + random.nextString() + StringPool.DASH
				+ random.nextString() + StringPool.DASH + random.nextString() + StringPool.DASH + random.nextString();
		return prefix.toLowerCase() + StringPool.DASH + code.toLowerCase();
	}
	
}
