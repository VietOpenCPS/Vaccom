package org.vaccom.vcmgt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSFilter extends CorsFilter {
	public CORSFilter(CorsConfigurationSource source) {
		super((CorsConfigurationSource) source);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Origin, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		if (response.getHeader("Access-Control-Allow-Origin") == null)
			response.addHeader("Access-Control-Allow-Origin", "*");
		filterChain.doFilter(request, response);
	}
}
