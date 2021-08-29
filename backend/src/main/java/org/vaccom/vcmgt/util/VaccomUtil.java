package org.vaccom.vcmgt.util;

import org.vaccom.vcmgt.security.impl.RandomString;

import com.liferay.petra.string.StringPool;

public class VaccomUtil {
	
	public static final int MOIDANGKY = 0;
	public static final int DANGKYCHINHTHUC = 1;
	public static final int XOADANGKY = 2;
	
	public static final int CHUAKIEMTRA = 0;
	public static final int KIEMTRAKHONGTRUNG = 1;
	public static final int KIEMTRACOTRUNG = 2;
	
	public static final int DANGMO = 0;
	public static final int DADONG = 1;
	
	public static final int DUKIEN = 0;
	public static final int HENGOICHOXACNHAN = 1;
	public static final int HENDAXACNHAN = 2;
	public static final int DACHECKIN = 3;
	
	public static final int DATIEMXONG = 4;
	public static final int CHUADUOCTIEM = 5;
	public static final int XACNHANKHONGDEN = 5;

	
	public static String generateQRCode(String prefix, int unitLength) {
		RandomString random = new RandomString(unitLength);
		String code = random.nextString() + StringPool.DASH + random.nextString() + StringPool.DASH
				+ random.nextString() + StringPool.DASH + random.nextString() + StringPool.DASH + random.nextString();
		return prefix.toLowerCase() + StringPool.DASH + code.toLowerCase();
	}

}
