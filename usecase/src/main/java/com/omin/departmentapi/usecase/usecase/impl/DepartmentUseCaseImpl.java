package com.omin.departmentapi.usecase.usecase.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.omin.departmentapi.usecase.DepartmentUseCase;
import com.omni.departmentapi.domain.entity.DepartmentEntity;
import com.omni.departmentapi.domain.port.DepartmentRepository;

@Service
class DepartmentUseCaseImpl implements DepartmentUseCase {
	
	//@Autowired
	private DepartmentRepository departmentRepository; 
	
	@Override
	public List<DepartmentEntity> findAll(Sort sort) {
		return departmentRepository.findAll(sort);
	}
	
	@Override
	public DepartmentEntity findByCode(Long code) {
		return departmentRepository
				.findById(code)
				.orElseThrow(EntityNotFoundException::new);
	}
	
	@Override
	public DepartmentEntity create(DepartmentEntity entity) {
		return departmentRepository.save(entity);
	}

	@Override
	@Transactional
	public DepartmentEntity delete(Long code) {
		DepartmentEntity entity = findByCode(code);
		entity.disable();
		return entity;
	}
	
	@Override
	public DepartmentEntity update(Long code, DepartmentEntity entity) {
		DepartmentEntity department = findByCode(code);
		department.copy(entity);

		return create(department);
	}
}
