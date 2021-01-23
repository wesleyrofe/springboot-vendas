package projetospring.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projetospring.domain.entity.Usuario;
import projetospring.exception.SenhaInvalidaException;
import projetospring.rest.dto.CredenciaisDTO;
import projetospring.rest.dto.TokenDTO;
import projetospring.security.jwt.JwtService;
import projetospring.service.impl.UsuarioServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return service.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{

            Usuario usuario =  Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha())
                    .build();
            UserDetails usuarioAutenticado = service.autenticar(usuario);

            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getLogin(), token);

        }catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
