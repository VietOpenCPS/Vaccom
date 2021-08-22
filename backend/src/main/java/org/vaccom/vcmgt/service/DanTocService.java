package org.vaccom.vcmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.DanToc;

/**
 * @author vaccom
 *
 */
@Service
public interface DanTocService {
	
	public DanToc findByDanTocMa(String danTocMa);

	public List<DanToc> findAll();
	
	public DanToc updateDanToc(DanToc danToc);
}
