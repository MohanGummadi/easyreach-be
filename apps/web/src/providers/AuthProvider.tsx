import { createContext, useContext, useState, useEffect, ReactNode } from 'react'
import { AuthResponse, LoginRequest, UserDto } from '@easyreach/types'
import api from '../api/axios'
import { endpoints } from '../api/endpoints'

interface AuthContextType {
  user?: UserDto
  accessToken?: string
  refreshToken?: string
  login: (data: LoginRequest) => Promise<void>
  logout: () => Promise<void>
}

const AuthContext = createContext<AuthContextType>({
  login: async () => {},
  logout: async () => {},
})

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserDto | undefined>()
  const [accessToken, setAccessToken] = useState<string | undefined>()
  const [refreshToken, setRefreshToken] = useState<string | undefined>()

  useEffect(() => {
    const token = localStorage.getItem('token') || undefined
    const refresh = localStorage.getItem('refreshToken') || undefined
    const u = localStorage.getItem('user')
    if (token) setAccessToken(token)
    if (refresh) setRefreshToken(refresh)
    if (u) setUser(JSON.parse(u))
  }, [])

  const login = async (data: LoginRequest) => {
    const res = await api.post<AuthResponse>(endpoints.auth.login, data)
    setAccessToken(res.data.accessToken)
    setRefreshToken(res.data.refreshToken)
    setUser(res.data.user)
    localStorage.setItem('token', res.data.accessToken)
    localStorage.setItem('refreshToken', res.data.refreshToken)
    localStorage.setItem('user', JSON.stringify(res.data.user))
  }

  const logout = async () => {
    const refresh = localStorage.getItem('refreshToken')
    if (refresh) {
      try {
        await api.post(endpoints.auth.logout, { refreshToken: refresh })
      } catch {}
    }
    localStorage.clear()
    setAccessToken(undefined)
    setRefreshToken(undefined)
    setUser(undefined)
  }

  return (
    <AuthContext.Provider value={{ user, accessToken, refreshToken, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => useContext(AuthContext)
