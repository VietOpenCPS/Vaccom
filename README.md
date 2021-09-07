# Vaccom

Vaccom là phần mềm hỗ trợ quản lý và tổ chức Điểm tiêm chủng Vaccine, phát hành theo [giấy phép AGPL v3.0](LICENSE).

Ý tưởng Vaccom được khởi xướng từ [công ty cổ phần phát triển nguồn mở FDS](https://fds.vn/), và được hỗ trợ & đóng góp phát triển bởi nhóm các thành viên [VFOSSA](https://vfossa.vn/)

## Mô tả yêu cầu và quy trình phần mềm quản lý và tổ chức tiêm chủng vaccine

### Bối cảnh thực tế

Công tác tiêm chủng ở VN hiện nay đang ở giai đoạn gấp rút, tuy nhiên cách tổ chức tiêm chủng và phân bổ vaccine còn nhiều bất cập, dẫn tới chưa thống nhất các dữ liệu tiêm chủng bao gồm quy trình đăng ký, sàng lọc, lên danh sách tiêm chủng, tiêm chủng và tập trung dữ liệu tiêm chủng còn chưa thống nhất.

#### Hiện tại có thể phân loại các điểm tiêm chủng theo tính chất của các cơ quan tiêm chủng như sau:

1. ***Tiêm chủng tại các bệnh viện***: Tiêm chủng theo một đối tượng nào đó, theo danh sách do bệnh viện chủ động đề xuất.

2. ***Tiêm chủng tại các điểm tiêm chủng di động***: Tiêm chủng mang tính di động bằng các xe tiêm chủng, hiện đang áp dụng rộng rãi tại các tâm điểm dịch ví dụ như TP.HCM.

3. ***Tiêm chủng tại các Phường/Xã hoặc các địa điểm được chỉ định theo đợt***: Đây là cách tiêm chủng phổ biến nhất hiện tại, tiêm chủng theo khu vực dân cư hoặc bộ phận người tiêm chủng làm việc trong các doanh nghiệp, tổ chức lớn, nhiều người.

Các điểm tiêm chủng dạng (1) và (2) khác biệt với các điểm tiêm chủng dạng (3) là dạng (1) và (2) có đủ điều kiện kỹ thuật để tiêm chủng. Điểm tiêm chủng dạng (3) chỉ là đơn vị tổ chức địa điểm tiêm chủng, đơn vị thực hiện tiêm thông thường là các Phòng Y tế quận, đơn lẻ có thể là các y bác sỹ thuộc một bệnh viện nào đó.

Do tính chất của tiêm chủng và thời điểm tiêm chủng, đến thời điểm này, việc tăng tốc tiêm chủng là chủ trương của Nhà nước, do đó, các điểm tiêm chủng tập trung vào việc tiêm, còn chưa đồng bộ về việc quản lý thông tin tiêm chủng.

Hiện có rất nhiều nền tảng quản lý tiêm chủng như Sổ SKĐT, các ứng dụng như Bluezone, ứng dụng khai báo y tế và một số ứng dụng khác. Do thiếu tính thống nhất nên các ứng dụng này được sử dụng theo nhiều cách khác nhau tại các điểm tiêm chủng. Các ứng dụng vừa thừa (tính trùng lặp chức năng), vừa thiếu (các chức năng quản lý thông tin tiêm chủng của cơ sở) do đó chưa đáp ứng được nhu cầu của các điểm tiêm chủng.

#### Có thể phân loại việc sử dụng các ứng dụng theo các giai đoạn tiêm chủng như sau:

1. ***Giai đoạn đăng ký tiêm chủng***

Các ứng dụng như sổ sức khoẻ điện tử (trên mobile), cổng quản lý tiêm chủng quốc gia đều có chức năng đăng ký tiêm chủng trực tuyến. Nhưng trên thực tế, sau khi đăng ký, người sử dụng hầu như đều không được tiếp nhận tại các cơ sở tiêm chủng. Lý do là dữ liệu của các ứng dụng này không được các đơn vị có trách nhiệm trực tiếp lên danh sách tiêm chủng khai thác.

Phương pháp đăng ký hiện tại là đăng ký qua các điểm tiêm chủng. Các điểm tiêm chủng tự lên danh sách theo tiêu chí của mình, lập danh sách theo số lượng vaccine được phân bổ và tiến hành tiêm. Đối với các điểm tiêm chủng cấp Phường/Xã, việc thu thập thông tin người tiêm chủng được tiến hành  thủ công thông qua các tổ dân phố, công an địa phương rất vất vả và mất nhiều thời gian cho việc hiệu chỉnh các sai sót trong việc lập danh sách.

Có thể nói, giai đoạn này hoàn toàn thủ công, chưa có bất kỳ ứng dụng công nghệ nào cho đến thời điểm này, danh sách được lập theo bảng excel và nộp cho cơ quan có trách nhiệm tiêm chủng.

2. ***Giai đoạn thông báo tiêm chủng***

Khi có danh sách được lập, các điểm tiêm chủng sẽ tiến hành thông báo cho người dự kiến được tiêm. Cách thông báo cũng khá đa dạng như gọi điện thoại, thông qua người giới thiệu, các nhóm chat, email. Phương pháp thông báo không thống nhất và chưa có phương pháp khoa học dẫn tới các điểm tiêm chủng vào đầu giờ sẽ trở thành các điểm tập trung đông người vi phạm nguyên tắc 5K. Có những điểm tiêm chủng cao điểm có tới hàng trăm người tụ tập chờ tiêm, có nguy cơ trở thành các điểm lây nhiễm virus.

Chưa có một giải pháp công nghệ thống nhất nào cho giai đoạn này. Việc ứng dụng công nghệ tuỳ thuộc vào sự sáng tạo của từng điểm tiêm chủng.

3. ***Giai đoạn chuẩn bị tiêm chủng***

Vào thời điểm tiêm chủng, như đã phân tích ở trên, do hầu hết sẽ hẹn cùng một giờ đối với một lượt vaccine nên sẽ xảy ra hiện tượng tụ tập đông người. 

Ứng dụng phổ biến nhất trong giai đoạn này là sử dụng mã QR Code để checkin vào điểm tiêm chủng. Mã QR Code trong nhiều trường hợp được sử dụng không đúng cách, ví dụ như bản chất của mã QR Code là để xác nhận người đã đến địa điểm dán mã, nhưng lại không có tính xác thực để checkin, do đó thủ tục rườm rà, thời gian checkin lâu, không hiệu quả.

Các ứng dụng để thực hiện checkin là Bluezone, Sổ SKĐT, Ứng dụng khai báo y tế. Do các ứng dụng này sinh ra các mã QR khác nhau dẫn tới các điểm tiêm chủng phải hướng dẫn người sử dụng rất vất vả, có nhiều người đến nơi mới cài ứng dụng, nếu lỗi phát sinh thì gặp khó khăn trong việc checkin.

Đây là một bước rất bất cập trong công tác tiêm chủng.

4. ***Giai đoạn tiêm chủng***

Giai đoạn tiêm chủng được thực hiện theo quy trình của ngành Y tế, do vậy khá bài bản, hầu hết người đi tiêm chủng đều cơ bản hài lòng với bước này. Điểm khác biệt của bước này ở các cơ sở tiêm chủng là người lớn tuổi hoặc có bệnh lý nền, huyết áp tại thời điểm tiêm chủng tăng cao do nhiều lý do, bao gồm cả lý do tâm lý dẫn tới không được tiêm. Về quản lý thông tin sẽ phải có bước sàng lọc những người không được tiêm và những người được tiêm.

5. ***Giai đoạn xác nhận tiêm chủng***

Giai đoạn xác nhận tiêm chủng được chia thành 2 loại:

  5.1. Sau khi tiêm chủng, cơ sở tiêm chủng in giấy xác nhận tiêm chủng cho người được tiêm, ghi rõ các thông tin loại vaccine được tiêm, số lần tiêm. Sau đó hoặc không nhập vào CSDL quốc gia về tiêm chủng hoặc nhập chậm dẫn tới hiện tượng người được tiêm chủng không có thông tin trên phần mềm sức khoẻ điện tử. Hiện tượng này hay xảy ra ở các điểm tiêm chủng như bệnh viện.

  5.2. Sau khi tiêm chủng, song song với việc in và phát giấy chứng nhận tiêm chủng, cán bộ tiêm chủng nhập luôn dữ liệu vào phần mềm sổ sức khoẻ điện tử, do đó người được tiêm có thể kiểm tra ngay tình trạng tiêm chủng của mình trên ứng dụng. Các Phòng Y tế Quận/Huyện tuân thủ khá nghiêm túc quy trình này.

Với việc không có đầy đủ dữ liệu trên ứng dụng sổ sức khoẻ điện tử sẽ dẫn tới việc thống kê thiếu chính xác về số lượng người được tiêm chủng. Đối với cá nhân sẽ chưa sử dụng được hoàn toàn tình trạng tiêm chủng trên ứng dụng mà phải thay bằng chứng nhận giấy khi cần xuất trình tại các điểm yêu cầu chứng nhận tiêm chủng hoặc khi tiêm mũi thứ 2.

### Bài toán cần giải quyết

Với phân tích trên, có thể thấy rằng yếu tố công nghệ thống nhất tại các giai đoạn (1), (2), (3) là chưa có. Các giai đoạn (4), (5) có thể giải quyết hoàn toàn bằng phần mềm Sổ sức khoẻ điện tử và Cổng thông tin tiêm chủng quốc gia đã được xây dựng.

Như vậy, nhu cầu thực tế của các cơ sở tiêm chủng về ứng dụng công nghệ tập trung vào 3 điểm:

1. Công tác thu thập thông tin đăng ký tiêm chủng
2. Công tác thông báo thời gian, địa điểm được tiêm chủng đến từng người qua các phương tiện như SMS/Zalo, email, gọi điện... để có thể chủ động đến điểm tiêm chủng vào thời gian hợp lý, tránh tụ tập đông người.
3. Công tác kiểm tra danh tính người được tiêm chủng khi checkin vào các điểm tiêm chủng.

Do đã có CSDL quốc gia về tiêm chủng, nên phần mềm này có thể triển khai độc lập tại các điểm tiêm chủng hoặc sử dụng dịch vụ trên cloud. Việc làm phần mềm này hỗ trợ cho công tác tổ chức tiêm chủng, không làm thay các chức năng về quản lý thông tin tiêm chủng đến từng người dân của CSDL quốc gia. Phần mềm này là thành phần bổ sung cho CSDL quốc gia, giảm tải cho đơn vị triển khai phần mềm CSDL quốc gia về hỗ trợ người sử dụng với số lượng lớn (riêng xã/phường ở VN đã lên tới hơn 11.000 điểm).

### Cách triển khai phần mềm

Phần mềm được xây dựng và áp dụng thực tế tại Quận Long Biên - Hà Nội với sự đóng góp nguồn lực phi lợi nhuận của một số thành viên doanh nghiệp của CLB PMNM VN (VFOSSA).

Phần mềm được phát hành với giấy phép phần mềm tự do nguồn mở, giấy phép được lựa chọn là AGPL. Mục tiêu là phần mềm miễn phí để có thể nhân rộng ở các điểm tiêm chủng trên toàn quốc, hỗ trợ công tác phòng chống dịch.

Kế hoạch thực hiện: Phần mềm được làm trong 2 tuần, đầu tháng 9 sẽ triển khai toàn diện tại Quận Long Biên.

### Các chức năng chính của phần mềm

1. **Nhóm chức năng đăng ký tiêm chủng**

- Cho phép cán bộ nhập dữ liệu đăng ký tiêm chủng qua webform (mẫu kèm theo) hoặc nhập vào file excel chuẩn để upload tạo danh sách đề nghị tiêm chủng. Ở cấp Phường/Xã việc này do tổ trưởng dân phố thực hiện.
- Sau khi xong danh sách, phần mềm tự động so sánh với danh sách đã được tiêm chủng trong CSDL của Phường/Xã và loại bỏ ngay những trường hợp đã tiêm, chú ý có 2 lần tiêm.
- Người sử dụng sử dụng chức năng Nộp danh sách để đưa dữ liệu đăng ký lên đơn vị tổ chức tiêm chủng.
- Tổng hợp của các danh sách được nộp lên sẽ là danh sách đăng ký tiêm chủng của điểm tiêm chủng, không giới hạn số lượng.

2. **Nhóm chức năng duyệt tiêm chủng**

- Người sử dụng ở điểm tiêm chủng nhập dữ liệu về số lượng vaccine của từng đợt tiêm chủng, thời gian, địa điểm dự kiến của đợt tiêm chủng
- Trước thời điểm tiêm chủng 3 ngày (hoặc thời gian tuỳ chọn), cán bộ của điểm tiêm chủng lập danh sách tiêm chủng của từng đợt trên cơ sở danh sách đã được đăng ký. Danh sách tiêm chủng có thể được thêm từng người, có công cụ xoá, sửa dữ liệu của từng người được dự kiến tiêm chủng.
- Có chức năng xuất danh sách dự kiến tiêm chủng ra Excel để các Phường xã có thể nộp dữ liệu cho Phòng Y tế, nhập vào CSDL quốc gia về tiêm chủng trước khi tiêm.

3. **Nhóm chức năng thông báo tiêm chủng**

- Trên cơ sở danh sách được duyệt của từng đợt tiêm chủng, hệ thống tự sinh các thông báo qua Zalo, email bao gồm các thông tin của đợt tiêm chủng, giờ tiêm chủng (có thể phân loại để chia đợt, tránh tập trung cùng 1 thời điểm), mã QR Code để checkin vào điểm tiêm chủng.
- Cho phép in danh sách tiêm chủng theo từng đơn vị (ví dụ tổ dân phố) để phát cho Tổ trưởng thông báo cho người được tiêm chủng.
- Trong trường hợp không gửi được qua Zalo/email, hệ thống thông báo qua SMS

4. **Nhóm chức năng hỗ trợ checkin tại điểm tiêm chủng**

- Khi người dự kiến được tiêm chủng đến điểm tiêm chủng theo thời gian đã hẹn, tại điểm kiểm soát chỉ cần xuất trình CMT và mã QR Code đã được gửi trước đó để làm công tác checkin. Thời gian checkin nhanh và đơn giản để đảm bảo giãn cách.

5. **Nhóm chức năng đồng bộ dữ liệu tiêm chủng**

- Sau khi hoàn thành đợt tiêm chủng, cần lấy dữ liệu kết xuất từ CSDL Quốc gia về tiêm chủng để cập nhật lại danh sách những người đã được tiêm chủng, phục vụ loại bỏ trùng ở bước đăng ký tiêm chủng.

# Các đơn vị đóng góp cho dự án
1. [FDS](http://fds.vn) lên ý tưởng, chủ trì phát triển.
2. [NetNam](http://netnam.vn) hỗ trợ hạ tầng triển khai
3. [iWay](https://www.iwayvietnam.com/) hỗ trợ quản trị kho mã nguồn trên github, cung cấp dịch vụ email thông báo, đóng gói docker
4. [ePacific Telecom](https://epacific.com.vn/) hỗ trợ dịch vụ Tổng đài, SMS
5. [VINADES](http://vinades.vn/) hỗ trợ truyền thông
7. ... Và các cá nhân khác được ghi nhận ở kho code Github

# Các đầu mối liên lạc của dự án
1. Chủ nhiệm dự án: anh [Trần Kiêm Dũng](http://m.me/trantn1) - CEO FDS
2. Phụ trách công nghệ: anh [Tạ Tuấn Anh](http://m.me/tanhtanhvn) - CTO FDS

# Các địa chỉ mạng xã hội
- Group Facebook: https://www.facebook.com/groups/vaccom.vn
- Fanpage: https://www.facebook.com/vaccom.vn

