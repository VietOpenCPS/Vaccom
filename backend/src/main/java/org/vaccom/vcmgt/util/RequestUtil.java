package org.vaccom.vcmgt.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

public class RequestUtil {
	public static String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return StringPool.BLANK;
	}
	
}
