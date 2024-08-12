package com.softplan.desafio.service;

import com.softplan.desafio.model.Cliente;
import com.softplan.desafio.model.Transacao;
import com.softplan.desafio.repository.ClienteRepository;
import com.softplan.desafio.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransacaoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public Transacao criarTransacao(Long id, Transacao transacao) throws Exception {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new Exception("Cliente não encontrado"));

        if (transacao.getTipo().equals("d") && cliente.getSaldo() - transacao.getValor() < -cliente.getLimite()) {
            throw new Exception("Saldo insuficiente");
        }

        cliente.setSaldo(cliente.getSaldo() + (transacao.getTipo().equals("r") ? transacao.getValor() : -transacao.getValor()));
        clienteRepository.save(cliente);

        return transacaoRepository.save(transacao);
    }

    public Map<String, Object> obterExtrato(Long id) throws Exception {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new Exception("Cliente não encontrado"));

        Map<String, Object> extrato = new HashMap<>();
        extrato.put("saldo", cliente.getSaldo());
        extrato.put("limite", cliente.getLimite());
        extrato.put("data_extrato", LocalDateTime.now().toString());
        List<Transacao> transacoes = transacaoRepository.findByClienteId(id);
        extrato.put("ultimas_transacoes", transacoes);

        return extrato;
    }
}