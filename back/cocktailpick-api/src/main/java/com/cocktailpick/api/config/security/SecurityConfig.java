package com.cocktailpick.api.config.security;

import com.cocktailpick.api.security.*;
import com.cocktailpick.api.security.oauth2.CustomOAuth2UserService;
import com.cocktailpick.api.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.cocktailpick.api.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.cocktailpick.api.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{
	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

	private final CustomUserDetailsService customUserDetailsService;

	private final CustomOAuth2UserService customOAuth2UserService;

	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	private final TokenProvider tokenProvider;

	private final TokenAuthenticationFilter tokenAuthenticationFilter;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		http
            .headers(h -> h.frameOptions().disable())
			.cors(c -> c.configurationSource(corsConfigurationSource()))
			.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf(csrf ->csrf.disable())
			.formLogin(fl ->fl.disable())
			.httpBasic(hb -> hb.disable())
			.exceptionHandling(eh ->eh.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
            .authorizeRequests(auth -> auth
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
                    .antMatchers(HttpMethod.GET, "/api/ingredients/**")
                    .hasAnyRole(USER, ADMIN)
                    .antMatchers(HttpMethod.POST, "/api/ingredients")
                    .hasRole(ADMIN)
                    .antMatchers(HttpMethod.PUT, "/api/ingredients/**")
                    .hasRole(ADMIN)
                    .antMatchers(HttpMethod.DELETE, "/api/ingredients/**")
                    .hasRole(ADMIN)
                    .antMatchers(HttpMethod.POST, "/api/user-cocktails/**")
                    .hasAnyRole(USER, ADMIN)
                    .antMatchers(HttpMethod.GET, "/api/user-cocktails/**")
                    .permitAll()
                    .antMatchers(HttpMethod.PUT, "/api/user-cocktails/**")
                    .hasAnyRole(USER, ADMIN)
                    .anyRequest()
                    .permitAll()
            )
            .oauth2Login(oauth -> oauth
                    .authorizationEndpoint(aep -> aep
                            .baseUri("/api/oauth2/authorize")
                            .authorizationRequestRepository(cookieAuthorizationRequestRepository()))
                    .redirectionEndpoint(rep -> rep
                            .baseUri("/api/oauth2/callback/*"))
                    .userInfoEndpoint(uep -> uep.userService(customOAuth2UserService))
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
            );

		http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(loginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
	}

	private LoginFilter loginFilter(AuthenticationManager authenticationManager) throws Exception {
		LoginFilter loginFilter = new LoginFilter();
		loginFilter.setFilterProcessesUrl("/api/user/login");
		loginFilter.setAuthenticationManager(authenticationManager);
		loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(tokenProvider));
		return loginFilter;
	}
}
