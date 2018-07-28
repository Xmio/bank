package com.bank.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.exception.BankAccountNotFoundException;
import com.bank.exception.TransferException;
import com.bank.exception.TransferNotFoundException;
import com.bank.model.Account;
import com.bank.model.Transfer;
import com.bank.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService service;

	@RequestMapping(method = POST, path = "account")
	private Long newAccount(@Valid @RequestBody Account account) {
		return service.createNewAccount(account);
	}

	@RequestMapping(method = GET, path = "account/{accountId}")
	private Account retriveAccountr(@PathVariable("accountId") Long accountId) throws BankAccountNotFoundException {
		return service.findBankAccount(accountId);
	}

	@RequestMapping(method = POST, path = "transfer")
	private Long newTransfer(@Valid @RequestBody Transfer transfer)
			throws BankAccountNotFoundException, TransferException {
		return service.createNewTransfer(transfer);
	}

	@RequestMapping(method = GET, path = "transfer/{transferId}")
	private Transfer retriveTransfer(@PathVariable("transferId") Long transferId)
			throws BankAccountNotFoundException, TransferNotFoundException {
		return service.findTransferAccount(transferId);
	}

}
