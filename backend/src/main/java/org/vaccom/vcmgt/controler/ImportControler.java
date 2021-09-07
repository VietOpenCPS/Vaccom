package org.vaccom.vcmgt.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.vaccom.vcmgt.action.ImportDataAction;
import org.vaccom.vcmgt.entity.VaiTro;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.RoleUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/v1/import")
public class ImportControler {
	@Autowired
	private ImportDataAction importDataAction;

	@RequestMapping(value = "/exceldata", method = RequestMethod.POST)
	public ResponseEntity<?> importQuocGia(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file, @RequestParam(name = "sheetAt", defaultValue = "0") int sheetAt,
			@RequestParam("startCol") int startCol, @RequestParam("endCol") int endCol,
			@RequestParam("startRow") int startRow, @RequestParam("endRow") int endRow,
			@RequestParam("table") String table) {
		try {
			VaiTro vaiTro = (VaiTro) request.getAttribute("_VAI_TRO");

			if (!RoleUtil.isCanBoDiaBan(vaiTro)
					&& !RoleUtil.isCanBoYTe(vaiTro)
					&& !RoleUtil.isQuanTriCoSo(vaiTro)
					&& !RoleUtil.isQuanTriHeThong(vaiTro)
					&& !RoleUtil.isCanBoUBND(vaiTro)
			) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(MessageUtil.getVNMessageText("data.import.permission_error"));
			}
			importDataAction.importData(vaiTro, table, file, sheetAt, startCol, endCol, startRow, endRow);
			String msg = MessageUtil.getVNMessageText("data.import." + table + ".success");

			return ResponseEntity.status(HttpStatus.OK).body(msg);
		} catch (Exception e) {
			_log.error(e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}

	private Log _log = LogFactory.getLog(ImportControler.class);
}
