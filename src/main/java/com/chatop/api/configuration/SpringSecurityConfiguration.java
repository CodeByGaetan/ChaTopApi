package com.chatop.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.chatop.api.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> {
			// auth.requestMatchers("/admin").hasRole("ADMIN");
			// auth.requestMatchers("/user").hasRole("USER");
			auth.requestMatchers("/auth/login").permitAll();
			auth.anyRequest().authenticated();
			})	

			// .csrf(AbstractHttpConfigurer::disable);
			.csrf((csrf) -> csrf.ignoringRequestMatchers("/auth/login"))

			.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.formLogin(Customizer.withDefaults())
		;
		return http.build();
	}

    @Bean
	public BCryptPasswordEncoder passwordEncoder2() {
		return new BCryptPasswordEncoder();
	}

    @Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}
}
