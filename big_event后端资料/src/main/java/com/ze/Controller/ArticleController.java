package com.ze.Controller;

import com.ze.Pojo.Article;
import com.ze.Pojo.PageBean;
import com.ze.Pojo.Result;
import com.ze.Service.ArticleService;
import com.ze.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Map;

/**
 * 验证是不是携带token   携带才可以访问其他资源
 * 新增文章
 */
@RestController
@RequestMapping("/artice")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    //文章列表条件分页
    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article>pb =articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }
    @DeleteMapping
    public Result delete(Integer id){
        articleService.delete(id);
        return Result.success();
    }
}
