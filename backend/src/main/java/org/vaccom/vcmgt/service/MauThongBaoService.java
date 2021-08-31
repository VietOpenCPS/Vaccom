package org.vaccom.vcmgt.service;

import org.vaccom.vcmgt.entity.MauThongBao;

public interface MauThongBaoService {
    public void deleteById(long id);
    public MauThongBao findById(long id);
    public MauThongBao findByLoaiThongBao(String LoaiThongBao);

}
