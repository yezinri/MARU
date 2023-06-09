package com.bird.maru.common.filter;

import com.bird.maru.auth.service.TokenUserService;
import com.bird.maru.domain.model.type.Provider;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * Implicit Grant 방식을 지원합니다. <br>
 * 이 필터는 "/login/oauth2/token"으로 들어오는 요청을 인터셉트하여 인증을 수행합니다.
 * 요청 헤더는 Access-Token을 반드시 포함해야 하고, 그 값은 "{provider} {accessToken}"의 형태이어야 합니다.
 * Access-Token을 이용하여 Resource Server로부터 사용자 정보를 획득한 후 인증을 수행합니다.
 */
public class ImplicitOAuth2LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenUserService tokenUserService;

    public ImplicitOAuth2LoginAuthenticationFilter(String defaultFilterProcessesUrl, TokenUserService tokenUserService) {
        super(defaultFilterProcessesUrl);
        this.tokenUserService = tokenUserService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] requestInfo = request.getHeader("Access-Token").split(" ");
            Provider provider = Provider.convert(requestInfo[0]);
            String accessToken = requestInfo[1];

            OAuth2User oAuth2User = tokenUserService.loadUser(provider, accessToken);
            return new UsernamePasswordAuthenticationToken(oAuth2User, null, Collections.emptyList());
        }  catch (Exception e) {
            throw new OAuth2AuthenticationException("OAuth2 인증 실패");
        }
    }

    /**
     * AbstractAuthenticationProcessingFilter의 doFilter()에서 attemptAuthentication()를 호출한 결과로 Authentication 객체를 잘 반환하면 이 메서드를 호출한다.
     * 원칙적으로는 AuthenticationSuccessHandler와 AuthenticationFailureHandler를 구현해서 주입받은 후에 설정해주어야 했겠지만, 편의상 이 방식을 택했다.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        SecurityContextHolder.getContext()
                             .setAuthentication(authResult);

        chain.doFilter(request, response);
    }

}
