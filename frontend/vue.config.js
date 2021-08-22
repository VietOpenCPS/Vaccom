const webpack = require('webpack');
module.exports = {
  publicPath: process.env.NODE_ENV === 'production' ? '/vac/' : '/',
  // outputDir: 'src/main/webapp',
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
