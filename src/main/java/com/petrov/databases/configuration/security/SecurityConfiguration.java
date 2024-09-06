package com.petrov.databases.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    @Autowired
    private DataSource dataSource;

//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("SELECT email")
//
//    }
}
