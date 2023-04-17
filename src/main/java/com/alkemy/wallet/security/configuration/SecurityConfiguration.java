package com.alkemy.wallet.security.configuration;

import com.alkemy.wallet.security.filters.JwtRequestFilter;
import com.alkemy.wallet.service.UserDetailsServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImplement userService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()

                //Auth
                .antMatchers(HttpMethod.POST,"/users/auth/login").permitAll()
                .antMatchers("/users/auth/register").permitAll()
                .antMatchers(HttpMethod.POST, "/roles/auth/register").permitAll()

                //EndpointsSec
                .antMatchers(HttpMethod.GET, "/users/paged").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.PATCH, "/users/{id}").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/accounts/{id}").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.GET, "/accounts/paged").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.POST, "/accounts").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.GET, "/accounts/balance/{id}").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.PATCH, "/accounts/{id}").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.GET, "/fixedDeposit").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.POST, "/fixedDeposit/simulate").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.PATCH, "/transactions/{id}").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.GET, "/transactions/sendUsd").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.GET, "/transactions/sendArs").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.POST, "/transactions/payment").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.GET, "/transactions/{id}").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
                .antMatchers(HttpMethod.POST, "/transactions/deposit").hasAnyAuthority(ROLE_USER, ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/transactions/list").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/transactions/list/{userId}").hasAuthority(ROLE_USER)
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.GET, "/users").hasAuthority(ROLE_ADMIN)

                .antMatchers(HttpMethod.GET, "/users/{id}").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)

                //Docs
                .antMatchers("/api/docs/**").permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}