package unoeste.fipp.bomservico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unoeste.fipp.bomservico.entities.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    List<Usuario> findAllByNivel(int nivel);

    Optional<Usuario> findByLogin(String login);

    @Query(value="SELECT * FROM users WHERE us_name LIKE %:keyword%",nativeQuery=true)
    List<Usuario> findAllByKeyword(String keyword);
}
