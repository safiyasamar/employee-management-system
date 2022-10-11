package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAYSLIP")
public class Payslip {

	@Column(name = "EMPNO") private int empNo;
	@Id
	@Column(name = "PAYSLIPID") private int payslipId;
	@Column(name = "MONTH") private String month;
	@Column(name = "NETSALARY") private double netSalary;
	
	public Payslip() {}

	public Payslip(int empNo,int payslipId, String month, double netSalary) {
		this.empNo = empNo;
		this.payslipId = payslipId;
		this.month = month;
		this.netSalary = netSalary;
	}

	public int getPayslipId() {
		return payslipId;
	}

	public void setPayslipId(int payslipId) {
		this.payslipId = payslipId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	
	
}
