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
        let configs = {
          headers: {
            'Authorization': 'Basic ' + window.btoa(name + ":" + pass)
          },
        }
        let dataPostApplicant = new URLSearchParams()
        axios.post('/rest/v1/security/login', dataPostApplicant, configs).then(function (response) {
          resolve(response.data)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    getUserInfo ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        if (!filter.user_id) {
          reject('')
          return
        }
        axios.get('/rest/v1/app/get/nguoidung/' + filter.user_id, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
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
    getDiaBanCoSo ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
            cosoyteid: filter.id
          }
        }
        if (filter.hasOwnProperty('page')) {
          param.params['page'] = filter.page
          param.params['size'] = filter.size
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        axios.get('/rest/v1/app/get/diabancoso', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    addDiaBanCoSo ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
            Authorization: 'Bearer ' + Vue.$cookies.get('Token')
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = filter.data
        axios.post('/rest/v1/app/add/diabancoso', dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    updateDiaBanCoSo ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = filter.data
        axios.put('/rest/v1/app/update/diabancoso/' + filter.id, dataPost, param).then(function (response) {
          let serializable = response
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
    updateCoSoYTe ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = filter.data
        axios.put('/rest/v1/app/update/cosoyte/' + filter.id, dataPost, param).then(function (response) {
          let serializable = response
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    deleteCoSoYTe ({commit, state}, filter) {
      let param = {
        headers: {
        },
        params: {
        }
      }
      try {
        if (Vue.$cookies.get('Token')) {
          param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
        }
      } catch (error) {
      }
      axios.delete('/rest/v1/app/delete/cosoyte/' + filter.id, param).then(function (response) {
        let serializable = response
        resolve(serializable)
      }).catch(function (error) {
        reject(error)
      })
    },
    getCoSoYTe ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        if (filter.hasOwnProperty('page')) {
          param.params['page'] = filter.page
          param.params['size'] = filter.size
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        axios.get('/rest/v1/app/get/cosoyte', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    addLichTiem ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = filter.data
        axios.post('/rest/v1/app/add/lichtiemchung', dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    addCaTiem ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = filter.data
        axios.post('/rest/v1/app/add/catiemchung', dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    updateLichTiem ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = filter.data
        axios.put('/rest/v1/app/update/lichtiemchung/' + filter.id, dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    getLichTiem ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        if (filter.hasOwnProperty('page')) {
          param.params['page'] = filter.page
          param.params['size'] = filter.size
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        axios.get('/rest/v1/app/get/lichtiemchung', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    getChiTietLichTiem ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        axios.get('/rest/v1/app/get/lichtiemchung/' + filter.id, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject('')
        })
      })
    },
    getCaTiem ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          },
          params: {
            lichtiemchungid: filter.lichtiemchungid,
            diabancosoid: filter.diabancosoid ? filter.diabancosoid : ''
          }
        }
        if (filter.hasOwnProperty('page')) {
          param.params['page'] = filter.page
          param.params['size'] = filter.size
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        axios.get('/rest/v1/app/get/catiemchung', param).then(function (response) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        axios.get('/rest/v1/app/get/doituong', param).then(function (response) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
            cmtcccd: filter.hasOwnProperty('cmtcccd') ? filter.cmtcccd : -1,
            nhomdoituong: filter.hasOwnProperty('nhomdoituong') ? filter.nhomdoituong : -1,
            ngaydangki: filter.hasOwnProperty('ngaydangki') ? filter.ngaydangki : -1,
            hovaten: filter.hasOwnProperty('hovaten') ? filter.hovaten : -1,
            diabancosoid: filter.hasOwnProperty('diabancosoid') ? filter.diabancosoid : -1,
            cosoytema: filter.hasOwnProperty('cosoytema') ? filter.cosoytema : -1,
            tinhtrangdangky: filter.hasOwnProperty('tinhtrangdangky') ? filter.tinhtrangdangky : '',
            kiemtratrung: filter.hasOwnProperty('kiemtratrung') ? filter.kiemtratrung : '',
          }
        }
        if (filter.hasOwnProperty('page')) {
          param.params['page'] = filter.page
          param.params['size'] = filter.size
        }

        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        // axios.get('/rest/v1/app/get/nguoitiemchung', param).then(function (response) {
        axios.get('/rest/v1/app/get/search/nguoitiemchung', param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject([])
        })
      })
    },
    updateRegistrationStatus ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = filter.data
        axios.put('/rest/v1/app/update/nguoitiemchung/tinhtrangdangky', dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    removeRegistrationStatus ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        axios.delete('/rest/v1/app/delete/nguoitiemchung/' + filter.id, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    createNguoiDung ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
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
    changePassNguoiDung ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = filter.data
        axios.put('/rest/v1/app/changepwd/' + filter.id, dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    changeBlockUser ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
          }
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = {}
        axios.put('/rest/v1/app/lock/nguoidung/' + filter.id + '/' + filter.block, dataPost, param).then(function (response) {
          let serializable = response.data
          resolve(serializable)
        }).catch(function (error) {
          reject(error)
        })
      })
    },
    exportDanhSach ({commit, state}, filter) {
      return new Promise((resolve, reject) => {
        let param = {
          headers: {
            'Content-Type': 'application/octet-stream'
          },
          params: filter.data,
          responseType: 'blob'
        }
        try {
          if (Vue.$cookies.get('Token')) {
            param.headers['Authorization'] = 'Bearer ' + Vue.$cookies.get('Token')
          }
        } catch (error) {
        }
        let dataPost = {}
        axios.post('/rest/v1/export/nguoitiemchung', dataPost, param).then(function (response) {
          // let fileNames = response.headers['content-disposition']
          // let fileName = fileNames.split('filename=')[1] || 'danhsach'
          // fileName = fileName.split('"').join('')
          let a = document.createElement('a')
          document.body.appendChild(a)
          a.style = 'display: none'
          let url = window.URL.createObjectURL(response.data)
          a.href = url
          a.download = filter.typeList + '.xls'
          a.click()
          window.URL.revokeObjectURL(url)
          resolve(response.data)
        }).catch(xhr => {
          reject(xhr)
        })
      })
    }
  },
})
