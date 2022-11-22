package com.nds.ssagdaglo.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nds.ssagdaglo.config.auth.PrincipalDetails;
import com.nds.ssagdaglo.db.entity.User;
import com.nds.ssagdaglo.db.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // 인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 타게 됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain); // 이거 있으면 500에러 계속 뜸 ㅡㅡ 한참 찾았네..
        System.out.println("인증이나 권한이 필요한 주소 요청이 됨!");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jetHeader: " + jwtHeader);

        // JWT 토큰을 검증해서, 정상적인 사용자인지 검증 필요!
        // 헤더가 없거나 Bearer로 시작하지 않는 경우
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰을 검증해서 정상적인 사용자인지 확인
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
        String userEmail = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("userEmail").asString();

        // 서명이 정상적으로 됨!
        if(userEmail != null) {
            System.out.println("username 정상: " + userEmail);
            User userEntity = userRepository.findByUserEmail(userEmail);
            System.out.println("userEntity: " + userEntity.getEmail());

            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            // JWT 토큰 서명을 통해서 서명이 정상이면 Authetication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities()); // 2번째는 원래 null이 아니라 pw가 들어가야 함.

            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 지향,
            SecurityContextHolder.getContext().setAuthentication(authentication); // 시큐리티를 저장할 수 있는 Session 공간!

            chain.doFilter(request, response);
        }
    }
}
