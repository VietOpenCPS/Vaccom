<template>
  <div>
    <v-card-text class="px-0">
        <v-row v-if="!form">
          <v-col
            cols="12"
            :md="!typeGoiTiem ? 4 : 8"
            class="pb-0"
          >
            <v-text-field
              label="Họ tên"
              v-model="dataSearch['HoVaTen']"
              outlined
              placeholder="Họ và tên"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
          >
            <v-text-field
              label="Số CMND/ CCCD"
              v-model="dataSearch['CMTCCCD']"
              outlined
              placeholder="Số CMND/ CCCD"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="!typeGoiTiem"
          >
            <v-autocomplete
              :items="listDoiTuong"
              label="Nhóm đối tượng"
              v-model="dataSearch['NhomDoiTuong']"
              item-text="doiTuongMoTa"
              item-value="id"
              hide-no-data
              outlined
              dense
              hide-details="auto"
              clearable
            >
            <template v-slot:selection="data">
              <span>{{data.item.id}}. {{ data.item.doiTuongMoTa }}</span>
            </template>
            <template v-slot:item="data">
              <span>{{data.item.id}}. {{ data.item.doiTuongMoTa }}</span>
            </template>
            </v-autocomplete>
          </v-col>
          
          <v-col
            cols="12"
            md="4"
            class="pb-0"
          >
            <v-autocomplete
                hide-no-data
                :items="listTinhThanh"
                v-model="tinhThanh"
                item-text="tinhThanhTen"
                item-value="tinhThanhMa"
                clearable
                outlined
                label="Tỉnh/ Thành phố"
                dense
                hide-details="auto"
            ></v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
          >
            <v-autocomplete
                hide-no-data
                :items="listQuanHuyen"
                v-model="quanHuyen"
                item-text="quanHuyenTen"
                item-value="quanHuyenMa"
                clearable
                outlined
                label="Quận/ Huyện"
                dense
                hide-details="auto"
            ></v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
          >
            <v-autocomplete
                hide-no-data
                :items="listXaPhuong"
                v-model="xaPhuong"
                item-text="phuongXaTen"
                item-value="phuongXaMa"
                clearable
                outlined
                label="Phường/ Xã"
                dense
                hide-details="auto"
            ></v-autocomplete>
          </v-col>
          
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="!typeGoiTiem && !addGoiTiem"
          >
            <v-autocomplete
              hide-no-data
              :items="listCoSoYTe"
              v-model="coSoYTe"
              item-text="tenCoSo"
              item-value="maCoSo"
              outlined
              label="Cơ sở y tế"
              dense
              hide-details="auto"
              clearable
          ></v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="!typeGoiTiem && !addGoiTiem"
          >
            <v-autocomplete
              hide-no-data
              :items="listDiaBan"
              v-model="dataSearch['DiaBanCoSo_ID']"
              item-text="tenDiaBan"
              item-value="id"
              outlined
              label="Tổ dân phố, khóm ấp, thôn bản…"
              dense
              hide-details="auto"
              clearable
          ></v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="typeGoiTiem || addGoiTiem"
          >
            <v-text-field
              label="Địa chỉ nơi ở"
              v-model="dataSearch['diachinoio']"
              outlined
              placeholder="Địa chỉ nơi ở"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="typeGoiTiem"
          >
            <v-autocomplete
              :items="listSoMuiTiem"
              label="Số mũi tiêm"
              v-model="dataSearch['soMuiTiem']"
              item-text="name"
              item-value="value"
              hide-no-data
              outlined
              dense
              hide-details="auto"
              clearable
            >
            </v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="typeGoiTiem || addGoiTiem"
          >
            <v-text-field
              label="Loại vaccine tiêm"
              v-model="dataSearch['loaiThuocTiem']"
              outlined
              placeholder="Loại vaccine tiêm"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="!typeGoiTiem && !addGoiTiem"
          >
            <v-text-field
              label="Ngày đăng ký tiêm"
              v-model="ngayDangKyFormatted"
              outlined
              placeholder="dd/mm/yyyy, ddmmyyyy"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="!typeGoiTiem && !addGoiTiem"
          >
            <v-autocomplete
              hide-no-data
              :items="listTrangThaiTrung"
              v-model="dataSearch['KiemTraTrung']"
              item-text="name"
              item-value="value"
              outlined
              label="Kiểm tra trùng lặp"
              dense
              hide-details="auto"
              clearable
          ></v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="4"
            class="pb-0"
            v-if="addLichTiem"
          >
            <v-checkbox
              color="#0072bc"
              class="mt-0 checkboxCmt d-inline-block"
              v-model="dataSearch['isDatTieuChuan']"
            >
              <template v-slot:label>
                <span style="font-weight: 500;color: #0072bc">ĐĂNG KÝ ĐẠT TIÊU CHUẨN</span>
              </template>
            </v-checkbox>
          </v-col>
          
        </v-row>
        <v-row v-if="form === 'giaydiduong'">
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-text-field
              label="Họ tên"
              v-model="dataSearch['HoVaTen']"
              outlined
              placeholder="Họ và tên"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-text-field
              label="Số CMND/ CCCD"
              v-model="dataSearch['CMTCCCD']"
              outlined
              placeholder="Số CMND/ CCCD"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-text-field
              label="Đơn vị/ công ty làm việc"
              v-model="dataSearch['NoiCtTenCoQuan']"
              outlined
              placeholder="Số CMND/ CCCD"
              dense
              clearable
              hide-details="auto"
            ></v-text-field>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-autocomplete
                class="flex xs12 md12"
                hide-no-data
                :items="listUyBanNhanDan"
                v-model="dataSearch['UyBanNhanDanID']"
                item-text="tenCoQuan"
                item-value="id"
                outlined
                label="Ủy ban nhân dân cấp giấy"
                dense
                clearable
                hide-details="auto"
            ></v-autocomplete>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="pb-0"
          >
            <v-autocomplete
                class="flex xs12 md12"
                hide-no-data
                :items="listTrangThaiGuiTinNhan"
                v-model="dataSearch['statusGuiTinNhan']"
                item-text="name"
                item-value="value"
                outlined
                label="Trạng thái gửi tin nhắn"
                dense
                clearable
                hide-details="auto"
            ></v-autocomplete>
          </v-col>
        </v-row>
        <v-row class="justify-end">
          <v-btn color="red" small class="mt-3 mx-3" @click="cancelSearch">
              <v-icon left size="20">
              mdi-close
              </v-icon>
              Thoát
          </v-btn>
          <v-btn color="#0072bc" small class="mt-3 mx-3" @click="$emit('trigger-search', dataSearch)">
              <v-icon left size="20">
              mdi-content-save
              </v-icon>
              Lọc danh sách
          </v-btn>
        </v-row>
    </v-card-text>
    
  </div>
  
</template>

<script>
  export default {
    name: 'Search',
    props: ['form','diabanid','typegiaydiduong','typeGoiTiem', 'addGoiTiem', 'loaiVaccine', 'addLichTiem'],
    data () {
      return {
        listUyBanNhanDan: [],
        listDoiTuong: [],
        listDiaBan: [],
        listCoSoYTe: [],
        listTrangThaiGuiTinNhan: [
          {name: 'Chưa gửi', value: 0},
          {name: 'Gửi Zalo thành công', value: 1},
          {name: 'Gửi SMS thành công', value: 2},
          {name: 'Gửi thất bại', value: 3}
        ],
        listTinhThanh: [],
        tinhThanh: '',
        listQuanHuyen: [],
        quanHuyen: '',
        listXaPhuong: [],
        xaPhuong: '',
        coSoYTe: '',
        dataSearch: {
          HoVaTen: '',
          CMTCCCD: '',
          NhomDoiTuong: '',
          DiaBanCoSo_ID: '',
          CoSoYTe_Ma: '',
          NgayDangKi: '',
          KiemTraTrung: -1,
          TinhThanh_Ma: '',
          QuanHuyen_Ma: '',
          PhuongXa_Ma: '',
          statusGuiTinNhan: -1,
          soMuiTiem: '',
          loaiThuocTiem: '',
          diachinoio: '',
          isDatTieuChuan: false
        },
        listSoMuiTiem: [
          {name: '1', value: 1},
          {name: '2', value: 2}
        ],
        listTrangThaiTrung: [
          {name: 'Chưa kiểm tra', value: 0},
          {name: 'Đã kiểm tra, không trùng lặp', value: 1},
          {name: 'Đã kiểm tra, bị trùng lặp', value: 2}
        ],
        ngayDangKyFormatted: ''
      }
    },
    created () {
      let vm = this
      vm.getCoSoYTe()
      vm.getDiaBanCoSo()
      vm.getNhomDoiTuong()
      vm.getUyBanNhanDan()
      vm.getTinhThanh()
      try {
        vm.dataSearch.loaiThuocTiem = vm.loaiVaccine
      } catch (error) {
      }
    },
    watch: {
      loaiVaccine (val) {
        this.dataSearch.loaiThuocTiem = val
      },
      coSoYTe (val) {
        this.dataSearch.CoSoYTe_Ma = val
        this.getDiaBanCoSo(val)
      },
      tinhThanh (val) {
        this.dataSearch.TinhThanh_Ma = val
        this.getQuanHuyen(val)
      },
      quanHuyen (val) {
        this.dataSearch.QuanHuyen_Ma = val
        this.getXaPhuong(val)
      },
      xaPhuong (val) {
        this.dataSearch.PhuongXa_Ma = val
        if (this.tinhThanh && this.quanHuyen) {
          this.getDiaBanCoSo('', 'tinhThanh')
        }
      },
      // ngayDangKyFormatted (val) {
      //   let splitNgayDangKy = String(val).split('/')
      //   this.dataSearch.NgayDangKi = splitNgayDangKy[2] + splitNgayDangKy[1] + splitNgayDangKy[0]
      // }
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
      userLogin () {
        return this.$store.getters.getPermistion
      }
    },
    methods: {
      getDataOutPut () {
        let vm = this
        return vm.dataSearch
      },
      cancelSearch () {
        let vm = this
        vm.dataSearch = {
          HoVaTen: '',
          CMTCCCD: '',
          NhomDoiTuong: '',
          DiaBanCoSo_ID: '',
          CoSoYTe_Ma: '',
          NgayDangKi: '',
          KiemTraTrung: -1,
          TinhThanh_Ma: '',
          QuanHuyen_Ma: '',
          PhuongXa_Ma: '',
          statusGuiTinNhan: -1
        },
        vm.$emit('trigger-cancel', vm.dataSearch)
      },
      getUyBanNhanDan () {
        let vm = this
        let filter = {
          page: 0,
          size: 100
        }
        vm.$store.dispatch('getUyBanNhanDan', filter).then(function (result) {
          vm.listUyBanNhanDan = result.hasOwnProperty('data') ? result.data : []
        })
      },
      getDiaBanCoSo (val, tinhThanh) {
        let vm = this
        let filter = {
          id: -1
        }
        if (tinhThanh) {
          filter = {
            id: -1,
            tinhthanhma: vm.tinhThanh,
            quanhuyenma: vm.quanHuyen,
            phuongxama: vm.xaPhuong,
            tinhthanhten: '',
            quanhuyenten: '',
            phuongxaten: '',
          }
        }
        if (val) {
          let obj = vm.listCoSoYTe.find(function (item) {
            return item.maCoSo == val
          })
          filter = {
            id: obj['id']
          }
        }
        vm.$store.dispatch('getDiaBanCoSo', filter).then(function (result) {
          if (result.hasOwnProperty('data') && result.data.length) {
            vm.listDiaBan = [{tenDiaBan: "Chưa gán địa bàn", id: 0}].concat(result.data)
          } else {
            vm.listDiaBan = []
          }
          if (!val && !tinhThanh) {
            try {
              let data = localStorage.getItem('user')
              let diaBanUser = JSON.parse(data)['diaBanCoSoId']
              let obj = vm.listDiaBan.find(function (item) {
                return item.id == diaBanUser
              })
              if (obj && obj['tinhThanhMa']) {
                vm.dataSearch['DiaBanCoSo_ID'] = obj['id']
                vm.tinhThanh= obj['tinhThanhMa']
                vm.quanHuyen = obj['quanHuyenMa']
                vm.xaPhuong = obj['phuongXaMa']
              } else {
                vm.dataSearch['DiaBanCoSo_ID'] = ''
              }
            } catch (error) {
              vm.dataSearch['DiaBanCoSo_ID'] = ''
            }
          } else {
            try {
              let data = localStorage.getItem('user')
              let diaBanUser = JSON.parse(data)['diaBanCoSoId']
              let obj = vm.listDiaBan.find(function (item) {
                return item.id == diaBanUser
              })
              if (obj && obj['tinhThanhMa']) {
                vm.dataSearch['DiaBanCoSo_ID'] = obj['id']
                // vm.tinhThanh= obj['tinhThanhMa']
                // vm.quanHuyen = obj['quanHuyenMa']
                // vm.xaPhuong = obj['phuongXaMa']
              } else {
                vm.dataSearch['DiaBanCoSo_ID'] = ''
              }
            } catch (error) {
              vm.dataSearch['DiaBanCoSo_ID'] = ''
            }
          }
        })
      },
      getCoSoYTe () {
        let vm = this
        let filter = {
        }
        vm.$store.dispatch('getCoSoYTe', filter).then(function (result) {
          vm.listCoSoYTe = result ? result : []
        })
      },
      getNhomDoiTuong () {
        let vm = this
        let filter = {
        }
        vm.$store.dispatch('getNhomDoiTuong', filter).then(function (result) {
          vm.listDoiTuong = result ? result : []
        })
      },
      getTinhThanh () {
        let vm = this
        let filter = {
        }
        vm.$store.dispatch('getDanhMucTinhThanh', filter).then(function (result) {
          vm.listTinhThanh = result ? result : []
          vm.tinhThanh = '01'
          vm.quanHuyen = '004'
          if (vm.tinhThanh && vm.listTinhThanh.length) {
            vm.getQuanHuyen(vm.tinhThanh)
          }
        }).catch(function(error){
          if (error && error.response && error.response.status == 401) {
            vm.$router.push({ path: '/login' })
          }
        })
      },
      getQuanHuyen (code) {
        let vm = this
        if (!code) {
          return
        }
        let obj = vm.listTinhThanh.find(function (item) {
          return item.tinhThanhMa == code
        })
        if (obj) {
          let filter = {
            idParent: obj['id']
          }
          vm.$store.dispatch('getDanhMucQuanHuyen', filter).then(function (result) {
            vm.listQuanHuyen = result ? result : []
            if (vm.quanHuyen) {
              vm.getXaPhuong(vm.quanHuyen)
            }
          })
        }
      },
      getXaPhuong (code) {
        let vm = this
        if (!code) {
          return
        }
        let obj = vm.listQuanHuyen.find(function (item) {
          return item.quanHuyenMa == code
        })
        if (!obj) {
          return
        }
        let filter = {
          idParent: obj['id']
        }
        vm.$store.dispatch('getDanhMucXaPhuong', filter).then(function (result) {
          vm.listXaPhuong = result ? result : []
        })
      },
      formatNgayDangKy () {
        let vm = this
        let lengthDate = String(vm.ngayDangKyFormatted).trim().length
        let splitDate = String(vm.ngayDangKyFormatted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.ngayDangKyFormatted = vm.translateDate(vm.ngayDangKyFormatted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.ngayDangKyFormatted)
          vm.ngayDangKyFormatted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.ngayDangKyFormatted = ''
        }
      },
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

