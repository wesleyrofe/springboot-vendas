package projetospring.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projetospring.domain.entity.Cliente;
import projetospring.domain.entity.ItemPedido;
import projetospring.domain.entity.Pedido;
import projetospring.domain.entity.Produto;
import projetospring.domain.enums.StatusPedido;
import projetospring.domain.repository.Clientes;
import projetospring.domain.repository.ItemsPedido;
import projetospring.domain.repository.Pedidos;
import projetospring.domain.repository.Produtos;
import projetospring.exception.PedidoNaoEncontradoException;
import projetospring.exception.RegraNegocioException;
import projetospring.rest.dto.ItemPedidoDTO;
import projetospring.rest.dto.PedidoDTO;
import projetospring.service.PedidoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Data
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;
    
    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {

        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
         if (items.isEmpty()){
             throw new RegraNegocioException("Não é possivel realizar um pedido sem items");
         }

         return items
                 .stream()
                 .map( dto -> {
                     Integer idProduto = dto.getProduto();
                     Produto produto = produtosRepository
                             .findById(idProduto)
                             .orElseThrow(
                                     () -> new RegraNegocioException("Código de produto inválido: " + idProduto)
                             );
                     ItemPedido itemPedido = new ItemPedido();
                     itemPedido.setQuantidade(dto.getQuantidade());
                     itemPedido.setPedido(pedido);
                     itemPedido.setProduto(produto);
                     return itemPedido;
                 })
                 .collect(Collectors.toList());

    }


}
