package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.PhuongXa;
import org.vaccom.vcmgt.repository.PhuongXaRepository;
import org.vaccom.vcmgt.service.PhuongXaService;

/**
 * @author vaccom
 *
 */
@Service
public class PhuongXaServiceImpl implements PhuongXaService {
	@Autowired
	private PhuongXaRepository phuongXaRepository;

	@Override
	public PhuongXa findByPhuongXaMa(String phuongXaMa) {

		return phuongXaRepository.findByPhuongXaMa(phuongXaMa);
	}

	@Override
	public List<PhuongXa> findByPhuongXaMa_QuanHuyenID(String phuongXaMa, long quanHuyenID) {

		return phuongXaRepository.findByPhuongXaMa_QuanHuyenID(phuongXaMa, quanHuyenID);
	}
}
