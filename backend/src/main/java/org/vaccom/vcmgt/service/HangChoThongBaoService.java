package org.vaccom.vcmgt.service;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.HangChoThongBao;

import java.util.List;

@Service
public interface HangChoThongBaoService {

    public List<HangChoThongBao> findByIsSent(boolean isSent);
    public List<HangChoThongBao> findByStatus(long status);

    public void deleteById(long id);
    public HangChoThongBao updateHangChoThongBao(HangChoThongBao hangChoThongBao);
    public HangChoThongBao findByIsSentTypeThongBao(boolean isSent, String loaiThongBao);
    public List<HangChoThongBao> findByIsSentIsReady (boolean isSent, boolean isReady);
    public List<HangChoThongBao> findByIsSentIsReadyCreateDate (boolean isSent, boolean isReady, String createDate);
    public HangChoThongBao findByPhone_LoaiThongBao(String toTelNo, String loaiThongBao);

    public HangChoThongBao findByLoaiThongBao_mappingKey(long mappingKey, String loaiThongBao);
    public List<HangChoThongBao> findByStatus_LoaiThongBao(long status, String loaiThongBao, long uyBanNhanDanId);

}
