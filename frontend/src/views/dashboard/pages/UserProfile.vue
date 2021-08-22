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
                    v-model="userInfo['userName']"
                    outlined
                    label="Tên tài khoản"
                    prepend-inner-icon="mdi-card-account-details-outline"
                    readonly
                  ></v-text-field>
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="userInfo['account']"
                    outlined
                    label="Tên đăng nhập"
                    prepend-inner-icon="mdi-account-check-outline"
                    readonly
                  ></v-text-field>
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="userInfo['telNo']"
                    outlined
                    label="Số điện thoại"
                    prepend-inner-icon="mdi-phone-in-talk-outline"
                    readonly
                  ></v-text-field>
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    :value="userInfo['role'] == 'Admin' ? 'Quản trị' : 'Cán bộ cơ sở'"
                    outlined
                    label="Loại tài khoản"
                    readonly
                  ></v-text-field>
                </v-col>

                <v-col
                  cols="12"
                >
                  <v-text-field
                    v-model="userInfo['address']"
                    outlined
                    label="Địa chỉ"
                    prepend-inner-icon="mdi-map-marker"
                    readonly
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                >
                  <v-btn class="mr-2" color="primary" @click="showChangePass">
                    <v-icon left>
                      mdi-lock-check-outline
                    </v-icon>
                    <span>Đổi mật khẩu đăng nhập</span>
                  </v-btn>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
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
          color="primary"
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
          <v-btn class="mr-2" color="primary" :loading="loading" :disabled="loading" @click="submitChangePass">
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
        dialogChangePass: false,
        userInfo: '',
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
    },
    computed: {},
    methods: {
      getUserInfo () {
        let vm = this
        // async function getUid() {
        //   let curr = await firebase.auth().currentUser
        //   // console.log('curr', curr)
        //   let uidad = curr.uid
        //   let infoad = db.collection("users").doc(uidad)
        //   infoad.get().then((querySnapshot) => {
        //     if (querySnapshot.exists) {
        //       vm.userInfo = querySnapshot.data()
        //       // console.log('userInfo',vm.userInfo)
        //     }
        //   }).catch((error) => {
        //   })
        // }
        // getUid()
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
          async function changePass() {
            var user = await firebase.auth().currentUser
            var email = await user.email
            var credentials = firebase.auth.EmailAuthProvider.credential(
              email,
              String(vm.currentPassWord).trim()
            )
            vm.loading = true
            user.reauthenticateWithCredential(credentials).then(() => {
              let newPassword = String(vm.newPassWord).trim()
              user.updatePassword(newPassword).then(() => {
                // Update successful.
                vm.userInfo['pid'] = window.btoa(newPassword)
                db.collection("users").doc(vm.userInfo.uid).set(vm.userInfo).then(() => {
                  setTimeout(function () {
                    vm.logout()
                  }, 300)
                }).catch(() => {
                  setTimeout(function () {
                    vm.logout()
                  }, 300)
                })
                vm.$store.commit('SHOW_SNACKBAR', {
                  show: true,
                  text: 'Đổi mật khẩu thành công. Vui lòng đăng nhập lại với mật khẩu mới',
                  color: 'success',
                })
                vm.dialogChangePass = false
                vm.loading = false
              }).catch((error) => {
                vm.$store.commit('SHOW_SNACKBAR', {
                  show: true,
                  text: 'Đổi mật khẩu thất bại',
                  color: 'error',
                })
                vm.loading = false
              })
            }).catch((error) => {
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Mật khẩu hiện tại không chính xác. Vui lòng kiểm tra lại',
                color: 'error',
              })
              vm.loading = false
            })
          }
          changePass()
        }
      },
      logout () {
        this.$store.dispatch('logout').then(() => {
          this.$router.push(
            {
              path: '/login'
            }
          )
        }).catch(() => {
          this.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Lỗi đăng xuất hệ thống',
            color: 'error',
          })
        })
      },
      goBack () {
        this.$router.push({ path: '/' })
      }
    },
  }
</script>
