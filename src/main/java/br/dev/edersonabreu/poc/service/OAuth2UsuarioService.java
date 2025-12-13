package br.dev.edersonabreu.poc.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.dev.edersonabreu.poc.domain.Permissao;
import br.dev.edersonabreu.poc.domain.Usuario;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OAuth2UsuarioService extends DefaultOAuth2UserService {
	
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	
	private final UsuarioService usuarioService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		OAuth2User oAuth2Usuario = super.loadUser(userRequest);
		String oAuth2UserNome = oAuth2Usuario.getAttribute("name");
		String oAuth2UserEmail = oAuth2Usuario.getAttribute("email");
		Set<Permissao> oAuth2Permissoes = oAuth2Usuario.getAuthorities().stream()
											.map(authority -> Permissao.builder().descricao(authority.getAuthority()).build())
											.collect(Collectors.toSet());
		
		Usuario usuario = usuarioService.consultar(oAuth2UserEmail);
		if(Objects.isNull(usuario)) {
			usuario = Usuario.builder()
					.nome(oAuth2UserNome)
					.email(oAuth2UserEmail)
					.senha(gerarSenhaSegura(16))
					.build();
			
			usuarioService.cadastrar(usuario);
			usuario = usuarioService.consultar(oAuth2UserEmail);
		}
		usuario.getPermissoes().addAll(oAuth2Permissoes);
		usuario.setAtributos(oAuth2Usuario.getAttributes());
		
		return usuario;
	}
	
	private String gerarSenhaSegura(int numBytes) {
        byte[] randomBytes = new byte[numBytes];
        SECURE_RANDOM.nextBytes(randomBytes);
        
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}