package com.example.finalpro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests()
                .requestMatchers("/", "/login", "/join", "/list", "/service1", "/loginTest", "/main").permitAll()
                .requestMatchers("/admin/**", "/admin/insertTicket").hasRole("admin")
                .anyRequest().authenticated();

        http.formLogin().loginPage("/login").permitAll()
                .failureUrl("/error")
                .defaultSuccessUrl("/service1",true);        //로그인 성공 후 이동 페이지 true를 붙여서 붙여서 절대경로 설정


        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/");

        http.httpBasic();

        return http.build();
    }
}
