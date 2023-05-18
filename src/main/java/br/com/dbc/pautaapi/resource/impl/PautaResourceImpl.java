package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.dbc.pautaapi.dto.response.ResultadoPautaResponse;
import br.com.dbc.pautaapi.entity.Opcao;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.exception.PautaAindaAbertaParaVotacaoException;
import br.com.dbc.pautaapi.exception.PautaAindaSemSessaoException;
import br.com.dbc.pautaapi.exception.PautaNaoEncontradaException;
import br.com.dbc.pautaapi.mapper.PautaMapper;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.dto.response.CriaPautaResponse;
import br.com.dbc.pautaapi.resource.PautaResource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@Slf4j
public class PautaResourceImpl implements PautaResource {
    private final PautaRepository pautaRepository;

    @Override
    public List<CriaPautaResponse> findAll() {
        log.info("iniciando PautaResource.findAll");
        List<CriaPautaResponse> collect = pautaRepository.findAll()
                .stream()
                .map(PautaMapper.INSTANCE::pautaToCriaPautaResponse)
                .collect(toList());
        log.info("finalizando PautaResource.findAll");
        return collect;
    }

    @Override
    public CriaPautaResponse create(CriaPautaRequest criaPautaRequest) {
        log.info("iniciando PautaResource.create");
        Pauta pauta = PautaMapper.INSTANCE.criaPautaRequestToPauta(criaPautaRequest);
        Pauta save = pautaRepository.save(pauta);
        log.info("finalizando PautaResource.create");
        return PautaMapper.INSTANCE.pautaToCriaPautaResponse(save);
    }

    @Override
    public ResultadoPautaResponse getResult(Integer pautaId) {
        log.info("iniciando PautaResource.getResult");
        Pauta pauta = pautaRepository.getPautaWithVotos(pautaId)
                .orElseThrow(() -> new PautaNaoEncontradaException("pauta nao encontrada"));

        if (isNull(pauta.getSessao()))
            throw new PautaAindaSemSessaoException("pauta ainda nao possui uma sessao");

        if (pauta.getSessao().estaAbertaParaVotacao())
            throw new PautaAindaAbertaParaVotacaoException("pauta ainda esta aberta para votacao");

        ResultadoPautaResponse resultadoPautaResponse = new ResultadoPautaResponse();
        resultadoPautaResponse.setPautaId(pauta.getId());
        resultadoPautaResponse.setDescricao(pauta.getDescricao());
        resultadoPautaResponse.setVotosAFavor(pauta.getSessao().getVotos()
                .stream()
                .filter(voto -> voto.getOpcao().equals(Opcao.SIM))
                .count()
        );
        resultadoPautaResponse.setVotosContra(pauta.getSessao().getVotos()
                .stream()
                .filter(voto -> voto.getOpcao().equals(Opcao.NAO))
                .count()
        );
        log.info("finalizando PautaResource.getResult");
        return resultadoPautaResponse;
    }
}
