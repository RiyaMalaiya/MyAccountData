package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.filter.AwsCognitoJwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	AwsCognitoJwtAuthenticationFilter awsCognitoJwtAuthenticationFilter;

	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	String token_issue_url;

	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().authorizeRequests().antMatchers("/login").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling();

		http.addFilterAfter(awsCognitoJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

}
