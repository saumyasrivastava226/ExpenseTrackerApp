package com.expensetracker.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.expensetracker.dto.ExpenseDTO;

public class ExpenseValidator  implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return ExpenseDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ExpenseDTO expenseDTO=(ExpenseDTO) target;
		// we have to validate date of expenseDTO
		if(expenseDTO.getDateString().equals("")|| expenseDTO.getDateString()==null||expenseDTO.getDateString().isEmpty())
		  errors.rejectValue("dateString", null, "Expense date should not be null");
	}

	
	
}
