package com.expensetracker.entity;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tbl_expenses")// we are going to map it to table named tbl expenses
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;// To make this field as primary key for this table we use @id annotaion and @generated value to auto-increment the value of id
	
	@Column(unique=true)
	private String expenseId;
	
	private String name;
	private String desciption;
	private BigDecimal amount;
	private Date expenseDate;
	//private String dateString; This is purely for UI purpose
	// Hence we are getting rid of it here
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	// now we have to create a service method to get the logged in user
	
	
}
