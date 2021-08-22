package org.vaccom.vcmgt.action.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.DoiTuongAction;
import org.vaccom.vcmgt.entity.DoiTuong;
import org.vaccom.vcmgt.service.DoiTuongService;

@Service
public class DoiTuongActionImpl implements DoiTuongAction{

	@Autowired
	private DoiTuongService doiTuongService;
	
	@Override
	public void addDoiTuong(String doiTuongMa, String doiTuongMoTa) throws Exception {
		DoiTuong doiTuong = new DoiTuong();
		doiTuong.setDoiTuongMa(doiTuongMa);
		doiTuong.setDoiTuongMoTa(doiTuongMoTa);
		doiTuongService.updateDoiTuong(doiTuong);
	}

	@Override
	public List<DoiTuong> findAll() {
		return doiTuongService.findAll();
	}
}
