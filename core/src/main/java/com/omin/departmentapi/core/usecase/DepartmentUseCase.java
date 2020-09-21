package com.omin.departmentapi.core.usecase;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.omin.departmentapi.core.entity.DepartmentEntity;

public interface DepartmentUseCase {
	
	List<DepartmentEntity> findAll(Sort sort);
	DepartmentEntity findByCode(Long code);
	
	DepartmentEntity create(DepartmentEntity entity);
	DepartmentEntity update(Long code, DepartmentEntity entity);
	
	DepartmentEntity delete(Long code);
	
}
