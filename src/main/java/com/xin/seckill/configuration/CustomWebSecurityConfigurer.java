package com.xin.seckill.configuration;


/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 安全配置
 * @date 2018-08-14 18:45
 * @Copyright (C)2018 , Luchaoxin
 */
//@Configuration
//@EnableWebSecurity
//public class CustomWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
public class CustomWebSecurityConfigurer   {


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/pages/login/**", "/js/**", "/app/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/app/login_page")
//                .loginProcessingUrl("/app/login")
//                .failureUrl("/app/login?error").permitAll()
//                .and()
//                .logout().permitAll();
//    }
}
