package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.response.CriaPautaResponse;
import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.dbc.pautaapi.dto.response.ResultadoPautaResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Pauta", description = "Operacoes relativas a Pauta")
@RequestMapping("/pauta")
public interface PautaResource {

    @ApiOperation(value = "Busca todas as pautas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pautas retornadas com sucesso"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CriaPautaResponse> findAll();

    @ApiOperation(value = "Cria uma pauta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pauta criada com sucesso"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CriaPautaResponse create(@RequestBody CriaPautaRequest criaPautaRequest);

    @GetMapping("/{pautaId}")
    @ResponseStatus(HttpStatus.OK)
    ResultadoPautaResponse getResult(@PathVariable("pautaId") Integer pautaId);
}
