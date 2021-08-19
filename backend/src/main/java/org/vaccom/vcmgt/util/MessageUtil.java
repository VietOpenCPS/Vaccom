package org.vaccom.vcmgt.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil {
	
	public static String getVNMessageText(String msgKey) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("language", new Locale("vi_VN"));
		if (resourceBundle.containsKey(msgKey)) {
			msgKey = resourceBundle.getString(msgKey);
		}
		return msgKey;
	}
}
