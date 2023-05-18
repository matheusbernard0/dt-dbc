package br.com.dbc.pautaapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sessao")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Boolean aberta = Boolean.FALSE;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    @OneToMany(mappedBy = "sessao", fetch = FetchType.LAZY)
    private List<Voto> votos = new ArrayList<>();

    public boolean estaAbertaParaVotacao() {
        return aberta && LocalDateTime.now().isBefore(fim);
    }
}
