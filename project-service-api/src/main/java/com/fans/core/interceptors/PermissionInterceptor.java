package com.fans.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.fans.annotations.ScopeLevel;
import com.fans.exception.http.ForbiddenException;
import com.fans.exception.http.UnAuthenticatedException;
import com.fans.utils.JwtTokenUtils;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * className: PermissionInterceptor
 *
 * @author k
 * @version 1.0
 * @description 权限认证拦截器
 * @date 2020-06-04 13:37
 **/
@SuppressWarnings(value = {"NullableProblems"})
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    /**
     * 认证token固定头
     */
    public static final String BEARER = "Bearer";
    /**
     * 存储token固定key
     */
    public static final String AUTHORIZATION = "Authorization";

    public PermissionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
        if (!scopeLevel.isPresent()) {
            return true;
        }
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.isBlank(bearerToken)) {
            throw new UnAuthenticatedException(10004);
        }
        //Bearer
        if (!bearerToken.startsWith(BEARER)) {
            throw new UnAuthenticatedException(10004);
        }
        List<String> tokenList = Splitter.on(StringUtils.SPACE).trimResults().omitEmptyStrings().splitToList(bearerToken);
        if (tokenList.size() != 2) {
            throw new UnAuthenticatedException(10004);
        }
        String token = tokenList.get(1);
        Optional<Map<String, Claim>> optionalMap = JwtTokenUtils.getClaims(token);
        Map<String, Claim> claimMap = optionalMap.orElseThrow(() -> new UnAuthenticatedException(10004));
        boolean valid = hasPermission(scopeLevel.get(), claimMap);
        if (valid) {
            setToThreadLocal(claimMap);
        }
        return valid;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    /**
     * description: 通过scopeLevel注解获取会员等级
     *
     * @param handler 有@ScopeLevel注解的方法
     * @return java.util.Optional<com.fans.core.annotations.ScopeLevel>
     * @author k
     * @date 2020/06/14 23:04
     **/
    private Optional<ScopeLevel> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if (scopeLevel == null) {
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }

    /**
     * description: 通过对比登录用户的scope决定是否有权限访问
     *
     * @param scopeLevel 方达上的scope
     * @param map        令牌中登录用户的信息
     * @return boolean 是否有权限访问
     * @author k
     * @date 2020/06/14 23:05
     **/
    private boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> map) {
        int level = scopeLevel.value();
        Integer scope = map.get(JwtTokenUtils.getSCOPE()).asInt();
        if (level > scope) {
            throw new ForbiddenException(10005);
        }
        return true;
    }

    /**
     * description:  登录成功后将用户信息保存至本地线程中
     *
     * @param map 令牌中登录用户的相关信息
     * @author k
     * @date 2020/06/14 23:06
     **/
    private void setToThreadLocal(Map<String, Claim> map) {
        //TODO
//        Long uid = map.get(JwtTokenUtils.getUID()).asLong();
//        Integer scope = map.get(JwtTokenUtils.getSCOPE()).asInt();
//        UserDao userDao = ApplicationContextHelper.popBean(UserDao.class);
//        assert userDao != null;
//        UserEntity userEntity = userDao.selectById(uid);
//        if (Optional.ofNullable(userEntity).isPresent()) {
//            LocalUser.set(userEntity, scope);
//        }
    }

}
