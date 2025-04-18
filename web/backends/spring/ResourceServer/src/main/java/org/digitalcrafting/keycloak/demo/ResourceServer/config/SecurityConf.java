package org.digitalcrafting.keycloak.demo.ResourceServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConf {

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.build();
    }
}
