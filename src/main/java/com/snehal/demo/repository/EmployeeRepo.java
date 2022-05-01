package com.snehal.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.snehal.demo.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	Employee findByEmpUsername(String username);
	List<Employee> findByManagerId(int managerId);
	

}
