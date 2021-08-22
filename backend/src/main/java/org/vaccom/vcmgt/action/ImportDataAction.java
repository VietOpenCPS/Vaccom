package org.vaccom.vcmgt.action;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImportDataAction {
	public void importData(String table, MultipartFile file, int sheetAt, int startCol, int endCol, int startRow,
			int endRow) throws Exception;
}
