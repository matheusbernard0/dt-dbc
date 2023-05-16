package br.com.dbc.pautaapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "voto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Voto {

    @EmbeddedId
    private VotoId id = new VotoId();

    @Enumerated(EnumType.STRING)
    private Opcao opcao;

    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    @MapsId("sessaoId")
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}