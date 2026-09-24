package med.voll.web_application.controller;

import jakarta.validation.Valid;
import med.voll.web_application.domain.RegraDeNegocioException;
import med.voll.web_application.domain.usuario.DadosAlteracaoSenha;
import med.voll.web_application.domain.usuario.Usuario;
import med.voll.web_application.domain.usuario.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    public static final String LOGIN = "autenticacao/login";
    public static final String LOGOUT = "autenticacao/logout";
    public static final String FORMULARIO_ALTERACAO_SENHA = "autenticacao/formulario-alteracao-senha";

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String carregaPaginaLogin() {
        return LOGIN;
    }

    @GetMapping("/alterar-senha")
    public String carregaPaginaAlteracaoSenha() {
        return FORMULARIO_ALTERACAO_SENHA;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(@Valid @ModelAttribute("dados") DadosAlteracaoSenha dados, BindingResult result, Model model, @AuthenticationPrincipal Usuario logado) {
        if (result.hasErrors()) {
            model.addAttribute("dados", dados);
            return FORMULARIO_ALTERACAO_SENHA;
        }

        try {
            usuarioService.alterarSenha(dados, logado);
            return "redirect:home";
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", dados);
            return FORMULARIO_ALTERACAO_SENHA;
        }
    }

    @GetMapping("/logout")
    public String carregaPaginaLogout(){
        return LOGOUT;
    }
}
