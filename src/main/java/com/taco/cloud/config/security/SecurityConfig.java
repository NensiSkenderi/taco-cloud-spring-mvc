package com.taco.cloud.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    DataSource dataSource;

    SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* here we write code that uses the given AuthenticationManagerBuilder
        to specify how users will be looked up during authentication.
         */


        // In-memory user store
        auth.inMemoryAuthentication()
                    .withUser("nensi")
                    .password("nensi")
                    .authorities("ROLE_USER")
                .and()
                    .withUser("test")
                    .password("test")
                    .authorities("ROLE_USER");

        // JDBC user store
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                // here we override basic user auth query
                .usersByUsernameQuery("select username, password, enabled from Users" + //Customizing user detail queries
                        "where username = ?")
                // here we override group auth query
                .authoritiesByUsernameQuery("select username, authority from UserAuthorities" +
                        "where username = ?")
                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));
    }
}
