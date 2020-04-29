import Vue from 'vue'
import App from './app.vue'
import router from './router'

Vue.config.productionTip = false;
//$mount 意思大概是渲染展示，展示当前目录下的APP.vue,渲染到#app。就是把APP.vue中的<template> 替换掉index页面中的id=app标签
new Vue({
    router,
  render: h => h(App),
}).$mount('#app');
