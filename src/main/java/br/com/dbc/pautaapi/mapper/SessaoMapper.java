package br.com.dbc.pautaapi.mapper;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.dbc.pautaapi.dto.response.CriaSessaoResponse;
import br.com.dbc.pautaapi.entity.Sessao;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper
public interface SessaoMapper {
    SessaoMapper INSTANCE = Mappers.getMapper(SessaoMapper.class);
    @Mapping(target = "inicio", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "aberta", expression = "java(Boolean.TRUE)")
    Sessao criaSessaoRequestToSessao(CriaSessaoRequest criaSessaoRequest, @MappingTarget Sessao sessao);

    CriaSessaoResponse sessaoToCriaSessaoResponse(Sessao sessao);

    @AfterMapping
    default void setFim(CriaSessaoRequest criaSessaoRequest, @MappingTarget Sessao sessao) {
        LocalDateTime fim = sessao.getInicio().plusHours(criaSessaoRequest.getHoras())
                .plusMinutes(criaSessaoRequest.getMinutos())
                .plusSeconds(criaSessaoRequest.getSegundos());
        sessao.setFim(fim);
    }
}
