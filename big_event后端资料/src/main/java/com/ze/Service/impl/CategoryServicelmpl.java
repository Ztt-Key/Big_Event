package com.ze.Service.impl;

import com.ze.Mapper.CategoryMapper;
import com.ze.Pojo.Category;
import com.ze.Service.CategoryService;
import com.ze.Utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServicelmpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String, Object> maps = ThreadLocalUtil.get();
        Integer id = (Integer) maps.get("id");
        category.setCreateUser(id);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String,Object>maps=ThreadLocalUtil.get();
        Integer id = (Integer) maps.get("id");
        return categoryMapper.lists(id);
    }

    @Override
    public Category detail(Integer id) {
        return categoryMapper.detail(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void deletes(Integer id) {
        categoryMapper.deletes(id);
    }
}
