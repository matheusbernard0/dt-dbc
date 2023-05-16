package br.com.dbc.pautaapi.dto.request;

import br.com.dbc.pautaapi.entity.Opcao;
import lombok.Data;

@Data
public class VotaRequest {
    private Integer usuarioId;
    private Integer pautaId;
    private Opcao opcao;
}
