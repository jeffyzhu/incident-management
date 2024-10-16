// frontend/src/components/IncidentForm.js

import React, { useContext, useState, useEffect } from 'react';
import { AuthContext } from '../context/AuthContext';
import { addIncident, updateIncident } from '../api/incidentService';

const IncidentForm = ({ selectedIncident, fetchIncidents, setSelectedIncident }) => {
    const { role } = useContext(AuthContext);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    useEffect(() => {
        if (selectedIncident) {
            setTitle(selectedIncident.title);
            setDescription(selectedIncident.description);
            setStatus(selectedIncident.status);
        } else {
            setTitle('');
            setDescription('');
            setStatus('');
        }
    }, [selectedIncident]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const incident = { title, description, status };
        try {
            if (selectedIncident && role === 'ROLE_ADMIN') {
                await updateIncident(selectedIncident.id, incident);
                setSuccess('Incident updated successfully.');
                setError(null);
                setSelectedIncident(null);
            } else if (!selectedIncident) {
                await addIncident(incident);
                setSuccess('Incident created successfully.');
                setError(null);
                setSelectedIncident(null);
            }
            setTitle('');
            setDescription('');
            setStatus('');
            fetchIncidents();
        } catch (error) {
            setError(error.response.data || 'Something went wrong. Please try again later.');
            setSuccess(null);
            console.error('There was an error!', error);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="mb-4">
            <div className="form-group">
                <label htmlFor="title">Title</label>
                <input
                    type="text"
                    id="title"
                    className="form-control"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                />
            </div>
            <div className="form-group">
                <label htmlFor="description">Description</label>
                <textarea
                    id="description"
                    className="form-control"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                ></textarea>
            </div>
            <div className="form-group">
                <label htmlFor="status">Status</label>
                <select
                    id="status"
                    className="form-control"
                    value={status}
                    onChange={(e) => setStatus(e.target.value)}
                    required
                >
                    <option value="">Select Status</option>
                    <option value="Open">Open</option>
                    <option value="Closed">Closed</option>
                    <option value="InProgress">In Progress</option>
                </select>
            </div>
            <button type="submit" className="btn btn-primary">
                {selectedIncident ? 'Update' : 'Create'} Incident
            </button>
            {error && <div className="alert alert-danger mt-3">{error}</div>}
            {success && <div className="alert alert-success mt-3">{success}</div>}
        </form>
    );
};

export default IncidentForm;