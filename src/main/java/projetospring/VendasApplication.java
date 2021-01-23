package projetospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import projetospring.domain.entity.Cliente;
import projetospring.domain.entity.Pedido;
import projetospring.domain.repository.Clientes;
import projetospring.domain.repository.Pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {

     public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class,args);
    }
}
