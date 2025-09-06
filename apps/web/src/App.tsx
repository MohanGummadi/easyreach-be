import './App.css'

function App() {
  return (
    <div className="min-h-screen flex flex-col">
      <header className="p-4 shadow-subtle flex items-center bg-brand-surface">
        <h1 className="text-xl font-semibold text-brand-ink">EasyReach</h1>
      </header>
      <main className="flex-1 p-4">
        <button className="bg-brand-ink text-white hover:bg-brand-ink-700 focus:outline-none focus:ring-2 focus:ring-brand-accent rounded-md px-4 py-2">Primary</button>
      </main>
    </div>
  )
}

export default App
