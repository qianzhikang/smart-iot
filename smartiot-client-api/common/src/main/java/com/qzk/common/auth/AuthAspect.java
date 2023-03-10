package com.qzk.common.auth;

import com.qzk.common.constant.ApplicationConst;
import com.qzk.common.domain.dto.UserTokenDto;
import com.qzk.common.exception.AuthException;
import com.qzk.common.redis.TokenSaveRedisDao;
import com.qzk.common.utils.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description 认证切面
 * @Date 2022-12-19-14-22
 * @Author qianzhikang
 */
@Component
@Aspect
@Order(1)
public class AuthAspect {

    @Resource
    private TokenSaveRedisDao tokenSaveRedisDao;

    @Around("@annotation(com.qzk.common.auth.Authentication)")
    public Object authentication(ProceedingJoinPoint point) throws Throwable {
        if (!checkToken()) {
            throw new AuthException("登陆已失效！");
        }
        return point.proceed();
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return HttpServletRequest
     */
    private HttpServletRequest getHttpServletRequest() {
        // 请求上下文
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 强制转换为 ServletRequestAttributes
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        assert servletRequestAttributes != null;
        return servletRequestAttributes.getRequest();
    }

    /**
     * 检查 token 是否合法
     */
    private Boolean checkToken() {
        try {
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            String token = httpServletRequest.getHeader("Authentication");
            if (JwtUtil.isTokenExpired(ApplicationConst.JWT_SECRET, token)) {
                Map claimsMap = JwtUtil.getClaims(ApplicationConst.JWT_SECRET, token);
                UserTokenDto userTokenDto = UserTokenDto.fromMap(claimsMap);
                Assert.notNull(userTokenDto.getUserId(), "非法token");
                String saveToken = tokenSaveRedisDao.getToken(userTokenDto.getUserId());
                Assert.isTrue(StringUtils.hasText(saveToken),"不存在的token");
                if (saveToken.equals(token)) {
                    httpServletRequest.setAttribute("id", userTokenDto.getUserId());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }
}
