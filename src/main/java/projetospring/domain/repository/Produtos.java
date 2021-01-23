package projetospring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetospring.domain.entity.Produto;


public interface Produtos extends JpaRepository<Produto, Integer> {

}
