import React, { useEffect, useState } from 'react';
import { Container, Typography, List, ListItem, ListItemText, Divider } from '@mui/material';

interface Transacao {
  id: number;
  cliente: {
    id: number;
    nome: string;
    limite: number;
    saldo: number;
  };
  valor: number;
  tipo: string;
  descricao: string;
}

interface ExtratoResponse {
  data_extrato: string;
  saldo: number;
  limite: number;
  ultimas_transacoes: Transacao[];
}

const Extrato: React.FC = () => {
  const [extrato, setExtrato] = useState<ExtratoResponse | null>(null);
  const [mensagem, setMensagem] = useState<string>('');
  const [clienteId, setClienteId] = useState<string | null>(null);

  useEffect(() => {
    // Função para buscar o extrato
    const fetchExtrato = () => {
      if (clienteId) {
        fetch(`http://localhost:9999/clientes/${clienteId}/extrato`)
          .then((response) => response.json())
          .then((data: ExtratoResponse) => {
            setExtrato(data);
            setMensagem('');
          })
          .catch(() => {
            setMensagem('Erro ao obter extrato.');
          });
      }
    };

    const clienteIdStorage = localStorage.getItem('clienteId');
    if (clienteIdStorage) {
      setClienteId(clienteIdStorage);
      fetchExtrato();
    } else {
      setMensagem('Nenhum ID de cliente encontrado.');
    }

    const handleStorageChange = (event: StorageEvent) => {
      if (event.key === 'transacaoAtualizada' && event.newValue && event.newValue.startsWith(clienteId || '')) {
        fetchExtrato();
      }

      if (event.key === 'clienteId') {
        const clienteIdStorage = localStorage.getItem('clienteId');
        if (clienteIdStorage) {
          setClienteId(clienteIdStorage);
        } else {
          setMensagem('Nenhum ID de cliente encontrado.');
        }
      }
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, [clienteId]);

  return (
    <Container>
      <Typography variant="h6" gutterBottom>
        Tela de Extrato
      </Typography>
      {mensagem && <Typography variant="body1" color="error" margin="normal">{mensagem}</Typography>}
      {extrato && (
        <>
          <Typography variant="body1" gutterBottom>
            <strong>ID do Cliente:</strong> {clienteId}
          </Typography>
          <Typography variant="body1" gutterBottom>
            <strong>Saldo:</strong> R$ {extrato.saldo}
          </Typography>
          <Typography variant="body1" gutterBottom>
            <strong>Limite:</strong> R$ {extrato.limite}
          </Typography>
          <Typography variant="body1" gutterBottom>
            <strong>Data do Extrato:</strong> {new Date(extrato.data_extrato).toLocaleString()}
          </Typography>
          <List>
            {Array.isArray(extrato.ultimas_transacoes) && extrato.ultimas_transacoes.length > 0 ? (
              extrato.ultimas_transacoes.map((transacao) => (
                <div key={transacao.id}>
                  <ListItem>
                    <ListItemText
                      primary={transacao.descricao}
                      secondary={`Valor: R$ ${transacao.valor} - Tipo: ${transacao.tipo}`}
                    />
                  </ListItem>
                  <Divider />
                </div>
              ))
            ) : (
              <Typography variant="body1" color="textSecondary">Nenhuma transação encontrada.</Typography>
            )}
          </List>
        </>
      )}
    </Container>
  );
};

export default Extrato;
