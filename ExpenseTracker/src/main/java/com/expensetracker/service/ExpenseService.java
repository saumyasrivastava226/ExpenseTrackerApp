package com.expensetracker.service;

import java.math.BigDecimal;
import java.sql.Date;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.dto.ExpenseFilterDTO;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.util.DateTimeUtil;
import com.ibm.icu.text.NumberFormat;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class ExpenseService {
	
	private final ExpenseRepository expenseRepository ;
	private final ModelMapper modelMapper;
	private final UserService userService;
	
	/*@Autowired
	public ExpenseService(ExpenseRepository expenseRepository)
	{
		this.expenseRepository=expenseRepository;
	}
	*/
    public List<ExpenseDTO> getAllExpenses()
    {
    	User user=userService.getLoggedInUser();
    	List<Expense> list=expenseRepository.findByExpenseDateBetweenAndUserId(
    			Date.valueOf(LocalDate.now().withDayOfMonth(1)),
    			Date.valueOf(LocalDate.now()),
    			user.getId()
    			);
    	
    	List<ExpenseDTO> expenseList= list.stream().map(this::mapToDTO).collect(Collectors.toList());
    	return expenseList;
    }
    private ExpenseDTO mapToDTO(Expense expense)
    {
    	/*ExpenseDTO expenseDTO= new ExpenseDTO();
    	expenseDTO.setId(expense.getId());
    	expenseDTO.setExpenseId(expense.getExpenseId());
    	expenseDTO.setAmount(expense.getAmount());
    	expenseDTO.setName(expense.getName());
    	expenseDTO.setDesciption(expense.getDesciption());
    	expenseDTO.setExpenseDate(expense.getExpenseDate());
    	return expenseDTO;*/
    	ExpenseDTO expenseDTO=modelMapper.map(expense, ExpenseDTO.class);
    	expenseDTO.setDateString(DateTimeUtil.convertDateToString(expenseDTO.getExpenseDate()));
    	return expenseDTO;
    	
    	
    }
    
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException
    {
    	// map the DTO to entity
    	
    	
    	Expense expense=mapToEntity(expenseDTO);
    	
    	//add the logged in user to the expense entity
    	expense.setUser(userService.getLoggedInUser());
    	
    	// save the entity to database
    	
    	expense=expenseRepository.save(expense);
    	
    	// map the entity to DTO
    	
    	return mapToDTO(expense);
    	
    }
    
    private Expense mapToEntity(ExpenseDTO expenseDTO) throws ParseException
    {
    	//map the DTO to eneity
    	
    	Expense expense=modelMapper.map(expenseDTO, Expense.class);
    	// We are going to make use of modelmapper to do that
    	// generate the expenseid
    	if(expense.getId()==null)// this because if we are updating we won't generate new id
    	     expense.setExpenseId(UUID.randomUUID().toString());
    	// set the expense date
    	// We will create a new util method to do that
    	expense.setExpenseDate(DateTimeUtil.convertStringToDate(expenseDTO.getDateString()));
    	// return expense entity
    	
    	return expense;
    }
    
    public void deleteExpense(String id)
    {
    	// the delete will take object entity so first with the help of id we will get the entity 
    	
    	// then we will pass the entity that we want to delete
    	//Expense existingExpense= expenseRepository.findByExpenseId(id).orElseThrow(()-> new RuntimeException("Expense Not found"));
    	
    	// we will use getExpense method
    	Expense existingExpense=getExpense(id);
    	expenseRepository.delete(existingExpense);// We are deleting the expense object
    }
    public ExpenseDTO getExpenseById(String id)
    {
    	//Expense existingExpense= expenseRepository.findByExpenseId(id).orElseThrow(()-> new RuntimeException("Expense Not found"));
    	// we have got the expense entity now we need to map it to ExpenseDTO, we already have modelmapper for that
    	//Expense existingExpense= expenseRepository.findByExpenseId(id).orElseThrow(()-> new RuntimeException("Expense Not found"));
    	
    	Expense existingExpense=getExpense(id);
    	
    	return mapToDTO(existingExpense);
    }
    private Expense getExpense(String id)
    {
    	return expenseRepository.findByExpenseId(id).orElseThrow(()-> new RuntimeException("Expense Not found"));
    }
    
    
    
    
    
    public List<ExpenseDTO> getFilteredExpenses(ExpenseFilterDTO expenseFilterDTO) throws ParseException
    {
    	
    	String keyword=expenseFilterDTO.getKeyword();
    	String sortBy=expenseFilterDTO.getSortBy();
    	String startDateString=expenseFilterDTO.getStartDate();
    	String endDateString=expenseFilterDTO.getEndDate();
    	
    	User user=userService.getLoggedInUser();
    	Date startDate=!startDateString.isEmpty()? DateTimeUtil.convertStringToDate(startDateString): new Date(0);
    	Date endDate=!endDateString.isEmpty()? DateTimeUtil.convertStringToDate(endDateString): new Date(System.currentTimeMillis());
    	List<Expense> list= expenseRepository.findByNameContainingAndExpenseDateBetweenAndUserId(keyword, startDate, endDate,user.getId() );
    	
    	List<ExpenseDTO> filteredList= list.stream().map(this::mapToDTO).collect(Collectors.toList());
    	if(sortBy.equals("date"))
    	{
    		// sort it by date
    		filteredList.sort((o1,o2)-> o2.getExpenseDate().compareTo(o1.getExpenseDate()));
    	}
    	else
    	{
    		 // sort it by amount
    		filteredList.sort((o1,o2)-> o2.getAmount().compareTo(o1.getAmount()));
    	}
    	return filteredList;
    }
    
    public String totalExpenses(List<ExpenseDTO> expenses)
    {
    	BigDecimal sum=new BigDecimal(0);
    	BigDecimal total=expenses.stream().map(x->x.getAmount().add(sum))
    	.reduce(BigDecimal.ZERO, BigDecimal::add);
    	NumberFormat format= NumberFormat.getCurrencyInstance(new Locale("en", "in"));
    	return format.format(total).substring(2);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
