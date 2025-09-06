import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from '../features/auth/pages/Login'
import Register from '../features/auth/pages/Register'
import ProtectedRoute from './ProtectedRoute'
import { AppShell } from '../layout/AppShell'
import CompaniesList from '../features/companies/pages/List'
import CompanyNew from '../features/companies/pages/New'
import VehicleEntryPayment from '../features/vehicle-entries-ops/pages/Payment'

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route element={<ProtectedRoute />}>
          <Route path="/app" element={<AppShell />}>
            <Route path="companies">
              <Route index element={<CompaniesList />} />
              <Route path="new" element={<CompanyNew />} />
            </Route>
            <Route path="vehicle-entries/:entryId/payment" element={<VehicleEntryPayment />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  )
}
