package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.DoiTuong;

/**
 * @author vaccom
 *
 */
@Service
public interface DoiTuongService {
	
	public DoiTuong updateDoiTuong(DoiTuong doiTuong);
	
	public DoiTuong findByDoiTuongMa(String doiTuongMa);

	public List<DoiTuong> findAll();
}
