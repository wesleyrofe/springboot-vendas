package projetospring.service;

import org.springframework.transaction.annotation.Transactional;
import projetospring.domain.entity.Pedido;
import projetospring.domain.enums.StatusPedido;
import projetospring.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    @Transactional
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
