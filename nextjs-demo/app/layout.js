import "./globals.css";

export const metadata = {
  title: "Stok Defteri",
  description: "Next.js frontend ve API route demo uygulaması",
};

export default function RootLayout({ children }) {
  return (
    <html lang="tr">
      <body>{children}</body>
    </html>
  );
}
