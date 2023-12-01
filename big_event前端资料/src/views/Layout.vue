
<script setup>
import {
  Management,
  Promotion,
  UserFilled,
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom,
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'
import { userInfoService } from '@/api/user.js'
import userInfoStore from '@/stores/userinfo.js'
import { useTokenStore } from '@/stores/token.js'
const userdata = userInfoStore()
const getUserInfo = async () => {
  const user = await userInfoService()
  console.log(user.data)
  userdata.setinfo(user.data)
}
getUserInfo()
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
//调用这个函数得到路由器
const router = useRouter()
//条目杯点击后，调用函数
const handleCommand = (command) => {
  if (command == 'logout') {
    ElMessageBox.confirm('确定要退出登录吗？', '退出登录', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
      .then(async () => {
        //到这里就是确定退出了这时候就需要调用接口完成退出
        useTokenStore().removeToken()
        userInfoService().remove()
        //跳转
        router.push('/login')
        ElMessage({
          type: 'success',
          message: '退出成功',
        })
        getAllCategory()
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消退出',
        })
      })
  } else {
    router.push('/user/' + command)
  }
}
</script>

<template>
  <!-- el-container  是element plus 的一个容器 -->
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <div class="el-aside__logo"></div>
      <el-menu active-text-color="#ffd04b"
               background-color="#232323"
               text-color="#fff"
               router>
        <el-menu-item index="/article/rticleCategory">
          <el-icon>
            <Management />
          </el-icon>
          <span>文章分类</span>
        </el-menu-item>
        <el-menu-item index="/article/ArticleManage">
          <el-icon>
            <Promotion />
          </el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-sub-menu>
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>个人中心</span>
          </template>
          <el-menu-item index="/user/Userinfo">
            <el-icon>
              <User />
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>
          <el-menu-item index="/user/UserAvatar">
            <el-icon>
              <Crop />
            </el-icon>
            <span>更换头像</span>
          </el-menu-item>
          <el-menu-item index="/user/UserResetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <!-- 右侧主区域 -->
    <el-container>
      <!-- 头部区域 -->
      <el-header>
        <div>电子信息<strong>{{userdata.info.username?userdata.info.username:userdata.info.nickname}}</strong></div>
        <el-dropdown placement="bottom-end"
                     @command="handleCommand">
          <span class="el-dropdown__box">
            <el-avatar :src="userdata.info.userPic?userdata.info.userPic:avatar" />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <!-- command:条目被点击后触发，在事件函数上可以生命一个参数。接受条目对应指令 -->
              <el-dropdown-item command="Userinfo"
                                :icon="User">基本资料</el-dropdown-item>
              <el-dropdown-item command="UserAvatar"
                                :icon="Crop">更换头像</el-dropdown-item>
              <el-dropdown-item command="UserResetPassword"
                                :icon="EditPen">重置密码</el-dropdown-item>
              <el-dropdown-item command="logout"
                                :icon="SwitchButton">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中间区域 -->
      <el-main>
        <router-view></router-view>
        <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
          内容展示区
        </div> -->
      </el-main>
      <!-- 底部区域 -->
      <el-footer>2023/11/29/12/49</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .el-aside {
    background-color: #232323;

    &__logo {
      height: 120px;
      background: url('@/assets/logo.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }

  .el-header {
    background-color: #fff;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .el-dropdown__box {
      display: flex;
      align-items: center;

      .el-icon {
        color: #999;
        margin-left: 10px;
      }

      &:active,
      &:focus {
        outline: none;
      }
    }
  }

  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
  }
}
</style>