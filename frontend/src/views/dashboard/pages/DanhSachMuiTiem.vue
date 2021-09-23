<template>
  <div>
    <v-container
      id=""
      fluid
      tag="section"
      :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'padding-top: 75px' : ''"
    >
      <base-material-card
        style="margin-top: 20px"
        icon="mdi-clipboard-text"
        title="DANH SÁCH ĐÃ TIÊM CHỦNG"
        class="px-5 py-3"
      >
        <v-btn color="#0072bc" small class="mx-0" @click.stop="showTimKiem" style="position: absolute; right: 40px; top: 15px;">
          <v-icon left size="20">
            mdi-filter-plus-outline
          </v-icon>
          Lọc danh sách
        </v-btn>
        <v-card-text v-if="showAdvanceSearch">
          <tim-kiem ref="timkiem" typeGoiTiem='true' v-on:trigger-search="searchMuiTiem" v-on:trigger-cancel="cancelSearchMuiTiem"></tim-kiem>
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex my-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </span>

            <v-btn v-if="userLogin['role_name'] == 'QuanTriHeThong' || userLogin['role_name'] == 'QuanTriCoSo'" color="orange" small class="mx-0" @click.stop="pickFileImport" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-import
              </v-icon>
              Import danh sách
            </v-btn>
            <input v-if="userLogin['role_name'] == 'QuanTriHeThong' || userLogin['role_name'] == 'QuanTriCoSo'" type="file" id="fileImport" @input="uploadFileImport($event)" style="display:none">
          </div>
          
          <v-data-table
            :headers="headers"
            :items="items"
            :items-per-page="itemsPerPage"
            hide-default-footer
            class="elevation-1"
            no-data-text="Không có"
            :loading="loadingData"
            loading-text="Đang tải... "
          >
            <template v-slot:header.muiTiemChung="{ header }">
              <div>
                <div class="py-2" style="border-bottom: 1px solid #dedede;">Thông tin mũi tiêm</div>
                <v-layout wrap>
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;">
                    <p class="py-2 mb-0">Mũi 1</p>
                  </v-flex>
                  <v-flex class="xs12 md6">
                    <p class="py-2 mb-0">Mũi 2</p>
                  </v-flex>
                </v-layout>
              </div>
            </template>
            <template v-slot:item.index="{ item, index }">
              <span>{{ (page+1) * itemsPerPage - itemsPerPage + index + 1 }}</span>
            </template>
            <template v-slot:item.hoVaTen="{ item, index }">
              <div>
                <p class="mb-0" style="font-weight: 500;">{{ item.hoVaTen}}</p>
                <p class="mb-2" style="color: blue">Ngày sinh: {{ item.ngaySinh }}</p>
              </div>
            </template>
            <template v-slot:item.nhomDoiTuong="{ item, index }">
                <div class="mb-2">{{ item.nhomDoiTuong}}</div>
            </template>
            <template v-slot:item.diaChiThuongTru="{ item, index }">
                <p class="mb-2">{{ item.diaChiThuongTru}} - {{item.phuongXaTen}} - {{item.quanHuyenTen}} - {{item.tinhThanhTen}}</p>
            </template>
            <template v-slot:item.muiTiemChung="{ item, index }">
              <div style="width: 250px;height: 100%;">
                <v-layout wrap style="height: 100%;" v-if="item.muiTiemChung && item.muiTiemChung.length">
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;" v-for="(item2, index2) in item.muiTiemChung" :key="index2"
                    v-if="item.muiTiemChung && item.muiTiemChung[index2]['lanTiem'] == 1"
                  >
                    <p class="py-2 mb-0" style="text-align: left;">
                      <span>Ngày tiêm: {{item.muiTiemChung[index2]['ngayTiemChung']}}</span><br>
                      <span>Loại thuốc: {{item.muiTiemChung[index2]['loaiThuocTiem']}}</span><br>
                      <span>Địa điểm: {{item.muiTiemChung[index2]['diaDiemTiemChung']}}</span>
                    </p>
                  </v-flex>
                  <v-flex class="xs12 md6" v-for="(item2, index2) in item.muiTiemChung" :key="index2"
                    v-if="item.muiTiemChung && item.muiTiemChung[index2]['lanTiem'] == 2"
                  >
                    <p class="py-2 mb-0 pl-2" style="text-align: left;">
                      <span>Ngày tiêm: {{item.muiTiemChung[index2]['ngayTiemChung']}}</span><br>
                      <span>Loại thuốc: {{item.muiTiemChung[index2]['loaiThuocTiem']}}</span><br>
                      <span>Địa điểm: {{item.muiTiemChung[index2]['diaDiemTiemChung']}}</span>
                    </p>
                  </v-flex>
                </v-layout>
                <v-layout wrap style="height: 100%;" v-else>
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;"></v-flex>
                  <v-flex class="xs12 md6"></v-flex>
                </v-layout>
              </div>
            </template>
            <template v-slot:item.action="{ item }">
              <div style="width: 150px">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="viewDetail(item)" color="blue" text icon class="ml-2" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-account-details-outline</v-icon>
                    </v-btn>
                  </template>
                  <span>Thông tin chi tiết</span>
                </v-tooltip>
              </div>
              
            </template>
          </v-data-table>
          <pagination v-if="pageCount" :pageInput="page" :pageCount="pageCount" @tiny:change-page="changePage"></pagination>
        </v-card-text>
      </base-material-card>
      <v-dialog
        max-width="600"
        v-model="dialog"
      >
        <v-card>
          <v-toolbar
            dark
            color="primary"
          >
            <v-toolbar-title>Thông tin chi tiết</v-toolbar-title>
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
            
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="dialog = false">
              <v-icon left>
                mdi-close
              </v-icon>
              Thoát
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog
        max-width="1000"
        v-model="dialogDetail"
        fullscreen
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title>Thông tin tiêm chủng</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialogDetail = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="pt-0">
            <div class="my-3">
                <v-icon size=22 color="#0072bc" class="mr-3" >
                    mdi-account-check-outline
                </v-icon>
                <span style="color: #0072bc;font-weight: bold">THÔNG TIN NGƯỜI TIÊM</span>
            </div>
            <v-layout wrap>
              <v-text-field
                  class="flex xs12 md3"
                  v-model="detaiInfo.hoVaTen"
                  outlined
                  label="Họ tên"
                  placeholder="Họ tên"
                  dense
                  readonly
                  hide-details="auto"
              ></v-text-field>
              <v-text-field
                  label="Giới tính"
                  placeholder="Giới tính"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="formatSex(detaiInfo.gioiTinh)"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Ngày sinh"
                  placeholder="Ngày sinh"
                  class="flex xs12 md3 pl-2 mb-2"
                  v-model="detaiInfo.ngaySinh"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Số CMND/ CCCD"
                  placeholder="Số CMND/ CCCD"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.cmtcccd ? detaiInfo.cmtcccd : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Số thẻ BHYT"
                  placeholder="Số thẻ BHYT"
                  class="flex xs12 md3 mb-2"
                  :value="detaiInfo.soTheBHYT ? detaiInfo.soTheBHYT : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Số điện thoại"
                  placeholder="Số điện thoại"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.soDienThoai ? detaiInfo.soDienThoai : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Email"
                  placeholder="Email"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.email ? detaiInfo.email : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Địa chỉ"
                  placeholder="Địa chỉ"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.diaChiNoiO"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Nhóm đối tượng"
                  placeholder="Nhóm đối tượng"
                  class="flex xs12 md3 mb-2"
                  :value="detaiInfo.nhomDoiTuong"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Đơn vị công tác"
                  placeholder="Đơn vị công tác"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.donViCongTac ? detaiInfo.donViCongTac : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
              <v-text-field
                  label="Ngày đăng ký tiêm"
                  placeholder="Ngày đăng ký tiêm"
                  class="flex xs12 md3 pl-2 mb-2"
                  :value="detaiInfo.ngayDangKi ? detaiInfo.ngayDangKi : ' '"
                  dense
                  outlined
                  hide-details="auto"
                  readonly
              ></v-text-field>
            </v-layout>
            <div class="my-3">
                <v-icon size=22 color="#0072bc" class="mr-3" >
                    mdi-history
                </v-icon>
                <span style="color: #0072bc;font-weight: bold">LỊCH SỬ TIÊM</span>
            </div>
            <v-layout wrap v-if="detaiInfo['muiTiemChung'] && detaiInfo['muiTiemChung'].length">
              <v-card outlined class="pa-2 mr-2" max-width="450" min-width="350" v-for="(item, index) in detaiInfo['muiTiemChung']" v-bind:key="index">
                <div class="mb-2">
                  <v-icon size=22 color="green" class="mr-3" >
                      mdi-checkbox-marked-circle-outline
                  </v-icon>
                  <span style="font-weight: bold">MŨI TIÊM {{item['lanTiem']}}</span>
                </div>
                <p class="mb-2">Tên vắc xin: {{item['loaiThuocTiem']}}</p>
                <p class="mb-2">Nơi sản xuất: {{item['noiSanXuat']}}</p>
                <p class="mb-2">Lô vắc xin: {{item['soLoThuoc']}}</p>
                <p class="mb-2">Ngày tiêm: {{item['ngayTiemChung']}}</p>
                <p class="mb-2">Địa điểm tiêm: {{item['diaDiemTiemChung']}}</p>
              </v-card>
            </v-layout>
            <p v-else>Chưa có lịch sử tiêm chủng</p>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-container>
    
  </div>
  
</template>

<script>
  import $ from 'jquery'
  import Search from './FormTimKiem.vue'
  import Pagination from './Pagination'
  export default {
    name: 'Customers',
    components: {
    'tim-kiem': Search,
    'pagination': Pagination
    },
    data () {
      return {
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
        dialogDetail: false,
        detaiInfo: '',
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
          // {
          //   sortable: false,
          //   text: 'Mã nhóm đối tượng',
          //   align: 'left',
          //   value: 'nhomDoiTuong'
          // },
          {
            sortable: false,
            text: 'Số điện thoại',
            align: 'left',
            value: 'soDienThoai'
          },
          {
            sortable: false,
            text: 'Địa chỉ',
            align: 'left',
            value: 'diaChiThuongTru'
          },
          // {
          //   sortable: false,
          //   text: 'Thông tin mũi tiêm',
          //   align: 'center',
          //   value: 'muiTiemChung',
          //   class: 'px-0'
          // },
          // {
          //   sortable: false,
          //   text: 'Thao tác',
          //   align: 'center',
          //   value: 'action'
          // },
        ],
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 1)
      let isSigned = this.$store.getters.getIsSigned
      if (!isSigned) {
        vm.$router.push({ path: '/login?redirect=/pages/danh-sach-dang-ky-tiem-moi' })
        return
      }
      vm.getDanhSachMuiTiem(0)
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
    },
    methods: {
      viewDetail (item) {
        let vm = this
        vm.detaiInfo = item
        vm.dialogDetail = true
      },
      searchMuiTiem (data) {
        let vm = this
        console.log('dataSearch', data)
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachMuiTiem(0, data)
      },
      cancelSearchMuiTiem (data) {
        let vm = this
        vm.showAdvanceSearch = false
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachMuiTiem(0, data)
      },
      showTimKiem () {
        let vm = this
        vm.showAdvanceSearch = true
      },
      getDanhSachMuiTiem (pageIn, dataSearch) {
        let vm = this
        vm.loadingData = true
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage,
          data: {
            tinhtrangdangki: 3,
            isSearchDaTiem: true,
            cmtcccd: dataSearch && dataSearch['CMTCCCD'] ? dataSearch['CMTCCCD'] : '',
            hovaten: dataSearch && dataSearch['HoVaTen'] ? dataSearch['HoVaTen'] : '',
            // diabancosoid: dataSearch && dataSearch.hasOwnProperty('DiaBanCoSo_ID') && dataSearch['DiaBanCoSo_ID'] ? dataSearch['DiaBanCoSo_ID'] : '',
            tinhthanhma: dataSearch && dataSearch['TinhThanh_Ma'] ? dataSearch['TinhThanh_Ma'] : '',
            quanhuyenma: dataSearch && dataSearch['QuanHuyen_Ma'] ? dataSearch['QuanHuyen_Ma'] : '',
            phuongxama: dataSearch && dataSearch['PhuongXa_Ma'] ? dataSearch['PhuongXa_Ma'] : '',
            soMuiTiem: dataSearch && dataSearch['soMuiTiem'] ? dataSearch['soMuiTiem'] : '',
            loaiThuocTiem: dataSearch && dataSearch['loaiThuocTiem'] ? dataSearch['loaiThuocTiem'] : '',
            diachinoio: dataSearch && dataSearch['diachinoio'] ? dataSearch['diachinoio'] : '',
          }
          
        }
        vm.$store.dispatch('locDanhSachNguoiTiemThieuThongTin', filter).then(function(result) {
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
      translateStatus (item, type) {
        let vm = this
        let arrIds = ''
        if (item === 'multiple') {
          if (vm.selected.length === 0) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Vui lòng chọn người đăng ký muốn chuyển',
              color: 'success',
            })
            return
          }
          arrIds = vm.selected.map(function(item) {
            return item['id']
          }).toString()
          console.log('selected', arrIds)
        }
        let filter = {
          data: {
            ids: item === 'multiple' ? arrIds : String(item.id),
            TinhTrangDangKi: type === 'remove' ? 2 : 1
          }
        }
        if (!filter['data']['ids']) {
          return
        }
        let textConfirm = type === 'remove' ? 'Bạn có chắc chắn muốn Rút đăng ký' : 'Bạn có chắc chắn muốn Chuyển đăng ký'
        let x = confirm(textConfirm)
        if (x) {
          vm.$store.dispatch('updateRegistrationStatus', filter).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: type === 'remove' ? 'Rút thành công' : 'Chuyển thành công',
              color: 'success',
            })
            vm.getDanhSachMuiTiem(0)
            vm.selected = []
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: type === 'remove' ? 'Rút thất bại' : 'Chuyển thất bại',
              color: 'error',
            })
          })
        }
        
      },
      removeRegistrationStatus (item) {
        let vm = this
        let textConfirm = 'Bạn có chắc chắn muốn xóa đăng ký'
        let x = confirm(textConfirm)
        if (x) {
          vm.$store.dispatch('removeRegistrationStatus', item).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thành công',
              color: 'success',
            })
            vm.getDanhSachMuiTiem(0)
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
          startCol:0,
          endCol:20,
          startRow:6,
          endRow:20000,
          table:'nguoidatiemchung'
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
            diabancosoid: vm.dataInputSearch && vm.dataInputSearch['DiaBanCoSo_ID'] ? vm.dataInputSearch['DiaBanCoSo_ID'] : '',
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
        vm.getDanhSachMuiTiem(config.page, vm.dataInputSearch)
      },
      editRegistration (item) {
        let vm = this
        vm.$store.commit('SET_RegistrationUpdate', item)
        vm.$router.push('/pages/dang-ky-tiem-moi/' + item.id)
      },
      formatSex (val) {
        if (val == 1) {
          return 'Nữ'
        } else if (val == 0) {
          return 'Nam'
        } else {
          return 'Không xác định'
        }
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

