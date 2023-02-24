package com.expensetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.expensetracker.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;



@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {
	
	
	
	   private final CustomUserDetailsService customUserDetailsService;
	
	   @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		  
		  http
              .authorizeRequests()
              .requestMatchers("/","/login","/register")
              .permitAll()
              .anyRequest().authenticated()
              .and()
              .csrf().disable()
              .formLogin().loginPage("/login")
              .failureUrl("/login?error=true")
              .defaultSuccessUrl("/expenses")
              .usernameParameter("email")
              .passwordParameter("password")
              .and()
              .logout().invalidateHttpSession(true).clearAuthentication(true)
              .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .logoutSuccessUrl("/login?logout")
               . permitAll();
		  
		  return http.build(); 
	    }
	  
	 
	  
	  
	  // ignore these static resources
	   @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> ( web.ignoring()).requestMatchers("/resources/**", "/js/**", "/css/**","/css/images/**");
	    }
	  
	  
	  // This is because we want to encode the password
	   @Bean
	  public BCryptPasswordEncoder passwordEncoder()
	  {
		  return new BCryptPasswordEncoder();
	  }
	  
	 
	  @Bean
	  public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, CustomUserDetailsService userDetailService) 
	    throws Exception {
	      return http.getSharedObject(AuthenticationManagerBuilder.class)
	        .userDetailsService(customUserDetailsService)
	        .passwordEncoder(bCryptPasswordEncoder)
	        .and()
	        .build();
	  }
	 
	  
	  
	  
	      
	

}
