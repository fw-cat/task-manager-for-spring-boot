package jp.ac.ohara.taskManager.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jp.ac.ohara.taskManager.service.auth.UserDetailsServiceImplt;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsServiceImplt userService;

	@Bean
	public UserDetailsManager userDetailsManager() {
		JdbcUserDetailsManager jdbcManager = new JdbcUserDetailsManager(this.dataSource);
		return jdbcManager;
	}

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
							.requestMatchers("/images/**").permitAll()
							.anyRequest().authenticated();
				})
				.formLogin(form -> {
					form
							.loginPage("/login/")
							.loginProcessingUrl("/login/")
							.defaultSuccessUrl("/")
							.failureUrl("/login/?error=true")
							.usernameParameter("mailAddress")
							.passwordParameter("password");
				})
				.userDetailsService(this.userService)
				.logout(logout -> {
					logout
							.logoutUrl("/logout/")
							.logoutSuccessUrl("/login/")
							.deleteCookies("JSESSIONID")
							.invalidateHttpSession(true);
				});
		return http.build();
	}

}
