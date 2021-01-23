package projetospring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projetospring.domain.entity.Usuario;
import projetospring.domain.repository.UsuarioRepository;
import projetospring.exception.SenhaInvalidaException;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }

//
    public UserDetails autenticar(Usuario usuario){
        UserDetails user= loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = passwordEncoder.matches(usuario.getSenha(), user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        System.out.println(repository.findByLogin(username));
        String[] roles = usuario.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
