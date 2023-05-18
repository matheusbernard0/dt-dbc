package br.com.dbc.pautaapi.resource.impl;

import br.com.dbc.pautaapi.dto.request.SalvaVotoRequest;
import br.com.dbc.pautaapi.dto.response.SalvaVotoResponse;
import br.com.dbc.pautaapi.entity.*;
import br.com.dbc.pautaapi.exception.*;
import br.com.dbc.pautaapi.mapper.VotoMapper;
import br.com.dbc.pautaapi.repository.PautaRepository;
import br.com.dbc.pautaapi.repository.UsuarioRepository;
import br.com.dbc.pautaapi.repository.VotoRepository;
import br.com.dbc.pautaapi.resource.VotoResource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@AllArgsConstructor
@Slf4j
public class VotoResourceImpl implements VotoResource {
    private final PautaRepository pautaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VotoRepository votoRepository;

    @Override
    public SalvaVotoResponse vote(SalvaVotoRequest salvaVotoRequest) {
        log.info("iniciando VotoResource.vote");
        Pauta pauta = pautaRepository.findByPautaId(salvaVotoRequest.getPautaId())
                .orElseThrow(() -> new PautaNaoEncontradaException("Pauta nao encontrada"));

        Sessao sessao = pauta.getSessao();
        if (Objects.isNull(sessao))
            throw new SessaoNaoExistenteException("Nao existe sessao aberta para a pauta");

        if (!sessao.estaAbertaParaVotacao())
            throw new SessaoEncerradaException("A sessao de votacao esta encerrada");

        Usuario usuario = usuarioRepository.findById(salvaVotoRequest.getUsuarioId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado"));

        votoRepository.findById(new VotoId(sessao.getId(), usuario.getId()))
                .ifPresent(voto -> {
                    throw new VotoJaRealizadoException("Voto ja realizado para usuario e pauta informada");
                });

        Voto voto = VotoMapper.INSTANCE.salvaVotoRequestToVoto(salvaVotoRequest);
        voto.setUsuario(usuario);
        voto.setSessao(sessao);

        Voto save = votoRepository.save(voto);
        log.info("finalizando VotoResource.vote");
        return VotoMapper.INSTANCE.votoToSalvaVotoResponse(save, new SalvaVotoResponse());
    }
}
