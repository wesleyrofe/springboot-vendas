package projetospring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projetospring.domain.entity.Produto;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ItemPedidoDTO {

    private Integer produto;
    private Integer quantidade;

}
