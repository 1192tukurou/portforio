package jp.levtech.rookie.config;

import org.springframework.http.HttpMethod;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	/**
	 * HTTPリクエストに対するセキュリティを設定するBean
	 *
	 * @param http HTTPセキュリティ
	 * @return セキュリティに関する設定
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.formLogin(form -> form.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/main", true)
				.permitAll()
				)
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/api/products")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/api/products/{id}")).permitAll()
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll())
				.authorizeHttpRequests(requests -> requests.anyRequest().authenticated());
		return http.build();
	}

	/**
	 * パスワードのエンコーダーのBean
	 *
	 * @return パスワードのエンコーダー
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}