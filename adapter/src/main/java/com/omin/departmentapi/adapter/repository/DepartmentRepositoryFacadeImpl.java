package com.omin.departmentapi.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.omin.departmentapi.adapter.entrypoint.mapper.DepartmentMapper;
import com.omin.departmentapi.domain.entity.DepartmentEntity;
import com.omin.departmentapi.domain.port.DepartmentRepositoryFacade;

@Service
public class DepartmentRepositoryFacadeImpl implements DepartmentRepositoryFacade {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public List<DepartmentEntity> findAll(String sortProperty) {
		return departmentRepository
				.findAll(Sort.by(sortProperty))
				.stream()
				.map(departmentMapper::toEntity)
				.collect(Collectors.toList());
	}


	@Override
	public Optional<DepartmentEntity> findById(Long code) {
		return departmentRepository
				.findById(code)
				.map(departmentMapper::toEntity);
	}

	@Override
	public DepartmentEntity save(DepartmentEntity entity) {
		return departmentMapper.toEntity(departmentRepository.save(departmentMapper.toModel(entity)));
	}
}
