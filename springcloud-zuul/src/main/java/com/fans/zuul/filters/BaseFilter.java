package com.fans.zuul.filters;

import com.fans.common.exception.http.HttpException;
import com.fans.common.properties.ExceptionProperty;
import com.fans.common.utils.JsonUtils;
import com.fans.common.vo.JsonData;
import com.google.common.base.Charsets;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * className: BaseFilter
 *
 * @author k
 * @version 1.0
 * @description filter基类
 * @date 2020-10-28 16:43
 **/
@Component
public class BaseFilter {

    private static ExceptionProperty exceptionProperty;

    @Resource(type = ExceptionProperty.class)
    public void setExceptionProperty(ExceptionProperty exceptionProperty) {
        BaseFilter.exceptionProperty = exceptionProperty;
    }

    public static void forbidden(RequestContext currentContext, HttpException httpException) {
        HttpServletRequest request = currentContext.getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        JsonData<?> jsonData;
        if (exceptionProperty != null) {
            jsonData = JsonData.failCodeMsg(httpException.getCode(),
                    exceptionProperty.getMessage(httpException.getCode()),
                    method + StringUtils.SPACE + uri);
        } else {
            jsonData = JsonData.failCodeMsg(10002, "禁止访问" ,
                    method + StringUtils.SPACE + uri);
        }
        // 停止zuul继续向下路由，禁止请求通信
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(httpException.getHttpStatus());
        currentContext.setResponseBody(JsonUtils.obj2String(jsonData));
        currentContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
        currentContext.getResponse().setCharacterEncoding(Charsets.UTF_8.displayName());
    }

}
