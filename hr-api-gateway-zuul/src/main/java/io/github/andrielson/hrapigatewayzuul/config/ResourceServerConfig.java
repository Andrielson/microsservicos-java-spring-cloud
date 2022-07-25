package io.github.andrielson.hrapigatewayzuul.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String[] ADMIN_ROUTES = { "/hr-payroll/**", "/hr-user/**", "/actuator/**",
			"/hr-worker/actuator/**", "/hr-oauth/actuator/**" };
	private static final String[] OPERATOR_ROUTES = { "/hr-worker/**" };
	private static final String[] PUBLIC_ROUTES = { "/hr-oauth/oauth/token" };
	private final JwtTokenStore tokenStore;

	public ResourceServerConfig(JwtTokenStore tokenStore) {
		super();
		this.tokenStore = tokenStore;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(PUBLIC_ROUTES).permitAll().antMatchers(HttpMethod.GET, OPERATOR_ROUTES)
				.hasAnyRole("OPERATOR", "ADMIN").antMatchers(ADMIN_ROUTES).hasRole("ADMIN").anyRequest()
				.authenticated();
		http.cors().configurationSource(corsConfigurationSource());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		var corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		var configurationSource = new UrlBasedCorsConfigurationSource();
		configurationSource.registerCorsConfiguration("/**", corsConfig);
		return configurationSource;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		var bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
