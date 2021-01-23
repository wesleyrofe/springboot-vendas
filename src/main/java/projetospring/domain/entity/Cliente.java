package projetospring.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table ( name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {

    @Id //toda Classe marcada como Entity deve ter um Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column //Quando o atributo do DB e da classe possui nome diferente, deve-se a tribuir o name referenciando o atributo do DB, fora isso, não é necessário)
    private Integer id;

    @Column(length = 100)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(length = 11)
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos;

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
