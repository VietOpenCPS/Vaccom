package org.vaccom.vcmgt.util;

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
}
