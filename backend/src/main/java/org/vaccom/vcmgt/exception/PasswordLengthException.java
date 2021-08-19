package org.vaccom.vcmgt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.LENGTH_REQUIRED)
public class PasswordLengthException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
