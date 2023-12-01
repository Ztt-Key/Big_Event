<script setup>
import userInfoStore from '@/stores/userinfo.js'
import { ref } from 'vue'
const userdata = userInfoStore()
const userInfo = ref({ ...userdata.info })
const rules = {
  nickname: [
    { required: true, message: '请输入用户昵称', trigger: 'blur' },
    {
      pattern: /^\S{2,10}$/,
      message: '昵称必须是2-10位的非空字符串',
      trigger: 'blur',
    },
  ],
  email: [
    { required: true, message: '请输入用户邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
  ],
}
import { userInfoUpdate } from '@/api/user.js'
import { ElMessage } from 'element-plus'
const upupdate = async () => {
  const user = await userInfoUpdate(userInfo.value)
  ElMessage.success('修改成功')
  //修改pinia中的数据
  userdata.setinfo(userInfo.value)
  console.log(userdata.info)
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>基本资料</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form :model="userInfo"
                 :rules="rules"
                 label-width="100px"
                 size="large">
          <el-form-item label="登录名称">
            <el-input v-model="userInfo.username"
                      disabled></el-input>
          </el-form-item>
          <el-form-item label="用户昵称"
                        prop="nickname">
            <el-input v-model="userInfo.nickname"></el-input>
          </el-form-item>
          <el-form-item label="用户邮箱"
                        prop="email">
            <el-input v-model="userInfo.email"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary"
                       @click="upupdate">提交修改</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>