### Release v0.4.3
* Thêm tiêu chí tìm kiếm người đã tiêm
* Tự động tạo tk công dân khi đồng bộ người đăng ký ban đầu sang đăng ký chính thức
* Cài đặt chức năng gửi thông báo khi giấy đi đường được cập nhật 
* Thêm đầu API thực hiện thêm giấy đi đường theo danh sách ID 
* Thêm bảng t_congdan, thêm các cột liên kết với bảng t_nguoitiemchung, t_muitiemchung, 
* Chỉnh sửa lại quy trình cập nhật trạng thái người tiêm chủng khi thêm vào phiếu hẹn 
* Chỉnh sửa quy trình cập nhật trạng thái người tiêm chủng khi thêm mũi tiêm chủng
* Chỉnh sửa quy trình đóng lịch tiêm chủng, đưa người tiêm chủng về danh sách chính thức theo điều kiện
* Chỉnh sửa quy trình xóa phiếu hẹn

### Release v0.4.2
* Thêm chức năng lưu lại thông tin mã lỗi của Zalo để tiện việc tra cứu debug của thông báo
* Sửa lỗi và tối ưu tính năng gửi tin nhắn
* Sửa lỗi và Tối ưu chức năng chuyển đổi số điện thoại (11 số cho zalo, 9 số cho sms)
* Thêm api quản lý hàng đợi để quản lý
* Chỉnh sửa thời gian cho Scheduler chạy từ 6h -> 22h
* Thêm tính năng Import Excel cho lịch tiêm
* Thêm tính năng màn hình chờ khi người dùng thao tác ấn đồng bộ
* Hiển thị thông báo số người đăng kí tiêm được đồng bộ
* Thêm bộ lọc cho danh sách người đã tiêm

### Release v0.4.1
* Thêm tính năng lọc danh sách dữ liệu không chuẩn(thiếu các trường bắt buộc khi import từ excel) - issue #85 
* Thêm danh mục loại thuốc
* Thêm cột địa bàn cơ sở trong danh sách export
* Thêm cấu hình gửi zalo cho nhiều đơn vị
* Đổi tên "Danh sách chứng nhận tiêm chủng" thành "Danh sách đã tiêm" - issue #87

### Release v0.4.0
* Tính năng quản lý lịch tiêm
* Gửi tin nhắn SMS thông báo giấy đi đường, lịch hẹn tiêm
* Bổ sung bộ lọc Tỉnh/Huyện/Xã

### Release v0.3.2
#### Hotfix:
* Backend: Sửa cơ chế check trùng người tiêm chủng khi impport từ excel
* Backend: Sửa các điều kiện tìm kiếm cho API lọc danh sách người tiêm chủng
* Backend: Sửa lỗi impport đối với các mẫu excel có merge các cột
* Frontend: Sửa lỗi cập nhật người tiêm chủng không tự chọn địa bàn cơ sơ

### Release v0.3.1
#### Hotfix:
* Frontend: Không tạo được tài khoản 
* Backend: Tài khoản mới tạo ko đăng nhập được
#### Bugfix:
* Phân trang các list dài
* Validate trường CMTND/CCCD/Passport đúng chuẩn.

### Release v0.3
#### New Features:
* Danh sách đăng ký đã rút
* Khôi phục danh sách đăng ký
* Xem thông tin lịch sử tiêm, lịch hẹn tiêm
* Kiểm tra trùng khi đưa vào danh sách chính thức
#### Fix bugs:
* Fix lỗi đánh dấu trạng thái trùng không chính xác

### Release v0.2
#### New Features:
* Cấp tài khoản cho người tiêm chủng được duyệt vào danh sách chính thức
* Cấp mã QRCode cho người tiêm chủng trong danh sách đăng ký tiêm
* Quản trị ca tiêm chủng
#### Fix bugs:
* Bỏ trường mật khẩu mã hóa trong danh sách người dùng trả về
* Xác thực tất cả các đầu API
* Phân quyền cho truy cập API

### Release v0.1
#### New Features:
* Quản lý thêm mới danh sách đối tượng đăng kí
* Nạp danh sách đăng kí từ Excel
* Kiểm tra sự trùng lặp của đối tương đăng kí
* Quản lý duyệt danh sách đăng kí chính thức
* Quản lý thông tin các mũi đã tiêm chủng của người đăng kí
* Cấp tài khoản người dùng cho đối tượng đăng kí
* Xuất danh sách đăng kí ra tệp Excel
* Quản trị danh sách cơ sở y tế
* Quản trị danh sách địa bàn cơ sở
* Quản trị danh sách người dùng (cán bộ y tế, cán bộ đia bàn)


* Tạo d/s gọi tiêm chuyển t_nguoitiemchung sang 4, tạo phiếu tiem
* Danh sách goi tiêm: t_nguoitiemchung join t_phieutiem, t_lichtiem(lọc theo các trạng thái của phiếu tiêm)
* Chuyển d/s gọi tiêm khi chưa gửi tin nhắn(trạng thái phiếu bằng 0) về d/s chính thức(cập nhật lại trạng thái đk = 1, và xóa phiếu)
* Chuyển d/s người chưa được tiêm về chính thức(trạng thái phiếu !=4 && !=0) về d/s chính thức(cập nhật lại trạng thái đk = 1, và xóa phiếu)
* Tích hợp thêm mẫu thông báo Sửa giấy đi đường khi cập nhật giấy đi đường đã cấp
* Chỉnh sửa lại payload cho hệ thống gửi tin nhắn phù hợp với Zalo Template

#### New Features:
* Cập nhật lại quy trình hệ thống
* Bổ sung chức năng năng lọc trong danh sách gọi tiêm
+ Họ và tên
+ Tỉnh/Huyện/Xã
+ Loại Vaccine
+ Số Mũi Tiêm
* Thêm chức năng lọc khi thêm người tiêm chủng vào danh sách gọi tiêm
+ Verocell : 4 tuần
+ Astra : 8 tuần
+ Moderna : 8 tuần
+ Pfizer : 3 tuần
* Bổ sung chức năng check-in, check-out trong trang QRCode
* Tối ưu danh sách chính thức
+ Bổ sung nhập thông tin mũi tiêm chủng trong form tạo ds chính thức
+ Bổ sung import danh sách đã tiêm vào danh sách chính thức

#Release 01/10/2021
+ Thêm tính năng lọc theo loại thuốc và điều kiện đạt tiêu chuẩn ngày tiêm
+ Chỉnh sửa lại lỗi pending khi thêm danh sách gọi tiêm
+ Chỉnh sửa lỗi trùng lặp người tiêm chủng khi đã xóa và thêm bản ghi mới không vào chính thức
+ Cấu hình lại gửi tin nhắn SMS và ZALO theo dạng template mới
+ Cấu hình giá trị tối đa của tên cơ sở là 30 kí tự theo template của Zalo
+ Thêm chức năng export với từng trạng thái phiếu hẹn
+ Sửa lại tính ngày trong lọc lịch
+ Sửa cơ chế đóng lịch tiêm