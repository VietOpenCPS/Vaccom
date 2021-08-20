package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.QuocGia;

/**
 * @author vaccom
 *
 */
@Service
public interface QuocGiaService {
	public QuocGia findByQuocGiaMa(String quocGiaMa);

	public List<QuocGia> findAll();
}
