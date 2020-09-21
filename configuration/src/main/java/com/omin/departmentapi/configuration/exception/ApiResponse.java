package com.omin.departmentapi.configuration.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse {
	
	private final String status;
	private final String message;
}
