/*
 * Copyright (c) 2017 T-IMPACT Development Team. All rights reserved.
 *
 *  This software is only to be used for the purpose for which it has been
 *  provided. No part of it is to be reproduced, disassembled, transmitted,
 *  stored in a retrieval system, nor translated in any human or computer
 *  language in any way for any purposes whatsoever without the prior written
 *  consent of the Sprinter Development Team.
 *  Infringement of copyright is a serious civil and criminal offence, which can
 *  result in heavy fines and payment of substantial damages.
 */
package com.timpact.nhsbt.ai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfLogoutHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.sql.DataSource;

/**
 * <code>WebSecurityConfig</code>
 * <p>
 * <p>
 * Web Security Configuration
 * </p>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        HttpSessionCsrfTokenRepository securityCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        securityCsrfTokenRepository.setHeaderName("X-XSRF-TOKEN");
        httpSecurity.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class)
                .headers()
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/login/**", "/js/**", "/css/**", "/img/**",
                        "/web/img/**", "/templates/**", "logout", "/h2/**")
                .permitAll().anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username").passwordParameter("password")
                .successHandler(new UrlAuthenticationSuccessHandler())
                .failureHandler(new UrlAuthenticationFailureHandler())
                .loginPage("/login").permitAll()
                .and().
                logout()
                .addLogoutHandler(new SecurityContextLogoutHandler())
                .addLogoutHandler(new CsrfLogoutHandler(securityCsrfTokenRepository))
                .addLogoutHandler(new CookieClearingLogoutHandler())
                .logoutSuccessHandler(new DefaultLogoutSuccessHandler())
                .and()
                .csrf().disable();
        // .csrfTokenRepository(securityCsrfTokenRepository);
        //and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(getDataSource())
                .usersByUsernameQuery(
                        "select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?");
    }

    /**
     * Retrieves DataSource
     *
     * @return <code>DataSource</code>
     */
    private DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driverClassName);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }
}
