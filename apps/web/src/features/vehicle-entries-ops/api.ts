import { useMutation } from '@tanstack/react-query'
import api from '../../api/axios'
import { endpoints } from '../../api/endpoints'
import type { VehicleEntryResponseDto } from '@easyreach/types'

export interface PaymentRequest {
  amount: number
  receivedBy?: string
  when?: string
}

export const useVehicleEntryPayment = (entryId: string) =>
  useMutation({
    mutationFn: (data: PaymentRequest) =>
      api
        .post<VehicleEntryResponseDto>(endpoints.vehicleEntryOps.payment(entryId), data)
        .then((r) => r.data),
  })
