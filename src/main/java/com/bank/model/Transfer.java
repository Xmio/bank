package com.bank.model;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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
public class Transfer {

	@Id
	@GeneratedValue(strategy = AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	private Account origin;

	@NotNull
	@ManyToOne
	private Account destiny;

	@NotNull
	private Double value;

}
