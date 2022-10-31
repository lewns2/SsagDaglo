package com.nds.ssagdaglo.config;

import com.nds.ssagdaglo.config.jwt.JwtAuthenticationFilter;
import com.nds.ssagdaglo.config.jwt.JwtAuthorizationFilter;
import com.nds.ssagdaglo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .addFilter(corsFilter) // @CrossOrigin(인증 x), 시큐리티 필터에 등록 인증(o)
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager를 던져줌.
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole(('ROLE_ADMIN'))")
                .antMatchers("/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole(('ROLE_ADMIN'))")
                .antMatchers("/admin/**")
                .access("hasRole(('ROLE_ADMIN'))")
                .anyRequest().permitAll();
    }
}
