package com.youlai.system.core.security.exception;

import com.youlai.system.common.result.ResultCode;
import com.youlai.system.common.util.ResponseUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理
 *
 * @author haoxr
 * @since 2.0.0
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        int status = response.getStatus();
        if (status == HttpServletResponse.SC_NOT_FOUND) {
            // 资源不存在
            ResponseUtils.writeErrMsg(response, ResultCode.RESOURCE_NOT_FOUND);
        } else {
          if(authException instanceof UsernameNotFoundException){
              ResponseUtils.writeErrMsg(response, ResultCode.USER_NOT_EXIST);
          }else if(authException instanceof BadCredentialsException){
              ResponseUtils.writeErrMsg(response, ResultCode.USERNAME_OR_PASSWORD_ERROR);
          }else {
              // 未认证或者token过期
              ResponseUtils.writeErrMsg(response, ResultCode.TOKEN_INVALID);
          }
        }
    }
}
