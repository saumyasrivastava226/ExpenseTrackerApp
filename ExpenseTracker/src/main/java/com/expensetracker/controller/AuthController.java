package com.expensetracker.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.expensetracker.dto.UserDTO;
import com.expensetracker.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
      // making the handler method and provide the mapping, it will return the view name
	
	private final UserService userService;
	@GetMapping( value={"","/","/login"})
	public String showLoginPage()
	{
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		if(auth==null|| auth instanceof AnonymousAuthenticationToken)
		{
			return "login";
		}
		return "redirect:/expenses";
	}
	
	
	@GetMapping("/register")
	public String showRegisterPage(Model model)
	{
		
		model.addAttribute("user", new UserDTO());
		
		 return "register";
		 
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") UserDTO userDTO,BindingResult result, Model model)
	{
		   System.out.println("Printing the user details :"+userDTO);
		   
		   if(result.hasErrors())
			   return "register";
		   // UserService method
		   userService.save(userDTO);
		   
		   model.addAttribute("successMsg",true);
		   return "response";// once the user has registered we would like to take him to login page
	}
}
