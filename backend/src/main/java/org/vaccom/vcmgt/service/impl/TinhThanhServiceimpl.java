package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.TinhThanh;
import org.vaccom.vcmgt.repository.TinhThanhRepository;
import org.vaccom.vcmgt.service.TinhThanhService;

/**
 * @author vaccom
 *
 */
@Service
public class TinhThanhServiceimpl implements TinhThanhService {

	@Autowired
	private TinhThanhRepository tinhThanhRepository;

	@Override
	public TinhThanh findByTinhThanhMa(String tinhThanhMa) {

		return tinhThanhRepository.findByTinhThanhMa(tinhThanhMa);
	}

	@Override
	public List<TinhThanh> findAll() {

		return tinhThanhRepository.findAll();
	}

	@Override
	public TinhThanh updateTinhThanh(TinhThanh tinhThanh) {

		return tinhThanhRepository.save(tinhThanh);
	}

}
