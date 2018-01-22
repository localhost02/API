package cn.localhost01.annotation;

import java.lang.annotation.*;

/**
 * @Description:将请求参数转换为JavaBean eq:支持传统的Get/Post，解决@RequestBody只能转换Json问题
 * @Author Ran.chunlin
 * @Date: Created in 4:44 2017/12/17
 */

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestFormBody {
    boolean required() default true;
}
