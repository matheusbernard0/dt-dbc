package br.com.dbc.pautaapi.mapper;

import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.dbc.pautaapi.dto.response.ResultadoPautaResponse;
import br.com.dbc.pautaapi.entity.Opcao;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.dto.response.CriaPautaResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PautaMapper {
    PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);

    Pauta criaPautaRequestToPauta(CriaPautaRequest criaPautaRequest);
    CriaPautaResponse pautaToCriaPautaResponse(Pauta pauta);

    @Mapping(target = "pautaId", source = "id")
    ResultadoPautaResponse pautoToResultadoPautaResponse(Pauta pauta, @MappingTarget ResultadoPautaResponse resultado);

    @AfterMapping
    default ResultadoPautaResponse completaDadosDaVotacao(Pauta pauta, @MappingTarget ResultadoPautaResponse resultado) {
        resultado.setVotosAFavor(pauta.getSessao().getVotos()
                .stream()
                .filter(voto -> voto.getOpcao().equals(Opcao.SIM))
                .count()
        );
        resultado.setVotosContra(pauta.getSessao().getVotos()
                .stream()
                .filter(voto -> voto.getOpcao().equals(Opcao.NAO))
                .count()
        );
        return resultado;
    }
}
