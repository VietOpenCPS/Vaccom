package org.vaccom.vcmgt.service;


import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.KhoaTruyCap;


@Service
public interface KhoaTruyCapService {
	
	public KhoaTruyCap findByKhoaTruyCap(String khoaTruyCap);
	
	public KhoaTruyCap findByDoiTuong(String doiTuong);
	
	public KhoaTruyCap updateKhoaTruyCap(KhoaTruyCap khoaTruyCap);

}
