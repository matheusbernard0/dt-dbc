package br.com.dbc.pautaapi.resource;


import br.com.dbc.pautaapi.dto.request.VotaRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/voto")
public interface VotoResource {

    @PostMapping
    void salvaVoto(@RequestBody VotaRequest votaRequest);
}
