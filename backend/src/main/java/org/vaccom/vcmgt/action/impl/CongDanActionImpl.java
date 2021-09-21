package org.vaccom.vcmgt.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.CongDanAction;
import org.vaccom.vcmgt.entity.CongDan;
import org.vaccom.vcmgt.service.CongDanService;
@Service
public class CongDanActionImpl implements CongDanAction {

    @Autowired
    private CongDanService congDanService;

    @Override
    public CongDan findByCongDanId(long id) {
        return congDanService.findByCongDanId(id);
    }

    @Override
    public CongDan update(CongDan congDan) {
        return congDanService.update(congDan);
    }
}
