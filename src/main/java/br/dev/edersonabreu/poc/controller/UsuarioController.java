package br.dev.edersonabreu.poc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.dev.edersonabreu.poc.domain.Usuario;
import br.dev.edersonabreu.poc.domain.http.CadastroUsuarioRequest;
import br.dev.edersonabreu.poc.exception.UsuarioJaExisteException;
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
		Usuario usuario = new Usuario();
		usuario.setEmail(cadastroUsuarioRequest.getEmail());
		usuario.setNome(cadastroUsuarioRequest.getNome());
		usuario.setSenha(cadastroUsuarioRequest.getSenha());
		
		try {
			usuarioService.cadastrar(usuario);
			mView.setViewName("redirect:/");
		} catch(UsuarioJaExisteException exception) {
			cadastroUsuarioRequest.setEmail(null);
			cadastroUsuarioRequest.setSenha(null);
			mView.addObject("erro", exception.getMessage());
			mView.addObject("request", cadastroUsuarioRequest);
			mView.setViewName("usuario-form");
		}
		
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
                               ModelAndView mView,
                               RedirectAttributes redirectAttributes) {
        if(!novaSenha.equals(confirmarSenha)) {
        	redirectAttributes.addFlashAttribute("erro", "A senhas devem ser iguais");
        	mView.setViewName("redirect:/usuario/perfil");
            return mView;
        }
        
        usuarioService.alterarSenha(usuario, novaSenha);
        
        redirectAttributes.addFlashAttribute("sucesso", "Senha alterada com sucesso");
        mView.setViewName("redirect:/usuario/perfil");
        return mView;
    }
}