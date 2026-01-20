package com.ey.security;

import java.io.IOException;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import com.ey.enums.Role;

import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	private final UserDetailsService userDetailsService;

	public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {

		this.jwtUtil = jwtUtil;

		this.userDetailsService = userDetailsService;

	}

	@Override
	protected void doFilterInternal(

			HttpServletRequest request,

			HttpServletResponse response,

			FilterChain filterChain

	) throws ServletException, IOException {

		String path = request.getRequestURI();

		if (path.startsWith("/api/auth/")) {

			filterChain.doFilter(request, response);

			return;

		}

		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);

			return;

		}

		String token = header.substring(7);

		try {

			String email = jwtUtil.extractEmail(token);

			Role role = jwtUtil.extractRole(token);

			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			UsernamePasswordAuthenticationToken auth =

					new UsernamePasswordAuthenticationToken(

							userDetails,

							null,

							List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))

					);

			SecurityContextHolder.getContext().setAuthentication(auth);

		} catch (JwtException | IllegalArgumentException ex) {

			SecurityContextHolder.clearContext();

		}

		filterChain.doFilter(request, response);

	}

}
