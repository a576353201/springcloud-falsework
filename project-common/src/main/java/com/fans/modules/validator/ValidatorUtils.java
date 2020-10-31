package com.fans.modules.validator;

import com.alibaba.fastjson.JSONObject;
import com.fans.modules.exception.ValidatorException;
import com.fans.modules.utils.JsonUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * className: ValidatorUtils
 *
 * @author k
 * @version 1.0
 * @description hibernate-validator数据校验工具类
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 * @date 2018-11-06 12:12
 **/
public class ValidatorUtils {
    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    private static <T> Map<String, String> validate(T t, Class<?>... groups) {
        Validator validator = VALIDATOR_FACTORY.getValidator();
        Set<ConstraintViolation<Object>> validateResult = validator.validate(t, groups);
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
            for (ConstraintViolation<?> violation : validateResult) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }

    public static void checkBasePram(Object param, String paramName, String message) {
        LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
        String target = JsonUtils.obj2String(param);
        if (StringUtils.isBlank(target)) {
            errors.put(paramName, message);
            throw new ValidatorException(Joiner.on("; ").useForNull(StringUtils.EMPTY).withKeyValueSeparator(":").join(errors));
        }
    }

    /**
     * description: 校验集合类
     *
     * @param collection 要校验的集合
     * @param groups     校验组
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author k
     * @date 2018/11/06 11:45
     **/
    private static Map<String, String> validateCollection(Collection<?> collection, Class<?>... groups) {
        // 判断集合是否为空
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map<String, String> errors;
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validate(object, groups);
        } while (errors.isEmpty());
        return errors;
    }

    /**
     * description: 字段校验检测
     *
     * @param param  校验参数
     * @param groups 校验组
     * @author k
     * @date 2018/11/06 12:14
     **/
    public static void check(Object param, Class<?>... groups) {
        Map<String, String> map;
        if (param instanceof Collection<?>) {
            map = ValidatorUtils.validateCollection((Collection<?>) param, groups);
        } else {
            map = ValidatorUtils.validate(param, groups);
        }
        if (MapUtils.isNotEmpty(map)) {
            throw new ValidatorException(Joiner.on("; ").useForNull(StringUtils.EMPTY).withKeyValueSeparator(":").join(map));
        }
    }

    /**
     * description: 获取对象验证错误信息
     *
     * @param bindingResult 绑定对象结果信息
     * @return com.alibaba.fastjson.JSONObject 错误对象
     * @author k
     * @date 2019/11/19 21:21
     **/
    public static JSONObject getErrors(BindingResult bindingResult) {
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> {
                fieldError.getField();
                fieldError.getDefaultMessage();
                jsonObject.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
        }
        return jsonObject;
    }
}
