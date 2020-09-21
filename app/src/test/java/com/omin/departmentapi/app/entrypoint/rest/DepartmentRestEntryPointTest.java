package com.omin.departmentapi.app.entrypoint.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omin.departmentapi.app.entrypoint.mapper.DepartmentMapper;
import com.omin.departmentapi.app.entrypoint.response.DepartmentResponse;
import com.omin.departmentapi.core.entity.DepartmentEntity;
import com.omin.departmentapi.core.entity.vo.BoardEnum;
import com.omin.departmentapi.core.usecase.DepartmentUseCase;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentRestEntryPointTest {
	
	private static final int ONE_HUNDRED = 100;
	private static final Long CODE_ONE = 1L;

	private static final Sort SORT_BY_CODE = Sort.by("code");
	
	private MockMvc mockMvc;
	
	@Mock
	private DepartmentUseCase departmentUseCase;
	
	@Spy
	private DepartmentMapper departmentMapper;
	
	@InjectMocks
	private DepartmentRestEntryPoint departmentRestEntryPoint;
	
	private JacksonTester<List<DepartmentResponse>> departmentsResponseTester;
	
	private JacksonTester<DepartmentResponse> departmentResponseTester;
	private JacksonTester<DepartmentEntity> departmentEntityTester;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(departmentRestEntryPoint)
				.build();
		
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void shouldFindAll() throws Exception {
		//given
		List<DepartmentEntity> departments = generateDepartments(ONE_HUNDRED);
		
		given(departmentUseCase.findAll(SORT_BY_CODE)).willReturn(departments);
		
		//when
		ResultActions result = mockMvc.perform(get("/departments"));
		
		//then
		result.andExpect(status().isOk());
		List<DepartmentResponse> body = extractResponseAsList(result);
		
		assertThat(body).hasSize(ONE_HUNDRED);
	}

	@Test
	public void shouldADepartmentByCode() throws Exception {
		//given
		DepartmentEntity department = generateDepartments(ONE_HUNDRED).get(0);
		
		given(departmentUseCase.findByCode(1L)).willReturn(department);
		
		//when
		ResultActions result = mockMvc.perform(get("/departments/1"));
		
		//then
		result.andExpect(status().isOk());
		
		DepartmentResponse body = extractResponse(result);
		
		assertThat(body).isNotNull();
	}
	
	@Test
	public void shouldCreateANewDepartment() throws Exception {
		//given
		DepartmentEntity entity = generateDepartments(ONE_HUNDRED).get(0);
		
		given(departmentUseCase.create(entity)).willReturn(entity);
		
		//when
		ResultActions result = mockMvc
				.perform(post("/departments")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(departmentEntityTester.write(entity).getJson()));
		
		//then
		result.andExpect(status().isCreated());
		result.andExpect(header().string("Location", "/departments/1"));
		
		DepartmentResponse body = extractResponse(result);
		
		assertThat(body).isNotNull();
	}

	@Test
	public void shouldUpdateADepartment() throws Exception {
		//given
		DepartmentEntity entity = generateDepartments(ONE_HUNDRED).get(0);
		
		given(departmentUseCase.update(CODE_ONE, entity)).willReturn(entity);
		
		//when
		ResultActions result = mockMvc
				.perform(put("/departments/1")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(departmentEntityTester.write(entity).getJson()));
		
		//then
		result.andExpect(status().isOk());
		
		DepartmentResponse body = extractResponse(result);
		
		assertThat(body).isEqualTo(departmentMapper.toResponse(entity));
	}
	
	
	@Test
	public void shouldDADepartment() throws Exception {
		//given
		DepartmentEntity entity = generateDepartments(ONE_HUNDRED).get(0);

		given(departmentUseCase.delete(CODE_ONE)).willReturn(entity);
		
		//when
		ResultActions result = mockMvc.perform(delete("/departments/1"));
		
		//then
		result.andExpect(status().isOk());
		
		DepartmentResponse body = extractResponse(result);
		
		assertThat(body).isEqualTo(departmentMapper.toResponse(entity));
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
		department.setName("Department " + code);
		department.setAddress("address");
		department.setCity("city");
		department.setState("state");
		department.setBoard(BoardEnum.EIS);
		department.setEnabled(Boolean.TRUE);
		department.setBoard(BoardEnum.NEGOCIOS);
		return department;
	}
	
	private DepartmentResponse extractResponse(ResultActions result) throws UnsupportedEncodingException, IOException {
		String bodyAsString = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		return departmentResponseTester.parseObject(bodyAsString);
	}
	
	private List<DepartmentResponse> extractResponseAsList(ResultActions result) throws UnsupportedEncodingException, IOException {
		String bodyAsString = result.andReturn().getResponse().getContentAsString();
		List<DepartmentResponse> body = departmentsResponseTester.parseObject(bodyAsString);
		return body;
	}
}
