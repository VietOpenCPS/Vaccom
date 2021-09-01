package org.vaccom.vcmgt.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.UyBanNhanDanAction;
import org.vaccom.vcmgt.entity.UyBanNhanDan;
import org.vaccom.vcmgt.service.NguoiDungService;
import org.vaccom.vcmgt.service.UyBanNhanDanService;
@Service
public class UyBanNhanDanActionImpl implements UyBanNhanDanAction {

    @Autowired
    UyBanNhanDanService uyBanNhanDanService;
    @Override
    public UyBanNhanDan findById(long id) {
        return uyBanNhanDanService.findById(id);
    }
}
