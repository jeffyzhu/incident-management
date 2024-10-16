import React, { useContext, useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, useNavigate } from 'react-router-dom';
import { AuthProvider, AuthContext } from './context/AuthContext';
import { getIncidents } from './api/incidentService';
import IncidentForm from './components/IncidentForm';
import IncidentList from './components/IncidentList';
import Login from './components/Login';
import NavBar from './components/NavBar';
import './App.css';

const PrivateRoute = ({ children }) => {
    const { role } = useContext(AuthContext);

    return role ? children : <Navigate to="/login" />;
};

const App = () => {


    return (
        <AuthProvider>
            <Router>
                <InnerApp />
            </Router>
        </AuthProvider>
    );
};

const InnerApp = () => {
    const [incidents, setIncidents] = useState([]);
    const [selectedIncident, setSelectedIncident] = useState(null);

    const fetchIncidents = async () => {
        try {
            const data = await getIncidents();
            setIncidents(data);
        } catch (error) {
            console.error('There was an error!', error);
        }
    };

    useEffect(() => {
        fetchIncidents();
    }, []);
    const { isAuthenticated, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <>
            {isAuthenticated && <NavBar handleLogout={handleLogout} />}
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route
                    path="/"
                    element={
                        <PrivateRoute>
                            <div className="container mt-4">
                                <IncidentForm
                                    selectedIncident={selectedIncident}
                                    fetchIncidents={fetchIncidents}
                                    setSelectedIncident={setSelectedIncident}
                                />
                                <IncidentList
                                    incidents={incidents}
                                    fetchIncidents={fetchIncidents}
                                    setSelectedIncident={setSelectedIncident}
                                />
                            </div>
                        </PrivateRoute>
                    }
                />
            </Routes>
        </>
    );
};

export default App;