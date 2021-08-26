<template>
  <v-container
    id="user-profile"
    fluid
    tag="section"
  >
    <v-row justify="center">
      <v-col
        cols="12"
        md="8"
      >
        <base-material-card>
          <template v-slot:heading>
            <div class="text-h3 font-weight-light">
              Thông tin tài khoản
            </div>
          </template>

          <v-form>
            <v-container class="py-0 mt-5">
              <v-row v-if="userInfo">
                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="userInfo['HoVaTen']"
                    outlined
                    label="Họ và tên"
                    prepend-inner-icon="mdi-card-account-details-outline"
                    dense
                  ></v-text-field>
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="userInfo['ChucDanh']"
                    outlined
                    label="Chức danh"
                    prepend-inner-icon="mdi-account-check-outline"
                    dense
                  ></v-text-field>
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="userInfo['SoDienThoai']"
                    outlined
                    label="Số điện thoại"
                    prepend-inner-icon="mdi-phone-in-talk-outline"
                    dense
                  ></v-text-field>
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="userInfo['Email']"
                    outlined
                    label="Email"
                    prepend-inner-icon="mdi-card-account-mail-outline"
                    dense
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  md="6"
                >
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
                      prepend-inner-icon="mdi-map-marker"
                  ></v-autocomplete>
                </v-col>
                <v-col
                  cols="12"
                  md="6"
                >
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
                </v-col>
              </v-row>
            </v-container>
          </v-form>
          <v-row class="px-3">
            <v-col
              cols="12"
            >
              <v-btn class="mr-4" color="primary" @click="submitUpdateUser">
                <v-icon left>
                  mdi-content-save
                </v-icon>
                <span>Cập nhật thông tin</span>
              </v-btn>
              <v-btn class="mr-2" color="#0072bc" @click="showChangePass">
                <v-icon left>
                  mdi-lock-check-outline
                </v-icon>
                <span>Thay đổi mật khẩu</span>
              </v-btn>
            </v-col>
          </v-row>
          
        </base-material-card>
      </v-col>
    </v-row>

    <v-dialog
      max-width="600"
      v-model="dialogChangePass"
    >
      <v-card>
        <v-toolbar
          dark
          color="#0072bc"
        >
          <v-toolbar-title>Đổi mật khẩu</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn
              icon
              dark
              @click="dialogChangePass = false"
            >
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </v-toolbar-items>
        </v-toolbar>
        <v-card-text class="mt-5">
          <v-form
            ref="formChangePass"
            v-model="validFormChangePass"
            lazy-validation
          >
            <v-text-field
              v-model="currentPassWord"
              :rules="passwordRules"
              required
              outlined
              label="Mật khẩu hiện tại"
              prepend-inner-icon="mdi-lock-check-outline"
              clearable
            ></v-text-field>
            <v-text-field
              v-model="newPassWord"
              :rules="passwordRules"
              required
              outlined
              label="Mật khẩu mới"
              prepend-inner-icon="mdi-lock-check-outline"
              clearable
            ></v-text-field>
          </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
          <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="dialogChangePass = false">
            <v-icon left>
              mdi-close
            </v-icon>
            Thoát
          </v-btn>
          <v-btn class="mr-2" color="#0072bc" :loading="loading" :disabled="loading" @click="submitChangePass">
            <v-icon left>
              mdi-content-save
            </v-icon>
            <span>Xác nhận</span>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
  const name = 'page-userprofile'
  export default {
    name: name,
    data() {
      return {
        listDiaBan: [],
        listCoSoYTe: [],
        coSoYTe: '',
        diaBanCoSo: '',
        dialogChangePass: false,
        userId: '',
        userInfo: {
          HoVaTen: '',
          ChucDanh: '',
          SoDienThoai: '',
          Email: '',
          CoSoYTe_ID: '',
          DiaBanCoSo_ID: '',
          QuanTriHeThong: false
        },
        validFormChangePass: true,
        loading: false,
        currentPassWord: '',
        newPassWord: '',
        passwordRules: [
          v => !!v || 'Mật khẩu là bắt buộc',
          v => (v && v.length >= 6 && v.length <= 75) || 'Mật khẩu ít nhất 6 ký tự',
        ],
      }
    },
    created () {
      let vm = this
      vm.getUserInfo()
      vm.getCoSoYTe()
      vm.getDiaBanCoSo()
    },
    coSoYTe (val) {
      this.userInfo.CoSoYTe_ID = val
      this.getDiaBanCoSo(val)
    },
    computed: {},
    methods: {
      getUserInfo () {
        let vm = this
        let dataUser = JSON.parse(localStorage.getItem('user'))
        vm.userId = dataUser['user_id']
        let filter = {
          user_id: dataUser['user_id']
        }
        vm.$store.dispatch('getUserInfo', filter).then(function(dataInfo) {
          vm.userInfo.HoVaTen = dataInfo.hoVaTen
          vm.userInfo.ChucDanh = dataInfo.chucDanh
          vm.userInfo.SoDienThoai = dataInfo.soDienThoai
          vm.userInfo.Email = dataInfo.email
          vm.coSoYTe = dataInfo.coSoYTeId
          vm.userInfo.DiaBanCoSo_ID = dataInfo.diaBanCoSoId
          vm.userInfo.QuanTriHeThong = dataInfo.quanTriHeThong
        }).catch (function () {
        })
      },
      getDiaBanCoSo (val) {
        let vm = this
        let filter = {
          id: -1
        }
        if (val) {
          let obj = vm.listCoSoYTe.find(function (item) {
            return item.maCoSo == val
          })
          filter = {
            id: obj['id']
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
      submitUpdateUser () {
        let vm = this
        vm.userInfo['CoSoYTe_ID'] = vm.coSoYTe
        let filter = {
          id: vm.userId,
          data: vm.userInfo
        }
        vm.$store.dispatch('updateNguoiDung', filter).then(function (result) {
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Cập nhật thành công',
            color: 'success',
          })
          let dataUser = JSON.parse(localStorage.getItem('user'))
          let dataUpdate = Object.assign(dataUser, {
            coSoYTeId: vm.userInfo['CoSoYTe_ID'],
            diaBanCoSoId: vm.userInfo['DiaBanCoSo_ID'],
            hoVaTen: vm.userInfo['HoVaTen']
          })
          localStorage.setItem('user', JSON.stringify(dataUpdate))
          
        }).catch(function () {
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Cập nhật thất bại',
            color: 'error',
          })
        })
      },
      showChangePass () {
        let vm = this
        vm.dialogChangePass = true
        setTimeout(function () {
          vm.currentPassWord = ''
          vm.newPassWord = ''
          vm.$refs.formChangePass.resetValidation()
        }, 100)
      },
      submitChangePass () {
        let vm = this
        if (vm.$refs.formChangePass.validate()) {
          let filter = {
            id: vm.userId,
            data: {
              MatKhauMoi: vm.newPassWord,
              MatKhauCu: vm.currentPassWord
            }
          }
          vm.$store.dispatch('changePassNguoiDung', filter).then(function (result) {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Cập nhật thành công. Đăng nhập với mật khẩu mới',
              color: 'success',
            })
            setTimeout(function () {
              vm.$store.commit('SET_ISSIGNED', false)
              if (localStorage.getItem('user')) {
                localStorage.setItem('user', null)
              }
              vm.$cookies.set('Token','')
              vm.$router.push({ path: '/login' })
            }, 500)
          }).catch(function () {
            vm.$store.commit('SHOW_SNACKBAR', {
              show: true,
              text: 'Cập nhật thất bại',
              color: 'error',
            })
          })
        }
      },
      goBack () {
        this.$router.push({ path: '/' })
      }
    },
  }
</script>
