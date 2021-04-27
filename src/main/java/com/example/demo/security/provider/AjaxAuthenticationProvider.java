package com.example.demo.security.provider;

import com.example.demo.security.service.AccountContext;
import com.example.demo.security.token.AjaxAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 검증을 위한 메서드
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String)authentication.getCredentials();

        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, accountContext.getAccount().getPassword())){
            throw new BadCredentialsException("BadCredentialsException");
        }

        AjaxAuthenticationToken ajaxAuthenticationToken =
                new AjaxAuthenticationToken(accountContext.getAccount(), null,
                        accountContext.getAuthorities());

        return ajaxAuthenticationToken;
    }

    // 현재 파라미터로 전달되는 Authentication과 AuthenticationProvider 클래스가 사용하고자 하는 토큰과 일치할 떄 인증처리를 할 수 있게 처리
    @Override
    public boolean supports(Class<?> authenticationToken) {
        return authenticationToken.equals(AjaxAuthenticationToken.class);
    }
}
