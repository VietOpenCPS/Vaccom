package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaccom.vcmgt.entity.KhoaDangKy;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.repository.KhoaDangKyRepository;
import org.vaccom.vcmgt.repository.KhoaTruyCapRepository;
import org.vaccom.vcmgt.repository.NguoiDungRepository;
import org.vaccom.vcmgt.service.NguoiDungService;

@Service
@Transactional
public class NguoiDungServiceImpl implements NguoiDungService {

	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	@Autowired
	private KhoaDangKyRepository khoaDangKyRepository;

	@Autowired
	private KhoaTruyCapRepository khoaTruyCapRepository;

	@Override
	public NguoiDung updateNguoiDung(NguoiDung nguoiDung) {
		return nguoiDungRepository.save(nguoiDung);
	}

	@Override
	@Transactional
	public NguoiDung addNguoiDung(NguoiDung nguoiDung, KhoaDangKy khoaDangKy) {
		nguoiDung = nguoiDungRepository.save(nguoiDung);
		khoaDangKy.setNguoiDungID(nguoiDung.getId());
		khoaDangKyRepository.save(khoaDangKy);
		return nguoiDung;
	}

	@Override
	@Transactional
	public void deleteNguoiDung(long nguoiDungId, long khoaDangKyId, long khoaTruyCapId) throws Exception {
		nguoiDungRepository.deleteById(nguoiDungId);

		if (khoaDangKyId > 0) {
			khoaDangKyRepository.deleteById(khoaDangKyId);
		}

		if (khoaTruyCapId > 0) {
			khoaTruyCapRepository.deleteById(khoaTruyCapId);
		}
	}

	@Override
	public long countAll() {
		return nguoiDungRepository.count();
	}

	@Override
	public void deleteNguoiDung(long id) throws Exception {
		nguoiDungRepository.deleteById(id);
	}

	@Override
	public NguoiDung findByID(long id) {
		return nguoiDungRepository.findById(id);
	}

	@Override
	public NguoiDung findByTenDanNhap(String tenDangNhap) {

		return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
	}

	@Override
	public List<NguoiDung> findAll(int page, int size) {

		if (page < 0 || size < 0) {
			page = 0;
			size = 30;
		}
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<NguoiDung> pases = nguoiDungRepository.findAll(pageable);
		return pases.getContent();
	}

	@Override
	public int countByDiaBanCoSoId(long id) {
		return nguoiDungRepository.countByDiaBanCoSoId(id);
	}

}
