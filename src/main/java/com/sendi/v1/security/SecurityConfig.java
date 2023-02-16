package com.sendi.v1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Autowired
//    private JpaUserDetailsService jpaUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                    .authorizeRequests()
                        .antMatchers("/")
                            .permitAll()
                .and()
                    .authorizeRequests()
                        .anyRequest()
                            .authenticated()
                .and()
                    .oauth2ResourceServer().jwt();

        //httpSecurity.headers().frameOptions().sameOrigin(); //for h2 database to function normally
        return httpSecurity.build();
    }

//    @Bean
//    public JpaUserDetailsManager userDetailsService(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.userDetailsService(this.jpaUserDetailsService).passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
