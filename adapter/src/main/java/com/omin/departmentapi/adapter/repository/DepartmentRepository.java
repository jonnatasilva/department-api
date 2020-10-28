package com.omin.departmentapi.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omin.departmentapi.adapter.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
