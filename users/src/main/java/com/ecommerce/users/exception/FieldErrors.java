package com.ecommerce.users.exception;

import java.util.Map;

public class FieldErrors extends AbstractError {
	public Map<String, String> errors;
	public FieldErrors(Map<String, String> errors, String description) {
		super(description);
		this.errors = errors;
	}

}
