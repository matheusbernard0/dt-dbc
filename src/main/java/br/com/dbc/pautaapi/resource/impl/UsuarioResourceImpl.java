package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.dbc.pautaapi.dto.response.CriaUsuarioResponse;
import br.com.dbc.pautaapi.entity.Usuario;
import br.com.dbc.pautaapi.exception.UsuarioJaCadastradoException;
import br.com.dbc.pautaapi.mapper.UsuarioMapper;
import br.com.dbc.pautaapi.repository.UsuarioRepository;
import br.com.dbc.pautaapi.resource.UsuarioResource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@Slf4j
public class UsuarioResourceImpl implements UsuarioResource {
    private final UsuarioRepository usuarioRepository;

    @Override
    public CriaUsuarioResponse createUser(CriaUsuarioRequest criaUsuarioRequest) {
        log.info("iniciando UsuarioResource.createUser");
        usuarioRepository.findUsuarioByCpf(criaUsuarioRequest.getCpf())
                .ifPresent(usuario -> {throw new UsuarioJaCadastradoException("usuario ja cadastrado");});

        Usuario usuario = usuarioRepository.save(UsuarioMapper.INSTANCE.criaUsuarioRequestToUsuario(criaUsuarioRequest));
        log.info("finalizando UsuarioResource.createUser");
        return UsuarioMapper.INSTANCE.usuarioToCriaUsuarioResponse(usuario);
    }

    @Override
    public List<CriaUsuarioResponse> findAll() {
        log.info("iniciando UsuarioResource.findAll");
        List<CriaUsuarioResponse> list = usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper.INSTANCE::usuarioToCriaUsuarioResponse)
                .collect(toList());
        log.info("finalizando UsuarioResource.findAll");
        return list;
    }
}
