package com.autobots.automanager;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@SpringBootApplication
public class AutomanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Component
	public static class Runner implements ApplicationRunner {
		@Autowired
		public UsuarioRepositorio repositorio;

		@Override
		public void run(ApplicationArguments args) throws Exception {
			Usuario usuario = new Usuario();
			usuario.setNome("Pedro Alcântara de Bragança e Bourbon");
			usuario.setNomeSocial("Dom Pedro");
			usuario.getPerfis().add(PerfilUsuario.CLIENTE);

			Telefone telefone = new Telefone();
			telefone.setDdd("21");
			telefone.setNumero("981234576");
			usuario.getTelefones().add(telefone);

			Endereco endereco = new Endereco();
			endereco.setEstado("Rio de Janeiro");
			endereco.setCidade("Rio de Janeiro");
			endereco.setBairro("Copacabana");
			endereco.setRua("Avenida Atlântica");
			endereco.setNumero("1702");
			endereco.setCodigoPostal("22021001");
			endereco.setInformacoesAdicionais("Hotel Copacabana palace");
			usuario.setEndereco(endereco);

			Documento rg = new Documento();
			rg.setTipo(TipoDocumento.RG);
			rg.setDataEmissao(Calendar.getInstance().getTime());
			rg.setNumero("1500");

			Documento cpf = new Documento();
			cpf.setTipo(TipoDocumento.CPF);
			cpf.setDataEmissao(Calendar.getInstance().getTime());
			cpf.setNumero("00000000001");

			usuario.getDocumentos().add(rg);
			usuario.getDocumentos().add(cpf);

			Email email = new Email();
			email.setEndereco("dompedro@email.com");
			usuario.getEmails().add(email);

			repositorio.save(usuario);
		}
	}

}