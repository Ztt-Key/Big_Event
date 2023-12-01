package com.ze.Controller;

import com.ze.Pojo.Category;
import com.ze.Pojo.Result;
import com.ze.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章分类以及查看文章
 */
@RestController
@RequestMapping("/category")
public class CategoryConreoller {
    @Autowired
    private CategoryService categoryService;
    // 新增文章分类
    @PostMapping
    public Result add(@RequestBody @Validated(Category.add.class) Category category){
        categoryService.add(category);
        return Result.success();
    }
    //获取全部列表
    @GetMapping
    public Result<List<Category>> list(){
       List<Category> lists = categoryService.list();
        return Result.success(lists);
    }
    //获取文章分类详情根据发布人的id获取
    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category c =  categoryService.detail(id);
        return  Result.success(c);
    }
    //更新文章
    @PutMapping
    public Result update(@RequestBody @Validated(Category.update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }
    @DeleteMapping
    public Result deletes(Integer id){
        categoryService.deletes(id);
        return Result.success();
    }
}
