package com.ze.Service.impl;

import com.ze.Mapper.UserMapper;
import com.ze.Pojo.User;
import com.ze.Service.UserService;
import com.ze.Utils.Md5Util;
import com.ze.Utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServicelmpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
       User user =  userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        //对密码进行加密
        String md5String = Md5Util.getMD5String(password);
        //注册完成添加到数据库
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar( String url) {
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(url,id);

    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map=ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }


}
