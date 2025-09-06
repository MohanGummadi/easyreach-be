import { ReactNode } from 'react'

export interface DataTableColumn<T> {
  header: string
  accessor: (row: T) => ReactNode
}

export interface DataTableProps<T> {
  data: T[]
  columns: DataTableColumn<T>[]
}

export function DataTable<T>({ data, columns }: DataTableProps<T>) {
  return (
    <table className="min-w-full border">
      <thead>
        <tr>
          {columns.map((c, i) => (
            <th key={i} className="border px-2">
              {c.header}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((row, i) => (
          <tr key={i}>
            {columns.map((c, j) => (
              <td key={j} className="border px-2">
                {c.accessor(row)}
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  )
}
