package com.ze.Mapper;

import com.ze.Pojo.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into big_event.article (title, content, cover_img, state, category_id, create_user, create_time, update_time) values " +
            "(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);


    List<Article> list(Integer categoryId, String state, Integer id);

    @Delete("delete from big_event.article where  id=#{id}")
    void delete(Integer id);
}
