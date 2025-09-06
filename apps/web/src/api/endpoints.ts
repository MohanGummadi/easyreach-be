export const endpoints = {
  auth: {
    register: '/auth/register',
    login: '/auth/login',
    refresh: '/auth/refresh',
    logout: '/auth/logout',
  },
  companies: {
    root: '/api/companies',
    byId: (id: string) => `/api/companies/${id}`,
  },
  vehicleEntryOps: {
    payment: (entryId: string) => `/api/vehicle-entries-ops/${entryId}/payment`,
  },
}
