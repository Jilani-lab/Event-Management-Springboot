package com.springboot.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		// get JWT token fron http request
		String token = getTokenFromRequest(request);
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			
			//get username from token
			String username=jwtTokenProvider.getUsername(token);
			
			//load the username from database inbuild class Userdetails
			//load the user assosiate the token
			UserDetails userDetails= userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authenticationToken = new 
					UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
		}
		filterChain.doFilter(request, response);
		
		//get Jwt token from HttpRequest
		
		
	}
	private String getTokenFromRequest(HttpServletRequest request) {
		
		String bearerToekn = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToekn) && bearerToekn.startsWith("Bearer ")) {
			return bearerToekn.substring(7,bearerToekn.length());
		}
		return null;
	}

}
