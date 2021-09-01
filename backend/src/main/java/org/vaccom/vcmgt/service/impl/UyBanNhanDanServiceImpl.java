package org.vaccom.vcmgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.UyBanNhanDan;
import org.vaccom.vcmgt.repository.NguoiDungRepository;
import org.vaccom.vcmgt.repository.UyBanNhanDanRepository;
import org.vaccom.vcmgt.service.UyBanNhanDanService;
@Service
public class UyBanNhanDanServiceImpl implements UyBanNhanDanService {
    @Autowired
    private UyBanNhanDanRepository uyBanNhanDanRepository;

    @Override
    public UyBanNhanDan findById(long id) {
        return uyBanNhanDanRepository.findById(id);
    }
}
