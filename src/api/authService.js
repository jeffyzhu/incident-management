// frontend/src/api/authService.js

const API_URL = process.env.REACT_APP_AUTH_URL ||'http://localhost:8080/auth';

export const login = async (username, password) => {
    const token = btoa(`${username}:${password}`);
    const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${token}`
        }
    });
    if (!response.ok) {
        throw new Error('Invalid credentials');
    }
    const data = await response.json();
    return { token, role: data.role };
};