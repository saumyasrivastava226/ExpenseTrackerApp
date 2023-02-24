package com.expensetracker.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.dto.ExpenseFilterDTO;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.util.DateTimeUtil;
import com.expensetracker.validator.ExpenseValidator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ExpenseController {
	
	/*private static List<ExpenseDTO> list=new ArrayList<>();
	
	static {
		ExpenseDTO expense=new ExpenseDTO();
		expense.setName("Water bill");
		expense.setDesciption("water bill");
		expense.setAmount(new BigDecimal(700.00));
		expense.setExpenseDate(new Date(System.currentTimeMillis()));
		list.add(expense);
		
		expense=new ExpenseDTO();
		expense.setName("Electricity bill");
		expense.setDesciption("electricity bill");
		expense.setAmount(new BigDecimal(900.00));
		expense.setExpenseDate(new Date(System.currentTimeMillis()));
		list.add(expense);
		// now the list has two expenses and now we have to pass the list of expenses to thymeleaf
		
		
		
	}*/ // This was the hard coded way of displaying the expenses list now we will display the list from the database
	
	// In order to do that we will use ExpenseService class
	private final ExpenseService expenseService;
	
 
	// we will create a method that will return us the view name
	@GetMapping("/expenses")
	public String showExpense(Model model)
	{
		 List<ExpenseDTO> list=expenseService.getAllExpenses();
		  model.addAttribute("expenses", list);
		  model.addAttribute("filter", new ExpenseFilterDTO(DateTimeUtil.getCurrentMonthStartDate(), DateTimeUtil.getCurrentMonthDate()));// we are adding expense filter to model  
		  String totalExpenses= expenseService.totalExpenses(list);
		  model.addAttribute("totalExpenses", totalExpenses);
		 return "expenses-list.html";
	}
	// we need to create a handler method for handling /createExpenses URL it will return the view name
	@GetMapping("/createExpenses")
	public String showExpenseForm(Model model)
	{
		model.addAttribute("expense", new ExpenseDTO());
		return "expense-form";// we don't need to add the extension
	}
	
	@PostMapping("/saveOrUpdateExpenses")
	public String saveOrUpdateExpenseDetails(@Valid @ModelAttribute("expense")ExpenseDTO expenseDTO, BindingResult result) throws ParseException
	{
		// binding the form values to java objects
		
		new ExpenseValidator().validate(expenseDTO,result);
		if(result.hasErrors()) {
			 return "expense-form";
		}
		expenseService.saveExpenseDetails(expenseDTO);
		return "redirect:/expenses";// We are redirecting to expenses lists
	
	}
	@GetMapping("/deleteExpense")
	public String deleteExpense(@RequestParam String id)
	{
		System.out.println("Prnting the expense ID -"+id);
		expenseService.deleteExpense(id);
		return "redirect:/expenses";
	}
	
	@GetMapping("/updateExpense")
	public String updateExpense(@RequestParam String id, Model model)
	{
		System.out.println("Printing the expense id inside update method: "+id);
		ExpenseDTO expense=expenseService.getExpenseById(id);
		
		model.addAttribute("expense", expense);
		return "expense-form";
	}
	
	
}
