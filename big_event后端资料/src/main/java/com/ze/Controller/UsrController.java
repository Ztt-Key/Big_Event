package com.ze.Controller;


import com.ze.Pojo.Result;
import com.ze.Pojo.User;
import com.ze.Service.UserService;
import com.ze.Utils.JwtUtil;
import com.ze.Utils.Md5Util;
import com.ze.Utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
public class UsrController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate RedisTemplate;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password) {
        //查询
        User user = userService.findByUserName(username);
        if (user==null){
            //注册
            userService.register(username,password);
            return Result.success();
        }else {
            return Result.error("用户名已经被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$")String password){
        //根据用户名查询用户
        User loginName = userService.findByUserName(username);
        //判断用户是否存在
        if (loginName==null){
            return Result.error("用户名错误");
        }
        //判断密码是否正确
        if (Md5Util.getMD5String(password).equals(loginName.getPassword())){
            //生成登录的token
            Map<String,Object>ztz = new HashMap<>();
            ztz.put("id",loginName.getId());
            ztz.put("user",loginName.getUsername());
            String token = JwtUtil.genToken(ztz);
            //把密码存储到redis中
            ValueOperations<String, String> Operations = RedisTemplate.opsForValue();
            Operations.set(token,token,12, TimeUnit.HOURS);

            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userinfo")
    public Result<User> userinfo(){
        //根据用户名查询用户
//        Map<String, Object> Map = JwtUtil.parseToken(token);
//        String username = (String) Map.get("user");
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("user");
        User user = userService.findByUserName(username);
        return  Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        /**
         * @RequestBody 把请求体中的json字符串转化为java对象
         */
        userService.update(user);
        return Result.success();
    }

    /**
     * @RequestParam == 前端传入的参数必修是URL
     * @param URL
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String URL){
        userService.updateAvatar(URL);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> map,@RequestHeader("Authorization") String token){
        /**
         * @RequestBody
         * mvc会自动把json字符串转换为java对象
         */
        String oldPwd = map.get("old_pwd");
        String newPwd = map.get("new_pwd");
        String rePwd = map.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("传入参数不正确");
        }
        Map<String,Object> maps = ThreadLocalUtil.get();
        String user = (String) maps.get("user");
        User UserName = userService.findByUserName(user);
        if (!UserName.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码不正确");
        }
        if (!newPwd.equals(rePwd)){
            return Result.error("两次填写的密码不一致");
        }
        userService.updatePwd(newPwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = RedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
