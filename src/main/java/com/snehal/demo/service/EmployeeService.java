package com.snehal.demo.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.snehal.demo.entity.Department;
import com.snehal.demo.entity.EmpRole;
import com.snehal.demo.entity.Employee;
import com.snehal.demo.error.DepartmentNotFoundException;
import com.snehal.demo.error.EmployeeNotFoundException;
import com.snehal.demo.repository.DepartmentRepo;
import com.snehal.demo.repository.EmployeeRepo;

@Service
public class EmployeeService implements UserDetailsService {
@Autowired	
EmployeeRepo repo;

@Autowired
DepartmentRepo drepo;

@Autowired
PasswordEncoder encoder;
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
Employee e = repo.findByEmpUsername(username);
if(e==null)
	throw new UsernameNotFoundException("Employee not found");
	return new EmployeePrinciple(e);
}

public List<Employee> getAll() {
	
return repo.findAll();
}

public List<Employee> getByManagerId(int managerId) throws EmployeeNotFoundException  {
	if(!repo.existsById(managerId))
		throw new EmployeeNotFoundException("Manager Not Found!!!");
	return repo.findByManagerId(managerId);
}

public List<Department> getDepartments() {
	return drepo.findAll();
}

public Department getDepartment(int deptId)throws DepartmentNotFoundException {
	if(!drepo.existsById(deptId))
		throw new DepartmentNotFoundException("Department Not Found!!!");
	
	return drepo.findById(deptId).get();
}

public Department addDepartment(Department dept) {
	return drepo.save(dept);
}

public Department updateDepartment( int deptId,Department dept) throws DepartmentNotFoundException {
	if(!drepo.existsById(deptId))
		throw new DepartmentNotFoundException("department not found....!");
	else {
		Department d=drepo.findById(deptId).get();
		if(Objects.nonNull(dept.getDeptName())|| !"".equals(dept.getDeptName()))
			d.setDeptName(dept.getDeptName());
		if(Objects.nonNull(dept.getDeptLocation())||!"".equals(dept.getDeptLocation()))
				d.setDeptLocation(dept.getDeptLocation());
		return drepo.save(d);
		
	}
}

public String deleteDepartment(int deptId) throws  DepartmentNotFoundException {
	if(!drepo.existsById(deptId))
		throw new DepartmentNotFoundException("department not found...!");
	else {
		drepo.deleteById(deptId);
		return "Record deleted succesefully...!";
	}
}
	
	
public Employee addEmployee(int deptId, Employee emp) throws DepartmentNotFoundException, EmployeeNotFoundException{
Department d=drepo.findById(deptId).get();
if(d==null)
	throw new DepartmentNotFoundException("department not found...!");
else if(!repo.existsById(emp.getManagerId())||(!repo.findById(emp.getManagerId()).get().getEmpDesignation().equals(EmpRole.MANAGER) && emp.getManagerId()!=999)) {
	throw new EmployeeNotFoundException("Employee not found...!");
}
else {
	if(repo.findById(emp.getManagerId()).get().getEmpDesignation().equals(EmpRole.MANAGER)) {
		emp.setEmpDesignation(EmpRole.EMPLOYEE);
	}
	else if(emp.getManagerId()==999) {
		emp.setEmpDesignation(EmpRole.MANAGER);
	}
	emp.setEmpPassword(encoder.encode(emp.getEmpPassword()));
	emp.setEmpdepartments(d);
	d.getEmployee().add(emp);
	
	return repo.save(emp);
}
	
}

			
public Employee updateuser(int empId, Employee emp)throws EmployeeNotFoundException {
	if(!repo.existsById(empId))
		throw new EmployeeNotFoundException("Employee not found....");
	else {
		Employee e= repo.findById(empId).get();
		if(Objects.nonNull(emp.getEmpName())||!"".equals(emp.getEmpName()))
			e.setEmpName(emp.getEmpName());
		
		if(Objects.nonNull(emp.getEmpSalary()))
			e.setEmpSalary(emp.getEmpSalary());
		
		if(Objects.nonNull(emp.getEmpEmailId())||"".equals(emp.getEmpEmailId()))
			e.setEmpEmailId(emp.getEmpEmailId());
		
		if(Objects.nonNull(emp.getEmpCellNo())||"".equals(emp.getEmpCellNo()))
			e.setEmpCellNo(emp.getEmpCellNo());
		
		if(Objects.nonNull(emp.getEmpAddress())||"".equals(emp.getEmpAddress()))
			e.setEmpAddress(emp.getEmpAddress());
		
		if(Objects.nonNull(emp.getEmpExperience()))
			e.setEmpExperience(emp.getEmpExperience());
	
	return repo.save(e);
		
	}	
	}

public String deleteUser(int empId) throws EmployeeNotFoundException{
	if(!repo.existsById(empId))
		throw new EmployeeNotFoundException("employee not found...!!");
	else {
		repo.deleteById(empId);
	
	return "Record deleted...";
	}
}

public Employee updateSelfByDirector(Employee emp, int empId) throws EmployeeNotFoundException{
if(empId!=999)
	throw new EmployeeNotFoundException("Employee not found....");
else {
	Employee e  =repo.findById(empId).get();
	if(Objects.nonNull(emp.getEmpName())||"".equals(emp.getEmpName()))
		e.setEmpName(emp.getEmpName());
	
	if(Objects.nonNull(emp.getEmpSalary()))
			e.setEmpSalary(emp.getEmpSalary());
	
	if(Objects.nonNull(emp.getEmpCellNo())||"".equals(emp.getEmpCellNo()))
		e.setEmpCellNo(emp.getEmpCellNo());
	
	if(Objects.nonNull(emp.getEmpEmailId())||"".equals(emp.getEmpEmailId()))
		e.setEmpEmailId(emp.getEmpEmailId());
	
	if(Objects.nonNull(emp.getEmpAddress())||"".equals(emp.getEmpAddress()))
		e.setEmpAddress(emp.getEmpAddress());
	
	if(Objects.nonNull(emp.getEmpExperience()))
		e.setEmpExperience(emp.getEmpExperience());
	
	if(Objects.nonNull(emp.getEmpUsername())||"".equals(emp.getEmpUsername()))
		e.setEmpUsername(emp.getEmpUsername());
	
	if(Objects.nonNull(emp.getEmpPassword())||"".equals(emp.getEmpPassword()))
		e.setEmpPassword(emp.getEmpPassword());
	

	return repo.save(e);
}
}

public List<Employee> getEmployee(int empId) throws EmployeeNotFoundException {
	if(!repo.existsById(empId) || !repo.findById(empId).get().getEmpDesignation().equals(EmpRole.MANAGER))
		throw new EmployeeNotFoundException("Employee not found ....!");
	else 
		return repo.findByManagerId(empId);
}

	


public Employee addEmpolyeeByManager(int deptId, Employee emp) throws DepartmentNotFoundException, EmployeeNotFoundException {
	
	
	if(!drepo.existsById(deptId))
		throw  new DepartmentNotFoundException("department not found....!");
	else if(!repo.existsById(emp.getManagerId()) || !repo.findById(emp.getManagerId()).get().getEmpDesignation().equals(EmpRole.MANAGER) || emp.getManagerId()==999){
		throw new EmployeeNotFoundException("Employee not found.....!");
	}
	else {
		Department d=drepo.findById(deptId).get();
		emp.setEmpDesignation(EmpRole.EMPLOYEE);
		emp.setEmpdepartments(d);
		emp.setEmpPassword(encoder.encode(emp.getEmpPassword()));
		d.getEmployee().add(emp);
		
		return repo.save(emp);
	}
	
}

public Employee updateEmployeeByManager(int empId, Employee emp) throws EmployeeNotFoundException {
	if(!repo.existsById(empId) || repo.findById(empId).get().getEmpDesignation().equals(EmpRole.MANAGER) || empId==0 ) 
		throw new EmployeeNotFoundException("Employee not found......!!");
		 else {
			Employee e = repo.findById(empId).get();
			if(Objects.nonNull(emp.getEmpName()) || !"".equals(emp.getEmpName()))
				e.setEmpName(emp.getEmpName());
			if(Objects.nonNull(emp.getEmpSalary()))
				e.setEmpSalary(emp.getEmpSalary());
			if(Objects.nonNull(emp.getEmpEmailId()) || !"".equals(emp.getEmpEmailId()))
				e.setEmpEmailId(emp.getEmpEmailId());
			if(Objects.nonNull(emp.getEmpCellNo()) || !"".equals(emp.getEmpCellNo()))
				e.setEmpCellNo(emp.getEmpCellNo());
			if(Objects.nonNull(emp.getEmpAddress()) || !"".equals(emp.getEmpAddress()))
				e.setEmpAddress(emp.getEmpAddress());
			if(Objects.nonNull(emp.getEmpExperience()))
				e.setEmpExperience(emp.getEmpExperience());
		       return repo.save(e);
		}
			
		
}
	
public Employee updateSelfEmployeeByManager(Employee emp, int empId) throws EmployeeNotFoundException {
	if(!repo.existsById(empId)||repo.findById(empId).get().getEmpDesignation().equals(EmpRole.EMPLOYEE) || empId==999)
		throw new EmployeeNotFoundException("Employee not found.....");
	else {
		Employee e = repo.findById(empId).get();
		if(Objects.nonNull(emp.getEmpName()) || !"".equals(emp.getEmpName()))
			e.setEmpName(emp.getEmpName());
		if(Objects.nonNull(emp.getEmpSalary()))
			e.setEmpSalary(emp.getEmpSalary());
		if(Objects.nonNull(emp.getEmpEmailId()) || !"".equals(emp.getEmpEmailId()))
			e.setEmpEmailId(emp.getEmpEmailId());
		if(Objects.nonNull(emp.getEmpCellNo()) || !"".equals(emp.getEmpCellNo()))
			e.setEmpCellNo(emp.getEmpCellNo());
		if(Objects.nonNull(emp.getEmpAddress()) || !"".equals(emp.getEmpAddress()))
			e.setEmpAddress(emp.getEmpAddress());
		if(Objects.nonNull(emp.getEmpExperience()))
			e.setEmpExperience(emp.getEmpExperience());
		if(Objects.nonNull(emp.getEmpUsername()) || !"".equals(emp.getEmpUsername()))
			e.setEmpUsername(emp.getEmpUsername());
		if(Objects.nonNull(emp.getEmpPassword()) || !"".equals(emp.getEmpPassword()))
			e.setEmpPassword(encoder.encode(emp.getEmpPassword()));
		return repo.save(e);
		}
	
	
}

public String deleteEmployeeByMAnager(int empId) throws EmployeeNotFoundException {
	if(!repo.existsById(empId) || repo.findById(empId).get().getEmpDesignation().equals(EmpRole.MANAGER) || empId==999)
		
		throw new EmployeeNotFoundException("Employee not found.....!!");
	else {
		repo.deleteById(empId);
		return "Record Deleted";
	}
		
}

public Employee updateSelfByEmployee(int empId, Employee emp) throws EmployeeNotFoundException {
	
	if(!repo.existsById(empId)||repo.findById(empId).get().getEmpDesignation().equals(EmpRole.MANAGER) || empId==999)
		throw new EmployeeNotFoundException("Employee not found.....");
	else {
		Employee e = repo.findById(empId).get();
		if(Objects.nonNull(emp.getEmpName()) || !"".equals(emp.getEmpName()))
			e.setEmpName(emp.getEmpName());
		if(Objects.nonNull(emp.getEmpSalary()))
			e.setEmpSalary(emp.getEmpSalary());
		if(Objects.nonNull(emp.getEmpEmailId()) || !"".equals(emp.getEmpEmailId()))
			e.setEmpEmailId(emp.getEmpEmailId());
		if(Objects.nonNull(emp.getEmpCellNo()) || !"".equals(emp.getEmpCellNo()))
			e.setEmpCellNo(emp.getEmpCellNo());
		if(Objects.nonNull(emp.getEmpAddress()) || !"".equals(emp.getEmpAddress()))
			e.setEmpAddress(emp.getEmpAddress());
		if(Objects.nonNull(emp.getEmpExperience()))
			e.setEmpExperience(emp.getEmpExperience());
		if(Objects.nonNull(emp.getEmpUsername()) || !"".equals(emp.getEmpUsername()))
			e.setEmpUsername(emp.getEmpUsername());
		if(Objects.nonNull(emp.getEmpPassword()) || !"".equals(emp.getEmpPassword()))
			e.setEmpPassword(encoder.encode(emp.getEmpPassword()));
		return repo.save(e);
	}

}


}
