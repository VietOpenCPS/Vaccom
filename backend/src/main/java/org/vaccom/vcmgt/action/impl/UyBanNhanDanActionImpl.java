package org.vaccom.vcmgt.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.action.UyBanNhanDanAction;
import org.vaccom.vcmgt.dto.UyBanNhanDanDto;
import org.vaccom.vcmgt.entity.UyBanNhanDan;
import org.vaccom.vcmgt.service.UyBanNhanDanService;

import java.util.List;

@Service
public class UyBanNhanDanActionImpl implements UyBanNhanDanAction {

    @Autowired
    UyBanNhanDanService uyBanNhanDanService;

    @Override
    public UyBanNhanDan findById(long id) {
        return uyBanNhanDanService.findById(id);
    }

    @Override
    public List<UyBanNhanDan> findAll(int page, int size) {
        return uyBanNhanDanService.findAll(page, size);
    }

    @Override
    public long countAll() {
        return uyBanNhanDanService.countAll();
    }

    @Override
    public UyBanNhanDan create(UyBanNhanDanDto uyBanNhanDanDto) throws Exception {
        UyBanNhanDan uyBanNhanDan = new UyBanNhanDan(uyBanNhanDanDto);
        return uyBanNhanDanService.save(uyBanNhanDan);
    }

    @Override
    public UyBanNhanDan update(UyBanNhanDan uyBanNhanDan, UyBanNhanDanDto uyBanNhanDanDto) throws Exception {
        if(uyBanNhanDanDto.tenCoQuan != null && !uyBanNhanDanDto.tenCoQuan.isEmpty()) {
            uyBanNhanDan.setTenCoQuan(uyBanNhanDanDto.tenCoQuan);
        }

        if(uyBanNhanDanDto.tinhThanhMa != null && !uyBanNhanDanDto.tinhThanhMa.isEmpty()) {
            uyBanNhanDan.setTinhThanhMa(uyBanNhanDanDto.tinhThanhMa);
        }

        if(uyBanNhanDanDto.tinhThanhTen != null && !uyBanNhanDanDto.tinhThanhTen.isEmpty()) {
            uyBanNhanDan.setTinhThanhTen(uyBanNhanDanDto.tinhThanhTen);
        }

        if(uyBanNhanDanDto.quanHuyenMa != null && !uyBanNhanDanDto.quanHuyenMa.isEmpty()) {
            uyBanNhanDan.setQuanHuyenMa(uyBanNhanDanDto.quanHuyenMa);
        }

        if(uyBanNhanDanDto.quanHuyenTen != null && !uyBanNhanDanDto.quanHuyenTen.isEmpty()) {
            uyBanNhanDan.setQuanHuyenTen(uyBanNhanDanDto.quanHuyenTen);
        }

        if(uyBanNhanDanDto.phuongXaMa != null && !uyBanNhanDanDto.phuongXaMa.isEmpty()) {
            uyBanNhanDan.setPhuongXaMa(uyBanNhanDanDto.phuongXaMa);
        }

        if(uyBanNhanDanDto.phuongXaTen != null && !uyBanNhanDanDto.phuongXaTen.isEmpty()) {
            uyBanNhanDan.setPhuongXaTen(uyBanNhanDanDto.phuongXaTen);
        }

        if(uyBanNhanDanDto.diaChiHoatDong != null && !uyBanNhanDanDto.diaChiHoatDong.isEmpty()) {
            uyBanNhanDan.setDiaChiHoatDong(uyBanNhanDanDto.diaChiHoatDong);
        }

        if(uyBanNhanDanDto.nguoiDaiDien != null && !uyBanNhanDanDto.nguoiDaiDien.isEmpty()) {
            uyBanNhanDan.setNguoiDaiDien(uyBanNhanDanDto.nguoiDaiDien);
        }

        if(uyBanNhanDanDto.soDienThoai != null && !uyBanNhanDanDto.soDienThoai.isEmpty()) {
            uyBanNhanDan.setSoDienThoai(uyBanNhanDanDto.soDienThoai);
        }

        return uyBanNhanDanService.save(uyBanNhanDan);
    }

    @Override
    public void delete(UyBanNhanDan uyBanNhanDan) {
        uyBanNhanDanService.delete(uyBanNhanDan);
    }
}
