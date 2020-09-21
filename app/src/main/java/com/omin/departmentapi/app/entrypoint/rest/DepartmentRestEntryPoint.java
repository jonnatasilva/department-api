package com.omin.departmentapi.app.entrypoint.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omin.departmentapi.app.entrypoint.DepartmentEntryPoint;
import com.omin.departmentapi.app.entrypoint.mapper.DepartmentMapper;
import com.omin.departmentapi.app.entrypoint.response.DepartmentResponse;
import com.omin.departmentapi.core.entity.DepartmentEntity;
import com.omin.departmentapi.core.usecase.DepartmentUseCase;

@CrossOrigin("*")
@RestController
@RequestMapping("/departments")
class DepartmentRestEntryPoint implements DepartmentEntryPoint {

	private static final String CODE = "code";

	@Autowired
	private DepartmentUseCase departmentUseCase;
	
	@Autowired
	private DepartmentMapper departmentMapper; 
	
	@GetMapping
	@Override
	public List<DepartmentResponse> findAll() {
		return departmentUseCase
				.findAll(Sort.by(CODE))
				.stream()
				.map(departmentMapper::toResponse)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/{code}")
	@Override
	public DepartmentResponse findByCode(@PathVariable Long code) {
		DepartmentEntity department = departmentUseCase.findByCode(code);
		return departmentMapper.toResponse(department);
	}
	
	@PostMapping
	@Override
	public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentEntity entity) throws URISyntaxException {
		DepartmentEntity department = departmentUseCase.create(entity);
		
		return ResponseEntity
				.created(new URI("/departments/" + department.getCode()))
				.body(departmentMapper.toResponse(department));
	}
	
	@PutMapping("/{code}")
	@Override
	public DepartmentResponse update(@PathVariable Long code, @Valid @RequestBody DepartmentEntity entity) {
		DepartmentEntity department = departmentUseCase.update(code, entity);
		return departmentMapper.toResponse(department);
	}
	
	@DeleteMapping("/{code}")
	@Override
	public DepartmentResponse delete(@PathVariable Long code) {
		DepartmentEntity department = departmentUseCase.delete(code);
		return departmentMapper.toResponse(department);
	}
}
