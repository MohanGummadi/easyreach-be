import { HTMLAttributes } from 'react'

export const Card = ({ className = '', ...props }: HTMLAttributes<HTMLDivElement>) => (
  <div className={`border rounded p-4 shadow-sm ${className}`} {...props} />
)
