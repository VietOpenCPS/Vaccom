package org.vaccom.vcmgt.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

public class DatetimeUtil {

	public static final String _VN_TIME_ZONE_ = "Asia/Ho_Chi_Minh";

	public static final String _VN_DATE_FORMAT = "dd/MM/yyyy";

	public static String dateToString(Date date, String pattern) {

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);

		if (Validator.isNull(date) || Validator.isNull(pattern)) {
			return StringPool.BLANK;
		}

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		return formatter.format(calendar.getTime());
	}

	public static Date stringToDate(String dateString, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			return formatter.parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}
}
