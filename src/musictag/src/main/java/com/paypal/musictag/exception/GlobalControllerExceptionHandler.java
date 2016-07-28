package com.paypal.musictag.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * log any exception that is neither annotated with a HttpStatus nor
	 * handled by any ExceptionHandler in controller.
	 * 
	 * @param e
	 * @throws Exception
	 */
	@ExceptionHandler(value=Exception.class)
	public void defaultExceptionHandler(Exception e) throws Exception{
		if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
			throw e;
		}
		
		logger.error(null, e);
	}
}
