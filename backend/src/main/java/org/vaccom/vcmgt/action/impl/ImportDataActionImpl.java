package org.vaccom.vcmgt.action.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vaccom.vcmgt.action.CoSoYTeAction;
import org.vaccom.vcmgt.action.DanTocAction;
import org.vaccom.vcmgt.action.DiaBanCoSoAction;
import org.vaccom.vcmgt.action.DoiTuongAction;
import org.vaccom.vcmgt.action.DonViHanhChinhAction;
import org.vaccom.vcmgt.action.FileStorageAction;
import org.vaccom.vcmgt.action.ImportDataAction;
import org.vaccom.vcmgt.action.NguoiTiemChungAction;
import org.vaccom.vcmgt.action.QuocGiaAction;


import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

@Service
public class ImportDataActionImpl implements ImportDataAction {
	@Autowired
	private FileStorageAction fileStorageAction;

	@Autowired
	private QuocGiaAction quocGiaAction;

	@Autowired
	private DanTocAction danTocAction;

	@Autowired
	private DoiTuongAction doiTuongAction;

	@Autowired
	private CoSoYTeAction coSoYTeAction;

	@Autowired
	private DiaBanCoSoAction diaBanCoSoAction;

	@Autowired
	private NguoiTiemChungAction nguoiTiemChungAction;

	@Autowired
	private DonViHanhChinhAction donViHanhChinhAction;

	@Override
	public void importData(String table, MultipartFile file, int sheetAt, int startCol, int endCol, int startRow,
			int endRow) throws Exception {
		HSSFWorkbook workbook = null;
		try {

			File tmp = fileStorageAction.storeFile(file, String.valueOf(System.currentTimeMillis()));

			workbook = new HSSFWorkbook(new FileInputStream(tmp));
			Sheet sheet = workbook.getSheetAt(sheetAt);
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber < startRow) {
					rowNumber++;
					continue;
				}

				if (rowNumber > endRow) {
					break;
				}

				rowNumber++;

				Iterator<Cell> cellsInRow = currentRow.iterator();

				int cellNumber = 0;

				String[] rowData = new String[endCol - startCol + 1];

				while (cellsInRow.hasNext()) {

					if (cellNumber < startCol) {

						cellNumber++;

						continue;
					}

					if (cellNumber > endCol) {
						break;
					}

					Cell cell = cellsInRow.next();

					String value = StringPool.BLANK;
					
					if (cell.getCellType().getCode() == CellType.NUMERIC.getCode()) {
						value = String.format("%d",(long)cell.getNumericCellValue());
					} else if (cell.getCellType().getCode() == CellType.BOOLEAN.getCode()) {
						value = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType().getCode() == CellType.ERROR.getCode()) {
						value = StringPool.BLANK;
					} else {
						value = cell.getStringCellValue();
					}
					
					System.out.println(cell.getCellType().getCode() + "|" + value);

					rowData[cell.getColumnIndex()] = value;

					cellNumber++;
				}

				// insert data

				switch (table) {
				case "quocgia":
					quocGiaAction.addQuocGia(rowData[0], rowData[1]);
					break;
				case "dantoc":
					danTocAction.addDanToc(rowData[0], rowData[1]);
					break;
				case "donvihanhchinh":
					donViHanhChinhAction.addDonViHanhChinh(rowData[1], rowData[0], rowData[3], rowData[2], rowData[5],
							rowData[4]);
					break;
				case "doituong":
					doiTuongAction.addDoiTuong(rowData[0], rowData[1]);
					break;
				case "cosoyte":
					coSoYTeAction.addCoSoYTe(rowData[0], StringPool.BLANK, rowData[1], StringPool.BLANK, rowData[2],
							StringPool.BLANK, rowData[3], rowData[4], StringPool.BLANK, StringPool.BLANK,
							StringPool.BLANK);
					break;

				case "diabancoso":
					diaBanCoSoAction.addDiaBanCoSo(rowData[0], StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
							StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 0);
					break;

				case "nguoitiemchung":
					nguoiTiemChungAction.addNguoiTiemChung(rowData[1], rowData[2], GetterUtil.getInteger(rowData[3], 2),
							rowData[7], GetterUtil.getInteger(rowData[4], 0), rowData[5], rowData[6], StringPool.BLANK,
							rowData[8], rowData[15], rowData[10], rowData[9], rowData[12], rowData[11], rowData[14],
							rowData[13], GetterUtil.getLong(rowData[0], 0), StringPool.BLANK, StringPool.BLANK,
							StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
							StringPool.BLANK, StringPool.BLANK, 0);
					break;
				default:
					break;
				}
			}

			workbook.close();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

}
