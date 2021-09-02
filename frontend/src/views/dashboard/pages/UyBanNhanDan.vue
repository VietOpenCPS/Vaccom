<template>
  <div v-if="userLogin['role_name'] === 'QuanTriHeThong'">
    <v-container
      id="users"
      fluid
      tag="section"
      :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'padding-top: 75px' : ''"
    >
      <base-material-card
        style="margin-top: 20px"
        icon="mdi-clipboard-text"
        title="Danh sách Ủy ban nhân dân"
        class="px-5 py-3"
      >
          <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : ''">
            <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
              <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
                Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> Ủy ban nhân dân
              </div>
              <span class="mr-auto pt-2" v-else>
                Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> Ủy ban nhân dân
              </span>
              <v-btn color="#0072bc" class="mx-0" @click.stop="addMember('add')">
                <v-icon left size="22">
                  mdi-plus
                </v-icon>
                Thêm Ủy ban nhân dân
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
              :items-per-page="itemsPerPage"
            >
              <template v-slot:item.index="{ item, index }">
                <span>{{ index + 1 }}</span>
              </template>
              <template v-slot:item.action="{ item }">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="addMember('update', item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <span>Sửa</span>
                </v-tooltip>
                <v-tooltip top >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn @click="deleteCoSo(item)" color="red" text icon class="" v-bind="attrs" v-on="on">
                      <v-icon size="22">mdi-delete</v-icon>
                    </v-btn>
                  </template>
                  <span>Xóa</span>
                </v-tooltip>
              </template>
            </v-data-table>
            <pagination v-if="pageCount" :pageInput="page" :pageCount="pageCount" @tiny:change-page="changePage"></pagination>
          </v-card-text>
      </base-material-card>
      <v-dialog
        max-width="900"
        v-model="dialogAddMember"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title v-if="typeAction === 'add'">Thêm Ủy ban nhân dân</v-toolbar-title>
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
              ref="formAddMember"
              v-model="validFormAdd"
              lazy-validation
            >
                <v-layout wrap>
                    <v-text-field
                        class="flex xs12 md6"
                        v-model="thongTinUbnd.tenCoQuan"
                        outlined
                        label="Tên cơ quan"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md6 pl-2"
                        v-model="thongTinUbnd.maDinhDanh"
                        outlined
                        label="Mã cơ quan"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md12"
                        v-model="thongTinUbnd.diaChiHoatDong"
                        outlined
                        label="Địa chỉ"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-autocomplete
                        class="flex xs12 md4"
                        hide-no-data
                        :items="listTinhThanh"
                        v-model="tinhThanh"
                        item-text="tinhThanhTen"
                        item-value="tinhThanhMa"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Tỉnh/ Thành phố"
                        dense
                    ></v-autocomplete>
                    <v-autocomplete
                        class="flex xs12 md4 pl-2"
                        hide-no-data
                        :items="listQuanHuyen"
                        v-model="quanHuyen"
                        item-text="quanHuyenTen"
                        item-value="quanHuyenMa"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Quận/ Huyện"
                        dense
                    ></v-autocomplete>
                    <v-autocomplete
                        class="flex xs12 md4 pl-2"
                        hide-no-data
                        :items="listXaPhuong"
                        v-model="xaPhuong"
                        item-text="phuongXaTen"
                        item-value="phuongXaMa"
                        clearable
                        :rules="required"
                        required
                        outlined
                        label="Phường/ Xã"
                        dense
                    ></v-autocomplete>
                    <v-text-field
                        class="flex xs12 md6"
                        v-model="thongTinUbnd.nguoiDaiDien"
                        outlined
                        label="Người đại diện"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md6 pl-2"
                        v-model="thongTinUbnd.soDienThoai"
                        outlined
                        label="Số điện thoại"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
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
  import Pagination from './Pagination'
  export default {
    name: 'Users',
    components: {
      'pagination': Pagination
    },
    data () {
      return {
        loading: false,
        loadingData: false,
        dialogAddMember: false,
        thongTinUbnd: {
          uyBanNhanDanChaId: 0,
          maDinhDanh: '',
          tenCoQuan: '',
          tinhThanhMa: '',
          tinhThanhTen: '',
          quanHuyenMa: '',
          quanHuyenTen: '',
          phuongXaMa: '',
          phuongXaTen: '',
          diaChiHoatDong: '',
          nguoiDaiDien: '',
          soDienThoai: ''
        },
        listTinhThanh: [],
        tinhThanh: '',
        listQuanHuyen: [],
        quanHuyen: '',
        listXaPhuong: [],
        xaPhuong: '',
        totalItem: 0,
        page: 0,
        pageCount: 0,
        itemsPerPage: 50,
        typeAction: '',
        coSoUpdate: '',
        items: [],
        validFormAdd: true,
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
            text: 'Mã cơ quan',
            align: 'center',
            value: 'maDinhDanh'
          },
          {
            sortable: false,
            text: 'Tên cơ quan',
            align: 'center',
            value: 'tenCoQuan'
          },
          {
            sortable: false,
            text: 'Địa chỉ',
            align: 'center',
            value: 'diaChiHoatDong'
          },
          {
            sortable: false,
            text: 'Tỉnh/ thành phố',
            value: 'tinhThanhTen'
          },
          {
            sortable: false,
            text: 'Quận/ huyện',
            align: 'center',
            value: 'quanHuyenTen'
          },
          {
            sortable: false,
            text: 'Phường/ xã',
            align: 'center',
            value: 'phuongXaTen'
          },
          {
            sortable: false,
            text: 'Người đại diện',
            align: 'center',
            value: 'nguoiDaiDien'
          },
          {
            sortable: false,
            text: 'Số điện thoại',
            align: 'center',
            value: 'soDienThoai'
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
      vm.getUyBanNhanDan(0)
      vm.getTinhThanh()
    },
    watch: {
      tinhThanh (val) {
        this.thongTinUbnd.tinhThanhMa = val
        this.getQuanHuyen(val)
      },
      quanHuyen (val) {
        this.thongTinUbnd.quanHuyenMa = val
        this.getXaPhuong(val)
      },
      xaPhuong (val) {
        this.thongTinUbnd.phuongXaMa = val 
      },
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
       getUyBanNhanDan (pageIn) {
        let vm = this
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage
        }
        vm.$store.dispatch('getUyBanNhanDan', filter).then(function (result) {
          vm.items = result.hasOwnProperty('data') ? result.data : []
          vm.totalItem = result.hasOwnProperty('total') ? result.total : 0
          vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
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
            vm.thongTinUbnd.maDinhDanh = vm.coSoUpdate.maDinhDanh
            vm.thongTinUbnd.tenCoQuan = vm.coSoUpdate.tenCoQuan
            vm.thongTinUbnd.diaChiHoatDong = vm.coSoUpdate.diaChiHoatDong
            vm.thongTinUbnd.nguoiDaiDien = vm.coSoUpdate.nguoiDaiDien
            vm.tinhThanh = vm.coSoUpdate.tinhThanhMa
            vm.quanHuyen = vm.coSoUpdate.quanHuyenMa
            vm.xaPhuong = vm.coSoUpdate.phuongXaMa
            vm.thongTinUbnd.soDienThoai = vm.coSoUpdate.soDienThoai

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
            vm.thongTinUbnd.tinhThanhMa = vm.tinhThanh
            vm.thongTinUbnd.tinhThanhTen = obj ? obj['tinhThanhTen'] : ''
          }
          if (vm.quanHuyen) {
            let obj = vm.listQuanHuyen.find(function (item) {
              return item.quanHuyenMa == vm.quanHuyen
            })
            vm.thongTinUbnd.quanHuyenMa = vm.quanHuyen
            vm.thongTinUbnd.quanHuyenTen = obj ? obj['quanHuyenTen'] : ''
          }
          if (vm.xaPhuong) {
            let obj = vm.listXaPhuong.find(function (item) {
              return item.phuongXaMa == vm.xaPhuong
            })
            vm.thongTinUbnd.phuongXaMa = vm.xaPhuong
            vm.thongTinUbnd.phuongXaTen = obj ? obj['phuongXaTen'] : ''
          }
          console.log('thongTinUbnd', vm.thongTinUbnd)
        } catch (error) {
          vm.processingAction = false
        }
      },
      deleteCoSo (user) {
        let vm = this
        let x = confirm('Bạn có chắc chắn xóa cơ sở y tế này?')
        if (x) {
          let filter = {
            ids: [user['id']]
          }
          vm.loading = true
          vm.$store.dispatch('deleteUyBanNhanDan', filter).then(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Xóa thành công',
              color: 'success',
            })
            setTimeout(function () {
              vm.getUyBanNhanDan(0)
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
              data: vm.thongTinUbnd
            }
            vm.loading = true
            vm.$store.dispatch('addUyBanNhanDan', filter).then(userCredential => {
              vm.loading = false
              vm.dialogAddMember = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm cơ sở thành công',
                color: 'success',
              })
              vm.getUyBanNhanDan(0)
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
              data: vm.thongTinUbnd
            }
            vm.loading = true
            vm.$store.dispatch('updateUyBanNhanDan', filter).then(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.dialogAddMember = false
              vm.getUyBanNhanDan(0)
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
      changePage (config) {
        let vm = this
        vm.page = config.page
        vm.getUyBanNhanDan(config.page)
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

