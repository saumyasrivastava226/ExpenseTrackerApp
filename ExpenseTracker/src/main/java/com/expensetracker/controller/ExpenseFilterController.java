package com.expensetracker.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.dto.ExpenseFilterDTO;
import com.expensetracker.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ExpenseFilterController {

	
	
	private final ExpenseService expenseService;
	@GetMapping("/filterExpenses")
	public String filterExpenses(@ModelAttribute("filter")ExpenseFilterDTO expenseFilterDTO, Model model) throws ParseException
	{
		//We will call service method here
		
		 List<ExpenseDTO> list=expenseService.getFilteredExpenses(expenseFilterDTO);
		 model.addAttribute("expenses",list);
		 
		  String totalExpenses= expenseService.totalExpenses(list);
		  model.addAttribute("totalExpenses", totalExpenses);
		 return "expenses-list";
	}
	
	
}
