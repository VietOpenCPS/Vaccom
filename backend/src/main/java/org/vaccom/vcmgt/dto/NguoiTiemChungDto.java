package org.vaccom.vcmgt.dto;

import org.vaccom.vcmgt.entity.MuiTiemChung;

import javax.persistence.Column;
import java.util.List;

public class NguoiTiemChungDto {



    public String hovaten;

    public String maqr;

    public String ngaysinh;

    public int gioitinh;

    public String cmtcccd;

    public int nhomdoituong;

    public int caTiemChungId;

    public int lichTiemChungId;

    public String donvicongtac;

    public long congDanId;

    public String sodienthoai;

    public String email;

    public String sothebhyt;

    public String diachinoio;

    public String tinhthanhma;

    public String tinhthanhten;

    public String quanhuyenma;

    public String quanhuyenten;

    public String phuongxama;

    public String phuongxaten;

    public long diabancosoid;

    public long cosoyteid;

    public String cosoytema;

    public String cosoyteten;

    public String dantocma;

    public String quoctichma;

    public String tiensudiung;

    public String cacbenhlydangmac;

    public String cacthuocdangdung;

    public String ghichu;

    public String ngaydangki;

    public int tinhtrangdangki;

    public int kiemtratrung;

    public String ketquakiemTra;

    public int tinhtrangxacnhan;

    public List<Integer> listtinhtrangxacnhan;
    public List<Integer> listtinhtrangdangki;

    public int typeGet;

    public boolean isSearchOr = false;
    public boolean isSearchDaTiem = false;

    public List<MuiTiemChungDto> listMuiTieuChungDto;

    public String loaiThuocTiem;

    public int soMuiTiem;

    public String ngayTiemCuoi;

}
