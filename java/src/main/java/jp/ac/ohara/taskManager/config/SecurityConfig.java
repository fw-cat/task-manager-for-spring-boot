package jp.ac.ohara.taskManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic(
						(basic) -> basic.disable())
				.authorizeHttpRequests(request -> {
					request
							.requestMatchers("/login/").permitAll()
							.requestMatchers("/register/").permitAll()
							.requestMatchers("/webjars/**").permitAll()
							.requestMatchers("/js/**").permitAll()
							.requestMatchers("/css/**").permitAll()
							.anyRequest().authenticated();
				})
				.formLogin(form -> {
					form.loginPage("/login/").defaultSuccessUrl("/");
				});
		return http.build();
	}

}
