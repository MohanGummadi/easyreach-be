import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
})

let isRefreshing = false
let subscribers: ((token: string) => void)[] = []

function onRefreshed(token: string) {
  subscribers.forEach((cb) => cb(token))
  subscribers = []
}

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  const headerName = import.meta.env.VITE_COMPANY_HEADER_NAME || 'X-Company-Id'
  const company = localStorage.getItem('companyId')
  if (company) {
    ;(config.headers as Record<string, string>)[headerName] = company
  }
  return config
})

api.interceptors.response.use(
  (res) => res,
  async (error) => {
    const { response, config } = error
    if (response?.status === 401) {
      const refreshToken = localStorage.getItem('refreshToken')
      if (refreshToken) {
        if (isRefreshing) {
          return new Promise((resolve) => {
            subscribers.push((token) => {
              config.headers.Authorization = `Bearer ${token}`
              resolve(api.request(config))
            })
          })
        }
        isRefreshing = true
        try {
          const r = await axios.post(`${import.meta.env.VITE_API_BASE_URL}/auth/refresh`, {
            refreshToken,
          })
          localStorage.setItem('token', r.data.accessToken)
          localStorage.setItem('refreshToken', r.data.refreshToken)
          onRefreshed(r.data.accessToken)
          config.headers.Authorization = `Bearer ${r.data.accessToken}`
          return api.request(config)
        } catch (e) {
          localStorage.clear()
        } finally {
          isRefreshing = false
        }
      }
    }
    return Promise.reject(error)
  }
)

export default api
