const BASE_URL = 'http://localhost:8080/api';

const USERS_BASE = `${BASE_URL}/users`;
const ALERTS_BASE = `${BASE_URL}/alerts`;

const getToken = () => {
  const token = localStorage.getItem('token');
  return token ? JSON.parse(token) : null;
};

const endpoints = {
  users: {
    base: USERS_BASE,
    login: `${USERS_BASE}/login`,
    signup: `${USERS_BASE}/register`,
    byId: (id) => `${USERS_BASE}/${id}`,
    password: (id) => `${USERS_BASE}/${id}/password`,
    discord: (id) => `${USERS_BASE}/${id}/discord`,
  },
  alerts: {
    base: ALERTS_BASE,
    active: `${ALERTS_BASE}/active`,
    byId: (id) => `${ALERTS_BASE}/${id}`,
    toggle: (id) => `${ALERTS_BASE}/${id}/toggle`,
  },
};

const request = async (url, options = {}) => {
  try {
    const response = await fetch(url, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${getToken()}`,
        ...options.headers,
      },
    });

    if (response.status === 204) return true;
    if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

    return await response.json();
  } catch (error) {
    console.error(`API Error at ${url}:`, error);
    return null;
  }
};

const api = {
  // --- USERS ---
  login: (payload) => request(endpoints.users.login, { method: 'POST', body: JSON.stringify(payload) }),

  signup: (payload) => request(endpoints.users.signup, { method: 'POST', body: JSON.stringify(payload) }),
  updateUser: (id, payload) => request(endpoints.users.byId(id), { method: 'PUT', body: JSON.stringify(payload) }),

  updateUserPassword: (id, payload) =>
    request(endpoints.users.password(id), { method: 'PUT', body: JSON.stringify(payload) }),

  updateUserDiscord: (id, payload) =>
    request(endpoints.users.discord(id), { method: 'PUT', body: JSON.stringify(payload) }),

  // --- ALERTS ---
  fetchActiveAlerts: () => request(endpoints.alerts.active),

  fetchAlertById: (id) => request(endpoints.alerts.byId(id)),

  postAlert: (payload) => request(endpoints.alerts.base, { method: 'POST', body: JSON.stringify(payload) }),

  updateAlert: (id, payload) => request(endpoints.alerts.byId(id), { method: 'PUT', body: JSON.stringify(payload) }),

  toggleAlert: (id) => request(endpoints.alerts.toggle(id), { method: 'PUT' }),

  deleteAlert: (id) => request(endpoints.alerts.byId(id), { method: 'DELETE' }),
};

export default api;
