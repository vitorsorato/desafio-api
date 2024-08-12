package com.softplan.desafio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Cliente cliente;

    @NotNull
    private Integer valor;

    @NotNull
    private String tipo;

    @NotNull
    private String descricao;

    @Column(name = "realizada_em", nullable = false, updatable = false)
    private LocalDateTime realizadaEm = LocalDateTime.now();

    // Construtor sem argumentos
    public Transacao() {
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Cliente getCliente() {
        return cliente;
    }

    public void setCliente(@NotNull Cliente cliente) {
        this.cliente = cliente;
    }

    public @NotNull Integer getValor() {
        return valor;
    }

    public void setValor(@NotNull Integer valor) {
        this.valor = valor;
    }

    public @NotNull String getTipo() {
        return tipo;
    }

    public void setTipo(@NotNull String tipo) {
        this.tipo = tipo;
    }

    public @NotNull String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NotNull String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getRealizadaEm() {
        return realizadaEm;
    }

    public void setRealizadaEm(LocalDateTime realizadaEm) {
        this.realizadaEm = realizadaEm;
    }
}
