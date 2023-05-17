package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.dbc.pautaapi.dto.response.CriaSessaoResponse;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/sessao")
public interface SessaoResource {
    @PatchMapping("/open/{pautaId}")
    CriaSessaoResponse openSession(@RequestBody(required = false) CriaSessaoRequest criaSessaoRequest, @PathVariable Integer pautaId);
}
