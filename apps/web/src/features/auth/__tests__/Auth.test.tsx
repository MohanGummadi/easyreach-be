import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import AppRoutes from '../../../routes'
import { QueryProvider } from '../../../providers/QueryProvider'
import { AuthProvider } from '../../../providers/AuthProvider'

test('login flow stores token and fetches companies', async () => {
  window.history.pushState({}, '', '/login')
  render(
    <QueryProvider>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </QueryProvider>
  )

  await userEvent.type(screen.getByPlaceholderText(/email/i), 'user@test.com')
  await userEvent.type(screen.getByPlaceholderText(/password/i), 'secret')
  await userEvent.click(screen.getByText(/login/i))

  await waitFor(() => expect(localStorage.getItem('token')).toBe('token123'))

  // navigate to companies
  await userEvent.click(await screen.findByText(/companies/i))
  expect(await screen.findByText('Acme')).toBeInTheDocument()
})
