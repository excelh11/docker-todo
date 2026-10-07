const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  chainWebpack: (config) => {
    // 브라우저 탭 제목
    config.plugin('html').tap((args) => {
      args[0].title = 'TODO';
      return args;
    });
  },
})
