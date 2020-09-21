package com.omin.departmentapi.core.usecase.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import com.omin.departmentapi.core.dataprovider.DepartmentRepository;
import com.omin.departmentapi.core.entity.DepartmentEntity;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentUseCaseTest {
	
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int ONE_HANDRED = 100;

	private static final long CODE_ONE = 1L;

	private static final Sort SORT_BY_CODE = Sort.by("code");

	@Mock
	private DepartmentRepository departmentRepository;
	
	@InjectMocks
	private DepartmentUseCaseImpl departmentUseCase;
	
	@Test
	public void contextLoads() {
		assertThat(departmentUseCase).isNotNull();
	}
	
	@Test
	public void shouldFindAllDepartments() {
		//given
		List<DepartmentEntity> departments = generateDepartments(ONE_HANDRED);
		given(departmentRepository.findAll(SORT_BY_CODE)).willReturn(departments);
		
		//when
		List<DepartmentEntity> result = departmentUseCase.findAll(SORT_BY_CODE);
		
		//then
		assertThat(result).containsExactlyElementsOf(departments);
	}
	
	@Test
	public void shouldFindAEmptyListOfDepartments() {
		//given
		given(departmentRepository.findAll(SORT_BY_CODE)).willReturn(Collections.emptyList());
		
		//when
		List<DepartmentEntity> result = departmentUseCase.findAll(SORT_BY_CODE);
		
		//then
		assertThat(result).isEmpty();
	}
	
	@Test
	public void shouldFindADepartmentByCode() {
		//given
		DepartmentEntity department = generateDepartments(ONE).get(ZERO);
		given(departmentRepository.findById(CODE_ONE)).willReturn(Optional.of(department));
		
		//when
		DepartmentEntity result = departmentUseCase.findByCode(CODE_ONE);
		
		//then
		assertThat(result).isEqualTo(department);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void shouldThrowsAnExceptionWhenNoDepartmentIsFoundByCode() {
		//given
		given(departmentRepository.findById(CODE_ONE)).willReturn(Optional.empty());
				
		//when
		departmentUseCase.findByCode(CODE_ONE);
	}
	
	@Test
	public void shouldCreateANewDepartment() {
		//given
		DepartmentEntity entity = new DepartmentEntity();

		DepartmentEntity entityCreated = new DepartmentEntity();
		entityCreated.setCode(CODE_ONE);
		given(departmentRepository.save(entity)).willReturn(entityCreated);
		
		//when
		DepartmentEntity result = departmentUseCase.create(entity);
		
		//then
		assertThat(result).isEqualTo(entityCreated);
	}
	
	@Test
	public void shouldDeleteTheDepartment() {
		//given
		DepartmentEntity department = generateDepartments(ONE).get(ZERO);
		given(departmentRepository.findById(CODE_ONE)).willReturn(Optional.of(department));
		
		//when
		DepartmentEntity result = departmentUseCase.delete(CODE_ONE);
		
		//then
		assertThat(result.getEnabled()).isFalse();
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void shouldThrowsAnExceptionOnTryDeleteADepartmentNotFound() {
		//given
		given(departmentRepository.findById(CODE_ONE)).willReturn(Optional.empty());
		
		//when
		departmentUseCase.delete(CODE_ONE);
	}
	
	@Test
	public void shouldUpdateADepartment() {
		//given
		DepartmentEntity department = generateDepartments(ONE).get(ZERO);
		given(departmentRepository.findById(CODE_ONE)).willReturn(Optional.of(department));
		
		
		DepartmentEntity departmentUpdated = new DepartmentEntity();
		departmentUpdated.setCode(CODE_ONE);
		departmentUpdated.setName("Teste Updated");
		
		given(departmentRepository.save(departmentUpdated)).willReturn(departmentUpdated);
		
		//when
		DepartmentEntity result = departmentUseCase.update(CODE_ONE, departmentUpdated);
		
		//then
		assertThat(result).isEqualTo(departmentUpdated);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void shouldThrowsAnExceptionOnTryUpdateADepartmentNotFound() {
		//given
		given(departmentRepository.findById(CODE_ONE)).willReturn(Optional.empty());
		
		
		DepartmentEntity departmentUpdated = new DepartmentEntity();
		departmentUpdated.setCode(CODE_ONE);
		departmentUpdated.setName("Teste Updated");
		
		//when
		departmentUseCase.update(CODE_ONE, departmentUpdated);
	}

	private List<DepartmentEntity> generateDepartments(int numberOfElements) {
		List<DepartmentEntity> departments = new ArrayList<>();
		for (int code = 1; code <= numberOfElements; code++) {
			departments.add(createADepartment(code));
		}
		return departments;
	}

	private DepartmentEntity createADepartment(int code) {
		DepartmentEntity department = new DepartmentEntity();
		department.setCode((long) code);
		return department;
	}
}
