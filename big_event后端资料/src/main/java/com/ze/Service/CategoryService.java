package com.ze.Service;

import com.ze.Pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category detail(Integer id);

    void update(Category category);

    void deletes(Integer id);
}
