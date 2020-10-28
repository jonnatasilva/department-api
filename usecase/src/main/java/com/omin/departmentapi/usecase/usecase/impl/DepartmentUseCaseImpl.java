package com.omin.departmentapi.usecase.usecase.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

import com.omin.departmentapi.domain.entity.DepartmentEntity;
import com.omin.departmentapi.domain.port.DepartmentRepositoryFacade;
import com.omin.departmentapi.usecase.DepartmentUseCase;

@Named
class DepartmentUseCaseImpl implements DepartmentUseCase {
	
	static final String SORT_BY_CODE = "code";
	
	@Inject
	private DepartmentRepositoryFacade departmentRepository; 
	
	@Override
	public List<DepartmentEntity> findAll() {
		return departmentRepository.findAll(SORT_BY_CODE);
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
	public DepartmentEntity delete(Long code) {
		DepartmentEntity entity = findByCode(code);
		entity.disable();
		
		return create(entity);
	}
	
	@Override
	public DepartmentEntity update(Long code, DepartmentEntity entity) {
		DepartmentEntity department = findByCode(code);
		department.copy(entity);

		return create(department);
	}
}
