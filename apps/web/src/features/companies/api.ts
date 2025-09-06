import { CompanyRequestDto, CompanyResponseDto, PagingParams } from '@easyreach/types'
import { endpoints } from '../../api/endpoints'
import { useList, useCreate } from '../../api/hooks'

export const useCompanies = (params?: PagingParams) =>
  useList<CompanyResponseDto>('companies', endpoints.companies.root, params)

export const useCreateCompany = () =>
  useCreate<CompanyRequestDto, CompanyResponseDto>('companies', endpoints.companies.root)
