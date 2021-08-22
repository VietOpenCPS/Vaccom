import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import $ from 'jquery'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    barColor: 'rgba(0, 0, 0, .8), rgba(0, 0, 0, .8)',
    barImage: '',
    snackbar: {
      show: false,
      text: '',
      color: '',
    },
    drawer: null,
    access_token: null,
    username: '',
    userPermistion: 'guest',
    userProfile: null,
    breakpointName: 'lg',
    indexTab: 0,
    isSigned:  false,
    registrationUpdate: ''
  },
  getters: {
    getIsSigned: (state) => {
      return state.isSigned
    },
    getSnackbar: (state) => {
      return state.snackbar
    },
    getAccessToken: (state) => {
      return state.access_token
    },
    getUsername: (state) => state.username,
    getPermistion: (state) => state.userPermistion,
    getUserProfile: (state) => state.userProfile,
    getBreakpointName: (state) => state.breakpointName,
    getIndexTab: (state) => state.indexTab,
    getRegistrationUpdate: (state) => state.registrationUpdate
  },
  mutations: {
    SET_ISSIGNED (state, payload) {
      state.isSigned = payload
    },
    SET_BAR_IMAGE (state, payload) {
      state.barImage = payload
    },
    SET_DRAWER (state, payload) {
      state.drawer = payload
    },
    SET_LOGIN(state, { access_token, expires_in }) {
      state.access_token = access_token
      state.expires_in = expires_in
    },
    SET_ACCESS_TOKEN(state, token) {
      state.access_token = token
    },
    SET_USER_PERMISTION(state, permistion) {
      state.userPermistion = permistion
    },
    SET_LOGIN_PROFILE(state, payload) {
      if (payload) {
        state.username = payload.displayName
        state.userProfile = payload
      } else {
        state.username = ''
        state.userProfile = {}
      }
    },
    SHOW_SNACKBAR(state, { color, text }) {
      state.snackbar.show = true
      state.snackbar.color = color
      state.snackbar.text = text
    },
    HIDE_SNACKBAR(state) {
      state.snackbar.show = false
    },
    SET_BREAKPOINTNAME(state, breakpointName) {
      state.breakpointName = breakpointName
    },
    SET_INDEXTAB(state, indexTab) {
      state.indexTab = indexTab
      console.log('SET_INDEXTAB', state.indexTab)
    },
    SET_RegistrationUpdate(state, data) {
      state.registrationUpdate = data
    }
  },
  actions: {
    loginApp({ commit }, { username, password }) {
      return new Promise((resolve, reject) => {
        let name = String(username).trim()
        let pass = String(password).trim()
        // let configs = {
        //   headers: {
        //     'Authorization': 'Basic ' + window.btoa(name + ":" + pass)
        //   },
        // }
        // let dataPostApplicant = new URLSearchParams()
        // axios.post('/rest/v1/security/login', dataPostApplicant, configs).then(function (response) {
        //   resolve(response.data)
        // }).catch(function (error) {
        //   reject(error)
        // })
        $.ajax({
          url: 'http://119.17.200.69:8630/rest/v1/security/login',
          type: 'POST',
          headers: {
            'Authorization': 'Basic ' + window.btoa(name + ":" + pass)
          },
          success: function (result) {
            let serializable = result
            resolve(serializable)
          },
          error: function (xhr) {
            reject(xhr)
          }
        })
      })
    },
    register({ commit, dispatch }, data) {
      return request({
        url: '/auth/register',
        method: 'post',
        data: data,
      }).then((resp) => {
        commit('SET_LOGIN', resp)
        dispatch('fetchProfile')
        return resp
      })
    },
    logout({ commit, dispatch }) {
      return new Promise((resolve, reject) => {
        firebase.auth().signOut().then(() => {
          resolve('succsess')
        }).catch((error) => {
          reject('error')
        })
        
      })
      
    },
    // get current login user info
    fetchProfile({ commit, dispatch, rootState }) {
      return request({
        url: '/me',
        method: 'get',
      }).then((resp) => {
        commit('SET_LOGIN_PROFILE', resp.data)
        return resp
      })
    },
    getDiaBanCoSo ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
            page: filter.page,
            size: filter.size
          }
        }
        axios.get('/rest/v1/app/get/diabancoso', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    addCoSoYTe ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        let dataPost = filter.data
        axios.post('/rest/v1/app/add/cosoyte', dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    getCoSoYTe ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
            page: filter.page,
            size: filter.size
          }
        }
        axios.get('/rest/v1/app/get/cosoyte', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    getNhomDoiTuong ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        axios.get('', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    getQuocGia ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        axios.get('/rest/v1/app/get/quocgia', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    getDanToc ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        axios.get('/rest/v1/app/get/dantoc', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    getDanhMucTinhThanh ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        axios.get('/rest/v1/app/get/tinhthanh', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    getDanhMucQuanHuyen ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        axios.get('/rest/v1/app/get/quanhuyen/' + filter.idParent, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    getDanhMucQuanHuyen ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        axios.get('/rest/v1/app/get/quanhuyen/' + filter.idParent, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    getDanhMucXaPhuong ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        axios.get('/rest/v1/app/get/phuongxa/' + filter.idParent, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    createRegistration ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        let dataPost = filter.data
        axios.post('/rest/v1/app/add/nguoitiemchung', dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    updateRegistration ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        let dataPost = filter.data
        axios.put('/rest/v1/app/update/nguoitiemchung/' + filter.id, dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    getNguoiTiemChung ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
            page: 0,
            size: 30
          }
        }
        axios.get('/rest/v1/app/get/nguoitiemchung', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    createNguoiDung ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        let dataPost = filter
        axios.post('/rest/v1/app/add/nguoidung', dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    getNguoiDung ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
            page: filter.page,
            size: filter.size
          }
        }
        axios.get('/rest/v1/app/get/nguoidung', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    updateNguoiDung ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        let dataPost = filter.data
        axios.put('/rest/v1/app/update/nguoidung/' + filter.id, dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
  },
})
