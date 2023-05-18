package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.dbc.pautaapi.dto.response.CriaUsuarioResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Usuario", description = "Operacoes relativas a Usuario")
@RequestMapping("/usuario")
public interface UsuarioResource {

    @ApiOperation(value = "Cria um usuario")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuario criado com sucesso"),
            @ApiResponse(code = 400, message = "Usuario ja cadastrado"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CriaUsuarioResponse createUser(@RequestBody CriaUsuarioRequest criaUsuarioRequest);

    @ApiOperation(value = "Busca todos os usuarios")
    @ApiResponses({
            @ApiResponse(code = 200, message = "usuarios retornados com sucesso")
    })
    @GetMapping
    List<CriaUsuarioResponse> findAll();
}
