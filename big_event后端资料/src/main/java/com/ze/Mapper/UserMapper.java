package com.ze.Mapper;

import com.ze.Pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper {
    /**
     * 查询用户名
     * @param username
     * @return
     */

    @Select(" select * from big_event.user where username=#{username}")
    User findByUserName(String username);

    /**
     * 添加
     * @param username
     * @param password
     */
    @Insert("  insert into big_event.user (username, password, create_time, update_time)\n" +
            "        values (#{username},#{password},now(),now());")
    void add(String username, String password);


    @Update("update big_event.user set nickname=#{nickname}, email=#{email}, update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update  big_event.user set user_pic=#{url},update_time=now() where id=#{id}")
    void updateAvatar(String url,Integer id);

    @Update("update  big_event.user set password=#{newPwd},update_time=now() where id=#{id}")
    void updatePwd(String newPwd,Integer id);
}
