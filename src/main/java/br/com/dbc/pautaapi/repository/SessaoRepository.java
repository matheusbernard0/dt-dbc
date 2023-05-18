package br.com.dbc.pautaapi.repository;

import br.com.dbc.pautaapi.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    Optional<Sessao> findSessaoByPauta_id(Integer pautaId);

    @Query("select s from Sessao s inner join fetch s.votos where s.pauta.id = ?1")
    Optional<Sessao> findByPautaId(Integer pautaId);
}
