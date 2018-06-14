package com.yuhq.test.config;

import com.yuhq.test.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private  UserDetailsService userDetailsService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public SecurityConfiguration(UserDetailsService userDetailsService, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userDetailsService = userDetailsService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostConstruct
    public void init() throws Exception {
        authenticationManagerBuilder
                .authenticationProvider(securityDaoAuthenticationProvider());
//            .userDetailsService(userDetailsService)
//            .passwordEncoder(passwordEncoder());
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new MyUserDetailsService();
//    }



    @Bean
    public DaoAuthenticationProvider securityDaoAuthenticationProvider()  throws Exception {
        SecurityDaoAuthenticationProvider authProvider = new SecurityDaoAuthenticationProvider();
//        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        System.out.println(userDetailsService instanceof  MyUserDetailsService);
        return authProvider;
    }

    /**
     * 实际不可能使用， 不能配置，
     * @return
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user_1").password("123456").authorities("ROLE_USER","ROLE_ADMIN")
//                .and()
//                .withUser("user_2").password("1234567").authorities("ROLE_USER","ROLE_ADMIN");
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }


}
