package org.vaccom.vcmgt.action;

import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.entity.UyBanNhanDan;
@Service
public interface UyBanNhanDanAction {
    public UyBanNhanDan findById (long id);
}
