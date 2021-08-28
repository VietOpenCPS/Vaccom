<template>
  <!-- <div v-if="userLogin['role_name'] === 'admin'"> -->
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
        title="Danh sách dữ liệu danh mục"
        class="px-5 py-3"
      >
        <v-layout wrap class="mt-3">
          <v-flex xs12 md6 class="pr-2 py-2">
            <div style="border: 1px solid #dedede;border-radius: 4px">
              <v-list>
                <v-list-item>
                  <v-list-item-avatar>
                    <v-icon
                      class="blue"
                      dark
                    >mdi-clipboard-text</v-icon>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <v-list-item-title><span style="font-weight: bold; color: #0072bc;">DANH MỤC QUỐC GIA</span></v-list-item-title>
                  </v-list-item-content>

                  <v-list-item-action>
                    <v-btn small color="#0072bc" class="mx-0" @click.stop="pickFileImport('quocgia')" :loading="loadingData" :disabled="loadingData">
                      <v-icon left size="22">
                        mdi-import
                      </v-icon>
                      Import danh mục
                    </v-btn>
                    <input type="file" id="file_quocgia" @input="uploadFileImport('quocgia')" style="display:none">
                  </v-list-item-action>
                </v-list-item>
              </v-list>
              <v-layout wrap class="px-3 pb-2">
                <v-text-field
                  class="flex"
                  label="SHEET"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['quocgia']['sheetAt']"
                ></v-text-field>
                <v-text-field
                  class="flex"
                  label="Từ cột"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['quocgia']['startCol']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  class="flex"
                  label="Đến cột"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['quocgia']['endCol']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  label="Từ hàng"
                  outlined
                  hide-details="auto"
                  dense
                  v-model="configData['quocgia']['startRow']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  label="Đến hàng"
                  outlined
                  hide-details="auto"
                  dense
                  v-model="configData['quocgia']['endRow']"
                  style="width: 70px"
                ></v-text-field>
              </v-layout>
            </div>
          </v-flex>
          <v-flex xs12 md6 class="pl-2 py-2">
            <div style="border: 1px solid #dedede;border-radius: 4px">
              <v-list>
                <v-list-item>
                  <v-list-item-avatar>
                    <v-icon
                      class="blue"
                      dark
                    >mdi-clipboard-text</v-icon>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <v-list-item-title><span style="font-weight: bold; color: #0072bc;">DANH MỤC DÂN TỘC</span></v-list-item-title>
                  </v-list-item-content>

                  <v-list-item-action>
                    <v-btn small color="#0072bc" class="mx-0" @click.stop="pickFileImport('dantoc')" :loading="loadingData" :disabled="loadingData">
                      <v-icon left size="22">
                        mdi-import
                      </v-icon>
                      Import danh mục
                    </v-btn>
                    <input type="file" id="file_dantoc" @input="uploadFileImport('dantoc')" style="display:none">
                  </v-list-item-action>
                </v-list-item>
              </v-list>
              <v-layout wrap class="px-3 pb-2">
                <v-text-field
                  class="flex"
                  label="SHEET"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['dantoc']['sheetAt']"
                ></v-text-field>
                <v-text-field
                  class="flex"
                  label="Từ cột"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['dantoc']['startCol']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  class="flex"
                  label="Đến cột"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['dantoc']['endCol']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  label="Từ hàng"
                  outlined
                  hide-details="auto"
                  dense
                  v-model="configData['dantoc']['startRow']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  label="Đến hàng"
                  outlined
                  hide-details="auto"
                  dense
                  v-model="configData['dantoc']['endRow']"
                  style="width: 70px"
                ></v-text-field>
              </v-layout>
            </div>
          </v-flex>
          <v-flex xs12 md6 class="pr-2 py-2">
            <div style="border: 1px solid #dedede;border-radius: 4px">
              <v-list>
                <v-list-item>
                  <v-list-item-avatar>
                    <v-icon
                      class="blue"
                      dark
                    >mdi-clipboard-text</v-icon>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <v-list-item-title><span style="font-weight: bold; color: #0072bc;">DANH MỤC ĐƠN VỊ HÀNH CHÍNH</span></v-list-item-title>
                  </v-list-item-content>

                  <v-list-item-action>
                    <v-btn small color="#0072bc" class="mx-0" @click.stop="pickFileImport('donvihanhchinh')" :loading="loadingData" :disabled="loadingData">
                      <v-icon left size="22">
                        mdi-import
                      </v-icon>
                      Import danh mục
                    </v-btn>
                    <input type="file" id="file_donvihanhchinh" @input="uploadFileImport('donvihanhchinh')" style="display:none">
                  </v-list-item-action>
                </v-list-item>
              </v-list>
              <v-layout wrap class="px-3 pb-2">
                <v-text-field
                  class="flex"
                  label="SHEET"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['donvihanhchinh']['sheetAt']"
                ></v-text-field>
                <v-text-field
                  class="flex"
                  label="Từ cột"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['donvihanhchinh']['startCol']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  class="flex"
                  label="Đến cột"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['donvihanhchinh']['endCol']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  label="Từ hàng"
                  outlined
                  hide-details="auto"
                  dense
                  v-model="configData['donvihanhchinh']['startRow']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  label="Đến hàng"
                  outlined
                  hide-details="auto"
                  dense
                  v-model="configData['donvihanhchinh']['endRow']"
                  style="width: 70px"
                ></v-text-field>
              </v-layout>
            </div>
          </v-flex>
          <v-flex xs12 md6 class="pl-2 py-2">
            <div style="border: 1px solid #dedede;border-radius: 4px">
              <v-list>
                <v-list-item>
                  <v-list-item-avatar>
                    <v-icon
                      class="blue"
                      dark
                    >mdi-clipboard-text</v-icon>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <v-list-item-title><span style="font-weight: bold; color: #0072bc;">DANH MỤC NHÓM ĐỐI TƯỢNG</span></v-list-item-title>
                  </v-list-item-content>

                  <v-list-item-action>
                    <v-btn small color="#0072bc" class="mx-0" @click.stop="pickFileImport('doituong')" :loading="loadingData" :disabled="loadingData">
                      <v-icon left size="22">
                        mdi-import
                      </v-icon>
                      Import danh mục
                    </v-btn>
                    <input type="file" id="file_doituong" @input="uploadFileImport('doituong')" style="display:none">
                  </v-list-item-action>
                </v-list-item>
              </v-list>
              <v-layout wrap class="px-3 pb-2">
                <v-text-field
                  class="flex"
                  label="SHEET"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['doituong']['sheetAt']"
                ></v-text-field>
                <v-text-field
                  class="flex"
                  label="Từ cột"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['doituong']['startCol']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  class="flex"
                  label="Đến cột"
                  outlined
                  dense
                  hide-details="auto"
                  v-model="configData['doituong']['endCol']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  label="Từ hàng"
                  outlined
                  hide-details="auto"
                  dense
                  v-model="configData['doituong']['startRow']"
                  style="width: 70px"
                ></v-text-field>
                <v-text-field
                  label="Đến hàng"
                  outlined
                  hide-details="auto"
                  dense
                  v-model="configData['doituong']['endRow']"
                  style="width: 70px"
                ></v-text-field>
              </v-layout>
            </div>
          </v-flex>
        </v-layout>
          
      </base-material-card>
    </v-container>
    
  </div>
  
</template>

<script>
  import $ from 'jquery'
  export default {
    name: 'DuLieuDanhMuc',
    components: {
    },
    data () {
      return {
        loadingData: false,
        configData: {
          'quocgia': {
            sheetAt: 4,
            startCol:0,
            endCol: 1,
            startRow: 1,
            endRow: 245
          },
          'dantoc': {
            sheetAt: 3,
            startCol:0,
            endCol: 1,
            startRow: 1,
            endRow: 58
          },
          'donvihanhchinh': {
            sheetAt: 5,
            startCol:0,
            endCol: 5,
            startRow: 1,
            endRow: 10608
          },
          'doituong': {
            sheetAt: 0,
            startCol:0,
            endCol: 1,
            startRow: 3,
            endRow: 18
          }
        }
      }
    },
    created () {
      let vm = this
      vm.$store.commit('SET_INDEXTAB', 3)
    },
    watch: {
      
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      pickFileImport (table) {
        document.getElementById('file_' + table).value = ''
        document.getElementById('file_' + table).click()
      },
      uploadFileImport (table) {
        let vm = this
        let files = $('#file_'+table)[0].files
        let file = files[0]
        vm.processingAction = true
        let filter = {
          file: file,
          table: table
        }
        let config = vm.configData[table]
        let data = Object.assign(filter, config)
        console.log('data', data)
        vm.$store.dispatch('importDanhSach', data).then(function(result) {
          vm.processingAction = false
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Import danh sách thành công',
            color: 'success',
          })
        }).catch(function () {
          vm.processingAction = false
          vm.$store.commit('SHOW_SNACKBAR', {
            show: true,
            text: 'Import thất bại',
            color: 'error',
          })
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

