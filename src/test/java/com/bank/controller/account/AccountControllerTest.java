package com.bank.controller.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.model.Account;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AccountControllerTest {

	private static final Account DEFAULT_ACCOUNT = Account.builder().name("John").balance(10.0).build();

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void tryToCreateANewAccount() {
		ResponseEntity<Long> response = restTemplate.postForEntity("/account", DEFAULT_ACCOUNT, Long.class);
		assertThat(response.getStatusCode()).isEqualTo(OK);
		Account account = restTemplate.getForEntity("/account/{id}", Account.class, response.getBody()).getBody();
		assertThat(account.getName()).isEqualTo("John");
		assertThat(account.getBalance()).isEqualTo(10.0);
	}

	@Test
	public void tryToCreateANewAccountWithoutName() {
		ResponseEntity<Void> response = restTemplate.postForEntity("/account", Account.builder().balance(10.0).build(),
				Void.class);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void tryToCreateANewAccountWithoutBalance() {
		ResponseEntity<Void> response = restTemplate.postForEntity("/account", Account.builder().name("John").build(),
				Void.class);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void tryToFindANonExistentAccount() {
		ResponseEntity<Void> response = restTemplate.getForEntity("/account/{id}", Void.class, Long.MAX_VALUE);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

}
