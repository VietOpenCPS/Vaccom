package org.vaccom.vcmgt.action.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

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
import org.vaccom.vcmgt.action.*;


import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.dto.LichLamViecDto;
import org.vaccom.vcmgt.util.DatetimeUtil;

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

	@Autowired
	private GiayDiDuongAction giayDiDuongAction;

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
					
					//System.out.println(cell.getCellType().getCode() + "|" + value + "|" + cell.getColumnIndex() + "|" + cellNumber);

					//rowData[cell.getColumnIndex()] = value;
					
					rowData[cellNumber] = value;

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


				case "giaydiduong":
					GiayDiDuongDto giayDiDuongDto = new GiayDiDuongDto();
					giayDiDuongDto.hoVaTen     = rowData[1] != null ? rowData[1] : "";
					giayDiDuongDto.soDienThoai = rowData[2] != null ? rowData[2] : "";
					giayDiDuongDto.noiODiaChi  =  rowData[3] != null ? rowData[3] : "";
					giayDiDuongDto.noiCtDiaChi =  rowData[4] != null ? rowData[4] : "";
					giayDiDuongDto.cmtcccd     =  rowData[5] != null ? rowData[5] : "";
					giayDiDuongDto.noiCtTenCoQuan =  rowData[6] != null ? rowData[6] : "";

					LichLamViecDto lichLamViecDto = new LichLamViecDto();

					List<Integer> listDayInWeek = null;
					List<String> listDayMonth = null ;
					List<String> listDayMonthFormatted = new ArrayList<>();
					String[] listHour;
					String fromHour = null;
					String toHour = null;

					if(rowData[7] != null && !rowData[7].isEmpty()) {
						try {
							listDayInWeek = Arrays.stream(rowData[7].replaceAll(" ", "").split(","))
									.map(Integer::parseInt)
									.collect(Collectors.toList());
						} catch (Exception e) {
							listDayInWeek = null;
							System.out.print("Error with: " + rowData[7]);
						}
					}

					if(rowData[8] != null && !rowData[8].isEmpty()) {
						try {
							listDayMonth = Arrays.stream(rowData[8].replaceAll(" ", "").split(","))
									.collect(Collectors.toList());

							for(String oneDayMonth: listDayMonth) {
								if(oneDayMonth.contains("/")) {
									continue;
								}

								Date date = DatetimeUtil.stringToDate(oneDayMonth, "ddMMyyyy");

								if(date == null) {
									continue;
								}

								oneDayMonth = DatetimeUtil.dateToString(date, "dd/MM/yyyy");
								listDayMonthFormatted.add(oneDayMonth);
							}

						} catch (Exception e) {
							System.out.print("Error with: " + rowData[8]);
						}
					}

					if(rowData[9] != null && !rowData[9].isEmpty()) {
						try {
							listHour = rowData[9].replaceAll(" ", "").split("-");
							if(listHour == null || listHour.length != 2) {
								fromHour = null;
								toHour = null;
							} else {
								fromHour = listHour[0];
								if(fromHour.length() == 4) {
									fromHour = fromHour.substring(0,2) + ":" + fromHour.substring(2,4);
								}
								toHour   = listHour[1];
								if(toHour.length() == 4) {
									toHour = toHour.substring(0,2) + ":" + toHour.substring(2,4);
								}

							}
						} catch (Exception e) {
							fromHour = null;
							toHour = null;
							System.out.print("Error with: " + rowData[9]);
						}
					}

					lichLamViecDto.ngayTuan = listDayInWeek;
					lichLamViecDto.ngayThang = listDayMonthFormatted;
					lichLamViecDto.tuGio = fromHour;
					lichLamViecDto.denGio = toHour;

					giayDiDuongDto.thoiHan  = rowData[10] != null ? rowData[10] : "";
					giayDiDuongDto.ghiChu   = rowData[11] != null ? rowData[11] : "";
					giayDiDuongDto.lichLamViec = lichLamViecDto;

					giayDiDuongAction.create(giayDiDuongDto);
				default:
					break;
				}
			}

			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

}
