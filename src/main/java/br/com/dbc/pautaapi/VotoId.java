package br.com.dbc.pautaapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VotoId implements Serializable {
        @Column(name = "sessao_id")
        private Integer sessaoId;
        @Column(name = "usuario_id")
        private Integer usuarioId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotoId votoId = (VotoId) o;
        return sessaoId.equals(votoId.sessaoId) && usuarioId.equals(votoId.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessaoId, usuarioId);
    }
}
