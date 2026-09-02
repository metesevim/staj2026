# Next.js frontend + backend demo

Issue #47 için hazırlanan küçük bir full-stack ürün yönetimi uygulamasıdır.

## Çalıştırma

```bash
npm install
npm run dev
```

Uygulama `http://localhost:3000` adresinde açılır.

## API

- `GET /api/products`: ürünleri listeler
- `POST /api/products`: ürün ekler
- `DELETE /api/products/{id}`: ürün siler

Veriler demo amacıyla uygulama belleğinde tutulur ve sunucu yeniden başladığında
başlangıç verilerine döner.
