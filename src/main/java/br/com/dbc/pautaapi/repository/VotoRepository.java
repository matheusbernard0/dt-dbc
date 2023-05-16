package br.com.dbc.pautaapi.repository;

import br.com.dbc.pautaapi.entity.Voto;
import br.com.dbc.pautaapi.entity.VotoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, VotoId> {
}
