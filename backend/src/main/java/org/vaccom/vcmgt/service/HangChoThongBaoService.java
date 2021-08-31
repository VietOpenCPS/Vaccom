package org.vaccom.vcmgt.service;

import org.vaccom.vcmgt.entity.HangChoThongBao;
import org.vaccom.vcmgt.entity.MauThongBao;

import java.util.List;

public interface HangChoThongBaoService {

    public List<HangChoThongBao> findByIsSent(boolean isSent);

    public void deleteById(long id);
    public HangChoThongBao updateHangChoThongBao(HangChoThongBao hangChoThongBao);
    public HangChoThongBao findByIsSentTypeThongBao(boolean isSent, String loaiThongBao);
    public List<HangChoThongBao> findByIsSentIsReady (boolean isSent, boolean isReady);
}
