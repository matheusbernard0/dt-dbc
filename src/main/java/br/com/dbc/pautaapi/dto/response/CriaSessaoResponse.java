package br.com.dbc.pautaapi.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CriaSessaoResponse {
    private Integer id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Boolean aberta;
}
