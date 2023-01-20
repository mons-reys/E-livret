package com.example.Elivret.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Un filtre qui a la charge de récupérer le JWT dans les en-tetes, et de le
 * vérifier si il existe afin de construire le contexte de sécurité Spring
 * Security.
 */
public class JwtFilter extends OncePerRequestFilter {

	private JwtProvider jwtTokenProvider;

	public JwtFilter(JwtProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
		System.out.println("Init JWT filter");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		System.out.println(httpServletRequest.getHeader("Authorization"));

		String token = jwtTokenProvider.resolveToken(httpServletRequest);

		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (MyJwtException ex) {
			// Cette ligne est très importante pour garantir que
			// le contexte de sécurité est bien supprimé.
			SecurityContextHolder.clearContext();
			httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
			return;
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
