<template>
  <div class="text-center mt-4">
    <nav role="navigation" aria-label="Pagination Navigation">
        <ul class="v-pagination theme--light">
        <li>
            <button @click="prevPage"  type="button" aria-label="Previous page" 
            :class="currentPage == 0 ? 'v-pagination__navigation v-pagination__navigation--disabled' : 'v-pagination__navigation'">
            <i aria-hidden="true" class="v-icon notranslate mdi mdi-chevron-left theme--light"></i>
            </button>
        </li>
        <li>
            <button type="button" aria-current="true" class="v-pagination__item v-pagination__item--active primary">
            {{currentPage + 1}}
            </button>
        </li>
        <li>
            <button @click="nextPage" type="button" aria-label="Next page" 
            :class="currentPage == pageCount -1 ? 'v-pagination__navigation v-pagination__navigation--disabled' : 'v-pagination__navigation'">
            <i aria-hidden="true" class="v-icon notranslate mdi mdi-chevron-right theme--light"></i>
            </button>
        </li>
        </ul>
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
            default: 30
        },
    },
    data () {
      return {
        currentPage: 0
      }
    },
    created () {
        this.currentPage = this.pageInput
    },
    watch: {
        pageInput (val) {
            this.currentPage = val
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

