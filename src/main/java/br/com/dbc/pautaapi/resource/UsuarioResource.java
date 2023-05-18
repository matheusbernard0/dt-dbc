package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.dbc.pautaapi.dto.response.CriaUsuarioResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/usuario")
public interface UsuarioResource {
    @PostMapping
    CriaUsuarioResponse createUser(@RequestBody CriaUsuarioRequest criaUsuarioRequest);
}
