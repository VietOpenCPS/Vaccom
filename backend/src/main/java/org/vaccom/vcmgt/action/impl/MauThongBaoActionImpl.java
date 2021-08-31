package org.vaccom.vcmgt.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.MauThongBaoAction;
import org.vaccom.vcmgt.entity.MauThongBao;
import org.vaccom.vcmgt.service.MauThongBaoService;
@Service
public class MauThongBaoActionImpl implements MauThongBaoAction {

    @Autowired
    private MauThongBaoService mauThongBaoService;

    @Override
    public MauThongBao findByLoaiThongBao(String loaiThongBao) {
        return mauThongBaoService.findByLoaiThongBao(loaiThongBao);
    }
}
