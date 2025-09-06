import { Link } from 'react-router-dom'

export function Sidebar() {
  return (
    <aside className="w-48 border-r p-2">
      <ul className="space-y-2">
        <li>
          <Link to="/app/companies">Companies</Link>
        </li>
      </ul>
    </aside>
  )
}
