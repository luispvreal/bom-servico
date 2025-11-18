package unoeste.fipp.bomservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unoeste.fipp.bomservico.entities.Interesse;

import java.util.List;

@Repository
public interface InteresseRespository extends JpaRepository<Interesse, Long> {

    @Query(value = "SELECT * FROM interesse", nativeQuery = true)
    public List<Interesse> findAllInteresse();
}
