package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.PhieuHenTiem;
import org.vaccom.vcmgt.repository.PhieuHenTiemRepository;
import org.vaccom.vcmgt.service.PhieuHenTiemService;



/**
 * @author vaccom
 *
 */
@Service
public class PhieuHenTiemServiceImpl implements PhieuHenTiemService {

	@Autowired
	private PhieuHenTiemRepository phieuHenTiemRepository;
	
	@Override
	public long countByLichTiemChungId(long id) {
		return phieuHenTiemRepository.countByLichTiemChungId(id);
	}


	@Override
	public PhieuHenTiem findById(long id) {

		return phieuHenTiemRepository.findById(id);
	}
	
	@Override
	public PhieuHenTiem findByMaQR(String maQR) {
		return phieuHenTiemRepository.findByMaQR(maQR);
	}

	@Override
	public List<PhieuHenTiem> findAll() {

		return phieuHenTiemRepository.findAll();
	}

	@Override
	public PhieuHenTiem updatePhieuHenTiem(PhieuHenTiem phieuHenTiem) {

		return phieuHenTiemRepository.save(phieuHenTiem);
	}

	@Override
	public void deleteById(long id) {
		phieuHenTiemRepository.deleteById(id);
	}

	@Override
	public List<PhieuHenTiem> findByLichTiemChungId(long id, int page, int size) {
		if (page < 0 || size < 0) {
			page = 0;
			size = 30;
		}
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<PhieuHenTiem> pases = phieuHenTiemRepository.findByLichTiemChungId(id, pageable);
		return pases.getContent();
	}

	@Override
	public List<PhieuHenTiem> findByNguoiTiemChungId(long id) {
		return phieuHenTiemRepository.findByNguoiTiemChungId(id);
	}
}
