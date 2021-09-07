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
        title="Danh sách địa bàn cơ sở"
        class="px-5 py-3"
      >
          <v-flex class="mx-4 mt-3">
            <div class="mb-3">Chọn cơ sở y tế</div>
            <v-autocomplete
              class="flex xs12 md12"
              hide-no-data
              :items="listCoSoYTe"
              v-model="coSoYTeSearch"
              item-text="tenCoSo"
              item-value="id"
              outlined
              dense
              hide-details="auto"
              clearable
            ></v-autocomplete>
          </v-flex>
          <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : ''">
            <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
              <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
                Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> địa bàn
              </div>
              <span class="mr-auto pt-2" v-else>
                Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> địa bàn
              </span>
              <v-btn color="#0072bc" class="mx-0" @click.stop="addMember('add')">
                <v-icon left size="22">
                  mdi-plus
                </v-icon>
                Thêm địa bàn
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
                  <v-btn @click.stop="addMember('update', item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                    <v-icon size="22">mdi-pencil</v-icon>
                  </v-btn>
                </template>
                <span>Sửa</span>
              </v-tooltip>
              <!-- <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn @click="deleteUser(item)" color="red" text icon class="" v-bind="attrs" v-on="on">
                    <v-icon size="22">mdi-delete</v-icon>
                  </v-btn>
                </template>
                <span>Xóa</span>
              </v-tooltip> -->
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
            <v-toolbar-title v-if="typeAction === 'add'">Thêm địa bàn cơ sở</v-toolbar-title>
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
                        class="flex xs12 md12"
                        v-model="thongTinDiaBan.TenDiaBan"
                        outlined
                        :rules="required"
                        required
                        label="Tên địa bàn"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-autocomplete
                        class="flex xs12 md12"
                        hide-no-data
                        :items="listCoSoYTe"
                        v-model="coSoYTe"
                        item-text="tenCoSo"
                        item-value="id"
                        clearable
                        outlined
                        label="Cơ sở y tế"
                        dense
                    ></v-autocomplete>
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
                        hide-details="auto"
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
                        hide-details="auto"
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
        coSoYTeSearch: '',
        coSoYTe: '',
        thongTinDiaBan: {
            TenDiaBan: '',
            TinhThanh_Ma: '',
            TinhThanh_Ten: '',
            QuanHuyen_Ma: '',
            QuanHuyen_Ten: '',
            PhuongXa_Ma: '',
            PhuongXa_Ten: '',
            CoSoYTe_ID: ''
        },
        listCoSoYTe: [],
        listTinhThanh: [],
        tinhThanh: '',
        listQuanHuyen: [],
        quanHuyen: '',
        listXaPhuong: [],
        xaPhuong: '',
        totalItem: 0,
        page: 0,
        pageCount: 0,
        itemsPerPage: 30,
        typeAction: '',
        userUpdate: '',
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
        diaBanUpdate: '',
        headers: [
          {
            sortable: false,
            text: 'STT',
            align: 'center',
            value: 'index'
          },
          {
            sortable: false,
            text: 'Tên địa bàn',
            align: 'center',
            value: 'tenDiaBan'
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
            text: 'Thao tác',
            align: 'center',
            value: 'action'
          },
        ],
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 4)
      vm.getCoSoYTe()
      vm.getDiaBanCoSo()
      vm.getTinhThanh()
    },
    watch: {
      tinhThanh (val) {
        this.thongTinDiaBan.TinhThanh_Ma = val
        this.getQuanHuyen(val)
      },
      quanHuyen (val) {
        this.thongTinDiaBan.QuanHuyen_Ma = val
        this.getXaPhuong(val)
      },
      xaPhuong (val) {
        this.thongTinDiaBan.PhuongXa_Ma = val 
      },
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
       getDiaBanCoSo (pageIn, idCoSo) {
        let vm = this
        let filter = {
          id: idCoSo ? idCoSo : -1,
          page: pageIn,
          size: vm.itemsPerPage
        }
        vm.$store.dispatch('getDiaBanCoSo', filter).then(function (result) {
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
          // if (vm.listCoSoYTe.length) {
          //   vm.coSoYTeSearch = vm.listCoSoYTe[0]['id']
          // }
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
        vm.dialogAddMember = true
        if (type === 'add') {
          setTimeout(function () {
            vm.$refs.formAddMember.reset()
            vm.$refs.formAddMember.resetValidation()
            if (vm.coSoYTeSearch) {
              vm.coSoYTe = vm.coSoYTeSearch
              // vm.tinhThanh = '01'
              // vm.quanHuyen = '004'
              // vm.xaPhuong = '00148'
            }
          }, 200)
        } else {
          vm.diaBanUpdate = user
          setTimeout(function () {
            vm.coSoYTe = vm.diaBanUpdate.coSoYTeId
            vm.thongTinDiaBan.TenDiaBan = vm.diaBanUpdate.tenDiaBan
            vm.tinhThanh = vm.diaBanUpdate.tinhThanhMa
            vm.quanHuyen = vm.diaBanUpdate.quanHuyenMa
            vm.xaPhuong = vm.diaBanUpdate.phuongXaMa
            vm.$refs.formAddMember.resetValidation()
          }, 200)
        }
        
      },

      deleteUser (user) {
        let vm = this
      },
      formatDataInput () {
        let vm = this
        try {
          if (vm.tinhThanh) {
            let obj = vm.listTinhThanh.find(function (item) {
              return item.tinhThanhMa == vm.tinhThanh
            })
            vm.thongTinDiaBan.TinhThanh_Ma = vm.tinhThanh
            vm.thongTinDiaBan.TinhThanh_Ten = obj ? obj['tinhThanhTen'] : ''
          }
          if (vm.quanHuyen) {
            let obj = vm.listQuanHuyen.find(function (item) {
              return item.quanHuyenMa == vm.quanHuyen
            })
            vm.thongTinDiaBan.QuanHuyen_Ma = vm.quanHuyen
            vm.thongTinDiaBan.QuanHuyen_Ten = obj ? obj['quanHuyenTen'] : ''
          }
          if (vm.xaPhuong) {
            let obj = vm.listXaPhuong.find(function (item) {
              return item.phuongXaMa == vm.xaPhuong
            })
            vm.thongTinDiaBan.PhuongXa_Ma = vm.xaPhuong
            vm.thongTinDiaBan.PhuongXa_Ten = obj ? obj['phuongXaTen'] : ''
          }
          vm.thongTinDiaBan.CoSoYTe_ID = vm.coSoYTe
          console.log('thongTinDiaBan', vm.thongTinDiaBan)
        } catch (error) {
          vm.processingAction = false
        }
      },
      submitAddMember () {
        let vm = this
        if (vm.$refs.formAddMember.validate()) {
          vm.formatDataInput()
          if (vm.typeAction === 'add') {
            let filter = {
              data: vm.thongTinDiaBan
            }
            vm.loading = true
            vm.$store.dispatch('addDiaBanCoSo', filter).then(userCredential => {
              vm.loading = false
              vm.dialogAddMember = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm địa bàn thành công',
                color: 'success',
              })
              vm.getDiaBanCoSo(0, vm.coSoYTeSearch)
            })
            .catch((error) => {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm địa bàn y tế không thành công',
                color: 'error',
              })
              // ..
            });
          } else {
            
            let filter = {
              id: vm.diaBanUpdate['id'],
              data: vm.thongTinDiaBan
            }
            vm.loading = true
            vm.$store.dispatch('updateDiaBanCoSo', filter).then(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.dialogAddMember = false
              vm.getDiaBanCoSo(0, vm.coSoYTeSearch)
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
        vm.getDiaBanCoSo(config.page, vm.coSoYTeSearch)
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

