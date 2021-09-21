<template>
  <div>
    <v-container
      id="users"
      fluid
      tag="section"
      :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'padding-top: 75px' : ''"
    >
      <base-material-card
        style="margin-top: 20px"
        icon="mdi-clipboard-text"
        title="DANH SÁCH GỌI TIÊM"
        class="px-5 py-3"
      >
        <v-btn color="#0072bc" small class="mx-0" @click.stop="showTimKiem" style="position: absolute; right: 40px; top: 15px;">
          <v-icon left size="20">
            mdi-filter-plus-outline
          </v-icon>
          Lọc danh sách
        </v-btn>
        <v-card-text v-if="showAdvanceSearch">
          <tim-kiem ref="timkiem" typeGoiTiem='true' v-on:trigger-search="searchDangKyTiem" v-on:trigger-cancel="cancelSearchDangKyTiem"></tim-kiem>
        </v-card-text>
        <v-card-text>
          <v-row>
            <v-col
              cols="12"
              class="pb-0 mb-3"
            >
              <div><span style="color: red">(*) </span>Chọn lịch tiêm</div>
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              md="12"
              class="pb-0"
            >
              <v-autocomplete
                hide-no-data
                :items="danhSachLichTiemChung"
                v-model="lichTiemChungFilter"
                item-text="diaDiemTiemChung"
                item-value="id"
                outlined
                label="Lịch tiêm chủng"
                dense
                hide-details="auto"
                clearable
            >
              <template v-slot:selection="data">
                <span>{{ data.item.diaDiemTiemChung }} (Ngày tiêm: {{data.item.ngayBatDau}})</span>
              </template>
              <template v-slot:item="data">
                <span>{{ data.item.diaDiemTiemChung }} (Ngày tiêm: {{data.item.ngayBatDau}})</span>
              </template>
            </v-autocomplete>
            </v-col>
            <!-- <v-col
              cols="12"
              md="6"
              class="pb-0"
            >
              <v-autocomplete
                  hide-no-data
                  :items="danhSachCaTiemChungFilte"
                  v-model="caTiemChungFilter"
                  item-text="gioHenTiem"
                  item-value="id"
                  outlined
                  label="Ca tiêm chủng"
                  dense
                  hide-details="auto"
                  clearable
              >
                <template v-slot:selection="data">
                  <span>{{data.item.gioHenTiem}} - {{data.item.ngayHenTiem}}</span>
                </template>
                <template v-slot:item="data">
                  <span>{{data.item.gioHenTiem}} - {{data.item.ngayHenTiem}}</span>
                </template>
              </v-autocomplete>
            </v-col> -->
            <v-col
              cols="12"
              md="12"
              class="pb-0 mt-2"
            >
              <v-autocomplete
                  hide-no-data
                  :items="danhSachTinhTrangXacNhan"
                  v-model="trangThaiFilter"
                  item-text="name"
                  item-value="value"
                  outlined
                  label="Trạng thái phiếu hẹn"
                  dense
                  hide-details="auto"
                  clearable
              >
              </v-autocomplete>
            </v-col>
          </v-row>
          <!-- <v-row class="justify-end">
            <v-btn color="red" small class="mt-3 mx-3" @click="cancelSearch">
                <v-icon left size="20">
                mdi-close
                </v-icon>
                Thoát
            </v-btn>
            <v-btn color="#0072bc" small class="mt-3 mx-3" @click="search">
                <v-icon left size="20">
                mdi-content-save
                </v-icon>
                Lọc danh sách
            </v-btn>
          </v-row> -->
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0 pt-5'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span>
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span>
            </span>
            <!-- <v-btn v-if="selected.length" color="green" small class="mx-2 mr-4" @click.stop="xacNhanTinhTrangPhieuHen(selected, 2, 'multiple')">
              Xác nhận lịch hẹn
            </v-btn>
            <v-btn v-if="selected.length" color="blue" small class="mx-2 mr-4" @click.stop="xacNhanTinhTrangPhieuHen(selected, 2, 'multiple')">
              Check-in
            </v-btn>
            <v-btn v-if="selected.length" color="amber" small class="mx-2 mr-4" @click.stop="xacNhanTinhTrangPhieuHen(selected, 2, 'multiple')">
              Chưa được tiêm
            </v-btn>
            <v-btn v-if="selected.length" color="red" small class="mx-2 mr-4" @click.stop="xacNhanTinhTrangPhieuHen(selected, 2, 'multiple')">
              Xác nhận không đến
            </v-btn>
            <v-btn v-if="selected.length" color="amber" small class="mx-2 mr-4" @click.stop="xacNhanTinhTrangPhieuHen(selected, 2, 'multiple')">
              In phiếu hẹn xác nhận
            </v-btn>
            <v-btn color="#0072bc" small class="mr-0" @click.stop="exportDanhSach" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-export
              </v-icon>
              Xuất danh sách
            </v-btn> -->
            <v-btn v-if="trangThaiFilter == 0" color="#0072bc" small class="mr-0" @click.stop="guiThongBao" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-export
              </v-icon>
              Gửi thông báo hẹn tiêm
            </v-btn>
          </div>
          <v-data-table
            v-model="selected"
            show-select
            :headers="headers"
            :items="items"
            hide-default-footer
            class="elevation-1"
            no-data-text="Không có"
            :loading="loadingData"
            loading-text="Đang tải... "
            :items-per-page="itemsPerPage"
          >
            <template v-slot:item.index="{ item, index }">
              <span>{{ (page + 1) * itemsPerPage - itemsPerPage + index + 1 }}</span>
            </template>
            <template v-slot:item.tinhTrangXacNhan="{ item }">
                <span :class="getColorTrangThai(item.tinhTrangXacNhan)"> {{ getTextTrangThai(item.tinhTrangXacNhan) }}</span>
            </template>
            <template v-slot:item.action="{ item }">
              <div class="text-center">
                <v-tooltip v-if="item.tinhTrangXacNhan === 0" top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="deletePhieuHen(item.idPhieu)" color="red" icon text class="mx-2" v-bind="attrs" v-on="on">
                      <v-icon>
                        mdi-delete
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>Xóa phiếu hẹn</span>
                </v-tooltip>
                <v-tooltip v-if="item.tinhTrangXacNhan === 1" top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="xacNhanTinhTrangPhieuHen(item.idPhieu, 2, 'only')" color="green" icon text class="mx-2" v-bind="attrs" v-on="on">
                      <v-icon>
                        mdi-account-check
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>Thực hiện xác nhận lịch hẹn</span>
                </v-tooltip>
                <v-tooltip v-if="item.tinhTrangXacNhan === 2" top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="xacNhanTinhTrangPhieuHen(item.idPhieu, 3, 'only')" color="blue" text icon class="mx-2" v-bind="attrs" v-on="on">
                      <v-icon>
                        mdi-account-check
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>Thực hiện check-in lịch hẹn</span>
                </v-tooltip>
                <v-tooltip v-if="item.tinhTrangXacNhan === 1" top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="xacNhanTinhTrangPhieuHen(item.idPhieu, 6, 'only')" color="red" icon text class="mx-2" v-bind="attrs" v-on="on">
                      <v-icon>
                        mdi-account-cancel-outline
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>Thực hiện xác nhận không đến</span>
                </v-tooltip>
                <v-tooltip v-if="item.tinhTrangXacNhan === 2" top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn color="amber" icon text class="mx-2" v-bind="attrs" v-on="on">
                      <v-icon>
                          mdi-file-document-edit-outline
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>In phiếu xác nhận hẹn gọi</span>
                </v-tooltip>
                <v-tooltip v-if="item.tinhTrangXacNhan === 3" top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="xacNhanTinhTrangPhieuHen(item.idPhieu, 5, 'only')" color="red" text icon class="mx-2" v-bind="attrs" v-on="on">
                      <v-icon>
                        mdi-account-cancel
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>Thực hiện hoãn tiêm</span>
                </v-tooltip>
                <v-tooltip v-if="item.tinhTrangXacNhan === 3" top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="addMuiTiemChung(item)" color="indigo" text icon class="mx-2" v-bind="attrs" v-on="on">
                      <v-icon>
                        mdi-file-check
                      </v-icon>
                    </v-btn>
                  </template>
                  <span>Thực hiện nhập kết quả tiêm</span>
                </v-tooltip>

              </div>
            </template>
          </v-data-table>
          <pagination v-if="pageCount" :pageInput="page" :pageCount="pageCount" @tiny:change-page="changePage"></pagination>
        </v-card-text>
      </base-material-card>
      <v-dialog
        max-width="1000"
        v-model="dialogMuiTiem"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title v-if="typeAction === 'add'">Thông tin kết quả tiêm</v-toolbar-title>
            <v-toolbar-title v-else>Cập nhật thông tin</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialogMuiTiem = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="mt-5">
            <v-form
              ref="formMuiTiemChung"
              v-model="validFormAdd"
              lazy-validation
            >
                <v-layout wrap>
                    <v-text-field
                        class="flex xs12 md6 px-2"
                        v-model="muiTiemChung.HoVaTen"
                        outlined
                        label="Họ và tên"
                        placeholder="Họ và tên"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                      label="Ngày sinh"
                      class="flex xs12 md6 px-2"
                      v-model="muiTiemChung.NgaySinh"
                      placeholder="dd/mm/yyyy, ddmmyyyy"
                      v-mask="'##/##/####'"
                      dense
                      outlined
                      required
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md12 px-2"
                        v-model="muiTiemChung.CMTCCCD"
                        outlined
                        label="CMT/CCCD"
                        placeholder="CMT/CCCD"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md12 px-2"
                        v-model="muiTiemChung.LanTiem"
                        outlined
                        label="Lần tiêm"
                        placeholder=""
                        type="number"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md12 px-2"
                        v-model="muiTiemChung.NgayTiemChung"
                        outlined
                        label="Ngày tiêm chủng"
                        placeholder="dd/mm/yyyy, ddmmyyyy"
                        v-mask="'##/##/####'"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                      label="Giờ tiêm chủng"
                      class="flex xs12 md6 px-2"
                      v-model="muiTiemChung.GioHenTiem"
                      placeholder="hh:mm, hhmm"
                      v-mask="'##:##'"
                      dense
                      outlined
                      required
                    ></v-text-field>
                    <v-text-field
                      label="Địa điểm tiêm chủng"
                      class="flex xs12 md6 px-2"
                      v-model="muiTiemChung.DiaDiemTiemChung"
                      placeholder="Địa điểm tiêm chủng"
                      dense
                      outlined
                      required
                    ></v-text-field>
                    <v-text-field
                      label="Loại thuốc tiêm"
                      class="flex xs12 md6 px-2"
                      v-model="muiTiemChung.LoaiThuocTiem"
                      placeholder="Loại thuốc tiêm"
                      dense
                      outlined
                      required
                    ></v-text-field>
                    <!-- <v-text-field
                      label="Nơi sản xuất"
                      class="flex xs12 md6 px-2"
                      v-model="muiTiemChung.NoiSanXuat"
                      placeholder="Nơi sản xuất"
                      dense
                      outlined
                      required
                    ></v-text-field>
                    <v-text-field
                      label="Số lô thuốc"
                      class="flex xs12 md6 px-2"
                      v-model="muiTiemChung.SoLoThuoc"
                      placeholder="Số lô thuốc"
                      dense
                      outlined
                      required
                    ></v-text-field>
                    <v-text-field
                      label="Hạn sử dụng"
                      class="flex xs12 md6 px-2"
                      v-model="muiTiemChung.HanSuDung"
                      placeholder="dd/mm/yyyy, ddmmyyyy"
                      v-mask="'##/##/####'"
                      dense
                      outlined
                      required
                    ></v-text-field> -->
                </v-layout>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-end">
            
            <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="cancelPhieuHenTiem">
              <v-icon left>
                mdi-close
              </v-icon>
              Thoát
            </v-btn>
            <v-btn class="mr-2" color="#0072bc" :loading="loading" :disabled="loading" @click="submitMuiTiemChung">
              <v-icon left>
                mdi-send
              </v-icon>
              <span>Gửi đi</span>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
    
  </div>
  
</template>

<script>
  import axios from 'axios'
  import Vue from 'vue'
  import Pagination from './Pagination'
  import Search from './FormTimKiem.vue'
  export default {
    name: 'DanhSachGoiTiem',
    components: {
    'tim-kiem': Search,
    'pagination': Pagination
    },
    data () {
      return {
        processingAction: false,
        selected: [],
        validFormAdd: true,
        loading: false,
        loadingData: false,
        lichTiemChungFilter: '',
        caTiemChungFilter: '',
        totalItem: 0,
        page: 0,
        pageCount: 0,
        itemsPerPage: 50,
        typeAction: 'add',
        dialogMuiTiem: false,
        items: [],
        coSoYTe: '',
        ngayCheckinFomarted: '',
        trangThaiFilter: 0,
        danhSachLichTiemChung: [],
        danhSachCaTiemChungFilte: [],
        danhSachCaTiemChung: [],
        danhSachNguoiTiemChung: [],
        danhSachTinhTrangXacNhan: [
          {name: 'Chờ gửi thông báo', value: 0},
          {name: 'Chờ xác nhận', value: 1},
          {name: 'Đã xác nhận', value: 2},
          {name: 'Đã check-in', value: 3},
          {name: 'Đã tiêm xong', value: 4},
          {name: 'Chưa được tiêm', value: 5},
          {name: 'Xác nhận không đến', value: 6}
        ],
        muiTiemChung: {
          CongDan_ID: '',
          HoVaTen: '',
          NgaySinh: '',
          CMTCCCD: '',
          CoSoYTe_Ma: '',
          CoSoYTe_Ten: '',
          LanTiem: '',
          NgayTiemChung: '',
          GioTiemChung: '',
          DiaDiemTiemChung: '',
          LoaiThuocTiem: '',
          NoiSanXuat: '',
          SoLoThuoc: '',
          HanSuDung: '',
          CoSoYTe_Id: '',
          MaPhieuHen: ''
        },
        showAdvanceSearch: false,
        phieuHenTiemUpdate: '',
        required: [
          (value) => {
            if(String(value).trim()){
                return true
              } else {
                return 'Thông tin bắt buộc'
              } 
          }
        ],
        headers: [
          {
            sortable: false,
            text: 'STT',
            align: 'center',
            value: 'index'
          },
          {
            sortable: false,
            text: 'Người tiêm chủng',
            align: 'center',
            value: 'hoVaTen'
          },
          {
            sortable: false,
            text: 'Ngày hẹn tiêm',
            align: 'left',
            value: 'ngayHenTiem'
          },
          {
            sortable: false,
            text: 'Giờ hẹn tiêm',
            align: 'left',
            value: 'gioHenTiem'
          },
          {
            sortable: false,
            text: 'Trạng thái',
            align: 'left',
            value: 'tinhTrangXacNhan'
          },
          {
            sortable: false,
            text: 'Thao tác',
            align: 'center',
            value: 'action'
          }
        ],
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 2)
      let query = vm.$router.history.current.query
      console.log('query', query)
      if (query.hasOwnProperty('lichTiemId') && query.lichTiemId) {
        vm.lichTiemChungFilter = Number(query.lichTiemId)
      }
      vm.getLichTiem()
      // vm.getDanhSachPhieuHenTiem(0)
    },
    watch: {
      lichTiemChungFilter (val) {
        if (val) {
          this.getDanhSachPhieuHenTiem(0)
        }
      },
      // caTiemChungFilter (val) {
      //   if (val) {
      //     this.getDanhSachPhieuHenTiem(0)
      //   }
      // },
      trangThaiFilter (val) {
        this.getDanhSachPhieuHenTiem(0)
      }
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      formatNgayHenTiem () {
        let vm = this
        let lengthDate = String(vm.muiTiemChung.NgayHenTiem).trim().length
        let splitDate = String(vm.muiTiemChung.NgayHenTiem).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.muiTiemChung.NgayHenTiem = vm.translateDate(vm.muiTiemChung.NgayHenTiem)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.muiTiemChung.NgayHenTiem)
          vm.muiTiemChung.NgayHenTiem = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.muiTiemChung.NgayHenTiem = ''
        }
      },
      formatNgayCheckin () {
        let vm = this
        let lengthDate = String(vm.ngayCheckinFomarted).trim().length
        let splitDate = String(vm.ngayCheckinFomarted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.ngayCheckinFomarted = vm.translateDate(vm.ngayCheckinFomarted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.ngayCheckinFomarted)
          vm.ngayCheckinFomarted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.ngayCheckinFomarted = ''
        }
      },
      translateDate (date) {
        if (!date) return null
        const [day, month, year] = date.split('/')
        return `${day.padStart(2, '0')}/${month.padStart(2, '0')}/${year}`
      },
      searchDangKyTiem (data) {
        let vm = this
        vm.searchAll = true
        console.log('dataSearch', data)
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachPhieuHenTiem(0, data)
      },
      cancelSearchDangKyTiem (data) {
        let vm = this
        vm.showAdvanceSearch = false
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachPhieuHenTiem(0, data)
      },
      getDanhSachPhieuHenTiem (pageIn, dataSearch) {
        let vm = this
        if (vm.lichTiemChungFilter) {
          let param = {
            headers: {
            },
            params: {
                page: pageIn,
                size: vm.itemsPerPage
            }
          }
          try {
          if (Vue.$cookies.get('Token')) {
              param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
          } catch (error) {
          }
          let dataPost = {
              lichTiemChungId: vm.lichTiemChungFilter ? vm.lichTiemChungFilter : '',
              cmtcccd: dataSearch && dataSearch['CMTCCCD'] ? dataSearch['CMTCCCD'] : '',
              hovaten: dataSearch && dataSearch['HoVaTen'] ? dataSearch['HoVaTen'] : '',
              diabancosoid: dataSearch && dataSearch.hasOwnProperty('DiaBanCoSo_ID') ? dataSearch['DiaBanCoSo_ID'] : '',
              tinhthanhma: dataSearch && dataSearch['TinhThanh_Ma'] ? dataSearch['TinhThanh_Ma'] : '',
              quanhuyenma: dataSearch && dataSearch['QuanHuyen_Ma'] ? dataSearch['QuanHuyen_Ma'] : '',
              phuongxama: dataSearch && dataSearch['PhuongXa_Ma'] ? dataSearch['PhuongXa_Ma'] : '',
              listtinhtrangxacnhan: [vm.trangThaiFilter],
              tinhtrangdangki: vm.trangThaiFilter == 4 ? 3 : 4
          }
          vm.loading = true
          axios.post('/rest/v1/app/get/search-nguoitiemchung', dataPost, param).then(function (response) {
              vm.loading = false
              if (response.data.data && response.data.data.length) {
                vm.items = vm.handleArrPhieu(response.data.data)
                // vm.items = response.data.data
                vm.totalItem = response.data.hasOwnProperty('total') ? response.data.total : 0
                vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
              } else {
                vm.items = []
                vm.totalItem = 0
                vm.pageCount = 0
              }
              
          }).catch(function (error) {
              vm.loading = false
              vm.items = []
              vm.totalItem = 0
              vm.pageCount = 0
          })
        }
      },
      guiThongBao () {
        let vm = this
        if (vm.lichTiemChungFilter) {
          let param = {
            headers: {
            },
            params: {
            }
          }
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
          let textConfirm = 'Bạn có chắc chắn muốn gửi thông báo hẹn tiêm?'
          let x = confirm(textConfirm)
          if (x) {
            vm.processingAction = true
            axios.get('/rest/v1/app/update/phieuhentiem/guiThongBao/' + vm.lichTiemChungFilter, param).then(function (response) {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Đã gửi thông báo thành công',
                color: 'success',
              })
              vm.processingAction = false   
            }).catch(function (error) {
              vm.processingAction = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Gửi thông báo thất bại',
                color: 'error',
              })
            })
          }
        }
        
      },
      getLichTiem () {
        let vm = this
        let filter = {
          page: -1,
          size: -1,
        }
        vm.$store.dispatch('getLichTiem', filter).then(function (result) {
          vm.danhSachLichTiemChung = result.hasOwnProperty('data') ? result.data : []
          if (vm.danhSachLichTiemChung.length) {
            // vm.danhSachLichTiemChung = vm.danhSachLichTiemChung.filter(function (item) {
            //   return item.tinhTrangLich == 0
            // })
            // vm.getCaTiem('created')
          }
        })
      },
      getCaTiem (type) {
        let vm = this
        try {
          // const user = JSON.parse(localStorage.getItem('user'))
          let filter = {
            lichtiemchungid: type === 'search' || type === 'created' ? vm.lichTiemChungFilter : this.muiTiemChung.LichTiemChung_ID,
            diabancosoid:  '',
            page: 0,
            size: 100,
          }
          vm.$store.dispatch('getCaTiem', filter).then(function (result) {
            if (type === 'search'  || type === 'created') {
              vm.danhSachCaTiemChungFilte = result.hasOwnProperty('data') ? result.data : []
            } else {
              vm.danhSachCaTiemChung = result.hasOwnProperty('data') ? result.data : []
            }
            
          })
        } catch (err) {

        }

      },
      getAllNguoiTiemChung () {
        let vm = this
        try {
          let param = {
            headers: {
            },
            params: {
              page: 0,
              size: 100,
            }
          }
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
          axios.get('/rest/v1/app/get/nguoitiemchung/all', param).then(function (response) {
            if (response.data && response.data.data) {
              vm.danhSachNguoiTiemChung = response.data.data
            }
            
          }).catch(function (error) {});
        } catch (e) {

        }
      },
      changePage (config) {
        let vm = this
        vm.page = config.page
        vm.getDanhSachPhieuHenTiem(config.page)
      },
      addMuiTiemChung (item) {
        let vm = this
        let lichTiemSelected = vm.danhSachLichTiemChung.find(function (item) {
          return item.id == vm.lichTiemChungFilter
        })
        vm.muiTiemChung.CongDan_ID = item.congDanID
        vm.muiTiemChung.HoVaTen = item.hoVaTen
        vm.muiTiemChung.CMTCCCD = item.cmtcccd
        vm.muiTiemChung.NgaySinh = item.ngaySinh
        vm.muiTiemChung.CoSoYTe_Id = item.coSoYTeId
        vm.muiTiemChung.CoSoYTe_Ma = item.coSoYTeMa
        vm.muiTiemChung.CoSoYTe_Ten = item.CoSoYTe_Ten
        vm.muiTiemChung.LanTiem = item.lanTiem
        vm.muiTiemChung.NgayTiemChung = item.ngayHenTiem
        vm.muiTiemChung.GioTiemChung = item.gioHenTiem
        vm.muiTiemChung.DiaDiemTiemChung = lichTiemSelected.diaDiemTiemChung
        vm.muiTiemChung.LoaiThuocTiem = lichTiemSelected.loaiThuocTiem,
        vm.muiTiemChung.MaPhieuHen = item.idPhieu
        vm.muiTiemChung.NoiSanXuat = ''
        vm.muiTiemChung.SoLoThuoc = ''
        vm.muiTiemChung.HanSuDung = ''
        vm.dialogMuiTiem = true

      },
      submitMuiTiemChung () {
        let vm = this
        if (vm.$refs.formMuiTiemChung.validate()) {
          let param = {
            headers: {
            },
            params: {
            }
          }
          try {
          if (Vue.$cookies.get('Token')) {
              param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
          } catch (error) {
          }
          let dataPost = vm.muiTiemChung
          vm.loading = true
          axios.post('/rest/v1/app/add/muitiemchung', dataPost, param).then(function (response) {
          vm.loading = false
          vm.dialogMuiTiem = false
          vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Thông tin tiêm chủng thêm thành công',
              color: 'success',
          })
            vm.page = 0
            vm.getDanhSachPhieuHenTiem(0)
          }).catch(function (error) {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Thông tin tiêm chủng thêm thất bại',
              color: 'error',
              })
          });
          
        }
      },
      deletePhieuHenTiem (item) {
        let vm = this
        let textConfirm = 'Bạn có chắc chắn muốn xóa lịch tiêm này'
        let x = confirm(textConfirm)
        if (x) {
          vm.$store.dispatch('deletePhieuHenTiem', item).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thành công',
              color: 'success',
            })
            vm.getCaTiem(0, vm.uid)
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thất bại',
              color: 'error',
            })
          })
        }
      },
      cancelPhieuHenTiem () {
        let vm = this
        vm.dialogMuiTiem = false
      },
      xacNhanTinhTrangPhieuHen (item, tinhTrang, type) {
        let vm = this
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = {
          TinhTrangXacNhan: tinhTrang,
          ids: ''
        }
        if (type === 'only') {
          dataPost.ids = String(item)
        }
        vm.loading = true
        axios.put('/rest/v1/app/update/phieuhentiem/tinhtrangxacnhan', dataPost, param).then(function (response) {
          vm.loading = false
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Thành công',
            color: 'success',
          })
          vm.getDanhSachPhieuHenTiem(0)
        }).catch(function (error) {
            vm.loading = false
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Thất bại',
              color: 'error',
            })
        });
      },
      deletePhieuHen (id) {
        let vm = this
        let textConfirm = 'Bạn có chắc chắn muốn xóa phiếu hẹn này'
        let x = confirm(textConfirm)
        if (x) {
          let param = {
            headers: {
            },
            params: {
            }
          }
          try {
            if (Vue.$cookies.get('Token')) {
              param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
            }
          } catch (error) {
          }
          vm.loading = true
          axios.delete('/rest/v1/app/delete/phieuhentiem/' + id, param).then(function (response) {
            vm.loading = false
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thành công',
              color: 'success',
            })
            vm.getDanhSachPhieuHenTiem(0)
          }).catch(function (error) {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Xóa thất bại',
                color: 'error',
              })
          })
        }
      },
      showTimKiem () {
        let vm = this
        vm.showAdvanceSearch = true
      },
      cancelSearch () {
        this.showAdvanceSearch = false
      },
      search() {
        this.getDanhSachPhieuHenTiem(0)
      },
      getTextTrangThai(trangThai) {
          switch (trangThai) {
              case 0:
                return 'Chờ gửi thông báo';
              case 1:
                  return 'Chờ xác nhận';
              case 2:
                  return 'Đã xác nhận'
              case 3:
                  return 'Đã check-in'
              case 4:
                  return 'Đã tiêm xong'
              case 5:
                  return 'Chưa được tiêm'
              case 6:
                  return 'Xác nhận không đến'              
          }
          return ''
      },
      getColorTrangThai (trangThai ) {
          switch (trangThai) {
              case 1:
                  return 'orange--text';
              case 2:
                  return 'green--text'
              case 3:
                  return 'blue--text'
              case 4:
                  return 'blue--text'
              case 5:
                  return 'red--text'
              case 6:
                  return 'grey--text'          
          }
          return ''
      },
      handleArrPhieu (arr) {
        let newArr = []
        arr.forEach(item => {
          if(item.phieuHenTiem.length > 0 ) {
            item.phieuHenTiem.forEach((item2, index) => {
              if (index === 0) {
                let obj = {
                  ...item2,
                  ...item,
                  idPhieu: item2.id
                }
                newArr.push(obj)
              }
            })
          }
        })
        return newArr 
      }
    }
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

