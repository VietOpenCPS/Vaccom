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
        max-width="700"
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
        items: [],
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
        },
        showAdvanceSearch: false,
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
            value: 'NhomDoiTuong'
          },
          {
            sortable: false,
            text: 'Số điện thoại',
            align: 'left',
            value: 'CMTCCCD'
          },
          {
            sortable: false,
            text: 'Địa chỉ',
            align: 'left',
            value: 'DiaChiNoiO'
          },
          {
            sortable: false,
            text: 'Ngày đăng ký tiêm',
            align: 'center',
            value: 'NgayDangKi'
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
      vm.getCoSoYTe()
      vm.getTinhThanh()
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
       getCoSoYTe () {
        let vm = this
        let filter = {
          page: 1,
          size: 30
        }
        vm.$store.dispatch('getCoSoYTe', filter).then(function (result) {
          vm.items = result ? result : []
          vm.totalItem = result.length
        })
      },
      getTinhThanh () {
        let vm = this
        let filter = {
        }
        vm.$store.dispatch('getDanhMucTinhThanh', filter).then(function (result) {
          vm.listTinhThanh = result ? result : []
          if (vm.tinhThanh) {
            vm.getQuanHuyen(vm.tinhThanh)
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
        let filter = {
          idParent: obj['id']
        }
        vm.$store.dispatch('getDanhMucQuanHuyen', filter).then(function (result) {
          vm.listQuanHuyen = result ? result : []
          if (vm.quanHuyen) {
            vm.getXaPhuong(vm.quanHuyen)
          }
        })
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
      addMember (type, user) {
        let vm = this
        vm.typeAction = type
        vm.coSoUpdate = user
        vm.dialogAddMember = true
        if (type === 'add') {
          setTimeout(function () {
            vm.$refs.formAddMember.reset()
            vm.$refs.formAddMember.resetValidation()
          }, 200)
        } else {
          setTimeout(function () {
            vm.thongTinCoSo.MaCoSo = vm.coSoUpdate.maCoSo
            vm.thongTinCoSo.TenCoSo = vm.coSoUpdate.tenCoSo
            vm.thongTinCoSo.DiaChiCoSo = vm.coSoUpdate.diaChiCoSo
            vm.thongTinCoSo.NguoiDaiDien = vm.coSoUpdate.nguoiDaiDien
            vm.tinhThanh = vm.coSoUpdate.tinhThanhMa
            vm.quanHuyen = vm.coSoUpdate.quanHuyenMa
            vm.xaPhuong = vm.coSoUpdate.phuongXaMa
            vm.thongTinCoSo.SoDienThoai = vm.coSoUpdate.soDienThoai

            vm.$refs.formAddMember.resetValidation()
          }, 200)
        }
        
      },
      formatDataInput () {
        let vm = this
        try {
          if (vm.tinhThanh) {
            let obj = vm.listTinhThanh.find(function (item) {
              return item.tinhThanhMa == vm.tinhThanh
            })
            vm.thongTinCoSo.TinhThanh_Ma = vm.tinhThanh
            vm.thongTinCoSo.TinhThanh_Ten = obj ? obj['tinhThanhTen'] : ''
          }
          if (vm.quanHuyen) {
            let obj = vm.listQuanHuyen.find(function (item) {
              return item.quanHuyenMa == vm.quanHuyen
            })
            vm.thongTinCoSo.QuanHuyen_Ma = vm.quanHuyen
            vm.thongTinCoSo.QuanHuyen_Ten = obj ? obj['quanHuyenTen'] : ''
          }
          if (vm.xaPhuong) {
            let obj = vm.listXaPhuong.find(function (item) {
              return item.phuongXaMa == vm.xaPhuong
            })
            vm.thongTinCoSo.PhuongXa_Ma = vm.xaPhuong
            vm.thongTinCoSo.PhuongXa_Ten = obj ? obj['phuongXaTen'] : ''
          }
          console.log('thongTinCoSo', vm.thongTinCoSo)
        } catch (error) {
          vm.processingAction = false
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
        if (vm.$refs.formAddMember.validate()) {
          vm.formatDataInput()
          if (vm.typeAction === 'add') {
            let filter = {
              data: vm.thongTinCoSo
            }
            vm.loading = true
            vm.$store.dispatch('addCoSoYTe', filter).then(userCredential => {
              vm.loading = false
              vm.dialogAddMember = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm cơ sở thành công',
                color: 'success',
              })
              vm.getCoSoYTe()
            })
            .catch((error) => {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm cơ sở y tế không thành công',
                color: 'error',
              })
              // ..
            });
          } else {
            let filter = {
              id: vm.coSoUpdate['id'],
              data: vm.thongTinCoSo
            }
            vm.loading = true
            vm.$store.dispatch('updateCoSoYTe', filter).then(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.dialogAddMember = false
              vm.getCoSoYTe()
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

