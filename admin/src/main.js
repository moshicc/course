import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false;
Vue.prototype.$ajax =axios;
/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
    console.log("请求：",config);
    return config;
},error => {});
axios.interceptors.response.use(function (response) {
    console.log("返回结果：",response);
    return response;
},error => {});

//$mount 意思大概是渲染展示，展示当前目录下的APP.vue,渲染到#app。就是把APP.vue中的<template> 替换掉index页面中的id=app标签
new Vue({
    router,
  render: h => h(App),
}).$mount('#app');
