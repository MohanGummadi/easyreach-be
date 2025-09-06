import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import api from './axios'
import type { Page, PagingParams } from '@easyreach/types'

export const useList = <T>(key: string, url: string, params?: PagingParams) =>
  useQuery({
    queryKey: [key, params],
    queryFn: async () => (await api.get<Page<T>>(url, { params })).data,
  })

export const useGet = <T>(key: string, url: string) =>
  useQuery({ queryKey: [key], queryFn: async () => (await api.get<T>(url)).data })

export const useCreate = <TReq, TRes>(key: string, url: string) => {
  const qc = useQueryClient()
  return useMutation({
    mutationFn: async (data: TReq) => (await api.post<TRes>(url, data)).data,
    onSuccess: () => qc.invalidateQueries({ queryKey: [key] }),
  })
}

export const useUpdate = <TReq, TRes>(key: string, urlBuilder: (id: string) => string) => {
  const qc = useQueryClient()
  return useMutation({
    mutationFn: async ({ id, data }: { id: string; data: TReq }) =>
      (await api.put<TRes>(urlBuilder(id), data)).data,
    onSuccess: () => qc.invalidateQueries({ queryKey: [key] }),
  })
}

export const useDelete = <TRes>(key: string, urlBuilder: (id: string) => string) => {
  const qc = useQueryClient()
  return useMutation({
    mutationFn: async (id: string) => (await api.delete<TRes>(urlBuilder(id))).data,
    onSuccess: () => qc.invalidateQueries({ queryKey: [key] }),
  })
}
