"use client";

import { useCallback, useEffect, useMemo, useState } from "react";

const emptyForm = { name: "", category: "", stock: "" };

export default function Home() {
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const loadProducts = useCallback(async () => {
    setLoading(true);
    setError("");
    try {
      const response = await fetch("/api/products", { cache: "no-store" });
      if (!response.ok) throw new Error("Ürünler yüklenemedi.");
      setProducts(await response.json());
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadProducts();
  }, [loadProducts]);

  const totalStock = useMemo(
    () => products.reduce((total, product) => total + product.stock, 0),
    [products],
  );

  async function addProduct(event) {
    event.preventDefault();
    setSaving(true);
    setError("");
    try {
      const response = await fetch("/api/products", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ ...form, stock: Number(form.stock) }),
      });
      const result = await response.json();
      if (!response.ok) throw new Error(result.message);
      setProducts((current) => [...current, result]);
      setForm(emptyForm);
    } catch (requestError) {
      setError(requestError.message);
    } finally {
      setSaving(false);
    }
  }

  async function deleteProduct(id) {
    setError("");
    try {
      const response = await fetch(`/api/products/${id}`, { method: "DELETE" });
      if (!response.ok) throw new Error("Ürün silinemedi.");
      setProducts((current) => current.filter((product) => product.id !== id));
    } catch (requestError) {
      setError(requestError.message);
    }
  }

  return (
    <main>
      <header className="masthead">
        <div>
          <p className="eyebrow">STAJ2026 · NEXT.JS FULL-STACK DEMO</p>
          <h1>Stok<br />defteri</h1>
        </div>
        <div className="summary" aria-label="Stok özeti">
          <span>{products.length} ürün</span>
          <strong>{totalStock}</strong>
          <small>toplam stok</small>
        </div>
      </header>

      <section className="workspace">
        <form className="product-form" onSubmit={addProduct}>
          <div className="section-heading">
            <span>Yeni kayıt</span>
            <span>API · POST</span>
          </div>
          <label>
            Ürün adı
            <input required maxLength="80" value={form.name}
              onChange={(event) => setForm({ ...form, name: event.target.value })}
              placeholder="Örn. Mekanik klavye" />
          </label>
          <label>
            Kategori
            <input required maxLength="50" value={form.category}
              onChange={(event) => setForm({ ...form, category: event.target.value })}
              placeholder="Örn. Aksesuar" />
          </label>
          <label>
            Stok adedi
            <input required min="0" max="9999" type="number" value={form.stock}
              onChange={(event) => setForm({ ...form, stock: event.target.value })}
              placeholder="0" />
          </label>
          <button disabled={saving} type="submit">
            {saving ? "Kaydediliyor…" : "Ürünü kaydet"}
          </button>
          {error && <p className="error" role="alert">{error}</p>}
        </form>

        <section className="ledger" aria-live="polite">
          <div className="section-heading">
            <span>Güncel envanter</span>
            <span>API · GET / DELETE</span>
          </div>
          {loading ? (
            <p className="empty">Kayıtlar yükleniyor…</p>
          ) : products.length === 0 ? (
            <p className="empty">Henüz ürün yok. İlk kaydı soldaki formdan ekleyebilirsin.</p>
          ) : (
            <ul>
              {products.map((product) => (
                <li key={product.id}>
                  <span className="record-id">#{String(product.id).padStart(3, "0")}</span>
                  <div className="record-name">
                    <strong>{product.name}</strong>
                    <span>{product.category}</span>
                  </div>
                  <div className="stock"><strong>{product.stock}</strong><span>adet</span></div>
                  <button className="delete-button" type="button"
                    onClick={() => deleteProduct(product.id)}
                    aria-label={`${product.name} ürününü sil`}>Sil</button>
                </li>
              ))}
            </ul>
          )}
        </section>
      </section>
    </main>
  );
}
