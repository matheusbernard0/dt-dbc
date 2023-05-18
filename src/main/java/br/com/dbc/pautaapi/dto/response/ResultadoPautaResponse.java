package br.com.dbc.pautaapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoPautaResponse {
    @JsonProperty("pauta_id")
    private Integer pautaId;
    private String descricao;
    @JsonProperty("votos_a_favor")
    private long votosAFavor;
    @JsonProperty("votos_contra")
    private long votosContra;
}
