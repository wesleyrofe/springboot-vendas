package projetospring.rest.controller;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetospring.domain.entity.ItemPedido;
import projetospring.domain.entity.Pedido;
import projetospring.domain.enums.StatusPedido;
import projetospring.rest.dto.AtualizacaoStatusDTO;
import projetospring.rest.dto.InformacaoItemPedidoDTO;
import projetospring.rest.dto.InformacaoPedidoDTO;
import projetospring.rest.dto.PedidoDTO;
import projetospring.service.PedidoService;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();

    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody  AtualizacaoStatusDTO dto){

        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));


    }

    @GetMapping("{id}")
    public InformacaoPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map( p -> converter(p))
                .orElseThrow( () -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    private InformacaoPedidoDTO converter(Pedido pedido){
        return InformacaoPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){

        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        System.out.println("Cheguei no item pedido");
        System.out.println(itens);
        return itens
                .stream()
                .map(
                        item -> InformacaoItemPedidoDTO
                        .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnit(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
                ).collect(Collectors.toList());
    }
}
