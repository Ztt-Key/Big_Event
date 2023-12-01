import { defineStore } from 'pinia'
import { ref } from 'vue'
const userInfoStore = defineStore('userinfo', () => {


  //定义状态相关的内容
  const info = ref()
  const setinfo = (newvalue) => {
    info.value = newvalue
  }

  const remove = () => {
    info.value = {}
  }
  return { info, setinfo, remove }
}, { persist: true })
export default userInfoStore