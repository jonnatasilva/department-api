package com.omin.departmentapi.adapter.entrypoint.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class DepartmentRequest {

	private final Long code;
	private final String name;
	private final String address;
	private final String city;
	private final String state;
	private final String board;
	private final Boolean enabled;
	
	public DepartmentRequest(@JsonProperty("code") Long code,
			@JsonProperty("name") String name,
			@JsonProperty("address") String address,
			@JsonProperty("city") String city,
			@JsonProperty("state") String state,
			@JsonProperty("board") String board,
			@JsonProperty("enabled")Boolean enabled) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.board = board;
		this.enabled = enabled;
	}
}
