package com.softplan.desafio.controller;

import com.softplan.desafio.model.Cliente;
import com.softplan.desafio.model.Transacao;
import com.softplan.desafio.request.TransacaoRequest;
import com.softplan.desafio.service.ClienteService;
import com.softplan.desafio.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<?> criarTransacao(@PathVariable Long id, @RequestBody TransacaoRequest request) {
        try {
            Cliente cliente = clienteService.obterClientePorId(id);
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
            }

            Transacao transacao = new Transacao();
            transacao.setCliente(cliente);
            transacao.setValor(request.getValor());
            transacao.setTipo(request.getTipo());
            transacao.setDescricao(request.getDescricao());

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

    @GetMapping
    public ResponseEntity<?> obterTodosClientes() {
        try {
            List<Cliente> clientes = clienteService.obterTodosClientes();
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());        }
    }
}