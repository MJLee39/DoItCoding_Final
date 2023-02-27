package com.example.finalpro;

import com.example.finalpro.service.CustomerService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Setter
@SpringBootApplication
public class FinalproApplication {
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("passwordEncoder 만들어짐!");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(FinalproApplication.class, args);
    }

}
