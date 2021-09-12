package org.vaccom.vcmgt.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.HangChoThongBaoAction;
import org.vaccom.vcmgt.constant.ZaloConstant;
import org.vaccom.vcmgt.entity.HangChoThongBao;
import org.vaccom.vcmgt.entity.NguoiDung;
import org.vaccom.vcmgt.service.HangChoThongBaoService;
import org.vaccom.vcmgt.util.ZaloNotificationUtil;

import java.util.List;


@Service
public class HangChoThongBaoActionImpl implements HangChoThongBaoAction {
    @Autowired
    private HangChoThongBaoService hangChoThongBaoService;

    @Override
    public HangChoThongBao addHangChoThongBao(String jsonObject, NguoiDung nguoiDung, boolean isReady, String loaiThongBao, long uyBanNhanDanID, long mappingKey) throws Exception {
        HangChoThongBao hangChoThongBao = new HangChoThongBao();
        hangChoThongBao.setLoaiThongBao(loaiThongBao);
        hangChoThongBao.setSent(false);
        hangChoThongBao.setPayload(jsonObject);
        hangChoThongBao.setToEmail(nguoiDung.getEmail());
        hangChoThongBao.setToTelNo(ZaloNotificationUtil.convertPhoneNumber(nguoiDung.getSoDienThoai()));
        hangChoThongBao.setReady(isReady);
        hangChoThongBao.setUyBanNhanDanId(uyBanNhanDanID);
        hangChoThongBao.setStatus(ZaloConstant.CHUA_GUI);
        hangChoThongBao.setMappingKey(mappingKey);

        return hangChoThongBaoService.updateHangChoThongBao(hangChoThongBao);
    }

    @Override
    public HangChoThongBao addHangChoThongBao(String jsonObject, String SoDienThoai, String Email, boolean isReady, String loaiThongBao, long uyBanNhanDanID, long mappingKey) throws Exception {

        HangChoThongBao hangChoThongBao = new HangChoThongBao();
        hangChoThongBao.setLoaiThongBao(loaiThongBao);
        hangChoThongBao.setSent(false);
        hangChoThongBao.setPayload(jsonObject);
        hangChoThongBao.setToEmail(Email);
        hangChoThongBao.setToTelNo(ZaloNotificationUtil.convertPhoneNumber(SoDienThoai));
        hangChoThongBao.setReady(isReady);
        hangChoThongBao.setUyBanNhanDanId(uyBanNhanDanID);
        hangChoThongBao.setStatus(ZaloConstant.CHUA_GUI);
        hangChoThongBao.setMappingKey(mappingKey);

        return hangChoThongBaoService.updateHangChoThongBao(hangChoThongBao);
    }

    @Override
    public HangChoThongBao updateReadyForHangCho(String LoaiThongBao, boolean isSent, boolean isReady) {
        HangChoThongBao hangChoThongBao = hangChoThongBaoService.findByIsSentTypeThongBao(isSent, LoaiThongBao);
        hangChoThongBao.setReady(isReady);
        return hangChoThongBaoService.updateHangChoThongBao(hangChoThongBao);
    }

    @Override
    public List<HangChoThongBao> findByIsSent(boolean isSent) {
        return hangChoThongBaoService.findByIsSent(isSent);
    }

    @Override
    public List<HangChoThongBao> findByStatus(long status) {
        return hangChoThongBaoService.findByStatus(status);
    }

    @Override
    public HangChoThongBao update(HangChoThongBao hangChoThongBao) {
        return hangChoThongBaoService.updateHangChoThongBao(hangChoThongBao);
    }

    @Override
    public List<HangChoThongBao> findByIsSentIsReady(boolean isSent, boolean isReady) {
        return hangChoThongBaoService.findByIsSentIsReady(isSent, isReady);
    }

    @Override
    public HangChoThongBao findByPhone_LoaiThongBao(String toTelNo, String LoaiThongBao) {
        return hangChoThongBaoService.findByPhone_LoaiThongBao(toTelNo, LoaiThongBao);
    }

    @Override
    public HangChoThongBao findByLoaiThongBao_mappingKey(long mappingKey, String loaiThongBao) {
        return hangChoThongBaoService.findByLoaiThongBao_mappingKey(mappingKey, loaiThongBao);
    }


}
