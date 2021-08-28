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
        title="DANH SÁCH LỊCH TIÊM"
        class="px-5 py-3"
      >
        <!-- <v-btn class="mx-0" fab dark small color="#0072bc" @click.stop="showTimKiem" style="position: absolute; right: 40px; top: 15px;">
          <v-icon dark>
            mdi-magnify
          </v-icon>
        </v-btn> -->
        <v-card-text v-if="showAdvanceSearch">
          <!-- <tim-kiem ref="timkiem" v-on:trigger-search="searchDangKyTiem"></tim-kiem> -->
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span>
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span>
            </span>
            <v-btn v-if="userLogin['coSoYTeId']" color="#0072bc" class="mx-0" @click.stop="addMember('add')">
                <v-icon left size="22">
                  mdi-plus
                </v-icon>
                Thêm lịch tiêm
              </v-btn>
          </div>
          <v-data-table
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
              <div style="width: 100px">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="editLichTiem(item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <span>Sửa thông tin</span>
                </v-tooltip>
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="deleteLichTiem(item)" color="red" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-delete</v-icon>
                    </v-btn>
                  </template>
                  <span>Xóa lịch tiêm</span>
                </v-tooltip>
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="viewCaTiem(item)" color="green" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-collapse-all-outline</v-icon>
                    </v-btn>
                  </template>
                  <span>Xem ca tiêm</span>
                </v-tooltip>
              </div>
            </template>
          </v-data-table>
          <pagination v-if="pageCount" :pageInput="page" :pageCount="pageCount" @tiny:change-page="changePage"></pagination>
        </v-card-text>
      </base-material-card>
      <v-dialog
        max-width="1000"
        v-model="dialogAddMember"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title v-if="typeAction === 'add'">Thêm lịch tiêm</v-toolbar-title>
            <v-toolbar-title v-else>Cập nhật thông tin</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialogAddMember = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="mt-5">
            <v-form
              ref="formAddLichTiem"
              v-model="validFormAdd"
              lazy-validation
            >
                <v-layout wrap>
                    <!-- <v-autocomplete
                        class="flex xs12 md12"
                        hide-no-data
                        :items="listCoSoYTe"
                        v-model="coSoYTe"
                        item-text="tenCoSo"
                        item-value="id"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Cơ sở y tế"
                        dense
                    ></v-autocomplete> -->
                    <v-text-field
                        class="flex xs12 md12"
                        v-model="thongTinLichTiem.MaDot"
                        outlined
                        label="Mã đợt tiêm"
                        placeholder="Mã đợt tiêm"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                      label="Ngày bắt đầu"
                      class="flex xs12 md6"
                      v-model="startDateFormatted"
                      placeholder="dd/mm/yyyy, ddmmyyyy"
                      @blur="formatStartDate"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Ngày kết thúc"
                      class="flex xs12 md6 pl-2"
                      v-model="endDateFormatted"
                      placeholder="dd/mm/yyyy, ddmmyyyy"
                      @blur="formatEndDate"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Địa chỉ tiêm chủng"
                      class="flex xs12 md12"
                      v-model="thongTinLichTiem.DiaDiemTiemChung"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Loại thuốc tiêm"
                      class="flex xs12 md6"
                      v-model="thongTinLichTiem.LoaiThuocTiem"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Nơi sản xuất"
                      class="flex xs12 md6 pl-2"
                      v-model="thongTinLichTiem.NoiSanXuat"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Số lô thuốc"
                      class="flex xs12 md6"
                      v-model="thongTinLichTiem.SoLoThuoc"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Hạn sử dụng"
                      class="flex xs12 md6 pl-2"
                      v-model="expDateFormatted"
                      @blur="formatExpDate"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Số ca tiêm"
                      class="flex xs12 md6"
                      v-model="thongTinLichTiem.SoCaTiem"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Số mũi một ca"
                      class="flex xs12 md6 pl-2"
                      v-model="thongTinLichTiem.SoMuiMotCa"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Bác sỹ khám, tư vấn"
                      class="flex xs12 md6"
                      v-model="thongTinLichTiem.BacSiKham"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      label="Số điện thoại bác sỹ"
                      class="flex xs12 md6 pl-2"
                      v-model="thongTinLichTiem.SoDienThoai"
                      dense
                      outlined
                    ></v-text-field>
                    <v-autocomplete
                        class="flex xs12 md6"
                        hide-no-data
                        :items="tinhTrangList"
                        v-model="thongTinLichTiem.TinhTrangLich"
                        item-text="name"
                        item-value="value"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Tình trạng lịch tiêm"
                        dense
                    ></v-autocomplete>
                    
                </v-layout>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-end">
            
            <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="cancelAddMember">
              <v-icon left>
                mdi-close
              </v-icon>
              Thoát
            </v-btn>
            <v-btn class="mr-2" color="#0072bc" :loading="loading" :disabled="loading" @click="submitAddMember">
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
  // import Search from './FormTimKiem.vue'
  import Pagination from './Pagination'
  export default {
    name: 'Customers',
    components: {
    // 'tim-kiem': Search,
    'pagination': Pagination
    },
    data () {
      return {
        validFormAdd: true,
        loading: false,
        loadingData: false,
        listDaiLy: [],
        dailySelected: '',
        dialog: false,
        lastVisible: '',
        firstVisible: '',
        totalItem: 0,
        page: 0,
        pageCount: 0,
        itemsPerPage: 15,
        typeAction: 'add',
        dialogAddMember: false,
        items: [],
        listCoSoYTe: [],
        coSoYTe: '',
        startDateFormatted: '',
        endDateFormatted: '',
        expDateFormatted: '',
        tinhTrangList: [
          {name: 'Chưa mở danh sách', value: 0},
          {name: 'Đang mở danh sách', value: 1},
          {name: 'Đã đóng kết thúc', value: 2}
        ],
        thongTinLichTiem: {
          CoSoYTe_ID: '',
          MaDot: '',
          NgayBatDau: '',
          NgayKetThuc: '',
          DiaDiemTiemChung: '',
          LoaiThuocTiem: '',
          NoiSanXuat: '',
          SoLoThuoc: '',
          HanSuDung: '',
          SoCaTiem: '',
          SoMuiMotCa: '',
          TongSoMuiTiem: '',
          TinhTrangLich: '',
          BacSiKham: '',
          SoDienThoai: ''
        },
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
        },
        showAdvanceSearch: false,
        lichTiemUpdate: '',
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
            text: 'Mã đợt',
            align: 'left',
            value: 'maDot'
          },
          {
            sortable: false,
            text: 'Ngày bắt đầu',
            align: 'left',
            value: 'ngayBatDau'
          },
          {
            sortable: false,
            text: 'Ngày kết thúc',
            align: 'left',
            value: 'ngayKetThuc'
          },
          {
            sortable: false,
            text: 'Địa chỉ tiêm chủng',
            align: 'left',
            value: 'diaDiemTiemChung'
          },
          {
            sortable: false,
            text: 'Tổng số mũi tiêm dự kiến',
            align: 'left',
            value: 'tongSoMuiTiem'
          },
          {
            sortable: false,
            text: 'Thông tin lô thuốc',
            align: 'left',
            value: 'loaiThuocTiem'
          },
          {
            sortable: false,
            text: 'Thông tin bác sỹ',
            align: 'left',
            value: 'bacSiKham'
          },
          {
            sortable: false,
            text: 'Tình trạng',
            align: 'left',
            value: 'tinhTrangLich'
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
      vm.getLichTiem(0)
      // vm.getCoSoYTe()
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      editLichTiem (item) {
        let vm = this
        vm.typeAction = 'update'
        vm.lichTiemUpdate = item
        vm.thongTinLichTiem.MaDot = item.maDot
        vm.startDateFormatted = item.ngayBatDau
        vm.endDateFormatted = item.ngayKetThuc
        vm.thongTinLichTiem.DiaDiemTiemChung = item.diaDiemTiemChung
        vm.thongTinLichTiem.LoaiThuocTiem = item.loaiThuocTiem
        vm.thongTinLichTiem.NoiSanXuat = item.noiSanXuat
        vm.thongTinLichTiem.SoLoThuoc = item.soLoThuoc
        vm.expDateFormatted = item.hanSuDung
        vm.thongTinLichTiem.SoCaTiem = item.soCaTiem
        vm.thongTinLichTiem.SoMuiMotCa = item.soMuiMotCa
        vm.thongTinLichTiem.TongSoMuiTiem = item.tongSoMuiTiem
        vm.thongTinLichTiem.TinhTrangLich = item.tinhTrangLich
        vm.thongTinLichTiem.BacSiKham = item.bacSiKham
        vm.thongTinLichTiem.SoDienThoai = item.soDienThoai
        vm.dialogAddMember = true
      },
      viewCaTiem (item) {
        let vm = this
        vm.$router.push({ path: '/pages/lich-tiem-chung/' + item.id})
      },
      formatStartDate () {
        let vm = this
        let lengthDate = String(vm.startDateFormatted).trim().length
        let splitDate = String(vm.startDateFormatted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.startDateFormatted = vm.translateDate(vm.startDateFormatted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.startDateFormatted)
          vm.startDateFormatted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.startDateFormatted = ''
        }
      },
      formatEndDate () {
        let vm = this
        let lengthDate = String(vm.endDateFormatted).trim().length
        let splitDate = String(vm.endDateFormatted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.endDateFormatted = vm.translateDate(vm.endDateFormatted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.endDateFormatted)
          vm.endDateFormatted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.endDateFormatted = ''
        }
      },
      formatExpDate () {
        let vm = this
        let lengthDate = String(vm.expDateFormatted).trim().length
        let splitDate = String(vm.expDateFormatted).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.expDateFormatted = vm.translateDate(vm.expDateFormatted)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.expDateFormatted)
          vm.expDateFormatted = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.expDateFormatted = ''
        }
      },
      getLichTiem (pageIn) {
        let vm = this
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage,
        }
        vm.$store.dispatch('getLichTiem', filter).then(function (result) {
          vm.items = result.hasOwnProperty('data') ? result.data : []
          vm.totalItem = result.hasOwnProperty('total') ? result.total : 0
          vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
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
      changePage (config) {
        let vm = this
        vm.page = config.page
        vm.getLichTiem(config.page)
      },
      addMember (type, user) {
        let vm = this
        vm.typeAction = type
        vm.dialogAddMember = true
        if (type === 'add') {
          setTimeout(function () {
            vm.$refs.formAddLichTiem.reset()
            vm.$refs.formAddLichTiem.resetValidation()
          }, 200)
        }         
      },
      formatDataInput () {
        let vm = this
        try {
          vm.thongTinLichTiem.CoSoYTe_ID = vm.userLogin['coSoYTeId']
          // let splitNgayBatDau = String(vm.startDateFormatted).split('/')
          vm.thongTinLichTiem.NgayBatDau = vm.startDateFormatted
          // let splitNgayKetThuc = String(vm.endDateFormatted).split('/')
          vm.thongTinLichTiem.NgayKetThuc = vm.endDateFormatted
          // let splitHanSuDung = String(vm.expDateFormatted).split('/')
          vm.thongTinLichTiem.HanSuDung = vm.expDateFormatted
          vm.thongTinLichTiem.SoCaTiem = Number(vm.thongTinLichTiem.SoCaTiem)
          vm.thongTinLichTiem.SoMuiMotCa = Number(vm.thongTinLichTiem.SoMuiMotCa)
          vm.thongTinLichTiem.TongSoMuiTiem = vm.thongTinLichTiem.SoCaTiem*vm.thongTinLichTiem.SoMuiMotCa
          console.log('thongTinLichTiem', vm.thongTinLichTiem)
        } catch (error) {
        }
      },
      submitAddMember () {
        let vm = this
        if (vm.$refs.formAddLichTiem.validate()) {
          vm.formatDataInput()
          if (vm.typeAction === 'add') {
            let filter = {
              data: vm.thongTinLichTiem
            }
            vm.loading = true
            vm.$store.dispatch('addLichTiem', filter).then(userCredential => {
              vm.loading = false
              vm.dialogAddMember = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm lịch tiêm thành công',
                color: 'success',
              })
              vm.page = 0
              vm.getLichTiem(0)
            })
            .catch((error) => {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm lịch tiêm không thành công',
                color: 'error',
              })
            });
          } else {
            let filter = {
              id: vm.lichTiemUpdate['id'],
              data: vm.thongTinLichTiem
            }
            vm.loading = true
            vm.$store.dispatch('updateLichTiem', filter).then(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.dialogAddMember = false
              vm.getLichTiem(0)
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
      deleteLichTiem (item) {
        let vm = this
        let textConfirm = 'Bạn có chắc chắn muốn xóa lịch tiêm này'
        let x = confirm(textConfirm)
        if (x) {
          vm.$store.dispatch('deleteLichTiem', item).then(function (result) {
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
      cancelAddMember () {
        let vm = this
        vm.dialogAddMember = false
      },
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

