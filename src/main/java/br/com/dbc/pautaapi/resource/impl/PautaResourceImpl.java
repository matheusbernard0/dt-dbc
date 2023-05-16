package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.entity.Pauta;
import br.com.dbc.pautaapi.repository.PautaRepository;
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
    public Pauta create(Pauta pauta) {
        return pautaRepository.save(pauta);
    }
}
