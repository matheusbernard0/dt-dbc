package br.com.dbc.pautaapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pauta")
@Getter
@Setter
public class Pauta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    @JsonIgnore
    @OneToOne(mappedBy = "pauta")
    private Sessao sessao;
}
