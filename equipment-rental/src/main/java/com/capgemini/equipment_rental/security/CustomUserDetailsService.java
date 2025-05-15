package com.capgemini.equipment_rental.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capgemini.equipment_rental.entity.Users;
import com.capgemini.equipment_rental.exceptions.UserNotFoundException;
import com.capgemini.equipment_rental.repositories.UsersRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	private UsersRepository userRepository;
	
	public CustomUserDetailsService() {
	}

	@Autowired
	public CustomUserDetailsService(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws  UserNotFoundException{
		Users user = userRepository.findByEmail( email).orElseThrow(
				() -> new UserNotFoundException("User not found with  email: " + email));

		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserType()));


		return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
	
	}
}
