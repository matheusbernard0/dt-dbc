package br.com.dbc.pautaapi.dto.request;

import lombok.Data;

@Data
public class CriaUsuarioRequest {
    private String nome;
    private String cpf;
}
