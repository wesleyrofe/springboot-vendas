package projetospring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetospring.domain.entity.ItemPedido;


public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {

}
