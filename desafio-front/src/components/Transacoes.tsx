import React, { useState } from 'react';
import { Button, TextField, Typography, Container } from '@mui/material';

const Transacoes: React.FC = () => {
  const [clienteId, setClienteId] = useState<number | ''>('');
  const [mensagem, setMensagem] = useState('');

  const handleTransacao = async () => {
    if (clienteId === '') {
      setMensagem('Por favor, insira um ID de cliente.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:9999/clientes/${clienteId}/transacoes`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          valor: Math.floor(Math.random() * 1000), // Valor aleatório
          tipo: 'r', // Tipo exemplo
          descricao: 'Transação de exemplo',
          clienteId: clienteId
        }),
      });

      if (response.ok) {
        const data = await response.json();
        setMensagem(`Transação criada com sucesso! ID: ${clienteId}`);
        // Envia uma mensagem para a aba /extrato
        localStorage.setItem('clienteId', clienteId.toString());
        window.open('/extrato', '_blank');
      } else {
        setMensagem('Erro ao criar transação.');
      }
    } catch (error) {
      setMensagem('Erro ao criar transação.');
    }
  };

  return (
    <Container>
      <Typography variant="h6" gutterBottom>
        Tela de Transações
      </Typography>
      <TextField
        label="ID do Cliente"
        variant="outlined"
        type="number"
        value={clienteId}
        onChange={(e) => setClienteId(Number(e.target.value))}
        fullWidth
        margin="normal"
      />
      <Button variant="contained" color="primary" onClick={handleTransacao}>
        Criar Transação
      </Button>
      {mensagem && <Typography variant="body1" color="error" margin="normal">{mensagem}</Typography>}
    </Container>
  );
};

export default Transacoes;
