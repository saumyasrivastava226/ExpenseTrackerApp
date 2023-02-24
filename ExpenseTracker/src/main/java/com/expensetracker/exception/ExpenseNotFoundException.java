package com.expensetracker.exception;

import lombok.Getter;

@Getter
public class ExpenseNotFoundException  extends Exception{
	
	private String message;
	
	public ExpenseNotFoundException(String message)
	{
		this.message=message;
	}

}
