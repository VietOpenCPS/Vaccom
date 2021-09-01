import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/base'
import './plugins/chartist'
import './plugins/vee-validate'
import vuetify from './plugins/vuetify'
import i18n from './i18n'
import { VueMaskDirective } from 'v-mask'
import VueCookies from 'vue-cookies'
import axios from 'axios'
Vue.use(VueCookies)
Vue.directive('mask', VueMaskDirective)

const baseURL = 'http://119.17.200.69:8630'
// const baseURL = 'https://vaccom.vn:8080'
if (typeof baseURL !== 'undefined') {
  axios.defaults.baseURL = baseURL
}
if (Vue.$cookies.get('Token')) {
  store.commit('SET_ISSIGNED', true)
} else {
  store.commit('SET_ISSIGNED', false)
  localStorage.setItem('user', null)
}
Vue.mixin({
  data: () => ({
  }),
  methods: {
  },
  computed: {
    isSigned () {
      return this.$store.getters.getIsSigned
    },
    userLogin () {
      let userInfo = ''
      try {
        userInfo = JSON.parse(localStorage.getItem('user'))
      } catch (error) {
      }
      return userInfo
    }
  }
})
new Vue({
  router,
  store,
  vuetify,
  i18n,
  beforeCreate () {
    
  },
  render: h => h(App),
}).$mount('#app')
