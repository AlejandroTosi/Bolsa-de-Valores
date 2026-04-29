import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useState } from 'react';
import Login from './pages/Login';
import MainPage from './pages/MainPage';

function App() {
  {
    /*const [user, setUser] = useState({ userId: 1, name: 'Dev Teste' });*/
  }

  const [user, setUser] = useState(null);

  return (
    <Router>
      <Routes>
        {/* Login */}
        /*
        <Route path="/login" element={<Login onLogin={setUser} />} />
        */
        {/* MainPage */}
        <Route path="/MainPage" element={user ? <MainPage user={user} /> : <Navigate to="/login" />} />
        {/* Redirect if not authenticated */}
        /*
        <Route path="*" element={<Navigate to={user ? '/MainPage' : '/login'} />} />
        */
      </Routes>
    </Router>
  );
}

export default App;
