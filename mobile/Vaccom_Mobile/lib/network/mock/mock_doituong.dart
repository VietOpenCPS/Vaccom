// https://thachban.vaccom.vn:8080/rest/v1/app/get/doituong

const mockDoiTuong = r'''[
    {
        "id": 1,
        "doiTuongMa": "1",
        "doiTuongMoTa": "Người làm việc trong các cơ sở y tế, ngành y tế (công lập và tư nhân)"
    },
    {
        "id": 2,
        "doiTuongMa": "2",
        "doiTuongMoTa": "Người tham gia phòng chống dịch"
    },
    {
        "id": 3,
        "doiTuongMa": "3",
        "doiTuongMoTa": "Lực lượng Quân đội"
    },
    {
        "id": 4,
        "doiTuongMa": "4",
        "doiTuongMoTa": "Lực lượng Công an"
    },
    {
        "id": 5,
        "doiTuongMa": "5",
        "doiTuongMoTa": "Nhân viên, cán bộ ngoại giao của Việt Nam và thân nhân được cử đi nước ngoài; người làm việc trong các cơ quan Ngoại giao, Lãnh sự, các tổ chức quốc tế hoạt động tại Việt Nam"
    },
    {
        "id": 6,
        "doiTuongMa": "6",
        "doiTuongMoTa": "Hải quan, cán bộ làm công tác xuất nhập cảnh"
    },
    {
        "id": 7,
        "doiTuongMa": "7",
        "doiTuongMoTa": "Người cung cấp dịch vụ thiết yếu: hàng không, vận tải, du lịch; cung cấp dịch vụ điện, nước"
    },
    {
        "id": 8,
        "doiTuongMa": "8",
        "doiTuongMoTa": "Giáo viên, người làm việc, học sinh, sinh viên tại các cơ sở giáo dục, đào tạo; lực lượng bác sỹ trẻ; người làm việc tại các cơ quan, đơn vị hành chính; các tổ chức hành nghề luật sư, công chứng, đấu giá… thường xuyên tiếp xúc với nhiều người"
    },
    {
        "id": 9,
        "doiTuongMa": "9",
        "doiTuongMoTa": "Người mắc các bệnh mạn tính; Người trên 65 tuổi"
    },
    {
        "id": 10,
        "doiTuongMa": "10",
        "doiTuongMoTa": "Người sinh sống tại các vùng có dịch"
    },
    {
        "id": 11,
        "doiTuongMa": "11",
        "doiTuongMoTa": "Người nghèo, các đối tượng chính sách xã hội"
    },
    {
        "id": 12,
        "doiTuongMa": "12",
        "doiTuongMoTa": "Người được cơ quan nhà nước có thẩm quyền cử đi công tác, học tập, lao động ở nước ngoài hoặc có nhu cầu xuất cảnh để công tác, học tập và lao động ở nước ngoài; chuyên gia nước ngoài làm việc tại Việt Nam"
    },
    {
        "id": 13,
        "doiTuongMa": "13",
        "doiTuongMoTa": "Các đối tượng là người lao động, thân nhân người lao động đang làm việc tại các doanh nghiệp (bao gồm cả doanh nghiệp trong khu công nghiệp, khu chế xuất, doanh nghiệp kinh doanh vận tải, tín dụng, du lịch…), cơ sở kinh doanh dịch vụ thiết yếu như các cơ sở lưu trú, ăn uống, ngân hàng, chăm sóc sức khỏe, dược, vật tư y tế… cơ sở bán lẻ, bán buôn, chợ, công trình xây dựng, người dân ở vùng, khu du lịch"
    },
    {
        "id": 14,
        "doiTuongMa": "14",
        "doiTuongMoTa": "Các chức sắc, chức việc các tôn giáo"
    },
    {
        "id": 15,
        "doiTuongMa": "15",
        "doiTuongMoTa": "Người lao động tự do"
    },
    {
        "id": 16,
        "doiTuongMa": "16",
        "doiTuongMoTa": "Các đối tượng khác theo Quyết định của Bộ trưởng Bộ Y tế hoặc Chủ tịch Ủy ban nhân dân tỉnh, thành phố và đề xuất của các đơn vị viện trợ vắc xin cho Bộ Y tế"
    }
]''';