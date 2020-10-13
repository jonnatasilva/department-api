package com.omni.departmentapi.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.omni.departmentapi.domain.entity.vo.BoardEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@Entity
@Table(name = "department")
public class DepartmentEntity implements Serializable {
	
	private static final long serialVersionUID = 7117123949516186560L;

	@Id
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
	@Enumerated(EnumType.STRING)
	private BoardEnum board;
	
	private Boolean enabled = Boolean.TRUE;
	
	public void disable() {
		this.enabled = Boolean.FALSE;
	}
	
	public void copy(DepartmentEntity from) {
		this.setName(from.getName());
		this.setAddress(from.getAddress());
		this.setCity(from.getCity());
		this.setState(from.getState());
		this.setBoard(from.getBoard());
		this.setEnabled(from.getEnabled());
	}
}
