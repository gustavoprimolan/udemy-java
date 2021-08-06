package com.eazybytes.springsection2.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * 
	 * 	/myAccount -> Secured
	 *  /myBalance -> Secured
	 *  /myLoans -> Secured
	 *  /myCards -> Secured
	 *  /notices -> Not Secured
	 *  /contact -> Not Secured 
	 * 
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//default implementation
//		http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
//		http.formLogin();
//		http.httpBasic();
		
		//CUSTOM IMPLEMENTATION
		http.authorizeRequests((requests) -> {
			requests.antMatchers("/myAccount").authenticated();
			requests.antMatchers("/myBalance").authenticated();
			requests.antMatchers("/myLoans").authenticated();
			requests.antMatchers("/myCards").authenticated();
			//IF ANYONE CALLING - FREE TO EXECUTE ENDPOINT
			requests.antMatchers("/notices").permitAll();
			requests.antMatchers("/contact").permitAll();
		});
		http.formLogin();
		http.httpBasic();
		
		
//		http.authorizeRequests()
//				.antMatchers("/myAccount").authenticated()
//				.antMatchers("/myBalance").authenticated()
//				.antMatchers("/myLoans").authenticated()
//				.antMatchers("/myCards").authenticated()
//				.and()
//			.formLogin().and()
//			.httpBasic();
		
		// Configuration to deny All the requests
		// DENY EVERYTHING EVEN AUTHENTICATED
		//http.authorizeRequests().anyRequest().denyAll().and().httpBasic();
		
		
//		Configuration to permit all the requests
//		http.authorizeRequests().anyRequest().permitAll().and().httpBasic();

	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// INSTEAD OF USING application.properties, WE CAN ADD USERS AND PASSWORDS IN MEMORY LIKE THIS
		// PROPERTIES:
		//		spring.security.user.name=eazybytes
		//		spring.security.user.password=12345
//		auth.inMemoryAuthentication().withUser("admin").password("12345").authorities("admin").and()
//		.withUser("user").password("12345").authorities("read").and()
//		.passwordEncoder(NoOpPasswordEncoder.getInstance());
	
		//OTHER WAY TO USE IN MEMORY IMPLEMENTATION:
		
//		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//		UserDetails user = User.withUsername("admin").password("12345").authorities("admin").build();
//		UserDetails user1 = User.withUsername("user").password("12345").authorities("read").build();
//		userDetailsManager.createUser(user);
//		userDetailsManager.createUser(user1);
//		auth.userDetailsService(userDetailsManager);	
//	}
	
	// THIS THE METHOD WE NEED TO SPRING SELECT INFOS IN DATABASE
	// WE NEED CREATE THE TABLES AS JdbcUserDetailsManager requires
	@Bean
	public UserDetailsService userDetailsService(DataSource datasource) {
		return new JdbcUserDetailsManager(datasource);
	}
	
	//WITHOUT THIS METHOD, THE AUTHENTICATION NEEDS A PasswordEncoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
}
