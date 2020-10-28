package com.omin.departmentapi.adapter.entrypoint;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.omin.departmentapi.adapter.entrypoint.request.DepartmentRequest;
import com.omin.departmentapi.adapter.entrypoint.response.DepartmentResponse;

public interface DepartmentEntryPoint {
	
	List<DepartmentResponse> findAll();
	DepartmentResponse findByCode(Long code);
	
	ResponseEntity<DepartmentResponse> create(DepartmentRequest request) throws URISyntaxException;
	DepartmentResponse update(Long code, DepartmentRequest request);
	
	DepartmentResponse delete(Long code);
}
