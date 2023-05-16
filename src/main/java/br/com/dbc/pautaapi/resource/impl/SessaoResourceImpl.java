package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.entity.Sessao;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.SessaoRepository;
import br.com.dbc.pautaapi.resource.SessaoResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class SessaoResourceImpl implements SessaoResource {
    private final PautaRepository pautaRepository;
    private final SessaoRepository sessaoRepository;

    @Override
    public Sessao openSession(CriaSessaoRequest criaSessaoRequest, Integer pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("pauta nao encontrada"));

        if (Objects.nonNull(pauta.getSessao()))
            throw new RuntimeException("sessao ja iniciada");

        LocalDateTime inicio = LocalDateTime.now();

        LocalDateTime fim = inicio.plusHours(criaSessaoRequest.getHoras())
                .plusMinutes(criaSessaoRequest.getMinutos())
                .plusSeconds(criaSessaoRequest.getSegundos());

        Sessao sessao = Sessao.builder()
                .inicio(inicio)
                .fim(fim)
                .aberta(Boolean.TRUE)
                .pauta(pauta)
                .build();

        return sessaoRepository.save(sessao);
    }
}
