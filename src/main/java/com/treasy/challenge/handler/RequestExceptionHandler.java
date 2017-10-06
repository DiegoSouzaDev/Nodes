package com.treasy.challenge.handler;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class RequestExceptionHandler {
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String emptyResultDataAccessHandler(EmptyResultDataAccessException e) {
		return "Nó inexistente. Nenhum nó será removido";
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String entityNotFoundHandler(EntityNotFoundException e) {
		return "Não foi possivel encontrar o nó a ser atualizado ou o nó informado como 'parentId'";
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String dataIntegrityHandler(DataIntegrityViolationException e) {
		return "Não foi possivel encontrar o nó informado como 'parentId'";
	}
}
