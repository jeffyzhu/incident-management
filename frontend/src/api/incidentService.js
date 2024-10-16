// frontend/src/api/incidentService.js

import axios from 'axios';

const BASE_URL = process.env.REACT_APP_BASE_URL || 'http://localhost:8080/incidents';

const getAuthToken = () => localStorage.getItem('authToken');

const headers = () => ({
    'Content-Type': 'application/json',
    'Authorization': `Basic ${getAuthToken()}`
});

export const getIncidents = async () => {
    try {
        const response = await axios.get(BASE_URL, { headers: headers() });
        return response.data;
    } catch (error) {
        console.error('Error fetching incidents:', error);
        throw error;
    }
};

export const addIncident = async (incident) => {
    try {
        const response = await axios.post(BASE_URL, incident, { headers: headers() });
        return response.data;
    } catch (error) {
        console.error('Error adding incident:', error);
        throw error;
    }
};

export const updateIncident = async (id, incident) => {
    try {
        const response = await axios.put(`${BASE_URL}/${id}`, incident, { headers: headers() });
        return response.data;
    } catch (error) {
        console.error(`Error updating incident with id ${id}:`, error);
        throw error;
    }
};

export const deleteIncident = async (id) => {
    try {
        const response = await axios.delete(`${BASE_URL}/${id}`, { headers: headers() });
        return response.data;
    } catch (error) {
        console.error(`Error deleting incident with id ${id}:`, error);
        throw error;
    }
};