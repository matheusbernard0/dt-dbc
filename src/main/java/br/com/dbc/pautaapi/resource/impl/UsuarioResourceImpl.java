package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.dbc.pautaapi.entity.Usuario;
import br.com.dbc.pautaapi.repository.UsuarioRepository;
import br.com.dbc.pautaapi.resource.UsuarioResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UsuarioResourceImpl implements UsuarioResource {
    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario createUser(CriaUsuarioRequest criaUsuarioRequest) {
        usuarioRepository.findUsuarioByCpf(criaUsuarioRequest.getCpf())
                .ifPresent(usuario -> {throw new RuntimeException("usuario ja cadastrado");});

        return usuarioRepository.save(Usuario.builder()
                .cpf(criaUsuarioRequest.getCpf())
                .nome(criaUsuarioRequest.getNome())
                .build());
    }
}
