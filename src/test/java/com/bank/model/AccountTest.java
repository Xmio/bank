package com.bank.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.bank.exception.TransferException;

public class AccountTest {

	@Test
	public void tryToWithdraw() throws Exception {
		Account account = Account.builder().name("John").balance(100.0).build();
		account.withdraw(10.0);
		assertThat(account.getBalance()).isEqualTo(90.0);
	}

	@Test(expected = TransferException.class)
	public void tryToWithdrawWithoutBalance() throws Exception {
		Account account = Account.builder().name("John").balance(100.0).build();
		account.withdraw(100.1);
	}

	@Test
	public void tryToWithdrawWithoutBalanceMessage() {
		try {
			Account account = Account.builder().name("John").balance(100.0).build();
			account.withdraw(100.1);
		}
		catch (TransferException exception) {
			assertThat(exception.getMessage())
					.isEqualTo("Without enough balance for withdraw. Currently balance: 100.0");
		}
	}

	@Test
	public void tryToDeposit() {
		Account account = Account.builder().name("John").balance(100.0).build();
		account.deposit(10.0);
		assertThat(account.getBalance()).isEqualTo(110.0);
	}

}
