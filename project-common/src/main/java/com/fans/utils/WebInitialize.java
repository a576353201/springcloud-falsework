package com.fans.utils;

import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * className: WebInitialize
 *
 * @author k
 * @version 1.0
 * @description request, response, session获取
 * @date 2019-04-01 10:56
 **/
@Data
public class WebInitialize {
    public static HttpServletRequest request;
    public static HttpServletResponse response;
    public static HttpSession session;

    static {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        request = attributes.getRequest();
        response = attributes.getResponse();
        session = request.getSession();
    }

    /**
     * 获取域名
     *
     * @return domain信息
     */
    public static String getDomain() {
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    /**
     * 获取请求来源
     *
     * @return origin
     */
    public static String getOrigin() {
        return request.getHeader("Origin");
    }

    public static String getIp() {
        return NetWorkUtils.getIpAddress(request);
    }

}
