package com.omin.departmentapi.adapter.entrypoint.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omin.departmentapi.adapter.entrypoint.mapper.DepartmentMapper;
import com.omin.departmentapi.adapter.entrypoint.request.DepartmentRequest;
import com.omin.departmentapi.adapter.entrypoint.response.DepartmentResponse;
import com.omin.departmentapi.domain.entity.DepartmentEntity;
import com.omin.departmentapi.domain.entity.vo.BoardEnum;
import com.omin.departmentapi.usecase.DepartmentUseCase;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentRestEntryPointTest {
	
	private static final int ONE_HUNDRED = 100;
	
	private static final long CODE_ONE = 1L;
	private static final String DEPARTMENT_ONE_NAME = "Department " + CODE_ONE;
	private static final String DEPARTMENT_ONE_ADDRESS = "address";
	private static final String DEPARTMENT_CITY = "city";
	private static final String DEPARTMENT_STATE = "state";
	private static final Boolean DEPARTMENT_ENABLED = Boolean.TRUE;


	private MockMvc mockMvc;
	
	@Mock
	private DepartmentUseCase departmentUseCase;
	
	@Spy
	private DepartmentMapper departmentMapper;
	
	@InjectMocks
	private DepartmentRestEntryPoint departmentRestEntryPoint;
	
	private JacksonTester<List<DepartmentResponse>> departmentsResponseTester;
	
	private JacksonTester<DepartmentResponse> departmentResponseTester;
	private JacksonTester<DepartmentRequest> departmentEntityTester;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(departmentRestEntryPoint)
				.setCustomArgumentResolvers(new SortHandlerMethodArgumentResolver())
				.build();
		
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void shouldFindAll() throws Exception {
		//given
		List<DepartmentRequest> departmentsRequest = generateDepartments(ONE_HUNDRED);
		
		List<DepartmentEntity> entities = toEntities(departmentsRequest);
		
		given(departmentUseCase.findAll()).willReturn(entities);
		
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
		DepartmentRequest department = generateDepartments(ONE_HUNDRED).get(0);
		
		DepartmentEntity entity = departmentMapper.toEntity(department);
		
		given(departmentUseCase.findByCode(1L)).willReturn(entity);
		
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
		DepartmentRequest request = generateDepartments(ONE_HUNDRED).get(0);
		DepartmentEntity entity = departmentMapper.toEntity(request);

		given(departmentUseCase.create(entity)).willReturn(entity);
		
		//when
		ResultActions result = mockMvc
				.perform(post("/departments")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(departmentEntityTester.write(request).getJson()));
		
		//then
		result.andExpect(status().isCreated());
		result.andExpect(header().string("Location", "/departments/1"));
		
		DepartmentResponse body = extractResponse(result);
		
		assertThat(body).isNotNull();
	}

	@Test
	public void shouldUpdateADepartment() throws Exception {
		//given
		DepartmentRequest request = generateDepartments(ONE_HUNDRED).get(0);
		DepartmentEntity entity = departmentMapper.toEntity(request);

		given(departmentUseCase.update(CODE_ONE, entity)).willReturn(entity);
		
		//when
		ResultActions result = mockMvc
				.perform(put("/departments/1")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(departmentEntityTester.write(request).getJson()));
		
		//then
		result.andExpect(status().isOk());
		
		DepartmentResponse body = extractResponse(result);
		
		assertThat(body).isEqualTo(departmentMapper.toResponse(entity));
	}
	
	
	@Test
	public void shouldDADepartment() throws Exception {
		//given
		DepartmentRequest request = generateDepartments(ONE_HUNDRED).get(0);
		DepartmentEntity entity = departmentMapper.toEntity(request);
		
		given(departmentUseCase.delete(CODE_ONE)).willReturn(entity);
		
		//when
		ResultActions result = mockMvc.perform(delete("/departments/1"));
		
		//then
		result.andExpect(status().isOk());
		
		DepartmentResponse body = extractResponse(result);
		
		assertThat(body).isEqualTo(departmentMapper.toResponse(entity));
	}
	
	private List<DepartmentRequest> generateDepartments(int numberOfElements) {
		List<DepartmentRequest> departments = new ArrayList<>(numberOfElements);
		for (int code = 1; code <= numberOfElements; code++) {
			departments.add(createDepartment(code));
		}
		return departments;
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
	
	private DepartmentRequest createDepartment(long code) {
		return new DepartmentRequest(
				code,
				DEPARTMENT_ONE_NAME,
				DEPARTMENT_ONE_ADDRESS,
				DEPARTMENT_CITY,
				DEPARTMENT_STATE,
				BoardEnum.EIS.toString(),
				DEPARTMENT_ENABLED);
	}
	
	private List<DepartmentEntity> toEntities(List<DepartmentRequest> departmentsRequest) {
		return departmentsRequest
				.stream()
				.map(departmentMapper::toEntity)
				.collect(Collectors.toList());
	}
}
