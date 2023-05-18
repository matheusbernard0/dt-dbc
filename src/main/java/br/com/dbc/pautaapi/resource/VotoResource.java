package br.com.dbc.pautaapi.resource;


import br.com.dbc.pautaapi.dto.request.SalvaVotoRequest;
import br.com.dbc.pautaapi.dto.response.SalvaVotoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Voto", description = "Operacoes relativas a Voto")
@RequestMapping("/voto")
public interface VotoResource {


    @ApiOperation(value = "Realiza o voto de um usuario em uma pauta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Voto realizado com sucesso"),
            @ApiResponse(code = 404, message = "Pauta informada nao encontrada | Usuario informado nao encontrado"),
            @ApiResponse(code = 400, message = "Nao existe sessao para a pauta informada | Sessao da pauta informada ja esta encerrada | Voto ja realizado para usuario e pauta informada"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    SalvaVotoResponse vote(@RequestBody SalvaVotoRequest salvaVotoRequest);
}
