package unoeste.fipp.bomservico.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.bomservico.entities.Erro;
import unoeste.fipp.bomservico.entities.Usuario;
import unoeste.fipp.bomservico.services.UsuarioService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "apis/user")
public class UsuarioRestController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "test")
    ResponseEntity<Object> test(){
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "get-all")
    ResponseEntity<Object> getAll(@PathVariable int nivel, @PathVariable String login){
        List<Usuario> usuarioList = usuarioService.getAll("");
        if(nivel == 1)
            return ResponseEntity.ok(usuarioList);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro: ", "Usuário sem permissão de acesso"));
    }

    @GetMapping(value="get-by-keyword/{keyword}")
    ResponseEntity<Object> getByKeyword(@PathVariable String keyword){
        List <Usuario> usuarioList;
        usuarioList=usuarioService.getAll(keyword);
        //usuario=usuarioRepository.findById(id).get();
        return ResponseEntity.ok(usuarioList);
    }

    @GetMapping(value="get-by-level/{level}")
    ResponseEntity<Object> getByLevel(@PathVariable int level){
        return ResponseEntity.ok(usuarioService.getByLevel(level));
    }

    @GetMapping(value="get-by-id/{id}")
    ResponseEntity<Object> getById(@PathVariable Long id){
        Usuario usuario=usuarioService.getById(id);
        if(usuario==null)
            return ResponseEntity.badRequest().body(new Erro("Usuario não encontrado",""));
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    ResponseEntity<Object> addUser(@RequestBody Usuario usuario){
        if(usuario!=null){
            try {
                Usuario novo = usuarioService.saveUser(usuario);
                return ResponseEntity.ok(novo);
            }
            catch (Exception e){
                return ResponseEntity.badRequest().body(new Erro("Erro ao inserir novo usuario",e.getMessage()));
            }
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao inserir novo usuario","usuario inconsistente"));
    }

    @PutMapping
    ResponseEntity<Object> updateUser(@RequestBody Usuario usuario){
        if(usuario!=null && !usuario.getLogin().isEmpty()){
            try {
                Usuario novo = usuarioService.saveUser(usuario);
                return ResponseEntity.ok(novo);
            }
            catch (Exception e){
                return ResponseEntity.badRequest().body(new Erro("Erro ao alterar usuario",e.getMessage()));
            }
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar usuario","usuario inconsistente"));
    }

    @DeleteMapping(value="/{id}")
    ResponseEntity<Object> deleteUser(@PathVariable Long id)
    {
        if(usuarioService.deleteUser(id))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.badRequest().body(new Erro("Usuario não existe",""));
    }
}
