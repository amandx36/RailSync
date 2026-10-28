"use client";

import { FormEvent, useState } from "react";
import { api } from "@/lib/api-client";
import "./page.css";

type AuthMode = "login" | "register";

export default function MiddlePartHome() {
  const [mode, setMode] = useState<AuthMode>("login");
  const [busy, setBusy] = useState(false);
  const [notice, setNotice] = useState<{ text: string; success: boolean } | null>(null);

  async function submitAuth(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setBusy(true);
    setNotice(null);
    const form = new FormData(event.currentTarget);
    const email = String(form.get("email") ?? "").trim();
    const password = String(form.get("password") ?? "");

    try {
      if (mode === "register") {
        const result = await api.register({
          email,
          password,
          firstName: String(form.get("firstName") ?? "").trim(),
          lastName: String(form.get("lastName") ?? "").trim(),
        });
        if (result.token) {
          localStorage.setItem("railsync-token", result.token);
          setNotice({ text: "Account created. You’re ready to get started.", success: true });
        } else {
          setNotice({ text: result.message || "Could not create your account.", success: false });
        }
      } else {
        const result = await api.login({ email, password });
        if (result.token) {
          localStorage.setItem("railsync-token", result.token);
          setNotice({ text: "Signed in successfully. Welcome to RailSync.", success: true });
        } else {
          setNotice({ text: result.message || "Could not sign in.", success: false });
        }
      }
    } catch (error) {
      setNotice({
        text: error instanceof Error ? error.message : "Unable to reach the server. Please try again.",
        success: false,
      });
    } finally {
      setBusy(false);
    }
  }

  return (
    <>
      <section className="hero" id="home">
        <div className="hero-copy">
          <p className="eyebrow"><span className="live-dot" /> YOUR RAIL NETWORK, IN VIEW</p>
          <h1>Keep every<br /><em>journey</em> moving.</h1>
          <p className="hero-intro">One clear place to manage train services, routes, and availability. Less friction for your team. Better journeys for everyone.</p>
          <a className="hero-cta" href="#access">Get started <span aria-hidden="true">↓</span></a>
          <div className="hero-meta"><span>01</span><span className="meta-divider" /> MADE FOR MODERN RAIL</div>
        </div>
        <div className="hero-note"><span className="note-line" /> A clearer view<br />of what moves you.</div>
        <div className="hero-orbit" aria-hidden="true"><span>RS</span></div>
      </section>

      <section className="intro-strip" aria-label="Platform highlights">
        <p>BUILT FOR THE PEOPLE BEHIND EVERY JOURNEY</p>
        <div><span>01</span> Services <i>✳</i> <span>02</span> Routes <i>✳</i> <span>03</span> Availability</div>
      </section>

      <section className="access-section" id="access">
        <div className="access-copy">
          <p className="eyebrow">YOUR OPERATIONS DESK</p>
          <h2>Good to have<br />you on board.</h2>
          <p>Sign in to your RailSync account or create one to get your rail operations moving.</p>
          <div className="access-footnote"><span className="secure-icon">✳</span><span>Secure access<br /><small>Your account, your operations.</small></span></div>
        </div>
        <div className="auth-card">
          <div className="auth-card-heading"><span>ACCOUNT ACCESS</span><span className="auth-index">01 / 02</span></div>
          <div className="auth-tabs" role="tablist" aria-label="Account access">
            <button type="button" role="tab" aria-selected={mode === "login"} className={mode === "login" ? "active" : ""} onClick={() => { setMode("login"); setNotice(null); }}>Sign in</button>
            <button type="button" role="tab" aria-selected={mode === "register"} className={mode === "register" ? "active" : ""} onClick={() => { setMode("register"); setNotice(null); }}>Create account</button>
          </div>
          <form className="auth-form" onSubmit={submitAuth}>
            {mode === "register" && <div className="form-grid">
              <label>First name<input name="firstName" autoComplete="given-name" required /></label>
              <label>Last name<input name="lastName" autoComplete="family-name" required /></label>
            </div>}
            <label>Email address<input name="email" type="email" autoComplete="email" placeholder="you@example.com" required /></label>
            <label>Password<input name="password" type="password" autoComplete={mode === "login" ? "current-password" : "new-password"} minLength={8} maxLength={100} placeholder="At least 8 characters" required /></label>
            {notice && <p className={notice.success ? "form-success" : "form-error"} role="status">{notice.text}</p>}
            <button className="button button-primary button-wide" type="submit" disabled={busy}>{busy ? "Please wait…" : mode === "login" ? "Sign in to RailSync" : "Create your account"}<span aria-hidden="true"> ↗</span></button>
          </form>
          <p className="auth-legal">By continuing, you agree to use RailSync responsibly.</p>
        </div>
      </section>
    </>
  );
}
