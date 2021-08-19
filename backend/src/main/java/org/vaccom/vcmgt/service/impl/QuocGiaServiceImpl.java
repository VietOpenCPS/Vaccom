package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.QuocGia;
import org.vaccom.vcmgt.repository.QuocGiaRepository;
import org.vaccom.vcmgt.service.QuocGiaService;

/**
 * @author vaccom
 *
 */
@Service
public class QuocGiaServiceImpl implements QuocGiaService{
	@Autowired
	private QuocGiaRepository quocGiaRepository;
	
	@Override
	public QuocGia findByQuocGiaMa(String quocGiaMa) {
		
		return quocGiaRepository.findByQuocGiaMa(quocGiaMa);
	}

	@Override
	public List<QuocGia> findAll() {
		return quocGiaRepository.findAll();
	}
	
}
