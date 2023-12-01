package com.ze.Service;

import com.ze.Pojo.Article;
import com.ze.Pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void delete(Integer id);
}
