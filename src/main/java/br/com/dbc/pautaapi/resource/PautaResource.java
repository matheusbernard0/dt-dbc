package br.com.dbc.pautaapi.resource;

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
    Pauta create(@RequestBody Pauta pauta);
}
