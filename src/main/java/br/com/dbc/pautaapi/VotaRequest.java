package br.com.dbc.pautaapi;

import lombok.Data;

@Data
public class VotaRequest {
    private Integer usuarioId;
    private Integer pautaId;
    private Opcao opcao;
}
