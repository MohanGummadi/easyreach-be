import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useCompanies } from '../api'
import { DataTable, Pagination, Button } from '@easyreach/ui'

export default function CompaniesList() {
  const [page, setPage] = useState(0)
  const { data } = useCompanies({ page })
  return (
    <div className="space-y-2">
      <Link to="new">
        <Button>New</Button>
      </Link>
      <DataTable
        data={data?.content ?? []}
        columns={[{ header: 'Name', accessor: (r) => r.companyName }]}
      />
      {data && (
        <Pagination page={data.number} totalPages={data.totalPages} onPageChange={setPage} />
      )}
    </div>
  )
}
