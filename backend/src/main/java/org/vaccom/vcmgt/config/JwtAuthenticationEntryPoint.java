package org.vaccom.vcmgt.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by rajeevkumarsingh on 07/12/17.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Log _log = LogFactory.getLog(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {
		
		_log.error("Responding with unauthorized error. Message - {} " + e.getMessage());
		
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
	}
}
