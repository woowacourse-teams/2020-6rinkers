package com.cocktailpick.api.config.security;

import com.cocktailpick.api.security.*;
import com.cocktailpick.api.security.oauth2.CustomOAuth2UserService;
import com.cocktailpick.api.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.cocktailpick.api.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.cocktailpick.api.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

	private final CustomUserDetailsService customUserDetailsService;

	private final CustomOAuth2UserService customOAuth2UserService;

	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	private final TokenProvider tokenProvider;

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
			.userDetailsService(customUserDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.headers()
			.frameOptions()
			.disable()
			.and()
			.cors()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.csrf()
			.disable()
			.formLogin()
			.disable()
			.httpBasic()
			.disable()
			.exceptionHandling()
			.authenticationEntryPoint(new RestAuthenticationEntryPoint())
			.and()
			.authorizeRequests()
			.antMatchers("/",
				"/error",
				"/favicon.ico",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.svg",
				"/**/*.jpg",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/**/upload/csv")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.POST, "/api/cocktails")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.PUT, "/api/cocktails/**")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.DELETE, "/api/cocktails/**")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.POST, "/api/tags")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.PUT, "/api/tags/**")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.DELETE, "/api/tags/**")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.POST, "/api/terminologies")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.PUT, "/api/terminologies/**")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.DELETE, "/api/terminologies/**")
			.hasRole(ADMIN)
			.antMatchers(HttpMethod.POST, "/api/ingredients")
			.hasRole(USER)
			.antMatchers(HttpMethod.GET, "/api/ingredients/**")
			.hasRole(USER)
			.antMatchers(HttpMethod.PUT, "/api/ingredients/**")
			.hasRole("ADMIN")
			.anyRequest()
			.permitAll()
			.and()
			.oauth2Login()
			.authorizationEndpoint()
			.baseUri("/api/oauth2/authorize")
			.authorizationRequestRepository(cookieAuthorizationRequestRepository())
			.and()
			.redirectionEndpoint()
			.baseUri("/api/oauth2/callback/*")
			.and()
			.userInfoEndpoint()
			.userService(customOAuth2UserService)
			.and()
			.successHandler(oAuth2AuthenticationSuccessHandler)
			.failureHandler(oAuth2AuthenticationFailureHandler);

		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	private LoginFilter loginFilter() throws Exception {
		LoginFilter loginFilter = new LoginFilter();
		loginFilter.setFilterProcessesUrl("/api/user/login");
		loginFilter.setAuthenticationManager(authenticationManagerBean());
		loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(tokenProvider));
		return loginFilter;
	}
}
