package br.com.dbc.pautaapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CriaUsuarioResponse {
    private Integer id;
    private String nome;
    private String cpf;
    @JsonProperty("data_de_criacao")
    private LocalDateTime dataDeCriacao;
}
