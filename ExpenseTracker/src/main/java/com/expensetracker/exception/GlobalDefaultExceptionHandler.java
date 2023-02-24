package com.expensetracker.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	
	
	  @ExceptionHandler(ExpenseNotFoundException.class)
	  public String handleExpenseNotFoundException(HttpServletRequest request, ExpenseNotFoundException e, Model model)
	  {
		     model.addAttribute("notFound", true);
		     model.addAttribute("message", e.getMessage());
		     return "redirect:/response.html";
	  }
	  
	  @ExceptionHandler(Exception.class)
	  public String handleGlobalException(HttpServletRequest request, ExpenseNotFoundException e, Model model)
	  {
		     model.addAttribute("serverError", true);
		     model.addAttribute("message", e.getMessage());
		     model.addAttribute("stacktrace", e.getStackTrace());
		     return "redirect:/response.html";
	  }
}
