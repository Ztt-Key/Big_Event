package com.ze.HandlerConfig;

import com.ze.Pojo.Result;
import com.ze.Utils.JwtUtil;
import com.ze.Utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 这个注解的意思是把这个handler注入到ioc容器里面 @Component
 *
 * 完成这一步操作之后还需要对这个拦截器进行注册
 *
 * try catch 如果token存在则正常运行  如果不存在则会抛出异常
 *
 * preHandle 在请求controller发生之前执行也就是所有的请求都会经过这个拦截器
 */
@Component
public class LoginHeandler implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate RedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        try {
            //从redis中获取相同的token
            ValueOperations<String, String> Operations = RedisTemplate.opsForValue();
            String s = Operations.get(token);
            if (s==null){
                //redis中的token已经失效了
                throw new RuntimeException();
            }
            Map<String, Object> stringObjectMap = JwtUtil.parseToken(token);
            /**
             *  preHandle=true 拦截器的放行
             */
//            System.out.println("拦截器放行了");
            //把业务数据存到ThreadLocal中
            ThreadLocalUtil.set(stringObjectMap);
            return true;
        } catch (Exception e) {
            //设置响应码为401
            response.setStatus(401);
            /**
             * 拦截器的不放行
             */
            System.err.println("拦截器没有放行");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       //清除ThreadLocalUtil中的数据防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
