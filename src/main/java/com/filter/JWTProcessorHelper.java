package com.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.model.Response;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;

@Component
public class JWTProcessorHelper {

	@SuppressWarnings("rawtypes")
	@Autowired
	ConfigurableJWTProcessor configurableJWTProcessor;

	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	String token_issue_url;

	Response responseData = new Response();

	@SuppressWarnings("unchecked")
	public Authentication getAuthentication(String jwtToken) throws Exception {
		JWTClaimsSet claimset = null;
		claimset = configurableJWTProcessor.process(jwtToken.startsWith("Bearer") ? jwtToken.substring(7) : jwtToken,
				null);
		if (!isIssuedCorrectly(claimset)) {
			throw new Exception("Invalid token ");
		}

		String username = claimset.getClaims().get("username").toString();

		if (username != null) {
			responseData.setAccessToken(jwtToken.startsWith("Bearer") ? jwtToken.substring(7) : jwtToken);
			return new JwtAuthentication(null, claimset, null);
		}
		return null;
	}

	private boolean isIssuedCorrectly(JWTClaimsSet claimset) {
		return claimset.getIssuer().equals(token_issue_url);
	}
}
