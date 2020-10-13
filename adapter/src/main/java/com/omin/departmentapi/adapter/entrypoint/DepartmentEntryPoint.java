package com.omin.departmentapi.adapter.entrypoint;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;

import com.omin.departmentapi.adapter.entrypoint.response.DepartmentResponse;
import com.omni.departmentapi.domain.entity.DepartmentEntity;

public interface DepartmentEntryPoint {
	
	List<DepartmentResponse> findAll(@SortDefault Sort sort);
	DepartmentResponse findByCode(Long code);
	
	ResponseEntity<DepartmentResponse> create(DepartmentEntity entity) throws URISyntaxException;
	DepartmentResponse update(Long code, DepartmentEntity entity);
	
	DepartmentResponse delete(Long code);
}
