package com.sparta.springcore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // api 마다 접근 가능한 롤을 설정하기 위해! 필요한 annotation 입니다.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.authorizeRequests()
                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // 회원가입 login 없이 허용
                .antMatchers("/user/**").permitAll()
                // h2-console login 없이 허용
                .antMatchers("/h2-console/**").permitAll()
                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated() //어떠한 요청이 오든지 로그인과정이 없으면 로그인을 하겠다
                .and()
                //.formLogin()// 하지만 로그인페이지는 허용 -
                .formLogin()
                .loginPage("/user/login")   //login 위치
                .failureUrl("/user/login/error")    //login 실패시
                .defaultSuccessUrl("/")//로그인이 완료되었을때 url
                .permitAll()
                .and()
                .logout()//로그아웃기능도 허용  -
                .logoutUrl("/user/logout") // 이렇게 해주면 이 url이 들어오면 스프링시큐리티가 알아서 로그아웃을 시켜준다.
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/user/forbidden");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}