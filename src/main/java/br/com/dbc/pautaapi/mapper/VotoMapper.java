package br.com.dbc.pautaapi.mapper;


import br.com.dbc.pautaapi.dto.request.SalvaVotoRequest;
import br.com.dbc.pautaapi.dto.response.SalvaVotoResponse;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.entity.Voto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VotoMapper {
    VotoMapper INSTANCE = Mappers.getMapper(VotoMapper.class);

    Voto salvaVotoRequestToVoto(SalvaVotoRequest salvaVotoRequest);
    SalvaVotoResponse votoToSalvaVotoResponse(Voto voto, @MappingTarget SalvaVotoResponse salvaVotoResponse);
    @AfterMapping
    default void atualizaVotoResponse(Voto voto, @MappingTarget SalvaVotoResponse salvaVotoResponse) {
        salvaVotoResponse.setPautaId(voto.getSessao().getPauta().getId());
        salvaVotoResponse.setUserId(voto.getId().getUsuarioId());
    }
}
