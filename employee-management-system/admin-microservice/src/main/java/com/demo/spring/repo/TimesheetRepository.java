package com.demo.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Integer>{
}
