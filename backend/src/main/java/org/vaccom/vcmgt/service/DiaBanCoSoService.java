package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.DiaBanCoSo;

/**
 * @author vaccom
 *
 */
@Service
public interface DiaBanCoSoService {
	
	public int countByCoSoYTeId(long id);
	
	public void deleteById(long id);
	
	public DiaBanCoSo updateDiaBanCoSo(DiaBanCoSo diaBanCoSo);
	
	public DiaBanCoSo findById(long id);
	
	public List<DiaBanCoSo> findByCoSoYTeId(long id);

	public List<DiaBanCoSo> findAll();
}
