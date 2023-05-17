package br.com.dbc.pautaapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String cpf;

    @Column(name = "data_de_criacao")
    private LocalDateTime dataDeCriacao;

    @OneToMany(mappedBy = "usuario")
    private List<Voto> votos = new ArrayList<>();
}
