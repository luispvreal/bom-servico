package unoeste.fipp.bomservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unoeste.fipp.bomservico.entities.Anuncio;

import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio,Long> {

    @Query(value = "SELECT * FROM anuncio WHERE anu_titulo LIKE %:filtro%", nativeQuery = true)
    List<Anuncio> buscarPorTitulo(@Param("filtro") String filtro);
}
