package com.bank.model;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.bank.exception.TransferException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = AUTO)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private Double balance;

	public void withdraw(Double value) throws TransferException {
		if (balance >= value)
			balance -= value;
		else
			throw new TransferException(balance);
	}

	public void deposit(Double value) {
		balance += value;
	}

}
