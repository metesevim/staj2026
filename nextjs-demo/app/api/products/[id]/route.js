import { removeProduct } from "@/lib/products";

export async function DELETE(_request, { params }) {
  const { id } = await params;
  const productId = Number(id);

  if (!Number.isInteger(productId) || !removeProduct(productId)) {
    return Response.json({ message: "Ürün bulunamadı." }, { status: 404 });
  }

  return new Response(null, { status: 204 });
}
