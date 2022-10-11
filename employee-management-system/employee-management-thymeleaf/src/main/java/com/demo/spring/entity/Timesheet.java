package com.demo.spring.entity;

public class Timesheet {
	
	private int empNo;
	private int tno;
	private int pId;
	private String pType;
	private String location;
	private double totalHours;
	
	
	public Timesheet() {}

	public Timesheet(int empNo, int tno, int pId, String pType, String location, double totalHours) {
		this.empNo = empNo;
		this.tno = tno;
		this.pId = pId;
		this.pType = pType;
		this.location = location;
		this.totalHours = totalHours;
	}

	public int getTno() {
		return tno;
	}

	public void setTno(int tno) {
		this.tno = tno;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getpType() {
		return pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(double totalHours) {
		this.totalHours = totalHours;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	
}
