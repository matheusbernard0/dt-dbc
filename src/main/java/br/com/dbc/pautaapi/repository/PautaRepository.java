package br.com.dbc.pautaapi.repository;

import br.com.dbc.pautaapi.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {

    @Override
    @Query("select p from Pauta as p left join fetch p.sessao")
    List<Pauta> findAll();

    @Query("select p from Pauta p left join fetch p.sessao s left join fetch s.votos v where p.id = ?1")
    Optional<Pauta> getPautaWithVotos(Integer pautaId);
}
