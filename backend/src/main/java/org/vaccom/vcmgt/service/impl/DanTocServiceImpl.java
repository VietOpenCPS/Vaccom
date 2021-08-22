package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.DanToc;
import org.vaccom.vcmgt.repository.DanTocRepository;
import org.vaccom.vcmgt.service.DanTocService;

/**
 * @author vaccom
 *
 */
@Service
public class DanTocServiceImpl implements DanTocService {

	@Autowired
	private DanTocRepository danTocRepository;

	@Override
	public DanToc findByDanTocMa(String danTocMa) {

		return danTocRepository.findByDanTocMa(danTocMa);
	}

	@Override
	public List<DanToc> findAll() {

		return danTocRepository.findAll();
	}

	@Override
	public DanToc updateDanToc(DanToc danToc) {

		return danTocRepository.save(danToc);
	}

}
