import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import api from '../services/api';

const Login = ({ onLogin }) => {
  const navigate = useNavigate();
  const [view, setView] = useState('login');

  const changeToRegister = () => {
    setView('register');
  };

  const changeToLogin = () => {
    setView('login');
  };

  const SendLogin = async (e) => {
    e.preventDefault();
    const payload = {
      email: e.target[0].value,
      password: e.target[1].value,
    };
    await api.login(payload);

    if (payload) {
      localStorage.setItem('token', JSON.stringify(payload));
      localStorage.setItem('user', JSON.stringify(payload));
      onLogin(payload);
      navigate('/MainPage');
    }
  };

  const SendRegister = async (e) => {
    e.preventDefault();
    const payload = {
      username: e.target[0].value,
      email: e.target[1].value,
      password: e.target[2].value,
    };
    await api.signup(payload);
  };

  return (
    <div className="login-container" style={{ justifyContent: 'center' }}>
      <div className="card-layout">
        <div className="logo text-center">
          <button className="btn-primary" onClick={changeToLogin}>
            Login
          </button>
          <button className="btn-primary" onClick={changeToRegister}>
            Register
          </button>
        </div>

        {view === 'login' ? (
          <form className="form-login" onSubmit={SendLogin}>
            <input className="input-field" type="email" placeholder="Email" required />
            <input className="input-field" type="password" placeholder="Password" required />
            <button className="btn-primary" type="submit">
              Login
            </button>
          </form>
        ) : (
          <form className="form-register" onSubmit={SendRegister}>
            <input className="input-field" type="username" placeholder="Name" required />
            <input className="input-field" type="email" placeholder="Email" required />
            <input className="input-field" type="password" placeholder="Password" required />
            <button className="btn-primary" type="submit">
              Sign Up
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default Login;
