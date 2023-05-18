package br.com.dbc.pautaapi.resource.v1.impl;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.dbc.pautaapi.dto.response.CriaSessaoResponse;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.entity.Sessao;
import br.com.dbc.pautaapi.exception.PautaNaoEncontradaException;
import br.com.dbc.pautaapi.exception.SessaoJaIniciadaException;
import br.com.dbc.pautaapi.mapper.SessaoMapper;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.SessaoRepository;
import br.com.dbc.pautaapi.resource.v1.SessaoResource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
@AllArgsConstructor
@Slf4j
public class SessaoResourceImpl implements SessaoResource {
    private final PautaRepository pautaRepository;
    private final SessaoRepository sessaoRepository;

    @Override
    public CriaSessaoResponse openSession(CriaSessaoRequest criaSessaoRequest, Integer pautaId) {
        if (Objects.isNull(criaSessaoRequest))
            criaSessaoRequest = CriaSessaoRequest.builder()
                    .minutos(1)
                    .build();

        log.info("iniciando SessaoResource.openSession");
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new PautaNaoEncontradaException("pauta nao encontrada"));

        if (Objects.nonNull(pauta.getSessao()))
            throw new SessaoJaIniciadaException("sessao ja iniciada");

        Sessao sessao = SessaoMapper.INSTANCE.criaSessaoRequestToSessao(criaSessaoRequest, new Sessao());
        sessao.setPauta(pauta);

        Sessao save = sessaoRepository.save(sessao);
        log.info("finalizando SessaoResource.openSession");
        return SessaoMapper.INSTANCE.sessaoToCriaSessaoResponse(save);
    }
}
