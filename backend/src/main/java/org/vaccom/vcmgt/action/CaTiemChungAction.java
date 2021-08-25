package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.CaTiemChung;

/**
 * @author vaccom
 *
 */
@Service
public interface CaTiemChungAction {

	public CaTiemChung addCaTiemChung(String reqBody) throws Exception;

	public boolean deleteById(long id) throws Exception;

	public CaTiemChung findById(long id);

	public long countCaTiemChung(Long lichTiemChungId, Long diaBanCoSoId);
	
	public List<CaTiemChung> searchCaTiemChung(Long lichTiemChungId, Long diaBanCoSoId, int page, int size);	

	public CaTiemChung updateCaTiemChung(long id, String reqBody) throws Exception;
}
