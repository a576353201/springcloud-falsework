package com.fans.modules.validator.custom;


import com.fans.modules.annotations.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * className: PasswordValidator
 *
 * @author k
 * @version 1.0
 * @description 密码验证器
 * @date 2020-06-04 15:39
 **/
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private Integer min;
    private Integer max;


    @Override
    public void initialize(Password constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return min <= password.length() && max >= password.length();
    }
}
