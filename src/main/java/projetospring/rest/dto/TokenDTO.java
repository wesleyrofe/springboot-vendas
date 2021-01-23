package projetospring.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {

    private String token;
    private String login;

    public TokenDTO(String token, String login) {
        this.token = token;
        this.login = login;
    }
}
