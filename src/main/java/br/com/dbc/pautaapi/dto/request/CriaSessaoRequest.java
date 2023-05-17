package br.com.dbc.pautaapi.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class CriaSessaoRequest {
    @Min(value = 0, message = "horas deve ser um numero natural")
    private int horas;
    @Min(value = 0, message = "minutos deve ser um numero natural")
    private int minutos;
    @Min(value = 0, message = "segundos deve ser um numero natural")
    private int segundos;
}
