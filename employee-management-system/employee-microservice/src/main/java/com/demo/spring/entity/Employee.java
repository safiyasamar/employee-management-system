package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
	@Id

	@Column(name = "EMPNO")
	private int empNo;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ADDRESS")
	private String city;
	@Column(name = "SALARY")
	private double salary;

	public Employee() {
	}

	public Employee(int empNo, String name, String city, double salary) {
		this.empNo = empNo;
		this.name = name;
		this.city = city;
		this.salary = salary;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
