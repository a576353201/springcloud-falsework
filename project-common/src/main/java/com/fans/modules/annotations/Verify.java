package com.fans.modules.annotations;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Verify {

    Class<?>[] groups() default {};

}
