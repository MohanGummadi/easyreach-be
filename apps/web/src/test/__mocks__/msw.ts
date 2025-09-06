import { setupServer } from 'msw/node'
import { http, HttpResponse } from 'msw'
import {
  AuthResponse,
  CompanyResponseDto,
  Page,
  VehicleEntryResponseDto,
} from '@easyreach/types'

let companies: CompanyResponseDto[] = [{ id: '1', companyName: 'Acme' }]

const handlers = [
  http.post('*/auth/login', async ({ request }) => {
    const body = (await request.json()) as any
    const res: AuthResponse = {
      accessToken: 'token123',
      refreshToken: 'refresh123',
      user: { id: '1', email: body.email, name: 'Test User' },
    }
    return HttpResponse.json(res)
  }),
  http.post('*/auth/refresh', () =>
    HttpResponse.json({ accessToken: 'token123', refreshToken: 'refresh123' })
  ),
  http.post('*/auth/logout', () => HttpResponse.json({})),
  http.get('*/api/companies', ({ request }) => {
    if (request.headers.get('authorization') !== 'Bearer token123') {
      return new HttpResponse(null, { status: 401 })
    }
    const res: Page<CompanyResponseDto> = {
      content: companies,
      number: 0,
      size: companies.length,
      totalElements: companies.length,
      totalPages: 1,
    }
    return HttpResponse.json(res)
  }),
  http.post('*/api/companies', async ({ request }) => {
    const body = (await request.json()) as any
    const newCompany = { id: String(companies.length + 1), companyName: body.companyName }
    companies.push(newCompany)
    return HttpResponse.json(newCompany)
  }),
  http.post('*/api/vehicle-entries-ops/:entryId/payment', async ({ params, request }) => {
    const body = (await request.json()) as any
    const res: VehicleEntryResponseDto = {
      entryId: params.entryId as string,
      amount: body.amount,
    }
    return HttpResponse.json(res)
  }),
]

export const server = setupServer(...handlers)
