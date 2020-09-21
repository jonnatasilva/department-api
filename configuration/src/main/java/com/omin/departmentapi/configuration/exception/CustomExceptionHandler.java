package com.omin.departmentapi.configuration.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse entityNotFoundExceptionHandler(EntityNotFoundException exception) {
		return new ApiResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), "Entity not found");
	}
}
