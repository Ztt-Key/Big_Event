//导入请求工具类
import request from '@/utils/request.js'

//文章分类列表查询
export const articleCategoryListService = () => {
  return request.get('/category')
}
//文章分类添加接口
export const addCategoryService = (categoryData) => {
  return request.post('/category', categoryData)
}
//修改
export const pdateCategoryService = (categoryData) => {
  return request.put('/category', categoryData)
}
//删除
export const deleteCategoryService = (id) => {
  return request.delete('/category?id=' + id)
}
//文章列表查询
export const articleListService = (params) => {
  //请求参数格式为 querystring 时参数传递为   , { parms: parms }
  return request.get('/artice', { params: params })
}
//文章添加
export const articleaddService = (params) => {
  return request.post('/artice', params)
}