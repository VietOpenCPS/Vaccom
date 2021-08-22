package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.MuiTiemChung;
import org.vaccom.vcmgt.repository.MuiTiemChungRepository;
import org.vaccom.vcmgt.service.MuiTiemChungService;

/**
 * @author vaccom
 *
 */
@Service
public class MuiTiemChungServiceImpl implements MuiTiemChungService {

	@Autowired
	private MuiTiemChungRepository muiTiemChungRepository;
	
	@Override
	public long countByCoSoYTeMa(String coSoYTeMa) {
		// TODO Auto-generated method stub
		return muiTiemChungRepository.countByCoSoYTeMa(coSoYTeMa);
	}

	@Override
	public MuiTiemChung findById(long id) {

		return muiTiemChungRepository.findById(id);
	}

	@Override
	public List<MuiTiemChung> findAll() {

		return muiTiemChungRepository.findAll();
	}

	@Override
	public MuiTiemChung updateMuiTiemChung(MuiTiemChung muiTiemChung) {

		return muiTiemChungRepository.save(muiTiemChung);
	}

	@Override
	public void deleteById(long id) {
		muiTiemChungRepository.deleteById(id);
	}

	@Override
	public List<MuiTiemChung> findByCmtcccd(String cmtcccd) {
		return muiTiemChungRepository.findByCmtcccd(cmtcccd);
	}

	@Override
	public List<MuiTiemChung> findByCoSoYTeMa(String coSoYTeMa, int page, int size) {
		if (page < 0 || size < 0) {
			page = 0;
			size = 30;
		}
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<MuiTiemChung> pases = muiTiemChungRepository.findByCoSoYTeMa(coSoYTeMa, pageable);
		return pases.getContent();
	}

}
