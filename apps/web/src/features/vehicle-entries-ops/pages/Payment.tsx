import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { useParams } from 'react-router-dom'
import { Input, Button } from '@easyreach/ui'
import { useVehicleEntryPayment } from '../api'

const schema = z.object({
  amount: z.number().positive(),
  receivedBy: z.string().optional(),
  when: z.string().optional(),
})

type FormData = z.infer<typeof schema>

export default function VehicleEntryPayment() {
  const { entryId } = useParams<{ entryId: string }>()
  const mutation = useVehicleEntryPayment(entryId!)
  const { register, handleSubmit } = useForm<FormData>({
    resolver: zodResolver(schema),
  })
  const onSubmit = async (data: FormData) => {
    await mutation.mutateAsync(data)
  }
  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-2 max-w-sm">
      <Input
        type="number"
        step="0.01"
        placeholder="Amount"
        {...register('amount', { valueAsNumber: true })}
      />
      <Input placeholder="Received By" {...register('receivedBy')} />
      <Input type="datetime-local" placeholder="When" {...register('when')} />
      <Button type="submit">Pay</Button>
      {mutation.isSuccess && <p>Success</p>}
    </form>
  )
}
