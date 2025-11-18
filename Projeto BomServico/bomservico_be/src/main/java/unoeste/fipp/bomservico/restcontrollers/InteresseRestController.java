package unoeste.fipp.bomservico.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.bomservico.entities.Erro;
import unoeste.fipp.bomservico.entities.Interesse;
import unoeste.fipp.bomservico.services.InteresseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "apis/interesse")
public class InteresseRestController {
    @Autowired
    private InteresseService interesseService;

    @PostMapping
    ResponseEntity<Object> inserirInteresse(@RequestBody Interesse interesse){
        if(interesse != null){
            try{
                Interesse novo = interesseService.saveInteresse(interesse);
                return ResponseEntity.ok(novo);
            }
            catch(Exception e){
                return ResponseEntity.badRequest().body(new Erro("Erro: ", e.getMessage()));
            }
        }
        return ResponseEntity.badRequest().body(new Erro("Erro", "Não foi possível enviar a mensagem"));
    }

    @GetMapping(value = "/get-all")
    ResponseEntity<Object> listaInteresse(){
        List<Interesse> listaInteresses = interesseService.findAllInteresse();
        if(listaInteresses != null)
            return ResponseEntity.ok(listaInteresses);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro", "Não há nenhum interesse cadastrado"));
    }
}
