package org.vaccom.vcmgt.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.QuanHuyen;

/**
 * @author vaccom
 *
 */
@Service
public interface QuanHuyenService{
	public QuanHuyen findByQuanHuyenMa(String quanHuyenMa);

	public List<QuanHuyen> findByTinhThanhID(long tinhThanhID);
	
	public QuanHuyen updateQuanHuyen(QuanHuyen quanHuyen);
}
