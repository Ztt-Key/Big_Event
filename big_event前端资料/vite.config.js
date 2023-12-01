import { fileURLToPath, URL } from 'node:url'

import vue from '@vitejs/plugin-vue'
import { defineConfig } from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': {//获取路径中包含api的才进行跨域处理
        target: "http://localhost:8080",
        changeOrigin: true,//修改源为 target
        rewrite: (path) => path.replace(/^\/api/, '')

      }
    }
  }
})
