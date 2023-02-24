package com.expensetracker.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expensetracker.dto.UserDTO;
import com.expensetracker.entity.User;
import com.expensetracker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	  // we are going to autowire user repository
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	
	public void save(UserDTO userDTO)
	{
		//encoding the password before saving
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		// first of all we will have to map userdto to user 
		User user= mapToEntity(userDTO);
		 
		user.setUserId(UUID.randomUUID().toString());
		userRepository.save(user);
		
	}
	
	private User mapToEntity(UserDTO userDTO)
	{
		 return modelMapper.map(userDTO, User.class);
		
	}
	
	public User getLoggedInUser()
	{
		// We will get the logged in user from SecurityContextHolder
		 Authentication auth=  SecurityContextHolder.getContext().getAuthentication();
		 String loggedInUserEmail= auth.getName();
		 return userRepository.findByEmail(loggedInUserEmail).orElseThrow(()-> new UsernameNotFoundException("User not found"+loggedInUserEmail));
	}
	
}
