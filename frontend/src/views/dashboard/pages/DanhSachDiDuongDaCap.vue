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
        title="DANH SÁCH GIẤY ĐI ĐƯỜNG ĐÃ CẤP"
        class="px-5 py-3"
      >
        <v-btn color="#0072bc" small class="mx-0" @click.stop="showTimKiem" style="position: absolute; right: 40px; top: 15px;">
          <v-icon left size="20">
            mdi-filter-plus-outline
          </v-icon>
          Lọc danh sách
        </v-btn>
        <v-card-text v-if="showAdvanceSearch">
          <tim-kiem ref="timkiem" form="giaydiduong" v-on:trigger-search="searchGiayDiDuong" v-on:trigger-cancel="cancelSearchGiayDiDuong"></tim-kiem>
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex my-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </span>

            <!-- <v-btn color="#0072bc" small class="mx-0 mr-4" @click.stop="exportDanhSach" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-export
              </v-icon>
              Xuất danh sách
            </v-btn> -->
            <!-- translateStatusMultiple -->
            <v-btn color="red" small class="mx-0" @click.stop="showRutGiayDiDuong('multiple')" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-text-box-check-outline
              </v-icon>
              Rút giấy đi đường
            </v-btn>
            <!-- <v-btn color="orange" small class="mx-0" @click.stop="showRutGiayDiDuong('all')" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-text-box-check-outline
              </v-icon>
              Rút tất cả
            </v-btn> -->
            <input v-if="userLogin['role_name'] == 'QuanTriHeThong' || userLogin['role_name'] == 'CanBoUBND'" type="file" id="fileImport" @input="uploadFileImport($event)" style="display:none">
          </div>
          
          <v-data-table
            v-model="selected"
            show-select
            :headers="headers"
            :items="items"
            :items-per-page="itemsPerPage"
            hide-default-footer
            class="elevation-1"
            no-data-text="Không có"
            :loading="loadingData"
            loading-text="Đang tải... "
          >
            <template v-slot:header.lichLamViec="{ header }">
              <div>
                <div class="py-2 px-2" style="border-bottom: 1px solid #dedede;">Thời gian làm việc</div>
                <v-layout wrap>
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;">
                    <p class="py-2 mb-0 px-2">Các ngày</p>
                  </v-flex>
                  <v-flex class="xs12 md6">
                    <p class="py-2 mb-0 px-2">Thời gian</p>
                  </v-flex>
                </v-layout>
              </div>
            </template>
            <template v-slot:item.index="{ item, index }">
              <div>{{ (page+1) * itemsPerPage - itemsPerPage + index + 1 }}</div>
            </template>
            <template v-slot:item.hoVaTen="{ item, index }">
              <div>
                <p class="mb-0" style="font-weight: 500;">{{ item.hoVaTen}}</p>
              </div>
            </template>
            <template v-slot:item.cmtcccd="{ item, index }">
                <p class="mb-2">{{ item.cmtcccd }}</p>
            </template>
            <template v-slot:item.soDienThoai="{ item, index }">
                <p class="mb-2">{{ item.soDienThoai }}</p>
            </template>
            <template v-slot:item.noiODiaChi="{ item, index }">
                <p class="mb-2">{{ item.noiODiaChi}}, {{item.noiOPhuongXaTen}} - {{item.noiOQuanHuyenTen}} - {{item.noiOTinhThanhTen}}</p>
            </template>
            <template v-slot:item.noiCtDiaChi="{ item, index }">
                <p class="mb-2">{{ item.noiCtDiaChi }}, {{item.noiCtPhuongXaTen}} - {{item.noiCtQuanHuyenTen}} - {{item.noiCtTinhThanhTen}}</p>
            </template>
            <template v-slot:item.noiCtTenCoQuan="{ item, index }">
                <p class="mb-2">{{ item.noiCtTenCoQuan }}</p>
            </template>
            <template v-slot:item.lichLamViec="{ item, index }">
              <div style="width: 100%;min-width: 250px;height: 100%;">
                <v-layout wrap style="height: 100%;">
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;">
                    <p class="py-2 mb-0 pr-2" style="color:green;">{{translateLichNgay(item.lichLamViec)}}</p>
                  </v-flex>
                  <v-flex class="xs12 md6">
                    <p class="py-2 mb-0">Từ: <span style="color:green;">{{translateLichGio(item.lichLamViec, 'start')}}</span></p>
                    <p class="py-0 mb-0">Đến: <span style="color:blue;">{{translateLichGio(item.lichLamViec, 'end')}}</span></p>
                  </v-flex>
                </v-layout>
              </div>
            </template>
            <template v-slot:item.thoiHan="{ item, index }">
                <p class="mb-2">Từ ngày: <span style="color:green;">{{ item.ngayCap }}</span></p>
                <p class="mb-2" v-if="item.thoiHan">Đến ngày: <span style="color:blue;">{{ item.thoiHan }}</span></p>
            </template>
            <template v-slot:item.action="{ item }">
              <div style="width: 100px">
                <!-- <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="editRegistration(item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <span>Sửa thông tin</span>
                </v-tooltip> -->
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="showRutGiayDiDuong('one', item)" color="red" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-text-box-check-outline</v-icon>
                    </v-btn>
                  </template>
                  <span>Rút giấy đi đường</span>
                </v-tooltip>
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="xemGiayDiduong(item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-account-eye-outline</v-icon>
                    </v-btn>
                  </template>
                  <span>Xem giấy đi đường</span>
                </v-tooltip>
              </div>
              
            </template>
          </v-data-table>
          <pagination v-if="pageCount" :pageInput="page" :pageCount="pageCount" @tiny:change-page="changePage"></pagination>
        </v-card-text>
      </base-material-card>
      <v-dialog
        max-width="1000"
        v-model="dialog"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title >Rút giấy đi đường</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialog = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="mt-5">
            <v-form
              ref="formRut"
              v-model="validFormRut"
              lazy-validation
            >
                <v-layout wrap>
                  <v-textarea
                      class="flex xs12 md12"
                      v-model="lyDoRut"
                      outlined
                      label="Lý do rút giấy đi đường"
                      dense
                      clearable
                      rows="3"
                      :rules="required"
                      required
                  ></v-textarea>
                </v-layout>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-end">
            
            <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="dialog = false">
              <v-icon left>
                mdi-close
              </v-icon>
              Thoát
            </v-btn>
            <v-btn class="mr-2" color="#0072bc" :loading="loading" :disabled="loading" @click="submitTranslate">
              <v-icon left>
                mdi-content-save
              </v-icon>
              <span>Đồng ý</span>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog
        max-width="750"
        v-model="dialogGiayDiDuong"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title >THÔNG TIN GIẤY ĐI ĐƯỜNG</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialogGiayDiDuong = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="mx-0 my-0 py-0 px-0">
            <giay-di-duong :uid="zaloUid"></giay-di-duong>
          </v-card-text>
          <v-card-actions class="justify-end">
            
            <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="dialogGiayDiDuong = false">
              <v-icon left>
                mdi-close
              </v-icon>
              Thoát
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
    
  </div>
  
</template>

<script>
  import $ from 'jquery'
  import Search from './FormTimKiem.vue'
  import Pagination from './Pagination'
  import GiayDiDuong from './MauGiayDiDuong.vue'
  export default {
    name: 'Customers',
    components: {
    'tim-kiem': Search,
    'giay-di-duong': GiayDiDuong,
    'pagination': Pagination
    },
    data () {
      return {
        zaloUid: '',
        loading: false,
        loadingData: false,
        processingAction: false,
        listDaiLy: [],
        dailySelected: '',
        dialog: false,
        lastVisible: '',
        firstVisible: '',
        totalItem: 0,
        page: 0,
        pageCount: 0,
        itemsPerPage: 50,
        items: [],
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
        },
        showAdvanceSearch: false,
        selected: [],
        dataInputSearch: '',
        validFormRut: true,
        typeAction: '',
        giayDiDuongRut: '',
        lyDoRut: '',
        dataSearchOutPut: '',
        dialogGiayDiDuong: false,
        required: [
          v => !!v || 'Thông tin bắt buộc'
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
            text: 'Họ tên',
            align: 'left',
            value: 'hoVaTen'
          },
          {
            sortable: false,
            text: 'Số CMND/ CCCD',
            align: 'left',
            value: 'cmtcccd'
          },
          {
            sortable: false,
            text: 'Số điện thoại',
            align: 'left',
            value: 'soDienThoai'
          },
          {
            sortable: false,
            text: 'Địa chỉ nơi ở/ cư trú',
            align: 'left',
            value: 'noiODiaChi'
          },
          {
            sortable: false,
            text: 'Địa điểm làm việc',
            align: 'left',
            value: 'noiCtDiaChi'
          },
          {
            sortable: false,
            text: 'Đơn vị/ công ty làm việc',
            align: 'left',
            value: 'noiCtTenCoQuan'
          },
          {
            sortable: false,
            text: 'Lịch làm việc',
            align: 'center',
            value: 'lichLamViec',
            class: 'px-0'
          },
          {
            sortable: false,
            text: 'Thời hạn sử dụng',
            align: 'center',
            value: 'thoiHan'
          },
          {
            sortable: false,
            text: 'Ghi chú',
            align: 'center',
            value: 'ghiChu'
          },
          {
            sortable: false,
            text: 'Thao tác',
            align: 'center',
            value: 'action'
          },
        ],
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 3)
      let isSigned = this.$store.getters.getIsSigned
      vm.getDanhSachDaCap(0)
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
    },
    methods: {
      translateLichNgay (data) {
        try {
          let input = JSON.parse(data)
          let dataOut = ''
          let ngayTuan = input['ngayTuan']
          let ngayThang = input['ngayThang']
          if (ngayTuan && ngayTuan.length) {
            let ngayTuanString = ''
            ngayTuan.forEach(element => {
              let day = ''
              if (element == 0) {
                day = 'Chủ nhật, '
              } else {
                day = 'Thứ ' + element + ', '
              }
              ngayTuanString += day
            })
            ngayTuanString = ngayTuanString.trim().substring(0, ngayTuanString.trim().length - 1)
            dataOut += ngayTuanString
          }
          if (ngayThang && ngayThang.length) {
            dataOut = dataOut + ' - Các ngày: ' + ngayThang.toString().replace(/,/g, "; ")
          }
          return dataOut
          
        } catch (error) {
          return ''
        }
      },
      translateLichGio (data, type) {
        try {
          let input = JSON.parse(data)
          let timeStart = input['tuGio'] ? input['tuGio'].split(':')[0] + 'h' + input['tuGio'].split(':')[1] : ''
          let endStart = input['denGio'] ? input['denGio'].split(':')[0] + 'h' + input['denGio'].split(':')[1] : ''
          if (type === 'start') {
            return timeStart
          } else {
            return endStart
          }
        } catch (error) {
          return ''
        }
      },
      goToRegister () {
        let vm = this
        vm.$router.push({ path: '/pages/dang-ky-di-duong/0' })
      },
      searchGiayDiDuong (data) {
        let vm = this
        console.log('dataSearch', data)
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachDaCap(0, data)
      },
      cancelSearchGiayDiDuong (data) {
        let vm = this
        vm.showAdvanceSearch = false
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachDaCap(0, data)
      },
      showTimKiem () {
        let vm = this
        vm.showAdvanceSearch = true
      },
      getDanhSachDaCap (pageIn, dataSearch) {
        let vm = this
        vm.loadingData = true
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage,
          cmtcccd: dataSearch && dataSearch['CMTCCCD'] ? dataSearch['CMTCCCD'] : '',
          hoVaTen: dataSearch && dataSearch['HoVaTen'] ? dataSearch['HoVaTen'] : '',
          noiCtTenCoQuan: dataSearch && dataSearch['NoiCtTenCoQuan'] ? dataSearch['NoiCtTenCoQuan'] : '',
          uyBanNhanDanID: dataSearch && dataSearch.hasOwnProperty('UyBanNhanDanID') ? dataSearch['UyBanNhanDanID'] : '',
          status: 1,
          typeSearch: 'giaydiduong'
        }
        vm.$store.dispatch('getGiayDiDuong', filter).then(function(result) {
          vm.loadingData = false
          if (result) {
            vm.items = result.hasOwnProperty('data') ? result.data : []
            vm.totalItem = result.hasOwnProperty('total') ? result.total : 0
            vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
          } else {
            vm.items = []
            vm.totalItem = 0
          }
        }).catch(function () {
          vm.items = []
          vm.totalItem = 0
          vm.loadingData = false
        })
      },
      translateStatus (item) {
        let vm = this
        let filter = {
          data: Object.assign(item, {status: 2, ghiChu: vm.lyDoRut})
        }
        vm.$store.dispatch('duyetGiayDiDuong', filter).then(function (result) {
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Rút thành công',
            color: 'success',
          })
          vm.processingAction = false
          vm.getDanhSachDaCap(0)
          vm.dialog = false
        }).catch(function () {
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Rút không thành công',
            color: 'error',
          })
          vm.processingAction = false
        })
        
      },
      xemGiayDiduong (item) {
        let vm = this
        vm.zaloUid = item.maQR
        vm.dialogGiayDiDuong = true
      },
      showRutGiayDiDuong (type, item) {
        let vm = this
        vm.typeAction = type
        vm.lyDoRut = ''
        if (type === 'multiple') {
          if (vm.selected.length === 0) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Vui lòng chọn người muốn rút giấy đi đường',
              color: 'error',
            })
            return
          }
          vm.dialog = true
        } else if (type === 'all') {
          if (!vm.totalItem) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Không có giấy đi đường',
              color: 'error',
            })
            return
          } else {
            vm.dialog = true
          }
        } else {
          vm.giayDiDuongRut = item
          vm.dialog = true
        }
      },
      submitTranslate () {
        let vm = this
        if (vm.$refs.formRut.validate()) {
          if (vm.typeAction === 'multiple') {
            vm.translateStatusMultiple()
          } else if (vm.typeAction === 'all') {
            vm.translateStatusAll()
          } else {
            vm.translateStatus(vm.giayDiDuongRut)
          }
        }
      },
      translateStatusAll () {
        let vm = this
        if (vm.showAdvanceSearch) {
          vm.dataSearchOutPut = vm.$refs.timkiem.getDataOutPut()
          console.log('dataSearch', vm.dataSearchOutPut)     
        }
          
        let filter = {
          data: {
            // ids: arrIds,
            status: 2,
            ghiChu: vm.lyDoRut
          }
        }
        let textConfirm = 'Bạn có chắc chắn rút tất cả ' + vm.totalItem + ' giấy đi đường?'
        let x = confirm(textConfirm)
        if (x) {
          vm.$store.dispatch('duyetNhieuGiayDiDuong', filter).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Rút thành công',
              color: 'success',
            })
            vm.getDanhSachDaCap(0)
            vm.selected = []
            vm.dialog = false
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Rút thất bại',
              color: 'error',
            })
          })
        }
      },
      translateStatusMultiple () {
        let vm = this
        let arrIds = ''
        if (vm.selected.length === 0) {
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Vui lòng chọn người muốn rút giấy đi đường',
            color: 'error',
          })
          return
        }
        arrIds = vm.selected.map(function(item) {
          return item['id']
        })
          
        let filter = {
          data: {
            ids: arrIds,
            status: 2,
            ghiChu: vm.lyDoRut
          }
        }
        console.log('filter', filter)
        if (!filter['data']['ids'] || filter['data']['ids'].length == 0) {
          return
        }
        let textConfirm = 'Bạn có chắc chắn thực hiện thao tác này?'
        let x = confirm(textConfirm)
        if (x) {
          vm.$store.dispatch('duyetNhieuGiayDiDuong', filter).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Rút thành công',
              color: 'success',
            })
            vm.getDanhSachDaCap(0)
            vm.selected = []
            vm.dialog = false
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Rút thất bại',
              color: 'error',
            })
          })
        }
      },
      removeRegistrationStatus (item) {
        let vm = this
        let filter = {
          id: item.id,
          data: item
        }
        let textConfirm = 'Bạn có chắc chắn muốn xóa đăng ký'
        let x = confirm(textConfirm)
        if (x) {
          vm.$store.dispatch('removeGiayDiDuong', filter).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thành công',
              color: 'success',
            })
            vm.getDanhSachDaCap(0)
            vm.selected = []
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thất bại',
              color: 'error',
            })
          })
        }
      },
      removeRegistrationMultiple (item) {
        let vm = this
        let arrIds = ''
        if (item === 'multiple') {
          if (vm.selected.length === 0) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Vui lòng chọn người đăng ký muốn xóa',
              color: 'error',
            })
            return
          }
          arrIds = vm.selected.map(function(item) {
            return item['id']
          })
          
        }
        let filter = {
          data: {
            ids: item === 'multiple' ? arrIds : String(item.id)
          }
        }
        if (!filter['data']['ids'] || filter['data']['ids'].length == 0) {
          return
        }
        let textConfirm = 'Bạn có chắc chắn muốn xóa đăng ký'
        let x = confirm(textConfirm)
        if (x) {
          vm.$store.dispatch('removeMultipleGiayDiDuong', filter).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thành công',
              color: 'success',
            })
            vm.getDanhSachDaCap(0)
            vm.selected = []
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thất bại',
              color: 'error',
            })
          })
        }
      },
      pickFileImport () {
        document.getElementById('fileImport').value = ''
        document.getElementById('fileImport').click()
      },
      uploadFileImport () {
        let vm = this
        let files = $('#fileImport')[0].files
        let file = files[0]
        vm.processingAction = true
        let filter = {
          file: file,
          sheetAt:0,
          startCol:0,
          endCol:16,
          startRow:8,
          endRow:1000,
          table:'giaydiduong'
        }
        vm.$store.dispatch('importDanhSach', filter).then(function(result) {
          vm.processingAction = false
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Import danh sách thành công',
            color: 'success',
          })
        }).catch(function () {
          vm.processingAction = false
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Import thất bại',
            color: 'error',
          })
        })
      },
      exportDanhSach () {
        let vm = this
        vm.processingAction = true
        let filter = {
          typeList: 'DanhSachTiemMoi',
          data: {
            page: -1,
            size: -1,
            tinhtrangdangky: 0,
            cmtcccd: vm.dataInputSearch && vm.dataInputSearch['CMTCCCD'] ? vm.dataInputSearch['CMTCCCD'] : '',
            nhomdoituong: vm.dataInputSearch && vm.dataInputSearch['NhomDoiTuong'] ? vm.dataInputSearch['NhomDoiTuong'] : '',
            ngaydangki: vm.dataInputSearch && vm.dataInputSearch['NgayDangKi'] ? vm.dataInputSearch['NgayDangKi'] : '',
            hovaten: vm.dataInputSearch && vm.dataInputSearch['HoVaTen'] ? vm.dataInputSearch['HoVaTen'] : '',
            diabancosoid: vm.dataInputSearch && vm.dataInputSearch.hasOwnProperty('DiaBanCoSo_ID') ? vm.dataInputSearch['DiaBanCoSo_ID'] : '',
            cosoytema: vm.dataInputSearch && vm.dataInputSearch['CoSoYTe_Ma'] ? vm.dataInputSearch['CoSoYTe_Ma'] : '',
            kiemtratrung: vm.dataInputSearch && vm.dataInputSearch['KiemTraTrung'] ? vm.dataInputSearch['KiemTraTrung'] : -1
          }
        }
        vm.$store.dispatch('exportDanhSach', filter).then(function(result) {
          vm.processingAction = false
        }).catch(function () {
          vm.processingAction = false
        })
      }, 
      changePage (config) {
        let vm = this
        vm.page = config.page
        vm.getDanhSachDaCap(config.page, vm.dataInputSearch)
      },
      editRegistration (item) {
        let vm = this
        vm.$store.commit('SET_GiayDiDuongUpdate', item)
        vm.$router.push('/pages/dang-ky-di-duong/' + item.id)
      },
      parseDate (date) {
        if (!date) {
          return ''
        } else {
          let lengthDate = String(date).length
          if (lengthDate === 4) {
            return date
          } else {
            return String(date).slice(6,8) + '/' + String(date).slice(4,6) + '/' + String(date).slice(0,4)
          }
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

