package org.vaccom.vcmgt.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.MauThongBao;
import org.vaccom.vcmgt.repository.MauThongBaoRepository;
import org.vaccom.vcmgt.service.MauThongBaoService;


@Service
public class MauThongBaoServiceImpl implements MauThongBaoService {

    @Autowired
    private MauThongBaoRepository mauThongBaoRepository;

    @Override
    public void deleteById(long id) {
        mauThongBaoRepository.deleteById(id);
    }

    @Override
    public MauThongBao findById(long id) {
        return mauThongBaoRepository.findById(id);
    }

    @Override
    public MauThongBao findByLoaiThongBao(String LoaiThongBao) {
        return mauThongBaoRepository.findByLoaiThongBao(LoaiThongBao);
    }

}
