package br.dev.edersonabreu.poc.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String email;	
	@ToString.Exclude
	private String senha;
	private Boolean ativo;
	
	private transient final List<Permissao> permissoes = new ArrayList<Permissao>();
	
	@Override
	public String getUsername() {
		return nome;
	}
	
	@Override
	public String getPassword() {
		return senha;
	}
	
	@Override
	public boolean isEnabled() {
		return ativo;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return permissoes;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}