<template>
  <div class="wrap-page-login" style="min-height: calc(100vh - 92px) !important">
    <v-container fluid class="page-login" fill-height>
      
    </v-container>
  </div>
</template>

<script>
const name = 'page-login'
export default {
  name: name,
  data() {
    return {
      loading: false
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
  
.label-bh {
  font-weight: bold;
  color: #0c0078;
}
.wrap-page-login{
  /* background-image: url(/vac/images/bg_active.jpg);
  background-position: center; */
  /* background-color: #1ae28a;
  background: linear-gradient( -45deg, #209077 0%, #1ae28a 100%); */
}
.wrap-page-login:before {
  z-index: 0;
  width: 100%;
  display: block;
  left: 0;
  top: 0;
  content: "";
  background: rgba(0,0,0,.21)
}
.page-login__card {
  border-radius: 6px !important;
  background-color: transparent;
  box-shadow: none !important
}
.v-card__title {
  background: linear-gradient(65deg,#171cc2,#ff5200);
  color: #fff;
  /* margin-top: -55px; */
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
  /* font-weight: bold; */
  width: 100%;
  height: 42px !important;
  /* background: linear-gradient(65deg,#49c217,#ff5200); */
  border-radius: 4px;
}
</style>
