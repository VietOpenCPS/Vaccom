package org.vaccom.vcmgt.action;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public interface ExportDataAction {
	
	public File exportNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki,
			String hovaten, Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung, String tinhthanhma, String tinhthanhten, String quanhuyenma
			,  String quanhuyenten, String phuongxama, String phuongxaten) throws Exception;
}

