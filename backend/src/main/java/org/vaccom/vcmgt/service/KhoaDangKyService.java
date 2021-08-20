package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.KhoaDangKy;


@Service
public interface KhoaDangKyService {
	
	public KhoaDangKy findByNguoiDungID(long nguoiDungID);

}
