export interface Book {
  id: number;
  title: string;
  author: string;
  isbn: string;
  description: string;
  genre: string;
  price: number;
  stockQuantity: number;
  imageUrl: string;
  featured: boolean;
}
