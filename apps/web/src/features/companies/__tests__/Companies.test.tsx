import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter, Routes, Route } from 'react-router-dom'
import { QueryProvider } from '../../../providers/QueryProvider'
import { AuthProvider } from '../../../providers/AuthProvider'
import CompaniesList from '../pages/List'
import CompanyNew from '../pages/New'

beforeEach(() => {
  localStorage.setItem('token', 'token123')
})

test('list and create company', async () => {
  render(
    <QueryProvider>
      <AuthProvider>
        <MemoryRouter initialEntries={['/app/companies']}>
          <Routes>
            <Route path="/app/companies" element={<CompaniesList />} />
            <Route path="/app/companies/new" element={<CompanyNew />} />
          </Routes>
        </MemoryRouter>
      </AuthProvider>
    </QueryProvider>
  )

  expect(await screen.findByText('Acme')).toBeInTheDocument()
  await userEvent.click(screen.getByText(/new/i))
  await userEvent.type(screen.getByPlaceholderText(/company name/i), 'NewCo')
  await userEvent.click(screen.getByText(/save/i))
  expect(await screen.findByText('NewCo')).toBeInTheDocument()
})
