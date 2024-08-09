import React, { useEffect, useState } from 'react';
import { Container, Typography } from '@mui/material';

const Extrato: React.FC = () => {
  const [extrato, setExtrato] = useState<any[]>([]);
  const [mensagem, setMensagem] = useState('');

  useEffect(() => {
    const clienteId = localStorage.getItem('clienteId');
    if (clienteId) {
      fetch(`http://localhost:9999/clientes/${clienteId}/extrato`)
        .then((response) => response.json())
        .then((data) => {
          setExtrato(data);
          setMensagem('');
        })
        .catch(() => {
          setMensagem('Erro ao obter extrato.');
        });
    } else {
      setMensagem('Nenhum ID de cliente encontrado.');
    }
  }, []);

  return (
    <Container>
      <Typography variant="h6" gutterBottom>
        Tela de Extrato
      </Typography>
      {mensagem && <Typography variant="body1" color="error" margin="normal">{mensagem}</Typography>}
      <ul>
        {extrato.map((item) => (
          <li key={item.id}>
            {item.descricao}: {item.valor}
          </li>
        ))}
      </ul>
    </Container>
  );
};

export default Extrato;
