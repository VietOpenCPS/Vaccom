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

	public static enum VaiTro {
		QUANTRIHETHONG(1, "quantrihethong"), QUANTRICOSO(2, "quantricoso"), CANBOYTE(3, "canboyte"),
		CANBODIABAN(4, "canbodiaban"), NGUOIDUNG(5, "nguoidung");
		private int value;
		private String name;

		private VaiTro(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {

			return value;
		}

		public String getName() {

			return name;
		}

		@Override
		public String toString() {

			return this.getName();
		}

		public static VaiTro getEnum(int value) {

			for (VaiTro v : values())
				if (v.getValue() == value)
					return v;
			throw new IllegalArgumentException();
		}
	}

	public static String getRoleName(int vaiTro) {
		String roleName = StringPool.BLANK;
		switch (vaiTro) {
		case 1:
			roleName = VaiTro.QUANTRIHETHONG.getName();
			break;
		case 2:
			roleName = VaiTro.QUANTRICOSO.getName();
			break;
		case 3:
			roleName = VaiTro.CANBOYTE.getName();
			break;
		case 4:
			roleName = VaiTro.CANBODIABAN.getName();
			break;
		case 5:
			roleName = VaiTro.NGUOIDUNG.getName();
			break;
		default:
			break;
		}

		return roleName;
	}

	public static boolean isQuanTriHeThong(int vaiTro) {
		if (vaiTro == 1) {
			return true;
		}

		return false;
	}

	public static boolean isQuanTriCoSo(int vaiTro) {
		if (vaiTro == 2) {
			return true;
		}

		return false;
	}

	public static boolean isCanBoYTe(int vaiTro) {
		if (vaiTro == 3) {
			return true;
		}

		return false;
	}

	public static boolean isCanBoDiaBan(int vaiTro) {
		if (vaiTro == 4) {
			return true;
		}

		return false;
	}

	public static boolean isNguoiDung(int vaiTro) {
		if (vaiTro == 5) {
			return true;
		}

		return false;
	}
	
	public static boolean hasNguoiDungPermission(int vaiTro) {
		if (vaiTro <= VaiTro.NGUOIDUNG.getValue()) {
			return true;
		}

		return false;
	}

	public static boolean hasCanBoDiaBanPermission(int vaiTro) {
		if (vaiTro < VaiTro.NGUOIDUNG.getValue()) {
			return true;
		}

		return false;
	}

	public static boolean hasCanBoYTePermission(int vaiTro) {
		if (vaiTro < VaiTro.CANBODIABAN.getValue()) {
			return true;
		}

		return false;
	}

	public static boolean hasQuanTriCoSoPermission(int vaiTro) {
		if (vaiTro < VaiTro.CANBOYTE.getValue()) {
			return true;
		}

		return false;
	}

	public static boolean hasQuanTriHeThongPermission(int vaiTro) {
		if (vaiTro < VaiTro.QUANTRICOSO.getValue()) {
			return true;
		}

		return false;
	}

	public static String generateQRCode(String prefix, int unitLength) {
		RandomString random = new RandomString(unitLength);
		String code = random.nextString() + StringPool.DASH + random.nextString() + StringPool.DASH
				+ random.nextString() + StringPool.DASH + random.nextString() + StringPool.DASH + random.nextString();
		return prefix.toLowerCase() + StringPool.DASH + code.toLowerCase();
	}

}
