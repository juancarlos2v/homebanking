package com.mindhub.homebanking.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@CrossOrigin
@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers( HttpMethod.POST, "/api/login", "/api/logout", "/api/payments").permitAll()
                .antMatchers("/api/clients/","/api/accounts/","/api/cards/","/api/transactions/").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/transactions/","/api/loans/").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/web/index.html","/web/index-admin.html","/web/register.html","/web/script/**").permitAll()

                .antMatchers("/h2-console", "/web/manager.html","/web/admin-loans.html").hasAuthority("ADMIN")
               .antMatchers( "/api/clients/current").hasAuthority("CLIENT")
                .antMatchers("/web/accounts.html", "/web/account.html", "/web/cards.html", "/web/create-cards.html", "/web/transfer.html","/web/loan-application.html").hasAuthority("CLIENT");
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");
        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");

        // turn off checking for CSRF tokens
        http.csrf().disable();
        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();
        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }



}
