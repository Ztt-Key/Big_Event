//定制请求的实例
//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus';
//定义一个变量,记录公共的前缀  ,  baseURL   因为使用了代理技术所以   http://localhost:8080  放到了代理部分
//const baseURL = 'http://localhost:8080';
const baseURL = "/api"
//创建统一实例
const instance = axios.create({ baseURL })
//请求拦截器
import { useTokenStore } from '@/stores/token.js';
instance.interceptors.request.use(
    (config) => {
        //请求前的回调 添加token
        const token = useTokenStore()
        if (token.token) {
            config.headers.Authorization = token.token
        }
        return config
    }),
    (err) => {
        Promise.reject(err);
    }
//添加响应拦截器
// import { useRoute } from "vue-router";
// const routers = useRoute()
import routers from '@/router';
instance.interceptors.response.use(
    result => {
        if (result.data.code === 0) {
            return result.data;
        }
        ElMessage.error(result.data.msg ? result.data.msg : "服务异常")
        //把异步操作转化为失败
        result.Promise.reject(result.data)
    },
    err => {
        if (err.response.status === 401) {
            ElMessage.error("请先登录")
            routers.push('/login')
        } else {
            ElMessage.error("服务异常")
            return Promise.reject(err);//异步的状态转化成失败的状态
        }

    }
)
export default instance;