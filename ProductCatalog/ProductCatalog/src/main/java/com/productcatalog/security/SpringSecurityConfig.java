package com.productcatalog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String USER = "USER";
	private static final String ADMIN = "ADMIN";

	private static final String[] AUTH_WHITELIST = {
			"**/swagger-resources/**",
	        "/swagger-ui.html",
	        "/v2/api-docs",
	        "/webjars/**"
    };
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles(USER).and().withUser("admin")
				.password("password").roles(USER, ADMIN);
	}

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers(AUTH_WHITELIST);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers(HttpMethod.GET, "api/categories").hasAnyRole(USER, ADMIN)
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
				.antMatchers(HttpMethod.POST, "api/categories").hasRole(ADMIN)
				.antMatchers(HttpMethod.PUT, "api/categories/**").hasRole(ADMIN)
				.antMatchers(HttpMethod.DELETE, "api/categories/**").hasRole(ADMIN)
				.antMatchers(HttpMethod.GET, "api/products").hasAnyRole(USER, ADMIN)
				.antMatchers(HttpMethod.POST, "api/products").hasRole(ADMIN)
				.antMatchers(HttpMethod.PUT, "api/products/**").hasRole(ADMIN)
				.antMatchers(HttpMethod.DELETE, "api/products/**").hasRole(ADMIN).and().httpBasic().and().csrf()
				.disable().formLogin().disable().headers().frameOptions().disable();
	}
}
