package br.com.dbc.pautaapi;

import lombok.Data;

@Data
public class CriaUsuarioRequest {
    private String nome;
    private String cpf;
}
