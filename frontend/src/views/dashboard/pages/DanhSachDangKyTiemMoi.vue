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
        title="DANH SÁCH ĐĂNG KÝ TIÊM MỚI"
        class="px-5 py-3"
      >
        <v-btn color="#0072bc" small class="mx-0" @click.stop="showTimKiem" style="position: absolute; right: 40px; top: 15px;">
          <v-icon left size="20">
            mdi-filter-plus-outline
          </v-icon>
          Lọc danh sách
        </v-btn>
        <v-card-text v-if="showAdvanceSearch">
          <tim-kiem ref="timkiem" v-on:trigger-search="searchDangKyTiem" v-on:trigger-cancel="cancelSearchDangKyTiem"></tim-kiem>
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex my-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </span>

            <v-btn v-if="userLogin['coSoYTeId']" color="orange" small class="mx-0 mr-4" @click.stop="pickFileImport" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-import
              </v-icon>
              Import danh sách
            </v-btn>
            <v-btn color="#0072bc" small class="mx-0 mr-4" @click.stop="exportDanhSach" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-export
              </v-icon>
              Xuất danh sách
            </v-btn>
            <!-- <v-btn color="red" small class="mx-0 mr-4" @click.stop="translateStatus('multiple', 'remove')" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-delete
              </v-icon>
              Xóa đăng ký
            </v-btn>   -->
            <v-btn color="green" small class="mx-0" @click.stop="translateStatus('multiple')" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-transfer
              </v-icon>
              Chuyển đăng ký chính thức
            </v-btn>
            <input v-if="userLogin['coSoYTeId']" type="file" id="fileImport" @input="uploadFileImport($event)" style="display:none">
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
            <template v-slot:item.index="{ item, index }">
              <div>{{ (page+1) * itemsPerPage - itemsPerPage + index + 1 }}</div>
            </template>
            <template v-slot:item.hoVaTen="{ item, index }">
              <div :style="item.kiemTraTrung == 2 ? 'background: yellow' : ''" :title="item.kiemTraTrung == 2 ? 'Đăng ký trùng lặp' : ''">
                <p class="mb-0" style="font-weight: 500;">{{ item.hoVaTen}}</p>
                <p class="mb-2" style="color: blue">Ngày sinh: {{ item.ngaySinh }}</p>
              </div>
            </template>
            <template v-slot:item.cmtcccd="{ item, index }">
                <p class="mb-2">{{ item.cmtcccd }}</p>
            </template>
            <template v-slot:item.nhomDoiTuong="{ item, index }">
                <p class="mb-2">{{ item.nhomDoiTuong }}</p>
            </template>
            <template v-slot:item.soDienThoai="{ item, index }">
                <p class="mb-2">{{ item.soDienThoai }}</p>
            </template>
            <template v-slot:item.diaChiNoiO="{ item, index }">
                <p class="mb-2">{{ item.diaChiNoiO}} - {{item.phuongXaTen}} - {{item.quanHuyenTen}} - {{item.tinhThanhTen}}</p>
            </template>
            <template v-slot:item.ngayDangKi="{ item, index }">
                <p class="mb-2">{{ item.ngayDangKi }}</p>
            </template>
            <template v-slot:item.action="{ item }">
              <div style="width: 150px">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="editRegistration(item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <span>Sửa thông tin</span>
                </v-tooltip>
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="removeRegistrationStatus(item)" color="red" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-delete</v-icon>
                    </v-btn>
                  </template>
                  <span>Xóa đăng ký</span>
                </v-tooltip>
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="translateStatus(item)" color="green" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-transfer</v-icon>
                    </v-btn>
                  </template>
                  <span>Chuyển chính thức</span>
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
            text: 'Mã nhóm đối tượng',
            align: 'left',
            value: 'nhomDoiTuong'
          },
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
            value: 'diaChiNoiO'
          },
          {
            sortable: false,
            text: 'Ngày đăng ký tiêm',
            align: 'center',
            value: 'ngayDangKi'
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
      vm.$store.commit('SET_INDEXTAB', 1)
      let isSigned = this.$store.getters.getIsSigned
      if (!isSigned) {
        vm.$router.push({ path: '/login?redirect=/pages/danh-sach-dang-ky-tiem-moi' })
        return
      }
      vm.getDanhSachDangKyMoi(0)
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
    },
    methods: {
      searchDangKyTiem (data) {
        let vm = this
        console.log('dataSearch', data)
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachDangKyMoi(0, data)
      },
      cancelSearchDangKyTiem (data) {
        let vm = this
        vm.showAdvanceSearch = false
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachDangKyMoi(0, data)
      },
      showTimKiem () {
        let vm = this
        vm.showAdvanceSearch = true
      },
      getDanhSachDangKyMoi (pageIn, dataSearch) {
        let vm = this
        vm.loadingData = true
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage,
          tinhtrangdangky: 0,
          cmtcccd: dataSearch && dataSearch['CMTCCCD'] ? dataSearch['CMTCCCD'] : '',
          nhomdoituong: dataSearch && dataSearch['NhomDoiTuong'] ? dataSearch['NhomDoiTuong'] : '',
          ngaydangki: dataSearch && dataSearch['NgayDangKi'] ? dataSearch['NgayDangKi'] : '',
          hovaten: dataSearch && dataSearch['HoVaTen'] ? dataSearch['HoVaTen'] : '',
          diabancosoid: dataSearch && dataSearch['DiaBanCoSo_ID'] ? dataSearch['DiaBanCoSo_ID'] : '',
          cosoytema: dataSearch && dataSearch['CoSoYTe_Ma'] ? dataSearch['CoSoYTe_Ma'] : '',
          kiemtratrung: dataSearch && dataSearch['KiemTraTrung'] ? dataSearch['KiemTraTrung'] : ''
        }
        vm.$store.dispatch('getNguoiTiemChung', filter).then(function(result) {
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
            vm.getDanhSachDangKyMoi(0)
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
            vm.getDanhSachDangKyMoi(0)
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
          table:'nguoitiemchung'
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
        vm.getDanhSachDangKyMoi(config.page, vm.dataInputSearch)
      },
      editRegistration (item) {
        let vm = this
        vm.$store.commit('SET_RegistrationUpdate', item)
        vm.$router.push('/pages/dang-ky-tiem-moi/' + item.id)
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

