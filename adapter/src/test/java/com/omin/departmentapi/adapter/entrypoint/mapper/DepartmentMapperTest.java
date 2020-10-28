package com.omin.departmentapi.adapter.entrypoint.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.omin.departmentapi.adapter.entrypoint.response.DepartmentResponse;
import com.omin.departmentapi.domain.entity.DepartmentEntity;
import com.omin.departmentapi.domain.entity.vo.BoardEnum;

public class DepartmentMapperTest {
	
	private static final long CODE_ONE = 1L;
	private static final String DEPARTMENT_ONE_NAME = "Department " + CODE_ONE;
	private static final String DEPARTMENT_ONE_ADDRESS = "address";
	private static final String DEPARTMENT_CITY = "city";
	private static final String DEPARTMENT_STATE = "state";
	private static final Boolean DEPARTMENT_ENABLED = Boolean.TRUE;


	
	private DepartmentMapper departmentMapper = new DepartmentMapper();
	
	@Test
	public void shouldMapEntityToResponse() {
		//given
		com.omin.departmentapi.domain.entity.DepartmentEntity entity = createDepartmentOne();
		
		//when
		DepartmentResponse response =  departmentMapper.toResponse(entity);
		
		//then
		assertThat(response.getCode()).isEqualTo(entity.getCode());
		assertThat(response.getName()).isEqualTo(entity.getName());
		assertThat(response.getAddress()).isEqualTo(entity.getAddress());
		assertThat(response.getCity()).isEqualTo(entity.getCity());
		assertThat(response.getState()).isEqualTo(entity.getState());
		assertThat(response.getBoard()).isEqualTo(entity.getBoard().getDescription());
		assertThat(response.getEnabled()).isEqualTo(entity.getEnabled());
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
