import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter, Routes, Route } from 'react-router-dom'
import { QueryProvider } from '../../../providers/QueryProvider'
import { AuthProvider } from '../../../providers/AuthProvider'
import VehicleEntryPayment from '../pages/Payment'

beforeEach(() => {
  localStorage.setItem('token', 'token123')
})

test('vehicle entry payment', async () => {
  render(
    <QueryProvider>
      <AuthProvider>
        <MemoryRouter initialEntries={['/app/vehicle-entries/1/payment']}>
          <Routes>
            <Route
              path="/app/vehicle-entries/:entryId/payment"
              element={<VehicleEntryPayment />}
            />
          </Routes>
        </MemoryRouter>
      </AuthProvider>
    </QueryProvider>
  )

  await userEvent.type(screen.getByPlaceholderText(/amount/i), '10')
  await userEvent.click(screen.getByText(/pay/i))
  expect(await screen.findByText(/success/i)).toBeInTheDocument()
})
