package org.vaccom.vcmgt.action.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.ExportDataAction;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.service.NguoiTiemChungService;

@Service
public class ExportDataActionImpl implements ExportDataAction {

	@Value("${file.template-dir}")
	private String templates;

	@Autowired
	private NguoiTiemChungService nguoiTiemChungService;

	@Override
	public File exportNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung) throws Exception {

		long total = nguoiTiemChungService.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten, diabancosoid,
				cosoytema, tinhtrangdangki, kiemtratrung);

		List<NguoiTiemChung> lstNguoiTiemChung = nguoiTiemChungService.searchNguoiTiemChung(cmtcccd, nhomdoituong,
				ngaydangki, hovaten, diabancosoid, cosoytema, tinhtrangdangki, kiemtratrung, 0, (int) total);
		
		if(lstNguoiTiemChung != null) {
			
		}

		File file = new File(templates + "/nguoitiemchung.xls");

		System.out.println(file.getCanonicalPath());

		return file;

	}
}
