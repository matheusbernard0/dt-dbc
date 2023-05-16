package br.com.dbc.pautaapi.repositories;

import br.com.dbc.pautaapi.Voto;
import br.com.dbc.pautaapi.VotoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, VotoId> {
}
