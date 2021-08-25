<template>
  <div class="text-center mt-4">
    <nav role="navigation" aria-label="Pagination Navigation" style="position: relative;">
        <ul class="v-pagination theme--light">
        <li>
            <button @click="prevPage"  type="button" aria-label="Previous page" 
            :class="currentPage == 0 ? 'v-pagination__navigation v-pagination__navigation--disabled' : 'v-pagination__navigation'">
            <i aria-hidden="true" class="v-icon notranslate mdi mdi-chevron-left theme--light"></i>
            </button>
        </li>
        <li>
            <button type="button" aria-current="true" class="v-pagination__item v-pagination__item--active primary">
            {{currentPage + 1}} / {{pageCount}}
            </button>
        </li>
        <li>
            <button @click="nextPage" type="button" aria-label="Next page" 
            :class="currentPage == pageCount -1 ? 'v-pagination__navigation v-pagination__navigation--disabled' : 'v-pagination__navigation'">
            <i aria-hidden="true" class="v-icon notranslate mdi mdi-chevron-right theme--light"></i>
            </button>
        </li>
        </ul>
        <v-autocomplete
            class=""
            hide-no-data
            :items="listPage"
            v-model="currentPagePagination"
            item-text="name"
            item-value="value"
            dense
            outlined
            hide-details="auto"
            style="display: inline-block;
            position: absolute;
            right: 0px;
            width: 150px;"
        ></v-autocomplete>
    </nav>
    
  </div>
  
</template>

<script>
  export default {
    name: 'Search',
    props: {
        pageInput: {
            type: Number,
            default: 0
        },
        pageCount: {
            type: Number,
            default: 0
        },
    },
    data () {
      return {
        currentPage: 0,
        currentPagePagination: 1,
        listPage: []
      }
    },
    created () {
      let vm = this
      vm.currentPage = vm.pageInput
      vm.currentPagePagination = vm.currentPage + 1
      for (let i = 1; i <= vm.pageCount; i++) {
        let item = {
          name: 'Trang ' + i,
          value: i
        }
        vm.listPage.push(item)
      }
    },
    watch: {
        pageInput (val) {
            this.currentPage = val
            this.currentPagePagination = this.currentPage + 1
        },
        pageCount (val) {
          let vm = this
          for (let i = 1; i <= val; i++) {
            let item = {
              name: 'Trang ' + i,
              value: i
            }
            vm.listPage.push(item)
          }
        },
        currentPagePagination (val) {
          let vm = this
          vm.currentPage = val -1
          vm.$emit('tiny:change-page', {
            page: vm.currentPage
          })
        }
    },
    computed: {
      breakpointName () {
        return this.$store.getters.getBreakpointName
      }
    },
    methods: {
      prevPage () {
        let vm = this
        vm.currentPage -= 1
        vm.$emit('tiny:change-page', {
          page: vm.currentPage
        })
      },
      nextPage () {
        let vm = this
        vm.currentPage += 1
        vm.$emit('tiny:change-page', {
          page: vm.currentPage
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

