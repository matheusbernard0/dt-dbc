package br.com.dbc.pautaapi.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CriaPautaRequest {
    @NotBlank(message = "descricao precisa ser informada")
    private String descricao;
}
