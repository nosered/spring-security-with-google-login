package br.dev.edersonabreu.poc.domain;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Permissao implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String descricao;

	@Override
	public String getAuthority() {
		return descricao;
	}
}