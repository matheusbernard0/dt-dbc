package br.com.dbc.pautaapi.repository;

import br.com.dbc.pautaapi.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    Optional<Sessao> findSessaoByPauta_id(Integer pautaId);
}
