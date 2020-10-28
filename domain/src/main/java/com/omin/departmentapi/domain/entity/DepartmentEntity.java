package com.omin.departmentapi.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.omin.departmentapi.domain.entity.vo.BoardEnum;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class DepartmentEntity {
	
	@NotNull(message = "The field code must not be null")
	private Long code;

	@NotBlank(message = "The field name must not be blank")
	private String name;

	@NotBlank(message = "The field address must not be blank")
	private String address;
	
	@NotBlank(message = "The field city must not be blank")
	private String city;
	
	@NotBlank(message = "The field state must not be blank")
	private String state;

	@NotNull(message = "The field board must not be null")
	private BoardEnum board;
	
	private Boolean enabled = Boolean.TRUE;
	
	public void disable() {
		this.enabled = Boolean.FALSE;
	}
	
	public void copy(DepartmentEntity from) {
		this.name = from.getName();
		this.address = from.getAddress();
		this.city = from.getCity();
		this.state = from.getState();
		this.board = from.getBoard();
		this.enabled = from.getEnabled();
	}
}