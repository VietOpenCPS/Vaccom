package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.DanTocAction;
import org.vaccom.vcmgt.entity.DanToc;
import org.vaccom.vcmgt.service.DanTocService;

@Service
public class DanTocActionImpl implements DanTocAction {

	@Autowired
	private DanTocService danTocService;

	@Override
	public DanToc addDanToc(String danTocMa, String danTocTen) throws Exception {

		DanToc danToc = danTocService.findByDanTocMa(danTocMa);

		if (danToc == null) {
			danToc = new DanToc();
			danToc.setDanTocMa(danTocMa);
			danToc.setDanTocTen(danTocTen);
			danToc = danTocService.updateDanToc(danToc);
		}

		return danToc;
	}

	@Override
	public List<DanToc> findAll() {
		return danTocService.findAll();
	}

}
