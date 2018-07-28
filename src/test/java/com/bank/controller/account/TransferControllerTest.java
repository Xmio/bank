package com.bank.controller.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

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
public class TransferControllerTest {

	private static final Account INEXISTENT_ACCOUNT = Account.builder().id(Long.MAX_VALUE).build();
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
	public void tryToCreateANewTransfer() {
		ResponseEntity<Long> response = restTemplate.postForEntity("/transfer", defaultTransfer, Long.class);
		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isNotNull();
	}

	@Test
	public void tryToFindANonExistentTransfer() {
		ResponseEntity<Void> response = restTemplate.getForEntity("/transfer/{id}", Void.class, Long.MAX_VALUE);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void verifyDataOfNewTransfer() {
		ResponseEntity<Long> response = restTemplate.postForEntity("/transfer", defaultTransfer, Long.class);
		Transfer transferResponse = restTemplate.getForEntity("/transfer/{id}", Transfer.class, response.getBody())
				.getBody();
		assertThat(transferResponse.getId()).isNotNull();
		assertThat(transferResponse.getValue()).isEqualTo(defaultTransfer.getValue());
		assertThat(transferResponse.getDestiny().getId()).isEqualTo(defaultTransfer.getDestiny().getId());
		assertThat(transferResponse.getOrigin().getId()).isEqualTo(defaultTransfer.getOrigin().getId());
	}

	@Test
	public void tryToTransferWithoutDestiny() {
		Transfer transfer = Transfer.builder().origin(originAccount).value(VALUE).build();
		ResponseEntity<Void> response = restTemplate.postForEntity("/transfer", transfer, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void tryToTransferWithoutValue() {
		Transfer transfer = Transfer.builder().destiny(destinyAccount).origin(originAccount).build();
		ResponseEntity<Void> response = restTemplate.postForEntity("/transfer", transfer, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void tryToTransferWithoutOrigin() {
		Transfer transfer = Transfer.builder().destiny(destinyAccount).value(VALUE).build();
		ResponseEntity<Void> response = restTemplate.postForEntity("/transfer", transfer, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void tryToTransferToANonExistentDestiny() {
		Transfer transfer = Transfer.builder().destiny(INEXISTENT_ACCOUNT).origin(originAccount).value(VALUE).build();
		ResponseEntity<Void> response = restTemplate.postForEntity("/transfer", transfer, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void tryToTransferToANonExistentOrigin() {
		Transfer transfer = Transfer.builder().destiny(destinyAccount).origin(INEXISTENT_ACCOUNT).value(VALUE).build();
		ResponseEntity<Void> response = restTemplate.postForEntity("/transfer", transfer, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

	@Test
	public void tryToTransferWithoutBalance() {
		Transfer transfer = Transfer.builder().destiny(destinyAccount).origin(originAccount).value(200.0).build();
		ResponseEntity<Void> response = restTemplate.postForEntity("/transfer", transfer, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
	}

}
