package com.expensetracker.dto;

import java.math.BigDecimal;
import java.sql.Date;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

	private Long id;
	private String expenseId;
	
	@NotBlank(message="Expense name should not be empty")  
	 @Size(min=3, message="Expense name should be atleast {min} characters")                             // We need to validate the expense, so we will add annotation
	private String name;
	private String desciption;
	
	@NotNull(message="Expense amount should not be null")
	@Min(value=1,message="Expense amount should not be less than 1")
	private BigDecimal amount;
	private Date expenseDate;
	// changing the date format
	private String dateString;//dd-mm-yyyy
	
	
	
	 
}
