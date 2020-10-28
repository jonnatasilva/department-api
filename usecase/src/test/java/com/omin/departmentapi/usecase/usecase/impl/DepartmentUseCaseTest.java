package com.omin.departmentapi.usecase.usecase.impl;

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

import com.omin.departmentapi.domain.entity.DepartmentEntity;
import com.omin.departmentapi.domain.entity.vo.BoardEnum;
import com.omin.departmentapi.domain.port.DepartmentRepositoryFacade;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentUseCaseTest {
	
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int ONE_HANDRED = 100;

	private static final long CODE_ONE = 1L;
	private static final String DEPARTMENT_ONE_NAME = "Department " + CODE_ONE;
	private static final String DEPARTMENT_ONE_ADDRESS = "address";
	private static final String DEPARTMENT_CITY = "city";
	private static final String DEPARTMENT_STATE = "state";
	private static final Boolean DEPARTMENT_ENABLED = Boolean.TRUE;

	@Mock
	private DepartmentRepositoryFacade departmentRepository;
	
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
		given(departmentRepository.findAll(DepartmentUseCaseImpl.SORT_BY_CODE)).willReturn(departments);
		
		//when
		List<DepartmentEntity> result = departmentUseCase.findAll();
		
		//then
		assertThat(result).containsExactlyElementsOf(departments);
	}
	
	@Test
	public void shouldFindAEmptyListOfDepartments() {
		//given
		given(departmentRepository.findAll(DepartmentUseCaseImpl.SORT_BY_CODE)).willReturn(Collections.emptyList());
		
		//when
		List<DepartmentEntity> result = departmentUseCase.findAll();
		
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
		DepartmentEntity entity = createDepartmentOne();

		given(departmentRepository.save(entity)).willReturn(entity);
		
		//when
		DepartmentEntity result = departmentUseCase.create(entity);
		
		//then
		assertThat(result).isEqualTo(entity);
	}
	
	@Test
	public void shouldDeleteTheDepartment() {
		//given
		DepartmentEntity department = createDepartmentOne();
		given(departmentRepository.findById(CODE_ONE)).willReturn(Optional.of(department));
		given(departmentRepository.save(department)).willReturn(department);
		
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
		
		
		DepartmentEntity departmentUpdated = createDepartment(CODE_ONE, "Teste Updated");
		
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
		
		
		DepartmentEntity departmentUpdated = createDepartment(CODE_ONE, "Teste Updated");
		
		//when
		departmentUseCase.update(CODE_ONE, departmentUpdated);
	}

	private List<DepartmentEntity> generateDepartments(int numberOfElements) {
		List<DepartmentEntity> departments = new ArrayList<>();
		for (int code = 1; code <= numberOfElements; code++) {
			departments.add(createDepartment(code));
		}
		return departments;
	}

	private DepartmentEntity createDepartment(long code) {
		return createDepartment(code, DEPARTMENT_ONE_NAME);
	}
	
	private DepartmentEntity createDepartment(long code, String name) {
		return new DepartmentEntity(
				(long) code,
				name,
				DEPARTMENT_ONE_ADDRESS,
				DEPARTMENT_CITY,
				DEPARTMENT_STATE,
				BoardEnum.EIS,
				DEPARTMENT_ENABLED);
	}
	
	private DepartmentEntity createDepartmentOne() {
		return createDepartment(CODE_ONE);
	}
}
