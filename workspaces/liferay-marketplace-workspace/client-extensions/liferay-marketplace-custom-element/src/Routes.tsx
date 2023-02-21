import { BrowserRouter, Route, Routes } from 'react-router-dom';

import { DashboardPage } from './pages/DashboardPage/DashboardPage';
import { AppCreationFlow } from './pages/AppCreationFlow/AppCreationFlow';

export default function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<DashboardPage />} />
                <Route path='/create-new-app' element={<AppCreationFlow />} />
            </Routes>
        </BrowserRouter>
    );
}