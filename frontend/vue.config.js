const webpack = require('webpack');
module.exports = {
  productionSourceMap: false,
  // publicPath: process.env.NODE_ENV === 'production' ? '/vac/' : '/',
  publicPath: '/',
  outputDir: 'src/main/webapp',
  lintOnSave: false,
  devServer: {
    disableHostCheck: true,
  },

  transpileDependencies: ['vuetify'],

  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: false,
    },
  },
}
