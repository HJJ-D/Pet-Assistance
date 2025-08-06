import Vue from 'vue'
import App from './App.vue'
// 载入自定义主题
import './assets/style/theme/index.css'
// 载入全局css配置
import './assets/global.css'
// 载入路由
import router from './router'
// 载入elementUI
import ElementUI from 'element-ui';
//import 'element-ui/lib/theme-chalk/index.css';
// 引入echarts
import * as echarts from 'echarts'
// 引入axios
import axios from 'axios'
import '@/api/websocket' //全局引入 webSocket 通讯组件
axios.defaults.baseURL = '/api' // nodeJS Express.js模拟后端 减少资源消耗 低配电脑福音

// 引入videoJS，用于解决阿里云OSS的M3U8视频播放
import videoJS from 'video.js'
import 'video.js/dist/video-js.css'
import 'videojs-flash'; // 要播放rtmp流, 必须引入


axios.interceptors.request.use(config => {
  // 为请求头对象添加token令牌验证的Authorization字段
  config.headers.Authorization = window.localStorage.getItem('token')
  return config
})

axios.interceptors.response.use(response => {
  const token = response.headers.authorization
  if (token) {
    window.localStorage.setItem('token', token)
  } else {
    window.localStorage.removeItem('token')
    window.localStorage.removeItem('user')
    router.push('/login')
  }
  return response
})


Vue.prototype.$echarts = echarts;
Vue.prototype.$http = axios;
Vue.prototype.$video = videoJS

Vue.use(ElementUI);

Vue.config.productionTip = false

// 时间戳转换年月日时分秒-过滤器
Vue.filter('dateFormat', function (originVal) {
  const dt = new Date(originVal)

  const y = dt.getFullYear()
  const m = (dt.getMonth() + 1 + '').padStart(2, '0')
  const d = (dt.getDate() + '').padStart(2, '0')

  const hh = (dt.getHours() + '').padStart(2, '0')
  const mm = (dt.getMinutes() + '').padStart(2, '0')
  const ss = (dt.getSeconds() + '').padStart(2, '0')
  // `yyyy-mm-dd hh:mm:ss`
  return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
