package com.qzk.common.auth;

import com.qzk.common.constant.ApplicationConst;
import com.qzk.common.domain.dto.UserTokenDto;
import com.qzk.common.exception.AuthException;
import com.qzk.common.utils.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description 认证切面
 * @Date 2022-12-19-14-22
 * @Author qianzhikang
 */
@Component
@Aspect
public class AuthAspect {

    @Around("@annotation(com.qzk.common.auth.Authentication)")
    public Object authentication(ProceedingJoinPoint point) throws Throwable {
        checkToken();
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
                httpServletRequest.setAttribute("id", userTokenDto.getUserId());
                return true;
            } else {
               return false;
            }
        } catch (Exception e) {
            throw new AuthException(e.getMessage());
        }
    }
}
