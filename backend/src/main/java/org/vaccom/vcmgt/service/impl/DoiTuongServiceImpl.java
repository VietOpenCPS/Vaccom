package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.DoiTuong;
import org.vaccom.vcmgt.repository.DoiTuongRepository;
import org.vaccom.vcmgt.service.DoiTuongService;

/**
 * @author vaccom
 *
 */
@Service
public class DoiTuongServiceImpl implements DoiTuongService{
	@Autowired
	private DoiTuongRepository doiTuongRepository;
	
	@Override
	public DoiTuong findByDoiTuongMa(String doiTuongMa) {
		
		return doiTuongRepository.findByDoiTuongMa(doiTuongMa);
	}

	@Override
	public List<DoiTuong> findAll() {
		return doiTuongRepository.findAll();
	}

	@Override
	public DoiTuong updateDoiTuong(DoiTuong doiTuong) {

		return doiTuongRepository.save(doiTuong);
	}
	
}
