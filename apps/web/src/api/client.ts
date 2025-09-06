import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
})

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  const headerName = import.meta.env.VITE_COMPANY_HEADER_NAME || 'X-Company-Id'
  const company = localStorage.getItem('companyId')
  if (company) {
    (config.headers as Record<string, string>)[headerName] = company
  }
  return config
})

api.interceptors.response.use(
  (res) => res,
  async (error) => {
    if (error.response?.status === 401) {
      try {
        const refreshToken = localStorage.getItem('refreshToken')
        if (refreshToken) {
          const r = await axios.post(`${import.meta.env.VITE_API_BASE_URL}/auth/refresh`, { refreshToken })
          localStorage.setItem('token', r.data.accessToken)
          error.config.headers.Authorization = `Bearer ${r.data.accessToken}`
          return api.request(error.config)
        }
      } catch {
        localStorage.removeItem('token')
        localStorage.removeItem('refreshToken')
      }
    }
    return Promise.reject(error)
  }
)

export default api
