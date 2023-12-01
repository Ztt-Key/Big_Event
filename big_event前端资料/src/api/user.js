//导入request.js请求工具
import request from "@/utils/request.js"

//发送请求

//提供调用注册的接口函数
export const userRegisterService = (resdata) => {
  //借用URLserachparams完成传递  URLSearchParams()是个构造函数，将返回一个可以操作查询字符串的对象。
  const parmas = new URLSearchParams()
  for (let key in resdata) {
    parmas.append(key, resdata[key])
  }
  return request.post("/user/register", parmas)
}

//提供调用登录接口的函数
export const userLoginService = (loginData) => {
  const params = new URLSearchParams();
  for (let key in loginData) {
    params.append(key, loginData[key])
  }
  return request.post('/user/login', params)
}


//获取用户详细信息
export const userInfoService = () => {
  return request.get('/user/userinfo')
}
//修改用户信息
export const userInfoUpdate = (params) => {
  return request.put('/user/update', params)
}
//修改用户头像
export const useravayer = (URL) => {
  const user = new URLSearchParams()
  user.append('url', URL)
  return request.patch('/user/updateAvatar', user)
}