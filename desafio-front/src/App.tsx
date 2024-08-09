import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Outlet } from 'react-router-dom';
import Transacoes from './components/Transacoes';
import Extrato from './components/Extrato';
import { CssBaseline, Container, Button, Box } from '@mui/material';

const App: React.FC = () => {
  return (
    <Router>
      <CssBaseline />
      <Container>
        <Box my={2}>
          <Button variant="contained" component={Link} to="/transacoes" sx={{ mr: 2 }}>
            Transações
          </Button>
          <Button variant="contained" component={Link} to="/extrato">
            Extrato
          </Button>
        </Box>
        <Routes>
          <Route path="/transacoes" element={<Transacoes />} />
          <Route path="/extrato" element={<Extrato />} />
        </Routes>
      </Container>
    </Router>
  );
};

export default App;
