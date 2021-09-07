package org.vaccom.vcmgt.action;

import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.entity.GiayDiDuong;

import java.util.List;

public interface GiayDiDuongAction {
    public GiayDiDuong create(GiayDiDuongDto giayDiDuongDto) throws Exception;

    public GiayDiDuong update(GiayDiDuong giayDiDuong, GiayDiDuongDto giayDiDuongDto) throws Exception;

    public GiayDiDuong findById(long id);

    public GiayDiDuong findByCheckSum(String checksum);

    public GiayDiDuong findByMaQr(String maQr);

    public List<GiayDiDuong> findByUyBanNhanDanID(int uyBanNhanDanId, int page, int size);

    public long countByUyBanNhanDanID(int id);

    public List<GiayDiDuong> findByUyBanNhanDanIdAndStatus(int uyBanNhanDanId, int status, int page, int size);

    public long countByUyBanNhanDanIdAndStatus(int id, int status);

    public long countAll();

    public void delete(GiayDiDuong giayDiDuong);
}
