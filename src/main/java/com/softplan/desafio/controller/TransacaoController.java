package com.softplan.desafio.controller;

import com.softplan.desafio.model.Transacao;
import com.softplan.desafio.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<?> criarTransacao(@PathVariable Long id, @RequestBody Transacao transacao) {
        try {
            Transacao novaTransacao = transacaoService.criarTransacao(id, transacao);
            return ResponseEntity.ok(novaTransacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<?> obterExtrato(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(transacaoService.obterExtrato(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}