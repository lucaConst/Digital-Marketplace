package com.example.a3.config;

import com.example.a3.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {


    @Autowired
    private MyUserDetailsService userDetailService;

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(encoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/registration").permitAll()
                .requestMatchers("/index","/get_all_products").hasAnyAuthority("BUYER","PRODUCT_SELLER","ADMIN")
                .requestMatchers("/admin").hasAuthority("ADMIN")
                .requestMatchers("/register_new_product").hasAnyAuthority("PRODUCT_SELLER","ADMIN")
                .requestMatchers("/test").hasAuthority("PRODUCT_SELLER")
                .requestMatchers("/become_seller").hasAuthority("BUYER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login").
                failureUrl("/login?error=true").
                defaultSuccessUrl("/index").
                usernameParameter("user_name").passwordParameter("password").
                and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
                logoutSuccessUrl("/login").
                and().exceptionHandling().accessDeniedPage("/access-denied");;

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }


}
