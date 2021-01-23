package projetospring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projetospring.domain.entity.Cliente;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query(value = "select c from Cliente c where c.nome like %:nome%")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);


    //@Modifyng  utilizado para fazer alterações no DB, mesmo que Transactional

    boolean existsByNome( String nome);

    @Query(value = "select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);



//    @Autowired
//    private EntityManager entityManager;
//
//    @Transactional
//    public Cliente salvar(Cliente cliente){
//        entityManager.persist(cliente);
//        return cliente;
//    }
//
//    @Transactional
//    public Cliente atualizar(Cliente cliente){
//        entityManager.merge(cliente);
//        return cliente;
//    }
//
//    @Transactional
//    public void deletar(Cliente cliente){
//        if(!entityManager.contains(cliente)){
//            cliente = entityManager.merge(cliente);
//        }
//        entityManager.remove(cliente);
//    }
//
//    @Transactional
//    public void deletar(Integer id){
//        Cliente cliente = entityManager.find(Cliente.class, id);
//        deletar(cliente);
//    }
//
//    @Transactional
//    public List<Cliente> obterTodos(){
//        return entityManager.createQuery("from Cliente", Cliente.class).getResultList();
//    }
//
//    @Transactional( readOnly = true)
//    public List<Cliente> buscarPorNome (String nome){
//        // Parametro JPA - utiliza-se ":" em antes do campo, igual ao campo nome
//        String jpql = "select c from Cliente c where c.nome like :nome";
//        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
//        query.setParameter("nome", "%" + nome + "%");
//        return query.getResultList();
//
//    }

//    private RowMapper<Cliente> obterClienteMapper() {
//        return new RowMapper<Cliente>() {
//            @Override
//            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
//                return new Cliente(resultSet.getInt("id"),
//                        resultSet.getString("nome"));
//            }
//        };
//    }
}
