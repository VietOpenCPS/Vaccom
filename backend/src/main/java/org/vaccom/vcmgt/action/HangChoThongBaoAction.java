package org.vaccom.vcmgt.action;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.HangChoThongBao;
import org.vaccom.vcmgt.entity.NguoiDung;

import java.util.List;

@Service
public interface HangChoThongBaoAction {
    public HangChoThongBao addHangChoThongBao(String jsonObject, NguoiDung nguoiDung, boolean isReady, String loaiThongBao, long uyBanNhanDanID) throws Exception;

    public HangChoThongBao addHangChoThongBao(String jsonObject, String SoDienThoai, String Email, boolean isReady, String loaiThongBao, long uyBanNhanDanID) throws Exception;

    public HangChoThongBao updateReadyForHangCho(String LoaiThongBao, boolean isSent, boolean isReady);

    public List<HangChoThongBao> findByIsSent(boolean isSent);

    public HangChoThongBao update(HangChoThongBao hangChoThongBao);

    public List<HangChoThongBao> findByIsSentIsReady(boolean isSent, boolean isReady);

    public HangChoThongBao findByPhone_LoaiThongBao(String toTelNo, String LoaiThongBao);

}
