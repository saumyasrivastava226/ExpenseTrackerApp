package com.expensetracker.dto;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
            
	@NotBlank(message="User name cannot be null")
	private String name;
	
	@Email(message="Invalid email address")
	private String email;
	
	@NotBlank(message="Password should not be null")
	@Size(min=5, message="Password should be {min} characters")
	private String password;
	private String confirmpassword;// We are not going to save this inside database
	// This is for UI purpose
}
