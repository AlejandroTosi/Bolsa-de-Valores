import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import api from '../services/api';

function Login({ onLogin }) {
  const navigate = useNavigate();
  const [view, setView] = useState('login');

  const SendLogin = async (e) => {
    e.preventDefault();

    const email = e.target.querySelector('input[type="email"]').value;
    const password = e.target.querySelector('input[type="password"]').value;

    const apiResponse = await api.login({ email, password });

    if (apiResponse) {
      localStorage.setItem('token', apiResponse.access_token);
      localStorage.setItem('user', JSON.stringify(apiResponse.user));

      if (onLogin) onLogin(apiResponse.user);

      navigate('/MainPage', { replace: true });
    }
  };

  const SendRegister = async (e) => {
    e.preventDefault();
    const payload = {
      username: e.target[0].value,
      email: e.target[1].value,
      password: e.target[2].value,
    };

    const success = await api.signup(payload);
    if (success) {
      alert('Cadastro realizado!');
      setView('login');
    }
  };

  return (
    <div className="login-container" style={{ display: 'flex', justifyContent: 'center', marginTop: '100px' }}>
      <div className="card-layout" style={{ width: '350px', padding: '20px' }}>
        <div style={{ marginBottom: '20px', textAlign: 'center' }}>
          <button className="btn-primary" onClick={() => setView('login')} style={{ marginRight: '10px' }}>
            Login
          </button>
          <button className="btn-primary" onClick={() => setView('register')}>
            Cadastro
          </button>
        </div>

        {view === 'login' ? (
          <form className="form-login" onSubmit={SendLogin}>
            <input
              className="input-field"
              type="email"
              placeholder="Email"
              required
              style={{ width: '100%', marginBottom: '10px' }}
            />
            <input
              className="input-field"
              type="password"
              placeholder="Password"
              required
              style={{ width: '100%', marginBottom: '10px' }}
            />
            <button className="btn-primary" type="submit" style={{ width: '100%' }}>
              Login
            </button>
          </form>
        ) : (
          <form className="form-register" onSubmit={SendRegister}>
            <input
              className="input-field"
              type="text"
              placeholder="Name"
              required
              style={{ width: '100%', marginBottom: '10px' }}
            />
            <input
              className="input-field"
              type="email"
              placeholder="Email"
              required
              style={{ width: '100%', marginBottom: '10px' }}
            />
            <input
              className="input-field"
              type="password"
              placeholder="Password"
              required
              style={{ width: '100%', marginBottom: '10px' }}
            />
            <button className="btn-primary" type="submit" style={{ width: '100%' }}>
              Sign Up
            </button>
          </form>
        )}
      </div>
    </div>
  );
}

export default Login;
