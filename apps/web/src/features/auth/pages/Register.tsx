import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { Input, Button } from '@easyreach/ui'
import api from '../../../api/axios'
import { endpoints } from '../../../api/endpoints'
import { useNavigate } from 'react-router-dom'

const schema = z.object({
  name: z.string().min(1),
  email: z.string().email(),
  password: z.string().min(1),
})

export default function Register() {
  const navigate = useNavigate()
  const { register, handleSubmit } = useForm<z.infer<typeof schema>>({ resolver: zodResolver(schema) })
  const onSubmit = async (data: z.infer<typeof schema>) => {
    await api.post(endpoints.auth.register, data)
    navigate('/login')
  }
  return (
    <form onSubmit={handleSubmit(onSubmit)} className="p-4 space-y-2 max-w-sm mx-auto">
      <Input placeholder="Name" {...register('name')} />
      <Input placeholder="Email" {...register('email')} />
      <Input type="password" placeholder="Password" {...register('password')} />
      <Button type="submit">Register</Button>
    </form>
  )
}
