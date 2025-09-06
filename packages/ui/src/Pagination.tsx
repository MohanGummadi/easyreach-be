export interface PaginationProps {
  page: number
  totalPages: number
  onPageChange: (p: number) => void
}

export const Pagination = ({ page, totalPages, onPageChange }: PaginationProps) => (
  <div className="flex gap-2 mt-2">
    <button disabled={page <= 0} onClick={() => onPageChange(page - 1)}>
      Prev
    </button>
    <span>
      {page + 1}/{totalPages}
    </span>
    <button disabled={page + 1 >= totalPages} onClick={() => onPageChange(page + 1)}>
      Next
    </button>
  </div>
)
