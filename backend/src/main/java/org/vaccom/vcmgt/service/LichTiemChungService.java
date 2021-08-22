package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.LichTiemChung;

/**
 * @author vaccom
 *
 */
@Service
public interface LichTiemChungService {
	
	public long countAll();
	
	public void deleteById(long id);
	
	public LichTiemChung updateLichTiemChung(LichTiemChung lichTiemChung);
	
	public LichTiemChung findById(long id);
		
	public List<LichTiemChung> findCoSoYTeId(long id);

	public List<LichTiemChung> findAll(int page, int size);
}
