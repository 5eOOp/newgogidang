package me.seoop.newgogidang.config;

import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.security.handler.MemberLoginSuccessHandler;
import me.seoop.newgogidang.security.service.MemberUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberUserDetailsService memberUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/member/login").permitAll()
                .antMatchers("/store/list").permitAll();

        http.formLogin().loginPage("/member/login").successHandler(successHandler());
        http.logout().logoutUrl("/member/logout").logoutSuccessUrl("/");
        http.csrf().disable();
        http.oauth2Login();
        http.oauth2Login().loginPage("/").successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60*60*24*7).userDetailsService(memberUserDetailsService);
    }

    private MemberLoginSuccessHandler successHandler() {
        return new MemberLoginSuccessHandler(passwordEncoder());
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user1")
//                .password("$2a$10$xqOtww57Gh3xwqbkJZMyHe8TXhWtak2WdHNdnnXcE3q.m1PNXa1fi")
//                .roles("USER");
//    }
}
