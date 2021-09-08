<template>
  <div class="wrap-page-login">
    <v-container fluid class="page-login" fill-height>
      <v-row>
        <v-col :cols="12">
          <v-card class="pa-3 page-login__card" tile>
            <v-card-title class="mx-3 py-0">
              <div class="image-title-login text-center my-2">
                <!-- <img style="border-radius: 10px;" src="/images/logo.png?t=1619886615424" alt="" height="80" contain /> -->
                <img style="width: auto;height: 52px;border-radius: 10px;" src="/images/logo_banner.png">
              </div>
              <div class="text-title-login white--text text-center mb-3" style="word-break: break-word;">QUẢN LÝ VÀ TỔ CHỨC ĐIỂM TIÊM CHỦNG VACCINE</div>
            </v-card-title>
            <v-card-text class="pb-0">
              <v-form ref="form" v-model="formValid" class="mt-10 mb-5" lazy-validation>
                <v-text-field
                  v-model="formModel.username"
                  append-icon="mdi-email"
                  autocomplete="off"
                  name="login"
                  label="Tên đăng nhập"
                  type="text"
                  required
                  outlined
                  :rules="formRule.username"
                />
                <v-text-field
                  v-model="formModel.password"
                  append-icon="mdi-lock"
                  autocomplete="off"
                  name="password"
                  label="Mật khẩu"
                  type="password"
                  :rules="formRule.password"
                  required
                  outlined
                  @keyup.enter="handleLogin"
                />
              </v-form>
            </v-card-text>
            <v-card-actions class="mx-2 pt-0 row">
              <v-btn class="btn-submit-login" tile color="#0072bc" :loading="loading" @click="handleLogin">
                <v-icon size="20" color="#fff" class="mr-2">mdi-login-variant</v-icon> 
                <span>ĐĂNG NHẬP</span>
              </v-btn>
              <v-btn class="btn-submit-login my-3 mx-0" tile color="#0072bc" @click="goBack">
                <v-icon size="20" color="#fff" class="mr-2">mdi-reply-all</v-icon> 
                <span>QUAY LẠI</span>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
const name = 'page-login'
import axios from 'axios'
export default {
  name: name,
  data() {
    return {
      loading: false,
      formValid: false,
      formModel: {
        username: '',
        password: ''
      },
      formRule: {
        username: [(v) => !!v || this.$t('Thông tin bắt buộc', ['username'])],
        password: [(v) => !!v || this.$t('Thông tin bắt buộc', ['password'])],
      }
    }
  },
  created () {
    let vm = this
    // vm.getUserInfo()
  },
  computed: {},
  methods: {
    handleLogin() {
      let vm = this
      if (vm.$refs.form.validate()) {
        vm.loading = true
        vm.$store.dispatch('loginApp', vm.formModel).then(function(result) {
          vm.$cookies.set('Token',result.access_token,60 * 60 * 10)
          let dataUser = {
            role_name: result.role_name,
            user_id: result.user_id
          }
          localStorage.setItem('user', JSON.stringify(dataUser))
          vm.$store.commit('SET_ISSIGNED', true)
          setTimeout(function () {
            vm.$store.dispatch('getUserInfo', result).then(function(dataInfo) {
              vm.loading = false
              if (dataInfo) {
                dataUser['hoVaTen'] = dataInfo.hoVaTen
                dataUser['uyBanNhanDanId'] = dataInfo.hasOwnProperty('uyBanNhanDanId') ? dataInfo.uyBanNhanDanId : ''
                dataUser['diaBanCoSoId'] = dataInfo.diaBanCoSoId
                dataUser['coSoYTeId'] = dataInfo.coSoYTeId
                dataUser['nguoiTiemChungId'] = dataInfo.nguoiTiemChungId
                localStorage.setItem('user', JSON.stringify(dataUser))
                let redirect = vm.$route.query.redirect
                let route = redirect ? { path: redirect } : { path: '/pages/dang-ky-tiem-moi/0' }
                if (result.role_name === 'CanBoUBND') {
                  route = redirect ? { path: redirect } : { path: '/pages/danh-sach-di-duong-yeu-cau' }
                }
                vm.$router.push(route)
              } else {
                vm.$store.commit('SHOW_SNACKBAR', {
                  show: true,
                  text: 'Không lấy được thông tin tài khoản',
                  color: 'error',
                })
              }
            }).catch(function (error) {
              let redirect = vm.$route.query.redirect
              let route = redirect ? { path: redirect } : { path: '/pages/dang-ky-tiem-moi/0' }
              vm.$router.push(route)
              vm.$store.commit('SHOW_SNACKBAR', {
                show: true,
                text: 'Không lấy được thông tin tài khoản',
                color: 'error',
              })
              vm.loading = false
            })
          }, 300)
          
        }).catch((error) => {
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Tên đăng nhập hoặc mật khẩu không chính xác',
            color: 'error',
          })
          vm.loading = false
        })
      }
    },
    getUserInfo () {
      let vm = this
      try {
        let dataUser = JSON.parse(localStorage.getItem('user'))
        if (!dataUser['user_id']) {
          return
        }
        let filter = {
          user_id: dataUser['user_id']
        }
        vm.$store.dispatch('getUserInfo', filter).then(function(dataInfo) {
          if (dataInfo) {
            vm.$router.push({ path: '/pages/dang-ky-tiem-moi/0' })
          }
        }).catch (function (error) {
          console.log('error', error.status)
        })
      } catch (error) {
        
      }
      
    },
    goBack () {
      window.history.back()
    },
    handleRegister() {
      // console.log(this)
    },
    handleSocialLogin() {},
  },
}
</script>

<style lang="sass" scoped>
.page-login
  &__card
  max-width: 500px
  margin: 0 auto
</style>
<style lang="css" scoped>
.wrap-page-login{
  height: 100vh;
  background-image: url(/images/bg_active.jpg);
  background-position: top;
  background-size: auto
}
.wrap-page-login:before {
  position: absolute;
  z-index: 0;
  width: 100%;
  height: 100%;
  display: block;
  left: 0;
  top: 0;
  content: "";
  background: rgba(0,0,0,.21)
}
.page-login__card {
  border-radius: 6px !important;
}
.v-card__title {
  background: linear-gradient(65deg,#3ec7d3,#2243ad);
  color: #fff;
  margin-top: -55px;
  height: auto;
  border-radius: 6px !important;
}
.text-title-login {
  width: 100%;
  margin-top: -10px;
  margin-bottom: 8px;
  font-size: 16px;
    font-weight: bold;
}
.image-title-login {
  width: 100%;
}
.btn-submit-login {
  font-size: 16px;
  font-weight: bold;
  width: 100%;
  height: 42px !important;
  /* background: linear-gradient(65deg,#49c217,#ff5200); */
  border-radius: 4px;
}
</style>
