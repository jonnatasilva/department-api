package com.omin.departmentapi.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.omni.departmentapi.domain.entity.DepartmentEntity;
import com.omni.departmentapi.domain.entity.vo.BoardEnum;

public class DepartmentEntityTest {

	private static final long CODE_ONE = 1L;

	@Test
	public void shouldDisableTheDepartment() {
		//given
		DepartmentEntity departmentEntity = new DepartmentEntity();
		
		//when
		departmentEntity.disable();
		
		//then
		assertThat(departmentEntity.getEnabled()).isFalse();
	}
	
	@Test
	public void shouldCopyFromADepartment() {
		//given
		DepartmentEntity departmentEntityFrom = createADepartment();
		DepartmentEntity departmentEntityTo = new DepartmentEntity();
		
		//when
		departmentEntityTo.copy(departmentEntityFrom);
				
		//then
		assertThat(departmentEntityTo).isEqualTo(departmentEntityFrom);
	}
	
	private DepartmentEntity createADepartment() {
		DepartmentEntity department = new DepartmentEntity();
		department.setName("Department " + CODE_ONE);
		department.setAddress("address");
		department.setCity("city");
		department.setState("state");
		department.setBoard(BoardEnum.EIS);
		department.setEnabled(Boolean.TRUE);
		department.setBoard(BoardEnum.NEGOCIOS);
		return department;
	}
}
