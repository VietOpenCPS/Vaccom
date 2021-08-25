package org.vaccom.vcmgt.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;

public class DatetimeUtil {
	
	public static final String _VN_TIME_ZONE_ = "Asia/Ho_Chi_Minh";
	
	public static final String _VN_DATE_FORMAT = "dd/MM/yyyy";

	public static String dateToString(Date date, String pattern) {
		
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(pattern);

		if (Validator.isNull(date) || Validator.isNull(pattern)) {
			return StringPool.BLANK;
		}

		dateFormat.setTimeZone(TimeZoneUtil.getTimeZone(_VN_TIME_ZONE_));

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		return dateFormat.format(calendar.getTime());
	}
}
