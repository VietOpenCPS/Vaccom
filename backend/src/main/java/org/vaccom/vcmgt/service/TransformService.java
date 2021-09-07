package org.vaccom.vcmgt.service;

import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.entity.GiayDiDuong;

public interface TransformService {
    public void paperTraffic(GiayDiDuong giayDiDuong, GiayDiDuongDto giayDiDuongDto, boolean isCreate) throws Exception;
    public String base64(GiayDiDuongDto giayDiDuongDto) throws Exception;
    public String calenderWorking(GiayDiDuongDto giayDiDuongDto) throws Exception;
}
