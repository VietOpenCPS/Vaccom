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
        title="DANH SÁCH CA TIÊM"
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
        <v-card-text class="pt-0">
            <div class="my-3">
                <v-icon color="#0072bc" class="mr-3" >
                    mdi-calendar-month
                </v-icon>
                <span style="color: #0072bc">THÔNG TIN LỊCH TIÊM</span>
            </div>
            <v-layout wrap>
                <v-text-field
                    class="flex xs12 md6"
                    v-model="thongTinLichTiem.maDot"
                    outlined
                    label="Mã đợt tiêm"
                    placeholder="Mã đợt tiêm"
                    dense
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Ngày bắt đầu"
                    class="flex xs12 md3 pl-2 mb-2"
                    v-model="thongTinLichTiem.ngayBatDau"
                    dense
                    outlined
                    hide-details="auto"
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Ngày kết thúc"
                    class="flex xs12 md3 pl-2 mb-2"
                    v-model="thongTinLichTiem.ngayKetThuc"
                    dense
                    outlined
                    hide-details="auto"
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Địa chỉ tiêm chủng"
                    class="flex xs12 md6"
                    v-model="thongTinLichTiem.diaDiemTiemChung"
                    dense
                    outlined
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Bác sỹ khám, tư vấn"
                    class="flex xs12 md3 pl-2"
                    v-model="thongTinLichTiem.bacSiKham"
                    dense
                    outlined
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Số điện thoại bác sỹ"
                    class="flex xs12 md3 pl-2"
                    v-model="thongTinLichTiem.soDienThoai"
                    dense
                    outlined
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Loại thuốc tiêm"
                    class="flex xs12 md3"
                    v-model="thongTinLichTiem.loaiThuocTiem"
                    dense
                    outlined
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Nơi sản xuất"
                    class="flex xs12 md3 pl-2"
                    v-model="thongTinLichTiem.noiSanXuat"
                    dense
                    outlined
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Số lô thuốc"
                    class="flex xs12 md3 pl-2"
                    v-model="thongTinLichTiem.soLoThuoc"
                    dense
                    outlined
                    readonly
                ></v-text-field>
                <v-text-field
                    label="Hạn sử dụng"
                    class="flex xs12 md3 pl-2 pl-2"
                    v-model="thongTinLichTiem.hanSuDung"
                    dense
                    outlined
                    readonly
                ></v-text-field>
                
            </v-layout>
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> ca tiêm
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> ca tiêm
            </span>
            <v-btn v-if="userLogin['coSoYTeId']" color="#0072bc" class="mx-0" @click.stop="addMember('add')">
                <v-icon left size="22">
                  mdi-plus
                </v-icon>
                Thêm ca tiêm
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
            <template v-slot:item.ngayHenTiem="{ item, index }">
                {{item.gioHenTien}} ngày {{item.ngayHenTien}}
            </template>
            
            <template v-slot:item.action="{ item }">
              <div style="width: 100px">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="editCaTiem(item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <span>Sửa thông tin</span>
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
            <v-toolbar-title v-if="typeAction === 'add'">Thêm ca tiêm</v-toolbar-title>
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
              ref="formAddCaTiem"
              v-model="validFormAdd"
              lazy-validation
            >
                <v-layout wrap>
                  <v-autocomplete
                      hide-no-data
                      :rules="required"
                      required
                      :items="listDiaBan"
                      v-model="thongTinCaTiem['DiaBanCoSo_ID']"
                      item-text="tenDiaBan"
                      item-value="id"
                      outlined
                      label="Địa bàn cơ sở"
                      placeholder="Địa bàn cơ sở"
                      dense
                  ></v-autocomplete>
                    <v-text-field
                        class="flex xs12 md12"
                        v-model="thongTinCaTiem['STT']"
                        outlined
                        :rules="required"
                        required
                        label="Số thứ tự ca tiêm"
                        placeholder="Số thứ tự ca tiêm"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                      label="Thời gian hẹn tiêm"
                      class="flex xs12 md6"
                      v-model="thongTinCaTiem['GioHenTiem']"
                      placeholder="mm:ss"
                      dense
                      outlined
                    ></v-text-field>
                    <v-text-field
                      class="flex xs12 md6 pl-2"
                      v-model="ngayHenTiem"
                      placeholder="dd/mm/yyyy, ddmmyyyy"
                      @blur="formatNgayTiem"
                      dense
                      hide-details="auto"
                      outlined
                      label="Ngày hẹn tiêm"
                    ></v-text-field>
                    <v-text-field
                      label="Số mũi một ca"
                      class="flex xs12 md6"
                      v-model="thongTinCaTiem['SoMuiTiem']"
                      dense
                      outlined
                    ></v-text-field>
                                        
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
            <v-btn class="mr-2" color="#0072bc" :loading="loading" :disabled="loading" @click="submitForm">
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
    props: ['uid'],
    data () {
      return {
        diaBanCoSo: '',
        listDiaBan: [],
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
        thongTinCaTiem: {
          STT: '',
          NgayHenTiem: '',
          GioHenTiem: '',
          SoMuiTiem: '',
          DiaBanCoSo_ID: ''
        },
        thongTinLichTiem: {
          bacSiKham: '',
          coSoYTeId: '',
          diaDiemTiemChung: '',
          hanSuDung: '',
          id: '',
          loaiThuocTiem: '',
          maDot: '',
          maQR: '',
          ngayBatDau: '',
          ngayKetThuc: '',
          noiSanXuat: '',
          soCaTiem: '',
          soDienThoai: '',
          soLoThuoc: '',
          soMuiMotCa: '',
          tinhTrangLich: '',
          tongSoMuiTiem: ''
        },
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
        },
        showAdvanceSearch: false,
        lichTiemUpdate: '',
        ngayHenTiem: '',
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
            text: 'Thứ tự ca',
            align: 'center',
            value: 'stt'
          },
          {
            sortable: false,
            text: 'Địa bàn cơ sở',
            align: 'left',
            value: 'diaBanCoSo_ID'
          },
          {
            sortable: false,
            text: 'Thời gian hẹn tiêm',
            align: 'left',
            value: 'ngayHenTiem'
          },
          {
            sortable: false,
            text: 'Số mũi tiêm',
            align: 'left',
            value: 'soMuiTiem'
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
      if (!vm.uid) {
        vm.$router.push({ path: '/pages/lich-tiem-chung' })
        return
      }
      vm.getChiTietLichTiem()
      vm.getDiaBanCoSo()
      vm.getCaTiem(0, vm.uid)
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      getDiaBanCoSo () {
        let vm = this
        if (vm.userLogin['coSoYTeId']) {
          let filter = {
            id: vm.userLogin['coSoYTeId']
          }        
          vm.$store.dispatch('getDiaBanCoSo', filter).then(function (result) {
            if (result.hasOwnProperty('data') && result.data.length) {
              vm.listDiaBan = result.data
            }
          })
        }
      },
      getChiTietLichTiem () {
        let vm = this
        let filter = {
          id: vm.uid
        }        
        vm.$store.dispatch('getChiTietLichTiem', filter).then(function (result) {
          if (result) {
            console.log('result', result)
            vm.thongTinLichTiem = result
          }
        })
        
      },
      editCaTiem (item) {
        let vm = this
        vm.typeAction = 'update'
        // vm.lichTiemUpdate = item
        // vm.thongTinCaTiem.MaDot = item.maDot
        // vm.startDateFormatted = item.ngayBatDau
        // vm.endDateFormatted = item.ngayKetThuc
        // vm.thongTinCaTiem.DiaDiemTiemChung = item.diaDiemTiemChung
        // vm.thongTinCaTiem.LoaiThuocTiem = item.loaiThuocTiem
        // vm.thongTinCaTiem.NoiSanXuat = item.noiSanXuat
        // vm.thongTinCaTiem.SoLoThuoc = item.soLoThuoc
        // vm.expDateFormatted = item.hanSuDung
        // vm.thongTinCaTiem.SoCaTiem = item.soCaTiem
        // vm.thongTinCaTiem.SoMuiMotCa = item.soMuiMotCa
        // vm.thongTinCaTiem.TongSoMuiTiem = item.tongSoMuiTiem
        // vm.thongTinCaTiem.TinhTrangLich = item.tinhTrangLich
        // vm.thongTinCaTiem.BacSiKham = item.bacSiKham
        // vm.thongTinCaTiem.SoDienThoai = item.soDienThoai
        vm.dialogAddMember = true
      },
      getCaTiem (pageIn, idLich) {
        let vm = this
        let filter = {
          lichtiemchungid: idLich,
          diabancosoid: '',
          page: pageIn,
          size: vm.itemsPerPage,
        }
        vm.$store.dispatch('getCaTiem', filter).then(function (result) {
          vm.items = result.hasOwnProperty('data') ? result.data : []
          vm.totalItem = result.hasOwnProperty('total') ? result.total : 0
          vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
        })
      },
      formatNgayTiem () {
        let vm = this
        let lengthDate = String(vm.ngayHenTiem).trim().length
        let splitDate = String(vm.ngayHenTiem).split('/')
        if (lengthDate && lengthDate > 4 && splitDate.length === 3 && splitDate[2]) {
          vm.ngayHenTiem = vm.translateDate(vm.ngayHenTiem)
        } else if (lengthDate && lengthDate === 8) {
          let date = String(vm.ngayHenTiem)
          vm.ngayHenTiem = date.slice(0,2) + '/' + date.slice(2,4) + '/' + date.slice(4,8)
        } else {
          vm.ngayHenTiem = ''
        }
      },
      
      changePage (config) {
        let vm = this
        vm.page = config.page
        vm.getCaTiem(config.page)
      },
      addMember (type, user) {
        let vm = this
        vm.typeAction = type
        vm.dialogAddMember = true
        if (type === 'add') {
          setTimeout(function () {
            vm.$refs.formAddCaTiem.reset()
            vm.$refs.formAddCaTiem.resetValidation()
          }, 200)
        }         
      },
      formatDataInput () {
        let vm = this
        try {
          vm.thongTinCaTiem['STT'] = Number(vm.thongTinCaTiem['STT'])
          vm.thongTinCaTiem['NgayHenTiem'] = vm.ngayHenTiem
          vm.thongTinCaTiem['SoMuiTiem'] = Number(vm.thongTinCaTiem['SoMuiTiem'])
          console.log('thongTinCaTiem', vm.thongTinCaTiem)
        } catch (error) {
        }
      },
      submitForm () {
        let vm = this
        if (vm.$refs.formAddCaTiem.validate()) {
          vm.formatDataInput()
          if (vm.typeAction === 'add') {
            let filter = {
              data: vm.thongTinCaTiem
            }
            vm.loading = true
            vm.$store.dispatch('addCaTiem', filter).then(userCredential => {
              vm.loading = false
              vm.dialogAddMember = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm ca tiêm thành công',
                color: 'success',
              })
              vm.page = 0
              vm.getCaTiem(0, vm.uid)
            })
            .catch((error) => {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm ca tiêm không thành công',
                color: 'error',
              })
            });
          } else {
            let filter = {
              id: vm.lichTiemUpdate['id'],
              data: vm.thongTinCaTiem
            }
            vm.loading = true
            vm.$store.dispatch('updateCaTiem', filter).then(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.dialogAddMember = false
              vm.getCaTiem(0, vm.uid)
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

