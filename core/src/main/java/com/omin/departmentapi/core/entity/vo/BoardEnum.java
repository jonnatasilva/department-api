package com.omin.departmentapi.core.entity.vo;

import lombok.Getter;

@Getter
public enum BoardEnum {
	
	EIS("E.I.S"),
	RECUPERACAO("Recuperação"),
	NEGOCIOS("Negócios");
	
	private String description;
	
	BoardEnum(String description) {
		this.description = description;
	}
}
