export interface Product {
  id: number;
  title: string;
  stock: number;
  price: number;
  sizes: Size[];
  brands: Brand[];
  categories: Category[];
  description: string;
  picture: string;
}

export interface Size {
  id: number;
  value: string;
}

export interface Brand {
  id: number;
  name: string;
}

export interface Category {
  id: number;
  name: string;
}
