const initialProducts = [
  { id: 1, name: "Mekanik klavye", category: "Aksesuar", stock: 12 },
  { id: 2, name: "USB-C hub", category: "Bağlantı", stock: 7 },
  { id: 3, name: "Dikey mouse", category: "Aksesuar", stock: 19 },
];

const store = globalThis.__productStore ?? {
  products: [...initialProducts],
  nextId: initialProducts.length + 1,
};

globalThis.__productStore = store;

export function listProducts() {
  return store.products;
}

export function createProduct(product) {
  const created = { id: store.nextId++, ...product };
  store.products.push(created);
  return created;
}

export function removeProduct(id) {
  const index = store.products.findIndex((product) => product.id === id);
  if (index === -1) return false;
  store.products.splice(index, 1);
  return true;
}
