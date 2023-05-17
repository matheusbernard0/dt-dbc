package br.com.dbc.pautaapi.mapper;

import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.dto.response.CriaPautaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PautaMapper {
    PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);

    Pauta criaPautaRequestToPauta(CriaPautaRequest criaPautaRequest);
    CriaPautaResponse pautaToCriaPautaResponse(Pauta pauta);
}
