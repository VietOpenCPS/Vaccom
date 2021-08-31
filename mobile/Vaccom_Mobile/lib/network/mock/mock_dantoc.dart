// https://thachban.vaccom.vn:8080/rest/v1/app/get/dantoc

const mockDanToc = r'''[
    {
        "id": 1,
        "danTocMa": "01",
        "danTocTen": "Kinh"
    },
    {
        "id": 2,
        "danTocMa": "02",
        "danTocTen": "Tày"
    },
    {
        "id": 3,
        "danTocMa": "03",
        "danTocTen": "Thái"
    },
    {
        "id": 4,
        "danTocMa": "04",
        "danTocTen": "Mường"
    },
    {
        "id": 5,
        "danTocMa": "05",
        "danTocTen": "Khmer"
    },
    {
        "id": 6,
        "danTocMa": "06",
        "danTocTen": "Hoa"
    },
    {
        "id": 7,
        "danTocMa": "07",
        "danTocTen": "Nùng"
    },
    {
        "id": 8,
        "danTocMa": "08",
        "danTocTen": "Mông"
    },
    {
        "id": 9,
        "danTocMa": "09",
        "danTocTen": "Dao"
    },
    {
        "id": 10,
        "danTocMa": "10",
        "danTocTen": "Gia Rai"
    },
    {
        "id": 11,
        "danTocMa": "11",
        "danTocTen": "Ê  Đê"
    },
    {
        "id": 12,
        "danTocMa": "12",
        "danTocTen": "Ba Na"
    },
    {
        "id": 13,
        "danTocMa": "13",
        "danTocTen": "Sán Chay"
    },
    {
        "id": 14,
        "danTocMa": "14",
        "danTocTen": "Chăm"
    },
    {
        "id": 15,
        "danTocMa": "15",
        "danTocTen": "Cơ Ho"
    },
    {
        "id": 16,
        "danTocMa": "16",
        "danTocTen": "Xơ Đăng"
    },
    {
        "id": 17,
        "danTocMa": "17",
        "danTocTen": "Sán Dìu"
    },
    {
        "id": 18,
        "danTocMa": "18",
        "danTocTen": "Hrê"
    },
    {
        "id": 19,
        "danTocMa": "19",
        "danTocTen": "RaGlay"
    },
    {
        "id": 20,
        "danTocMa": "20",
        "danTocTen": "Mnông"
    },
    {
        "id": 21,
        "danTocMa": "21",
        "danTocTen": "Thổ (4)"
    },
    {
        "id": 22,
        "danTocMa": "22",
        "danTocTen": "Xtiêng"
    },
    {
        "id": 23,
        "danTocMa": "23",
        "danTocTen": "Khơ mú"
    },
    {
        "id": 24,
        "danTocMa": "24",
        "danTocTen": "Bru Vân Kiều"
    },
    {
        "id": 25,
        "danTocMa": "25",
        "danTocTen": "Cơ Tu"
    },
    {
        "id": 26,
        "danTocMa": "26",
        "danTocTen": "Giáy"
    },
    {
        "id": 27,
        "danTocMa": "27",
        "danTocTen": "Tà Ôi"
    },
    {
        "id": 28,
        "danTocMa": "28",
        "danTocTen": "Mạ"
    },
    {
        "id": 29,
        "danTocMa": "29",
        "danTocTen": "Giẻ-Triêng"
    },
    {
        "id": 30,
        "danTocMa": "30",
        "danTocTen": "Co"
    },
    {
        "id": 31,
        "danTocMa": "31",
        "danTocTen": "Chơ Ro"
    },
    {
        "id": 32,
        "danTocMa": "32",
        "danTocTen": "Xinh Mun"
    },
    {
        "id": 33,
        "danTocMa": "33",
        "danTocTen": "Hà Nhì"
    },
    {
        "id": 34,
        "danTocMa": "34",
        "danTocTen": "Chu Ru"
    },
    {
        "id": 35,
        "danTocMa": "35",
        "danTocTen": "Lào"
    },
    {
        "id": 36,
        "danTocMa": "36",
        "danTocTen": "La Chí"
    },
    {
        "id": 37,
        "danTocMa": "37",
        "danTocTen": "Kháng"
    },
    {
        "id": 38,
        "danTocMa": "38",
        "danTocTen": "Phù Lá"
    },
    {
        "id": 39,
        "danTocMa": "39",
        "danTocTen": "La Hủ"
    },
    {
        "id": 40,
        "danTocMa": "40",
        "danTocTen": "La Ha"
    },
    {
        "id": 41,
        "danTocMa": "41",
        "danTocTen": "Pà Thẻn"
    },
    {
        "id": 42,
        "danTocMa": "42",
        "danTocTen": "Lự"
    },
    {
        "id": 43,
        "danTocMa": "43",
        "danTocTen": "Ngái"
    },
    {
        "id": 44,
        "danTocMa": "44",
        "danTocTen": "Chứt"
    },
    {
        "id": 45,
        "danTocMa": "45",
        "danTocTen": "Lô Lô"
    },
    {
        "id": 46,
        "danTocMa": "46",
        "danTocTen": "Mảng"
    },
    {
        "id": 47,
        "danTocMa": "47",
        "danTocTen": "Cơ Lao"
    },
    {
        "id": 48,
        "danTocMa": "48",
        "danTocTen": "Bố Y"
    },
    {
        "id": 49,
        "danTocMa": "49",
        "danTocTen": "Cống"
    },
    {
        "id": 50,
        "danTocMa": "50",
        "danTocTen": "Si La"
    },
    {
        "id": 51,
        "danTocMa": "51",
        "danTocTen": "Pu Péo"
    },
    {
        "id": 52,
        "danTocMa": "52",
        "danTocTen": "Rơ Măm"
    },
    {
        "id": 53,
        "danTocMa": "53",
        "danTocTen": "Brâu"
    },
    {
        "id": 54,
        "danTocMa": "54",
        "danTocTen": "Ơ Đu"
    },
    {
        "id": 55,
        "danTocMa": "55",
        "danTocTen": "Người nước ngoài"
    },
    {
        "id": 56,
        "danTocMa": "99",
        "danTocTen": "Không rõ"
    }
]''';