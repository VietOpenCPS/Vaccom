<template>
  <div v-if="userLogin['role_name'] === 'QuanTriHeThong' || userLogin['role_name'] === 'QuanTriCoSo'">
    <v-container
      id="users"
      fluid
      tag="section"
      :style="breakpointName === 'xs' || breakpointName === 'sm' ? 'padding-top: 75px' : ''"
    >
      <base-material-card
        style="margin-top: 20px"
        icon="mdi-clipboard-text"
        title="Danh sách người dùng"
        class="px-5 py-3"
      >
          <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : ''">
            <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
              <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
                Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người dùng
              </div>
              <span class="mr-auto pt-2" v-else>
                Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người dùng
              </span>
              <v-btn color="#0072bc" class="mx-0" @click.stop="addMember('add')">
                <v-icon left size="22">
                  mdi-plus
                </v-icon>
                Thêm người dùng
              </v-btn>
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
            <template v-slot:item.index="{ item, index }">
              <!-- <span>{{ index + 1 }}</span> -->
              <span>{{ (page+1) * itemsPerPage - itemsPerPage + index + 1 }}</span>
            </template>
            <template v-slot:item.quanTriHeThong="{ item }">
              <span style="color: blue" v-if="item.quanTriHeThong == 0">Người dùng</span>
              <span style="color: red" v-if="item.quanTriHeThong == 1">Quản trị hệ thống</span>
              <span style="color: orange" v-if="item.quanTriHeThong == 2">Quản trị địa bàn</span>
            </template>
            <template v-slot:item.khoaTaiKhoan="{ item }">
              <span style="color: red" v-if="item.khoaTaiKhoan">Đang khóa</span>
              <span style="color: blue" v-else>Đang mở</span>
            </template>
            <template v-slot:item.action="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn @click="addMember('update', item)" color="blue" text icon class="" v-bind="attrs" v-on="on">
                    <v-icon size="22">mdi-pencil</v-icon>
                  </v-btn>
                </template>
                <span>Cập nhật thông tin</span>
              </v-tooltip>
              <v-tooltip top v-if="!item['khoaTaiKhoan'] && userLogin['role_name'] === 'QuanTriHeThong'">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn @click="updateStatusUser('block', item)" color="orange" text icon class="" v-bind="attrs" v-on="on">
                    <v-icon size="22">mdi-lock-check</v-icon>
                  </v-btn>
                </template>
                <span>Khóa tài khoản</span>
              </v-tooltip>
              <v-tooltip top v-if="item['khoaTaiKhoan'] && userLogin['role_name'] === 'QuanTriHeThong'">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn @click="updateStatusUser('open', item)" color="green" text icon class="" v-bind="attrs" v-on="on">
                    <v-icon size="22">mdi-lock-open-variant-outline</v-icon>
                  </v-btn>
                </template>
                <span>Mở khóa tài khoản</span>
              </v-tooltip>
              <v-tooltip top v-if="userLogin['role_name'] === 'QuanTriHeThong'">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn @click="showPhanQuyen(item)" color="red" text icon class="" v-bind="attrs" v-on="on">
                    <v-icon size="22">mdi-account-cog-outline</v-icon>
                  </v-btn>
                </template>
                <span>Phân quyền</span>
              </v-tooltip>
            </template>
            
            </v-data-table>
            <pagination v-if="pageCount" :pageInput="page" :pageCount="pageCount" @tiny:change-page="changePage"></pagination>
          </v-card-text>
      </base-material-card>
      <v-dialog
        max-width="700"
        v-model="dialogAddMember"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title v-if="typeAction === 'add'">Thêm người dùng</v-toolbar-title>
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
                        v-if="typeAction === 'add'"
                        v-model="userInfo.TenDangNhap"
                        :rules="required"
                        required
                        outlined
                        label="Tên đăng nhập"
                        prepend-inner-icon="mdi-account-check-outline"
                        dense
                        clearable
                    ></v-text-field>

                    <v-text-field
                        class="flex xs12 md6 pl-2"
                        v-if="typeAction === 'add'"
                        v-model="userInfo.MatKhau"
                        :rules="passwordRules"
                        required
                        outlined
                        label="Mật khẩu đăng nhập"
                        prepend-inner-icon="mdi-lock-check-outline"
                        dense
                        clearable
                    ></v-text-field>

                    <v-text-field
                        class="flex xs12 md6"
                        v-model="userInfo.HoVaTen"
                        :rules="nameRules"
                        required
                        outlined
                        label="Tên người dùng"
                        prepend-inner-icon="mdi-card-account-details-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md6 pl-2"
                        v-model="userInfo.ChucDanh"
                        outlined
                        label="Chức danh"
                        prepend-inner-icon="mdi-card-account-details-outline"
                        dense
                        clearable
                    ></v-text-field>
                    <v-text-field
                        class="flex xs12 md6"
                        v-model="userInfo.SoDienThoai"
                        :rules="telNoRules"
                        outlined
                        label="Số điện thoại"
                        prepend-inner-icon="mdi-phone-in-talk-outline"
                        dense
                        clearable
                    ></v-text-field>

                    <v-text-field
                        class="flex xs12 md6 pl-2"
                        v-model="userInfo.Email"
                        outlined
                        label="Email"
                        prepend-inner-icon="mdi-card-account-details-outline"
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
                        outlined
                        label="Cơ sở y tế quản lý"
                        dense
                        clearable
                        prepend-inner-icon="mdi-map-marker"
                    ></v-autocomplete>
                    <v-autocomplete
                        class="flex xs12 md12"
                        hide-no-data
                        :items="listDiaBan"
                        v-model="userInfo['DiaBanCoSo_ID']"
                        item-text="tenDiaBan"
                        item-value="id"
                        clearable
                        outlined
                        label="Địa bàn cơ sở quản lý"
                        dense
                        prepend-inner-icon="mdi-map-marker"
                    ></v-autocomplete>
                    <v-autocomplete
                        :rules="required"
                        required
                        v-if="typeAction === 'add' && userLogin['role_name'] === 'QuanTriHeThong'"
                        class="flex xs12 md12"
                        hide-no-data
                        :items="listVaiTro"
                        v-model="userInfo['QuanTriHeThong']"
                        item-text="name"
                        item-value="value"
                        clearable
                        outlined
                        label="Loại tài khoản"
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

      <v-dialog
        max-width="700"
        v-model="dialogAssign"
      >
        <v-card>
          <v-toolbar
            dark
            color="#0072bc"
          >
            <v-toolbar-title>Phân quyền vai trò</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialogAssign = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="mt-5">
            <v-form
              ref="formAssign"
              v-model="validFormAssign"
              lazy-validation
            >
                <v-layout wrap>
                  <v-autocomplete
                      class="flex xs12 md12"
                      hide-no-data
                      :items="listVaiTro"
                      v-model="vaiTro"
                      item-text="name"
                      item-value="value"
                      clearable
                      outlined
                      label="Loại tài khoản"
                      dense
                      :rules="required"
                      required
                  ></v-autocomplete>
                </v-layout>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="dialogAssign = false">
              <v-icon left>
                mdi-close
              </v-icon>
              Thoát
            </v-btn>
            <v-btn class="mr-2" color="#0072bc" :loading="loading" :disabled="loading" @click="assignRole">
              <v-icon left>
                mdi-content-save
              </v-icon>
              <span>Cập nhật</span>
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
        listDiaBan: [],
        listCoSoYTe: [],
        vaiTro: 0,
        dialogAssign: false,
        listVaiTro: [
          {name: 'Người dùng', value: 0},
          {name: 'Quản trị hệ thống', value: 1},
          {name: 'Quản trị địa bàn', value: 2}
        ],
        userInfo: {
          TenDangNhap: '',
          HoVaTen: '',
          ChucDanh: '',
          SoDienThoai: '',
          Email: '',
          MatKhau: '',
          DiaBanCoSo_ID: '',
          CoSoYTe_ID: '',
          VaiTro: 0,
          KhoaTaiKhoan: false
        },
        totalItem: 0,
        page: 0,
        pageCount: 0,
        itemsPerPage: 30,
        typeAction: '',
        userUpdate: '',
        items: [],
        validFormAdd: true,
        validFormAssign: true,
        userName: '',
        coSoYTe: '',
        diaBanCoSo: '',
        nameRules: [
          v => !!v || 'Tên người dùng là bắt buộc'
        ],
        account: '',
        accountRules: [
          v => !!v || 'Tên đăng nhập là bắt buộc',
          v => /^[a-zA-Z0-9]+$/.test(v) || 'Tên đăng nhập gồm chữ hoặc số',
          v => (v && v.length <= 75) || 'Tên đăng nhập không quá 75 ký tự',
        ],
        passWord: '',
        passwordRules: [
          v => !!v || 'Mật khẩu đăng nhập là bắt buộc',
          v => (v && v.length >= 6 && v.length <= 75) || 'Mật khẩu ít nhất 6 ký tự',
        ],
        telNo: '',
        telNoRules: [
          // v => !!v || 'Số điện thoại là bắt buộc',
          // v => /^0([1-9]{1}\d{8})$/.test(v) || 'Số điện thoại gồm 10 chữ số',
        ],
        address: '',
        addressRules: [
          v => (v && v.length <= 200) || 'Địa chỉ không quá 200 ký tự',
        ],
        required: [
          v => (!!v || v==0) || 'Thông tin bắt buộc'
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
            text: 'Họ và tên',
            align: 'left',
            value: 'hoVaTen'
          },
          {
            sortable: false,
            text: 'Tên đăng nhập',
            value: 'tenDangNhap'
          },
          {
            sortable: false,
            text: 'Số điện thoại',
            align: 'center',
            value: 'soDienThoai'
          },
          {
            sortable: false,
            text: 'Chức danh',
            align: 'center',
            value: 'chucDanh'
          },
          {
            sortable: false,
            text: 'Cơ sở y tế',
            align: 'left',
            value: 'coSoYTeId'
          },
          {
            sortable: false,
            text: 'Địa bàn',
            align: 'left',
            value: 'diaBanCoSoId'
          },
          {
            sortable: false,
            text: 'Loại tài khoản',
            align: 'center',
            value: 'quanTriHeThong'
          },
          {
            sortable: false,
            text: 'Trạng thái tài khoản',
            align: 'center',
            value: 'khoaTaiKhoan'
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
      if (vm.userLogin['role_name'] === 'QuanTriCoSo') {
        vm.listVaiTro = [
          {name: 'Người dùng', value: 0}
        ]
      }
      vm.getMembers(0)
      vm.getCoSoYTe()
      vm.getDiaBanCoSo()
    },
    watch: {
      coSoYTe (val) {
        this.userInfo.CoSoYTe_ID = val
        this.getDiaBanCoSo(val)
      },
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      getDiaBanCoSo (val) {
        let vm = this
        let filter = {
          id: -1
        }
        if (val) {
          filter = {
            id: val
          }
        }
        
        vm.$store.dispatch('getDiaBanCoSo', filter).then(function (result) {
          if (result.hasOwnProperty('data') && result.data.length) {
            vm.listDiaBan = result.data
          }
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
      getMembers (pageIn) {
        let vm = this
        vm.loadingData = true
        let filter = {
          page: pageIn,
          size: vm.itemsPerPage,
        }
        vm.$store.dispatch('getNguoiDung', filter).then(function (result) {
          vm.loadingData = false
          if (result) {
            vm.items = result.hasOwnProperty('data') ? result.data : []
            vm.totalItem = result.hasOwnProperty('total') ? result.total : 0
            vm.pageCount = Math.ceil(vm.totalItem / vm.itemsPerPage)
          } else {
            vm.items = []
            vm.totalItem = 0
            vm.pageCount = 0
          }
        }).catch(function () {
          vm.items = []
          vm.totalItem = 0
          vm.pageCount = 0
          vm.loadingData = false
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
          }, 200)
        } else {
          setTimeout(function () {
            vm.userUpdate = user
            vm.userInfo = {}
            vm.userInfo.HoVaTen = user.hoVaTen
            vm.userInfo.ChucDanh = user.chucDanh
            vm.userInfo.SoDienThoai = user.soDienThoai
            vm.userInfo.Email = user.email
            vm.userInfo.DiaBanCoSo_ID = user.diaBanCoSoId
            vm.coSoYTe = user.coSoYTeId
            vm.userInfo.VaiTro = user.quanTriHeThong
            vm.$refs.formAddMember.resetValidation()
          }, 200)
        }
        
      },
      updateStatusUser (type, user) {
        let vm = this
        vm.userUpdate = user
        let filter = vm.userUpdate
        filter['block'] = type === 'open' ? false : true
        vm.$store.dispatch('changeBlockUser', filter).then(userCredential => {
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Cập nhật thành công',
            color: 'success',
          })
          vm.getMembers(0)
        })
      },
      showPhanQuyen (item) {
        let vm = this
        vm.userUpdate = item
        vm.vaiTro = item.quanTriHeThong
        vm.dialogAssign = true
      },
      assignRole () {
        let vm = this
        if (vm.$refs.formAssign.validate()) {
          let filter = Object.assign(vm.userUpdate, {QuanTriHeThong: vm.vaiTro})
          vm.$store.dispatch('assignRole', filter).then(userCredential => {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Phân quyền thành công',
              color: 'success',
            })
            vm.dialogAssign = false
            vm.getMembers(0)
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Phân quyền thất bại',
              color: 'error',
            })
          })
        }
      },
      assignAdmin (item) {
        let vm = this
        let x = confirm('Bạn có chắc chắn cấp quyền Admin cho tài khoản này?')
        if (x) {
          let filter = item
          vm.$store.dispatch('assignAdmin', filter).then(userCredential => {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Cấp quyền thành công',
              color: 'success',
            })
            vm.getMembers(0)
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Cấp quyền thất bại',
              color: 'error',
            })
          })
        }
      },
      changePage (config) {
        let vm = this
        vm.page = config.page
        vm.getMembers(config.page)
      },
      deleteUser (user) {
        let vm = this
      },
      submitAddMember () {
        let vm = this
        if (vm.$refs.formAddMember.validate()) {
          if (vm.typeAction === 'add') {
            let filter = vm.userInfo
            vm.loading = true
            vm.$store.dispatch('createNguoiDung', filter).then(userCredential => {
              vm.loading = false
              vm.dialogAddMember = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Thêm người dùng thành công',
                color: 'success',
              })
              vm.getMembers(0)
            })
            .catch((error) => {
              vm.loading = false
              console.log()
              let errorCode = error.code;
              let errorMessage = error.message;
              let mess = ''
              if (error.response && error.response.data == 'tendangnhap.exist') {
                mess = 'Tên đăng nhập đã được sử dụng. Vui lòng sử dụng tên đăng nhập khác.'
              } else {
                mess = errorMessage
              }
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: mess,
                color: 'error',
              })
              // ..
            });
          } else {
            let filter = {
              id: vm.userUpdate.id,
              data: vm.userInfo
            }
            vm.loading = true
            vm.$store.dispatch('updateNguoiDung', filter).then(function () {
              vm.loading = false
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Cập nhật thành công',
                color: 'success',
              })
              vm.dialogAddMember = false
              vm.getMembers(0)
            }).catch(function () {
              vm.loading = false
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

