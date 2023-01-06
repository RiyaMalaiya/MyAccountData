package com.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AwsCognitoJwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JWTProcessorHelper jwtProcessorHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		Authentication authentication = null;

		try {
			String jwtToken = request.getHeader("Authorization");
			if (jwtToken != null && !jwtToken.isEmpty()) {
				authentication = jwtProcessorHelper.getAuthentication(jwtToken);
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception ex) {

		}

		filterChain.doFilter(request, response);

	}

}
