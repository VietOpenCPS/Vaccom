package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.CoSoYTe;

/**
 * @author vaccom
 *
 */
@Service
public interface CoSoYTeService {
	
	public void deleteById(long id);
	
	public CoSoYTe updateCoSoYTe(CoSoYTe coSoYTe);
	
	public CoSoYTe findById(long id);
	
	public CoSoYTe findByMaCoSo(String maCoSo);
		
	public List<CoSoYTe> findAll(int page, int size);

	public List<CoSoYTe> findAll();
}
