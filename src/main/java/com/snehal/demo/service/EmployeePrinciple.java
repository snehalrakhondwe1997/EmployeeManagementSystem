package com.snehal.demo.service;

import java.util.Collection;

import java.util.Collections;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.snehal.demo.entity.EmpRole;
import com.snehal.demo.entity.Employee;

public class EmployeePrinciple  implements UserDetails{


 Employee employee;
public EmployeePrinciple (Employee employee) {
	super();
	this.employee=employee;
}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String  str=employee.getEmpDesignation().toString();
		return Collections.singleton(new SimpleGrantedAuthority(str) );
	}

	@Override
	public String getPassword() {
		return employee.getEmpPassword();
	}

	@Override
	public String getUsername() {
		return employee.getEmpUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
