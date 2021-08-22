package org.vaccom.vcmgt.action;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.DanToc;

@Service
public interface DanTocAction {

	public DanToc addDanToc(String danTocMa, String danTocTen) throws Exception;

	public List<DanToc> findAll();
}
