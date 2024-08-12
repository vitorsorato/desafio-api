import React, { useState } from 'react';
import { Button, TextField, Typography, Container, Snackbar, Alert } from '@mui/material';

const Transacoes: React.FC = () => {
  const [clienteId, setClienteId] = useState<number | ''>('');
  const [mensagem, setMensagem] = useState<string>('');
  const [openSnack, setOpenSnack] = useState<boolean>(false);

  const handleTransacao = async () => {
    if (clienteId === '') {
      setMensagem('Por favor, insira um ID de cliente.');
      setOpenSnack(true);
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
          tipo: 'r',
          descricao: 'Transação de exemplo',
          clienteId: clienteId
        }),
      });

      if (response.ok) {
        setMensagem(`Transação criada com sucesso! ID: ${clienteId}`);
        setOpenSnack(true);
        
        const dataAtual = new Date().toISOString();
        localStorage.setItem('clienteId', clienteId.toString());
        localStorage.setItem('transacaoAtualizada', `${clienteId.toString()}-${dataAtual}`);
      } else {
        setMensagem('Erro ao criar transação.');
        setOpenSnack(true);
      }
    } catch (error) {
      setMensagem('Erro ao criar transação.');
      setOpenSnack(true);
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
      <Snackbar
        open={openSnack}
        autoHideDuration={6000}
      >
        <Alert severity={mensagem.startsWith('Erro') ? 'error' : 'success'}>
          {mensagem}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default Transacoes;
