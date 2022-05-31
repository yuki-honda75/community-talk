//package com.example.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private UserDetailsService userDetailsService;
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring()
//			.antMatchers("/css/**"
//						,"/js/**");
//	}
//	
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.anyRequest().permitAll();
//		
//		http.formLogin()
//			.defaultSuccessUrl("/")
//			.loginProcessingUrl("/login")
//			.failureUrl("/?error=true")
//			.defaultSuccessUrl("http://localhost:3000/", false)
//			.usernameParameter("email")
//			.passwordParameter("password");
//		
//		http.logout()
//			.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
//			.logoutSuccessUrl("/")
//			.deleteCookies("JSESSIONID")
//			.invalidateHttpSession(true);
//	}
//}
