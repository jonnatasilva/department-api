package com.omin.departmentapi.adapter.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Entity
@Table(name = "department")
public class Department {
	
	@Id
	private Long code;

	private String name;

	private String address;
	
	private String city;
	
	private String state;

	private String board;
	
	private Boolean enabled = Boolean.TRUE;
}
