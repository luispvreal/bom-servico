package unoeste.fipp.bomservico.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.bomservico.entities.Erro;
import unoeste.fipp.bomservico.entities.Login;
import unoeste.fipp.bomservico.entities.Usuario;
import unoeste.fipp.bomservico.security.JWTTokenProvider;
import unoeste.fipp.bomservico.services.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login){
        Usuario usuario = usuarioService.getByLogin(login.getLogin());

        if(usuario != null && usuario.getSenha().equals(login.getSenha())){
            String token = JWTTokenProvider.getToken(usuario.getLogin(), usuario.getNivel());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao autenticar usuário",""));
    }
}
