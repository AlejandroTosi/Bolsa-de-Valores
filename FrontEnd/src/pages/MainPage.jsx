import { useNavigate } from 'react-router-dom';
import '../App.css';
import { useState, useEffect } from 'react';
import api from '../services/api';

function MainPage() {
  const navigate = useNavigate();
  const [alerts, setAlerts] = useState([]);
  const [symbol, setSymbol] = useState('');
  const [price, setPrice] = useState('');
  const [type, setType] = useState('ABOVE');

  // 1. Token validation
  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
    } else {
      loadAlerts();
    }
  }, [navigate]);

  // 2. Load alert list
  const loadAlerts = async () => {
    const data = await api.fetchActiveAlerts();
    if (data) setAlerts(data);
  };

  // 3. New alert
  const handleCreateAlert = async (e) => {
    e.preventDefault();

    const payload = {
      symbol: symbol.toUpperCase(),
      targetPrice: parseFloat(price),
      alertType: type, // ABOVE ou BELOW
    };

    const result = await api.postAlert(payload);
    if (result) {
      setSymbol('');
      setPrice('');
      loadAlerts(); //Refresh
      alert('Alerta criado com sucesso!');
    }
  };

  // 4. Deletar Alerta
  const handleDelete = async (id) => {
    const success = await api.deleteAlert(id);
    if (success) {
      setAlerts(alerts.filter((a) => a.id !== id));
    }
  };

  return (
    <div className="dashboard-container">
      <header className="header-main">
        <div className="logo">
          Invest+ <span>Alerts</span>
        </div>
        <div className="user-profile">TODO: Validador de back-end online</div>
      </header>

      <main className="main-layout">
        {/* LADO ESQUERDO */}
        <section className="col-left">
          <div className="card-layout">
            <h3 className="title">TODO: Implementar formulário de alerta</h3>
            <form className="form-alerta" onSubmit={handleCreateAlert}>
              <input value={symbol} onChange={(e) => setSymbol(e.target.value)} placeholder="Pesquisar ativo" />{' '}
              <select className="input-field" value={type} onChange={(e) => setType(e.target.value)}>
                <option value="ABOVE">Acima de</option>
                <option value="BELOW">Abaixo de</option>
              </select>
              <input
                className="input-field"
                type="number"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                placeholder="Preço"
              />
              <button className="btn-primary">Criar Alerta</button>
            </form>
          </div>

          <div className="card-layout" style={{ flex: 1 }}>
            <p className="subtitle">TODO: resultado da pesquisa e grafico especifico</p>
          </div>
        </section>

        {/* LADO DIREITO */}
        <section className="col-right">
          <div className="card-layout">
            <h3 className="title">Alertas Ativos</h3>
            <div className="alert-list">
              <div className="alert-item">
                <span>
                  <strong>TODO: inserir lista de alertas</strong>
                </span>
                <button className="delete-btn">Remover</button>
              </div>
            </div>
          </div>

          <div className="card-layout" style={{ flex: 1 }}>
            <h3 className="title">Monitoramento Real-Time</h3>
            {/* TODO: implementar gráfico de monitoramento em tempo real */}
          </div>
        </section>
      </main>
    </div>
  );
}

export default MainPage;
