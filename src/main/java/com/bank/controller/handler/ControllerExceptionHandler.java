package com.bank.controller.handler;

import static java.awt.TrayIcon.MessageType.ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bank.dto.MessageDTO;
import com.bank.exception.BankAccountNotFoundException;
import com.bank.exception.TransferException;
import com.bank.exception.TransferNotFoundException;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class ControllerExceptionHandler {

	@ExceptionHandler(BankAccountNotFoundException.class)
	@ResponseStatus(BAD_REQUEST)
	public @ResponseBody MessageDTO handleException(BankAccountNotFoundException exception) {
		log.error(exception.getMessage(), exception);
		return new MessageDTO(exception.getMessage(), ERROR);
	}

	@ExceptionHandler(TransferNotFoundException.class)
	@ResponseStatus(BAD_REQUEST)
	public @ResponseBody MessageDTO handleException(TransferNotFoundException exception) {
		log.error(exception.getMessage(), exception);
		return new MessageDTO(exception.getMessage(), ERROR);
	}

	@ExceptionHandler(TransferException.class)
	@ResponseStatus(BAD_REQUEST)
	public @ResponseBody MessageDTO handleException(TransferException exception) {
		log.error(exception.getMessage(), exception);
		return new MessageDTO(exception.getMessage(), ERROR);
	}

}