package com.springboot.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.model.User;
import com.springboot.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 User user=userRepository.findByUsernameOrEmail(username, username).orElseThrow(()->new UsernameNotFoundException("User not find with username or email: "+ username));
		
		Set<GrantedAuthority> authorities= user.getRoles()
				.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),authorities);
	}
	
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		User user = userRepository.findByUsernameOrEmail(username, username)
//		.orElseThrow(()->new UsernameNotFoundException("User not find with username or email: "+username));
//		
//		Set<GrantedAuthority> authorities= user.getRoles()
//				.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
//	}
	
}
