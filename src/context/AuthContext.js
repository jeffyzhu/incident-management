// frontend/src/context/AuthContext.js

import React, { createContext, useState, useEffect } from 'react';
import { login as loginService } from '../api/authService';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [role, setRole] = useState(null);

    useEffect(() => {
        const storedToken = localStorage.getItem('authToken');
        const storedRole = localStorage.getItem('role');
        if (storedToken && storedRole) {
            setIsAuthenticated(true);
            setRole(storedRole);
        }
    }, []);

    const login = async (username, password) => {
        try {
            const { token, role } = await loginService(username, password);
            localStorage.setItem('authToken', token);
            localStorage.setItem('role', role);
            setRole(role);
            setIsAuthenticated(true);
        } catch (error) {
            console.error('Login failed:', error);
            throw error;
        }
    };

    const logout = () => {
        localStorage.removeItem('authToken');
        localStorage.removeItem('role');
        setIsAuthenticated(false);
        setRole(null);
    };

    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout, role }}>
            {children}
        </AuthContext.Provider>
    );
};

