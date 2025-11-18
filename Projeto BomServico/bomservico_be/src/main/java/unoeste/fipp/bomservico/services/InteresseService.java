package unoeste.fipp.bomservico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unoeste.fipp.bomservico.entities.Interesse;
import unoeste.fipp.bomservico.repositories.InteresseRespository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InteresseService {
    @Autowired
    private InteresseRespository interesseRespository;

    public Interesse saveInteresse(Interesse interesse){
        Interesse inter = interesseRespository.save(interesse);
        return inter;
    }

    public Interesse getInteresseById(Long id){
        Interesse inter = interesseRespository.findById(id).orElse(null);
        return inter;
    }

    public List<Interesse> findAllInteresse(){
        List<Interesse> listaInteresses = interesseRespository.findAll();
        return listaInteresses;
    }

    public boolean deleteInteresse(Long id){
        Interesse inter = interesseRespository.findById(id).orElse(null);
        if(inter != null){
            interesseRespository.deleteById(id);
            return true;
        }
        return false;
    }
}
