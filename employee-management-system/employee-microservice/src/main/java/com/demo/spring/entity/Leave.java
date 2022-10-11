package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLEAVE")
public class Leave {
	
	@Column(name ="EMPNO") private int empNo;
	@Id
	@Column(name = "LEAVEID") private int leaveId;
	@Column(name = "NOOFDAYS") private int noOfDays;
	@Column(name = "Reason") private String reason;
	@Column(name = "STATUS") private String status;
	
	public Leave() {}

	public Leave(int empNo, int leaveId, int noOfDays, String reason, String status) {
		this.empNo = empNo;
		this.leaveId = leaveId;
		this.noOfDays = noOfDays;
		this.reason = reason;
		this.status = status;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	
}
