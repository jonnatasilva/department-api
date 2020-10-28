package com.omin.departmentapi.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.omin.departmentapi.domain.entity.vo.BoardEnum;

public class DepartmentEntityTest {

	private static final long CODE_ONE = 1L;
	private static final String DEPARTMENT_ONE_NAME = "Department " + CODE_ONE;
	private static final String DEPARTMENT_ONE_ADDRESS = "address";
	private static final String DEPARTMENT_CITY = "city";
	private static final String DEPARTMENT_STATE = "state";
	private static final Boolean DEPARTMENT_ENABLED = Boolean.TRUE;
	
	@Test
	public void shouldDisableTheDepartment() {
		//given
		DepartmentEntity departmentEntity = createDepartmentOne();
		
		//when
		departmentEntity.disable();
		
		//then
		assertThat(departmentEntity.getEnabled()).isFalse();
	}

	@Test
	public void shouldCopyFromADepartment() {
		//given
		DepartmentEntity departmentEntityFrom = createDepartmentNull();
		DepartmentEntity departmentEntityTo = createDepartmentOne();
		
		//when
		departmentEntityTo.copy(departmentEntityFrom);
				
		//then
		assertThat(departmentEntityTo).isEqualTo(departmentEntityFrom);
	}
	
	private DepartmentEntity createDepartmentNull() {
		return new DepartmentEntity(
				CODE_ONE,
				null,
				null,
				null,
				null,
				null,
				null);
	}
	
	private DepartmentEntity createDepartmentOne() {
		return new DepartmentEntity(
				CODE_ONE,
				DEPARTMENT_ONE_NAME,
				DEPARTMENT_ONE_ADDRESS,
				DEPARTMENT_CITY,
				DEPARTMENT_STATE,
				BoardEnum.EIS,
				DEPARTMENT_ENABLED);
	}
}
