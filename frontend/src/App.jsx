import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

function Home() {
  return <h2 className="text-center mt-5">Página de inicio</h2>;
}

function About() {
  return <h2 className="text-center mt-5">Acerca de nosotros</h2>;
}

function App() {
  return (
    <BrowserRouter>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">
        <Link to="/" className="navbar-brand">MiApp</Link>
        <div className="navbar-nav">
          <Link to="/" className="nav-link">Inicio</Link>
          <Link to="/about" className="nav-link">Acerca</Link>
        </div>
      </nav>

      <div className="container mt-4">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;