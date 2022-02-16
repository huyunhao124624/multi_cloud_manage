package com.hyh.netdev.security;



import com.hyh.netdev.security.util.JwtUtil;
import com.hyh.netdev.security.util.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 配置Spring Security
 *
 * @author hyh
 */
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private CustomHttp401AuthenticationEntryPoint customHttp401AuthenticationEntryPoint;
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    private RedisUtil redisUtil;
    private JwtUtil jwtUtil;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/druid/**")
                .antMatchers("/api/user/login**")
                .antMatchers("/temp/**")
                .antMatchers("/api/user/register**")
                .antMatchers("/api/ignore/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .addFilter(new CustomAuthenticationFilter(authenticationManager(), redisUtil, jwtUtil))
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customHttp401AuthenticationEntryPoint);
    }
}
