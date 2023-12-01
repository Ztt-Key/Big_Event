import './assets/main.scss'

import { createApp } from 'vue'


//因为分页显示的是英文所以需要导入中文语言包
import router from '@/router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/dist/locale/zh-cn.js'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'
import App from './App.vue'
const app = createApp(App)
// 因为携带的token是存储在内存中的所以当我们刷新页面会失去token这时候就需要我们的  pinia-persistedstate-plugin  持久化存储插件了
const pinia = createPinia()
const persist = createPersistedState()
pinia.use(persist)
app.use(pinia)
app.use(router)
app.use(ElementPlus, { locale })
app.mount('#app')
