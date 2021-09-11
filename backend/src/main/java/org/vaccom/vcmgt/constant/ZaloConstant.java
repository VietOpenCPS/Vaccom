package org.vaccom.vcmgt.constant;

public class ZaloConstant {
    //Zalo Notification

    // Khung của Body Zalo
    public final static String phone = "phone";
    public final static String template_id = "template_id";
    public final static String template_data = "template_data";
    public final static String tracking_id = "tracking_id";

    //  Notification 1 : Đăng ký đối tượng tiêm chủng đã được duyệt chính thức
    public final static String HoVaTen = "HoVaTen";
    public final static String TenDangNhap = "TenDangNhap";
    public final static String MatKhau = "MatKhau";

    //Notification 2 : Thông báo lịch hẹn gọi tiêm chủng
//    public final static String HoVaTen = "HoVaTen";
    public final static String NgayHenTiem = "NgayHenTiem";
    public final static String GioHenTiem = "GioHenTiem";
    public final static String DiaDiem = "DiaDiem";
    public final static String LanTiem = "LanTiem";
    public final static String LoaiThuocTiem = "LoaiThuocTiem";
    public final static String DonViTiem = "DonViTiem";
    public final static String SoDonViCap = "sodonvicap";



    public final static String Loai_XacNhan_NguoiTiemChung = "AUTH_ACC";
    public final static String Loai_Giay_Di_Duong = "GIAY_DI_DUONG";
    public final static String Loai_Hen_TiemChung = "HEN_TIEM_CHUNG";
    public final static String Access_Token = "ACCESS_TOKEN";


    // Notification chứng nhận tiêm chủng
    public final static String CoSoYTe = "CoSoYTe";
    public final static String SoLo = "SoLo";
    public final static String NgayTiemChung = "NgayTiemChung";
    public final static String GioTiemChung = "GioTiemChung";
    public final static String QrCodeID = "QRCodeId";

    // Notifcation Giay di đường
    public final static String DonViCap = "Donvicap";
    public final static String statusGuiTinNhan = "statusGuiTinNhan";

    public final static String LinkQrCode = "LinkQrCode";


    // response trả về
    public final static String error = "error";
    public final static String message = "message";

    // SMS config
    public final static String account = "account";
    public final static String password = "password";

    // Status gui tin
    public final static long CHUA_GUI = 0;
    public final static long GUI_ZALO_THANH_CONG = 1;
    public final static long GUI_SMS_THANH_CONG = 2;
    public final static long GUI_THAT_BAI = 3;


}
