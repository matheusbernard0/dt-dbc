package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.CriaSessaoRequest;
import br.com.dbc.pautaapi.dto.response.CriaSessaoResponse;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.entity.Sessao;
import br.com.dbc.pautaapi.mapper.SessaoMapper;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.SessaoRepository;
import br.com.dbc.pautaapi.resource.SessaoResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class SessaoResourceImpl implements SessaoResource {
    private final PautaRepository pautaRepository;
    private final SessaoRepository sessaoRepository;

    @Override
    public CriaSessaoResponse openSession(CriaSessaoRequest criaSessaoRequest, Integer pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("pauta nao encontrada"));

        if (Objects.nonNull(pauta.getSessao()))
            throw new RuntimeException("sessao ja iniciada");

        Sessao sessao = SessaoMapper.INSTANCE.criaSessaoRequestToSessao(criaSessaoRequest, new Sessao());
        sessao.setPauta(pauta);

        return SessaoMapper.INSTANCE.sessaoToCriaSessaoResponse(sessaoRepository.save(sessao));
    }
}
