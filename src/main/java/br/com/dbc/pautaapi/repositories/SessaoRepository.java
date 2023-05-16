package br.com.dbc.pautaapi.repositories;

import br.com.dbc.pautaapi.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    Optional<Sessao> findSessaoByPauta_id(Integer pautaId);
}
