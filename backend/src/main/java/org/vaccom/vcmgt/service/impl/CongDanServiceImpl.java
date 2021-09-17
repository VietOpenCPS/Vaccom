package org.vaccom.vcmgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaccom.vcmgt.entity.CongDan;
import org.vaccom.vcmgt.repository.CongDanRepository;
import org.vaccom.vcmgt.service.CongDanService;

public class CongDanServiceImpl implements CongDanService {

    @Autowired
    private CongDanRepository congDanRepository;

    @Override
    public CongDan findByCongDanId(long id) {
        return congDanRepository.findByCongDanId(id);
    }
}
