<template>
  <div class="wrap-page-login" style="min-height: calc(100vh - 92px) !important">
    <iframe src="/documents/hdsd.pdf" type="application/pdf" width="100%" height="100%" style="overflow: auto;min-height: 1000px;" frameborder="0"></iframe>
  </div>
</template>

<script>
const name = 'page-hdsd'
export default {
  name: name,
  data() {
    return {
      loading: false
    }
  },
  created () {
    let vm = this
  },
  watch: {
    '$route': function (newRoute, oldRoute) {
    }
  },
  computed: {

  },
  methods: {

  },
}
</script>

<style lang="sass" scoped>
.page-login
  &__card
  max-width: 500px
  margin: 0 auto
</style>
<style lang="css">
  main {
    padding-top: 0px !important;
  }
</style>
<style lang="css" scoped>
</style>
