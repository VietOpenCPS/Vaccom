package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.PhuongXa;


/**
 * @author vaccom
 *
 */
@Service
public interface PhuongXaService {
	public PhuongXa findByPhuongXaMa(String phuongXaMa);

	public List<PhuongXa> findByQuanHuyenID(long quanHuyenID);
	
	public PhuongXa updatePhuongXa(PhuongXa phuongXa);
}
