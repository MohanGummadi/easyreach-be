export interface UserDto {
  id?: string;
  email: string;
  name: string;
  companyId?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RefreshRequest {
  refreshToken: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  user: UserDto;
}

export interface CompanyRequestDto {
  companyName: string;
}

export interface CompanyResponseDto extends CompanyRequestDto {
  id: string;
}

export interface VehicleEntryResponseDto {
  entryId: string;
  amount: number;
  paidAmount?: number;
  pendingAmt?: number;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export interface PagingParams {
  page?: number;
  size?: number;
}
