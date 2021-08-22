package org.vaccom.vcmgt.controler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.vaccom.vcmgt.action.ImportDataAction;

import org.vaccom.vcmgt.util.MessageUtil;

@RestController
@RequestMapping("/rest/v1/import")
public class ImportControler {
	@Autowired
	private ImportDataAction importDataAction;

	@RequestMapping(value = "/exceldata", method = RequestMethod.POST)
	public ResponseEntity<?> importQuocGia(@RequestParam("file") MultipartFile file,
			@RequestParam("sheetAt") int sheetAt, @RequestParam("startCol") int startCol,
			@RequestParam("endCol") int endCol, @RequestParam("startRow") int startRow,
			@RequestParam("endRow") int endRow, @RequestParam("table") String table) {
		try {
			importDataAction.importData(table, file, sheetAt, startCol, endCol, startRow, endRow);
			String msg = MessageUtil.getVNMessageText("import.data.quocgia.success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}

	private Log _log = LogFactory.getLog(ImportControler.class);
}
