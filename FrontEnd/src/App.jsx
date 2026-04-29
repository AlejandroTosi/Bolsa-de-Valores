import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useState } from 'react';
import Login from './pages/Login';
import MainPage from './pages/MainPage';

function App() {
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem('user');
    try {
      return saved ? JSON.parse(saved) : null;
    } catch (e) {
      return null;
    }
  });

  return (
    <Router>
      <Routes>
        {/* Login */}
        <Route path="/login" element={<Login onLogin={setUser} />} />

        {/* MainPage */}
        <Route path="/MainPage" element={user ? <MainPage /> : <Navigate to="/login" replace />} />

        {/* Redirect if not authenticated */}
        <Route path="*" element={<Navigate to={user ? '/MainPage' : '/login'} replace />} />
      </Routes>
    </Router>
  );
}

export default App;
