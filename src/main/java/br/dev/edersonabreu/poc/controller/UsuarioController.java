package br.dev.edersonabreu.poc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.dev.edersonabreu.poc.domain.Usuario;
import br.dev.edersonabreu.poc.domain.http.CadastroUsuarioRequest;
import br.dev.edersonabreu.poc.service.UsuarioService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(value = "/usuario")
@AllArgsConstructor
public class UsuarioController {
	
	private UsuarioService usuarioService;
	
	@GetMapping("/form")
	public ModelAndView formCadastroUsuario(ModelAndView mView) {
		mView.setViewName("usuario-form");
		mView.addObject("request", new CadastroUsuarioRequest());
		
		return mView;
	}
	
	@PostMapping("/cadastrar")
	public ModelAndView cadastarUsuario(@ModelAttribute CadastroUsuarioRequest cadastroUsuarioRequest, ModelAndView mView) {
		mView.setViewName("redirect:/");
		Usuario usuario = new Usuario();
		usuario.setEmail(cadastroUsuarioRequest.getEmail());
		usuario.setNome(cadastroUsuarioRequest.getNome());
		usuario.setSenha(cadastroUsuarioRequest.getSenha());
		
		usuarioService.cadastrar(usuario);
		
		return mView;
	}
	
	@GetMapping("/perfil")
    public ModelAndView perfil(@AuthenticationPrincipal Usuario usuario, ModelAndView mView) {
		mView.setViewName("perfil");
        mView.addObject("usuario", usuario);
        
        return mView;
    }
	
	@PostMapping("/alterar-senha")
    public ModelAndView alterarSenha(@RequestParam String novaSenha,
                               @RequestParam String confirmarSenha,
                               @AuthenticationPrincipal Usuario usuario,
                               ModelAndView mView) {
        if(!novaSenha.equals(confirmarSenha)) {
        	mView.setViewName("redirect:/usuario/perfil?erroSenha");
            return mView;
        }
        
        usuarioService.alterarSenha(usuario, novaSenha);
        
        mView.setViewName("redirect:/usuario/perfil?senhaAlterada");
        return mView;
    }
}