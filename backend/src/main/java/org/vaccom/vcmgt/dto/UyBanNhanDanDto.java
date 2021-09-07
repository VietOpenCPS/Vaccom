package org.vaccom.vcmgt.dto;

import org.springframework.data.relational.core.sql.In;

import javax.persistence.Column;
import java.util.List;

public class UyBanNhanDanDto {

    public String tenCoQuan;

    public String maDinhDanh;

    public long uyBanNhanDanChaID;

    public String tinhThanhMa;

    public String tinhThanhTen;

    public String quanHuyenMa;

    public String quanHuyenTen;

    public String phuongXaMa;

    public String phuongXaTen;

    public String diaChiHoatDong;

    public String nguoiDaiDien;

    public String soDienThoai;

    public List<Integer> listIdUpdate;
    public List<Integer> listIdDelete;
}
