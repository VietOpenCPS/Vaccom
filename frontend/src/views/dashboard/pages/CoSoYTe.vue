<template>
  <div v-if="userLogin['role_name'] === 'admin'">
    <v-container
      id="users"
      fluid
      tag="section"
      :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'padding-top: 75px' : ''"
    >
      <base-material-card
        style="margin-top: 20px"
        icon="mdi-clipboard-text"
        title="Danh sách cơ sở y tế"
        class="px-5 py-3"
      >
          <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : ''">
            <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
              <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
                Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> cơ sở y tế
              </div>
              <span class="mr-auto pt-2" v-else>
                Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> cơ sở y tế
              </span>
              <v-btn color="#0072bc" class="mx-0" @click.stop="addMember('add')">
                <v-icon left size="22">
                  mdi-plus
                </v-icon>
                Thêm cơ sở y tế
              </v-btn>
            </div>
            <v-data-table
              :headers="headers"
              :items="items"
              :page.sync="page"
              :items-per-page="itemsPerPage"
              hide-default-footer
              class="elevation-1"
              @page-count="pageCount = $event"
              no-data-text="Không có"
              :loading="loadingData"
              loading-text="Đang tải... "
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
              <v-tooltip top v-if="item['role'] === 'Member'">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn @click="deleteUser(item)" color="red" text icon class="" v-bind="attrs" v-on="on">
                    <v-icon size="22">mdi-delete</v-icon>
                  </v-btn>
                </template>
                <span>Xóa</span>
              </v-tooltip>
            </template>
            
            </v-data-table>
            <div class="text-center mt-4">
              <v-pagination
                v-model="page"
                :length="pageCount"
              ></v-pagination>
            </div>
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
            <v-toolbar-title v-if="typeAction === 'add'">Thêm cơ sở y tế</v-toolbar-title>
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
                        v-model="thongTinCoSo.MaCoSo"
                        outlined
                        label="Mã cơ sở"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md6 pl-2"
                        v-model="thongTinCoSo.TenCoSo"
                        outlined
                        label="Tên cơ sở"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md12"
                        v-model="thongTinCoSo.DiaChiCoSo"
                        outlined
                        label="Địa chỉ cơ sở"
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
                        v-model="thongTinCoSo.NguoiDaiDien"
                        outlined
                        label="Người đại diện"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md6 pl-2"
                        v-model="thongTinCoSo.SoDienThoai"
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
  export default {
    name: 'Users',

    data () {
      return {
        loading: false,
        loadingData: false,
        dialogAddMember: false,
        thongTinCoSo: {
          MaCoSo: '',
          TenCoSo: '',
          TinhThanh_Ma: '',
          TinhThanh_Ten: '',
          QuanHuyen_Ma: '',
          QuanHuyen_Ten: '',
          PhuongXa_Ma: '',
          PhuongXa_Ten: '',
          DiaChiCoSo: '',
          NguoiDaiDien: '',
          SoDienThoai: ''
        },
        listTinhThanh: [],
        tinhThanh: '',
        listQuanHuyen: [],
        quanHuyen: '',
        listXaPhuong: [],
        xaPhuong: '',
        totalItem: 0,
        page: 1,
        pageCount: 0,
        itemsPerPage: 5,
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
        headers: [
          {
            sortable: false,
            text: 'STT',
            align: 'center',
            value: 'index'
          },
          {
            sortable: false,
            text: 'Mã cơ sơ',
            align: 'center',
            value: 'maCoSo'
          },
          {
            sortable: false,
            text: 'Tên cơ sở',
            align: 'center',
            value: 'tenCoSo'
          },
          {
            sortable: false,
            text: 'Địa chỉ cơ sở',
            align: 'center',
            value: 'diaChiCoSo'
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
      vm.getCoSoYTe()
      vm.getTinhThanh()
    },
    watch: {
      tinhThanh (val) {
        this.thongTinCoSo.TinhThanh_Ma = val
        this.getQuanHuyen(val)
      },
      quanHuyen (val) {
        this.thongTinCoSo.QuanHuyen_Ma = val
        this.getXaPhuong(val)
      },
      xaPhuong (val) {
        this.thongTinCoSo.PhuongXa_Ma = val 
      },
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
        vm.userUpdate = user
        vm.dialogAddMember = true
        if (type === 'add') {
          setTimeout(function () {
            vm.$refs.formAddMember.reset()
            vm.$refs.formAddMember.resetValidation()
          }, 200)
        } else {
          setTimeout(function () {
            // diaChiCoSo: "203 Bồ Đề"
            // id: 2
            // maCoSo: "001"
            // nguoiDaiDien: "Nguyễn Văn Nam"
            // phuongXaMa: "00139"
            // phuongXaTen: "Phường Bồ Đề"
            // quanHuyenMa: "004"
            // quanHuyenTen: "Quận Long Biên"
            // soDienThoai: "0897893123"
            // tenCoSo: "Trung tâm y tế phường Bồ Đề"
            // tinhThanhMa: "01"
            // tinhThanhTen: "Thành phố Hà Nội"
            // vm.thongTinCoSo = 
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
      updateStatusUser (type, user) {
        let vm = this
        vm.userUpdate = user
      },
      deleteUser (user) {
        let vm = this
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
              vm.getMembers()
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
              id: '',
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
              vm.getMembers()
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

