package com.expensetracker.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Optional<Expense> findByExpenseId(String id);
	// behind the scenes JPA will do its work
	List<Expense> findByNameContainingAndExpenseDateBetweenAndUserId(String keyword, Date startDate, Date endDate, Long id);
	
	List<Expense> findByUserId(Long id);
	
	//This finder method is responsible for findinf the expenses between two dates for a particular user
	 
	 List<Expense> findByExpenseDateBetweenAndUserId(Date startDate, Date endDate,Long id);
	
}
