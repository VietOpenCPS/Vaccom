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
          <tim-kiem ref="timkiem" v-on:trigger-search="searchDangKyTiem"></tim-kiem>
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span>
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span>
            </span>
            <v-btn color="#0072bc" class="mx-0" @click.stop="addMember('add')">
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
              <span>{{ page * itemsPerPage - itemsPerPage + index + 1 }}</span>
            </template>
            <!-- <template v-slot:item.DiaChiNoiO="{ item, index }">
                <p class="mb-2">{{ item.DiaChiNoiO}} - {{item.PhuongXa_Ten}} - {{item.QuanHuyen_Ten}} - {{item.TinhThanh_Ten}}</p>
            </template> -->
          </v-data-table>
          <div class="text-center mt-4" v-if="pageCount">
            <nav role="navigation" aria-label="Pagination Navigation">
              <ul class="v-pagination theme--light">
                <li>
                  <button @click="prevPage"  type="button" aria-label="Previous page" 
                    :class="page == 1 ? 'v-pagination__navigation v-pagination__navigation--disabled' : 'v-pagination__navigation'">
                    <i aria-hidden="true" class="v-icon notranslate mdi mdi-chevron-left theme--light"></i>
                  </button>
                </li>
                <li>
                  <button type="button" aria-current="true" class="v-pagination__item v-pagination__item--active primary">
                    {{page}}
                  </button>
                </li>
                <li>
                  <button @click="nextPage" type="button" aria-label="Next page" 
                    :class="page == pageCount ? 'v-pagination__navigation v-pagination__navigation--disabled' : 'v-pagination__navigation'">
                    <i aria-hidden="true" class="v-icon notranslate mdi mdi-chevron-right theme--light"></i>
                  </button>
                </li>
              </ul>
            </nav>
          </div>
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
                    <v-autocomplete
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
                    ></v-autocomplete>
                    <v-text-field
                        class="flex xs12 md12"
                        v-model="thongTinLichTiem.TenDot"
                        outlined
                        label="Tên đợt tiêm"
                        placeholder="Tên đợt tiêm"
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
                      label="Tổng số mũi tiêm"
                      class="flex xs12 md6"
                      v-model="thongTinLichTiem.TongSoMuiTiem"
                      dense
                      outlined
                    ></v-text-field>

                    <v-autocomplete
                        class="flex xs12 md6 pl-2"
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
  import Search from './FormTimKiem.vue'
  export default {
    name: 'Customers',
    components: {
    'tim-kiem': Search
    },
    data () {
      return {
        loading: false,
        loadingData: false,
        listDaiLy: [],
        dailySelected: '',
        dialog: false,
        lastVisible: '',
        firstVisible: '',
        totalItem: 0,
        page: 1,
        pageCount: 0,
        itemsPerPage: 10,
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
          TenDot: '',
          NgayBatDau: '',
          NgayKetThuc: '',
          DiaDiemTiemChung: '',
          LoaiThuocTiem: '',
          NoiSanXuat: '',
          SoLoThuoc: '',
          HanSuDung: '',
          TongSoMuiTiem: '',
          TinhTrangLich: ''
        },
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
        },
        showAdvanceSearch: false,
        lichTiemUpdate: '',
        headers: [
          {
            sortable: false,
            text: 'STT',
            align: 'center',
            value: 'index'
          },
          {
            sortable: false,
            text: 'Tên đợt',
            align: 'left',
            value: 'TenDot'
          },
          {
            sortable: false,
            text: 'Ngày bắt đầu',
            align: 'left',
            value: 'NgayBatDau'
          },
          {
            sortable: false,
            text: 'Ngày kết thúc',
            align: 'left',
            value: 'NgayKetThuc'
          },
          {
            sortable: false,
            text: 'Địa chỉ tiêm chủng',
            align: 'left',
            value: 'DiaChiTiemChung'
          },
          {
            sortable: false,
            text: 'Tổng số mũi tiêm',
            align: 'left',
            value: 'TongSoMuiTiem'
          },
          {
            sortable: false,
            text: 'Thông tin lô thuốc',
            align: 'left',
            value: 'LoaiThuocTiem'
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
      vm.getLichTiem()
      vm.getCoSoYTe()
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
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
      getLichTiem () {
        let vm = this
        let filter = {
          page: 1,
          size: 30
        }
        vm.$store.dispatch('getLichTiem', filter).then(function (result) {
          vm.items = result ? result : []
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
      
      addMember (type, user) {
        let vm = this
        vm.typeAction = type
        vm.lichTiemUpdate = user
        vm.dialogAddMember = true
        if (type === 'add') {
          setTimeout(function () {
            vm.$refs.formAddLichTiem.reset()
            vm.$refs.formAddLichTiem.resetValidation()
          }, 200)
        } else {
          setTimeout(function () {
            vm.thongTinLichTiem.MaCoSo = vm.lichTiemUpdate.maCoSo
            vm.thongTinLichTiem.TenCoSo = vm.lichTiemUpdate.tenCoSo
            vm.thongTinLichTiem.DiaChiCoSo = vm.lichTiemUpdate.diaChiCoSo
            vm.thongTinLichTiem.NguoiDaiDien = vm.lichTiemUpdate.nguoiDaiDien
            vm.tinhThanh = vm.lichTiemUpdate.tinhThanhMa
            vm.quanHuyen = vm.lichTiemUpdate.quanHuyenMa
            vm.xaPhuong = vm.lichTiemUpdate.phuongXaMa
            vm.thongTinLichTiem.SoDienThoai = vm.lichTiemUpdate.soDienThoai

            vm.$refs.formAddLichTiem.resetValidation()
          }, 200)
        }
        
      },
      formatDataInput () {
        let vm = this
        try {
          let splitNgayBatDau = String(vm.startDateFormatted).split('/')
          vm.thongTinLichTiem.NgayBatDau = splitNgayBatDau[2] + splitNgayBatDau[1] + splitNgayBatDau[0]
          let splitNgayKetThuc = String(vm.endDateFormatted).split('/')
          vm.thongTinLichTiem.NgayKetThuc = splitNgayKetThuc[2] + splitNgayKetThuc[1] + splitNgayKetThuc[0]
          let splitHanSuDung = String(vm.expDateFormatted).split('/')
          vm.thongTinLichTiem.HanSuDung = splitHanSuDung[2] + splitHanSuDung[1] + splitHanSuDung[0]
          console.log('thongTinLichTiem', vm.thongTinLichTiem)
        } catch (error) {
        }
      },
      deleteCoSo (user) {
        let vm = this
        let x = confirm('Bạn có chắc chắn xóa cơ sở y tế này?')
        if (x) {
          let filter = {
            id: user['id']
          }
          vm.loading = true
          vm.$store.dispatch('deleteCoSoYTe', filter).then(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thành công',
              color: 'success',
            })
            setTimeout(function () {
              vm.getCoSoYTe()
            }, 500)
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thất bại',
              color: 'error',
            })
          })
        }
        
      },
      submitAddMember () {
        let vm = this
        if (vm.$refs.formAddLichTiem.validate()) {
          if (vm.typeAction === 'add') {
            vm.formatDataInput()
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
              vm.getLichTiem()
            })
            .catch((error) => {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm lịch tiêm không thành công',
                color: 'error',
              })
              // ..
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
              vm.getLichTiem()
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

