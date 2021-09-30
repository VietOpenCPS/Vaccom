package org.vaccom.vcmgt.action.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.ExportDataAction;
import org.vaccom.vcmgt.entity.DiaBanCoSo;
import org.vaccom.vcmgt.entity.MuiTiemChung;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.service.DiaBanCoSoService;
import org.vaccom.vcmgt.service.MuiTiemChungService;
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

	@Autowired
	private MuiTiemChungService muiTiemChungService;
	
	@Autowired
	private DiaBanCoSoService diaBanCoSoService;

	@Override
	public File exportNguoiTiemChung(String cmtcccd, Integer nhomdoituong, String ngaydangki, String hovaten,
			Long diabancosoid, String cosoytema, Integer tinhtrangdangki, Integer kiemtratrung, String tinhthanhma, String tinhthanhten, String quanhuyenma
			,  String quanhuyenten, String phuongxama, String phuongxaten) throws Exception {

		File destDir = new File(exportDir);

		if (!destDir.exists()) {
			destDir.mkdir();
		}

		String fileDir = exportDir + "/" + System.currentTimeMillis() + ".xls";

		File export = new File(fileDir);

		long total = nguoiTiemChungService.countNguoiTiemChung(cmtcccd, nhomdoituong, ngaydangki, hovaten, diabancosoid,
				cosoytema, tinhtrangdangki, kiemtratrung, tinhthanhma, tinhthanhten, quanhuyenma, quanhuyenten, phuongxama,
				phuongxaten, false, null, null);

		List<NguoiTiemChung> lstNguoiTiemChung = nguoiTiemChungService.searchNguoiTiemChung(cmtcccd, nhomdoituong,
				ngaydangki, hovaten, diabancosoid, cosoytema, tinhtrangdangki, kiemtratrung, 0, (int) total,
				tinhthanhma, tinhthanhten, quanhuyenma, quanhuyenten, phuongxama, phuongxaten, false, null, null);

		if (lstNguoiTiemChung != null) {

			File file = new File(templates + "/nguoitiemchung.xls");
			
			//System.out.println(file.getCanonicalPath());

			Workbook workbook = null;

			try {

				workbook = HSSFWorkbookFactory.create(file);

				Sheet sheet = workbook.getSheetAt(0);

				int rowIndex = 8;

				int stt = 1;
				Cell cell = null;
				for (NguoiTiemChung nguoiTiemChung : lstNguoiTiemChung) {

					Row row = sheet.createRow(rowIndex);

					//Cell cell0 = row.createCell(0);
					//cell0.setCellValue(stt);
					cell = row.createCell(0);
					cell.setCellValue(stt);

					//Cell cell1 = row.createCell(1);
					//cell1.setCellValue(nguoiTiemChung.getHoVaTen());
					cell = row.createCell(1);
					cell.setCellValue(nguoiTiemChung.getHoVaTen());

					//Cell cell2 = row.createCell(2);
					//cell2.setCellValue(nguoiTiemChung.getNgaySinh());
					cell = row.createCell(2);
					cell.setCellValue(nguoiTiemChung.getNgaySinh());

					//Cell cell3 = row.createCell(3);
					//cell3.setCellValue(nguoiTiemChung.getGioiTinh());
					cell = row.createCell(3);
					cell.setCellValue(nguoiTiemChung.getGioiTinh());

					//Cell cell4 = row.createCell(4);
					//cell4.setCellValue(nguoiTiemChung.getNhomDoiTuong());
					cell = row.createCell(4);
					cell.setCellValue(nguoiTiemChung.getNhomDoiTuong());

					//Cell cell5 = row.createCell(5);
					//cell5.setCellValue(nguoiTiemChung.getDonViCongTac());
					cell = row.createCell(5);
					cell.setCellValue(nguoiTiemChung.getDonViCongTac());

					//Cell cell6 = row.createCell(6);
					//cell6.setCellValue(nguoiTiemChung.getSoDienThoai());
					cell = row.createCell(6);
					cell.setCellValue(nguoiTiemChung.getSoDienThoai());

					//Cell cell7 = row.createCell(7);
					//cell7.setCellValue(nguoiTiemChung.getCmtcccd());
					cell = row.createCell(7);
					cell.setCellValue(nguoiTiemChung.getCmtcccd());

					//Cell cell8 = row.createCell(8);
					//cell8.setCellValue(nguoiTiemChung.getSoTheBHYT());
					cell = row.createCell(8);
					cell.setCellValue(nguoiTiemChung.getSoTheBHYT());
					
					//Cell cell9 = row.createCell(9);
					//cell9.setCellValue(nguoiTiemChung.getTinhThanhTen());
					cell = row.createCell(9);
					cell.setCellValue(nguoiTiemChung.getTinhThanhTen());

					//Cell cell10 = row.createCell(10);
					//cell10.setCellValue(nguoiTiemChung.getTinhThanhMa());
					
					cell = row.createCell(10);
					cell.setCellValue(nguoiTiemChung.getTinhThanhMa());

					//Cell cell11 = row.createCell(11);
					//cell11.setCellValue(nguoiTiemChung.getQuanHuyenTen());
					Cell cell11 = row.createCell(11);
					cell11.setCellValue(nguoiTiemChung.getQuanHuyenTen());

					//Cell cell12 = row.createCell(12);
					//cell12.setCellValue(nguoiTiemChung.getQuanHuyenMa());
					cell = row.createCell(12);
					cell.setCellValue(nguoiTiemChung.getQuanHuyenMa());

					//Cell cell13 = row.createCell(13);
					//cell13.setCellValue(nguoiTiemChung.getPhuongXaTen());
					cell = row.createCell(13);
					cell.setCellValue(nguoiTiemChung.getPhuongXaTen());

					//Cell cell14 = row.createCell(14);
					//cell14.setCellValue(nguoiTiemChung.getPhuongXaMa());
					cell = row.createCell(14);
					cell.setCellValue(nguoiTiemChung.getPhuongXaMa());
					
					DiaBanCoSo diaBanCoSo = diaBanCoSoService.findById(nguoiTiemChung.getDiaBanCoSoId());
					//Cell cell15 = row.createCell(15);
					//cell15.setCellValue(diaBanCoSo != null ? diaBanCoSo.getTenDiaBan() : StringPool.BLANK);
					cell = row.createCell(15);
					cell.setCellValue(diaBanCoSo != null ? diaBanCoSo.getTenDiaBan() : StringPool.BLANK);

					//Cell cell16 = row.createCell(16);
					//cell16.setCellValue(nguoiTiemChung.getDiaChiNoiO());
					cell = row.createCell(16);
					cell.setCellValue(nguoiTiemChung.getDiaChiNoiO());

					List<MuiTiemChung> lstMuiTiemChung = muiTiemChungService
							.findByCongDan_ID(nguoiTiemChung.getCongDanID());
					//Cell cell17 = row.createCell(17);
					//cell17.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 1)
					//		? lstMuiTiemChung.get(0).getLoaiThuocTiem()
					//		: StringPool.BLANK);
					cell = row.createCell(17);
					cell.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 1)
							? lstMuiTiemChung.get(0).getLoaiThuocTiem()
							: StringPool.BLANK);

					//Cell cell18 = row.createCell(18);
					//cell18.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 1)
					//		? (lstMuiTiemChung.get(0).getNgayTiemChung() + StringPool.SPACE
					//				+ lstMuiTiemChung.get(0).getGioTiemChung())
					//		: StringPool.BLANK);
					cell = row.createCell(18);
					cell.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 1)
							? (lstMuiTiemChung.get(0).getNgayTiemChung() + StringPool.SPACE
									+ lstMuiTiemChung.get(0).getGioTiemChung())
							: StringPool.BLANK);
					
					//Cell cell19 = row.createCell(19);
					//cell19.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 1)
					//		? lstMuiTiemChung.get(0).getSoLoThuoc()
					//		: StringPool.BLANK);
					cell = row.createCell(19);
					cell.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 1)
							? lstMuiTiemChung.get(0).getSoLoThuoc()
							: StringPool.BLANK);
					
					cell = row.createCell(20);
					cell.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 1)
							? lstMuiTiemChung.get(0).getDiaDiemTiemChung()
							: StringPool.BLANK);
					
					//Cell cell21 = row.createCell(21);
					//cell21.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 2)
					//		? lstMuiTiemChung.get(1).getLoaiThuocTiem()
					//		: StringPool.BLANK);
					cell = row.createCell(21);
					cell.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 2)
							? lstMuiTiemChung.get(1).getLoaiThuocTiem()
							: StringPool.BLANK);
					
					//Cell cell22 = row.createCell(22);
					//cell22.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 2)
					//		? (lstMuiTiemChung.get(1).getNgayTiemChung() + StringPool.SPACE
					//				+ lstMuiTiemChung.get(1).getGioTiemChung())
					//		: StringPool.BLANK);
					cell = row.createCell(22);
					cell.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 2)
							? (lstMuiTiemChung.get(1).getNgayTiemChung() + StringPool.SPACE
									+ lstMuiTiemChung.get(1).getGioTiemChung())
							: StringPool.BLANK);
					
					//Cell cell23 = row.createCell(23);
					//cell23.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 2)
					//		? lstMuiTiemChung.get(1).getSoLoThuoc()
					//		: StringPool.BLANK);
					cell = row.createCell(23);
					cell.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 2)
							? lstMuiTiemChung.get(1).getSoLoThuoc()
							: StringPool.BLANK);
					
					//Cell cell24 = row.createCell(24);
					//cell24.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 2)
					//		? lstMuiTiemChung.get(1).getDiaDiemTiemChung()
					//		: StringPool.BLANK);
					cell = row.createCell(24);
					cell.setCellValue((lstMuiTiemChung != null && lstMuiTiemChung.size() >= 2)
							? lstMuiTiemChung.get(1).getDiaDiemTiemChung()
							: StringPool.BLANK);
					
					//Cell cell25 = row.createCell(25);
					//cell25.setCellValue(nguoiTiemChung.getGhiChu());
					cell = row.createCell(25);
					cell.setCellValue(nguoiTiemChung.getGhiChu());
					
					//Cell cell26 = row.createCell(26);
					//cell26.setCellValue(StringPool.BLANK);
					cell = row.createCell(26);
					cell.setCellValue(StringPool.BLANK);

					rowIndex++;
					stt++;
				}

				FileOutputStream outputStream = new FileOutputStream(export);

				workbook.write(outputStream);

				outputStream.close();

			} catch (Exception e) {
				_log.error(e);
			} finally {
				workbook.close();
			}
		}
		
		//System.out.println(new File(fileDir).getCanonicalPath());

		return new File(fileDir);

	}

	private final Log _log = LogFactory.getLog(ExportDataActionImpl.class);
}
