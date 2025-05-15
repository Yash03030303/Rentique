package com.capgemini.equipment_rental.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.equipment_rental.dto.LoginDto;
import com.capgemini.equipment_rental.dto.ResponseToken;
import com.capgemini.equipment_rental.entity.Users;
import com.capgemini.equipment_rental.exceptions.UserAlreadyExistsException;
import com.capgemini.equipment_rental.security.JwtUtils;
import com.capgemini.equipment_rental.services.UsersService;

@RestController
@RequestMapping("/auth")
public class AuthController {
		AuthenticationManager authenticationManager;
		UsersService userService;
		PasswordEncoder passwordEncoder;
		JwtUtils jwtService;
		
		@Autowired
		public AuthController(AuthenticationManager authenticationManager, UsersService userService,
				PasswordEncoder passwordEncoder, JwtUtils jwtService) {
			super();
			this.authenticationManager = authenticationManager;
			this.userService = userService;
			this.passwordEncoder = passwordEncoder;
			this.jwtService = jwtService;
		}
		
		@PostMapping("/signin")
		public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

			if (authentication.isAuthenticated()) {
				Users user = userService.findByEmail(loginDto.getEmail());
				Map<String, Object> claims = new HashMap<>();
				claims.put("name", user.getName());
				claims.put("phone", user.getPhone());
				claims.put("email", user.getEmail());
				claims.put("userid", user.getUserId());
				claims.put("usertype", user.getUserType());
				String token = jwtService.generateToken(loginDto.getEmail(), claims);
				ResponseToken responseToken = new ResponseToken(token);
				return ResponseEntity.status(HttpStatus.OK).body(responseToken);
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not Authorized !!");
		}

		@PostMapping("/register")
		public ResponseEntity<Users> registerUser(@RequestBody Users user) {
			if (userService.existsByEmail(user.getEmail()))
				throw new UserAlreadyExistsException(" Email Exists !");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
		}
		}
		
		

