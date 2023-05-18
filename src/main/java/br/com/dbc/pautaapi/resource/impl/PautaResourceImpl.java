package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.dbc.pautaapi.dto.response.ResultadoPautaResponse;
import br.com.dbc.pautaapi.entity.Opcao;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.mapper.PautaMapper;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.dto.response.CriaPautaResponse;
import br.com.dbc.pautaapi.resource.PautaResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
public class PautaResourceImpl implements PautaResource {
    private final PautaRepository pautaRepository;

    @Override
    public List<CriaPautaResponse> findAll() {
        return pautaRepository.findAll()
                .stream()
                .map(PautaMapper.INSTANCE::pautaToCriaPautaResponse)
                .collect(toList());
    }

    @Override
    public CriaPautaResponse create(CriaPautaRequest criaPautaRequest) {
        Pauta pauta = PautaMapper.INSTANCE.criaPautaRequestToPauta(criaPautaRequest);
        return PautaMapper.INSTANCE.pautaToCriaPautaResponse(pautaRepository.save(pauta));
    }

    @Override
    public ResultadoPautaResponse getResult(Integer pautaId) {
        Pauta pauta = pautaRepository.getPautaWithVotos(pautaId)
                .orElseThrow(() -> new RuntimeException("pauta nao encontrada"));

        if (isNull(pauta.getSessao()))
            throw new RuntimeException("pauta ainda nao possui uma sessao");

        if (pauta.getSessao().estaAbertaParaVotacao())
            throw new RuntimeException("pauta ainda esta aberta para votacao");

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
        return resultadoPautaResponse;
    }
}
