package br.com.dbc.pautaapi.resource;

import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.dbc.pautaapi.entity.Usuario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/usuario")
public interface UsuarioResource {
    @PostMapping
    Usuario createUser(@RequestBody CriaUsuarioRequest criaUsuarioRequest);
}
