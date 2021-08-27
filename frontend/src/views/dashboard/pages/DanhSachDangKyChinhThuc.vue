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
        title="DANH SÁCH ĐĂNG KÝ TIÊM CHÍNH THỨC"
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
            <v-btn color="#0072bc" small class="mx-0 mr-4" @click.stop="exportDanhSach" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-export
              </v-icon>
              Xuất danh sách
            </v-btn>
            <v-btn color="orange" small class="mx-0" @click.stop="translateStatus('multiple')" :loading="processingAction" :disabled="processingAction">
              <v-icon left size="20">
                mdi-backup-restore
              </v-icon>
              Rút đăng ký
            </v-btn>  
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
              <div :style="item.kiemTraTrung == 2 ? 'background: yellow' : ''" :title="item.kiemTraTrung == 2 ? 'Đăng ký trùng lặp' : ''">
                <p class="mb-0" style="font-weight: 500;">{{ item.hoVaTen}}</p>
                <p class="mb-2" style="color: blue">Ngày sinh: {{ item.ngaySinh }}</p>
              </div>
            </template>
            <template v-slot:item.nhomDoiTuong="{ item, index }">
                <div class="mb-2">{{ item.nhomDoiTuong}}</div>
            </template>
            <template v-slot:item.diaChiNoiO="{ item, index }">
                <p class="mb-2">{{ item.diaChiNoiO}} - {{item.phuongXaTen}} - {{item.quanHuyenTen}} - {{item.tinhThanhTen}}</p>
            </template>
            <template v-slot:item.muiTiemChung="{ item, index }">
              <div style="width: 250px;height: 100%;">
                <v-layout wrap style="height: 100%;">
                  <v-flex class="xs12 md6" style="border-right: 1px solid #dedede;">
                    <p class="py-2 mb-0"></p>
                  </v-flex>
                  <v-flex class="xs12 md6">
                    <p class="py-2 mb-0"></p>
                  </v-flex>
                </v-layout>
              </div>
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
                    <v-btn @click="translateStatus(item)" color="orange" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-backup-restore</v-icon>
                    </v-btn>
                  </template>
                  <span>Rút đăng ký</span>
                </v-tooltip>
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
        max-width="1000"
        v-model="dialogDetail"
        fullscreen
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title>Thông tin người đăng ký tiêm</v-toolbar-title>
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
                  v-model="detaiInfo.gioiTinh"
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
              <v-card  outlined class="pa-2 mr-2" max-width="450" min-width="350" v-for="(item, index) in detaiInfo['muiTiemChung']" v-bind:key="index">
                <div class="mb-2">
                  <v-icon size=22 color="green" class="mr-3" >
                      mdi-checkbox-marked-circle-outline
                  </v-icon>
                  <span style="font-weight: bold">MŨI {{index + 1}}</span> - 
                  <span style="">Đã tiêm</span>
                </div>
                <p class="mb-2">Tên vắc xin: </p>
                <p class="mb-2">Lô vắc xin: </p>
                <p class="mb-2">Ngày tiêm: </p>
                <p class="mb-2">Địa điểm tiêm: </p>
              </v-card>
              <!-- <v-card class="pa-2 ml-2" outlined max-width="450" min-width="350">
                <div class="mb-2">
                  <v-icon size=22 color="red" class="mr-3" >
                      mdi-cancel
                  </v-icon>
                  <span style="font-weight: bold">MŨI 2</span> - 
                  <span style="">Chưa tiêm</span>
                </div>
                <p class="mb-2">Tên vắc xin: </p>
                <p class="mb-2">Lô vắc xin: </p>
                <p class="mb-2">Ngày tiêm: </p>
                <p class="mb-2">Địa điểm tiêm: </p>
              </v-card> -->
            </v-layout>
            <p v-else>Chưa có lịch sử tiêm chủng</p>
            <div class="my-3">
                <v-icon size=22 color="#0072bc" class="mr-3" >
                    mdi-history
                </v-icon>
                <span style="color: #0072bc;font-weight: bold">THÔNG TIN LỊCH HẸN</span>
            </div>
            <v-layout wrap v-if="detaiInfo['phieuHenTiem'] && detaiInfo['phieuHenTiem'].length">
              <v-card  outlined class="pa-2 mr-2" max-width="450" min-width="350" v-for="(item, index) in detaiInfo['phieuHenTiem']" v-bind:key="index">
                
              </v-card>
            </v-layout>
            <p v-else>Chưa có thông tin hẹn tiêm</p>
          </v-card-text>
        </v-card>
      </v-dialog>
      
    </v-container>
    
  </div>
  
</template>

<script>
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
        detaiInfo: '',
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
        },
        showAdvanceSearch: false,
        selected: [],
        dataInputSearch: '',
        dialogDetail: '',
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
            text: 'Thông tin mũi tiêm',
            align: 'center',
            value: 'muiTiemChung',
            class: 'px-0'
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
      vm.getDanhSachDangKyChinhThuc(0)
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
        vm.getDanhSachDangKyChinhThuc(0, data)
      },
      cancelSearchDangKyTiem (data) {
        let vm = this
        vm.showAdvanceSearch = false
        vm.dataInputSearch = data
        vm.page = 0
        vm.totalItem = 0
        vm.pageCount = 0
        vm.getDanhSachDangKyChinhThuc(0, data)
      },
      showTimKiem () {
        let vm = this
        vm.showAdvanceSearch = true
      },
      getDanhSachDangKyChinhThuc (pageIn, dataSearch) {
        let vm = this
        vm.loadingData = true
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage,
          tinhtrangdangky: 1,
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
      exportDanhSach () {
        let vm = this
        vm.processingAction = true
        let filter = {
          typeList: 'DanhSachTiemChinhThuc',
          data: {
            page: -1,
            size: -1,
            tinhtrangdangky: 1,
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
      translateStatus (item) {
        let vm = this
        let arrIds = ''
        if (item === 'multiple') {
          if (vm.selected.length === 0) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Vui lòng chọn người muốn rút đăng ký',
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
            TinhTrangDangKi: 2
          }
        }
        if (!filter['data']['ids']) {
          return
        }
        let x = confirm('Bạn có chắc chắn thực hiện hành động này?')
        if (x) {
          vm.$store.dispatch('updateRegistrationStatus', filter).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Rút đăng ký thành công',
              color: 'success',
            })
            vm.getDanhSachDangKyChinhThuc(0)
            vm.selected = []
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Rút đăng ký thất bại',
              color: 'error',
            })
          })
        }
      },
      viewDetail (item) {
        let vm = this
        vm.detaiInfo = item
        vm.dialogDetail = true
      },
      changePage (config) {
        let vm = this
        vm.page = config.page
        vm.getDanhSachDangKyChinhThuc(config.page, vm.dataInputSearch)
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
      }
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

