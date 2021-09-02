package org.vaccom.vcmgt.action;

import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.dto.UyBanNhanDanDto;
import org.vaccom.vcmgt.entity.GiayDiDuong;
import org.vaccom.vcmgt.entity.UyBanNhanDan;

import java.util.List;

public interface UyBanNhanDanAction {
    public UyBanNhanDan findById(long id);

    public List<UyBanNhanDan> findAll(int page, int size);

    public long countAll();

    public UyBanNhanDan create(UyBanNhanDanDto uyBanNhanDanDto) throws Exception;

    public UyBanNhanDan update(UyBanNhanDan uyBanNhanDan, UyBanNhanDanDto uyBanNhanDanDto) throws Exception;

    public void delete(UyBanNhanDan uyBanNhanDan);
}
