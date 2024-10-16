// frontend/src/components/IncidentList.js

import React, { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { deleteIncident } from '../api/incidentService';

const IncidentList = ({ incidents, fetchIncidents, setSelectedIncident }) => {
    const { role } = useContext(AuthContext);
    const handleDelete = async (id) => {
        if (role === 'ROLE_ADMIN') {
            try {
                await deleteIncident(id);
                fetchIncidents();
            } catch (error) {
                console.error('There was an error!', error);
            }
        }
    };

    return (
        <table className="table table-striped">
            <thead className="thead-dark">
            <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Status</th>
                {role === 'ROLE_ADMIN' && <th>Actions</th>} {/* Only show Actions column for Admins */}
            </tr>
            </thead>
            <tbody>
            {incidents.map((incident) => (
                <tr key={incident.id}>
                    <td>{incident.title}</td>
                    <td>{incident.description}</td>
                    <td>{incident.status}</td>
                    {role === 'ROLE_ADMIN' && (
                    <td>
                        <button
                            className="btn btn-sm btn-warning mr-2"
                            onClick={() => setSelectedIncident(incident)}
                        >
                            Edit
                        </button>
                        <button
                            className="btn btn-sm btn-danger"
                            onClick={() => handleDelete(incident.id)}
                        >
                            Delete
                        </button>
                    </td>
                    )}
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default IncidentList;