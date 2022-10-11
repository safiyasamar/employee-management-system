package com.demo.spring.entity;

public class EmployeeDto {

	private int empNo;
	private String name;
	private String city;
	private double salary;

	public EmployeeDto() {
	}

	public EmployeeDto(int empNo, String name, String city, double salary) {
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
