package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = { "empresa", "cliente", "funcionario", "veiculo", "mercadorias", "servicos" })
@ToString(exclude = { "empresa", "cliente", "funcionario", "veiculo", "mercadorias", "servicos" })
@Entity
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false, unique = true)
	private String identificacao;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Empresa empresa;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Usuario cliente;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Usuario funcionario;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Veiculo veiculo;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Mercadoria> mercadorias = new HashSet<>();
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Servico> servicos = new HashSet<>();
}