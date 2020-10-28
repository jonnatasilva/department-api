package com.omin.departmentapi.adapter.entrypoint.mapper;

import org.springframework.stereotype.Service;

import com.omin.departmentapi.adapter.entrypoint.request.DepartmentRequest;
import com.omin.departmentapi.adapter.entrypoint.response.DepartmentResponse;
import com.omin.departmentapi.adapter.model.Department;
import com.omin.departmentapi.domain.entity.DepartmentEntity;
import com.omin.departmentapi.domain.entity.vo.BoardEnum;

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
	
	public DepartmentEntity toEntity(Department department) {
		return new DepartmentEntity(department.getCode(),
				department.getName(),
				department.getAddress(),
				department.getCity(),
				department.getState(),
				BoardEnum.valueOf(department.getBoard()),
				department.getEnabled());
	}
	
	public DepartmentEntity toEntity(DepartmentRequest request) {
		return new DepartmentEntity(request.getCode(),
				request.getName(),
				request.getAddress(),
				request.getCity(),
				request.getState(),
				BoardEnum.valueOf(request.getBoard()),
				request.getEnabled());
	}
	
	public Department toModel(DepartmentEntity entity) {
		return new Department(entity.getCode(),
				entity.getName(),
				entity.getAddress(),
				entity.getCity(),
				entity.getState(),
				entity.getBoard().toString(),
				entity.getEnabled());
	}
}
