package com.softplan.desafio.repository;

import com.softplan.desafio.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByClienteId(Long clienteId);
}
