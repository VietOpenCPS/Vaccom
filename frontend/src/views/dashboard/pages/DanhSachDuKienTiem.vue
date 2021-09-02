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
        title="DANH SÁCH DỰ KIẾN TIÊM"
        class="px-5 py-3"
      >
      
        <v-btn color="#0072bc" small class="mx-0" @click.stop="showTimKiem" style="position: absolute; right: 40px; top: 15px;">
          <v-icon left size="20">
            mdi-filter-plus-outline
          </v-icon>
          Lọc danh sách
        </v-btn>
        <v-card-text v-if="showAdvanceSearch">
          <v-row>
            <v-col
              cols="12"
              md="6"
              class="pb-0"
            >
              <v-autocomplete
                hide-no-data
                :items="danhSachLichTiemChung"
                v-model="lichTiemChungFilter"
                item-text="diaDiemTiemChung"
                item-value="id"
                outlined
                placeholder="Lịch tiêm chủng"
                dense
                hide-details="auto"
                clearable
            ></v-autocomplete>
            </v-col>
            <v-col
              cols="12"
              md="6"
              class="pb-0"
            >
              <v-autocomplete
                hide-no-data
                :items="danhSachCaTiemChung"
                v-model="lichTiemChungFilter"
                item-text="diaDiemTiemChung"
                item-value="id"
                outlined
                placeholder="Lịch tiêm chủng"
                dense
                hide-details="auto"
                clearable
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
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span>
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span>
            </span>
            <v-btn color="#0072bc" small class="mx-2" @click.stop="exportDanhSach" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-export
              </v-icon>
              Xuất danh sách
            </v-btn>
            <v-btn color="orange" small class="mx-2 mr-4" @click.stop="addPhieuHenTiem('add')">
              Gọi tiêm
            </v-btn>
            <v-btn v-if="userLogin['coSoYTeId']" small color="#0072bc" class="mx-0" @click.stop="addPhieuHenTiem('add')">
                <v-icon left size="22">
                  mdi-plus
                </v-icon>
                Bổ sung danh sách dự kiến
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
          >
            <template v-slot:item.index="{ item, index }">
              <span>{{ (page + 1) * itemsPerPage - itemsPerPage + index + 1 }}</span>
            </template>
            <template v-slot:item.tongSoMuiTiem="{ item, index }">
                {{item.tongSoMuiTiem}}
                <p class="mb-2">Số ca: {{ item.soCaTiem}} </p>
                <p class="mb-2">Số mũi/ca: {{ item.soMuiMotCa}} </p>
            </template>
            <template v-slot:item.loaiThuocTiem="{ item, index }">
                <p class="mb-2">Loại: {{ item.loaiThuocTiem}} </p>
                <p class="mb-2">Nơi sx: {{ item.noiSanXuat}} </p>
                <p class="mb-2">Hạn dùng: {{ item.hanSuDung}} </p>
            </template>
            <template v-slot:item.bacSiKham="{ item, index }">
                <p class="mb-2">{{ item.bacSiKham}} </p>
                <p class="mb-2">{{ item.soDienThoai}} </p>
            </template>
            <template v-slot:item.tinhTrangLich="{ item, index }">
                <p class="mb-2" v-if="item.tinhTrangLich == 0"> Chưa mở danh sách</p>
                <p class="mb-2" v-if="item.tinhTrangLich == 1"> Đang mở danh sách</p>
                <p class="mb-2" v-if="item.tinhTrangLich == 2"> Đã đóng kết thúc</p>
            </template>
            <template v-slot:item.action="{ item }">
              <div class="text-center">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="goiTiem(item)" color="orange" small flat class="" v-bind="attrs" v-on="on">
                      Gọi tiêm
                    </v-btn>
                  </template>
                  <span>Thực hiện gọi tiêm</span>
                </v-tooltip>
              </div>
            </template>
          </v-data-table>
          <pagination v-if="pageCount" :pageInput="page" :pageCount="pageCount" @tiny:change-page="changePage"></pagination>
        </v-card-text>
      </base-material-card>
      <v-dialog
        max-width="1000"
        v-model="dialogPhieuDuKienTiem"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title v-if="typeAction === 'add'">Thêm phiếu dự kiến tiêm</v-toolbar-title>
            <v-toolbar-title v-else>Cập nhật thông tin</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialogPhieuDuKienTiem = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="mt-5">
            <v-form
              ref="formPhieuHenTiem"
              v-model="validFormAdd"
              lazy-validation
            >
                <v-layout wrap>
                    <v-text-field
                        class="flex xs12 md12 px-2"
                        v-model="phieuHenTiem.ID"
                        outlined
                        label="ID"
                        placeholder="ID"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-autocomplete
                        class="flex xs12 md6 px-2"
                        hide-no-data
                        :items="danhSachLichTiemChung"
                        v-model="phieuHenTiem.LichTiemChung_ID"
                        @change="getCaTiem"
                        item-text="diaDiemTiemChung"
                        item-value="id"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Lịch Tiêm Chủng"
                        dense
                    ></v-autocomplete>
                    <v-autocomplete
                        class="flex xs12 md6 px-2"
                        hide-no-data
                        :items="danhSachCaTiemChung"
                        v-model="phieuHenTiem.CaTiemChung_ID"
                        item-text="name"
                        item-value="value"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Ca tiêm chủng"
                        dense
                    ></v-autocomplete>
                    <v-autocomplete
                        class="flex xs12 px-2"
                        hide-no-data
                        :items="danhSachNguoiTiemChung"
                        v-model="phieuHenTiem.NguoiTiemChung_ID"
                        item-text="name"
                        item-value="value"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Người tiêm chủng"
                        dense
                    ></v-autocomplete>
                    <v-text-field
                      label="Lần tiêm"
                      class="flex xs12 md12 px-2"
                      v-model="phieuHenTiem.LanTiem"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Ngày hẹn tiêm"
                      class="flex xs12 md6 px-2"
                      v-model="ngayHenTiemFomarted"
                      placeholder="dd/mm/yyyy, ddmmyyyy"
                      @blur="formatNgayHenTiem"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Giờ hẹn tiêm"
                      class="flex xs12 md6 px-2"
                      v-model="phieuHenTiem.GioHenTiem"
                      placeholder="hh:mm, hhmm"
                      dense
                      outlined
                    ></v-text-field>
                    <!-- <v-autocomplete
                        class="flex xs12 md6"
                        hide-no-data
                        :items="danhSachTinhTrangXacNhan"
                        v-model="phieuHenTiem.TinhTrangXacNhan"
                        item-text="name"
                        item-value="value"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Ca tiêm chủng"
                        dense
                    ></v-autocomplete> -->
                    <v-textarea
                      label="Ghi chú lý do"
                      class="flex xs12 md12 px-2"
                      v-model="phieuHenTiem.TinhTrangXacNhan"
                      dense
                      outlined
                    ></v-textarea>
                    <v-textarea
                      label="Thông tin check-in"
                      class="flex xs12 md12 px-2"
                      v-model="phieuHenTiem.ThongTinCheckin"
                      dense
                      outlined
                    ></v-textarea>
                    <v-text-field
                      label="Giờ được tiêm"
                      class="flex xs12 md6 px-2"
                      v-model="phieuHenTiem.GioDuocTiem"
                      placeholder="hh:mm, hhmm"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Ngày check-in"
                      class="flex xs12 md6 px-2"
                      v-model="ngayCheckinFomarted"
                      placeholder="dd/mm/yyyy, ddmmyyyy"
                      @blur="formatNgayCheckin"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="TrieuChungSauTiem"
                      class="flex xs12 md6 px-2"
                      v-model="phieuHenTiem.TrieuChungSauTiem"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Điều trị triệu chứng"
                      class="flex xs12 md6 px-2"
                      v-model="phieuHenTiem.DieuTriTrieuChung"
                      dense
                      outlined
                    ></v-text-field>
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
            <v-btn class="mr-2" color="#0072bc" :loading="loading" :disabled="loading" @click="submitPhieuHenTiem">
              <v-icon left>
                mdi-content-save
              </v-icon>
              <span v-if="typeAction === 'add'">Thêm mới</span>
              <span v-else>Cập nhật</span>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
    
  </div>
  
</template>

<script>
  const danhSachPhieu = [
    {
      ID: '1',
      MaQR: '',
      LichTiemChung_ID: 'LichTiem1',
      CaTiemChung_ID: 'CaTiem1',
      NguoiTiemChung_ID: 'NguoiTiem1',
      LanTiem: 1,
      NgayHenTiem: '20/11/20201',
      GioHenTiem: '09:00',
      TinhTrangXacNhan: 0,
      GhiChuLyDo: 'Chưa đc tiêm bala bala',
      NgayCheckin: '20/11/20201',
      ThongTinCheckin: 'Thông tin bala bala bala',
      GioDuocTiem: '10:00',
      TrieuChungSauTiem: 'Đau đầu sổ mũi',
      DieuTriTrieuChung: 'ok ok',
    },
    {
      ID: '2',
      MaQR: '',
      LichTiemChung_ID: 'LichTiem2',
      CaTiemChung_ID: 'CaTiem2',
      NguoiTiemChung_ID: 'NguoiTiem2',
      LanTiem: 1,
      NgayHenTiem: '20/11/20201',
      GioHenTiem: '09:00',
      TinhTrangXacNhan: 0,
      GhiChuLyDo: 'Chưa đc tiêm bala bala',
      NgayCheckin: '20/11/20201',
      ThongTinCheckin: 'Thông tin bala bala bala',
      GioDuocTiem: '10:00',
      TrieuChungSauTiem: 'Đau đầu sổ mũi',
      DieuTriTrieuChung: 'ok ok',
    },
    {
      ID: '3',
      MaQR: '',
      LichTiemChung_ID: 'LichTiem3',
      CaTiemChung_ID: 'CaTiem3',
      NguoiTiemChung_ID: 'NguoiTiem3',
      LanTiem: 1,
      NgayHenTiem: '20/11/20201',
      GioHenTiem: '09:00',
      TinhTrangXacNhan: 0,
      GhiChuLyDo: 'Chưa đc tiêm bala bala',
      NgayCheckin: '20/11/20201',
      ThongTinCheckin: 'Thông tin bala bala bala',
      GioDuocTiem: '10:00',
      TrieuChungSauTiem: 'Đau đầu sổ mũi',
      DieuTriTrieuChung: 'ok ok',
    },
    {
      ID: '4',
      MaQR: '',
      LichTiemChung_ID: 'LichTiem4',
      CaTiemChung_ID: 'CaTiem4',
      NguoiTiemChung_ID: 'NguoiTiem4',
      LanTiem: 1,
      NgayHenTiem: '20/11/20201',
      GioHenTiem: '09:00',
      TinhTrangXacNhan: 0,
      GhiChuLyDo: 'Chưa đc tiêm bala bala',
      NgayCheckin: '20/11/20201',
      ThongTinCheckin: 'Thông tin bala bala bala',
      GioDuocTiem: '10:00',
      TrieuChungSauTiem: 'Đau đầu sổ mũi',
      DieuTriTrieuChung: 'ok ok',
    }
  ]
  import axios from 'axios'
  import Vue from 'vue'
  // import Search from './FormTimKiem.vue'
  import Pagination from './Pagination'
  export default {
    name: 'DanhSachDuKienTiem',
    components: {
    // 'tim-kiem': Search,
    'pagination': Pagination
    },
    data () {
      return {
        selected: [],
        validFormAdd: true,
        loading: false,
        loadingData: false,
        lichTiemChungFilter: '',
        caTiemChungFilter: '',
        totalItem: 0,
        page: 0,
        pageCount: 0,
        itemsPerPage: 15,
        typeAction: 'add',
        dialogPhieuDuKienTiem: false,
        items: [],
        listCoSoYTe: [],
        coSoYTe: '',
        ngayHenTiemFomarted: '',
        ngayCheckinFomarted: '',
        endDateFormatted: '',
        expDateFormatted: '',
        danhSachLichTiemChung: [],
        danhSachCaTiemChung: [],
        danhSachNguoiTiemChung: [],
        danhSachTinhTrangXacNhan: [
          {name: 'Dự kiến', value: 0},
          {name: 'Hẹn gọi chờ xác nhận', value: 1},
          {name: 'Hẹn đã xác nhận', value: 2},
          {name: 'Đã tiêm xong, đã check-in', value: 3},
          {name: 'Chưa được tiêm', value: 4},
          {name: 'Xác nhận không đến', value: 5},
        ],
        phieuHenTiem: {
          ID: '',
          MaQR: '',
          LichTiemChung_ID: '',
          CaTiemChung_ID: '',
          NguoiTiemChung_ID: '',
          LanTiem: '',
          NgayHenTiem: '',
          GioHenTiem: '',
          TinhTrangXacNhan: '',
          GhiChuLyDo: '',
          NgayCheckin: '',
          ThongTinCheckin: '',
          GioDuocTiem: '',
          TrieuChungSauTiem: '',
          DieuTriTrieuChung: '',
        },
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
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
            value: 'NguoiTiemChung_ID'
          },
          {
            sortable: false,
            text: 'Ngày hẹn tiêm',
            align: 'left',
            value: 'NgayHenTiem'
          },
          {
            sortable: false,
            text: 'Giờ hẹn tiêm',
            align: 'left',
            value: 'GioHenTiem'
          },
          {
            sortable: false,
            text: 'Thao tác',
            align: 'left',
            value: 'action'
          }
        ],
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 2)
      let isSigned = this.$store.getters.getIsSigned
      if (!isSigned) {
        vm.$router.push({ path: '/login?redirect=/pages/lich-tiem-chung' })
        return
      }
      vm.getDanhSachPhieuDuKienTiem(0)
      vm.getLichTiem()
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      editPhieuHenTiem (item) {
        let vm = this
        vm.typeAction = 'update'
        vm.phieuHenTiemUpdate = item
        vm.phieuHenTiem.MaDot = item.maDot
        vm.ngayHenTiemFomarted = item.ngayBatDau
        vm.endDateFormatted = item.ngayKetThuc
        vm.phieuHenTiem.DiaDiemTiemChung = item.diaDiemTiemChung
        vm.phieuHenTiem.LoaiThuocTiem = item.loaiThuocTiem
        vm.phieuHenTiem.NoiSanXuat = item.noiSanXuat
        vm.phieuHenTiem.SoLoThuoc = item.soLoThuoc
        vm.expDateFormatted = item.hanSuDung
        vm.phieuHenTiem.SoCaTiem = item.soCaTiem
        vm.phieuHenTiem.SoMuiMotCa = item.soMuiMotCa
        vm.phieuHenTiem.TongSoMuiTiem = item.tongSoMuiTiem
        vm.phieuHenTiem.TinhTrangLich = item.tinhTrangLich
        vm.phieuHenTiem.BacSiKham = item.bacSiKham
        vm.phieuHenTiem.SoDienThoai = item.soDienThoai
        vm.dialogPhieuDuKienTiem = true
      },
      formatNgayHenTiem () {
        let vm = this
        let lengthDate = String(vm.ngayHenTiemFomarted).trim().length
        let splitDate = String(vm.ngayHenTiemFomarted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.ngayHenTiemFomarted = vm.translateDate(vm.ngayHenTiemFomarted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.ngayHenTiemFomarted)
          vm.ngayHenTiemFomarted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.ngayHenTiemFomarted = ''
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
      getDanhSachPhieuDuKienTiem (pageIn) {
        let vm = this
        let param = {
          headers: {
          },
          params: {
            lichtiemchungid: vm.lichTiemChungFilter,
            catiemchungid: vm.lichTiemChungFilter,
            page: pageIn,
            size: vm.itemsPerPage,
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        axios.get('/rest/v1/app/get/phieuhentiem/4', param).then(function (response) {
          let serializable = response.data
          if(serializable) {
            vm.items = serializable.hasOwnProperty('data') ? serializable.data : []
            vm.totalItem = serializable.hasOwnProperty('total') ? serializable.total : 0
            vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
          }
        }).catch(function (error) {
          vm.items = []
          vm.totalItem = danhSachPhieu.length
          vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
        })
        vm.items = danhSachPhieu
        vm.totalItem = danhSachPhieu.length
        vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
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
            vm.lichTiemChungFilter = danhSachLichTiemChung[0].hasOwnProperty('id') ? danhSachLichTiemChung[0]['id'] : ''
            th
          }
        })
      },
      getCaTiem () {
        let vm = this
        let filter = {
          lichtiemchungid: this.phieuHenTiem.LichTiemChung_ID,
          // diabancosoid:  
          page: -1,
          size: -1,
        }
        vm.$store.dispatch('getCaTiem', filter).then(function (result) {
          vm.danhSachCaTiemChung = result.hasOwnProperty('data') ? result.data : []
        })
      },
      changePage (config) {
        let vm = this
        vm.page = config.page
        vm.getDanhSachPhieuDuKienTiem(config.page)
      },
      addPhieuHenTiem (type, user) {
        let vm = this
        vm.typeAction = type
        vm.dialogPhieuDuKienTiem = true
        if (type === 'add') {
          setTimeout(function () {
            vm.$refs.formPhieuHenTiem.reset()
            vm.$refs.formPhieuHenTiem.resetValidation()
          }, 200)
        }         
      },
      formatDataInput () {
        let vm = this
        try {
          vm.phieuHenTiem.CoSoYTe_ID = vm.userLogin['coSoYTeId']
          // let splitNgayBatDau = String(vm.ngayHenTiemFomarted).split('/')
          vm.phieuHenTiem.NgayBatDau = vm.ngayHenTiemFomarted
          // let splitNgayKetThuc = String(vm.endDateFormatted).split('/')
          vm.phieuHenTiem.NgayKetThuc = vm.endDateFormatted
          // let splitHanSuDung = String(vm.expDateFormatted).split('/')
          vm.phieuHenTiem.HanSuDung = vm.expDateFormatted
          vm.phieuHenTiem.SoCaTiem = Number(vm.phieuHenTiem.SoCaTiem)
          vm.phieuHenTiem.SoMuiMotCa = Number(vm.phieuHenTiem.SoMuiMotCa)
          vm.phieuHenTiem.TongSoMuiTiem = vm.phieuHenTiem.SoCaTiem*vm.phieuHenTiem.SoMuiMotCa
          console.log('phieuHenTiem', vm.phieuHenTiem)
        } catch (error) {
        }
      },
      submitPhieuHenTiem () {
        let vm = this
        if (vm.$refs.formPhieuHenTiem.validate()) {
          vm.formatDataInput()
          if (vm.typeAction === 'add') {
            let filter = {
              data: vm.phieuHenTiem
            }
            vm.loading = true
            vm.$store.dispatch('addLichTiem', filter).then(userCredential => {
              vm.loading = false
              vm.dialogPhieuDuKienTiem = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm phiếu dự kiến tiêm thành công',
                color: 'success',
              })
              vm.page = 0
              vm.getDanhSachPhieuDuKienTiem(0)
            })
            .catch((error) => {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm lịch tiêm không thất bại',
                color: 'error',
              })
            });
          } else {
            let filter = {
              id: vm.phieuHenTiemUpdate['id'],
              data: vm.phieuHenTiem
            }
            vm.loading = true
            vm.$store.dispatch('updateLichTiem', filter).then(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.dialogPhieuDuKienTiem = false
              vm.getDanhSachPhieuDuKienTiem(0)
            }).catch(function () {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thất bại',
                color: 'error',
              })
            })
          }
          
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
        vm.dialogPhieuDuKienTiem = false
      },
      goiTiem () {
  
      },
      showTimKiem () {
        let vm = this
        vm.showAdvanceSearch = true
      },
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

