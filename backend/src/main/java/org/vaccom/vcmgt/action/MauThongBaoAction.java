package org.vaccom.vcmgt.action;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.MauThongBao;

@Service
public interface MauThongBaoAction {
    public MauThongBao findByLoaiThongBao(String loaiThongBao);
}
