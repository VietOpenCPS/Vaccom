package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.QuocGiaAction;
import org.vaccom.vcmgt.entity.QuocGia;
import org.vaccom.vcmgt.service.QuocGiaService;

@Service
public class QuocGiaActionImpl implements QuocGiaAction {

	@Autowired
	private QuocGiaService quocGiaService;

	@Override
	public QuocGia addQuocGia(String quocGiaMa, String quocGiaTen) throws Exception {

		QuocGia quocGia = quocGiaService.findByQuocGiaMa(quocGiaMa);

		if (quocGia == null) {
			quocGia = new QuocGia();
			quocGia.setQuocGiaMa(quocGiaMa);
			quocGia.setQuocGiaTen(quocGiaTen);
			quocGia = quocGiaService.updateQuocGia(quocGia);
		}

		return quocGia;
	}

	@Override
	public List<QuocGia> finAll() {
		return quocGiaService.findAll();
	}

}
