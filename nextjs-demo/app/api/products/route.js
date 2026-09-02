import { createProduct, listProducts } from "@/lib/products";

export const dynamic = "force-dynamic";

export async function GET() {
  return Response.json(listProducts());
}

export async function POST(request) {
  const body = await request.json();
  const name = typeof body.name === "string" ? body.name.trim() : "";
  const category = typeof body.category === "string" ? body.category.trim() : "";
  const stock = Number(body.stock);

  if (!name || !category || !Number.isInteger(stock) || stock < 0 || stock > 9999) {
    return Response.json(
      { message: "Ürün adı, kategori ve geçerli bir stok adedi gereklidir." },
      { status: 400 },
    );
  }

  return Response.json(createProduct({ name, category, stock }), { status: 201 });
}
