package org.vaccom.vcmgt.action;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.vaccom.vcmgt.entity.VaiTro;

@Service
public interface ImportDataAction {
	public void importData(VaiTro vaiTro, String table, MultipartFile file, int sheetAt, int startCol, int endCol, int startRow,
						   int endRow) throws Exception;
}
