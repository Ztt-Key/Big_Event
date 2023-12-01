package com.ze.Service;

import com.ze.Pojo.User;

public interface UserService {
    /**
     * 根据用户名查询是不是可以注册
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 注册业务
     * @param username
     * @param password
     */
    void register(String username, String password);

    void update(User user);

    void updateAvatar(String url);

    void updatePwd(String newPwd);
}
