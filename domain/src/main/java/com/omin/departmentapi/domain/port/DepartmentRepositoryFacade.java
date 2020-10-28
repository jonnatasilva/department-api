package com.omin.departmentapi.domain.port;

import java.util.List;
import java.util.Optional;

import com.omin.departmentapi.domain.entity.DepartmentEntity;

public interface DepartmentRepositoryFacade {

	List<DepartmentEntity> findAll(String sortProperty);
	Optional<DepartmentEntity> findById(Long code);
	DepartmentEntity save(DepartmentEntity entity);
}
