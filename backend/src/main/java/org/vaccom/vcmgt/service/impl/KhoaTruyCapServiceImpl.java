package org.vaccom.vcmgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.KhoaTruyCap;
import org.vaccom.vcmgt.repository.KhoaTruyCapRepository;
import org.vaccom.vcmgt.service.KhoaTruyCapService;

@Service
public class KhoaTruyCapServiceImpl implements KhoaTruyCapService {

	@Autowired
	private KhoaTruyCapRepository khoaTruyCapRepository;

	@Override
	public KhoaTruyCap findByKhoaTruyCap(String khoaTruyCap) {
		return khoaTruyCapRepository.findByKhoaTruyCap(khoaTruyCap);
	}

	@Override
	public KhoaTruyCap updateKhoaTruyCap(KhoaTruyCap khoaTruyCap) {
		return khoaTruyCapRepository.save(khoaTruyCap);
	}

	@Override
	public KhoaTruyCap findByDoiTuong(String doiTuong) {
		return khoaTruyCapRepository.findByDoiTuong(doiTuong);
	}

}
