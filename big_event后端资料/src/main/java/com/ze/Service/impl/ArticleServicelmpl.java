package com.ze.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ze.Mapper.ArticleMapper;
import com.ze.Pojo.Article;
import com.ze.Pojo.PageBean;
import com.ze.Service.ArticleService;
import com.ze.Utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServicelmpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        /**
         * 对一些数据进行补充
         */
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String,Object>maps = ThreadLocalUtil.get();
        Integer id = (Integer) maps.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    /**
     * 条件分页列表查询
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @param categoryId
     * @param state
     * @return
     */
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        /**
         * List向下强转为Page   Page为list一个实现类
         * 因为分页查询返回的是一个Page对象
         * PageHelper.startPage(分页的页数,每个分页的内容数量);
         */
        //创建PageBean对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询   Mybaits插件
        PageHelper.startPage(pageNum,pageSize);
        //自己只有自己发布的文章才可以操作   可以通过ThreadlLocal中获取当前用户id
        Map<String,Object>maps = ThreadLocalUtil.get();
        Integer id = (Integer) maps.get("id");
        //调用mapper
       List<Article> as = articleMapper.list(categoryId,state,id);
       //page中提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数据
       Page<Article> p = (Page<Article>) as;
       //拿数据填冲到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
