package org.vaccom.vcmgt.util;

import com.liferay.portal.kernel.util.Validator;

public class VaccomUtil {
	public static String getRoleName(boolean isQuanTriHeThong) {
		if (isQuanTriHeThong) {
			return "admin";
		}

		return "user";
	}

	public static boolean isQuanTriHeThong(String roleName) {
		if (Validator.isNotNull(roleName) && roleName.equalsIgnoreCase("admin")) {
			return true;
		}

		return false;
	}
}
