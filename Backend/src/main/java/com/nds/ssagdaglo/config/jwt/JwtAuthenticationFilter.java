package com.nds.ssagdaglo.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nds.ssagdaglo.config.auth.PrincipalDetails;
import com.nds.ssagdaglo.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter: 로그인 시도중...");

        // 1. username, password 받아서
        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                System.out.println(input); // username=user&password=1234 보내는 형식에 따라 이렇게 혹은 Json 으로 출력됨!
//            }

            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class); // User1(id=0, username=user, password=1234, roles=null) 같은 형태로 객체에 담김.
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면, authenticatioin 객체가 session 영역에 저장됨!
            // authentication에 내 로그인 정보가 담김.
            // DB에 있는 username과 password가 일치한다는 의미.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // => 로그인이 되었다는 의미!!
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨! " + principalDetails.getUser().getEmail());
            // authenticatioin 객체가 session 영역에 저장해야하고, return 해주면 됨.
            // return 하는 이유: 권한 관리를 security가 대신 해주므로, 편하라고 return 함.
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 필요가 없음. 단지 권한 처리때문에 session 넣어줌.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication() 함수가 실행된다.
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨: 인증 완료되었다는 의미!!");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA 방식은 아니고, Hash 암호방식
        String jwtToken = JWT.create()
                .withSubject(JwtProperties.HEADER_STRING)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 10분(60초 * 10)
                .withClaim("email", principalDetails.getUser().getEmail()) // withClaim 부분엔 내가 리턴하고 싶은 값을 담아주면 됨!
                .withClaim("nickname", principalDetails.getUser().getNickName())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken); // Bearer 다음에 무조건 한칸 띄어야됨!!!!
    }
}
