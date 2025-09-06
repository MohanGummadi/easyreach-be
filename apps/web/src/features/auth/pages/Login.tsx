import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { LoginRequest } from '@easyreach/types'
import { useAuth } from '../../../providers/AuthProvider'
import { Input, Button } from '@easyreach/ui'
import { useNavigate } from 'react-router-dom'

const schema = z.object({
  email: z.string().email(),
  password: z.string().min(1),
})

export default function Login() {
  const navigate = useNavigate()
  const { login } = useAuth()
  const { register, handleSubmit } = useForm<LoginRequest>({ resolver: zodResolver(schema) })
  const onSubmit = async (data: LoginRequest) => {
    await login(data)
    navigate('/app')
  }
  return (
    <form onSubmit={handleSubmit(onSubmit)} className="p-4 space-y-2 max-w-sm mx-auto">
      <Input placeholder="Email" {...register('email')} />
      <Input type="password" placeholder="Password" {...register('password')} />
      <Button type="submit">Login</Button>
    </form>
  )
}
