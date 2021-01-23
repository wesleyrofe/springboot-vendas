package projetospring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetospring.domain.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
