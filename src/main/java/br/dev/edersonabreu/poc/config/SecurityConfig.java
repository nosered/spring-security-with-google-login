package br.dev.edersonabreu.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login", "/usuario/form", "/usuario/cadastrar").permitAll()
				.requestMatchers("/h2-console/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.headers(headers -> headers
				.frameOptions(frame -> frame.disable())
			)
			.formLogin(form -> form
				.loginPage("/login") // GET
				.loginProcessingUrl("/login") // POST
				.defaultSuccessUrl("/", true)
				.failureUrl("/login?error=true")
				.permitAll()
			)
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.permitAll()
			);
		
		return httpSecurity.build();
	}
}