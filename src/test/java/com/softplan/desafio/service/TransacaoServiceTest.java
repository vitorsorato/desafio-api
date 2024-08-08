package com.softplan.desafio.service;

import com.softplan.desafio.model.Cliente;
import com.softplan.desafio.model.Transacao;
import com.softplan.desafio.repository.ClienteRepository;
import com.softplan.desafio.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarTransacao_Success() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setSaldo(1000);
        cliente.setLimite(500);

        Transacao transacao = new Transacao();
        transacao.setTipo("d");
        transacao.setValor(100);
        transacao.setDescricao("Compra");
        transacao.setCliente(cliente);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        Transacao result = transacaoService.criarTransacao(1L, transacao);

        assertEquals(transacao, result);
        assertEquals(900, cliente.getSaldo());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testCriarTransacao_Failure_SaldoInsuficiente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setSaldo(100);
        cliente.setLimite(500);

        Transacao transacao = new Transacao();
        transacao.setTipo("d");
        transacao.setValor(1000);
        transacao.setDescricao("Compra");
        transacao.setCliente(cliente);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Exception exception = assertThrows(Exception.class, () -> {
            transacaoService.criarTransacao(1L, transacao);
        });

        assertEquals("Saldo insuficiente", exception.getMessage());
    }


    @Test
    void testObterExtrato_Success() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setSaldo(1000);
        cliente.setLimite(500);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(transacaoRepository.findAll()).thenReturn(List.of());

        Map<String, Object> extrato = transacaoService.obterExtrato(1L);

        assertEquals(1000, extrato.get("saldo"));
        assertEquals(500, extrato.get("limite"));
    }

    @Test
    void testObterExtrato_Failure() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            transacaoService.obterExtrato(1L);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }
}
