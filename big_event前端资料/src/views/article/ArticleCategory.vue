<script setup>
import { Edit, Delete } from '@element-plus/icons-vue'
import { ref } from 'vue'
const categorys = ref([
  {
    id: 3,
    categoryName: '美食',
    categoryAlias: 'my',
    createTime: '2023-09-02 12:06:59',
    updateTime: '2023-09-02 12:06:59',
  },
  {
    id: 4,
    categoryName: '娱乐',
    categoryAlias: 'yl',
    createTime: '2023-09-02 12:08:16',
    updateTime: '2023-09-02 12:08:16',
  },
  {
    id: 5,
    categoryName: '军事',
    categoryAlias: 'js',
    createTime: '2023-09-02 12:08:33',
    updateTime: '2023-09-02 12:08:33',
  },
])

//声明一个异步函数因为我们来到之后需要等待await服务查询完才可以所以需要异步函数
//获取所有文章分类数据
import { articleCategoryListService } from '@/api/article.js'
const getAllCategory = async () => {
  let result = await articleCategoryListService()
  categorys.value = result.data
}
getAllCategory()
//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
  categoryName: '',
  categoryAlias: '',
})
//添加分类表单校验
const rules = {
  categoryName: [
    { required: true, message: '请输入分类名称', trigger: 'blur' },
  ],
  categoryAlias: [
    { required: true, message: '请输入分类别名', trigger: 'blur' },
  ],
}
//调用接口添加表单
import {
  addCategoryService,
  pdateCategoryService,
  deleteCategoryService,
} from '@/api/article.js'
import { ElMessage } from 'element-plus'
const addCategory = async () => {
  const add = await addCategoryService(categoryModel.value)
  ElMessage.success(add.msg ? add.msg : '添加成功')

  //刷新页面查询全部
  getAllCategory()
  //把弹窗消失
  dialogVisible.value = false
  categoryModel.value = ''
}
//定义变量控制标题变化
const title = ref('')
//编辑弹窗
const shows = (row) => {
  dialogVisible.value = true
  title.value = '编辑分类'
  //数据拷贝
  categoryModel.value.categoryName = row.categoryName
  categoryModel.value.categoryAlias = row.categoryAlias
  //扩展id属性将来传递给后端完成分类的修改
  categoryModel.value.id = row.id
}

const uopdataCategory = async () => {
  const update = await pdateCategoryService(categoryModel.value)
  ElMessage.success('修改成功')
  getAllCategory()
  dialogVisible.value = false
}
//点击添加分类清空模型数据
const cleatData = () => {
  categoryModel.value.categoryName = ''
  categoryModel.value.categoryAlias = ''
}
//删除分类
import { ElMessageBox } from 'element-plus'
const deletes = (row) => {
  ElMessageBox.confirm('确定要删除这条分类吗？', '温馨提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(async () => {
      //到这里就是确定删除了 这时候就需要调用接口完成删除
      const deletess = await deleteCategoryService(row.id)
      console.log(row.id)
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      getAllCategory()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章分类</span>
        <div class="extra">
          <el-button type="primary"
                     @click="dialogVisible=true,title='添加分类',cleatData()">添加分类</el-button>
        </div>
      </div>
    </template>
    <el-table :data="categorys"
              style="width: 100%">
      <el-table-column label="序号"
                       width="100"
                       type="index"> </el-table-column>
      <el-table-column label="分类名称"
                       prop="categoryName"></el-table-column>
      <el-table-column label="分类别名"
                       prop="categoryAlias"></el-table-column>
      <el-table-column label="创建时间"
                       prop="createTime"></el-table-column>
      <el-table-column label="修改时间"
                       prop="updateTime"></el-table-column>
      <el-table-column label="操作"
                       width="100">
        <!-- 意思是将来你点击编辑按钮所在的哪一行数据 -->
        <template #default="{ row }">
          <el-button :icon="Edit"
                     circle
                     plain
                     type="primary"
                     @click="shows(row)"></el-button>
          <el-button :icon="Delete"
                     circle
                     plain
                     type="danger"
                     @click="deletes(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible"
               :title="title"
               width="30%">
      <el-form :model="categoryModel"
               :rules="rules"
               label-width="100px"
               style="padding-right: 30px">
        <el-form-item label="分类名称"
                      prop="categoryName">
          <el-input v-model="categoryModel.categoryName"
                    minlength="1"
                    maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="分类别名"
                      prop="categoryAlias">
          <el-input v-model="categoryModel.categoryAlias"
                    minlength="1"
                    maxlength="15"></el-input>
        </el-form-item>

      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary"
                     @click="title == '添加分类'?addCategory():uopdataCategory()"> 确认 </el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>