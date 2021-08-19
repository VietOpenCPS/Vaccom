package org.vaccom.vcmgt.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.repository.KhoaDangKyRepository;
import org.vaccom.vcmgt.service.KhoaDangKyService;


@Service
public class KhoaDangKyServiceImpl implements KhoaDangKyService{

	@Autowired
	private KhoaDangKyRepository khoaRepository;
	
	@Override
	public KhoaDangKy findByNguoiDungID(long nguoiDungID) {
		return khoaRepository.findByNguoiDungID(nguoiDungID);
	}
	
}
