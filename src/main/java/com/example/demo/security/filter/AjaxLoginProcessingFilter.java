package com.example.demo.security.filter;

import com.example.demo.domain.AccountDTO;
import com.example.demo.security.token.AjaxAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 해당 URL로 왔을 때 필터 실행
     */
    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        if(!isAjax(httpServletRequest)){
            throw new IllegalStateException("Authentication is not supported");
        }

        AccountDTO accountDTO = objectMapper.readValue(httpServletRequest.getReader(), AccountDTO.class);
        if(StringUtils.isEmpty(accountDTO.getUsername()) || StringUtils.isEmpty(accountDTO.getPassword())){
            throw new IllegalArgumentException("Username or Password is empty");
        }

        // 인증처리
        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(accountDTO.getUsername(), accountDTO.getPassword());

        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    private boolean isAjax(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getHeader("X-Requested-With").equals("XMLHttpRequest")){
            return true;
        }
        return false;
    }
}
