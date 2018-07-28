package com.bank.exception;

import lombok.Getter;

@Getter
public class TransferNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public TransferNotFoundException(Long accountId) {
		super("Transfer not found for id: " + accountId);
	}

}
