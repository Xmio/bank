package com.bank.exception;

import lombok.Getter;

@Getter
public class BankAccountNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public BankAccountNotFoundException(Long accountId) {
		super("Bank account not found for id: " + accountId);
	}

}
