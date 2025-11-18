package unoeste.fipp.bomservico.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unoeste.fipp.bomservico.entities.Anuncio;
import unoeste.fipp.bomservico.entities.Erro;
import unoeste.fipp.bomservico.services.AnuncioService;

import java.util.List;

@RestController
@RequestMapping(value = "apis/anunciante")
public class AnuncianteRestController {
    @Autowired
    private AnuncioService anuncioService;

    @GetMapping(value = "get-by-id/{id}")
    public ResponseEntity<Object> getAnuncioById(@PathVariable Long id){
        Anuncio anuncio = anuncioService.getAnuncioByID(id);
        if(anuncio!=null)
            return ResponseEntity.ok(anuncio);
        else
            return ResponseEntity.badRequest().body("Erro");
    }

    @GetMapping(value = "/get-titulo/{titulo}")
    ResponseEntity<Object> getAnuncioByTitulo(@RequestParam String titulo){
        List<Anuncio> listaAnuncio = anuncioService.getAnuncioByNome(titulo);
        if (listaAnuncio!=null)
            return ResponseEntity.ok(listaAnuncio);
        else
            return ResponseEntity.badRequest().body(new Erro("Erro", "Nenhum anuncio foi encontrado"));
    }
}
