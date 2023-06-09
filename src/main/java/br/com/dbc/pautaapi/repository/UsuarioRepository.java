package br.com.dbc.pautaapi.repository;

import br.com.dbc.pautaapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findUsuarioByCpf(String cpf);
}
