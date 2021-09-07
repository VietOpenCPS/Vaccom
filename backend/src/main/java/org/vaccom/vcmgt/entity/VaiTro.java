package org.vaccom.vcmgt.entity;

import org.vaccom.vcmgt.util.RoleUtil;

import com.liferay.petra.string.StringPool;

public class VaiTro {

	public boolean isQuanTriHeThong;

	public boolean isQuanTriCoSo;

	public boolean isCanBoUBND;

	public boolean isCanBoYTe;

	public boolean isCanBoDiaBan;

	public boolean isNguoiDung;

	public long uyBanNhanDanId;

	public long nguoiTiemChungId;

	public long coSoYTeId;

	public long diaBanCoSoId;

	public long currentId;


	public VaiTro(NguoiDung nguoiDung) {

		if (nguoiDung == null) {
			this.setCanBoUBND(false);
			this.setCanBoYTe(false);
			this.setCanBoDiaBan(false);
			this.setNguoiDung(false);
			this.setQuanTriCoSo(false);
			this.setQuanTriHeThong(false);

			this.setNguoiTiemChungId(0);
			this.setDiaBanCoSoId(0);
			this.setCoSoYTeId(0);
			this.setUyBanNhanDanId(0);
		}

		this.setCanBoUBND(nguoiDung.getUyBanNhanDanId() > 0 ? true : false);
		this.setCanBoYTe(nguoiDung.getCoSoYTeId() > 0 ? true : false);
		this.setCanBoDiaBan(nguoiDung.getDiaBanCoSoId() > 0 ? true : false);
		this.setNguoiDung(nguoiDung.getNguoiTiemChungId() > 0 ? true : false);
		this.setQuanTriCoSo(nguoiDung.getQuanTriHeThong() == RoleUtil.REGION_ADMIN ? true : false);
		this.setQuanTriHeThong(nguoiDung.getQuanTriHeThong() == RoleUtil.SUPER_ADMIN ? true : false);
		this.setNguoiTiemChungId(nguoiDung.getNguoiTiemChungId());
		this.setDiaBanCoSoId(nguoiDung.getDiaBanCoSoId());
		this.setCoSoYTeId(nguoiDung.getCoSoYTeId());
		this.setUyBanNhanDanId(nguoiDung.getUyBanNhanDanId());
	}

	public String getTenVaiTro() {
		if (isQuanTriHeThong) {
			return "QuanTriHeThong";
		} else if (isQuanTriCoSo) {
			return "QuanTriCoSo";
		} else if (isCanBoUBND) {
			return "CanBoUBND";
		} else if (isCanBoYTe) {
			return "CanBoYTe";
		} else if (isCanBoDiaBan) {
			return "CanBoDiaBan";
		} else if (isNguoiDung) {
			return "NguoiDung";
		} else {
			return StringPool.BLANK;
		}
	}

	public long getCurrentId() {
		return currentId;
	}

	public void setCurrentId(long currentId) {
		this.currentId = currentId;
	}

	public boolean isQuanTriHeThong() {
		return isQuanTriHeThong;
	}

	public void setQuanTriHeThong(boolean isQuanTriHeThong) {
		this.isQuanTriHeThong = isQuanTriHeThong;
	}

	public boolean isQuanTriCoSo() {
		return isQuanTriCoSo;
	}

	public void setQuanTriCoSo(boolean isQuanTriCoSo) {
		this.isQuanTriCoSo = isQuanTriCoSo;
	}

	public boolean isCanBoUBND() {
		return isCanBoUBND;
	}

	public void setCanBoUBND(boolean isCanBoUBND) {
		this.isCanBoUBND = isCanBoUBND;
	}

	public boolean isCanBoYTe() {
		return isCanBoYTe;
	}

	public void setCanBoYTe(boolean isCanBoYTe) {
		this.isCanBoYTe = isCanBoYTe;
	}

	public boolean isCanBoDiaBan() {
		return isCanBoDiaBan;
	}

	public void setCanBoDiaBan(boolean isCanBoDiaBan) {
		this.isCanBoDiaBan = isCanBoDiaBan;
	}

	public boolean isNguoiDung() {
		return isNguoiDung;
	}

	public void setNguoiDung(boolean isNguoiDung) {
		this.isNguoiDung = isNguoiDung;
	}

	public long getUyBanNhanDanId() {
		return uyBanNhanDanId;
	}

	public void setUyBanNhanDanId(long uyBanNhanDanId) {
		this.uyBanNhanDanId = uyBanNhanDanId;
	}

	public long getNguoiTiemChungId() {
		return nguoiTiemChungId;
	}

	public void setNguoiTiemChungId(long nguoiTiemChungId) {
		this.nguoiTiemChungId = nguoiTiemChungId;
	}

	public long getCoSoYTeId() {
		return coSoYTeId;
	}

	public void setCoSoYTeId(long coSoYTeId) {
		this.coSoYTeId = coSoYTeId;
	}

	public long getDiaBanCoSoId() {
		return diaBanCoSoId;
	}

	public void setDiaBanCoSoId(long diaBanCoSoId) {
		this.diaBanCoSoId = diaBanCoSoId;
	}

}
