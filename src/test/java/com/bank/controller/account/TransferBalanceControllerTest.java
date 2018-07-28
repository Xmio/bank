package com.bank.controller.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.model.Account;
import com.bank.model.Transfer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TransferBalanceControllerTest {

	private static final Double VALUE = 10.0;
	private Account originAccount = Account.builder().name("John").balance(100.0).build();
	private Account destinyAccount = Account.builder().name("Paul").balance(100.0).build();
	private Transfer defaultTransfer = Transfer.builder().destiny(destinyAccount).origin(originAccount).value(VALUE)
			.build();

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void startup() {
		ResponseEntity<Long> originResponse = restTemplate.postForEntity("/account", originAccount, Long.class);
		originAccount.setId(originResponse.getBody());
		ResponseEntity<Long> destinyResponse = restTemplate.postForEntity("/account", destinyAccount, Long.class);
		destinyAccount.setId(destinyResponse.getBody());
	}

	@Test
	public void verifyNewOriginBalance() {
		restTemplate.postForEntity("/transfer", defaultTransfer, Long.class);
		Account account = restTemplate.getForEntity("/account/{id}", Account.class, defaultTransfer.getOrigin().getId())
				.getBody();
		assertThat(account.getBalance()).isEqualTo(defaultTransfer.getOrigin().getBalance() - VALUE);
	}

	@Test
	public void verifyNewDestinyBalance() {
		restTemplate.postForEntity("/transfer", defaultTransfer, Long.class);
		Account account = restTemplate
				.getForEntity("/account/{id}", Account.class, defaultTransfer.getDestiny().getId()).getBody();
		assertThat(account.getBalance()).isEqualTo(defaultTransfer.getDestiny().getBalance() + VALUE);
	}

}
