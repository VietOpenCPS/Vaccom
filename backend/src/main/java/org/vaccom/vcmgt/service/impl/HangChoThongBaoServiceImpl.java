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
    public HangChoThongBao findByPhone_LoaiThongBao(String toTelNo, String loaiThongBao) {
        return hangChoThongBaoRepository.findByPhone_LoaiThongBao(toTelNo, loaiThongBao);
    }


}
