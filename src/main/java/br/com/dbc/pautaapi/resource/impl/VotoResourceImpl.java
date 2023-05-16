package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.VotaRequest;
import br.com.dbc.pautaapi.entity.*;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.UsuarioRepository;
import br.com.dbc.pautaapi.repository.VotoRepository;
import br.com.dbc.pautaapi.resource.VotoResource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@AllArgsConstructor
public class VotoResourceImpl implements VotoResource {
    private final PautaRepository pautaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VotoRepository votoRepository;

    @Override
    public void salvaVoto(VotaRequest votaRequest) {
        Pauta pauta = pautaRepository.findById(votaRequest.getPautaId())
                .orElseThrow(() -> new RuntimeException("Pauta nao encontrada"));

        Sessao sessao = pauta.getSessao();
        if (Objects.isNull(sessao))
            throw new RuntimeException("Nao existe sessao aberta para a pauta");

        if (!sessao.estaAbertaParaVotacao())
            throw new RuntimeException("A sessao de votacao esta encerrada");

        Usuario usuario = usuarioRepository.findById(votaRequest.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        votoRepository.findById(new VotoId(sessao.getId(), usuario.getId()))
                .ifPresent(voto -> {
                    throw new RuntimeException("Voto ja realizado para usuario e pauta informada");
                });

        Voto voto = new Voto();
        voto.setUsuario(usuario);
        voto.setSessao(sessao);
        voto.setOpcao(votaRequest.getOpcao());
        votoRepository.save(voto);
    }
}
