package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.CoSoYTe;
import org.vaccom.vcmgt.repository.CoSoYTeRepository;
import org.vaccom.vcmgt.service.CoSoYTeService;

/**
 * @author vaccom
 *
 */
@Service
public class CoSoYTeServiceImpl implements CoSoYTeService {

	@Autowired
	private CoSoYTeRepository coSoYTeRepository;

	@Override
	public CoSoYTe findById(long id) {

		return coSoYTeRepository.findById(id);
	}

	@Override
	public List<CoSoYTe> findAll() {

		return coSoYTeRepository.findAll();
	}

	@Override
	public CoSoYTe updateCoSoYTe(CoSoYTe coSoYTe) {

		return coSoYTeRepository.save(coSoYTe);
	}

	@Override
	public void deleteById(long id) {
		coSoYTeRepository.deleteById(id);
	}

	@Override
	public CoSoYTe findByMaCoSo(String maCoSo) {
		return coSoYTeRepository.findByMaCoSo(maCoSo);
	}

	@Override
	public List<CoSoYTe> findAll(int page, int size) {

		if (page < 0 || size < 0) {
			page = 0;
			size = 30;
		}
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<CoSoYTe> pases = coSoYTeRepository.findAll(pageable);
		return pases.getContent();
	}

}
