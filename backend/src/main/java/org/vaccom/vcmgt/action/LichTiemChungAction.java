package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.LichTiemChung;

/**
 * @author vaccom
 *
 */
@Service
public interface LichTiemChungAction {
	
	public long countAll();
	
	public long countCoSoYTeId(long id);
	
	public LichTiemChung addLichTiemChung(String reqBody) throws Exception;
	
	public boolean deleteById(long id) throws Exception;
	
	public LichTiemChung findById(long id);
	
	public List<LichTiemChung> findAll(int page, int size);
	
	public List<LichTiemChung> findByCoSoYTeId(long id, int page, int size);

	public LichTiemChung updateLichTiemChung(long id, String reqBody) throws Exception;
}
