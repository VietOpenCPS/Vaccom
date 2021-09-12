package org.vaccom.vcmgt.dto;

import javax.persistence.Column;
import java.util.List;

public class GiayDiDuongDto {
    public String maQR;

    public String hoVaTen;

    public String cmtcccd;

    public String soDienThoai;

    public String email;

    public String noiCtTenCoQuan;

    public String noiCtDiaChi;

    public String noiCtTinhThanhMa;

    public String noiCtTinhThanhTen;

    public String noiCtQuanHuyenMa;

    public String noiCtQuanHuyenTen;

    public String noiCtPhuongXaMa;

    public String noiCtPhuongXaTen;

    public String noiODiaChi;

    public String noiOTinhThanhMa;

    public String noiOTinhThanhTen;

    public String noiOQuanHuyenMa;

    public String noiOQuanHuyenTen;

    public String noiOPhuongXaMa;

    public String noiOPhuongXaTen;

    public LichLamViecDto lichLamViec;

    public int uyBanNhanDanID;

    public String ngayCap;

    public String thoiHan;

    public String ghiChu;

    public int status;

    public List<Integer> listIdUpdate;
    public List<Integer> listIdDelete;

    public boolean isSearchAll;

    public int page;
    public int size;

}
