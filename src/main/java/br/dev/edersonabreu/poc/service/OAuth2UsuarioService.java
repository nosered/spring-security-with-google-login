package br.dev.edersonabreu.poc.service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.dev.edersonabreu.poc.domain.Usuario;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OAuth2UsuarioService extends DefaultOAuth2UserService {

	private final UsuarioService usuarioService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		OAuth2User oAuthUsuario = super.loadUser(userRequest);
		String oAuth2UserNome = oAuthUsuario.getAttribute("name");
		String oAuth2UserEmail = oAuthUsuario.getAttribute("email");
		
		Usuario usuario = usuarioService.consultar(oAuth2UserEmail);
		if(Objects.isNull(usuario)) {
			usuario = Usuario.builder()
					.nome(oAuth2UserNome)
					.email(oAuth2UserEmail)
					.build();
			
			usuarioService.cadastrar(usuario);
			
			usuario = usuarioService.consultar(oAuth2UserEmail);
		}
		
		Set<GrantedAuthority> authorities = usuario.getPermissoes().stream()
											.map(permissao -> new SimpleGrantedAuthority(permissao.getDescricao()))
											.collect(Collectors.toSet());
		authorities.addAll(oAuthUsuario.getAuthorities());

		return new DefaultOAuth2User(authorities, oAuthUsuario.getAttributes(), "email");
	}
}