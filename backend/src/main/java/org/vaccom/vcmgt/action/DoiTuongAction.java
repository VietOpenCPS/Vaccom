package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.DoiTuong;

@Service
public interface DoiTuongAction {

	public void addDoiTuong(String doiTuongMa, String doiTuongMoTa) throws Exception;

	public List<DoiTuong> findAll();

}
