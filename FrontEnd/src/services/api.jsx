const BASE_URL = 'http://localhost:8080/api';
const USERS_BASE = `${BASE_URL}/users`;
const ALERTS_BASE = `${BASE_URL}/alerts`;

const getToken = () => {
  return localStorage.getItem('token');
};

const request = async (url, options = {}) => {
  const token = getToken();
  try {
    const response = await fetch(url, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
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
  login: (payload) => request(`${USERS_BASE}/login`, { method: 'POST', body: JSON.stringify(payload) }),

  signup: (payload) => request(`${USERS_BASE}/register`, { method: 'POST', body: JSON.stringify(payload) }),

  updateUser: (userId, payload) => request(`${USERS_BASE}/${userId}`, { method: 'PUT', body: JSON.stringify(payload) }),

  updateUserPassword: (userId, payload) =>
    request(`${USERS_BASE}/${userId}/password`, { method: 'PUT', body: JSON.stringify(payload) }),

  updateUserDiscord: (userId, payload) =>
    request(`${USERS_BASE}/${userId}/discord`, { method: 'PUT', body: JSON.stringify(payload) }),

  fetchActiveAlerts: (userId) => request(`${ALERTS_BASE}/active?userId=${userId}`),

  fetchAllAlerts: (userId) => request(`${ALERTS_BASE}/all?userId=${userId}`),

  fetchAlertById: (id) => request(`${ALERTS_BASE}/${id}`),

  postAlert: (payload) => request(ALERTS_BASE, { method: 'POST', body: JSON.stringify(payload) }),

  updateAlert: (id, payload) => request(`${ALERTS_BASE}/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),

  toggleAlert: (id, userId, active) =>
    request(`${ALERTS_BASE}/${id}/toggle`, {
      method: 'PATCH',
      body: JSON.stringify({ userId, active }),
    }),

  deleteAlert: (id, userId) => request(`${ALERTS_BASE}/${id}?userId=${userId}`, { method: 'DELETE' }),
};

export default api;
