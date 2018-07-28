package com.bank.exception;

import lombok.Getter;

@Getter
public class TransferException extends Exception {

	private static final long serialVersionUID = 1L;

	public TransferException(Double balance) {
		super("Without enough balance for withdraw. Currently balance: " + balance);
	}

}
