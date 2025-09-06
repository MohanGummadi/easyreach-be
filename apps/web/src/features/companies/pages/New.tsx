import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { Input, Button } from '@easyreach/ui'
import { useCreateCompany } from '../api'
import { useNavigate } from 'react-router-dom'

const schema = z.object({
  companyName: z.string().min(1),
})

export default function CompanyNew() {
  const navigate = useNavigate()
  const mutation = useCreateCompany()
  const { register, handleSubmit } = useForm<z.infer<typeof schema>>({ resolver: zodResolver(schema) })
  const onSubmit = async (data: z.infer<typeof schema>) => {
    await mutation.mutateAsync(data)
    navigate('/app/companies')
  }
  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-2 max-w-sm">
      <Input placeholder="Company Name" {...register('companyName')} />
      <Button type="submit">Save</Button>
    </form>
  )
}
