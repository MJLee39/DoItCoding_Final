package com.example.finalpro;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests()
                .requestMatchers("/","/login","/signUp","/list","/service1","/main","/detail","/search").permitAll()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/css/**","/images/**","/image/**","/**", "/db/**").permitAll()
                //.requestMatchers("/admin/**").hasRole("admin")
                .anyRequest().authenticated();

        http.formLogin().loginPage("/login").permitAll()
                .failureUrl("/loginFailed")
                .defaultSuccessUrl("/main",true);        //로그인 성공 후 이동 페이지 true를 붙여서 붙여서 절대경로 설정


        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/main");

        http.httpBasic();

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return (web -> web.ignoring().requestMatchers("/static/images/**", "/static/image/**","/js/**","/static/css/**"));
//    }
}
