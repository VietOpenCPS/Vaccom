package org.vaccom.vcmgt.service;

import org.vaccom.vcmgt.entity.GiayDiDuong;

import java.util.List;

public interface GiayDiDuongService {
    //Read
    public GiayDiDuong findById(long id);

    public GiayDiDuong findByChecksum(String checksum);

    public GiayDiDuong findByMaQR(String MaQr);

    public List<GiayDiDuong> findAll(int page, int size);

    public List<GiayDiDuong> findByUyBanNhanDanID(int uyBanNhanDanId, int page, int size);

    public long countByUyBanNhanDanID(int id);

    public List<GiayDiDuong> findByUyBanNhanDanIdAndStatus(int uyBanNhanDanId, int status, int page, int size);

    public long countByUyBanNhanDanIdAndStatus(int id, int status);

    public long countAll();

    //Update
    public GiayDiDuong save(GiayDiDuong giayDiDuong);

    //Delete
    public void delete(GiayDiDuong giayDiDuong);

}
