import { useNavigate } from 'react-router-dom';
import '../App.css';
import { useState, useEffect } from 'react';
import api from '../services/api';

function MainPage() {
  const navigate = useNavigate();
  const [alerts, setAlerts] = useState([]);
  const [symbol, setSymbol] = useState('');
  const [price, setPrice] = useState('');
  const [type, setType] = useState('ABOVE'); // Matches your <option> values

  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    if (!user || !user.id) {
      navigate('/login');
    } else {
      loadAlerts();
    }
  }, []);

  const loadAlerts = async () => {
    if (user?.id) {
      const data = await api.fetchActiveAlerts(user.id);
      if (data) setAlerts(data);
    }
  };

  const handleCreateAlert = async (e) => {
    e.preventDefault();
    if (!symbol || !price) return alert('Preencha todos os campos');

    const payload = {
      userId: user.id,
      ticker: symbol.toUpperCase(),
      conditionType: type,
      targetValue: parseFloat(price),
      notification_channel: 'EMAIL',
    };

    const result = await api.postAlert(payload);
    if (result) {
      setSymbol('');
      setPrice('');
      loadAlerts();
      alert('Alerta criado com sucesso!');
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Deseja excluir este alerta?')) {
      const success = await api.deleteAlert(id, user.id);
      if (success) {
        setAlerts(alerts.filter((a) => a.id !== id));
      }
    }
  };

  return (
    <div className="dashboard-container">
      <header className="header-main">
        <div className="logo">
          Invest+ <span>Alerts</span>
        </div>
        <div className="user-profile">Olá, {user?.username || 'Usuário'}</div>
      </header>

      <main className="main-layout">
        <section className="col-left">
          <div className="card-layout">
            <h3 className="title">Configurar Novo Alerta</h3>
            <form className="form-alerta" onSubmit={handleCreateAlert}>
              <input
                className="input-field"
                value={symbol}
                onChange={(e) => setSymbol(e.target.value)}
                placeholder="Ex: BTCUSDT"
              />
              <select className="input-field" value={type} onChange={(e) => setType(e.target.value)}>
                <option value="ABOVE">Acima de</option>
                <option value="BELOW">Abaixo de</option>
              </select>
              <input
                className="input-field"
                type="number"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                placeholder="Preço Alvo"
              />
              <button className="btn-primary" type="submit">
                Criar Alerta
              </button>
            </form>
          </div>

          <div className="card-layout" style={{ flex: 1 }}>
            <p className="subtitle">Gráfico de {symbol || 'Ativo'} (Em breve)</p>
          </div>
        </section>

        <section className="col-right">
          <div className="card-layout">
            <h3 className="title">Alertas Ativos</h3>
            <div className="alert-list">
              {alerts.length > 0 ? (
                alerts.map((alert) => (
                  <div key={alert.id} className="alert-item">
                    <span>
                      <strong>{alert.ticker}</strong>: {alert.conditionType === 'ABOVE' ? '>' : '<'} {alert.targetValue}
                    </span>
                    <button className="delete-btn" onClick={() => handleDelete(alert.id)}>
                      Remover
                    </button>
                  </div>
                ))
              ) : (
                <p>Nenhum alerta ativo.</p>
              )}
            </div>
          </div>

          <div className="card-layout" style={{ flex: 1 }}>
            <h3 className="title">Monitoramento Real-Time</h3>
            {/* Implementar WebSocket aqui futuramente */}
          </div>
        </section>
      </main>
    </div>
  );
}

export default MainPage;
