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

