import { InputHTMLAttributes } from 'react'

export const Input = ({ className = '', ...props }: InputHTMLAttributes<HTMLInputElement>) => (
  <input className={`border px-2 py-1 rounded ${className}`} {...props} />
)
