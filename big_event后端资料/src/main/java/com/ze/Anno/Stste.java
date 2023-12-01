package com.ze.Anno;

import Vaildation.StateVaildation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 自定义注解
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateVaildation.class}) //指定校验规则的类
public @interface Stste {
    //提供校验失败后的提示信息
    String message() default "{参数值只可以是已发布或草稿}";
    //指定分组
    Class<?>[] groups() default {};
    //负载 可以获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
