package br.com.dbc.pautaapi.repository;

import br.com.dbc.pautaapi.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> { }
