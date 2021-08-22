package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.NguoiTiemChung;
import org.vaccom.vcmgt.repository.NguoiTiemChungRepository;
import org.vaccom.vcmgt.service.NguoiTiemChungService;

@Service
public class NguoiTiemChungServiceImpl implements NguoiTiemChungService {
	@Autowired
	private NguoiTiemChungRepository nguoiTiemChungRepository;

	@Override
	public NguoiTiemChung findByCmtcccd(String cmtcccd) {

		return nguoiTiemChungRepository.findByCmtcccd(cmtcccd);
	}

	@Override
	public NguoiTiemChung findById(long id) {

		return nguoiTiemChungRepository.findById(id);
	}

	@Override
	public NguoiTiemChung updateNguoiTiemChung(NguoiTiemChung nguoiTiemChung) {

		return nguoiTiemChungRepository.save(nguoiTiemChung);
	}

	@Override
	public void deleteNguoiTiemChung(long id) throws Exception {
		nguoiTiemChungRepository.deleteById(id);
	}

	@Override
	public List<NguoiTiemChung> searchNguoiTiemChung(int page, int size) {
		if (page < 0 || size < 0) {
			page = 0;
			size = 30;
		}
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<NguoiTiemChung> pases = nguoiTiemChungRepository.findAll(pageable);
		return pases.getContent();

	}

	@Override
	public int countByDiaBanCoSoId(long id) {
		return nguoiTiemChungRepository.countByDiaBanCoSoId(id);
	}
}
