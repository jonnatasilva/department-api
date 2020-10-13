package com.omin.departmentapi.adapter.entrypoint.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.omin.departmentapi.adapter.entrypoint.response.DepartmentResponse;
import com.omni.departmentapi.domain.entity.DepartmentEntity;
import com.omni.departmentapi.domain.entity.vo.BoardEnum;

public class DepartmentMapperTest {
	
	public static final long ONE = 1L;

	private DepartmentMapper departmentMapper = new DepartmentMapper();
	
	@Test
	public void shouldMapEntityToResponse() {
		//given
		DepartmentEntity entity = createADepartmentEntity();
		
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
	
	private DepartmentEntity createADepartmentEntity() {
		DepartmentEntity department = new DepartmentEntity();
		department.setCode(1L);
		department.setName("Department " + ONE);
		department.setAddress("address");
		department.setCity("city");
		department.setState("state");
		department.setBoard(BoardEnum.EIS);
		department.setEnabled(Boolean.TRUE);
		department.setBoard(BoardEnum.NEGOCIOS);
		return department;
	}
}
