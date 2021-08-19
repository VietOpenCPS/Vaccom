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
	public List<QuanHuyen> findByQuanHuyenMa_TinhThanhID(String quanHuyenMa, long tinhThanhID) {
		return quanHuyenRepository.findByQuanHuyenMa_TinhThanhID(quanHuyenMa, tinhThanhID);
	}

}
