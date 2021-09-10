package org.vaccom.vcmgt.dto;

public class MuiTiemChungDto {
    public String ngaytiem;
    public String tenThuoc;
    public String soLo;

    public MuiTiemChungDto(String ngaytiemDto, String tenThuocDto, String soLoDto) {
        ngaytiem = ngaytiemDto;
        tenThuoc = tenThuocDto;
        soLo     = soLoDto;
    }
}
