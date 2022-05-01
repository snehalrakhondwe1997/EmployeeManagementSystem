package com.snehal.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity


public class Employee {
	@Id
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq")
	@SequenceGenerator(name="seq", initialValue=1000)
	private int  empId;
	private String empName;
	private double empSalary;
	@Column(unique = true)
	@Email
	private String  empEmailId;
	@Range(min =21, message="Employee age cannot be less than 10 years")
	private int empage;
	
	@Column(unique = true)
    @Length(max=13 ,min=10 , message="Cell number cannot be less than 10 characters")
	private String empCellNo;
	@Length(min=3, message="Address cannot be less than 3 characters")
	private String empAddress;
	@NonNull
	private EmpRole empDesignation;
	private float empExperience;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="deptId")
	@JsonIgnore
	private Department empdepartments;
	
	@Column(unique = true)
	private String empUsername;
	private String empPassword;
	private int managerId;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public double getEmpSalary() {
		return empSalary;
	}
	public void setEmpSalary(double empSalary) {
		this.empSalary = empSalary;
	}
	public String getEmpEmailId() {
		return empEmailId;
	}
	public void setEmpEmailId(String empEmailId) {
		this.empEmailId = empEmailId;
	}
	public int getEmpage() {
		return empage;
	}
	public void setEmpage(int empage) {
		this.empage = empage;
	}
	public String getEmpCellNo() {
		return empCellNo;
	}
	public void setEmpCellNo(String empCellNo) {
		this.empCellNo = empCellNo;
	}
	public String getEmpAddress() {
		return empAddress;
	}
	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}
	public EmpRole getEmpDesignation() {
		return empDesignation;
	}
	public void setEmpDesignation(EmpRole employee) {
		this.empDesignation = employee;
	}
	public float getEmpExperience() {
		return empExperience;
	}
	public void setEmpExperience(float empExperience) {
		this.empExperience = empExperience;
	}
	public Department getEmpdepartments() {
		return empdepartments;
	}
	public void setEmpdepartments(Department empdepartments) {
		this.empdepartments = empdepartments;
	}
	public String getEmpUsername() {
		return empUsername;
	}
	public void setEmpUsername(String empUsername) {
		this.empUsername = empUsername;
	}
	public String getEmpPassword() {
		return empPassword;
	}
	public void setEmpPassword(String empPassword) {
		this.empPassword = empPassword;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", empSalary=" + empSalary + ", empEmailId="
				+ empEmailId + ", empage=" + empage + ", empCellNo=" + empCellNo + ", empAddress=" + empAddress
				+ ", empDesignation=" + empDesignation + ", empExperience=" + empExperience + ", empdepartments="
				+ empdepartments + ", empUsername=" + empUsername + ", empPassword=" + empPassword + ", managerId="
				+ managerId + "]";
	}
	
	
	}