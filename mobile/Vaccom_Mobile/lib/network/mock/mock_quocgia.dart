// https://thachban.vaccom.vn:8080/rest/v1/app/get/quocgia

const mockQuocGia = r'''[
    {
        "id": 1,
        "quocGiaMa": "VN",
        "quocGiaTen": "Việt Nam"
    },
    {
        "id": 2,
        "quocGiaMa": "AF",
        "quocGiaTen": "Afghanistan"
    },
    {
        "id": 3,
        "quocGiaMa": "AX",
        "quocGiaTen": "Aland Islands"
    },
    {
        "id": 4,
        "quocGiaMa": "AL",
        "quocGiaTen": "Albania"
    },
    {
        "id": 5,
        "quocGiaMa": "DZ",
        "quocGiaTen": "Algeria"
    },
    {
        "id": 6,
        "quocGiaMa": "AS",
        "quocGiaTen": "American Samoa"
    },
    {
        "id": 7,
        "quocGiaMa": "AD",
        "quocGiaTen": "Andorra"
    },
    {
        "id": 8,
        "quocGiaMa": "AO",
        "quocGiaTen": "Angola"
    },
    {
        "id": 9,
        "quocGiaMa": "AI",
        "quocGiaTen": "Anguilla"
    },
    {
        "id": 10,
        "quocGiaMa": "AQ",
        "quocGiaTen": "Antarctica"
    },
    {
        "id": 11,
        "quocGiaMa": "AG",
        "quocGiaTen": "Antigua and Barbuda"
    },
    {
        "id": 12,
        "quocGiaMa": "AR",
        "quocGiaTen": "Argentina"
    },
    {
        "id": 13,
        "quocGiaMa": "AM",
        "quocGiaTen": "Armenia"
    },
    {
        "id": 14,
        "quocGiaMa": "AW",
        "quocGiaTen": "Aruba"
    },
    {
        "id": 15,
        "quocGiaMa": "AU",
        "quocGiaTen": "Australia"
    },
    {
        "id": 16,
        "quocGiaMa": "AT",
        "quocGiaTen": "Austria"
    },
    {
        "id": 17,
        "quocGiaMa": "AZ",
        "quocGiaTen": "Azerbaijan"
    },
    {
        "id": 18,
        "quocGiaMa": "BS",
        "quocGiaTen": "Bahamas"
    },
    {
        "id": 19,
        "quocGiaMa": "BH",
        "quocGiaTen": "Bahrain"
    },
    {
        "id": 20,
        "quocGiaMa": "BD",
        "quocGiaTen": "Bangladesh"
    },
    {
        "id": 21,
        "quocGiaMa": "BB",
        "quocGiaTen": "Barbados"
    },
    {
        "id": 22,
        "quocGiaMa": "BY",
        "quocGiaTen": "Belarus"
    },
    {
        "id": 23,
        "quocGiaMa": "BE",
        "quocGiaTen": "Belgium"
    },
    {
        "id": 24,
        "quocGiaMa": "BZ",
        "quocGiaTen": "Belize"
    },
    {
        "id": 25,
        "quocGiaMa": "BJ",
        "quocGiaTen": "Benin"
    },
    {
        "id": 26,
        "quocGiaMa": "BM",
        "quocGiaTen": "Bermuda"
    },
    {
        "id": 27,
        "quocGiaMa": "BT",
        "quocGiaTen": "Bhutan"
    },
    {
        "id": 28,
        "quocGiaMa": "BO",
        "quocGiaTen": "Bolivia"
    },
    {
        "id": 29,
        "quocGiaMa": "BA",
        "quocGiaTen": "Bosnia and Herzegovina"
    },
    {
        "id": 30,
        "quocGiaMa": "BW",
        "quocGiaTen": "Botswana"
    },
    {
        "id": 31,
        "quocGiaMa": "BV",
        "quocGiaTen": "Bouvet Island"
    },
    {
        "id": 32,
        "quocGiaMa": "BR",
        "quocGiaTen": "Brazil"
    },
    {
        "id": 33,
        "quocGiaMa": "VG",
        "quocGiaTen": "British Virgin Islands"
    },
    {
        "id": 34,
        "quocGiaMa": "IO",
        "quocGiaTen": "British Indian Ocean Territory"
    },
    {
        "id": 35,
        "quocGiaMa": "BN",
        "quocGiaTen": "Brunei Darussalam"
    },
    {
        "id": 36,
        "quocGiaMa": "BG",
        "quocGiaTen": "Bulgaria"
    },
    {
        "id": 37,
        "quocGiaMa": "BF",
        "quocGiaTen": "Burkina Faso"
    },
    {
        "id": 38,
        "quocGiaMa": "BI",
        "quocGiaTen": "Burundi"
    },
    {
        "id": 39,
        "quocGiaMa": "KH",
        "quocGiaTen": "Cambodia"
    },
    {
        "id": 40,
        "quocGiaMa": "CM",
        "quocGiaTen": "Cameroon"
    },
    {
        "id": 41,
        "quocGiaMa": "CA",
        "quocGiaTen": "Canada"
    },
    {
        "id": 42,
        "quocGiaMa": "CV",
        "quocGiaTen": "Cape Verde"
    },
    {
        "id": 43,
        "quocGiaMa": "KY",
        "quocGiaTen": "Cayman Islands"
    },
    {
        "id": 44,
        "quocGiaMa": "CF",
        "quocGiaTen": "Central African Republic"
    },
    {
        "id": 45,
        "quocGiaMa": "TD",
        "quocGiaTen": "Chad"
    },
    {
        "id": 46,
        "quocGiaMa": "CL",
        "quocGiaTen": "Chile"
    },
    {
        "id": 47,
        "quocGiaMa": "CN",
        "quocGiaTen": "China"
    },
    {
        "id": 48,
        "quocGiaMa": "HK",
        "quocGiaTen": "Hong Kong"
    },
    {
        "id": 49,
        "quocGiaMa": "MO",
        "quocGiaTen": "Macao"
    },
    {
        "id": 50,
        "quocGiaMa": "CX",
        "quocGiaTen": "Christmas Island"
    },
    {
        "id": 51,
        "quocGiaMa": "CC",
        "quocGiaTen": "Cocos (Keeling) Islands"
    },
    {
        "id": 52,
        "quocGiaMa": "CO",
        "quocGiaTen": "Colombia"
    },
    {
        "id": 53,
        "quocGiaMa": "KM",
        "quocGiaTen": "Comoros"
    },
    {
        "id": 54,
        "quocGiaMa": "CG",
        "quocGiaTen": "Congo (Brazzaville)"
    },
    {
        "id": 55,
        "quocGiaMa": "CD",
        "quocGiaTen": "Congo, Democratic Republic of the"
    },
    {
        "id": 56,
        "quocGiaMa": "CK",
        "quocGiaTen": "Cook Islands"
    },
    {
        "id": 57,
        "quocGiaMa": "CR",
        "quocGiaTen": "Costa Rica"
    },
    {
        "id": 58,
        "quocGiaMa": "HR",
        "quocGiaTen": "Croatia"
    },
    {
        "id": 59,
        "quocGiaMa": "CU",
        "quocGiaTen": "Cuba"
    },
    {
        "id": 60,
        "quocGiaMa": "CY",
        "quocGiaTen": "Cyprus"
    },
    {
        "id": 61,
        "quocGiaMa": "CZ",
        "quocGiaTen": "Czech Republic"
    },
    {
        "id": 62,
        "quocGiaMa": "DK",
        "quocGiaTen": "Denmark"
    },
    {
        "id": 63,
        "quocGiaMa": "DJ",
        "quocGiaTen": "Djibouti"
    },
    {
        "id": 64,
        "quocGiaMa": "DM",
        "quocGiaTen": "Dominica"
    },
    {
        "id": 65,
        "quocGiaMa": "DO",
        "quocGiaTen": "Dominican Republic"
    },
    {
        "id": 66,
        "quocGiaMa": "EC",
        "quocGiaTen": "Ecuador"
    },
    {
        "id": 67,
        "quocGiaMa": "EG",
        "quocGiaTen": "Egypt"
    },
    {
        "id": 68,
        "quocGiaMa": "SV",
        "quocGiaTen": "El Salvador"
    },
    {
        "id": 69,
        "quocGiaMa": "GQ",
        "quocGiaTen": "Equatorial Guinea"
    },
    {
        "id": 70,
        "quocGiaMa": "ER",
        "quocGiaTen": "Eritrea"
    },
    {
        "id": 71,
        "quocGiaMa": "EE",
        "quocGiaTen": "Estonia"
    },
    {
        "id": 72,
        "quocGiaMa": "ET",
        "quocGiaTen": "Ethiopia"
    },
    {
        "id": 73,
        "quocGiaMa": "FK",
        "quocGiaTen": "Falkland Islands (Malvinas)"
    },
    {
        "id": 74,
        "quocGiaMa": "FO",
        "quocGiaTen": "Faroe Islands"
    },
    {
        "id": 75,
        "quocGiaMa": "FJ",
        "quocGiaTen": "Fiji"
    },
    {
        "id": 76,
        "quocGiaMa": "FI",
        "quocGiaTen": "Finland"
    },
    {
        "id": 77,
        "quocGiaMa": "FR",
        "quocGiaTen": "France"
    },
    {
        "id": 78,
        "quocGiaMa": "GF",
        "quocGiaTen": "French Guiana"
    },
    {
        "id": 79,
        "quocGiaMa": "PF",
        "quocGiaTen": "French Polynesia"
    },
    {
        "id": 80,
        "quocGiaMa": "TF",
        "quocGiaTen": "French Southern Territories"
    },
    {
        "id": 81,
        "quocGiaMa": "GA",
        "quocGiaTen": "Gabon"
    },
    {
        "id": 82,
        "quocGiaMa": "GM",
        "quocGiaTen": "Gambia"
    },
    {
        "id": 83,
        "quocGiaMa": "GE",
        "quocGiaTen": "Georgia"
    },
    {
        "id": 84,
        "quocGiaMa": "DE",
        "quocGiaTen": "Germany"
    },
    {
        "id": 85,
        "quocGiaMa": "GH",
        "quocGiaTen": "Ghana"
    },
    {
        "id": 86,
        "quocGiaMa": "GI",
        "quocGiaTen": "Gibraltar"
    },
    {
        "id": 87,
        "quocGiaMa": "GR",
        "quocGiaTen": "Greece"
    },
    {
        "id": 88,
        "quocGiaMa": "GL",
        "quocGiaTen": "Greenland"
    },
    {
        "id": 89,
        "quocGiaMa": "GD",
        "quocGiaTen": "Grenada"
    },
    {
        "id": 90,
        "quocGiaMa": "GP",
        "quocGiaTen": "Guadeloupe"
    },
    {
        "id": 91,
        "quocGiaMa": "GU",
        "quocGiaTen": "Guam"
    },
    {
        "id": 92,
        "quocGiaMa": "GT",
        "quocGiaTen": "Guatemala"
    },
    {
        "id": 93,
        "quocGiaMa": "GG",
        "quocGiaTen": "Guernsey"
    },
    {
        "id": 94,
        "quocGiaMa": "GN",
        "quocGiaTen": "Guinea"
    },
    {
        "id": 95,
        "quocGiaMa": "GW",
        "quocGiaTen": "Guinea-Bissau"
    },
    {
        "id": 96,
        "quocGiaMa": "GY",
        "quocGiaTen": "Guyana"
    },
    {
        "id": 97,
        "quocGiaMa": "HT",
        "quocGiaTen": "Haiti"
    },
    {
        "id": 98,
        "quocGiaMa": "HM",
        "quocGiaTen": "Heard Island and Mcdonald Islands"
    },
    {
        "id": 99,
        "quocGiaMa": "VA",
        "quocGiaTen": "Holy See (Vatican City State)"
    },
    {
        "id": 100,
        "quocGiaMa": "HN",
        "quocGiaTen": "Honduras"
    },
    {
        "id": 101,
        "quocGiaMa": "HU",
        "quocGiaTen": "Hungary"
    },
    {
        "id": 102,
        "quocGiaMa": "IS",
        "quocGiaTen": "Iceland"
    },
    {
        "id": 103,
        "quocGiaMa": "IN",
        "quocGiaTen": "India"
    },
    {
        "id": 104,
        "quocGiaMa": "ID",
        "quocGiaTen": "Indonesia"
    },
    {
        "id": 105,
        "quocGiaMa": "IR",
        "quocGiaTen": "Iran, Islamic Republic of"
    },
    {
        "id": 106,
        "quocGiaMa": "IQ",
        "quocGiaTen": "Iraq"
    },
    {
        "id": 107,
        "quocGiaMa": "IE",
        "quocGiaTen": "Ireland"
    },
    {
        "id": 108,
        "quocGiaMa": "IM",
        "quocGiaTen": "Isle of Man"
    },
    {
        "id": 109,
        "quocGiaMa": "IL",
        "quocGiaTen": "Israel"
    },
    {
        "id": 110,
        "quocGiaMa": "IT",
        "quocGiaTen": "Italy"
    },
    {
        "id": 111,
        "quocGiaMa": "JM",
        "quocGiaTen": "Jamaica"
    },
    {
        "id": 112,
        "quocGiaMa": "JP",
        "quocGiaTen": "Japan"
    },
    {
        "id": 113,
        "quocGiaMa": "JE",
        "quocGiaTen": "Jersey"
    },
    {
        "id": 114,
        "quocGiaMa": "JO",
        "quocGiaTen": "Jordan"
    },
    {
        "id": 115,
        "quocGiaMa": "KZ",
        "quocGiaTen": "Kazakhstan"
    },
    {
        "id": 116,
        "quocGiaMa": "KE",
        "quocGiaTen": "Kenya"
    },
    {
        "id": 117,
        "quocGiaMa": "KI",
        "quocGiaTen": "Kiribati"
    },
    {
        "id": 118,
        "quocGiaMa": "KR",
        "quocGiaTen": "Korea, Republic of"
    },
    {
        "id": 119,
        "quocGiaMa": "KW",
        "quocGiaTen": "Kuwait"
    },
    {
        "id": 120,
        "quocGiaMa": "KG",
        "quocGiaTen": "Kyrgyzstan"
    },
    {
        "id": 121,
        "quocGiaMa": "LA",
        "quocGiaTen": "Lao"
    },
    {
        "id": 122,
        "quocGiaMa": "LV",
        "quocGiaTen": "Latvia"
    },
    {
        "id": 123,
        "quocGiaMa": "LB",
        "quocGiaTen": "Lebanon"
    },
    {
        "id": 124,
        "quocGiaMa": "LS",
        "quocGiaTen": "Lesotho"
    },
    {
        "id": 125,
        "quocGiaMa": "LR",
        "quocGiaTen": "Liberia"
    },
    {
        "id": 126,
        "quocGiaMa": "LY",
        "quocGiaTen": "Libya"
    },
    {
        "id": 127,
        "quocGiaMa": "LI",
        "quocGiaTen": "Liechtenstein"
    },
    {
        "id": 128,
        "quocGiaMa": "LT",
        "quocGiaTen": "Lithuania"
    },
    {
        "id": 129,
        "quocGiaMa": "LU",
        "quocGiaTen": "Luxembourg"
    },
    {
        "id": 130,
        "quocGiaMa": "MK",
        "quocGiaTen": "Macedonia, Republic of"
    },
    {
        "id": 131,
        "quocGiaMa": "MG",
        "quocGiaTen": "Madagascar"
    },
    {
        "id": 132,
        "quocGiaMa": "MW",
        "quocGiaTen": "Malawi"
    },
    {
        "id": 133,
        "quocGiaMa": "MY",
        "quocGiaTen": "Malaysia"
    },
    {
        "id": 134,
        "quocGiaMa": "MV",
        "quocGiaTen": "Maldives"
    },
    {
        "id": 135,
        "quocGiaMa": "ML",
        "quocGiaTen": "Mali"
    },
    {
        "id": 136,
        "quocGiaMa": "MT",
        "quocGiaTen": "Malta"
    },
    {
        "id": 137,
        "quocGiaMa": "MH",
        "quocGiaTen": "Marshall Islands"
    },
    {
        "id": 138,
        "quocGiaMa": "MQ",
        "quocGiaTen": "Martinique"
    },
    {
        "id": 139,
        "quocGiaMa": "MR",
        "quocGiaTen": "Mauritania"
    },
    {
        "id": 140,
        "quocGiaMa": "MU",
        "quocGiaTen": "Mauritius"
    },
    {
        "id": 141,
        "quocGiaMa": "YT",
        "quocGiaTen": "Mayotte"
    },
    {
        "id": 142,
        "quocGiaMa": "MX",
        "quocGiaTen": "Mexico"
    },
    {
        "id": 143,
        "quocGiaMa": "FM",
        "quocGiaTen": "Micronesia, Federated States of"
    },
    {
        "id": 144,
        "quocGiaMa": "MD",
        "quocGiaTen": "Moldova"
    },
    {
        "id": 145,
        "quocGiaMa": "MC",
        "quocGiaTen": "Monaco"
    },
    {
        "id": 146,
        "quocGiaMa": "MN",
        "quocGiaTen": "Mongolia"
    },
    {
        "id": 147,
        "quocGiaMa": "ME",
        "quocGiaTen": "Montenegro"
    },
    {
        "id": 148,
        "quocGiaMa": "MS",
        "quocGiaTen": "Montserrat"
    },
    {
        "id": 149,
        "quocGiaMa": "MA",
        "quocGiaTen": "Morocco"
    },
    {
        "id": 150,
        "quocGiaMa": "MZ",
        "quocGiaTen": "Mozambique"
    },
    {
        "id": 151,
        "quocGiaMa": "MM",
        "quocGiaTen": "Myanmar"
    },
    {
        "id": 152,
        "quocGiaMa": "NA",
        "quocGiaTen": "Namibia"
    },
    {
        "id": 153,
        "quocGiaMa": "NR",
        "quocGiaTen": "Nauru"
    },
    {
        "id": 154,
        "quocGiaMa": "NP",
        "quocGiaTen": "Nepal"
    },
    {
        "id": 155,
        "quocGiaMa": "NL",
        "quocGiaTen": "Netherlands"
    },
    {
        "id": 156,
        "quocGiaMa": "AN",
        "quocGiaTen": "Netherlands Antilles"
    },
    {
        "id": 157,
        "quocGiaMa": "NC",
        "quocGiaTen": "New Caledonia"
    },
    {
        "id": 158,
        "quocGiaMa": "NZ",
        "quocGiaTen": "New Zealand"
    },
    {
        "id": 159,
        "quocGiaMa": "NI",
        "quocGiaTen": "Nicaragua"
    },
    {
        "id": 160,
        "quocGiaMa": "NE",
        "quocGiaTen": "Niger"
    },
    {
        "id": 161,
        "quocGiaMa": "NG",
        "quocGiaTen": "Nigeria"
    },
    {
        "id": 162,
        "quocGiaMa": "NU",
        "quocGiaTen": "Niue"
    },
    {
        "id": 163,
        "quocGiaMa": "NF",
        "quocGiaTen": "Norfolk Island"
    },
    {
        "id": 164,
        "quocGiaMa": "MP",
        "quocGiaTen": "Northern Mariana Islands"
    },
    {
        "id": 165,
        "quocGiaMa": "NO",
        "quocGiaTen": "Norway"
    },
    {
        "id": 166,
        "quocGiaMa": "OM",
        "quocGiaTen": "Oman"
    },
    {
        "id": 167,
        "quocGiaMa": "PK",
        "quocGiaTen": "Pakistan"
    },
    {
        "id": 168,
        "quocGiaMa": "PW",
        "quocGiaTen": "Palau"
    },
    {
        "id": 169,
        "quocGiaMa": "PS",
        "quocGiaTen": "Palestinian Territory, Occupied"
    },
    {
        "id": 170,
        "quocGiaMa": "PA",
        "quocGiaTen": "Panama"
    },
    {
        "id": 171,
        "quocGiaMa": "PG",
        "quocGiaTen": "Papua New Guinea"
    },
    {
        "id": 172,
        "quocGiaMa": "PY",
        "quocGiaTen": "Paraguay"
    },
    {
        "id": 173,
        "quocGiaMa": "PE",
        "quocGiaTen": "Peru"
    },
    {
        "id": 174,
        "quocGiaMa": "PH",
        "quocGiaTen": "Philippines"
    },
    {
        "id": 175,
        "quocGiaMa": "PN",
        "quocGiaTen": "Pitcairn"
    },
    {
        "id": 176,
        "quocGiaMa": "PL",
        "quocGiaTen": "Poland"
    },
    {
        "id": 177,
        "quocGiaMa": "PT",
        "quocGiaTen": "Portugal"
    },
    {
        "id": 178,
        "quocGiaMa": "PR",
        "quocGiaTen": "Puerto Rico"
    },
    {
        "id": 179,
        "quocGiaMa": "QA",
        "quocGiaTen": "Qatar"
    },
    {
        "id": 180,
        "quocGiaMa": "RE",
        "quocGiaTen": "Réunion"
    },
    {
        "id": 181,
        "quocGiaMa": "RO",
        "quocGiaTen": "Romania"
    },
    {
        "id": 182,
        "quocGiaMa": "RU",
        "quocGiaTen": "Russian Federation"
    },
    {
        "id": 183,
        "quocGiaMa": "RW",
        "quocGiaTen": "Rwanda"
    },
    {
        "id": 184,
        "quocGiaMa": "BL",
        "quocGiaTen": "Saint-Barthélemy"
    },
    {
        "id": 185,
        "quocGiaMa": "SH",
        "quocGiaTen": "Saint Helena"
    },
    {
        "id": 186,
        "quocGiaMa": "KN",
        "quocGiaTen": "Saint Kitts and Nevis"
    },
    {
        "id": 187,
        "quocGiaMa": "LC",
        "quocGiaTen": "Saint Lucia"
    },
    {
        "id": 188,
        "quocGiaMa": "MF",
        "quocGiaTen": "Saint-Martin (French part)"
    },
    {
        "id": 189,
        "quocGiaMa": "PM",
        "quocGiaTen": "Saint Pierre and Miquelon"
    },
    {
        "id": 190,
        "quocGiaMa": "VC",
        "quocGiaTen": "Saint Vincent and Grenadines"
    },
    {
        "id": 191,
        "quocGiaMa": "WS",
        "quocGiaTen": "Samoa"
    },
    {
        "id": 192,
        "quocGiaMa": "SM",
        "quocGiaTen": "San Marino"
    },
    {
        "id": 193,
        "quocGiaMa": "ST",
        "quocGiaTen": "Sao Tome and Principe"
    },
    {
        "id": 194,
        "quocGiaMa": "SA",
        "quocGiaTen": "Saudi Arabia"
    },
    {
        "id": 195,
        "quocGiaMa": "SN",
        "quocGiaTen": "Senegal"
    },
    {
        "id": 196,
        "quocGiaMa": "RS",
        "quocGiaTen": "Serbia"
    },
    {
        "id": 197,
        "quocGiaMa": "SC",
        "quocGiaTen": "Seychelles"
    },
    {
        "id": 198,
        "quocGiaMa": "SL",
        "quocGiaTen": "Sierra Leone"
    },
    {
        "id": 199,
        "quocGiaMa": "SG",
        "quocGiaTen": "Singapore"
    },
    {
        "id": 200,
        "quocGiaMa": "SK",
        "quocGiaTen": "Slovakia"
    },
    {
        "id": 201,
        "quocGiaMa": "SI",
        "quocGiaTen": "Slovenia"
    },
    {
        "id": 202,
        "quocGiaMa": "SB",
        "quocGiaTen": "Solomon Islands"
    },
    {
        "id": 203,
        "quocGiaMa": "SO",
        "quocGiaTen": "Somalia"
    },
    {
        "id": 204,
        "quocGiaMa": "ZA",
        "quocGiaTen": "South Africa"
    },
    {
        "id": 205,
        "quocGiaMa": "GS",
        "quocGiaTen": "South Georgia and the South Sandwich Islands"
    },
    {
        "id": 206,
        "quocGiaMa": "SS",
        "quocGiaTen": "South Sudan"
    },
    {
        "id": 207,
        "quocGiaMa": "ES",
        "quocGiaTen": "Spain"
    },
    {
        "id": 208,
        "quocGiaMa": "LK",
        "quocGiaTen": "Sri Lanka"
    },
    {
        "id": 209,
        "quocGiaMa": "SD",
        "quocGiaTen": "Sudan"
    },
    {
        "id": 210,
        "quocGiaMa": "SR",
        "quocGiaTen": "Suriname *"
    },
    {
        "id": 211,
        "quocGiaMa": "SJ",
        "quocGiaTen": "Svalbard and Jan Mayen Islands"
    },
    {
        "id": 212,
        "quocGiaMa": "SZ",
        "quocGiaTen": "Swaziland"
    },
    {
        "id": 213,
        "quocGiaMa": "SE",
        "quocGiaTen": "Sweden"
    },
    {
        "id": 214,
        "quocGiaMa": "CH",
        "quocGiaTen": "Switzerland"
    },
    {
        "id": 215,
        "quocGiaMa": "SY",
        "quocGiaTen": "Syrian Arab Republic (Syria)"
    },
    {
        "id": 216,
        "quocGiaMa": "TW",
        "quocGiaTen": "Taiwan"
    },
    {
        "id": 217,
        "quocGiaMa": "TJ",
        "quocGiaTen": "Tajikistan"
    },
    {
        "id": 218,
        "quocGiaMa": "TZ",
        "quocGiaTen": "Tanzania"
    },
    {
        "id": 219,
        "quocGiaMa": "TH",
        "quocGiaTen": "Thailand"
    },
    {
        "id": 220,
        "quocGiaMa": "TL",
        "quocGiaTen": "Timor-Leste"
    },
    {
        "id": 221,
        "quocGiaMa": "TG",
        "quocGiaTen": "Togo"
    },
    {
        "id": 222,
        "quocGiaMa": "TK",
        "quocGiaTen": "Tokelau"
    },
    {
        "id": 223,
        "quocGiaMa": "TO",
        "quocGiaTen": "Tonga"
    },
    {
        "id": 224,
        "quocGiaMa": "TT",
        "quocGiaTen": "Trinidad and Tobago"
    },
    {
        "id": 225,
        "quocGiaMa": "TN",
        "quocGiaTen": "Tunisia"
    },
    {
        "id": 226,
        "quocGiaMa": "TR",
        "quocGiaTen": "Turkey"
    },
    {
        "id": 227,
        "quocGiaMa": "TM",
        "quocGiaTen": "Turkmenistan"
    },
    {
        "id": 228,
        "quocGiaMa": "TC",
        "quocGiaTen": "Turks and Caicos Islands"
    },
    {
        "id": 229,
        "quocGiaMa": "TV",
        "quocGiaTen": "Tuvalu"
    },
    {
        "id": 230,
        "quocGiaMa": "UG",
        "quocGiaTen": "Uganda"
    },
    {
        "id": 231,
        "quocGiaMa": "UA",
        "quocGiaTen": "Ukraine"
    },
    {
        "id": 232,
        "quocGiaMa": "AE",
        "quocGiaTen": "United Arab Emirates"
    },
    {
        "id": 233,
        "quocGiaMa": "GB",
        "quocGiaTen": "United Kingdom"
    },
    {
        "id": 234,
        "quocGiaMa": "US",
        "quocGiaTen": "United States of America"
    },
    {
        "id": 235,
        "quocGiaMa": "UM",
        "quocGiaTen": "United States Minor Outlying Islands"
    },
    {
        "id": 236,
        "quocGiaMa": "UY",
        "quocGiaTen": "Uruguay"
    },
    {
        "id": 237,
        "quocGiaMa": "UZ",
        "quocGiaTen": "Uzbekistan"
    },
    {
        "id": 238,
        "quocGiaMa": "VU",
        "quocGiaTen": "Vanuatu"
    },
    {
        "id": 239,
        "quocGiaMa": "VE",
        "quocGiaTen": "Venezuela (Bolivarian Republic of)"
    },
    {
        "id": 240,
        "quocGiaMa": "VI",
        "quocGiaTen": "Virgin Islands, US"
    },
    {
        "id": 241,
        "quocGiaMa": "WF",
        "quocGiaTen": "Wallis and Futuna Islands"
    },
    {
        "id": 242,
        "quocGiaMa": "EH",
        "quocGiaTen": "Western Sahara"
    },
    {
        "id": 243,
        "quocGiaMa": "YE",
        "quocGiaTen": "Yemen"
    },
    {
        "id": 244,
        "quocGiaMa": "ZM",
        "quocGiaTen": "Zambia"
    },
    {
        "id": 245,
        "quocGiaMa": "ZW",
        "quocGiaTen": "Zimbabwe"
    }
]''';