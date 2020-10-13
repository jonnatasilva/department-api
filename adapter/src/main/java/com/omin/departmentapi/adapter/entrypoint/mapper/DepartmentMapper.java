package com.omin.departmentapi.adapter.entrypoint.mapper;

import org.springframework.stereotype.Service;

import com.omin.departmentapi.adapter.entrypoint.response.DepartmentResponse;
import com.omni.departmentapi.domain.entity.DepartmentEntity;

@Service
public class DepartmentMapper {
	
	public DepartmentResponse toResponse(DepartmentEntity entity) {
		return new DepartmentResponse(entity.getCode(),
				entity.getName(),
				entity.getAddress(),
				entity.getCity(),
				entity.getState(),
				entity.getBoard().getDescription(),
				entity.getEnabled());
	}
}
