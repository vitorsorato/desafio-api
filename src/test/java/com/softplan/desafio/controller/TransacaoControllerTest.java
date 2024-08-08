package com.softplan.desafio.controller;

import com.softplan.desafio.model.Cliente;
import com.softplan.desafio.model.Transacao;
import com.softplan.desafio.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class TransacaoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TransacaoController transacaoController;

    @Mock
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
    }

    @Test
    void testCriarTransacao_Success() throws Exception {
        Transacao transacao = new Transacao();
        transacao.setId(1L);
        transacao.setTipo("d");
        transacao.setValor(100);
        transacao.setDescricao("Compra");

        when(transacaoService.criarTransacao(anyLong(), any(Transacao.class))).thenReturn(transacao);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                        .contentType("application/json")
                        .content("{\"tipo\": \"d\", \"valor\": 100, \"descricao\": \"Compra\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value("d"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Compra"));

        verify(transacaoService, times(1)).criarTransacao(anyLong(), any(Transacao.class));
    }

    @Test
    void testCriarTransacao_Failure() throws Exception {
        when(transacaoService.criarTransacao(anyLong(), any(Transacao.class)))
                .thenThrow(new RuntimeException("Erro de criação"));

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes/1/transacoes")
                        .contentType("application/json")
                        .content("{\"tipo\": \"d\", \"valor\": 100, \"descricao\": \"Compra\"}"))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Erro de criação"));
    }

    @Test
    void testObterExtrato_Success() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setSaldo(1000);
        cliente.setLimite(500);

        when(transacaoService.obterExtrato(anyLong())).thenReturn(Map.of(
                "saldo", 1000,
                "limite", 500,
                "data_extrato", "2024-08-08T00:00:00",
                "ultimas_transacoes", List.of()
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/1/extrato"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldo").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limite").value(500));

        verify(transacaoService, times(1)).obterExtrato(anyLong());
    }

    @Test
    void testObterExtrato_Failure() throws Exception {
        when(transacaoService.obterExtrato(anyLong())).thenThrow(new RuntimeException("Cliente não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/1/extrato"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Cliente não encontrado"));
    }
}
