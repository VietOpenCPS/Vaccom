package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.QuanHuyen;
import org.vaccom.vcmgt.repository.QuanHuyenRepository;
import org.vaccom.vcmgt.service.QuanHuyenService;

/**
 * @author vaccom
 *
 */
@Service
public class QuanHuyenServiceImpl implements QuanHuyenService {
	@Autowired
	private QuanHuyenRepository quanHuyenRepository;

	@Override
	public QuanHuyen findByQuanHuyenMa(String quanHuyenMa) {

		return quanHuyenRepository.findByQuanHuyenMa(quanHuyenMa);
	}

	@Override
	public List<QuanHuyen> findByTinhThanhID(long tinhThanhID) {
		return quanHuyenRepository.findByTinhThanhID(tinhThanhID);
	}

	@Override
	public QuanHuyen updateQuanHuyen(QuanHuyen quanHuyen) {

		return quanHuyenRepository.save(quanHuyen);
	}

}
