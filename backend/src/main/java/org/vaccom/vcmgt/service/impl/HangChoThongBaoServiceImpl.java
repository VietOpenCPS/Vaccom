package org.vaccom.vcmgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.HangChoThongBao;
import org.vaccom.vcmgt.repository.HangChoThongBaoRepository;
import org.vaccom.vcmgt.repository.KhoaDangKyRepository;
import org.vaccom.vcmgt.repository.KhoaTruyCapRepository;
import org.vaccom.vcmgt.service.HangChoThongBaoService;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class HangChoThongBaoServiceImpl implements HangChoThongBaoService {
    @Autowired
    private EntityManager em;

    @Autowired
    private HangChoThongBaoRepository hangChoThongBaoRepository;

    @Autowired
    private KhoaDangKyRepository khoaDangKyRepository;

    @Autowired
    private KhoaTruyCapRepository khoaTruyCapRepository;

    @Override
    public List<HangChoThongBao> findByIsSent(boolean isSent) {
        return hangChoThongBaoRepository.findByIsSent(isSent);
    }

    @Override
    public List<HangChoThongBao> findByStatus(long status) {
        return hangChoThongBaoRepository.findByStatus(status);
    }

    @Override
    public void deleteById(long id) {
        hangChoThongBaoRepository.deleteById(id);
    }

    @Override
    public HangChoThongBao updateHangChoThongBao(HangChoThongBao hangChoThongBao) {
        return hangChoThongBaoRepository.save(hangChoThongBao);
    }

    @Override
    public HangChoThongBao findByIsSentTypeThongBao(boolean isSent, String loaiThongBao) {
        return hangChoThongBaoRepository.findByIsSentTypeThongBao(isSent, loaiThongBao);
    }

    @Override
    public List<HangChoThongBao> findByIsSentIsReady(boolean isSent, boolean isReady) {
        return hangChoThongBaoRepository.findByIsSentIsReady(isSent, isReady);
    }

    @Override
    public List<HangChoThongBao> findByIsSentIsReadyCreateDate(boolean isSent, boolean isReady, String createDate) {
        return hangChoThongBaoRepository.findByIsSentIsReadyCreateDate(isSent, isReady, createDate);
    }

    @Override
    public HangChoThongBao findByPhone_LoaiThongBao(String toTelNo, String loaiThongBao) {
        return hangChoThongBaoRepository.findByPhone_LoaiThongBao(toTelNo, loaiThongBao);
    }

    @Override
    public HangChoThongBao findByLoaiThongBao_mappingKey(long mappingKey, String loaiThongBao) {
        return hangChoThongBaoRepository.findByLoaiThongBao_mappingKey(mappingKey, loaiThongBao);
    }

    @Override
    public List<HangChoThongBao> findByStatus_LoaiThongBao(long status, String loaiThongBao, long uyBanNhanDanId) {
        return hangChoThongBaoRepository.findByStatus_LoaiThongBao(status, loaiThongBao, uyBanNhanDanId);
    }


}
