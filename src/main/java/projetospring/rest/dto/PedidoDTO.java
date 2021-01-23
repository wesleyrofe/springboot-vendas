package projetospring.rest.dto;

//{
//        "cliente": 1,
//        "total": 100.00,
//        "items": [
//        {
//        "produto": 1,
//        "quantidade": 3
//        }
//        ]
//}

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projetospring.domain.entity.ItemPedido;
import projetospring.validation.NotEmptyList;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PedidoDTO {
    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer cliente;
    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;

    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<ItemPedidoDTO> items;


}
