package org.vaccom.vcmgt.action.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.GiayDiDuongAction;
import org.vaccom.vcmgt.controler.AuthenticationControler;
import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.entity.GiayDiDuong;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.GiayDiDuongService;
import org.vaccom.vcmgt.service.TransformService;
import org.vaccom.vcmgt.util.MessageUtil;

import org.vaccom.vcmgt.util.VaccomUtil;


import java.util.List;

@Service
public class GiayDiDuongActionImpl implements GiayDiDuongAction {
    private Log _log = LogFactory.getLog(GiayDiDuongActionImpl.class);

    @Autowired
    GiayDiDuongService giayDiDuongService;

    @Autowired
    TransformService transformService;

    @Override
    public GiayDiDuong create(GiayDiDuongDto giayDiDuongDto) throws Exception {

        if(giayDiDuongDto.hoVaTen == null || giayDiDuongDto.hoVaTen.isEmpty()) {
            return null;
        }

        String checkSum = transformService.base64(giayDiDuongDto);
        GiayDiDuong giayDiDuongExisted = giayDiDuongService.findByChecksum(checkSum);

        if(giayDiDuongExisted != null) {
            return null;
        }

        GiayDiDuong giayDiDuong = new GiayDiDuong();
        transformService.paperTraffic(giayDiDuong, giayDiDuongDto, true);
        return giayDiDuongService.save(giayDiDuong);
    }

    @Override
    public GiayDiDuong update(GiayDiDuong giayDiDuong, GiayDiDuongDto giayDiDuongDto) throws Exception {
        transformService.paperTraffic(giayDiDuong, giayDiDuongDto, false);
        return giayDiDuongService.save(giayDiDuong);
    }

    @Override
    public GiayDiDuong findById(long id) {
        return giayDiDuongService.findById(id);
    }

    @Override
    public GiayDiDuong findByCheckSum(String checksum) {
        return giayDiDuongService.findByChecksum(checksum);
    }

    @Override
    public GiayDiDuong findByMaQr(String maQr) {
        return giayDiDuongService.findByMaQR(maQr);
    }

    @Override
    public List<GiayDiDuong> findByUyBanNhanDanID(int uyBanNhanDanId, int page, int size) {
        return giayDiDuongService.findByUyBanNhanDanID(uyBanNhanDanId, page, size);
    }

    @Override
    public long countByUyBanNhanDanID(int id) {
        return giayDiDuongService.countByUyBanNhanDanID(id);
    }

    @Override
    public List<GiayDiDuong> findByUyBanNhanDanIdAndStatus(int uyBanNhanDanId, int status, int page, int size) {
        return giayDiDuongService.findByUyBanNhanDanIdAndStatus(uyBanNhanDanId, status, page, size);
    }

    @Override
    public long countByUyBanNhanDanIdAndStatus(int id, int status) {
        return giayDiDuongService.countByUyBanNhanDanIdAndStatus(id, status);
    }

    @Override
    public long countAll() {
        return 0;
    }

    @Override
    public void delete(GiayDiDuong giayDiDuong) {
        giayDiDuongService.delete(giayDiDuong);
    }
}
