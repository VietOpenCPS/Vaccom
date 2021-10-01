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
	public long countByCoSoYTeId(long coSoYTeId) {
		return muiTiemChungRepository.countByCoSoYTeId(coSoYTeId);
	}
	
	@Override

	public long countByCongDan_ID(long id) {
		return muiTiemChungRepository.countBycongDanID(id);

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
	public MuiTiemChung create(MuiTiemChung muiTiemChung) {
		return muiTiemChungRepository.save(muiTiemChung);
	}

	@Override
	public List<MuiTiemChung> findMuiTiemChungDatDieuKien(String loaiThuocTiem, int soNgayTiem) {
		return muiTiemChungRepository.findMuiTiemChungDatDieuKien(loaiThuocTiem, soNgayTiem);
	}

	@Override
	public List<MuiTiemChung> findMuiTiemChungDatDieuKien(String loaiThuocTiem) {
		return muiTiemChungRepository.findMuiTiemChungDatDieuKien(loaiThuocTiem);
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
	public List<MuiTiemChung> findByCongDan_ID(long id) {
		return muiTiemChungRepository.findByCongDanID(id);
	}


	@Override
	public List<MuiTiemChung> findByCoSoYTeId(long coSoYTeId, int page, int size) {
		if (page < 0 || size < 0) {
			page = 0;
			size = 30;
		}
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<MuiTiemChung> pases = muiTiemChungRepository.findByCoSoYTeId(coSoYTeId, pageable);
		return pases.getContent();
	}

}
