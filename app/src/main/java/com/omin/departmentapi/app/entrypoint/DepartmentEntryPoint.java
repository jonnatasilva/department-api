package com.omin.departmentapi.app.entrypoint;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omin.departmentapi.app.entrypoint.response.DepartmentResponse;
import com.omin.departmentapi.core.entity.DepartmentEntity;

public interface DepartmentEntryPoint {
	
	List<DepartmentResponse> findAll();
	DepartmentResponse findByCode(Long code);
	
	ResponseEntity<DepartmentResponse> create(DepartmentEntity entity) throws URISyntaxException;
	DepartmentResponse update(Long code, DepartmentEntity entity);
	
	DepartmentResponse delete(Long code);
}
