package com.fans.zuul.controller;

import com.fans.constant.ServiceConstants;
import com.fans.vo.JsonData;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * className: ZuulServerController
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2020-10-29 23:04
 **/
@RestController
public class ZuulServerController implements ZuulServerControllerApi {

    @Resource(type = RestTemplate.class)
    private RestTemplate restTemplate;

    @Override
    public JsonData<String> refreshAll() {
        try {
            String url = "http://" + ServiceConstants.CONFIG.concat("/actuator/bus-refresh");
            restTemplate.postForEntity(url, null, Object.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return JsonData.fail(e.getMessage());
        }
        return JsonData.success("所有云端服务以刷新！！！");
    }

    @Override
    public JsonData<?> error(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();
        RequestContext requestContext = RequestContext.getCurrentContext();
        ZuulException zuulException = (ZuulException) requestContext.getThrowable();
        return JsonData.failCodeMsg(10001, zuulException.getMessage().substring(zuulException.getMessage().indexOf(".") + 1), method + " " + requestUrl);
    }
}
