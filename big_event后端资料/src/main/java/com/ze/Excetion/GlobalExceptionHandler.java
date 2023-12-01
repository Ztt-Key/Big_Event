package com.ze.Excetion;

import com.ze.Pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * 会把校验的数据转换为json传给客户端
 * Exception e  会封装一个错误提示信息
 *
 * StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败"  string提供的工具类
 * 为什么要包装？   因为有的错误可能不会给你提示
 *
 * RestControllerAdvice 可以将控制器的全局配置放在同一个位置
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 这个注解的意思是要用来处理的异常  Exception=异常包
     *
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result HandleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
