import "./page.css";

export default function HeaderHome() {
  return (
    <header className="topbar">
      <a className="brand" href="#home" aria-label="RailSync home">
        <span className="brand-mark" aria-hidden="true">R</span>
        <span><strong>RailSync</strong><small>Train operations</small></span>
      </a>
      <nav className="topbar-actions" aria-label="Main navigation">
        <span className="system-status"><i /> All systems ready</span>
        <a className="text-link" href="#access">Sign in <span aria-hidden="true">↗</span></a>
      </nav>
    </header>
  );
}
