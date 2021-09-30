package org.vaccom.vcmgt.service;

import org.vaccom.vcmgt.dto.CongDanDto;
import org.vaccom.vcmgt.entity.CongDan;

public interface CongDanService {
    public CongDan findByCongDanId(long id);

    public CongDan addCongDan(CongDanDto congDanDto);

    public CongDan save(CongDan congDan);

    public CongDan findBySdtOrCmt(String sdt, String cmt);

    public int countBySoDienThoaiAndCmtcccd(String sdt, String cmt);

    public CongDan update(CongDan congDan);

    public CongDan findByHoVaTenAndSoDienThoai(String hoVaTen, String sdt);
}
