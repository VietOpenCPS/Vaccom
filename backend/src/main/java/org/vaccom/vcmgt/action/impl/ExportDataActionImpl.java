package org.vaccom.vcmgt.action.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.ExportDataAction;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.service.NguoiTiemChungService;

import com.liferay.petra.string.StringPool;

@Service
public class ExportDataActionImpl implements ExportDataAction {

	@Value("${file.template-dir}")
	private String templates;

	@Value("${file.export-dir}")
	private String exportDir;

	@Autowired
	private NguoiTiemChungService nguoiTiemChungService;

	@Override
	public File exportNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung) throws Exception {

		File destDir = new File(exportDir);

		if (!destDir.exists()) {
			destDir.mkdir();
		}
		
		String fileDir = exportDir + "/" + System.currentTimeMillis() + ".xls";

		File export = new File(fileDir);

		long total = nguoiTiemChungService.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten, diabancosoid,
				cosoytema, tinhtrangdangki, kiemtratrung);
		
		System.out.println(total);

		List<NguoiTiemChung> lstNguoiTiemChung = nguoiTiemChungService.searchNguoiTiemChung(cmtcccd, nhomdoituong,
				ngaydangki, hovaten, diabancosoid, cosoytema, tinhtrangdangki, kiemtratrung, 0, (int) total);
		
		System.out.println(lstNguoiTiemChung.size());

		if (lstNguoiTiemChung != null) {

			File file = new File(templates + "/nguoitiemchung.xls");

			Workbook workbook = null;

			try {

				workbook = HSSFWorkbookFactory.create(file);

				Sheet sheet = workbook.getSheetAt(0);

				int rowIndex = 8;
				
				int stt = 1;

				for (NguoiTiemChung nguoiTiemChung : lstNguoiTiemChung) {

					Row row = sheet.createRow(rowIndex);

					Cell cell0 = row.createCell(0);
					cell0.setCellValue(stt);

					Cell cell1 = row.createCell(1);
					cell1.setCellValue(nguoiTiemChung.getHoVaTen());

					Cell cell2 = row.createCell(2);
					cell2.setCellValue(nguoiTiemChung.getNgaySinh());

					Cell cell3 = row.createCell(3);
					cell3.setCellValue(nguoiTiemChung.getGioiTinh());

					Cell cell4 = row.createCell(4);
					cell4.setCellValue(nguoiTiemChung.getNhomDoiTuong());

					Cell cell5 = row.createCell(5);
					cell5.setCellValue(nguoiTiemChung.getDonViCongTac());

					Cell cell6 = row.createCell(6);
					cell6.setCellValue(nguoiTiemChung.getSoDienThoai());

					Cell cell7 = row.createCell(7);
					cell7.setCellValue(nguoiTiemChung.getCmtcccd());

					Cell cell8 = row.createCell(8);
					cell8.setCellValue(nguoiTiemChung.getSoTheBHYT());

					Cell cell9 = row.createCell(9);
					cell9.setCellValue(nguoiTiemChung.getTinhThanhTen());

					Cell cell10 = row.createCell(10);
					cell10.setCellValue(nguoiTiemChung.getTinhThanhMa());

					Cell cell11 = row.createCell(11);
					cell11.setCellValue(nguoiTiemChung.getQuanHuyenTen());

					Cell cell12 = row.createCell(12);
					cell12.setCellValue(nguoiTiemChung.getQuanHuyenMa());

					Cell cell13 = row.createCell(13);
					cell13.setCellValue(nguoiTiemChung.getPhuongXaTen());

					Cell cell14 = row.createCell(14);
					cell14.setCellValue(nguoiTiemChung.getPhuongXaMa());

					Cell cell15 = row.createCell(15);
					cell15.setCellValue(nguoiTiemChung.getDiaChiNoiO());

					Cell cell16 = row.createCell(16);
					cell16.setCellValue(StringPool.BLANK);

					Cell cell17 = row.createCell(17);
					cell17.setCellValue(StringPool.BLANK);
					Cell cell18 = row.createCell(18);
					cell18.setCellValue(StringPool.BLANK);
					Cell cell19 = row.createCell(19);
					cell19.setCellValue(StringPool.BLANK);
					Cell cell20 = row.createCell(20);
					cell20.setCellValue(StringPool.BLANK);
					Cell cell21 = row.createCell(21);
					cell21.setCellValue(StringPool.BLANK);
					Cell cell22 = row.createCell(22);
					cell22.setCellValue(StringPool.BLANK);
					Cell cell23 = row.createCell(23);
					cell23.setCellValue(StringPool.BLANK);
					Cell cell24 = row.createCell(24);
					cell24.setCellValue(StringPool.BLANK);
					Cell cell25 = row.createCell(25);
					cell25.setCellValue(StringPool.BLANK);

					rowIndex++;
					stt++;
				}

				FileOutputStream outputStream = new FileOutputStream(export);

				workbook.write(outputStream);

				outputStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				workbook.close();
			}
		}

		return new File(fileDir);

	}
}
