package com.snehal.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snehal.demo.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}
