package br.dev.edersonabreu.poc.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

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
public class Usuario implements UserDetails, OAuth2User {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String email;	
	@ToString.Exclude
	private String senha;
	private Boolean ativo;
	
	@Builder.Default
	private transient List<Permissao> permissoes = new ArrayList<Permissao>();
	@Builder.Default
	private transient Map<String, Object> atributos = new HashMap<String, Object>();
	
	@Override
	public String getUsername() {
		return email;
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

	@Override
	public Map<String, Object> getAttributes() {
		return atributos;
	}

	@Override
	public String getName() {
		return email;
	}
}