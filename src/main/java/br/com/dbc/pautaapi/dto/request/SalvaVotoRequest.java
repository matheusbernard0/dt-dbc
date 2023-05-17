package br.com.dbc.pautaapi.dto.request;

import br.com.dbc.pautaapi.entity.Opcao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SalvaVotoRequest {
    @JsonProperty("usuario_id")
    private Integer usuarioId;
    @JsonProperty("pauta_id")
    private Integer pautaId;
    private Opcao opcao;
}
