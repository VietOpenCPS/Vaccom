package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.CaTiemChung;

/**
 * @author vaccom
 *
 */
@Service
public interface CaTiemChungService {
	
	public void deleteById(long id);
	
	public CaTiemChung findById(long id);
	
	public long countCaTiemChung(Long lichTiemChungId, Long diaBanCoSoId);
	
	public List<CaTiemChung> searchCaTiemChung(Long lichTiemChungId, Long diaBanCoSoId, int page, int size);
	
	public CaTiemChung updateCaTiemChung(CaTiemChung caTiemChung);
		
}
