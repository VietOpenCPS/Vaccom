// https://thachban.vaccom.vn:8080/rest/v1/app/get/tinhthanh

const mockTinhThanh = r'''[
    {
        "id": 1,
        "tinhThanhMa": "01",
        "tinhThanhTen": "Thành phố Hà Nội"
    },
    {
        "id": 2,
        "tinhThanhMa": "02",
        "tinhThanhTen": "Tỉnh Hà Giang"
    },
    {
        "id": 3,
        "tinhThanhMa": "04",
        "tinhThanhTen": "Tỉnh Cao Bằng"
    },
    {
        "id": 4,
        "tinhThanhMa": "06",
        "tinhThanhTen": "Tỉnh Bắc Kạn"
    },
    {
        "id": 5,
        "tinhThanhMa": "08",
        "tinhThanhTen": "Tỉnh Tuyên Quang"
    },
    {
        "id": 6,
        "tinhThanhMa": "10",
        "tinhThanhTen": "Tỉnh Lào Cai"
    },
    {
        "id": 7,
        "tinhThanhMa": "11",
        "tinhThanhTen": "Tỉnh Điện Biên"
    },
    {
        "id": 8,
        "tinhThanhMa": "12",
        "tinhThanhTen": "Tỉnh Lai Châu"
    },
    {
        "id": 9,
        "tinhThanhMa": "14",
        "tinhThanhTen": "Tỉnh Sơn La"
    },
    {
        "id": 10,
        "tinhThanhMa": "15",
        "tinhThanhTen": "Tỉnh Yên Bái"
    },
    {
        "id": 11,
        "tinhThanhMa": "17",
        "tinhThanhTen": "Tỉnh Hoà Bình"
    },
    {
        "id": 12,
        "tinhThanhMa": "19",
        "tinhThanhTen": "Tỉnh Thái Nguyên"
    },
    {
        "id": 13,
        "tinhThanhMa": "20",
        "tinhThanhTen": "Tỉnh Lạng Sơn"
    },
    {
        "id": 14,
        "tinhThanhMa": "22",
        "tinhThanhTen": "Tỉnh Quảng Ninh"
    },
    {
        "id": 15,
        "tinhThanhMa": "24",
        "tinhThanhTen": "Tỉnh Bắc Giang"
    },
    {
        "id": 16,
        "tinhThanhMa": "25",
        "tinhThanhTen": "Tỉnh Phú Thọ"
    },
    {
        "id": 17,
        "tinhThanhMa": "26",
        "tinhThanhTen": "Tỉnh Vĩnh Phúc"
    },
    {
        "id": 18,
        "tinhThanhMa": "27",
        "tinhThanhTen": "Tỉnh Bắc Ninh"
    },
    {
        "id": 19,
        "tinhThanhMa": "30",
        "tinhThanhTen": "Tỉnh Hải Dương"
    },
    {
        "id": 20,
        "tinhThanhMa": "31",
        "tinhThanhTen": "Thành phố Hải Phòng"
    },
    {
        "id": 21,
        "tinhThanhMa": "33",
        "tinhThanhTen": "Tỉnh Hưng Yên"
    },
    {
        "id": 22,
        "tinhThanhMa": "34",
        "tinhThanhTen": "Tỉnh Thái Bình"
    },
    {
        "id": 23,
        "tinhThanhMa": "35",
        "tinhThanhTen": "Tỉnh Hà Nam"
    },
    {
        "id": 24,
        "tinhThanhMa": "36",
        "tinhThanhTen": "Tỉnh Nam Định"
    },
    {
        "id": 25,
        "tinhThanhMa": "37",
        "tinhThanhTen": "Tỉnh Ninh Bình"
    },
    {
        "id": 26,
        "tinhThanhMa": "38",
        "tinhThanhTen": "Tỉnh Thanh Hóa"
    },
    {
        "id": 27,
        "tinhThanhMa": "40",
        "tinhThanhTen": "Tỉnh Nghệ An"
    },
    {
        "id": 28,
        "tinhThanhMa": "42",
        "tinhThanhTen": "Tỉnh Hà Tĩnh"
    },
    {
        "id": 29,
        "tinhThanhMa": "44",
        "tinhThanhTen": "Tỉnh Quảng Bình"
    },
    {
        "id": 30,
        "tinhThanhMa": "45",
        "tinhThanhTen": "Tỉnh Quảng Trị"
    },
    {
        "id": 31,
        "tinhThanhMa": "46",
        "tinhThanhTen": "Tỉnh Thừa Thiên Huế"
    },
    {
        "id": 32,
        "tinhThanhMa": "48",
        "tinhThanhTen": "Thành phố Đà Nẵng"
    },
    {
        "id": 33,
        "tinhThanhMa": "49",
        "tinhThanhTen": "Tỉnh Quảng Nam"
    },
    {
        "id": 34,
        "tinhThanhMa": "51",
        "tinhThanhTen": "Tỉnh Quảng Ngãi"
    },
    {
        "id": 35,
        "tinhThanhMa": "52",
        "tinhThanhTen": "Tỉnh Bình Định"
    },
    {
        "id": 36,
        "tinhThanhMa": "54",
        "tinhThanhTen": "Tỉnh Phú Yên"
    },
    {
        "id": 37,
        "tinhThanhMa": "56",
        "tinhThanhTen": "Tỉnh Khánh Hòa"
    },
    {
        "id": 38,
        "tinhThanhMa": "58",
        "tinhThanhTen": "Tỉnh Ninh Thuận"
    },
    {
        "id": 39,
        "tinhThanhMa": "60",
        "tinhThanhTen": "Tỉnh Bình Thuận"
    },
    {
        "id": 40,
        "tinhThanhMa": "62",
        "tinhThanhTen": "Tỉnh Kon Tum"
    },
    {
        "id": 41,
        "tinhThanhMa": "64",
        "tinhThanhTen": "Tỉnh Gia Lai"
    },
    {
        "id": 42,
        "tinhThanhMa": "66",
        "tinhThanhTen": "Tỉnh Đắk Lắk"
    },
    {
        "id": 43,
        "tinhThanhMa": "67",
        "tinhThanhTen": "Tỉnh Đắk Nông"
    },
    {
        "id": 44,
        "tinhThanhMa": "68",
        "tinhThanhTen": "Tỉnh Lâm Đồng"
    },
    {
        "id": 45,
        "tinhThanhMa": "70",
        "tinhThanhTen": "Tỉnh Bình Phước"
    },
    {
        "id": 46,
        "tinhThanhMa": "72",
        "tinhThanhTen": "Tỉnh Tây Ninh"
    },
    {
        "id": 47,
        "tinhThanhMa": "74",
        "tinhThanhTen": "Tỉnh Bình Dương"
    },
    {
        "id": 48,
        "tinhThanhMa": "75",
        "tinhThanhTen": "Tỉnh Đồng Nai"
    },
    {
        "id": 49,
        "tinhThanhMa": "77",
        "tinhThanhTen": "Tỉnh Bà Rịa - Vũng Tàu"
    },
    {
        "id": 50,
        "tinhThanhMa": "79",
        "tinhThanhTen": "Thành phố Hồ Chí Minh"
    },
    {
        "id": 51,
        "tinhThanhMa": "80",
        "tinhThanhTen": "Tỉnh Long An"
    },
    {
        "id": 52,
        "tinhThanhMa": "82",
        "tinhThanhTen": "Tỉnh Tiền Giang"
    },
    {
        "id": 53,
        "tinhThanhMa": "83",
        "tinhThanhTen": "Tỉnh Bến Tre"
    },
    {
        "id": 54,
        "tinhThanhMa": "84",
        "tinhThanhTen": "Tỉnh Trà Vinh"
    },
    {
        "id": 55,
        "tinhThanhMa": "86",
        "tinhThanhTen": "Tỉnh Vĩnh Long"
    },
    {
        "id": 56,
        "tinhThanhMa": "87",
        "tinhThanhTen": "Tỉnh Đồng Tháp"
    },
    {
        "id": 57,
        "tinhThanhMa": "89",
        "tinhThanhTen": "Tỉnh An Giang"
    },
    {
        "id": 58,
        "tinhThanhMa": "91",
        "tinhThanhTen": "Tỉnh Kiên Giang"
    },
    {
        "id": 59,
        "tinhThanhMa": "92",
        "tinhThanhTen": "Thành phố Cần Thơ"
    },
    {
        "id": 60,
        "tinhThanhMa": "93",
        "tinhThanhTen": "Tỉnh Hậu Giang"
    },
    {
        "id": 61,
        "tinhThanhMa": "94",
        "tinhThanhTen": "Tỉnh Sóc Trăng"
    },
    {
        "id": 62,
        "tinhThanhMa": "95",
        "tinhThanhTen": "Tỉnh Bạc Liêu"
    },
    {
        "id": 63,
        "tinhThanhMa": "96",
        "tinhThanhTen": "Tỉnh Cà Mau"
    }
]''';