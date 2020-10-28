package com.omin.departmentapi.usecase;

import java.util.List;

import javax.validation.Valid;

import com.omin.departmentapi.domain.entity.DepartmentEntity;

public interface DepartmentUseCase {
	
	List<DepartmentEntity> findAll();
	DepartmentEntity findByCode(Long code);
	
	DepartmentEntity create(@Valid DepartmentEntity entity);
	DepartmentEntity update(Long code, @Valid DepartmentEntity entity);
	
	DepartmentEntity delete(Long code);
}
