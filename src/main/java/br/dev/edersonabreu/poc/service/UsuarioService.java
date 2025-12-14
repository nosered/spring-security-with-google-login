package br.dev.edersonabreu.poc.service;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.edersonabreu.poc.domain.PermissaoUsuario;
import br.dev.edersonabreu.poc.domain.Usuario;
import br.dev.edersonabreu.poc.exception.UsuarioJaExisteException;
import br.dev.edersonabreu.poc.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {
	
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private final UsuarioRepository usuarioRepository;
	
	public Usuario consultar(String email) {
		return usuarioRepository.consultar(email.toLowerCase());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = consultar(username);
		if(Objects.isNull(usuario) || Objects.isNull(usuario.getPassword())) {
			throw new UsernameNotFoundException("Usuario nao encontrado");
		}
		
		return usuario;
	}
	
	@Transactional
	public void cadastrar(Usuario usuario) {
		usuario.setEmail(usuario.getEmail().toLowerCase());
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		
		if(usuarioRepository.existeUsuario(usuario.getEmail().toLowerCase())) {
			throw new UsuarioJaExisteException();
		}
		
		Integer idUsuario = usuarioRepository.salvar(usuario);
		PermissaoUsuario permissaoUsuario = PermissaoUsuario.builder()
												.idUsuario(idUsuario)
												.idPermissao(2) // ROLE_USER
												.build();
		usuarioRepository.salvarPermissoes(List.of(permissaoUsuario));
	}
	
	public void alterarSenha(Usuario usuario, String novaSenha) {
		usuario.setSenha(passwordEncoder.encode(novaSenha));
		
		usuarioRepository.alterarSenha(usuario);
	}
}