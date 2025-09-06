import { Link } from 'react-router-dom'
import { Button } from '@easyreach/ui'
import { useAuth } from '../providers/AuthProvider'

export function Header() {
  const { logout } = useAuth()
  return (
    <header className="p-2 border-b flex justify-between">
      <Link to="/app">EasyReach</Link>
      <Button onClick={logout}>Logout</Button>
    </header>
  )
}
