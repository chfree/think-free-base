package com.tennetcn.free.swagger.annotations;

import com.tennetcn.free.swagger.configuration.ThinkSwaggerConfig;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * @author chfree
 * @email chfree001@gmail.com
 * @create 2019-07-20 08:52
 * @comment
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableSwagger2
@Import(ThinkSwaggerConfig.class)
public @interface EnableThinkSwagger {

}
