package org.vaccom.vcmgt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.LichTiemChung;

/**
 * @author vaccom
 *
 */
@Service
public interface LichTiemChungService {
	
	public long countAll();
	
	public long countCoSoYTeId(long id);
	
	public void deleteById(long id);
	
	public LichTiemChung updateLichTiemChung(LichTiemChung lichTiemChung);
	
	public LichTiemChung findById(long id);
		
	public List<LichTiemChung> findByCoSoYTeId(long id);
	
	public List<LichTiemChung> findByCoSoYTeId(long id, int page, int size);

	public List<LichTiemChung> findAll(int page, int size);

	Optional<LichTiemChung> findByQRCode(String qrCode);
}
