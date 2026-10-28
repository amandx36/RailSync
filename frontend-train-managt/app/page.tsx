import HeaderHome from "./components/header/page";
import MiddlePartHome from "./components/middelPartHome/page";
import FooterHome from "./components/footer/page";

export default function Home() {
  return (
    <div className="app-shell">
      <HeaderHome />
      <main><MiddlePartHome /></main>
      <FooterHome />
    </div>
  );
}
