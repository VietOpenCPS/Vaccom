package org.vaccom.vcmgt.action;

import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.dto.NguoiTiemChungDto;
import org.vaccom.vcmgt.dto.ResultSearchDto;
import org.vaccom.vcmgt.entity.GiayDiDuong;
import org.vaccom.vcmgt.entity.NguoiTiemChung;

import java.util.List;

public interface GiayDiDuongAction {
    public GiayDiDuong create(GiayDiDuongDto giayDiDuongDto) throws Exception;

    public GiayDiDuong update(GiayDiDuong giayDiDuong, GiayDiDuongDto giayDiDuongDto) throws Exception;

    public GiayDiDuong updateStatus(GiayDiDuong giayDiDuong, int statusNew) throws Exception;

    public GiayDiDuong findById(long id);

    public GiayDiDuong findByCheckSum(String checksum);

    public GiayDiDuong findByMaQr(String maQr);

    public List<GiayDiDuong> findByUyBanNhanDanID(int uyBanNhanDanId, int page, int size);

    public long countByUyBanNhanDanID(int id);

    public List<GiayDiDuong> findByUyBanNhanDanIdAndStatus(int uyBanNhanDanId, int status, int page, int size);

    public long countByUyBanNhanDanIdAndStatus(int id, int status);

    public long countAll();

    public ResultSearchDto<GiayDiDuong> search(GiayDiDuongDto giayDiDuongDto);

    public void delete(GiayDiDuong giayDiDuong);
}
