package com.demo.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Payslip;

public interface PayslipRepository extends JpaRepository<Payslip, Integer>{
	public List<Payslip> findAllByEmpNo(int empNo);
}
