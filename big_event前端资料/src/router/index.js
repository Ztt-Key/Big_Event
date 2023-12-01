import Layout from '@/views/Layout.vue'
import LoginView from '@/views/Login.vue'
import ArticleCategory from '@/views/article/ArticleCategory.vue'
import ArticleManage from '@/views/article/ArticleManage.vue'
import UserAvatar from '@/views/user/UserAvatar.vue'
import Userinfo from '@/views/user/UserInfo.vue'
import UserResetPassword from '@/views/user/UserResetPassword.vue'
import { createRouter, createWebHistory } from 'vue-router'

//定义路由关系
const routes = [
  // commont
  { path: '/login', component: LoginView },
  {
    // 路径为/会出现什么都没有的情况 所以当路径为/的时候设置重定向为 '/article/ArticleManage',
    path: '/', component: Layout, redirect: '/article/ArticleManage',
    children: [
      { path: '/user/UserAvatar', component: UserAvatar },
      { path: '/user/Userinfo', component: Userinfo },
      { path: '/user/UserResetPassword', component: UserResetPassword },
      { path: '/article/rticleCategory', component: ArticleCategory },
      { path: '/article/ArticleManage', component: ArticleManage },
    ]
  },
]
//创建路由器
const router = createRouter({
  history: createWebHistory(),
  routes: routes
})
export default router