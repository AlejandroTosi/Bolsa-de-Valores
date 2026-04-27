import ButtonBase from '../components/ButtonBase';
import Layout from '../components/Layout';
import '../App.css';
import { useState, useEffect } from 'react';

function MainPage({ user }) {
  //useEffect(() => {
  //const loggedUser = localStorage.getItem('@App:user');

  //if(!loggedUser){
  // Navigate('/login');{
  // TODO: implementar redirecionamento para login caso usuário não esteja logado
  //}
  //}

  const [alerts, setAlerts] = useState([]);

  const uservalidation = () => {
    if (user === null) {
      return <p>TODO: Implementar validação de usuário</p>;
    }
  };
  const fetchAlerts = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/alerts');
      const data = await response.json();
      setAlerts(data);
    } catch (error) {
      console.error('Erro ao buscar alertas:', error);
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
            <form className="form-alerta">
              <input className="input-field" style={{ gridColumn: 'span 2' }} placeholder="Ativo (PETR4)" />
              <select className="input-field">
                <option>Acima de</option>
                <option>Abaixo de</option>
              </select>
              <input className="input-field" type="number" placeholder="Preço" />
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
