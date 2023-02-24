package com.expensetracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity// jpa annotation to tell that this is entity
@Table(name="tbl_users")// annotation to create table in database
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	
	@Id // to mark as primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY)// auto_increment
	private Long id;
	
	@Column(unique=true)
	private String userId;
	 
	private String name;
	
	@Column(unique=true)
	private String email;
	private String password;
	
}
