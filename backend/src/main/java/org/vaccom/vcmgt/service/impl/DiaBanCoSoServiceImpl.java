package org.vaccom.vcmgt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.DiaBanCoSo;
import org.vaccom.vcmgt.repository.DiaBanCoSoRepository;
import org.vaccom.vcmgt.service.DiaBanCoSoService;

/**
 * @author vaccom
 *
 */
@Service
public class DiaBanCoSoServiceImpl implements DiaBanCoSoService {

	@Autowired
	private DiaBanCoSoRepository diaBanCoSoRepository;

	@Override
	public DiaBanCoSo findById(long id) {

		return diaBanCoSoRepository.findById(id);
	}

	@Override
	public List<DiaBanCoSo> findAll() {

		return diaBanCoSoRepository.findAll();
	}

	@Override
	public DiaBanCoSo updateDiaBanCoSo(DiaBanCoSo diaBanCoSo) {

		return diaBanCoSoRepository.save(diaBanCoSo);
	}

	@Override
	public void deleteById(long id) {
		diaBanCoSoRepository.deleteById(id);
	}

	@Override
	public int countByCoSoYTeId(long id) {
		return diaBanCoSoRepository.countByCoSoYTeId(id);
	}

	@Override
	public List<DiaBanCoSo> findByCoSoYTeId(long id) {
		return diaBanCoSoRepository.findByCoSoYTeId(id);
	}

}
