package projetospring.security.jwt;

import com.sun.corba.se.spi.ior.ObjectKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import projetospring.VendasApplication;
import projetospring.domain.entity.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario){
        long expString = Long.valueOf(expiracao);
        LocalDateTime datahoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instante = datahoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instante);



        return Jwts.builder()
                    .setSubject(usuario.getLogin())
                    .setExpiration(data)
                    .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                    .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
        try{
            Claims claims = obterClaims(token);
            Date dataexpiracao = claims.getExpiration();
            LocalDateTime data = dataexpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);

        }catch(Exception ex){
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
        JwtService service = contexto.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("fulano").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);

        boolean isTokenValido = service.tokenValido(token);
        System.out.println("O token está válido : "+ isTokenValido);
        System.out.println(service.obterLoginUsuario(token));

    }
}
