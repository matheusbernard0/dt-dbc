package br.com.dbc.pautaapi.mapper;

import br.com.dbc.pautaapi.dto.request.CriaUsuarioRequest;
import br.com.dbc.pautaapi.dto.response.CriaUsuarioResponse;
import br.com.dbc.pautaapi.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "dataDeCriacao", expression = "java(java.time.LocalDateTime.now())")
    Usuario criaUsuarioRequestToUsuario(CriaUsuarioRequest criaUsuarioRequest);
    CriaUsuarioResponse usuarioToCriaUsuarioResponse(Usuario usuario);

}
