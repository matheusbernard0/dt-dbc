package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.dbc.pautaapi.dto.response.CriaSessaoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Sessao", description = "Operacoes relativas a Sessao")
@RequestMapping("/sessao")
public interface SessaoResource {

    @ApiOperation(value = "Cria a sessao para uma pauta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessao iniciada com sucesso"),
            @ApiResponse(code = 404, message = "Pauta nao encontrada"),
            @ApiResponse(code = 400, message = "Sessao ja iniciada para a pauta informada"),
    })
    @PatchMapping("/inicia/{pautaId}")
    @ResponseStatus(HttpStatus.OK)
    CriaSessaoResponse openSession(@RequestBody(required = false) CriaSessaoRequest criaSessaoRequest, @PathVariable Integer pautaId);
}
