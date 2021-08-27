// https://thachban.vaccom.vn:8080/rest/v1/app/get/quanhuyen/1

const mockQuanHuyen = r'''[
    {
        "id": 1,
        "quanHuyenMa": "001",
        "quanHuyenTen": "Quận Ba Đình",
        "tinhThanhID": 1
    },
    {
        "id": 2,
        "quanHuyenMa": "002",
        "quanHuyenTen": "Quận Hoàn Kiếm",
        "tinhThanhID": 1
    },
    {
        "id": 3,
        "quanHuyenMa": "003",
        "quanHuyenTen": "Quận Tây Hồ",
        "tinhThanhID": 1
    },
    {
        "id": 4,
        "quanHuyenMa": "004",
        "quanHuyenTen": "Quận Long Biên",
        "tinhThanhID": 1
    },
    {
        "id": 5,
        "quanHuyenMa": "005",
        "quanHuyenTen": "Quận Cầu Giấy",
        "tinhThanhID": 1
    },
    {
        "id": 6,
        "quanHuyenMa": "006",
        "quanHuyenTen": "Quận Đống Đa",
        "tinhThanhID": 1
    },
    {
        "id": 7,
        "quanHuyenMa": "007",
        "quanHuyenTen": "Quận Hai Bà Trưng",
        "tinhThanhID": 1
    },
    {
        "id": 8,
        "quanHuyenMa": "008",
        "quanHuyenTen": "Quận Hoàng Mai",
        "tinhThanhID": 1
    },
    {
        "id": 9,
        "quanHuyenMa": "009",
        "quanHuyenTen": "Quận Thanh Xuân",
        "tinhThanhID": 1
    },
    {
        "id": 10,
        "quanHuyenMa": "016",
        "quanHuyenTen": "Huyện Sóc Sơn",
        "tinhThanhID": 1
    },
    {
        "id": 11,
        "quanHuyenMa": "017",
        "quanHuyenTen": "Huyện Đông Anh",
        "tinhThanhID": 1
    },
    {
        "id": 12,
        "quanHuyenMa": "018",
        "quanHuyenTen": "Huyện Gia Lâm",
        "tinhThanhID": 1
    },
    {
        "id": 13,
        "quanHuyenMa": "019",
        "quanHuyenTen": "Quận Nam Từ Liêm",
        "tinhThanhID": 1
    },
    {
        "id": 14,
        "quanHuyenMa": "020",
        "quanHuyenTen": "Huyện Thanh Trì",
        "tinhThanhID": 1
    },
    {
        "id": 15,
        "quanHuyenMa": "021",
        "quanHuyenTen": "Quận Bắc Từ Liêm",
        "tinhThanhID": 1
    },
    {
        "id": 16,
        "quanHuyenMa": "250",
        "quanHuyenTen": "Huyện Mê Linh",
        "tinhThanhID": 1
    },
    {
        "id": 17,
        "quanHuyenMa": "268",
        "quanHuyenTen": "Quận Hà Đông",
        "tinhThanhID": 1
    },
    {
        "id": 18,
        "quanHuyenMa": "269",
        "quanHuyenTen": "Thị xã Sơn Tây",
        "tinhThanhID": 1
    },
    {
        "id": 19,
        "quanHuyenMa": "271",
        "quanHuyenTen": "Huyện Ba Vì",
        "tinhThanhID": 1
    },
    {
        "id": 20,
        "quanHuyenMa": "272",
        "quanHuyenTen": "Huyện Phúc Thọ",
        "tinhThanhID": 1
    },
    {
        "id": 21,
        "quanHuyenMa": "273",
        "quanHuyenTen": "Huyện Đan Phượng",
        "tinhThanhID": 1
    },
    {
        "id": 22,
        "quanHuyenMa": "274",
        "quanHuyenTen": "Huyện Hoài Đức",
        "tinhThanhID": 1
    },
    {
        "id": 23,
        "quanHuyenMa": "275",
        "quanHuyenTen": "Huyện Quốc Oai",
        "tinhThanhID": 1
    },
    {
        "id": 24,
        "quanHuyenMa": "276",
        "quanHuyenTen": "Huyện Thạch Thất",
        "tinhThanhID": 1
    },
    {
        "id": 25,
        "quanHuyenMa": "277",
        "quanHuyenTen": "Huyện Chương Mỹ",
        "tinhThanhID": 1
    },
    {
        "id": 26,
        "quanHuyenMa": "278",
        "quanHuyenTen": "Huyện Thanh Oai",
        "tinhThanhID": 1
    },
    {
        "id": 27,
        "quanHuyenMa": "279",
        "quanHuyenTen": "Huyện Thường Tín",
        "tinhThanhID": 1
    },
    {
        "id": 28,
        "quanHuyenMa": "280",
        "quanHuyenTen": "Huyện Phú Xuyên",
        "tinhThanhID": 1
    },
    {
        "id": 29,
        "quanHuyenMa": "281",
        "quanHuyenTen": "Huyện Ứng Hòa",
        "tinhThanhID": 1
    },
    {
        "id": 30,
        "quanHuyenMa": "282",
        "quanHuyenTen": "Huyện Mỹ Đức",
        "tinhThanhID": 1
    }
]''';