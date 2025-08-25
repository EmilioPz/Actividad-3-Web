export interface Recipe {
  id: number;
  nombre: string;
  instrucciones: string;
  tiempo: string;
  imagen?: string | null;
}