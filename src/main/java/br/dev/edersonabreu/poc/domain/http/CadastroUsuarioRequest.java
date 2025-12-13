package br.dev.edersonabreu.poc.domain.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CadastroUsuarioRequest {
	
	private String nome;
	private String email;
	@ToString.Exclude
	private String senha;
}