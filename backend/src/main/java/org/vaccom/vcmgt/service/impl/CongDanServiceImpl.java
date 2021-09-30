package org.vaccom.vcmgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.dto.CongDanDto;
import org.vaccom.vcmgt.entity.CongDan;
import org.vaccom.vcmgt.repository.CongDanRepository;
import org.vaccom.vcmgt.service.CongDanService;

@Service
public class CongDanServiceImpl implements CongDanService {

    @Autowired
    private CongDanRepository congDanRepository;

    @Override
    public CongDan findByCongDanId(long id) {
        return congDanRepository.findById(id);
    }

    @Override
    public CongDan addCongDan(CongDanDto congDanDto) {
        CongDan congdanNew = new CongDan(congDanDto);
        return congDanRepository.save(congdanNew);
    }

    @Override
    public CongDan save(CongDan congDan) {
        return congDanRepository.save(congDan);
    }

    @Override
    public CongDan findBySdtOrCmt(String sdt, String cmt) {
        return congDanRepository.findBySoDienThoaiOrCmtcccd(sdt, cmt);
    }

    @Override
    public int countBySoDienThoaiAndCmtcccd(String sdt, String cmt) {
        return congDanRepository.countBySoDienThoaiAndCmtcccd(sdt, cmt);
    }

    @Override
    public CongDan update(CongDan congDan) {
        return congDanRepository.save(congDan);
    }

    @Override
    public CongDan findByHoVaTenAndSoDienThoai(String hoVaTen, String sdt) {
        return congDanRepository.findByHoVaTenAndSoDienThoai(hoVaTen, sdt);
    }
}
