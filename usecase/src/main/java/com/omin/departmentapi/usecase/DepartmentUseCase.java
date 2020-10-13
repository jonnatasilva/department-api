package com.omin.departmentapi.usecase;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.omni.departmentapi.domain.entity.DepartmentEntity;

public interface DepartmentUseCase {
	
	List<DepartmentEntity> findAll(Sort sort);
	DepartmentEntity findByCode(Long code);
	
	DepartmentEntity create(DepartmentEntity entity);
	DepartmentEntity update(Long code, DepartmentEntity entity);
	
	DepartmentEntity delete(Long code);
	
}
