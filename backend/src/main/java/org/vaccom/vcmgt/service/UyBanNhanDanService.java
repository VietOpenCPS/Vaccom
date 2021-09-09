package org.vaccom.vcmgt.service;

import org.vaccom.vcmgt.entity.GiayDiDuong;
import org.vaccom.vcmgt.entity.UyBanNhanDan;

import java.util.List;

public interface UyBanNhanDanService {
    public UyBanNhanDan findById(long id);

    public List<UyBanNhanDan> findAll(int page, int size);

    public long countAll();

    //Update
    public UyBanNhanDan save(UyBanNhanDan uyBanNhanDan);

    //Delete
    public void delete(UyBanNhanDan uyBanNhanDan);

    public List<UyBanNhanDan> findByNotificationConfigExceptNull();
}
