package com.bank.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.exception.BankAccountNotFoundException;
import com.bank.exception.TransferException;
import com.bank.exception.TransferNotFoundException;
import com.bank.model.Account;
import com.bank.model.Transfer;

import lombok.Synchronized;

@Service
@Transactional
public class AccountService {

	@Autowired
	private EntityManager entityManager;

	public Long createNewAccount(Account account) {
		entityManager.persist(account);
		return account.getId();
	}

	public Account findBankAccount(Long accountId) throws BankAccountNotFoundException {
		Account bankAccount = entityManager.find(Account.class, accountId);
		if (bankAccount == null)
			throw new BankAccountNotFoundException(accountId);
		return bankAccount;
	}

	@Synchronized
	public Long createNewTransfer(Transfer transfer) throws BankAccountNotFoundException, TransferException {
		Account destiny = findBankAccount(transfer.getDestiny().getId());
		Account origin = findBankAccount(transfer.getOrigin().getId());

		origin.withdraw(transfer.getValue());
		destiny.deposit(transfer.getValue());

		entityManager.persist(transfer);
		return transfer.getId();
	}

	public Transfer findTransferAccount(Long transferId) throws TransferNotFoundException {
		Transfer transfer = entityManager.find(Transfer.class, transferId);
		if (transfer == null)
			throw new TransferNotFoundException(transferId);
		return transfer;
	}

}
