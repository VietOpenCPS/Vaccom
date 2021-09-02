package org.vaccom.vcmgt.service;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.UyBanNhanDan;
@Service
public interface UyBanNhanDanService {
    public UyBanNhanDan findById (long id);
}
