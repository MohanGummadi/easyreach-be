import { ButtonHTMLAttributes } from 'react'

export const Button = ({ className = '', ...props }: ButtonHTMLAttributes<HTMLButtonElement>) => (
  <button className={`px-2 py-1 border rounded ${className}`} {...props} />
)
