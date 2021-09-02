package org.vaccom.vcmgt.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vaccom.vcmgt.constant.StatusConstant;
import org.vaccom.vcmgt.dto.GiayDiDuongDto;
import org.vaccom.vcmgt.dto.LichLamViecDto;
import org.vaccom.vcmgt.entity.GiayDiDuong;
import org.vaccom.vcmgt.exception.ActionException;
import org.vaccom.vcmgt.service.TransformService;
import org.vaccom.vcmgt.util.MessageUtil;
import org.vaccom.vcmgt.util.VaccomUtil;

import java.util.Base64;

@Service
public class TransformServiceImpl implements TransformService {
    private ObjectMapper obj = new ObjectMapper();

    @Override
    public void paperTraffic(GiayDiDuong giayDiDuong, GiayDiDuongDto giayDiDuongDto, boolean isCreate) throws Exception{

        if(giayDiDuongDto.hoVaTen != null && !giayDiDuongDto.hoVaTen.isEmpty()) {
            giayDiDuong.setHoVaTen(giayDiDuongDto.hoVaTen);
        }

        if(giayDiDuongDto.cmtcccd != null && !giayDiDuongDto.cmtcccd.isEmpty()) {
            giayDiDuong.setCmtcccd(giayDiDuongDto.cmtcccd);
        }

        if(giayDiDuongDto.soDienThoai != null && !giayDiDuongDto.soDienThoai.isEmpty()) {
            giayDiDuong.setSoDienThoai(giayDiDuongDto.soDienThoai);
        }

        if(giayDiDuongDto.email != null && !giayDiDuongDto.email.isEmpty()) {
            giayDiDuong.setEmail(giayDiDuongDto.email);
        }

        if(giayDiDuongDto.noiCtTenCoQuan != null && !giayDiDuongDto.noiCtTenCoQuan.isEmpty()) {
            giayDiDuong.setNoiCtTenCoQuan(giayDiDuongDto.noiCtTenCoQuan);
        }

        if(giayDiDuongDto.noiCtDiaChi != null && !giayDiDuongDto.noiCtDiaChi.isEmpty()) {
            giayDiDuong.setNoiCtDiaChi(giayDiDuongDto.noiCtDiaChi);
        }

        if(giayDiDuongDto.noiCtTinhThanhMa != null && !giayDiDuongDto.noiCtTinhThanhMa.isEmpty()) {
            giayDiDuong.setNoiCtTinhThanhMa(giayDiDuongDto.noiCtTinhThanhMa);
        }

        if(giayDiDuongDto.noiCtTinhThanhTen != null && !giayDiDuongDto.noiCtTinhThanhTen.isEmpty()) {
            giayDiDuong.setNoiCtTinhThanhTen(giayDiDuongDto.noiCtTinhThanhTen);
        }

        if(giayDiDuongDto.noiCtQuanHuyenMa != null && !giayDiDuongDto.noiCtQuanHuyenMa.isEmpty()) {
            giayDiDuong.setNoiCtQuanHuyenMa(giayDiDuongDto.noiCtQuanHuyenMa);
        }

        if(giayDiDuongDto.noiCtQuanHuyenTen != null && !giayDiDuongDto.noiCtQuanHuyenTen.isEmpty()) {
            giayDiDuong.setNoiCtQuanHuyenTen(giayDiDuongDto.noiCtQuanHuyenTen);
        }

        if(giayDiDuongDto.noiCtPhuongXaMa != null && !giayDiDuongDto.noiCtPhuongXaMa.isEmpty()) {
            giayDiDuong.setNoiCtPhuongXaMa(giayDiDuongDto.noiCtPhuongXaMa);
        }

        if(giayDiDuongDto.noiCtPhuongXaTen != null && !giayDiDuongDto.noiCtPhuongXaTen.isEmpty()) {
            giayDiDuong.setNoiCtPhuongXaTen(giayDiDuongDto.noiCtPhuongXaTen);
        }

        if(giayDiDuongDto.noiODiaChi != null && !giayDiDuongDto.noiODiaChi.isEmpty()) {
            giayDiDuong.setNoiODiaChi(giayDiDuongDto.noiODiaChi);
        }

        if(giayDiDuongDto.noiOTinhThanhMa != null && !giayDiDuongDto.noiOTinhThanhMa.isEmpty()) {
            giayDiDuong.setNoiOTinhThanhMa(giayDiDuongDto.noiOTinhThanhMa);
        }

        if(giayDiDuongDto.noiOTinhThanhTen != null && !giayDiDuongDto.noiOTinhThanhTen.isEmpty()) {
            giayDiDuong.setNoiOTinhThanhTen(giayDiDuongDto.noiOTinhThanhTen);
        }

        if(giayDiDuongDto.noiOQuanHuyenMa != null && !giayDiDuongDto.noiOQuanHuyenMa.isEmpty()) {
            giayDiDuong.setNoiOQuanHuyenMa(giayDiDuongDto.noiOQuanHuyenMa);
        }

        if(giayDiDuongDto.noiOQuanHuyenTen != null && !giayDiDuongDto.noiOQuanHuyenTen.isEmpty()) {
            giayDiDuong.setNoiOQuanHuyenTen(giayDiDuongDto.noiOQuanHuyenTen);
        }

        if(giayDiDuongDto.noiOPhuongXaMa != null && !giayDiDuongDto.noiOPhuongXaMa.isEmpty()) {
            giayDiDuong.setNoiOPhuongXaMa(giayDiDuongDto.noiOPhuongXaMa);
        }

        if(giayDiDuongDto.noiOPhuongXaTen != null && !giayDiDuongDto.noiOPhuongXaTen.isEmpty()) {
            giayDiDuong.setNoiOPhuongXaTen(giayDiDuongDto.noiOPhuongXaTen);
        }

        if(isCreate) {
            giayDiDuong.setUyBanNhanDanID(giayDiDuongDto.uyBanNhanDanID);

            String lichLamViec = "{}";

            if(giayDiDuongDto.lichLamViec != null) {
                lichLamViec = calenderWorking(giayDiDuongDto);
                giayDiDuong.setLichLamViec(lichLamViec);
            }

            giayDiDuong.setChecksum(base64(giayDiDuongDto));
            giayDiDuong.setStatus(StatusConstant.PENDING);
            giayDiDuong.setMaQR(VaccomUtil.generateQRCode("gdd", 6));
        }

        if(!isCreate) {
            if(giayDiDuongDto.status != StatusConstant.PENDING) {
                giayDiDuong.setStatus(giayDiDuongDto.status);
            }
        }

        if(giayDiDuongDto.ngayCap != null && !giayDiDuongDto.ngayCap.isEmpty()) {
            giayDiDuong.setNgayCap(giayDiDuongDto.ngayCap);
        }

        if(giayDiDuongDto.thoiHan != null && !giayDiDuongDto.thoiHan.isEmpty()) {
            giayDiDuong.setThoiHan(giayDiDuongDto.thoiHan);
        }

        if(giayDiDuongDto.ghiChu != null && !giayDiDuongDto.ghiChu.isEmpty()) {
            giayDiDuong.setGhiChu(giayDiDuongDto.ghiChu);
        }
    }

    @Override
    public String base64(GiayDiDuongDto giayDiDuongDto) throws Exception {
        String hoVaten        = giayDiDuongDto.hoVaTen != null && !giayDiDuongDto.hoVaTen.isEmpty() ? giayDiDuongDto.hoVaTen.trim() : "";
        String sdt            = giayDiDuongDto.soDienThoai != null && !giayDiDuongDto.soDienThoai.isEmpty() ? giayDiDuongDto.soDienThoai.trim() : "";
        String noiODiaChi     = giayDiDuongDto.noiODiaChi != null && !giayDiDuongDto.noiODiaChi.isEmpty() ? giayDiDuongDto.noiODiaChi.trim() : "";
        String noiCtDiaChi    = giayDiDuongDto.noiCtDiaChi != null && !giayDiDuongDto.noiCtDiaChi.isEmpty() ? giayDiDuongDto.noiCtDiaChi.trim() : "";
        String cmt            = giayDiDuongDto.cmtcccd != null && !giayDiDuongDto.cmtcccd.isEmpty() ? giayDiDuongDto.cmtcccd.trim() : "";
        String noiCtTenCoQuan = giayDiDuongDto.noiCtTenCoQuan != null && !giayDiDuongDto.noiCtTenCoQuan.isEmpty() ? giayDiDuongDto.noiCtTenCoQuan.trim() : "";
        String lichLamViec    = calenderWorking(giayDiDuongDto).trim();
        String thoiHan        = giayDiDuongDto.thoiHan != null && !giayDiDuongDto.thoiHan.isEmpty() ? giayDiDuongDto.thoiHan.trim() : "";

        String sumText = hoVaten + "_" + sdt +  "_" + noiODiaChi +  "_" + noiCtDiaChi +  "_" + cmt +  "_" + noiCtTenCoQuan +  "_" + lichLamViec +  "_" + thoiHan;

        return Base64.getEncoder().encodeToString(sumText.getBytes());
    }

    @Override
    public String calenderWorking(GiayDiDuongDto giayDiDuongDto) throws Exception {
        if(giayDiDuongDto.lichLamViec == null) {
            return "{}";
        }

        try {
            return obj.writeValueAsString(giayDiDuongDto.lichLamViec);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
