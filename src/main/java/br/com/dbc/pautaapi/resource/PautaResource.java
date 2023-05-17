package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.response.CriaPautaResponse;
import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.dbc.pautaapi.entity.Pauta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/pauta")
public interface PautaResource {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Pauta> findAll();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CriaPautaResponse create(@RequestBody CriaPautaRequest criaPautaRequest);
}
