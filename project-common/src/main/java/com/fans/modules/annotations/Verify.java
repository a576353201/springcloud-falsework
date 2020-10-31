package com.fans.modules.annotations;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Verify {

    String message() default "入参不合法，请检查！！！";

    Class<?>[] groups() default {};

}
