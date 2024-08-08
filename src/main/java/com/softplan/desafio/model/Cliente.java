package com.softplan.desafio.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer limite;

    @NotNull
    @Column(nullable = false)
    private Integer saldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Integer getLimite() {
        return limite;
    }

    public void setLimite(@NotNull Integer limite) {
        this.limite = limite;
    }

    public @NotNull Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(@NotNull Integer saldo) {
        this.saldo = saldo;
    }
}
