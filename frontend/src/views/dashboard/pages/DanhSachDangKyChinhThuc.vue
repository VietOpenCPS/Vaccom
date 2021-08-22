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
        title="DANH SÁCH ĐĂNG KÝ TIÊM CHÍNH THỨC"
        class="px-5 py-3"
      >
        <v-btn class="mx-0" fab dark small color="#0072bc" @click.stop="showTimKiem" style="position: absolute; right: 40px; top: 15px;">
          <v-icon dark>
            mdi-magnify
          </v-icon>
        </v-btn>
        <v-card-text v-if="showAdvanceSearch">
          <tim-kiem ref="timkiem" v-on:trigger-search="searchDangKyTiem"></tim-kiem>
        </v-card-text>
        <v-card-text :class="breakpointName !== 'lg' ? 'px-0' : 'pt-0'">
          <div :class="breakpointName === 'xs' ? 'mb-3' : 'd-flex mb-3'">
            <div class="mr-auto pt-2 mb-3" v-if="breakpointName === 'xs'">
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </div>
            <span class="mr-auto pt-2" v-else>
              Tổng số: <span style="font-weight: bold; color: green">{{totalItem}}</span> người
            </span>
          </div>
          <v-data-table
            :headers="headers"
            :items="items"
            hide-default-footer
            class="elevation-1"
            no-data-text="Không có"
            :loading="loadingData"
            loading-text="Đang tải... "
          >
            <template v-slot:item.index="{ item, index }">
              <span>{{ page * itemsPerPage - itemsPerPage + index + 1 }}</span>
            </template>
            <template v-slot:item.HoVaTen="{ item, index }">
                <p class="mb-2">{{ item.HoVaTen}}</p>
                <p class="mb-2" style="color: blue">Ngày sinh: {{ item.NgaySinh}}</p>
            </template>
            <template v-slot:item.DiaChiNoiO="{ item, index }">
                <p class="mb-2">{{ item.DiaChiNoiO}} - {{item.PhuongXa_Ten}} - {{item.QuanHuyen_Ten}} - {{item.TinhThanh_Ten}}</p>
            </template>
          </v-data-table>
          <div class="text-center mt-4" v-if="pageCount">
            <nav role="navigation" aria-label="Pagination Navigation">
              <ul class="v-pagination theme--light">
                <li>
                  <button @click="prevPage"  type="button" aria-label="Previous page" 
                    :class="page == 1 ? 'v-pagination__navigation v-pagination__navigation--disabled' : 'v-pagination__navigation'">
                    <i aria-hidden="true" class="v-icon notranslate mdi mdi-chevron-left theme--light"></i>
                  </button>
                </li>
                <li>
                  <button type="button" aria-current="true" class="v-pagination__item v-pagination__item--active primary">
                    {{page}}
                  </button>
                </li>
                <li>
                  <button @click="nextPage" type="button" aria-label="Next page" 
                    :class="page == pageCount ? 'v-pagination__navigation v-pagination__navigation--disabled' : 'v-pagination__navigation'">
                    <i aria-hidden="true" class="v-icon notranslate mdi mdi-chevron-right theme--light"></i>
                  </button>
                </li>
              </ul>
            </nav>
          </div>
        </v-card-text>
      </base-material-card>
      <v-dialog
        max-width="600"
        v-model="dialog"
      >
        <v-card>
          <v-toolbar
            dark
            color="primary"
          >
            <v-toolbar-title>Thông tin chi tiết</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                icon
                dark
                @click="dialog = false"
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text class="mt-5">
            
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-btn color="red" class="white--text mr-2" :loading="loading" :disabled="loading" @click="dialog = false">
              <v-icon left>
                mdi-close
              </v-icon>
              Thoát
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
    
  </div>
  
</template>

<script>
  import Search from './FormTimKiem.vue'
  export default {
    name: 'Customers',
    components: {
    'tim-kiem': Search
    },
    data () {
      return {
        loading: false,
        loadingData: false,
        listDaiLy: [],
        dailySelected: '',
        dialog: false,
        lastVisible: '',
        firstVisible: '',
        totalItem: 0,
        page: 1,
        pageCount: 0,
        itemsPerPage: 10,
        items: [],
        advanceSearchData: {
          codeNumber: '',
          customerTelNo: '',
          branchUid: ''
        },
        showAdvanceSearch: false,
        headers: [
          {
            sortable: false,
            text: 'STT',
            align: 'center',
            value: 'index'
          },
          {
            sortable: false,
            text: 'Họ tên',
            align: 'left',
            value: 'HoVaTen'
          },
          {
            sortable: false,
            text: 'Số CMND/ CCCD',
            align: 'left',
            value: 'CMTCCCD'
          },
          {
            sortable: false,
            text: 'Đối tượng',
            align: 'left',
            value: 'NhomDoiTuong'
          },
          {
            sortable: false,
            text: 'Số điện thoại',
            align: 'left',
            value: 'CMTCCCD'
          },
          {
            sortable: false,
            text: 'Địa chỉ',
            align: 'left',
            value: 'DiaChiNoiO'
          },
          {
            sortable: false,
            text: 'Ngày đăng ký tiêm',
            align: 'center',
            value: 'NgayDangKi'
          }
        ],
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 1)
      let isSigned = this.$store.getters.getIsSigned
      if (!isSigned) {
        vm.$router.push({ path: '/login?redirect=/pages/danh-sach-dang-ky-chinh-thuc' })
        return
      }
      vm.getCounter()
      vm.getCustomer()
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      },
      userLogin () {
        return this.$store.getters.getPermistion
      }
    },
    methods: {
      searchDangKyTiem (data) {
        let vm = this
        console.log('dataSearch', data)
      },
      showTimKiem () {
        let vm = this
        vm.showAdvanceSearch = !vm.showAdvanceSearch
      },
      getBranchs () {
        let vm = this
        db.collection("users").get().then(function(querySnapshot) {
          let users = []
          if (querySnapshot.size) {
            querySnapshot.docs.forEach(function(item) {
              users.push(item.data())
            })
            vm.listDaiLy = users
          } else {
            vm.listDaiLy = []
          }
        }).catch(function () {
        })
      },
      getCounter () {
        let vm = this
        let refs = db.collection('counters').doc('counterCustomer')
        refs.collection('shards').get().then((snapshot) => {
          let total = 0
          let pageCount = 0
          snapshot.forEach((doc) => {
            total += doc.data().count
          })
          if (total && vm.itemsPerPage) {
            pageCount = Math.ceil(total / vm.itemsPerPage)
          }
          vm.totalItem = total
          vm.pageCount = pageCount
          console.log('pagination', total, pageCount)
        })
      },
      searchCustomer () {
        let vm = this
        vm.loadingData = true
        let keySearch = ''
        let valueSearch = ''
        if (vm.advanceSearchData['codeNumber']) {
          keySearch = 'codeNumber'
          valueSearch = vm.advanceSearchData['codeNumber']
        }
        if (vm.advanceSearchData['customerTelNo'] && !vm.advanceSearchData['codeNumber']) {
          keySearch = 'customerTelNo'
          valueSearch = vm.advanceSearchData['customerTelNo']
        }
        if (vm.dailySelected && !vm.advanceSearchData['codeNumber'] && !vm.advanceSearchData['customerTelNo']) {
          keySearch = 'branchUid'
          valueSearch = vm.dailySelected['uid']
        }
        let refsCollection = db.collection("customers").where(keySearch, "==", valueSearch)
        if (!valueSearch) {
          refsCollection = db.collection("customers")
        } else {
          valueSearch = String(valueSearch)
        }
        
        console.log('keySearch', keySearch, valueSearch)
        refsCollection.get().then(function(querySnapshot) {
          vm.loadingData = false
          let customers = []
          if (querySnapshot.size) {
            querySnapshot.docs.forEach(function(item) {
              customers.push(item.data())
            })
            vm.items = customers
            vm.totalItem = querySnapshot.size
            vm.pageCount = Math.ceil(querySnapshot.size / vm.itemsPerPage)
          } else {
            vm.items = []
            vm.totalItem = 0
          }
        }).catch(function () {
          vm.loadingData = false
          vm.items = []
          vm.totalItem = 0
        })
      },
      getCustomer () {
        let vm = this
        vm.loadingData = true
        db.collection("customers").orderBy('dealDate').limit(vm.itemsPerPage).get().then(function(querySnapshot) {
          vm.loadingData = false
          vm.lastVisible = querySnapshot.docs[querySnapshot.docs.length-1]
          let customers = []
          if (querySnapshot.size) {
            querySnapshot.docs.forEach(function(item) {
              customers.push(item.data())
            })
            vm.items = customers
          } else {
            vm.items = []
          }
        }).catch(function () {
          vm.loadingData = false
        })
      },
      prevPage () {
        let vm = this
        vm.loadingData = true
        vm.page -= 1
        db.collection("customers").orderBy("dealDate").endBefore(vm.firstVisible).limit(vm.itemsPerPage).get().then(function(querySnapshot) {
          vm.loadingData = false
          vm.lastVisible = querySnapshot.docs[querySnapshot.docs.length-1]
          let customers = []
          if (querySnapshot.size) {
            querySnapshot.docs.forEach(function(item) {
              customers.push(item.data())
            })
            vm.items = customers
          } else {
            vm.items = []
          }
        }).catch(function () {
          vm.loadingData = false
        })
      },
      nextPage () {
        let vm = this
        vm.loadingData = true
        vm.page += 1
        db.collection("customers").orderBy("dealDate").startAfter(vm.lastVisible).limit(vm.itemsPerPage).get().then(function(querySnapshot) {
          vm.loadingData = false
          vm.firstVisible = querySnapshot.docs[0]
          let customers = []
          if (querySnapshot.size) {
            querySnapshot.docs.forEach(function(item) {
              customers.push(item.data())
            })
            vm.items = customers
          } else {
            vm.items = []
          }
        }).catch(function () {
          vm.loadingData = false
        })
      },
    },
  }
</script>
<style lang="css" scoped>
  .v-data-table-header-mobile {
    display: none !important;
  }
</style>

