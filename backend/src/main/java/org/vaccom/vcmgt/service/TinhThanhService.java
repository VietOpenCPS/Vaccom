package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.vaccom.vcmgt.entity.TinhThanh;

/**
 * @author vaccom
 *
 */
@Service
public interface TinhThanhService {
	public TinhThanh findByTinhThanhMa(String tinhThanhMa);

	public List<TinhThanh> findAll();
}
