package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.QuocGia;

@Service
public interface QuocGiaAction {

	public QuocGia addQuocGia(String quocGiaMa, String quocGiaTen) throws Exception;

	public List<QuocGia> finAll();
}
