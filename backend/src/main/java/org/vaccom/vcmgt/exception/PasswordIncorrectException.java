package org.vaccom.vcmgt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Password incorrect")
public class PasswordIncorrectException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
