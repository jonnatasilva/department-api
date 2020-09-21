package com.omin.departmentapi.app.entrypoint.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class DepartmentResponse {
	
	private final Long code;
	private final String name;
	private final String address;
	private final String city;
	private final String state;
	private final String board;
	private final Boolean enabled;

	public DepartmentResponse(@JsonProperty("code") Long code,
			@JsonProperty("name") String name,
			@JsonProperty("address") String address,
			@JsonProperty("city") String city,
			@JsonProperty("state") String state,
			@JsonProperty("board") String board,
			@JsonProperty("enabled") Boolean enabled
			) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.board = board;
		this.enabled = enabled;
	}
}
