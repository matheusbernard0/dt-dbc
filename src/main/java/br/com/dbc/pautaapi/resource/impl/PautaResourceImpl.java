package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.CriaPautaRequest;
import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.mapper.PautaMapper;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.dto.response.CriaPautaResponse;
import br.com.dbc.pautaapi.resource.PautaResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PautaResourceImpl implements PautaResource {
    private final PautaRepository pautaRepository;

    @Override
    public List<Pauta> findAll() {
        return pautaRepository.findAll();
    }

    @Override
    public CriaPautaResponse create(CriaPautaRequest criaPautaRequest) {
        Pauta pauta = PautaMapper.INSTANCE.criaPautaRequestToPauta(criaPautaRequest);
        return PautaMapper.INSTANCE.pautaToCriaPautaResponse(pautaRepository.save(pauta));
    }
}
