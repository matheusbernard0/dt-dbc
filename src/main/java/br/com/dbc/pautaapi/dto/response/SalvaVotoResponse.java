package br.com.dbc.pautaapi.dto.response;

import br.com.dbc.pautaapi.entity.Opcao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SalvaVotoResponse {
    @JsonProperty("pauta_id")
    private Integer pautaId;
    @JsonProperty("usuario_id")
    private Integer userId;
    private Opcao opcao;
    private LocalDateTime data;
}
