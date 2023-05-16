package br.com.dbc.pautaapi.repository;

import br.com.dbc.pautaapi.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {

    @Override
    @Query("select p from Pauta as p left join fetch p.sessao")
    List<Pauta> findAll();
}
