package br.dev.edersonabreu.poc.exception;

public class UsuarioJaExisteException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private static final String MENSAGEM = "Este endereço de e-mail já está sendo utilizado por um usuário";
	
	public UsuarioJaExisteException() {
		super(MENSAGEM);
	}
}